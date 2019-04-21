package com.example.zadmin.relaxia.events.ConnectTheDotsEvents;

import com.example.zadmin.relaxia.events.AbstractEvent;
import com.example.zadmin.relaxia.events.EventObserver;

/**
 * Created by Shide on 5/4/17.
 */

public class ConnectDotsDifficultySelectedEvent extends AbstractEvent {

    public final int difficulty;
    public static final String TYPE = ConnectDotsDifficultySelectedEvent.class.getName();

    public ConnectDotsDifficultySelectedEvent(int difficulty){
        this.difficulty = difficulty;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    protected void fire(EventObserver eventObserver) {
        eventObserver.onEvent(this);
    }
}
