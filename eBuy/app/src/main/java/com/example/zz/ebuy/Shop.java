package com.example.zz.ebuy;

import android.graphics.Bitmap;

import java.net.URI;

public class Shop {
    private String name;
    private int imageId;
    Shop(String name, int imageId){
        this.name=name;
        this.imageId=imageId;
    }
    public String getName(){
        return name;
    }
    public int getImageId(){
        return imageId;
    }
}
