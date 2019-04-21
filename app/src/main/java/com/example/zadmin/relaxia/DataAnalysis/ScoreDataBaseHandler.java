package com.example.zadmin.relaxia.DataAnalysis;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jjoe64.graphview.series.DataPoint;

/**
 * Created by Shide on 4/4/17.
 */

public class ScoreDataBaseHandler extends SQLiteOpenHelper {

    private static final String TAG = "ScoreDataBaseHandlerClass";

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Scores.db";


    public static final String TABLE_ALL = "TableForScores";



    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DIFF_LEVEL = "Difficulty";
    public static final String COLUMN_SCORE = "Score";
    public static final String COLUMN_TIME = "Time";
    public static final String COLUMN_DATETIME = "DateTime";
    public static final String COLUMN_GAMETYPE = "GameType";


    public static final String MEMORY_GAME_ANIMALS = "MemoryGameAnimals";
    public static final String MEMORY_GAME_MONSTERS = "MemoryGameMonsters";
    public static final String CONNECT_THE_DOTS = "ConnectTheDots";
    public static final String PICTURE_TO_WORDS = "PictureToWords";



    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_ALL + " ("
            + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_GAMETYPE + " TEXT,"
            + COLUMN_DIFF_LEVEL + " INTEGER, "
            + COLUMN_SCORE + " INTEGER, "
            + COLUMN_TIME + " INTEGER,"
            + COLUMN_DATETIME + " TEXT"
            + ");";

    private static ScoreDataBaseHandler mInstance;
    private Context context;
    public static synchronized ScoreDataBaseHandler getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (mInstance == null) {
            mInstance = new ScoreDataBaseHandler(context);
        }
        return mInstance;
    }

    public ScoreDataBaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        Log.i(TAG, "onOpen database!");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG, "onCreate databasehandler");
        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public void insertIntoTable(String gameType, int diff_level, int score, int time, String dateTime){
        SQLiteDatabase db = getWritableDatabase();
        /*String query = "INSERT INTO " + TABLE_ALL + "("
                + COLUMN_GAMETYPE + ", "
                + COLUMN_DIFF_LEVEL + ", "
                + COLUMN_SCORE + ", "
                + COLUMN_TIME + ", "
                + COLUMN_DATETIME + ") "
                + " VALUES ( "
                + "'"+ gameType +"' , "
                + diff_level+", "
                + score+", "
                + time+", "
                + "'"+ dateTime+"'"
                + ");";
        db.execSQL(query);*/
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_GAMETYPE, gameType);
        cv.put(COLUMN_DIFF_LEVEL, diff_level);
        cv.put(COLUMN_SCORE,score);
        cv.put(COLUMN_TIME, time);
        cv.put(COLUMN_DATETIME, dateTime);
        db.insert(TABLE_ALL, null, cv);
        Log.i(TAG, "Inserting into table. GameType: " + gameType + "diff_level: "+ diff_level + "score: " + score + "time : "+ time + "dateTime: " + dateTime);

    }

    public void dbToString(){
        Log.i(TAG, "dbToString()");
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ALL + " WHERE 1 ;";
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();
        String temp="";
        while(!(c.isAfterLast())){
            temp += "id: " + c.getString(c.getColumnIndex(COLUMN_ID));
            temp += ", gameType: " + c.getString(c.getColumnIndex(COLUMN_GAMETYPE));
            temp += ", dateTime: "+c.getString(c.getColumnIndex(COLUMN_DATETIME));
            temp += ", diff_level: "+c.getString(c.getColumnIndex(COLUMN_DIFF_LEVEL));
            temp += ", score: "+c.getString(c.getColumnIndex(COLUMN_SCORE));
            temp += ", time: "+c.getString(c.getColumnIndex(COLUMN_TIME));
            Log.i(TAG, " output: " + temp);
            c.moveToNext();
        }
        c.close();

        dbToCoords();
        db.close();

    }

    public DataPoint[] dbToCoords() {

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ALL + " ORDER BY " + COLUMN_ID + " ASC;";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        int totalAttempts = c.getCount();                   //Get the number of rows in the table, which is the number of attempts
        DataPoint[] values = new DataPoint[totalAttempts];  //Array of DataPoints, each DataPoint holds an (X,Y) coordinate
        double[] highScorePerLevel = new double[10];        //An array to store the highest score earned for each level
        double totalScore = 0;                              //Use for calculating total score which will be the Y coordinate

        for(int i=0; i<10; i++) {                           //Initialise all the high scores to be 0
            highScorePerLevel[i] = 0;
        }

        while(!(c.isAfterLast())) {

            int attemptNumber = c.getInt(c.getColumnIndex(COLUMN_ID));  //This is the X coordinate
            int level = c.getInt(c.getColumnIndex(COLUMN_DIFF_LEVEL));
            double score = c.getDouble(c.getColumnIndex(COLUMN_SCORE)); //This is the score (to be replaced by stars)


            if(highScorePerLevel[level-1] < score) {                    //Check if cursor score is greater than previous recorded high score for that level
                highScorePerLevel[level-1] = score;

                totalScore = 0;
                for(int i=0; i<10; i++) {
                    totalScore += highScorePerLevel[i];                 //Recalculate total high score
                }
            }

            DataPoint XYcoordinate = new DataPoint(attemptNumber, totalScore);  //Assign attempt number and total score to DataPoint
            values[attemptNumber-1] = XYcoordinate;                             //Add DataPoint to the DataPoint array
            c.moveToNext();

            Log.v("DataPoint", XYcoordinate.getX()+" "+XYcoordinate.getY());

        }

        c.close();
        return values;

    }
}
