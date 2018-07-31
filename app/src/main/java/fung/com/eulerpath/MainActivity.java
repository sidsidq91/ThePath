package fung.com.eulerpath;

import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    GameView gameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        Display display = getWindowManager().getDefaultDisplay();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Point size = new Point();
        display.getSize(size);
        gameView = new GameView(this,size.x,size.y);
        setContentView(gameView);
    }
    @Override
    protected void onResume(){
        super.onResume();
        gameView.onResume();
    }
    @Override
    protected void onPause(){
        super.onPause();
        gameView.onPause();
    }
}
