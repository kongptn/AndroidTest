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
import com.example.android.findjoinsports.CreateActivity.CreateBB_Gun;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class DetailsActivity extends AppCompatActivity {

    String user_id, name, email, user_firstname, user_lastname, user_tel, user_age, user_sex,userid_add,user_join;
    TextView tvnameshow, tvemailshow, tvfirstnameshow, tvlastnameshow, tvtelshow, tvageshow, tvsexshow;
    private static String URL_READSHOW = "http://10.13.3.103/findjoinsport/android_register_login/ShowUsers.php";
    static String URL_ADDFRIENDS = "http://10.13.3.103/findjoinsport/friend/request_friends.php";
    ImageView profile_image;
    Button btn_addfriends,btn_delfriends,btn_wait;
    String getId,mName;
    SessionManager sessionManager;
    String status_id= "F01";
    String status_noti = "N04";
    int rf_id;

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
        btn_delfriends = findViewById(R.id.btn_delfriends);
        btn_wait = findViewById(R.id.btn_wait);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.USER_ID);
        mName = user.get(sessionManager.NAME);

        user_id = getIntent().getExtras().getString("user_id", "");
        //user_id = getIntent().getExtras().getString("userid_join", "");
        Log.d("ppp", user_id + getId);

        if (getId.equals(user_id)){
            btn_addfriends.setVisibility(View.GONE);
            btn_wait.setVisibility(View.GONE);
            btn_delfriends.setVisibility(View.GONE);
        }


        btn_addfriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestAdd();
                sendNonti(user_id,mName+"ขอเป็นเพื่อน");
                put_noti_sql(user_id,getId);
                //  sendRequestAdd_2();
                btn_addfriends.setVisibility(View.GONE);
                btn_wait.setVisibility(View.VISIBLE);
                Toast.makeText(DetailsActivity.this,"ส่งคำขอแล้ว", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());
                //  btn_addfriends.setVisibility(View.GONE);
                // btn_delfriends.setVisibility(View.VISIBLE);
                // btn_delfriends.setVisibility(View.GONE);
                // btn_delfriends.setVisibility(View.INVISIBLE);
            }
        });

        btn_wait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                del_friend(rf_id);
                btn_wait.setVisibility(View.GONE);
                Log.d("checkValue", String.valueOf(rf_id));
                btn_addfriends.setVisibility(View.VISIBLE);

                Toast.makeText(DetailsActivity.this,"ยกเลิกคำขอแล้ว", Toast.LENGTH_SHORT).show();


            }
        });

        btn_delfriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                del_friend(rf_id);
                btn_delfriends.setEnabled(false);
                Toast.makeText(DetailsActivity.this,"ลบเพื่อนแล้ว", Toast.LENGTH_SHORT).show();

            }
        });
        onShow(user_id);
        check_friend();
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
    private void check_friend() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.Host)+"/findjoinsport/friend/check_friend.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("log",response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    rf_id = Integer.parseInt(jObj.getString("rf_id"));
                    int userid = Integer.parseInt(jObj.getString("user_id"));
                    String status_id = jObj.getString("status_id");
                    Log.d("sdjosdf",status_id);
                    String status_name = jObj.getString("status_name");

                    if (status_id.equals("F02")){
                        //Log.d("sdjosdf",status_id);
                        btn_addfriends.setVisibility(View.GONE);
                        btn_delfriends.setVisibility(View.VISIBLE);
                    }

                    if (status_id.equals("F01")){
                        //Log.d("sdjosdf",status_id);
                        btn_addfriends.setVisibility(View.GONE);
                        btn_wait.setVisibility(View.VISIBLE);
                    }

//                    if (user_id == Integer.parseInt(getId)){
//                        btn_addfriends.setVisibility(View.GONE);
//                        btn_wait.setVisibility(View.GONE);
//                        btn_delfriends.setVisibility(View.GONE);
//                    }
                    if (getId.equals(user_id)){
                        btn_addfriends.setVisibility(View.GONE);
                        btn_wait.setVisibility(View.GONE);
                        btn_delfriends.setVisibility(View.GONE);
                    }


//


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

                params.put("user_id",getId);
                Log.d("sdadoo",getId);

                params.put("user_get",user_id);



                return params;
            }

        };
        requestQueue.add(request);

    }

    private void sendRequestAdd() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.Host)+"/findjoinsport/friend/request_friends.php", new Response.Listener<String>() {
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

    private void del_friend(final int rf_id) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.Host)+"/findjoinsport/friend/delete_friend.php", new Response.Listener<String>() {
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

                params.put("rf_id", String.valueOf(rf_id));
                Log.d("dell", String.valueOf(rf_id));




                return params;
            }

        };
        requestQueue.add(request);

    }

    private void sendNonti(final String user_id,final String noti) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.Host)+"/findjoinsport/android_register_login/push_notification.php", new Response.Listener<String>() {
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

                params.put("user_create",user_id);
                Log.d("sdadoo",user_id);

                params.put("Notification", noti);
                Log.d("lksll",noti);




                return params;
            }

        };
        requestQueue.add(request);
    }

    private void put_noti_sql(final String User_id,final String getId) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.Host)+"/findjoinsport/android_register_login/insert_noti_sql.php", new Response.Listener<String>() {
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

                params.put("user_create",User_id);
                Log.d("sdadoo",User_id);

                params.put("userid_join", getId);
                Log.d("last",getId);

                params.put("status_noti", status_noti);



                return params;
            }

        };
        requestQueue.add(request);
    }

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

                Intent back = new Intent(DetailsActivity.this, NavDrawer.class);
                startActivity(back);

                return true;
            default:

                return super.onOptionsItemSelected(item);
        }


    }

}
