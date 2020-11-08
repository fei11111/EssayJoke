package com.fei.baselibrary.http;

/**
 * @ClassName: EngineCallBack
 * @Description: 网络请求返回
 * @Author: Fei
 * @CreateDate: 2020-11-08 15:09
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-11-08 15:09
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface EngineCallBack {

    //失败
    void onError(Exception e);

    //成功
    void onSuccess(String result);

    //默认请求返回
    public static final EngineCallBack DEFAULT_CALLBACK = new EngineCallBack() {
        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onSuccess(String result) {

        }
    };

}
