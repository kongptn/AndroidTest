package com.example.android.findjoinsports;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CreateFootball extends AppCompatActivity implements View.OnClickListener {
    private EditText editStad_name, editdescrip, NAME;
    private ImageView imgView;
    private Button btn_create, ChooseBT;
    private String stadium_name, description;
    private final int IMG_REQUEST = 1;
    private Bitmap bitmap;
    private static final String URL = "http://192.168.2.34/findjoinsport/football/InsertData.php";
    private String UploadUrl = "http://192.168.2.34/ImageUploadApp/updateinfo.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_football);

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

    private void onBindView() {
        editStad_name = (EditText) findViewById(R.id.editStad_name);
        editdescrip = (EditText) findViewById(R.id.editdescrip);
        btn_create = (Button) findViewById(R.id.btn_create);
    }

    private void onEditText() {
        stadium_name = editStad_name.getText().toString();
        description = editdescrip.getText().toString();
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

                    // --
                    imgView.setImageResource(0);
                    imgView.setVisibility(View.GONE);
                    NAME.setText("");
                    NAME.setVisibility(View.GONE);
                    Toast.makeText(CreateFootball.this, "สร้างกิจกรรมแล้ว", Toast.LENGTH_SHORT).show();
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.d("Create Error", error.toString());
//                    Toast.makeText(CreateFootball.this, "เกิดข้อผิดพลาดโปรดลองอีกครั้ง", Toast.LENGTH_SHORT).show();
                    Toast.makeText(CreateFootball.this,"กรอกผิดแล้วไอโล้นซ่าาาา",Toast.LENGTH_SHORT).show();

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
