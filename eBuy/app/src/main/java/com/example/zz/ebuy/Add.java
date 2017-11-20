package com.example.zz.ebuy;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Add extends AppCompatActivity {

    private SQLiteOpenHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        dbHelper =new MyDatabaseHelper(this,"UserData.db",null,1) ;
        dbHelper.getWritableDatabase();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button AddShop_yes = findViewById(R.id.AddShop_yes);
        Button AddShop_no = findViewById(R.id.AddShop_no);
        final EditText shopname=findViewById(R.id.shopname);
        final ImageView shop_image=findViewById(R.id.shop_image);
        toolbar.setLogo(R.drawable.ebuy);
        toolbar.setTitle("eBuy");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        AddShop_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent("android.intent.action.MAIN2");
                startActivity(intent);
            }
        });

        AddShop_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check1()&&!check2()) {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("shopname", shopname.getText().toString().trim());
                    db.insert("shopdata", null, values);
                    values.clear();
                    Toast.makeText(Add.this, "添加店铺成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent("android.intent.action.MAIN2");
                    startActivity(intent);
                }
            }
            private boolean check1(){
                if(shopname.getText().toString().trim().equals("")){
                    Toast.makeText(Add.this, "店铺名不能为空", Toast.LENGTH_SHORT).show();
                }else{
                    return true;
                }
                return false;
            }
            private boolean check2() {
                SQLiteDatabase sdb = dbHelper.getReadableDatabase();
                Cursor cursor=sdb.query("shopdata",null,null,null,null,null,null);
                if (cursor.moveToFirst()) {
                    do {
                        String shop_name=cursor.getString(cursor.getColumnIndex("shopname"));
                        if (shop_name.equals(shopname.getText().toString().trim())){
                            Toast.makeText(Add.this,"店铺已存在",Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    }while (cursor.moveToNext());
                }
                cursor.close();
                sdb.close();
                return false;
            }
        });
    }

    protected void onDestroy(){
        super.onDestroy();
    }
}