package com.example.android.findjoinsports.Adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.findjoinsports.DATA.Act_User_CreateData;
import com.example.android.findjoinsports.R;
import com.example.android.findjoinsports.DATA.RecyclerSearch;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Belal on 10/18/2017.
 */

public class Adapter_Act_User_Craete extends RecyclerView.Adapter<Adapter_Act_User_Craete.ProductViewHolder> {


    private Context mCtx;
    private List<Act_User_CreateData> act_user_createDataList;
    private OnItemClickListener listener;

    public Adapter_Act_User_Craete(Context mCtx, List<Act_User_CreateData> act_user_createDataList, OnItemClickListener listener) {
        this.mCtx = mCtx;
        this.act_user_createDataList = act_user_createDataList;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int id);
    }


    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_act_user_create, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, final int position) {
        final Act_User_CreateData act_user_createData = act_user_createDataList.get(position);

        //loading the image
        String photo = "http://10.13.3.135/findjoinsport/football/"+act_user_createData.getPhoto();
        if (photo.equalsIgnoreCase("")){
            photo = "Default";
        }
        Picasso.with(mCtx).load(photo).placeholder(R.drawable.s).into(holder.imageView);

        String photo_user = "http://10.13.3.135/android_register_login/"+act_user_createData.getPhoto_user();
        if (photo_user.equalsIgnoreCase("")){
            photo_user = "Default";
        }
        Picasso.with(mCtx).load(photo_user).placeholder(R.drawable.n).into(holder.imgUser);
        Log.d("CheckPhoto",photo);
        holder.textViewStadiumName.setText(act_user_createData.getStadium_name());
        holder.textViewName.setText(act_user_createData.getName());
        holder.textViewDate.setText(act_user_createData.getDate());
        holder.textViewTime.setText(act_user_createData.getTime());


        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(act_user_createData.getId());
            }
        });
        // holder.id.setText(recyclerSearch.getId());

        final String userid = String.valueOf(act_user_createData.getId());


        //holder.textViewLocation.setText(recyclerSearch.getLocation());
        //holder.textViewDescriptrion.setText(recyclerSearch.getDescription());


       /* holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx,DescriptionActivity.class);
                intent.putExtra("photo",recyclerSearch.getPhoto());
                intent.putExtra("textViewStadiumName",recyclerSearch.getStadium_name());
                intent.putExtra("textViewName",recyclerSearch.getName());
                intent.putExtra("textViewDate",recyclerSearch.getDate());
                intent.putExtra("textViewTime",recyclerSearch.getTime());
                intent.putExtra("textViewLocation",recyclerSearch.getLocation());
                intent.putExtra("textViewDescriptrion",recyclerSearch.getDescription());
                intent.putExtra("id",recyclerSearch.getId());

                mCtx.startActivity(intent);

            }
        });
*/
    }

    @Override
    public int getItemCount() {
        return act_user_createDataList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout parentLayout;
        TextView textViewStadiumName, textViewName, textViewDate, textViewTime, textViewLocation, textViewDescriptrion;
        ImageView imageView, imgUser;
        LinearLayout rootView;


        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewStadiumName = itemView.findViewById(R.id.textViewStadiumName);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            imageView = itemView.findViewById(R.id.imageView1);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            imgUser = itemView.findViewById(R.id.imgUser);
            rootView = itemView.findViewById(R.id.rootView);
        }
    }
}