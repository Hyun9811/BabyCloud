package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegisterIdCheckApi {
    String REGISTER_URL = "http://3.34.191.129/";
    @FormUrlEncoded
    @POST("registerIdCheck.php")
    Call<String> getUserId(


            @Field("id") String id

    );
}
