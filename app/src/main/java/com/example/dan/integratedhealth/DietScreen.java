package com.example.dan.integratedhealth;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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
    private int total_calories = 0;
    private LinkedHashMap<String,String> foods_calories = new LinkedHashMap<String, String>();
    private final static String FOODTEXT = "food.txt";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_screen);
        ProgressBar progress_bar = (ProgressBar) findViewById(R.id.diet_progress_bar);
        progress_bar.setProgress(90);

        foods = this.getResources().getStringArray(R.array.food_keys);
        calories = this.getResources().getStringArray(R.array.food_values);

//        foodfile = getResources().openRawResource(R.raw.food);

        //sets up the food and calories hashmap

        //foods_calories = new LinkedHashMap<String,String>();
        for (int i = 0; i < Math.min(foods.length, calories.length); i++){
            foods_calories.put(foods[i], calories[i]);
        }

        add_to_food_table("potato");
        //System.out.println(getFilesDir());

        sample_writes(this);

        String long_ass_string = read_from_file(this);

        interpret_food_file(long_ass_string);
        add_to_food_table("beans");


    }

    public void sample_writes(Context context) {
        empty_file(context);
        write_to_file("potato#90 calories|", context);
        write_to_file("beans#90 calories|", context);
        write_to_file("carrots#90 calories|", context);
        write_to_file("tomatoes#90 calories|", context);
    }


    public int calculate_progress(int starting_weight, int curr_weight, int goal_weight) {
        return ((starting_weight - curr_weight)/(starting_weight - goal_weight));
    }

    public void add_to_food_table(String food) {
        TableLayout food_table = (TableLayout) findViewById(R.id.food_table);
        food_table.addView(get_food_data(food));


        total_calories += strip_calories_string(get_calories(food));
        update_calories_table();
    }

    public int strip_calories_string(String calories) {
        String[] parts = calories.split(" ");
        return Integer.parseInt(parts[0]);
    }

    public void update_calories_table(){
        TextView calories_textview = (TextView) findViewById(R.id.total_calories);
        calories_textview.setText(String.valueOf(total_calories) + " calories");
    }

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
