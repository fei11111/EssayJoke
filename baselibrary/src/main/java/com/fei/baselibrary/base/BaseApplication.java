package com.fei.baselibrary.base;

import android.app.Application;

import com.fei.baselibrary.ExceptionCrashHandler;

/**
 * @ClassName: BaseApplication
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2020/11/9 15:38
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/9 15:38
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public abstract class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        //初始化全局异常
        initCrash();
        //初始化热修复
        initHotFix();
        //初始化网络请求引擎
        initHttp();
    }

    protected abstract void initHttp();

    //初始化热修复
    private void initHotFix() {

    }

    //初始化全局异常
    private void initCrash() {
        ExceptionCrashHandler.getInstance().init(this);
    }

}
