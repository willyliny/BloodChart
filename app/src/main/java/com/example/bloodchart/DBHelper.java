package com.example.bloodchart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBHelper extends SQLiteOpenHelper
{
    public static final String DB_LOGIN = "login.db";
    private static String PACKAGE_NAME = "com.example.bloodchart";
    private static String DB_PATH = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/" + PACKAGE_NAME + "/databases/";
    private static String DB_NAME = "bloodchart";
    private SQLiteDatabase db;
    private final Context context;

    public DBHelper(Context context){
        super(context, DB_LOGIN , null, 1);
        this.context = context;

    }

    @Override
    public  void onCreate(SQLiteDatabase db){
        db.execSQL("create table users(username TEXT, account TEXT primary key, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("drop table if exists users");
    }

    public Boolean insertData(String username, String account, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("account", account);
        values.put("password", password);

        long result = db.insert("users", null, values);
        if(result == -1) return false;
        else
            return true;
    }

    public Boolean checkaccount(String account){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where account=?", new String[] {account});
        if(cursor.getCount()>0)
            return true;
        else
            return  false;
    }
}
