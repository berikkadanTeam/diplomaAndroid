package com.login.mobi.loginapp.views.restaurants;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.restaurants.Restaurant;
import com.login.mobi.loginapp.singleton.SingletonSharedPref;
import com.login.mobi.loginapp.views.restaurantMenu.RestaurantDishTypesPage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RestaurantTableBookingPage extends AppCompatActivity {

    // variables
    private String selectedTableID;
    private String userID;
    private int numberOfGuests = 2;
    private String jsonData;

    // Singleton
    SingletonSharedPref sharedPref;

    // xml elements: texts, buttons
    private TextView numberOfGuestsTextView, pickedDateTextView, pickedTimeTextView, restaurantName, averageCheck, delivery, seats, description;
    private CardView plusGuest, minusGuest;
    private Button pickDateButton;
    RadioButton openMenuYes, openMenuNo;
    RadioGroup radio;

    // Calendar: Date Picker from https://www.codingdemos.com/android-datepicker-button/
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat sdf;
    private int year, month, dayOfMonth;
    private Calendar calendar;

    // Time: Number Picker from: http://pestohacks.blogspot.com/2015/04/number-money-picker-dialog-in-android.html
    private Button timePickerDialogButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_table_booking_page);

        /* Получение данных ресторана с RestaurantWebViewPage */
        Intent intent = getIntent();
        selectedTableID = intent.getStringExtra("TableID");
        jsonData = intent.getStringExtra("RestaurantData");
        final Restaurant restaurant = new Gson().fromJson(jsonData, Restaurant.class);
        Log.d("SelectedTableID", selectedTableID);
        //Toast.makeText(getApplicationContext(), "selectedtableID: " + selectedtableID, Toast.LENGTH_SHORT).show();



        restaurantName = (TextView) findViewById(R.id.restaurant_name);
        restaurantName.setText(restaurant.getName());

        /* Select Number of Guests */
        CardView minusGuest = (CardView) findViewById(R.id.minus_guest);
        CardView plusGuest = (CardView) findViewById(R.id.plus_guest);
        numberOfGuestsTextView = (TextView) findViewById(R.id.guests_count);
        plusGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfGuests < 10) { // TODO: Change max count
                    numberOfGuests++;
                    numberOfGuestsTextView.setText(String.valueOf(numberOfGuests));

                }
            }
        });
        minusGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfGuests != 1) {
                    numberOfGuests--;
                    numberOfGuestsTextView.setText(String.valueOf(numberOfGuests));
                }
                if (numberOfGuests == 1){
                    numberOfGuestsTextView.setText(String.valueOf(numberOfGuests));
                }

            }
        });

        /* Select Date */
        pickDateButton = (Button) findViewById(R.id.pick_date_button);
        pickedDateTextView = (TextView) findViewById(R.id.picked_date_textview);
        Date c = Calendar.getInstance().getTime();
        sdf = new SimpleDateFormat("dd.MM.yyyy");
        String today = sdf.format(c);
        pickedDateTextView.setText(today);
        pickDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(RestaurantTableBookingPage.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                //pickedDateTextView.setText(day + "." + (month + 1) + "." + year);
                                String dateToSdf, monthToSdf;
                                if(day < 10){
                                    dateToSdf = "0" + day;
                                }
                                else
                                    dateToSdf = String.valueOf(day);

                                if((month+1) < 10){
                                    monthToSdf = "0" + (month+1);
                                }
                                else
                                    monthToSdf = String.valueOf(month+1);
                                pickedDateTextView.setText(dateToSdf + "." + monthToSdf + "." + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });



        /* Select Time */
        timePickerDialogButton = (Button)findViewById(R.id.pick_time_button);
        pickedTimeTextView = (TextView) findViewById(R.id.picked_time_textview);
        timePickerDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RestaurantTableBookingPage.this);
                LayoutInflater inflater = RestaurantTableBookingPage.this.getLayoutInflater();
                View theView = inflater.inflate(R.layout.restaurant_number_picker_dialog, null);

                final NumberPicker hour = (NumberPicker) theView.findViewById(R.id.euro_picker);
                final NumberPicker minute = (NumberPicker) theView.findViewById(R.id.cent_picker);
                builder.setView(theView)
                        .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (hour.getValue() < 10 && minute.getValue() >= 10)            // if hour < 10
                                    pickedTimeTextView.setText("0" + hour.getValue() + ":" + minute.getValue());
                                else if (minute.getValue() < 10 && hour.getValue() >= 10)       // if minute < 10
                                    pickedTimeTextView.setText(hour.getValue() + ":0" + minute.getValue());
                                else if (hour.getValue() < 10 && minute.getValue() < 10)        // if both < 10
                                    pickedTimeTextView.setText("0" + hour.getValue() + ":0" + minute.getValue());
                                else if (hour.getValue() >= 10 && minute.getValue() >= 10)      // if both >= 10
                                    pickedTimeTextView.setText(hour.getValue() + ":" + minute.getValue());
                            }
                        })
                        .setNegativeButton("ОТМЕНА", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) { }
                        });

                String[] stringArrayForHours = new String[24];
                for(int i=0; i<24; i++){
                    if (i<10)
                        stringArrayForHours[i] = "0" + Integer.toString(i);
                    else
                        stringArrayForHours[i] = Integer.toString(i);
                }
                hour.setMaxValue(stringArrayForHours.length-1);
                hour.setMinValue(Integer.valueOf(stringArrayForHours[0]));
                hour.setDisplayedValues(stringArrayForHours);


                String[] stringArrayForMinutes = new String[60];
                for(int i=0; i<60; i++){
                    if (i<10)
                        stringArrayForMinutes[i] = "0" + Integer.toString(i);
                    else
                        stringArrayForMinutes[i] = Integer.toString(i);
                }
                minute.setMaxValue(stringArrayForMinutes.length-1);
                minute.setMinValue(Integer.valueOf(stringArrayForMinutes[0]));
                minute.setDisplayedValues(stringArrayForMinutes);

                builder.show();
                }
        });



        /* Select Open Menu or No */
        openMenuYes = (RadioButton) findViewById(R.id.open_menu_yes);
        openMenuNo = (RadioButton) findViewById(R.id.open_menu_no);
        radio = (RadioGroup) findViewById(R.id.menu_radio_group);
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View radioButton = radio.findViewById(checkedId);
                int index = radio.indexOfChild(radioButton);
                // Add logic here
                switch (index) {
                    case 0: // openMenuYes radio button
                        /* Отправка restaurantID на страницу RestaurantDishTypesPage */
                        Intent intent = new Intent(RestaurantTableBookingPage.this, RestaurantDishTypesPage.class);
                        intent.putExtra("RestaurantID", restaurant.getId());
                        startActivity(intent);
                        //startActivity(new Intent(RestaurantTableBookingPage.this, RestaurantDishTypesPage.class));
                        break;
                    case 1: // openMenuNo radio button
                        //Toast.makeText(getApplicationContext(), "Selected button number " + index, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });


        sharedPref = SingletonSharedPref.getInstance(this);
        userID = sharedPref.getString(SingletonSharedPref.USER_ID);
        Log.d("UserID", userID);
        if(userID != null){
            // TODO
        }

    }



}
