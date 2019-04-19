package com.example.a3673605.myapplication;

/**
 * Created by 3673605 on 03/04/19.
 */

public class Score {
    private int score;
    private String nom;
    public Score(String line){
        int i=line.length()-1;
        String n="";
        String s="";
        int no;
        while(line.charAt(i)!=' '){
            n=line.charAt(i)+n;
            i--;
        }
        no=Integer.parseInt(n);
        this.score=no;
        while(i>=0){
            s=line.charAt(i)+s;
            i--;
        }
        this.nom=s;
    }

    public int getScore() {
        return score;
    }

    public String getNom() {
        return nom;
    }
}
