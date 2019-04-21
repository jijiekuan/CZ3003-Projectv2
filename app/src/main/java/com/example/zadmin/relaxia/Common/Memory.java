package com.example.zadmin.relaxia.Common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.zadmin.relaxia.DataAnalysis.ScoreDataBaseHandler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Memory {

    private static final String TAG = "MemoryClass";

    private static final String SHARED_PREFERENCES_NAME = "ScoreForGames";
    private static String highStartKey = "theme_%d_difficulty_%d";
    private static ScoreDataBaseHandler dbHandler;


    public static void save(int theme, int difficulty, int stars) {
        int highStars = getHighStars(theme, difficulty);
        if (stars > highStars) {
            SharedPreferences sharedPreferences = Shared.context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
            Editor edit = sharedPreferences.edit();
            String key = String.format(highStartKey, theme, difficulty);
            edit.putInt(key, stars).commit();
        }
    }

    //This save method is only applicable to memory_game...what about the other 2 games?
    //Keep track of number stars using "theme and difficulty" as key.
    //If i want the database to keep track of how they play the game...i need to store a record everytime the game is completed.
    //I can store it using theme and difficulty level too for memory game.
    public static void save(int theme, int difficulty, int stars, int score, int time) {
        int highStars = getHighStars(theme, difficulty);
        if (stars > highStars) {
            SharedPreferences sharedPreferences = Shared.context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
            Editor edit = sharedPreferences.edit();
            String key = String.format(highStartKey, theme, difficulty);
            edit.putInt(key, stars).commit();
        }
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String dateTimeString = df.format(date);
        String game_type = "";
        if(theme ==1 ) {
            game_type = ScoreDataBaseHandler.MEMORY_GAME_ANIMALS;
        }
        else if(theme == 2){
            game_type = ScoreDataBaseHandler.MEMORY_GAME_MONSTERS;
        }
        dbHandler = ScoreDataBaseHandler.getInstance(Shared.context);
        dbHandler.insertIntoTable(game_type, difficulty, score, time, dateTimeString);
        dbHandler.dbToString();
    }





    public static int getHighStars(int theme, int difficulty) {
        SharedPreferences sharedPreferences = Shared.context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String key = String.format(highStartKey, theme, difficulty);
        return sharedPreferences.getInt(key, 0);
    }
}
