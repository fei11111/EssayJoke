package com.fei.baselibrary.base;

import android.content.Intent;
import android.os.Bundle;

import com.fei.baselibrary.ioc.ViewUtils;
import com.fei.baselibrary.utils.SPUtils;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        ViewUtils.inject(this);
        initTile();
        initView();
        initData();
    }

    /**
     * 初始化数据
     * */
    protected abstract void initData();

    /**
     * 初始化界面
     * */
    protected abstract void initView();

    /**
     * 初始化标题
     * */
    protected abstract void initTile();

    /**
     * 获取布局文件
     * */
    @LayoutRes
    protected abstract int getLayoutRes();

    /**
     * 启动Activity
     * */
    protected void startActivity(Class<?> clazz) {
        startActivity(new Intent(this,clazz));
    }
}
