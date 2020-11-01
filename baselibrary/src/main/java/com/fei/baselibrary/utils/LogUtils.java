package com.fei.baselibrary.utils;

import android.util.Log;

import com.fei.baselibrary.BuildConfig;


/**
 * @ClassName: LogUtils
 * @Description: 日志工具类
 * @Author: Fei
 * @CreateDate: 2020-11-01 14:32
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-11-01 14:32
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class LogUtils {

    public static void v(final String tag, final String msg) {

        if (BuildConfig.BUILD_TYPE.equals("debug")) {
            Log.v(tag, "--> " + msg);
        }
    }

    public static void d(final String tag, final String msg) {
        if (BuildConfig.BUILD_TYPE.equals("debug")) {
            Log.d(tag, "--> " + msg);
        }
    }

    public static void i(final String tag, final String msg) {
        if (BuildConfig.BUILD_TYPE.equals("debug")) {
            Log.i(tag, "--> " + msg);
        }
    }

    public static void w(final String tag, final String msg) {
        if (BuildConfig.BUILD_TYPE.equals("debug")) {
            Log.w(tag, "--> " + msg);
        }
    }

    public static void e(final String tag, final String msg) {
        if (BuildConfig.BUILD_TYPE.equals("debug")) {
            Log.e(tag, "--> " + msg);
        }
    }

    public static void print(String tag, String msg) {
        System.out.println("tag==" + msg);
    }
}
