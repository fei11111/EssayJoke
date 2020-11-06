package com.fei.baselibrary.view.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;

import com.fei.baselibrary.view.ViewHelper;

/**
 * @ClassName: AbsNavigationBar 抽象类
 * @Description: 自定义toolBar都集成这个类，实现自己的头部
 * @Author: Fei
 * @CreateDate: 2020/11/6 15:59
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/6 15:59
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public abstract class AbsNavigationBar implements INavigationBar {

    /**
     * 参数类，通过参数可以操作布局控件
     */
    private Builder.NavigationParam P;

    public AbsNavigationBar(@NonNull Builder.NavigationParam p) {
        P = p;
    }

    /**
     * 通过viewId获取控件
     */
    public <T extends View> T getView(@IdRes int viewId) {
        return P.mViewHelper.getView(viewId);
    }

    /**
     * 设置点击事件
     */
    public void setOnClickListener(@IdRes int viewId, View.OnClickListener onClickListener) {
        P.mViewHelper.setClickListener(viewId, onClickListener);
    }

    /**
     * 设置文本
     */
    public void setText(@IdRes int viewId, CharSequence text) {
        P.mViewHelper.setText(viewId, text);
    }


    /**
     * 构建类
     */
    public abstract class Builder<T extends AbsNavigationBar> {

        /**
         * 参数类
         */
        private NavigationParam P;

        /**
         * 获取Activity里的android.R.id.content作为父类布局
         */
        public Builder(@NonNull Context context) {
            this(context, null);
        }

        /**
         * @param viewGroup 是头部的父类布局
         */
        public Builder(@NonNull Context context, ViewGroup viewGroup) {
            P = new NavigationParam(context, viewGroup);
        }

        /**
         * 设置文本
         *
         * @param viewId 控件id
         * @param text   文本
         */
        public Builder setText(@IdRes int viewId, CharSequence text) {
            P.mViewHelper.setText(viewId, text);
            return this;
        }

        /**
         * 设置点击事件
         */
        public Builder setOnClickListener(@IdRes int viewId, View.OnClickListener onClickListener) {
            P.mViewHelper.setClickListener(viewId, onClickListener);
            return this;
        }

        public Builder build() {
            //创建NavigationBar
            AbsNavigationBar navigationBar = createNavigationBar(P);
            P.layoutRes = navigationBar.getLayoutRes();
            //1.获取布局文件
            View contentView = P.mViewHelper.setContentView(P.mContext, P.layoutRes);
            if (contentView == null) {
                //如果布局文件为空，说明头部布局没有实现
                throw new IllegalArgumentException("please make sure getLayoutRes has value");
            }
            //2.添加入父类布局
            if (P.mViewGroup == null && P.mContext instanceof Activity) {
                P.mViewGroup = ((Activity) P.mContext).findViewById(android.R.id.content);
            }
            if (P.mViewGroup == null) {
                throw new IllegalArgumentException("can not find viewGroup,please init viewGroup for navigationBar");
            }
            P.mViewGroup.addView(contentView, 0);
            return this;
        }

        protected abstract AbsNavigationBar createNavigationBar(NavigationParam param);


        /**
         * 参数类，将所有需要用的参数放在此类
         */
        public class NavigationParam {

            public final Context mContext;//上下文
            public ViewGroup mViewGroup;//父类布局
            public final ViewHelper mViewHelper;//辅助类
            public int layoutRes;//头部布局

            public NavigationParam(Context mContext, ViewGroup mViewGroup) {
                this.mContext = mContext;
                this.mViewGroup = mViewGroup;
                mViewHelper = new ViewHelper();
            }
        }

    }


}
