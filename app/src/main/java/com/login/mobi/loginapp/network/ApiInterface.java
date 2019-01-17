package com.login.mobi.loginapp.network;

/**
 * Created by vakidzaci on 10/2/18.
 */

import com.login.mobi.loginapp.network.model.authorization.SignIn;
import com.login.mobi.loginapp.network.model.cities.Cities;
import com.login.mobi.loginapp.network.model.restaurants.Restaurant;

import java.util.List;

//import okhttp
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface ApiInterface {

    @GET("api/Restaurants/GetRestaurants")
    Call<List<Restaurant>> getRestaurants();

    @GET("api/Restaurants/GetCities")
    Call<List<Cities>> getCities();

    @FormUrlEncoded
    @POST("api/accounts")
    Call<String> signup(
            @Field("email")     String email,
            @Field("firstName") String firstName,
            @Field("lastName")  String lastName,
            @Field("password")  String password,
            @Field("location")  String location
    );

    @FormUrlEncoded
    @POST("api/auth/login")
    Call<SignIn> signin(
            @Field("userName")  String username,
            @Field("password")  String password
    );


}
