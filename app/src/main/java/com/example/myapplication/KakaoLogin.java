package com.example.myapplication;

import android.app.Application;
import com.kakao.sdk.common.KakaoSdk;

public class KakaoLogin extends Application {

    private static KakaoLogin instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance= this;

        KakaoSdk.init(this,"c9c76167ab2856e3e930fd6b62b39669");

    }
}
