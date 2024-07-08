package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface KakaoSocialInterface {
    String REGISTER_URL = "http://3.34.191.129/";
    @FormUrlEncoded
    @POST("kakaoSocialRegister.php")
    Call<KakaoUserInfo> getUserLogin(

            @Field("id") String kakaoId,
            @Field("kakaoEmail") String kakaoEmail,
            @Field("kakaoName") String kakaoName

    );
}
