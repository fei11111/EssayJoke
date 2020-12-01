# EssayJoke
*内涵段子（封装库）

####AsyncTask执行过程
#####ececute()方法一调用就会去判断状态，如果状态不对就会抛异常，然后会把状态置为Running ，  然后执行onPreExecute(), 开一个线程执行 doInBackground(),         doInBackground()执行完毕之后会利用Handler发送消息切换主线程中，然后执行onPostExecute()方法，最后把状态置为FINISHED。

####资源加载
 `try {
            Resources superRes = getResources();
            //AssetManager只能反射
            AssetManager assetManager = AssetManager.class.newInstance();
            //addAssetPath不可见，反射获取
            Method addAssetPath = assetManager.getClass().getDeclaredMethod("addAssetPath", String.class);
            //皮肤文件
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                    File.separator + "skin.skin");
            if (!file.exists()) {
                LogUtils.i(TAG, "皮肤文件不存在");
                return;
            }
            if (addAssetPath != null) {
                //加载皮肤
                addAssetPath.invoke(assetManager, file.getAbsolutePath());
                //创建皮肤Resources
                Resources resources = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
                // 通过资源名称,类型，包名获取Id
                int id = resources.getIdentifier("main_bg", "drawable", "com.fei.skin");
                Drawable drawable = resources.getDrawable(id);
                ImageView imageView = findViewById(R.id.iv_bg);
                imageView.setImageDrawable(drawable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }`
        
####加载View拦截 LayoutInflaterCompat.setFactory2()

####[bindService流程->ContextImpl.bindServiceCommon->封装sd-AMS.bindIsolatedService->AS.bindServiceLocked  ->requestServiceBindingLocked->ApplicationThread..scheduleBindService绑定服务
####->AMS.publishService->AS.publishServiceLocked


####JNI笔记####

1.写native方法
    public static native void combine(String oldApkPath,
                                      String newApkPath,
                                      String patchPath);

2.javah ­d jni -encoding utf-8 ­classpath D:\Workspace\NDKFirst\app\src\main\java com.brainbg.ndkfirst.NDKUtils
3.上面命令会生成头文件，将头文件放入jni目录，jni目录跟src同级，然后实现.c
4.1 添加 Android.mk文件(必加)
LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE        := first-jni
LOCAL_SRC_FILES     := first.c
include $(BUILD_SHARED_LIBRARY)

4.2 添加 Application.mk文件
APP_PLATFORM := android-16
APP_ABI :=all

4.3 生产so文件
cd D:\Workspace\NDKFirst\app

ndk-build

4.4 用Gradle链接c++项目
jni目录中右击任意文件选择Link C++ project with Gradle

5.拷贝bsdiff.c、bsdiff.h、bspatch.h、bspatch.c文件到jni目录，然后
bspatch会加载bzip2，所以还要下载bzip2

7.在gradle.properties下加android.useDeprecatedNdk=true

