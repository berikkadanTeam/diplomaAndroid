package com.login.mobi.loginapp.views.client.menu.orders;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.booking.MyBookingsMenu;
import com.login.mobi.loginapp.network.model.order.MyOrders;
import com.login.mobi.loginapp.singleton.SingletonSharedPref;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsPage extends AppCompatActivity{

    // xml elements: recyclerView, buttons
    private TextView total;
    private RecyclerView rv;
    private CardView makePreorderBtn;

    // variables
    String jsonData;
    private OrderDetailsOrderedDishesAdapter adapter;
    private List<MyBookingsMenu> list = new ArrayList<>();
    private int totalSum = 0;

    // Snackbar
    View parentLayout;

    // Shared Preferences
    SingletonSharedPref sharedPref;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_ordered_dishes);
        parentLayout = findViewById(android.R.id.content);

        sharedPref = SingletonSharedPref.getInstance(this);

        /* Получение данных ресторана с OrdersPage */
        Intent intent = getIntent();
        jsonData = intent.getStringExtra("OrderData");
        MyOrders order = new Gson().fromJson(jsonData, MyOrders.class);
        Log.d("OrderData", jsonData);

        makePreorderBtn = (CardView) findViewById(R.id.booking_info_complete_button);
        makePreorderBtn.setVisibility(View.GONE);



        if (order.getMenu().size() > 0 || order.getMenu().isEmpty() == false) {    // order.getMenu() != null из-за этого, когда menu пустой, выходило "Есть"
            rv = findViewById(R.id.rv);
            rv.setLayoutManager(new LinearLayoutManager(this));
            list = order.getMenu();
            adapter = new OrderDetailsOrderedDishesAdapter(this, list);
            rv.setAdapter(adapter);
            rv.setItemAnimator(new DefaultItemAnimator());
        }

        for (int i=0; i<list.size(); i++){
            totalSum += (list.get(i).getDishCount() * list.get(i).getPrice());
        }
        total = (TextView) findViewById(R.id.total_price);
        total.setText("Итого: " + Integer.toString(totalSum) + " тг.");



    }


}
