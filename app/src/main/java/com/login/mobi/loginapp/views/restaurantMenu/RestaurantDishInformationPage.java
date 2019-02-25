package com.login.mobi.loginapp.views.restaurantMenu;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.login.mobi.loginapp.R;


public class RestaurantDishInformationPage extends AppCompatActivity {
    // xml elements: TextView
    TextView dishName, dishComposition;

    // Progress Dialog
    private ProgressDialog progressDialog;

    // Snackbar
    View parentLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_dish_information_page);
        parentLayout = findViewById(android.R.id.content);

        dishName = (TextView) findViewById(R.id.dish_name);
        dishComposition = (TextView) findViewById(R.id.dish_composition);

        dishName.setText("HELLO I WORK");
    }
}
