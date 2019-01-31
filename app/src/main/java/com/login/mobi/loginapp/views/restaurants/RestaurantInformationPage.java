package com.login.mobi.loginapp.views.restaurants;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.login.mobi.loginapp.MainActivity;
import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.RestaurantRepository;
import com.login.mobi.loginapp.network.ApiInterface;
import com.login.mobi.loginapp.network.model.restaurantInformation.RestaurantInformation;
import com.login.mobi.loginapp.network.model.restaurants.Restaurant;
import com.login.mobi.loginapp.network.requests.restaurantInformation.GetRestaurantInformation;
import com.login.mobi.loginapp.network.requests.restaurants.GetRestaurants;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantInformationPage extends AppCompatActivity {
        //implements GetRestaurants.GetRestaurantsInterface, GetRestaurantInformation.GetRestaurantInformationInterface {

    // variables
    String restaurantId;

    private List<Restaurant> list;
    private ArrayList<RestaurantInformation> restaurantDataList;

    // xml elements
    private TextView name, address, cuisine, averageCheck, delivery, seats, description;

    // Api
    private ApiInterface apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_information_page_test);

        name = (TextView) findViewById(R.id.restaurant_page_name);
        address = (TextView) findViewById(R.id.restaurant_page_location);
        cuisine = (TextView) findViewById(R.id.restaurant_page_cuisines);
        averageCheck = (TextView) findViewById(R.id.restaurant_page_average_check);
        delivery = (TextView) findViewById(R.id.restaurant_page_delivery);
        seats = (TextView) findViewById(R.id.restaurant_page_seats);
        description = (TextView) findViewById(R.id.restaurant_page_description);

        Intent intent = getIntent();
        String jsonData = intent.getStringExtra("restaurantData");
        //Tvoi dannyi
        Restaurant restaurant = new Gson().fromJson(jsonData, Restaurant.class);
        Log.d("RestaurantData", jsonData);

        restaurantId = restaurant.getId();
        name.setText(restaurant.getName());
        address.setText(restaurant.getAddres() + "\n" + restaurant.getCity());
        cuisine.setText(restaurant.getKitchen());
        averageCheck.setText(Integer.toString(restaurant.getAvgCheck()));
        seats.setText(Integer.toString(restaurant.getSeats()));
        description.setText(restaurant.getDescription());
        boolean hasDelivery = restaurant.getDelivery();
            if (hasDelivery)
                delivery.setText("Есть");
            else
                delivery.setText("Нет");

        //fetchRestaurantData();
        //name.setText(list.get(restaurantId).getName());
        //name.setText(restaurantId);


//
//        GetRestaurantInformation getRestaurantInformation = new GetRestaurantInformation(this);
//        getRestaurantInformation.getRestaurantInformation();
    }

    // Эти два метода уже не нужны, потому что передали всю информацию через intent

//    @Override
//    public void getRestaurantInformation(List<RestaurantInformation> response) {
//        if(response != null){
//            Toast.makeText(this,"Info response not NULL", Toast.LENGTH_LONG).show();
//        }else{
//            Toast.makeText(this,"NULL", Toast.LENGTH_LONG).show();
//        }
//    }


//    private void fetchRestaurantData(){
//        Call<RestaurantInformation> call = apiService.getRestaurantInformation(restaurantId);
//        call.enqueue(new Callback<RestaurantInformation>() {
//            @Override
//            public void onResponse(Call<RestaurantInformation> call, Response<RestaurantInformation> response) {
//                if (response.isSuccessful()) {
//                    restaurantDataList = new ArrayList<>();
//                    //restaurantDataList = response.body().getSpotList();
//                    //String dateToFormat = response.body().getTime();
//                    Log.e("info","info" + response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RestaurantInformation> call, Throwable t) {
//                Log.e("info","Error Fetching Spots from DO:" + t);
//            }
//        });
//    }


}
