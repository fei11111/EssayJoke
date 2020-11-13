package com.fei.baselibrary.http;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.fei.baselibrary.utils.LogUtils;

import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName: HttpUtil 链式
 * @Description: 网络请求工具类
 * @Author: Fei
 * @CreateDate: 2020-11-08 15:09
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-11-08 15:09
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class HttpUtil {

    private static final String TAG = "HttpUtil";
    public static final int POST = 0x001;
    public static final int GET = 0x002;

    //上下文
    private static WeakReference<Context> mContext;

    //url
    private String mUrl;

    //请求方式
    private int requestType = GET;

    //参数
    private static Map mParams;

    //网络请求
    private static IHttpEngine httpEngine;

    //是否缓存
    private boolean cache;

    private static HttpUtil instance;

    private HttpUtil(Context context) {
        mContext = new WeakReference<>(context);
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
    public HttpUtil exchange(IHttpEngine mHttpEngine) {
        httpEngine = mHttpEngine;
        return this;
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
        if (mParams == null) {
            mParams = new HashMap();
        }
        mParams.put(key, value);
        return this;
    }

    /**
     * 添加所有参数
     */
    public HttpUtil addParams(Map<String, Object> params) {
        if (mParams == null) {
            mParams = new HashMap();
        }
        mParams.putAll(params);
        return this;
    }

    public HttpUtil cache(boolean mCache) {
        cache = mCache;
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
        callBack.onPreExecute(mContext.get(), mParams);

        if (requestType == GET) {
            httpEngine.get(cache, mContext.get(), mUrl, mParams, callBack);
        } else if (requestType == POST) {
            httpEngine.post(cache, mContext.get(), mUrl, mParams, callBack);
        }
    }


    /**
     * 解析一个类上面的泛型class信息
     */
    public static Class<?> analysisClazzInfo(Object object) {
        Type genType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return (Class<?>) params[0];
    }

    /**
     * 拼接url
     */
    public static String jointParams(@NonNull String url, Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        if (params != null) {
            sb.append("?");
            Set<Map.Entry<String, Object>> entries = params.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                sb.append(entry.getKey() + "=" + entry.getValue());
                sb.append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
