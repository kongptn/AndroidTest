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

import com.example.android.findjoinsports.Constants.ConstansAPI;
import com.example.android.findjoinsports.R;
import com.example.android.findjoinsports.DATA.RecyclerSearch;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Belal on 10/18/2017.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ProductViewHolder> {


    private Context mCtx;
    private List<RecyclerSearch> recyclerSearchList;
    private OnItemClickListener listener;

    public Adapter(Context mCtx, List<RecyclerSearch> recyclerSearchList,OnItemClickListener listener) {
        this.mCtx = mCtx;
        this.recyclerSearchList = recyclerSearchList;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int id);
    }


    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_layout, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, final int position) {
        final RecyclerSearch recyclerSearch = recyclerSearchList.get(position);

        //loading the image
        String photo = ConstansAPI.URL_PHOTO_ACT+recyclerSearch.getPhoto();
        if (photo.equalsIgnoreCase("")){
            photo = "Default";
        }
        Picasso.with(mCtx).load(photo).placeholder(R.drawable.s).into(holder.imageView);

        String photo_user = ConstansAPI.URL_PHOTO_USER+recyclerSearch.getPhoto_user();
        if (photo_user.equalsIgnoreCase("")){
            photo_user = "Default";
        }
        Picasso.with(mCtx).load(photo_user).placeholder(R.drawable.n).into(holder.imgUser);
        Log.d("CheckPhoto",photo);
        holder.textViewStadiumName.setText(recyclerSearch.getStadium_name());
        holder.textViewName.setText(recyclerSearch.getName());
        holder.textViewDate.setText(recyclerSearch.getDate());
        holder.textViewTime.setText(recyclerSearch.getTime());


        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(recyclerSearch.getId());
            }
        });
       // holder.id.setText(recyclerSearch.getId());

        final String userid = String.valueOf(recyclerSearch.getId());


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
        return recyclerSearchList.size();
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