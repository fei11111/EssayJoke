package com.fei.framelibrary.http;

import com.fei.baselibrary.base.BaseApplication;
import com.fei.baselibrary.http.ICacheEngine;
import com.fei.baselibrary.utils.SPUtils;

/**
 * @ClassName: HttpSPCache
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2021/2/4 10:44
 * @UpdateUser: Fei
 * @UpdateDate: 2021/2/4 10:44
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class HttpSPCache implements ICacheEngine {
    @Override
    public String getResult(String key) {
        return (String) SPUtils.getInstance(BaseApplication.context).get(key, "");
    }

    @Override
    public void saveCache(String key, String result) {
        SPUtils.getInstance(BaseApplication.context).put(key, result);
    }
}
