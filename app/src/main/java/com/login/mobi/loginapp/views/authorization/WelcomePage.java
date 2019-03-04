package com.login.mobi.loginapp.views.authorization;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.restaurants.Restaurant;
import com.login.mobi.loginapp.network.requests.restaurants.GetRestaurants;

import java.util.List;


public class WelcomePage extends AppCompatActivity implements GetRestaurants.GetRestaurantsInterface{

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


        GetRestaurants getRestaurants = new GetRestaurants(this);
        getRestaurants.getRestaurants();

    }

    public void openSignInPage(View v){
        startActivity(new Intent(WelcomePage.this, SignInPage.class));
        finish();
    }

    @Override
    public void getRestaurants(List<Restaurant> response) {
        if(response != null){
            Toast.makeText(this,"" + response.size(), Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"NULL", Toast.LENGTH_LONG).show();
        }
    }


}
