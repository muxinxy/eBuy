package com.example.zz.ebuy;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import static com.example.zz.ebuy.AddActivity.CHOOSE_PHOTO;

public class PersonActivity extends AppCompatActivity {

    private String imagePath;
    private SQLiteOpenHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper =new MyDatabaseHelper(this,"UserData.db",null,1) ;
        dbHelper.getWritableDatabase();
        Intent intent=getIntent();
        final String username_intent=intent.getStringExtra("username_intent");
        setContentView(R.layout.activity_person);
        Button person_yes=findViewById(R.id.person_yes);
        Button person_no=findViewById(R.id.person_no);
        final EditText person_nickname=findViewById(R.id.person_nickname);
        final EditText person_tel=findViewById(R.id.person_tel);
        final EditText person_address=findViewById(R.id.person_address);
        final TextView person_username=findViewById(R.id.person_username);
        final ImageView person_head=findViewById(R.id.head_person);

        Toolbar toolbar = findViewById(R.id.toolbar_person);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();

        SQLiteDatabase sdb = dbHelper.getReadableDatabase();
        Cursor cursor=sdb.query("userdata",null,null,null,null,null,null);
        if (cursor.getCount()!=0&&cursor.moveToFirst()) {
            do {
                String username=cursor.getString(cursor.getColumnIndex("username"));
                String nickname=cursor.getString(cursor.getColumnIndex("nickname"));
                String header=cursor.getString(cursor.getColumnIndex("icon_image"));
                int tel=cursor.getInt(cursor.getColumnIndex("tel"));
                String address=cursor.getColumnName(cursor.getColumnIndex("address"));
                if (username.equals(username_intent)){
                    person_username.setText(username);
                    person_nickname.setText(nickname);
                    String telphone=Integer.toString(tel);
                    person_tel.setText(telphone);
                    person_address.setText(address);
                    Glide.with(this).load(header).asBitmap().placeholder(R.drawable.header).error(R.drawable.header).into(person_head);
                    break;
                }
            }while (cursor.moveToNext());
        }
        cursor.close();

        person_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent("android.intent.action.MAIN2");
                startActivity(intent);
                finish();
            }
        });

        person_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check1()&&!check2()){
                    SQLiteDatabase sdb = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("nickname", person_nickname.getText().toString().trim());
                    values.put("tel", person_tel.getText().toString().trim());
                    values.put("address", person_address.getText().toString().trim());
                    values.put("icon_image", imagePath);
                    sdb.update("userdata" ,values, "username=?",new String[]{person_username.getText().toString()});
                    values.clear();
                    Toast.makeText(PersonActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent("android.intent.action.MAIN2");
                    intent.putExtra("username_intent",person_username.getText().toString().trim());
                    startActivity(intent);
                }
            }
            private boolean check1(){
                if (person_nickname.getText().toString().equals("")){
                    Toast.makeText(PersonActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
                }else if (person_nickname.getText().toString().contains(" ")){
                    Toast.makeText(PersonActivity.this,"不能包含空格",Toast.LENGTH_SHORT).show();
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
                        String nickname=cursor.getString(cursor.getColumnIndex("nickname"));
                        if (nickname.equals(person_nickname.getText().toString().trim())){
                            Toast.makeText(PersonActivity.this,"昵称已存在",Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    }while (cursor.moveToNext());
                }
                cursor.close();
                return false;
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "你拒绝了权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT>=19)
                        // 4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                }else {
                    //4.4以下系统使用这个方法处理图片
                    handleImageBeforeKitKat(data);
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        imagePath = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            assert uri != null;
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else {
            assert uri != null;
            if ("content".equalsIgnoreCase(uri.getScheme())) {
                // 如果是content类型的Uri，则使用普通方式处理
                imagePath = getImagePath(uri, null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                // 如果是file类型的Uri，直接获取图片路径即可
                imagePath = uri.getPath();
            }
        }
        displayImage(imagePath); // 根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        ImageView shop_image=findViewById(R.id.shop_image);
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            shop_image.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "获取图片失败", Toast.LENGTH_SHORT).show();
        }
    }
    protected void onDestroy(){
        super.onDestroy();
    }
}
