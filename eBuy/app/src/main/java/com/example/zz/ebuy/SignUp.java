package com.example.zz.ebuy;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.content.ContentValues;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    private MyDatabaseHelper dbHelper ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        dbHelper =new MyDatabaseHelper(this,"UserData.db",null,1) ;
        dbHelper.getWritableDatabase();

        final Button sign_yes= findViewById(R.id.sign_yes);
        final Button sign_no = findViewById(R.id.sign_no);
        final EditText sign_username=findViewById(R.id.sign_username);
        final EditText sign_password=findViewById(R.id.sign_password);
        final EditText re_sign_password=findViewById(R.id.re_sign_password);


        sign_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(SignUp.this, MainActivity.class);
                startActivity(intent);
            }
        });
        sign_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check1()&&!check2()) {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("username", sign_username.getText().toString().trim());
                    values.put("password", sign_password.getText().toString().trim());
                    db.insert("userdata", null, values);
                    values.clear();
                    Toast.makeText(SignUp.this, "注册成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUp.this, Main2Activity.class);
                    startActivity(intent);
                }
            }
            private boolean check1(){
                if(sign_username.getText().toString().trim().equals("")){
                    Toast.makeText(SignUp.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                }else if(sign_password.getText().toString().trim().equals("")){
                    Toast.makeText(SignUp.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }else if(!sign_password.getText().toString().trim().equals(re_sign_password.getText().toString().trim())) {
                    Toast.makeText(SignUp.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                }else{
                    return true;
                }
                return false;
            }
            private boolean check2() {
                SQLiteDatabase sdb = dbHelper.getReadableDatabase();
                Cursor cursor=sdb.query("userdata",null,null,null,null,null,null);
                if (cursor.moveToFirst()) {
                    do {
                        String username=cursor.getString(cursor.getColumnIndex("username"));
                        if (username.equals(sign_username.getText().toString().trim())){
                            Toast.makeText(SignUp.this,"用户名已存在",Toast.LENGTH_SHORT).show();
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
        dbHelper.close();
    }

}