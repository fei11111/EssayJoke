package com.fei.framelibrary.navigationBar;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.fei.baselibrary.view.navigationbar.AbsNavigationBar;
import com.fei.framelibrary.R;

/**
 * @ClassName: DefaultNavigatorBar
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2020/11/6 17:21
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/6 17:21
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DefaultNavigatorBar extends AbsNavigationBar {

    public DefaultNavigatorBar(@NonNull Builder.NavigationParam p) {
        super(p);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.app_head_view;
    }

//    public static class Builder extends AbsNavigationBar.Builder<DefaultNavigatorBar>{
//
//        public Builder(@NonNull Context context) {
//            super(context);
//        }
//
//        public Builder(@NonNull Context context, ViewGroup viewGroup) {
//            super(context, viewGroup);
//        }
//
//        @Override
//        protected DefaultNavigatorBar createNavigationBar(NavigationParam param) {
//            return new DefaultNavigatorBar(param);
//        }
//
//        public class DefaultNavigatorBarParam extends NavigationParam{
//
//            public DefaultNavigatorBarParam(Context mContext, ViewGroup mViewGroup) {
//                super(mContext, mViewGroup);
//            }
//        }
//    }
}
