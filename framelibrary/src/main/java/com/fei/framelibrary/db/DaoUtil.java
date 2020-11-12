package com.fei.framelibrary.db;

import android.database.Cursor;
import android.util.ArrayMap;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private static final String TAG = "DaoUtil";


    /**
     * 基本类型转成对象类型
     */
    public static Class<?> covertTypeToObjectType(String name) {
        switch (name.toLowerCase()) {
            case "int":
                return Integer.class;
            case "boolean":
                return Boolean.class;
            case "float":
                return Float.class;
            case "double":
                return Double.class;
            case "string":
                return String.class;
            case "long":
                return Long.class;
            default:
                return null;
        }
    }

    /**
     * 基本类型转成对象类型
     */
    public static String covertObjectTypeToType(Class<?> clazz) {
        if (Integer.class.equals(clazz)) {
            return "int";
        } else if (Boolean.class.equals(clazz)) {
            return "boolean";
        } else if (Float.class.equals(clazz)) {
            return "float";
        } else if (Double.class.equals(clazz)) {
            return "double";
        } else if (String.class.equals(clazz)) {
            return "string";
        } else if (Long.class.equals(clazz)) {
            return "long";
        }
        return null;
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

    /**
     * 将游标值转成list
     */
    public static <T> List<T> covertQueryToList(Cursor query, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        ArrayMap<String, Method> methodArrayMap = new ArrayMap<>();
        if (query != null && query.moveToNext()) {
            try {
                do {
                    T data = clazz.newInstance();
                    Field[] fields = clazz.getDeclaredFields();
                    for (Field field : fields) {
                        //1.获取属性名
                        field.setAccessible(true);
                        String fieldName = field.getName();
                        String typeName = field.getType().getSimpleName();
                        //2.通过属性名获取下标
                        int columnIndex = query.getColumnIndex(fieldName);
                        if (columnIndex == -1) continue;
                        //3.通过下标获取值
                        //3.1 通过获取游标的getXxx方法
                        if (typeName == null) continue;
                        if (typeName.equalsIgnoreCase("boolean")) {
                            //查询中，没有boolean类型，需要转换
                            typeName = "int";
                        } else if (typeName.equalsIgnoreCase("date")) {
                            typeName = "long";
                        }
                        String methodName = "get" + typeName.substring(0, 1).toUpperCase() + typeName.substring(1);
                        Method getMethod = methodArrayMap.get(methodName);
                        if (getMethod == null) {
                            getMethod = query.getClass().getMethod(methodName, int.class);//getString(下标);
                            methodArrayMap.put(methodName, getMethod);//缓存getXxx方法，不用一直用反射获取
                        }
                        //3.2 通过反射获取值
                        if (getMethod == null) continue;
                        Object value = getMethod.invoke(query, columnIndex);
                        //3.3需要做类型转换
                        if (field.getType().getSimpleName().equalsIgnoreCase("boolean")) {
                            //如果是boolean类型，数据库保存时int类型，需要转换
                            if ("0".equals(String.valueOf(value))) {
                                value = false;
                            } else if ("1".equals(String.valueOf(value))) {
                                value = true;
                            }
                        } else if (field.getType().getSimpleName().equalsIgnoreCase("char") ||
                                field.getType().getSimpleName().equalsIgnoreCase("character")
                        ) {
                            value = ((String) value).charAt(0);
                        } else if (field.getType().getSimpleName().equalsIgnoreCase("date")) {
                            long date = (Long) value;
                            if (date <= 0) {
                                value = null;
                            } else {
                                value = new Date(date);
                            }
                        }
                        //4.通过反射注入到对象中
                        field.set(data, value);
                        list.add(data);
                    }
                } while (query.moveToNext());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } finally {
                query.close();
            }
        }


        return list;
    }
}
