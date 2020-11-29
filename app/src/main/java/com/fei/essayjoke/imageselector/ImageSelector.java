package com.fei.essayjoke.imageselector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.fei.essayjoke.imageselector.bean.ImageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ImageSelector
 * @Description: java类作用描述
 * @Author: Fei
 * @CreateDate: 2020-11-25 20:34
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-11-25 20:34
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ImageSelector {

    private int mRequestCode = -1;
    private int mMax = 3;
    private ArrayList<ImageBean> mSelectImages;
    private boolean mMulti;
    private boolean mShowCamera;

    /**
     * 初始化，必须先调用
     * */
    public static ImageSelector with() {
        return new ImageSelector();
    }

    /**
     * 最大数量（最多多少张）
     * */
    public ImageSelector max(int max){
        this.mMax = max;
        return this;
    }

    /**
     * 已选图片
     * */
    public ImageSelector selectImages(ArrayList<ImageBean> selectImages) {
        this.mSelectImages = selectImages;
        return this;
    }

    /**
     * 是否多选
     * */
    public ImageSelector multi(boolean isMulti){
        this.mMulti = isMulti;
        return this;
    }

    /**
     * 是否显示拍照
     * */
    public ImageSelector showCamera(boolean isShowCamera){
        this.mShowCamera = isShowCamera;
        return this;
    }

    /**
     * 请求码
     * */
    public ImageSelector requestCode(int requestCode){
        this.mRequestCode = requestCode;
        return this;
    }

    /**
     * 创建
     * */
    public void create(Activity context) {
        Intent intent = new Intent(context, ImageSelectorActivity.class);
        intent.putExtra(ImageSelectorActivity.EXTRA_SHOW_CAMERA,mShowCamera);
        intent.putExtra(ImageSelectorActivity.EXTRA_MULTI,mMulti);
        intent.putExtra(ImageSelectorActivity.EXTRA_MAX_COUNT,mMax);
        intent.putParcelableArrayListExtra(ImageSelectorActivity.EXTRA_PICS,mSelectImages);
        context.startActivityForResult(intent,mRequestCode);
    }





}
