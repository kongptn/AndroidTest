package com.example.android.findjoinsports;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
//import
import java.util.HashMap;
//คลาส
public class SessionManager {
    // ตัวแปร
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE_ = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String NAME = "NAME";
    public static final String EMAIL = "EMAIL";
    public static final String USER_FIRSTNAME = "USER_FIRSTNAME";
    public static final String USER_LASTNAME = "USER_LASTNAME";
    public static final String USER_AGE = "USER_AGE";
    public static final String USER_TEL = "USER_TEL";
    public static final String PASSWORD = "PASSWORD";
    public static final String USER_ID = "USER_ID";
    public static final String USER_SEX = "USER_SEX";
    public static final String SECURITY_CODE = "SECURITY_CODE";
    // public static final String PHOTO = "PHOTO";

    // contructor เพื่อให้หน้าที่ใช้ส่งค่า Context มา
    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE_);
        editor = sharedPreferences.edit();
    }
    // set ค่าเข้า ตอน login
    public void createSession(String name, String email, String user_id, String user_firstname, String user_lastname, String user_age, String user_tel, String user_sex, String password, String security_code) {
        editor.putBoolean(LOGIN, true);
        editor.putString(NAME, name);
        editor.putString(EMAIL, email);
        editor.putString(USER_FIRSTNAME, user_firstname);
        editor.putString(USER_LASTNAME, user_lastname);
        editor.putString(USER_AGE, user_age);
        editor.putString(USER_TEL, user_tel);
        editor.putString(PASSWORD, password);
        editor.putString(USER_ID, user_id);
        editor.putString(USER_SEX, user_sex);
        editor.putString(SECURITY_CODE, security_code);
        //editor.putString(PHOTO, photo);
        editor.apply();


    }

    public boolean isLoggin() {
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLogin() {
        if (!this.isLoggin()) {
            Intent i = new Intent(context, MainActivity.class);
            context.startActivity(i);
            ((Edit_Profile) context).finish();
        }
    }

    // get ค่าเอาไปใช่
    public HashMap<String, String> getUserDetail() {
        HashMap<String, String> user = new HashMap<>();
        user.put(NAME, sharedPreferences.getString(NAME, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(USER_FIRSTNAME, sharedPreferences.getString(USER_FIRSTNAME, null));
        user.put(USER_LASTNAME, sharedPreferences.getString(USER_LASTNAME, null));
        user.put(USER_AGE, sharedPreferences.getString(USER_AGE, null));
        user.put(USER_TEL, sharedPreferences.getString(USER_TEL, null));
        user.put(PASSWORD, sharedPreferences.getString(PASSWORD, null));
        user.put(USER_ID, sharedPreferences.getString(USER_ID, null));
        user.put(USER_SEX, sharedPreferences.getString(USER_SEX, null));
        user.put(SECURITY_CODE, sharedPreferences.getString(SECURITY_CODE, null));
        //user.put(PHOTO, sharedPreferences.getString(PHOTO, null));
        return user;
    }

}

