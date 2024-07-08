package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;


public class ProfileFragment extends Fragment {

    TextView userIdTv;
    View view;
    String userId,userName,userImage;
    ImageView userProfileImageIv;
    Button userProfileModifyBtn;

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getUserInfoShared();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile,container,false);

        init();
        onClickListener();
        userIdTv.setText(userName);
        userProfileImageSetting();
        return view;
    }
    void getUserInfoShared(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("유저 정보", Context.MODE_PRIVATE);
        userName = sharedPreferences.getString("userName","");
        userId = sharedPreferences.getString("userId","");
        userImage = sharedPreferences.getString("userImage","");


    }
    void userProfileImageSetting(){
        Glide.with(this)
                .load(userImage)
                .into(userProfileImageIv);
    }
    void onClickListener(){
        userProfileModifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserProfileModify.class);
                startActivity(intent);

            }
        });
    }
    void init(){
        userIdTv = view.findViewById(R.id.user_name_tv);
        userProfileImageIv = view.findViewById(R.id.user_profile_image_iv);
        userProfileModifyBtn = view.findViewById(R.id.user_profile_modify_btn);
    }






}