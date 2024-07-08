package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;



public class SignRetrofitData {
    String name,id,pw;
//    @SerializedName("name")  String name;
//    @SerializedName("id") String id;
//    @SerializedName("pw") String pw;
@SerializedName("message") String message;


    public SignRetrofitData(String name, String id, String pw){
        this.name = name;
        this.id = id;
        this.pw = pw;
    }

    public String getMessage(){
        return message;
    }
}
