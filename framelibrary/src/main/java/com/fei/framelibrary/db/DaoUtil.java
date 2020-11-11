package com.fei.framelibrary.db;

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
class DaoUtil {


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

    /**
     * 将boolean类型转成int
     */
    private static int convertBooleanToInt(Boolean b) {
        if (b) return 1;
        else return 0;
    }

    /**
     * 获取数据，如果是boolean类型就转成int的数据返回，其它直接返回
     */
    public static Object formatValue(Object value) {
        String simpleName = value.getClass().getComponentType().getSimpleName();
        if (simpleName.equalsIgnoreCase("boolean")) {
            return convertBooleanToInt((Boolean) value);
        } else {
            return value;
        }
    }
}
