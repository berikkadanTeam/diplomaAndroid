package com.login.mobi.loginapp.network.model.personnel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Personnel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("phoneNumber")
    @Expose
    private Object phoneNumber;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public Object getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(Object phoneNumber) { this.phoneNumber = phoneNumber; }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Personnel{");
        sb.append("id='").append(id).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", phoneNumber=").append(phoneNumber);
        sb.append('}');
        return sb.toString();
    }
}