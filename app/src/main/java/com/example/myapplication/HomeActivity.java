package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {

    ProfileFragment profileFragment;
    CameraFragment cameraFragment;
    FeedFragment feedFragment;
    SearchFragment chattingFragment;
    BottomNavigationView bottomNavigationView;
    String userId;
    TextView nameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        bottomNavigationClickListener();

    } // onCreate end

    void init(){
        profileFragment = new ProfileFragment();
        cameraFragment = new CameraFragment();
        feedFragment = new FeedFragment();
        chattingFragment = new SearchFragment();
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        getSupportFragmentManager().beginTransaction().replace(R.id.containers, feedFragment).commitAllowingStateLoss();

    } // init end

    void onClickListener(){

    }

    void bottomNavigationClickListener(){
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.feed) {
                    Log.i(TAG, "feed 들어옴");
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, feedFragment).commitAllowingStateLoss();
                    return true;
                } else if (id == R.id.chatting) {
                    Log.i(TAG, "chatting 들어옴");
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, chattingFragment).commitAllowingStateLoss();
                    return true;
                } else if (id == R.id.camera) {
                    Log.i(TAG, "camera 들어옴");
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, cameraFragment).commitAllowingStateLoss();
                    return true;
                } else if (id == R.id.profile) {
                    Log.i(TAG, "profile 들어옴");
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, profileFragment).commitAllowingStateLoss();
                    return true;
                }
                return false;
            }

        });

    }
}