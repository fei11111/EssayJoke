package com.fei.baselibrary.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: PermissionSuccess
 * @Description: 成功回调
 * @Author: Fei
 * @CreateDate: 2020/11/20 9:51
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/20 9:51
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PermissionSuccess {
}
