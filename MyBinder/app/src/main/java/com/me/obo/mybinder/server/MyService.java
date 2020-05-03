package com.me.obo.mybinder.server;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import com.me.obo.mybinder.log.OLog;
import com.me.obo.mybinder.protocal.RemoteProtocal;

public class MyService extends Service {
    private static final String TAG = "MyService";

    @Override
    public IBinder onBind(Intent intent) {
        OLog.i(TAG, "onBind");
        return myBinder;
    }

    Binder myBinder = new Binder() {
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            OLog.i(TAG, "code = " + code + " flags = " + flags);
            switch (code) {
                case RemoteProtocal.CODE_ADD: {
                    int a = data.readInt();
                    int b = data.readInt();
                    int result = add(a, b);
                    reply.writeInt(result);
                    return true;
                }
            }
            return super.onTransact(code, data, reply, flags);
        }
    };

    public int add(int a, int b) {
        return a + b;
    }
}
