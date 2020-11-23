package com.fei.baselibrary.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.fei.baselibrary.ioc.Visibility;

import java.lang.ref.WeakReference;

/**
 * @ClassName: 辅助类，初始化布局控件和点击事件
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2020/11/5 18:33
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/5 18:33
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ViewHelper {

    private View contentView;
    private SparseArray<WeakReference<View>> views;
    private static final String TAG = "ViewHelper";

    /**
     * 通过contentViewId设置布局
     */
    public View setContentView(@NonNull Context context, @LayoutRes int layoutResId) {
        if (layoutResId != 0) {
            this.setContentView(LayoutInflater.from(context).inflate(layoutResId, null));
        }
        return contentView;
    }

    /**
     * 通过contentViewId设置布局
     */
    public View setContentView(@NonNull Context context, @LayoutRes int layoutResId, ViewGroup viewGroup) {
        if (layoutResId != 0) {
            this.setContentView(LayoutInflater.from(context).inflate(layoutResId, viewGroup, false));
        }
        return contentView;
    }

    /**
     * 设置布局
     */
    public void setContentView(View contentView) {
        this.contentView = contentView;
        views = new SparseArray<>();
    }

    /**
     * 通过缓存获取View
     */
    public <T extends View> T getView(@IdRes int viewId) {
        WeakReference<View> viewWeakReference = views.get(viewId);
        View view = null;
        if (viewWeakReference != null) {
            view = viewWeakReference.get();
        }
        if (view == null) {
            view = contentView.findViewById(viewId);
            views.put(viewId, new WeakReference<View>(view));
        }
        return (T) view;
    }

    /**
     * 设置文本
     */
    public void setText(@IdRes int viewId, CharSequence text) {
        TextView textView = getView(viewId);
        if (textView != null) {
            textView.setText(text);
        }
    }

    /**
     * 设置控件是否可见
     */
    public void setVisibility(@IdRes int viewId, @Visibility int visibility) {
        View view = getView(viewId);
        if (view != null) {
            view.setVisibility(visibility);
        }
    }

    /**
     * 设置点击事件
     */
    public void setClickListener(@IdRes int viewId, View.OnClickListener onClickListener) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(onClickListener);
        }
    }

    /**
     * 设置图片
     */
    public void setIcon(@IdRes int viewId, @DrawableRes int drawableId) {
        ImageView imageView = getView(viewId);
        if (imageView != null) {
            imageView.setImageResource(drawableId);
        }
    }

    public void setColor(@IdRes int viewId, @ColorRes int color) {
        View view = getView(viewId);
        if (view != null) {
            ColorStateList colorStateList = view.getContext().getResources().getColorStateList(color);
            view.setBackgroundColor(colorStateList.getDefaultColor());
        }
    }
}
