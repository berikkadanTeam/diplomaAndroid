package com.login.mobi.loginapp.views.client.restaurants;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.ServerResponse;
import com.login.mobi.loginapp.network.model.booking.TableBookingWithPreorder;
import com.login.mobi.loginapp.network.model.restaurants.Restaurant;
import com.login.mobi.loginapp.network.requests.booking.BookTable;
import com.login.mobi.loginapp.singleton.SingletonSharedPref;
import com.login.mobi.loginapp.views.client.Booking;
import com.login.mobi.loginapp.views.client.menu.bookings.BookingsPage;
import com.login.mobi.loginapp.views.client.restaurantMenu.PreorderTabsMainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RestaurantTableBookingPage extends AppCompatActivity implements BookTable.BookTableInterface {

    // variables
    private String selectedTableID;
    private String userID;
    private int numberOfGuests = 2;
    private String jsonData;

    // Singleton
    SingletonSharedPref sharedPref;

    // xml elements: texts, buttons
    private TextView numberOfGuestsTextView, pickedDateTextView, pickedTimeTextView, restaurantName, averageCheck, delivery, seats, description, surname, name, email, phone;
    private EditText preferences;
    private CardView plusGuest, minusGuest;
    private Button pickDateButton;
    RadioButton openMenuYes, openMenuNo;
    RadioGroup radio;
    private LinearLayout bookTableBtn;

    // Calendar: Date Picker from https://www.codingdemos.com/android-datepicker-button/
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat sdf;
    private int year, month, dayOfMonth;
    private Calendar calendar;

    // Time: Number Picker from: http://pestohacks.blogspot.com/2015/04/number-money-picker-dialog-in-android.html
    private Button timePickerDialogButton;

    // Snackbar
    View parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_table_booking_page);
        parentLayout = findViewById(android.R.id.content);

        sharedPref = SingletonSharedPref.getInstance(this);

        /* Получение данных ресторана с RestaurantWebViewPage */
        Intent intent = getIntent();
        //selectedTableID = intent.getStringExtra("TableID");
        //jsonData = intent.getStringExtra("RestaurantData");
        //final Restaurant restaurant = new Gson().fromJson(jsonData, Restaurant.class);
        selectedTableID = Booking.tableID;
        final Restaurant restaurant = Booking.restaurant;
        Log.d("SelectedTableID", selectedTableID);
        //Toast.makeText(getApplicationContext(), "selectedtableID: " + selectedtableID, Toast.LENGTH_SHORT).show();

        Log.d("FROM WEBVIEW", "cdeced" + restaurant.getName());
        Log.d("FROM ORDERED", "cdeced " + Booking.preorder.toString());

        restaurantName = (TextView) findViewById(R.id.restaurant_name);
        restaurantName.setText(restaurant.getName());


        /* Select Date */
        pickDateButton = (Button) findViewById(R.id.pick_date_button);
        pickedDateTextView = (TextView) findViewById(R.id.picked_date_textview);
        Date c = Calendar.getInstance().getTime();
        sdf = new SimpleDateFormat("dd.MM.yyyy");
        if (Booking.date == null){      //|| Booking.date.isEmpty()) {
            //sdf = new SimpleDateFormat("dd.MM.yyyy");
            String today = sdf.format(c);
            pickedDateTextView.setText(today);
            try {
                Date todayDate = sdf.parse(today);
                Booking.date = todayDate;
                Log.d("BOOKING.DATE NULL", todayDate.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //Booking.date = today;
        } else {
            Log.d("BOOKING.DATE NOT NULL", Booking.date.toString());
            pickedDateTextView.setText(sdf.format(Booking.date)); //Booking.date
        }
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
                                //Booking.date = pickedDateTextView.getText().toString();
                                try {
                                    Booking.date = sdf.parse(pickedDateTextView.getText().toString());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });



        /* Select Time */
        timePickerDialogButton = (Button)findViewById(R.id.pick_time_button);
        pickedTimeTextView = (TextView) findViewById(R.id.picked_time_textview);
        Calendar calendarTime = Calendar.getInstance();
        if (Booking.time == null || Booking.time.isEmpty()) {
            SimpleDateFormat sdfForTime = new SimpleDateFormat("HH:mm");
            String now = sdfForTime.format(calendarTime.getTime());
            pickedTimeTextView.setText(now);
            Booking.time = now;
        } else {
            pickedTimeTextView.setText(Booking.time);
        }
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
                                if (hour.getValue() < 10 && minute.getValue() >= 10) {          // if hour < 10
                                    pickedTimeTextView.setText("0" + hour.getValue() + ":" + minute.getValue());
                                    Booking.time = pickedTimeTextView.getText().toString();
                                }
                                else if (minute.getValue() < 10 && hour.getValue() >= 10) {      // if minute < 10
                                    pickedTimeTextView.setText(hour.getValue() + ":0" + minute.getValue());
                                    Booking.time = pickedTimeTextView.getText().toString();
                                }
                                else if (hour.getValue() < 10 && minute.getValue() < 10) {      // if both < 10
                                    pickedTimeTextView.setText("0" + hour.getValue() + ":0" + minute.getValue());
                                    Booking.time = pickedTimeTextView.getText().toString();
                                }
                                else if (hour.getValue() >= 10 && minute.getValue() >= 10) {    // if both >= 10
                                    pickedTimeTextView.setText(hour.getValue() + ":" + minute.getValue());
                                    Booking.time = pickedTimeTextView.getText().toString();
                                }

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


        /* Select Number of Guests */
        CardView minusGuest = (CardView) findViewById(R.id.minus_guest);
        CardView plusGuest = (CardView) findViewById(R.id.plus_guest);
        numberOfGuestsTextView = (TextView) findViewById(R.id.guests_count);
        if (Booking.guests == null) {
            numberOfGuestsTextView.setText(String.valueOf(numberOfGuests));
            Booking.guests = numberOfGuests;
        } else {
            numberOfGuestsTextView.setText(String.valueOf(Booking.guests));
        }
        plusGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfGuests < 10) { // TODO: Change max count
                    numberOfGuests++;
                    numberOfGuestsTextView.setText(String.valueOf(numberOfGuests));
                    Booking.guests = Integer.parseInt(numberOfGuestsTextView.getText().toString());
                }
            }
        });
        minusGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfGuests != 1) {
                    numberOfGuests--;
                    numberOfGuestsTextView.setText(String.valueOf(numberOfGuests));
                    Booking.guests = Integer.parseInt(numberOfGuestsTextView.getText().toString());
                }
                if (numberOfGuests == 1){
                    numberOfGuestsTextView.setText(String.valueOf(numberOfGuests));
                    Booking.guests = Integer.parseInt(numberOfGuestsTextView.getText().toString());
                }

            }
        });


        /* Enter preferences  */
        preferences = (EditText) findViewById(R.id.preferences);
        if (Booking.preferences == null || Booking.preferences.isEmpty()) {
            Booking.preferences = preferences.getText().toString();
        } else {
            preferences.setText(Booking.preferences);
        }
        preferences.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Booking.preferences = preferences.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                Booking.preferences = preferences.getText().toString();
            }
        });





        /* Select radio buttons Open Menu: Yes or No */
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
                        //Intent intent = new Intent(RestaurantTableBookingPage.this, RestaurantDishTypesPage.class);    // раньше была эта страница, сейчас поменяли на версию меню с tabs
                        Intent intent = new Intent(RestaurantTableBookingPage.this, PreorderTabsMainActivity.class);
                        intent.putExtra("RestaurantID", restaurant.getId());
                        startActivity(intent);
                        RestaurantTableBookingPage.this.finish();
                        //startActivity(new Intent(RestaurantTableBookingPage.this, RestaurantDishTypesPage.class));
                        break;
                    case 1: // openMenuNo radio button
                        //Toast.makeText(getApplicationContext(), "Selected button number " + index, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });


        surname = findViewById(R.id.input_surname);
        surname.setText(sharedPref.getString(SingletonSharedPref.USER_SURNAME));
        name = findViewById(R.id.input_name);
        name.setText(sharedPref.getString(SingletonSharedPref.USER_NAME));
        email = findViewById(R.id.input_email);
        email.setText(sharedPref.getString(SingletonSharedPref.USER_EMAIL));
        phone = (TextView) findViewById(R.id.input_phone);
        phone.setText(sharedPref.getString(SingletonSharedPref.USER_PHONE));

        /* Book table in restaurant */
        bookTableBtn = findViewById(R.id.book_table_button);
        bookTableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userID = sharedPref.getString(SingletonSharedPref.USER_ID);
                TableBookingWithPreorder booking = new TableBookingWithPreorder();
                booking.setTableId(Booking.tableID);
                booking.setUserId(userID);
                booking.setDate(Booking.date);
                booking.setTime(Booking.time);
                booking.setComments(Booking.preferences);
                booking.setMenu(Booking.preorder);
                booking.setNumberOfGuests(Booking.guests);
                BookTable b = new BookTable(booking,RestaurantTableBookingPage.this,"Bearer " + sharedPref.getString(SingletonSharedPref.TOKEN));
                b.bookTable();
                // TODO if code == 200 BookTable.reset(); -> in class each string = null; and create this function
                // https://stackoverflow.com/questions/9749078/clearing-static-data-ondestroy

            }
        });




    }


    @Override
    public void getBookTableInformation(ServerResponse response, int code) {
        Log.d("code", code + "");
        if (code == 200){
            Intent intent = new Intent(this, BookingsPage.class);
            startActivity(intent);
            finish();
        }
        else
            Snackbar.make(parentLayout, "Ошибка! " + response.getStatus(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
}
