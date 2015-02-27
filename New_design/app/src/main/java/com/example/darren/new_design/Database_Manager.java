package com.example.darren.new_design;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database_Manager {


    private static final int DATABASE_VERSION = 1;

    public static final String KEY_USERID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_Email = "email";
    public static final String KEY_PASSWORD = "pass";

    private static final String DATABASE_NAME = "Database";
    private static final String DATABASE_TABLE1 = "Users";
    private static final String DATABASE_TABLE2 = "Ex_description";

    public static final String KEY_DID = "_id";
    public static final String KEY_DESC = "description";

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
            // Creating tables
            String DATA1_CREATE = "create table " + DATABASE_TABLE1 + "(_id integer primary key autoincrement, "
                    + "name text not null, "
                    + "email text not null,"
                    + "pass text not null);";

            // Creating tables
            String DATA2_CREATE = "create table " + DATABASE_TABLE2 + "(_id integer primary key autoincrement, "
                    + "name text not null, "
                    + "description text not null);";


            // Create Tables
            db.execSQL(DATA1_CREATE);
            db.execSQL(DATA2_CREATE);

            //default inserts
            db.execSQL("INSERT INTO " + DATABASE_TABLE1 + " (name, email, pass) Values ('admin', 'email@admin.com', 'admin')");

            Exercise_descriptions(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            // drops table if it- exists
            //db.execSQL("DROP TABLE IF EXISTS" + DATABASE_TABLE1);
            //db.execSQL("DROP TABLE IF EXISTS" + DATABASE_TABLE2);

            // create table
            this.onCreate(db);
        }
    }

    private static void Exercise_descriptions(SQLiteDatabase db){
        //Description 1
        db.execSQL("INSERT INTO " + DATABASE_TABLE2 + " (name, description) Values ('Side to side head rotation exercise, 6-8 feet away', " +
                "'Place tablet on a shelf or ledge at roughly eye level. Stand 6-8 feet away. The screen has an ‘E’ letter in the middle of the " +
                "screen. Move your head from side to side while focusing on the letter. If the letter starts to go out of focus then slow down. " +
                "Continue this exercise until timer has finished.')");

        //Description 2
        db.execSQL("INSERT INTO " + DATABASE_TABLE2 + " (name, description) Values ('Up and down head rotation exercise, 6-8 feet away', " +
                "'Place tablet on a shelf or ledge at roughly eye level. Stand 6-8 feet away. The screen has an ‘E’ letter in the middle of the" +
                " screen. Move your head up and down while focusing on the letter. If the letter starts to go out of focus then slow down. Continue " +
                "this exercise until timer has finished.')");

        //Description 3
        db.execSQL("INSERT INTO " + DATABASE_TABLE2 + " (name, description) Values ('Side to side head rotation exercise, arm’s length in front', " +
                "'While standing, hold the tablet at arm’s length in front of you. The screen has an ‘E’ letter in the middle of the screen. Move your" +
                " head from side to side while focusing on the letter. If the letter starts to go out of focus then slow down. Continue this exercise " +
                "until timer has finished.')");

        //Description 4
        db.execSQL("INSERT INTO " + DATABASE_TABLE2 + " (name, description) Values ('Up and down head rotation exercise, arm’s length in front', " +
                "'While standing, hold the tablet at arm’s length in front of you. The screen has an ‘E’ letter in the middle of the screen. Move your" +
                " head up and down while focusing on the letter. If the letter starts to go out of focus then slow down. Continue this exercise until " +
                "timer has finished.')");

        //Description 5
        db.execSQL("INSERT INTO " + DATABASE_TABLE2 + " (name, description) Values ('Two points of focus, arm’s length in front', " +
                "'For this exercise, hold the tablet at arm’s length in front of you. The screen has an ‘X’ on the left and ‘Z’ on the right. Start by " +
                "looking at the ‘X’ with your eyes and head pointing at it. Then you are going look at the ‘Z’ without moving your head. Then move you head " +
                "also to point at the ‘Z’. Move your eyes to the ‘X’, move your head to the ‘X’. And repeat the process until the timer has finished. ')");

        //Description 6
        db.execSQL("INSERT INTO " + DATABASE_TABLE2 + " (name, description) Values ('Closing eyes, arm’s length in front', " +
                "'For this exercise, hold the tablet at arm’s length in front of you. It is recommend to be seated for this exercise.  The screen has an " +
                "‘X’ letter in the middle of the screen. Start by looking at the ‘X’. Close your eyes and move your head to one side while trying to keep " +
                "your eye on the ‘X’. Open your eyes to see if they stayed on the ‘X’. Repeat the exercise, varying the speed and direction of head movement" +
                " each time until the timer has finished.')");

        //Description 7
        db.execSQL("INSERT INTO " + DATABASE_TABLE2 + " (name, description) Values ('Move the tablet in the opposite direction, side to side', " +
                "'For this exercise, hold the tablet at arm’s length in front of you. Holding the tablet directly in front of you look at the ‘E’. " +
                "You are then going to move you head in one direction to the side while moving the tablet in the opposite direction. Keep your eyes on " +
                "the ‘E’ and make sure it remains in focus. The amount you move your head and tablet should not be very big otherwise the letter will go " +
                "out of focus. Increase the amount your head moves and the speed at which you do so as you progress. ')");

        //Description 8
        db.execSQL("INSERT INTO " + DATABASE_TABLE2 + " (name, description) Values ('Move the tablet in the opposite direction, up and down', " +
                "'For this exercise, hold the tablet at arm’s length in front of you. Holding the tablet directly in front of you look at the ‘E’. " +
                "You are then going to point you head up while moving the tablet down and vice versa. Keep your eyes on the ‘E’ and make sure it remains " +
                "in focus. The amount you move your head and tablet should not be very big otherwise the letter will go out of focus. Increase the amount " +
                "your head moves and the speed at which you do so as you progress. ')");

        //Description 9
        db.execSQL("INSERT INTO " + DATABASE_TABLE2 + " (name, description) Values ('Side to side head rotation exercise, placing tablet against a window ', " +
                "'For this exercise place tablet up against a window on a ledge, if you don’t have a ledge hold the tablet against the window at roughly eye level." +
                " The screen has an ‘E’ letter in the middle of the screen. Move your head from side to side while focusing on the letter. If the letter starts to go " +
                "out of focus then slow down. Continue this exercise until timer has finished.')");

        //Description 10
        db.execSQL("INSERT INTO " + DATABASE_TABLE2 + " (name, description) Values ('Up and down head rotation exercise, placing tablet against a window', " +
                "'For this exercise place tablet up against a window on a ledge, if you don’t have a ledge hold the tablet against the window at roughly eye " +
                "level. The screen has an ‘E’ letter in the middle of the screen. Move your head up and down while focusing on the letter. If the letter starts " +
                "to go out of focus then slow down. Continue this exercise until timer has finished.')");
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
        initialValues.put(KEY_Email, email);
        initialValues.put(KEY_PASSWORD, pass);
        return db.insert(DATABASE_TABLE1, null, initialValues);
    }


    public Cursor getAllUsers(){
        return db.query(DATABASE_TABLE1, new String[] {
                        KEY_USERID,
                        KEY_NAME,
                        KEY_Email,
                        KEY_PASSWORD},
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
                        KEY_DESC},
                null,
                null,
                null,
                null,
                null);
    }

    public Cursor getUser(String rowId) throws SQLException{
        Cursor mCursor =
                db.query(true, DATABASE_TABLE1, new String[] {
                                KEY_USERID,
                                KEY_NAME,
                                KEY_Email,
                                KEY_PASSWORD
                        },
                        KEY_USERID + "='" + rowId + "'",
                        null,
                        null,
                        null,
                        null,
                        null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean getUser(String name, String pass) throws SQLException{
        Cursor mCursor =
                db.query(true, DATABASE_TABLE1, new String[] {
                                KEY_USERID,
                                KEY_NAME,
                                KEY_Email,
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
        }

        if( mCursor.getCount() > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}