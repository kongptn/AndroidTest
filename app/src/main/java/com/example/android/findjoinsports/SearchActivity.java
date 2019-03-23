package com.example.android.findjoinsports;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchActivity extends Fragment implements View.OnClickListener {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, null);


        Button bt_serchBALL = (Button) view.findViewById(R.id.bt_serchBALL);
        bt_serchBALL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),SearchFootball.class);
                startActivity(i);
            }
        });

//        Button bt_serchBAS = (Button) view.findViewById(R.id.bt_serchBAS);
//        bt_serchBAS.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getActivity(),TestSearch.class);
//                startActivity(i);
//            }
//        });

//        Button bt_serchBAS = (Button) view.findViewById(R.id.bt_serchBAS);
//        //Button phonebookBtn = (Button) view.findViewById(R.id.phbookButton);
//
//        bt_serchBAS.setOnClickListener(this);
        //phonebookBtn.setOnClickListener(this);
//        Button bt_serchBAS = (Button) view.findViewById(R.id.bt_serchBAS);
//        bt_serchBAS.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentSearch fragmentSearch= new FragmentSearch();
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_container, fragmentSearch, "findThisFragment")
//                        .addToBackStack(null)
//                        .commit();
//            }
//        });
        Button bt_serchGUN = (Button)view.findViewById(R.id.bt_serchGUN);
        bt_serchGUN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fram,new SearchBB_GUN());
                fr.commit();
            }
        });

        Button bt_serchBAS = (Button)view.findViewById(R.id.bt_serchBAS);
        bt_serchBAS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fram,new SearchBasketball());
                fr.commit();
            }
        });


//        Button bt_serchBAS = (Button)view.findViewById(R.id.bt_serchBAS);
//        bt_serchBAS.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentTransaction fr = getFragmentManager().beginTransaction();
//                fr.replace(R.id.fram,new SearchBasketball());
//                fr.commit();
//            }
//        });

        return view;
    }

//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//        Fragment fragment = null;
//
//        switch (item.getItemId()){
//            case R.id.bt_serchGUN:
//                fragment = new SearchBB_GUN();
//                break;
//
//            case R.id.bt_serchBAS:
//                fragment = new SearchBasketball();
//                break;
//
////            case R.id.navigation_create_act:
////                fragment = new CreateActivity();
////                break;
////
////            case R.id.navigation_profile:
////                fragment = new Profile();
////                break;
//        }
////
//        return loadFragment(fragment);
//    }

//    private boolean loadFragment(Fragment fragment){
//        if (fragment != null){
//
//            getFragmentManager()
//                    .beginTransaction().replace(R.id.fragment_container, fragment)
//                    .commit();
//            return true;
//        }
//        return false;
//    }

    @Override
    public void onClick(View v) {
//        Fragment fragment = null;
//        switch (v.getId()) {
//            case R.id.bt_serchBAS:
//                fragment = new SearchBasketball();
//                replaceFragment(fragment);
//                break;
//
////            case R.id.phbookButton:
////                fragment = new PhoneBookFragment();
////                replaceFragment(fragment);
////                break;
//        }
    }

//    private void replaceFragment(Fragment fragment) {
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.replace(R.id.fragment_container, fragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }
//
//





}
