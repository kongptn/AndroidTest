package com.example.android.findjoinsports.DATA;

public class Comment_Data {
    private int cm_id;
    private  int id;
    private  int user_id;
    // private String noti_text;
    private String name;
    private String photo_user;
    private String cm_data;






    public Comment_Data(int cm_id, int id,int user_id,String name, String photo_user, String cm_data) {
        this.cm_id = cm_id;
        this.id = id;
        this.user_id = user_id;
        this.name = name;
        this.photo_user = photo_user;
        this.cm_data = cm_data;





    }



    public int getCm_id() {
        return cm_id;
    }
    public int getId() {
        return id;
    }


    public int getUser_id() {
        return user_id;
    }




    public String getName() {
        return name;
    }

    public String getPhoto_user() {
        return photo_user;
    }

    public String getCm_data() {
        return cm_data;
    }


}
