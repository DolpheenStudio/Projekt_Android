package com.example.first_app_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PlaceActivity extends AppCompatActivity {

    public static final String PLACE_ID_KEY ="placeId";
    private TextView txtPlaceName, txtYear,txtDescription, ratingBarDescription[] = new TextView[3], txtRatingAverage;
    private Button btnAddToWantToSee, btnAddToAlreadySeen, btnRanking,
    btnAddToFavourite, btnDeletePlace;
    private ImageView placeImage;
    private RatingBar[] ratingBarsArray = new RatingBar[3];
    private RatingBar ratingAverage;
    private int placeId = -1;

    SQLiteManager sqLiteManager;

    private float[] ratingArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        sqLiteManager = SQLiteManager.instanceOfDatabase(this);

        txtYear = findViewById(R.id.txtYear);
        txtPlaceName = findViewById(R.id.txtPlaceName);
        txtDescription = findViewById(R.id.txtDescription);
        btnAddToAlreadySeen = findViewById(R.id.btnAddAlreadySeen);
        btnAddToFavourite = findViewById(R.id.btnAddToFavourites);
        btnAddToWantToSee =findViewById(R.id.btnAddWantToSee);
        placeImage = findViewById(R.id.imgPlace);
        ratingBarsArray[0] = findViewById(R.id.ratingBarThingsToSee);
        ratingBarsArray[1] = findViewById(R.id.ratingBarPrices);
        ratingBarsArray[2] = findViewById(R.id.ratingBarFood);
        ratingAverage = findViewById(R.id.ratingBarAvarage);

        ratingBarDescription[0] = findViewById(R.id.txtRatingThingsToSee);
        ratingBarDescription[1] = findViewById(R.id.txtRatingPrices);
        ratingBarDescription[2] = findViewById(R.id.txtRatingFood);
        txtRatingAverage = findViewById(R.id.txtRatingAvarage);

        btnRanking = findViewById(R.id.btnRanking);
        btnDeletePlace = findViewById(R.id.btnDeletePlace);

//todo: get data from recycle view

        for(int i = 0; i < ratingBarsArray.length; i++)
        {
            ratingBarsArray[i].setVisibility(View.INVISIBLE);
            ratingBarDescription[i].setVisibility(View.INVISIBLE);
            ratingAverage.setVisibility(View.INVISIBLE);
            txtRatingAverage.setVisibility(View.INVISIBLE);
        }

        Intent intent = getIntent();
        if (null!=intent) {
            placeId = intent.getIntExtra(PLACE_ID_KEY, -1);
            if (placeId != -1) {
                Place incomingPlace = Utils.getInstance().getPlaceById(placeId);
                if (null != incomingPlace) {
                    setData(incomingPlace);

                    handleAlreadySeen(placeId - 1);
                    handleWantToSeePlaces(placeId - 1);
                    handleFavoritePlaces(placeId - 1);
                }
            }
        }

        for(int i = 0; i < ratingBarsArray.length; i++) {
            int finalI = i;
            ratingBarsArray[i].setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    Place thisPlace = Utils.getInstance().getPlaceById(placeId);
                    thisPlace.setRatingArray(v, finalI);
                    ratingArray = new float[thisPlace.ratingArray.length];
                    float ratingSum = 0;
                    for(int j = 0; j < ratingArray.length; j++)
                    {
                        ratingArray[j] = thisPlace.ratingArray[j];
                        ratingSum += ratingArray[j];
                    }
                    ratingAverage.setRating(ratingSum / ratingArray.length);
                    sqLiteManager.updatePlaceRatingDB(thisPlace);
                }
            });
        }
        btnRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlaceActivity.this, RatingActivity.class);
                intent.putExtra("placeId", placeId);
                startActivity(intent);
            }
        });

        btnDeletePlace.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                sqLiteManager.deletePlaceFromDB(Utils.getPlaceById(placeId));
                Intent intent  = new Intent(PlaceActivity.this, AllPlacesActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this,MainActivity.class);
        //when u use back button u will go back to main activity
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(intent);
    }

    private void handleAlreadySeen(int placeId){
        if(Place.placeArrayList.get(placeId).getIsAlreadySeen()){
            btnAddToAlreadySeen.setEnabled(false);
            for(int i = 0; i < ratingBarsArray.length; i++)
            {
                ratingBarsArray[i].setVisibility(View.VISIBLE);
                ratingBarDescription[i].setVisibility(View.VISIBLE);
            }
            ratingAverage.setVisibility(View.VISIBLE);
            txtRatingAverage.setVisibility(View.VISIBLE);

            float ratingSum = 0;
            ratingArray = new float[Place.placeArrayList.get(placeId).ratingArray.length];
            for(int j = 0; j < ratingArray.length; j++)
            {
                ratingArray[j] = Place.placeArrayList.get(placeId).ratingArray[j];
                ratingSum += ratingArray[j];
            }
            ratingAverage.setRating(ratingSum / ratingArray.length);
        }else{
            btnAddToAlreadySeen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Place.placeArrayList.get(placeId).setIsAlreadySeen(true);
                    sqLiteManager.updatePlaceRatingDB(Place.placeArrayList.get(placeId));
                    Toast.makeText(PlaceActivity.this,"Place added",Toast.LENGTH_SHORT).show();
                    // navigate the user
                    finish();
                    startActivity(getIntent());
                }
            });
        }
    }

    private void handleWantToSeePlaces(final int placeId){
        if(Place.placeArrayList.get(placeId).getIsWantToSee()){
            btnAddToWantToSee.setEnabled(false);
        }else{
            btnAddToWantToSee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Place.placeArrayList.get(placeId).setIsWantToSee(true);
                    sqLiteManager.updatePlaceRatingDB(Place.placeArrayList.get(placeId));
                    Toast.makeText(PlaceActivity.this,"Place added",Toast.LENGTH_SHORT).show();
                    // navigate the user
                    btnAddToWantToSee.setEnabled(false);
                }
            });
        }
    }

    private void handleFavoritePlaces(final int placeId){

        if(Place.placeArrayList.get(placeId).getIsFavourite()){
            btnAddToFavourite.setEnabled(false);
        }else{
            btnAddToFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Place.placeArrayList.get(placeId).setIsFavourite(true);
                    sqLiteManager.updatePlaceRatingDB(Place.placeArrayList.get(placeId));
                    Toast.makeText(PlaceActivity.this,"Place added",Toast.LENGTH_SHORT).show();
                    // navigate the user
                    btnAddToFavourite.setEnabled(false);
                }
            });
        }
    }



    private void setData(Place place){
        txtPlaceName.setText(place.getName());
        txtYear.setText(String.valueOf(place.getYear()));
        txtDescription.setText(place.getLongDesc());

        for(int i = 0; i < ratingBarsArray.length; i ++)
        {
            ratingBarsArray[i].setRating(place.getRatingArray(i));
        }

        if(getIntent().getFloatExtra("rating", 0) != 0)
        {
            ratingAverage.setRating(getIntent().getFloatExtra("rating", 0));
        }

        placeImage.setImageURI(place.getImageUri());

    }

}