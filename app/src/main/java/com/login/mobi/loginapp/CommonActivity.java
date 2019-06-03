package com.login.mobi.loginapp;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.login.mobi.loginapp.network.SignalRService;

/**
 * Created by nurilkaa on 2019-04-23.
 */

public class CommonActivity extends AppCompatActivity {
    // SignalR
    private boolean bounded;
    private SignalRService signalRService;
    private ServiceConnection signalRServiceConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bounded = true;
            SignalRService.LocalBinder localBinder = (SignalRService.LocalBinder) service;
            signalRService = localBinder.getInstance();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bounded = false;
            signalRService = null;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectToSignalRService();
    }

    // SignalR
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("SignalRService","CommonActivity onDestroy>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        if(bounded){
            unbindService(signalRServiceConnection);
        }
    }

    private void connectToSignalRService() {
        Intent signalRServiceIntent = new Intent(getBaseContext(), SignalRService.class);
        signalRServiceIntent.putExtra("isStartForegroundService",true);    // c версии 8.0 добавили ограничения на исп. задач в бэкграунде
        startService(signalRServiceIntent);
        bindService(signalRServiceIntent, signalRServiceConnection,BIND_AUTO_CREATE);
    }
}
