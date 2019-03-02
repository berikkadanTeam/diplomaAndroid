package com.login.mobi.loginapp.views.restaurantMenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.restaurantMenu.RestaurantDishes;
import com.login.mobi.loginapp.views.restaurants.RestaurantTableBookingPage;

import java.util.ArrayList;
import java.util.List;


public class RestaurantDishesPreorderPage extends AppCompatActivity{

    public RestaurantDishesPreorderPage() { }

    // xml elements: editText, recyclerView, buttons
    private TextView totalSum;
    private RecyclerView rv;
    private LinearLayout preOrderBtn;

    // variables
    private RestaurantDishesAdapter adapter;
    private List<RestaurantDishes> list = new ArrayList<>();
    int dishTypeID;
    private String restaurantID;
    private int amountOfDish = 1;

    // Snackbar
    View parentLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_dishes_preorder);
        parentLayout = findViewById(android.R.id.content);




        /* Dish Types RecyclerView*/
        rv = findViewById(R.id.rv);
//        rv.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new RestaurantDishesAdapter(this, list, dishTypeID);
//        rv.setAdapter(adapter);
//        rv.setItemAnimator(new DefaultItemAnimator());

        totalSum = (TextView) findViewById(R.id.total_price);

        // preOrder button
        preOrderBtn = findViewById(R.id.make_preorder);
        preOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar.make(parentLayout, "PREORDER", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                startActivity(new Intent(RestaurantDishesPreorderPage.this, RestaurantTableBookingPage.class));
            }
        });


    }








}