package com.login.mobi.loginapp.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.login.mobi.loginapp.Models.AugustEntities;
import com.login.mobi.loginapp.Models.Restaurant;

import java.util.List;

@Dao
public interface RestaurantDAO {

    @Insert()
    void insert(Restaurant restaurant);

    @Update
    void update(Restaurant restaurant);

    @Delete
    void delete(Restaurant restaurant);

    @Query("DELETE FROM restaurants")
    void deleteAllRestaurants();

    @Query("SELECT * FROM restaurants ORDER BY book_count DESC")
    List<Restaurant> getAllByPopularity();

    @Query("SELECT * FROM restaurants ORDER BY name")
    List<Restaurant> getAllByName();

    @Query("DELETE FROM restaurants")
    void nukeTable();

    @Query("Select * from restaurants where id=:restaurantId")
    List<Restaurant> getById(int restaurantId);

    @Query("UPDATE restaurants SET book_count = book_count+1 where id=:restaurantId")
    void incrementBooks(int restaurantId);

}
