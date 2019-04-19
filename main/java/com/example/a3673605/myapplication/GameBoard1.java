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

import tendroid.model.Position;
import tendroid.model.PositionList;
import tendroid.model.TenGame;

/**
 * Created by 3673605 on 20/02/19.
 */

public class GameBoard1 extends SurfaceView implements SurfaceHolder.Callback {
    TheApplication app;
    int canvasWidth;
    int cellSize;
    Paint paint = new Paint();
    private boolean asksave=false;

    int modeCouleur;
    private int preset1[]={Color.rgb(204,0,0),Color.rgb(204,102,0),Color.rgb(204,204,0),Color.rgb(102,204,0),Color.rgb(0,204,0),Color.rgb(0,204,102),Color.rgb(0,204,204),Color.rgb(0,102,204),Color.rgb(0,0,204),Color.rgb(102,0,204)},
            preset2[]={Color.rgb(128,128,128),Color.rgb(255,0,127),Color.rgb(255,0,255),Color.rgb(127,0,255),Color.rgb(0,0,255),Color.rgb(0,128,255),Color.rgb(0,255,128),Color.rgb(0,255,0),Color.rgb(0,128,255),Color.rgb(255,255,0)},
            preset3[]={Color.rgb(0,0,153),Color.rgb(0,204,0),Color.rgb(255,153,51),Color.rgb(178,102,255),Color.rgb(255,51,153),Color.rgb(0,153,153),Color.rgb(255,51,51),Color.rgb(153,51,255),Color.rgb(0,153,0),Color.rgb(76,0,153)};
    private int tab[][]={preset1,preset2,preset3};

    public GameBoard1(Context c) {
        super(c);
        getHolder().addCallback(this);
        app = (TheApplication) (c.getApplicationContext());
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(c);
        modeCouleur = m.getInt("modeCouleur",0);
    }

    public GameBoard1(Context c, AttributeSet as) {
        super(c, as);
        getHolder().addCallback(this);
        app = (TheApplication) (c.getApplicationContext());
    }

    @Override
    public void onDraw(Canvas c) {
        c.drawColor(Color.BLACK);
        SharedPreferences a = PreferenceManager.getDefaultSharedPreferences((EcranJeu1) getContext());
        int terne = a.getInt("terne", 255);
        paint.setColor(Color.RED);
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                paint.setColor(tab[0][app.getJeu().get(new Position(x, y))]);

                paint.setAlpha(terne);
                if(app.leJeu.getSelectedGroup()!=null && app.leJeu.getSelectedGroup().contains(new Position(x, y))){
                    paint.setColor(Color.RED);
                }
                c.drawRect(x*cellSize, y*cellSize, (x+1)*cellSize, (y+1)*cellSize, paint);

            }
        }
        paint.setColor(Color.WHITE);
        for (int x = 0; x <= canvasWidth; x += cellSize) {
            c.drawLine(x, 0, x, canvasWidth, paint);
        }
        for (int y = 0; y <= canvasWidth; y += cellSize) {
            c.drawLine(0, y, canvasWidth, y, paint);
        }

        paint.setTextSize((float)(cellSize*0.5));
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                paint.setColor(Color.WHITE);
                c.drawText(""+app.getJeu().get(new Position(x, y)/*Integer.toString(app.getJeu().get(new Position(x, y))*/),
                        (x * cellSize) + cellSize/3,
                        (y * cellSize) + cellSize*2/3,
                        paint);
            }
        }
                /*



                System.out.println("  " + app.leJeu.get(new Position(0, 0)) + app.leJeu.get(new Position(0, 1)) + app.leJeu.get(new Position(0, 2)) + app.leJeu.get(new Position(0, 3)) + app.leJeu.get(new Position(0, 4)) + "\n" + app.leJeu.get(new Position(1, 0)) + app.leJeu.get(new Position(1, 1)) + app.leJeu.get(new Position(1, 2)) + app.leJeu.get(new Position(1, 3)) + app.leJeu.get(new Position(1, 4)) + "\n" + app.leJeu.get(new Position(2, 0)) + app.leJeu.get(new Position(2, 1)) + app.leJeu.get(new Position(2, 2)) + app.leJeu.get(new Position(2, 3)) + app.leJeu.get(new Position(2, 4)) + "\n" + app.leJeu.get(new Position(3, 0)) + app.leJeu.get(new Position(3, 1)) + app.leJeu.get(new Position(3, 2)) + app.leJeu.get(new Position(3, 3)) + app.leJeu.get(new Position(3, 4)) + "\n" + app.leJeu.get(new Position(4, 0)) + app.leJeu.get(new Position(4, 1)) + app.leJeu.get(new Position(4, 2)) + app.leJeu.get(new Position(4, 3)) + app.leJeu.get(new Position(4, 4)) + "\n");

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
                if (app.leJeu.getSelectedGroup() != null && app.leJeu.getSelectedGroup().contains(new Position(x, y))) {
                    paint.setColor(Color.RED);
                }
                c.drawRect(x * cellSize, y * cellSize, (x + 1) * cellSize, (y + 1) * cellSize, paint);
            }
        }

        paint.setTextSize((float) (cellSize * 0.5));
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                paint.setColor(Color.WHITE);
                c.drawText("" + app.getJeu().get(new Position(x, y)),
                        (x * cellSize) + cellSize / 3,
                        (y * cellSize) + cellSize * 2 / 3,
                        paint);
            }
        }*/
        if (app.isWin()) {
            if (asksave == false) {
                paint.setTextSize(200);
                paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                paint.setColor(Color.RED);
                c.drawText(getResources().getString(R.string.phrasewin),
                        50,
                        400,
                        paint);
                ((EcranJeu1) getContext()).popup_save();
                asksave = true;
            }
        } else {
            if (app.isLoosed()) {
                c.drawColor(Color.BLACK);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this.getContext());
                alertDialog.setMessage(R.string.phraseLose);
                alertDialog.create();
                alertDialog.show();
                app.onReset();
            }
            ((EcranJeu1) getContext()).changerscore(app.score);
        }
    }

    public void reDraw() {
        Canvas c = getHolder().lockCanvas();
        if (c != null) {
            this.onDraw(c);
            getHolder().unlockCanvasAndPost(c);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder sh, int f, int w, int h) {
        canvasWidth = w;
        cellSize = w / 5; // ATTENTION: constante
        reDraw();
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
                int tabrefill[]=new int[25];
                int compteurmax = 1;
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        if (app.leJeu.get(new Position(i, j)) == null ){

                        }else {
                            if (app.leJeu.get(new Position(i, j)) > compteurmax) {
                                compteurmax = app.leJeu.get(new Position(i, j));
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
                for (int k = 0; k < 25; k++) {
                    int i = (int)((Math.random())*100);
                    int compteur = 50;
                    int cc=1;
                    //System.out.println("wtf"+i);
                    for (int z = 1; z < compteurmax; z++) {
                        //System.out.println("allo ? "+(Math.pow(0.5, z+1)*100));
                        if (i < compteur) {
                            tabrefill[k] = z;
                            cc=2;
                            break;
                        }
                        compteur = compteur + (int)(Math.pow(0.5, z+1)*100);
                    }
                    if(cc==1) {
                        tabrefill[k] = compteurmax;
                    }else{
                        cc=1;
                    }
                    //System.out.println("wtf:"+compteur+"et"+compteurmax+" et "+cc);
                }
                compteurIncre(new Position(x / cellSize, y / cellSize));
                theGame.transition(new Position(x / cellSize, y / cellSize));
                theGame.refill(tabrefill);
                reDraw();
                this.app.saveGame();
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
        asksave=false;
        app.onReset();
        reDraw();
    }
}