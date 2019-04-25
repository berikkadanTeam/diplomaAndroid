package com.login.mobi.loginapp.views.client.menu.orders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.order.MyOrders;
import com.login.mobi.loginapp.network.requests.order.GetMyOrders;
import com.login.mobi.loginapp.singleton.SingletonSharedPref;

import java.util.ArrayList;
import java.util.List;


public class OrdersPage extends AppCompatActivity implements GetMyOrders.GetMyOrdersInterface{

    public OrdersPage() { }

    // xml elements: recyclerView, buttons
    private TextView ordersMainTextView;
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
    private String userID, token;



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
        userID = sharedPref.getString(SingletonSharedPref.USER_ID);
        token = sharedPref.getString(SingletonSharedPref.TOKEN);
        GetMyOrders getMyOrders = new GetMyOrders(this, userID, "Bearer " + token);
        getMyOrders.getMyOrders();

//        String orderJson=null;//by Grant
//        if(getIntent()!=null){
//            orderJson=getIntent().getStringExtra("orderJson");
//        }
//        Log.d("myLog","onCreate: " + orderJson);
//        if(!TextUtils.isEmpty(orderJson)){
//            MyOrders myOrders=new Gson().fromJson(orderJson,MyOrders.class);
//            list.add(myOrders);
//            adapter.notifyDataSetChanged();
//        }

    }


    @Override
    protected void onNewIntent(Intent intent) {//by Grant
        super.onNewIntent(intent);
        String orderJson = intent.getStringExtra("orderJson");
        Log.d("myLog","onNewIntent: " + orderJson);

        MyOrders myOrders = new Gson().fromJson(orderJson, MyOrders.class);
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
    public void getMyOrders(List<MyOrders> response, int code) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (code == 200){   //if (response.size()>0 || response.isEmpty() == false) { //response != null){
            Log.d("MyOrders", response.toString() + " ");
            list = response;
            adapter.arrayChanged(list);
        }
        else { // if (response.isEmpty() || response.size() == 0) {   // было еще response == null ||
            Log.d("MyOrders", "Orders are empty");
            ordersMainTextView.setText("У Вас нет заказов");
            //Snackbar.make(parentLayout, "У Вас нет заказов", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }


//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(OrdersPage.this, BottomNavigationPage.class);
//        intent.putExtra("status", 1);
//        startActivity(intent);
//        finish();
//    }
}