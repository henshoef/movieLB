package com.example.android.moviemanagment;

import android.content.Context;


/**
 * Created by Android on 11/03/2018.
 */

public class MovieSample{
     private  int id;
    private  String name;
    private  String decription;
    private  String imageUrl;


    public MovieSample(int id, String name, String decription, String imageUrl) {
        this.id = id;
        this.name = name;
        this.decription = decription;
        this.imageUrl = imageUrl;
    }

    public MovieSample() {

    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void set(MovieSample m){
        name =m.getName();
        decription = m.getDecription();
    }
}
