package com.example.a3673605.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by 3673605 on 19/04/19.
 */

public class EcranJeu1 extends Activity {
    TheApplication app;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);*/
        setMyLayout();
        app=(TheApplication)(getApplicationContext().getApplicationContext());
    }
    protected void setMyLayout()
    {
        setContentView(R.layout.ecranjeu1);
    }
    public void changerscore(int a){
        ((TextView)findViewById(R.id.Score1)).setText(String.valueOf(a));
    }

    public void quitter(View view) {
        finish();
    }

    public void reset(View view) {
        ((GameBoard1) findViewById(R.id.boardSurface)).reset();
    }

    public void onPause(){
        super.onPause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        boolean sombre = m.getBoolean("modesombre",false);
        LinearLayout l;
        l = (LinearLayout) findViewById(R.id.layout2);
        if (sombre){
            l.setBackgroundColor(Color.rgb(26, 26, 26));
        }else{
            l.setBackgroundColor(Color.WHITE);
        }
    }
    public void popup_save(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        LinearLayout ll_alert = new LinearLayout(this);

        final EditText ed_input = new EditText(this);
        ll_alert.addView(ed_input);
        ed_input.setHint(R.string.nom);

        alertDialog.setTitle(R.string.titre);
        alertDialog.setMessage(R.string.message);
        alertDialog.setView(ll_alert);

        alertDialog.setNegativeButton(R.string.quitter,null);
        alertDialog.setPositiveButton(R.string.save,
                new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String msg = ed_input.getText().toString();
                        app.saveScore(msg);
                    }
                });
        alertDialog.create();
        alertDialog.show();
    }
}