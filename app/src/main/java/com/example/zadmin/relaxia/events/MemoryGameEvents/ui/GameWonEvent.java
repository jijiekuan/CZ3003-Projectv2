package com.example.zadmin.relaxia.events.MemoryGameEvents.ui;

import com.example.zadmin.relaxia.Models.MemoryGame.GameState;
import com.example.zadmin.relaxia.events.AbstractEvent;
import com.example.zadmin.relaxia.events.EventObserver;

/**
 * Created by Shide on 21/3/17.
 */

public class GameWonEvent extends AbstractEvent{
    public static final String TYPE = NextGameEvent.class.getName();
    public GameState gameState;

    public GameWonEvent(GameState gameState) {
        this.gameState = gameState;
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
