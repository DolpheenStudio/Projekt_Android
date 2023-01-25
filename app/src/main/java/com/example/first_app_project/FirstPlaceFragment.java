package com.example.first_app_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FirstPlaceFragment extends Fragment {

    TextView txtPlaceName;

    public FirstPlaceFragment() {
        // Required empty public constructor
    }

    public FirstPlaceFragment(String placeName) {
        setTxtPlaceName(placeName);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_first_place, container, false);

        txtPlaceName = view.findViewById(R.id.txtPlaceName);

        return view;
    }

    public void setTxtPlaceName(String placeName)
    {
        txtPlaceName.setText(placeName);
    }
}