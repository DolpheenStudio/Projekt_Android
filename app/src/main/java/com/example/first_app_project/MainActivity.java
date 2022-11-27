package com.example.first_app_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnAllPlaces,btnAlreadySeen, btnWantToSee, btnFavorite,btnRating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFromDBToMemory();
        initViews();

        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        if(Place.placeArrayList.isEmpty())
        {
            Place tempPlace1 = new Place(1,1952,"Pałac kultury",
                    "https://s3.eu-central-1.amazonaws.com/pressland-cms/cache/article_show_cover_1_1/cv/palac-kultury-i-nauki-w-warszawie.jpeg","nice place","very nice nice place");
            sqLiteManager.addPlaceRatingToDatabase(tempPlace1);

            Place tempPlace2 = new Place(2,476,"Koloseum",
                    "https://bi.im-g.pl/im/5f/5a/1a/z27631967Q,Koloseum.jpg","nice place","very nice nice place");
            sqLiteManager.addPlaceRatingToDatabase(tempPlace2);
        }
        else
        {

        }

        btnAllPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, AllPlacesActivity.class);
                startActivity(intent);
            }
        });

        Utils.getInstance();

        btnAlreadySeen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AlreadySeenPlacesActivity.class);
                startActivity(intent);
            }
        });

        btnWantToSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WantToSeeActivity.class);
                startActivity(intent);
            }
        });
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FavouriteActivity.class);
                startActivity(intent);
            }
        });
        btnRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RatingActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadFromDBToMemory() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        Place.placeArrayList.clear();
        sqLiteManager.populatePlaceArray();
    }

    private void initViews(){
        btnRating = findViewById(R.id.btnRating);
        btnAllPlaces=findViewById(R.id.btnAllPlaces);
        btnAlreadySeen=findViewById(R.id.btnAlreadySeen);
        btnWantToSee =findViewById(R.id.btnWantToSee);
        btnFavorite =findViewById(R.id.btnFavourite);

    }
}