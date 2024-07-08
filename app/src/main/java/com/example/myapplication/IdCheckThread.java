package com.example.myapplication;

import android.widget.TextView;

public class IdCheckThread extends Thread{
    String idCheck;
    TextView idCheckTv;

//    public IdCheckThread(String idCheck){
//        this.idCheck = idCheck;
//    }

    @Override
    public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


    }
}
