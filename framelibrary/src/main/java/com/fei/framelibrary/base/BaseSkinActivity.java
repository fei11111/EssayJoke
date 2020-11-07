package com.fei.framelibrary.base;

import android.os.Bundle;

import com.fei.baselibrary.base.BaseActivity;
import com.fei.baselibrary.utils.SPUtils;
import com.fei.framelibrary.R;

import androidx.annotation.Nullable;


public abstract class  BaseSkinActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initTheme();
        super.onCreate(savedInstanceState);
    }

    protected void initTheme() {
        int mode = (int) SPUtils.get(this, "fontMode", 0);
        if (mode == 0) {
            setTheme(R.style.NormalFontStyle);
        } else if (mode == 1) {
            setTheme(R.style.SmallFontStyle);
        } else if (mode == 2) {
            setTheme(R.style.BigFontStyle);
        }
    }
}
