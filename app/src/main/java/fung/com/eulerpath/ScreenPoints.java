package fung.com.eulerpath;

import android.content.Context;

import java.util.ArrayList;

public class ScreenPoints {
    private int pointnumber;
    private int screenX;
    private int screenY;
    private int x;
    private int y;
    private int neighbours;
    private int[] nbrs;
    private ArrayList<Integer> BlockList = new ArrayList<Integer>();
    private String Color;
    private boolean allowed;
    private int tito=0;
    private boolean passed;
    public ScreenPoints(){

    }

    public ScreenPoints(int screenX, int screenY){
        this.screenX=screenX;
        this.screenY=screenY;
        this.Color = "#FFFFFF";
    }
    //-------------------------------setters
    public void setStuff(int number, int xd,int X,int yd, int Y,int neighbours, int[] nbrs){
//        for (int i=0;i<nbrs.length;i++){
//            System.out.print(nbrs[i]+"  -  ");
//        }
        System.out.print("\n");
        this.nbrs = nbrs;
        this.neighbours = neighbours;
        this.pointnumber=number;
        this.x=X*screenX/xd;
        this.y=Y*screenY/yd;
        this.Color = "#FFFFFF";
    }
    public void setColor(String color) {
        Color = color;
    }
    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }
    public void incrmnttito() {
        this.tito++;
    }
    public void setTito(int tito){
        this.tito = tito;
    }
    public void addBlackListPoint(int poinnum){
        BlockList.add(poinnum);
    }
    public void clearBlockList(){
        BlockList.clear();
    }
    //-------------------------------getters
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getDistance(int x, int y){
        int distance = (int) Math.sqrt( (Math.pow(x-this.x,2)) + (Math.pow(y-this.y,2)) );
        return distance;
    }
    public int[] getNbrs() {
        return nbrs;
    }
    public String getColor() {
        return Color;
    }
    public int getTito() {
        return tito;
    }
    public int getNeighbours() {
        return neighbours;
    }
    public boolean isAllowed() {
        return allowed;
    }

    public ArrayList<Integer> getBlockList() {
        return BlockList;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}
