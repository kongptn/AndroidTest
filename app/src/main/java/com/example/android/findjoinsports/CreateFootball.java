package com.example.android.findjoinsports;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class CreateFootball extends AppCompatActivity {
    private EditText editName,editLastName;
    private Button btn_create;
    private static final String URL = "http://192.168.2.33/test/InsertData.php";
    private String name,lastname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_football);
        onBindView();

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditText();
                onButtonClick();
            }
        });
    }

    private void onBindView(){
        editName = (EditText)findViewById(R.id.editName);
        editLastName = (EditText) findViewById(R.id.editLastName);
        btn_create = (Button)findViewById(R.id.btn_create);
    }

    private void onEditText(){
        name = editName.getText().toString();
        lastname = editLastName.getText().toString();
    }

    private void onButtonClick(){
        if (!name.isEmpty() && !lastname.isEmpty()){
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("onResponse", response);
                    editName.setText("");
                    editLastName.setText("");
                    Toast.makeText(CreateFootball.this, "สร้างกิจกรรมแล้ว", Toast.LENGTH_SHORT).show();
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.d("Create Error",error.toString());
                    Toast.makeText(CreateFootball.this,"เกิดข้อผิดพลาดโปรดลองอีกครั้ง",Toast.LENGTH_SHORT).show();

                }

                private Context getContext() {
                    return null;
                }
            }){
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("name",name);
                    params.put("lastname",lastname);

                    return params;
                }
            };
            requestQueue.add(request);
        }
    }
}
