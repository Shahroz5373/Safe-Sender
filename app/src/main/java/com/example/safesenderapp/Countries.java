package com.example.safesenderapp;
import java.io.Serializable;

public class Countries implements Serializable {
    private String name;
    private int image;
    public Countries(){

    }
    public void setName(String name){this.name=name;}
    public String getName(){return name;}

    public void setImage(int image){this.image=image;}

    public int getImage() {
        return image;
    }
}
