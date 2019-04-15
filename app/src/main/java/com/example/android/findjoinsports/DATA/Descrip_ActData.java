package com.example.android.findjoinsports.DATA;

public class Descrip_ActData {
    private int req_id;
    private  int id;
    private  int userid_join;
    private String name;
    private String photo_user;




    public Descrip_ActData(int req_id,int id,int userid_join , String name, String photo_user) {
        this.id = id;
        this.photo_user = photo_user;
        this.name = name;
        this.req_id = req_id;
        this.userid_join = userid_join;


    }



    public int getId() {
        return id;
    }
    public int getReq_id() {
        return req_id;
    }
    public int getUserid_join() {
        return userid_join;
    }




    public String getPhoto_user() {
        return photo_user;
    }



    public String getName() {
        return name;
    }





}
