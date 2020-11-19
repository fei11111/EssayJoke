package com.fei.essayjoke;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.fei.baselibrary.utils.LogUtils;

/**
 * @ClassName: MessageService
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2020/11/19 11:22
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/19 11:22
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MessageService extends Service {

    private static final String TAG = "MessageService";
    private final int messageServiceId = 1;

    @Override
    public void onCreate() {
        super.onCreate();

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    LogUtils.i(TAG, "进程存在");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(messageServiceId, new Notification());
        startService(new Intent(this, GuardService.class));
        bindService(new Intent(this, GuardService.class), conn, Context.BIND_IMPORTANT);
        return START_STICKY;
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtils.i(TAG, "绑定成功");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            startService(new Intent(MessageService.this, GuardService.class));
            bindService(new Intent(MessageService.this, GuardService.class), conn, Context.BIND_IMPORTANT);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return new ServiceConnect.Stub() {
        };
    }
}
