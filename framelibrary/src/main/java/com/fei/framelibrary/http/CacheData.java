package com.fei.framelibrary.http;

/**
 * @ClassName: CacheData
 * @Description: 缓存表
 * @Author: Fei
 * @CreateDate: 2020/11/12 19:49
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/12 19:49
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CacheData {

    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "CacheData{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
