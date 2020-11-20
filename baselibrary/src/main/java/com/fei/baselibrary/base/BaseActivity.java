package com.fei.baselibrary.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fei.baselibrary.ioc.ViewUtils;
import com.fei.baselibrary.permission.PermissionFail;
import com.fei.baselibrary.permission.PermissionSuccess;
import com.fei.baselibrary.permission.PermissionUtil;
import com.fei.baselibrary.utils.LogUtils;

public abstract class BaseActivity extends AppCompatActivity {

    private PermissionUtil permissionUtil;

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
     * 权限申请
     */
    public void callPermissions(String[] permission, int requestCode) {
        permissionUtil = PermissionUtil.with(this).code(requestCode).permissions(permission);
        permissionUtil.request();
    }

    @PermissionSuccess
    public abstract void onPermissionSuccess(int requestCode);

    @PermissionFail
    public abstract void onPermissionFail(int requestCode);


    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化界面
     */
    protected abstract void initView();

    /**
     * 初始化标题
     */
    protected abstract void initTile();

    /**
     * 获取布局文件
     */
    @LayoutRes
    protected abstract int getLayoutRes();

    /**
     * 启动Activity
     */
    protected void startActivity(Class<?> clazz) {
        startActivity(new Intent(this, clazz));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionUtil.onResult(requestCode,permissions,grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
