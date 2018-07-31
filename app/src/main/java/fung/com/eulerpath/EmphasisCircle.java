package fung.com.eulerpath;

public class EmphasisCircle {
    private float raduis;
    private float growspeed;
    private boolean grow=false;
    private int x;
    private int y;
    private boolean decend=false;
    private boolean acsend=true;

    public EmphasisCircle(){
        growspeed= (float) 1.5;
    }
    public void setLocation(int x,int y){
        this.x = x;
        this.y = y;
    }
    public void update(){
        if(grow) {
            if(acsend) {
                raduis += growspeed;
            }
            if (decend){
                raduis -= growspeed;
            }
            if (raduis>50){
                acsend=false;
                decend=true;
            }
            if (raduis<=0){
                acsend=true;
                decend=false;
                grow=false;
            }
        }
    }

    public float getRaduis() {
        return raduis;
    }

    public void setGrow(boolean grow) {
        this.grow = grow;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
