package com.login.mobi.loginapp.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "books", foreignKeys = {@ForeignKey(entity = Table.class, parentColumns = "id",
                                                childColumns = "table_id", onDelete = CASCADE), @ForeignKey(entity = User.class,
                                                parentColumns = "id", childColumns = "usr_id", onDelete = CASCADE)},
                                                indices = {@Index("table_id"), @Index("usr_id")})
public class Book {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int usr_id;

    public int table_id;

    public String time;


    public Book(int usr_id, int table_id, String time) {
        this.usr_id = usr_id;
        this.table_id = table_id;
        this.time = time;
    }
}
