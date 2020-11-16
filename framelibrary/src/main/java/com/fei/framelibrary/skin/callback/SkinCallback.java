package com.fei.framelibrary.skin.callback;

import android.view.View;

import com.fei.framelibrary.skin.SkinAttr;

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
     * @param view 拦截的view
     * @param attr 获取到的属性 ，包含资源名
     * */
    void callback(View view, SkinAttr attr, String packageName);
}
