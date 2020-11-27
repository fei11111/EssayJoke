package com.fei.essayjoke;

import com.fei.framelibrary.base.BaseSkinActivity;
import com.fei.framelibrary.navigationBar.DefaultNavigatorBar;

/**
 * @ClassName: TestActivity
 * @Description: 测试热修复
 * @Author: Fei
 * @CreateDate: 2020/11/4 10:02
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/4 10:02
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TestActivity extends BaseSkinActivity {

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initTile() {
        DefaultNavigatorBar defaultNavigatorBar = new DefaultNavigatorBar.Builder(this)
                .setTitle("Test").build();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_test;
    }
}
