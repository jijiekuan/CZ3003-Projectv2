package com.example.zadmin.relaxia;

/**
 * Created by zAdmin on 21/3/2017.
 */

import android.app.Application;

import com.example.zadmin.relaxia.Utilities.FontLoader;

public class GameApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FontLoader.loadFonts(this);
    }
}