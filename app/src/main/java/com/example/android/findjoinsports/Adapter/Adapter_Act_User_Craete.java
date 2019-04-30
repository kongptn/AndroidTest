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
import android.widget.Toast;

import com.example.android.findjoinsports.Constants.ConstansAPI;
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
   // private final String status_id;

    public Adapter_Act_User_Craete(Context mCtx, List<Act_User_CreateData> act_user_createDataList, OnItemClickListener listener) {
        this.mCtx = mCtx;
        this.act_user_createDataList = act_user_createDataList;
        this.listener = listener;
      //  this.status_id = status_id;
      //  Log.d("sda",status_id);
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
        String photo = ConstansAPI.URL_PHOTO_ACT+act_user_createData.getPhoto();
        if (photo.equalsIgnoreCase("")){
            photo = "Default";
        }
        Picasso.with(mCtx).load(photo).placeholder(R.drawable.s).into(holder.imageView);

        String photo_user = ConstansAPI.URL_PHOTO_USER+act_user_createData.getPhoto_user();
        if (photo_user.equalsIgnoreCase("")){
            photo_user = "Default";
        }
        Picasso.with(mCtx).load(photo_user).placeholder(R.drawable.n).into(holder.imgUser);
        Log.d("CheckPhoto",photo);
        holder.textViewStadiumName.setText(act_user_createData.getStadium_name());
        holder.textViewName.setText(act_user_createData.getName());
        holder.textViewDate.setText(act_user_createData.getDate());
        holder.textViewTime.setText(act_user_createData.getTime());

        holder.status_close.setText(act_user_createData.getStatus_id());


//        if (status_id.equals("เปิดรับ")){
//            Log.d("ppopoo",status_id);
//            holder.status_close.setVisibility(View.GONE);
//        }



        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(act_user_createData.getId());
                //Toast.makeText(mCtx, status_id, Toast.LENGTH_SHORT).show();

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
        TextView status_close;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewStadiumName = itemView.findViewById(R.id.textViewStadiumName);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            imageView = itemView.findViewById(R.id.imageView1);
            status_close = itemView.findViewById(R.id.status_close);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            imgUser = itemView.findViewById(R.id.imgUser);
            rootView = itemView.findViewById(R.id.rootView);
        }
    }
}