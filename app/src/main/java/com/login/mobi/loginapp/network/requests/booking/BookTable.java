package com.login.mobi.loginapp.network.requests.booking;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.login.mobi.loginapp.network.ApiInterface;
import com.login.mobi.loginapp.network.BaseApi;
import com.login.mobi.loginapp.network.model.ServerResponse;
import com.login.mobi.loginapp.network.model.booking.TableBookingWithPreorder;

import java.io.IOException;
import java.lang.reflect.Type;

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
        public void getBookTableInformation(ServerResponse response, int code);
    }



    public void bookTable(){
        ApiInterface service = BaseApi.getRetrofit().create(ApiInterface.class);
        Call<ServerResponse> call = service.bookTable(token, tableBookingWithPreorder);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Gson gson = new Gson();
                Type type = new TypeToken<ServerResponse>(){}.getType();
                ServerResponse response_message = new ServerResponse();

                //tableInterface.getBookTableInformation(response.body(), response.code());
                try {
                    if (response.code() != 200) {
                        response_message = gson.fromJson(response.errorBody().string(), type);
                        if (response_message.getStatus() != null) {
                            tableInterface.getBookTableInformation(response_message, response.code());
                        }
                    } else{
                        tableInterface.getBookTableInformation(response.body(), response.code());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                tableInterface.getBookTableInformation(null, -1);
            }
        });
    }


}
