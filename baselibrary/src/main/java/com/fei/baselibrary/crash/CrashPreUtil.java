package com.fei.baselibrary.crash;

import android.content.Context;

import com.fei.baselibrary.utils.SPUtils;

/**
 * @ClassName: CrashPreUtil
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2020/11/16 15:21
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/16 15:21
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CrashPreUtil {

    private static CrashPreUtil instance;
    private static Context context;

    public CrashPreUtil(Context context) {
        context = context.getApplicationContext();
    }

    public static CrashPreUtil getInstance(Context context) {
        if (instance == null) {
            synchronized (CrashPreUtil.class) {
                if (instance == null) {
                    instance = new CrashPreUtil(context);
                }
            }
        }
        return instance;
    }

    public void put(String key, Object value) {
        SPUtils.getInstance(context).init(CrashConfig.CRASH_FILE_NAME);
        SPUtils.getInstance(context).put(key, value);
    }

    public Object get(String key) {
        SPUtils.getInstance(context).init(CrashConfig.CRASH_FILE_NAME);
        return SPUtils.getInstance(context).get(key, "").toString();
    }
}
