package com.example.android.findjoinsports.DATA;

    public class Notification_Data {
    private int noti_id;
    private  int user_send;
    private  int user_get;
   // private String noti_text;
        private String name;
        private String photo_user;

        private String status_id;
        private String status_name;






        public Notification_Data(int noti_id, int user_send,int user_get,String name, String photo_user, String status_id, String status_name) {
        this.noti_id = noti_id;
        this.user_send = user_send;
        this.user_get = user_get;
            this.name = name;
            this.photo_user = photo_user;

            this.status_id = status_id;
            this.status_name = status_name;




        }



    public int getNoti_id() {
        return noti_id;
    }
    public int getUser_send() {
        return user_send;
    }


    public int getUser_get() {
        return user_get;
    }




        public String getName() {
            return name;
        }

        public String getPhoto_user() {
            return photo_user;
        }

        public String getStatus_id() {
            return status_id;
        }

        public String getStatus_name() {
            return status_name;
        }

}
