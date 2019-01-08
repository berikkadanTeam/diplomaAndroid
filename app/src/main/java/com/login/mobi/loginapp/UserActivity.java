package com.login.mobi.loginapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.login.mobi.loginapp.Models.User;

public class UserActivity extends AppCompatActivity {
    private TextView tvUser;
    private TextView tvName;
    private TextView tvDesc;
    private TextView tvAddress;
    private TextView tvBooksNumber;
    private Toolbar toolbar;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        toolbar = findViewById(R.id.toolbar);
        tvName = findViewById(R.id.textView7);
        tvDesc = findViewById(R.id.textView8);
        tvAddress = findViewById(R.id.textView9);
        tvBooksNumber = findViewById(R.id.textView4);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tomato");
        tvName.setText("Tomato");
        tvDesc.setText("All meals are made of tomatoes");
        tvAddress.setText("Zhandosov 69");
        tvBooksNumber.setText("Booked: 420 times");
//        user = (User) getIntent().getSerializableExtra("User");
//
//        tvUser = findViewById(R.id.tvUser);
//
//        if (user != null) {
//            tvUser.setText("WELCOME "+user.getName() +" "+user.getLastName());
//        }
    }
}
