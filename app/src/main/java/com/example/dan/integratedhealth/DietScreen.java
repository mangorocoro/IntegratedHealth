package com.example.dan.integratedhealth;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

public class DietScreen extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_screen);
        ProgressBar progress_bar = (ProgressBar) findViewById(R.id.diet_progress_bar);
        progress_bar.setProgress(90);

    }

    public int calculate_progress(int starting_weight, int curr_weight, int goal_weight) {
        return ((starting_weight - curr_weight)/(starting_weight - goal_weight));
    }



}
