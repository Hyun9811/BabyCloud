package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class KakaoUserInfo {
    String id,email,name;
    @SerializedName("message") String message;
    @SerializedName("nickname") String nickname;
    KakaoUserInfo(String id, String email, String name){
        this.id = id;
        this.email = email;
        this.name = name;
    }
    public String getMessage(){
        return message;
    }

}
