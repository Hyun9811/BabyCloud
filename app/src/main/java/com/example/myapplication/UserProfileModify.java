package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class UserProfileModify extends AppCompatActivity {

    Button backBtn,logoutBtn,unRegisterBtn;
    ProfileFragment profileFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_modify);
        init();
        onClickListener();
    }

    void init(){
        backBtn = findViewById(R.id.back_btn);
        profileFragment = new ProfileFragment();
        logoutBtn = findViewById(R.id.logout_btn);
        unRegisterBtn = findViewById(R.id.unRegister_btn);
    }

    void onClickListener() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("유저 정보",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(UserProfileModify.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}