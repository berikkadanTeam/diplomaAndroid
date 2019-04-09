package com.login.mobi.loginapp.network.model.restaurantInformation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantInformation {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("addres")
    @Expose
    private String addres;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("kitchen")
    @Expose
    private String kitchen;
    @SerializedName("delivery")
    @Expose
    private Boolean delivery;
    @SerializedName("avgCheck")
    @Expose
    private Integer avgCheck;
    @SerializedName("seats")
    @Expose
    private Integer seats;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("cityId")
    @Expose
    private Integer cityId;
    @SerializedName("fileName")
    @Expose
    private String fileName;
    @SerializedName("filePath")
    @Expose
    private Object filePath;
    @SerializedName("city")
    @Expose
    private Object city;
    @SerializedName("workDay")
    @Expose
    private List<WorkDay> workDay = null;
    @SerializedName("tables")
    @Expose
    private List<Table> tables = null;
    private Object menu;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getKitchen() {
        return kitchen;
    }

    public void setKitchen(String kitchen) {
        this.kitchen = kitchen;
    }

    public Boolean getDelivery() {
        return delivery;
    }

    public void setDelivery(Boolean delivery) {
        this.delivery = delivery;
    }

    public Integer getAvgCheck() {
        return avgCheck;
    }

    public void setAvgCheck(Integer avgCheck) {
        this.avgCheck = avgCheck;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Object getFilePath() {
        return filePath;
    }

    public void setFilePath(Object filePath) {
        this.filePath = filePath;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public List<WorkDay> getWorkDay() {
        return workDay;
    }

    public void setWorkDay(List<WorkDay> workDay) {
        this.workDay = workDay;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    public List<Image> getImages() { return images; }

    public void setImages(List<Image> images) { this.images = images; }

}
