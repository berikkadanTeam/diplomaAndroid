package com.login.mobi.loginapp.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.login.mobi.loginapp.Models.AugustEntities;

import java.util.List;

@Dao
public interface AugustDAO {
    @Query("SELECT * FROM augustentities")
    List<AugustEntities> getAll();

    @Insert
    void insertSingleAugustEntity(AugustEntities augustEntitiy);

    @Query("DELETE FROM augustentities")
    public void nukeTable();
}