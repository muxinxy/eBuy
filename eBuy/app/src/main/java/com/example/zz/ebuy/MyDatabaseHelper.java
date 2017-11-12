package com.example.zz.ebuy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String User="create table userdata("
            + "id integer primary key autoincrement,"
            + "username text,"
            + "password text)";
    private Context myContext;
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        myContext =context ;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(User);
        Toast .makeText(myContext ,"Create succeeded!",Toast.LENGTH_LONG ).show() ;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists userdata") ;
        onCreate(db) ;
    }
}