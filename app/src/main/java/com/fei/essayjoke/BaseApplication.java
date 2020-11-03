package com.fei.essayjoke;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.alipay.euler.andfix.patch.PatchManager;
import com.fei.baselibrary.ExceptionCrashHandler;
import com.fei.baselibrary.utils.DeviceUtil;
import com.fei.baselibrary.utils.LogUtils;

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

    public static PatchManager patchManager;

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
        patchManager = new PatchManager(this);
        PackageInfo packageInfo = DeviceUtil.getPackageInfo(this);
        if (packageInfo != null) {
            LogUtils.i("tag",packageInfo.versionName);
            patchManager.init(packageInfo.versionName);//current version
        }
        patchManager.loadPatch();;

        File dir = Environment.getExternalStorageDirectory();
        File file = new File(dir, "out.apatch");
        Log.i("tag",file.getAbsolutePath());
        if(file.exists()) {
            try {
                BaseApplication.patchManager.addPatch(file.getAbsolutePath());
                Toast.makeText(this, "修复成功", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "修复失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //初始化全局异常
    private void initCrash() {
        ExceptionCrashHandler.getInstance().init(this);
    }
}
