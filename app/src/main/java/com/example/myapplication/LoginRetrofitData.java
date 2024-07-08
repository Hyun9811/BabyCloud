package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class LoginRetrofitData {
    @SerializedName("message") String message;
    @SerializedName("nickname") String nickname;
    @SerializedName("userId") String userId;

    public String getMessage(){
        return message;
    }
    public String getNickname(){
        return nickname;
    }

    public String getUserId(){
        return userId;
    }
}
