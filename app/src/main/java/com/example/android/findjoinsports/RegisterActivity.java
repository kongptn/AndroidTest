package com.example.android.findjoinsports;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText name, email, password, c_password, firstname, lastname, age, phone, security_code;
    private Button btn_regist;
    private RadioGroup sex;
    private RadioButton mSex;
    private ProgressBar loading;
    private static String URL_REGIST = "http://192.168.2.37/findjoinsport/android_register_login/register.php";

    private static String strSex,strEmail;
    private RadioButton rb_male, rb_female;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loading = findViewById(R.id.loading);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.telephone);
        c_password = findViewById(R.id.c_password);
        btn_regist = findViewById(R.id.btn_regist);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        age = findViewById(R.id.age);
        phone = findViewById(R.id.phonenum);
        security_code = findViewById(R.id.se_code);
        sex = findViewById(R.id.sex);
        rb_male = findViewById(R.id.rb_male);
        rb_female = findViewById(R.id.rb_female);


        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Regist();

            }
        });

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


    }
    private boolean isValidEmail(String strEmail){
        return !TextUtils.isEmpty(strEmail) && Patterns.EMAIL_ADDRESS.matcher(strEmail).matches();
    }

    private boolean Regist() {
//        loading.setVisibility(View.VISIBLE);
//        btn_regist.setVisibility(View.GONE);
        final String c_password = this.c_password.getText().toString().trim();
        final String name = this.name.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();
        final String firstname = this.firstname.getText().toString().trim();
        final String lastname = this.lastname.getText().toString().trim();
        final String age = this.age.getText().toString().trim();
        final String phone = this.phone.getText().toString().trim();
        final String security_code = this.security_code.getText().toString().trim();


        if (name.isEmpty()) {
            this.name.requestFocus();
            Toast.makeText(this, "กรุณากรอกชื่อผู้ใช้", Toast.LENGTH_SHORT).show();
            return false;
        } else if (firstname.isEmpty()) {
            this.firstname.requestFocus();
            Toast.makeText(this, "กรุณากรอกชื่อ", Toast.LENGTH_SHORT).show();
            return false;
        } else if (lastname.isEmpty()) {
            this.lastname.requestFocus();
            Toast.makeText(this, "กรุณากรอกนามสกุล", Toast.LENGTH_SHORT).show();
            return false;
        } else if (email.isEmpty()) {
            this.email.requestFocus();
            Toast.makeText(this, "กรุณากรอกอีเมลล์", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.length() < 8) {
            this.password.requestFocus();
            Toast.makeText(this, "กรุณากรอกรหัสผ่านไม่ต่ำกว่า 8 ตัว", Toast.LENGTH_SHORT).show();
            return false;

        } else if (c_password.length() < 8) {
            this.c_password.requestFocus();
            Toast.makeText(this, "กรุณากรอกยืนยันรหัสผ่าน", Toast.LENGTH_SHORT).show();
            return false;

        } else if (!c_password.equals(password)) {
            this.c_password.requestFocus();
            Toast.makeText(this, "กรุณากรอกรหัสผ่านให้ถูกต้อง", Toast.LENGTH_SHORT).show();
            return true;
        } else if (age.length() > 2) {
            this.age.requestFocus();
            Toast.makeText(this, "กรุณากรอกอายุให้ถูกต้อง", Toast.LENGTH_SHORT).show();
            return false;
        } else if (phone.length() < 10) {
            this.phone.requestFocus();
            Toast.makeText(this, "กรุณากรอกเบอร์โทรศัพท์ให้ถูกต้อง", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!rb_female.isChecked() && !rb_male.isChecked()) {
            Toast.makeText(this, "กรุณาเลือกเพศ", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (security_code.length() < 4) {
            Toast.makeText(this, "กรุณากรอกรหัสรักษาความปลอดภัย", Toast.LENGTH_SHORT).show();
            this.security_code.requestFocus();
            return false;
        }

        if (email.trim().isEmpty() || !isValidEmail(email) ){
            Toast.makeText(this, "กรุณากรอกอีเมลล์ให้ถูกต้อง", Toast.LENGTH_SHORT).show();
            this.email.requestFocus();
            return false;
        }


        StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.Host)+"/findjoinsport/android_register_login/test-regis.php",

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Log.d("ko", response);
                        if (response.toString().contains("true")){
                            Toast.makeText(RegisterActivity.this, "Register Success!", Toast.LENGTH_SHORT).show();

                            Intent btn_regist = new Intent(RegisterActivity.this, MainActivity.class); //to page main

                            startActivity(btn_regist);
                        }else{
                            Toast.makeText(RegisterActivity.this, "อีเมลล์ซ้ำ หรือ เบอร์โทรศัพท์ซ้ำ", Toast.LENGTH_SHORT).show();
                        }
//
//                        if (response.equals("email_error")){
//
//
//                        }
//                        else {
//
//                        }



//                            if (response.equals("false")){
//                                Toast.makeText(RegisterActivity.this, "กรอกข้อมูลให้ถูกต้อง", Toast.LENGTH_SHORT).show();
//                            }


//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            String success = jsonObject.getString("success");
//
//                            Log.d("respone",response);
//                            if (success.equals("1")) {
//
//                                Toast.makeText(RegisterActivity.this, "Register Success!", Toast.LENGTH_SHORT).show();
//
//                                Intent btn_regist = new Intent(RegisterActivity.this, MainActivity.class); //to page main
//
//                                startActivity(btn_regist);
//                            }
//



                   /*     try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");



                            if (success.equals("1")) {


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Register Error! " + e.toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            btn_regist.setVisibility(View.VISIBLE);
                        }*/
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(RegisterActivity.this, "Register Error! " + error.toString(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        btn_regist.setVisibility(View.VISIBLE);

                    }
                }) {
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
                params.put("user_sex", strSex);
                params.put("security_code", security_code);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);

        return true;
    }
}




