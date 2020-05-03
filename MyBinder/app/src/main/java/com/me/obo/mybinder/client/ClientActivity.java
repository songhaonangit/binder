package com.me.obo.mybinder.client;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.me.obo.mybinder.R;
import com.me.obo.mybinder.log.OLog;
import com.me.obo.mybinder.protocal.RemoteProtocal;

public class ClientActivity extends Activity {
    private static final String TAG = "ClientActivity";

    private IBinder mBinder;
    private TextView tvConnectState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        tvConnectState = findViewById(R.id.tv_connect_state);
    }

    public void onClick(View v) {
        OLog.i(TAG, "onClick");
        switch (v.getId()) {
            case R.id.btn_bind:
                // 点击 绑定按钮
                bindRemoteService();
                break;
            case R.id.btn_add:
                // 点击 Add 按钮
                if (mBinder != null) {
                    int result = add(1, 2);
                    Toast.makeText(this, "1 + 2 = " + result, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Please connect first", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    // 绑定远程服务
    private void bindRemoteService() {
        // 远程服务具体名称
        ComponentName componentName = new ComponentName(this, "com.me.obo.mybinder.server.MyService");
        Intent intent = new Intent();
        intent.setComponent(componentName);
        // 绑定到服务
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            // 持有binder的引用
            mBinder = iBinder;
            tvConnectState.setText("Connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {}
    };

    /**
     * 加法运算
     * @param a
     * @param b
     */
    private int add(int a, int b) {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        // 写入参数 a
        data.writeInt(a);
        // 写入参数 b
        data.writeInt(b);
        try {
            // 调用远程服务
            mBinder.transact(RemoteProtocal.CODE_ADD, data, reply, 0);
            // 获取远程计算结果
            int result = reply.readInt();
            OLog.i(TAG, "result = " + result);
            return result;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
