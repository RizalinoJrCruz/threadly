package com.example.threadlyv2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "threadlyv2.db";
    public static final String TABLE_NAME_USER = "allusers";
    public static final String TABLE_NAME_POST = "posts";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 4); // Incremented version
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true); // Enable foreign key constraints
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            // Create allusers table
            db.execSQL("CREATE TABLE " + TABLE_NAME_USER + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "fullname TEXT, " +
                    "username TEXT UNIQUE, " +
                    "password TEXT," +
                    "profile_picture TEXT)"); // New column for profile picture


            // Create posts table
            db.execSQL("CREATE TABLE " + TABLE_NAME_POST + " (" +
                    "post_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT, " +
                    "post_title TEXT, " +
                    "post_body TEXT, " +
                    "created_at DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY(username) REFERENCES allusers(username))");
        } catch (Exception e) {
            Log.e("DB_ERROR", "Error creating tables: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_POST);
        onCreate(db);
    }

    public Boolean insertData(String fullname, String username, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            return false;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fullname", fullname);
        contentValues.put("username", username);
        contentValues.put("password", password);

        long result = db.insert(TABLE_NAME_USER, null, contentValues);
        return result != -1;
    }

    public Boolean insertPost(String username, String postTitle, String postBody) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username); // Include username
        contentValues.put("post_title", postTitle);
        contentValues.put("post_body", postBody);

        try {
            long result = db.insert(TABLE_NAME_POST, null, contentValues);
            return result != -1;
        } catch (Exception e) {
            Log.e("DB_ERROR", "Error inserting post: " + e.getMessage());
            return false;
        }
    }

    // Method to fetch all posts
    public Cursor getAllPosts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME_POST + " ORDER BY created_at DESC", null);
    }


    public Boolean checkUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_USER + " WHERE username = ?", new String[]{username});

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public Boolean checkUsernamePassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_USER + " WHERE username = ? AND password = ?", new String[]{username, password});

        boolean valid = cursor.getCount() > 0;
        cursor.close();
        return valid;
    }
//    public Boolean updateProfilePicture(String username, String imagePath) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("profile_picture", imagePath);
//
//        int result = db.update(TABLE_NAME_USER, contentValues, "username = ?", new String[]{username});
//        return result > 0;
//    }

    public String getProfilePicture(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT profile_image_path FROM users WHERE username = ?", new String[]{username});

        if (cursor != null && cursor.moveToFirst()) {
            String path = cursor.getString(0);
            cursor.close();
            return path;
        }

        return null;
    }
    public boolean updateProfilePicture(String username, String imagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("profile_image_path", imagePath);

        int rows = db.update("users", values, "username = ?", new String[]{username});
        return rows > 0;
    }

}
