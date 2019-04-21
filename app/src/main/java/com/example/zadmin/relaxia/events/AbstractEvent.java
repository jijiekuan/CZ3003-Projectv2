package com.example.zadmin.relaxia.events;

/**
 * Created by Shide on 21/3/17.
 */

public abstract class AbstractEvent implements Event {

    protected abstract void fire(EventObserver eventObserver);

}
