package com.example.dan.integratedhealth;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



/**
 * Created by twinniss on 4/1/2017.
 */

public class ExerciseListFragment extends Fragment {

    private View view;
    final String[] exercise_list = {"Shoulders", "Triceps", "Biceps", "Chest", "Back", "Legs", "Abs", "Cardio"};

    final String[] tricep_list = {"Ring Down", "Tricep Extension"};
    final String[] bicep_list = {"Barbell Curl", "Dumbbell Curl"};
    final String[] chest_list = {"Bench Press", "Flat Dumbbell Fly"};
    final String[] back_list = {"Dead Lift", "Barbell Row"};
    final String[] legs_list = {"Squat", "Leg Press"};
    final String[] abs_list = {"Crunch", "Plank"};
    final String[] cardio_list = {"Running", "Swimming"};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_exercise,container,false);
        ListView lv = (ListView) view.findViewById(R.id.main_categories);
        final List<String> exercise = new ArrayList<String>(Arrays.asList(exercise_list));

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, exercise);
        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 0:
                        ShoulderFragment shoulderFragment = new ShoulderFragment();
                        getFragmentManager().beginTransaction().replace(R.id.content_id,shoulderFragment).addToBackStack("shoulder").commit();
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                }
            }
        });
        return view;
    }
    public void onBackPress(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }




}
