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
import com.example.android.findjoinsports.Adapter.Adapter_Invite_Joinact;
import com.example.android.findjoinsports.Adapter.Adapter_redJoin_Creator;
import com.example.android.findjoinsports.Adapter.Adapter_reqJoin;
import com.example.android.findjoinsports.DATA.Invite_JoinactData;
import com.example.android.findjoinsports.DATA.RecyclerSearch;
import com.example.android.findjoinsports.DATA.Request_JoinData_Creator;
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
public class Invite_Joinact extends Fragment {


    public Invite_Joinact() {
        // Required empty public constructor
    }

    private static final String URL_sendREQJOIN = "http://192.168.2.34/findjoinsport/friend/show_invite_joinact.php";
    //private static final String URL_REQJOIN = "http://192.168.2.35/findjoinsport/request_joinact/reqjoin_show.php";

    //a list to store all the products
    List<Invite_JoinactData> invite_joinactDataList;
    String getId;
    //the recyclerview
    RecyclerView recyclerView;
    SessionManager sessionManager;

    int id;
    //    String userid_join;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_invite__joinact, container, false);

        sessionManager = new SessionManager(getContext());
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.USER_ID);
        //getting the recyclerview from xml
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //initializing the productlist
        invite_joinactDataList = new ArrayList<>();


        //this method will fetch and parse json
        //to display it in recyclerview


        sendRequest();
//        Requst();
        return view;
    }

    private void sendRequest() {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_sendREQJOIN,
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
                                int invite_id = object.getInt("invite_id");
                                id = object.getInt("id");
                                String stadium_name = object.getString("stadium_name");
                                String name = object.getString("name");
                                String photo = object.getString("photo");
                                String date = object.getString("date");
                                String time = object.getString("time");
                                int number_join = object.getInt("number_join");
                                int user_id = object.getInt("user_id");
                                int userid_invite = object.getInt("userid_invite");




                                String photo_user = object.getString("photo_user");
//                                int userid_join = object.getInt("userid_join");
                                // int user_create = object.getInt("user_create");
//                                Log.d("sd", String.valueOf(userid_join));

                                //int number_join = object.getInt("number_join");

                                Log.d("kpkp",stadium_name);

                                String status_id = object.getString("status_id");
                                String status_name = object.getString("status_name");

                              //  String status_id = object.getString("status_id");

                               // String status_name = object.getString("status_name");

                                Invite_JoinactData invite_joinactData = new Invite_JoinactData(invite_id, userid_invite, user_id , id , number_join , name, photo, photo_user, stadium_name, date, time, status_id, status_name);
                                invite_joinactDataList.add(invite_joinactData);
                            }

                            //creating adapter object and setting it to recyclerview
                            Adapter_Invite_Joinact adapter_invite_joinact = new Adapter_Invite_Joinact(getContext(), invite_joinactDataList, new Adapter_Invite_Joinact.OnItemClickListener() {

                                @Override
                                public void onItemClick(int id) {
//                                    Intent intent = new Intent(getContext(),DescriptionActivity.class);
//                                    intent.putExtra("id",String.valueOf(id));
//                                    Toast.makeText(getContext(), String.valueOf(id), Toast.LENGTH_SHORT).show();
//                                    startActivity(intent);
                                }
                            });
                            recyclerView.setAdapter(adapter_invite_joinact);
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
                params.put("user_id", getId);
                Log.d("ssss",getId);



                return params;
            }

        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

//    private void Requst() {
//
//        /*
//         * Creating a String Request
//         * The request type is GET defined by first parameter
//         * The URL is defined in the second parameter
//         * Then we have a Response Listener and a Error Listener
//         * In response listener we will get the JSON response as a String
//         * */
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REQJOIN,
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
//                                int req_id = object.getInt("req_id");
//                                int id = object.getInt("id");
//                                int user_id = object.getInt("user_id");
//                                int userid_join = object.getInt("userid_join");
//                                int user_create = object.getInt("user_create");
//                                Log.d("eeee", String.valueOf(userid_join));
//                                String stadium_name = object.getString("stadium_name");
//                                Log.d("kpkp",stadium_name);
//                                String date = object.getString("date");
//                                String time = object.getString("time");
//                                String name = object.getString("name");
//                                String status_id = object.getString("status_id");
//                                String photo_user = object.getString("photo_user");
//                                String status_name = object.getString("status_name");
//                                Request_JoinData request_joinData = new Request_JoinData(req_id,id,user_id,userid_join,user_create,stadium_name, date,  time,  status_id,  status_name,  name, photo_user);
//                                request_joinDataList.add(request_joinData);
//                            }
//
//                            //creating adapter object and setting it to recyclerview
//                            Adapter_reqJoin adapter_reqJoin = new Adapter_reqJoin(getContext(), request_joinDataList, new Adapter_reqJoin.OnItemClickListener() {
//
//                                @Override
//                                public void onItemClick(int id) {
////                                    Intent intent = new Intent(getContext(),DescriptionActivity.class);
////                                    intent.putExtra("id",String.valueOf(id));
////                                    Toast.makeText(getContext(), String.valueOf(id), Toast.LENGTH_SHORT).show();
////                                    startActivity(intent);
//                                }
//                            });
//                            recyclerView.setAdapter(adapter_reqJoin);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//
//                    }
//                }){
//
//            protected Map<String, String> getParams() {
//                // Posting params to login url
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("user_create", getId);
//                Log.d("sd",getId);
//
//
//
//
//                return params;
//            }
//
//        };
//
//        //adding our stringrequest to queue
//        Volley.newRequestQueue(getContext()).add(stringRequest);
//    }

}
