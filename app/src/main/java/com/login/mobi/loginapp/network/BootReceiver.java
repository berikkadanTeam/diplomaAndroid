package com.login.mobi.loginapp.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

/**
 * Created by nurilkaa on 2019-03-19.
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive (Context context, Intent intent) {
        Log.d("SignalRService","BootReceiver>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Intent signalRServiceIntent = new Intent(context,SignalRService.class);
        signalRServiceIntent.putExtra("isStartForegroundService",true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            context.startForegroundService(signalRServiceIntent);
        } else{
            context.startService(signalRServiceIntent);
        }
    }
}
