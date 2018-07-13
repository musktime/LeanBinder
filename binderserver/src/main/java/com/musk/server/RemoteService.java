package com.musk.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import com.musk.bindpublic.Water;
import com.musk.bindpublic.WaterManagerImpl;
import java.util.ArrayList;
import java.util.List;

/**
 * 1.实名Binder：能通过ServiceManager查询到的Binder
 * 2.匿名Binder：普通应用开发的Binder
 *
 * ---------------------------------------------------------
 * Binder实体对象：Binder服务的提供者
 * Binder引用对象：Binder实体对象在客户端进程的代表
 * Binder代理对象：又叫接口对象，为应用上层提供服务
 */
public class RemoteService extends Service {

    private List<Water>waters=new ArrayList<>();
    private final WaterManagerImpl mBinder=new WaterManagerImpl() {
        @Override
        public void addWater(Water water) throws RemoteException {
            waters.add(water);
        }

        @Override
        public List<Water> getWaters() throws RemoteException {
            return waters;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
