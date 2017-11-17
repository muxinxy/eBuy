package com.example.zz.ebuy;

import android.content.ClipData;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    private DrawerLayout mydrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mydrawerLayout=findViewById(R.id.drawer_layout);
        NavigationView navView=findViewById(R.id.nav_view);
        ActionBar actionBar=getSupportActionBar();

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
                Intent intent=new Intent(Main2Activity.this,Add.class);
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

    protected void onRestart(){
        super.onRestart();
    }
}
