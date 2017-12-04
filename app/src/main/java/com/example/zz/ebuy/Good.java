package com.example.zz.ebuy;

public class Good {
    private String name;
    private String imageId;
    Good(String name, String imageId){
        this.name=name;
        this.imageId=imageId;
    }
    public String getName(){
        return name;
    }
    public String getImageId(){
        return imageId;
    }
}
