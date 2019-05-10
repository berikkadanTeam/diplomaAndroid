package com.login.mobi.loginapp.network.requests.balance;

import com.login.mobi.loginapp.network.ApiInterface;
import com.login.mobi.loginapp.network.BaseApi;
import com.login.mobi.loginapp.network.model.ServerResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FillBalance {

    String token, userID, howMuch;
    FillBalanceInterface fillBalanceInterface;

    public FillBalance(FillBalanceInterface fillBalanceInterface, String token, String userID, String howMuch) {
        this.fillBalanceInterface = fillBalanceInterface;
        this.token = token;
        this.userID = userID;
        this.howMuch = howMuch;
    }

    public interface FillBalanceInterface{
        public void getFillBalanceResponse(ServerResponse response, int code);
    }



    public void fillBalance(){
        ApiInterface service = BaseApi.getRetrofit().create(ApiInterface.class);
        Call<ServerResponse> call = service.fillBalance(token, userID, howMuch);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                fillBalanceInterface.getFillBalanceResponse(response.body(), response.code());
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                fillBalanceInterface.getFillBalanceResponse(null, -1);
            }
        });
    }


}
