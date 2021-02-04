package com.fei.baselibrary.http;

/**
 * @ClassName: ICacheEngine
 * @Description: 网络存储引擎
 * @Author: Fei
 * @CreateDate: 2021/2/4 10:04
 * @UpdateUser: Fei
 * @UpdateDate: 2021/2/4 10:04
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface ICacheEngine {

    public String getResult(String key);

    public void saveCache(String key,String result);

}
