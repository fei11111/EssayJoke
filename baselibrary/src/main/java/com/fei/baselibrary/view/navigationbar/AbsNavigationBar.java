package com.fei.baselibrary.view.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;

import com.fei.baselibrary.ioc.Visibility;
import com.fei.baselibrary.utils.StatusBarUtil;
import com.fei.baselibrary.view.ViewHelper;

/**
 * @ClassName: AbsNavigationBar 抽象类 链式+builder
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
    private static final String TAG = "AbsNavigationBar";
    protected Builder.NavigationParam P;

    public AbsNavigationBar(@NonNull Builder.NavigationParam p) {
        P = p;
    }

    /**
     * 对象可以直接赋值操作，通过viewId获取控件
     */
    public <T extends View> T getView(@IdRes int viewId) {
        return P.mViewHelper.getView(viewId);
    }

    /**
     * 对象可以直接赋值操作，设置点击事件
     */
    protected void setOnClickListener(@IdRes int viewId, View.OnClickListener onClickListener) {
        P.mViewHelper.setClickListener(viewId, onClickListener);
    }

    /**
     * 对象可以直接赋值操作，设置文本
     */
    protected void setText(@IdRes int viewId, CharSequence text) {
        P.mViewHelper.setText(viewId, text);
    }

    /**
     * 对象可以直接赋值操作，设置控件是否可见
     */
    protected void setVisibility(@IdRes int viewId, @Visibility int visibility) {
        P.mViewHelper.setVisibility(viewId, visibility);
    }

    /**
     * 设置图片
     */
    protected void setIcon(@IdRes int viewId, @DrawableRes int drawableId) {
        P.mViewHelper.setIcon(viewId, drawableId);
    }


    /**
     * 构建类
     */
    public abstract static class Builder<T extends AbsNavigationBar, P extends Builder.NavigationParam> {

        /**
         * 参数类
         */
        protected P P;
        /**
         * 所有文本
         */
        private SparseArray<CharSequence> textArray = new SparseArray<>();
        /**
         * 所有事件
         */
        private SparseArray<View.OnClickListener> clickArray = new SparseArray<>();
        /**
         * 所有图片
         */
        private SparseArray<Integer> iconArray = new SparseArray<>();
        /**
         * 所有可见
         */
        private SparseArray<Integer> visibleArray = new SparseArray<>();


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
            P = createParam(context, viewGroup);
        }

        protected abstract P createParam(Context context, ViewGroup viewGroup);


        /**
         * 设置文本
         *
         * @param viewId 控件id
         * @param text   文本
         */
        protected Builder setText(@IdRes int viewId, CharSequence text) {
            textArray.put(viewId, text);
            return this;
        }

        /**
         * 设置控件是否可见
         */
        protected Builder setVisibility(@IdRes int viewId, @Visibility int visibility) {
            visibleArray.put(viewId, visibility);
            return this;
        }

        /**
         * 设置点击事件
         */
        protected Builder setOnClickListener(@IdRes int viewId, View.OnClickListener onClickListener) {
            clickArray.put(viewId, onClickListener);
            return this;
        }

        /**
         * 设置图片
         */
        protected Builder setIcon(@IdRes int viewId, @DrawableRes int drawableId) {
            iconArray.put(viewId, drawableId);
            return this;
        }

        public T build() {
            //创建NavigationBar
            T navigationBar = createNavigationBar(P);
            P.layoutRes = navigationBar.getLayoutRes();
            //1.获取布局文件
            View contentView = P.mViewHelper.setContentView(P.mContext, P.layoutRes, P.mViewGroup);
            if (contentView == null) {
                //如果布局文件为空，说明头部布局没有实现
                throw new IllegalArgumentException("please make sure getLayoutRes has value");
            }
            //1.1判断父类是否为空
            if (P.mViewGroup == null && P.mContext instanceof Activity) {
                ViewGroup activityRoot = (ViewGroup) ((Activity) (P.mContext))
                        .getWindow().getDecorView();
                P.mViewGroup = (ViewGroup) activityRoot.getChildAt(0);
                Drawable background = contentView.getBackground();
                if (background instanceof ColorDrawable) {
                    //沉浸式
                    int color = ((ColorDrawable) background).getColor();
                    StatusBarUtil.statusBarTintColor((Activity) P.mContext, color);
                }
            }
            if (P.mViewGroup == null) {
                throw new IllegalArgumentException("can not find viewGroup,please init viewGroup for navigationBar");
            }
            //2.添加入父类布局
            P.mViewGroup.addView(contentView, 0);
            //3.自定义默认参数
            setDefaultParam();
            //4.遍历参数
            //4.1遍历文本
            for (int i = 0; i < textArray.size(); i++) {
                P.mViewHelper.setText(textArray.keyAt(i), textArray.valueAt(i));
            }
            //4.2遍历点击事件
            for (int i = 0; i < clickArray.size(); i++) {
                P.mViewHelper.setClickListener(clickArray.keyAt(i), clickArray.valueAt(i));
            }
            //4.3遍历可见
            for (int i = 0; i < iconArray.size(); i++) {
                P.mViewHelper.setIcon(iconArray.keyAt(i), iconArray.valueAt(i));
            }
            //4.4遍历图片
            for (int i = 0; i < visibleArray.size(); i++) {
                P.mViewHelper.setVisibility(visibleArray.keyAt(i), visibleArray.valueAt(i));
            }

            return navigationBar;
        }

        protected abstract void setDefaultParam();

        /**
         * 创建自己的navigationBar和NavigationBarParam，一定要用泛型，这样可以增加自定义的参数和方法
         */
        protected abstract T createNavigationBar(P param);


        /**
         * 参数类，将所有需要用的参数放在此类
         */
        public static class NavigationParam {

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
