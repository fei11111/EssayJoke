package com.fei.essayjoke;

/**
 * @ClassName: Human
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2020/11/25 10:33
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/25 10:33
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class Human implements IBank{
    @Override
    public String apply(String str) {
        System.out.println("办卡"+str);
        return "收到";
    }
}
