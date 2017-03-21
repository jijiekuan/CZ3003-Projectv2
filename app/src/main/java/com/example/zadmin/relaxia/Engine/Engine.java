package com.example.zadmin.relaxia.Engine;

import com.example.zadmin.relaxia.events.ConnectTheDotsEvents.EventObserverConnectTheDots;
import com.example.zadmin.relaxia.events.Event;
import com.example.zadmin.relaxia.events.EventObserver;
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

/**
 * Created by zAdmin on 21/3/2017.
 */

public class Engine implements EventObserverMemoryGame, EventObserverPictureToWords, EventObserverConnectTheDots{

    @Override
    public void onEvent(FlipCardEvent event) {

    }

    @Override
    public void onEvent(DifficultySelectedEvent event) {

    }

    @Override
    public void onEvent(HidePairCardsEvent event) {

    }

    @Override
    public void onEvent(FlipDownCardsEvent event) {

    }

    @Override
    public void onEvent(StartEvent event) {

    }

    @Override
    public void onEvent(ThemeSelectedEvent event) {

    }

    @Override
    public void onEvent(GameWonEvent event) {

    }

    @Override
    public void onEvent(BackGameEvent event) {

    }

    @Override
    public void onEvent(NextGameEvent event) {

    }

    @Override
    public void onEvent(ResetBackgroundEvent event) {

    }

    @Override
    public void onEvent(Event event) {

    }
}
