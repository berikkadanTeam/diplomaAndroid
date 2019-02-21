package com.login.mobi.loginapp.network.model.userInformation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserInformation {

    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("roles")
    @Expose
    private List<String> roles = null;
    @SerializedName("restaurantId")
    @Expose
    private String restaurantId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserInformation{");
        sb.append("userName='").append(userName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", roles=").append(roles);
        sb.append(", restaurantId='").append(restaurantId).append('\'');
        sb.append('}');
        return sb.toString();
    }

    
}