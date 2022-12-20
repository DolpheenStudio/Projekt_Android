package com.example.first_app_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;

public class WantToSeeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_want_to_see);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.placesRecView);
        PlaceRecViewAdapter adapter = new PlaceRecViewAdapter(this,"WantToSee");
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Place> wantToSeePlacesArray = new ArrayList<>();

        for(Place tempPlace : Place.placeArrayList)
        {
            if(tempPlace.getIsWantToSee())
            {
                wantToSeePlacesArray.add(tempPlace);
            }
        }

        adapter.setPlaces(wantToSeePlacesArray);
    }


    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this,MainActivity.class);
        //when u use back button u will go back to main activity
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}