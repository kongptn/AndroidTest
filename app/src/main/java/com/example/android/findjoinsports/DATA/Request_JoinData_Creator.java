package com.example.android.findjoinsports.DATA;

public class Request_JoinData_Creator {
    private int req_id;
    private  int id;
    private  int user_id;
    private  int userid_join;
    private  int number_join;
    private String stadium_name;
    private String date;
    private String time;
    private String status_id;
    private String status_name;
    private String name;
    private String photo_user;


    public Request_JoinData_Creator(int req_id,int id,int user_id,int userid_join,int number_join ,String stadium_name,String date, String time, String status_id, String status_name, String name, String photo_user) {
        this.id = id;
        this.user_id = user_id;
        this.status_id = status_id;
        this.stadium_name = stadium_name;
        this.photo_user = photo_user;
        this.date = date;
        this.time = time;
        this.name = name;
        this.status_name = status_name;
        this.req_id = req_id;
        this.userid_join = userid_join;
        this.number_join = number_join;



    }



    public int getId() {
        return id;
    }
    public int getUser_id() {
        return user_id;
    }
    public int getReq_id() {
        return req_id;
    }
    public int getUserid_join() {
        return userid_join;
    }

    public int getNumber_join() {
        return number_join;
    }



    public String getStatus_id() {
        return status_id;
    }

    public String getStadium_name() {
        return stadium_name;
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


    public String getStatus_name() {
        return status_name;
    }


}
