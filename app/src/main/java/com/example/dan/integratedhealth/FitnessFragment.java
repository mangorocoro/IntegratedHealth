package com.example.dan.integratedhealth;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
public class FitnessFragment extends Fragment {
    private View rootView;
    FragmentViewer fv;
    private HashMap<String,HashMap> scenarioData;
    private HashMap<String, String> meta;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        scenarioData = new HashMap<>();
        if (getArguments() != null) {
            scenarioData = (HashMap) getArguments().getSerializable("taskScenarioData");
            meta = scenarioData.get("metadata");
        }
        if (meta.get("name").equals("Robert")) {
            rootView = inflater.inflate(R.layout.fragment_newbie_workout, container, false);
            TextView header = new TextView(getContext());
            header.setText("Dumbell Raise");
            ListView l = (ListView) rootView.findViewById(R.id.noob1);
            l.addHeaderView(header);
            ListView l1 = (ListView) rootView.findViewById(R.id.noob2);
            TextView header1 = new TextView(getContext());
            header1.setText("Lunge");
            l1.addHeaderView(header1);
        } else if (meta.get("name").equals("Dave")) {
            rootView = inflater.inflate(R.layout.fragment_pro_workout, container, false);
            TextView header = new TextView(getContext());
            header.setText("Bench Press");
            ListView l = (ListView) rootView.findViewById(R.id.pro1);
            l.addHeaderView(header);
            ListView l1 = (ListView) rootView.findViewById(R.id.pro2);
            TextView header1 = new TextView(getContext());
            header1.setText("Deadlift");
            l1.addHeaderView(header1);
        } else {
            rootView = inflater.inflate(R.layout.fragment_fitness, container, false);
            fv = (FragmentViewer) getActivity();
            if (fv.exercise_list.isEmpty()) {
                final Button button = (Button) rootView.findViewById(R.id.workout);
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        ExerciseListFragment exerciseListFragment = new ExerciseListFragment();
                        getFragmentManager().beginTransaction().replace(R.id.content_id, exerciseListFragment).addToBackStack("Fitness Home").commit();
                        // Perform action on click
                    }
                });
            } else {
                TextView tv = (TextView) rootView.findViewById(R.id.message);
                tv.setText("");
                ListView lv = (ListView) rootView.findViewById(R.id.existing_exercise);
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, fv.exercise_list.get(fv.current_exercise));
                TextView header = new TextView(getContext());
                header.setText(fv.current_exercise);
                lv.addHeaderView(header);
                lv.setAdapter(arrayAdapter);
                fv.list_exercise = fv.current_exercise;
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        DataEntryFragment dataEntryFragment = new DataEntryFragment();
                        getFragmentManager().beginTransaction().replace(R.id.content_id, dataEntryFragment).addToBackStack("Fitness Home").commit();
                    }
                });
                Button b = (Button) rootView.findViewById(R.id.workout);
                b.setText("Add another exercise");
            }
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            actionBar.setTitle("Fitness Information for " + meta.get("name"));
        }
        return rootView;
    }
    public void onBackPress(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }
}