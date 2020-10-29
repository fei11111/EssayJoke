package com.fei.baselibrary.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: OnClick
 * @Description: 方法注解
 * @Author: Fei
 * @CreateDate: 2020/10/29 17:33
 * @UpdateUser: Fei
 * @UpdateDate: 2020/10/29 17:33
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnClick {
    int[] value();
}
