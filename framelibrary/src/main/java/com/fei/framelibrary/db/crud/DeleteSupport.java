package com.fei.framelibrary.db.crud;

import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.fei.framelibrary.db.DaoUtil;

import java.util.Map;

/**
 * @ClassName: DeleteSupport
 * @Description: 删除支持类
 * @Author: Fei
 * @CreateDate: 2020/11/12 17:07
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/12 17:07
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DeleteSupport<T> {

    private final SQLiteDatabase db;
    private final String tableName;
    private String whereClause;
    private String[] whereArgs;

    public DeleteSupport(SQLiteDatabase db, String tableName) {
        this.db = db;
        this.tableName = tableName;
    }

    /**
     * 删除条件
     * */
    public DeleteSupport<T> where(@NonNull String where,@NonNull String... mWhereArgs) {
        whereClause = where;
        whereArgs = mWhereArgs;
        return this;
    }

    /**
     * 删除,可直接传map，查询条件的key和value
     */
    public DeleteSupport<T> select(Map<String, Object> map) {
        DaoUtil.covertMapToParams(map, whereClause, whereArgs);
        return this;
    }

    /**
     * 删除
     */
    public void delete() {
        if ((!TextUtils.isEmpty(whereClause) && TextUtils.isEmpty(whereArgs.toString())) ||
                (TextUtils.isEmpty(whereClause) && !TextUtils.isEmpty(whereArgs.toString()))) {
            throw new IllegalArgumentException("参数有误");
        }

        db.delete(tableName, whereClause, whereArgs);
    }

}
