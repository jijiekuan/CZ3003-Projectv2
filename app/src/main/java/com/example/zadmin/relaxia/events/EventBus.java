package com.example.zadmin.relaxia.events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Handler;
import android.util.Log;

public class EventBus {

    private static final String TAG = "EventBusClass";

    private Handler mHandler;
    private static EventBus mInstance = null;
    private final Map<String, List<EventObserver>> events = Collections.synchronizedMap(new HashMap<String, List<EventObserver>>());
    private Object obj = new Object();

    private EventBus() {
        mHandler = new Handler();
    }

    public static EventBus getInstance() {
        Log.i(TAG, "Eventbus created");
        if (mInstance == null) {
            mInstance = new EventBus();
        }
        return mInstance;
    }

    synchronized public void listen(String eventType, EventObserver eventObserver) {
        Log.i(TAG, "Eventbus listen");
        List<EventObserver> observers = events.get(eventType);
        if (observers == null) {
            observers = Collections.synchronizedList(new ArrayList<EventObserver>());
        }
        observers.add(eventObserver);
        events.put(eventType, observers);
    }

    synchronized public void unlisten(String eventType, EventObserver eventObserver) {
        List<EventObserver> observers = events.get(eventType);
        if (observers != null) {
            observers.remove(eventObserver);
        }
    }

    public void notify(Event event) {
        Log.i(TAG, "Inside notify method");
        synchronized (obj) {
            List<EventObserver> observers = events.get(event.getType()); //get this list of EventObservers of event type
            if (observers != null) {
                for (EventObserver observer : observers) {
                    Log.i(TAG, "Firing method:");
                    AbstractEvent abstractEvent = (AbstractEvent) event; //downcasting! Event is an interface
                    abstractEvent.fire(observer);
                }
            }
        }
    }

    public void notify(final Event event, long delay) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                EventBus.this.notify(event);
            }
        }, delay);
    }

}

