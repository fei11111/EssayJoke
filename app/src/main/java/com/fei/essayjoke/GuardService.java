package com.fei.essayjoke;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * @ClassName: GuardService
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2020/11/19 11:25
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/19 11:25
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class GuardService extends Service {

    private final int guardService = 1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(guardService, new Notification());
        bindService(new Intent(GuardService.this, MessageService.class), conn, Context.BIND_IMPORTANT);
        return START_STICKY;
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            startService(new Intent(GuardService.this, MessageService.class));
            bindService(new Intent(GuardService.this, MessageService.class), conn, Context.BIND_IMPORTANT);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return new ProcessAidl.Stub() {
        };
    }
}
