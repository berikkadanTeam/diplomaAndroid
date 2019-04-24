package com.login.mobi.loginapp.views.waiter.orders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.order.MyOrders;
import com.login.mobi.loginapp.network.requests.order.waiter.GetRestaurantOrders;
import com.login.mobi.loginapp.singleton.SingletonSharedPref;

import java.util.ArrayList;
import java.util.List;


public class OrdersPage extends AppCompatActivity implements GetRestaurantOrders.GetRestaurantOrdersInterface{

    public OrdersPage() { }

    // xml elements: recyclerView, buttons
    private TextView ordersMainTextView, pageTitle;
    private EditText searchEditText;
    private RecyclerView rv;

    // variables
    private OrdersAdapter adapter;
    private List<MyOrders> list = new ArrayList<>();

    // Snackbar
    View parentLayout;

    // Progress Dialog
    private ProgressDialog progressDialog;

    // Shared Preferences
    SingletonSharedPref sharedPref;
    private String restaurantID, token;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_orders);
        parentLayout = findViewById(android.R.id.content);


        progressDialog = new ProgressDialog(this, R.style.ProgressDialogInCenter);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);
        progressDialog.show();


        /* My orders RecyclerView*/
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OrdersAdapter(this, list);
        rv.setAdapter(adapter);
        rv.setItemAnimator(new DefaultItemAnimator());

        ordersMainTextView = (TextView) findViewById(R.id.ordersMainTextView);
        pageTitle = (TextView) findViewById(R.id.page_title);
        pageTitle.setText("Заказы в ресторане");

        /* Search */
        searchEditText = (EditText) findViewById(R.id.search_edit_text);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchQuery = searchEditText.getText().toString();
                OrdersPage.this.searchFunc(searchQuery);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        sharedPref = SingletonSharedPref.getInstance(this);
        restaurantID = sharedPref.getString(SingletonSharedPref.RESTAURANT_ID);
        token = sharedPref.getString(SingletonSharedPref.TOKEN);
        GetRestaurantOrders getRestaurantOrders = new GetRestaurantOrders(this, restaurantID, "Bearer " + token);
        getRestaurantOrders.getRestaurantOrders();

        String orderJson=null;//by Grant
        if(getIntent()!=null){
            orderJson=getIntent().getStringExtra("orderJson");
        }
        Log.d("myLog","onCreate: " + orderJson);
        if(!TextUtils.isEmpty(orderJson)){
            MyOrders myOrders=new Gson().fromJson(orderJson,MyOrders.class);
            list.add(myOrders);
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {//by Grant
        super.onNewIntent(intent);
        String orderJson=intent.getStringExtra("orderJson");
        Log.d("myLog","onNewIntent: "+orderJson);

        MyOrders myOrders=new Gson().fromJson(orderJson,MyOrders.class);
        list.add(myOrders);
        adapter.notifyDataSetChanged();
    }


    /* Search booking by restaurant name */
    public void searchFunc(String text) {
        ArrayList<MyOrders> founded = new ArrayList<>();
        for (MyOrders s : list) {
            int i = s.getNumberOfOrder().toString().toLowerCase().indexOf(text.toLowerCase());
            if (i >= 0) {
                founded.add(s);
            }
        }
        adapter.arrayChanged(founded);
    }



    @Override
    public void getRestaurantOrders(List<MyOrders> response, int code) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (code == 200){         // response != null && response.size()>0) {
            Log.d("MyOrders", response.toString() + " ");
            list = response;
            adapter.arrayChanged(list);
        }
        else { // if (response == null || response.isEmpty() || response.size() == 0) {
            ordersMainTextView.setText("В ресторане нет заказов");
            //Snackbar.make(parentLayout, "У Вас нет заказов", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }
}