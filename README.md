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

####startActivity->startActivityForResult->mInstrumentation.execStartActivity->ActivityTaskManagerService.startActivity
####->ActivityStarter.exec->ActivityStarter.startActivityUnChecked->mRootActivityContainer.resumeFocusedStacksTopActivities
####->activityStack.resumeTopActivityUncheckedLocked->{startPausingLocked->PauseActivityItem->ActivityThread
                                                      {mStackSupervisor.startSpecificActivityLocked->LaunchActivityItem->ActivityThread
                                                      ->mInstrumentation.callActivityOnCreate->Acitivity.onCreate->setContentView
                                                      ->onFinishInflate
                                                      {transaction.setLifecycleStateRequest->ResumeActivityItem->ActivityThread
                                                      ->vm.addView->viewrootImpl.setView->performMeasure->measure->onMeasure
                                                      ->performLayout->layout->onLayout
                                                      ->performDraw->draw->onDraw
                                                                   
                                                      
                                                       



####View

1.获取字体宽高paint.getTextBounds()

2.baseline计算
//bottom是正值，top是负值
dy = (fontMetrics.bottom-fontMetrics.top) / 2 -fontMetrics.bottom;
//dy代表高度一半到baseline的距离
baseLine = getHeight()/2 + dy;

3.继承ViewGroup不会调用onDraw()
##可以用dispatchDraw()
##设置背景
##setWillNotDraw()

4.draw用的是模板设计模式




