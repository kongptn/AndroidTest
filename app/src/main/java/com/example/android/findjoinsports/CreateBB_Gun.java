package com.example.android.findjoinsports;


import android.app.DatePickerDialog;
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
import android.view.View;
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
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreateBB_Gun extends AppCompatActivity implements View.OnClickListener ,DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private EditText editStad_name, editdescrip, NAME;
    private TextView textDate, textTime, textPlace, tvPlace;
    private ImageView imgView;
    private Button btn_create, ChooseBT;
    private String stadium_name, description ,date, time;
    private final int IMG_REQUEST = 1;
    int PLACE_PICKER_REQUEST = 1;
    String type_id = "2";
    private Bitmap bitmap;
    private static final String URL = "http://192.168.2.37/findjoinsport/football/InsertData.php";
    private String UploadUrl = "http://10.13.3.102/ImageUploadApp/updateinfo.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bb__gun);

        tvPlace = (TextView)findViewById(R.id.tvPlace);

        textPlace = (TextView)findViewById(R.id.textPlace);
        textPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                Intent intent;
                try {
                    intent = builder.build(CreateBB_Gun.this);
                    startActivityForResult(intent,PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        Button btnTime = (Button) findViewById(R.id.btnTime);
        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });


        Button btnDate = (Button) findViewById(R.id.btnDate);
        btnDate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });
        textDate = (TextView)findViewById(R.id.textDate);
        textTime = (TextView)findViewById(R.id.textTime);
        ChooseBT = (Button) findViewById(R.id.chooseBT);
        NAME = (EditText) findViewById(R.id.name);
        imgView = (ImageView) findViewById(R.id.imageView);
        btn_create = (Button) findViewById(R.id.btn_create);
        ChooseBT.setOnClickListener(this);
        btn_create.setOnClickListener(this);


        onBindView();

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditText();
                onButtonClick();
//                uploadImage();
            }
        });
    }

    public void goPlacePicker (View view){ //map api

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(CreateBB_Gun.this),PLACE_PICKER_REQUEST);

        }catch (GooglePlayServicesRepairableException e){
            e.printStackTrace();
        }catch (GooglePlayServicesNotAvailableException e){
            e.printStackTrace();
        }

    }

    protected void onActivityResultMap(int requestCode, int resultCode ,Intent data){
        if (requestCode == PLACE_PICKER_REQUEST)
        {
            if (resultCode == RESULT_OK)
            {
                Place place = PlacePicker.getPlace(data,this);
                String address = String.format("Place: %s",place.getAddress());
                textPlace.setText(address);
            }
        }
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView textTime = (TextView)findViewById(R.id.textTime);
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

    private void onBindView() {
        editStad_name = (EditText) findViewById(R.id.editStad_name);
        editdescrip = (EditText) findViewById(R.id.editdescrip);
        btn_create = (Button) findViewById(R.id.btn_create);
    }

    private void onEditText() {
        stadium_name = editStad_name.getText().toString();
        description = editdescrip.getText().toString();

        date = textDate.getText().toString();
        time = textTime.getText().toString();
        type_id = "2";
    }

    private void onButtonClick() {
        if (!stadium_name.isEmpty() && !description.isEmpty()) {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("onResponse", response);
                    editStad_name.setText("");
                    editdescrip.setText("");


                    textDate.setText("");
                    textTime.setText("");
                    // --
                    imgView.setImageResource(0);
                    imgView.setVisibility(View.GONE);
                    NAME.setText("");
                    NAME.setVisibility(View.GONE);
                    Toast.makeText(CreateBB_Gun.this, "สร้างกิจกรรมแล้ว", Toast.LENGTH_SHORT).show();
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.d("Create Error", error.toString());
//                    Toast.makeText(CreateFootball.this, "เกิดข้อผิดพลาดโปรดลองอีกครั้ง", Toast.LENGTH_SHORT).show();
                    Toast.makeText(CreateBB_Gun.this,"กรอกผิดแล้ว",Toast.LENGTH_SHORT).show();

                }

                private Context getContext() {
                    return null;
                }
            }) {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("stadium_name", stadium_name);
                    params.put("description", description);
                    params.put("name", NAME.getText().toString().trim());
                    params.put("image", imageToString(bitmap));
                    params.put("date", date);
                    params.put("time", time);
                    params.put("type_id", type_id);
                    return params;
                }
            };
            requestQueue.add(request);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chooseBT:
                selectImage();
                break;


            case R.id.btn_create:
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
                NAME.setVisibility(View.VISIBLE);
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
}
