package com.example.android.findjoinsports.Adapter;

import android.app.Dialog;
import android.content.Context;

import android.content.Intent;
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
import com.example.android.findjoinsports.DATA.List_FriendData;
import com.example.android.findjoinsports.DATA.Request_FriendData;
import com.example.android.findjoinsports.DetailsActivity;
import com.example.android.findjoinsports.Friends_List;
import com.example.android.findjoinsports.R;
import com.example.android.findjoinsports.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Adapter_List_Friends extends RecyclerView.Adapter<Adapter_List_Friends.ReqjoinViewHolder> {
    String mUser_id,rf_id,user_id;
    String status_id = "F02";
    //private static final String URL_DIA = "http://192.168.2.37/findjoinsport/friend/update_reqfriend.php";
    private Context mCtx;
    private List<List_FriendData> list_friendDataListi;
    private OnItemClickListener listener_reqjoin;

    SessionManager sessionManager;

//     public Adapter_reqJoin(Context mCtx, List<Request_JoinData> request_joinDataList,Adapter.OnItemClickListener reqjoin) {
//        this.mCtx = mCtx;
//        this.request_joinDataList = request_joinDataList;
//        this.listener_reqjoin = (OnItemClickListener) reqjoin;
//    }

    public Adapter_List_Friends(Context mCtx, List<List_FriendData> list_friendDataListi, OnItemClickListener onItemClickListener) {
        this.mCtx = mCtx;
        this.list_friendDataListi = list_friendDataListi;
        this.listener_reqjoin =  onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int user_id);
    }


    @Override
    public ReqjoinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_list_friend, null);
        sessionManager = new SessionManager(mCtx);
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();
        mUser_id = user.get(sessionManager.USER_ID);
        Log.d("id",mUser_id);

        return new ReqjoinViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReqjoinViewHolder holder, final int position) {
        final List_FriendData list_friendData = list_friendDataListi.get(position);
        String photo_user = ConstansAPI.URL_PHOTO_USER+list_friendData.getPhoto_user();
        if (photo_user.equalsIgnoreCase("")){
            photo_user = "Default";
        }
        Picasso.with(mCtx).load(photo_user).placeholder(R.drawable.n).into(holder.images);


        holder.name.setText(list_friendData.getName());




        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                listener_reqjoin.onItemClick(request_friend.getId());
                listener_reqjoin.onItemClick(list_friendData.getUser_id());
             //   listener_reqjoin.onItemClick(list_friendData.getUserid_add());
                //   listener_reqjoin.onItemClick(Integer.parseInt(request_friend.getStatus_id()));
                //listener_reqjoin.onItemClick(list_friendData.getRf_id());
//                listener_reqjoin.onItemClick(request_friend.getUser_create());
                user_id = String.valueOf((list_friendData.getUser_id()));
                Intent i = new Intent(mCtx,DetailsActivity.class);
                i.putExtra("user_id",String.valueOf(user_id));
                Toast.makeText(mCtx, String.valueOf(user_id), Toast.LENGTH_SHORT).show();
                mCtx.startActivity(i);

//                actid = String.valueOf((request_friend.getId()));
//
//                numjoin = Integer.parseInt(String.valueOf((request_friend.getNumber_join())));
//
              //  rf_id = String.valueOf((list_friendData.getRf_id()));


//                user_join = String.valueOf((request_friend.getUserid_join()));
//                Log.d("fdf", user_join);




            }
        });
    }

    @Override
    public int getItemCount() {
        return list_friendDataListi.size();
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




}
