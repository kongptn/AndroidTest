package com.example.android.findjoinsports.DATA;

public class Request_Invite_JoinactData {
    private int rf_id;
    // private  int userid_add;
    private  int user_id;
    private String status_id;
    private String status_name;
    private String name;
    private String photo_user;


    public Request_Invite_JoinactData(int rf_id, int user_id, String status_id, String status_name, String name, String photo_user) {
        this.rf_id = rf_id;
        // this.userid_add = userid_add;
        this.user_id = user_id;
        this.status_id = status_id;
        this.status_name = status_name;
        this.name = name;
        this.status_id = status_id;
        this.name = name;
        this.photo_user = photo_user;


    }



    public int getRf_id() {
        return rf_id;
    }
    public int getUser_id() {
        return user_id;
    }
//    public int getUserid_add() {
//        return userid_add;
//    }


    public String getStatus_id() {
        return status_id;
    }

    public String getStatus_name() {
        return status_name;
    }



    public String getPhoto_user() {
        return photo_user;
    }


    public String getName() {
        return name;
    }





}
