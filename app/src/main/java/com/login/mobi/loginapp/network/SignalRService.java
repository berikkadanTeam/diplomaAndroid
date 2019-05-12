package com.login.mobi.loginapp.network;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.singleton.SingletonSharedPref;
import com.login.mobi.loginapp.views.waiter.orders.OrdersPage;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnectionState;

import io.reactivex.Single;

/**
 * Created by nurilkaa on 2019-03-12.
 */

public class SignalRService extends Service{
    private static final String TAG = SignalRService.class.getSimpleName();
    public static boolean DEBUG = true;
    private IBinder binder = new LocalBinder();
    private final static String DOMAIN = "http://5.23.55.101";
    private final static String URL = "/chat";
    private HubConnection hubConnection;
    private volatile boolean isConnectRunnableRunning;
    private NotificationManager notificationManager;
    private static final int START_FOREGROUND_ID = 245;
    private static final String CHANEL_ID = "com.login.mobi.loginapp.chanel";

    // Shared Preferences
    SingletonSharedPref sharedPref;
    private String userID, token;


    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANEL_ID,SignalRService.class.getSimpleName(), NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannel);
        }
        sharedPref = SingletonSharedPref.getInstance(this);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"onBind>>>>>>>>>>>>>>>>>>>>>>");
        connect();
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG,"onUnbind>>>>>>>>>>>>>>>>>>>>>>");
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"onStartCommand>>>>>>>>>>>>>>>>>>>>>>");
        if (intent!=null){
            boolean isStartForegroundService = intent.getBooleanExtra("isStartForegroundService",false);
            if (isStartForegroundService){
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext(), CHANEL_ID);
                Intent deleteIntent = new Intent(this, SignalRService.class);
                deleteIntent.putExtra("isCancelNotification", true);
                PendingIntent deletePendingIntent = PendingIntent.getService(this,
                        123,
                        deleteIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT);
                startForeground(START_FOREGROUND_ID, builder.setDeleteIntent(deletePendingIntent).build());
            }
            if (intent.getBooleanExtra("isCancelNotification",false)){
                notificationManager.cancel(START_FOREGROUND_ID);
            }
        }
        connect();
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (DEBUG){
            Log.d(TAG,"onDestroy()>>>>>>>>>>>>>>>>>>>>>>");
        }
        new Thread(new DisconnectRunnable()).start();
    }

    @Override
    public void onTaskRemoved (Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.d(TAG,"onTaskRemoved>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        PendingIntent pendingIntent=PendingIntent.getService(this,258,new Intent(this,SignalRService.class),PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 1000, pendingIntent);
    }

    public class LocalBinder extends Binder {
        public SignalRService getInstance(){
            return SignalRService.this;
        }
    }

    private void connect(){
        new Thread(new ConnectRunnable()).start();
    }

    private class ConnectRunnable implements Runnable {
        @Override
        public void run() {
            if (isConnectRunnableRunning){
                return;
            }
            isConnectRunnableRunning = true;
            if (hubConnection!=null && hubConnection.getConnectionState() == HubConnectionState.CONNECTED){
                isConnectRunnableRunning = false;
                return;
            }
            if (DEBUG) {
                Log.d(TAG, "start connecting thread id: " + Thread.currentThread().getId() + " name: " + Thread.currentThread().getName()+" :hubConnection : "+hubConnection);
            }

            //String token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJpYW1iZXJpa2thZGFuQGdtYWlsLmNvbSIsImp0aSI6IjU1ODMzOWNhLTM5ZTktNDVkNC1iOWI3LTk4NmFmNDBhNGU4ZSIsImlhdCI6MTU1MjYyNzM3OCwicm9sIjoiYXBpX2FjY2VzcyIsImlkIjoiNjJlMDU2MTEtMjY4Ny00NTI1LTk2YTItNDRhZjhmNGYxNTlkIiwibmJmIjoxNTUyNjI3Mzc4LCJleHAiOjE1ODQxNjMzNzgsImlzcyI6IndlYkFwaSIsImF1ZCI6Imh0dHA6Ly9sb2NhbGhvc3Q6NTAwNC8ifQ.82VykYQZ5d4IneECu_wyn-5VJUVEEC0giZVgZJQFte0";
            token = sharedPref.getString(SingletonSharedPref.TOKEN);
            Log.d(TAG,"token: " + SingletonSharedPref.TOKEN);

            String role = sharedPref.getString(SingletonSharedPref.ROLE);
            Log.d("SignalR ROLE", role);

            hubConnection = HubConnectionBuilder.create(DOMAIN.concat(URL))
                    .withAccessTokenProvider(Single.defer(() -> Single.just(token)))
                    .build();



            // Когда user сделал заказ
            if (sharedPref.getStringSet("roles").contains("Waiter")){
                hubConnection.on("ListenToOrder", (userName,message) -> {
                    Log.d(TAG,"New Order from client Message: " + message);

                    Intent intent = new Intent(getBaseContext(), OrdersPage.class);
                    intent.putExtra("orderJson", message);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        NotificationChannel notificationChannel = new NotificationChannel(CHANEL_ID,SignalRService.class.getSimpleName(), NotificationManager.IMPORTANCE_DEFAULT);
                        notificationChannel.enableLights(true);
                        notificationChannel.setLightColor(Color.GREEN);
                        notificationChannel.setShowBadge(true);
                        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                        notificationManager.createNotificationChannel(notificationChannel);
                    }

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext(), CHANEL_ID);
                    builder.setSmallIcon(R.drawable.icon_food)
                            .setContentTitle("В ресторане появился новый заказ!")
                            .setContentText(message)
                            .setTicker("В ресторане появился новый заказ!") //текст, который отобразится вверху статус-бара при создании уведомления
                            .setContentIntent(pendingIntent)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon_food))
                            .setDefaults(NotificationCompat.DEFAULT_ALL); // звук, вибро и диодный индикатор выставляются по умолчанию

                    int notificationId = 159;
                    notificationManager.notify(notificationId,builder.build());

                }, String.class,String.class);
            }





            else if(sharedPref.getStringSet("roles").contains("User")){
                // Когда waiter взял заказ user-a
                hubConnection.on("ListenToAccept", (username, messageOrder) -> {
                    Log.d(TAG,"Waiter Accept Message: " + messageOrder);

                    Intent intent = new Intent(getBaseContext(), com.login.mobi.loginapp.views.client.menu.orders.OrdersPage.class); //by Grant
                    intent.putExtra("orderJson", messageOrder);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        NotificationChannel notificationChannel = new NotificationChannel(CHANEL_ID,SignalRService.class.getSimpleName(), NotificationManager.IMPORTANCE_DEFAULT);
                        notificationChannel.enableLights(true);
                        notificationChannel.setLightColor(Color.GREEN);
                        notificationChannel.setShowBadge(true);
                        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                        notificationManager.createNotificationChannel(notificationChannel);
                    }

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext(), CHANEL_ID);
                    builder.setSmallIcon(R.drawable.icon_food)
                            .setContentTitle("Официант взял Ваш заказ!")
                            .setContentText(messageOrder)
                            .setTicker("Официант взял Ваш заказ!") //текст, который отобразится вверху статус-бара при создании уведомления
                            .setContentIntent(pendingIntent)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon_food))
                            .setDefaults(NotificationCompat.DEFAULT_ALL); // звук, вибро и диодный индикатор выставляются по умолчанию

                    int notificationId = 159;
                    notificationManager.notify(notificationId,builder.build());

                }, String.class,String.class);

                // TODO Когда admin подтвердил бронирование
                hubConnection.on("ListenToConfirmReserve", () -> {
                    String message = "Ваше бронирование подтвердили!";

                    Log.d(TAG,"Accept Message: " + message);

                    Intent intent = new Intent(getBaseContext(), com.login.mobi.loginapp.views.client.menu.orders.OrdersPage.class); //by Grant
                    intent.putExtra("orderJson", message);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        NotificationChannel notificationChannel = new NotificationChannel(CHANEL_ID,SignalRService.class.getSimpleName(), NotificationManager.IMPORTANCE_DEFAULT);
                        notificationChannel.enableLights(true);
                        notificationChannel.setLightColor(Color.GREEN);
                        notificationChannel.setShowBadge(true);
                        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                        notificationManager.createNotificationChannel(notificationChannel);
                    }

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext(), CHANEL_ID);
                    builder.setSmallIcon(R.drawable.icon_table)
                            .setContentTitle("Бронирование")
                            .setContentText(message)
                            .setTicker("Ваше бронирование подтвердили!") //текст, который отобразится вверху статус-бара при создании уведомления
                            .setContentIntent(pendingIntent)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon_table))
                            .setDefaults(NotificationCompat.DEFAULT_ALL); // звук, вибро и диодный индикатор выставляются по умолчанию

                    int notificationId = 159;
                    notificationManager.notify(notificationId,builder.build());

                });

                // TODO Когда admin отклонил бронирование
                hubConnection.on("ListenToRejectReserve", () -> {
                    String message = "Ваше бронирование, к сожалению, отклонили :(";
                    Log.d(TAG,"Reject Message: " + message);

                    Intent intent = new Intent(getBaseContext(), com.login.mobi.loginapp.views.client.menu.orders.OrdersPage.class); //by Grant
                    intent.putExtra("orderJson", message);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        NotificationChannel notificationChannel = new NotificationChannel(CHANEL_ID,SignalRService.class.getSimpleName(), NotificationManager.IMPORTANCE_DEFAULT);
                        notificationChannel.enableLights(true);
                        notificationChannel.setLightColor(Color.GREEN);
                        notificationChannel.setShowBadge(true);
                        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                        notificationManager.createNotificationChannel(notificationChannel);
                    }

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext(), CHANEL_ID);
                    builder.setSmallIcon(R.drawable.icon_table)
                            .setContentTitle("Бронирование")
                            .setContentText(message)
                            .setTicker("Ваше бронирование, к сожалению, отклонили :(") //текст, который отобразится вверху статус-бара при создании уведомления
                            .setContentIntent(pendingIntent)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon_table))
                            .setDefaults(NotificationCompat.DEFAULT_ALL); // звук, вибро и диодный индикатор выставляются по умолчанию

                    int notificationId = 159;
                    notificationManager.notify(notificationId,builder.build());

                });
            }






            // TODO Когда user сделал бронирование
            else if(sharedPref.getStringSet("roles").contains("Admin")){
                // TODO Когда admin подтвердил бронирование
                hubConnection.on("ListenToBookingTable", () -> {
                    String message = "В ресторане сделали новое бронирование!";

                    Log.d(TAG,"AcceptMess: " + message);

                    Intent intent = new Intent(getBaseContext(), com.login.mobi.loginapp.views.client.menu.orders.OrdersPage.class); //by Grant
                    intent.putExtra("orderJson", message);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        NotificationChannel notificationChannel = new NotificationChannel(CHANEL_ID,SignalRService.class.getSimpleName(), NotificationManager.IMPORTANCE_DEFAULT);
                        notificationChannel.enableLights(true);
                        notificationChannel.setLightColor(Color.GREEN);
                        notificationChannel.setShowBadge(true);
                        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                        notificationManager.createNotificationChannel(notificationChannel);
                    }

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext(), CHANEL_ID);
                    builder.setSmallIcon(R.drawable.icon_table)
                            .setContentTitle("Бронирование!")
                            .setContentText(message)
                            .setTicker("В ресторане сделали новое бронирование!") //текст, который отобразится вверху статус-бара при создании уведомления
                            .setContentIntent(pendingIntent)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon_table))
                            .setDefaults(NotificationCompat.DEFAULT_ALL); // звук, вибро и диодный индикатор выставляются по умолчанию

                    int notificationId = 159;
                    notificationManager.notify(notificationId,builder.build());

                });
            }



            try {
                hubConnection.start().doFinally(() -> {
                    Log.d(TAG,"isConnected>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    isConnectRunnableRunning=false;
                }).blockingAwait();
            } catch (RuntimeException e){
                if(DEBUG){
                    Log.d(TAG,"can not connect to network: " + e.getMessage());
                }
            }

            //hubConnection.send("MakeOrder","Test from android");
        }
    }

    private class DisconnectRunnable implements Runnable {
        @Override
        public void run() {
            if (hubConnection != null && hubConnection.getConnectionState() == HubConnectionState.CONNECTED){
                hubConnection.stop();
            }
        }
    }


}
