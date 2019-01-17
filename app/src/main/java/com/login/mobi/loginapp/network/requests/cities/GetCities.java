package com.login.mobi.loginapp.network.requests.cities;

import android.content.Context;
import android.util.Log;

import com.login.mobi.loginapp.network.ApiInterface;
import com.login.mobi.loginapp.network.BaseApi;
import com.login.mobi.loginapp.network.model.cities.Cities;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GetCities {

    GetCitiesInterface anInterface;

    public GetCities(Context context) {
        anInterface = (GetCitiesInterface) context;
    }

    public interface GetCitiesInterface{
        public void getCities(List<Cities> response);
    }



    public void getCities(){

        ApiInterface service = BaseApi.getRetrofit().create(ApiInterface.class);
        Call<List<Cities>> call = service.getCities();
        call.enqueue(new Callback<List<Cities>>() {
            @Override
            public void onResponse(Call<List<Cities>> call, Response<List<Cities>> response) {
                anInterface.getCities(response.body());
                Log.d("mylog","body - " + response.body());
            }

            @Override
            public void onFailure(Call<List<Cities>> call, Throwable t) {
                anInterface.getCities(null);
                Log.d("mylog","error "+t.getLocalizedMessage());
            }
        });
    }

}
