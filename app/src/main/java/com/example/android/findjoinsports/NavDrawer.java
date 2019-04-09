package com.example.android.findjoinsports;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.findjoinsports.CreateActivity.CreateActivity;
import com.example.android.findjoinsports.SearchActivity.SearchActivity;


public class NavDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentTransaction frr = getSupportFragmentManager().beginTransaction();
        frr.add(R.id.fram,new SearchActivity());
        frr.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //when application starts this fragment will be displayed
        setTitle("ค้นหากิจกรรม");
        SearchActivity fragment = new SearchActivity();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fram, fragment,"ค้นหากิจกรรม");
        fragmentTransaction.commit();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_news:
                            selectedFragment = new Alert();
                            setTitle("แจ้งเตือน");
                            break;
                        case R.id.nav_search_act:
                            selectedFragment = new SearchActivity();
                            setTitle("ค้นหากิจกรรม");
                            break;
                        case R.id.nav_create_act:
                            selectedFragment = new CreateActivity();
                            setTitle("สร้างกิจกรรม");
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new Profile();
                            setTitle("ข้อมูลส่วนตัว");
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fram,
                            selectedFragment).commit();
                    return true;
                }
            };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            setTitle("คำร้องขอเข้ากิจกรรม");
            Request_Join_Creator fragment = new Request_Join_Creator();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fram, fragment,"คำร้องขอเข้ากิจกรรม");
            fragmentTransaction.commit();
        } else if (id == R.id.nav_news) {
            setTitle("กิจกรรมที่สร้าง");
            Act_User_Create fragment = new Act_User_Create();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fram, fragment,"กิจกรรมที่สร้าง");
            fragmentTransaction.commit();
        } else if (id == R.id.nav_search_act) {
            setTitle("คำร้องขอเป็นเพื่อน");
            Request_Friend fragment = new Request_Friend();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fram, fragment,"คำร้องขอเป็นเพื่อน");
            fragmentTransaction.commit();

        } else if (id == R.id.nav_join_act) {
            setTitle("กิจกรรมที่สมัคร");
            Request_Join fragment = new Request_Join();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fram, fragment,"กิจกรรมที่สมัคร");
            fragmentTransaction.commit();

        } else if (id == R.id.nav_friend) {
            setTitle("เพื่อน");
            Friend fragment = new Friend();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fram, fragment,"เพื่อน");
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
