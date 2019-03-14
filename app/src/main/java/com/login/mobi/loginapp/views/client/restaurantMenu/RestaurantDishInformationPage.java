package com.login.mobi.loginapp.views.client.restaurantMenu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.restaurantMenu.RestaurantDishes;


public class RestaurantDishInformationPage extends AppCompatActivity {
    // xml elements: TextView
    TextView dishName, dishType, dishComposition;
    ImageView dishPhoto;

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
        dishType = (TextView) findViewById(R.id.dish_type);
        dishComposition = (TextView) findViewById(R.id.dish_composition);
        dishPhoto = (ImageView) findViewById(R.id.dish_photo);

        /* Получение данных блюда с RestaurantDishesAdapter */
        Intent intent = getIntent();
        jsonData = intent.getStringExtra("DishInformation");
        RestaurantDishes dish = new Gson().fromJson(jsonData, RestaurantDishes.class);
        Log.d("DishInformation", jsonData);


        dishName.setText(dish.getNameOfDish());
        dishType.setText(dish.getTitle());
        dishComposition.setText(dish.getComposition());
        String filePath = "http://5.23.55.101/Files/menu/" + dish.getFileName();  // berikkadan.kz домен просрочен
        Glide.with(this).load(filePath).into(dishPhoto);
        //Picasso.get().load(filePath).into(dishPhoto);
    }
}
