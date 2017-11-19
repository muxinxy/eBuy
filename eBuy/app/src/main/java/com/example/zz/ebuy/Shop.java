package com.example.zz.ebuy;

/**
 * Created by zz on 2017/11/18.
 */

public class Shop {
    private String name;
    private int imageId;
    public Shop(String name,int imageId){
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
