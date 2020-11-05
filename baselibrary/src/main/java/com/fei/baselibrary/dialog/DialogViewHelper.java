package com.fei.baselibrary.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;

/**
 * @ClassName: dialog辅助类
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2020/11/5 18:33
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/5 18:33
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DialogViewHelper {

    private View contentView;
    private SparseArray<WeakReference<View>> views;

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
     * 设置布局
     */
    public void setContentView(View contentView) {
        this.contentView = contentView;
        views = new SparseArray<>();
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
     * 设置点击事件
     */
    public void setClickListener(@IdRes int viewId, View.OnClickListener onClickListener) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(onClickListener);
        }
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

}
