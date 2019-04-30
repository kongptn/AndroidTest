package com.example.android.findjoinsports.Adapter;

import android.app.Dialog;
import android.content.Context;

import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.android.findjoinsports.DATA.List_FriendData;
import com.example.android.findjoinsports.DATA.Request_FriendData;
import com.example.android.findjoinsports.DATA.Request_Invite_JoinactData;
import com.example.android.findjoinsports.R;
import com.example.android.findjoinsports.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Adapter_ReqInvite_Joinact extends RecyclerView.Adapter<Adapter_ReqInvite_Joinact.ReqjoinViewHolder> {
    private final String act_id,user_create;
    String mUser_id,rf_id,user_id,mName;
    String status_id = "J01";

    String status_noti = "N03";

    private Context mCtx;
    private List<Request_Invite_JoinactData> request_invite_joinactDataList;
    private OnItemClickListener listener_reqjoin;


    SessionManager sessionManager;
    Dialog myDialog;

//     public Adapter_reqJoin(Context mCtx, List<Request_JoinData> request_joinDataList,Adapter.OnItemClickListener reqjoin) {
//        this.mCtx = mCtx;
//        this.request_joinDataList = request_joinDataList;
//        this.listener_reqjoin = (OnItemClickListener) reqjoin;
//    }

    public Adapter_ReqInvite_Joinact(Context mCtx, List<Request_Invite_JoinactData> request_invite_joinactDataList,String act_id,String user_create, OnItemClickListener onItemClickListener) {
        this.mCtx = mCtx;
        this.request_invite_joinactDataList = request_invite_joinactDataList;
        this.listener_reqjoin =  onItemClickListener;
        this.act_id = act_id;
        Log.d("avtttttt",act_id);
        this.user_create = user_create;
    }

    public interface OnItemClickListener {
        void onItemClick(int id);
    }


    @Override
    public ReqjoinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_reqinvite_joinact, null);
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
        final Request_Invite_JoinactData request_invite_joinactData = request_invite_joinactDataList.get(position);
        String photo_user = ConstansAPI.URL_PHOTO_USER+request_invite_joinactData.getPhoto_user();
        if (photo_user.equalsIgnoreCase("")){
            photo_user = "Default";
        }
        Picasso.with(mCtx).load(photo_user).placeholder(R.drawable.n).into(holder.images);


        holder.name.setText(request_invite_joinactData.getName());


        myDialog = new Dialog(mCtx);
        myDialog.setContentView(R.layout.dialog_req_invitejoin);

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                listener_reqjoin.onItemClick(request_friend.getId());
                listener_reqjoin.onItemClick(request_invite_joinactData.getUser_id());
                //   listener_reqjoin.onItemClick(list_friendData.getUserid_add());
                //   listener_reqjoin.onItemClick(Integer.parseInt(request_friend.getStatus_id()));
                listener_reqjoin.onItemClick(request_invite_joinactData.getRf_id());
//                listener_reqjoin.onItemClick(request_friend.getUser_create());

//                actid = String.valueOf((request_friend.getId()));
//
//                numjoin = Integer.parseInt(String.valueOf((request_friend.getNumber_join())));
//
                rf_id = String.valueOf((request_invite_joinactData.getRf_id()));

                user_id = String.valueOf((request_invite_joinactData.getUser_id()));
//
//                user_join = String.valueOf((request_friend.getUserid_join()));
//                Log.d("fdf", user_join);
                TextView dialog_tv = (TextView) myDialog.findViewById(R.id.dialog_tv);
                Button bt_not_accept = (Button)myDialog.findViewById(R.id.bt_not_accept);
                bt_not_accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });



                ImageView imgDialog = (ImageView)myDialog.findViewById(R.id.imgDialog);
                dialog_tv.setText(request_invite_joinactData.getName());
                String photo_user = ConstansAPI.URL_PHOTO_USER+request_invite_joinactData.getPhoto_user();
                if (photo_user.equalsIgnoreCase("")){
                    photo_user = "Default";
                }
                Picasso.with(mCtx).load(photo_user).placeholder(R.drawable.n).into(imgDialog);

                Button bt_accept = (Button)myDialog.findViewById(R.id.bt_accept);
                bt_accept.setOnClickListener(new View.OnClickListener() {

                    // String act_id = String.valueOf((request_joinData_creator.getId()));


                    @Override
                    public void onClick(View v) {
//                        Intent intent = new Intent(mCtx,DescriptionActivity.class);
                        //  intent.putExtra("userid_join",request_joinData_creator.getUserid_join());
                        Button_Accept();
                        sendNonti(user_id,mName+" เชิญให้เข้าร่วมกิจกรรม");
                        put_noti_sql(user_id,mUser_id);
//                        Update_numjoin();
                        //numjoin ++;
//                        Intent intent = new Intent(mCtx,DescriptionActivity.class);
//                        intent.putExtra("id",String.valueOf(id));
                        // mCtx.startActivity(intent);
                        Toast.makeText(mCtx, "เชิญเข้าร่วมแล้ว", Toast.LENGTH_SHORT).show();
                        myDialog.dismiss();
                        // Toast.makeText(mCtx, String.valueOf(id), Toast.LENGTH_SHORT).show();
                    }

                });
                myDialog.show();



            }
        });
    }

    @Override
    public int getItemCount() {
        return request_invite_joinactDataList.size();
    }

    class ReqjoinViewHolder extends RecyclerView.ViewHolder {


        TextView name, email;
        ImageView images;
        LinearLayout rootView;

        public ReqjoinViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            images = itemView.findViewById(R.id.images);
            rootView = itemView.findViewById(R.id.rootView);
        }
    }
    private void Button_Accept() {

        RequestQueue requestQueue = Volley.newRequestQueue(mCtx);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstansAPI.URL_DIA_INV_JOIN,
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

                params.put("userid_invite",mUser_id);
                Log.d("invite", mUser_id);

                params.put("id",act_id);
                Log.d("ididididididdd",act_id);

                params.put("user_get",user_id);
                Log.d("ididididididdd",user_id);

                params.put("user_create",user_create);
                Log.d("ppp",user_create);


                return params;
            }

        };

        //adding our stringrequest to queue
        requestQueue.add(stringRequest);
    }

    private void sendNonti(final String user_id,final String noti) {

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

                params.put("user_create",user_id);
                Log.d("sdadoo",user_id);

                params.put("Notification", noti);
                Log.d("lksll",noti);



                return params;
            }

        };
        requestQueue.add(request);
    }

    private void put_noti_sql(final String user_id,final String mUser_id) {

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

                params.put("user_create",user_id);
                Log.d("sdadoo",user_id);

                params.put("userid_join", mUser_id);
                Log.d("last",mUser_id);

                params.put("status_noti", status_noti);



                return params;
            }

        };
        requestQueue.add(request);
    }




}
