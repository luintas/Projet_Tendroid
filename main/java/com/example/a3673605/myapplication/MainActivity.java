package com.example.a3673605.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import tendroid.model.TenGame;

public class MainActivity extends Activity {
    public ArrayList<Score> scoreListe;
    TheApplication app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);*/
        setContentView(R.layout.activity_main);
        scoreListe = new ArrayList<Score>();
        app=((TheApplication)getApplicationContext().getApplicationContext());

    }
    public void affichecontinu(View v){
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        boolean modeexp = m.getBoolean("modeexperimentale",false);
        if(modeexp) {
            String line = null;
            try (BufferedReader is = new BufferedReader(
                    new InputStreamReader(openFileInput("save_Game.txt")))) {
                line = is.readLine();
            } catch (IOException e) {
            }
            if (line != null) {
                app.setLeJeu();
                goEcranJeu();
            } else {
                app.setLeJeuRandomize();
                goEcranJeu();
            }
        }else{
            String line = null;
            try (BufferedReader is = new BufferedReader(
                    new InputStreamReader(openFileInput("save_Game.txt")))) {
                line = is.readLine();
            } catch (IOException e) {
            }
            if (line != null) {
                app.setLeJeu();
                goEcranJeu1();
            } else {
                app.setLeJeuRandomize();
                goEcranJeu1();
            }
        }
    }
    public void afficheLePlateau(View V) {
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        boolean modeexp = m.getBoolean("modeexperimentale",false);
        if(modeexp) {
                app.setLeJeuRandomize();
                goEcranJeu();

        }else{
                app.setLeJeuRandomize();
                goEcranJeu1();

        }
    }
    public void afficheTimeLimit(View V) {
        Intent playIntent = new Intent(this, TimeLimit.class);
        startActivity(playIntent);
    }

    public void goEcranJeu1(){
        Intent playIntent = new Intent(this, EcranJeu1.class);
        startActivity(playIntent);
    }

    public void goEcranJeu(){
        Intent playIntent = new Intent(this, EcranJeu.class);
        startActivity(playIntent);
    }

    public void quitter(View view) {
        finish();
    }

    public void afficherPref(View v) {
        Intent playIntent = new Intent(MainActivity.this, Preferences.class);
        startActivity(playIntent);
    }
    public void afficherLesScores(View v){
        String res ="";
        String line = "";
        try (BufferedReader is = new BufferedReader(
                new InputStreamReader(openFileInput("save_file.txt")))){
            while((line = is.readLine())!=null) {
                Score a = new Score(line);
                if(a.getNom().charAt(0)!=' ') {
                    scoreListe.add(a);
                }
            }
        } catch (IOException e) {
        }
        int i,j;
        for (i=0;i<scoreListe.size();i++){
            for(j=i;j<scoreListe.size();j++){
                if(scoreListe.get(i).getScore()<scoreListe.get(j).getScore()){
                    Collections.swap(scoreListe,i,j);
                }
            }
        }
        for (i=0;i<scoreListe.size()&&i<10;i++){
            res=res+(i+1)+"."+scoreListe.get(i).getNom()+" : "+scoreListe.get(i).getScore()+"\n";
        }
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        LinearLayout ll_alert = new LinearLayout(this);
        alertDialog.setTitle(R.string.titre);
        alertDialog.setMessage(res);
        alertDialog.setNegativeButton(R.string.quitter,null);
        alertDialog.create();
        alertDialog.show();
        scoreListe.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        boolean sombre = m.getBoolean("modesombre",false);
        LinearLayout l;
        l = (LinearLayout) findViewById(R.id.layout1);
        if (sombre){
            l.setBackgroundColor(Color.rgb(26, 26, 26));
            l = (LinearLayout) findViewById(R.id.layout2);
            l.setBackgroundColor(Color.rgb(26, 26, 26));
        }else{
            l.setBackgroundColor(Color.WHITE);
            l = (LinearLayout) findViewById(R.id.layout2);
            l.setBackgroundColor(Color.WHITE);
        }
    }
    public void onStop() {
        super.onStop();
        app.saveGame();
    }
}
