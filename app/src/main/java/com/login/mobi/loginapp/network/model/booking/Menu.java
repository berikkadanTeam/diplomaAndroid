package com.login.mobi.loginapp.network.model.booking;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Menu {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("dishCount")
    @Expose
    private Integer dishCount;
    @SerializedName("price")
    @Expose
    private Integer dishPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getDishCount() {
        return dishCount;
    }

    public void setDishCount(Integer dishCount) {
        this.dishCount = dishCount;
    }

    public Integer getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(Integer dishPrice) {
        this.dishPrice = dishPrice;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Menu{");
        sb.append("id='").append(id).append('\'');
        sb.append(", dishCount=").append(dishCount);
        sb.append('}');
        return sb.toString();
    }
}