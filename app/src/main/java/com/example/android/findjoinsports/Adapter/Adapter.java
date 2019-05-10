package com.example.android.findjoinsports.Adapter;
import android.content.Context;
import android.graphics.Color;
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
import com.example.android.findjoinsports.R;
import com.example.android.findjoinsports.DATA.RecyclerSearch;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Belal on 10/18/2017.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ProductViewHolder> {


    private Context mCtx;
    private List<RecyclerSearch> recyclerSearchList;
    private OnItemClickListener listener;
  private final String status_id;

     int actid;
    String status_close;

    public Adapter(Context mCtx, List<RecyclerSearch> recyclerSearchList,String status_id,int id,OnItemClickListener listener) {
        this.mCtx = mCtx;
        this.recyclerSearchList = recyclerSearchList;
        this.listener = listener;
        this.status_id = status_id;
        this.actid = id;
        Log.d("dddssdd",status_id + actid);
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
    public void onBindViewHolder(final ProductViewHolder holder, final int position) {
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
        holder.status_close.setText(recyclerSearch.getStatus_id());



//
//
            String date_act = recyclerSearch.getDate();
            Log.d("daa", date_act);

            Calendar calendar = Calendar.getInstance();
            String currentDate = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());
            Log.d("cs", currentDate);
            Log.d("sdda", recyclerSearch.getDate());

            Log.d("dsad", date_act + currentDate);

            if (date_act.compareTo(currentDate) <0){
                holder.status_close.setText("กิจกรรมนี้ผ่านไปแล้ว");
            }



//        String dateStr = "04/05/2010";
//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
//        String strDate = curFormater.format(calendar.getTime());
//        SimpleDateFormat postFormater = new SimpleDateFormat("MMMM dd, yyyy");
//
//        //String newDateStr = postFormater.format(dateObj);
//try {
//    Date dateObj = curFormater.parse(strDate);
//    Date open = curFormater.parse(recyclerSearch.getDate());
//
//    if (dateObj.after(open)){
//        holder.status_close.setText("close");
//    }
//
//}catch (ParseException e){
//    e.printStackTrace();
//}

//
//            String openTime = recyclerSearch.getDate();
//           // String closeTime = recyclerSearch.get(position).getClose();
//
//            Calendar calendar = Calendar.getInstance();
//            SimpleDateFormat DateFormat = new SimpleDateFormat("HH:mm:ss");
//            String strDate = DateFormat.format(calendar.getTime());
//           // String strDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
//            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
//
//            try {
//                Date current = dateFormat.parse(strDate);
//                Date open = dateFormat.parse(recyclerSearch.getDate());
//               // Date close = dateFormat.parse(itemList.get(position).getClose());
//              //  Log.d("timeget", itemList.get(position).getGaragename() + String.valueOf(open));
//               // Log.d("timegetclose", itemList.get(position).getGaragename() + String.valueOf(close));
//              //  Log.d("timegetcurrent", itemList.get(position).getGaragename() + String.valueOf(current));
//
//                if (current.after(open)) {
//                    holder.status_close.setText("close");
//                   // viewHolder.tvOpen.setBackgroundColor(Color.parseColor("#66CC66"));
//                }
////                } else if (openTime.equals("00:00:00") && closeTime.equals("00:00:00")) {
////                    viewHolder.tvOpen.setText("ไม่ได้ลงทะเบียน");
////                    viewHolder.tvOpen.setBackgroundColor(Color.parseColor("#6699FF"));
////                } else {
////                    viewHolder.tvOpen.setText("Close");
////                    viewHolder.tvOpen.setBackgroundColor(Color.parseColor("#FF7F50"));
////                }
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }

//        else {
//            viewHolder.tvOpen.setText("Close");
//            viewHolder.tvOpen.setBackgroundColor(Color.parseColor("#FF7F50"));
//        }
//




        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String status_actt = holder.status_close.getText().toString();
                if (!status_actt.equals("กิจกรรมนี้ผ่านไปแล้ว")){
                    listener.onItemClick(recyclerSearch.getId());
                }
                else {
                    Toast.makeText(mCtx, "no", Toast.LENGTH_SHORT).show();

                }
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
        TextView status_close;


        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewStadiumName = itemView.findViewById(R.id.textViewStadiumName);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            imageView = itemView.findViewById(R.id.imageView1);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            status_close = itemView.findViewById(R.id.status_close);
            imgUser = itemView.findViewById(R.id.imgUser);
            rootView = itemView.findViewById(R.id.rootView);
        }
    }
}