package com.login.mobi.loginapp.network.model.booking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MyBookingsMenu {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("nameOfDish")
    @Expose
    private String nameOfDish;
    @SerializedName("typeId")
    @Expose
    private Integer typeId;
    @SerializedName("composition")
    @Expose
    private String composition;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("restaurantId")
    @Expose
    private Object restaurantId;
    @SerializedName("title")
    @Expose
    private Object title;
    @SerializedName("fileName")
    @Expose
    private String fileName;
    @SerializedName("fileId")
    @Expose
    private String fileId;
    @SerializedName("filePath")
    @Expose
    private Object filePath;
    @SerializedName("dishCount")
    @Expose
    private Integer dishCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameOfDish() {
        return nameOfDish;
    }

    public void setNameOfDish(String nameOfDish) {
        this.nameOfDish = nameOfDish;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Object getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Object restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Object getTitle() {
        return title;
    }

    public void setTitle(Object title) {
        this.title = title;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Object getFilePath() {
        return filePath;
    }

    public void setFilePath(Object filePath) {
        this.filePath = filePath;
    }

    public Integer getDishCount() {
        return dishCount;
    }

    public void setDishCount(Integer dishCount) {
        this.dishCount = dishCount;
    }

}