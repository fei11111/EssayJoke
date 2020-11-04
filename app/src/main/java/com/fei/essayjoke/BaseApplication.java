package com.fei.essayjoke;

import android.app.Application;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.fei.baselibrary.ExceptionCrashHandler;
import com.fei.baselibrary.fix.FixManager;

import java.io.File;
import java.io.IOException;

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
        init();
    }

    private void init() {
        //初始化全局异常
        initCrash();
        //初始化热修复
        initHotFix();
    }

    //初始化热修复
    private void initHotFix() {

    }

    //初始化全局异常
    private void initCrash() {
        ExceptionCrashHandler.getInstance().init(this);
    }
}
