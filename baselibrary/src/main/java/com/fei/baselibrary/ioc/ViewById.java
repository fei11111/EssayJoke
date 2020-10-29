package com.fei.baselibrary.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.text.Format;

/**
 * @ClassName: ViewById
 * @Description: View注解
 * @Author: Fei
 * @CreateDate: 2020/10/29 15:45
 * @UpdateUser: Fei
 * @UpdateDate: 2020/10/29 15:45
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Target(ElementType.FIELD)//表示Annotation的位置，FIELD表示属性，METHOD表示方法,TYPE表示类
@Retention(RetentionPolicy.RUNTIME)//什么时候生效,RUNTIME表示运行时，SOURCE表示源码资源,CLASS表示编译时
public @interface ViewById {
    int value();
}
