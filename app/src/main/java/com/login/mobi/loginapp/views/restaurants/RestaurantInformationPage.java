package com.login.mobi.loginapp.views.restaurants;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.restaurants.Restaurant;
import com.login.mobi.loginapp.network.requests.restaurants.GetRestaurants;

import org.w3c.dom.Text;

import java.util.List;

public class RestaurantInformationPage extends AppCompatActivity implements GetRestaurants.GetRestaurantsInterface {

    // variables
    String restaurantId;

    private List<Restaurant> list;

    // xml elements
    private TextView name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_information_page);

        name = (TextView) findViewById(R.id.restaurant_page_name_2);

        Intent intent = getIntent();
        restaurantId = intent.getStringExtra("RestaurantID");
        Toast.makeText(this,"ID: " + restaurantId, Toast.LENGTH_LONG).show();
        //name.setText(list.get(restaurantId).getName());
        //name.setText(restaurantId);


        GetRestaurants getRestaurants = new GetRestaurants(this);
        getRestaurants.getRestaurants();
    }

    @Override
    public void getRestaurants(List<Restaurant> response) {
        if(response != null){
            Toast.makeText(this,"Restaurants displayed (name + description)", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"NULL", Toast.LENGTH_LONG).show();
        }
    }
}
