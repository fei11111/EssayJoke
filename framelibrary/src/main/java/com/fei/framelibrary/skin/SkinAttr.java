package com.fei.framelibrary.skin;

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


    public SkinAttr(String resName, String resType) {
        this.resName = resName;
        this.resType = resType;
    }

    /**
     *
     */
    public void skin(View view, SkinType skinType, String packageName) {
        skinType.skin(view, resName, resType, packageName);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
