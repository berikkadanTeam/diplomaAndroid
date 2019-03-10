package com.login.mobi.loginapp.network.model.restaurants;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Restaurant {

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
    private String filePath;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("workDay")
    @Expose
    private List<WorkDay> workDay = null;
    @SerializedName("tables")
    @Expose
    private Object tables;
    @SerializedName("area")
    @Expose
    private Object area;
    @SerializedName("menu")
    @Expose
    private Object menu;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getAddres() { return addres; }

    public void setAddres(String addres) { this.addres = addres; }

    public String getNumber() { return number; }

    public void setNumber(String number) { this.number = number; }

    public String getKitchen() { return kitchen; }

    public void setKitchen(String kitchen) { this.kitchen = kitchen; }

    public Boolean getDelivery() { return delivery; }

    public void setDelivery(Boolean delivery) { this.delivery = delivery; }

    public Integer getAvgCheck() { return avgCheck; }

    public void setAvgCheck(Integer avgCheck) { this.avgCheck = avgCheck; }

    public Integer getSeats() { return seats; }

    public void setSeats(Integer seats) { this.seats = seats; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Integer getCityId() { return cityId; }

    public void setCityId(Integer cityId) { this.cityId = cityId; }

    public String getFileName() { return fileName; }

    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getFilePath() { return filePath; }

    public void setFilePath(String filePath) { this.filePath = filePath; }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public List<WorkDay> getWorkDay() { return workDay; }

    public void setWorkDay(List<WorkDay> workDay) { this.workDay = workDay; }

    public Object getTables() { return tables; }

    public void setTables(Object tables) { this.tables = tables; }

    public Object getArea() { return area; }

    public void setArea(Object area) { this.area = area; }

    public Object getMenu() { return menu; }

    public void setMenu(Object menu) { this.menu = menu; }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", addres='" + addres + '\'' +
                ", number=" + number +
                ", kitchen='" + kitchen + '\'' +
                ", delivery=" + delivery +
                ", avgCheck=" + avgCheck +
                ", seats=" + seats +
                ", description='" + description + '\'' +
                ", cityId=" + cityId +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", city='" + city + '\'' +
                ", workDay=" + workDay +
                ", tables=" + tables +
                ", area=" + area +
                ", menu=" + menu +
                '}';
    }

}

//@SerializedName("name")
//@Expose
//private String name;
//@SerializedName("addres")
//@Expose
//private String addres;
//@SerializedName("number")
//@Expose
//private Integer number;
//@SerializedName("kitchen")
//@Expose
//private String kitchen;
//@SerializedName("delivery")
//@Expose
//private Boolean delivery;
//@SerializedName("avgCheck")
//@Expose
//private Integer avgCheck;
//@SerializedName("seats")
//@Expose
//private Integer seats;
//@SerializedName("description")
//@Expose
//private String description;
//@SerializedName("cityId")
//@Expose
//private Integer cityId;
//@SerializedName("fileName")
//@Expose
//private String fileName;
//@SerializedName("filePath")
//@Expose
//private String filePath;
//@SerializedName("city")
//@Expose
//private String city;
//@SerializedName("workDay")
//@Expose
//private List<WorkDay> workDay = null;
//
//public String getName() {
//return name;
//}
//
//public void setName(String name) {
//this.name = name;
//}
//
//public String getAddres() {
//return addres;
//}
//
//public void setAddres(String addres) {
//this.addres = addres;
//}
//
//public Integer getNumber() {
//return number;
//}
//
//public void setNumber(Integer number) {
//this.number = number;
//}
//
//public String getKitchen() {
//return kitchen;
//}
//
//public void setKitchen(String kitchen) {
//this.kitchen = kitchen;
//}
//
//public Boolean getDelivery() {
//return delivery;
//}
//
//public void setDelivery(Boolean delivery) {
//this.delivery = delivery;
//}
//
//public Integer getAvgCheck() {
//return avgCheck;
//}
//
//public void setAvgCheck(Integer avgCheck) {
//this.avgCheck = avgCheck;
//}
//
//public Integer getSeats() {
//return seats;
//}
//
//public void setSeats(Integer seats) {
//this.seats = seats;
//}
//
//public String getDescription() {
//return description;
//}
//
//public void setDescription(String description) {
//this.description = description;
//}
//
//public Integer getCityId() {
//return cityId;
//}
//
//public void setCityId(Integer cityId) {
//this.cityId = cityId;
//}
//
//public String getFileName() {
//return fileName;
//}
//
//public void setFileName(String fileName) {
//this.fileName = fileName;
//}
//
//public String getFilePath() {
//return filePath;
//}
//
//public void setFilePath(String filePath) {
//this.filePath = filePath;
//}
//
//public String getCity() {
//return city;
//}
//
//public void setCity(String city) {
//this.city = city;
//}
//
//public List<WorkDay> getWorkDay() {
//return workDay;
//}
//
//public void setWorkDay(List<WorkDay> workDay) {
//this.workDay = workDay;
//}