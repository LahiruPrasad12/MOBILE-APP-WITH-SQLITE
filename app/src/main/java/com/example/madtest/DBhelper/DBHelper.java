package com.example.madtest.DBhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.madtest.database.UsersMaster;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "UserInfo.db";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String TABLE_CREATE_QUERY = " CREATE TABLE " + UsersMaster.Users.TABLE_NAME + "(" +
                UsersMaster.Users._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                UsersMaster.Users.COLUMN_NAME_USERNAME + " TEXT," +
                UsersMaster.Users.COLUMN_NAME_PASSWORD + " TEXT);";

        db.execSQL(TABLE_CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + UsersMaster.Users.TABLE_NAME;
        db.execSQL(DROP_TABLE_QUERY);
        onCreate(db);
    }

    public Long addInfo(String user_name, String password) {
        System.out.println(user_name);
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UsersMaster.Users.COLUMN_NAME_USERNAME, user_name);
        values.put(UsersMaster.Users.COLUMN_NAME_PASSWORD, password);

        long newRowId = db.insert(UsersMaster.Users.TABLE_NAME, null, values);
        db.close();
        return newRowId;
    }


    //Read All Information
    public List<Users> getInfo() {
        List<Users> ar = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = " SELECT * FROM " + UsersMaster.Users.TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Users student = new Users();
                student.setId(cursor.getInt(0));
                student.setUsername(cursor.getString(1));
                student.setPassword(cursor.getString(2));

                ar.add(student);
            } while (cursor.moveToNext());
        }
        System.out.println(ar);
        return ar;
    }


    //selected data
    public List readAllInfo(String req) {
        SQLiteDatabase db = getReadableDatabase();

        String projection[] = {
                UsersMaster.Users.COLUMN_NAME_USERNAME,
                UsersMaster.Users.COLUMN_NAME_PASSWORD
        };

        String sortOrder = UsersMaster.Users.COLUMN_NAME_USERNAME + " ASC";

        Cursor cursor = db.query(
                UsersMaster.Users.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        List userName = new ArrayList();
        List Password = new ArrayList();


        while (cursor.moveToNext()) {
            String uName = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_USERNAME));
            String uPass = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_USERNAME));
            userName.add(uName);
            Password.add(uPass);
        }
        cursor.close();

        System.out.println(userName);
        System.out.println("userName");
        if (req == "user") {
            return userName;
        } else if (req == "password") {
            return Password;
        } else {
            return null;
        }
    }

    //Get Specific data
    public Users getStudent(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(UsersMaster.Users.TABLE_NAME, new String[]{UsersMaster.Users._ID, UsersMaster.Users.COLUMN_NAME_USERNAME, UsersMaster.Users.COLUMN_NAME_PASSWORD}, UsersMaster.Users.COLUMN_NAME_USERNAME + " =?",
                new String[]{String.valueOf(name)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToNext();

        Users student = new Users(cursor.getString(1), cursor.getString(2));
        return student;

    }

    //DELETE SPECIFIC DATA
    public void deleteInfo(String name) {
        SQLiteDatabase db = getReadableDatabase();
        String selection = UsersMaster.Users.COLUMN_NAME_USERNAME + " LIKE?";
        String selectinArgs[] = {name};
        db.delete(UsersMaster.Users.TABLE_NAME, selection, selectinArgs);
    }


}
