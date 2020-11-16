package com.fei.framelibrary.skin;

import android.util.SparseArray;

import com.fei.framelibrary.skin.callback.SkinCallback;

import androidx.collection.SimpleArrayMap;

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

    private static SkinManager instance;
    //缓存每个界面所有view
    private SimpleArrayMap<SkinCallback, SparseArray<SkinView>> arrayMap = new SimpleArrayMap<>();

    static {
        instance = new SkinManager();
    }

    public static SkinManager getInstance() {
        return instance;
    }

    /**
     * 注册，缓存起来
     * */
    public void register(SkinCallback callback, SkinView skinView) {
        if (arrayMap.containsKey(callback)) {
            SparseArray<SkinView> skinViewSparseArray = arrayMap.get(callback);
            skinViewSparseArray.put(skinViewSparseArray.size(), skinView);
        } else {
            SparseArray<SkinView> skinViewSparseArray = new SparseArray<>();
            skinViewSparseArray.put(0, skinView);
            arrayMap.put(callback, skinViewSparseArray);
        }
    }

    /**
     * 加载皮肤文件
     * @param path 皮肤文件路径
     * */
    public void load(String path) {
        for (int i = 0; i < arrayMap.size(); i++) {
            SkinCallback skinCallback = arrayMap.keyAt(i);
            SparseArray<SkinView> skinViewSparseArray = arrayMap.valueAt(i);
            for (int j = 0; j < skinViewSparseArray.size(); j++) {
//                skinViewSparseArray.valueAt(i).skin();
//                skinCallback.callback();
            }
        }
    }

    /**
     * 恢复原来皮肤
     * */
    public void restore(){

    }

    /**
     * 解绑
     * */
    public void unregister(SkinCallback callback) {
        arrayMap.remove(callback);
    }
}


