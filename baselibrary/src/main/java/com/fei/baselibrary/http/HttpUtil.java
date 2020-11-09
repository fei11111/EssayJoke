package com.fei.baselibrary.http;

import android.content.Context;
import android.text.TextUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: HttpUtil
 * @Description: 网络请求工具类
 * @Author: Fei
 * @CreateDate: 2020-11-08 15:09
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-11-08 15:09
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class HttpUtil {

    public static final int POST = 0x001;
    public static final int GET = 0x002;

    //上下文
    private Context mContext;

    //url
    private String mUrl;

    //请求方式
    private int requestType = GET;

    //参数
    private Map mParams;

    //网络请求
    private static IHttpEngine httpEngine;

    private static HttpUtil instance;

    private HttpUtil(Context context) {
        mContext = context;
        mParams = new ConcurrentHashMap();
    }

    /**
     * 创建httpUtil
     */
    public static HttpUtil with(Context context) {
        if (instance == null) {
            synchronized (HttpUtil.class) {
                if (instance == null) {
                    instance = new HttpUtil(context);
                }
            }
        }
        return instance;
    }


    /**
     * 在Application初始化
     */
    public static void init(IHttpEngine mHttpEngine) {
        httpEngine = mHttpEngine;
    }

    /**
     * 改变网络请求框架
     */
    public static void exchange(IHttpEngine mHttpEngine) {
        httpEngine = mHttpEngine;
    }

    /**
     * get请求方式
     */
    public HttpUtil get(String url) {
        this.mUrl = url;
        requestType = GET;
        return this;
    }

    /**
     * post请求方式
     */
    public HttpUtil post(String url) {
        this.mUrl = url;
        requestType = POST;
        return this;
    }

    /**
     * 添加参数
     */
    public HttpUtil addParam(String key, Object value) {
        mParams.put(key, value);
        return this;
    }

    /**
     * 添加所有参数
     */
    public HttpUtil addParams(Map<String, Object> params) {
        mParams.putAll(params);
        return this;
    }

    /**
     * 执行
     */
    public void execute() {
        execute(EngineCallBack.DEFAULT_CALLBACK);
    }


    /**
     * 执行
     */
    public void execute(EngineCallBack callBack) {
        if (httpEngine == null) {
            throw new IllegalArgumentException("未调用init方法，初始化");
        }

        if (mContext == null) {
            throw new IllegalArgumentException("未调用init方法，初始化");
        }

        if (TextUtils.isEmpty(mUrl)) {
            throw new IllegalArgumentException("未初始化url");
        }

        //可以添加业务逻辑代码
        callBack.onPreExecute(mContext, mParams);

        if (requestType == GET) {
            httpEngine.get(mContext, mUrl, mParams, callBack);
        } else if (requestType == POST) {
            httpEngine.post(mContext, mUrl, mParams, callBack);
        }
    }


    /**
     * 解析一个类上面的class信息
     */
    public static Class<?> analysisClazzInfo(Object object) {
        Type genType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return (Class<?>) params[0];
    }

}
