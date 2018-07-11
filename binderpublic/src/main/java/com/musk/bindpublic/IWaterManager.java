package com.musk.bindpublic;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import java.util.List;

public interface IWaterManager extends IInterface {
    static final String DESCRIPTOR="com.musk.bindpublic.IWaterManager";
    static final int TRANSACTION_addWater= IBinder.FIRST_CALL_TRANSACTION+0;
    static final int TRANSACTION_getWaters=IBinder.FIRST_CALL_TRANSACTION+1;

    public void addWater(Water water)throws RemoteException;
    public List<Water>getWaters() throws RemoteException;
}