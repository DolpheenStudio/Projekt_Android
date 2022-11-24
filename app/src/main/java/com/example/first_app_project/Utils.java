package com.example.first_app_project;

import java.util.ArrayList;

public class Utils {

    private static Utils instance;
    public static ArrayList<Place> allPlaces;
    private static ArrayList<Place> alreadySeen;
    private static ArrayList<Place> wantToSee;
    private static ArrayList<Place> favoritePlaces;

    public Utils() {

        if(null == allPlaces){
            allPlaces =new ArrayList<>();
            initData();
        }
        if(null == alreadySeen){
            alreadySeen =new ArrayList<>();
            initData();
        }
        if(null == wantToSee){
            wantToSee =new ArrayList<>();
            initData();
        }

        if(null == favoritePlaces){
            favoritePlaces =new ArrayList<>();
            initData();
        }

    }

    private void initData() {
        // add inital data
        allPlaces = new ArrayList<>();
        allPlaces.add(new Place(1,1952,"Pałac kultury",
                "https://s3.eu-central-1.amazonaws.com/pressland-cms/cache/article_show_cover_1_1/cv/palac-kultury-i-nauki-w-warszawie.jpeg","nice place","very nice nice place"));
        allPlaces.add(new Place(2,476,"Koloseum",
                "https://bi.im-g.pl/im/5f/5a/1a/z27631967Q,Koloseum.jpg","nice place","very nice nice place"));
    }


    public static Utils getInstance() {
        if(null != instance){
            return instance;
        }else{
            instance =   new Utils();
            return instance;
        }
    }



    public static ArrayList<Place> getAllBooks() {
        return allPlaces;
    }

    public static ArrayList<Place> getAlreadySeen() {
        return alreadySeen;
    }

    public static ArrayList<Place> getWantToSee() {
        return wantToSee;
    }


    public static ArrayList<Place> getFavoritePlaces() {
        return favoritePlaces;
    }

    public static Place getPlaceById(int id){
        for(Place b: allPlaces){
            if(b.getId()==id){
                return b;
            }
        }
        return null;
    }

    public boolean addToAlreadyRead(Place place){
        return alreadySeen.add(place);
        //jeżeli się doda wartość będzie true
    }
    public boolean addToWishList(Place place){
        return wantToSee.add(place);
    }
    public boolean addToFavourite(Place place){
        return favoritePlaces.add(place);
    }


    public boolean removeFromAlreadyRead(Place place){
        return alreadySeen.remove(place);
    }

    public boolean removeFromWantToRead(Place place){
        return wantToSee.remove(place);
    }

    public boolean removeFromFvouites(Place place){
        return favoritePlaces.remove(place);
    }

}
