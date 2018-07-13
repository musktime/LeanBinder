package com.musk.bindpublic;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.List;

/**
 * 1.Client 发起远程调用请求 也就是RPC 到Binder。同时将自己挂起，挂起的原因是要等待RPC调用结束以后返回的结果

   2.Binder 收到RPC请求以后 把参数收集一下，调用transact方法，把RPC请求转发给service端。

   3.service端 收到rpc请求以后 就去线程池里 找一个空闲的线程去走service端的 onTransact方法 ，实际上也就是真正在运
    行service端的 方法了，等方法运行结束 就把结果 写回到binder中。

   4.Binder 收到返回数据以后 就唤醒原来的Client 线程，返回结果。至此，一次进程间通信 的过程就结束了
 */
public abstract class WaterManagerImpl extends Binder implements IWaterManager{

    public WaterManagerImpl(){
        this.attachInterface(this,DESCRIPTOR);
    }

    public static IWaterManager asInterface(IBinder obj){
        if(obj==null){
            return null;
        }
        IInterface iin=obj.queryLocalInterface(DESCRIPTOR);
        if(((iin!=null)&&(iin instanceof IWaterManager))){
            return (IWaterManager)iin;
        }
        return new WaterManagerProxy(obj);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    @Override
    protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
        switch (code){
            case INTERFACE_TRANSACTION: {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            case TRANSACTION_addWater: {
                data.enforceInterface(DESCRIPTOR);
                if (0 != data.readInt()) {
                    Water arg0 = Water.CREATOR.createFromParcel(data);
                }
                return true;
            }
            case TRANSACTION_getWaters:{
                data.enforceInterface(DESCRIPTOR);
                List<Water>waters=getWaters();
                reply.writeNoException();
                reply.writeTypedList(waters);
                return true;
            }
        }
        return super.onTransact(code, data, reply, flags);
    }

    /**
     * Binder代理类负责将方法代号，参数，返回值包装进parcel传递
     */
    private static class WaterManagerProxy extends WaterManagerImpl{
        private IBinder mRemote;

        public WaterManagerProxy(IBinder remote){
            mRemote=remote;
        }

        @Nullable
        @Override
        public String getInterfaceDescriptor() {
            return DESCRIPTOR;
        }

        @Override
        public IBinder asBinder() {
            return mRemote;
        }

        @Override
        public void addWater(Water water) throws RemoteException {
            Parcel data=Parcel.obtain();
            Parcel reply=Parcel.obtain();
            try {
                data.writeInterfaceToken(DESCRIPTOR);
                if (water != null) {
                    data.writeInt(1);
                    water.writeToParcel(data, 0);
                } else {
                    data.writeInt(0);
                }
                mRemote.transact(TRANSACTION_addWater, data, reply, 0);
                reply.readException();
            }finally {
                data.recycle();
                reply.recycle();
            }
        }

        @Override
        public List<Water> getWaters() throws RemoteException {
            Parcel data=Parcel.obtain();
            Parcel reply=Parcel.obtain();
            List<Water>waters;
            try{
                data.writeInterfaceToken(DESCRIPTOR);
                mRemote.transact(TRANSACTION_getWaters,data,reply,0);
                reply.readException();
                waters=reply.createTypedArrayList(Water.CREATOR);
            }finally {
                data.recycle();
                reply.recycle();
            }
            return waters;
        }
    }
}