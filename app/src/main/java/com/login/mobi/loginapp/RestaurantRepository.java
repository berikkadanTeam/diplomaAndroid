package com.login.mobi.loginapp;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.os.AsyncTask;

import com.login.mobi.loginapp.Database.AppDatabase;
import com.login.mobi.loginapp.Database.RestaurantDAO;
import com.login.mobi.loginapp.Models.Restaurant;

import java.util.List;

public class RestaurantRepository {

    private RestaurantDAO restaurantDAO;

    private List<Restaurant> allRestaurantsByPopularity;
    private List<Restaurant> allRestaurantsByName;

    public RestaurantRepository(Application application){

        AppDatabase database = AppDatabase.getInstance(application);
        restaurantDAO = database.restaurantDAO();
        allRestaurantsByPopularity = restaurantDAO.getAllByPopularity();
        allRestaurantsByName = restaurantDAO.getAllByName();
    }

    @Insert
    public void insert(Restaurant restaurant){
        new InsertRestaurantAsyncTask(restaurantDAO).execute(restaurant);
    }

    @Update
    void update(Restaurant restaurant){}

    @Delete
    void delete(Restaurant restaurant){}

    @Query("DELETE FROM restaurants")
    void deleteAllRestaurants(){}

    private static class InsertRestaurantAsyncTask extends AsyncTask<Restaurant, Void, Void>{

        private RestaurantDAO restaurantDAO;

        private InsertRestaurantAsyncTask(RestaurantDAO restaurantDAO){
            this.restaurantDAO = restaurantDAO;
        }

        @Override
        protected Void doInBackground(Restaurant... restaurants) {
            restaurantDAO.insert(restaurants[0]);
            return null;
        }

    }

    public List<Restaurant> getAllRestaurantsByName() {
        return allRestaurantsByName;
    }

    public List<Restaurant> getAllRestaurantsByPopularity() {
        return allRestaurantsByPopularity;
    }
}
