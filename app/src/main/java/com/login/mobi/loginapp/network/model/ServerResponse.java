package com.login.mobi.loginapp.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServerResponse {

    @SerializedName("err")
    @Expose
    private List<String> err = null;
    @SerializedName("Password")
    @Expose
    private List<String> password = null;

    public List<String> getErr() {
        return err;
    }

    public void setErr(List<String> err) {
        this.err = err;
    }

    public List<String> getPassword() {
        return password;
    }

    public void setPassword(List<String> password) {
        this.password = password;
    }
}