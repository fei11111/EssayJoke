package com.fei.essayjoke;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Toast;

import com.fei.framelibrary.base.BaseSkinActivity;
import com.fei.framelibrary.navigationBar.DefaultNavigatorBar;

public class MainActivity extends BaseSkinActivity {

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


//        HttpUtil.with(this).get("http://jd.itying.com/api/pcontent")
//                .addParam("id","5a080b2ead8b300e28e2fec9")//59f1e4919bfd8f3bd030eed6
//                .cache(true)// 读取缓存
//                .execute(new HttpCallBack<DiscoverListResult>() {
//            @Override
//            public void onError(Exception e) {
//
//            }
//
//
//            @Override
//            public void onSuccess(DiscoverListResult result) {
//
//            }
//        });
        startService(new Intent(this, UserServie.class));
        Intent intent = new Intent();
        intent.setAction("com.fei.user");
        intent.setPackage("com.fei.essayjoke");
        bindService(intent,
                conn, Context.BIND_AUTO_CREATE);

    }

    private UserAidl userAidl;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            userAidl = UserAidl.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void initView() {
//        ivText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CustomDialog dialog = new CustomDialog.Builder(MainActivity.this)
//                        .setContentView(R.layout.dialog_comment_detail).setText(R.id.submit_btn, "接收").show();
//                final EditText editText = dialog.getView(R.id.comment_editor);
//                dialog.setOnClickListener(R.id.submit_btn, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(MainActivity.this, editText.getText().toString(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
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

    public void skin(View view) throws RemoteException {
//        // 从服务器上下载
//
//        String SkinPath = Environment.getExternalStorageDirectory().getAbsolutePath()
//                + File.separator + "skin.skin";
////        // 换肤
//        SkinManager.getInstance().load(SkinPath);
        Toast.makeText(this, userAidl.getPassword(), Toast.LENGTH_SHORT).show();
    }

    public void skin1(View view) {
        // 恢复默认
//        SkinManager.getInstance().restore();
    }


    public void skin2(View view) {
//        // 跳转
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
    }


}