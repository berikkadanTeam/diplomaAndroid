package com.login.mobi.loginapp.network.requests.restaurantMenu;

import android.util.Log;

import com.login.mobi.loginapp.network.ApiInterface;
import com.login.mobi.loginapp.network.BaseApi;
import com.login.mobi.loginapp.network.model.restaurantMenu.RestaurantDishes;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GetRestaurantDishes {

    String restaurantID;
    String token;
    com.login.mobi.loginapp.network.requests.restaurantMenu.GetRestaurantDishes.GetRestaurantDishesInterface anInterface;

    public GetRestaurantDishes(com.login.mobi.loginapp.network.requests.restaurantMenu.GetRestaurantDishes.GetRestaurantDishesInterface context, String restaurantID, String token) {
        anInterface = context;
        this.restaurantID = restaurantID;
        this.token = token;
    }

    public interface GetRestaurantDishesInterface{
        public void getRestaurantDishes(List<RestaurantDishes> response);
    }



    public void getRestaurantDishes(){

        ApiInterface service = BaseApi.getRetrofit().create(ApiInterface.class);
        Call<List<RestaurantDishes>> call = service.getRestaurantDishes(token, restaurantID);
        call.enqueue(new Callback<List<RestaurantDishes>>() {
            @Override
            public void onResponse(Call<List<RestaurantDishes>> call, Response<List<RestaurantDishes>> response) {
                anInterface.getRestaurantDishes(response.body());
                Log.d("mylog","body - " + response.body());
            }

            @Override
            public void onFailure(Call<List<RestaurantDishes>> call, Throwable t) {
                anInterface.getRestaurantDishes(null);
                Log.d("mylog","error " + t.getLocalizedMessage());
            }
        });
    }

}
