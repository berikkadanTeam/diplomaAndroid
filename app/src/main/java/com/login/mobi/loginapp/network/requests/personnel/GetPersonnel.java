package com.login.mobi.loginapp.network.requests.personnel;

import android.util.Log;

import com.login.mobi.loginapp.network.ApiInterface;
import com.login.mobi.loginapp.network.BaseApi;
import com.login.mobi.loginapp.network.model.personnel.Personnel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetPersonnel {
    String token, restaurantID;
    GetPersonnel.GetPersonnelInterface anInterface;

    public GetPersonnel(GetPersonnel.GetPersonnelInterface anInterface, String token, String restaurantID) {
        this.anInterface = anInterface;
        this.token = token;
        this.restaurantID = restaurantID;
    }

    public interface GetPersonnelInterface{
        public void getPersonnel(List<Personnel> response);
    }



    public void getPersonnel(){
        ApiInterface service = BaseApi.getRetrofit().create(ApiInterface.class);
        Call<List<Personnel>> call = service.getPersonnel(token, restaurantID);
        call.enqueue(new Callback<List<Personnel>>() {
            @Override
            public void onResponse(Call<List<Personnel>> call, Response<List<Personnel>> response) {
                anInterface.getPersonnel(response.body());
                Log.d("mylog","body - " + response.body());
            }

            @Override
            public void onFailure(Call<List<Personnel>> call, Throwable t) {
                anInterface.getPersonnel(null);
                Log.d("mylog","error " + t.getLocalizedMessage());
            }
        });
    }

}
