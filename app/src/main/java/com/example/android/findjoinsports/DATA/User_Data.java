package com.example.android.findjoinsports.DATA;

import com.google.gson.annotations.SerializedName;

public class User_Data {

    @SerializedName("user_id") private int Id;
    @SerializedName("name") private String Name;
    @SerializedName("email") private String Email;
    @SerializedName("user_firstname") private String User_firstname;
    @SerializedName("user_lastname") private String User_lastname;
    @SerializedName("user_tel") private String User_tel;
    @SerializedName("user_age") private String User_age;
    @SerializedName("user_sex") private String User_sex;
    @SerializedName("photo_user") private String Photo_user;

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getUser_firstname() {
        return User_firstname;
    }

    public String getUser_lastname() {
        return User_lastname;
    }

    public String getUser_tel() { return User_tel; }

    public String getUser_age() {
        return User_age;
    }

    public String getUser_sex() {
        return User_sex;
    }

    public String getPhoto_user() {
        return Photo_user;
    }



}
