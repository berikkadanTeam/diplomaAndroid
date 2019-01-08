package com.login.mobi.loginapp;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.login.mobi.loginapp.Database.AppDatabase;
import com.login.mobi.loginapp.Database.UserDatabase;
import com.login.mobi.loginapp.Models.Book;
import com.login.mobi.loginapp.Models.Restaurant;
import com.login.mobi.loginapp.Models.Table;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    ArrayList<String> timeList = new ArrayList<>(Arrays.asList("12:00-14:00", "14:00-16:00",
            "16:00-18:00", "18:00-20:00"));

    ArrayList<Integer> tableList = new ArrayList<>();

    TextView name;
    TextView description;
    TextView address;
    TextView booked;
    ImageView schema;
    ImageView iv;
    Button book;
    Spinner tableSpinner;
    Spinner time;

    View toolbar;

    TextView title;
    ImageView back;

    int tableNumber;
    String bookTime;

    int restaurantId = -1;

    Restaurant restaurant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final UserDatabase db = ((MyApp) getApplicationContext()).database;

        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        address = findViewById(R.id.address);
        schema = findViewById(R.id.schema);
        booked = findViewById(R.id.booked);
        iv = findViewById(R.id.iv);
        book = findViewById(R.id.book);
        tableSpinner = findViewById(R.id.table);
        time = findViewById(R.id.time);

        toolbar = findViewById(R.id.toolbar);

        title = toolbar.findViewById(R.id.title);
        back = toolbar.findViewById(R.id.backbutton);

        title.setText("Restaurant details");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        Intent intent = getIntent();

        restaurantId = intent.getIntExtra("id", -1);

        time.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, timeList));

        tableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tableNumber = tableList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bookTime = timeList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        List<Table> tables = db.tableDAO().getAllByRestaurantId(restaurantId);


        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int tableNumberToDbase = db.tableDAO().getTableIdByTableNumberAndResId(tableNumber,restaurantId);
                final List<Book> books = db.bookDAO().getByTableId(tableNumberToDbase);
                Log.d("TAG", "books size" + books.size());
                SharedPreferences pref = getSharedPreferences("MainActivity", Context.MODE_PRIVATE);
                if (pref.getInt("userId", -1) != -1){
                    if (books.size() != 0){
                        Log.d("TAG", "books size" + books.size());
                        boolean flag = false;
                        for (int i=0;i<books.size();i++) {
                            Log.d("TAG", "inside for" + books.get(i).table_id + " " + tableNumberToDbase + " " + books.get(i).time + " " + bookTime);

                            if ((books.get(i).time).equals(bookTime)) {
                                Log.d("TAG", "inside if");
                                AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
                                builder.setTitle("Go Back")
                                        .setMessage("Sorry, table is already reserved!")
                                        .setCancelable(false)
                                        .setNegativeButton("ОК",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });
                                AlertDialog alert = builder.create();
                                alert.show();
                                flag = true;
                            }
                        }
                        if (!flag){
                                int tableNumberToDb = db.tableDAO().getTableIdByTableNumberAndResId(tableNumber,restaurantId);
                                Log.d("TAG", "stuff" + tableNumber + " " + bookTime);
                                Book book = new Book(pref.getInt("userId", -1), tableNumberToDb, bookTime);
                                db.bookDAO().insert(book);
                                db.restaurantDAO().incrementBooks(restaurantId);

                                Log.d("TAG", "inside if");
                                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
                                    builder.setTitle("Table reserved")
                                            .setMessage("You successfully reserved the table")
                                            .setCancelable(false)
                                            .setNegativeButton("ОК",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();
                                                        }
                                                    });
                                    AlertDialog alert = builder.create();
                                    alert.show();
                                }
                    }else{
                        int tableNumberToDb = db.tableDAO().getTableIdByTableNumberAndResId(tableNumber,restaurantId);
                        Log.d("TAG", "stuff" + tableNumberToDb + " " + bookTime);
                        Book book = new Book(pref.getInt("userId", -1), tableNumberToDb, bookTime);
                        db.bookDAO().insert(book);
                        db.restaurantDAO().incrementBooks(restaurantId);
                        AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
                        builder.setTitle("Table reserved")
                                .setMessage("You successfully reserved the table")
                                .setCancelable(false)
                                .setNegativeButton("ОК",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                        AlertDialog alert = builder.create();
                        alert.show();
//                        finish();
                    }
                    Log.d("TAG","book " + db.bookDAO().getAll());
                }
            }
        });



        Log.d("TAG", "id " + restaurantId);

        if (restaurantId != -1){
            restaurant = db.restaurantDAO().getById(restaurantId).get(0);

            tableList.clear();

            for (int i = 0; i < db.tableDAO().getAllByRestaurantId(restaurant.id).size(); i++){
                tableList.add(i + 1);
            }
            Log.d("TAG", "size" + db.tableDAO().getAllByRestaurantId(restaurant.id).size());
            tableSpinner.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, tableList));

            booked.setText(restaurant.bookCount + " times");
            Picasso.get().load(restaurant.imageUrl).into(iv);
            name.setText(restaurant.name);
            description.setText(restaurant.description);
            address.setText(restaurant.address);
            Picasso.get().load(restaurant.schema).into(schema);
        }
    }
}
