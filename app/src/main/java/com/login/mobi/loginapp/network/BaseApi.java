package com.login.mobi.loginapp.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vakidzaci on 10/2/18.
 */

public class BaseApi {


    public static final String BASE_URL ="http://5.23.55.101/" ;

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
