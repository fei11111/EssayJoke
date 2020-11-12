package com.fei.framelibrary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.fei.baselibrary.utils.LogUtils;
import com.fei.framelibrary.base.BaseSkinApplication;

import java.io.File;

/**
 * @ClassName: DbSupportFactory
 * @Description: 数据库工厂
 * @Author: Fei
 * @CreateDate: 2020/11/11 14:25
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/11 14:25
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DbSupportFactory {

    private static final String TAG = "DbSupportFactory";

    private SQLiteDatabase db;

    private static DbSupportFactory factory;

    private DbSupportFactory() {
        File file = null;
        if (Environment.isExternalStorageEmulated()) {
            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +
                    BaseSkinApplication.context.getPackageName()
                    + File.separator + "db" +
                    File.separator + "essay.db");
        } else {
            file = new File(BaseSkinApplication.context.getDir(Environment.DIRECTORY_DOCUMENTS, Context.MODE_PRIVATE).
                    getAbsolutePath() + File.separator + "db" +
                    File.separator + "essay.db");
        }
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
            LogUtils.i(TAG, "新建数据库文件夹");
        }
        LogUtils.i(TAG, "数据库路径:" + file.getAbsolutePath());
        db = SQLiteDatabase.openOrCreateDatabase(file, null);
    }

    public static DbSupportFactory getFactory() {
        if (factory == null) {
            synchronized (DbSupportFactory.class) {
                if (factory == null) {
                    factory = new DbSupportFactory();
                }
            }
        }
        return factory;
    }

    public <T> IDaoSupport<T> getDao(Class<T> clazz) {
        IDaoSupport dao = new DaoSupport(db, clazz);
        dao.init();
        return dao;
    }


}
