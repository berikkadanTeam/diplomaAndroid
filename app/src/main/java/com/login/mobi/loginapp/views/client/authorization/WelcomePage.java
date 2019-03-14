package com.login.mobi.loginapp.views.client.authorization;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.login.mobi.loginapp.R;


public class WelcomePage extends AppCompatActivity { // implements GetRestaurants.GetRestaurantsInterface{

    private TextView btnSignIn;
    private Button btnSignUp;
    private TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);

        btnSignIn = (TextView) findViewById(R.id.signIn);
        btnSignUp = (Button) findViewById(R.id.startButton);
        welcomeText = (TextView) findViewById(R.id.welcomeText);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomePage.this, SignUpPage.class));
            }
        });


        /*
        GetRestaurants getRestaurants = new GetRestaurants(this);
        getRestaurants.getRestaurants();
        */
    }

    public void openSignInPage(View v){
        startActivity(new Intent(WelcomePage.this, SignInPage.class));
        finish();
    }

    /*
    @Override
    public void getRestaurants(List<Restaurant> response) {
        if(response != null){
            Toast.makeText(this,"" + response.size(), Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"NULL", Toast.LENGTH_LONG).show();
        }
    }
    */

}
