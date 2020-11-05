package com.fei.baselibrary.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;

import com.fei.baselibrary.R;

/**
 * @ClassName: 自定义dialog
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2020/11/5 18:30
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/5 18:30
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CustomDialog extends Dialog {

    public CustomController controller;

    CustomDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        controller = new CustomController(this, getWindow());
    }

    public View getView(@IdRes int viewId) {
        return controller.getViewHelper().getView(viewId);
    }

    public void setOnClickListener(@IdRes int viewId, View.OnClickListener onClickListener) {
        controller.getViewHelper().setClickListener(viewId, onClickListener);
    }

    /**
     * Set a listener to be invoked when the dialog is canceled.
     *
     * <p>This will only be invoked when the dialog is canceled.
     * Cancel events alone will not capture all ways that
     * the dialog might be dismissed. If the creator needs
     * to know when a dialog is dismissed in general, use
     * {@link #setOnDismissListener}.</p>
     *
     * @param listener The {@link DialogInterface.OnCancelListener} to use.
     */
    public void setOnCancelListener(@Nullable OnCancelListener listener) {
    }

    /**
     * Set a listener to be invoked when the dialog is dismissed.
     *
     * @param listener The {@link DialogInterface.OnDismissListener} to use.
     */
    public void setOnDismissListener(@Nullable OnDismissListener listener) {

    }


    public static class Builder {

        private final CustomController.CustomParam P;

        /**
         * Creates a builder for an alert dialog that uses the default alert
         * dialog theme.
         * <p>
         * The default alert dialog theme is defined by
         * {@link android.R.attr#alertDialogTheme} within the parent
         * {@code context}'s theme.
         *
         * @param context the parent context
         */
        public Builder(@NonNull Context context) {
            this(context, R.style.dialog);
        }

        /*
         * 设置布局
         * */

        public Builder setContentView(@LayoutRes int layoutResId) {
            P.mContentViewId = layoutResId;
            return this;
        }

        /**
         * 设置布局
         */
        public Builder setContentView(@NonNull View contentView) {
            P.mContentView = contentView;
            return this;
        }

        /**
         * Creates a builder for an alert dialog that uses an explicit theme
         * resource.
         * <p>
         * The specified theme resource ({@code themeResId}) is applied on top
         * of the parent {@code context}'s theme. It may be specified as a
         * style resource containing a fully-populated theme, such as
         * {@link android.R.style#Theme_Material_Dialog}, to replace all
         * attributes in the parent {@code context}'s theme including primary
         * and accent colors.
         * <p>
         * To preserve attributes such as primary and accent colors, the
         * {@code themeResId} may instead be specified as an overlay theme such
         * as {@link android.R.style#ThemeOverlay_Material_Dialog}. This will
         * override only the window attributes necessary to style the alert
         * window as a dialog.
         * <p>
         * Alternatively, the {@code themeResId} may be specified as {@code 0}
         * to use the parent {@code context}'s resolved value for
         * {@link android.R.attr#alertDialogTheme}.
         *
         * @param context    the parent context
         * @param themeResId the resource ID of the theme against which to inflate
         *                   this dialog, or {@code 0} to use the parent
         *                   {@code context}'s default alert dialog theme
         */
        public Builder(@NonNull Context context, @StyleRes int themeResId) {
            P = new CustomController.CustomParam(
                    context, themeResId);
        }

        /**
         * 设置文本
         */
        public CustomDialog.Builder setText(@IdRes int viewId, CharSequence text) {
            P.mTextArray.put(viewId, text);
            return this;
        }

        /**
         * 设置点击事件
         */
        public CustomDialog.Builder setOnClickListener(@IdRes int viewId, View.OnClickListener onClickListener) {
            P.mClickArray.put(viewId, onClickListener);
            return this;
        }

        /**
         * Sets whether the dialog is cancelable or not.  Default is true.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public CustomDialog.Builder setCancelable(boolean cancelable) {
            P.mCancelable = cancelable;
            return this;
        }

        /**
         * Set a custom view resource to be the contents of the Dialog. The
         * resource will be inflated, adding all top-level views to the screen.
         *
         * @param layoutResId Resource ID to be inflated.
         * @return this Builder object to allow for chaining of calls to set
         * methods
         */
        public CustomDialog.Builder setView(@LayoutRes int layoutResId) {
            P.mContentView = null;
            P.mContentViewId = layoutResId;
            return this;
        }

        /**
         * @param view the view to use as the contents of the custom dialog
         * @return this Builder object to allow for chaining of calls to set
         * methods
         */
        public CustomDialog.Builder setView(View view) {
            P.mContentView = view;
            P.mContentViewId = 0;
            return this;
        }

        /**
         * 设置宽度全屏
         */
        public CustomDialog.Builder fullWidth() {
            P.mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        /**
         * 设置dialog宽和高
         */
        public CustomDialog.Builder setWidthAndHeight(int width, int height) {
            P.mWidth = width;
            P.mHeight = height;
            return this;
        }

        /**
         * 从底部弹出
         */
        public CustomDialog.Builder fromBottom() {
            P.mAnimation = R.style.dialog_anim_from_bottom;
            P.mGravity = Gravity.BOTTOM;
            return this;
        }

        /**
         * Creates an {@link CustomDialog} with the arguments supplied to this
         * builder.
         * <p>
         * Calling this method does not display the dialog. If no additional
         * processing is needed, {@link #show()} may be called instead to both
         * create and display the dialog.
         */
        public CustomDialog create() {
            // Context has already been wrapped with the appropriate theme.
            final CustomDialog dialog = new CustomDialog(P.mContext, P.mThemeResId);
            //将所有参数进行组装
            P.apply(dialog.controller);
            return dialog;
        }

        /**
         * Creates an {@link AlertDialog} with the arguments supplied to this
         * builder and immediately displays the dialog.
         * <p>
         * Calling this method is functionally identical to:
         * <pre>
         *     AlertDialog dialog = builder.create();
         *     dialog.show();
         * </pre>
         */
        public CustomDialog show() {
            final CustomDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }

}
