package com.example.zadmin.relaxia.events.MemoryGameEvents.ui;

import android.util.Log;

import com.example.zadmin.relaxia.events.AbstractEvent;
import com.example.zadmin.relaxia.events.EventObserver;

/**
 * Created by Shide on 21/3/17.
 */

public class StartEvent extends AbstractEvent {
    public static final String TYPE = StartEvent.class.getName();
    @Override
    protected void fire(EventObserver eventObserver) {
        Log.i("EventBusClass", "Start event Fired");
        eventObserver.onEvent(this);
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
