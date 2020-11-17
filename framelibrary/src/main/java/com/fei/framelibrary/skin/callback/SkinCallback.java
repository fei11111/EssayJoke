package com.fei.framelibrary.skin.callback;

import android.content.res.Resources;

import com.fei.framelibrary.skin.attr.SkinView;

/**
 * @ClassName: SkinCallback
 * @Description:
 * @Author: Fei
 * @CreateDate: 2020-11-16 19:36
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-11-16 19:36
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface SkinCallback {

    /**
     * @param skinView 拦截的view 包含view和skinArr，有属性名，资源名，资源类型，可以通过判断属性名更换皮肤
     * @param packageName 皮肤的包名
     * @param resources 皮肤资源类，利用该类进行皮肤替换
     */
    void callback(SkinView skinView, String packageName, Resources resources);
}
