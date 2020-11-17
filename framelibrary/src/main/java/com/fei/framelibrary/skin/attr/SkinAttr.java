package com.fei.framelibrary.skin.attr;

import android.content.res.Resources;
import android.view.View;

/**
 * @ClassName: SkinAttr
 * @Description: 换肤属性
 * android:background = "@drawable/main_bg"
 * background为属性名
 * drawable为资源类型，也可以是color
 * main_bg 为资源名
 * @Author: Fei
 * @CreateDate: 2020/11/16 15:31
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/16 15:31
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SkinAttr implements Cloneable {

    //属性名
    private String attrName;
    //资源名字
    private String resName;
    //资源类型
    private String resType;

    public SkinAttr(String attrName, String resName, String resType) {
        this.attrName = attrName;
        this.resName = resName;
        this.resType = resType;
    }

    /**
     *
     */
    public void skin(Resources resources, View view, String packageName) {
        SkinType skinType = null;
        if (attrName.equalsIgnoreCase(SkinType.BACKGROUND.getResName().toLowerCase())) {
            skinType = SkinType.BACKGROUND;
        } else if (attrName.equalsIgnoreCase(SkinType.SRC.getResName().toLowerCase())) {
            skinType = SkinType.SRC;
        } else if (attrName.equalsIgnoreCase(SkinType.TEXT_COLOR.getResName().toLowerCase())) {
            skinType = SkinType.TEXT_COLOR;
        }
        if (skinType != null) {
            skinType.skin(resources,view, resName, resType, packageName);
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
