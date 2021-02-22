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
                                                      ->performLaunchActivity->activity.attach->phoneWindow和windowManager,windowManager实现是windowManagerImpl
                                                      ->mInstrumentation.callActivityOnCreate->Acitivity.onCreate->setContentView
                                                                   ->View.onFinishInflate
                                                      {transaction.setLifecycleStateRequest->ResumeActivityItem->ActivityThread
                                                      ->Activity.onResume
                                                      ->vm.addView->windowManagerImpl.addView->WindowMangerGlobal.addView->viewrootImpl.setView->requestLayout
                                                                  ->performMeasure->measure->onMeasure
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

类的加载流程：加载、验证、准备、解析和初始化

六大原则（小插曲：链式调用）
1.单一原则：一个类只负责一件事
2.开闭原则：对外可扩展，对内是关闭的
3.里式替换原则：最主要的体现是继承和实现，一般跟开闭原则配套使用
4.依赖倒置：高层只依赖抽象，不依赖细节，也就是不依赖实现
5.接口隔离原则：一个接口里尽量少的方法，还有意思就是面向接口编程
6.迪米特原则：一个对象应该对其它对象有最少的了解，也就是调用时不需要知道太多东西，就可以直接调用

面向切面编程（AOP）AspectJ(再生成dex文件前新增一些代码)
1 下载 aspectj-1.8.10.jar 文件
2 双击安装 一直点击下一步
3 新建应用  然后在 build.gradle 里面添加配置
4 去安装目录下面 copy 一个开发包
5 写代码

类图
1.Generalization泛型(继承)
2.Realization实现
3.Dependency依赖，体现局部变量函数参数
4.Association关联，体现在成员变量，双向，单向
5.Aggregation聚合，整体与部分，并且没有了整体，局部也可单独存在，如公司和员工，车和轮胎
6.Composition组合，整体与局部的关系，是一种强烈的包含关系，部分不能脱离整体存在，如公司和部门


apt+注解(用来在编译时扫描和处理注解)
简单来说就是在编译期，通过注解生成.java文件。
举例如ButterKnife，三个module
butterKnife-annotation 定义注解 /java module
butterKnife 工具类
butterKnife-processor 注解处理器 /java module
app 依赖 butterKnife和butterKnife-annotation，并且annotationProcessor butterKnife-processor
butterKnife-processor 依赖butterKnife-annotation
注意1.若Gradle<2.2
在Project的 build.gradle中：
buildscript {
    dependencies {
        classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
    }
}
在Module的buile.gradle中：
apply plugin: 'com.android.application'
apply plugin: 'com.neenbedankt.android-apt'
dependencies {
    apt project(':apt-processor')
}
注意2.运行时不能生成文件
processor的build.gradle加入如下两句成功
compileOnly 'com.google.auto.service:auto-service:1.0-rc4'
annotationProcessor 'com.google.auto.service:auto-service:1.0-rc4'

设计模式
1.单例模式：
套路：1.构造函数必须私有，防止外层new
     2.提供一个静态方法获得对象
volatile: 1.防止线程重排序
          2.线程可见性

2.Builder设计模式(参数很多的情况下)
套路：1.静态内部类用于设置参数
     2.静态内部类创建返回对象

3.工厂设计模式 （创建对象的过程是交由工厂实现，实现解耦）
套路：很多对象有共性，并且我们不想让别人知道创建的细节并且创建的过程较为复杂
3.1简单工厂（枚举生产工厂对象）
3.2工厂方法（一个工厂生产一种对象）
3.3抽象工厂（一个工厂生产一类对象）

4.装饰设计模式：在不是继承的方式下，扩展一个对象的功能。

5.模板设计模式
5.1 Activity生命周期
5.2 AsyncTask
5.3 View的draw()方法
BlockingQueue 先进先出队列
SynchronousQueue 线程安全队列，它里面没有固定的缓存
PriorityBlockingQueue 无序的可以根据优先级排序

6.策略设计模式（算法不一样，但功能一样）


9.代理设计模式
9.1静态代理-将对象作为参数传入类中
9.2动态代理-Proxy.newProxyInstance

10.原型设计模式
套路：实现clonable，重写clone()




