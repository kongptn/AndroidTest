package com.example.android.findjoinsports.SearchActivity;


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
import com.example.android.findjoinsports.Constants.ConstansAPI;
import com.example.android.findjoinsports.DATA.RecyclerSearch;
import com.example.android.findjoinsports.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFootball extends Fragment {


    public SearchFootball() {
        // Required empty public constructor
    }


    //a list to store all the products
    List<RecyclerSearch> recyclerSearchList;

    //the recyclerview
    RecyclerView recyclerView;
    String status_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_search_football, container, false);

        //getting the recyclerview from xml
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //initializing the productlist
        recyclerSearchList = new ArrayList<>();

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
        StringRequest stringRequest = new StringRequest(Request.Method.GET, getString(R.string.Host)+"/findjoinsport/search_activity/api.php",
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
                                Log.d("stat",status_id);

                                RecyclerSearch recyclerSearch = new RecyclerSearch(id, userid, stadiumname, photo, photo_user, date, time, name, location, description, status_id);
                                recyclerSearchList.add(recyclerSearch);
                            }

                            //creating adapter object and setting it to recyclerview
                            Adapter adapter = new Adapter(getContext(), recyclerSearchList,status_id, new Adapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int id) {
                                    Intent intent = new Intent(getContext(),DescriptionActivity.class);
                                    intent.putExtra("id",String.valueOf(id));
                                  //  Toast.makeText(getContext(), String.valueOf(id), Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                }
                            });
                            recyclerView.setAdapter(adapter);
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
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }
}