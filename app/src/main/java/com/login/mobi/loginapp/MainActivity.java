package com.login.mobi.loginapp;

import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.login.mobi.loginapp.Database.UserDao;
import com.login.mobi.loginapp.Database.UserDatabase;
import com.login.mobi.loginapp.Models.User;

public class MainActivity extends AppCompatActivity {

    private Button btSignIn;
    private Button btSignUp;
    private EditText edtEmail;
    private EditText edtPassword;
    private UserDatabase database;

    private UserDao userDao;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Check User...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);


//        database = Room.databaseBuilder(this, UserDatabase.class, "mi-database.db")
//                .allowMainThreadQueries()
//                .fallbackToDestructiveMigration()
//                .build();

        userDao = ((MyApp) getApplicationContext()).database.getUserDao();


        btSignIn = findViewById(R.id.btSignIn);
        btSignUp = findViewById(R.id.btSignUp);

        edtEmail = findViewById(R.id.emailinput);
        edtPassword = findViewById(R.id.passwordinput);



        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });
        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!emptyValidation()) {
                    progressDialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            User user = userDao.getUser(edtEmail.getText().toString(), edtPassword.getText().toString());
                            if(user!=null){
                                Intent i = new Intent(MainActivity.this, HomeActivity.class);
                                SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);
                                pref.edit().putInt("userId", user.getId()).apply();
//                                i.putExtra("User", user);
                                startActivity(i);
                                finish();
                            }else{
                                Toast.makeText(MainActivity.this, "Unregistered user, or incorrect", Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        }
                    }, 1000);

                }else{
                    Toast.makeText(MainActivity.this, "Empty Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private boolean emptyValidation() {
        if (TextUtils.isEmpty(edtEmail.getText().toString()) || TextUtils.isEmpty(edtPassword.getText().toString())) {
            return true;
        }else {
            return false;
        }
    }
}
