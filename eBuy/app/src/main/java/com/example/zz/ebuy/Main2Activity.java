package com.example.zz.ebuy;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private DrawerLayout mydrawerLayout;
    private List<Shop> shopList=new ArrayList<>();
    private MyDatabaseHelper dbHelper;
    private ShopAdapter ShopAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        dbHelper =new MyDatabaseHelper(this,"UserData.db",null,1) ;
        dbHelper.getWritableDatabase();

        Toolbar toolbar = findViewById(R.id.toolbar_main2);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();

        mydrawerLayout=findViewById(R.id.drawer_layout);
        NavigationView navView=findViewById(R.id.nav_view);

        final TextView shop_name=findViewById(R.id.shop_name);

        Intent intent=getIntent();
        final String username_intent=intent.getStringExtra("username_intent");

        initShops();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        recyclerView .setLayoutManager(layoutManager);
        ShopAdapter = new ShopAdapter(shopList);
        recyclerView.setAdapter(ShopAdapter);

        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu);
        }

        navView.setCheckedItem(R.id.username_header);
        View headerView = navView.getHeaderView(0);
        ImageView icon_image = headerView.findViewById(R.id.icon_image);
        TextView username_header=headerView.findViewById(R.id.username_header);
        SQLiteDatabase sdb = dbHelper.getReadableDatabase();
        Cursor cursor=sdb.query("userdata",null,null,null,null,null,null);
        if (cursor.moveToFirst()) {
            do {
                String username=cursor.getString(cursor.getColumnIndex("username"));
                String nickname=cursor.getString(cursor.getColumnIndex("nickname"));
                String userimage=cursor.getColumnName(cursor.getColumnIndex("icon_image"));
                if (username.equals(username_intent)){
                    username_header.setText(nickname);
                    Glide.with(this).load(userimage).asBitmap().placeholder(R.drawable.header).error(R.drawable.header).into(icon_image);
                    break;
                }
            }while (cursor.moveToNext());
        }
        cursor.close();


        ShopAdapter.setOnItemClickListener(new ShopAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent("android.intent.action.SHOP");
                startActivity(intent);
                finish();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(Main2Activity.this,"onLongClick"+position,Toast.LENGTH_SHORT).show();
            }
        });

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_person:
                        Intent intent=new Intent(Main2Activity.this,PersonActivity.class);
                        intent.putExtra("username_intent",username_intent);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.nav_about:
                        Intent intent2=new Intent(Main2Activity.this,AboutActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_quit:
                        Intent intent1=new Intent(Main2Activity.this,MainActivity.class);
                        startActivity(intent1);
                        finish();
                        break;
                    case R.id.nav_car:
                        Intent intent3=new Intent(Main2Activity.this,CarActivity.class);
                        startActivity(intent3);
                        finish();
                        break;
                    case R.id.nav_order:
                        Intent intent4=new Intent(Main2Activity.this,OrderActivity.class);
                        startActivity(intent4);
                        finish();
                }
                mydrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    public void initShops() {
        try {
            SQLiteDatabase sdb = dbHelper.getReadableDatabase();
            Cursor cursor=sdb.query("shopdata",null,null,null,null,null,null);
            if (cursor.moveToFirst()) {
                do {
                    String shop_name=cursor.getString(cursor.getColumnIndex("shopname"));
                    String shop_image=cursor.getString(cursor.getColumnIndex("shopimage"));
                    shopList.add(new Shop(shop_name,shop_image));
                }while (cursor.moveToNext());
            }
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
                startActivity(intent);
                finish();
                break;
            case R.id.SearchShop:
                Toast.makeText(Main2Activity.this,"SearchShop",Toast.LENGTH_SHORT).show();break;
            default:
        }
        return true;
    }


}
