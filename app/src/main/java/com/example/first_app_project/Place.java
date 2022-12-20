package com.example.first_app_project;

import android.net.Uri;

import java.util.ArrayList;

public class Place {

    public static ArrayList<Place> placeArrayList = new ArrayList<>();

    private int id, year;
    private String name, shortDesc,longDesc;
    private Uri imageUri;
    private Boolean isExpanded;
    public float[] ratingArray = new float[3];
    private boolean isAlreadySeen, isWantToSee, isFavourite;
    public Place(int id, int year, String name, Uri imageUri, String shortDesc, String longDesc) {
        this.id = id;
        this.year = year;
        this.name = name;
        this.imageUri = imageUri;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
        this.isExpanded = false;
        for(int i = 0; i < ratingArray.length; i++)
        {
            ratingArray[i] = 0;
        }
        isAlreadySeen = false;
        isWantToSee = false;
        isFavourite = false;
    }

    public Place(int id, int year, String name, Uri imageUri, String shortDesc, String longDesc, float[] ratingArray,
                 boolean isAlreadySeen, boolean isWantToSee, boolean isFavourite) {
        this.id = id;
        this.year = year;
        this.name = name;
        this.imageUri = imageUri;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
        this.isExpanded = false;
        this.ratingArray = ratingArray;

        this.isAlreadySeen = isAlreadySeen;
        this.isWantToSee = isWantToSee;
        this.isFavourite = isFavourite;

    }

    public Boolean getExpanded() {
        return isExpanded;
    }

    public void setExpanded(Boolean expanded) {
        isExpanded = expanded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUrl) {
        this.imageUri = imageUri;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    public float getRatingArray(int index) { return this.ratingArray[index]; }

    public void setRatingArray(float rating, int index) { this.ratingArray[index] = rating; }

    public boolean getIsWantToSee() {return this.isWantToSee;}

    public void setIsWantToSee(boolean wantToSee) {this.isWantToSee = wantToSee;}

    public boolean getIsAlreadySeen() {return this.isAlreadySeen;}

    public void setIsAlreadySeen(boolean alreadySeen) {this.isAlreadySeen = alreadySeen;}

    public boolean getIsFavourite() {return this.isFavourite;}

    public void setIsFavourite(boolean favourite) {this.isFavourite = favourite;}


    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", year=" + year +
                ", name='" + name + '\'' +
                ", imageUri='" + imageUri + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                ", longDesc='" + longDesc + '\'' +
                '}';
    }
}
