package com.login.mobi.loginapp.views.waiter.orders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.ServerResponse;
import com.login.mobi.loginapp.network.model.booking.MyBookingsMenu;
import com.login.mobi.loginapp.network.model.order.MyOrders;
import com.login.mobi.loginapp.network.requests.order.waiter.AcceptOrder;
import com.login.mobi.loginapp.singleton.SingletonSharedPref;
import com.login.mobi.loginapp.views.client.menu.orders.OrderDetailsOrderedDishesAdapter;

import java.util.ArrayList;
import java.util.List;


public class OrderedDishesPage extends AppCompatActivity implements AcceptOrder.AcceptOrderInterface{

    public OrderedDishesPage() { }

    // xml elements: editText, recyclerView, buttons
    private TextView total;
    private RecyclerView rv;
    private LinearLayout takeOrderBtn;
    private CardView makePreorderBtn, takeOrderCardView;

    // variables
    private OrderDetailsOrderedDishesAdapter adapter;
    private List<MyBookingsMenu> list = new ArrayList<>();
    String jsonData;
    private int totalSum = 0;

    // Snackbar
    View parentLayout;

    // Singleton
    SingletonSharedPref sharedPref;
    private String token, orderID, waiterID;


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
        takeOrderCardView = (CardView) findViewById(R.id.take_order_waiter_cardview);
        takeOrderCardView.setVisibility(View.VISIBLE);


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

        token = sharedPref.getString(SingletonSharedPref.TOKEN);
        waiterID = sharedPref.getString(SingletonSharedPref.USER_ID);
        takeOrderBtn = (LinearLayout) findViewById(R.id.take_order_waiter);
        // TODO if waiter == null, display this button; if not don't display
        takeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar.make(parentLayout, "Взять заказ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                orderID = order.getId();
                AcceptOrder acceptOrder = new AcceptOrder(OrderedDishesPage.this, "Bearer " + token, orderID, waiterID);
                acceptOrder.acceptOrder();
            }
        });



    }

    @Override
    public void getAcceptOrderResponse(ServerResponse response, int code) {
        if (code == 200){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OrderedDishesPage.this);
            alertDialogBuilder.setIcon(R.drawable.icon_accept);
            alertDialogBuilder.setTitle(response.getStatus());
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(OrderedDishesPage.this, com.login.mobi.loginapp.views.waiter.orders.OrdersPage.class);
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        else
            Snackbar.make(parentLayout, "Ошибка...", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
}