package com.fei.baselibrary.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: PermissionUtil
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2020/11/20 9:28
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/20 9:28
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PermissionUtil {

    private Activity context;
    private String[] permissions;
    private int code;

    private PermissionUtil(Activity mContext) {
        this.context = mContext;
    }

    /**
     * 初始化
     */
    public static PermissionUtil with(Activity mContext) {
        return new PermissionUtil(mContext);
    }

    /**
     * 需要请求的权限
     */
    public PermissionUtil permission(String permission) {
        this.permissions = new String[]{permission};
        return this;
    }

    /**
     * 需要请求的权限
     */
    public PermissionUtil permissions(String[] permissions) {
        this.permissions = permissions;
        return this;
    }

    /**
     * 请求码
     */
    public PermissionUtil code(int requestCode) {
        this.code = requestCode;
        return this;
    }

    /**
     * 请求
     */
    public void request() {

        //判断是否大于23版本

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            //1.检测值是否都赋了
            if (context == null) {
                throw new IllegalArgumentException("context 没有赋值");
            }

            if (permissions == null) {
                throw new IllegalArgumentException("permissions 没有赋值");
            }

            //2.检测权限是否赋予了
            List<String> list = new ArrayList<>();
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(context, permission)
                        != PackageManager.PERMISSION_GRANTED) {
                    //添加没有授权
                    //如果用户之前拒绝授权，返回true
                    if (ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
                        PermissionHelper.success(context, code);
                        return;
                    } else {
                        list.add(permission);
                    }
                }
            }

            //3.请求没有赋予的权限
            if (list.size() > 0) {
                context.requestPermissions(list.toArray(new String[list.size()]), code);
            } else {
                //4.已经授权
                PermissionHelper.success(context, code);
            }
        } else {
            //4.已经授权
            PermissionHelper.success(context, code);
        }
    }

    public void onResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == code) {
            boolean isFail = false;
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    isFail = true;
                    break;
                }
            }
            if (isFail) {
                PermissionHelper.fail(context, code);
            } else {
                PermissionHelper.success(context, code);
            }
        }
    }

}
