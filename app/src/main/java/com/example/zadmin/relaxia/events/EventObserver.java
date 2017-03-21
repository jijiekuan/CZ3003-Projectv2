package com.example.zadmin.relaxia.events;

import com.example.zadmin.relaxia.events.MemoryGameEvents.ui.FlipCardEvent;

/**
 * Created by Shide on 21/3/17.
 */

public interface EventObserver {
    void onEvent(Event event);
}
