package com.example.zadmin.relaxia;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.example.zadmin.relaxia.Common.Shared;
import com.example.zadmin.relaxia.Engine.Engine;
import com.example.zadmin.relaxia.Engine.ScreenController;
import com.example.zadmin.relaxia.UI.MemoryGameUI.PopUpManager;
import com.example.zadmin.relaxia.Utilities.Utils;
import com.example.zadmin.relaxia.events.EventBus;
import com.example.zadmin.relaxia.events.MemoryGameEvents.ui.BackGameEvent;

import static com.example.zadmin.relaxia.Common.Shared.context;

public class MainActivity extends FragmentActivity {

    private ImageView mBackgroundImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //this.deleteDatabase(ScoreDataBaseHandler.DATABASE_NAME);
        context = getApplicationContext();
        Shared.engine = Engine.getInstance();
        Shared.eventBus = EventBus.getInstance();

        setContentView(R.layout.activity_main);
        mBackgroundImage = (ImageView) findViewById(R.id.background_image);

        Shared.activity = this;
        Shared.engine.start();
        Shared.engine.setBackgroundImageView(mBackgroundImage);


        // set background
        //setBackgroundImage();

        // set menu
        ScreenController.getInstance().openScreen(ScreenController.Screen.MAIN_MENU);
    }

    @Override
    protected void onDestroy() {
        Shared.engine.stop();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (PopUpManager.isShown()) {
            PopUpManager.closePopup();
            if (ScreenController.getLastScreen() == ScreenController.Screen.MEMORYGAME) {
                Shared.eventBus.notify(new BackGameEvent());
            }
        } else if (ScreenController.getInstance().onBack()) {
            super.onBackPressed();
        }
    }

    private void setBackgroundImage() {
        Bitmap bitmap = Utils.scaleDown(R.drawable.background, Utils.screenWidth(), Utils.screenHeight());
        bitmap = Utils.crop(bitmap, Utils.screenHeight(), Utils.screenWidth());
        bitmap = Utils.downscaleBitmap(bitmap, 2);
        mBackgroundImage.setImageBitmap(bitmap);
    }
}