package com.login.mobi.loginapp.network.model.booking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyBookings {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("comments")
    @Expose
    private String comments;
    @SerializedName("countPerson")
    @Expose
    private Integer countPerson;
    @SerializedName("numberOfTable")
    @Expose
    private Integer numberOfTable;
    @SerializedName("tableName")
    @Expose
    private Object tableName;
    @SerializedName("firstName")
    @Expose
    private Object firstName;
    @SerializedName("lastName")
    @Expose
    private Object lastName;
    @SerializedName("phoneNumber")
    @Expose
    private Object phoneNumber;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("menu")
    @Expose
    private List<MyBookingsMenu> menu = null;
    @SerializedName("reservConfirmed")
    @Expose
    private boolean reservConfirmed;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("addres")
    @Expose
    private String addres;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("numberOfBooking")
    @Expose
    private Integer numberOfBooking;
    @SerializedName("numberOfGuests")
    @Expose
    private Integer numberOfGuests;
    @SerializedName("getDate")
    @Expose
    private String getDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public Integer getCountPerson() {
        return countPerson;
    }

    public void setCountPerson(Integer countPerson) {
        this.countPerson = countPerson;
    }

    public Integer getNumberOfTable() {
        return numberOfTable;
    }

    public void setNumberOfTable(Integer numberOfTable) {
        this.numberOfTable = numberOfTable;
    }

    public Object getTableName() {
        return tableName;
    }

    public void setTableName(Object tableName) {
        this.tableName = tableName;
    }

    public Object getFirstName() {
        return firstName;
    }

    public void setFirstName(Object firstName) {
        this.firstName = firstName;
    }

    public Object getLastName() {
        return lastName;
    }

    public void setLastName(Object lastName) {
        this.lastName = lastName;
    }

    public Object getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Object phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public List<MyBookingsMenu> getMenu() {
        return menu;
    }

    public void setMenu(List<MyBookingsMenu> menu) {
        this.menu = menu;
    }

    public boolean getReservConfirmed() {
        return reservConfirmed;
    }

    public void setReservConfirmed(boolean reservConfirmed) { this.reservConfirmed = reservConfirmed; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getNumberOfBooking() { return numberOfBooking; }

    public void setNumberOfBooking(Integer numberOfBooking) { this.numberOfBooking = numberOfBooking; }

    public Integer getNumberOfGuests() { return numberOfGuests; }

    public void setNumberOfGuests(Integer numberOfGuests) { this.numberOfGuests = numberOfGuests; }

    public String getGetDate() { return getDate; }

    public void setGetDate(String getDate) { this.getDate = getDate; }

}
