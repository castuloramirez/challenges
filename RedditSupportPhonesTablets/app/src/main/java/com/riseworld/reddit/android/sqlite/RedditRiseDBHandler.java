package com.riseworld.reddit.android.sqlite;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.riseworld.reddit.android.ui.pojo.DataObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the SQLite database
 **/
public class RedditRiseDBHandler extends SQLiteOpenHelper {

    public static final String TABLE_PROGRAMMING = "programming";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_AUTOR = "autor";
    public static final String COLUMN_PERMALINK = "permalink";
    public static final String COLUMN_URL = "url";//Host/Ip


    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "reddit.db";

    /**
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public RedditRiseDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_PROGRAMMING + "'");

        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_PROGRAMMING + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT," +
                COLUMN_AUTOR + " TEXT," +
                COLUMN_PERMALINK + " TEXT," +
                COLUMN_URL + " TEXT" + ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    /**
     * @param connection
     */
    public void addConnection(Connection connection) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, connection.getTitle());
        values.put(COLUMN_AUTOR, connection.getAutor());
        values.put(COLUMN_PERMALINK, connection.getPermalink());
        values.put(COLUMN_URL, connection.getUrl());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PROGRAMMING, null, values);
        db.close();
    }

    /**
     * @param name
     */
    public void deleteConnection(String name) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PROGRAMMING + " WHERE " + COLUMN_TITLE + "=\"" + name + "\";");

    }


    public void deleteAllConnections() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PROGRAMMING);

    }

    /**
     * @param name
     * @return
     */
    public boolean connectionExist(String name) {
        SQLiteDatabase db = getWritableDatabase();
        String dbString = "";
        String query = "SELECT FROM " + TABLE_PROGRAMMING + " WHERE " + COLUMN_TITLE + "=\"" + name + "\";";

        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();


        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex(COLUMN_TITLE)) != null) {
                return true;
            }
            c.moveToNext();
        }
        db.close();
        return false;
    }

    /**
     * @return
     */
    public String databaseToString() {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PROGRAMMING + " WHERE 1";

        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex(COLUMN_TITLE)) != null) {
                dbString += c.getString(c.getColumnIndex(COLUMN_TITLE));
                dbString += "\n";
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }

    /**
     * @return
     */
    public List<String> getConnectionsFromDatabase() {
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PROGRAMMING + " WHERE 1";

        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex(COLUMN_TITLE)) != null) {
                list.add(c.getString(c.getColumnIndex(COLUMN_TITLE)));
            }
            c.moveToNext();
        }
        db.close();
        return list;
    }


    /**
     * @param title
     * @return
     */
    public Connection getConnection(String title) {
        SQLiteDatabase db = getWritableDatabase();
        String dbString = "";
        Connection connection = new Connection();
        String query = "SELECT * FROM " + TABLE_PROGRAMMING + " WHERE " + COLUMN_TITLE + "=\"" + title + "\";";

        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex(COLUMN_TITLE)) != null) {
                connection.setId(c.getInt(c.getColumnIndex(COLUMN_ID)));
                connection.setTitle(c.getString(c.getColumnIndex(COLUMN_TITLE)));
                connection.setAutor(c.getString(c.getColumnIndex(COLUMN_AUTOR)));
                connection.setPermalink(c.getString(c.getColumnIndex(COLUMN_PERMALINK)));
                connection.setUrl(c.getString(c.getColumnIndex(COLUMN_URL)));
            }
            c.moveToNext();
        }
        db.close();
        return connection;
    }





    public ArrayList<DataObject> getAllConnections() {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PROGRAMMING;
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        ArrayList<DataObject> results = new ArrayList<>();
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex(COLUMN_TITLE)) != null) {
                DataObject dataObject = new DataObject(c.getString(c.getColumnIndex(COLUMN_TITLE)), c.getString(c.getColumnIndex(COLUMN_AUTOR)));
                dataObject.setPermalink(c.getString(c.getColumnIndex(COLUMN_PERMALINK)));
                dataObject.setUrl(c.getString(c.getColumnIndex(COLUMN_URL)));
                results.add(dataObject);
            }
            c.moveToNext();
        }
        db.close();
        return results;
    }




}