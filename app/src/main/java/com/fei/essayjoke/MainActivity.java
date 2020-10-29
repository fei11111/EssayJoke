package com.fei.essayjoke;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fei.baselibrary.ioc.CheckNet;
import com.fei.baselibrary.ioc.OnClick;
import com.fei.baselibrary.ioc.ViewById;
import com.fei.baselibrary.ioc.ViewUtils;

public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.tv_text)
    private TextView tvText;

    @ViewById(R.id.iv_text)
    private ImageView ivText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewUtils.inject(this);

        tvText.setText("测试");

    }

    @OnClick({R.id.iv_text,R.id.tv_text})
    @CheckNet("")
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_text:
                Toast.makeText(this, "点击文本", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_text:
                Toast.makeText(this, "点击图片", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}