package com.login.mobi.loginapp.network.requests.booking;

import com.login.mobi.loginapp.network.ApiInterface;
import com.login.mobi.loginapp.network.BaseApi;
import com.login.mobi.loginapp.network.model.ServerResponse;
import com.login.mobi.loginapp.network.model.booking.TableBookingWithPreorder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookTable {
    String token;
    TableBookingWithPreorder tableBookingWithPreorder;
    BookTableInterface tableInterface;

    public BookTable(TableBookingWithPreorder tableBookingWithPreorder, BookTableInterface tableInterface, String token) {
        this.tableBookingWithPreorder = tableBookingWithPreorder;
        this.tableInterface = tableInterface;
        this.token = token;
    }

    public interface BookTableInterface{
        public void getBookTableInformation(ServerResponse response);
    }



    public void bookTable(){
        ApiInterface service = BaseApi.getRetrofit().create(ApiInterface.class);
        Call<ServerResponse> call = service.bookTable(token, tableBookingWithPreorder);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                tableInterface.getBookTableInformation(response.body());
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                tableInterface.getBookTableInformation(null);
            }
        });
    }


}
