package com.login.mobi.loginapp.network.model.userInformation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Set;

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
    private Set<String> roles = null;
    @SerializedName("restaurantId")
    @Expose
    private String restaurantId;
    @SerializedName("virtualMoney")
    @Expose
    private Integer virtualMoney;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;

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


    // Pojo cделал его List, но для того, чтобы хранить роль в SharedPreferences, сделали Set<String>
    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }


    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getVirtualMoney() { return virtualMoney; }

    public void setVirtualMoney(Integer virtualMoney) { this.virtualMoney = virtualMoney; }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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