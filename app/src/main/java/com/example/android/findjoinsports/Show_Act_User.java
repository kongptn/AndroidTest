package com.example.android.findjoinsports;
import com.android.volley.AuthFailureError;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.LocationListener;
import android.location.LocationManager;
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
import com.example.android.findjoinsports.Adapter.Recyclerview_Userjoinact;
import com.example.android.findjoinsports.Constants.ConstansAPI;
import com.example.android.findjoinsports.CreateActivity.CreateBasketball;
import com.example.android.findjoinsports.DATA.Descrip_ActData;
import com.example.android.findjoinsports.R;
import com.example.android.findjoinsports.SearchActivity.DescriptionActivity;
import com.example.android.findjoinsports.SearchActivity.SearchActivity;
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

public class Show_Act_User extends AppCompatActivity implements OnMapReadyCallback {

    private static final String URL_SHOW = "http://192.168.2.34/findjoinsport/search_activity/test.php";
    private static final String URL_DEL = "http://192.168.2.34/findjoinsport/football/delete_act.php";
    private static final String URL_SHOW_USER = "http://192.168.2.34/findjoinsport/football/show_userjoin.php";
    private static final String URL_DEL_REQ = " http://192.168.2.34/findjoinsport/football/delete_reqjoin.php";

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
    String id;
    private Button btFinish;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private Boolean mLocationPermissionsGranted = false;



    ImageView image,imgUser;
    TextView tvUserName, tvStadium, tvPlace, tvDate, tvTime, tvDescript, tvLocation, tvNumJoin;
    String userid, mUser_id, status_id,User_id;
    Button btn_join,btn_edit;
    SessionManager sessionManager;

    List<Descrip_ActData> descrip_actDataList;

    //the recyclerview
    RecyclerView recyclerView;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__act__user);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


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
        btn_edit = findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Show_Act_User.this,Edit_Activity.class);
                intent.putExtra("id",String.valueOf(userid));
                Toast.makeText(Show_Act_User.this, String.valueOf(userid), Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }
        });

        btn_join = findViewById(R.id.btn_join);
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delete_act();
                Delete_req();
                Toast.makeText(Show_Act_User.this, "ลบกิจกรรมแล้ว", Toast.LENGTH_SHORT).show();
            }
        });

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
                    String numjoin = jObj.getString("number_join");
                    User_id = jObj.getString("user_id");
                    Log.d("usss",User_id);
//                        if(jObj.getString("Photo_user") != null){String Photo_user = jObj.getString("Photo_user") ;}
                    String Photo_user = jObj.getString("photo_user");
                    // Log.d("pppphhhh",Photo_user);
                    String Latitude = jObj.getString("Latitude");
                    String Longitude = jObj.getString("Longitude");

                    double lat = Double.parseDouble(Latitude);
                    double lng = Double.parseDouble(Longitude);
                    LatLng LLgarage = new LatLng(lat, lng);
                    mMap.addMarker(new MarkerOptions().position(LLgarage));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(LLgarage));
                    CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LLgarage, 14);
                    mMap.moveCamera(update);
                    mMap.getUiSettings().setScrollGesturesEnabled(true);
                    mMap.getUiSettings().setZoomGesturesEnabled(true);
                    // Log.d("pppphhhh",Photo_user);




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

                    Picasso.with(Show_Act_User.this).load(photo).placeholder(R.drawable.s).into(image);

                    String photo_user = ConstansAPI.URL_PHOTO_USER+Photo_user;
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
                            Recyclerview_Userjoinact recyclerview_userjoinact = new Recyclerview_Userjoinact(Show_Act_User.this, descrip_actDataList, new Recyclerview_Userjoinact.OnItemClickListener() {

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
                        Toast.makeText(Show_Act_User.this, error.getMessage(), Toast.LENGTH_SHORT).show();

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
        Volley.newRequestQueue(Show_Act_User.this).add(stringRequest);
    }

    private void Delete_act() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.Host)+"/findjoinsport/football/delete_act.php", new Response.Listener<String>() {
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
        StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.Host)+"/findjoinsport/football/delete_reqjoin.php", new Response.Listener<String>() {
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

                Intent menu_back = new Intent(Show_Act_User.this, NavDrawer.class);
                startActivity(menu_back);

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







