package com.fei.framelibrary.skin;

import android.view.View;

import java.util.List;

/**
 * @ClassName: SkinView
 * @Description: 换肤View
 * @Author: Fei
 * @CreateDate: 2020/11/16 15:31
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/16 15:31
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SkinView {

    private View view;
    //view里有多个属性
    private List<SkinAttr> attrs;

    public SkinView(View view, List<SkinAttr> attrs) {
        this.view = view;
        this.attrs = attrs;
    }

    public void skin(SkinType skinType, String packageName) {
        for (SkinAttr attr : attrs) {
            attr.skin(view, skinType, packageName);
        }
    }
}
