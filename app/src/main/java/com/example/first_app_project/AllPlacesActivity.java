package com.example.first_app_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

public class AllPlacesActivity extends AppCompatActivity {
    private RecyclerView placeRecView;
    private PlaceRecViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_places);
        adapter = new PlaceRecViewAdapter(this,"allPlaces");
        placeRecView =findViewById(R.id.placesRecView);
        placeRecView.setAdapter(adapter);
        placeRecView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setPlaces(Utils.getInstance().getAllBooks());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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