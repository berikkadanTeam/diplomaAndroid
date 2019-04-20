package com.login.mobi.loginapp.network.model.booking;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class TableBookingWithPreorder {

    @SerializedName("date")
    @Expose
    private Date date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("comments")
    @Expose
    private String comments;
    @SerializedName("tableId")
    @Expose
    private String tableId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("menu")
    @Expose
    private List<Menu> menu = null;
    @SerializedName("numberOfGuests")
    @Expose
    private Integer numberOfGuests;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Menu> getMenu() {
        return menu;
    }

    public void setMenu(List<Menu> menu) {
        this.menu = menu;
    }

    public Integer getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(Integer numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }
}