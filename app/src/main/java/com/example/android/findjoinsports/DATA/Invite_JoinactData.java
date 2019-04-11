package com.example.android.findjoinsports.DATA;

public class Invite_JoinactData {

    private int invite_id;
    private  int userid_invite;
    private  int user_id;
    private  int id;
    private  int number_join;

    private String name;
    private String photo;
    private String photo_user;
    private String stadium_name;
    private String date;
    private String time;
    private String status_id;
    private String status_name;


    public Invite_JoinactData(int invite_id, int userid_invite, int user_id ,int id ,int number_join , String name, String photo,String photo_user, String stadium_name, String date, String time, String status_id, String status_name) {
        this.invite_id = invite_id;
        this.userid_invite = userid_invite;
        this.user_id = user_id;
        this.id = id;
        this.number_join = number_join;
        this.name = name;
        this.photo = photo;

        this.photo_user = photo_user;
        this.stadium_name = stadium_name;
        this.date = date;
        this.time = time;
        this.status_id = status_id;
        this.status_name = status_name;


    }



    public int getInvite_id() {
        return invite_id;
    }
    public int getUserid_invite() {
        return userid_invite;
    }
    public int getUser_id() {
        return user_id;
    }
    public int getId() {
        return id;
    }

    public int getNumber_join() {
        return number_join;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public String getPhoto_user() {
        return photo_user;
    }



    public String getStadium_name() {
        return stadium_name;
    }


    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getStatus_id() {
        return status_id;
    }

    public String getStatus_name() {
        return status_name;
    }





}
