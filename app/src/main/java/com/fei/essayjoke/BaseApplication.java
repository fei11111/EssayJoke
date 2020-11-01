package com.fei.essayjoke;

import android.app.Application;

import com.fei.baselibrary.ExceptionCrashHandler;

/**
 * @ClassName: BaseApplication
 * @Description: java类作用描述
 * @Author: Fei
 * @CreateDate: 2020-11-01 14:25
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-11-01 14:25
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ExceptionCrashHandler.getInstance().init(this);
    }
}
