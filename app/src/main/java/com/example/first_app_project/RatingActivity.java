package com.example.first_app_project;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import static com.example.first_app_project.PlaceActivity.PLACE_ID_KEY;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class RatingActivity extends AppCompatActivity {

    private FrameLayout firstFrameLayout;
    private FrameLayout parentFrameLayout;

    float[][] placesRatingArray;
    int placeId = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        firstFrameLayout = findViewById(R.id.firstFrameLayout);
        parentFrameLayout = findViewById(R.id.ratingFrameLayout);

        if(getIntent().toString().contains("has extras")) {
            placeId = getIntent().getIntExtra(PLACE_ID_KEY, 0);
            Toast.makeText(getApplicationContext(), String.valueOf(placeId), Toast.LENGTH_SHORT).show();
        }

        ArrayList<Place> alreadySeenPlaces = new ArrayList<>();

        int idx = 0;
        for(Place tempPlace : Place.placeArrayList)
        {
            if(tempPlace.getIsAlreadySeen())
            {
                alreadySeenPlaces.add(tempPlace);
            }
        }

        placesRatingArray = new float[alreadySeenPlaces.size()][2];

        for(int i = 0; i < alreadySeenPlaces.size(); i++)
        {
            float ratingSum = 0;
            for(int j = 0; j < alreadySeenPlaces.get(i).ratingArray.length; j++)
            {
                ratingSum += alreadySeenPlaces.get(i).ratingArray[j];
            }
            placesRatingArray[i][0] = ratingSum / alreadySeenPlaces.get(i).ratingArray.length;
            placesRatingArray[i][1] = alreadySeenPlaces.get(i).getId();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Arrays.sort(placesRatingArray, (a, b) -> Float.compare(b[0], a[0]));
        }

        if(alreadySeenPlaces.size() > 0) {
            if(placesRatingArray[0][1] == placeId)
            {
                firstFrameLayout.setBackgroundColor(Color.RED);
            }

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.firstFrameLayout, new PlaceFragment((int) placesRatingArray[0][1], placesRatingArray[0][0], 1));
            ft.commit();
        }
        for(int i = 1; i < alreadySeenPlaces.size(); i++)
        {
            parentFrameLayout.addView(createFrameLayout(i, i));
        }

        parentFrameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT * alreadySeenPlaces.size()));
    }

    private FrameLayout createFrameLayout(int placeIdParam, int index)
    {
        FrameLayout tempFrameLayout = new FrameLayout(this);
        tempFrameLayout.setLayoutParams(new FrameLayout.LayoutParams(firstFrameLayout.getLayoutParams()));
        tempFrameLayout.setTranslationY(200 * index);
        tempFrameLayout.setId(placeIdParam);

        if(placesRatingArray[index][1] == placeId)
        {
            tempFrameLayout.setBackgroundColor(Color.RED);
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(placeIdParam, new PlaceFragment((int) placesRatingArray[index][1], placesRatingArray[index][0], index + 1));
        ft.commit();

        return tempFrameLayout;
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this,MainActivity.class);
        //when u use back button u will go back to main activity
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(intent);
    }
}