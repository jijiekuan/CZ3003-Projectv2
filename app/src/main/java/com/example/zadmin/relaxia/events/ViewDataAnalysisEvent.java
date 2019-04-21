package com.example.zadmin.relaxia.events;

/**
 * Created by JJ on 8/4/2017.
 */

public class ViewDataAnalysisEvent extends AbstractEvent {
    public static final String TYPE = ViewDataAnalysisEvent.class.getName();
    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    protected void fire(EventObserver eventObserver) {
        eventObserver.onEvent(this);
    }
}
