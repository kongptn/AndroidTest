package com.example.android.findjoinsports;

import com.example.android.findjoinsports.Constants.ConstansAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "http://192.168.2.35/findjoinsport/android_register_login/";
    public static Retrofit retrofit;

    public static Retrofit getApiClient(){

        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ConstansAPI.URL_PHOTO_USER)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
