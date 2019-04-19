package com.example.a3673605.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import javax.security.auth.callback.Callback;


public class TimeLimit extends EcranJeu1 {
    private long time;
    private  TextView timeleft;
    private Handler handler;
    private Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app=(TheApplication)getApplication().getApplicationContext();
        app.mode=app.TIMELIMIT;
        this.timeleft=findViewById(R.id.timeLeft);
        timeleft.setText("" + (int)time);
    }
    protected void setMyLayout()
    {
        setContentView(R.layout.timelimit);
    }

    private void resetTime(long time){
        this.time=time;
    }

    public void countDownStart() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    long millis = System.currentTimeMillis()-TimeLimit.this.time;
                    int seconds = 60-((int) (millis / 1000));
                    if(seconds>=0) {
                        timeleft.setText("Temps restant:" + (seconds));
                        TimeLimit.this.app.setTime(seconds);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1 * 1000);
    }
    public void onClick(final View v){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(R.string.play);
        alertDialog.setPositiveButton(R.string.continu,
                new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //app.onReset();
                        TimeLimit.this.timeleft.setText("Temps restant:"+60);
                        resetTime(System.currentTimeMillis());
                        app.setTime(60);//J'ai juste mis une valeur superieur à 0 il faut la modifier pour qu'elle corresponde au parametres d'entrées
                        reset(v);
                        countDownStart();
                    }
                });
        alertDialog.create();
        alertDialog.show();
    }
    public void changertemps(int secondes){
        ((TextView)findViewById(R.id.Score1)).setText(String.valueOf(secondes));
    }
}

