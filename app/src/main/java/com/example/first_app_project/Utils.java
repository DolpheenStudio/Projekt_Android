package com.example.first_app_project;

import java.util.ArrayList;

public class Utils {

    private static Utils instance;
    public static ArrayList<Place> alreadySeen;
    public static ArrayList<Place> wantToSee;
    public static ArrayList<Place> favoritePlaces;

    public Utils() {

        if(null == alreadySeen){
            alreadySeen =new ArrayList<>();
        }
        if(null == wantToSee){
            wantToSee =new ArrayList<>();
        }

        if(null == favoritePlaces){
            favoritePlaces =new ArrayList<>();
        }
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
        return Place.placeArrayList;
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
        for(Place b: Place.placeArrayList){
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
