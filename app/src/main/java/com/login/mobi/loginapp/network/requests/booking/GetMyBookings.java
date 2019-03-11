package com.login.mobi.loginapp.network.requests.booking;

import android.util.Log;

import com.login.mobi.loginapp.network.ApiInterface;
import com.login.mobi.loginapp.network.BaseApi;
import com.login.mobi.loginapp.network.model.booking.MyBookings;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetMyBookings {
    String userID;
    String token;
    GetMyBookingsInterface anInterface;

    public GetMyBookings(GetMyBookingsInterface context, String userID, String token) {
        anInterface = context;
        this.userID = userID;
        this.token = token;
    }

    public interface GetMyBookingsInterface{
        public void getMyBookings(List<MyBookings> response);
    }



    public void getMyBookings(){

        ApiInterface service = BaseApi.getRetrofit().create(ApiInterface.class);
        Call<List<MyBookings>> call = service.getMyBookings(token, userID);
        call.enqueue(new Callback<List<MyBookings>>() {
            @Override
            public void onResponse(Call<List<MyBookings>> call, Response<List<MyBookings>> response) {
                anInterface.getMyBookings(response.body());
                Log.d("mylog","body - " + response.body());
            }

            @Override
            public void onFailure(Call<List<MyBookings>> call, Throwable t) {
                anInterface.getMyBookings(null);
                Log.d("mylog","error " + t.getLocalizedMessage());
            }
        });
    }
}
