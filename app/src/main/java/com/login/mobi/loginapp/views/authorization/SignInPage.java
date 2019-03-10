package com.login.mobi.loginapp.views.authorization;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.authorization.SignIn;
import com.login.mobi.loginapp.network.model.userInformation.UserInformation;
import com.login.mobi.loginapp.network.requests.authorization.PostSignIn;
import com.login.mobi.loginapp.network.requests.userInformation.GetUserInformation;
import com.login.mobi.loginapp.singleton.SingletonSharedPref;
import com.login.mobi.loginapp.views.bottomNavigation.BottomNavigationPage;

import static com.login.mobi.loginapp.singleton.SingletonSharedPref.Key.EXPIRES_IN;
import static com.login.mobi.loginapp.singleton.SingletonSharedPref.TOKEN;
import static com.login.mobi.loginapp.singleton.SingletonSharedPref.USER_ID;


public class SignInPage extends AppCompatActivity implements PostSignIn.PostSignInInterface, GetUserInformation.GetUserInformationInterface {

    // xml elements: texts, buttons
    private EditText email, password;
    private Button btnSignIn, btnCancel;

    private ProgressDialog progressDialog;
    private CoordinatorLayout coordinatorLayout;

    SingletonSharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_page);

        sharedPref = SingletonSharedPref.getInstance();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnCancel = findViewById(R.id.cancel);
        btnSignIn = findViewById(R.id.signIn);

        progressDialog = new ProgressDialog(this, R.style.ProgressDialogInCenter);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        //fab = (FloatingActionButton) findViewById(R.id.fab);

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
            sharedPref.put(TOKEN, response.getAuthToken());
            sharedPref.put(EXPIRES_IN, response.getExpiresIn());
            sharedPref.put(USER_ID, response.getId());
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            sharedPref = SingletonSharedPref.getInstance(this);
            GetUserInformation getUserInformation = new GetUserInformation(this, response.getId(), "Bearer " + response.getAuthToken());
            getUserInformation.getUserInformation(sharedPref.getmPref());

            startActivity(new Intent(SignInPage.this, BottomNavigationPage.class));
            finish();
        } else {
            //Toast.makeText(SignInPage.this, "Электронная почта и пароль не совпадают", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
//          Snackbar snackbar = Snackbar.make(coordinatorLayout, "Электронная почта и пароль не совпадают", Snackbar.LENGTH_LONG);
//          snackbar.show();
//          //snackbar.setText("Client's data is already exist on server or is invalid");
//          snackbar.setActionTextColor(Color.RED);

        }
    }

    @Override
    public void getUserInformation(UserInformation response) {
        Log.d("UserInformation", response.toString() + " ");
    }

    @Override
    public void errorMessage(String error) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG);
        snackbar.show();
        snackbar.setActionTextColor(Color.RED);
    }

    public void sendDataToSignIn(){
        PostSignIn postSignIn = new PostSignIn(this, email.getText().toString(), password.getText().toString());
        postSignIn.postSignIn();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, WelcomePage.class));
        finish();
    }


}
