package com.example.bloodchart;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class DBHelper extends SQLiteOpenHelper
{
    public static final String DB_LOGIN = "login2.db";
    private SQLiteDatabase db;
    private final Context context;
    private static final Integer db_date = 2;
    private static final Integer db_time = 3;
    private static final Integer db_sbp = 4;
    private static final Integer db_dbp = 5;
    HomeActivity HA;
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

    public Boolean updateBPdata(String _id, String date, String time, String sbp, String dbp){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("date", date);
        values.put("time", time);
        values.put("sbp", sbp);
        values.put("dbp", dbp);
        long result = db.update("userbpdata", values, "id=?",new String[] {_id});
//        long result = db.insert("userbpdata", null, values);

        if(result == -1) return false;
        else
            return true;
    }

    public Boolean deleteBPdata(String _id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("userbpdata","id=?",new String[] {_id});
        if(result == -1) return false;
        else
            return true;
    }

    public StringBuffer searchUserRecordData(String account){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from userbpdata where account=?", new String[] {account});
        cursor.moveToFirst();
        String[] title = {"date", "time", "sbp", "dbp"};
        StringBuffer csvText = new StringBuffer();
        for(int i=0; i<title.length; i++){
            csvText.append(title[i]+",");
        }

        do{
            csvText.append("\n");
            csvText.append(cursor.getString(db_date) + ",");
            csvText.append(cursor.getString(db_time) + ",");
            csvText.append(cursor.getString(db_sbp) + ",");
            csvText.append(cursor.getString(db_dbp) + ",");

            System.out.println("date: " + cursor.getString(db_date));
            System.out.println("time: " + cursor.getString(db_time));
            System.out.println("sbp: " + cursor.getString(db_sbp));
            System.out.println("dbp: " + cursor.getString(db_dbp));

        }while (cursor.moveToNext());
        Log.d(TAG, "makeCSV\n" + csvText);
        System.out.println("csv: " + csvText);
        return csvText;
    }

    public Cursor getdata(String account){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from userbpdata where account=?", new String[] {account});
        cursor.moveToFirst();
        return cursor;
    }

}
