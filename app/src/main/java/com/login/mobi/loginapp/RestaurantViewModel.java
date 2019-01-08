package com.login.mobi.loginapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.login.mobi.loginapp.Models.Restaurant;

import java.util.List;

public class RestaurantViewModel extends AndroidViewModel {

    private RestaurantRepository restaurantRepository;
    private List<Restaurant> allRestaurantsByPopularity;
    private List<Restaurant> allRestaurantsByName;


    public RestaurantViewModel(@NonNull Application application){
        super(application);
        restaurantRepository = new RestaurantRepository(application);
        allRestaurantsByPopularity = restaurantRepository.getAllRestaurantsByPopularity();
        allRestaurantsByName = restaurantRepository.getAllRestaurantsByName();
    }

    public void insert(Restaurant restaurant) {
        restaurantRepository.insert(restaurant);
    }

    public void update(Restaurant restaurant) {
        restaurantRepository.update(restaurant);
    }

    public void delete(Restaurant restaurant) {
        restaurantRepository.delete(restaurant);
    }

    public void deleteAllRestaurants() {
        restaurantRepository.deleteAllRestaurants();
    }

    public List<Restaurant> getAllRestaurantsByPopularity() {
        return allRestaurantsByPopularity;
    }

    public List<Restaurant> getAllRestaurantsByName() {
        return allRestaurantsByName;
    }
}
