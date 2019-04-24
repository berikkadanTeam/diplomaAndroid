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
import com.login.mobi.loginapp.network.model.personnel.Personnel;
import com.login.mobi.loginapp.network.requests.personnel.GetPersonnel;
import com.login.mobi.loginapp.singleton.SingletonSharedPref;

import java.util.ArrayList;
import java.util.List;


public class MyRestaurantPersonnelPage extends AppCompatActivity implements GetPersonnel.GetPersonnelInterface{

    public MyRestaurantPersonnelPage() { }

    // xml elements: recyclerView, buttons
    private TextView pageTitle, bookingsMainTextView;
    private EditText searchEditText;
    private RecyclerView rv;

    // variables
    private MyRestaurantPersonnelAdapter adapter;
    private List<Personnel> list = new ArrayList<>();

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
        setContentView(R.layout.admin_menu_personnel);
        parentLayout = findViewById(android.R.id.content);


        progressDialog = new ProgressDialog(this, R.style.ProgressDialogInCenter);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);
        progressDialog.show();


        /* My bookings RecyclerView*/
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRestaurantPersonnelAdapter(this, list);
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
                MyRestaurantPersonnelPage.this.searchFunc(searchQuery);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        sharedPref = SingletonSharedPref.getInstance(this);
        restaurantID = sharedPref.getString(SingletonSharedPref.RESTAURANT_ID);
        token = sharedPref.getString(SingletonSharedPref.TOKEN);
        GetPersonnel getPersonnel = new GetPersonnel(this, "Bearer " + token, restaurantID);
        getPersonnel.getPersonnel();

    }


    /* Search booking by booking number */
    public void searchFunc(String text) {
        ArrayList<Personnel> founded = new ArrayList<>();
        for (Personnel s : list) {
            String surnameAndName = s.getFirstName() + s.getLastName();
            int i = surnameAndName.toLowerCase().indexOf(text.toLowerCase());
            if (i >= 0) {
                founded.add(s);
            }
        }
        adapter.arrayChanged(founded);
    }


    @Override
    public void getPersonnel(List<Personnel> response) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (response != null && response.size()>0) {
            Log.d("MyRestaurantPersonnel", response.toString() + " ");
            list = response;
            adapter.arrayChanged(list);
        }
        else if (response == null || response.isEmpty() || response.size() == 0){
            bookingsMainTextView.setText("У ресторана нет персонала");
            //Snackbar.make(parentLayout, "У Вас нет бронирований", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }
}