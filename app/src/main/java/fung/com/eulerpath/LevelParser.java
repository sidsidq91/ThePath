package fung.com.eulerpath;

import android.content.Context;

import org.json.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import static fung.com.eulerpath.GameView.screenPointses;
import static fung.com.eulerpath.GameView.pointnuber;
import static fung.com.eulerpath.GameView.emphasisCircle;

public class LevelParser {
    private int level;
    private Context context;
    public LevelParser(Context context){
        this.context = context;
    }
    public void parse(int level){
        this.level=level;
        InputStream inputStream = context.getResources().openRawResource(R.raw.lvls);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int ctr;
        try {
            ctr = inputStream.read();
            while (ctr != -1) {
                byteArrayOutputStream.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        }catch (IOException ex){
            System.out.println("reading err");
        }
        try {
            JSONObject obj = new JSONObject(byteArrayOutputStream.toString());
            JSONArray jsonArray = obj.getJSONArray("lvls");
            JSONObject levelObject = (JSONObject) jsonArray.get(level);
            //---------------------------------getting data out-------------------------------------
                String ln = levelObject.getString("levelNumber");
                String pn = levelObject.getString("pointNumber");
                int thepointNumber = Integer.valueOf(pn);
                pointnuber = thepointNumber;
                int levelNumber = Integer.valueOf(ln);
                JSONArray points = levelObject.getJSONArray("point");
                JSONObject[] point = new JSONObject[thepointNumber];
                int[] pointstuff = new int[7];         //0-pointnumber 1-xd 2-x 3-yd 4-y 5-nbrsnum 6-nbrs
                for (int i=0;i<thepointNumber;i++){
                    point[i] = (JSONObject) points.get(i);
                    //-------------------------------------------------
                    String poinnum = point[i].getString("PointNumer");
                    pointstuff[0] = Integer.valueOf(poinnum);
                    //-------------------------------------------------
                    String xd = point[i].getString("Xdevision");
                    pointstuff[1] = Integer.valueOf(xd);
                    //-------------------------------------------------
                    String xx = point[i].getString("X");
                    pointstuff[2] = Integer.valueOf(xx);
                    //-------------------------------------------------
                    String yd = point[i].getString("Ydevision");
                    pointstuff[3] = Integer.valueOf(yd);
                    //-------------------------------------------------
                    String y = point[i].getString("Y");
                    pointstuff[4] = Integer.valueOf(y);
                    //-------------------------------------------------
                    String nbrnum = point[i].getString("NbrNumber");
                    pointstuff[5] = Integer.valueOf(nbrnum);
                    //-------------------------------------------------
                    String nbrs = point[i].getString("Nbrs");
                    String[] nbs = nbrs.split(",");
                    int[] sides = new int[nbs.length];
                    for (int j=0;j<nbs.length;j++){
                        sides[j] = Integer.valueOf(nbs[j]);
                    }
                    //-------------------------------------------------
                    screenPointses[i].setStuff(i,pointstuff[1],pointstuff[2],pointstuff[3],pointstuff[4],pointstuff[5],sides);
                    emphasisCircle[i].setLocation(screenPointses[i].getX(),screenPointses[i].getY());
                }

        } catch (JSONException e) {
            System.out.println("parsing error");
        }
    }
}
