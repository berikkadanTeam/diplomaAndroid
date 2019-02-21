package com.login.mobi.loginapp.network;

/**
 * Created by vakidzaci on 10/2/18.
 */

import com.login.mobi.loginapp.network.model.authorization.SignIn;
import com.login.mobi.loginapp.network.model.cities.Cities;
import com.login.mobi.loginapp.network.model.restaurantInformation.RestaurantInformation;
import com.login.mobi.loginapp.network.model.restaurants.Restaurant;
import com.login.mobi.loginapp.network.model.userInformation.UserInformation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

//import okhttp


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

    // http://qaru.site/questions/419191/retrofit-authorization-bearer-token
    @GET("api/Users/GetUserInfo")
    Call<List<UserInformation>> getUserInformation();
    //(@Query("UserId") String userID, @Header("Authorization") String authHeader);




    @GET("api/Restaurants/GetRestaurants")
    Call<List<RestaurantInformation>> getRestaurantInformation();
    // OR
    @GET("api/Restaurants/GetRestaurants")
    Call<RestaurantInformation> getRestaurantInformation(@Query("restaurantId") String restaurantId);

}
