package com.example.android.findjoinsports;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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

public class Edit_Profile extends AppCompatActivity  {

    private static final String TAG = Edit_Profile.class.getSimpleName();
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
    private static String URL_READ = "http://10.13.3.135/android_register_login/read_detail.php";
    private static String URL_EDIT = "http://10.13.3.135/android_register_login/edit_detail.php";
    private static String URL_UPLOAD = "http://10.13.3.135/android_register_login/upload.php";


    private static String strSex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__profile);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        profile_image = findViewById(R.id.profile_image);
        btn_photo_upload = findViewById(R.id.btn_photo);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        //password = findViewById(R.id.password);
        user_firstname = findViewById(R.id.user_firstname);
        user_lastname = findViewById(R.id.user_lastname);
        user_age = findViewById(R.id.user_age);
        user_tel = findViewById(R.id.telephone);
        user_sex = findViewById(R.id.user_sex);
        sex = findViewById(R.id.sex);

        mSexMale = findViewById(R.id.rb_male);
        mSexFemale = findViewById(R.id.rb_female);



        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.USER_ID);

        btn_photo_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });

        sex.setOnCheckedChangeListener(new OnCheckedChangeListener() {  //mgen=radio
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

    }


    //getUserDetail
    private void getUserDetail(){

        final ProgressDialog progressDialog = new ProgressDialog(this);
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
                                for (int i =0; i < jsonArray.length(); i++){

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
                                    Log.d("ph",strImgUrl);


                                    if (strSex.equalsIgnoreCase("Male")){
                                        mSexMale.setChecked(true);
                                    }else if (strSex.equalsIgnoreCase("Female")){
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

                                    String ph = "http://192.168.2.33/android_register_login/"+strImgUrl;
                                    if (ph.equalsIgnoreCase("")){
                                        ph = "default";
                                    }
                                    Picasso.with(Edit_Profile.this).load(ph).into(profile_image);
//
                                    //loading the image
//                                    String getPhoto = "http://10.13.4.64/android_register_login/profile_image/"+object.getString();
//                                    if (getPhoto.equalsIgnoreCase("")){
//                                        getPhoto = "Default";
//                                    }

                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(Edit_Profile.this, "Error Reading Detail"+e.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(Edit_Profile.this, "Error Reading Detail"+error.toString(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserDetail();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_action, menu);

        action = menu;
        action.findItem(R.id.menu_save).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_edit:

                name.setFocusableInTouchMode(true);
                email.setFocusableInTouchMode(true);
                user_firstname.setFocusableInTouchMode(true);
                user_lastname.setFocusableInTouchMode(true);
                user_age.setFocusableInTouchMode(true);
                user_tel.setFocusableInTouchMode(true);
                user_sex.setFocusableInTouchMode(true);


                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(name, InputMethodManager.SHOW_IMPLICIT);

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                return true;

            case R.id.menu_save:

                SaveEditDetail();

                action.findItem(R.id.menu_edit).setVisible(true);
                action.findItem(R.id.menu_save).setVisible(false);


                name.setFocusableInTouchMode(false);
                email.setFocusableInTouchMode(false);
                user_firstname.setFocusableInTouchMode(false);
                user_lastname.setFocusableInTouchMode(false);
                user_age.setFocusableInTouchMode(false);
                user_tel.setFocusableInTouchMode(false);
                user_sex.setFocusableInTouchMode(false);



                name.setFocusable(false);
                email.setFocusable(false);
                user_firstname.setFocusable(false);
                user_lastname.setFocusable(false);
                user_age.setFocusable(false);
                user_tel.setFocusable(false);
                user_sex.setFocusable(false);


                return true;

            default:

                return super.onOptionsItemSelected(item);
        }

    }

    //save
    private void SaveEditDetail() {

        final String name = this.name.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String user_firstname = this.user_firstname.getText().toString().trim();
        final String user_lastname = this.user_lastname.getText().toString().trim();
        final String user_age = this.user_age.getText().toString().trim();
        final String user_tel = this.user_tel.getText().toString().trim();


        final String user_sex = strSex;

        final String user_id = getId;

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDIT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")){
                                Toast.makeText(Edit_Profile.this, "Success", Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(Edit_Profile.this, "Error"+e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(Edit_Profile.this, "Error"+error.toString(), Toast.LENGTH_SHORT).show();

                    }
                })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name",name);
                params.put("email", email);
                params.put("user_firstname", user_firstname);
                params.put("user_lastname", user_lastname);
                params.put("user_age", user_age);
                params.put("user_tel", user_tel);
                params.put("user_id", user_id);
                params.put("user_sex", user_sex);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void chooseFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "select Picture"), 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){

            Uri filePath = data.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                profile_image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

            UploadPicture(getId, getStringImage(bitmap));

        }
    }

    private void UploadPicture(final String user_id, final String photo_user) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPLOAD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        Log.i(TAG, response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")){
                                Toast.makeText(Edit_Profile.this, "Success!", Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(Edit_Profile.this, "Try Again!"+ e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(Edit_Profile.this, "Try Again!" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", user_id);
                params.put("photo_user", photo_user);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public String getStringImage(Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);


        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT);

        return encodedImage;

    }


}

