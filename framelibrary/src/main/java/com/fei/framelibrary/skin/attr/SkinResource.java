package com.fei.framelibrary.skin.attr;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ClassName: SkinResource
 * @Description: 替换资源
 * @Author: Fei
 * @CreateDate: 2020/11/16 15:28
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/16 15:28
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

   /*
    *    try {
            Resources superRes = getResources();
            //AssetManager只能反射
            AssetManager assetManager = AssetManager.class.newInstance();
            //addAssetPath不可见，反射获取
            Method addAssetPath = assetManager.getClass().getDeclaredMethod("addAssetPath", String.class);
            //皮肤文件
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                    File.separator + "skin.skin");
            if (!file.exists()) {
                LogUtils.i(TAG, "皮肤文件不存在");
                return;
            }
            if (addAssetPath != null) {
                //加载皮肤
                addAssetPath.invoke(assetManager, file.getAbsolutePath());
                //创建皮肤Resources
                Resources resources = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
                // 通过资源名称,类型，包名获取Id
                int id = resources.getIdentifier("main_bg", "drawable", "com.fei.skin");
                Drawable drawable = resources.getDrawable(id);
                ImageView imageView = findViewById(R.id.iv_bg);
                imageView.setImageDrawable(drawable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    * */
public class SkinResource {

    private Context context;

    public SkinResource(Context mContext) {
        context = mContext.getApplicationContext();
    }

    public Resources getResources(String path) {
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
            Resources supResources = context.getResources();
            //创建新皮肤的资源类
            return new Resources(assetManager, supResources.getDisplayMetrics(), supResources.getConfiguration());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
