package com.fei.framelibrary.skin.support;

import android.content.Context;

import com.fei.baselibrary.utils.SPUtils;
import com.fei.framelibrary.skin.config.SkinConfig;

/**
 * @ClassName: SkinPreUtil
 * @Description: java类作用描述
 * @Author: Fei
 * @CreateDate: 2020-11-16 19:41
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-11-16 19:41
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SkinPreUtil {

    private static Context context;
    private static SkinPreUtil instance;

    private SkinPreUtil(Context mContext) {
        context = mContext.getApplicationContext();
    }

    public static SkinPreUtil getInstance(Context mContext) {
        if (instance == null) {
            synchronized (SkinPreUtil.class) {
                if (instance == null) {
                    instance = new SkinPreUtil(mContext);
                }
            }
        }
        return instance;
    }

    public String get() {
        SPUtils.getInstance(context).init(SkinConfig.SKIN_FILE);
        return SPUtils.getInstance(context).get(SkinConfig.SKIN_KEY, "").toString();
    }

    public void put(String value) {
        SPUtils.getInstance(context).init(SkinConfig.SKIN_FILE);
        SPUtils.getInstance(context).put(SkinConfig.SKIN_KEY, value);
    }

    public void clear() {
        SPUtils.getInstance(context).init(SkinConfig.SKIN_FILE);
        SPUtils.getInstance(context).put(SkinConfig.SKIN_KEY, "");
    }

}
