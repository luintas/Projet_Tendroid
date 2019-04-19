package com.example.a3673605.myapplication;

import android.os.Handler;

public class HandleExtend extends Handler {
    public HandleExtend(Callback c){
        super(c);
    }

    public boolean handleMessage(int time) {
        long millis = System.currentTimeMillis() - time;
        int seconds = (int) (millis / 1000);
        int minutes = seconds / 60;
        seconds     = seconds % 60;
        return false;
    }

}