package com.login.mobi.loginapp.views.client.menu.bookings;

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
import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.ServerResponse;
import com.login.mobi.loginapp.network.model.booking.MyBookings;
import com.login.mobi.loginapp.network.model.booking.MyBookingsMenu;
import com.login.mobi.loginapp.network.requests.booking.DeleteMyBooking;
import com.login.mobi.loginapp.singleton.SingletonSharedPref;

import java.util.ArrayList;
import java.util.List;

public class BookingDetailsPage extends AppCompatActivity implements DeleteMyBooking.DeleteMyBookingInterface{

    // xml elements: recyclerView, buttons
    private TextView restaurantName, date, time, numberOfGuests, preferences, preorder, surname, name, email, phone;
    private LinearLayout deleteBookingBtn;
    private RecyclerView rv;

    // variables
    String jsonData;
    private BookingDetailsPreorderDishesAdapter adapter;
    private List<MyBookingsMenu> list = new ArrayList<>();
    String bookingId;

    // Snackbar
    View parentLayout;

    // Shared Preferences
    SingletonSharedPref sharedPref;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_booking_details);
        parentLayout = findViewById(android.R.id.content);

        sharedPref = SingletonSharedPref.getInstance(this);

        /* Получение данных ресторана с фрагмента Restaurant */
        Intent intent = getIntent();
        jsonData = intent.getStringExtra("BookingData");
        MyBookings booking = new Gson().fromJson(jsonData, MyBookings.class);
        Log.d("BookingData", jsonData);

        restaurantName = (TextView) findViewById(R.id.restaurant_name);
        restaurantName.setText(booking.getName());
        date = (TextView) findViewById(R.id.picked_date_textview);
        date.setText(booking.getDate());
        time = (TextView) findViewById(R.id.picked_time_textview);
        time.setText(booking.getTime());
        numberOfGuests = (TextView) findViewById(R.id.guests_count);
        numberOfGuests.setText(booking.getCountPerson() + " -> ПОЗЖЕ НАДО ИЗМЕНИТЬ");
        preferences = (TextView) findViewById(R.id.preferences);
        if (booking.getComments() != null || !booking.getComments().isEmpty() || booking.getComments().length() != 0) {
            preferences.setText(booking.getComments());
        } else {
            preferences.setText("Отсутствуют");
        }
        preorder = (TextView) findViewById(R.id.has_preorder_or_not);
        if (booking.getMenu() != null || !booking.getMenu().isEmpty()) {
            preorder.setText("Есть");
            rv = findViewById(R.id.rv);
            rv.setLayoutManager(new LinearLayoutManager(this));
            list = booking.getMenu();
            adapter = new BookingDetailsPreorderDishesAdapter(this, list);
            rv.setAdapter(adapter);
            rv.setItemAnimator(new DefaultItemAnimator());
        } else {
            preorder.setText("Отсутствует");
        }


        surname = (TextView) findViewById(R.id.input_surname);
        //surname.setText((CharSequence) booking.getFirstName().toString());
        surname.setText("ПОКА ТАКОЙ ИНФЫ НЕТ");
        name = (TextView) findViewById(R.id.input_name);
        //name.setText(booking.getLastName());
        name.setText("ПОКА ТАКОЙ ИНФЫ НЕТ");
        email = (TextView) findViewById(R.id.input_email);
        email.setText("ПОКА ТАКОЙ ИНФЫ НЕТ");
        phone = (TextView) findViewById(R.id.input_phone);
        phone.setText(booking.getNumber());

        deleteBookingBtn = (LinearLayout) findViewById(R.id.delete_booking);
        deleteBookingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar.make(parentLayout, "DELETE BOOKING BUTTON", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                token = sharedPref.getString(SingletonSharedPref.TOKEN);
                bookingId = booking.getId();
                DeleteMyBooking deleteMyBooking = new DeleteMyBooking(BookingDetailsPage.this, "Bearer " + token, bookingId);
                deleteMyBooking.deleteBooking();
            }
        });
    }

    @Override
    public void getDeleteBookingResponse(ServerResponse response, int code) {
        if (response != null){
            Log.d("DeleteBooking", response.toString());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Snackbar.make(parentLayout, response.getStatus(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        Thread.sleep(3000);
                        runOnUiThread(new Runnable(){
                            public void run() {
                                BookingDetailsPage.this.onBackPressed();
                            }
                        });

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
    }
}
