package com.musk.binder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.TextView;

import com.musk.bindpublic.IWaterManager;
import com.musk.bindpublic.Water;
import com.musk.bindpublic.WaterManagerImpl;

public class TestActivity extends Activity implements View.OnClickListener{

    //binder引用
    private IWaterManager mService;

    //服务连接
    private ServiceConnection conn= new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = WaterManagerImpl.asInterface(service);
            try {
                //关联销毁回调
                service.linkToDeath(deathHandle,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };

    //binder对象销毁回调
    private final IBinder.DeathRecipient deathHandle=new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            //binder died
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv=new TextView(this);
        tv.setText("test");
        tv.setClickable(true);
        tv.setOnClickListener(this);
        setContentView(tv);

        Intent in=new Intent("m.u.s.k");
        bindService(in,conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }

    @Override
    public void onClick(View v) {
        try {
            mService.addWater(new Water());
            mService.getWaters();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}