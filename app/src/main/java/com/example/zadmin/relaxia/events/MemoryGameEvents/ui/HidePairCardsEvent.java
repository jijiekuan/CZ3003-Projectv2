package com.example.zadmin.relaxia.events.MemoryGameEvents.ui;

import com.example.zadmin.relaxia.events.AbstractEvent;
import com.example.zadmin.relaxia.events.EventObserver;

/**
 * Created by Shide on 21/3/17.
 */

public class HidePairCardsEvent extends AbstractEvent {

    public static final String TYPE = HidePairCardsEvent.class.getName();
    public int id1;
    public int id2;

    public HidePairCardsEvent(int id1, int id2) {
        this.id1 = id1;
        this.id2 = id2;
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
