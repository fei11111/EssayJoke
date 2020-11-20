package com.fei.baselibrary.permission;

import android.content.Context;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ClassName: PermissionHelper
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2020/11/20 9:56
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/20 9:56
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
class PermissionHelper {

    public static void success(Context context, int requestCode) {
        Method[] declaredMethods = context.getClass().getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            PermissionSuccess annotation = declaredMethod.getAnnotation(PermissionSuccess.class);
            if (annotation != null) {
                try {
                    if (!declaredMethod.isAccessible()) {
                        declaredMethod.setAccessible(true);
                    }
                    declaredMethod.invoke(context, requestCode);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void fail(Context context, int requestCode) {
        Method[] declaredMethods = context.getClass().getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            PermissionFail annotation = declaredMethod.getAnnotation(PermissionFail.class);
            if (annotation != null) {
                try {
                    if (!declaredMethod.isAccessible()) {
                        declaredMethod.setAccessible(true);
                    }
                    declaredMethod.invoke(context, requestCode);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
