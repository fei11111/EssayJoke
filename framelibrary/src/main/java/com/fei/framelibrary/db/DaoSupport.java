package com.fei.framelibrary.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.ArrayMap;

import com.fei.baselibrary.utils.LogUtils;
import com.fei.framelibrary.db.crud.DeleteSupport;
import com.fei.framelibrary.db.crud.InsertSupport;
import com.fei.framelibrary.db.crud.QuerySupport;
import com.fei.framelibrary.db.crud.UpdateSupport;

import java.lang.reflect.Field;

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
    private DeleteSupport<T> deleteSupport;
    private QuerySupport<T> querySupport;
    private InsertSupport<T> insertSupport;
    private UpdateSupport<T> updateSupport;
    private final ArrayMap<String, Class<?>> fieldArrayMap = new ArrayMap<>();

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
    public InsertSupport<T> getInsertSupport() {
        if (insertSupport == null) {
            insertSupport = new InsertSupport<>(db, tableName, fieldArrayMap);
        }
        return insertSupport;
    }


    @Override
    public QuerySupport<T> getQuerySupport() {
        if (querySupport == null) {
            querySupport = new QuerySupport<>(db, tableName, clazz);
        }
        return querySupport;
    }

    @Override
    public DeleteSupport<T> getDeleteSupport() {
        if (deleteSupport == null) {
            deleteSupport = new DeleteSupport<>(db, tableName);
        }
        return deleteSupport;
    }

    @Override
    public UpdateSupport<T> getUpdateSupport() {
        if (updateSupport == null) {
            updateSupport = new UpdateSupport<>(db, tableName);
        }
        return updateSupport;
    }


    @Override
    public void dropTable() {
        db.execSQL("drop table " + tableName);
    }
}
