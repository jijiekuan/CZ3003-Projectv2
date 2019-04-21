package com.example.zadmin.relaxia.Engine;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.example.zadmin.relaxia.Common.Memory;
import com.example.zadmin.relaxia.Common.Music;
import com.example.zadmin.relaxia.Common.Shared;
import com.example.zadmin.relaxia.GameObjects.MemoryGameObject;
import com.example.zadmin.relaxia.Models.MemoryGame.BoardArrangment;
import com.example.zadmin.relaxia.Models.MemoryGame.BoardConfiguration;
import com.example.zadmin.relaxia.Models.MemoryGame.Game;
import com.example.zadmin.relaxia.Models.MemoryGame.GameState;
import com.example.zadmin.relaxia.Models.MemoryGame.Theme.Theme;
import com.example.zadmin.relaxia.Models.MemoryGame.Theme.Themes;
import com.example.zadmin.relaxia.R;
import com.example.zadmin.relaxia.UI.MemoryGameUI.PopUpManager;
import com.example.zadmin.relaxia.Utilities.Clock;
import com.example.zadmin.relaxia.Utilities.Utils;
import com.example.zadmin.relaxia.events.ConnectTheDotsEvents.ConnectDotsDifficultySelectedEvent;
import com.example.zadmin.relaxia.events.ConnectTheDotsEvents.EventObserverConnectTheDots;
import com.example.zadmin.relaxia.events.ConnectTheDotsEvents.StartConnectDotsEvent;
import com.example.zadmin.relaxia.events.Event;
import com.example.zadmin.relaxia.events.EventObserverViewDataAnalysis;
import com.example.zadmin.relaxia.events.MemoryGameEvents.EventObserverMemoryGame;
import com.example.zadmin.relaxia.events.MemoryGameEvents.ui.BackGameEvent;
import com.example.zadmin.relaxia.events.MemoryGameEvents.ui.DifficultySelectedEvent;
import com.example.zadmin.relaxia.events.MemoryGameEvents.ui.FlipCardEvent;
import com.example.zadmin.relaxia.events.MemoryGameEvents.ui.FlipDownCardsEvent;
import com.example.zadmin.relaxia.events.MemoryGameEvents.ui.GameWonEvent;
import com.example.zadmin.relaxia.events.MemoryGameEvents.ui.HidePairCardsEvent;
import com.example.zadmin.relaxia.events.MemoryGameEvents.ui.NextGameEvent;
import com.example.zadmin.relaxia.events.MemoryGameEvents.ui.ResetBackgroundEvent;
import com.example.zadmin.relaxia.events.MemoryGameEvents.ui.StartEvent;
import com.example.zadmin.relaxia.events.MemoryGameEvents.ui.ThemeSelectedEvent;
import com.example.zadmin.relaxia.events.PictureToWordsEvents.EventObserverPictureToWords;
import com.example.zadmin.relaxia.events.ViewDataAnalysisEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zAdmin on 21/3/2017.
 */

public class Engine implements EventObserverMemoryGame, EventObserverPictureToWords, EventObserverConnectTheDots, EventObserverViewDataAnalysis{

    private static final String TAG = "EngineClass";

    private static Engine mInstance = null;


    private MemoryGameObject MemoryGame = new MemoryGameObject();


    private ScreenController mScreenController;
    private Handler mHandler;

    private Engine() {
        Log.i(TAG, "Engine private method. Creates new screenController");
        mScreenController = ScreenController.getInstance();
        mHandler = new Handler();
    }

    public static Engine getInstance() {
        if (mInstance == null) {
            Log.i(TAG, "New engine instance being created");
            mInstance = new Engine();
        }
        return mInstance;
    }

    public void start() {
        Log.i(TAG, "Engine Start!");
        Shared.eventBus.listen(DifficultySelectedEvent.TYPE, this);
        Shared.eventBus.listen(FlipCardEvent.TYPE, this);
        Shared.eventBus.listen(StartEvent.TYPE, this);
        Shared.eventBus.listen(ThemeSelectedEvent.TYPE, this);
        Shared.eventBus.listen(BackGameEvent.TYPE, this);
        Shared.eventBus.listen(NextGameEvent.TYPE, this);
        Shared.eventBus.listen(ResetBackgroundEvent.TYPE, this);
        Shared.eventBus.listen(ViewDataAnalysisEvent.TYPE, this);
        Shared.eventBus.listen(ConnectDotsDifficultySelectedEvent.TYPE, this);
        Shared.eventBus.listen(StartConnectDotsEvent.TYPE, this);
    }

    public void stop() {
        MemoryGame.mPlayingGame = null;
        MemoryGame.mBackgroundImage.setImageDrawable(null);
        MemoryGame.mBackgroundImage = null;
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;

        Shared.eventBus.unlisten(DifficultySelectedEvent.TYPE, this);
        Shared.eventBus.unlisten(FlipCardEvent.TYPE, this);
        Shared.eventBus.unlisten(StartEvent.TYPE, this);
        Shared.eventBus.unlisten(ThemeSelectedEvent.TYPE, this);
        Shared.eventBus.unlisten(BackGameEvent.TYPE, this);
        Shared.eventBus.unlisten(NextGameEvent.TYPE, this);
        Shared.eventBus.unlisten(ResetBackgroundEvent.TYPE, this);
        Shared.eventBus.unlisten(ConnectDotsDifficultySelectedEvent.TYPE, this);
        Shared.eventBus.unlisten(StartConnectDotsEvent.TYPE, this);
        Shared.eventBus.unlisten(ViewDataAnalysisEvent.TYPE, this);

        mInstance = null;
    }

    @Override
    public void onEvent(ResetBackgroundEvent event) {
        Drawable drawable = MemoryGame.mBackgroundImage.getDrawable();
        if (drawable != null) {
            ((TransitionDrawable) drawable).reverseTransition(2000);
        } else {
            new AsyncTask<Void, Void, Bitmap>() {

                @Override
                protected Bitmap doInBackground(Void... params) {
                    Bitmap bitmap = Utils.scaleDown(R.drawable.background, Utils.screenWidth(), Utils.screenHeight());
                    return bitmap;
                }

                protected void onPostExecute(Bitmap bitmap) {
                    MemoryGame.mBackgroundImage.setImageBitmap(bitmap);
                };

            }.execute();
        }
    }

    @Override
    public void onEvent(StartEvent event) {
        Log.i("EventBusClass", "onEvent StartEvent");
        mScreenController.openScreen(ScreenController.Screen.THEME_SELECT);
    }

    @Override
    public void onEvent(NextGameEvent event) {
        PopUpManager.closePopup();
        int difficulty = MemoryGame.mPlayingGame.boardConfiguration.difficulty;
        if (MemoryGame.mPlayingGame.gameState.achievedStars == 3 && difficulty < 6) {
            difficulty++;
        }
        Shared.eventBus.notify(new DifficultySelectedEvent(difficulty));
    }

    @Override
    public void onEvent(BackGameEvent event) {
        PopUpManager.closePopup();
        mScreenController.openScreen(ScreenController.Screen.DIFFICULTY);
    }

    @Override
    public void onEvent(ThemeSelectedEvent event) {

        Log.i("EventBusClass", "Enter themeSelected event");

        MemoryGame.mSelectedTheme = event.theme;
        mScreenController.openScreen(ScreenController.Screen.DIFFICULTY);
        AsyncTask<Void, Void, TransitionDrawable> task = new AsyncTask<Void, Void, TransitionDrawable>() {

            @Override
            protected TransitionDrawable doInBackground(Void... params) {
                Bitmap bitmap = Utils.scaleDown(R.drawable.background, Utils.screenWidth(), Utils.screenHeight());
                Bitmap backgroundImage = Themes.getBackgroundImage(MemoryGame.mSelectedTheme);
                backgroundImage = Utils.crop(backgroundImage, Utils.screenHeight(), Utils.screenWidth());
                Drawable backgrounds[] = new Drawable[2];
                backgrounds[0] = new BitmapDrawable(Shared.context.getResources(), bitmap);
                backgrounds[1] = new BitmapDrawable(Shared.context.getResources(), backgroundImage);
                TransitionDrawable crossfader = new TransitionDrawable(backgrounds);
                return crossfader;
            }

            @Override
            protected void onPostExecute(TransitionDrawable result) {
                super.onPostExecute(result);
                MemoryGame.mBackgroundImage.setImageDrawable(result);
                result.startTransition(2000);
            }
        };
        task.execute();
    }


    @Override
    public void onEvent(DifficultySelectedEvent event) {
        MemoryGame.mFlippedId = -1;
        MemoryGame.mPlayingGame = new Game();
        MemoryGame.mPlayingGame.boardConfiguration = new BoardConfiguration(event.difficulty);
        MemoryGame.mPlayingGame.theme = MemoryGame.mSelectedTheme;
        MemoryGame.mToFlip = MemoryGame.mPlayingGame.boardConfiguration.numTiles;

        // arrange board
        arrangeBoard();

        // start the screen
        mScreenController.openScreen(ScreenController.Screen.MEMORYGAME);
    }

    private void arrangeBoard() {
        BoardConfiguration boardConfiguration = MemoryGame.mPlayingGame.boardConfiguration;
        BoardArrangment boardArrangment = new BoardArrangment();

        // build pairs
        // result {0,1,2,...n} // n-number of tiles
        List<Integer> ids = new ArrayList<Integer>();
        for (int i = 0; i < boardConfiguration.numTiles; i++) {
            ids.add(i);
        }
        // shuffle
        // result {4,10,2,39,...}
        Collections.shuffle(ids);

        // place the board
        List<String> tileImageUrls = MemoryGame.mPlayingGame.theme.tileImageUrls;
        Collections.shuffle(tileImageUrls);
        boardArrangment.pairs = new HashMap<Integer, Integer>();
        boardArrangment.tileUrls = new HashMap<Integer, String>();
        int j = 0;
        for (int i = 0; i < ids.size(); i++) {
            if (i + 1 < ids.size()) {
                // {4,10}, {2,39}, ...
                boardArrangment.pairs.put(ids.get(i), ids.get(i + 1));
                // {10,4}, {39,2}, ...
                boardArrangment.pairs.put(ids.get(i + 1), ids.get(i));
                // {4,
                boardArrangment.tileUrls.put(ids.get(i), tileImageUrls.get(j));
                boardArrangment.tileUrls.put(ids.get(i + 1), tileImageUrls.get(j));
                i++;
                j++;
            }
        }

        MemoryGame.mPlayingGame.boardArrangment = boardArrangment;
    }

    @Override
    public void onEvent(FlipCardEvent event) {
        // Log.i("my_tag", "Flip: " + event.id);
        int id = event.id;
        if (MemoryGame.mFlippedId == -1) {
            MemoryGame.mFlippedId = id;
            // Log.i("my_tag", "Flip: mFlippedId: " + event.id);
        } else {
            if (MemoryGame.mPlayingGame.boardArrangment.isPair(MemoryGame.mFlippedId, id)) {
                // Log.i("my_tag", "Flip: is pair: " + mFlippedId + ", " + id);
                // send event - hide id1, id2
                Shared.eventBus.notify(new HidePairCardsEvent(MemoryGame.mFlippedId, id), 1000);
                // play music
                mHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Music.playCorrent();
                    }
                }, 1000);
                MemoryGame.mToFlip -= 2;
                if (MemoryGame.mToFlip == 0) {
                    int passedSeconds = (int) (Clock.getInstance().getPassedTime() / 1000);
                    Clock.getInstance().pause();
                    int totalTime = MemoryGame.mPlayingGame.boardConfiguration.time;
                    GameState gameState = new GameState();
                    MemoryGame.mPlayingGame.gameState = gameState;
                    // remained seconds
                    gameState.remainedSeconds = totalTime - passedSeconds;

                    // calc stars
                    if (passedSeconds <= totalTime / 2) {
                        gameState.achievedStars = 3;
                    } else if (passedSeconds <= totalTime - totalTime / 5) {
                        gameState.achievedStars = 2;
                    } else if (passedSeconds < totalTime) {
                        gameState.achievedStars = 1;
                    } else {
                        gameState.achievedStars = 0;
                    }

                    // calc score
                    gameState.achievedScore = MemoryGame.mPlayingGame.boardConfiguration.difficulty * gameState.remainedSeconds * MemoryGame.mPlayingGame.theme.id;

                    // save to memory
                    //Memory.save(MemoryGame.mPlayingGame.theme.id, MemoryGame.mPlayingGame.boardConfiguration.difficulty, gameState.achievedStars);
                    Memory.save(MemoryGame.mPlayingGame.theme.id, MemoryGame.mPlayingGame.boardConfiguration.difficulty,gameState.achievedStars,gameState.achievedScore,passedSeconds);
                    Shared.eventBus.notify(new GameWonEvent(gameState), 1200);
                }
            } else {
                // Log.i("my_tag", "Flip: all down");
                // send event - flip all down
                Shared.eventBus.notify(new FlipDownCardsEvent(), 1000);
            }
            MemoryGame.mFlippedId = -1;
            // Log.i("my_tag", "Flip: mFlippedId: " + mFlippedId);
        }
    }

    public Game getActiveGame() {
        return MemoryGame.mPlayingGame;
    }

    public Theme getSelectedTheme() {
        return MemoryGame.mSelectedTheme;
    }

    public void setBackgroundImageView(ImageView backgroundImage) {
        MemoryGame.mBackgroundImage = backgroundImage;
    }

    @Override
    public void onEvent(StartConnectDotsEvent event) {
        ScreenController.getInstance().openScreen(ScreenController.Screen.CTD_DIFFICULTY);
    }

    @Override
    public void onEvent(ConnectDotsDifficultySelectedEvent event) {
        Shared.Difficulty = event.difficulty;
        ScreenController.getInstance().openScreen(ScreenController.Screen.CTDGAME);

    }

    @Override
    public void onEvent(ViewDataAnalysisEvent event) {
        ScreenController.getInstance().openScreen(ScreenController.Screen.DATA_ANALYSIS);

    }



    @Override
    public void onEvent(Event event) {
        Log.i("EventBusClass", "Enter main onEvent method");
        if(event instanceof StartEvent){
            Log.i("EventBusClass", "new StartEvent");
            onEvent(new StartEvent());
        }
        else if(event instanceof ThemeSelectedEvent){
            Log.i("EventBusClass", "new ThemeSelectedEvent");
            Theme temp = ((ThemeSelectedEvent) event).theme;
            onEvent(new ThemeSelectedEvent(temp));
        }
        else if(event instanceof DifficultySelectedEvent) {
            Log.i("EventBusClass", "new Difficulty SelectedEvent");
            int difficulty = ((DifficultySelectedEvent) event).difficulty;
            onEvent(new DifficultySelectedEvent(difficulty));
        }
        else if(event instanceof FlipCardEvent){
            Log.i("EventBusClass", "new FlipCardEvent");
            int id = ((FlipCardEvent)event).id;
            onEvent(new FlipCardEvent(id));
        }
        else if(event instanceof NextGameEvent){
            Log.i(TAG, "new NextGameEvent");
            onEvent(new NextGameEvent());
        }
        else if(event instanceof ResetBackgroundEvent){
            Log.i(TAG, "new ResetBackgroundEvent");
            onEvent(new ResetBackgroundEvent());
        }
        else if(event instanceof StartConnectDotsEvent){
            Log.i(TAG, "new ConnectTheDotsEvent");
            onEvent(new StartConnectDotsEvent());
        }
        else if(event instanceof ConnectDotsDifficultySelectedEvent){
            Log.i(TAG, "new ConnectDotsDifficultySelectedEvent");
            int difficulty = ((ConnectDotsDifficultySelectedEvent) event).difficulty;
            onEvent(new ConnectDotsDifficultySelectedEvent(difficulty));
        }
        else if(event instanceof ViewDataAnalysisEvent) {
            Log.i(TAG, " new ViewDataAnalysisEvent");
            onEvent(new ViewDataAnalysisEvent());
        }
    }


}
