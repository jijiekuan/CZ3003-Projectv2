package com.example.jj.myapplication;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConnectDotsActivity extends AppCompatActivity {

    private int screenWidth;
    private int screenHeight;
    private int difficulty;
    private static final int SCREEN_BOUNDARY = 200;
    private static final int DOT_COMFORT_ZONE = 128;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_connect_dots);

        difficulty = getIntent().getIntExtra("difficulty", 0);

        screenWidth = getResources().getDisplayMetrics().widthPixels-SCREEN_BOUNDARY;
        screenHeight = getResources().getDisplayMetrics().heightPixels-SCREEN_BOUNDARY;

        ConnectDotsView game = (ConnectDotsView) findViewById(R.id.connectDotsView);
        game.setPoints(createPoints());

    }

    private List<Point> createPoints() {
        List<Point> mPoints = new ArrayList<Point>();
        Random r = new Random();
        int count = 0;
        while(count<difficulty) {
            int x = r.nextInt(screenWidth)+100;
            int y = r.nextInt(screenHeight)+100;
            if(verifyPoint(x, y, mPoints)) {
                Point point = new Point(x, y);
                mPoints.add(point);
                count++;
            }
        }
        return mPoints;
    }

    private boolean verifyPoint(int x, int y, List<Point> mPoints) {
        if(mPoints.isEmpty()) {
            return true;
        }
        //To check if points overlap.
        for(Point point : mPoints) {
            if((Math.abs(x-point.x)<DOT_COMFORT_ZONE) && (Math.abs(y-point.y)<DOT_COMFORT_ZONE)) {
                return false;
            }
        }
        return true;
    }


}
