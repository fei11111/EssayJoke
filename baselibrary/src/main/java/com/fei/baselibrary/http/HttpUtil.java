package com.fei.baselibrary.http;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

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

    private HttpUtil(Context context) {
        mContext = context;
        mParams = new HashMap();
    }

    public static HttpUtil with(Context context) {
        return new HttpUtil(context);
    }

    /**
     *
     */
    public static void init(IHttpEngine mHttpEngine) {
        httpEngine = mHttpEngine;
    }

    public HttpUtil post() {
        requestType = POST;
        return this;
    }


}
