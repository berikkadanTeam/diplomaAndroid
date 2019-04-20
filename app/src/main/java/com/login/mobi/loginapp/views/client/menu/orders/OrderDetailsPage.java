package com.login.mobi.loginapp.views.client.menu.orders;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.booking.Menu;
import com.login.mobi.loginapp.network.model.booking.MyBookingsMenu;
import com.login.mobi.loginapp.singleton.SingletonSharedPref;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsPage extends AppCompatActivity{

    // xml elements: recyclerView, buttons
    private TextView bookingNumber, restaurantName, date, time, numberOfGuests, preferences, preorder, surname, name, email, phone;
    private RecyclerView rv;

    // variables
    String jsonData;
    private OrderDetailsOrderedDishesAdapter adapter;
    private List<MyBookingsMenu> list = new ArrayList<>();

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
        Menu menu = new Gson().fromJson(jsonData, Menu.class);
        Log.d("RECEIVED OrderData", jsonData);
//        Type collectionType = new TypeToken<List<Menu>>(){}.getType();
//        final List<Menu> lcs = (List<Menu>) new Gson().fromJson(jsonData, collectionType);
//        Log.d("OrderData", lcs.toString());

//        bookingNumber = (TextView) findViewById(R.id.booking_current_page_text);
//        bookingNumber.setText("Бронирование №" + booking.getNumberOfBooking() + " в");
//        restaurantName = (TextView) findViewById(R.id.restaurant_name);
//        restaurantName.setText(booking.getName());
//        date = (TextView) findViewById(R.id.picked_date_textview);
//        date.setText(booking.getGetDate());
//        time = (TextView) findViewById(R.id.picked_time_textview);
//        time.setText(booking.getTime());
//        numberOfGuests = (TextView) findViewById(R.id.guests_count);
//        numberOfGuests.setText(Integer.toString(booking.getNumberOfGuests()));
//        preferences = (TextView) findViewById(R.id.preferences);
//        if ( !booking.getComments().isEmpty() || booking.getComments().length() != 0) {
//            preferences.setText(booking.getComments());
//        } else {
//            preferences.setText("Отсутствуют");
//        }
//        preorder = (TextView) findViewById(R.id.has_preorder_or_not);
//        if (booking.getMenu().size() > 0 || booking.getMenu().isEmpty() == false) {    // booking.getMenu() != null из-за этого, когда menu пустой, выходило "Есть"
//            preorder.setText("Есть");
//            rv = findViewById(R.id.rv);
//            rv.setLayoutManager(new LinearLayoutManager(this));
//            list = booking.getMenu();
//            adapter = new OrderDetailsOrderedDishesAdapter(this, list);
//            rv.setAdapter(adapter);
//            rv.setItemAnimator(new DefaultItemAnimator());
//        } else {
//            preorder.setText("Нет");
//        }
//
//
//        surname = (TextView) findViewById(R.id.input_surname);
//        surname.setText("ПОКА ТАКОЙ ИНФЫ НЕТ");
//        name = (TextView) findViewById(R.id.input_name);
//        name.setText("ПОКА ТАКОЙ ИНФЫ НЕТ");
//        email = (TextView) findViewById(R.id.input_email);
//        email.setText("ПОКА ТАКОЙ ИНФЫ НЕТ");
//        phone = (TextView) findViewById(R.id.input_phone);
//        phone.setText(booking.getNumber());


    }


}
