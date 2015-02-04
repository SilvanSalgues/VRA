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

    private Context context;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

	/* Database class handles all the queries */

    public Database_Manager(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            // Creating tables
            String DATA1_CREATE = "create table " + DATABASE_TABLE1 + "(_id integer primary key autoincrement, "
                    + "name text not null, "
                    + "email text not null,"
                    + "pass text not null);";


            db.execSQL(DATA1_CREATE);

            //default inserts
            db.execSQL("INSERT INTO " + DATABASE_TABLE1 + " (name, email, pass) Values ('admin', 'email@admin.com', 'admin')");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            // drops table if it- exists
            db.execSQL("DROP TABLE IF EXISTS" + DATABASE_TABLE1);

            // create fresh books table
            this.onCreate(db);
        }
    }

    public Database_Manager open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        DBHelper.close();
    }

    public long insertUser(String name, String email, String pass)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_Email, email);
        initialValues.put(KEY_PASSWORD, pass);
        return db.insert(DATABASE_TABLE1, null, initialValues);
    }


    public Cursor getAllUsers()
    {
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

    public Cursor getUser(String rowId) throws SQLException
    {
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

    public boolean getUser(String name, String pass) throws SQLException
    {
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

        if(mCursor.getCount() > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}