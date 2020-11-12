package com.fei.framelibrary.db.crud;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.fei.framelibrary.db.DaoUtil;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: QuerySupport
 * @Description: 查询支持类
 * @Author: Fei
 * @CreateDate: 2020/11/12 16:42
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/12 16:42
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class QuerySupport<T> {

    private final SQLiteDatabase db;
    private final Class<T> clazz;
    private final String tableName;
    private static final String TAG = "QuerySupport";
    private String[] mQueryColumns;
    private String selection;
    private String[] selectionArgs;
    private String groupBy;
    private String having;
    private String orderBy;
    private String limit;


    public QuerySupport(SQLiteDatabase db, String tableName, Class<T> clazz) {
        this.db = db;
        this.tableName = tableName;
        this.clazz = clazz;
    }

    public QuerySupport<T> columns(String... columns) {
        this.mQueryColumns = columns;
        return this;
    }

    /**
     * 查询条件 "age = ?"
     */
    public QuerySupport<T> selection(@NonNull String mSelection, @NonNull String... mSelectionArgs) {
        selection = mSelection;
        selectionArgs = mSelectionArgs;
        return this;
    }

    /**
     * 查询,只有一个查询条件可以使用
     */
    public QuerySupport<T> only(@NonNull String key, String value) {
        selection = key + " = ?";
        selectionArgs = new String[]{value};
        return this;
    }

    /**
     * 查询,可直接传map，查询条件的key和value
     */
    public QuerySupport<T> select(@NonNull Map<String, Object> map) {
        DaoUtil.covertMapToParams(map, selection, selectionArgs);
        return this;
    }

    public QuerySupport<T> groupBy(String mGroupBy) {
        groupBy = mGroupBy;
        return this;
    }

    public QuerySupport<T> having(String mHaving) {
        having = mHaving;
        return this;
    }

    public QuerySupport<T> orderBy(String mOrderBy) {
        orderBy = mOrderBy;
        return this;
    }

    public QuerySupport<T> limit(String mLimit) {
        limit = mLimit;
        return this;
    }


    /**
     * 查询
     */
    public List<T> query() {

        if ((!TextUtils.isEmpty(selection) && TextUtils.isEmpty(selectionArgs.toString())) ||
                (TextUtils.isEmpty(selection) && !TextUtils.isEmpty(selectionArgs.toString()))) {
            throw new IllegalArgumentException("参数有误");
        }

        Cursor query = db.query(tableName, mQueryColumns, selection, selectionArgs, groupBy, having, orderBy, limit);
        return DaoUtil.covertQueryToList(query, clazz);
    }

}
