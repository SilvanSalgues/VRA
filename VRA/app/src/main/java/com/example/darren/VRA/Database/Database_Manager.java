package com.example.darren.VRA.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Database_Manager {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Database";

    private static final String DATABASE_TABLE1 = "Users";
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "pass";
    public static final String KEY_LASTACTIVE = "last_active";
    public static final String KEY_CURRENTEX = "current_ex";
    public static final String KEY_COUNRTY = "country";
    public static final String KEY_POINTSIZE = "pointsize";
    public static final String KEY_LOGGEDIN = "loggedIn";
    public static final String KEY_PHOTO = "photo";

    private static final String DATABASE_TABLE2 = "Ex_description";
    public static final String KEY_DESC = "description";
    public static final String KEY_URL = "url";

    private static final String DATABASE_TABLE3 = "Ex_List";
    public static final String KEY_WEEK = "week";
    public static final String KEY_DAY = "day";
    public static final String KEY_TIMEOFDAY = "timeofday";
    public static final String KEY_EXERCISENUM = "exerciseNum";
    public static final String KEY_EXTYPE = "type";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_GIF = "gifposition";
    public static final String KEY_SPEED = "speed";

    private static final String DATABASE_TABLE4 = "Ex_Results";
    public static final String KEY_PAUSED = "paused";
    public static final String KEY_COMPLETED = "completed";
    public static final String KEY_DIZZINESS = "dizziness";

    private static final String DATABASE_TABLE5 = "Ex_Times";
    public static final String KEY_USERID = "user_id";
    public static final String KEY_TIMEHOURS = "timeHours";
    public static final String KEY_TIMEMINUTES = "timeMinutes";

    private static final String DATABASE_TABLE6 = "PausedTable";
    public static final String KEY_PAUSEAT = "pausedAt";

    Context context;

    private DatabaseHelper DBHelper;
    public SQLiteDatabase SQLiteDatabase;

	/* Database class handles all the queries */

    public Database_Manager(Context ctx){
        this.context = ctx;
        this.DBHelper = new DatabaseHelper(context);
        this.SQLiteDatabase = DBHelper.getWritableDatabase();
    }

    private static class DatabaseHelper extends SQLiteOpenHelper{
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db){

            // Creating Users table
            String DATA1_CREATE = "create table " + DATABASE_TABLE1 + "(_id INTEGER primary key autoincrement, " +
                    "name TEXT not null, "+
                    "email TEXT not null, "+
                    "pass TEXT not null, " +
                    "last_active TEXT, " +
                    "current_ex INTEGER, " +
                    "country TEXT, " +
                    "pointsize INTEGER, " +
                    "loggedIn INTEGER," +
                    "photo BLOB);";

            // Creating Ex_description table
            String DATA2_CREATE = "create table " + DATABASE_TABLE2 + "(_id INTEGER primary key autoincrement, "+
                    "name TEXT not null, "+
                    "description TEXT not null, "+
                    "url TEXT not null);";

            // Creating Ex_List table
            String DATA3_CREATE = "create table " + DATABASE_TABLE3 + "(_id INTEGER primary key autoincrement, "+
                    "week INTEGER not null, "+
                    "day INTEGER not null, "+
                    "timeofday TEXT not null, " +
                    "exerciseNum INTEGER not null, " +
                    "type TEXT not null, " +
                    "duration INTEGER not null, " +
                    "gifposition INTEGER not null, " +
                    "speed REAL not null);";

            // Creating Ex_Results table
            String DATA4_CREATE = "create table " + DATABASE_TABLE4 + "(_id INTEGER primary key autoincrement, "+
                    "user_id INTEGER not null, " +
                    "week INTEGER not null, " +
                    "day INTEGER not null, " +
                    "timeofday TEXT not null, " +
                    "exerciseNum INTEGER not null, " +
                    "paused INTEGER null, " +
                    "completed INTEGER not null, " +
                    "duration INTEGER not null, " +
                    "dizziness INTEGER null);";

            // Creating Ex_times table
            String DATA5_CREATE = "create table " + DATABASE_TABLE5 + "(_id INTEGER primary key autoincrement, "+
                    "timeofday TEXT not null, " +
                    "timeHours INTEGER not null, " +
                    "timeMinutes INTEGER not null);";

            // PausedTable
            String DATA6_CREATE = "create table " + DATABASE_TABLE6 + "(_id INTEGER primary key autoincrement, "+
                    "user_id INTEGER not null, " +
                    "week INTEGER not null, " +
                    "day INTEGER not null, " +
                    "timeofday TEXT not null, " +
                    "exerciseNum INTEGER not null, " +
                    "pausedAt INTEGER not null);";

            // Create Tables
            db.execSQL(DATA1_CREATE);
            db.execSQL(DATA2_CREATE);
            db.execSQL(DATA3_CREATE);
            db.execSQL(DATA4_CREATE);
            db.execSQL(DATA5_CREATE);
            db.execSQL(DATA6_CREATE);

            Exercise_descriptions(db);
            Exercise_List(db);
            Exercise_times(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            // drops table if it- exists
            db.execSQL("DROP TABLE IF EXISTS" + DATABASE_TABLE1);
            db.execSQL("DROP TABLE IF EXISTS" + DATABASE_TABLE2);
            db.execSQL("DROP TABLE IF EXISTS" + DATABASE_TABLE3);
            db.execSQL("DROP TABLE IF EXISTS" + DATABASE_TABLE4);
            db.execSQL("DROP TABLE IF EXISTS" + DATABASE_TABLE5);
            db.execSQL("DROP TABLE IF EXISTS" + DATABASE_TABLE6);

            // create table
            this.onCreate(db);
        }
    }

    private static void Exercise_descriptions(SQLiteDatabase db){
        //Description 1
        db.execSQL("INSERT INTO " + DATABASE_TABLE2 + " (name, description, url) Values ('Side to side head rotation exercise, 6-8 feet away', " +
                "'Place tablet on a shelf or ledge at roughly eye level. Stand 6-8 feet away. The screen has an ‘E’ letter in the middle of the " +
                "screen. Move your head from side to side while focusing on the letter. If the letter starts to go out of focus then slow down. " +
                "Continue this exercise until timer has finished.', " +
                "'pOcgcPUp1_g')");

        //Description 2
        db.execSQL("INSERT INTO " + DATABASE_TABLE2 + " (name, description, url) Values ('Up and down head rotation exercise, 6-8 feet away', " +
                "'Place tablet on a shelf or ledge at roughly eye level. Stand 6-8 feet away. The screen has an ‘E’ letter in the middle of the" +
                " screen. Move your head up and down while focusing on the letter. If the letter starts to go out of focus then slow down. Continue " +
                "this exercise until timer has finished.', " +
                "'weQvgn0UTTo')");

        //Description 3
        db.execSQL("INSERT INTO " + DATABASE_TABLE2 + " (name, description, url) Values ('Side to side head rotation exercise, arm’s length in front', " +
                "'While standing, hold the tablet at arm’s length in front of you. The screen has an ‘E’ letter in the middle of the screen. Move your" +
                " head from side to side while focusing on the letter. If the letter starts to go out of focus then slow down. Continue this exercise " +
                "until timer has finished.', " +
                "'s8XK3uziOxQ')");

        //Description 4
        db.execSQL("INSERT INTO " + DATABASE_TABLE2 + " (name, description, url) Values ('Up and down head rotation exercise, arm’s length in front', " +
                "'While standing, hold the tablet at arm’s length in front of you. The screen has an ‘E’ letter in the middle of the screen. Move your" +
                " head up and down while focusing on the letter. If the letter starts to go out of focus then slow down. Continue this exercise until " +
                "timer has finished.', " +
                "'s8XK3uziOxQ')"); // Update url when exercise4 is created

        //Description 5
        db.execSQL("INSERT INTO " + DATABASE_TABLE2 + " (name, description, url) Values ('Two points of focus, arm’s length in front', " +
                "'For this exercise, hold the tablet at arm’s length in front of you. The screen has an ‘X’ on the left and ‘Z’ on the right. Start by " +
                "looking at the ‘X’ with your eyes and head pointing at it. Then you are going look at the ‘Z’ without moving your head. Then move you head " +
                "also to point at the ‘Z’. Move your eyes to the ‘X’, move your head to the ‘X’. And repeat the process until the timer has finished. ', " +
                "'3Flza2CJ5OY')");

        //Description 6
        db.execSQL("INSERT INTO " + DATABASE_TABLE2 + " (name, description, url) Values ('Closing eyes, arm’s length in front', " +
                "'For this exercise, hold the tablet at arm’s length in front of you. It is recommend to be seated for this exercise.  The screen has an " +
                "‘X’ letter in the middle of the screen. Start by looking at the ‘X’. Close your eyes and move your head to one side while trying to keep " +
                "your eye on the ‘X’. Open your eyes to see if they stayed on the ‘X’. Repeat the exercise, varying the speed and direction of head movement" +
                " each time until the timer has finished.', " +
                "'mqNX6vkjHaA')");

        //Description 7
        db.execSQL("INSERT INTO " + DATABASE_TABLE2 + " (name, description, url) Values ('Move the tablet in the opposite direction, side to side', " +
                "'For this exercise, hold the tablet at arm’s length in front of you. Holding the tablet directly in front of you look at the ‘E’. " +
                "You are then going to move you head in one direction to the side while moving the tablet in the opposite direction. Keep your eyes on " +
                "the ‘E’ and make sure it remains in focus. The amount you move your head and tablet should not be very big otherwise the letter will go " +
                "out of focus. Increase the amount your head moves and the speed at which you do so as you progress.', " +
                "'g-6_l4frnWc')");

        //Description 8
        db.execSQL("INSERT INTO " + DATABASE_TABLE2 + " (name, description, url) Values ('Move the tablet in the opposite direction, up and down', " +
                "'For this exercise, hold the tablet at arm’s length in front of you. Holding the tablet directly in front of you look at the ‘E’. " +
                "You are then going to point you head up while moving the tablet down and vice versa. Keep your eyes on the ‘E’ and make sure it remains " +
                "in focus. The amount you move your head and tablet should not be very big otherwise the letter will go out of focus. Increase the amount " +
                "your head moves and the speed at which you do so as you progress.', " +
                "'53rzyxZBuhw')");

        //Description 9
        db.execSQL("INSERT INTO " + DATABASE_TABLE2 + " (name, description, url) Values ('Side to side head rotation exercise, placing tablet against a window ', " +
                "'For this exercise place tablet up against a window on a ledge, if you don’t have a ledge hold the tablet against the window at roughly eye level." +
                " The screen has an ‘E’ letter in the middle of the screen. Move your head from side to side while focusing on the letter. If the letter starts to go " +
                "out of focus then slow down. Continue this exercise until timer has finished.', " +
                "'C5cDhnIuCQo')");

        //Description 10
        db.execSQL("INSERT INTO " + DATABASE_TABLE2 + " (name, description, url) Values ('Up and down head rotation exercise, placing tablet against a window', " +
                "'For this exercise place tablet up against a window on a ledge, if you don’t have a ledge hold the tablet against the window at roughly eye " +
                "level. The screen has an ‘E’ letter in the middle of the screen. Move your head up and down while focusing on the letter. If the letter starts " +
                "to go out of focus then slow down. Continue this exercise until timer has finished.', " +
                "'1kfldT6fvxI')");
    }
    private static void Exercise_List(SQLiteDatabase db) {

        for (int week = 1; week < 7; week++) {
            Log.d("Week ", "" + week);
            // Day 1
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 1, 'Morning Exercise#1',    0, 'Exercise_Type1', 60, 0, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 1, 'Morning Exercise#1',    1, 'Exercise_Type1', 60, 1, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 1, 'Morning Exercise#2',    0, 'Exercise_Type1', 60, 0, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 1, 'Morning Exercise#2',    1, 'Exercise_Type1', 60, 1, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 1, 'Afternoon Exercise#1',  0, 'Exercise_Type1', 60, 0, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 1, 'Afternoon Exercise#1',  1, 'Exercise_Type1', 60, 1, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 1, 'Afternoon Exercise#2',  0, 'Exercise_Type1', 60, 0, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 1, 'Afternoon Exercise#2',  1, 'Exercise_Type1', 60, 1, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 1, 'Afternoon Exercise#2',  2, 'Exercise_Type1', 60, 0, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 1, 'Afternoon Exercise#2',  3, 'Exercise_Type1', 60, 1, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 1, 'Afternoon Exercise#2',  4, 'Exercise_Type2', 60, 0, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 1, 'Afternoon Exercise#2',  5, 'Exercise_Type1', 60, 1, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 1, 'Afternoon Exercise#2',  6, 'Exercise_Type1', 60, 0, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 1, 'Afternoon Exercise#2',  7, 'Exercise_Type1', 60, 1, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 1, 'Evening Exercise',      0, 'Exercise_Type1', 60, 0, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 1, 'Evening Exercise',      1, 'Exercise_Type1', 60, 1, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 1, 'Evening Exercise',      8, 'Exercise_Type1', 60, 0, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 1, 'Evening Exercise',      9, 'Exercise_Type1', 60, 1, 0.3)");

            //Day 2
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 2, 'Morning Exercise#1',    0, 'Exercise_Type1', 60, 0, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 2, 'Morning Exercise#1',    1, 'Exercise_Type1', 60, 1, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 2, 'Morning Exercise#2',    0, 'Exercise_Type1', 60, 0, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 2, 'Morning Exercise#2',    1, 'Exercise_Type1', 60, 1, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 2, 'Afternoon Exercise#1',  0, 'Exercise_Type1', 60, 0, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 2, 'Afternoon Exercise#1',  1, 'Exercise_Type1', 60, 1, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 2, 'Afternoon Exercise#2',  0, 'Exercise_Type1', 60, 0, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 2, 'Afternoon Exercise#2',  1, 'Exercise_Type1', 60, 1, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 2, 'Evening Exercise',      0, 'Exercise_Type1', 60, 0, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 2, 'Evening Exercise',      1, 'Exercise_Type1', 60, 1, 0.3)");

            //Day 3
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 3, 'Morning Exercise#1',    0, 'Exercise_Type1', 60, 0, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 3, 'Morning Exercise#1',    1, 'Exercise_Type1', 60, 1, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 3, 'Morning Exercise#2',    0, 'Exercise_Type1', 60, 0, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 3, 'Morning Exercise#2',    1, 'Exercise_Type1', 60, 1, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 3, 'Afternoon Exercise#1',  0, 'Exercise_Type1', 60, 0, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 3, 'Afternoon Exercise#1',  1, 'Exercise_Type1', 60, 1, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 3, 'Afternoon Exercise#2',  0, 'Exercise_Type1', 60, 0, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 3, 'Afternoon Exercise#2',  1, 'Exercise_Type1', 60, 1, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 3, 'Evening Exercise',      0, 'Exercise_Type1', 60, 0, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 3, 'Evening Exercise',      1, 'Exercise_Type1', 60, 1, 0.3)");

            //Day 4
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 4, 'Morning Exercise#1',    0, 'Exercise_Type1', 60, 0, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 4, 'Morning Exercise#1',    1, 'Exercise_Type1', 60, 1, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 4, 'Morning Exercise#2',    0, 'Exercise_Type1', 60, 0, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 4, 'Morning Exercise#2',    1, 'Exercise_Type1', 60, 1, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 4, 'Afternoon Exercise#1',  0, 'Exercise_Type1', 60, 0, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 4, 'Afternoon Exercise#1',  1, 'Exercise_Type1', 60, 1, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 4, 'Afternoon Exercise#2',  0, 'Exercise_Type1', 60, 0, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 4, 'Afternoon Exercise#2',  1, 'Exercise_Type1', 60, 1, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 4, 'Evening Exercise',      0, 'Exercise_Type1', 60, 0, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 4, 'Evening Exercise',      1, 'Exercise_Type1', 60, 1, 0.3)");

            //Day 5
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 5, 'Morning Exercise#1',    0, 'Exercise_Type1', 60, 0, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 5, 'Morning Exercise#1',    1, 'Exercise_Type1', 60, 1, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 5, 'Morning Exercise#2',    0, 'Exercise_Type1', 60, 0, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 5, 'Morning Exercise#2',    1, 'Exercise_Type1', 60, 1, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 5, 'Afternoon Exercise#1',  0, 'Exercise_Type1', 60, 0, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 5, 'Afternoon Exercise#1',  1, 'Exercise_Type1', 60, 1, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 5, 'Afternoon Exercise#2',  0, 'Exercise_Type1', 60, 0, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 5, 'Afternoon Exercise#2',  1, 'Exercise_Type1', 60, 1, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 5, 'Evening Exercise',      0, 'Exercise_Type1', 60, 0, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 5, 'Evening Exercise',      1, 'Exercise_Type1', 60, 1, 0.3)");

            //Day 6
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 6, 'Morning Exercise#1',    0, 'Exercise_Type1', 60, 0, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 6, 'Morning Exercise#1',    1, 'Exercise_Type1', 60, 1, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 6, 'Morning Exercise#2',    0, 'Exercise_Type1', 60, 0, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 6, 'Morning Exercise#2',    1, 'Exercise_Type1', 60, 1, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 6, 'Afternoon Exercise#1',  0, 'Exercise_Type1', 60, 0, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 6, 'Afternoon Exercise#1',  1, 'Exercise_Type1', 60, 1, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 6, 'Afternoon Exercise#2',  0, 'Exercise_Type1', 60, 0, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 6, 'Afternoon Exercise#2',  1, 'Exercise_Type1', 60, 1, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 6, 'Evening Exercise',      0, 'Exercise_Type1', 60, 0, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 6, 'Evening Exercise',      1, 'Exercise_Type1', 60, 1, 0.3)");

            //Day 7
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 7, 'Morning Exercise#1',    0, 'Exercise_Type1', 60, 2, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 7, 'Morning Exercise#1',    1, 'Exercise_Type1', 60, 3, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 7, 'Morning Exercise#2',    0, 'Exercise_Type1', 60, 4, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 7, 'Morning Exercise#2',    1, 'Exercise_Type1', 60, 5, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 7, 'Afternoon Exercise#1',  0, 'Exercise_Type1', 60, 6, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 7, 'Afternoon Exercise#1',  1, 'Exercise_Type1', 60, 7, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 7, 'Afternoon Exercise#2',  0, 'Exercise_Type1', 60, 8, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 7, 'Afternoon Exercise#2',  1, 'Exercise_Type1', 60, 9, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 7, 'Evening Exercise',      0, 'Exercise_Type1', 60, 10, 0.3)");
            db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (" + week + ", 7, 'Evening Exercise',      1, 'Exercise_Type1', 60, 2, 0.3)");
        }
    }
    public void Exercise_Result_Setup(SQLiteDatabase db, int user_id){

        Cursor cur;
        cur = db.query(DATABASE_TABLE3, new String[] {
                        KEY_WEEK,
                        KEY_DAY,
                        KEY_TIMEOFDAY,
                        KEY_EXERCISENUM},
                null,
                null,
                null,
                null,
                null);
        cur.moveToFirst();
        do{
            db.execSQL("INSERT INTO " + DATABASE_TABLE4 + " ( user_id, week, day, timeofday, exerciseNum, paused, completed, duration, dizziness ) Values (" + user_id + ", "+ cur.getInt(0) +", "+ cur.getInt(1) +", '"+ cur.getString(2) +"',  '"+ cur.getString(3) +"', 0, 0, 0, 0)");
        }while (cur.moveToNext());
        cur.close();
    }
    private static void Exercise_times(SQLiteDatabase db){
        db.execSQL("INSERT INTO " + DATABASE_TABLE5 + " ( timeofday, timeHours, timeMinutes ) Values ('Morning Exercise#1', 9, 0)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE5 + " ( timeofday, timeHours, timeMinutes ) Values ('Morning Exercise#2', 11, 30)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE5 + " ( timeofday, timeHours, timeMinutes ) Values ('Afternoon Exercise#1', 2, 0)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE5 + " ( timeofday, timeHours, timeMinutes ) Values ('Afternoon Exercise#2', 4, 0)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE5 + " ( timeofday, timeHours, timeMinutes ) Values ('Evening Exercise', 6, 0)");

    }

    public Database_Manager open() throws SQLException{
        SQLiteDatabase = DBHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        DBHelper.close();
    }

    public long insertUser(String name, String email, String pass){

        ContentValues args = new ContentValues();
        args.put(KEY_LOGGEDIN, 0);
        SQLiteDatabase.update(DATABASE_TABLE1, args, null, null);

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_EMAIL, email);
        initialValues.put(KEY_PASSWORD, pass);
        initialValues.put(KEY_CURRENTEX, 1);
        initialValues.put(KEY_LOGGEDIN, 1);
        initialValues.put(KEY_POINTSIZE, 1);
        return SQLiteDatabase.insert(DATABASE_TABLE1, null, initialValues);
    }
    public boolean getUserLogin(String email, String pass) throws SQLException{
        boolean check = false;
        Cursor mCursor =
                SQLiteDatabase.query(true, DATABASE_TABLE1, new String[] {
                                KEY_ID
                        },
                        KEY_EMAIL + "='" + email + "'" + " and " + KEY_PASSWORD + "='" + pass + "'" ,
                        null,
                        null,
                        null,
                        null,
                        null);

        if (mCursor != null) {
            mCursor.moveToFirst();
            if( mCursor.getCount() > 0)
            {
                check = true;
            }
            mCursor.close();
        }
        else
        {
            check = false;
        }

        return check;
    }
    public int getUserID(String email, String pass) throws SQLException{
        int UserID = -1;
        Cursor Cur =
                SQLiteDatabase.query(true, DATABASE_TABLE1, new String[] {
                                KEY_ID
                        },
                        KEY_EMAIL + "='" + email + "'" + " and " + KEY_PASSWORD + "='" + pass + "'" ,
                        null,
                        null,
                        null,
                        null,
                        null);

        if (Cur != null) {
            Cur.moveToFirst();
            UserID = Cur.getInt(0);
            Cur.close();
        }

        return UserID;
    }
    public int isUserLoggedIn() throws SQLException{
        int userid = -1;
        Cursor Cur =
                SQLiteDatabase.query(true, DATABASE_TABLE1, new String[] {
                                KEY_ID,
                        },
                        KEY_LOGGEDIN + "='" + 1 + "'",
                        null,
                        null,
                        null,
                        null,
                        null);

        if (Cur != null) {
            Cur.moveToFirst();
            if( Cur.getCount() > 0)
            {
                userid = Cur.getInt(0);
            }
            Cur.close();
        }
        return userid;
    }
    public int getpointsize(int user_id) throws SQLException{
        int count  = 0;
        Cursor cur =
                SQLiteDatabase.query(true, DATABASE_TABLE1, new String[] {
                                KEY_POINTSIZE
                        },
                        KEY_ID + "='" + user_id + "'",
                        null,
                        null,
                        null,
                        null,
                        null);


        if (cur != null) {
            cur.moveToFirst();

            count = cur.getInt(0);
            cur.close();
        }

        return count;
    }

    public Cursor getUser(int user_id) throws SQLException{
        return SQLiteDatabase.query(true, DATABASE_TABLE1, new String[] {
                        KEY_NAME,
                        KEY_EMAIL,
                        KEY_PASSWORD,
                        KEY_LASTACTIVE,
                        KEY_CURRENTEX,
                        KEY_COUNRTY,
                        KEY_POINTSIZE,
                        KEY_LOGGEDIN
                },
                KEY_ID + "='" + user_id + "'",
                null,
                null,
                null,
                null,
                null);
    }
    public byte [] getUserImage(int user_id){
        byte [] image = null;
        Cursor cur =  SQLiteDatabase.query(true, DATABASE_TABLE1, new String[] {
                        KEY_PHOTO
                },
                KEY_ID + "='" + user_id + "'",
                null,
                null,
                null,
                null,
                null);

        if (cur != null) {
            cur.moveToFirst();
            image = cur.getBlob(0);
            cur.close();
        }

        return image;
    }
    public Cursor getExerciseDescriptions(){
        return SQLiteDatabase.query(DATABASE_TABLE2, new String[] {
                        KEY_NAME,
                        KEY_DESC,
                        KEY_URL},
                null,
                null,
                null,
                null,
                null);
    }
    public String getExerciseDescription(int pos ){
        String Desc = "";
        Cursor cur = SQLiteDatabase.query(DATABASE_TABLE2,new String[] {
                        KEY_DESC
                },
                null,
                null,
                null,
                null,
                null);

        if (cur != null) {
            cur.moveToPosition(pos);
            Desc = cur.getString(0);
            cur.close();
        }

        return Desc;
    }
    public Cursor getExerciseListforDay(int week, int day){
        return SQLiteDatabase.query(DATABASE_TABLE3, new String[] {
                        KEY_WEEK,
                        KEY_DAY,
                        KEY_TIMEOFDAY,
                        KEY_EXERCISENUM,
                        KEY_EXTYPE,
                        KEY_DURATION,
                        KEY_GIF,
                        KEY_SPEED},
                KEY_WEEK + "='" + week + "'" + " and " +  KEY_DAY + "='" + day + "'",
                null,
                null,
                null,
                null,
                null);
    }
    public Cursor getExericseResultsforDay(int user_id, int week, int day){
        return SQLiteDatabase.query(DATABASE_TABLE4, new String[] {
                        KEY_WEEK,
                        KEY_DAY,
                        KEY_TIMEOFDAY,
                        KEY_EXERCISENUM,
                        KEY_PAUSED,
                        KEY_COMPLETED,
                        KEY_DIZZINESS},
                KEY_USERID + "='" + user_id + "'" + " and " +  KEY_WEEK + "='" + week + "'" + " and " +  KEY_DAY + "='" + day + "'",
                null,
                null,
                null,
                null,
                null);
    }
    public Cursor getExerciseTimes(){
        return SQLiteDatabase.query(DATABASE_TABLE5, new String[] {
                        KEY_TIMEOFDAY,
                        KEY_TIMEHOURS,
                        KEY_TIMEMINUTES},
                null,
                null,
                null,
                null,
                null);
    }
    public Cursor getCompleteExerciseList(){
        return SQLiteDatabase.query(DATABASE_TABLE3, new String[] {
                        KEY_WEEK,
                        KEY_DAY,
                        KEY_TIMEOFDAY,
                        KEY_EXERCISENUM,
                        KEY_EXTYPE,
                        KEY_DURATION,
                        KEY_GIF,
                        KEY_SPEED},
                null,
                null,
                null,
                null,
                null);
    }

    public int getExerciseNum(int day, int week, String timeofday, int pos ){
        int exerciseNum = 0;
        Cursor cur = SQLiteDatabase.query(DATABASE_TABLE3,new String[] {
                        KEY_EXERCISENUM
                },
                KEY_DAY + "='" + day + "'" + " and " + KEY_WEEK + "='" + week + "'" + " and " + KEY_TIMEOFDAY + "='" + timeofday + "'",
                null,
                null,
                null,
                null,
                null);

        if (cur != null) {
            cur.moveToPosition(pos);
            exerciseNum = cur.getInt(0);
            cur.close();
        }

        return exerciseNum;
    }
    public int getExerciseId(int day, int week, String timeofday, int exerciseNum ){
        int exerciseId = 0;
        Cursor cur = SQLiteDatabase.query(DATABASE_TABLE3,new String[] {
                        KEY_ID
                },
                KEY_DAY + "='" + day + "'" + " and " + KEY_WEEK + "='" + week + "'" + " and " + KEY_TIMEOFDAY + "='" + timeofday + "'" + " and " + KEY_EXERCISENUM + "='" + exerciseNum + "'",
                null,
                null,
                null,
                null,
                null);

        if (cur.getCount() !=0) {
            cur.moveToFirst();
            exerciseId = cur.getInt(0);
            cur.close();
        }

        return exerciseId;
    }
    public int getExerciseId(int user_id) throws SQLException{
        int exerciseId  = 0;
        Cursor cur =
                SQLiteDatabase.query(true, DATABASE_TABLE1, new String[] {
                                KEY_CURRENTEX
                        },
                        KEY_ID + "='" + user_id + "'",
                        null,
                        null,
                        null,
                        null,
                        null);


        if (cur.getCount() !=0) {
            cur.moveToFirst();
            exerciseId = cur.getInt(0);
            cur.close();
        }
        return (exerciseId);
    }
    public int getPausedCount(int user_id, int day, int week, String timeofday, int exerciseNum ){
        int PausedCount = 0;
        Cursor cur = SQLiteDatabase.query(DATABASE_TABLE4,new String[] {
                        KEY_PAUSED
                },
                KEY_USERID + "='" + user_id + "'" + " and " + KEY_DAY + "='" + day + "'" + " and " + KEY_WEEK + "='" + week + "'" + " and " + KEY_TIMEOFDAY + "='" + timeofday + "'",
                null,
                null,
                null,
                null,
                null);

        if (cur.getCount() !=0) {
            cur.moveToPosition(exerciseNum);
            PausedCount = cur.getInt(0);
            cur.close();
        }

        return PausedCount;
    }
    public List<Integer> getPausedTimes(int user_id, int day, int week, String timeofday, int exerciseNum){
        List<Integer> PausedAt = new ArrayList<>();
        Cursor cur = SQLiteDatabase.query(DATABASE_TABLE6,new String[] {
                        KEY_PAUSEAT
                },
                KEY_USERID + "='" + user_id + "'" + " and " + KEY_DAY + "='" + day + "'" + " and " + KEY_WEEK + "='" + week + "'" + " and " + KEY_TIMEOFDAY + "='" + timeofday + "'" + " and " + KEY_EXERCISENUM + "='" + exerciseNum + "'",
                null,
                null,
                null,
                null,
                null);

        if (cur.getCount() !=0) {
            cur.moveToFirst();

            while(!cur.isAfterLast()) {
                PausedAt.add(cur.getInt(0));
                cur.moveToNext();
            }
            cur.close();
        }
        return PausedAt;
    }
    public int getDuration(int user_id, int day, int week, String timeofday, int exerciseNum ){
        int duration = 0;
        Cursor cur = SQLiteDatabase.query(DATABASE_TABLE4,new String[] {
                        KEY_DURATION
                },
                KEY_USERID + "='" + user_id + "'" + " and " + KEY_DAY + "='" + day + "'" + " and " + KEY_WEEK + "='" + week + "'" + " and " + KEY_TIMEOFDAY + "='" + timeofday + "'",
                null,
                null,
                null,
                null,
                null);

        if (cur != null) {
            cur.moveToPosition(exerciseNum);
            duration = cur.getInt(0);
            cur.close();
        }
        return duration;
    }
    public int getDizziness(int user_id, int day, int week, String timeofday, int exerciseNum ){
        int dizziness = 0;
        Cursor cur = SQLiteDatabase.query(DATABASE_TABLE4,new String[] {
                        KEY_DIZZINESS
                },
                KEY_USERID + "='" + user_id + "'" + " and " + KEY_DAY + "='" + day + "'" + " and " + KEY_WEEK + "='" + week + "'" + " and " + KEY_TIMEOFDAY + "='" + timeofday + "'",
                null,
                null,
                null,
                null,
                null);

        if (cur.getCount() !=0) {
            cur.moveToPosition(exerciseNum);
            dizziness = cur.getInt(0);
            cur.close();
        }
        return dizziness;
    }

    public void IncrementExerciseCount(int user_id, int ExerciseCount){

        ContentValues args = new ContentValues();
        args.put(KEY_CURRENTEX, ++ExerciseCount);
        Log.d("Update Exercise Count", "" + ExerciseCount);
        SQLiteDatabase.update(DATABASE_TABLE1, args, KEY_ID + "='" + user_id + "'", null) ;
    }
    public void updateExerciseCount(int user_id, int ExerciseCount){
        Log.d("Update Exercise Count", "" + (ExerciseCount));
        ContentValues args = new ContentValues();
        args.put(KEY_CURRENTEX, ExerciseCount);
        SQLiteDatabase.update(DATABASE_TABLE1, args, KEY_ID + "='" + user_id + "'", null) ;
    }

    public long insertPauseTime(int user_id, int week, int day, String timeofday, int exerciseNum, int pausetime){
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_USERID, user_id);
        initialValues.put(KEY_WEEK, week);
        initialValues.put(KEY_DAY, day);
        initialValues.put(KEY_TIMEOFDAY, timeofday);
        initialValues.put(KEY_EXERCISENUM, exerciseNum);
        initialValues.put(KEY_PAUSEAT, pausetime);
        return SQLiteDatabase.insert(DATABASE_TABLE6, null, initialValues);
    }
    public void updateLastActive(int user_id, String LastActive){
        Log.d("Update Count", "" + LastActive);
        ContentValues args = new ContentValues();
        args.put(KEY_LASTACTIVE, LastActive);
        SQLiteDatabase.update(DATABASE_TABLE1, args, KEY_ID + "='" + user_id + "'", null) ;
    }
    public void updateUSER(int user_id, String name, String password, String country, int pointsize){
        ContentValues args = new ContentValues();
        Log.d("Database", "User Updated" );
        args.put(KEY_NAME, name);
        args.put(KEY_PASSWORD, password);
        args.put(KEY_COUNRTY, country);
        args.put(KEY_POINTSIZE, pointsize);
        SQLiteDatabase.update(DATABASE_TABLE1, args, KEY_ID + "='" + user_id + "'", null);

    }

    public void CompleteEx(int day, int week, String timeofday, int exerciseNum, int duration){
        ContentValues args = new ContentValues();
        args.put(KEY_COMPLETED, 1);
        args.put(KEY_DURATION, duration);
        SQLiteDatabase.update(DATABASE_TABLE4, args, KEY_DAY + "='" + day + "'" + " and " + KEY_WEEK + "='" + week + "'" + " and " + KEY_TIMEOFDAY + "='" + timeofday + "'" + " and " + KEY_EXERCISENUM + "='" + exerciseNum + "'", null);

    }
    public void updatePausedCount(int day, int week, String timeofday, int exerciseNum){
        Cursor cur =  SQLiteDatabase.query(DATABASE_TABLE4, new String[] {
                        KEY_PAUSED,},
                KEY_DAY + "='" + day + "'" + " and " + KEY_WEEK + "='" + week + "'" + " and " + KEY_TIMEOFDAY + "='" + timeofday + "'",
                null,
                null,
                null,
                null,
                null);

        if (cur != null) {
            cur.moveToPosition(exerciseNum);
            //Increment Pause Count
            int PauseCount = cur.getInt(0) + 1;

            ContentValues args = new ContentValues();
            args.put(KEY_PAUSED, PauseCount);
            SQLiteDatabase.update(DATABASE_TABLE4, args, KEY_DAY + "='" + day + "'" + " and " + KEY_WEEK + "='" + week + "'" + " and " + KEY_TIMEOFDAY + "='" + timeofday + "'" + " and " + KEY_EXERCISENUM + "='" + exerciseNum + "'", null);

            cur.close();
        }
    }
    public void updateDizziness(int exercise_id, int dizziness){
        ContentValues args = new ContentValues();
        args.put(KEY_DIZZINESS, dizziness);
        SQLiteDatabase.update(DATABASE_TABLE4, args, KEY_ID + "='" + exercise_id + "'", null);

    }

    public void LogoutUSER (int userid){
        ContentValues args = new ContentValues();
        args.put(KEY_LOGGEDIN, 0);
        SQLiteDatabase.update(DATABASE_TABLE1, args, KEY_ID + "='" + userid + "'", null);
    }
    public void LoginUSER (int userid){
        ContentValues args = new ContentValues();
        args.put(KEY_LOGGEDIN, 1);
        SQLiteDatabase.update(DATABASE_TABLE1, args, KEY_ID + "='" + userid + "'", null);
    }

     /*public void updateUSERPhoto( int user_id, byte[] photo) throws SQLiteException {
        ContentValues args= new  ContentValues();
        args.put(KEY_PHOTO, photo);
        SQLiteDatabase.update(DATABASE_TABLE1, args, KEY_ID + "='" + user_id + "'", null);
    }*/

}