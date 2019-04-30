package com.example.android.findjoinsports;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.example.android.findjoinsports.Constants.ConstansAPI;
import com.example.android.findjoinsports.SearchActivity.DescriptionActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class DetailsActivity_Request extends AppCompatActivity {

    String user_id, name, email, user_firstname, user_lastname, user_tel, user_age, user_sex,userid_add,user_join;
    TextView tvnameshow, tvemailshow, tvfirstnameshow, tvlastnameshow, tvtelshow, tvageshow, tvsexshow;
    private static String URL_READSHOW = "http://10.13.3.103/findjoinsport/android_register_login/ShowUsers.php";
    static String URL_ADDFRIENDS = "http://10.13.3.103/findjoinsport/friend/request_friends.php";
    ImageView profile_image;
    Button btn_accept,btn_not_accept;
    String getId,mName,rf_id,mUser_id;
    SessionManager sessionManager;
    String status_id = "F02";
    String status_noti = "N05";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__request);

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
//        btn_accept = findViewById(R.id.btn_accept);
//        btn_not_accept = findViewById(R.id.btn_not_accept);

        HashMap<String, String> user = sessionManager.getUserDetail();
        mUser_id = user.get(sessionManager.USER_ID);
        mName = user.get(sessionManager.NAME);

        user_id = getIntent().getExtras().getString("userid_add", "");
        rf_id = getIntent().getExtras().getString("rf_id", "");
        //user_id = getIntent().getExtras().getString("userid_join", "");
        Log.d("ppp", user_id);


//        btn_accept.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Button_Accept();
//                sendNonti(userid_add,mName+" ตอบรับเป็นเพื่อน");
//                put_noti_sql(userid_add,mUser_id);
//                Toast.makeText(DetailsActivity_Request.this, "success", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        btn_not_accept.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                del_friend();
//                Toast.makeText(DetailsActivity_Request.this, "ลบคำขอแล้ว", Toast.LENGTH_SHORT).show();
//            }
//        });



        onShow(user_id);

    }
    private void onShow(final String user_id) {
//        if (!stadium_name.isEmpty() && !description.isEmpty()) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.Host)+"/findjoinsport/android_register_login/ShowUsers.php", new Response.Listener<String>() {
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
                    else ph = ConstansAPI.URL_PHOTO_USER+photo_user;


                    if (ph.equalsIgnoreCase("")){
                        ph = "default";
                    }
                    Picasso.with(DetailsActivity_Request.this).load(ph).into(profile_image);



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

//    private void sendNonti(final String userid_add,final String noti) {
//
//        RequestQueue requestQueue = Volley.newRequestQueue(DetailsActivity_Request.this);
//        StringRequest request = new StringRequest(Request.Method.POST, ConstansAPI.URL_NOTI, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.d("log",response.toString());
//
//
//            }
//
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                // Posting params to login url
//                Map<String, String> params = new HashMap<String, String>();
//
//                params.put("user_create",userid_add);
////                Log.d("sdadoo",userid_add);
//
//                params.put("Notification", noti);
//                Log.d("lksll",noti);
//
//
//
//
//
//                return params;
//            }
//
//        };
//        requestQueue.add(request);
//    }
//
//    private void put_noti_sql(final String userid_add,final String mUser_id) {
//
//        RequestQueue requestQueue = Volley.newRequestQueue(DetailsActivity_Request.this);
//        StringRequest request = new StringRequest(Request.Method.POST, ConstansAPI.URL_DIA_PUT_NOTI_SQL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.d("log",response.toString());
//
//
//            }
//
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                // Posting params to login url
//                Map<String, String> params = new HashMap<String, String>();
//
//                params.put("user_create",userid_add);
////                Log.d("sdadoo",userid_add);
//
//                params.put("userid_join", mUser_id);
//                Log.d("last",mUser_id);
//
//                params.put("status_noti", status_noti);
//
//
//
//                return params;
//            }
//
//        };
//        requestQueue.add(request);
//    }
//
//    private void del_friend() {
//
//        RequestQueue requestQueue = Volley.newRequestQueue(DetailsActivity_Request.this);
//        StringRequest request = new StringRequest(Request.Method.POST,ConstansAPI.URL_DIA_DEL_REQ_FRIEND, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.d("log",response.toString());
//            }
//
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                // Posting params to login url
//                Map<String, String> params = new HashMap<String, String>();
//
//                params.put("rf_id", String.valueOf(rf_id));
//                Log.d("dell", String.valueOf(rf_id));
//
//
//
//
//                return params;
//            }
//
//        };
//        requestQueue.add(request);
//    }
//
//    private void Button_Accept() {
//
//        RequestQueue requestQueue = Volley.newRequestQueue(DetailsActivity_Request.this);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstansAPI.URL_DIA_UPDATE_REQFRIEND,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                    }
//
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(DetailsActivity_Request.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//
//                    }
//                }){
//
//            protected Map<String, String> getParams() {
//                // Posting params to login url
//                Map<String, String> params = new HashMap<String, String>();
//
//                params.put("rf_id",rf_id);
//
//
//                params.put("status_id",status_id);
//                Log.d("st",rf_id + status_id);
//
//                return params;
//            }
//
//        };
//
//        //adding our stringrequest to queue
//        requestQueue.add(stringRequest);
//    }
//
//






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_action, menu);

        Menu action = menu;
        action.findItem(R.id.menu_save).setVisible(false);
        action.findItem(R.id.menu_edit).setVisible(false);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_back:

                Intent back = new Intent(DetailsActivity_Request.this, NavDrawer.class);
                startActivity(back);

                return true;
            default:

                return super.onOptionsItemSelected(item);
        }


    }

}
