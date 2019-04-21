package com.login.mobi.loginapp.views.admin;

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
import com.login.mobi.loginapp.network.model.booking.MyRestaurantBookings;
import com.login.mobi.loginapp.network.requests.booking.admin.GetMyRestaurantBookings;
import com.login.mobi.loginapp.singleton.SingletonSharedPref;

import java.util.ArrayList;
import java.util.List;


public class MyRestaurantBookingsPage extends AppCompatActivity implements GetMyRestaurantBookings.GetMyRestaurantBookingsInterface{

    public MyRestaurantBookingsPage() { }

    // xml elements: recyclerView, buttons
    private TextView pageTitle, bookingsMainTextView;
    private EditText searchEditText;
    private RecyclerView rv;

    // variables
    private MyRestaurantBookingsAdapter adapter;
    private List<MyRestaurantBookings> list = new ArrayList<>();

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
        setContentView(R.layout.menu_bookings);
        parentLayout = findViewById(android.R.id.content);


        progressDialog = new ProgressDialog(this, R.style.ProgressDialogInCenter);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);
        progressDialog.show();


        /* My bookings RecyclerView*/
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRestaurantBookingsAdapter(this, list);
        rv.setAdapter(adapter);
        rv.setItemAnimator(new DefaultItemAnimator());

        bookingsMainTextView = (TextView) findViewById(R.id.bookingsMainTextView);
        pageTitle = (TextView) findViewById(R.id.page_title);
        pageTitle.setText("Бронирования в ресторане");

        /* Search */
        searchEditText = (EditText) findViewById(R.id.search_edit_text);
        searchEditText.setHint("Поиск бронирования по номеру");
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchQuery = searchEditText.getText().toString();
                MyRestaurantBookingsPage.this.searchFunc(searchQuery);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        sharedPref = SingletonSharedPref.getInstance(this);
        restaurantID = sharedPref.getString(SingletonSharedPref.RESTAURANT_ID);
        token = sharedPref.getString(SingletonSharedPref.TOKEN);
        GetMyRestaurantBookings getMyRestaurantBookings = new GetMyRestaurantBookings(this, restaurantID, "Bearer " + token);
        getMyRestaurantBookings.getMyRestaurantBookings();

    }


    /* Search booking by booking number */
    public void searchFunc(String text) {
        ArrayList<MyRestaurantBookings> founded = new ArrayList<>();
        for (MyRestaurantBookings s : list) {
            int i = s.getNumberOfBooking().toString().toLowerCase().indexOf(text.toLowerCase());
            if (i >= 0) {
                founded.add(s);
            }
        }
        adapter.arrayChanged(founded);
    }


    @Override
    public void getMyRestaurantBookings(List<MyRestaurantBookings> response) {
        Log.d("MyRestaurantBookings", response.toString() + " ");
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (response != null && response.size()>0) {
            list = response;
            adapter.arrayChanged(list);
        }
        else if (response == null || response.isEmpty() || response.size() == 0){
            bookingsMainTextView.setText("В ресторане нет бронирований");
            //Snackbar.make(parentLayout, "У Вас нет бронирований", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }
}