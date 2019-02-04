package com.login.mobi.loginapp.network.requests.restaurants;

import android.content.Context;
import android.util.Log;

import com.login.mobi.loginapp.network.model.restaurants.Restaurant;
import com.login.mobi.loginapp.network.ApiInterface;
import com.login.mobi.loginapp.network.BaseApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GetRestaurants {

    GetRestaurantsInterface anInterface;

    public GetRestaurants(GetRestaurantsInterface context) {
        anInterface = context;
    }

    public interface GetRestaurantsInterface{
        public void getRestaurants(List<Restaurant> response);
    }



    public void getRestaurants(){

        ApiInterface service = BaseApi.getRetrofit().create(ApiInterface.class);
        Call<List<Restaurant>> call = service.getRestaurants();
        call.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                anInterface.getRestaurants(response.body());
                Log.d("mylog","body - " + response.body());
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                anInterface.getRestaurants(null);
                Log.d("mylog","error "+t.getLocalizedMessage());
            }
        });
    }


}
