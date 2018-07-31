package fung.com.eulerpath;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;

public class GameView extends SurfaceView implements Runnable{
    //<editor-fold>     Variables
    volatile boolean isPlaying;
    int level=0;
    private Thread gameThread=null;
    private SurfaceHolder surfaceHolder;
    private Canvas canvas;
    private Paint paint;
    private int x;
    private int y;
    public static int pointnuber=5;
    public static ScreenPoints[] screenPointses;
    public static EmphasisCircle[] emphasisCircle;
    private int[] xs = new int[60];
    private int[] ys = new int[60];
    int poincounter=0;
    private boolean JourneyBegun=false;
    private int onpoint;
    private int preonpoint=999;
    private float linefromx;
    private float linefromy;
    private ScreenFlash screenFlash;
    private LevelParser levelParserl;
    //</editor-fold>

    public GameView(Context context, int screenX, int screenY) {
        super(context);
        surfaceHolder = getHolder();
        paint = new Paint();
        screenPointses = new ScreenPoints[31];
        emphasisCircle = new EmphasisCircle[31];
        levelParserl = new LevelParser(context);
        for (int i=0;i<30;i++){
            screenPointses[i] = new ScreenPoints(screenX,screenY);
            emphasisCircle[i] = new EmphasisCircle();
        }
        nextLevel(9);
        screenFlash = new ScreenFlash(screenX,screenY);
    }

    @Override
    public void run() {
        while (isPlaying) {
            update();
            draw();
            control();
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x= (int) event.getX();
                y= (int) event.getY();
                checkBegun();
                break;
            case MotionEvent.ACTION_MOVE:
                x= (int) event.getX();
                y= (int) event.getY();
                check();
     //           checkWithOutGuide();
                break;
            case MotionEvent.ACTION_UP:
                JourneyBegun=false;
                poincounter=0;
                preonpoint=999;
                for (int i=0;i<pointnuber;i++){
                    screenPointses[i].setColor("#FFFFFF");
                    screenPointses[i].setAllowed(false);
                    screenPointses[i].setTito(0);
                    screenPointses[i].clearBlockList();
                    screenPointses[i].setPassed(false);
                }
                break;
        }
        invalidate();
        return true;
    }

    private void checkBegun() {
        int min=1000;
        for (int i=0;i<pointnuber;i++){
            if (screenPointses[i].getDistance(x,y)<min){
                min = screenPointses[i].getDistance(x,y);
                onpoint = i;
                JourneyBegun=true;
                linefromx=screenPointses[i].getX();
                linefromy=screenPointses[i].getY();
            }
        }
    }
    private void check(){
        for (int i=0;i<pointnuber;i++){
            if (Math.abs(x-screenPointses[i].getX())<30){
                if (Math.abs(y-screenPointses[i].getY())<30){
                    if(screenPointses[i].isAllowed()||preonpoint==999) {
                        onpoint = i;
                        if (preonpoint != onpoint) {
                            xs[poincounter] = screenPointses[i].getX();
                            ys[poincounter] = screenPointses[i].getY();
                            poincounter++;
                            linefromy = screenPointses[i].getY();
                            linefromx = screenPointses[i].getX();
                            if (preonpoint!=999){
                                screenPointses[preonpoint].incrmnttito();
                            }
                            int ppro =preonpoint;
                            preonpoint = onpoint;
                            emphasisCircle[onpoint].setGrow(true);
                            if (ppro!=999) {
                                screenPointses[preonpoint].incrmnttito();
                            }
                            setCircleColors(onpoint);
                        }
                    }else {
                        //Other Effects of a point not being allowed
                    }
                }
            }
        }
    }
    private void setCircleColors(int i){
        for (int j=0;j<pointnuber;j++){
            if (checkExistance(i,j)){
                screenPointses[j].setColor("#00FF00");
                screenPointses[j].setAllowed(true);
            }else {
                screenPointses[j].setAllowed(false);
                screenPointses[j].setColor("#FF0000");
            }
        }
    }
    private boolean checkExistance(int source, int key){
        boolean in=false;
        int[] alloweds = screenPointses[source].getNbrs();
        for (int i=0;i<alloweds.length;i++){
            if (alloweds[i] == key){
                in = true;
            }
        }
        return in;
    }

    private void checkWithOutGuide(){
        for (int i=0;i<pointnuber;i++){
            if (Math.abs(x-screenPointses[i].getX())<30){
                if (Math.abs(y-screenPointses[i].getY())<30){
                    if(screenPointses[i].isAllowed()||preonpoint==999) {
                        onpoint = i;
                        screenPointses[onpoint].setPassed(true);
                        if (preonpoint != onpoint) {
                            xs[poincounter] = screenPointses[i].getX();
                            ys[poincounter] = screenPointses[i].getY();
                            poincounter++;
                            linefromy = screenPointses[i].getY();
                            linefromx = screenPointses[i].getX();
                            if (preonpoint!=999){
                                screenPointses[preonpoint].addBlackListPoint(onpoint);
                                screenPointses[onpoint].addBlackListPoint(preonpoint);
                            }
                            preonpoint = onpoint;
                            emphasisCircle[onpoint].setGrow(true);
                            setAllowed(onpoint);
                        }
                    }else {
                        //Other Effects of a point not being allowed
                    }
                }
            }
        }
    }
    private void setAllowed(int i){
        for (int j=0;j<pointnuber;j++){
            if (!excheck(i,j)){
                screenPointses[j].setColor("#00FF00");
                screenPointses[j].setAllowed(true);
            }else {
                screenPointses[j].setAllowed(false);
                screenPointses[j].setColor("#FF0000");
            }
        }
    }
    private boolean excheck(int i, int j){
        boolean exists=false;
        ArrayList<Integer> blocks = screenPointses[i].getBlockList();
        Iterator e = blocks.iterator();
        while (e.hasNext()){
            int a = (int) e.next();
            if (j==a){
                exists=true;
            }
        }
        return exists;
    }

    private void update(){
        screenFlash.update();
        for (int i=0;i<pointnuber;i++){
            emphasisCircle[i].update();
        }
        int ccc=0;
        for (int i=0;i<pointnuber;i++){
            if (screenPointses[i].getNeighbours()<=screenPointses[i].getTito()){
                screenPointses[i].setAllowed(false);
                screenPointses[i].setColor("#FF0000");
            }
            if (screenPointses[i].getNeighbours()==screenPointses[i].getTito()){
                ccc++;
            }
        }
//        int ccc=0;
//        for (int i=0;i<pointnuber;i++){
//            if (screenPointses[i].isPassed()){
//                ccc++;
//            }
//        }
       if (ccc==pointnuber){
            screenFlash.setGrow(true);
        }
        if (screenFlash.isGrowFinished()) {
            level++;
            nextLevel(level);
            screenFlash.setGrowFinished(false);
        }
        if (preonpoint!=999) {
            if (screenPointses[preonpoint].getTito()>=screenPointses[preonpoint].getNeighbours()){
                for (int i=0;i<pointnuber;i++){
                    screenPointses[i].setAllowed(false);
                    screenPointses[i].setColor("#FF0000");
                }
            }
        }
    }
    private void draw(){
        if(surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.rgb(51, 0, 51));
            for (int i=0;i<pointnuber;i++){
                int[] nbsss = screenPointses[i].getNbrs();
                paint.setColor(Color.argb(70,153,153,153));
                for (int k=0;k<nbsss.length;k++){
                    canvas.drawLine(screenPointses[i].getX(),screenPointses[i].getY(),screenPointses[nbsss[k]].getX(),screenPointses[nbsss[k]].getY(),paint);
                }
            }
            paint.setColor(Color.rgb(255,255,255));
            paint.setStrokeWidth(12);
            if (JourneyBegun) {
                canvas.drawLine(linefromx, linefromy, x, y, paint);
            }
            if (JourneyBegun) {
                paint.setColor(Color.rgb(51,204,153));
                for (int i = 0; i < poincounter - 1; i++) {
                    canvas.drawLine(xs[i], ys[i], xs[i + 1], ys[i + 1], paint);
                }
            }
            for (int i=0;i<pointnuber;i++){
                paint.setColor(Color.parseColor(screenPointses[i].getColor()));
                canvas.drawCircle(screenPointses[i].getX(),screenPointses[i].getY(),10,paint);
            }
            paint.setColor(Color.YELLOW);
            canvas.drawCircle(screenFlash.getX(), screenFlash.getY(), screenFlash.getRaduis(), paint);
            paint.setColor(Color.argb(170,204,153,51));
            for (int i=0;i<pointnuber;i++){
                canvas.drawCircle(emphasisCircle[i].getX(),emphasisCircle[i].getY(),emphasisCircle[i].getRaduis(),paint);
            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
    private void control(){
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onResume(){
        isPlaying=true;
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void onPause(){
        isPlaying =false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void nextLevel(int level){
        levelParserl.parse(level);
    }
}
