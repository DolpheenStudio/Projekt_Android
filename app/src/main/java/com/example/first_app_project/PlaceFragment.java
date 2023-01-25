package com.example.first_app_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static com.example.first_app_project.PlaceActivity.PLACE_ID_KEY;

public class PlaceFragment extends Fragment {

    private String placeName;
    private String placeRanking;
    private String placeRating;

    private float placeRatingFloat;

    private int placeId;

    private TextView txtPlaceName;
    private TextView txtplaceRating;
    private TextView txtplaceRanking;

    private Button btnPlace;

    public PlaceFragment() {
        // Required empty public constructor
    }
    public PlaceFragment(int placeIdParam, float placeRatingParam, int placeRankingParam) {
        placeId = placeIdParam;
        placeName = Utils.getPlaceById(placeId).getName();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_place, container, false);
        txtPlaceName = (TextView) view.findViewById(R.id.txtPlaceName);
        txtPlaceName.setText(placeName);
        txtplaceRanking = (TextView) view.findViewById(R.id.txtPlaceRanking);
        txtplaceRanking.setText(placeRanking);
        txtplaceRating = (TextView) view.findViewById(R.id.txtPlaceRating);
        txtplaceRating.setText(placeRating);
        btnPlace = (Button) view.findViewById(R.id.btnPlace);
        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplication(), PlaceActivity.class);
                intent.putExtra(PLACE_ID_KEY, placeId);
                intent.putExtra("rating", placeRatingFloat);
                startActivity(intent);
            }
        });

        return view;
    }
}