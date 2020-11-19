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

    private final int JobWakeUpServiceId = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        JobInfo.Builder builder = new JobInfo.Builder(JobWakeUpServiceId, new ComponentName(this, JobWakeUpService.class));
        builder.setPeriodic(2000);
        JobInfo jobInfo = builder.build();
        JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        scheduler.schedule(jobInfo);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        // 判断服务有没有在运行
        boolean messageServiceAlive = serviceAlive(MessageService.class.getName());
        if (!messageServiceAlive) {
            startService(new Intent(this, MessageService.class));
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
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
