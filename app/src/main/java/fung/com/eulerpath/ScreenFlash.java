package fung.com.eulerpath;

public class ScreenFlash {
    private int x;
    private int y;
    private int raduis;
    private boolean grow;
    private int growspeed;
    private boolean growFinished=false;
    private boolean reversegrow=false;
    private String Color;
    public ScreenFlash(int screenX, int screenY){
        this.x = screenX/2;
        this.y = screenY/2;
        raduis=0;
        growspeed=5;
    }

    public void update(){
        if (grow){
            raduis += growspeed;
            if (raduis>2*x){
                grow=false;
                reversegrow=true;
                growFinished=true;
            }
        }
        if (reversegrow){
            raduis -= growspeed;
            if (raduis<=0){
                reversegrow=false;
            }
        }
    }

    public void setGrow(boolean grow) {
        this.grow = grow;
        growFinished=false;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public int getRaduis(){
        return this.raduis;
    }

    public boolean isGrowFinished() {
        return growFinished;
    }

    public void setReversegrow(boolean reversegrow) {
        this.reversegrow = reversegrow;
    }
    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public void setGrowFinished(boolean growFinished) {
        this.growFinished = growFinished;
    }
}