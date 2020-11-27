package com.fei.framelibrary.hook;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName: HookStartActivityUtil
 * @Description: 添加startActivity钩子，版本不同，可能反射的类，接口会不同，但是大致思路都一样
 * @Author: Fei
 * @CreateDate: 2020/11/26 11:21
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/26 11:21
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class HookStartActivityUtil {

    /**
     * 从源码可得startActivity会调用AMS.startActivity,AMS实现了接口
     * 只要通过动态代理就可以将没有在AndroidManifest.xml注册的Activity
     * 替换成已经注册的Activity，并将原有intent放在bundle里存着
     * 之后再启动Activity前将原来的intent替换回来就可以了
     * 就是在ActivityThread.Handler处理启动Activity前，利用Handler机制
     * 因为ActivityThread.Handler启动Activity是重写void handleMessage()
     * 原来是在handleMessage前会先调用callback.handleMessage，所以只要在
     * handleMessage前新增callback.handleMessage替换回原来Intent就可以了
     * 增加Handler.CallBack拦截，替换回原来Intent
     */

    //1.动态代理ActivityTaskManager
    //2.获取调用startActivity的实例，也就是ActivityTaskManager.getService()
    //3.通过default获取SingleTon，因为实例是IActivityTaskManagerSingleton.get()
    //获取SingleTon里面的属性mInstance
    //4.将mInstance传进钩子生成新的对象
    //5.替换属性mInstance的值为钩子
    //6.判断方法名为StartActivity，替换intent为ProxyActivity,存储原有intent
    //7.反射获取ActivityThread对象
    //8.反射获取ActivityThread里的mH属性
    //9.获取mH的值
    //10.反射获取Handler.CallBack属性
    //11.新建Handler.CallBack对象，拦截LAUNCH_ACTIVITY,更换intent为原来intent
    //12.将对象重新赋值给mH callback属性

    private static final String TAG = "HookStartActivityUtil";
    //保存原有Intent
    private static final String EXTRA_ORIGINAL_INTENT = "extra_original_intent";

    /**
     * @param context    上下文
     * @param proxyClass 代理类，已经在AndroidManifest.xml注册了
     */
    public static void hook(Context context, Class<?> proxyClass) throws Exception {

        //判断是否为空
        Objects.requireNonNull(context);

        //判断是否为空
        Objects.requireNonNull(proxyClass);

        //拦截Instrumentation.execStartActivity
        hookStartActivity(context, proxyClass);
        //ActivityThread 重置回原来的Intent
        hookActivityThread();
    }

    /**
     * 拦截Instrumentation.execStartActivity
     */
    private static void hookStartActivity(Context context, Class<?> proxyClass) throws Exception {
        //属性名
        String fieldName = "";
        //获取调用startActivity对象的类
        Class<?> clazz = null;
        //startActivity接口
        Class<?> interfaceClazz = null;
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt < Build.VERSION_CODES.O) {
            //sdk < O(26)
            clazz = Class.forName("android.app.ActivityManagerNative");
            fieldName = "gDefault";
            interfaceClazz = Class.forName("android.app.IActivityManager");
        } else if (sdkInt >= Build.VERSION_CODES.O && sdkInt <= Build.VERSION_CODES.P) {
            //sdk >= O(26) 并且 sdk <= P(28)
            clazz = ActivityManager.class;
            fieldName = "IActivityManagerSingleton";
            interfaceClazz = Class.forName("android.app.IActivityManager");
        } else {
            //sdk >= Q(29)
            clazz = Class.forName("android.app.ActivityTaskManager");
            fieldName = "IActivityTaskManagerSingleton";
            interfaceClazz = Class.forName("android.app.IActivityTaskManager");
        }

        //获取startActivity调用对象的对象
        Object instanceObject = getInstanceObject(clazz, fieldName);

        if (instanceObject == null) return;

        //3.获取SingleTon，因为实例是IActivityTaskManagerSingleton.get()
        Class<?> singleTonClass = Class.forName("android.util.Singleton");
        //获取SingleTon里面的属性mInstance,mInstance就是startActivity的实例
        Field mInstanceField = singleTonClass.getDeclaredField("mInstance");
        mInstanceField.setAccessible(true);

        //singletonClass获取get()方法
        Method getMethod = singleTonClass.getDeclaredMethod("get");
        getMethod.setAccessible(true);
        //获取mInstanceField的值
        Object instance = getMethod.invoke(instanceObject);

        if (instance == null) return;

        //4.将mInstance传进钩子生成新的对象
        instance = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{interfaceClazz}, new HookInvocationHandler(instance, context, proxyClass));
        //5.替换属性mInstance的值为钩子
        mInstanceField.set(instanceObject, instance);
    }

    /**
     * 获取实现类的属性的值
     *
     * @param clazz     实现类
     * @param fieldName 属性名
     */
    private static Object getInstanceObject(Class<?> clazz, String fieldName) throws Exception {
        Field declaredField = clazz.getDeclaredField(fieldName);
        declaredField.setAccessible(true);
        //因为是静态常量，所以可以get直接获取属性值
        return declaredField.get(null);
    }


    /**
     * 拦截
     */
    private static class HookInvocationHandler implements InvocationHandler {
        private final Object mInstance;
        private final Context mContext;
        private final Class<?> mProxyClass;

        public HookInvocationHandler(Object instance, Context context, Class<?> proxyClass) {
            this.mInstance = instance;
            this.mContext = context;
            this.mProxyClass = proxyClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("startActivity")) {
                //拦截启动Activity，替换intent
                Intent intent = (Intent) args[2];
                Intent newIntent = new Intent(mContext, mProxyClass);
                //将旧的intent放入参数里
                newIntent.putExtra(EXTRA_ORIGINAL_INTENT, intent);
                //替换,绕过跳转验证AndroidManifest.xml
                args[2] = newIntent;
            }

            return method.invoke(mInstance, args);
        }
    }

    /**
     * hook ActivityThread 重置回原来的Intent
     */
    private static void hookActivityThread() throws Exception {
        //1.获取ActivityThread对象
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        //获取属性sCurrentActivityThread，因为它是静态的，所以可以获取属性值
        Field activityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
        activityThreadField.setAccessible(true);
        //获取到ActivityThread对象了
        Object activityThread = activityThreadField.get(null);
        if (activityThread == null) {
            return;
        }
        //获取属性mH，就是handler
        Field mHField = activityThreadClass.getDeclaredField("mH");
        mHField.setAccessible(true);
        //获取mH属性值
        Object mH = mHField.get(activityThread);
        //获取Handler的callBack属性
        Field mCallbackField = Handler.class.getDeclaredField("mCallback");
        mCallbackField.setAccessible(true);
        mCallbackField.set(mH, new HookHandleCallBack());
    }

    /**
     * 通过添加HandlerCallBack拦截
     */
    private static class HookHandleCallBack implements Handler.Callback {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    //>=28，没有LAUNCH_ACTIVITY
                    //拦截msg.what等于EXECUTE_TRANSACTION
                    if (msg.what == 159) {
                        //ClientTransaction transaction = (ClientTransaction) msg.obj
                        Object obj = msg.obj;
                        //        final List<ClientTransactionItem> callbacks = transaction.getCallbacks();
                        Field mActivityCallbacksField = obj.getClass().getDeclaredField("mActivityCallbacks");
                        mActivityCallbacksField.setAccessible(true);
                        Object callbacks = mActivityCallbacksField.get(obj);
                        if (callbacks == null) {
                            // No callbacks to execute, return early.
                            return false;
                        }
                        //将对象转成数组
                        List<Object> objects = (List<Object>) callbacks;
                        for (int i = 0; i < objects.size(); ++i) {
                            Object item = objects.get(i);
                            Class<?> launchActivityItemClass = Class.forName("android.app.servertransaction.LaunchActivityItem");
                            //如果item就是launchActivityItemClass
                            if (item.getClass() == launchActivityItemClass) {
                                //获取item里的mIntent属性
                                Field mIntentField = item.getClass().getDeclaredField("mIntent");
                                mIntentField.setAccessible(true);
                                //获取mIntent值
                                Intent intent = (Intent) mIntentField.get(item);
                                //替换为原来intent
                                Intent originalIntent = intent.getParcelableExtra(EXTRA_ORIGINAL_INTENT);
                                if (originalIntent != null) {
                                    mIntentField.set(item, originalIntent);
                                }
                            }
                        }
                    }
                } else {
                    //<28
                    if (msg.what == 100) {
                        //LAUNCH_ACTIVITY
                        //ActivityClientRecord r = (ActivityClientRecord) msg.obj;
                        Object object = msg.obj;
                        //获取ActivityClientRecord的属性intent
                        Field intentField = object.getClass().getDeclaredField("intent");
                        intentField.setAccessible(true);
                        //获取mIntent值
                        Intent intent = (Intent) intentField.get(object);
                        //替换为原来intent
                        Intent originalIntent = intent.getParcelableExtra(EXTRA_ORIGINAL_INTENT);
                        if (originalIntent != null) {
                            intentField.set(object, originalIntent);
                        }

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }
    }
}
