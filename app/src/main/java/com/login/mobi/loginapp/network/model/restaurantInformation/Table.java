package com.login.mobi.loginapp.network.model.restaurantInformation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Table {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("countPerson")
    @Expose
    private Integer countPerson;
    @SerializedName("restaurantId")
    @Expose
    private String restaurantId;
    @SerializedName("translateX")
    @Expose
    private Double translateX;
    @SerializedName("translateY")
    @Expose
    private Double translateY;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("reservTable")
    @Expose
    private Boolean reservTable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCountPerson() {
        return countPerson;
    }

    public void setCountPerson(Integer countPerson) {
        this.countPerson = countPerson;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Double getTranslateX() {
        return translateX;
    }

    public void setTranslateX(Double translateX) {
        this.translateX = translateX;
    }

    public Double getTranslateY() {
        return translateY;
    }

    public void setTranslateY(Double translateY) {
        this.translateY = translateY;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Boolean getReservTable() {
        return reservTable;
    }

    public void setReservTable(Boolean reservTable) {
        this.reservTable = reservTable;
    }

}
