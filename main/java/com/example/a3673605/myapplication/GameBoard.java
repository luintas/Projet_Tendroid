package com.example.a3673605.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.io.InterruptedIOException;
import java.util.ArrayList;

import tendroid.model.Position;
import tendroid.model.PositionList;
import tendroid.model.TenGame;

/**
 * Created by 3673605 on 20/02/19.
 */

public class GameBoard extends SurfaceView implements SurfaceHolder.Callback {
    TheApplication app;
    int canvasWidth;
    int cellSize;
    Paint paint = new Paint();
    private Thread1 gameThread;
    private volatile boolean playing;
    private volatile boolean isMoving=false;
    public int descente=0;
    public float descentepack;
    public boolean isRefill=false;
    public boolean isPack=false;
    public int tabrefill[]=new int[25];
    public ArrayList<Position> listeEmptyPos = new ArrayList<Position>();
    public ArrayList<Position> listecasedescente = new ArrayList<Position>();
    public ArrayList<Integer> tabdescentepack = new ArrayList<Integer>();
    public ArrayList<Integer> tabpackvaleur = new ArrayList<Integer>();
    public ArrayList<Position> tabfuturempty = new ArrayList<Position>();
    public Position posappui;
    public boolean asksave=false;

    public int xoxo=0;
    public int yoyo=0;

    public GameBoard(Context c) {
        super(c);
        getHolder().addCallback(this);
        app = (TheApplication) (c.getApplicationContext());
    }

    public GameBoard(Context c, AttributeSet as) {
        super(c, as);
        getHolder().addCallback(this);
        app = (TheApplication) (c.getApplicationContext());
    }
    public void resume(){
        playing=true;
        gameThread = new Thread1();
        gameThread.start();
    }
    public class Thread1 extends Thread{
        public Thread1(){
            super();
        }
        @Override
        public void run(){
            while(playing){
                update();
                reDraw();
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void pause(){
        playing=false;

        try{
            gameThread.join();
        }catch(InterruptedException e){
            Log.e("ERR","Joining Thread");
        }
    }

    public boolean isPackDone(){
        for(int i=0;i<listecasedescente.size();i++){
        }
        if(listecasedescente==null){
            return true;
        }
        for(int i=0;i<listecasedescente.size();i++){
            if(((int)((tabdescentepack.get(i))*cellSize))> (descentepack) ){
                return false;
            }
        }
        return true;
    }
    public void PackCase(){
        int y=1;
        for(int i=0;i<listecasedescente.size();i++){
            if(y==0){
                y=1;
                i=0;
            }
            if(((int)((tabdescentepack.get(i))*cellSize)*10)<=descentepack) {
                tabdescentepack.remove(i);
                listecasedescente.remove(i);
                tabpackvaleur.remove(i);
                y=0;
            }
        }
    }
    public void setPack(){

        tabfuturempty = (ArrayList<Position>) app.getJeu().getSelectedGroup().clone();
        tabfuturempty.remove(0);
        descentepack=0;
        tabdescentepack.clear();
        listecasedescente.clear();
        int k,l;
        for(int i=0;i<5;i++){
            for(int j=0;j<4;j++){
                k=j+1;
                l=0;
                while(k<5){
                    if(tabfuturempty.contains(new Position(i,k))   && !(tabfuturempty.contains(new Position(i,j)))){
                        l++;
                    }
                    k++;
                }
                if(l>0){
                    tabdescentepack.add((Integer)l);
                    listecasedescente.add(new Position(i,j));
                    int p = app.getJeu().get(new Position(i,j));
                    tabpackvaleur.add(p);
                }
            }
        }
    }

    public void update() {
        if (isMoving) {
            if (isPack) {
                PackCase();
                descentepack=descentepack+(cellSize/10);
                if (isPackDone()) {
                    int compteurmax = 1;
                    for (int x = 0; x < 5; x++) {
                        for (int y = 0; y < 5; y++) {
                            if (app.leJeu.get(new Position(x, y)) == null ){

                            }else {
                                if (app.leJeu.get(new Position(x, y)) > compteurmax) {
                                    compteurmax = app.leJeu.get(new Position(x, y));
                                }
                            }
                        }
                    }
                    if (compteurmax >= 5) {
                        if (compteurmax >= 7) {
                            compteurmax--;
                        }
                        compteurmax--;
                    }
                    for (int x = 0; x < 25; x++) {
                        int i = (int)((Math.random())*100);
                        int compteur = 50;
                        int cc=1;
                        for (int z = 1; z < compteurmax; z++) {
                            if (i < compteur) {
                                tabrefill[x] = z;
                                cc=2;
                                break;
                            }
                            compteur = compteur + (int)(Math.pow(0.5, z+1)*100);
                        }
                        if(cc==1) {
                            tabrefill[x] = compteurmax;
                        }else{
                            cc=1;
                        }
                    }
                    listeEmptyPos=(ArrayList<Position>) app.getJeu().emptyPositions().clone();
                    app.leJeu.refill(tabrefill);
                    isPack = false;
                    isRefill = true;
                    tabdescentepack.clear();
                    listecasedescente.clear();
                    tabpackvaleur.clear();
                    descente=0;
                }
                int bouboul=1;
                for (int x = 4; x >= 0; x--) {//ligne
                    for (int y = 0; y < 5; y++) {//colonne
                        if(listeEmptyPos.contains(new Position(y,x))){
                            bouboul=2;
                        }
                    }
                    if(bouboul==2){
                        break;
                    }
                    descente=descente+cellSize;

                }
            }

            if (isRefill) {
                if (descente <= canvasWidth) {
                    descente=descente+canvasWidth/50;
                    //descente++;
                } else {
                    listeEmptyPos.clear();
                    isMoving = false;
                    isRefill = false;
                }
            }
        }
    }

    @Override
    public void onDraw(Canvas c) {
        c.drawColor(Color.WHITE);
        SharedPreferences a = PreferenceManager.getDefaultSharedPreferences((EcranJeu) getContext());
        int terne = a.getInt("terne", 255);

        if (isRefill) {
            for (int x = 0; x < 5; x++) {
                for (int y = 0; y < 5; y++) {
                        if (app.getJeu().get(new Position(x, y)) == 1) {
                            paint.setColor(Color.BLACK);
                        }
                        if (app.getJeu().get(new Position(x, y)) == 2) {
                            paint.setColor(Color.MAGENTA);
                        }
                        if (app.getJeu().get(new Position(x, y)) == 3) {
                            paint.setColor(Color.BLUE);
                        }
                        if (app.getJeu().get(new Position(x, y)) == 4) {
                            paint.setColor(Color.CYAN);
                        }
                        if (app.getJeu().get(new Position(x, y)) == 5) {
                            paint.setColor(Color.rgb(11, 102, 35));
                        }
                        if (app.getJeu().get(new Position(x, y)) == 6) {
                            paint.setColor(Color.rgb(76, 187, 23));
                        }
                        if (app.getJeu().get(new Position(x, y)) == 7) {
                            paint.setColor(Color.YELLOW);
                        }
                        if (app.getJeu().get(new Position(x, y)) == 8) {
                            paint.setColor(Color.rgb(255, 166, 00));
                        }
                        if (app.getJeu().get(new Position(x, y)) == 9) {
                            paint.setColor(Color.RED);
                        }

                    paint.setAlpha(terne);
                        if (listeEmptyPos.contains(new Position(x, y))) {
                            c.drawRect(x * cellSize, (y * cellSize) - (canvasWidth - descente), (x + 1) * cellSize, ((y + 1) * cellSize) - (canvasWidth - descente), paint);
                        } else {
                            c.drawRect(x * cellSize, y * cellSize, (x + 1) * cellSize, (y + 1) * cellSize, paint);
                        }

                }
            }
            paint.setTextSize((float) (cellSize * 0.5));
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            for (int x = 0; x < 5; x++) {
                for (int y = 0; y < 5; y++) {
                    if (app.getJeu().get(new Position(x, y)) != null) {
                        if (listeEmptyPos.contains(new Position(x, y))) {
                            paint.setColor(Color.WHITE);
                            c.drawText("" + app.getJeu().get(new Position(x, y)/*Integer.toString(app.getJeu().get(new Position(x, y))*/),
                                    (x * cellSize) + cellSize / 3,
                                    ((y * cellSize) + cellSize * 2 / 3) - (canvasWidth - descente),
                                    paint);
                        }else {
                            paint.setColor(Color.WHITE);
                            c.drawText("" + app.getJeu().get(new Position(x, y)/*Integer.toString(app.getJeu().get(new Position(x, y))*/),
                                    (x * cellSize) + cellSize / 3,
                                    (y * cellSize) + cellSize * 2 / 3,
                                    paint);
                        }
                    }
                }
            }
        }else {
            if (isPack) {
                for(int i=0;i<listecasedescente.size();i++){
                    paint.setColor(Color.BLACK);
                    if (tabpackvaleur.get(i) == 1) {
                        paint.setColor(Color.BLACK);
                    }
                    if (tabpackvaleur.get(i) == 2) {
                        paint.setColor(Color.MAGENTA);
                    }
                    if (tabpackvaleur.get(i) == 3) {
                        paint.setColor(Color.BLUE);
                    }
                    if (tabpackvaleur.get(i) == 4) {
                        paint.setColor(Color.CYAN);
                    }
                    if (tabpackvaleur.get(i) == 5) {
                        paint.setColor(Color.rgb(11, 102, 35));
                    }
                    if (tabpackvaleur.get(i) == 6) {
                        paint.setColor(Color.rgb(76, 187, 23));
                    }
                    if (tabpackvaleur.get(i) == 7) {
                        paint.setColor(Color.YELLOW);
                    }
                    if (tabpackvaleur.get(i) == 8) {
                        paint.setColor(Color.rgb(255, 166, 00));
                    }
                    if (tabpackvaleur.get(i) == 9) {
                        paint.setColor(Color.RED);
                    }
                    c.drawRect(listecasedescente.get(i).getCol() * cellSize, (listecasedescente.get(i).getLig() * cellSize) + (descentepack), (listecasedescente.get(i).getCol() + 1) * cellSize, (((listecasedescente.get(i).getLig() * cellSize) + 1) * cellSize) + (descentepack), paint);
                    paint.setColor(Color.WHITE);
                    c.drawText("" + app.getJeu().get(listecasedescente.get(i)/*Integer.toString(app.getJeu().get(new Position(x, y))*/),
                            (listecasedescente.get(i).getCol() * cellSize) + cellSize / 3,
                            ((listecasedescente.get(i).getLig() * cellSize) + cellSize * 2 / 3)+descentepack,
                            paint);
                }
                for (int x = 0; x < 5; x++) {
                    for (int y = 0; y < 5; y++) {
                        if (!listecasedescente.contains(new Position(x, y))) {
                            if (app.getJeu().get(new Position(x, y)) != null) {
                                if (app.getJeu().get(new Position(x, y)) == 1) {
                                    paint.setColor(Color.BLACK);
                                }
                                if (app.getJeu().get(new Position(x, y)) == 2) {
                                    paint.setColor(Color.MAGENTA);
                                }
                                if (app.getJeu().get(new Position(x, y)) == 3) {
                                    paint.setColor(Color.BLUE);
                                }
                                if (app.getJeu().get(new Position(x, y)) == 4) {
                                    paint.setColor(Color.CYAN);
                                }
                                if (app.getJeu().get(new Position(x, y)) == 5) {
                                    paint.setColor(Color.rgb(11, 102, 35));
                                }
                                if (app.getJeu().get(new Position(x, y)) == 6) {
                                    paint.setColor(Color.rgb(76, 187, 23));
                                }
                                if (app.getJeu().get(new Position(x, y)) == 7) {
                                    paint.setColor(Color.YELLOW);
                                }
                                if (app.getJeu().get(new Position(x, y)) == 8) {
                                    paint.setColor(Color.rgb(255, 166, 00));
                                }
                                if (app.getJeu().get(new Position(x, y)) == 9) {
                                    paint.setColor(Color.RED);
                                }

                                paint.setAlpha(terne);

                                if (!listecasedescente.contains(new Position(x, y))) {
                                    c.drawRect(x * cellSize, y * cellSize, (x + 1) * cellSize, (y + 1) * cellSize, paint);
                                }

                                paint.setTextSize((float) (cellSize * 0.5));
                                paint.setFlags(Paint.ANTI_ALIAS_FLAG);

                                paint.setColor(Color.WHITE);
                                c.drawText("" + app.getJeu().get(new Position(x, y)/*Integer.toString(app.getJeu().get(new Position(x, y))*/),
                                        (x * cellSize) + cellSize / 3,
                                        (y * cellSize) + cellSize * 2 / 3,
                                        paint);
                            }
                        }
                    }
                }
            } else {
                for (int x = 0; x < 5; x++) {
                    for (int y = 0; y < 5; y++) {
                        if (app.getJeu().get(new Position(x, y)) != null) {
                            Position p =new Position(x, y);
                            if (app.getJeu().get(p) == 1) {
                                paint.setColor(Color.BLACK);
                            }
                            if (app.getJeu().get(p) == 2) {
                                paint.setColor(Color.MAGENTA);
                            }
                            if (app.getJeu().get(p) == 3) {
                                paint.setColor(Color.BLUE);
                            }
                            if (app.getJeu().get(p) == 4) {
                                paint.setColor(Color.CYAN);
                            }
                            if (app.getJeu().get(p) == 5) {
                                paint.setColor(Color.rgb(11, 102, 35));
                            }
                            if (app.getJeu().get(p) == 6) {
                                paint.setColor(Color.rgb(76, 187, 23));
                            }
                            if (app.getJeu().get(p) == 7) {
                                paint.setColor(Color.YELLOW);
                            }
                            if (app.getJeu().get(p) == 8) {
                                paint.setColor(Color.rgb(255, 166, 00));
                            }
                            if (app.getJeu().get(p) == 9) {
                                paint.setColor(Color.RED);
                            }
                            paint.setAlpha(terne);
                            if (app.leJeu.getSelectedGroup() != null && app.leJeu.getSelectedGroup().contains(new Position(x, y))) {
                                paint.setColor(Color.RED);
                            }
                            c.drawRect(x * cellSize, y * cellSize, (x + 1) * cellSize, (y + 1) * cellSize, paint);
                            paint.setTextSize((float) (cellSize * 0.5));
                            paint.setFlags(Paint.ANTI_ALIAS_FLAG);

                            paint.setColor(Color.WHITE);
                            c.drawText("" + app.getJeu().get(new Position(x, y)/*Integer.toString(app.getJeu().get(new Position(x, y))*/),
                                    (x * cellSize) + cellSize / 3,
                                    (y * cellSize) + cellSize * 2 / 3,
                                    paint);
                        }
                    }
                }
                //System.out.println("  "+app.leJeu.get(new Position(0,0))+app.leJeu.get(new Position(0,1))+app.leJeu.get(new Position(0,2))+app.leJeu.get(new Position(0,3))+app.leJeu.get(new Position(0,4))+"\n"+app.leJeu.get(new Position(1,0))+app.leJeu.get(new Position(1,1))+app.leJeu.get(new Position(1,2))+app.leJeu.get(new Position(1,3))+app.leJeu.get(new Position(1,4))+"\n"+app.leJeu.get(new Position(2,0))+app.leJeu.get(new Position(2,1))+app.leJeu.get(new Position(2,2))+app.leJeu.get(new Position(2,3))+app.leJeu.get(new Position(2,4))+"\n"+app.leJeu.get(new Position(3,0))+app.leJeu.get(new Position(3,1))+app.leJeu.get(new Position(3,2))+app.leJeu.get(new Position(3,3))+app.leJeu.get(new Position(3,4))+"\n"+app.leJeu.get(new Position(4,0))+app.leJeu.get(new Position(4,1))+app.leJeu.get(new Position(4,2))+app.leJeu.get(new Position(4,3))+app.leJeu.get(new Position(4,4))+"\n");
                if (app.isWin()) {
                    if(asksave==false){
                    paint.setTextSize(200);
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setColor(Color.RED);
                    c.drawText(getResources().getString(R.string.phrasewin),
                            50,
                            400,
                            paint);
                        ((EcranJeu) getContext()).popup_save();
                        asksave=true;
                    }
                } else {
                    if (app.isLoosed()) {
                        c.drawColor(Color.BLACK);
                        paint.setTextSize(150);
                        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                        paint.setColor(Color.RED);
                        c.drawText(getResources().getString(R.string.phraseLose),
                                50,
                                400,
                                paint);
                    }
                }
            }
        }
    }

    public void reDraw() {
        Canvas c = getHolder().lockCanvas();
        if (c != null) {
            this.onDraw(c);
            getHolder().unlockCanvasAndPost(c);
        }else{
            System.out.println("ERREURRRRRRRRR");
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder sh, int f, int w, int h) {
        canvasWidth = w;
        cellSize = w / 5; // ATTENTION: constante
        //reDraw();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int action = event.getAction();
        TenGame theGame = app.getJeu();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                if (!isMoving) {
                    if(theGame.getSelectedGroup()==null) {
                        theGame.transition(new Position(x / cellSize, y / cellSize));
                    }else{
                        if(theGame.getSelectedGroup().contains(new Position(x / cellSize, y / cellSize))) {
                            setPack();
                            isPack=true;
                            isMoving=true;
                            posappui=new Position(x/cellSize,y/cellSize);

                            ((EcranJeu) getContext()).changerscore(app.score);
                            compteurIncre(new Position(x / cellSize, y / cellSize));
                            theGame.transition(new Position(x / cellSize, y / cellSize));
                            this.app.saveGame();
                        }else{
                            theGame.transition(new Position(x / cellSize, y / cellSize));
                        }
                    }
                }
                return true;
            }
            default:
                return false;
        }
    }
    public void compteurIncre(Position p){
        if (app.getJeu().getSelectedGroup() ==null){
            return;
        }
        if (app.getJeu().getSelectedGroup().contains(p)){
            app.score+=app.getJeu().getSelectedGroup().size() * (int)(app.getJeu().get(p));
        }
    }
    public void reset(){
        app.onReset();
        isPack=false;
        isMoving=false;
        isRefill=false;
        asksave=false;
        reDraw();
    }


}