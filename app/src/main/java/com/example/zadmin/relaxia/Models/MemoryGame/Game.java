package com.example.zadmin.relaxia.Models.MemoryGame;


import com.example.zadmin.relaxia.Models.MemoryGame.Theme.Theme;

/**
 * This is instance of active playing game
 *
 * @author sromku
 */
public class Game {

    /**
     * The board configuration
     */
    public BoardConfiguration boardConfiguration;

    /**
     * The board arrangment
     */
    public BoardArrangment boardArrangment;

    /**
     * The selected theme
     */
    public Theme theme;

    public GameState gameState;

}