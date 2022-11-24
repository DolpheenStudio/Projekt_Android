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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlaceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlaceFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
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
        placeRating = String.valueOf(String.format("%.2f", placeRatingParam));
        placeRatingFloat = placeRatingParam;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlaceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlaceFragment newInstance(String param1, String param2) {
        PlaceFragment fragment = new PlaceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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