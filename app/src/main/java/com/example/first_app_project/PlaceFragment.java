package com.example.first_app_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static com.example.first_app_project.PlaceActivity.PLACE_ID_KEY;

import java.util.Objects;

public class PlaceFragment extends Fragment {

    private String placeName;
    private String placeRanking;
    private String placeRating;

    private float placeRatingFloat;

    private int placeId;

    public PlaceFragment() {
        // Required empty public constructor
    }
    @SuppressLint("DefaultLocale")
    public PlaceFragment(int placeIdParam, float placeRatingParam, int placeRankingParam) {
        placeId = placeIdParam;
        placeName = Objects.requireNonNull(Utils.getPlaceById(placeId)).getName();
        placeRanking = String.valueOf(placeRankingParam);
        placeRating = String.format("%.2f", placeRatingParam);
        placeRatingFloat = placeRatingParam;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place, container, false);
        TextView txtPlaceName = (TextView) view.findViewById(R.id.txtPlaceName);
        txtPlaceName.setText(placeName);
        TextView txtPlaceRanking = (TextView) view.findViewById(R.id.txtPlaceRanking);
        txtPlaceRanking.setText(placeRanking);
        TextView txtPlaceRating = (TextView) view.findViewById(R.id.txtPlaceRating);
        txtPlaceRating.setText(placeRating);
        Button btnPlace = (Button) view.findViewById(R.id.btnPlace);
        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity().getApplication(), PlaceActivity.class);
                intent.putExtra(PLACE_ID_KEY, placeId);
                intent.putExtra("rating", placeRatingFloat);
                startActivity(intent);
            }
        });

        return view;
    }
}