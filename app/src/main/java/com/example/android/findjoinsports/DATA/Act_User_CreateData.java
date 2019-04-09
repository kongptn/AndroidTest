package com.example.android.findjoinsports.DATA;

public class Act_User_CreateData {

    private int id;
    private  int user_id;
    private String stadium_name;
    private String photo;
    private String photo_user;
    private String date;
    private String time;
    private String name;
    private String location;
    private String description;



    public Act_User_CreateData(int id,int user_id, String stadium_name, String photo,String photo_user, String date, String time, String name, String location, String description) {
        this.id = id;
        this.user_id = user_id;
        this.stadium_name = stadium_name;
        this.photo = photo;
        this.photo_user = photo_user;
        this.date = date;
        this.time = time;
        this.name = name;
        this.location = location;
        this.description = description;

    }



    public int getId() {
        return id;
    }
    public int getUser_id() {
        return user_id;
    }

    public String getStadium_name() {
        return stadium_name;
    }

    public String getPhoto() {
        return photo;
    }

    public String getPhoto_user() {
        return photo_user;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }


}
