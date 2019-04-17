package com.example.android.findjoinsports;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.findjoinsports.Adapter.Adapter_List_Friends;
import com.example.android.findjoinsports.Adapter.Adapter_ReqFriend;
import com.example.android.findjoinsports.Adapter.Adapter_ReqInvite_Joinact;
import com.example.android.findjoinsports.DATA.List_FriendData;
import com.example.android.findjoinsports.DATA.Request_FriendData;
import com.example.android.findjoinsports.DATA.Request_Invite_JoinactData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Friends_List_Invite extends AppCompatActivity {
    //private static final String URL= "http://10.13.4.158/findjoinsport/friend/invite_joinact.php";

    private static final String URL_REQfriend = "http://10.13.3.135/findjoinsport/friend/friend_list.php";

    //a list to store all the products
    List<Request_Invite_JoinactData> request_invite_joinactDataList;
    String getId,act_id,user_create;
    int rf_id;
    //the recyclerview
    RecyclerView recyclerView;
    SessionManager sessionManager;
    //    String userid_join;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends__list__invite);


        sessionManager = new SessionManager(Friends_List_Invite.this);
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.USER_ID);
        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Friends_List_Invite.this));

        //initializing the productlist
        request_invite_joinactDataList = new ArrayList<>();

        act_id = getIntent().getExtras().getString("id","");
        Log.d("act",act_id);

        user_create = getIntent().getExtras().getString("user_id","");
        Log.d("create",user_create);


        //this method will fetch and parse json
        //to display it in recyclerview


        sendRequest();
//        Requst();
    }

    private void sendRequest() {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REQfriend,
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
                                rf_id = object.getInt("rf_id");
//                                int userid_add = object.getInt("userid_add");
                                int user_id = object.getInt("user_id");
//                                int userid_join = object.getInt("userid_join");
                                // int user_create = object.getInt("user_create");
//                                Log.d("sd", String.valueOf(userid_join));
//                                int userid_join = object.getInt("userid_join");
//                                int number_join = object.getInt("number_join");
//                                String stadium_name = object.getString("stadium_name");
//                                Log.d("kpkp",stadium_name);
//                                String date = object.getString("date");
//                                String time = object.getString("time");
                                String name = object.getString("name");
                                Log.d("naaa",name);
                                String status_id = object.getString("status_id");
                                String photo_user = object.getString("photo_user");
                                String status_name = object.getString("status_name");

                                Request_Invite_JoinactData request_invite_joinactData = new Request_Invite_JoinactData(rf_id, user_id, status_id, status_name, name, photo_user);
                                request_invite_joinactDataList.add(request_invite_joinactData);
                            }

                            //creating adapter object and setting it to recyclerview
                            Adapter_ReqInvite_Joinact adapter_reqInvite_joinact = new Adapter_ReqInvite_Joinact(Friends_List_Invite.this, request_invite_joinactDataList,act_id,user_create, new Adapter_ReqInvite_Joinact.OnItemClickListener() {

                                @Override
                                public void onItemClick(int id) {
//                                    Intent intent = new Intent(getContext(),DescriptionActivity.class);
//                                    intent.putExtra("id",String.valueOf(id));
//                                    Toast.makeText(getContext(), String.valueOf(id), Toast.LENGTH_SHORT).show();
//                                    startActivity(intent);
                                }
                            });
                            recyclerView.setAdapter(adapter_reqInvite_joinact);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Friends_List_Invite.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }){

            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", getId);
//                Log.d("ssss",getId);

//                params.put("rf_id", String.valueOf(rf_id));

                return params;
            }

        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(Friends_List_Invite.this).add(stringRequest);
    }


}
