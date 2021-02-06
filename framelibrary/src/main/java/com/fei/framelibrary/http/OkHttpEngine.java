package com.fei.framelibrary.http;

import android.content.Context;

import com.fei.baselibrary.http.EngineCallBack;
import com.fei.baselibrary.http.HttpUtil;
import com.fei.baselibrary.http.ICacheEngine;
import com.fei.baselibrary.http.IHttpEngine;
import com.fei.baselibrary.utils.LogUtils;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @ClassName: OkHttpEngine
 * @Description: okhttp网络请求引擎, 还需要完善
 * @Author: Fei
 * @CreateDate: 2020-11-08 15:11
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-11-08 15:11
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class OkHttpEngine implements IHttpEngine {

    private static final String TAG = "OkHttpEngine";
    private OkHttpClient mOkHttpClient = new OkHttpClient();
    public static final MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json;charset=utf-8");
    public static final MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");


    /**
     * 组装post请求参数body
     */
    private RequestBody appendMultipartBody(Map<String, Object> params) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        addParams(builder, params);
        return builder.build();
    }


    // 添加参数
    private void addParams(MultipartBody.Builder builder, Map<String, Object> params) {
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                builder.addFormDataPart(key, params.get(key) + "");
                Object value = params.get(key);
                if (value instanceof File) {
                    // 处理文件 --> Object File
                    File file = (File) value;
                    builder.addFormDataPart(key, file.getName(), RequestBody
                            .create(MediaType.parse(guessMimeType(file
                                    .getAbsolutePath())), file));
                } else if (value instanceof List) {
                    // 代表提交的是 List集合
                    try {
                        List<File> listFiles = (List<File>) value;
                        for (int i = 0; i < listFiles.size(); i++) {
                            // 获取文件
                            File file = listFiles.get(i);
                            builder.addFormDataPart(key + i, file.getName(), RequestBody
                                    .create(MediaType.parse(guessMimeType(file
                                            .getAbsolutePath())), file));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    builder.addFormDataPart(key, value + "");
                }
            }
        }
    }

    /**
     * 猜测文件类型
     */
    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }


    @Override
    public void get(boolean cache, Context context, String url, Map<String, Object> params, final EngineCallBack callBack, ICacheEngine cacheEngine) {
        url = HttpUtil.jointParams(url, params);
        WeakReference mContext = new WeakReference(context);
        if (cache) {
            String result = cacheEngine.getResult(url);
            //4.网络请求
            Request request = get(url, mContext);
            execute(mContext, request, callBack, url, result, cacheEngine);
        } else {
            Request request = get(url, mContext);
            execute(mContext, request, callBack);
        }


    }

    @Override
    public void post(boolean cache, final Context context, String url, Map<String, Object> params, final EngineCallBack callBack, ICacheEngine cacheEngine) {
        final WeakReference mContext = new WeakReference(context);
        if (cache) {
            //1.需要缓存，通过url，加密转成key
            String key = HttpUtil.jointParams(url, params);
            String value = cacheEngine.getResult(key);
            //4.网络请求
            Request request = post(url, mContext, params);
            execute(mContext, request, callBack, key, value, cacheEngine);
        } else {
            Request request = post(url, mContext, params);
            execute(mContext, request, callBack);
        }
    }

    /**
     * 创建get请求
     */
    private Request get(String url, WeakReference mContext) {
        LogUtils.i(TAG, url);
        return new Request.Builder()
                .url(url)
                .tag(mContext.get())
                .get()
                .build();
    }

    /**
     * 创建post请求
     */
    private Request post(String url, WeakReference mContext, Map<String, Object> params) {
        RequestBody requestBody = appendMultipartBody(params);
        LogUtils.i(TAG, requestBody.toString());
        return new Request.Builder()
                .url(url)
                .tag(mContext.get())
                .post(requestBody)
                .build();
    }

    /**
     * 发起请求
     */
    private void execute(WeakReference mContext, Request request, final EngineCallBack callBack) {
        execute(mContext, request, callBack, null, null, null);
    }

    /**
     * 发起请求，并保持到数据库
     */
    private void execute(final WeakReference mContext, final Request request, final EngineCallBack callBack, final String key, final String value, final ICacheEngine cacheEngine) {
        mOkHttpClient.newCall(request).enqueue(
                new Callback() {
                    @Override
                    public void onFailure(okhttp3.Call call, IOException e) {
                        if (mContext.get() == null) return;
                        callBack.onError(e);
                    }

                    @Override
                    public void onResponse(okhttp3.Call call, Response response) throws IOException {
                        // 这个 两个回掉方法都不是在主线程中
                        if (mContext.get() == null) return;
                        String result = response.body().string();
                        LogUtils.i(TAG, "请求返回" + result);

                        if (cacheEngine != null) {
                            if (!value.equals(result)) {
                                //保存缓存
                                cacheEngine.saveCache(key, result);
                            }
                        }

                        callBack.onSuccess(result);
                    }
                }
        );
    }
}
