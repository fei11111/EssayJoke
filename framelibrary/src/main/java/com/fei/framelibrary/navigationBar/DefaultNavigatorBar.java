package com.fei.framelibrary.navigationBar;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.fei.baselibrary.view.navigationbar.AbsNavigationBar;
import com.fei.framelibrary.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * @ClassName: DefaultNavigatorBar
 * @Description: 默认NavigationBar 包含左边，中间，右边，中间editText等
 * @Author: Fei
 * @CreateDate: 2020/11/6 17:21
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/6 17:21
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DefaultNavigatorBar extends AbsNavigationBar {

    public DefaultNavigatorBar(@NonNull AbsNavigationBar.Builder.NavigationParam p) {
        super(p);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.app_head_view;
    }

    /**
     * 设置背景颜色
     * */
    public void setBackgroundColor(@ColorRes int color) {
        setBackgroundColor(color);
    }

    /**
     * 设置标题
     */
    public void setTitle(@NonNull CharSequence text) {
        setVisibility(R.id.tv_title, VISIBLE);
        setVisibility(R.id.rl_search, GONE);
        setText(R.id.tv_title, text);
    }

    /**
     * 设置搜索框
     */
    public void setSearchVisible() {
        setVisibility(R.id.tv_title, GONE);
        setVisibility(R.id.rl_search, VISIBLE);
    }

    /**
     * 设置左侧点击事件
     */
    public void setOnLeftListener(View.OnClickListener onLeftListener) {
        setOnClickListener(R.id.fl_head_left, onLeftListener);
    }

    /**
     * 设置左边图标
     */
    public void setLeftIcon(@DrawableRes int drawableId) {
        setVisibility(R.id.tv_head_left, GONE);
        setVisibility(R.id.iv_head_left, VISIBLE);
        setIcon(R.id.iv_head_left, drawableId);
    }

    /**
     * 设置左边文本
     */
    public void setLeftText(@NonNull CharSequence text) {
        setVisibility(R.id.tv_head_left, VISIBLE);
        setVisibility(R.id.iv_head_left, GONE);
        setText(R.id.tv_head_left, text);
    }

    /**
     * 设置右边文本
     */
    public void setRightText(@NonNull CharSequence text) {
        setVisibility(R.id.tv_head_right, VISIBLE);
        setVisibility(R.id.iv_head_right_one, GONE);
        setVisibility(R.id.iv_head_right_two, GONE);
        setText(R.id.tv_head_right, text);
    }

    /**
     * 设置右侧Icon
     */
    public void setIconFirst(@DrawableRes int drawableId) {
        setVisibility(R.id.tv_head_right, GONE);
        setVisibility(R.id.iv_head_right_one, VISIBLE);
        setVisibility(R.id.iv_head_right_two, GONE);
        setIcon(R.id.iv_head_right_one, drawableId);
    }

    /**
     * 隐藏左边
     * */
    public void hideLeft(){
        setIcon(R.id.fl_head_left,GONE);
        setOnLeftListener(null);
    }


    /**
     * 设置右侧Icon
     */
    public void setIconSecond(@DrawableRes int drawableId) {
        setVisibility(R.id.tv_head_right, GONE);
        setVisibility(R.id.iv_head_right_one, GONE);
        setVisibility(R.id.iv_head_right_two, VISIBLE);
        setIcon(R.id.iv_head_right_two, drawableId);
    }

    /**
     * 设置右侧文本点击事件
     */
    public void setOnRightTextListener(View.OnClickListener onRightListener) {
        setOnClickListener(R.id.tv_head_right, onRightListener);
    }

    /**
     * 设置右侧图标点击事件
     */
    public void setOnIconFirstListener(View.OnClickListener onRightListener) {
        setOnClickListener(R.id.iv_head_right_one, onRightListener);
    }

    /**
     * 设置右侧图标点击事件
     */
    public void setOnIconSecondListener(View.OnClickListener onRightListener) {
        setOnClickListener(R.id.iv_head_right_two, onRightListener);
    }

    /**
     * 获取搜索内容
     */
    public String getSearchContent() {
        EditText editText = getView(R.id.et_search);
        if (editText != null) {
            return editText.getText().toString();
        }
        return null;
    }


    public static class Builder extends AbsNavigationBar.Builder<DefaultNavigatorBar,
            Builder.DefaultNavigatorBarParam> {

        public Builder(@NonNull Context context) {
            super(context);
        }

        public Builder(@NonNull Context context, ViewGroup viewGroup) {
            super(context, viewGroup);
        }

        @Override
        protected DefaultNavigatorBarParam createParam(Context context, ViewGroup viewGroup) {
            return new DefaultNavigatorBarParam(context, viewGroup);
        }


        /**
         * 设置默认配置，设置左边返回图标和关闭Activity事件
         */
        @Override
        protected void setDefaultParam() {
            setIcon(R.id.iv_head_left, R.drawable.selector_head_left_arrow);
            setVisibility(R.id.iv_head_left,VISIBLE);
            setOnLeftListener(P.defaultLeftClickListener);
        }

        /**
         * 设置标题
         */
        public Builder setTitle(@NonNull CharSequence text) {
            setVisibility(R.id.tv_title, VISIBLE);
            setVisibility(R.id.rl_search, GONE);
            setText(R.id.tv_title, text);
            return this;
        }

        /**
         * 设置搜索框
         */
        public Builder setSearchVisiable() {
            setVisibility(R.id.tv_title, GONE);
            setVisibility(R.id.rl_search, VISIBLE);
            return this;
        }

        /**
         * 设置左侧点击事件
         */
        public Builder setOnLeftListener(View.OnClickListener onLeftListener) {
            setOnClickListener(R.id.fl_head_left, onLeftListener);
            return this;
        }

        /**
         * 设置左边图标
         */
        public Builder setLeftIcon(@DrawableRes int drawableId) {
            setVisibility(R.id.tv_head_left, GONE);
            setVisibility(R.id.iv_head_left, VISIBLE);
            setIcon(R.id.iv_head_left, drawableId);
            return this;
        }

        /**
         * 隐藏左边
         * */
        public Builder hideLeft(){
            setVisibility(R.id.fl_head_left,GONE);
            setOnLeftListener(null);
            return this;
        }

        /**
         * 设置左边文本
         */
        public Builder setLeftText(@NonNull CharSequence text) {
            setVisibility(R.id.tv_head_left, VISIBLE);
            setVisibility(R.id.iv_head_left, GONE);
            setText(R.id.tv_head_left, text);
            return this;
        }

        /**
         * 设置右边文本
         */
        public Builder setRightText(@NonNull CharSequence text) {
            setVisibility(R.id.tv_head_right, VISIBLE);
            setVisibility(R.id.iv_head_right_one, GONE);
            setVisibility(R.id.iv_head_right_two, GONE);
            setText(R.id.tv_head_right, text);
            return this;
        }

        /**
         * 设置右侧Icon
         */
        public Builder setIconFirst(@DrawableRes int drawableId) {
            setVisibility(R.id.tv_head_right, GONE);
            setVisibility(R.id.iv_head_right_one, VISIBLE);
            setVisibility(R.id.iv_head_right_two, GONE);
            setIcon(R.id.iv_head_right_one, drawableId);
            return this;
        }

        /**
         * 设置右侧Icon
         */
        public Builder setIconSecond(@DrawableRes int drawableId) {
            setVisibility(R.id.tv_head_right, GONE);
            setVisibility(R.id.iv_head_right_one, GONE);
            setVisibility(R.id.iv_head_right_two, VISIBLE);
            setIcon(R.id.iv_head_right_two, drawableId);
            return this;
        }

        /**
         * 设置背景颜色
         * */
        public Builder setBackgroundColor(@ColorRes int color) {
            P.backgroundColor = color;
            return this;
        }

        /**
         * 设置右侧文本点击事件
         */
        public Builder setOnRightTextListener(View.OnClickListener onRightListener) {
            setOnClickListener(R.id.tv_head_right, onRightListener);
            return this;
        }

        /**
         * 设置右侧图标点击事件
         */
        public Builder setOnIconFirstListener(View.OnClickListener onRightListener) {
            setOnClickListener(R.id.iv_head_right_one, onRightListener);
            return this;
        }

        /**
         * 设置右侧图标点击事件
         */
        public Builder setOnIconSecondListener(View.OnClickListener onRightListener) {
            setOnClickListener(R.id.iv_head_right_two, onRightListener);
            return this;
        }


        @Override
        protected DefaultNavigatorBar createNavigationBar(DefaultNavigatorBarParam param) {
            return new DefaultNavigatorBar(param);
        }

        public static class DefaultNavigatorBarParam extends AbsNavigationBar.Builder.NavigationParam {

            public View.OnClickListener defaultLeftClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) mContext).finish();
                }
            };

            public DefaultNavigatorBarParam(Context mContext, ViewGroup mViewGroup) {
                super(mContext, mViewGroup);
            }
        }

    }


}
