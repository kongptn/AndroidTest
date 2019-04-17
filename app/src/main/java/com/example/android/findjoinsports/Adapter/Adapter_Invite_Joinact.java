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

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.findjoinsports.DATA.Invite_JoinactData;
import com.example.android.findjoinsports.DATA.List_FriendData;
import com.example.android.findjoinsports.DATA.Request_FriendData;
import com.example.android.findjoinsports.R;
import com.example.android.findjoinsports.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Adapter_Invite_Joinact extends RecyclerView.Adapter<Adapter_Invite_Joinact.ReqjoinViewHolder> {
    String mUser_id,rf_id,invid,actid;
    String status_id = "J02";
    int numjoin = 1;

    private static final String URL_DIA = "http://10.13.3.135/findjoinsport/friend/update_inviteact.php";
    private static final String URL_numjoin = "http://10.13.3.135/findjoinsport/request_joinact/update_numberjoin.php";
    private Context mCtx;
    private List<Invite_JoinactData> invite_joinactDataList;
    private OnItemClickListener listener_reqjoin;

    SessionManager sessionManager;
    Dialog myDialog;
//     public Adapter_reqJoin(Context mCtx, List<Request_JoinData> request_joinDataList,Adapter.OnItemClickListener reqjoin) {
//        this.mCtx = mCtx;
//        this.request_joinDataList = request_joinDataList;
//        this.listener_reqjoin = (OnItemClickListener) reqjoin;
//    }

    public Adapter_Invite_Joinact(Context mCtx, List<Invite_JoinactData> invite_joinactDataList, OnItemClickListener onItemClickListener) {
        this.mCtx = mCtx;
        this.invite_joinactDataList = invite_joinactDataList;
        this.listener_reqjoin =  onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int id);
    }


    @Override
    public ReqjoinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_invite_joinact, null);
        sessionManager = new SessionManager(mCtx);
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();
        mUser_id = user.get(sessionManager.USER_ID);
        Log.d("id",mUser_id);

        return new ReqjoinViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReqjoinViewHolder holder, final int position) {
        final Invite_JoinactData invite_joinactData = invite_joinactDataList.get(position);
//        String photo_user = "http://10.13.1.19/android_register_login/"+invite_joinactData.getPhoto_user();
//        if (photo_user.equalsIgnoreCase("")){
//            photo_user = "Default";
//        }
//        Picasso.with(mCtx).load(photo_user).placeholder(R.drawable.n).into(holder.images);
//
//
//        holder.name.setText(invite_joinactData.getName());

        //loading the image
        String photo = "http://10.13.3.135/findjoinsport/football/"+invite_joinactData.getPhoto();
        if (photo.equalsIgnoreCase("")){
            photo = "Default";
        }
        Picasso.with(mCtx).load(photo).placeholder(R.drawable.s).into(holder.imageView);

        String photo_user = "http://10.13.3.135/android_register_login/"+invite_joinactData.getPhoto_user();
        if (photo_user.equalsIgnoreCase("")){
            photo_user = "Default";
        }
        Picasso.with(mCtx).load(photo_user).placeholder(R.drawable.n).into(holder.imgUser);
        Log.d("CheckPhoto",photo);
        holder.textViewStadiumName.setText(invite_joinactData.getStadium_name());
        holder.textViewName.setText(invite_joinactData.getName());
        holder.textViewDate.setText(invite_joinactData.getDate());
        holder.textViewTime.setText(invite_joinactData.getTime());
        holder.textViewStatus.setText(invite_joinactData.getStatus_name());

      //  holder.tvn.setText(invite_joinactData.getName());



//        myDialog = new Dialog(mCtx);
//        myDialog.setContentView(R.layout.dialog_invitejoin);


        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   listener_reqjoin.onItemClick(invite_joinactData.getId());
                //listener_reqjoin.onItemClick(invite_joinactData.getUser_id());
                //   listener_reqjoin.onItemClick(list_friendData.getUserid_add());
                //   listener_reqjoin.onItemClick(Integer.parseInt(request_friend.getStatus_id()));
               // listener_reqjoin.onItemClick(invite_joinactData.getRf_id());
//                listener_reqjoin.onItemClick(request_friend.getUser_create());
//                   invid = String.valueOf((invite_joinactData.getInvite_id()));
//                    actid = String.valueOf((invite_joinactData.getId()));
//
//                numjoin = Integer.parseInt(String.valueOf((invite_joinactData.getNumber_join())));
//
               // rf_id = String.valueOf((invite_joinactData.getRf_id()));
//
//                user_join = String.valueOf((request_friend.getUserid_join()));
//                Log.d("fdf", user_join);

//                TextView dialog_tv = (TextView) myDialog.findViewById(R.id.dialog_tv);
//                ImageButton bt_not_accept = (ImageButton)myDialog.findViewById(R.id.bt_not_accept);
//
//
//
//                ImageView imgDialog = (ImageView)myDialog.findViewById(R.id.imgDialog);
//                dialog_tv.setText(invite_joinactData.getName());
//                String photo_user = "http://192.168.2.34/android_register_login/"+invite_joinactData.getPhoto_user();
//                if (photo_user.equalsIgnoreCase("")){
//                    photo_user = "Default";
//                }
//                Picasso.with(mCtx).load(photo_user).placeholder(R.drawable.n).into(imgDialog);
//
//                ImageButton bt_accept = (ImageButton)myDialog.findViewById(R.id.bt_accept);
//                bt_accept.setOnClickListener(new View.OnClickListener() {
//
//                    // String act_id = String.valueOf((request_joinData_creator.getId()));
//
//
//                    @Override
//                    public void onClick(View v) {
////                        Intent intent = new Intent(mCtx,DescriptionActivity.class);
//                        //  intent.putExtra("userid_join",request_joinData_creator.getUserid_join());
//                        Button_Accept();
//                        Update_numjoin();
////                        Update_numjoin();
//                        numjoin ++;
////                        Intent intent = new Intent(mCtx,DescriptionActivity.class);
////                        intent.putExtra("id",String.valueOf(id));
//                        // mCtx.startActivity(intent);
//                        Toast.makeText(mCtx, "เชิญเข้าร่วมแล้ว", Toast.LENGTH_SHORT).show();
//                        // Toast.makeText(mCtx, String.valueOf(id), Toast.LENGTH_SHORT).show();
//                    }
//
//                });
//                myDialog.show();


            }
        });
    }

    @Override
    public int getItemCount() {
        return invite_joinactDataList.size();
    }

    class ReqjoinViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout parentLayout;
        TextView textViewStadiumName, textViewName, textViewDate, textViewTime, textViewLocation, textViewDescriptrion,textViewStatus,tvn;
        ImageView imageView, imgUser;
        LinearLayout rootView;

        public ReqjoinViewHolder(View itemView) {
            super(itemView);
            textViewStadiumName = itemView.findViewById(R.id.textViewStadiumName);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewTime = itemView.findViewById(R.id.textViewTime);
           // tvn = itemView.findViewById(R.id.tvn);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            imageView = itemView.findViewById(R.id.imageView1);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            imgUser = itemView.findViewById(R.id.imgUser);
            rootView = itemView.findViewById(R.id.rootView);
        }
    }

    private void Button_Accept() {

        RequestQueue requestQueue = Volley.newRequestQueue(mCtx);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DIA,
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

                params.put("invite_id",invid);
                Log.d("req", invid);

                params.put("status_id",status_id);
                Log.d("st",status_id);

                return params;
            }

        };

        //adding our stringrequest to queue
        requestQueue.add(stringRequest);
    }

    private void Update_numjoin() {

        RequestQueue requestQueue = Volley.newRequestQueue(mCtx);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_numjoin,
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

                params.put("id",actid);

                params.put("number_join", String.valueOf(numjoin));
                Log.d("sd", String.valueOf(numjoin));

                return params;
            }

        };

        //adding our stringrequest to queue
        requestQueue.add(stringRequest);
    }
}
