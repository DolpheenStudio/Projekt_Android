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
        allPlaces = Place.placeArrayList;
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
