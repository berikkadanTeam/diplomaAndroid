package com.login.mobi.loginapp.network.requests.discounts;

import android.util.Log;

import com.login.mobi.loginapp.network.ApiInterface;
import com.login.mobi.loginapp.network.BaseApi;
import com.login.mobi.loginapp.network.model.discounts.Discount;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetDiscounts {
    String token;
    GetDiscountsInterface anInterface;

    public GetDiscounts(GetDiscountsInterface anInterface, String token) {
        this.anInterface = anInterface;
        this.token = token;
    }

    public interface GetDiscountsInterface{
        public void getDiscounts(List<Discount> response);
    }



    public void getDiscounts(){
        ApiInterface service = BaseApi.getRetrofit().create(ApiInterface.class);
        Call<List<Discount>> call = service.getDiscounts(token);
        call.enqueue(new Callback<List<Discount>>() {
            @Override
            public void onResponse(Call<List<Discount>> call, Response<List<Discount>> response) {
                anInterface.getDiscounts(response.body());
                Log.d("mylog","body - " + response.body());
            }

            @Override
            public void onFailure(Call<List<Discount>> call, Throwable t) {
                anInterface.getDiscounts(null);
                Log.d("mylog","error " + t.getLocalizedMessage());
            }
        });
    }
}
