package com.example.android.findjoinsports;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
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
import com.example.android.findjoinsports.Adapter.Adapter_Notification;
import com.example.android.findjoinsports.Adapter.Adapter_ReqFriend;
import com.example.android.findjoinsports.DATA.Act_User_CreateData;
import com.example.android.findjoinsports.DATA.Notification_Data;
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
public class Alert extends Fragment {


    public Alert() {
        // Required empty public constructor
    }
    private static final String URL_PRODUCTS = "http://192.168.2.34/findjoinsport/request_joinact/act_user_create.php";

    //a list to store all the products
    List<Notification_Data> notification_dataList;
    String iduserlogin,status_id,status_N01;
    //the recyclerview
    RecyclerView recyclerView;
    SessionManager sessionManager;
    //    String userid_join;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_alert, container, false);

        sessionManager = new SessionManager(getContext());
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        iduserlogin = user.get(sessionManager.USER_ID);
        Log.d("oooi",iduserlogin);

        status_N01 = "N01";

        //getting the recyclerview from xml
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //initializing the productlist
        notification_dataList = new ArrayList<>();

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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.Host)+"/findjoinsport/android_register_login/show_notification.php",
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
                                int noti_id = object.getInt("noti_id");
                                int user_send = object.getInt("user_send");
                                int user_get = object.getInt("user_get");
                                //String noti_text = object.getString("noti_text");
                                String name = object.getString("name");
                                String photo_user = object.getString("photo_user");
                                status_id = object.getString("status_id");
                                Log.d("popll",status_id);
                                String status_name = object.getString("status_name");

                                Notification_Data notification_data = new Notification_Data( noti_id, user_send, user_get, name, photo_user,status_id, status_name);
                                notification_dataList.add(notification_data);
                            }

                            //creating adapter object and setting it to recyclerview
                            Adapter_Notification adapter_notification = new Adapter_Notification(notification_dataList, getContext(), new Adapter_Notification.OnItemClickListener() {

                                @Override
                                public void onItemClick(String status_id) {

                                     if (status_id.equals("N01")){
                                       Log.d("opfjds",status_id);
                                       FragmentTransaction fr = getFragmentManager().beginTransaction();
                                       fr.replace(R.id.fram,new Request_Join_Creator(),"คำร้องขอเข้ากิจกรรม");
                                       fr.commit();
                                     }else if (status_id.equals("N02")){
                                         FragmentTransaction fr = getFragmentManager().beginTransaction();
                                         fr.replace(R.id.fram,new Request_Join(),"คำร้องขอเข้ากิจกรรม");
                                         fr.commit();

                                     }else if (status_id.equals("N03")){
                                         FragmentTransaction fr = getFragmentManager().beginTransaction();
                                         fr.replace(R.id.fram,new Invite_Joinact(),"คำร้องขอเข้ากิจกรรม");
                                         fr.commit();

                                     }else if (status_id.equals("N04")) {
                                       FragmentTransaction fr = getFragmentManager().beginTransaction();
                                       fr.replace(R.id.fram, new Request_Friend(), "คำร้องขอเข้ากิจกรรม");
                                       fr.commit();
                                       }
                                     else if (status_id.equals("N05")) {
                                        Intent i = new Intent(getContext(),Friends_List.class);
                                        startActivity(i);
                                     }

//                                    status_id = String.valueOf(item);
//
//                                    status_N01="N01";
//                                   if (status_id==status_N01){
//                                       Log.d("opfjds",status_id);
//                                       FragmentTransaction fr = getFragmentManager().beginTransaction();
//                                       fr.replace(R.id.fram,new Request_Join_Creator(),"คำร้องขอเข้ากิจกรรม");
//                                       fr.commit();
//                                   }

                                    }






                                    });
                            recyclerView.setAdapter(adapter_notification);
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
