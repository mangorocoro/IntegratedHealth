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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class HomeScreen extends AppCompatActivity {

    TextView currentScenarioBox;

    HashMap<String, HashMap> taskscenario = new HashMap<String, HashMap>();

    static final int SCENARIO_NUM = 1;  // The request code

    private String prev_class;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
	
	


        prev_class = getIntent().getStringExtra("currentScenario");
        currentScenarioBox = (TextView) findViewById(R.id.currentScenario);

        if (prev_class != null) {
            currentScenarioBox.setText(R.string.fitness_robert_title);
            switch (prev_class) {
                case "Fitness - Robert":
                    currentScenarioBox.setText(R.string.fitness_robert_title);
                    populateData(1);
                    break;

                case "Fitness - Dave":
                    currentScenarioBox.setText(R.string.fitness_dave_title);
                    populateData(2);
                    break;

                case "Diet - Riley":
                    currentScenarioBox.setText(R.string.diet_riley_title);
                    populateData(3);
                    break;

                case "Diet - Vanessa":
                    currentScenarioBox.setText(R.string.diet_vanessa_title);
                    populateData(4);
                    break;

                case "General - George":
                    currentScenarioBox.setText(R.string.general_george_title);
                    populateData(5);
                    break;

                case "General - Jimmy":
                    currentScenarioBox.setText(R.string.general_jimmy_title);
                    populateData(6);
                    break;

                case "newuser":
                    currentScenarioBox.setText(R.string.new_user_title);
                    populateData(7);
                    break;

            }
        } else { // default scenario
            currentScenarioBox.setText(R.string.fitness_robert_title);
            populateData(1);
        }



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);


        
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void general(View v) {
        Intent intent = new Intent(this, FragmentViewer.class);
        intent.putExtra("intent","general");
        intent.putExtra("scenarioData", taskscenario);
        startActivity(intent);
    }

    public void diet(View v) {
        Intent intent = new Intent(this, FragmentViewer.class);
        intent.putExtra("intent","diet");
        intent.putExtra("scenarioData", taskscenario);
        startActivity(intent);
    }

    public void fitness(View v) {
        Intent intent = new Intent(this, FragmentViewer.class);
        intent.putExtra("intent","fitness");
        intent.putExtra("scenarioData", taskscenario);
        startActivity(intent);
    }


    public void pickScenario(View v) {
        Intent intent = new Intent(this, ScenarioPicker.class);
        startActivityForResult(intent, SCENARIO_NUM);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Check which request we're responding to
        if (requestCode != 0) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                int num = data.getIntExtra("scenarioNum", 1);
                // set the appropriate values in task scenario hash
                populateData(num);
            }
        }
    }

    private void populateData(int tasknum) {

        HashMap<String, String> generalData = new HashMap<String, String>();
        HashMap<String, String> dietData = new HashMap<String, String>();
        HashMap<String, String> fitnessData = new HashMap<String, String>();
        HashMap<String, String> nameAndScenario = new HashMap<String, String>();

        switch(tasknum) {
            /* Fitness - Robert */
            case 1:
                nameAndScenario.put("name", "Robert");
                nameAndScenario.put("scenarioName", "Fitness - Robert");

                generalData.put("heartrate", "114");
                generalData.put("height", "5 8");
                generalData.put("weight", "190");
                generalData.put("guthealth", "No Data");
                generalData.put("hydration", "Hydrated");

                currentScenarioBox.setText(R.string.fitness_robert_title);
                break;

            /* Fitness - Dave */
            case 2:
                nameAndScenario.put("name", "Dave");
                nameAndScenario.put("scenarioName", "Fitness - Dave");

                generalData.put("heartrate", "152");
                generalData.put("height", "5 10");
                generalData.put("weight", "190");
                generalData.put("guthealth", "Healthy");
                generalData.put("hydration", "Hydrated");

                currentScenarioBox.setText(R.string.fitness_dave_title);
                break;

            /* Diet - Riley */
            case 3:
                nameAndScenario.put("name", "Riley");
                nameAndScenario.put("scenarioName", "Diet - Riley");

                generalData.put("heartrate", "100");
                generalData.put("height", "5 4");
                generalData.put("weight", "150");
                generalData.put("guthealth", "Constipated");
                generalData.put("hydration", "Dehydrated");

                currentScenarioBox.setText(R.string.diet_riley_title);
                break;

            /* Diet - Vanessa */
            case 4:
                nameAndScenario.put("name", "Vanessa");
                nameAndScenario.put("scenarioName", "Diet - Vanessa");

                generalData.put("heartrate", "120");
                generalData.put("height", "5 9");
                generalData.put("weight", "120");
                generalData.put("guthealth", "Healthy");
                generalData.put("hydration", "Hydrated");

                currentScenarioBox.setText(R.string.diet_vanessa_title);
                break;

            /* General - George */
            case 5:
                nameAndScenario.put("name", "George");
                nameAndScenario.put("scenarioName", "General - George");

                generalData.put("heartrate", "152");
                generalData.put("height", "5 7");
                generalData.put("weight", "140");
                generalData.put("guthealth", "Healthy");
                generalData.put("hydration", "Dehydrated");

                currentScenarioBox.setText(R.string.general_george_title);
                break;

            /* General - Jimmy */
            case 6:
                nameAndScenario.put("name", "Jimmy");
                nameAndScenario.put("scenarioName", "General - Jimmy");

                generalData.put("heartrate", "180");
                generalData.put("height", "5 10");
                generalData.put("weight", "140");
                generalData.put("guthealth", "Constipated");
                generalData.put("hydration", "Dehydrated");

                currentScenarioBox.setText(R.string.general_jimmy_title);
                break;

            /* New User */
            case 7:
                nameAndScenario.put("name", "newuser");
                nameAndScenario.put("scenarioName", "newuser");

                generalData.put("heartrate", "please connect fitbit for data");
                generalData.put("height", "please input height");
                generalData.put("weight", "please input weight");
                generalData.put("guthealth", "no data");
                generalData.put("hydration", "no data");

                currentScenarioBox.setText(R.string.new_user_title);
                break;

        }

        taskscenario.put("general", generalData);
        taskscenario.put("metadata", nameAndScenario);

    }





}
