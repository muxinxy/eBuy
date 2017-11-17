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
        Toolbar ToolBar = findViewById(R.id.ToolBar);
        setSupportActionBar(ToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button AddShop_yes = findViewById(R.id.AddShop_yes);
        Button AddShop_no = findViewById(R.id.AddShop_no);

        ToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();;
            }
        });

        AddShop_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Add.this, Main2Activity.class);
                startActivity(intent);
            }
        });
    }
    protected void onDestroy(){
        super.onDestroy();
    }
}