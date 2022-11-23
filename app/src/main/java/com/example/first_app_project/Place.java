package com.example.first_app_project;

public class Place {

    private int id, year;
    private String name,imageUrl, shortDesc,longDesc;
    private Boolean isExpanded;
    private float[] ratingArray = new float[3];
    public Place(int id, int year, String name, String imageUrl, String shortDesc, String longDesc) {
        this.id = id;
        this.year = year;
        this.name = name;
        this.imageUrl = imageUrl;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
        this.isExpanded = false;
        for(int i = 0; i < ratingArray.length; i++)
        {
            ratingArray[i] = 0;
        }

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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


    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", year=" + year +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                ", longDesc='" + longDesc + '\'' +
                '}';
    }
}
