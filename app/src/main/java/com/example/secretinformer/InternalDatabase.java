package com.example.secretinformer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class InternalDatabase extends SQLiteOpenHelper {

    public static final String DB_NAME = "APP_DATA";
    public String sql_query = null;

    public InternalDatabase(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user_data(name text, password text);");
        //db.execSQL("create table issues(comaplains txt);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user_data;");
        //db.execSQL("drop table if exists issues;");
        onCreate(db);
    }

    public Boolean insertData(String name, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        sql_query = "insert into user_data values('" + name + "', '" + password + "');";
        db.execSQL(sql_query);
        return true;
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        sql_query = "select * from user_data;";
        Cursor res = db.rawQuery(sql_query, null);
        return res;
    }

    public void updateData(String name, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        sql_query = "update user_data set uname = '" + name + "', password = '" + password + "';";
        db.execSQL(sql_query);
    }
}
