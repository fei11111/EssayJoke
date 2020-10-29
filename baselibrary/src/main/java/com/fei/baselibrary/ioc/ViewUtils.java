package com.fei.baselibrary.ioc;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ClassName: ViewUtils
 * @Description: 注解+反射完成findViewById和setOnClickListener
 * @Author: Fei
 * @CreateDate: 2020/10/29 17:38
 * @UpdateUser: Fei
 * @UpdateDate: 2020/10/29 17:38
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ViewUtils {

    ///可用到Activity
    public static void inject(Activity activity) {
        inject(new ViewFinder(activity), activity);
    }

    ///可用到Fragment，adapter等
    public static void inject(View view, Object object) {
        inject(new ViewFinder(view), object);
    }

    ///可用到自定义View
    public static void inject(View view) {
        inject(new ViewFinder(view), view);
    }

    /**
     * 注册
     *
     * @param object 类，通过这个类找属性和方法
     * @param finder findViewById
     */
    private static void inject(ViewFinder finder, Object object) {
        injectField(finder, object);
        injectMethod(finder, object);
    }

    //注册方法
    private static void injectMethod(ViewFinder finder, Object object) {
        ///1.获取所有属性
        Class<?> aClass = object.getClass();
        Method[] declaredMethods = aClass.getDeclaredMethods();
        ///2.获取Annotation的值
        for (Method declaredMethod : declaredMethods) {
            OnClick onClick = declaredMethod.getAnnotation(OnClick.class);
            CheckNet checkNet = declaredMethod.getAnnotation(CheckNet.class);
            if (onClick != null) {
                int[] viewIds = onClick.value();
                for (int viewId : viewIds) {
                    ///3.findViewById找到View
                    View view = finder.findViewById(viewId);
                    if (view != null) {
                        ///4.新增点击事件
                        view.setOnClickListener(new DeclareClickListener(declaredMethod, object, checkNet));
                    }
                }
            }
        }
    }

    private static class DeclareClickListener implements View.OnClickListener {
        private final Method method;
        private final Object object;
        private final CheckNet checkNet;

        public DeclareClickListener(Method declaredMethod, Object object, CheckNet checkNet) {
            this.method = declaredMethod;
            this.object = object;
            this.checkNet = checkNet;
        }

        @Override
        public void onClick(View view) {
            ///判断是否需要检测网络状态
            if (checkNet != null) {
                //需要
                boolean isNet = checkConnectStateByNetworkInfo(view.getContext());
                if (!isNet) {
                    Toast.makeText(view.getContext(),
                            TextUtils.isEmpty(checkNet.value()) ? "亲，你的网络不太给力！" :
                                    checkNet.value(), Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            ///5.反射方法
            try {
                method.setAccessible(true);
                method.invoke(object, view);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    //注册属性
    private static void injectField(ViewFinder finder, Object object) {
        ///1.获取所有属性
        Class<?> aClass = object.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            ///2.获取Annotation的值
            ViewById viewById = declaredField.getAnnotation(ViewById.class);
            if (viewById != null) {
                int viewId = viewById.value();
                ///3.findViewById找到View
                View view = finder.findViewById(viewId);
                if (view != null) {
                    ///4.注入到属性
                    declaredField.setAccessible(true);
                    try {
                        declaredField.set(object, view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 根据当前设备的活动网络信息判断网络状态
     *
     * @param mContext
     * @return
     */
    private static boolean checkConnectStateByNetworkInfo(Context mContext) {

        ConnectivityManager cm = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null &&
                (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE
                        || networkInfo.getType() == ConnectivityManager.TYPE_WIFI
                        || networkInfo.getType() == ConnectivityManager.TYPE_ETHERNET);
    }
}
