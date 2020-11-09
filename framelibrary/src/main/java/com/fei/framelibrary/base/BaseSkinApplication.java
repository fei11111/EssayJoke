package com.fei.framelibrary.base;

import com.fei.baselibrary.base.BaseApplication;
import com.fei.baselibrary.http.HttpUtil;
import com.fei.framelibrary.http.OkHttpEngine;

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
        //初始化请求方式
        initHttp();
    }

    /**
     * 初始化请求
     */
    @Override
    public void initHttp() {
        HttpUtil.init(new OkHttpEngine());
    }
}
