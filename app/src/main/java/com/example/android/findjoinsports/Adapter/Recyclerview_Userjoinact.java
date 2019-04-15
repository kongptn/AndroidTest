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
import com.example.android.findjoinsports.DATA.Descrip_ActData;
import com.example.android.findjoinsports.DATA.List_FriendData;
import com.example.android.findjoinsports.DATA.Request_FriendData;
import com.example.android.findjoinsports.DATA.Request_JoinData_Creator;
import com.example.android.findjoinsports.DetailsActivity;
import com.example.android.findjoinsports.R;
import com.example.android.findjoinsports.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recyclerview_Userjoinact extends RecyclerView.Adapter<Recyclerview_Userjoinact.ReqjoinViewHolder> {
    String mUser_id,rf_id,user_join;
    String status_id = "F02";
    //private static final String URL_DIA = "http://192.168.2.37/findjoinsport/friend/update_reqfriend.php";
    private Context mCtx;
    private List<Descrip_ActData> descrip_actDataList;
    private OnItemClickListener listener_reqjoin;

    SessionManager sessionManager;

//     public Adapter_reqJoin(Context mCtx, List<Request_JoinData> request_joinDataList,Adapter.OnItemClickListener reqjoin) {
//        this.mCtx = mCtx;
//        this.request_joinDataList = request_joinDataList;
//        this.listener_reqjoin = (OnItemClickListener) reqjoin;
//    }

    public Recyclerview_Userjoinact(Context mCtx, List<Descrip_ActData> descrip_actDataList, OnItemClickListener onItemClickListener) {
        this.mCtx = mCtx;
        this.descrip_actDataList = descrip_actDataList;
        this.listener_reqjoin =  onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int id);
    }


    @Override
    public ReqjoinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_user_joinact, null);
        sessionManager = new SessionManager(mCtx);
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();
        mUser_id = user.get(sessionManager.USER_ID);
        Log.d("id",mUser_id);

        return new ReqjoinViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReqjoinViewHolder holder, final int position) {
        final Descrip_ActData descrip_actData = descrip_actDataList.get(position);
        String photo_user = "http://192.168.2.37/android_register_login/"+descrip_actData.getPhoto_user();
        if (photo_user.equalsIgnoreCase("")){
            photo_user = "Default";
        }
        Picasso.with(mCtx).load(photo_user).placeholder(R.drawable.n).into(holder.images);


        holder.name.setText(descrip_actData.getName());




        holder.images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_join = String.valueOf((descrip_actData.getUserid_join()));
                Intent i = new Intent(mCtx,DetailsActivity.class);
                i.putExtra("user_id",String.valueOf(user_join));
                Toast.makeText(mCtx, String.valueOf(user_join), Toast.LENGTH_SHORT).show();
                mCtx.startActivity(i);




            }
        });
    }

    @Override
    public int getItemCount() {
        return descrip_actDataList.size();
    }

    class ReqjoinViewHolder extends RecyclerView.ViewHolder {


        TextView name, email;
        ImageView images;
        LinearLayout rootView;

        public ReqjoinViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            images = itemView.findViewById(R.id.image_view);
            rootView = itemView.findViewById(R.id.rootView);
        }
    }




}
