package com.example.dan.integratedhealth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class ScenarioPicker extends Activity {

    public static final int FITNESS_ROBERT = 1;
    public static final int FITNESS_DAVE = 2;
    public static final int DIET_RILEY = 3;
    public static final int DIET_VANESSA = 4;
    public static final int GENERAL_GEORGE = 5;
    public static final int GENERAL_JIMMY = 6;
    public static final int NEW_USER = 7;

    private int selectedScenario = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenario_picker);

        final Spinner spinner = (Spinner) findViewById(R.id.scenario_spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = spinner.getSelectedItemPosition();
                final TextView scenarioDescription = (TextView)findViewById(R.id.scenario_description_box);
                ImageView img = (ImageView) findViewById(R.id.scenario_headshot);
                switch(pos){
                    case 0:
                        img.setImageResource(R.drawable.semantic_ui_middleagedasian);
                        scenarioDescription.setText(R.string.fitness_robert);
                        selectedScenario = pos+1;
                        break;
                    case 1:
                        img.setImageResource(R.drawable.semantic_ui_beardedwavyhair);
                        scenarioDescription.setText(R.string.fitness_dave);
                        selectedScenario = pos+1;
                        break;
                    case 2:
                        img.setImageResource(R.drawable.semantic_ui_asianmom);
                        scenarioDescription.setText(R.string.diet_riley);
                        selectedScenario = pos+1;
                        break;
                    case 3:
                        img.setImageResource(R.drawable.semantic_ui_earrings);
                        scenarioDescription.setText(R.string.diet_vanessa);
                        selectedScenario = pos+1;
                        break;
                    case 4:
                        img.setImageResource(R.drawable.semantic_ui_asianglasses);
                        scenarioDescription.setText(R.string.general_george);
                        selectedScenario = pos+1;
                        break;
                    case 5:
                        img.setImageResource(R.drawable.semantic_ui_asianguybangs);
                        scenarioDescription.setText(R.string.general_jimmy);
                        selectedScenario = pos+1;
                        break;
                    case 6:
                        img.setImageResource(R.drawable.semantic_ui_purplehairhipstergirl);
                        scenarioDescription.setText(R.string.new_user);
                        selectedScenario = pos+1;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.scenarios_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }



    /* Submit results on click */
    public void submitScenario(View v) {
        //Intent intent = new Intent(this, HomeScreen.class);
        Intent intent = new Intent();
        intent.putExtra("scenarioNum", selectedScenario);
        setResult(RESULT_OK, intent);
        finish();
    }


}
