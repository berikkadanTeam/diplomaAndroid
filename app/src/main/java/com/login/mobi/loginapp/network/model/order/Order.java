package com.login.mobi.loginapp.network.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.login.mobi.loginapp.network.model.booking.Menu;

import java.util.List;

public class Order {

    @SerializedName("tableId")
    @Expose
    private String tableId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("menu")
    @Expose
    private List<Menu> menu = null;

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

}