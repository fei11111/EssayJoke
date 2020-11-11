package com.fei.framelibrary.db;

import android.content.ContentValues;
import android.util.ArrayMap;

import com.fei.baselibrary.utils.SPUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @ClassName: DaoUtil
 * @Description: dao 工具类
 * @Author: Fei
 * @CreateDate: 2020/11/11 16:19
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/11 16:19
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DaoUtil {


    /**
     * 转成数据库基本类型
     */
    public static String covertTypeToDbType(String name) {
        switch (name.toLowerCase()) {
            case "int":
            case "boolean":
                return "integer";
            case "float":
            case "double":
                return "real";
            case "string":
                return "text";
            default:
                return null;
        }
    }

    public static <T> void insert(T data, ContentValues values) {

    }
}
