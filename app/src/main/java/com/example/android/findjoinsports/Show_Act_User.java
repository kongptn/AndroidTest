package com.example.android.findjoinsports;
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

public class Show_Act_User extends AppCompatActivity {

    private static final String URL_SHOW = "http://10.13.4.158/findjoinsport/search_activity/test.php";
    private static final String URL_DEL = "http://10.13.4.158/findjoinsport/football/delete_act.php";

    private static final String URL_DEL_REQ = " http://10.13.4.158/findjoinsport/football/delete_reqjoin.php";




    ImageView image,imgUser;
    TextView tvUserName, tvStadium, tvPlace, tvDate, tvTime, tvDescript, tvLocation, tvNumJoin;
    String userid, mUser_id, status_id,User_id;
    Button btn_join;
    SessionManager sessionManager;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__act__user);

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
                Delete_act();
                Delete_req();
                Toast.makeText(Show_Act_User.this, "ลบกิจกรรมแล้ว", Toast.LENGTH_SHORT).show();
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

                    String photo = "http://10.13.4.158/findjoinsport/football/"+Photo;
                    if (photo.equalsIgnoreCase("")){
                        photo = "Default";
                    }

                    Picasso.with(Show_Act_User.this).load(photo).placeholder(R.drawable.s).into(image);

                    String photo_user = "http://10.13.4.158/android_register_login/"+Photo_user;
                    if (photo_user.equalsIgnoreCase("")){
                        photo_user = "Default";
                    }

                    Picasso.with(Show_Act_User.this).load(photo_user).placeholder(R.drawable.n).into(imgUser);

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

    private void Delete_act() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, URL_DEL, new Response.Listener<String>() {
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
                params.put("id", userid);
                Log.d("dddd", userid);

                return params;
            }
        };
        requestQueue.add(request);
    }

    private void Delete_req() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, URL_DEL_REQ, new Response.Listener<String>() {
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
                params.put("id", userid);
                Log.d("dddd", userid);

                return params;
            }
        };
        requestQueue.add(request);
    }
}







