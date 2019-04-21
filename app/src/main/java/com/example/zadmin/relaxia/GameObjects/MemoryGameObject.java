package com.example.zadmin.relaxia.GameObjects;

import android.widget.ImageView;

import com.example.zadmin.relaxia.Engine.ScreenController;
import com.example.zadmin.relaxia.Models.MemoryGame.Game;
import com.example.zadmin.relaxia.Models.MemoryGame.Theme.Theme;

/**
 * Created by Shide on 4/4/17.
 */

public class MemoryGameObject {

    public Game mPlayingGame = null;
    public int mFlippedId = -1;
    public int mToFlip = -1;
    public Theme mSelectedTheme;
    public ImageView mBackgroundImage;



}
