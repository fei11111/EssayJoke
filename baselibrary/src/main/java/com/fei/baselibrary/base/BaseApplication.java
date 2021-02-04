package com.fei.baselibrary.base;

import android.app.Application;
import android.content.Context;

import com.fei.baselibrary.crash.ExceptionCrashHandler;
import com.fei.baselibrary.http.HttpUtil;
import com.fei.baselibrary.http.IHttpEngine;

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

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        init();
    }

    private void init() {
        //初始化全局异常
        initCrash();
        //初始化热修复
        initHotFix();
        //初始化网络请求引擎
        HttpUtil.initHttpEngine(initHttp());
    }

    protected abstract IHttpEngine initHttp();

    //初始化热修复
    private void initHotFix() {

    }

    //初始化全局异常
    private void initCrash() {
        ExceptionCrashHandler.getInstance().init(this);
    }

}
