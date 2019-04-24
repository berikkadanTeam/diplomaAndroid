package com.login.mobi.loginapp.views.admin;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.login.mobi.loginapp.CommonActivity;
import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.singleton.SingletonSharedPref;


// from: https://camposha.info/source/android-recyclerview-ratingbarimages-text

public class MainMenuActivity extends CommonActivity {
    // xml elements: texts, buttons, recyclerView
    private TextView fullName, email;
    private RecyclerView rv;

    // Shared Preferences
    SingletonSharedPref sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        fullName = (TextView) findViewById(R.id.full_name);
        email = (TextView) findViewById(R.id.email);

        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new MenuAdapter(this, AddMenuItems.getSpaceships()));


        sharedPref = SingletonSharedPref.getInstance(this);
        fullName.setText(sharedPref.getString(SingletonSharedPref.USER_SURNAME) + " " + sharedPref.getString(SingletonSharedPref.USER_NAME) + "\n" + "Администратор");
        email.setText(sharedPref.getString(SingletonSharedPref.USER_EMAIL));

    }

}
