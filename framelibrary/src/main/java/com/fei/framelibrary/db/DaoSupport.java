package com.fei.framelibrary.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.ArrayMap;

import com.fei.baselibrary.utils.LogUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
class DaoSupport<T> implements IDaoSupport<T> {

    private static final String TAG = "DaoSupport";

    private final SQLiteDatabase db;
    private final Class<T> clazz;
    private String tableName;
    private Object[] objects = new Object[2];
    private ArrayMap<String, Method> methodArrayMap = new ArrayMap<>();//存放ContentValues的重载put方法
    private ArrayMap<String, Class<?>> fieldArrayMap = new ArrayMap<>();//存放所有属性的数据库存放类型


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
        sb.append("create table if not exists ").append(tableName).append("(id" +
                " integer primary key autoincrement, ");
        //获取类所有属性进行遍历
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            String name = field.getName();
            String type = field.getType().getSimpleName();//类型
            Class<?> fieldClass = DaoUtil.covertTypeToObjectType(type);
            if (fieldClass == null) {
                throw new IllegalArgumentException("数据类型错误");
            }
            fieldArrayMap.put(name, fieldClass);
            sb.append(name + " " + fieldClass.getSimpleName() + ", ");
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
    public T queryById(Long aLong) {
        return null;
    }

    @Override
    public List<T> query(Map<String, Object> map) {
        String selection = null;
        String[] selectionArgs = null;

        if (map != null) {
            selection = "";
            selectionArgs = new String[map.size()];
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            for (int i = 0; i < entries.size(); i++) {
                Map.Entry<String, Object> entry = entries.iterator().next();
                selection += entry.getKey();
                Object value = DaoUtil.formatValue(entry.getValue());
                selectionArgs[i] = value.toString();

                LogUtils.i(TAG, " selection = " + selection + " selectionArgs " + selectionArgs.toString());
            }
        }
        Cursor query = db.query(tableName, null, selection, selectionArgs, null, null, null);
        return DaoUtil.covertQueryToList(query,clazz);
    }

    @Override
    public List<T> queryForAll() {
        return query(null);
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
    public int deleteByIds(Collection<Long> longs) {
        return 0;
    }

    @Override
    public void dropTable() {

    }
}
