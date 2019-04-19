package com.example.a3673605.myapplication;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import tendroid.model.Grid;
import tendroid.model.Position;
import tendroid.model.TenGame;

import static java.lang.Math.random;

/**
 * Created by 3673605 on 20/02/19.
 */

public class TheApplication extends Application {

    public static final int NORMAL=1;
    public static final int TIMELIMIT=2;
    public TenGame leJeu;
    //public Integer[][] precedent;
    public int score;
    public  int time;
    public  int mode;
    @Override

    public void onCreate() {
        super.onCreate();
        leJeu = new TenGame(randomizateur());
    }

    public void onReset(){
        leJeu = new TenGame(randomizateur());
        leJeu.pl=null;
        score=0;
        saveGame();
    }

    public TenGame getLeJeu(){
        return leJeu;
    }

    public void setLeJeuRandomize(){
        leJeu = new TenGame(randomizateur());
        score=0;
    }

    public void setLeJeu(){
        String res ="";
        int[] tab = new int[25];
        String line = "";
        try {BufferedReader is = new BufferedReader(new InputStreamReader(openFileInput("save_Game.txt")));
            int i=0;
            int j=0;
            int k=0;
            while((line = is.readLine())!=null) {
                if(k<25) {
                    tab[i % 25] = Integer.parseInt(line);
                    i = i + 5;
                    j++;
                    k++;
                    if (j >= 5) {
                        j = 0;
                        i++;
                    }
                }else{
                    score=Integer.parseInt(line);
                }
            }
        } catch (IOException e) {
        }
        leJeu=new TenGame(tab);

    }

    public void saveScore(String msg){
        String nom_score = msg+" "+score+"\n";
        try (FileOutputStream os =
                     openFileOutput("save_file.txt", Context.MODE_APPEND)) {
            os.write(nom_score.getBytes());
            os.close();
        } catch (IOException e) {
        }
    }
    public void saveGame(){
        String res = "";
        int i;
        for(i=1;i<=25;i++){
            res=res+leJeu.get(new Position((i-1)/5,(i-1)%5))+"\n";
        }
        res=res+score;
        try (FileOutputStream os =
                     openFileOutput("save_Game.txt", Context.MODE_PRIVATE)) {
            os.write(res.getBytes());
            os.close();
        } catch (IOException e) {
        }
    }

    public int[] randomizateur(){
        int[] ns= new int[25];
        for(int i =0; i<=24;i++){
            int j=(int)(Math.random()*10)+1;
            if(j<=4) {
                ns[i]=1;
            }else {
                if (j <= 7) {
                    ns[i] = 2;
                }else {

                    if (j <= 9) {
                        ns[i] = 3;
                    }else {

                        if (j == 10) {
                            ns[i] = 4;
                        }
                    }
                }
            }
        }
        return ns;
    }

    public int[] randomizateur1(){
        int[] ns= new int[25];
        for(int i =0; i<5;i++){
            for(int j =0; j<5;j++){
                ns[(i*5)+j]=j+i+1;
            }
        }
        ns[0]=2;
        return ns;
    }

    public boolean isLoosed(){
        for(int i =0; i<5;i++){
            for(int j =0; j<5;j++){
                if(leJeu.get(new Position(i, j)) != null) {
                    for (Position p1 : leJeu.adjPositions(new Position(i, j))) {
                        if (p1 != null) {
                            if (leJeu.get(new Position(i, j)).equals(leJeu.get(p1))) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
    public boolean isWin(){
        for(int i =0; i<5;i++){
            for(int j =0; j<5;j++){
                if(leJeu.get(new Position(i, j)) != null) {
                    if (leJeu.get(new Position(i, j)) == 10) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public TenGame getJeu(){
        return leJeu;
    }


    public boolean isLoosedTimer(){
        if(this.mode==this.NORMAL) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    for (Position p1 : leJeu.adjPositions(new Position(i, j))) {
                        if (leJeu.get(new Position(i, j)).equals(leJeu.get(p1))) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        else if(this.mode==this.TIMELIMIT)
            if(this.time<=0){
                return true;
            }
        return false;
    }
    public void setTime(int time){
        this.time=time;
    }
}