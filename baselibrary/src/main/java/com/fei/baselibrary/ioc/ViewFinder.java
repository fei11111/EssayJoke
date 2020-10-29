package com.fei.baselibrary.ioc;

import android.app.Activity;
import android.view.View;

/**
 * @ClassName: ViewFinder
 * @Description: 通过FindViewById找到View
 * @Author: Fei
 * @CreateDate: 2020/10/29 17:35
 * @UpdateUser: Fei
 * @UpdateDate: 2020/10/29 17:35
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ViewFinder {

    private static Activity mActivity;
    private static View mView;

    public ViewFinder(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public ViewFinder(View mView) {
        this.mView = mView;
    }

    public View findViewById(int viewId) {
        return mActivity != null ? mActivity.findViewById(viewId) : mView.findViewById(viewId);
    }
}
