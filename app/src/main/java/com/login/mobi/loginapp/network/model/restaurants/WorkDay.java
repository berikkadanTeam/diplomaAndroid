package com.login.mobi.loginapp.network.model.restaurants;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkDay {

@SerializedName("dayName")
@Expose
private String dayName;
@SerializedName("startTime")
@Expose
private String startTime;
@SerializedName("endTime")
@Expose
private String endTime;

public String getDayName() {
return dayName;
}

public void setDayName(String dayName) {
this.dayName = dayName;
}

public String getStartTime() {
return startTime;
}

public void setStartTime(String startTime) {
this.startTime = startTime;
}

public String getEndTime() {
return endTime;
}

public void setEndTime(String endTime) {
this.endTime = endTime;
}

@Override
public String toString() {
    final StringBuilder sb = new StringBuilder("WorkDay{");
    sb.append("dayName='").append(dayName).append('\'');
    sb.append(", startTime='").append(startTime).append('\'');
    sb.append(", endTime='").append(endTime).append('\'');
    sb.append('}');
    return sb.toString();
}


}