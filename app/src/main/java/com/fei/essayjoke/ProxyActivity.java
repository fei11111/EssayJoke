package com.fei.essayjoke;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.view.ContextThemeWrapper;

import com.fei.framelibrary.base.BaseSkinActivity;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import dalvik.system.BaseDexClassLoader;

/**
 * @ClassName: ProxyActivity
 * @Description: 代理界面
 * @Author: Fei
 * @CreateDate: 2020/11/26 11:38
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/26 11:38
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ProxyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String className = getIntent().getStringExtra("className");
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                File.separator + "app-debug.apk";
        try {
            //资源管理器
            AssetManager assetManager = AssetManager.class.newInstance();
            //获取加载资源方法
            Method addAssetPath = assetManager.getClass().getDeclaredMethod("addAssetPath", String.class);
            if (!addAssetPath.isAccessible()) {
                //如果不能访问，设置访问权限
                addAssetPath.setAccessible(true);
            }
            //反射调用方法，添加资源路径
            addAssetPath.invoke(assetManager, path);
            Resources supResources = getResources();
            //创建新皮肤的资源类
            supResources = new Resources(assetManager, supResources.getDisplayMetrics(), supResources.getConfiguration());
            //获取AppCompatActivity的属性mResources
            Field mResourcesField = AppCompatActivity.class.getDeclaredField("mResources");
            mResourcesField.setAccessible(true);
            //重新赋值
            mResourcesField.set(this, supResources);
        } catch (Exception e) {
            e.printStackTrace();
        }
        File optimizedDirectory = getCacheDir();
        BaseDexClassLoader mClassLoader = new BaseDexClassLoader(path,
                optimizedDirectory, null, getClassLoader());
        try {
            Object o = mClassLoader.loadClass(className).newInstance();
            Method method = o.getClass().getDeclaredMethod("onCreate");
            method.invoke(o,savedInstanceState);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
