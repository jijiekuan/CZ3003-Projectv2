package com.example.zadmin.relaxia.events.MemoryGameEvents.ui;

import com.example.zadmin.relaxia.events.AbstractEvent;
import com.example.zadmin.relaxia.events.EventObserver;

/**
 * Created by Shide on 21/3/17.
 */

public class ThemeSelectedEvent extends AbstractEvent {
    @Override
    protected void fire(EventObserver eventObserver) {
        eventObserver.onEvent(this);
    }

    @Override
    public String getType() {
        return null;
    }
}
