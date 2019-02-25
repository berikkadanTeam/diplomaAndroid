package com.login.mobi.loginapp.network.model.restaurantMenu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantDishes {

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
    private String title;
    @SerializedName("fileName")
    @Expose
    private String fileName;
    @SerializedName("fileId")
    @Expose
    private String fileId;
    @SerializedName("filePath")
    @Expose
    private String filePath;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RestaurantDishes{");
        sb.append("id='").append(id).append('\'');
        sb.append(", nameOfDish='").append(nameOfDish).append('\'');
        sb.append(", typeId=").append(typeId);
        sb.append(", composition='").append(composition).append('\'');
        sb.append(", price=").append(price);
        sb.append(", restaurantId=").append(restaurantId);
        sb.append(", title='").append(title).append('\'');
        sb.append(", fileName='").append(fileName).append('\'');
        sb.append(", fileId='").append(fileId).append('\'');
        sb.append(", filePath='").append(filePath).append('\'');
        sb.append('}');
        return sb.toString();
    }

}