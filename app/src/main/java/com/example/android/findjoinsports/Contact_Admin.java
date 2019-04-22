package com.example.android.findjoinsports;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.findjoinsports.CreateActivity.CreateBB_Gun;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Contact_Admin extends Fragment {

    String info_req,mUser;
    EditText et_req;
    Button bt_req;
    SessionManager sessionManager;

    private static final String URL_ADMIN = "http://10.13.3.103/findjoinsport/football/req_admin.php";
    public Contact_Admin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact__admin, container, false);


        sessionManager = new SessionManager(getContext());
        sessionManager.checkLogin();


        HashMap<String, String> user = sessionManager.getUserDetail();
        mUser = user.get(sessionManager.USER_ID);
        Log.d("txt",mUser);

        et_req = (EditText)view.findViewById(R.id.et_req);
        bt_req = (Button)view.findViewById(R.id.bt_req);

        bt_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onEditText();
                onButtonClick();
            }
        });


        return view;
    }

    private void onEditText() {
        info_req = et_req.getText().toString();



    }
    private void onButtonClick() {
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.Host)+"/findjoinsport/football/req_admin.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("onResponse", response);
                    et_req.setText("");

                    Toast.makeText(getContext(), "ส่งคำร้องแล้ว", Toast.LENGTH_SHORT).show();
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.d("Create Error", error.toString());
//                    Toast.makeText(CreateFootball.this, "เกิดข้อผิดพลาดโปรดลองอีกครั้ง", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(),"กรอกผิดแล้ว",Toast.LENGTH_SHORT).show();

                }

                private Context getContext() {
                    return null;
                }
            }) {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("info", info_req);
                    params.put("user_id",mUser);
                    return params;
                }
            };
            requestQueue.add(request);
        }

}
