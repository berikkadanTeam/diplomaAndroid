package com.login.mobi.loginapp.views.client.authorization;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.NetworkStateChangeReceiver;
import com.login.mobi.loginapp.singleton.SingletonSharedPref;
import com.login.mobi.loginapp.views.admin.MainMenuActivity;
import com.login.mobi.loginapp.views.client.bottomNavigation.BottomNavigationPage;
import com.login.mobi.loginapp.views.waiter.MainMenuActivityWaiter;

import static com.login.mobi.loginapp.network.NetworkStateChangeReceiver.IS_NETWORK_AVAILABLE;


public class WelcomePage extends AppCompatActivity {

    // xml elements: textView, buttons
    private TextView btnSignIn;
    private Button btnSignUp;
    private TextView welcomeText;

    // Shared Preferences
    SingletonSharedPref sharedPref;

    // Snackbar
    View parentLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);
        parentLayout = findViewById(android.R.id.content);

        IntentFilter intentFilter = new IntentFilter(NetworkStateChangeReceiver.NETWORK_AVAILABLE_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean isNetworkAvailable = intent.getBooleanExtra(IS_NETWORK_AVAILABLE, false);
                String networkStatus = isNetworkAvailable ? "connected" : "disconnected";
                Snackbar.make(parentLayout, "Network Status: " + networkStatus, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                //Snackbar.make(findViewById(R.id.activity_main), "Network Status: " + networkStatus, Snackbar.LENGTH_LONG).show();
            }
        }, intentFilter);


        btnSignIn = (TextView) findViewById(R.id.signIn);
        btnSignUp = (Button) findViewById(R.id.startButton);
        welcomeText = (TextView) findViewById(R.id.welcomeText);

        sharedPref = SingletonSharedPref.getInstance(this);
        if (sharedPref.getString(SingletonSharedPref.TOKEN) != null){
            switch (sharedPref.getString(SingletonSharedPref.ROLE)) {
                case "Admin": {
                    startActivity(new Intent(WelcomePage.this, MainMenuActivity.class));
                    finish();
                    break;
                }
                case "User": {
                    startActivity(new Intent(WelcomePage.this, BottomNavigationPage.class));
                    finish();
                    break;
                }
                case "Waiter": {
                    startActivity(new Intent(WelcomePage.this, MainMenuActivityWaiter.class));
                    finish();
                    break;
                }
                // TODO добавить роль официанта case "Kitchen staff"
            }
        }
//        else {
//            startActivity(new Intent(WelcomePage.this, SignInPage.class));
//        }

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(WelcomePage.this, SignUpPage.class));
            finish();
            }
        });

    }

    public void openSignInPage(View v){
        startActivity(new Intent(WelcomePage.this, SignInPage.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
