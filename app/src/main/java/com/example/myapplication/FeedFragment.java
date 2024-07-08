package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;


public class FeedFragment extends Fragment {


    View view;
    Button placeCheckBtn;
    Intent intent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_feed,container,false);
        init();
        onClickListener();

        return view;
    }
    void init(){
        placeCheckBtn = view.findViewById(R.id.place_check_btn);
    }
    void onClickListener(){
        placeCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), MyPlaceActivity.class);
                startActivity(intent);
            }
        });
    }

}