package com.example.android.findjoinsports.SearchActivity;
import com.android.volley.AuthFailureError;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.location.LocationListener;
import android.location.LocationManager;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.findjoinsports.Adapter.Adapter_Comment;
import com.example.android.findjoinsports.Adapter.Recyclerview_Userjoinact;
import com.example.android.findjoinsports.Constants.ConstansAPI;
import com.example.android.findjoinsports.DATA.Comment_Data;
import com.example.android.findjoinsports.DATA.Descrip_ActData;
import com.example.android.findjoinsports.Friends_List_Invite;
import com.example.android.findjoinsports.NavDrawer;
import com.example.android.findjoinsports.R;
import com.example.android.findjoinsports.SessionManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DescriptionActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String URL_SHOW = "http://10.13.3.103/findjoinsport/search_activity/test.php";
    private static final String URL_REQ = "http://10.13.3.103/findjoinsport/request_joinact/request_joinact.php";
    private static final String URL_SHOW_USER = "http://10.13.3.103/findjoinsport/football/show_userjoin.php";
    private static final String URL_NOTI = "http://10.13.3.103/findjoinsport/android_register_login/push_notification.php";

    String Latitude;
    private GoogleMap mMap;
    String Longitude;
    double lat;
    double lng;

    PlaceAutocompleteFragment placeAutoComplete;
    private LocationManager locationManager;
    private LocationListener listener;
    double locationLong;
    double locationLat;
    String local;
    int id;
    int req_id;
    private Button btFinish;
    private String comment;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private Boolean mLocationPermissionsGranted = false;



    EditText edit_comment;
    ImageView image,imgUser,img;
    TextView tvUserName, tvStadium, tvPlace, tvDate, tvTime, tvDescript, tvLocation, tvNumJoin;
    String userid, mUser_id, status_id,User_id,mName,status_noti,numjoin,numjoindel;
    Button btn_join,btn_invite,back,btn_del,btn_wait,btn_comment;
    SessionManager sessionManager;

    List<Descrip_ActData> descrip_actDataList;
    List<Comment_Data> comment_dataList;
    //the recyclerview
    RecyclerView recyclerView;
    RecyclerView recyclerView_comment;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();
        mUser_id = user.get(sessionManager.USER_ID);
        mName = user.get(sessionManager.NAME);
        Log.d("id",mUser_id);
        edit_comment = findViewById(R.id.edit_comment);
        imgUser = findViewById(R.id.imgUser);
        image = findViewById(R.id.image);
        img = findViewById(R.id.img);
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
        status_noti= "N01";
        btn_comment = findViewById(R.id.btn_comment);
        btn_del = findViewById(R.id.btn_del);
        btn_wait = findViewById(R.id.btn_wait);
        Log.d("sss", String.valueOf(userid));


        onButtonClick(userid);
        showUserjoin(userid);
        showComment();
        check_friend();
        getUserDetail();

        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditText();
                insertcomment();
                finish();
                startActivity(getIntent());
            }
        });

        btn_wait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                del_friend();
                btn_wait.setEnabled(false);
                Intent i = new Intent(DescriptionActivity.this,NavDrawer.class);
                Toast.makeText(DescriptionActivity.this, "ยกเลิกการสมัครแล้ว", Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });

        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                del_friend();
                Update_numjoin();
                numjoindel = (numjoin + (-1));
                btn_del.setEnabled(false);
                Intent i = new Intent(DescriptionActivity.this,NavDrawer.class);
                Toast.makeText(DescriptionActivity.this, "ถอนตัวแล้ว", Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });

        btn_join = findViewById(R.id.btn_join);
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestJoin();
                sendNonti(User_id,mName+" ขอเข้าร่วมกิจกรรม");
                put_noti_sql(User_id,mUser_id);
                btn_join.setEnabled(false);
                Intent i = new Intent(DescriptionActivity.this,NavDrawer.class);
                Toast.makeText(DescriptionActivity.this, "สมัครกิจกรรมแล้ว", Toast.LENGTH_SHORT).show();
                startActivity(i);
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



        recyclerView_comment = findViewById(R.id.recyclerview_comment);
        recyclerView_comment.setHasFixedSize(true);
        recyclerView_comment.setLayoutManager(new LinearLayoutManager(DescriptionActivity.this));
        comment_dataList = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);


        recyclerView = findViewById(R.id.recyclerview_userjoin);
//        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        //initializing the productlist
        descrip_actDataList = new ArrayList<>();

    }


    private void onButtonClick(final String userid) {
//        if (!stadium_name.isEmpty() && !description.isEmpty()) {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.Host)+"/findjoinsport/search_activity/test.php", new Response.Listener<String>() {
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
                        String status_id = jObj.getString("status_id");
                        Log.d("stat",status_id);
                        numjoin = jObj.getString("number_join");
                        Log.d("sdk",numjoin);
                        User_id = jObj.getString("user_id");
                        Log.d("usss",User_id);
//                        if(jObj.getString("Photo_user") != null){String Photo_user = jObj.getString("Photo_user") ;}
                        String Photo_user = jObj.getString("photo_user");
                        String Latitude = jObj.getString("Latitude");
                        String Longitude = jObj.getString("Longitude");
                        // Log.d("pppphhhh",Photo_user);

                        double lat = Double.parseDouble(Latitude);
                        double lng = Double.parseDouble(Longitude);
                        LatLng LLgarage = new LatLng(lat, lng);
                        mMap.addMarker(new MarkerOptions().position(LLgarage));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(LLgarage));
                        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LLgarage, 14);
                        mMap.moveCamera(update);
                        mMap.getUiSettings().setScrollGesturesEnabled(true);
                        mMap.getUiSettings().setZoomGesturesEnabled(true);



                        tvUserName.setText(Name);
                        tvStadium.setText(Stadium_name);
                        tvDate.setText(Date);
                        tvTime.setText(Time);
                        tvDescript.setText(Description);
                        tvLocation.setText(Location);
                        tvNumJoin.setText(numjoin);

                        String photo = ConstansAPI.URL_PHOTO_ACT+Photo;
                        if (photo.equalsIgnoreCase("")){
                            photo = "Default";
                        }
                        Picasso.with(DescriptionActivity.this).load(photo).placeholder(R.drawable.s).into(image);

                        String photo_user = ConstansAPI.URL_PHOTO_USER+Photo_user;
                        if (photo_user.equalsIgnoreCase("")){
                            photo_user = "Default";
                        }
                        Picasso.with(DescriptionActivity.this).load(photo_user).placeholder(R.drawable.n).into(imgUser);
                        Log.d("mm", photo_user);

                        if (User_id.equals(mUser_id)){
                            btn_join.setVisibility(View.GONE);
                            btn_wait.setVisibility(View.GONE);
                            btn_del.setVisibility(View.GONE);
                        }

                        if (status_id.equals("ปิดรับแล้ว")){
                            btn_join.setVisibility(View.GONE);
                            btn_invite.setVisibility(View.GONE);
                        }

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

    private void onEditText() {

    }

    private boolean insertcomment() {
        comment = edit_comment.getText().toString();
        if (comment.isEmpty()){
            edit_comment.requestFocus();
            Toast.makeText(DescriptionActivity.this, "กรุณาใส่ข้อความ", Toast.LENGTH_SHORT).show();
            return false;
        }
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.Host) + "/findjoinsport/search_activity/insert_comment.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("onResponse", response);
                    edit_comment.setText("");

                    Toast.makeText(DescriptionActivity.this, "โพสต์แล้ว", Toast.LENGTH_SHORT).show();
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.d("Create Error", error.toString());
//                    Toast.makeText(CreateFootball.this, "เกิดข้อผิดพลาดโปรดลองอีกครั้ง", Toast.LENGTH_SHORT).show();
                    Toast.makeText(DescriptionActivity.this, "กรอกผิดแล้ว", Toast.LENGTH_SHORT).show();
                }

//                private Context getContext() {
//                    return null;
//                }
            }) {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("cm_data", comment);
                    params.put("id", userid);
                    params.put("user_id", mUser_id);

                    Log.d("pppd",comment + userid + mUser_id);

                    return params;
                }
            };
            requestQueue.add(request);
        return true;
    }


    private void showUserjoin(final String userid) {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.Host)+"/findjoinsport/football/show_userjoin.php",
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

    private void showComment() {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.Host)+"/findjoinsport/search_activity/show_comment.php",
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
                                int cm_id = object.getInt("cm_id");
                                int id = object.getInt("id");
                                int user_id = object.getInt("user_id");
                                String name = object.getString("name");
                                String photo_user = object.getString("photo_user");
                                String cm_data = object.getString("cm_data");
                                Log.d("comme",cm_data);


                                Comment_Data comment_data = new Comment_Data(cm_id, id, user_id, name, photo_user, cm_data);
                                comment_dataList.add(comment_data);
                            }

                            //creating adapter object and setting it to recyclerview
                            Adapter_Comment adapter_comment = new Adapter_Comment(comment_dataList, DescriptionActivity.this, new Adapter_Comment.OnItemClickListener() {

                                                            @Override
                                                            public void onItemClick(int id) {
                            //                                    Intent intent = new Intent(getContext(),DescriptionActivity.class);
                            //                                    intent.putExtra("id",String.valueOf(id));
                            //                                    Toast.makeText(getContext(), String.valueOf(id), Toast.LENGTH_SHORT).show();
                            //                                    startActivity(intent);
                                                            }
                                                        });
                            recyclerView_comment.setAdapter(adapter_comment);
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
                Log.d("popi",userid);

                return params;
            }

        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(DescriptionActivity.this).add(stringRequest);
    }

    private void sendRequestJoin() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.Host)+"/findjoinsport/request_joinact/request_joinact.php", new Response.Listener<String>() {
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

                params.put("user_create",User_id);
                Log.d("sdadoo",User_id);

                params.put("Notification", noti);
                Log.d("lksll",noti);





                return params;
            }

        };
        requestQueue.add(request);
    }

    private void put_noti_sql(final String User_id,final String mUser_id) {

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

                params.put("userid_join", mUser_id);
                Log.d("last",mUser_id);

                params.put("status_noti", status_noti);



                return params;
            }

        };
        requestQueue.add(request);
    }

    private void check_friend() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.Host)+"/findjoinsport/request_joinact/check_join.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("log",response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    req_id = Integer.parseInt(jObj.getString("req_id"));
                     id = Integer.parseInt(jObj.getString("id"));
                    int user_id = Integer.parseInt(jObj.getString("user_id"));
                    String status_id = jObj.getString("status_id");
                    Log.d("testeest",status_id);
                    String status_name = jObj.getString("status_name");

                    if (status_id.equals("J02")){
                        Log.d("JJJ",status_id);
                        btn_join.setVisibility(View.GONE);
                        btn_del.setVisibility(View.VISIBLE);
                    }

                    if (status_id.equals("J01")){
                        //Log.d("sdjosdf",status_id);
                        btn_join.setVisibility(View.GONE);
                        btn_wait.setVisibility(View.VISIBLE);
                    }

//                    if (status_id.equals("F01")){
//                        //Log.d("sdjosdf",status_id);
////                        btn_addfriends.setVisibility(View.GONE);
////                        btn_wait.setVisibility(View.VISIBLE);
//                    }


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

                params.put("id",userid);
                Log.d("idid",userid);

                params.put("user_id",mUser_id);
                Log.d("mkmk",mUser_id);

                return params;
            }

        };
        requestQueue.add(request);
    }

    private void del_friend() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.Host)+"/findjoinsport/request_joinact/delete_join.php", new Response.Listener<String>() {
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

                params.put("req_id", String.valueOf(req_id));
                Log.d("dedaa", String.valueOf(req_id));




                return params;
            }

        };
        requestQueue.add(request);
    }

    private void Update_numjoin() {

        RequestQueue requestQueue = Volley.newRequestQueue(DescriptionActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.Host)+"/findjoinsport/request_joinact/update_numberjoin.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

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

                params.put("id",userid);

                params.put("number_join", String.valueOf(numjoindel));
                Log.d("sd", String.valueOf(numjoindel));

                return params;
            }

        };

        //adding our stringrequest to queue
        requestQueue.add(stringRequest);
    }

    private void getUserDetail(){

//        final ProgressDialog progressDialog = new ProgressDialog(getContext());
//        progressDialog.setMessage("Loading...");
//        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.Host)+"/findjoinsport/android_register_login/read_detail.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        progressDialog.dismiss();
//                        Log.i(TAG,"response"+ response.toString());

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            //สร้างตัวแปนที่ชื่อตรงกับ array list ที่สางมา
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            // เช็คว่า success มรค่าเป็น 1 หรือเปล่าถ้าเป็นก็ทำตามเงื่อนไข
                            if (success.equals("1")){
                                // เอาตัว array มา for เพื่อเอาค่าเเก็บไว้ใน String
                                for (int i =0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);
                                    // มันคือค่าที่ ตัว arry วนเก็บไว้ใน string
                                    String strName = object.getString("name").trim();
                                    String strEmail = object.getString("email").trim();
                                    String strUser_firstname = object.getString("user_firstname").trim();
                                    String strUser_lastname = object.getString("user_lastname").trim();
                                    String strUser_age = object.getString("user_age").trim();
                                    String strUser_tel = object.getString("user_tel").trim();
                                    String strSex = object.getString("user_sex").trim();
                                    String strImgUrl = object.getString("photo_user").trim();


//                                    if (strSex.equalsIgnoreCase("Male")) {
//                                        mSexMale.setChecked(true);
//                                    } else if (strSex.equalsIgnoreCase("Female")) {
//                                        mSexFemale.setChecked(true);
//                                    }
//
//                                    // เอาค่าที่ได้ settext
//                                    name.setText(strName);
//                                    email.setText(strEmail);
//                                    user_firstname.setText(strUser_firstname);
//                                    user_lastname.setText(strUser_lastname);
//                                    user_age.setText(strUser_age);
//                                    user_tel.setText(strUser_tel);
////                                    user_sex.setText(strSex);

                                    String ph = ConstansAPI.URL_PHOTO_USER+strImgUrl;
                                    if (ph.equalsIgnoreCase("")){
                                        ph = "default";
                                    }
                                    Picasso.with(DescriptionActivity.this).load(ph).placeholder(R.drawable.n).into(img);
//


                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
//                            progressDialog.dismiss();


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        progressDialog.dismiss();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //ตัวหน้าตรงกับใน php ที่ีรับค่า ตัวหลังคือค่าที่ส่งไป
                params.put("user_id", mUser_id);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(DescriptionActivity.this);
        requestQueue.add(stringRequest);
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getApplicationContext(), R.raw.style_json));

            if (!success) {
                Log.e("test", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("test", "Can't find style. Error: ", e);
        }

    }
}





