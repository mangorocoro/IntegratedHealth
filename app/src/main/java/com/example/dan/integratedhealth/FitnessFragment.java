package com.example.dan.integratedhealth;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.HashMap;


public class FitnessFragment extends Fragment {


    private View rootView;

    private HashMap<String,HashMap> scenarioData;
    private HashMap<String, String> meta;
    private HashMap<String, String> diet;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        scenarioData = new HashMap<>();
        if (getArguments()!=null) {
            scenarioData = (HashMap)getArguments().getSerializable("taskScenarioData");
            meta = scenarioData.get("metadata");
            diet = scenarioData.get("diet");
        }

        rootView=inflater.inflate(R.layout.fragment_fitness,container,false);

        final Button button = (Button) rootView.findViewById(R.id.workout);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ExerciseListFragment exerciseListFragment = new ExerciseListFragment();
                getFragmentManager().beginTransaction().replace(R.id.content_id,exerciseListFragment).commit();
                // Perform action on click
            }
        });


        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("Fitness Information for " + meta.get("name"));
        return rootView;
    }




}
