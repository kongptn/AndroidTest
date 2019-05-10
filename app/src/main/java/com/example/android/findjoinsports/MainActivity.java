package com.example.android.findjoinsports;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    TextView txtEmail, txtBirthday, txtFriends, btn_regis_page;
    ProgressDialog mDialog;
    ImageView imaAvatar;
    Button btn_login, forget_password, bbb, login_facebook;
    private EditText email, password;
    private ProgressBar loading;
    SessionManager sessionManager;
    String finalEmail;
    String token;
    private static String URL_LOGIN = "http://192.168.2.36/findjoinsport/android_register_login/login.php";
    private static String URL_REGIST = "http://192.168.2.36/findjoinsport/android_register_login/RegisFacebook.php";
    private static String URL_LOGINFACE = "http://192.168.2.36/findjoinsport/android_register_login/loginfacebook.php";
    private static String URL_UPDATE_TOKEN = "http://10.13.3.103/findjoinsport/android_register_login/register_token.php";

    LoginButton bt_loginface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);

        txtBirthday = (TextView) findViewById(R.id.txtBirthday);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtFriends = (TextView) findViewById(R.id.txtFriends);
        imaAvatar = (ImageView) findViewById(R.id.avatar);

        login_facebook = findViewById(R.id.login_button_facebook);

        bt_loginface = findViewById(R.id.bt_loginface);

        loading = findViewById(R.id.loading);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_login = (Button) findViewById(R.id.btn_login);    //ปุ่ม Login

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = email.getText().toString().trim();
                String mPass = password.getText().toString().trim();

                if (!mEmail.isEmpty() || !mPass.isEmpty()) {
                    Login(mEmail, mPass);


                } else {
                    email.setError("Please insert email");
                    password.setError("Please insert password");
                }

                token = String.valueOf(FirebaseMessaging.getInstance().subscribeToTopic("test"));
                token = FirebaseInstanceId.getInstance().getToken();

                //Log.d("toto", token);
                updateToken(email.getText().toString(), token);


            }
        });

//        bbb = (Button) findViewById(R.id.bbb);
//        bbb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //  Intent bbb = new Intent(MainActivity.this, SearchFriends.class);
//                // startActivity(bbb);
//            }
//        });

        btn_regis_page = (TextView) findViewById(R.id.btn_regis_page);
        btn_regis_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btn_regis_page = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(btn_regis_page);
            }
        });


        forget_password = (Button) findViewById(R.id.forget_password);
        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forget_password = new Intent(MainActivity.this, ResetPassword.class);
                startActivity(forget_password);
            }
        });


        login_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_loginface.performClick();
            }
        });

        callbackManager = CallbackManager.Factory.create();

        bt_loginface.setReadPermissions(Arrays.asList("email", "public_profile"));
        bt_loginface.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {


            @Override
            public void onSuccess(LoginResult loginResult) {

                mDialog = new ProgressDialog(MainActivity.this);
                mDialog.setMessage("Retrieving data...");
                mDialog.show();


                String accesstoken = loginResult.getAccessToken().getToken();

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        mDialog.dismiss();
                        Log.d("response", response.toString());
                        getData(object);

                        String email = null;
                        String user_photo = null;

                        try {
                            email = object.getString("email").trim();
                            user_photo = object.getString("id").trim();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        finalEmail = email;
                        final String ph = user_photo;


                        StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.Host) + "/findjoinsport/android_register_login/RegisFacebook.php",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                     //   Toast.makeText(MainActivity.this, "su", Toast.LENGTH_SHORT).show();
                                        select_datafacebook(finalEmail);


                                    }

                                },

                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                        Toast.makeText(MainActivity.this, "Register Error! " + error.toString(), Toast.LENGTH_SHORT).show();
                                        loading.setVisibility(View.GONE);
                                        login_facebook.setVisibility(View.VISIBLE);

                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("email", finalEmail);
                                params.put("photo_user", ph);
                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        requestQueue.add(stringRequest);
                    }
                });

                //Request Graph API
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, email");
                request.setParameters(parameters);
                request.executeAsync();


//                Intent login_facebook = new Intent(MainActivity.this, Edit_Profile.class); //login to page home
//
//                startActivity(login_facebook);
            }


            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }

        });


        //If already login
        if (AccessToken.getCurrentAccessToken() != null) {
            //Just set User Id
            //txtEmail.setText(AccessToken.getCurrentAccessToken().getUserId());
        }

    }

    private void updateToken(final String email, final String token) {
        // Tag used to cancel the request
        StringRequest strReq = new StringRequest(Request.Method.POST, getString(R.string.Host) + "/findjoinsport/android_register_login/register_token.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Token Response update: ", response.toString());


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
                params.put("email", email);
                params.put("token", token);
                return params;
            }
        };
        // Adding request to request queue

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(strReq);

    }

    private void getData(JSONObject object) {

        try {

            txtEmail.setText(object.getString("email"));
//            txtBirthday.setText(object.getString("birthday"));
//            txtFriends.setText("Friends: " + object.getJSONObject("friends").getJSONObject("summary").getString("total_count"));

            String ph = "https://graph.facebook.com/" + object.getString("id") + "/picture?width=250&height=250";
            if (ph.equalsIgnoreCase("")) {
                ph = "default";
            }
            Picasso.with(MainActivity.this).load(ph).into(imaAvatar);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

    }


    private boolean Login(final String email, final String password) {
        final String mpassword = this.password.getText().toString().trim();
        final String memail = this.email.getText().toString().trim();

        if (email.isEmpty()) {
            this.email.requestFocus();
            Toast.makeText(this, "กรุณากรอกอีเมลล์", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.isEmpty()) {
            this.password.requestFocus();
            Toast.makeText(this, "กรุณากรอกรหัสผ่าน", Toast.LENGTH_SHORT).show();
            return false;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.Host) + "/findjoinsport/android_register_login/login.php",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        token = String.valueOf(FirebaseMessaging.getInstance().subscribeToTopic("test"));
                        token = FirebaseInstanceId.getInstance().getToken();

//                        Log.d("toto", token);
                        updateToken(email, token);

                        if (response.toString().isEmpty()) {
                            Toast.makeText(MainActivity.this, "กรุณา Email และ Password ให้ถูกต้อง", Toast.LENGTH_SHORT).show();
                        } else {
                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("login");

                                if (success.equals("1")) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);


                                        String name = object.getString("name").trim();
                                        String email = object.getString("email").trim();
                                        String password = object.getString("password").trim();
                                        String user_firstname = object.getString("user_firstname").trim();
                                        String user_lastname = object.getString("user_lastname").trim();
                                        String user_age = object.getString("user_age").trim();
                                        String user_tel = object.getString("user_tel").trim();
                                        String user_id = object.getString("user_id").trim();
                                        String user_sex = object.getString("user_sex").trim();
                                        String security_code = object.getString("security_code").trim();
                                        //String photo = object.getString("photo").trim();


                                        sessionManager.createSession(name, email, user_id, user_firstname, user_lastname, user_age, user_tel, user_sex, password, security_code);

                                        Intent intent = new Intent(MainActivity.this, NavDrawer.class);
                                        intent.putExtra("name", name);
                                        intent.putExtra("email", email);
                                        //intent.putExtra("password", password);
                                        //intent.putExtra("user_firstname", user_firstname);
                                        //intent.putExtra("user_lastname", user_lastname);
                                        //intent.putExtra("user_age", user_age);
                                        //intent.putExtra("user_tel", user_tel);
                                        //intent.putExtra("id", id);

                                        loading.setVisibility(View.GONE);
                                        startActivity(intent);

                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                                loading.setVisibility(View.GONE);
                                btn_login.setVisibility(View.VISIBLE);
                                Toast.makeText(MainActivity.this, "Error " + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        loading.setVisibility(View.GONE);
                        btn_login.setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this, "Error " + error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        return true;
    }


    private void printKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.example.android.findjoinsports", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));

            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private boolean select_datafacebook(final String finalEmail) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.Host) + "/findjoinsport/android_register_login/loginfacebook.php",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        token = String.valueOf(FirebaseMessaging.getInstance().subscribeToTopic("test"));
                        token = FirebaseInstanceId.getInstance().getToken();

                        Log.d("toto", token);
                        updateToken(finalEmail, token);
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if (success.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);


                                    String name = object.getString("name").trim();
                                    String email = object.getString("email").trim();
                                    String password = object.getString("password").trim();
                                    String user_firstname = object.getString("user_firstname").trim();
                                    String user_lastname = object.getString("user_lastname").trim();
                                    String user_age = object.getString("user_age").trim();
                                    String user_tel = object.getString("user_tel").trim();
                                    String user_id = object.getString("user_id").trim();

                                    String user_sex = object.getString("user_sex").trim();
                                    String security_code = object.getString("security_code").trim();
                                    //String photo = object.getString("photo").trim();

                                    sessionManager.createSession(name, email, user_id, user_firstname, user_lastname, user_age, user_tel, password, user_sex, security_code);

                                    Intent intent = new Intent(MainActivity.this, Edit_Profile.class);
                                    intent.putExtra("name", name);
                                    intent.putExtra("email", email);
                                    //intent.putExtra("password", password);
                                    //intent.putExtra("user_firstname", user_firstname);
                                    //intent.putExtra("user_lastname", user_lastname);
                                    //intent.putExtra("user_age", user_age);
                                    //intent.putExtra("user_tel", user_tel);
                                    //intent.putExtra("id", id);

                                    loading.setVisibility(View.GONE);
                                    startActivity(intent);

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            loading.setVisibility(View.GONE);
                            btn_login.setVisibility(View.VISIBLE);
                            Toast.makeText(MainActivity.this, "Error " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        loading.setVisibility(View.GONE);
                        btn_login.setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this, "Error " + error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", finalEmail);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        return true;

    }
}
