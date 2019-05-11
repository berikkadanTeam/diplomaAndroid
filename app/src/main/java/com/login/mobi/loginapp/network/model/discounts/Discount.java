package com.login.mobi.loginapp.network.model.discounts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Discount {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("fileName")
    @Expose
    private String fileName;
    @SerializedName("filePath")
    @Expose
    private String filePath;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("restaurantId")
    @Expose
    private String restaurantId;
    @SerializedName("fromDate")
    @Expose
    private String fromDate;
    @SerializedName("toDate")
    @Expose
    private String toDate;
    @SerializedName("fileToUpload")
    @Expose
    private Object fileToUpload;
    @SerializedName("fromDateEx")
    @Expose
    private String fromDateEx;
    @SerializedName("toDateEx")
    @Expose
    private String toDateEx;

    public String getId() {  return id; }

    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getFileName() { return fileName; }

    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getFilePath() { return filePath; }

    public void setFilePath(String filePath) { this.filePath = filePath; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getRestaurantId() { return restaurantId; }

    public void setRestaurantId(String restaurantId) { this.restaurantId = restaurantId; }

    public String getFromDate() { return fromDate; }

    public void setFromDate(String fromDate) { this.fromDate = fromDate; }

    public String getToDate() { return toDate; }

    public void setToDate(String toDate) { this.toDate = toDate; }

    public Object getFileToUpload() { return fileToUpload; }

    public void setFileToUpload(Object fileToUpload) { this.fileToUpload = fileToUpload; }

    public String getFromDateEx() { return fromDateEx; }

    public void setFromDateEx(String fromDateEx) { this.fromDateEx = fromDateEx; }

    public String getToDateEx() { return toDateEx; }

    public void setToDateEx(String toDateEx) { this.toDateEx = toDateEx; }

}