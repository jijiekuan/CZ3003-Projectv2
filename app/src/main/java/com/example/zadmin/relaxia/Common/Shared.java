package com.example.zadmin.relaxia.Common;

/**
 * Created by zAdmin on 21/3/2017.
 */

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.example.zadmin.relaxia.Engine.Engine;
import com.example.zadmin.relaxia.events.EventBus;

public class Shared {

    public static Context context;
    public static FragmentActivity activity;
    public static Engine engine;
    public static EventBus eventBus;
    public static int Difficulty;
}
