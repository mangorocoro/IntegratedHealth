package com.example.dan.integratedhealth;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class FitnessFragment extends Fragment {


    private View rootView;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_fitness,container,false);

        final Button button = (Button) rootView.findViewById(R.id.workout);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ExerciseListFragment exerciseListFragment = new ExerciseListFragment();
                getFragmentManager().beginTransaction().replace(R.id.content_id,exerciseListFragment).commit();
                // Perform action on click
            }
        });


        return rootView;
    }




}
