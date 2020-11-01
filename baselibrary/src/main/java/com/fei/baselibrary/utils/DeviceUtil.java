package com.fei.baselibrary.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * @ClassName: DeviceUtil
 * @Description: 设备工具类，获取手机版本，应用版本
 * @Author: Fei
 * @CreateDate: 2020-11-01 14:32
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-11-01 14:32
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DeviceUtil {

    /**
     * 获取手机信息，软件信息
     */
    public static HashMap<String, String> obtainSimpleInfo(Context context) {
        HashMap<String, String> map = new HashMap<>();
        PackageInfo packageInfo = getPackageInfo(context);
        if (packageInfo != null) {
            map.put("VERSION_NAME", packageInfo.versionName);
            map.put("VERSION_CODE", packageInfo.versionCode + "");
        }
        map.put("MODEL", Build.MODEL);
        map.put("SDK_INT", Build.VERSION.SDK_INT + "");
        map.put("PRODUCT", Build.PRODUCT);
        map.put("MOBILE_INFO", getMobileInfo());
        return map;
    }

    /**
     * 获取软件信息
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo;
    }

    /**
     * 获取手机信息
     */
    private static String getMobileInfo() {
        StringBuffer sb = new StringBuffer();
        Field[] declaredFields = Build.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            try {
                String name = declaredField.getName();
                String value = declaredField.get(null).toString();
                sb.append(name + " = " + value);
                sb.append("\n");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
