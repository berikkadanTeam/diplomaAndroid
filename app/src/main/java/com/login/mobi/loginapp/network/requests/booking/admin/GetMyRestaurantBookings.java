package com.login.mobi.loginapp.network.requests.booking.admin;

import android.util.Log;

import com.login.mobi.loginapp.network.ApiInterface;
import com.login.mobi.loginapp.network.BaseApi;
import com.login.mobi.loginapp.network.model.booking.MyRestaurantBookings;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetMyRestaurantBookings {
    String restaurantID;
    String token;
    GetMyRestaurantBookingsInterface anInterface;

    public GetMyRestaurantBookings(GetMyRestaurantBookingsInterface context, String restaurantID, String token) {
        anInterface = context;
        this.restaurantID = restaurantID;
        this.token = token;
    }

    public interface GetMyRestaurantBookingsInterface{
        public void getMyRestaurantBookings(List<MyRestaurantBookings> response);
    }



    public void getMyRestaurantBookings(){

        ApiInterface service = BaseApi.getRetrofit().create(ApiInterface.class);
        Call<List<MyRestaurantBookings>> call = service.getMyRestaurantBookings(token, restaurantID);
        call.enqueue(new Callback<List<MyRestaurantBookings>>() {
            @Override
            public void onResponse(Call<List<MyRestaurantBookings>> call, Response<List<MyRestaurantBookings>> response) {
                anInterface.getMyRestaurantBookings(response.body());
                Log.d("mylog","body - " + response.body());
            }

            @Override
            public void onFailure(Call<List<MyRestaurantBookings>> call, Throwable t) {
                anInterface.getMyRestaurantBookings(null);
                Log.d("mylog","error " + t.getLocalizedMessage());
            }
        });
    }
}
