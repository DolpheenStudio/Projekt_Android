package com.example.first_app_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AllPlacesActivity extends AppCompatActivity {
    private RecyclerView placeRecView;
    private PlaceRecViewAdapter adapter;
    private Button btnAddPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_places);
        adapter = new PlaceRecViewAdapter(this,"allPlaces");
        placeRecView =findViewById(R.id.placesRecView);
        placeRecView.setAdapter(adapter);
        placeRecView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setPlaces(Place.placeArrayList);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onStart(){
        super.onStart();

        setContentView(R.layout.activity_all_places);
        adapter = new PlaceRecViewAdapter(this,"allPlaces");
        placeRecView =findViewById(R.id.placesRecView);
        placeRecView.setAdapter(adapter);
        placeRecView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setPlaces(Place.placeArrayList);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnAddPlace = findViewById(R.id.btnAddPlace);
        btnAddPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(AllPlacesActivity.this, AddPlace.class);
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