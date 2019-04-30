package com.example.android.findjoinsports;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.findjoinsports.Adapter.Adapter;
import com.example.android.findjoinsports.Adapter.Adapter_Act_User_Craete;
import com.example.android.findjoinsports.DATA.Act_User_CreateData;
import com.example.android.findjoinsports.DATA.RecyclerSearch;
import com.example.android.findjoinsports.R;
import com.example.android.findjoinsports.SearchActivity.DescriptionActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Act_User_Create extends Fragment {


    public Act_User_Create() {
        // Required empty public constructor
    }
    private static final String URL_PRODUCTS = "http://192.168.2.34/findjoinsport/request_joinact/act_user_create.php";

    //a list to store all the products
    List<Act_User_CreateData> act_user_createDataList;
    String iduserlogin;
    //the recyclerview
    RecyclerView recyclerView;
    SessionManager sessionManager;

    String status_id;
    //    String userid_join;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_act__user__create, container, false);

        sessionManager = new SessionManager(getContext());
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        iduserlogin = user.get(sessionManager.USER_ID);
        Log.d("oooi",iduserlogin);

        //getting the recyclerview from xml
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //initializing the productlist
        act_user_createDataList = new ArrayList<>();

        //this method will fetch and parse json
        //to display it in recyclerview
        loadProducts();

        return view;
    }

    private void loadProducts() {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.Host)+"/findjoinsport/request_joinact/act_user_create.php",
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
                                int id = object.getInt("id");
                                String stadiumname = object.getString("stadium_name");
                                String photo = object.getString("photo");
                                String date = object.getString("date");
                                String time = object.getString("time");
                                String name = object.getString("name");
                                String location = object.getString("location");
                                String description = object.getString("description");
                                int userid = object.getInt("user_id");
                                String photo_user = object.getString("photo_user");
                                status_id = object.getString("status_id");
                                Log.d("sra",status_id);

                                Act_User_CreateData act_user_createData = new Act_User_CreateData(id, userid, stadiumname, photo, photo_user, date, time, name, location, description, status_id);
                                act_user_createDataList.add(act_user_createData);
                            }

                            //creating adapter object and setting it to recyclerview
                            Adapter_Act_User_Craete adapter_act_user_craete = new Adapter_Act_User_Craete(getContext(), act_user_createDataList,status_id, new Adapter_Act_User_Craete.OnItemClickListener() {
                                                            @Override
                                                            public void onItemClick(int id) {
                                                                Intent intent = new Intent(getContext(),Show_Act_User.class);
                                                                intent.putExtra("id",String.valueOf(id));
                                                                Toast.makeText(getContext(), String.valueOf(id), Toast.LENGTH_SHORT).show();
                                                                startActivity(intent);
                                                            }
                                                        });
                            recyclerView.setAdapter(adapter_act_user_craete);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }){

            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();
                  params.put("user_id",iduserlogin);
                  Log.d("now",iduserlogin);


                return params;
            }

        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }


}
