package com.example.android.findjoinsports.SearchActivity;
import com.android.volley.AuthFailureError;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DescriptionActivity extends AppCompatActivity {

    private static final String URL_SHOW = "http://192.168.2.34/findjoinsport/search_activity/test.php";

//    List<RecyclerSearch> recyclerSearchList;
//
//    //the recyclerview
//    RecyclerView recyclerView;

    ImageView image;
    TextView tvUserName, tvStadium, tvPlace, tvDate, tvTime, tvDescript, tvLocation, tvNumJoin;
    String Stadium_name;
    int Photo;
    //    String Photo;
    String Date;
    String Time;
    String Name;
    String Location;
    String Description;
    int userid;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        image = findViewById(R.id.image);
        tvUserName = findViewById(R.id.tvUserName);
        tvStadium = findViewById(R.id.tvStadium);
        tvPlace = findViewById(R.id.tvPlace);
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        tvDescript = findViewById(R.id.tvDescript);
        tvLocation = findViewById(R.id.tvLocation);
        tvNumJoin = findViewById(R.id.tvNumJoin);
        userid = getIntent().getIntExtra("id", 0);
        Log.d("sss", String.valueOf(userid));


        onButtonClick();


    }

    private void onButtonClick() {
//        if (!stadium_name.isEmpty() && !description.isEmpty()) {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest request = new StringRequest(Request.Method.POST, URL_SHOW, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("onResponse", response);


                    Toast.makeText(DescriptionActivity.this, "สร้างกิจกรรมแล้ว", Toast.LENGTH_SHORT).show();
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.d("Create Error", error.toString());
//                    Toast.makeText(CreateFootball.this, "เกิดข้อผิดพลาดโปรดลองอีกครั้ง", Toast.LENGTH_SHORT).show();
                    Toast.makeText(DescriptionActivity.this,"กรอกผิดแล้ว",Toast.LENGTH_SHORT).show();

                }

                private Context getContext() {
                    return null;
                }
            }) {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id", String.valueOf(userid));

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

