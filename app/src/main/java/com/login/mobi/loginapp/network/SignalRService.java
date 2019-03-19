package com.login.mobi.loginapp.network;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.login.mobi.loginapp.singleton.SingletonSharedPref;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnectionState;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;

/**
 * Created by grant on 2019-03-12.
 */

public class SignalRService extends Service{
    private static final String TAG = SignalRService.class.getSimpleName();
    public static boolean DEBUG=true;
    private IBinder binder=new LocalBinder();
    private final static String DOMAIN= "http://5.23.55.101";
    private final static String URL="/chat";
    private HubConnection hubConnection;
    private ExecutorService executorService;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        executorService= Executors.newSingleThreadExecutor();
        executorService.submit(new ConnectRunnable());
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(DEBUG){
            Log.d(TAG,"onDestroy()>>>>>>>>>>>>>>>>>>>>>>");
        }
        new Thread(new DisconnectRunnable()).start();
    }

    public class LocalBinder extends Binder {
        public SignalRService getInstance(){
            return SignalRService.this;
        }
    }

    private class ConnectRunnable implements Runnable {
        @Override
        public void run() {
            if(hubConnection!=null && hubConnection.getConnectionState()== HubConnectionState.CONNECTED){
                return;
            }
            if(DEBUG) {
                Log.d(TAG, "start connecting thread id : " + Thread.currentThread().getId() + " name: " + Thread.currentThread().getName());
            }
            String token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJpYW1iZXJpa2thZGFuQGdtYWlsLmNvbSIsImp0aSI6IjU1ODMzOWNhLTM5ZTktNDVkNC1iOWI3LTk4NmFmNDBhNGU4ZSIsImlhdCI6MTU1MjYyNzM3OCwicm9sIjoiYXBpX2FjY2VzcyIsImlkIjoiNjJlMDU2MTEtMjY4Ny00NTI1LTk2YTItNDRhZjhmNGYxNTlkIiwibmJmIjoxNTUyNjI3Mzc4LCJleHAiOjE1ODQxNjMzNzgsImlzcyI6IndlYkFwaSIsImF1ZCI6Imh0dHA6Ly9sb2NhbGhvc3Q6NTAwNC8ifQ.82VykYQZ5d4IneECu_wyn-5VJUVEEC0giZVgZJQFte0";
            Log.d(TAG,"token: "+SingletonSharedPref.TOKEN);
            hubConnection = HubConnectionBuilder.create(DOMAIN.concat(URL))
                    .withAccessTokenProvider(Single.defer(() -> Single.just(token)))
                    .build();


            hubConnection.on("ListenToOrder", (userName,message) -> {
                Log.d(TAG,"New Message: " + message);
            }, String.class,String.class);

            hubConnection.start().blockingAwait();
            hubConnection.send("MakeOrder","Test from android");
        }
    }

    private class DisconnectRunnable implements Runnable{
        @Override
        public void run() {
            if(hubConnection!=null && hubConnection.getConnectionState()== HubConnectionState.CONNECTED){
                hubConnection.stop();
            }
            if(executorService!=null && !executorService.isShutdown()){
                executorService.shutdown();
                try {
                    executorService.awaitTermination(1, TimeUnit.MINUTES);
                } catch (InterruptedException e) {
                    if(DEBUG){
                        Log.d(TAG,"DisconnectRunnable() InterruptedException:  "+e.getMessage());
                    }
                }
            }
        }
    }

}
