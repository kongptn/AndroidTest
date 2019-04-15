package com.example.android.findjoinsports.Adapter;

import android.content.Context;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;


import com.example.android.findjoinsports.DATA.Request_JoinData;
import com.example.android.findjoinsports.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_reqJoin extends RecyclerView.Adapter<Adapter_reqJoin.ReqjoinViewHolder> {

    private Context mCtx;
    private List<Request_JoinData> request_joinDataList;
    private OnItemClickListener listener_reqjoin;
//     public Adapter_reqJoin(Context mCtx, List<Request_JoinData> request_joinDataList,Adapter.OnItemClickListener reqjoin) {
//        this.mCtx = mCtx;
//        this.request_joinDataList = request_joinDataList;
//        this.listener_reqjoin = (OnItemClickListener) reqjoin;
//    }

    public Adapter_reqJoin(Context mCtx, List<Request_JoinData> request_joinDataList, OnItemClickListener onItemClickListener) {
        this.mCtx = mCtx;
        this.request_joinDataList = request_joinDataList;
        this.listener_reqjoin =  onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int id);
    }


    @Override
    public ReqjoinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_req_join, null);
        return new ReqjoinViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReqjoinViewHolder holder, final int position) {
        final Request_JoinData request_joinData = request_joinDataList.get(position);
        String photo_user = "http://192.168.2.37/android_register_login/"+request_joinData.getPhoto_user();
        if (photo_user.equalsIgnoreCase("")){
            photo_user = "Default";
        }
        Picasso.with(mCtx).load(photo_user).placeholder(R.drawable.n).into(holder.imgUser);

        holder.textViewStadiumName.setText(request_joinData.getStadium_name());
        holder.textViewName.setText(request_joinData.getName());
        holder.textViewDate.setText(request_joinData.getDate());
        holder.textViewTime.setText(request_joinData.getTime());
        holder.textViewStatus.setText(request_joinData.getStatus_name());

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener_reqjoin.onItemClick(request_joinData.getId());
                listener_reqjoin.onItemClick(request_joinData.getUser_id());
                listener_reqjoin.onItemClick(Integer.parseInt(request_joinData.getStatus_id()));
                listener_reqjoin.onItemClick(request_joinData.getReq_id());
                listener_reqjoin.onItemClick(request_joinData.getUser_create());

            }
        });
    }

    @Override
    public int getItemCount() {
        return request_joinDataList.size();
    }

    class ReqjoinViewHolder extends RecyclerView.ViewHolder {

        ImageView imgUser;
        LinearLayout rootView;
        TextView textViewName, textViewStadiumName, textViewDate, textViewTime, textViewStatus;


        public ReqjoinViewHolder(View itemView) {
            super(itemView);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewStadiumName = itemView.findViewById(R.id.textViewStadiumName);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            imgUser = itemView.findViewById(R.id.imgUser);
            rootView = itemView.findViewById(R.id.rootView);
        }
    }




}
