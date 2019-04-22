package com.login.mobi.loginapp.network.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.login.mobi.loginapp.network.model.booking.MyBookingsMenu;

import java.util.List;

public class MyOrders {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("tableId")
    @Expose
    private String tableId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("menu")
    @Expose
    private List<MyBookingsMenu> menu;
    @SerializedName("numberOfOrder")
    @Expose
    private Integer numberOfOrder;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<MyBookingsMenu> getMenu() {
        return menu;
    }

    public void setMenu(List<MyBookingsMenu> menu) {
        this.menu = menu;
    }

    public Integer getNumberOfOrder() { return numberOfOrder; }

    public void setNumberOfOrder(Integer numberOfOrder) { this.numberOfOrder = numberOfOrder; }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MyOrders{");
        sb.append("id='").append(id).append('\'');
        sb.append(", tableId='").append(tableId).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", menu=").append(menu);
        sb.append('}');
        return sb.toString();
    }

}