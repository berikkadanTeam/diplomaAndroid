package com.login.mobi.loginapp.network;

/**
 * Created by vakidzaci on 10/2/18.
 */

import com.login.mobi.loginapp.network.model.cities.Cities;
import com.login.mobi.loginapp.network.model.restaurants.Restaurant;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiInterface {

    @GET("api/Restaurants/GetRestaurants")
    Call<List<Restaurant>> getRestaurants();

    @GET("api/Restaurants/GetCities")
    Call<List<Cities>> getCities();

}
