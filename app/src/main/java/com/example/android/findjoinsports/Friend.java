package com.example.android.findjoinsports;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class Friend extends Fragment {

    Button friends, searchfriends;
    public Friend() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend, container, false);


        friends = (Button) view.findViewById(R.id.friends);
        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getContext(), Friends_List.class);
                startActivity(i);
            }
        });


        searchfriends = (Button) view.findViewById(R.id.searchfriends);
        searchfriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent bbb = new Intent(getContext(), Search_Friend.class);
                startActivity(bbb);
            }
        });

        return view;
    }

}


