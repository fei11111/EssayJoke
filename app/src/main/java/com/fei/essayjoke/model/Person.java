package com.fei.essayjoke.model;

/**
 * @ClassName: Person
 * @Description: java类作用描述
 * @Author: Fei
 * @CreateDate: 2020-11-11 20:23
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-11-11 20:23
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class Person {

    private String name;
    private int age;
    private boolean isCheck;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", isCheck=" + isCheck +
                '}';
    }
}
