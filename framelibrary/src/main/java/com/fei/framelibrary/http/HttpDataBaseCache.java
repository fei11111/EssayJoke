package com.fei.framelibrary.http;

import android.text.TextUtils;
import android.util.LruCache;

import com.fei.baselibrary.http.ICacheEngine;
import com.fei.baselibrary.utils.LogUtils;
import com.fei.baselibrary.utils.MD5Util;
import com.fei.framelibrary.db.DbSupportFactory;
import com.fei.framelibrary.db.IDaoSupport;

import java.util.List;

/**
 * @ClassName: HttpCacheData
 * @Description: http数据库缓存
 * @Author: Fei
 * @CreateDate: 2021/2/4 10:14
 * @UpdateUser: Fei
 * @UpdateDate: 2021/2/4 10:14
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class HttpDataBaseCache implements ICacheEngine {

    private final String TAG = "HttpDataBaseCache";

    private LruCache<String, String> mLruCache;

    public HttpDataBaseCache() {
        int maxMemory = (int) (Runtime.getRuntime().totalMemory() / 1024);
        int cacheSize = maxMemory / 8;
        mLruCache = new LruCache<String, String>(cacheSize);
    }

    /**
     * @param key url+参数作为可以
     */
    @Override
    public String getResult(String key) {
        String value = mLruCache.get(key);
        if (!TextUtils.isEmpty(value)) {
            return value;
        }
        //1.需要缓存，通过url加参数，加密转成key
        key = MD5Util.getMd5(key);
        //2.去数据库查询
        IDaoSupport<CacheData> dao = DbSupportFactory.getFactory().getDao(CacheData.class);
        List<CacheData> list = dao.getQuerySupport().only("key", key).query();
        //3.如果有结果直接返回
        if (list != null && list.size() > 0) {
            CacheData data = list.get(0);
            LogUtils.i(TAG, "从数据库中获取到数据:" + data.toString());
            value = data.getValue();
        }
        return value;
    }

    @Override
    public void saveCache(String key, String result) {
        mLruCache.put(key, result);
        //2.去数据库查询
        IDaoSupport<CacheData> dao = DbSupportFactory.getFactory().getDao(CacheData.class);
        if (dao != null && key != null && result != null) {
            dao.getDeleteSupport().where("key = ?", key).delete();
            CacheData cacheData = new CacheData();
            cacheData.setKey(key);
            cacheData.setValue(result);
            dao.getInsertSupport().insert(cacheData);
            LogUtils.i(TAG, "插入数据库中");
        }
    }
}
