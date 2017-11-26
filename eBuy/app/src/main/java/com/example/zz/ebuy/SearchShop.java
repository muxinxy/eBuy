package com.example.zz.ebuy;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchShop extends AppCompatActivity {

    private DrawerLayout mydrawerLayout;
    private List<Shop> shopList=new ArrayList<>();
    private MyDatabaseHelper dbHelper;
    private ShopAdapter ShopAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_shop);
        Button btn_SearchShop=findViewById(R.id.btn_SearchShop);

        dbHelper =new MyDatabaseHelper(this,"UserData.db",null,1) ;
        dbHelper.getWritableDatabase();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        recyclerView .setLayoutManager(layoutManager);
        ShopAdapter = new ShopAdapter(shopList);
        recyclerView.setAdapter(ShopAdapter);

        btn_SearchShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initShops();
                onRestart();
            }
        });
    }

    public void initShops() {
        EditText edt_SearchShop=findViewById(R.id.edt_SearchShop);

        String shopname=edt_SearchShop.getText().toString();
        try {
            SQLiteDatabase sdb = dbHelper.getReadableDatabase();
            Cursor cursor=sdb.query("shopdata",null,null,null,null,null,null);
            if (cursor.moveToFirst()) {
                do {
                    String shop_name=cursor.getString(cursor.getColumnIndex("shopname"));
                    String shop_image=cursor.getString(cursor.getColumnIndex("shopimage"));
                    if (shopname.equals(shop_name)){
                        shopList.add(new Shop(shop_name,shop_image));
                    }
                }while (cursor.moveToNext());
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(SearchShop.this,"加载错误",Toast.LENGTH_SHORT).show();
        }
    }
    public void onRestart(){
        super.onRestart();
    }
}
