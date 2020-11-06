package com.fei.baselibrary.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.fei.baselibrary.view.ViewHelper;

/**
 * @ClassName: dialog控制器，主要进行操作
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2020/11/5 18:32
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/5 18:32
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CustomController {

    private final Dialog dialog;

    private final Window window;

    private final ViewHelper viewHelper;

    CustomController(@NonNull Dialog dialog, @NonNull Window window) {
        this.dialog = dialog;
        this.window = window;
        viewHelper = new ViewHelper();
    }

    public ViewHelper getViewHelper() {
        return viewHelper;
    }

    /**
     * 获取所有参数，apply进行组装
     */
   public static class CustomParam {

        public int mThemeResId;
        public Context mContext;
        public boolean mCancelable = true;
        public View mContentView;
        public int mContentViewId;
        public SparseArray<CharSequence> mTextArray = new SparseArray<>();
        public SparseArray<View.OnClickListener> mClickArray = new SparseArray<>();
        public DialogInterface.OnCancelListener mOnCancelListener;
        public DialogInterface.OnDismissListener mOnDismissListener;
        public DialogInterface.OnKeyListener mOnKeyListener;
        public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        public int mGravity = Gravity.CENTER;
        public int mAnimation = 0;

        public CustomParam(@NonNull Context context, @StyleRes int themeResId) {
            this.mContext = context;
            this.mThemeResId = themeResId;
        }

        public void apply(CustomController controller) {

            //初始化布局
            if (mContentView != null) {
                controller.viewHelper.setContentView(mContentView);
            } else {
                mContentView = controller.viewHelper.setContentView(mContext, mContentViewId);
            }

            if (mContentView == null) {
                //没有设置布局，抛出异常
                throw new IllegalArgumentException("please set contentView ");
            }
            //dialog设置布局
            controller.dialog.setContentView(mContentView);
            //设置文本
            for (int i = 0; i < mTextArray.size(); i++) {
                controller.viewHelper.setText(mTextArray.keyAt(i), mTextArray.valueAt(i));
            }

            //设置点击事件
            for (int i = 0; i < mClickArray.size(); i++) {
                controller.viewHelper.setClickListener(mClickArray.keyAt(i), mClickArray.valueAt(i));
            }

            //设置是否返回键可用
            controller.dialog.setCancelable(mCancelable);
            if (mCancelable) {
                //设置点击屏幕外是否消失
                controller.dialog.setCanceledOnTouchOutside(true);
            }
            controller.dialog.setOnCancelListener(mOnCancelListener);
            controller.dialog.setOnDismissListener(mOnDismissListener);
            if (mOnKeyListener != null) {
                controller.dialog.setOnKeyListener(mOnKeyListener);
            }

            if (mAnimation != 0) {
                controller.window.setWindowAnimations(mAnimation);
            }
            WindowManager.LayoutParams attributes = controller.window.getAttributes();
            attributes.width = mWidth;
            attributes.height = mHeight;
            attributes.gravity = mGravity;
            controller.window.setAttributes(attributes);
        }

    }

}
