package com.example.zadmin.relaxia.Fragments.MemoryGameFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zadmin.relaxia.Common.Shared;
import com.example.zadmin.relaxia.Models.MemoryGame.Game;
import com.example.zadmin.relaxia.R;
import com.example.zadmin.relaxia.UI.MemoryGameUI.BoardView;
import com.example.zadmin.relaxia.UI.MemoryGameUI.PopUpManager;
import com.example.zadmin.relaxia.Utilities.Clock;
import com.example.zadmin.relaxia.Utilities.FontLoader;
import com.example.zadmin.relaxia.events.Event;
import com.example.zadmin.relaxia.events.MemoryGameEvents.EventForMemoryGameFragment;
import com.example.zadmin.relaxia.events.MemoryGameEvents.ui.FlipDownCardsEvent;
import com.example.zadmin.relaxia.events.MemoryGameEvents.ui.GameWonEvent;
import com.example.zadmin.relaxia.events.MemoryGameEvents.ui.HidePairCardsEvent;

/**
 * Created by Shide on 21/3/17.
 */

public class MemoryGameFragment extends Fragment implements EventForMemoryGameFragment {
    private BoardView mBoardView;
    private TextView mTime;
    private ImageView mTimeImage;
    private LinearLayout ads;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.mg_game_fragment, container, false);
        view.setClipChildren(false);
        ((ViewGroup)view.findViewById(R.id.game_board)).setClipChildren(false);
        mTime = (TextView) view.findViewById(R.id.time_bar_text);
        mTimeImage = (ImageView) view.findViewById(R.id.time_bar_image);
        FontLoader.setTypeface(Shared.context, new TextView[] {mTime}, FontLoader.Font.GROBOLD);
        mBoardView = BoardView.fromXml(getActivity().getApplicationContext(), view);
        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.game_container);
        frameLayout.addView(mBoardView);
        frameLayout.setClipChildren(false);

        // build board
        buildBoard();
        Shared.eventBus.listen(FlipDownCardsEvent.TYPE, this);
        Shared.eventBus.listen(HidePairCardsEvent.TYPE, this);
        Shared.eventBus.listen(GameWonEvent.TYPE, this);

        return view;
    }

    @Override
    public void onDestroy() {
        Shared.eventBus.unlisten(FlipDownCardsEvent.TYPE, this);
        Shared.eventBus.unlisten(HidePairCardsEvent.TYPE, this);
        Shared.eventBus.unlisten(GameWonEvent.TYPE, this);
        super.onDestroy();
    }

    private void buildBoard() {
        Game game = Shared.engine.getActiveGame();
        int time = game.boardConfiguration.time;
        setTime(time);
        mBoardView.setBoard(game);

        startClock(time);
    }

    private void setTime(int time) {
        int min = time / 60;
        int sec = time - min*60;
        mTime.setText(" " + String.format("%02d", min) + ":" + String.format("%02d", sec));
    }

    private void startClock(int sec) {
        Clock clock = Clock.getInstance();
        clock.startTimer(sec*1000, 1000, new Clock.OnTimerCount() {

            @Override
            public void onTick(long millisUntilFinished) {
                setTime((int) (millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                setTime(0);
            }
        });
    }

    @Override
    public void onEvent(GameWonEvent event) {
        mTime.setVisibility(View.GONE);
        mTimeImage.setVisibility(View.GONE);
        PopUpManager.showPopupWon(event.gameState);
    }

    @Override
    public void onEvent(FlipDownCardsEvent event) {
        mBoardView.flipDownAll();
    }

    @Override
    public void onEvent(HidePairCardsEvent event) {
        mBoardView.hideCards(event.id1, event.id2);
    }

    @Override
    public void onEvent(Event event) {

    }

}
