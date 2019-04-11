package com.example.android.findjoinsports;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {


    private static final String TAG = Profile.class.getSimpleName();
    private TextView name, email, user_firstname,user_lastname,user_age,user_tel, user_sex;
    SessionManager sessionManager;
    String getId;
    private String user_id;
    private Menu action;
    private RadioGroup sex;
    private RadioButton mSex, mSexMale, mSexFemale;
    private Button btn_photo_upload;
    private Bitmap bitmap;
    CircleImageView profile_image;
    private static String URL_READ = "http://192.168.2.34/android_register_login/read_detail.php";

    private static String strSex;

    public Profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        sessionManager = new SessionManager(getContext());
        sessionManager.checkLogin();

        // profile_image = view.findViewById(R.id.profile_image);
        btn_photo_upload = view.findViewById(R.id.btn_photo);
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        //password = findViewById(R.id.password);
        user_firstname = view.findViewById(R.id.user_firstname);
        user_lastname = view.findViewById(R.id.user_lastname);
        user_age = view.findViewById(R.id.user_age);
        user_tel = view.findViewById(R.id.telephone);
        user_sex = view.findViewById(R.id.user_sex);
        sex = view.findViewById(R.id.sex);

        mSexMale = view.findViewById(R.id.rb_male);
        mSexFemale = view.findViewById(R.id.rb_female);


        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.USER_ID);


        Button btn_edit = view.findViewById(R.id.btn_edit);
        Button btn_logout = view.findViewById(R.id.btn_logout);

        sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {  //mgen=radio
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                mSex = sex.findViewById(i);
                switch (i) {
                    case R.id.rb_male:
                        strSex = mSex.getText().toString();
                        break;
                    case R.id.rb_female:
                        strSex = mSex.getText().toString();
                        break;
                }
            }
        });


        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), Edit_Profile.class);
                in.putExtra("some", "some data");
                startActivity(in);

            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), MainActivity.class);
                in.putExtra("some", "some data");
                startActivity(in);


            }


        });
        return view;
    }

    private void getUserDetail(){

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.i(TAG,"response"+ response.toString());

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


                                    if (strSex.equalsIgnoreCase("Male")) {
                                        mSexMale.setChecked(true);
                                    } else if (strSex.equalsIgnoreCase("Female")) {
                                        mSexFemale.setChecked(true);
                                    }

                                    // เอาค่าที่ได้ settext
                                    name.setText(strName);
                                    email.setText(strEmail);
                                    user_firstname.setText(strUser_firstname);
                                    user_lastname.setText(strUser_lastname);
                                    user_age.setText(strUser_age);
                                    user_tel.setText(strUser_tel);
                                    user_sex.setText(strSex);


                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //ตัวหน้าตรงกับใน php ที่ีรับค่า ตัวหลังคือค่าที่ส่งไป
                params.put("user_id", getId);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
    @Override
    public void onResume() {
        super.onResume();
        getUserDetail();
    }
}
