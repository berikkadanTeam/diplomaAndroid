package com.login.mobi.loginapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignInPage extends AppCompatActivity {

    private EditText email;
    private EditText password;

    private Button btnCancel;
    private Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_page);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnCancel = findViewById(R.id.cancel);
        btnSignIn = findViewById(R.id.signIn);


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInPage.this, WelcomePage.class));
                finish();
            }
        });
    }
}
