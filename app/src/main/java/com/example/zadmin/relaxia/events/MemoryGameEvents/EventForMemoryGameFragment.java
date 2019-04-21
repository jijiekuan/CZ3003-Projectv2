package com.example.zadmin.relaxia.events.MemoryGameEvents;

import com.example.zadmin.relaxia.events.EventObserver;
import com.example.zadmin.relaxia.events.MemoryGameEvents.ui.FlipDownCardsEvent;
import com.example.zadmin.relaxia.events.MemoryGameEvents.ui.GameWonEvent;
import com.example.zadmin.relaxia.events.MemoryGameEvents.ui.HidePairCardsEvent;

/**
 * Created by Shide on 21/3/17.
 */

public interface EventForMemoryGameFragment extends EventObserver {
    void onEvent(HidePairCardsEvent event);
    void onEvent(FlipDownCardsEvent event);
    void onEvent(GameWonEvent event);
}
