package com.copia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by steve on 7/13/17.
 */

public class UserDataBase extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "userTable";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FULLNAME = "full_name";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    // Database Version
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "userDB";

    public UserDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_FULLNAME + " TEXT,"
                + COLUMN_USERNAME + " TEXT," + COLUMN_PHONE + " TEXT," + COLUMN_EMAIL + " TEXT,"
                + COLUMN_PASSWORD + " TEXT" + ")";

        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addUser(String full_name, String username, String phone, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("full_name", full_name);
        contentValues.put("username", username);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("password", password);
        db.insert(TABLE_NAME, null, contentValues);
    }

    public ArrayList<UserModel> getUserInformation() {
        ArrayList<UserModel> userModelArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT  * FROM " + TABLE_NAME, null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            UserModel userModel = new UserModel();
            userModel.setUserId(String.valueOf(res.getInt(res
                    .getColumnIndex("id"))));
            userModel.setFullName(res.getString(res.getColumnIndex("full_name")));
            userModel.setUserName(res.getString(res.getColumnIndex("username")));
            userModel.setPhone(res.getString(res.getColumnIndex("phone")));
            userModel.setEmail(res.getString(res.getColumnIndex("email")));
            userModel.setPassword(res.getString(res.getColumnIndex("password")));
            userModelArrayList.add(userModel);
            res.moveToNext();
        }
        res.close();
        return userModelArrayList;
    }

    public void deleteUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("userTable", null, null);
        db.close();
    }

    public int getUserCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        // return row count
        cursor.close();
        return cnt;

    }

    public Boolean isUserRegistered(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "Select * from " + TABLE_NAME + " where " + COLUMN_USERNAME + " = '" + username
                + "' AND "
                + COLUMN_PASSWORD + "= '" + password + "'";
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
}
