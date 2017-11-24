package com.example.zz.ebuy;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private DrawerLayout mydrawerLayout;
    private List<Shop> shopList=new ArrayList<>();
    private SQLiteOpenHelper dbHelper;
    ShopAdapter ShopAdapter;

    public Main2Activity(SQLiteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void setUI(){
        shopList.add(new Shop("店铺","123"));
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager LayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL) ;
        recyclerView .setLayoutManager(LayoutManager );
        ShopAdapter = new ShopAdapter(shopList);
        recyclerView.setAdapter(ShopAdapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar_main2);
        setSupportActionBar(toolbar);
        mydrawerLayout=findViewById(R.id.drawer_layout);
        NavigationView navView=findViewById(R.id.nav_view);
        ActionBar actionBar=getSupportActionBar();
        Intent intent=getIntent();
        String username=intent.getStringExtra("username");
        try {
            initShops();
            setUI();
        }catch (Exception e){
            Toast.makeText(Main2Activity.this,"加载错误",Toast.LENGTH_SHORT).show();
        }

        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu);
        }
        navView.setCheckedItem(R.id.username);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mydrawerLayout.closeDrawers();
                return true;
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);


    }

    public void initShops() {
        try {
            SQLiteDatabase sdb = dbHelper.getReadableDatabase();
            Cursor cursor=sdb.query("shopdata",null,null,null,null,null,null);
            if (cursor!=null&&cursor.getCount()!=0&&cursor.moveToFirst()) {
                do {
                    String shop_name=cursor.getString(cursor.getColumnIndex("shopname"));
                    String shop_image=cursor.getString(cursor.getColumnIndex("shopimage"));
                    shopList.add(new Shop(shop_name,shop_image));
                }while (cursor.moveToNext());
            }
            assert cursor != null;
            cursor.close();
        }catch (Exception e){
            Toast.makeText(Main2Activity.this,"加载错误",Toast.LENGTH_SHORT).show();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu .toolbar,menu) ;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mydrawerLayout.openDrawer(GravityCompat.START);break;
            case R.id.AddShop:
                Intent intent=new Intent("android.intent.action.ADD");
                startActivity(intent);break;
            case R.id.SearchShop:
                Toast.makeText(Main2Activity.this,"SearchShop",Toast.LENGTH_SHORT).show();break;
            default:
        }
        return true;
    }


}
