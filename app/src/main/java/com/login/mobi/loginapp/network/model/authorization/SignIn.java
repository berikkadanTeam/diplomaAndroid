package com.login.mobi.loginapp.network.model.authorization;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignIn {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("auth_token")
    @Expose
    private String authToken;
    @SerializedName("expires_in")
    @Expose
    private Integer expiresIn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

}