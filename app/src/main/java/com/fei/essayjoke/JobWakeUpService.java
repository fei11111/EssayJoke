package com.fei.essayjoke;

import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.fei.baselibrary.utils.LogUtils;

import java.util.List;

/**
 * @ClassName: JobWakeUpService
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2020/11/19 14:19
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/19 14:19
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
//5.0才有JobService
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobWakeUpService extends JobService {

    private static final String TAG = "JobWakeUpService";
    private final int JobWakeUpServiceId = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.i(TAG, "onCreate");
        createHeartBeat();
    }

    private void createHeartBeat() {
        JobInfo.Builder builder = new JobInfo.Builder(JobWakeUpServiceId, new ComponentName(this, JobWakeUpService.class));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //7.0后执行周期时间会只有大于等于15分钟才执行，只有设置最小延迟时间才能避免
            builder.setMinimumLatency(10000);//执行的最小延迟时间
            builder.setOverrideDeadline(10000); //执行的最长延时时间
            builder.setBackoffCriteria(2000, JobInfo.BACKOFF_POLICY_LINEAR);//线性重试方案
        } else {
            builder.setPeriodic(10000);//设置执行周期
        }
        builder.setPersisted(false);//设备重启以后是否重新执行任务
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);//设置任何网络环境下都可以执行
        builder.setRequiresCharging(false);//设置是否在只有插入充电器的时候执行
        builder.setRequiresDeviceIdle(false);//设置手机系统处于空闲状态下执行
        JobInfo jobInfo = builder.build();
        JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        if (scheduler.schedule(jobInfo) < 0) {
            LogUtils.i(TAG, "失败");
        } else {
            LogUtils.i(TAG, "成功");
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.i(TAG, "onStartCommand");

        return START_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        // 判断服务有没有在运行
        LogUtils.i(TAG, "onStartJob");
        boolean messageServiceAlive = serviceAlive(MessageService.class.getName());
        if (!messageServiceAlive) {
            startService(new Intent(this, MessageService.class));
        }
        createHeartBeat();
        jobFinished(params, false);
        return true;

    }

    @Override
    public boolean onStopJob(JobParameters params) {
        LogUtils.i(TAG, "onStopJob");
        return false;
    }

    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param serviceName 是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    private boolean serviceAlive(String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(100);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }
}
