package com.example.android.findjoinsports;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResetPassword extends AppCompatActivity {

    SessionManager sessionManager;
    //private TextView code;
    private Button change;
    public String URL_SELECT_PASSWORD = "http://10.13.4.53/android_register_login/select-password.php";
    private EditText user_tel, security_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        change = (Button) findViewById(R.id.change);
        user_tel = (EditText) findViewById(R.id.telephone);
        security_code = (EditText)findViewById(R.id.sc);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // พอกดแล้วส่งค่าจาก edittext ไปตัว call service
                forgotPassword(user_tel.getText().toString(), security_code.getText().toString());
            }
        });


    }

    private void forgotPassword(final String user_tel, final String security_code) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SELECT_PASSWORD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        // ถ้า service ส่ง false มาแสดงว่าซ่ำ
                        if (response.toString().equalsIgnoreCase("false")) {
                            Toast.makeText(ResetPassword.this, "กรุณากรอกข้อมูลให้ถูกต้องและครบถ้วน", Toast.LENGTH_SHORT).show();
                        } else {
                            // ถ้าไม่ใช่ก๋มาเช้าอันนี้ ขะได้ช้อมูลมาเก็ชไว้ใน password
                            try {
                                JSONObject jObj = new JSONObject(response);
                                String password = jObj.getString("password");
                                Toast.makeText(ResetPassword.this, "รหัสผ่านของคุณคือ : "+password.toString(), Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Error Reading Detail" + e.toString(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "กรอกอีเมลใหถูก", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //ตัวหน้าตรงกับใน php ที่ีรับค่า ตัวหลังคือค่าที่ส่งไป
                params.put("user_tel", user_tel);
                params.put("security_code", security_code);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
