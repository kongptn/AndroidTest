package com.example.android.findjoinsports.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.android.findjoinsports.Constants.ConstansAPI;
import com.example.android.findjoinsports.DATA.Request_JoinData_Creator;
import com.example.android.findjoinsports.DetailsActivity;
import com.example.android.findjoinsports.R;
import com.example.android.findjoinsports.SearchActivity.DescriptionActivity;
import com.example.android.findjoinsports.SessionManager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Adapter_redJoin_Creator extends RecyclerView.Adapter<Adapter_redJoin_Creator.ReqjoinViewHolder> {

    private static final String URL_DIA = "http://10.13.3.103/findjoinsport/request_joinact/update_req.php";
    private static final String URL_numjoin = "http://10.13.3.103/findjoinsport/request_joinact/update_numberjoin.php";
    private static final String URL_NOTI = "http://10.13.3.103/android_register_login/push_notification.php";
    String mUser_id,user_join,actid,reqid,user_id,mName;
    int numjoin = 1;
    String status_noti = "N02";
    String status_id = "J02";

    private Context mCtx;
    private List<Request_JoinData_Creator> request_joinData_creatorList;
    private OnItemClickListener listener_reqjoin;
    SessionManager sessionManager;
    Dialog myDialog;
//     public Adapter_reqJoin(Context mCtx, List<Request_JoinData> request_joinDataList,Adapter.OnItemClickListener reqjoin) {
//        this.mCtx = mCtx;
//        this.request_joinDataList = request_joinDataList;
//        this.listener_reqjoin = (OnItemClickListener) reqjoin;
//    }

    public Adapter_redJoin_Creator(Context mCtx, List<Request_JoinData_Creator> request_joinData_creatorList, OnItemClickListener onItemClickListener) {
        this.mCtx = mCtx;
        this.request_joinData_creatorList = request_joinData_creatorList;
        this.listener_reqjoin =  onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int id);

    }


    @Override
    public ReqjoinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_req_join_creator, null);
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
        final Request_JoinData_Creator request_joinData_creator = request_joinData_creatorList.get(position);
        String photo_user = ConstansAPI.URL_PHOTO_USER+request_joinData_creator.getPhoto_user();
        if (photo_user.equalsIgnoreCase("")){
            photo_user = "Default";
        }
        Picasso.with(mCtx).load(photo_user).placeholder(R.drawable.n).into(holder.imgUser);



        holder.textViewStadiumName.setText(request_joinData_creator.getStadium_name());
        holder.textViewName.setText(request_joinData_creator.getName());
        holder.textViewDate.setText(request_joinData_creator.getDate());
        holder.textViewTime.setText(request_joinData_creator.getTime());
        holder.textViewStatus.setText(request_joinData_creator.getStatus_name());



        myDialog = new Dialog(mCtx);
        myDialog.setContentView(R.layout.dialog_reqjoin);


        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener_reqjoin.onItemClick(request_joinData_creator.getId());
                listener_reqjoin.onItemClick(request_joinData_creator.getUser_id());
              //  listener_reqjoin.onItemClick(Integer.parseInt(request_joinData_creator.getStatus_id()));
                listener_reqjoin.onItemClick(request_joinData_creator.getReq_id());
                //listener_reqjoin.onItemClick(request_joinData_creator.getUserid_join());

                actid = String.valueOf((request_joinData_creator.getId()));

                numjoin = Integer.parseInt(String.valueOf((request_joinData_creator.getNumber_join())));

                reqid = String.valueOf((request_joinData_creator.getReq_id()));

                user_join = String.valueOf((request_joinData_creator.getUserid_join()));
                 Log.d("fdf", user_join);

                TextView dialog_tv = (TextView) myDialog.findViewById(R.id.dialog_tv);
                ImageButton bt_not_accept = (ImageButton)myDialog.findViewById(R.id.bt_not_accept);



                ImageView imgDialog = (ImageView)myDialog.findViewById(R.id.imgDialog);
                dialog_tv.setText(request_joinData_creator.getName());
                String photo_user = ConstansAPI.URL_PHOTO_USER+request_joinData_creator.getPhoto_user();
                if (photo_user.equalsIgnoreCase("")){
                    photo_user = "Default";
                }
                Picasso.with(mCtx).load(photo_user).placeholder(R.drawable.n).into(imgDialog);

                ImageButton bt_accept = (ImageButton)myDialog.findViewById(R.id.bt_accept);
                bt_accept.setOnClickListener(new View.OnClickListener() {

                   // String act_id = String.valueOf((request_joinData_creator.getId()));


                    @Override
                    public void onClick(View v) {
//                        Intent intent = new Intent(mCtx,DescriptionActivity.class);
                      //  intent.putExtra("userid_join",request_joinData_creator.getUserid_join());
                        Button_Accept();
                        Update_numjoin();

                         numjoin ++;

                        sendNonti(user_join,mName+" ตอบรับเข้าร่วมกิจกรรม");
                        put_noti_sql(user_join,mUser_id);
//                        Intent intent = new Intent(mCtx,DescriptionActivity.class);
//                        intent.putExtra("id",String.valueOf(id));
                        // mCtx.startActivity(intent);
                        Toast.makeText(mCtx, "success", Toast.LENGTH_SHORT).show();
                       // Toast.makeText(mCtx, String.valueOf(id), Toast.LENGTH_SHORT).show();
                    }

                });
                myDialog.show();
            }


        });


        holder.imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_join = String.valueOf((request_joinData_creator.getUserid_join()));
                Intent i = new Intent(mCtx,DetailsActivity.class);
                i.putExtra("user_id",String.valueOf(user_join));
                Toast.makeText(mCtx, String.valueOf(user_join), Toast.LENGTH_SHORT).show();
                mCtx.startActivity(i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return request_joinData_creatorList.size();
    }

    class ReqjoinViewHolder extends RecyclerView.ViewHolder {

        ImageView imgUser;
        LinearLayout rootView,sssssss;
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


    private void Button_Accept() {

        RequestQueue requestQueue = Volley.newRequestQueue(mCtx);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstansAPI.URL_DIA_UPDATE_REQ,
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

                params.put("req_id",reqid);
                Log.d("req", reqid);

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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstansAPI.URL_UPDATE_numjoin,
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

    private void sendNonti(final String user_join,final String noti) {

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

                params.put("user_create",user_join);
                Log.d("sdadoo",user_join);

                params.put("Notification", noti);
                Log.d("lksll",noti);



                return params;
            }

        };
        requestQueue.add(request);
    }

    private void put_noti_sql(final String user_join,final String mUser_id) {

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

                params.put("user_create",user_join);
                Log.d("sdadoo",user_join);

                params.put("userid_join", mUser_id);
                Log.d("last",mUser_id);

                params.put("status_noti", status_noti);



                return params;
            }

        };
        requestQueue.add(request);
    }

}
