package com.fei.framelibrary.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.ArrayMap;

import com.fei.baselibrary.utils.LogUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: DaoSupport
 * @Description: dao实现类
 * @Author: Fei
 * @CreateDate: 2020/11/11 14:53
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/11 14:53
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DaoSupport<T> implements IDaoSupport<T, Long> {

    private static final String TAG = "DaoSupport";

    private final SQLiteDatabase db;
    private final Class<T> clazz;
    private String tableName;
    private Object[] objects = new Object[2];
    private ArrayMap<String, Method> methodArrayMap = new ArrayMap<>();


    public DaoSupport(SQLiteDatabase db, Class<T> clazz) {
        this.db = db;
        this.clazz = clazz;
    }

    /**
     * 创建表
     */
    @Override
    public void init() {

        /*"create table if not exists Person ("
                + "id integer primary key autoincrement, "
                + "name text, "
                + "age integer, "
                + "flag boolean)";*/
        tableName = clazz.getSimpleName();
        StringBuilder sb = new StringBuilder();
        sb.append("create table if not exists ").append(tableName).append(" ( id" +
                " integer primary key autoincrement, ");
        //获取类所有属性进行遍历
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            String name = field.getName();
            String type = field.getType().getSimpleName();//类型
            String dbType = DaoUtil.covertTypeToDbType(type);
            if (dbType == null) {
                throw new IllegalArgumentException("数据类型错误");
            }
            sb.append(name + " " + dbType + ", ");
        }
        sb.replace(sb.length() - 2, sb.length(), ")");
        LogUtils.i(TAG, sb.toString());
        db.execSQL(sb.toString());
    }

    @Override
    public long insert(T data) {
        ContentValues values = new ContentValues();
        Field[] declaredFields = data.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            objects[0] = field.getName();
            try {
                objects[1] = field.get(data);
                Class<?> type = objects[1].getClass().getComponentType();
                Method put = methodArrayMap.get(type.getSimpleName());
                if (put == null) {
                    put = values.getClass().getDeclaredMethod("put", String.class, type);
                    methodArrayMap.put(type.getSimpleName(), put);//将方法存入，减少找方法时间
                }
                put.setAccessible(true);
                put.invoke(values, objects[0], objects[1]);//反射注入数据;
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

    @Override
    public long insert(List<T> datas) {
        db.beginTransaction();
        for (T data : datas) {
            insert(data);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        return datas.size();
    }

    @Override
    public T queryForId(Long aLong) {
        return null;
    }

    @Override
    public List<T> queryForAll() {
        return null;
    }

    @Override
    public int update(T data) {
        return 0;
    }

    @Override
    public int update(T data, String whereColumnName, Map<String, String> excludeColumnNameMap) {
        return 0;
    }

    @Override
    public int updateId(T data, Long newId) {
        return 0;
    }

    @Override
    public int updateId(Collection<T> datas) {
        return 0;
    }

    @Override
    public int delete(T data) {
        return 0;
    }

    @Override
    public int deleteById(Long aLong) {
        return 0;
    }

    @Override
    public int delete(Collection<T> datas) {
        return 0;
    }

    @Override
    public int deleteIds(Collection<Long> longs) {
        return 0;
    }

    @Override
    public void dropTable() {

    }
}
