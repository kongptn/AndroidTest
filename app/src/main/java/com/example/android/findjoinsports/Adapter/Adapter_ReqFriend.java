package com.example.android.findjoinsports.Adapter;

import android.app.Dialog;
import android.content.Context;

import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.findjoinsports.Constants.ConstansAPI;
import com.example.android.findjoinsports.DATA.Request_FriendData;
import com.example.android.findjoinsports.R;
import com.example.android.findjoinsports.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Adapter_ReqFriend extends RecyclerView.Adapter<Adapter_ReqFriend.ReqjoinViewHolder> {
    String mUser_id,rf_id,userid_add,mName;
    String status_id = "F02";
    String status_noti = "N05";
    private Context mCtx;
    private List<Request_FriendData> request_friendList;
    private OnItemClickListener listener_reqjoin;

    SessionManager sessionManager;
    Dialog myDialog;
//     public Adapter_reqJoin(Context mCtx, List<Request_JoinData> request_joinDataList,Adapter.OnItemClickListener reqjoin) {
//        this.mCtx = mCtx;
//        this.request_joinDataList = request_joinDataList;
//        this.listener_reqjoin = (OnItemClickListener) reqjoin;
//    }

    public Adapter_ReqFriend(Context mCtx, List<Request_FriendData> request_friendList, OnItemClickListener onItemClickListener) {
        this.mCtx = mCtx;
        this.request_friendList = request_friendList;
        this.listener_reqjoin =  onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int id);
    }


    @Override
    public ReqjoinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_reqfriend, null);
        sessionManager = new SessionManager(mCtx);
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();
        mUser_id = user.get(sessionManager.USER_ID);
        mName = user.get(sessionManager.NAME);
        Log.d("id",mUser_id);

        return new ReqjoinViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReqjoinViewHolder holder, final int position) {
        final Request_FriendData request_friend = request_friendList.get(position);
        String photo_user = ConstansAPI.URL_PHOTO_USER+request_friend.getPhoto_user();
        if (photo_user.equalsIgnoreCase("")){
            photo_user = "Default";
        }
        Picasso.with(mCtx).load(photo_user).placeholder(R.drawable.n).into(holder.images);


        holder.name.setText(request_friend.getName());

        holder.textViewStatus.setText(request_friend.getStatus_name());

        myDialog = new Dialog(mCtx);
        myDialog.setContentView(R.layout.dialog_reqfriends);


        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                listener_reqjoin.onItemClick(request_friend.getId());
                listener_reqjoin.onItemClick(request_friend.getUser_id());
                listener_reqjoin.onItemClick(request_friend.getUserid_add());
             //   listener_reqjoin.onItemClick(Integer.parseInt(request_friend.getStatus_id()));
                listener_reqjoin.onItemClick(request_friend.getRf_id());
//                listener_reqjoin.onItemClick(request_friend.getUser_create());

//                actid = String.valueOf((request_friend.getId()));
//
//                numjoin = Integer.parseInt(String.valueOf((request_friend.getNumber_join())));
//
                rf_id = String.valueOf((request_friend.getRf_id()));
                Log.d("rfid",rf_id);

                userid_add= String.valueOf((request_friend.getUserid_add()));
//
//                user_join = String.valueOf((request_friend.getUserid_join()));
//                Log.d("fdf", user_join);

                TextView dialog_tv = (TextView) myDialog.findViewById(R.id.dialog_tv);
                ImageButton bt_not_accept = (ImageButton)myDialog.findViewById(R.id.bt_not_accept);

                bt_not_accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        del_friend();
                        Toast.makeText(mCtx, "ลบคำขอแล้ว", Toast.LENGTH_SHORT).show();
                    }
                });



                ImageView imgDialog = (ImageView)myDialog.findViewById(R.id.imgDialog);
                dialog_tv.setText(request_friend.getName());
                String photo_user = ConstansAPI.URL_PHOTO_USER+request_friend.getPhoto_user();
                if (photo_user.equalsIgnoreCase("")){
                    photo_user = "Default";
                }
                Picasso.with(mCtx).load(photo_user).placeholder(R.drawable.n).into(imgDialog);

                ImageButton bt_accept = (ImageButton)myDialog.findViewById(R.id.bt_accept);
                bt_accept.setOnClickListener(new View.OnClickListener() {

                    // String act_id = String.valueOf((request_joinData_creator.getId()));


                    @Override
                    public void onClick(View v) {
//                        Intent intent = new Intent(mCtx,DescriptionActivity.class);
                        //  intent.putExtra("userid_join",request_joinData_creator.getUserid_join());
                        Button_Accept();
                        sendNonti(userid_add,mName+" ตอบรับเป็นเพื่อน");
                        put_noti_sql(userid_add,mUser_id);
//                        Update_numjoin();

                        //numjoin ++;
//                        Intent intent = new Intent(mCtx,DescriptionActivity.class);
//                        intent.putExtra("id",String.valueOf(id));
                        // mCtx.startActivity(intent);
                        Toast.makeText(mCtx, "success", Toast.LENGTH_SHORT).show();
                        // Toast.makeText(mCtx, String.valueOf(id), Toast.LENGTH_SHORT).show();
                    }

                });
                myDialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return request_friendList.size();
    }

    class ReqjoinViewHolder extends RecyclerView.ViewHolder {


        TextView name, email, textViewStatus;
        ImageView images;
        LinearLayout rootView;

        public ReqjoinViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            images = itemView.findViewById(R.id.images);
            rootView = itemView.findViewById(R.id.rootView);
        }
    }

    private void Button_Accept() {

        RequestQueue requestQueue = Volley.newRequestQueue(mCtx);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstansAPI.URL_DIA_UPDATE_REQFRIEND,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mCtx, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }){

            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("rf_id",rf_id);
                Log.d("req", rf_id);

                params.put("status_id",status_id);
                Log.d("st",status_id);

                return params;
            }

        };

        //adding our stringrequest to queue
        requestQueue.add(stringRequest);
    }

    private void del_friend() {

        RequestQueue requestQueue = Volley.newRequestQueue(mCtx);
        StringRequest request = new StringRequest(Request.Method.POST,ConstansAPI.URL_DIA_DEL_REQ_FRIEND, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("log",response.toString());
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("rf_id", String.valueOf(rf_id));
                Log.d("dell", String.valueOf(rf_id));




                return params;
            }

        };
        requestQueue.add(request);
    }

    private void sendNonti(final String userid_add,final String noti) {

        RequestQueue requestQueue = Volley.newRequestQueue(mCtx);
        StringRequest request = new StringRequest(Request.Method.POST, ConstansAPI.URL_NOTI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("log",response.toString());


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_create",userid_add);
                Log.d("sdadoo",userid_add);

                params.put("Notification", noti);
                Log.d("lksll",noti);





                return params;
            }

        };
        requestQueue.add(request);
    }

    private void put_noti_sql(final String userid_add,final String mUser_id) {

        RequestQueue requestQueue = Volley.newRequestQueue(mCtx);
        StringRequest request = new StringRequest(Request.Method.POST, ConstansAPI.URL_DIA_PUT_NOTI_SQL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("log",response.toString());


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_create",userid_add);
                Log.d("sdadoo",userid_add);

                params.put("userid_join", mUser_id);
                Log.d("last",mUser_id);

                params.put("status_noti", status_noti);



                return params;
            }

        };
        requestQueue.add(request);
    }



}
