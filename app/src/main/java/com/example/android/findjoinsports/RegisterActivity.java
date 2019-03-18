package com.example.android.findjoinsports;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText name, email, password, c_password, firstname, lastname, age, phone;
    private Button btn_regist;
    private RadioGroup sex;
    private ProgressBar loading;
    private static String URL_REGIST = "http://192.168.2.34/android_register_login/register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loading = findViewById(R.id.loading);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        c_password = findViewById(R.id.c_password);
        btn_regist = findViewById(R.id.btn_regist);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        age = findViewById(R.id.age);
        phone = findViewById(R.id.phonenum);
        //sex = findViewById(R.id.sex);



        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Regist();
            }
        });
    }
    //
    private void Regist(){
        loading.setVisibility(View.VISIBLE);
        btn_regist.setVisibility(View.GONE);

        final String name = this.name.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();
        final String firstname = this.firstname.getText().toString().trim();
        final String lastname = this.lastname.getText().toString().trim();
         final String age = this.age.getText().toString().trim();
         final String phone = this.phone.getText().toString().trim();
        //final String sex = this.sex.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,



                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");



                            if (success.equals("1")){

                                Toast.makeText(RegisterActivity.this, "Register Success!", Toast.LENGTH_SHORT).show();

                                Intent btn_regist = new Intent(RegisterActivity.this, MainActivity.class); //to page main

                                startActivity(btn_regist);
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Register Error! " + e.toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            btn_regist.setVisibility(View.VISIBLE);
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {



                        Toast.makeText(RegisterActivity.this, "Register Error! " + error.toString(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        btn_regist.setVisibility(View.VISIBLE);


                    }


                })


        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
               params.put("user_firstname", firstname);
             params.put("user_lastname", lastname);
                params.put("user_age", age);
            params.put("user_tel", phone);
               // params.put("user_sex", sex);



                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);



    }
}
