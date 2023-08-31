package com.example.madpractical3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MainDBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "user_db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_FOLLOWED = "followed";

    public MainDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_USERS +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT," +
                COLUMN_FOLLOWED + " INTEGER)";
        db.execSQL(createTableQuery);
        insertInitialUsers(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    private void insertInitialUsers(SQLiteDatabase db) {
        // Insert 20 initial user records with randomized data
        for (int i = 0; i < 20; i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, "Name" + generateRandomNumber());
            values.put(COLUMN_DESCRIPTION, "Description " + generateRandomNumber());
            values.put(COLUMN_FOLLOWED, Math.random() < 0.5 ? 1 : 0);
            db.insert(TABLE_USERS, null, values);
        }
    }

    public List<User> getUsers() {
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                user.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
                user.setFollowed(cursor.getInt(cursor.getColumnIndex(COLUMN_FOLLOWED)) == 1);
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;
    }

    public void updateUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FOLLOWED, user.isFollowed() ? 1 : 0);
        db.update(TABLE_USERS, values, COLUMN_ID + "=?", new String[]{String.valueOf(user.getId())});
        db.close();
    }

    private int generateRandomNumber() {
        return (int) (Math.random() * 999999999);
    }
}