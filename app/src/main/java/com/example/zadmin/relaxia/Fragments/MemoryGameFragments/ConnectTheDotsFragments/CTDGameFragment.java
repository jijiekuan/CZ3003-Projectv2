package com.example.zadmin.relaxia.Fragments.MemoryGameFragments.ConnectTheDotsFragments;

import android.app.Fragment;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zadmin.relaxia.Common.Shared;
import com.example.zadmin.relaxia.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Shide on 4/4/17.
 */

public class CTDGameFragment extends Fragment {

    private int screenWidth;
    private int screenHeight;
    private int difficulty;
    private static final int SCREEN_BOUNDARY = 200;
    private static final int DOT_COMFORT_ZONE = 128;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.ctd_game_fragment, container, false);

        ConnectDotsView game = (ConnectDotsView) v.findViewById(R.id.connectDotsView);
        difficulty = Shared.Difficulty;

        screenWidth = getResources().getDisplayMetrics().widthPixels-SCREEN_BOUNDARY;
        screenHeight = getResources().getDisplayMetrics().heightPixels-SCREEN_BOUNDARY;
        game.setPoints(createPoints());

        return v;
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
