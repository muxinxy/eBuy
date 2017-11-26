package com.example.zz.ebuy;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;
    private List<Good> goodList=new ArrayList<>();
    private GoodAdapter GoodAdapter;
    private String shopname_intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        AppBarLayout appBarLayout = findViewById(R.id.ShopBar);
        TextView shop_header = findViewById(R.id.shop_header);
        FloatingActionButton fab=findViewById(R.id.fab);

        dbHelper = new MyDatabaseHelper(this, "UserData.db", null, 1);
        SQLiteDatabase sdb = dbHelper.getWritableDatabase();

        Bundle bundle = getIntent().getExtras();
        shopname_intent = bundle.getString("shopname_intent");
        String shopimage_intent = bundle.getString("shopimage_intent");

        shop_header.setText(shopname_intent);
        appBarLayout.setBackground(Drawable.createFromPath(shopimage_intent));

        initGoods();
        RecyclerView recyclerView = findViewById(R.id.shop_RecylerView);
        GridLayoutManager layoutManager=new GridLayoutManager(this,1);
        recyclerView .setLayoutManager(layoutManager);
        GoodAdapter = new GoodAdapter(goodList);
        recyclerView.setAdapter(GoodAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShopActivity.this,AddGoodActivity.class);
                intent.putExtra("shopname_intent",shopname_intent);
                startActivity(intent);
                finish();
            }
        });
    }

    public void initGoods() {
        try {
            SQLiteDatabase sdb = dbHelper.getReadableDatabase();
            Cursor cursor=sdb.query("gooddata",null,null,null,null,null,null);
            if (cursor.moveToFirst()) {
                do {
                    String shop_name=cursor.getString(cursor.getColumnIndex("shopname"));
                    String good_name=cursor.getString(cursor.getColumnIndex("goodname"));
                    String good_image=cursor.getString(cursor.getColumnIndex("goodimage"));
                    if (shop_name.equals(shopname_intent)){
                        goodList.add(new Good(good_name,good_image));
                    }
                }while (cursor.moveToNext());
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ShopActivity.this,"加载错误",Toast.LENGTH_SHORT).show();
        }
    }
}
