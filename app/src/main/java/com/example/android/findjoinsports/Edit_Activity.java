package com.example.android.findjoinsports;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.android.findjoinsports.DatePickerFragment;
import com.example.android.findjoinsports.R;

import com.example.android.findjoinsports.SearchActivity.DescriptionActivity;
import com.example.android.findjoinsports.SearchActivity.SearchActivity;
import com.example.android.findjoinsports.SessionManager;
import com.example.android.findjoinsports.TimePickerFragment;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Edit_Activity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private EditText editStad_name, editdescrip, PHOTO, editlocation;
    private TextView textDate, textTime, nametxt, emailtxt;
    private ImageView imgView;
    private Button btn_edit, ChooseBT, back;
    private String stadium_name, description, date, time, mUser, location, mName, actid, Time;
    private final int IMG_REQUEST = 1;
    String type_id = "3";
    int id;
    private Bitmap bitmap;
    private static final String URL_EDIT = "http://192.168.2.33/findjoinsport/football/update_act.php";
    private static final String URL_SHOW = "http://192.168.2.33/findjoinsport/football/edit_act.php";
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        actid = getIntent().getExtras().getString("id", "");
        Log.d("lpplpp", actid);

        HashMap<String, String> user = sessionManager.getUserDetail();
        mUser = user.get(sessionManager.USER_ID);
        mName = user.get(sessionManager.NAME);
        Log.d("bb", mUser);
        Log.d("nn", mName);
//        mEmail = user.get(sessionManager.EMAIL);


        Button btnTime = (Button) findViewById(R.id.btnTime);
        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });


        Button btnDate = (Button) findViewById(R.id.btnDate);
        btnDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        textDate = findViewById(R.id.textDate);
        textTime = findViewById(R.id.textTime);
        ChooseBT = findViewById(R.id.chooseBT);
        PHOTO = findViewById(R.id.photo);
        imgView = findViewById(R.id.imageView);
        editStad_name = findViewById(R.id.editStad_name);
        editdescrip = findViewById(R.id.editdescrip);
        btn_edit = findViewById(R.id.btn_edit);
        editlocation = findViewById(R.id.editlocation);
        onButtonClick(actid);

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveEditDetail();
                Toast.makeText(Edit_Activity.this, "แก้ไขกิจกรรมแล้ว", Toast.LENGTH_SHORT).show();
            }
        });



        back = findViewById(R.id.back);
        ChooseBT.setOnClickListener(this);
        //btn_edit.setOnClickListener(this);


        // onBindView();


//        btn_edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent backhome = new Intent(Edit_Activity.this, Show_Act_User.class);
//                startActivity(backhome);
//            }
//        });
////                onEditText();
////                onButtonClick();
////                uploadImage();
//
//
//            }
//        });
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView textTime = (TextView) findViewById(R.id.textTime);
        textTime.setText("Hour: " + hourOfDay + " Minute: " + minute);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        TextView textDate = (TextView) findViewById(R.id.textDate);
        textDate.setText(currentDateString);
    }


    private void onButtonClick(final String actid) {
//        if (!stadium_name.isEmpty() && !description.isEmpty()) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, URL_SHOW, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    String Stadium_name = jObj.getString("stadium_name");
                    String Photo = jObj.getString("photo");
                   // Log.d("pt", Photo);
                    String Date = jObj.getString("date");
                    Time = jObj.getString("time");

                    String Location = jObj.getString("location");
                    String Description = jObj.getString("description");
                    Log.d("llk", Description+" "+Location);

                    id = Integer.parseInt(jObj.getString("id"));

//                        if(jObj.getString("Photo_user") != null){String Photo_user = jObj.getString("Photo_user") ;}

                    // Log.d("pppphhhh",Photo_user);


//                    String.valueOf(editStad_name.setText(Stadium_name));
//                    Log.d("fddso", String.valueOf(editStad_name));

                    editStad_name.setText(Stadium_name);
                    editdescrip.setText(Description);
                    editlocation.setText(Location);
                    textDate.setText(Date);
                    textTime.setText(Time);

                    PHOTO.setText(Photo);
                    //Log.d("text",(textTime));
                    // --
                    String photo = "http://192.168.1.6/findjoinsport/football/" + Photo;
                    if (photo.equalsIgnoreCase("")) {
                        photo = "Default";
                    }

                    Picasso.with(Edit_Activity.this).load(photo).placeholder(R.drawable.s).into(imgView);


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
                 params.put("id", actid);

                return params;
            }

        };
        requestQueue.add(request);
    }

    private void SaveEditDetail() {

        final String strStad_name = editStad_name.getText().toString();
        final String strdescrip = editdescrip.getText().toString();
        final String strlocation = editlocation.getText().toString();
        final String strdate = textDate.getText().toString();
        final String strtime = textTime.getText().toString();
        final String strphoto = PHOTO.getText().toString();


        final String strid = String.valueOf(id);

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


                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                          //  Toast.makeText(Edit_Activity.this, "Error"+e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                       Toast.makeText(Edit_Activity.this, "Error"+error.toString(), Toast.LENGTH_SHORT).show();

                    }
                })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("stadium_name",strStad_name);
                params.put("description", strdescrip);
                params.put("location", strlocation);
                params.put("date", strdate);
                params.put("time", strtime);
                params.put("photo", strphoto);
                params.put("id", strid);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chooseBT:
                selectImage();
                break;


            case R.id.btn_edit:
//                uploadImage();
                break;
        }
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imgView.setImageBitmap(bitmap);
                imgView.setVisibility(View.VISIBLE);
                PHOTO.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
//        if (requestCode == PLACE_PICKER_REQUEST)//map api
//        {
//            if (resultCode == RESULT_OK)
//            {
//                Place place = PlacePicker.getPlace(CreateFootball.this, data);
//                tvPlace.setText(place.getAddress());
//            }
//        }

    }
//
//    private void uploadImage() {
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, UploadUrl,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            String Response = jsonObject.getString("response");
//                            Toast.makeText(CreateFootball.this, Response, Toast.LENGTH_LONG).show();
//                            imgView.setImageResource(0);
//                            imgView.setVisibility(View.GONE);
//                            NAME.setText("");
//                            NAME.setVisibility(View.GONE);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        })
//
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("name", NAME.getText().toString().trim());
//                params.put("image", imageToString(bitmap));
//                return params;
//            }
//        };
//        MySingleton.getInstance(CreateFootball.this).addToRequestQue(stringRequest);
//    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
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

                Intent menu_back = new Intent(Edit_Activity.this, Show_Act_User.class);
                menu_back.putExtra("id", String.valueOf(actid));
                startActivity(menu_back);

                return true;
            default:

                return super.onOptionsItemSelected(item);
        }


    }
}
