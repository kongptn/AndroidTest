package com.example.android.findjoinsports;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.findjoinsports.Adapter.Adapter_Friend;
import com.example.android.findjoinsports.DATA.User_Data;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search_Friend extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<User_Data> user_dataList;
    private Adapter_Friend adapter_friend;
    private ApiInterface apiInterface;
    ProgressBar progressBar;
    ImageView profile_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__friend);

        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        fetchUsers("");
    }


    public void fetchUsers(String key){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List< User_Data >> call = apiInterface.getUsers(key);

        call.enqueue(new Callback<List<User_Data>>() {
            @Override
            public void onResponse(Call<List<User_Data>> call, Response<List<User_Data>> response) {


                progressBar.setVisibility(View.GONE);
                user_dataList = response.body();
                adapter_friend = new Adapter_Friend(user_dataList, Search_Friend.this, new Adapter_Friend.OnItemClickListener() {
                    @Override
                    public void listener(int user_id,String name, String email, String user_firstname, String user_lastname, String user_tel, String user_age, String user_sex, String photo_user) {
                        Intent intent = new Intent(Search_Friend.this, DetailsActivity.class);
                        intent.putExtra("user_id", String.valueOf(user_id));
//                        intent.putExtra("name", String.valueOf(name));
//                        intent.putExtra("email", String.valueOf(email));
//                        intent.putExtra("user_firstname", String.valueOf(user_firstname));
//                        intent.putExtra("user_lastname", String.valueOf(user_lastname));
//                        intent.putExtra("user_tel", String.valueOf(user_tel));
//                        intent.putExtra("user_age", String.valueOf(user_age));
//                        intent.putExtra("user_sex", String.valueOf(user_sex));
//                        intent.putExtra("photo_user", String.valueOf(photo_user));
                        Toast.makeText(Search_Friend.this,String.valueOf(user_id), Toast.LENGTH_SHORT).show();
                        startActivity(intent);


                    }
                });
                recyclerView.setAdapter(adapter_friend);
                adapter_friend.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<User_Data>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Search_Friend.this, "Error on :" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menusea, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())

        );

        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                fetchUsers(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                fetchUsers(s);
                return false;
            }
        });

        return true;
    }
}
