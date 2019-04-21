package com.example.zadmin.relaxia.events.ConnectTheDotsEvents;

import com.example.zadmin.relaxia.events.AbstractEvent;
import com.example.zadmin.relaxia.events.EventObserver;

/**
 * Created by Shide on 5/4/17.
 */

public class StartConnectDotsEvent extends AbstractEvent {
    public static final String TYPE = StartConnectDotsEvent.class.getName();
    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    protected void fire(EventObserver eventObserver) {
        eventObserver.onEvent(this);
    }
}
