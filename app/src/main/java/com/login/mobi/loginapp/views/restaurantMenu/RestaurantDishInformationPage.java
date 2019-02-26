package com.login.mobi.loginapp.views.restaurantMenu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.restaurantMenu.RestaurantDishes;


public class RestaurantDishInformationPage extends AppCompatActivity {
    // xml elements: TextView
    TextView dishName, dishComposition;

    // variables
    String jsonData;

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


        /* Получение данных блюда с RestaurantDishesAdapter */
        Intent intent = getIntent();
        jsonData = intent.getStringExtra("DishInformation");
        RestaurantDishes dish = new Gson().fromJson(jsonData, RestaurantDishes.class);
        Log.d("DishInformation", jsonData);


        dishName.setText(dish.getNameOfDish());
        dishComposition.setText(dish.getComposition());
    }
}
