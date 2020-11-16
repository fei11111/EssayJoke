package com.fei.framelibrary.skin;

/**
 * @ClassName: SkinAttr
 * @Description: 换肤属性
 * @Author: Fei
 * @CreateDate: 2020/11/16 15:31
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/16 15:31
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SkinAttr {

    //资源名字
    private String resName;
    //资源类型
    private SkinType skinType;


    public SkinAttr(String resName, com.fei.framelibrary.skin.SkinType skinType) {
        this.resName = resName;
        this.skinType = skinType;
    }

    public void skin() {
        skinType.skin(resName);
    }
}
