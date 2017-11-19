package com.example.zz.ebuy;

import android.content.Intent;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main2Activity extends AppCompatActivity {

    private DrawerLayout mydrawerLayout;
    private Shop[] shops={new Shop("123",R.drawable.about),new Shop("456",R.drawable.car)};
    private List<Shop> shopList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mydrawerLayout=findViewById(R.id.drawer_layout);
        NavigationView navView=findViewById(R.id.nav_view);
        ActionBar actionBar=getSupportActionBar();
        initShops();

        RecyclerView recyclerView=findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);

        recyclerView.setLayoutManager(layoutManager);
        ShopAdapter adapter = new ShopAdapter(shopList);
        recyclerView.setAdapter(adapter);

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
    }

    private void initShops() {
        shopList.clear();
        for (int i=0;i<10;i++){
            Random random=new Random();
            int intdex=random.nextInt(shops.length);
            shopList.add(shops[intdex]);
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
            case R.id.AddGood:
                Toast.makeText(Main2Activity.this,"AddGood",Toast.LENGTH_SHORT).show();break;
            case R.id.SearchShop:
                Toast.makeText(Main2Activity.this,"SearchShop",Toast.LENGTH_SHORT).show();break;
            case R.id.SearchGood:
                Toast.makeText(Main2Activity.this,"SearchGood",Toast.LENGTH_SHORT).show();break;
            default:
        }
        return true;
    }


}
