package com.login.mobi.loginapp.views.client.restaurantMenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.booking.Menu;
import com.login.mobi.loginapp.network.model.restaurantMenu.RestaurantDishes;
import com.login.mobi.loginapp.views.client.Booking;
import com.login.mobi.loginapp.views.client.restaurants.RestaurantTableBookingPage;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class PreorderOrderedDishesPage extends AppCompatActivity{

    public PreorderOrderedDishesPage() { }

    // xml elements: editText, recyclerView, buttons
    private TextView totalSum;
    private RecyclerView rv;
    private LinearLayout preOrderBtn;

    // variables
    private PreorderOrderedDishesAdapter adapter;
    private List<RestaurantDishes> list = new ArrayList<>();
    int dishTypeID;
    private String restaurantID;
    private int amountOfDish = 1;
    String jsonData, jsonData2;
    List<RestaurantDishes> filteredDishes = new ArrayList<>();
    private int totalCheck;

    // Snackbar
    View parentLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_dishes_preorder);
        parentLayout = findViewById(android.R.id.content);


        /* Получение выбранных блюд с TabsMainActivity */
        Intent intent = getIntent();
        jsonData = intent.getStringExtra("ChosenDishesListForOrder");
//      jsonData2 = intent.getStringExtra("AllDishesList");
        jsonData2 = intent.getStringExtra("ChosenDishesListInformation");
        //List<Menu> menu = new Gson().fromJson(jsonData, Menu.class);
        Type collectionType = new TypeToken<List<Menu>>(){}.getType();
        final List<Menu> lcs = (List<Menu>) new Gson().fromJson(jsonData, collectionType);
        Log.d("ChosenDishesList", lcs.toString());

        Type collectionType2 = new TypeToken<List<RestaurantDishes>>(){}.getType();
        List<RestaurantDishes> dishes = (List<RestaurantDishes>) new Gson().fromJson(jsonData2, collectionType2);
//      Log.d("DishesList", dishes.toString());
        Log.d("ChosenDishesList", dishes.toString());

        if (lcs.size() == 0)
            Snackbar.make(parentLayout, "Вы не выбрали ничего для заказа", Snackbar.LENGTH_LONG).setAction("Action", null).show();

        for (int k=0; k<lcs.size(); k++) {
            for (int j=0; j<dishes.size(); j++){
                Log.d("filteredDishes1", filteredDishes.toString() + ": " + lcs.get(k).getId() + " vs. " + dishes.get(j).getId());
                if (lcs.get(k).getId().equals(dishes.get(j).getId())) {
                    filteredDishes.add(dishes.get(j));

                    if (lcs.get(k).getDishCount() > 1){
                        totalCheck += (dishes.get(j).getPrice()) * lcs.get(k).getDishCount();
                    }
                    else {
                        totalCheck += dishes.get(j).getPrice();
                    }
                    Log.d("filteredDishes2", filteredDishes.toString());
                }
            }
        }
        TextView totalPrice = (TextView) findViewById(R.id.total_price);
        totalPrice.setText("Итого: " + totalCheck + " ₸");

        /* Dish Types RecyclerView*/
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PreorderOrderedDishesAdapter(this, lcs, filteredDishes);
        rv.setAdapter(adapter);
        rv.setItemAnimator(new DefaultItemAnimator());

        totalSum = (TextView) findViewById(R.id.total_price);

        // preOrder button
        preOrderBtn = findViewById(R.id.make_preorder);
        preOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar.make(parentLayout, "PREORDER", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Booking.preorder = lcs;
                startActivity(new Intent(PreorderOrderedDishesPage.this, RestaurantTableBookingPage.class));
                PreorderOrderedDishesPage.this.finish();
            }
        });


    }








}