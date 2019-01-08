package com.login.mobi.loginapp.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.login.mobi.loginapp.Models.AugustEntities;
import com.login.mobi.loginapp.Models.Book;

import java.util.List;

@Dao
public interface BookDAO {

    @Insert
    void insert(Book book);

    @Query("SELECT * FROM books")
    List<Book> getAll();

    @Query("select * from books where table_id=:id")
    List<Book> getByTableId(int id);

//    @Query("select table_id from books where table_id=:id")
//    int getTableId(int id);

}
