package com.login.mobi.loginapp.views.authorization;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.authorization.SignIn;
import com.login.mobi.loginapp.network.requests.authorization.PostSignIn;
import com.login.mobi.loginapp.singleton.SingletonSharedPref;
import com.login.mobi.loginapp.views.bottomNavigation.BottomNavigationPage;

import android.app.ProgressDialog;

import static com.login.mobi.loginapp.singleton.SingletonSharedPref.Key.EXPIRES_IN;
import static com.login.mobi.loginapp.singleton.SingletonSharedPref.TOKEN;


public class SignInPage extends AppCompatActivity implements PostSignIn.PostSignInInterface{

    private EditText email;
    private EditText password;

    private Button btnCancel;
    private Button btnSignIn;

    private ProgressDialog progressDialog;

    SingletonSharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPref = SingletonSharedPref.getInstance();

        setContentView(R.layout.sign_in_page);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnCancel = findViewById(R.id.cancel);
        btnSignIn = findViewById(R.id.signIn);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Check User...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInPage.this, WelcomePage.class));
                finish();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!emptyFieldsValidation()) {
                    progressDialog.show();
//                  startActivity(new Intent(SignInPage.this, BottomNavigationPage.class));
//                  finish();
                    sendDataToSignIn();
                }else {
                    Toast.makeText(SignInPage.this, "Заполните все поля", Toast.LENGTH_LONG).show();
                }
            }
        });




    }   // from onCreate()



    private boolean emptyFieldsValidation(){
        if (TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(password.getText().toString())) {
            return true;
        }else {
            return false;
        }
    }


    @Override
    public void signIn(SignIn response, int code) {
        if(response != null && code == 200){
            Log.d("auth_token",response.getAuthToken());
            sharedPref.put(TOKEN,response.getAuthToken());
            sharedPref.put(EXPIRES_IN,response.getExpiresIn());
            startActivity(new Intent(SignInPage.this, BottomNavigationPage.class));
            finish();
        }else{
            Toast.makeText(SignInPage.this, "Электронная почта и пароль не совпадают", Toast.LENGTH_LONG).show();
        }
    }

    public void sendDataToSignIn(){
        PostSignIn postSignIn = new PostSignIn(this,email.getText().toString(),password.getText().toString());
        postSignIn.postSignIn();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, WelcomePage.class));
        finish();
    }
}
