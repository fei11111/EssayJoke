package com.fei.essayjoke;

import com.fei.framelibrary.base.BaseSkinApplication;
import com.fei.framelibrary.hook.HookStartActivityUtil;

/**
 * @ClassName: BaseApplication
 * @Description: java类作用描述
 * @Author: Fei
 * @CreateDate: 2020-11-01 14:25
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-11-01 14:25
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class EssayApplication extends BaseSkinApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            HookStartActivityUtil.hook(this,ProxyActivity.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
