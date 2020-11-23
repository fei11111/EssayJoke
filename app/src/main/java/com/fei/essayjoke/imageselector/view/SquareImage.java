package com.fei.essayjoke.imageselector.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * @ClassName: SquareImage
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2020/11/23 11:10
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/23 11:10
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SquareImage extends AppCompatImageView {
    public SquareImage(@NonNull Context context) {
        super(context);
    }

    public SquareImage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImage(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width;
        setMeasuredDimension(width, height);
    }
}
