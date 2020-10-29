package com.fei.baselibrary.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: CheckNet
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2020/10/29 19:55
 * @UpdateUser: Fei
 * @UpdateDate: 2020/10/29 19:55
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckNet {
    String value();
}
