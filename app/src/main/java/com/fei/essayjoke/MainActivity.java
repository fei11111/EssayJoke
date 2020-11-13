package com.fei.essayjoke;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fei.baselibrary.http.HttpUtil;
import com.fei.baselibrary.ioc.ViewById;
import com.fei.baselibrary.utils.LogUtils;
import com.fei.baselibrary.view.dialog.CustomDialog;
import com.fei.essayjoke.model.DiscoverListResult;
import com.fei.essayjoke.model.Person;
import com.fei.framelibrary.base.BaseSkinActivity;
import com.fei.framelibrary.db.DbSupportFactory;
import com.fei.framelibrary.db.IDaoSupport;
import com.fei.framelibrary.http.HttpCallBack;
import com.fei.framelibrary.navigationBar.DefaultNavigatorBar;

import java.util.List;

public class MainActivity extends BaseSkinActivity {

    @ViewById(R.id.tv_text)
    private TextView tvText;
    @ViewById(R.id.iv_text)
    private ImageView ivText;

    private static final String TAG = "MainActivity";

    @Override
    protected void initData() {
        //异常处理
//        File crashFile = ExceptionCrashHandler.getInstance().getCrashFile();
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

        ///热修复
//        FixManager fixManager = new FixManager(MainActivity.this);
//        fixManager.init();
//        fixManager.loadPatch();
//        File dir = Environment.getExternalStorageDirectory();
//        File file = new File(dir, "3.dex");
//        Log.i("tag", file.getAbsolutePath());
//        if (file.exists()&&file.isFile()) {
//            try {
//                fixManager.addPatch(file);///需要放到线程操作
//                Toast.makeText(MainActivity.this, "修复成功", Toast.LENGTH_SHORT).show();
//            } catch (IOException e) {
//                e.printStackTrace();
//                Toast.makeText(MainActivity.this, "修复失败", Toast.LENGTH_SHORT).show();
//            } catch (IllegalAccessException e) {
//                Toast.makeText(MainActivity.this, "修复失败", Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//            } catch (NoSuchFieldException e) {
//                Toast.makeText(MainActivity.this, "修复失败", Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        }


        //网络请求
//        HttpUtil.with(this).get("").execute(new HttpCallBack<String>() {
//
//            @Override
//            public void onPreExecute() {
//
//            }
//
//            @Override
//            public void onError(Exception e) {
//
//            }
//
//            @Override
//            public void onSuccess(String result) {
//
//            }
//        });
//
//        List<Person> persons = new ArrayList<>();
//        for (int i = 0; i < 10000; i++) {
//            Person person = new Person();
//            person.setName("Peter");
//            person.setAge(23 + i);
//            person.setCheck(true);
//            persons.add(person);
//        }


//        long startTime = System.currentTimeMillis();
//
//        IDaoSupport<Person> dao = DbSupportFactory.getFactory().getDao(Person.class);
////        long insert = dao.insert(persons);
////        LogUtils.i(TAG, "插入" + insert);
//        List<Person> people = dao.getQuerySupport().query();
//        LogUtils.i(TAG, people.toString());
//        long endTime = System.currentTimeMillis();
//
//        LogUtils.i(TAG, "" + (endTime - startTime));


        HttpUtil.with(this).get("http://jd.itying.com/api/pcontent")
                .addParam("id","5a080b2ead8b300e28e2fec9")//59f1e4919bfd8f3bd030eed6
                .cache(true)// 读取缓存
                .execute(new HttpCallBack<DiscoverListResult>() {
            @Override
            public void onError(Exception e) {

            }


            @Override
            public void onSuccess(DiscoverListResult result) {

            }
        });

    }

    @Override
    protected void initView() {
        ivText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog dialog = new CustomDialog.Builder(MainActivity.this)
                        .setContentView(R.layout.dialog_comment_detail).setText(R.id.submit_btn, "接收").show();
                final EditText editText = dialog.getView(R.id.comment_editor);
                dialog.setOnClickListener(R.id.submit_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, editText.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void initTile() {
        DefaultNavigatorBar defaultNavigatorBar = new DefaultNavigatorBar.Builder(this)
                .setTitle("标题").setRightText("发布").setOnRightTextListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "发布", Toast.LENGTH_SHORT).show();
                    }
                }).build();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }


}