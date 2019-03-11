package com.login.mobi.loginapp.views.menu.bookings;

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
import com.login.mobi.loginapp.network.model.booking.MyBookings;
import com.login.mobi.loginapp.network.requests.booking.GetMyBookings;
import com.login.mobi.loginapp.singleton.SingletonSharedPref;

import java.util.ArrayList;
import java.util.List;


public class BookingsPage extends AppCompatActivity implements GetMyBookings.GetMyBookingsInterface{

    public BookingsPage() { }

    // xml elements: recyclerView, buttons
    private TextView bookingsMainTextView;
    private EditText searchEditText;
    private RecyclerView rv;

    // variables
    private BookingsAdapter adapter;
    private List<MyBookings> list = new ArrayList<>();

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
        setContentView(R.layout.menu_bookings);
        parentLayout = findViewById(android.R.id.content);


        progressDialog = new ProgressDialog(this, R.style.ProgressDialogInCenter);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);
        progressDialog.show();


        /* My bookings RecyclerView*/
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BookingsAdapter(this, list);
        rv.setAdapter(adapter);
        rv.setItemAnimator(new DefaultItemAnimator());

        bookingsMainTextView = (TextView) findViewById(R.id.bookingsMainTextView);


        /* Search */
        searchEditText = (EditText) findViewById(R.id.search_edit_text);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchQuery = searchEditText.getText().toString();
                BookingsPage.this.searchFunc(searchQuery);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        sharedPref = SingletonSharedPref.getInstance(this);
        userID = sharedPref.getString(SingletonSharedPref.USER_ID);
        token = sharedPref.getString(SingletonSharedPref.TOKEN);
        GetMyBookings getMyBookings = new GetMyBookings(this, userID, "Bearer " + token);
        getMyBookings.getMyBookings();

    }


    /* Search booking by restaurant name */
    public void searchFunc(String text) {
        ArrayList<MyBookings> founded = new ArrayList<>();
        for (MyBookings s : list) {
            int i = s.getName().toLowerCase().indexOf(text.toLowerCase());
            if (i >= 0) {
                founded.add(s);
            }
        }
        adapter.arrayChanged(founded);
    }


    @Override
    public void getMyBookings(List<MyBookings> response) {
        Log.d("MyBookings", response.toString() + " ");
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (response != null) {
            list = response;
            adapter.arrayChanged(list);
        }
        else
            bookingsMainTextView.setText("У Вас нет бронирований");
            //Snackbar.make(parentLayout, "Нет блюд в данном разделе", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
}