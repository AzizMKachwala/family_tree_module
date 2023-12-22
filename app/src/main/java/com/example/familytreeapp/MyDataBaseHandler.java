package com.example.familytreeapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDataBaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FamilyTree";
    private static final String TABLE_FAMILY = "Family";
    private static final String KEY_USER_ID = "Id";
    private static final String KEY_USER_NAME = "Names";
    private static final String KEY_USER_DOB = "DateOfBirth";
    private static final String KEY_USER_IMAGE = "Image";
    private static final String KEY_USER_PARENT_ID = "ParentId";

    public MyDataBaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_FAMILY + "("
                + KEY_USER_ID + " INTEGER PRIMARY KEY,"
                + KEY_USER_NAME + " TEXT,"
                + KEY_USER_DOB + " TEXT,"
                + KEY_USER_IMAGE + " BLOB,"
                + KEY_USER_PARENT_ID + " INTEGER"
                + ")";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    public long insertMemberData(String Name, String DOB, String Image, String ParentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME, Name);
        values.put(KEY_USER_DOB, DOB);
        values.put(KEY_USER_IMAGE, Image);
        values.put(KEY_USER_PARENT_ID, ParentId);

        long insertedUserId = db.insert(TABLE_FAMILY, null, values);
        db.close();

        return insertedUserId;
    }

    public ArrayList<MyDbDataModelFamily> getAllMembers() {
        ArrayList<MyDbDataModelFamily> categoryList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FAMILY, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String userId = cursor.getString(cursor.getColumnIndex(KEY_USER_ID));
                @SuppressLint("Range") String userName = cursor.getString(cursor.getColumnIndex(KEY_USER_NAME));
                @SuppressLint("Range") String userDob = cursor.getString(cursor.getColumnIndex(KEY_USER_DOB));
                @SuppressLint("Range") String userImage = cursor.getString(cursor.getColumnIndex(KEY_USER_IMAGE));
                @SuppressLint("Range") String userParentId = cursor.getString(cursor.getColumnIndex(KEY_USER_PARENT_ID));

                MyDbDataModelFamily category = new MyDbDataModelFamily(userId, userName, userDob, userImage, userParentId);
                categoryList.add(category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return categoryList;
    }

    public void deleteMemberById(String userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAMILY, KEY_USER_ID + "=?", new String[]{userId});
        db.close();
    }

    public void updateMember(String userId, String newName, String newDOB, String newImage, String newParentId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME, newName);
        values.put(KEY_USER_DOB, newDOB);
        values.put(KEY_USER_IMAGE, newImage);
        values.put(KEY_USER_PARENT_ID, newParentId);

        db.update(TABLE_FAMILY, values, KEY_USER_ID + "=?", new String[]{userId});

        db.close();
    }

    public void updateParentId(String userId, long newParentId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_PARENT_ID, newParentId);

        db.update(TABLE_FAMILY, values, KEY_USER_ID + "=?", new String[]{userId});

        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
