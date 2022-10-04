package com.example.bloodchart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class DBHelper extends SQLiteOpenHelper
{
    public static final String DB_LOGIN = "login2.db";
    private SQLiteDatabase db;
    private final Context context;

    public DBHelper(Context context){
        super(context, DB_LOGIN , null, 1);
        this.context = context;

    }

    @Override
    public  void onCreate(SQLiteDatabase db){
        db.execSQL("create table users(username TEXT, account TEXT primary key, password TEXT)");
        db.execSQL("create table userbpdata(id INTEGER primary key AUTOINCREMENT,account TEXT, date TEXT, time TEXT, sbp TEXT, dbp TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("drop table if exists users");
        db.execSQL("drop table if exists userbpdata");
        onCreate(db);
    }

    public Boolean insertUserData(String username, String account, String password){
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

    public Boolean checkuser(String account, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where account=? and password=?", new String[] {account, password});
        if(cursor.getCount()>0)
            return true;
        else
            return  false;
    }

    public Map<String, String> findusermap(String account){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where account=?", new String[] {account});
        Map<String, String> map = new HashMap<>();
        cursor.moveToFirst();
        map.put("username", cursor.getString(0));
        map.put("account", cursor.getString(1));
        return map;
    }

    public Boolean insertBPdata(String account, String date, String time, String sbp, String dbp){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("account", account);
        values.put("date", date);
        values.put("time", time);
        values.put("sbp", sbp);
        values.put("dbp", dbp);

        long result = db.insert("userbpdata", null, values);
        
        if(result == -1) return false;
        else
            return true;
    }

}
