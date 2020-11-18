package com.fei.essayjoke;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

/**
 * @ClassName: Service
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2020/11/18 15:32
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/18 15:32
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class UserServie extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return new UserBinder();
    }

    private class UserBinder extends UserAidl.Stub{
        @Override
        public String getUserName() throws RemoteException {
            return "123";
        }

        @Override
        public String getPassword() throws RemoteException {
            return "456";
        }
    }
}
