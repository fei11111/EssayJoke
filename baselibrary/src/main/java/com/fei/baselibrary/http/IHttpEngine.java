package com.fei.baselibrary.http;

import android.content.Context;

import java.util.Map;

/**
 * @ClassName: IHttpEngine
 * @Description: 网络请求引擎
 * @Author: Fei
 * @CreateDate: 2020-11-08 15:08
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-11-08 15:08
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface IHttpEngine {

    //get请求
    void get(boolean cache, Context context, String url, Map<String, Object> params, EngineCallBack callBack);

    //post请求
    void post(boolean cache, Context context, String url, Map<String, Object> params, EngineCallBack callBack);
    //上传

    //下载

    //https添加证书

}
