package com.fei.essayjoke;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fei.baselibrary.ExceptionCrashHandler;
import com.fei.baselibrary.fix.FixManager;
import com.fei.baselibrary.ioc.ViewById;
import com.fei.framelibrary.BaseSkinActivity;

import java.io.File;
import java.io.IOException;

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

        FixManager fixManager = new FixManager(MainActivity.this);
        fixManager.init();
        fixManager.loadPatch();
        File dir = Environment.getExternalStorageDirectory();
        File file = new File(dir, "3.dex");
        Log.i("tag", file.getAbsolutePath());
        if (file.exists()&&file.isFile()) {
            try {
                fixManager.addPatch(file);///需要放到线程操作
                Toast.makeText(MainActivity.this, "修复成功", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "修复失败", Toast.LENGTH_SHORT).show();
            } catch (IllegalAccessException e) {
                Toast.makeText(MainActivity.this, "修复失败", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                Toast.makeText(MainActivity.this, "修复失败", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        ivText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(TestActivity.class);
            }
        });

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