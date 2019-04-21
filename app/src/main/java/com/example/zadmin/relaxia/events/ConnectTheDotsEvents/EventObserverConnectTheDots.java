package com.example.zadmin.relaxia.events.ConnectTheDotsEvents;

import com.example.zadmin.relaxia.events.EventObserver;
import com.example.zadmin.relaxia.events.MemoryGameEvents.ui.DifficultySelectedEvent;

/**
 * Created by Shide on 21/3/17.
 */

public interface EventObserverConnectTheDots extends EventObserver {
    void onEvent(StartConnectDotsEvent event);
    void onEvent(ConnectDotsDifficultySelectedEvent event);
}
