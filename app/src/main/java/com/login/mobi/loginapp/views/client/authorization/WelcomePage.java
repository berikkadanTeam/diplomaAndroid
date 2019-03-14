package com.login.mobi.loginapp.views.client.authorization;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.singleton.SingletonSharedPref;
import com.login.mobi.loginapp.views.admin.MainMenuActivity;
import com.login.mobi.loginapp.views.client.bottomNavigation.BottomNavigationPage;


public class WelcomePage extends AppCompatActivity {

    // xml elements: textView, buttons
    private TextView btnSignIn;
    private Button btnSignUp;
    private TextView welcomeText;

    // Shared Preferences
    SingletonSharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);

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
                case "Waiter": {  // TODO на самом деле это для клиента!!!
                    startActivity(new Intent(WelcomePage.this, BottomNavigationPage.class));
                    finish();
                    break;
                }
            }
        }
//        else {
//            startActivity(new Intent(WelcomePage.this, SignInPage.class));
//        }

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(WelcomePage.this, SignUpPage.class));
            }
        });



    }

    public void openSignInPage(View v){
        startActivity(new Intent(WelcomePage.this, SignInPage.class));
        finish();
    }


}
