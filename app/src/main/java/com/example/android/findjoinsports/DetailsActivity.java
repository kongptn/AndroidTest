package com.example.android.findjoinsports;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class DetailsActivity extends AppCompatActivity {

    String user_id, name, email, user_firstname, user_lastname, user_tel, user_age, user_sex,userid_add,user_join;
    TextView tvnameshow, tvemailshow, tvfirstnameshow, tvlastnameshow, tvtelshow, tvageshow, tvsexshow;
    private static String URL_READSHOW = "http://192.168.2.37/android_register_login/ShowUsers.php";
     static String URL_ADDFRIENDS = "http://192.168.2.37/findjoinsport/friend/request_friends.php";
    ImageView profile_image;
    Button btn_addfriends;
    String getId;
    SessionManager sessionManager;
    String status_id= "F01";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        profile_image = findViewById(R.id.profile_image);

        tvnameshow= findViewById(R.id.tvnameshow);
        tvemailshow= findViewById(R.id.tvemailshow);
        tvfirstnameshow= findViewById(R.id.tvfirstnameshow);
        tvlastnameshow= findViewById(R.id.tvlastnameshow);
        tvtelshow= findViewById(R.id.tvtelshow);
        tvageshow= findViewById(R.id.tvageshow);
        tvsexshow= findViewById(R.id.tvsexshow);
        btn_addfriends = findViewById(R.id.btn_addfriends);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.USER_ID);

        user_id = getIntent().getExtras().getString("user_id", "");
        //user_id = getIntent().getExtras().getString("userid_join", "");
        Log.d("ppp", user_id);

        btn_addfriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestAdd();
              //  sendRequestAdd_2();
                btn_addfriends.setEnabled(false);
                Toast.makeText(DetailsActivity.this,"ส่งคำขอแล้ว", Toast.LENGTH_SHORT).show();
            }
        });
        onShow(user_id);

    }
    private void onShow(final String user_id) {
//        if (!stadium_name.isEmpty() && !description.isEmpty()) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, URL_READSHOW, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    String name = jObj.getString("name");
                    String email = jObj.getString("email");
                    String user_firstname = jObj.getString("user_firstname");
                    String user_lastname = jObj.getString("user_lastname");
                    String user_tel = jObj.getString("user_tel");
                    String user_age = jObj.getString("user_age");
                    String user_sex = jObj.getString("user_sex");
                    String photo_user = jObj.getString("photo_user");

                    tvnameshow.setText(name);
                    tvemailshow.setText(email);
                    tvfirstnameshow.setText(user_firstname);
                    tvlastnameshow.setText(user_lastname);
                    tvtelshow.setText(user_tel);
                    tvageshow.setText(user_age);
                    tvsexshow.setText(user_sex);

                    String reg = "[0-9]{16}";
                    String temp = photo_user;
                    String ph = "";
                    boolean matches = Pattern.matches(reg, temp);
                    if (matches)
                        ph = "https://graph.facebook.com/" +photo_user + "/picture?width=250&height=250";
                    else ph = "http://192.168.2.37/android_register_login/"+photo_user;


                    if (ph.equalsIgnoreCase("")){
                        ph = "default";
                    }
                    Picasso.with(DetailsActivity.this).load(ph).into(profile_image);



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
                Map<String, String> params = new HashMap<>();
                params.put("user_id", user_id);
                Log.d("www", user_id);



                return params;
            }

        };
        requestQueue.add(request);


    }

    private void sendRequestAdd() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, URL_ADDFRIENDS, new Response.Listener<String>() {
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

                params.put("user_id", user_id);
                Log.d("dddd", user_id);



                params.put("status_id",status_id);
                Log.d("lll",status_id);

                params.put("userid_add", getId);
                Log.d("last",getId);


                return params;
            }

        };
        requestQueue.add(request);
    }

}
