package com.example.dan.integratedhealth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Clayton on 4/4/2017.
 */

public class AddFoodFragment extends AppCompatActivity implements View.OnClickListener {

    private View root_view;
    private EditText food_name;
    private EditText calories;
    private EditText fats;
    private EditText sugars;
    private EditText proteins;
    private EditText carbohydrates;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_addfood);

        food_name = (EditText) findViewById(R.id.food_edit_text);
        calories = (EditText) findViewById(R.id.calories_edit_text);
        fats = (EditText) findViewById(R.id.fats_edit_text);
        sugars = (EditText) findViewById(R.id.sugars_edit_text);
        proteins = (EditText) findViewById(R.id.protein_edit_text);
        carbohydrates= (EditText) findViewById(R.id.carbs_edit_text);

        Button save_button = (Button) findViewById(R.id.save_button);
        save_button.setOnClickListener(this);

        Button cancel_button = (Button) findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(this);
    }

//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        root_view = inflater.inflate(R.layout.fragment_addfood, container, false);
//
//        food_name = (EditText) root_view.findViewById(R.id.food_edit_text);
//        calories = (EditText) root_view.findViewById(R.id.calories_edit_text);
//        fats = (EditText) root_view.findViewById(R.id.fats_edit_text);
//        sugars = (EditText) root_view.findViewById(R.id.sugars_edit_text);
//        proteins = (EditText) root_view.findViewById(R.id.protein_edit_text);
//        carbohydrates= (EditText) root_view.findViewById(R.id.carbs_edit_text);
//
//        Button save_button = (Button) root_view.findViewById(R.id.save_button);
//        save_button.setOnClickListener(this);
//
//        Button cancel_button = (Button) root_view.findViewById(R.id.cancel_button);
//        cancel_button.setOnClickListener(this);
//
//        return root_view;
//    }

    @Override
    public void onClick(View v){
        switch(v.getId()) {
            case R.id.save_button:
                Intent save_intent = new Intent();
                save_intent.putExtra("FOOD_NAME", food_name.getText());

                if (calories.getText() != null && calories.getText().length() > 0) {
                    save_intent.putExtra("CALORIES", calories.getText());
                } else {
                    save_intent.putExtra("CALORIES", String.valueOf(0));
                }

                if (fats.getText() != null && fats.getText().length() > 0) {
                    save_intent.putExtra("FATS", fats.getText());
                } else {
                    save_intent.putExtra("FATS", String.valueOf(0));
                }

                if (sugars.getText() != null && sugars.getText().length() > 0) {
                    save_intent.putExtra("SUGARS", sugars.getText());
                } else {
                    save_intent.putExtra("SUGARS", String.valueOf(0));
                }

                if (proteins.getText() != null && proteins.getText().length() > 0) {
                    save_intent.putExtra("PROTEINS", proteins.getText());
                } else {
                    save_intent.putExtra("PROTEINS", String.valueOf(0));
                }

                if (carbohydrates.getText() != null && carbohydrates.getText().length() > 0) {
                    save_intent.putExtra("CARBS", carbohydrates.getText());
                } else {
                    save_intent.putExtra("CARBS", String.valueOf(0));
                }


                setResult(Activity.RESULT_OK, save_intent);
                finish();
                break;
            case R.id.cancel_button:
                Intent cancel_intent = new Intent();
                setResult(Activity.RESULT_CANCELED, cancel_intent);
                finish();
                break;
        }
    }
}
