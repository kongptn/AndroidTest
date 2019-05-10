package com.example.android.findjoinsports;

import com.example.android.findjoinsports.DATA.User_Data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("getUsers.php")
    Call<List<User_Data>> getUsers (@Query("key") String keyword );


}