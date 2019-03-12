package com.login.mobi.loginapp.views.menu.orders;

import android.app.ProgressDialog;
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

        ordersMainTextView = (TextView) findViewById(R.id.bookingsMainTextView);


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

    }


    /* Search booking by restaurant name */
    public void searchFunc(String text) {
        ArrayList<MyOrders> founded = new ArrayList<>();
        for (MyOrders s : list) {
            int i = s.getId().toLowerCase().indexOf(text.toLowerCase());
            if (i >= 0) {
                founded.add(s);
            }
        }
        adapter.arrayChanged(founded);
    }


    @Override
    public void getMyOrders(List<MyOrders> response) {
        Log.d("MyOrders", response.toString() + " ");
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (response != null) {
            list = response;
            adapter.arrayChanged(list);
        }
        else
            ordersMainTextView.setText("У Вас нет заказов");
        //Snackbar.make(parentLayout, "Нет блюд в данном разделе", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

}