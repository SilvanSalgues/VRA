package com.example.darren.new_design;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database_Manager {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Database";

    private static final String DATABASE_TABLE1 = "Users";
    public static final String KEY_USERID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "pass";
    public static final String KEY_LASTACTIVE = "last_active";
    public static final String KEY_EXCOUNT = "exercise_count";
    public static final String KEY_COUNRTY = "country";
    public static final String KEY_POINTSIZE = "pointsize";
    public static final String KEY_LOGGEDIN = "loggedIn";

    private static final String DATABASE_TABLE2 = "Ex_description";
    public static final String KEY_DID = "_id";
    public static final String KEY_DESC = "description";
    public static final String KEY_URL = "url";


    private static final String DATABASE_TABLE3 = "Ex_List";
    public static final String KEY_LID = "_id";
    public static final String KEY_WEEK = "week";
    public static final String KEY_DAY = "day";
    public static final String KEY_TIMEOFDAY = "timeofday";
    public static final String KEY_EXERCISENUM = "exerciseNum";
    public static final String KEY_EXTYPE = "type";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_GIF = "gifposition";
    public static final String KEY_SPEED = "speed";

    Context context;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

	/* Database class handles all the queries */

    public Database_Manager(Context ctx){
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
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
                    "exercise_count INTEGER, " +
                    "country TEXT, " +
                    "pointsize INTEGER, " +
                    "loggedIn INTEGER);";

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

            // Create Tables
            db.execSQL(DATA1_CREATE);
            db.execSQL(DATA2_CREATE);
            db.execSQL(DATA3_CREATE);

            //default inserts
            db.execSQL("INSERT INTO " + DATABASE_TABLE1 + " (name, email, pass, exercise_count, pointsize, loggedIn) Values ('admin', 'email@admin.com', 'admin', 0, 0, 1)");

            Exercise_descriptions(db);
            Exercise_List(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            // drops table if it- exists
            db.execSQL("DROP TABLE IF EXISTS" + DATABASE_TABLE1);
            db.execSQL("DROP TABLE IF EXISTS" + DATABASE_TABLE2);
            db.execSQL("DROP TABLE IF EXISTS" + DATABASE_TABLE3);

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
    private static void Exercise_List(SQLiteDatabase db){
        // Day 1
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 1, 'Morning Exercise#1',    0, 'Exercise_Type1', 60, 0, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 1, 'Morning Exercise#1',    1, 'Exercise_Type1', 60, 1, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 1, 'Morning Exercise#2',    0, 'Exercise_Type1', 60, 0, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 1, 'Morning Exercise#2',    1, 'Exercise_Type1', 60, 1, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 1, 'Afternoon Exercise#1',  0, 'Exercise_Type1', 60, 0, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 1, 'Afternoon Exercise#1',  1, 'Exercise_Type1', 60, 1, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 1, 'Afternoon Exercise#2',  0, 'Exercise_Type1', 60, 0, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 1, 'Afternoon Exercise#2',  1, 'Exercise_Type1', 60, 1, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 1, 'Afternoon Exercise#2',  2, 'Exercise_Type1', 60, 2, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 1, 'Afternoon Exercise#2',  3, 'Exercise_Type1', 60, 3, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 1, 'Afternoon Exercise#2',  4, 'Exercise_Type2', 60, 4, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 1, 'Afternoon Exercise#2',  5, 'Exercise_Type1', 60, 5, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 1, 'Afternoon Exercise#2',  6, 'Exercise_Type1', 60, 6, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 1, 'Afternoon Exercise#2',  7, 'Exercise_Type1', 60, 1, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 1, 'Evening Exercise',      0, 'Exercise_Type1', 60, 0, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 1, 'Evening Exercise',      1, 'Exercise_Type1', 60, 1, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 1, 'Evening Exercise',      8, 'Exercise_Type1', 60, 2, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 1, 'Evening Exercise',      9, 'Exercise_Type1', 60, 3, 0.3)");

        //Day 2
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 2, 'Morning Exercise#1',    0, 'Exercise_Type1', 60, 0, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 2, 'Morning Exercise#1',    1, 'Exercise_Type1', 60, 1, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 2, 'Morning Exercise#2',    0, 'Exercise_Type1', 60, 0, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 2, 'Morning Exercise#2',    1, 'Exercise_Type1', 60, 1, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 2, 'Afternoon Exercise#1',  0, 'Exercise_Type1', 60, 0, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 2, 'Afternoon Exercise#1',  1, 'Exercise_Type1', 60, 1, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 2, 'Afternoon Exercise#2',  0, 'Exercise_Type1', 60, 0, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 2, 'Afternoon Exercise#2',  1, 'Exercise_Type1', 60, 1, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 2, 'Evening Exercise',      0, 'Exercise_Type1', 60, 0, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 2, 'Evening Exercise',      1, 'Exercise_Type1', 60, 1, 0.3)");

        //Day 3
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 3, 'Morning Exercise#1',    0, 'Exercise_Type1', 60, 0, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 3, 'Morning Exercise#1',    1, 'Exercise_Type1', 60, 1, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 3, 'Morning Exercise#2',    0, 'Exercise_Type1', 60, 0, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 3, 'Morning Exercise#2',    1, 'Exercise_Type1', 60, 1, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 3, 'Afternoon Exercise#1',  0, 'Exercise_Type1', 60, 0, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 3, 'Afternoon Exercise#1',  1, 'Exercise_Type1', 60, 1, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 3, 'Afternoon Exercise#2',  0, 'Exercise_Type1', 60, 0, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 3, 'Afternoon Exercise#2',  1, 'Exercise_Type1', 60, 1, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 3, 'Evening Exercise',      0, 'Exercise_Type1', 60, 0, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 3, 'Evening Exercise',      1, 'Exercise_Type1', 60, 1, 0.3)");

        //Day 4
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 4, 'Morning Exercise#1',    0, 'Exercise_Type1', 60, 0, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 4, 'Morning Exercise#1',    1, 'Exercise_Type1', 60, 1, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 4, 'Morning Exercise#2',    0, 'Exercise_Type1', 60, 0, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 4, 'Morning Exercise#2',    1, 'Exercise_Type1', 60, 1, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 4, 'Afternoon Exercise#1',  0, 'Exercise_Type1', 60, 0, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 4, 'Afternoon Exercise#1',  1, 'Exercise_Type1', 60, 1, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 4, 'Afternoon Exercise#2',  0, 'Exercise_Type1', 60, 0, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 4, 'Afternoon Exercise#2',  1, 'Exercise_Type1', 60, 1, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 4, 'Evening Exercise',      0, 'Exercise_Type1', 60, 0, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 4, 'Evening Exercise',      1, 'Exercise_Type1', 60, 1, 0.3)");

        //Day 5
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 5, 'Morning Exercise#1',    0, 'Exercise_Type1', 60, 0, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 5, 'Morning Exercise#1',    1, 'Exercise_Type1', 60, 1, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 5, 'Morning Exercise#2',    0, 'Exercise_Type1', 60, 0, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 5, 'Morning Exercise#2',    1, 'Exercise_Type1', 60, 1, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 5, 'Afternoon Exercise#1',  0, 'Exercise_Type1', 60, 0, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 5, 'Afternoon Exercise#1',  1, 'Exercise_Type1', 60, 1, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 5, 'Afternoon Exercise#2',  0, 'Exercise_Type1', 60, 0, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 5, 'Afternoon Exercise#2',  1, 'Exercise_Type1', 60, 1, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 5, 'Evening Exercise',      0, 'Exercise_Type1', 60, 0, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 5, 'Evening Exercise',      1, 'Exercise_Type1', 60, 1, 0.3)");

        //Day 6
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 6, 'Morning Exercise#1',    0, 'Exercise_Type1', 60, 0, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 6, 'Morning Exercise#1',    1, 'Exercise_Type1', 60, 1, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 6, 'Morning Exercise#2',    0, 'Exercise_Type1', 60, 0, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 6, 'Morning Exercise#2',    1, 'Exercise_Type1', 60, 1, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 6, 'Afternoon Exercise#1',  0, 'Exercise_Type1', 60, 0, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 6, 'Afternoon Exercise#1',  1, 'Exercise_Type1', 60, 1, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 6, 'Afternoon Exercise#2',  0, 'Exercise_Type1', 60, 0, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 6, 'Afternoon Exercise#2',  1, 'Exercise_Type1', 60, 1, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 6, 'Evening Exercise',      0, 'Exercise_Type1', 60, 0, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 6, 'Evening Exercise',      1, 'Exercise_Type1', 60, 1, 0.3)");

        //Day 7
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 7, 'Morning Exercise#1',    0, 'Exercise_Type1', 60, 0, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 7, 'Morning Exercise#1',    1, 'Exercise_Type1', 60, 1, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 7, 'Morning Exercise#2',    0, 'Exercise_Type1', 60, 0, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 7, 'Morning Exercise#2',    1, 'Exercise_Type1', 60, 1, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 7, 'Afternoon Exercise#1',  0, 'Exercise_Type1', 60, 0, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 7, 'Afternoon Exercise#1',  1, 'Exercise_Type1', 60, 1, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 7, 'Afternoon Exercise#2',  0, 'Exercise_Type1', 60, 0, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 7, 'Afternoon Exercise#2',  1, 'Exercise_Type1', 60, 1, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 7, 'Evening Exercise',      0, 'Exercise_Type1', 60, 0, 0.3)");
        db.execSQL("INSERT INTO " + DATABASE_TABLE3 + " (week, day, timeofday, exerciseNum, type, duration, gifposition, speed ) Values (1, 7, 'Evening Exercise',      1, 'Exercise_Type1', 60, 1, 0.3)");
    }

    public Database_Manager open() throws SQLException{
        db = DBHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        DBHelper.close();
    }


    public long insertUser(String name, String email, String pass){
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_EMAIL, email);
        initialValues.put(KEY_PASSWORD, pass);
        initialValues.put(KEY_EXCOUNT, 0);
        return db.insert(DATABASE_TABLE1, null, initialValues);
    }

    public boolean getUserLogin(String name, String pass) throws SQLException{
        boolean check = false;
        Cursor mCursor =
                db.query(true, DATABASE_TABLE1, new String[] {
                                KEY_USERID,
                                KEY_NAME,
                                KEY_EMAIL,
                                KEY_PASSWORD
                        },
                        KEY_NAME + "='" + name + "'" + " and " + KEY_PASSWORD + "='" + pass + "'" ,
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
        }
        else
        {
            check = false;
        }
        return check;
    }
    public int getExerciseCount(String email) throws SQLException{
        int count  = 0;
        Cursor cur =
                db.query(true, DATABASE_TABLE1, new String[] {
                        KEY_EXCOUNT
                },
                KEY_EMAIL + "='" + email + "'",
                null,
                null,
                null,
                null,
                null);


        if (cur != null) {
            cur.moveToFirst();

            count = cur.getInt(0);
            //Log.d("Count ", "" + cur.getInt(0));
            cur.close();
        }

        return count;
    }

    public Cursor getUser(String email) throws SQLException{
        return db.query(true, DATABASE_TABLE1, new String[] {
                                KEY_NAME,
                                KEY_EMAIL,
                                KEY_PASSWORD,
                                KEY_LASTACTIVE,
                                KEY_EXCOUNT,
                                KEY_COUNRTY,
                                KEY_POINTSIZE,
                                KEY_LOGGEDIN
                        },
                        KEY_EMAIL + "='" + email + "'",
                        null,
                        null,
                        null,
                        null,
                        null);
    }
    public Cursor getExerciseDescriptions(){
        return db.query(DATABASE_TABLE2, new String[] {
                        KEY_DID,
                        KEY_NAME,
                        KEY_DESC,
                        KEY_URL},
                null,
                null,
                null,
                null,
                null);
    }
    public Cursor getExerciseListforDay(int day){
        return db.query(DATABASE_TABLE3, new String[] {
                        KEY_LID,
                        KEY_WEEK,
                        KEY_DAY,
                        KEY_TIMEOFDAY,
                        KEY_EXERCISENUM,
                        KEY_EXTYPE,
                        KEY_DURATION,
                        KEY_GIF,
                        KEY_SPEED},
                        KEY_DAY + "='" + day + "'",
                null,
                null,
                null,
                null,
                null);
    }

    public Cursor getCompleteExerciseList(){
        return db.query(DATABASE_TABLE3, new String[] {
                        KEY_LID,
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

    public void updateExerciseCount(String email, int ExerciseCount){
        Log.d("Update Count", "" + ExerciseCount);
        ContentValues args = new ContentValues();
        args.put(KEY_EXCOUNT, ExerciseCount);
        db.update(DATABASE_TABLE1, args, KEY_EMAIL + "='" + email + "'", null) ;
    }

    public void updateLastActive(String email, String LastActive){
        Log.d("Update Count", "" + LastActive);
        ContentValues args = new ContentValues();
        args.put(KEY_LASTACTIVE, LastActive);
        db.update(DATABASE_TABLE1, args, KEY_EMAIL + "='" + email + "'", null) ;
    }
}