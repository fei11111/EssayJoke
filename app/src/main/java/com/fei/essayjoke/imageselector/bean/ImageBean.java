package com.fei.essayjoke.imageselector.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @ClassName: ImageBean
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2020/11/23 9:27
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/23 9:27
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ImageBean implements Parcelable {

    private String path;
    private String type;
    private long size;
    private String name;
    private long date;
    private long id;

    public ImageBean() {
    }

    protected ImageBean(Parcel in) {
        path = in.readString();
        type = in.readString();
        size = in.readLong();
        name = in.readString();
        date = in.readLong();
        id = in.readLong();
    }

    public static final Creator<ImageBean> CREATOR = new Creator<ImageBean>() {
        @Override
        public ImageBean createFromParcel(Parcel in) {
            return new ImageBean(in);
        }

        @Override
        public ImageBean[] newArray(int size) {
            return new ImageBean[size];
        }
    };

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        dest.writeString(type);
        dest.writeLong(size);
        dest.writeString(name);
        dest.writeLong(date);
        dest.writeLong(id);
    }
}
