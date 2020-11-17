package com.fei.framelibrary.skin;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.SparseArray;

import androidx.collection.SimpleArrayMap;

import com.fei.baselibrary.utils.DeviceUtil;
import com.fei.baselibrary.utils.LogUtils;
import com.fei.framelibrary.skin.attr.SkinResource;
import com.fei.framelibrary.skin.attr.SkinView;
import com.fei.framelibrary.skin.callback.SkinCallback;
import com.fei.framelibrary.skin.support.SkinPreUtil;

import java.io.File;

/**
 * @ClassName: SkinManager
 * @Description: 换肤管理工具
 * 获取每个界面的所有View，通过Attr获取资源名字，资源类型，生成SkinView，
 * 加载皮肤，遍历每个界面里所有的view，通过SkinView里SkinAttr的资源名字获取到皮肤的id，重新设置，保存皮肤到sp。
 * @Author: Fei
 * @CreateDate: 2020/11/16 15:28
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/16 15:28
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SkinManager {

    private static final String TAG = "SkinManager";
    private static Context context;
    private static SkinManager instance;
    //缓存每个界面所有view
    private SimpleArrayMap<SkinCallback, SparseArray<SkinView>> arrayMap = new SimpleArrayMap<>();
    //标志当前app是否需要换肤
    private boolean isNeedSkin = false;
    //当前app换肤资源
    private Resources resources;
    //当前皮肤包名
    private String packageName;
    //资源获取类
    private SkinResource skinResource;

    public static SkinManager getInstance(Context mContext) {
        if (instance == null) {
            synchronized (SkinManager.class) {
                if (instance == null) {
                    instance = new SkinManager(mContext);
                }
            }
        }
        return instance;
    }

    /**
     * 初始化
     */
    public SkinManager(Context mContext) {
        context = mContext.getApplicationContext();

        //1.从sp获取换肤文件路径
        String path = SkinPreUtil.getInstance(context).get();
        //2.资源获取类
        skinResource = new SkinResource(context);
        if (!TextUtils.isEmpty(path)) {
            if (!new File(path).exists()) {
                LogUtils.i(TAG, "皮肤文件不存在");
                SkinPreUtil.getInstance(context).clear();
                return;
            }
            //3.获取皮肤包名
            packageName = DeviceUtil.getApkPackageName(context, path);
            if (TextUtils.isEmpty(packageName)) {
                SkinPreUtil.getInstance(context).clear();
                LogUtils.i(TAG, "获取包名失败");
                return;
            }
            //4.创建皮肤资源类
            resources = skinResource.getResources(path);
            //5.设置需要换肤标识
            isNeedSkin = true;
        }
    }


    /**
     * 注册，缓存起来
     */
    public void register(SkinCallback callback, SkinView skinView) {
        if (arrayMap.containsKey(callback)) {
            SparseArray<SkinView> skinViewSparseArray = arrayMap.get(callback);
            skinViewSparseArray.put(skinViewSparseArray.size(), skinView);
        } else {
            SparseArray<SkinView> skinViewSparseArray = new SparseArray<>();
            skinViewSparseArray.put(0, skinView);
            arrayMap.put(callback, skinViewSparseArray);
        }
        //1.判断当前app状态是否需要换肤
        if (isNeedSkin && resources != null && !TextUtils.isEmpty(packageName)) {
            //2.如果需要，则替换换肤文件的资源
            skinView.skin(resources, packageName);
        }
    }

    /**
     * 更换皮肤
     */
    private void changeSkin() {
        for (int i = 0; i < arrayMap.size(); i++) {
            SkinCallback skinCallback = arrayMap.keyAt(i);
            SparseArray<SkinView> skinViewSparseArray = arrayMap.valueAt(i);
            for (int j = 0; j < skinViewSparseArray.size(); j++) {
                SkinView skinView = skinViewSparseArray.valueAt(j);
                skinView.skin(resources, packageName);
                skinCallback.callback(skinView, packageName, resources);
            }
        }
    }

    /**
     * 加载皮肤文件
     *
     * @param path 皮肤文件路径
     */
    public void load(String path) {
        //1.判断文件是否存在
        if (!new File(path).exists()) {
            LogUtils.i(TAG, "皮肤文件不存在");
            return;
        }
        //1.1检验文件签名

        //判断是否已经加载了
        if (isNeedSkin) {
            String currentPath = SkinPreUtil.getInstance(context).get();
            if (currentPath.equals(path)) {
                LogUtils.i(TAG, "已经加载了");
                return;
            }
        }

        //1.2加载换肤资源类
        if (skinResource == null) {
            LogUtils.i(TAG, "skinResource没有初始化");
            return;
        }
        resources = skinResource.getResources(path);
        if (resources == null) {
            LogUtils.i(TAG, "获取resources失败");
            return;
        }
        //1.3.获取皮肤包名
        packageName = DeviceUtil.getApkPackageName(context, path);
        if (TextUtils.isEmpty(packageName)) {
            LogUtils.i(TAG, "获取包名失败");
            return;
        }

        // 校验签名  增量更新再说

        //2.标志当前app需要换肤
        isNeedSkin = true;
        //3.进行换肤
        changeSkin();
        //4.修改sp换肤文件路径
        SkinPreUtil.getInstance(context).put(path);
    }

    /**
     * 恢复原来皮肤
     */
    public void restore() {
        //1.将换肤资源类置null
        resources = null;
        //2.加载原app换肤资源
        String path = context.getPackageResourcePath();
        //3.更换为app原来资源
        resources = skinResource.getResources(path);
        //4.更换包名
        packageName = context.getPackageName();
        //5.恢复皮肤
        changeSkin();
        //6.清除sp换肤路径
        SkinPreUtil.getInstance(context).clear();
        //7.重置当前app换肤标志
        isNeedSkin = false;
    }

    /**
     * 解绑
     */
    public void unregister(SkinCallback callback) {
        arrayMap.remove(callback);
    }
}


