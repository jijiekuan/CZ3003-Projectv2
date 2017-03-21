package com.example.zadmin.relaxia.events.MemoryGameEvents.ui;

import com.example.zadmin.relaxia.events.AbstractEvent;
import com.example.zadmin.relaxia.events.EventObserver;

/**
 * Created by Shide on 21/3/17.
 */

public class FlipCardEvent extends AbstractEvent {
    public static final String TYPE = FlipCardEvent.class.getName();

    public final int id;

    public FlipCardEvent(int id) {
        this.id = id;
    }
    @Override
    protected void fire(EventObserver eventObserver) {
        eventObserver.onEvent(this);
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
