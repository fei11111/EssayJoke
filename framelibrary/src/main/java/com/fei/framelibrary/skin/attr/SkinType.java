package com.fei.framelibrary.skin.attr;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @ClassName: SkinType
 * @Description: 皮肤类型
 * @Author: Fei
 * @CreateDate: 2020/11/16 17:15
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/16 17:15
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public enum SkinType {

    //属性名-背景
    BACKGROUND("background") {
        @Override
        public void skin(Resources resources, View view, String resName, String resType, String packageName) {
            //获取资源id
            int identifier = getId(resources, resName, resType, packageName);
            if (identifier > 0) {
                if (resType.equalsIgnoreCase("color")) {
                    ColorStateList colorStateList = resources.getColorStateList(identifier);
                    view.setBackgroundColor(colorStateList.getDefaultColor());
                } else if (resType.equalsIgnoreCase("drawable")) {
                    Drawable drawable = resources.getDrawable(identifier);
                    view.setBackgroundDrawable(drawable);
                }

            }
        }
    },
    //属性名-文本颜色
    TEXT_COLOR("textColor") {
        @Override
        public void skin(Resources resources, View view, String resName, String resType, String packageName) {
            int identifier = getId(resources, resName, resType, packageName);
            if (identifier > 0) {
                if (view instanceof TextView) {
                    ColorStateList colorStateList = resources.getColorStateList(identifier);
                    ((TextView) view).setTextColor(colorStateList);
                }

            }
        }
    },
    //属性名-图片
    SRC("src") {
        @Override
        public void skin(Resources resources, View view, String resName, String resType, String packageName) {
            int identifier = getId(resources, resName, resType, packageName);
            if (identifier > 0) {
                if (view instanceof ImageView) {
                    Drawable drawable = resources.getDrawable(identifier);
                    ((ImageView) view).setImageDrawable(drawable);
                    //不能使用这个，因为必须使用皮肤的资源类resources
                    //setImageResource(identifier);用这个是会本来app的resources去查找，会找不到，所映射的表的id会不同
//                    ((ImageView) view).setImageResource(identifier);
                }
            }

        }
    };

    private static final String TAG = "SkinType";

    int getId(Resources resources, String resName, String resType, String packageName) {
        return resources.getIdentifier(resName, resType, packageName);
    }

    private String resName;

    SkinType(String resName) {
        this.resName = resName;
    }

    public String getResName() {
        return resName;
    }

    public abstract void skin(Resources resources, View view, String resName, String resType, String packageName);
}
