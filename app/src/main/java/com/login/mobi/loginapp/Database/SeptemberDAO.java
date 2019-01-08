package com.login.mobi.loginapp.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.login.mobi.loginapp.Models.SeptemberEntities;

import java.util.List;

@Dao
public interface SeptemberDAO {
    @Query("SELECT * FROM septemberentities")
    List<SeptemberEntities> getAll();

    @Insert
    void insertSingleSeptemberEntity(SeptemberEntities septemberEntitiy);

    @Query("DELETE FROM septemberentities")
    public void nukeTable();
}