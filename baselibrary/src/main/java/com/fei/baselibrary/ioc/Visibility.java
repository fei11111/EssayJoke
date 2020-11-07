package com.fei.baselibrary.ioc;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * @ClassName: Visibility
 * @Description: View 可见参数注解
 * @Author: Fei
 * @CreateDate: 2020-11-06 21:14
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-11-06 21:14
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@IntDef({VISIBLE, INVISIBLE, GONE})
@Retention(RetentionPolicy.SOURCE)
public @interface Visibility {
}
