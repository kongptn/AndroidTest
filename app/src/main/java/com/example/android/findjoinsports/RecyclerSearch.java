package com.example.android.findjoinsports;

public class RecyclerSearch {

    private int id;
    private String stadium_name;
    private String photo;
    private String date;
    private String time;
    private String name;

    public RecyclerSearch(int id, String stadium_name, String photo, String date, String time, String name) {
        this.id = id;
        this.stadium_name = stadium_name;
        this.photo = photo;
        this.date = date;
        this.time = time;
        this.name = name;
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
}
