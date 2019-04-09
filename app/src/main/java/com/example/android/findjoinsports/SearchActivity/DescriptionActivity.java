package com.example.android.findjoinsports.SearchActivity;
import com.android.volley.AuthFailureError;
import android.annotation.SuppressLint;
import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.findjoinsports.CreateActivity.CreateBasketball;
import com.example.android.findjoinsports.R;
import com.example.android.findjoinsports.SessionManager;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DescriptionActivity extends AppCompatActivity {

    private static final String URL_SHOW = "http://192.168.2.33/findjoinsport/search_activity/test.php";
    private static final String URL_REQ = "http://192.168.2.33/findjoinsport/request_joinact/request_joinact.php";


    ImageView image,imgUser;
    TextView tvUserName, tvStadium, tvPlace, tvDate, tvTime, tvDescript, tvLocation, tvNumJoin;
    String userid, mUser_id, status_id,User_id;
    Button btn_join;
    SessionManager sessionManager;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();
        mUser_id = user.get(sessionManager.USER_ID);
        Log.d("id",mUser_id);

        imgUser = findViewById(R.id.imgUser);
        image = findViewById(R.id.image);
        tvUserName = findViewById(R.id.tvUserName);
        tvStadium = findViewById(R.id.tvStadium);
        tvPlace = findViewById(R.id.tvPlace);
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        tvDescript = findViewById(R.id.tvDescript);
        tvLocation = findViewById(R.id.tvLocation);
        tvNumJoin = findViewById(R.id.tvNumJoin);
        userid = getIntent().getExtras().getString("id","");
        status_id = "J01";

        Log.d("sss", String.valueOf(userid));


        onButtonClick(userid);

        btn_join = findViewById(R.id.btn_join);
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestJoin();
                btn_join.setEnabled(false);
            }
        });
    }

    private void onButtonClick(final String userid) {
//        if (!stadium_name.isEmpty() && !description.isEmpty()) {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest request = new StringRequest(Request.Method.POST, URL_SHOW, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jObj = new JSONObject(response);
                        String Stadium_name = jObj.getString("stadium_name");
                        String Photo = jObj.getString("photo");
                        Log.d("pt",Photo);
                        String Date = jObj.getString("date");
                        String Time = jObj.getString("time");
                        String Name = jObj.getString("name");
                        String Location = jObj.getString("location");
                        String Description = jObj.getString("description");
                        String numjoin = jObj.getString("number_join");
                        User_id = jObj.getString("user_id");
                        Log.d("usss",User_id);
//                        if(jObj.getString("Photo_user") != null){String Photo_user = jObj.getString("Photo_user") ;}
                        String Photo_user = jObj.getString("photo_user");
                       // Log.d("pppphhhh",Photo_user);


                        tvUserName.setText(Name);
                        tvStadium.setText(Stadium_name);
                        tvDate.setText(Date);
                        tvTime.setText(Time);
                        tvDescript.setText(Description);
                        tvLocation.setText(Location);
                        tvNumJoin.setText(numjoin);

                        String photo = "http://192.168.2.33/findjoinsport/football/"+Photo;
                        if (photo.equalsIgnoreCase("")){
                            photo = "Default";
                        }

                        Picasso.with(DescriptionActivity.this).load(photo).placeholder(R.drawable.s).into(image);

                        String photo_user = "http://192.168.2.33/android_register_login/"+Photo_user;
                        if (photo_user.equalsIgnoreCase("")){
                            photo_user = "Default";
                        }

                        Picasso.with(DescriptionActivity.this).load(photo_user).placeholder(R.drawable.n).into(imgUser);

                        Log.d("mm", photo_user);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

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
                    params.put("id", userid);
                    Log.d("dddd", userid);
                    return params;
                }

            };
            requestQueue.add(request);
        }

    private void sendRequestJoin() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, URL_REQ, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("log",response.toString());
//                try {
//                    JSONObject jObj = new JSONObject(response);
//                    String Stadium_name = jObj.getString("stadium_name");
//                    String Photo = jObj.getString("photo");
//                    String Date = jObj.getString("date");
//                    String Time = jObj.getString("time");
//                    String Name = jObj.getString("name");
//                    String Location = jObj.getString("location");
//                    String Description = jObj.getString("description");
//
//
//
//                    tvUserName.setText(Name);
//                    tvStadium.setText(Stadium_name);
//                    tvDate.setText(Date);
//                    tvTime.setText(Time);
//                    tvDescript.setText(Description);
//                    tvLocation.setText(Location);
//
//                    String photo = "http://192.168.2.37/findjoinsport/football/"+Photo;
//                    if (photo.equalsIgnoreCase("")){
//                        photo = "Default";
//                    }
//
//                    Picasso.with(DescriptionActivity.this).load(photo).placeholder(R.drawable.s).into(image);
//
//                    Toast.makeText(DescriptionActivity.this, "ขอเข้าร่วมแล้ว", Toast.LENGTH_SHORT).show();
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

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
                params.put("id", userid);
                Log.d("dddd", userid);

                params.put("user_create",User_id);
                Log.d("lll",User_id);

                params.put("userid_join", mUser_id);
                Log.d("last",mUser_id);

                params.put("status_id", status_id);
                Log.d("sfas",status_id);


                return params;
            }

        };
        requestQueue.add(request);
    }
    }






//    private void onButtonClick() {
//
//        /*
//         * Creating a String Request
//         * The request type is GET defined by first parameter
//         * The URL is defined in the second parameter
//         * Then we have a Response Listener and a Error Listener
//         * In response listener we will get the JSON response as a String
//         * */
////        if (!userid.isEmpty()) {
////            RequestQueue requestQueue = Volley.newRequestQueue(this);
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_SHOW,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            //converting the string to json array object
//                            JSONArray array = new JSONArray(response);
//
//                            //traversing through all the object
//                            for (int i = 0; i < array.length(); i++) {
//
//                                //getting product object from json array
//                                JSONObject object = array.getJSONObject(i);
//
//                                //adding the product to product list
//                                userid = object.getInt("id");
////
//////                                String stadiumname = object.getString("stadium_name");
//                                Stadium_name = object.getString("stadium_name");
//
////                                Photo = Integer.parseInt(object.getString("photo"));
//                                Date = object.getString("date");
//                                Time = object.getString("time");
//                                Name = object.getString("name");
//                                Location = object.getString("location");
//                                Description = object.getString("description");
//
//                                tvStadium.setText(Stadium_name);
//                                Log.d("wddddw", String.valueOf((tvStadium)));
//                                tvDate.setText(Date);
//                                tvTime.setText(Time);
//                                tvUserName.setText(Name);
////                                tvPlace.setText(Location);
//                                tvDescript.setText(Description);
//
////
////                                RecyclerSearch recyclerSearch = new RecyclerSearch(id, stadiumname, photo, date, time, name, location, description);
////                                recyclerSearchList.add(recyclerSearch);
//                            }
//
//                            //creating adapter object and setting it to recyclerview
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
////                        Toast.makeText(DescriptionActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//
//        //adding our stringrequest to queue
//        Volley.newRequestQueue(this).add(stringRequest);
//    }

