package com.musk.server;

import android.os.RemoteException;
import com.musk.bindpublic.Water;
import com.musk.bindpublic.WaterManagerImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * 模拟系统AMS实现方式
 * MAS通过ServiceManager注册过了，属于实名binder
 */
public class WaterManagerService extends WaterManagerImpl{
    private List<Water>waters=new ArrayList<>();

    @Override
    public void addWater(Water water) throws RemoteException {
        waters.add(water);
    }

    @Override
    public List<Water> getWaters() throws RemoteException {
        return waters;
    }
}