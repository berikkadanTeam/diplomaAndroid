package com.login.mobi.loginapp.network.requests.restaurantMenu;

import android.util.Log;

import com.login.mobi.loginapp.network.ApiInterface;
import com.login.mobi.loginapp.network.BaseApi;
import com.login.mobi.loginapp.network.model.restaurantMenu.RestaurantDishTypes;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetRestaurantDishTypes {

    String token;
    com.login.mobi.loginapp.network.requests.restaurantMenu.GetRestaurantDishTypes.GetRestaurantDishTypesInterface anInterface;

    public GetRestaurantDishTypes(com.login.mobi.loginapp.network.requests.restaurantMenu.GetRestaurantDishTypes.GetRestaurantDishTypesInterface context, String token) {
        anInterface = context;
        this.token = token;
    }

    public interface GetRestaurantDishTypesInterface{
        public void getRestaurantDishTypes(List<RestaurantDishTypes> response);
    }



    public void getRestaurantDishTypes(){

        ApiInterface service = BaseApi.getRetrofit().create(ApiInterface.class);
        Call<List<RestaurantDishTypes>> call = service.getRestaurantDishTypes(token);
        call.enqueue(new Callback<List<RestaurantDishTypes>>() {
            @Override
            public void onResponse(Call<List<RestaurantDishTypes>> call, Response<List<RestaurantDishTypes>> response) {
                anInterface.getRestaurantDishTypes(response.body());
                Log.d("mylog","body - " + response.body());
            }

            @Override
            public void onFailure(Call<List<RestaurantDishTypes>> call, Throwable t) {
                anInterface.getRestaurantDishTypes(null);
                Log.d("mylog","error " + t.getLocalizedMessage());
            }
        });
    }

}
