package com.login.mobi.loginapp.views.admin;

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
import com.login.mobi.loginapp.network.model.booking.MyBookings;
import com.login.mobi.loginapp.network.model.booking.MyBookingsMenu;
import com.login.mobi.loginapp.network.requests.booking.admin.ConfirmBooking;
import com.login.mobi.loginapp.network.requests.booking.admin.RejectBooking;
import com.login.mobi.loginapp.singleton.SingletonSharedPref;
import com.login.mobi.loginapp.views.client.menu.bookings.BookingDetailsPreorderDishesAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyRestaurantBookingDetailsPage extends AppCompatActivity implements ConfirmBooking.ConfirmBookingInterface, RejectBooking.RejectBookingInterface {

    // xml elements: recyclerView, buttons
    private TextView bookingNumber, tableNumber, date, time, numberOfGuests, preferences, preorder, bookingClientData, surname, name, email, phone;
    private CardView acceptRejectBtns;
    private LinearLayout acceptBookingBtn, declineBookingBtn;
    private RecyclerView rv;

    // variables
    String jsonData;
    private BookingDetailsPreorderDishesAdapter adapter;
    private List<MyBookingsMenu> list = new ArrayList<>();
    String bookingId;
    boolean isConfirmed;

    // Snackbar
    View parentLayout;

    // Shared Preferences
    SingletonSharedPref sharedPref;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_menu_booking_details);
        parentLayout = findViewById(android.R.id.content);

        sharedPref = SingletonSharedPref.getInstance(this);

        /* Получение данных ресторана с MyRestaurantBookingsPage */
        Intent intent = getIntent();
        jsonData = intent.getStringExtra("BookingData");
        MyBookings booking = new Gson().fromJson(jsonData, MyBookings.class);
        Log.d("BookingData", jsonData);

        bookingNumber = (TextView) findViewById(R.id.booking_current_page_text);
        bookingNumber.setText("Бронирование №" + Integer.toString(booking.getNumberOfBooking()));
        tableNumber = (TextView) findViewById(R.id.restaurant_name);
        tableNumber.setText("Стол №" + Integer.toString(booking.getNumberOfTable()));
        date = (TextView) findViewById(R.id.picked_date_textview);
        date.setText(booking.getGetDate());
        time = (TextView) findViewById(R.id.picked_time_textview);
        time.setText(booking.getTime());
        numberOfGuests = (TextView) findViewById(R.id.guests_count);
        numberOfGuests.setText(Integer.toString(booking.getNumberOfGuests()));
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

        bookingClientData = (TextView) findViewById(R.id.booking_client_data);
        bookingClientData.setText("Данные гостя");
        surname = (TextView) findViewById(R.id.input_surname);
        //surname.setText((CharSequence) booking.getFirstName().toString());
        surname.setText(booking.getLastName());
        name = (TextView) findViewById(R.id.input_name);
        //name.setText(booking.getLastName());
        name.setText(booking.getFirstName());
        email = (TextView) findViewById(R.id.input_email);
        email.setText(booking.getEmail());
        phone = (TextView) findViewById(R.id.input_phone);
        if (booking.getPhoneNumber() != null)
            phone.setText(booking.getPhoneNumber());
        else
            phone.setText("");

        acceptRejectBtns = (CardView) findViewById(R.id.accept_reject_buttons);
        acceptBookingBtn = (LinearLayout) findViewById(R.id.accept_booking_button);
        declineBookingBtn = (LinearLayout) findViewById(R.id.decline_booking_button);
        token = sharedPref.getString(SingletonSharedPref.TOKEN);
        bookingId = booking.getId();

        Log.d("SHOW/HIDE BUTTONS", "ROLE: " + sharedPref.getString(SingletonSharedPref.ROLE) + " STATUS: " + booking.getReserveStatus().toString());
        if (booking.getReserveStatus() == 3 && sharedPref.getString(SingletonSharedPref.ROLE).equals("Admin")) {
            declineBookingBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Snackbar.make(parentLayout, "DELETE BOOKING BUTTON", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    RejectBooking rejectBooking = new RejectBooking(MyRestaurantBookingDetailsPage.this, "Bearer " + token, bookingId);
                    rejectBooking.rejectBooking();
                }
            });


            acceptBookingBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConfirmBooking confirmBooking = new ConfirmBooking(MyRestaurantBookingDetailsPage.this, "Bearer " + token, bookingId);
                    confirmBooking.confirmBooking();
                }
            });
        } else {
            acceptRejectBtns.setVisibility(View.GONE);
        }
    }

    @Override
    public void getConfirmBookingResponse(ServerResponse response, int code) {
        if (response != null){
            Log.d("ConfirmBooking", response.toString());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Snackbar.make(parentLayout, response.getStatus(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        Thread.sleep(3000);
                        runOnUiThread(new Runnable(){
                            public void run() {
                                //MyRestaurantBookingDetailsPage.this.onBackPressed();
                                startActivity(new Intent(MyRestaurantBookingDetailsPage.this, MyRestaurantBookingsPage.class));
                                finish();
                            }
                        });

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
    }


    @Override
    public void getRejectBookingResponse(ServerResponse response, int code) {
        if (response != null){
            Log.d("RejectBooking", response.toString());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Snackbar.make(parentLayout, response.getStatus(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        Thread.sleep(3000);
                        runOnUiThread(new Runnable(){
                            public void run() {
                                //MyRestaurantBookingDetailsPage.this.onBackPressed();
                                startActivity(new Intent(MyRestaurantBookingDetailsPage.this, MyRestaurantBookingsPage.class));
                                finish();
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
