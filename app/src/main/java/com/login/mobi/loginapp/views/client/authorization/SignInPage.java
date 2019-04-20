package com.login.mobi.loginapp.views.client.authorization;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
import com.login.mobi.loginapp.views.admin.MainMenuActivity;
import com.login.mobi.loginapp.views.client.bottomNavigation.BottomNavigationPage;
import com.login.mobi.loginapp.views.kitchen.MainMenuActivityKitchen;
import com.login.mobi.loginapp.views.waiter.MainMenuActivityWaiter;

import static com.login.mobi.loginapp.singleton.SingletonSharedPref.Key.EXPIRES_IN;
import static com.login.mobi.loginapp.singleton.SingletonSharedPref.RESTAURANT_ID;
import static com.login.mobi.loginapp.singleton.SingletonSharedPref.TOKEN;
import static com.login.mobi.loginapp.singleton.SingletonSharedPref.USER_EMAIL;
import static com.login.mobi.loginapp.singleton.SingletonSharedPref.USER_ID;
import static com.login.mobi.loginapp.singleton.SingletonSharedPref.USER_NAME;
import static com.login.mobi.loginapp.singleton.SingletonSharedPref.USER_SURNAME;


public class SignInPage extends AppCompatActivity implements PostSignIn.PostSignInInterface, GetUserInformation.GetUserInformationInterface {

    // xml elements: texts, buttons
    private EditText email, password;
    private Button btnSignIn, btnCancel;

    private ProgressDialog progressDialog;
    private CoordinatorLayout coordinatorLayout;
    private String role;

    // Shared Preferences
    SingletonSharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_page);

        sharedPref = SingletonSharedPref.getInstance(this);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnCancel = findViewById(R.id.cancel);
        btnSignIn = findViewById(R.id.signIn);

        progressDialog = new ProgressDialog(this, R.style.ProgressDialogInCenter);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

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

//        if(sharedPref.getString(SingletonSharedPref.TOKEN) != null){
//            switch (sharedPref.getString(SingletonSharedPref.ROLE)) {
//                case "Admin": {
//                    startActivity(new Intent(SignInPage.this, MainMenuActivity.class));
//                    finish();
//                    break;
//                }
//                case "Waiter": {  // TODO на самом деле это для клиента!!!
//                    startActivity(new Intent(SignInPage.this, BottomNavigationPage.class));
//                    finish();
//                    break;
//                }
//            }
//        }

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

            sharedPref = SingletonSharedPref.getInstance(this);
            GetUserInformation getUserInformation = new GetUserInformation(this, response.getId(), "Bearer " + response.getAuthToken());
            getUserInformation.getUserInformation(sharedPref.getmPref());

//          startActivity(new Intent(SignInPage.this, BottomNavigationPage.class));
//          finish();
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
        sharedPref.put(USER_SURNAME, response.getLastName());
        sharedPref.put(USER_NAME, response.getFirstName());
        sharedPref.put(USER_EMAIL, response.getUserName());

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                role = sharedPref.getString(SingletonSharedPref.ROLE);
                Log.d("UserInformation ROLE", role + "");

                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                switch (role){
                    case "Admin": {
                        sharedPref.put(RESTAURANT_ID, response.getRestaurantId());
                        startActivity(new Intent(SignInPage.this, MainMenuActivity.class));
                        finish();
                        break;
                    }
                    case "User": {
                        startActivity(new Intent(SignInPage.this, BottomNavigationPage.class));
                        finish();
                        break;
                    }
                    case "Waiter": {
                        startActivity(new Intent(SignInPage.this, MainMenuActivityWaiter.class));
                        finish();
                        break;
                    }
                    case "KitchenStuff": {
                        startActivity(new Intent(SignInPage.this, MainMenuActivityKitchen.class));
                        finish();
                        break;
                    }

                }
            }
        }, 1500);

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
