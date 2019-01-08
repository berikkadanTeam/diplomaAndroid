package com.login.mobi.loginapp.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.login.mobi.loginapp.Models.Book;
import com.login.mobi.loginapp.Models.Table;

import java.util.List;

@Dao
public interface TableDAO {

    @Insert
    void insert(Table table);

    @Query("SELECT * FROM tables")
    List<Table> getAll();

    @Query("DELETE FROM tables")
    void nukeTable();

    @Query("SELECT * FROM tables where res_id=:id")
    List<Table> getAllByRestaurantId(int id);

    @Query("SELECT id FROM tables where res_table_number=:number AND res_id=:restaurantId")
    int getTableIdByTableNumberAndResId(int number, int restaurantId);

}
