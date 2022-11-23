package com.example.first_app_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnAllPlaces,btnAlreadySeen, btnWantToSee, btnFavorite,btnAbout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initViews();

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



    }

    private void initViews(){
        btnAbout = findViewById(R.id.btnAbout);
        btnAllPlaces=findViewById(R.id.btnAllPlaces);
        btnAlreadySeen=findViewById(R.id.btnAlreadySeen);
        btnWantToSee =findViewById(R.id.btnWantToSee);
        btnFavorite =findViewById(R.id.btnFavourite);

    }
}