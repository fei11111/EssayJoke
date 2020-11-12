package com.fei.framelibrary.db.crud;

import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.fei.framelibrary.db.DaoUtil;

import java.util.Map;

/**
 * @ClassName: UpdateSupport
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2020/11/12 17:10
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/12 17:10
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class UpdateSupport<T> {

    private final SQLiteDatabase db;
    private final String tableName;
    private String whereClause;
    private String[] whereArgs;

    public UpdateSupport(SQLiteDatabase db, String tableName) {
        this.db = db;
        this.tableName = tableName;
    }

    /**
     * 更新条件
     * */
    public UpdateSupport<T> where(@NonNull String where,@NonNull String... mWhereArgs) {
        whereClause = where;
        whereArgs = mWhereArgs;
        return this;
    }


    /**
     * 更新,可直接传map，查询条件的key和value
     */
    public UpdateSupport<T> select(@NonNull Map<String, Object> map) {
        DaoUtil.covertMapToParams(map, whereClause, whereArgs);
        return this;
    }

    //更新
    public void update() {
        if ((!TextUtils.isEmpty(whereClause) && TextUtils.isEmpty(whereArgs.toString())) ||
                (TextUtils.isEmpty(whereClause) && !TextUtils.isEmpty(whereArgs.toString()))) {
            throw new IllegalArgumentException("参数有误");
        }

        db.update(tableName, null, whereClause, whereArgs);
    }


}
