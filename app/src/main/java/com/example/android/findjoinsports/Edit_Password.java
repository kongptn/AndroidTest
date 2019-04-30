package com.example.android.findjoinsports;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import java.util.HashMap;
import java.util.Map;

public class Edit_Password extends AppCompatActivity {

    private EditText passwordbef, passwordnew;
    private Button btn_editpassword;
    SessionManager sessionManager;
    public String URL_EDIT_PASSWORD = "http://192.168.1.3/android_register_login/edit_password.php";

    String getId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__password);

        passwordbef = (EditText) findViewById(R.id.c_password);
        passwordnew = (EditText) findViewById(R.id.new_password);

        btn_editpassword = (Button) findViewById(R.id.btn_editpassword);
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.USER_ID);



        btn_editpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // พอกดแล้วส่งค่าจาก edittext ไปตัว call service
                forgotPassword(passwordbef.getText().toString(), passwordnew.getText().toString());
            }
        });


    }

    private boolean forgotPassword(final String passwordbef, final String passwordnew) {


        final String user_id = getId;


        if (passwordbef.isEmpty()) {
            this.passwordbef.requestFocus();
            Toast.makeText(this, "กรุณากรอกรหัสผ่านเดิมให้ถูกต้อง", Toast.LENGTH_SHORT).show();
            return false;
        } else if (passwordnew.length() < 8) {
            this.passwordnew.requestFocus();
            Toast.makeText(this, "รหัสผ่านห้ามต่ำกว่า 8 ตัว", Toast.LENGTH_SHORT).show();
            return false;
        }



        StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.Host)+"/findjoinsport/android_register_login/edit_password.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("www", response);
                        if (response.toString().contains("true")) {
                            Toast.makeText(Edit_Password.this, "เปลี่ยนรหัสผ่านเรียบร้อย", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(Edit_Password.this, "กรุณากรอกรหัสผ่านให้ถูกต้อง", Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(), "กรอกรหัสผ่านให้ถูกต้อง", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //ตัวหน้าตรงกับใน php ที่ีรับค่า ตัวหลังคือค่าที่ส่งไป
                params.put("password", passwordbef);
                params.put("passwordNew", passwordnew);

                params.put("user_id", user_id);

                Log.d("aaa", passwordbef+" "+passwordnew+" "+user_id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


        return false;
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

                Intent back = new Intent(Edit_Password.this, NavDrawer.class);
                startActivity(back);

                return true;
            default:

                return super.onOptionsItemSelected(item);
        }


    }


}