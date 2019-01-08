package com.login.mobi.loginapp;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.login.mobi.loginapp.Database.UserDatabase;

public class MyApp extends Application {

    UserDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();

        database = Room.databaseBuilder(getApplicationContext(),
                UserDatabase.class,
                "mi-database.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }
}
