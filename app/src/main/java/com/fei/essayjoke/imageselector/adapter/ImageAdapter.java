package com.fei.essayjoke.imageselector.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fei.essayjoke.R;
import com.fei.essayjoke.imageselector.bean.ImageBean;
import com.fei.essayjoke.imageselector.listener.ImageSelectListener;
import com.fei.framelibrary.recyclerview.adapter.CommonRecyclerAdapter;
import com.fei.framelibrary.recyclerview.adapter.ViewHolder;

import java.util.List;

/**
 * @ClassName: ImageAdapter
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2020/11/23 10:23
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/23 10:23
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ImageAdapter extends CommonRecyclerAdapter<ImageBean> {

    private List<ImageBean> selectImageBeans;
    private int maxCount;

    public ImageAdapter(Context context, List<ImageBean> data, List<ImageBean> selectImageBeans, int maxCount) {
        super(context, data, R.layout.media_chooser_item);
        this.selectImageBeans = selectImageBeans;
        this.maxCount = maxCount;
    }

    @Override
    public void convert(ViewHolder holder, final ImageBean item) {
        if (item == null) {
            holder.setViewVisibility(R.id.image, View.INVISIBLE);
            holder.setViewVisibility(R.id.media_selected_indicator, View.INVISIBLE);
            holder.setViewVisibility(R.id.camera_ll, View.VISIBLE);
            holder.setOnIntemClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.cameraSelect();
                    }
                }
            });
        } else {
            holder.setViewVisibility(R.id.image, View.VISIBLE);
            holder.setViewVisibility(R.id.media_selected_indicator, View.VISIBLE);
            holder.setViewVisibility(R.id.camera_ll, View.INVISIBLE);

            ImageView imageView = holder.getView(R.id.image);
            Glide.with(mContext).load(item.getPath())
                    .centerCrop().into(imageView);

            //获取勾选view
            View view = holder.getView(R.id.media_selected_indicator);
            if (selectImageBeans.contains(item)) {
                view.setSelected(true);
            } else {
                view.setSelected(false);
            }
            holder.setOnIntemClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //获取原有状态
                    if (selectImageBeans.contains(item)) {
                        //移除
                        selectImageBeans.remove(item);
                    } else {
                        //新增，不能大于最大值
                        if (maxCount <= selectImageBeans.size()) {
                            Toast.makeText(mContext, "最多只能选取" + maxCount + "张图片", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            selectImageBeans.add(item);
                        }
                    }
                    notifyDataSetChanged();
                    if (listener != null) {
                        listener.imageSelect();
                    }
                }
            });
        }

    }

    private ImageSelectListener listener;

    public void setListener(ImageSelectListener listener) {
        this.listener = listener;
    }
}
