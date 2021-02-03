# EssayJoke
*内涵段子（封装库）

####AsyncTask执行过程
#####ececute()方法一调用就会去判断状态，如果状态不对就会抛异常，然后会把状态置为Running,
然后执行onPreExecute(), 开一个线程执行 doInBackground(),
doInBackground()执行完毕之后会利用Handler发送消息切换主线程中，然后执行onPostExecute()方法，最后把状态置为FINISHED。

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

5.invalidate会调用到ViewRootImpl,performDraw
,之后回到View的draw,里面有dispatchView分发到所有子View

6.自定义View套路
 6.1 自定义属性，获取自定义属性
 6.2 onMeasure测量自己宽高，如果继承TextView，Button不用自己测量
 6.3 onDraw 用于自己绘制
 6.4 onTouch 用于与用户交互

7.自定义ViewGroup套路
 7.1 自定义属性，获取自定义属性（很少用）
 7.2 onMeasure，for循环测量子View，根据子View宽高来计算自己的宽高
 7.3 onDraw一般不需要，默认情况下不会调用，如果需要绘制需要实现dispatchDraw
 7.4 onLayout用于摆放子View，前提是不是GONE的情况
 7.5 在很多情况下不会继承自ViewGroup，往往是继承系统提供好的ViewGroup

8.事件分发
如果说子 View 没有一个地方返回 true ,只会进来一次只会响应 DOWN 事件,代表不需要消费该事件,
如果你想响应 MOVE,UP 必须找个地方true,这样mFirstTouchTarget不为空，但是父类还是能在onInterceptTouchEvent、dispatchTouch拦截
对于ViewGroup来讲，如果你想拦截子 View 的 Touch 事件，可以覆写 onInterceptTouchEvent 返回 true 即可,
但是这样ViewGroup的onTouchEvent也会执行(MOVE的情况)，如果要直接拦截不让ViewGroup自己的onTouchEvent执行，可以拦截dispatchTouch然后直接返回，不返回super
要在onInterceptTouchEvent拦截，必须保证mFirstTouchTarget不能为null，不然onInterceptTouchEvent也不会进来
如果子 View 没有消费 touch 事件也会调用该 ViewGroup 的 onTouchEvent 方法


平板 layout-sw600dp
横屏 layout-land

ldpi     120    240*320
mdpi     160dp    320px*480px  1dp = 1px
hdpi     240dp    480*800  1dp = 1.5px
xhdpi    320    720*1280   1dp = 2px 


