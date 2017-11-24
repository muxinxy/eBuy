package com.example.zz.ebuy;

import android.graphics.Bitmap;

import java.net.URI;

public class Shop {
    private String name;
    private String imageId;
    Shop(String name, String imageId){
        this.name=name;
        this.imageId=imageId;
    }
    public String getName(){
        return name;
    }
    String getImageId(){
        return imageId;
    }
}
