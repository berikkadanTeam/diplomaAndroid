package com.login.mobi.loginapp.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "restaurants", indices = {@Index("id")})
public class Restaurant {

    @PrimaryKey
    public int id;

    public String name;

    public String description;

    public String imageUrl;

    public int schema;

    public String address;

    public int tableCount;

    @ColumnInfo(name = "book_count")
    public int bookCount;


    public Restaurant(int id,String name, String description, String imageUrl, String address, int tableCount, int bookCount, int schema) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.schema = schema;
        this.address = address;
        this.tableCount = tableCount;
        this.bookCount = bookCount;
    }

//    public Restaurant(String name, String description, String imageUrl, String address, int bookCount, int schema) {
//        this.name = name;
//        this.description = description;
//        this.imageUrl = imageUrl;
//        this.address = address;
//        this.bookCount = bookCount;
//        this.schema = schema;
//    }

//    public Restaurant(String name, String description, String address, int bookCount, byte[] schema) {
//        this.name = name;
//        this.description = description;
//        this.address = address;
//        this.bookCount = bookCount;
//        this.schema = schema;
//    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public int getBookCount() {
        return bookCount;
    }
}
