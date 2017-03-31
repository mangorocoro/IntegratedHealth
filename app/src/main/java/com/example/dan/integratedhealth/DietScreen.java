package com.example.dan.integratedhealth;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class DietScreen extends AppCompatActivity{

    private String[] foods;
    private String[] calories;
    private String[] macronutrients;
    private int total_calories = 0;
    private double total_proteins = 0;
    private double total_fats = 0;
    private double total_carbs = 0;
    private double total_sugars = 0;
    private LinkedHashMap<String,String> foods_calories = new LinkedHashMap<String, String>();
    private LinkedHashMap<String, String> verbose_foods_calories = new LinkedHashMap<String,String>();
    private final static String FOODTEXT = "food.txt";
    private Switch mySwitch;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_screen);
        ProgressBar progress_bar = (ProgressBar) findViewById(R.id.diet_progress_bar);
        progress_bar.setProgress(90);

        foods = this.getResources().getStringArray(R.array.food_keys);
        calories = this.getResources().getStringArray(R.array.food_values);
        macronutrients = this.getResources().getStringArray(R.array.verbose_food_values);

        for (int i = 0; i < Math.min(foods.length, calories.length); i++){
            foods_calories.put(foods[i], calories[i]);
        }

        for (int k = 0; k < Math.min(foods.length, macronutrients.length); k++) {
            verbose_foods_calories.put(foods[k], macronutrients[k]);
        }

        mySwitch = (Switch) findViewById(R.id.verbose_switch);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TableLayout food_table = (TableLayout) findViewById(R.id.food_table);
                if (isChecked) {
                    //Switch is on


                    for (int i = 0; i < food_table.getChildCount(); i++) {
                        View table_row = food_table.getChildAt(i);
                        if (table_row instanceof TableRow) {
                            TableRow food_table_row = (TableRow) table_row;
                            TextView food_item = (TextView) food_table_row.getChildAt(0);
                            TextView calories_view = (TextView) food_table_row.getChildAt(1);
                            calories_view.setText(get_macronutrients(food_item.getText().toString()));
                            update_table_to_verbose();
                        }
                    }
                } else {
                    //Switch is off
                    for (int i = 0; i < food_table.getChildCount(); i++) {
                        View table_row = food_table.getChildAt(i);
                        if (table_row instanceof TableRow) {
                            TableRow food_table_row = (TableRow) table_row;
                            TextView food_item = (TextView) food_table_row.getChildAt(0);
                            TextView calories_view = (TextView) food_table_row.getChildAt(1);
                            calories_view.setText(get_calories(food_item.getText().toString()));
                            update_calories_table();
                        }
                    }

                }
            }

        });



        add_to_food_table("potato");
        sample_writes(this);

        String long_ass_string = read_from_file(this);

        interpret_food_file(long_ass_string);
        add_to_food_table("beans");

        System.out.println(get_proteins("beans"));


    }

    public void sample_writes(Context context) {
        empty_file(context);
        write_to_file("potato#90 calories#90 calories, 15g carbs, 10g fat, 5g protein, 3g sugar|", context);
        write_to_file("beans#100 calories#100 calories, 30g carbs, 15g fat, 5g protein, 1g sugar|", context);
        write_to_file("carrots#90 calories#80 calories, 20g carbs, 10g fat, 5g protein, 2g sugar|", context);
        write_to_file("tomatoes#90 calories#60 calories, 10g carbs, 5g fat, 3g protein, 3g sugar|", context);
    }


    public int calculate_progress(int starting_weight, int curr_weight, int goal_weight) {
        return ((starting_weight - curr_weight)/(starting_weight - goal_weight));
    }

    //adds the food to the food table
    public void add_to_food_table(String food) {
        TableLayout food_table = (TableLayout) findViewById(R.id.food_table);
        food_table.addView(get_food_data(food));


        total_calories += strip_calories_string(get_calories(food));
        update_calories_table();

        total_carbs += strip_grams(get_carbs(food));
        total_fats += strip_grams(get_fats(food));
        total_proteins += strip_grams(get_proteins(food));
        total_sugars += strip_grams(get_sugars(food));

    }

    //removes the calories portion from the number of calories
    //90 calories -> 90
    public int strip_calories_string(String calories) {
        String[] parts = calories.split(" ");
        return Integer.parseInt(parts[0]);
    }

    public double strip_grams(String macronutrient) {
        String[] parts = macronutrient.split("g");
        return Integer.parseInt(parts[0]);
    }

    //increases the total calories count
    public void update_calories_table(){
        TextView calories_textview = (TextView) findViewById(R.id.total_calories);
        calories_textview.setText(String.valueOf(total_calories) + " calories");
    }

    public String total_macronutrients_string() {
        return total_carbs + "g carbs" + ", " + total_fats + "g fats" + ", " + total_proteins + "g proteins" + ", " + total_sugars + "g sugars";
    }

    public void update_table_to_verbose(){
        TextView calories_textview = (TextView) findViewById(R.id.total_calories);
        calories_textview.setText(total_macronutrients_string());

    }

//    public void update_total_table(){
//        TextView calories_textview = (TextView) findViewById(R.id.total_calories);
//    }

    //creates the table row for the food table that will show the food's name and food's calories
    //potato        90 cals
    public TableRow get_food_data(String food) {

        TableRow new_row = new TableRow(this);

        TextView new_food = new TextView(this);
        TextView new_food_calories = new TextView(this);
        new_food_calories.setGravity(Gravity.RIGHT);

        new_food.setText(food);
        new_food_calories.setText(get_calories(food));
        //System.out.println(get_calories(food));
        new_row.addView(new_food);
        new_row.addView(new_food_calories);

        return new_row;
    }

    public String get_calories(String food) {

        return foods_calories.get(food);

    }

    public String get_macronutrients(String food) {
        return verbose_foods_calories.get(food);
    }

    public String get_proteins(String food) {
        String macros = get_macronutrients(food);
        String[] macros_array = macros.split(",");
        String protein = macros_array[3].trim().split(" ")[0];
        return protein;
    }

    public String get_carbs(String food) {
        String macros = get_macronutrients(food);
        String[] macros_array = macros.split(",");
        String carbs = macros_array[1].trim().split(" ")[0];
        return carbs;
    }

    public String get_fats(String food) {
        String macros = get_macronutrients(food);
        String[] macros_array = macros.split(",");
        String fats = macros_array[2].trim().split(" ")[0];
        return fats;
    }

    public String get_sugars(String food) {
        String macros = get_macronutrients(food);
        String[] macros_array = macros.split(",");
        String sugars = macros_array[4].trim().split(" ")[0];
        return sugars;
    }

    public void add_food(String food, int calories) {
        write_to_file(food + "#" + calories + "|", this);
    }

    public void remove_food(String food) {
        foods_calories.remove(food);
    }

    private void interpret_food_file(String string) {

        //this string will be a giant string, but "|" divides the foods from each other
        String[] parts = string.split("[|]");

        for (String a: parts) {
            //now that the string is split into food calorie pairs, need to split again
            String[] second_parts = a.split("[#]");
            //food = second_parts[0], calories = second_parts[1]
            foods_calories.put(second_parts[0], second_parts[1]);

            //second_parts[2] is the macronutrients, so needs a null check to make sure
            if (second_parts[2] != null) {
                verbose_foods_calories.put(second_parts[0], second_parts[2]);
            }
        }

    }

    private void write_to_file(String data,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(FOODTEXT, Context.MODE_APPEND));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
        }
    }

    private String read_from_file(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("food.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            //Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            //Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    private void empty_file(Context context) {
        try {
            OutputStreamWriter emptying = new OutputStreamWriter(context.openFileOutput("food.txt", Context.MODE_PRIVATE));
            emptying.write("");
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
    }



}
