package com.fei.framelibrary.base;

import com.fei.baselibrary.base.BaseApplication;
import com.fei.baselibrary.http.ICacheEngine;
import com.fei.baselibrary.http.IHttpEngine;
import com.fei.framelibrary.http.HttpDataBaseCache;
import com.fei.framelibrary.http.OkHttpEngine;
import com.fei.framelibrary.skin.SkinManager;

/**
 * @ClassName: BaseSkinApplication
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2020/11/9 15:40
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/9 15:40
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class BaseSkinApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化皮肤
        initSkin();
    }

    @Override
    protected IHttpEngine initHttpEngine() {
        return new OkHttpEngine();
    }

    @Override
    protected ICacheEngine initHttpCacheEngine() {
        return new HttpDataBaseCache();
    }

    private void initSkin() {
        SkinManager.getInstance().init(this);
    }

}
