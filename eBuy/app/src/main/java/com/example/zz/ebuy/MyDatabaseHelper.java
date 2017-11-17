package com.example.zz.ebuy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String User="create table userdata("
            + "id integer primary key autoincrement,"
            + "username text,"
            + "password text)";

    private static final String Shop="create table shopdata("
            +"id integer primary key autoincrement,"
            +"shopname text,"
            +"shopimage text)";
    private Context myContext;
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        myContext =context ;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(User);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists userdata") ;
        db.execSQL("drop table if exists shopdata");
        onCreate(db) ;
    }
}