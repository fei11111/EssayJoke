package com.fei.baselibrary.view.navigationbar;

import androidx.annotation.LayoutRes;

/**
 * @ClassName: INavigationBar
 * @Description: titleBar接口，实现布局文件
 * @Author: Fei
 * @CreateDate: 2020/11/6 15:58
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/6 15:58
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface INavigationBar {

    /**
     * 获取布局文件，将布局放在这里，继承时就知道需要先获取布局文件
     */
    @LayoutRes
    int getLayoutRes();


}
