package com.fei.framelibrary.skin;

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

    //背景
    BACKGROUND("background") {
        @Override
        public void skin(String resName) {

        }
    },
    //文本颜色
    TEXT_COLOR("textColor") {
        @Override
        public void skin(String resName) {

        }
    },
    //图片
    SRC("src") {
        @Override
        public void skin(String resName) {

        }
    };

    private String resName;

    SkinType(String resName) {
        this.resName = resName;
    }

    public abstract void skin(String resName);
}
