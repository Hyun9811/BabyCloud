package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegisterInterface {

    String REGISTER_URL = "http://3.34.191.129/";
    @FormUrlEncoded
    @POST("register.php")
    Call<String> getUserRegist(
            @Field("name") String name,
            @Field("id") String id,
            @Field("pw") String pw,
            @Field("email") String email,
            @Field("social") String social,
            @Field("image") String image

    );
}
