package com.example.android.findjoinsports.SearchActivity;

public class RecyclerSearch {

    private int id;
    private String stadium_name;
    private String photo;
    private String date;
    private String time;
    private String name;
    private String location;
    private String description;



    public RecyclerSearch(int id, String stadium_name, String photo, String date, String time, String name, String location, String description) {
        this.id = id;
        this.stadium_name = stadium_name;
        this.photo = photo;
        this.date = date;
        this.time = time;
        this.name = name;
        this.location = location;
        this.description = description;

    }



    public int getId() {
        return id;
    }

    public String getStadium_name() {
        return stadium_name;
    }

    public String getPhoto() {
        return photo;
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
