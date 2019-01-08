package com.login.mobi.loginapp.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "tables", foreignKeys = @ForeignKey(entity = Restaurant.class,
                                                        parentColumns = "id",
                                                        childColumns = "res_id", onDelete = CASCADE ), indices = {@Index("res_id")})
public class Table {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int res_table_number;

    public int res_id;

    public Table(int res_table_number, int res_id) {
        this.res_table_number = res_table_number;
        this.res_id = res_id;
    }

}
