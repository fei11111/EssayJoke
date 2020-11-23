package com.fei.essayjoke.imageselector;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fei.baselibrary.ioc.OnClick;
import com.fei.baselibrary.ioc.ViewById;
import com.fei.essayjoke.R;
import com.fei.essayjoke.imageselector.adapter.ImageAdapter;
import com.fei.essayjoke.imageselector.bean.ImageBean;
import com.fei.essayjoke.imageselector.listener.ImageSelectListener;
import com.fei.framelibrary.base.BaseSkinActivity;
import com.fei.framelibrary.navigationBar.DefaultNavigatorBar;

import java.util.ArrayList;
import java.util.List;


/**
 * @ClassName: SelectImageViewActivity
 * @Description: 图片选择
 * @Author: Fei
 * @CreateDate: 2020-11-22 20:21
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-11-22 20:21
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ImageSelectorActivity extends BaseSkinActivity implements View.OnClickListener, ImageSelectListener {

    private static final String TAG = "ImageSelectorActivity";
    private static final int LOAD_ID = 0x111;

    //获取最大值
    public static final String EXTRA_MAX_COUNT = "extra_max_count";
    //获取已选图片
    public static final String EXTRA_PICS = "extra_pics";
    //是否多选
    public static final String EXTRA_MULTI = "extra_multi";
    //是否显示拍照
    public static final String EXTRA_SHOW_CAMERA = "extra_show_camera";
    //返回结果
    public static final String EXTRA_RESULT = "extra_result";

    private int maxCount = 3;
    private ArrayList<ImageBean> selectImageBeans;
    private boolean isMulti;
    private boolean isShowCamera;

    @ViewById(R.id.image_list_rv)
    private RecyclerView imageRv;
    @ViewById(R.id.loading_progress)
    ProgressBar progressBar;
    @ViewById(R.id.select_preview)
    TextView tvSelectPreview;
    @ViewById(R.id.select_num)
    TextView tvSelectNum;

    private List<ImageBean> imageBeans;

    @Override
    protected void initData() {
        //获取传过来的参数
        Intent intent = getIntent();
        maxCount = intent.getIntExtra(EXTRA_MAX_COUNT, 3);
        selectImageBeans = intent.getParcelableArrayListExtra(EXTRA_PICS);
        isMulti = intent.getBooleanExtra(EXTRA_MULTI, true);
        isShowCamera = intent.getBooleanExtra(EXTRA_SHOW_CAMERA, true);

        if (selectImageBeans == null) {
            selectImageBeans = new ArrayList<>();
        }

        //异步获取相册图片
        LoaderManager.getInstance(this).initLoader(LOAD_ID,
                null, loaderCallbacks);

        //改变显示
        exchangeViewShow();
    }

    /**
     * 改变显示
     */
    private void exchangeViewShow() {

        //预览按钮改变
        if (selectImageBeans.size() > 0) {
            tvSelectPreview.setEnabled(true);
            tvSelectPreview.setOnClickListener(this);
        } else {
            tvSelectPreview.setEnabled(false);
            tvSelectPreview.setOnClickListener(null);
        }
        //图片数量改变
        tvSelectNum.setText(selectImageBeans.size() + "/" + maxCount);
    }

    LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
        String[] IMAGE_PROJECTION = new String[]{
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.MIME_TYPE,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media._ID
        };

        @Override
        public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
            progressBar.setVisibility(View.VISIBLE);
            CursorLoader cursorLoader = new CursorLoader(ImageSelectorActivity.this,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION, IMAGE_PROJECTION[4] + " >0 AND " + IMAGE_PROJECTION[3] +
                    " = ? OR " + IMAGE_PROJECTION[3] + " = ?", new String[]{"image/jpeg", "image/png"}, IMAGE_PROJECTION[2] + " DESC");
            return cursorLoader;
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
            progressBar.setVisibility(View.INVISIBLE);
            imageBeans = new ArrayList<>();

            //显示拍照按钮
            if (isShowCamera) {
                imageBeans.add(null);
            }

            if (data != null && data.getCount() > 0) {

                while (data.moveToNext()) {
                    ImageBean imageBean = new ImageBean();
                    imageBean.setPath(data.getString(data.getColumnIndex(IMAGE_PROJECTION[0])));
                    imageBean.setName(data.getString(data.getColumnIndex(IMAGE_PROJECTION[1])));
                    imageBean.setDate(data.getLong(data.getColumnIndex(IMAGE_PROJECTION[2])));
                    imageBean.setType(data.getString(data.getColumnIndex(IMAGE_PROJECTION[3])));
                    imageBean.setSize(data.getLong(data.getColumnIndex(IMAGE_PROJECTION[4])));
                    imageBean.setId(data.getLong(data.getColumnIndex(IMAGE_PROJECTION[5])));
                    imageBeans.add(imageBean);
                }
                refreshView();
            }
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {

        }
    };

    /**
     * 刷新界面
     */
    private void refreshView() {
        ImageAdapter imageAdapter = new ImageAdapter(this, imageBeans, selectImageBeans, maxCount);
        imageAdapter.setListener(this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);
        imageRv.setLayoutManager(layoutManager);
        imageRv.setAdapter(imageAdapter);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initTile() {
        new DefaultNavigatorBar.Builder(this).setTitle("图片选择").build();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_select_image;
    }

    @Override
    public void onClick(View v) {

    }


    @Override
    public void imageSelect() {
        exchangeViewShow();
    }

    @Override
    public void cameraSelect() {

    }

    @OnClick(R.id.select_finish)
    private void finish(View view) {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(EXTRA_RESULT, selectImageBeans);
        setResult(RESULT_OK, intent);
        finish();
    }
}
