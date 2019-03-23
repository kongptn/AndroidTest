package com.example.android.findjoinsports;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Belal on 10/18/2017.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ProductViewHolder> {


    private Context mCtx;
    private List<RecyclerSearch> recyclerSearchList;

    public Adapter(Context mCtx, List<RecyclerSearch> recyclerSearchList) {
        this.mCtx = mCtx;
        this.recyclerSearchList = recyclerSearchList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_layout, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        RecyclerSearch recyclerSearch = recyclerSearchList.get(position);

        //loading the image
        String photo = "http://192.168.2.34/findjoinsport/football/"+recyclerSearch.getPhoto();
        if (photo.equalsIgnoreCase("")){
            photo = "Default";
        }
        Picasso.with(mCtx).load(photo).placeholder(R.drawable.ball).into(holder.imageView);
        Log.d("CheckPhoto",photo);
        holder.textViewStadiumName.setText(recyclerSearch.getStadium_name());
        holder.textViewName.setText(recyclerSearch.getName());
        holder.textViewDate.setText(recyclerSearch.getDate());
        holder.textViewTime.setText(recyclerSearch.getTime());

    }

    @Override
    public int getItemCount() {
        return recyclerSearchList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewStadiumName, textViewName, textViewDate, textViewTime;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewStadiumName = itemView.findViewById(R.id.textViewStadiumName);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            imageView = itemView.findViewById(R.id.imageView1);
        }
    }
}