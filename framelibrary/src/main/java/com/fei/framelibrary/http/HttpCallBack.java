package com.fei.framelibrary.http;

import android.content.Context;

import com.fei.baselibrary.http.EngineCallBack;
import com.fei.baselibrary.http.HttpUtil;
import com.google.gson.Gson;

import java.util.Map;

/**
 * @ClassName: HttpCallBack
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2020/11/9 17:11
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/9 17:11
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public abstract class HttpCallBack<T> implements EngineCallBack {

    private Gson gson = new Gson();

    @Override
    public void onPreExecute(Context context, Map<String, Object> params) {
        params.put("app_name","joke_essay");
        params.put("version_name","5.7.0");
        params.put("ac","wifi");
        params.put("device_id","30036118478");
        params.put("device_brand","Xiaomi");
        params.put("update_version_code","5701");
        params.put("manifest_version_code","570");
        params.put("longitude","113.000366");
        params.put("latitude","28.171377");
        params.put("device_platform","android");

        onPreExecute();
    }

    public abstract void onPreExecute();

    @Override
    public void onSuccess(String result) {
        T res = (T) gson.fromJson(result, HttpUtil.analysisClazzInfo(this));
        onSuccess(res);
    }

    public abstract void onSuccess(T result);
}
