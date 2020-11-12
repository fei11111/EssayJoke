package com.fei.framelibrary.db.crud;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.ArrayMap;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @ClassName: InsertSupport
 * @Description: 插入支持类
 * @Author: Fei
 * @CreateDate: 2020/11/12 17:07
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/12 17:07
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class InsertSupport<T> {

    private final SQLiteDatabase db;
    private final ArrayMap<String, Class<?>> fieldArrayMap;//存放所有属性名和数据库存放的类型
    private final String tableName;
    private Object[] objects = new Object[2];
    private ArrayMap<String, Method> methodArrayMap = new ArrayMap<>();//存放ContentValues的重载put方法


    public InsertSupport(SQLiteDatabase db, String tableName, ArrayMap<String, Class<?>> fieldArrayMap) {
        this.db = db;
        this.tableName = tableName;
        this.fieldArrayMap = fieldArrayMap;
    }

    /**
     * 插入
     * */
    public long insert(T data) {
        ContentValues values = new ContentValues();
        Field[] declaredFields = data.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            objects[0] = field.getName();
            try {
                objects[1] = field.get(data);
                String simpleName = field.getType().getSimpleName();
                Class<?> fieldClass = fieldArrayMap.get(objects[0]);
                if (fieldClass == null) throw new IllegalArgumentException("数据类型错误");
                Method put = methodArrayMap.get(simpleName);
                if (put == null) {
                    put = values.getClass().getDeclaredMethod("put", String.class, fieldClass);
                    methodArrayMap.put(simpleName, put);//将方法存入，减少找方法时间
                }
                put.invoke(values, objects);//反射注入数据;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } finally {
                objects[0] = null;
                objects[1] = null;
            }
        }
        return db.insert(tableName, null, values);
    }

    /**
     * 插入列表
     * */
    public long insert(List<T> datas) {
        db.beginTransaction();
        for (T data : datas) {
            insert(data);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        return datas.size();
    }

}
