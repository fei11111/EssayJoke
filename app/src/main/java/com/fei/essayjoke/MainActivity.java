package com.fei.essayjoke;

import android.widget.ImageView;
import android.widget.TextView;

import com.fei.baselibrary.ExceptionCrashHandler;
import com.fei.baselibrary.ioc.ViewById;
import com.fei.framelibrary.BaseSkinActivity;

import java.io.File;

public class MainActivity extends BaseSkinActivity {

    @ViewById(R.id.tv_text)
    private TextView tvText;
    @ViewById(R.id.iv_text)
    private ImageView ivText;

    private static final String TAG = "MainActivity";

    @Override
    protected void initData() {
        //获取异常文件
        File crashFile = ExceptionCrashHandler.getInstance().getCrashFile();
//        if (crashFile.exists()) {
//            //上传到服务器
//            try {
//                InputStreamReader fileReader = new InputStreamReader(new FileInputStream(crashFile));
//                char[] bytes = new char[1024];
//                int len = 0;
//                if ((len = fileReader.read(bytes)) != -1) {
//                    String str = new String(bytes, 0, len);
//                    LogUtils.i("Tag", str);
//                }
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initTile() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }


}