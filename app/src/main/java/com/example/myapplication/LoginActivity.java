package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LoginActivity extends AppCompatActivity {
    EditText loginIdEt, loginPwEt;
    Button loginBtn, kakaoLoginBtn;
    TextView signTv;
    Intent intent;
    String loginId, loginPw, TAG = "LoginActivity", kakaoId, kakaoEmail, kakaoName, social, kakaoImage, localUserImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        autoLogin();
        init();
        onClickListener();
    }

    void init() {
        loginIdEt = findViewById(R.id.id_et);
        loginPwEt = findViewById(R.id.pw_et);
        loginBtn = findViewById(R.id.login_btn);
        signTv = findViewById(R.id.sign_tv);
        kakaoLoginBtn = findViewById(R.id.kakao_login_btn);
    } // method init

    void onClickListener() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginCheck();
            }
        });
        signTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(LoginActivity.this, SignActivity.class);
                startActivity(intent);
            }
        });
        // 카카오 로그인 버튼
        kakaoLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(LoginActivity.this)) {
                    UserApiClient.getInstance().loginWithKakaoTalk(LoginActivity.this, callback);
                } else {
                    UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this, callback);
                }
            }
        });
    } // method onClickListener

    void loginApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LoginInterface.REGISTER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoginInterface loginInterface = retrofit.create(LoginInterface.class);
        Call<LoginRetrofitData> call = loginInterface.getUserLogin(loginId, loginPw);
        call.enqueue(new Callback<LoginRetrofitData>() {
            @Override
            public void onResponse(Call<LoginRetrofitData> call, Response<LoginRetrofitData> response) {
                Log.d(TAG, "onResponse: " + response.body());
                if (response.body().getMessage().equals("로그인 성공")) {
                    Toast.makeText(LoginActivity.this, response.body().getUserId(), Toast.LENGTH_SHORT).show();
                    intent = new Intent(LoginActivity.this, HomeActivity.class);
                    String userId = response.body().getUserId();
                    String userName = response.body().getNickname();
                    userInfoSharedPreferences(userId, userName, localUserImage);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoginRetrofitData> call, Throwable t) {
                t.getMessage();
            }
        });
    }

    void loginCheck() {
        loginId = loginIdEt.getText().toString();
        loginPw = loginPwEt.getText().toString();
        Log.d(TAG, "loginCheck: ");
        if (!loginId.isEmpty() && !loginPw.isEmpty()) {
            Log.d(TAG, "loginCheck: if" + loginId + "  " + loginPw);
            loginApi();
        } else {
            Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    // 카카오가 설치되어 있는지 확인 하는 메서드또한 카카오에서 제공 콜백 객체를 이용함
    Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
        @Override
        public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
            updateKakaoLoginUi();
            return null;
        }
    };

    void updateKakaoLoginUi() {
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                if (user != null) {
                    Log.d(TAG, "invoke: id " + user.getId());
                    Log.d(TAG, "invoke: email " + user.getKakaoAccount().getEmail());
                    Log.d(TAG, "invoke:  nickname " + user.getKakaoAccount().getProfile().getNickname());
                    Log.d(TAG, "invoke: image" + user.getKakaoAccount().getProfile().getProfileImageUrl());
                    kakaoId = String.valueOf(user.getId());
                    kakaoEmail = user.getKakaoAccount().getEmail();
                    kakaoName = user.getKakaoAccount().getProfile().getNickname();
                    kakaoImage = user.getKakaoAccount().getProfile().getProfileImageUrl();
                    registerKakaoSocial();

                } else {

                }
                return null;
            }
        });
    }

    void registerKakaoSocial() {
        String pw = "socialNotPw";
        String social = "kakao";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(KakaoSocialInterface.REGISTER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RegisterInterface registerInterface = retrofit.create(RegisterInterface.class);
        Call<String> call = registerInterface.getUserRegist(kakaoName, kakaoEmail, pw, kakaoEmail, social, kakaoImage);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d(TAG, "onResponse: " + response.body());
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                userInfoSharedPreferences(kakaoEmail, kakaoName, kakaoImage);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    void userInfoSharedPreferences(String userId, String userName, String userImage) {
        SharedPreferences sharedPreferences = getSharedPreferences("유저 정보", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userId", userId);
        editor.putString("userName", userName);
        editor.putString("userImage", userImage);
        editor.commit();

    }

    void autoLogin(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("유저 정보", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId","");
        if(!userId.isEmpty()){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        }
        else {
            Log.d(TAG, "autoLogin: "+ "저장된 아이디가 없습니다.");
        }
    }


}