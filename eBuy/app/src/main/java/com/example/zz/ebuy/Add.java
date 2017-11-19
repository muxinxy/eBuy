package com.example.zz.ebuy;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Add extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button AddShop_yes = findViewById(R.id.AddShop_yes);
        Button AddShop_no = findViewById(R.id.AddShop_no);
        toolbar.setLogo(R.drawable.ebuy);
        toolbar.setTitle("eBuy");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();;
            }
        });

        AddShop_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent("android.intent.action.MAIN2");
                startActivity(intent);
            }
        });
    }
    protected void onDestroy(){
        super.onDestroy();
    }
}