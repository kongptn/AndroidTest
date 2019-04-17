package com.example.android.findjoinsports.SearchActivity;
import com.android.volley.AuthFailureError;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.example.android.findjoinsports.Adapter.Adapter;
import com.example.android.findjoinsports.Adapter.Adapter_ReqInvite_Joinact;
import com.example.android.findjoinsports.Adapter.Recyclerview_Userjoinact;
import com.example.android.findjoinsports.CreateActivity.CreateBasketball;
import com.example.android.findjoinsports.DATA.Descrip_ActData;
import com.example.android.findjoinsports.DATA.RecyclerSearch;
import com.example.android.findjoinsports.DATA.Request_Invite_JoinactData;
import com.example.android.findjoinsports.DATA.Request_JoinData_Creator;
import com.example.android.findjoinsports.Edit_Activity;
import com.example.android.findjoinsports.Friends_List;
import com.example.android.findjoinsports.Friends_List_Invite;
import com.example.android.findjoinsports.NavDrawer;
import com.example.android.findjoinsports.R;
import com.example.android.findjoinsports.SessionManager;
import com.example.android.findjoinsports.Show_Act_User;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DescriptionActivity extends AppCompatActivity {

    private static final String URL_SHOW = "http://10.13.3.135/findjoinsport/search_activity/test.php";
    private static final String URL_REQ = "http://10.13.3.135/findjoinsport/request_joinact/request_joinact.php";

    private static final String URL_SHOW_USER = "http://10.13.3.135/findjoinsport/football/show_userjoin.php";

    private static final String URL_NOTI = "http://10.13.3.135/android_register_login/push_notification.php";





    ImageView image,imgUser;
    TextView tvUserName, tvStadium, tvPlace, tvDate, tvTime, tvDescript, tvLocation, tvNumJoin;
    String userid, mUser_id, status_id,User_id;
    Button btn_join,btn_invite,back;
    SessionManager sessionManager;

    List<Descrip_ActData> descrip_actDataList;

    //the recyclerview
    RecyclerView recyclerView;
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
        showUserjoin(userid);

        btn_join = findViewById(R.id.btn_join);
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestJoin();
                sendNonti(User_id,"มีผู้ใช้ขอเข้าร่วมกิจกรรม");
                btn_join.setEnabled(false);
            }
        });

        btn_invite = findViewById(R.id.btn_invite);
        btn_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DescriptionActivity.this,Friends_List_Invite.class);
                i.putExtra("id",String.valueOf(userid));
                i.putExtra("user_id",String.valueOf(User_id));
                Toast.makeText(DescriptionActivity.this, String.valueOf(userid), Toast.LENGTH_SHORT).show();
                btn_invite.setEnabled(false);
                startActivity(i);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);


        recyclerView = findViewById(R.id.recyclerview_userjoin);
//        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);




        //initializing the productlist
        descrip_actDataList = new ArrayList<>();

        back = findViewById(R.id.back);
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

                        String photo = "http://10.13.3.135/findjoinsport/football/"+Photo;
                        if (photo.equalsIgnoreCase("")){
                            photo = "Default";
                        }

                        Picasso.with(DescriptionActivity.this).load(photo).placeholder(R.drawable.s).into(image);

                        String photo_user = "http://10.13.3.135/android_register_login/"+Photo_user;
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

    private void showUserjoin(final String userid) {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SHOW_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject object = array.getJSONObject(i);

                                //adding the product to product list
                                int req_id = object.getInt("req_id");
//                                int userid_add = object.getInt("userid_add");
                                int id = object.getInt("id");
                                int userid_join = object.getInt("userid_join");

//
                                String name = object.getString("name");
                                Log.d("naaa",name);
                                String photo_user = object.getString("photo_user");


                                Descrip_ActData descrip_actData = new Descrip_ActData(req_id, id, userid_join , name, photo_user);
                                descrip_actDataList.add(descrip_actData);
                            }

                            //creating adapter object and setting it to recyclerview
                            Recyclerview_Userjoinact recyclerview_userjoinact = new Recyclerview_Userjoinact(DescriptionActivity.this, descrip_actDataList, new Recyclerview_Userjoinact.OnItemClickListener() {

                                @Override
                                public void onItemClick(int id) {
//                                    Intent intent = new Intent(getContext(),DescriptionActivity.class);
//                                    intent.putExtra("id",String.valueOf(id));
//                                    Toast.makeText(getContext(), String.valueOf(id), Toast.LENGTH_SHORT).show();
//                                    startActivity(intent);
                                }
                            });
                            recyclerView.setAdapter(recyclerview_userjoinact);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DescriptionActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }){

            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", userid);

                return params;
            }

        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(DescriptionActivity.this).add(stringRequest);
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

    private void sendNonti(final String User_id,final String noti) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, URL_NOTI, new Response.Listener<String>() {
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

                params.put("Notification", noti);
                Log.d("lksll",noti);



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

                Intent back = new Intent(DescriptionActivity.this, NavDrawer.class);
                startActivity(back);

                return true;
            default:

                return super.onOptionsItemSelected(item);
        }


    }

    }





