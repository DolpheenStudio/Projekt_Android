package com.example.first_app_project;

import java.util.ArrayList;

public class Utils {

    private static Utils instance;

    public Utils() {

    }

    public static Utils getInstance() {
        if (null == instance) {
            instance = new Utils();
        }
        return instance;
    }

    public static Place getPlaceById(int id){
        for(Place b: Place.placeArrayList){
            if(b.getId()==id){
                return b;
            }
        }
        return null;
    }
}
