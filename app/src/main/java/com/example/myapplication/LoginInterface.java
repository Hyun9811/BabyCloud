package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginInterface {
    String REGISTER_URL = "http://3.34.191.129/";
    @FormUrlEncoded
    @POST("login.php")
    Call<LoginRetrofitData> getUserLogin(

            @Field("loginId") String loginId,
            @Field("loginPw") String loginPw

    );
}
