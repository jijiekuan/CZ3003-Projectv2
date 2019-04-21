package com.example.zadmin.relaxia.events.MemoryGameEvents;

import com.example.zadmin.relaxia.events.EventObserver;
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

/**
 * Created by Shide on 21/3/17.
 */

public interface EventObserverMemoryGame extends EventObserver {
    void onEvent(FlipCardEvent event);

    void onEvent(DifficultySelectedEvent event);


    void onEvent(StartEvent event);

    void onEvent(ThemeSelectedEvent event);


    void onEvent(BackGameEvent event);

    void onEvent(NextGameEvent event);

    void onEvent(ResetBackgroundEvent event);
}

