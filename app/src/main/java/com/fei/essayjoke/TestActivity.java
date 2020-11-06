package com.fei.essayjoke;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fei.baselibrary.ioc.ViewById;
import com.fei.framelibrary.base.BaseSkinActivity;

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

    @ViewById(R.id.tv_text)
    private TextView tvText;
    @ViewById(R.id.iv_text)
    private ImageView ivText;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ivText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestActivity.this,  1/0+"测试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void initTile() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_test;
    }
}
