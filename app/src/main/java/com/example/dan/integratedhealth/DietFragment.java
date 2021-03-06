package com.example.dan.integratedhealth;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.text.TextUtils;
import android.text.method.TransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.SCROLL_INDICATOR_RIGHT;


public class DietFragment extends DialogFragment implements View.OnClickListener{


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
    private View root_view;
    public static final int REQUEST_CODE_ADD_FOOD = 90;
    public static final int REQUEST_CODE_CUSTOM_MACROS = 91;
    public boolean warning = false;

    private int added_fats;
    private int added_proteins;
    private int added_carbs;
    private int added_sugars;
    private String added_food_name;
    private int added_calories;

    private int goal_proteins = 150;
    private int goal_fats = 50;
    private int goal_carbs = 300;
    private int goal_sugars = 20;
    private int goal_calories = 2000;

    private String diet_plan = "traditional";

    private HashMap<String,HashMap> scenarioData;
    private HashMap<String, String> meta;
    private HashMap<String, String> diet;
    private HashMap<String, String> general;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        //setContentView(R.layout.activity_diet_screen);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        scenarioData = new HashMap<>();
        if (getArguments()!=null) {
            scenarioData = (HashMap)getArguments().getSerializable("taskScenarioData");
            meta = scenarioData.get("metadata");
            diet = scenarioData.get("diet");
            general = scenarioData.get("general");
        }
        root_view=inflater.inflate(R.layout.fragment_diet,container,false);

        final Button starting_weight = (Button)root_view.findViewById(R.id.starting_weight);
        starting_weight.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(getContext());
                View dialogView = li.inflate(R.layout.set_weight_dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                // set title
                alertDialogBuilder.setTitle("Enter New Weight");
                // set custom dialog icon
                alertDialogBuilder.setIcon(android.R.drawable.ic_input_add);
                // set custom_dialog.xml to alertdialog builder
                alertDialogBuilder.setView(dialogView);
                final EditText userInputWeight = (EditText) dialogView.findViewById(R.id.weight_input);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        // get user input and set it to etOutput
                                        // edit text
                                        String newWeight= userInputWeight.getText().toString();
                                        starting_weight.setText("Starting: " + newWeight);
                                        diet.put("startingweight", newWeight);
                                        scenarioData.put("diet", diet);
                                        update_progress_bar();
                                        //System.out.println("these are the hopefully updated general values: " + general.values());
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }
        });



        final Button current_weight = (Button) root_view.findViewById(R.id.current_weight);

        current_weight.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(getContext());
                View dialogView = li.inflate(R.layout.set_weight_dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                // set title
                alertDialogBuilder.setTitle("Enter New Weight");
                // set custom dialog icon
                alertDialogBuilder.setIcon(android.R.drawable.ic_input_add);
                // set custom_dialog.xml to alertdialog builder
                alertDialogBuilder.setView(dialogView);
                final EditText userInputWeight = (EditText) dialogView.findViewById(R.id.weight_input);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        // get user input and set it to etOutput
                                        // edit text
                                        String newWeight= userInputWeight.getText().toString();
                                        current_weight.setText("Current: " + newWeight);
                                        general.put("weight", newWeight);
                                        scenarioData.put("general", general);
                                        update_progress_bar();
                                        //System.out.println("these are the hopefully updated general values: " + general.values());
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }
        });


        final Button goal_weight = (Button) root_view.findViewById(R.id.goal_weight);

        goal_weight.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                LayoutInflater li = LayoutInflater.from(getContext());
                View dialogView = li.inflate(R.layout.set_weight_dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                // set title
                alertDialogBuilder.setTitle("Enter New Weight");
                // set custom dialog icon
                alertDialogBuilder.setIcon(android.R.drawable.ic_input_add);
                // set custom_dialog.xml to alertdialog builder
                alertDialogBuilder.setView(dialogView);
                final EditText userInputWeight = (EditText) dialogView.findViewById(R.id.weight_input);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        // get user input and set it to etOutput
                                        // edit text
                                        String newWeight= userInputWeight.getText().toString();
                                        goal_weight.setText("Goal: " + newWeight);
                                        diet.put("goalweight", newWeight);
                                        scenarioData.put("diet", diet);
                                        update_progress_bar();
                                        //System.out.println("these are the hopefully updated general values: " + general.values());
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }
        });

        starting_weight.setText("Starting: " + diet.get("startingweight"));
        current_weight.setText("Current: "+ general.get("weight"));
        goal_weight.setText("Goal: " + diet.get("goalweight"));

        //add_food_view = inflater.inflate(R.layout.fragment_addfood, container, false);

        Button food_table_add_row = (Button) root_view.findViewById(R.id.food_table_add_row_button);
        food_table_add_row.setOnClickListener(this);

        Button food_table_del_row = (Button) root_view.findViewById(R.id.food_table_del_row_button);
        food_table_del_row.setOnClickListener(this);

        Button food_table_cancel_button = (Button) root_view.findViewById(R.id.food_table_cancel_button);
        food_table_cancel_button.setOnClickListener(this);

        Button traditional_button = (Button) root_view.findViewById(R.id.traditional_button);
        traditional_button.setOnClickListener(this);

        Button vegetarian_button = (Button) root_view.findViewById(R.id.vegetarian_button);
        vegetarian_button.setOnClickListener(this);

        Button atkins_button = (Button) root_view.findViewById(R.id.atkins_button);
        atkins_button.setOnClickListener(this);

        Button keto_button = (Button) root_view.findViewById(R.id.keto_button);
        keto_button.setOnClickListener(this);

        Button custom_button = (Button) root_view.findViewById(R.id.custom_button);
        custom_button.setOnClickListener(this);

        ProgressBar progress_bar = (ProgressBar) root_view.findViewById(R.id.diet_progress_bar);
        if (diet.get("startingweight") != null && diet.get("goalweight") != null && general.get("weight") != null && diet.get("startingweight").length() < 4 && diet.get("goalweight").length() < 4) {
            progress_bar.setProgress((int) calculate_progress(Double.parseDouble(diet.get("startingweight")), Double.parseDouble(general.get("weight")), Double.parseDouble(diet.get("goalweight"))));
            System.out.println("aaaa" + (int) calculate_progress(Double.parseDouble(diet.get("startingweight")), Double.parseDouble(general.get("weight")), Double.parseDouble(diet.get("goalweight"))));
        } else {
            progress_bar.setProgress(0);
        }

        foods = this.getResources().getStringArray(R.array.food_keys);
        calories = this.getResources().getStringArray(R.array.food_values);
        macronutrients = this.getResources().getStringArray(R.array.verbose_food_values);

        for (int i = 0; i < Math.min(foods.length, calories.length); i++){
            foods_calories.put(foods[i], calories[i]);
        }

        for (int k = 0; k < Math.min(foods.length, macronutrients.length); k++) {
            verbose_foods_calories.put(foods[k], macronutrients[k]);
        }

        mySwitch = (Switch) root_view.findViewById(R.id.verbose_switch);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TableLayout food_table = (TableLayout) root_view.findViewById(R.id.food_table);
                TextView verbose_textview = (TextView) root_view.findViewById(R.id.verbose_textview);
                if (isChecked) {
                    //Switch is on


                    for (int i = 0; i < food_table.getChildCount(); i++) {
                        View table_row = food_table.getChildAt(i);
                        if (table_row instanceof TableRow) {
                            TableRow food_table_row = (TableRow) table_row;
                            TextView food_item = (TextView) food_table_row.getChildAt(1);
                            TextView calories_view = (TextView) food_table_row.getChildAt(2);
                            if (calories_view != null) {
                                calories_view.setText(get_macronutrients(food_item.getText().toString()));
                                //calories_view.setEllipsize(TextUtils.TruncateAt.START);
                                //calories_view.setText(get_calories(food_item.getText().toString()));
                                //update_table_to_verbose();
                                calories_view.setTextSize(10);
                                update_calories_table();


                            }
                        }
                    }

                    verbose_textview.setText(R.string.verbose_label);
                } else {
                    //Switch is off
                    for (int i = 0; i < food_table.getChildCount(); i++) {
                        View table_row = food_table.getChildAt(i);
                        if (table_row instanceof TableRow) {
                            TableRow food_table_row = (TableRow) table_row;
                            TextView food_item = (TextView) food_table_row.getChildAt(1);
                            TextView calories_view = (TextView) food_table_row.getChildAt(2);
                            if (calories_view != null) {
                                calories_view.setText(get_calories(food_item.getText().toString()));
                                calories_view.setTextSize(15);
                                update_calories_table();
                            }

                        }
                    }
                    verbose_textview.setText(R.string.simple_label);

                }
            }

        });

        setup_recommendations();

        //add_to_food_table("potato");
        //sample_writes(getActivity().getApplicationContext());

        String long_ass_string = read_from_file(getActivity().getApplicationContext(), "food.txt");

        interpret_food_file(long_ass_string, false);
        //add_to_food_table("beans");

        //add_to_food_table("steak");
        //add_to_food_table("steak");
        //add_to_food_table("steak");
        //add_to_food_table("steak");

        startup();
        //System.out.println("after startup" + warning);

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("Diet Information for " + meta.get("name"));

        return root_view;
    }

    public void update_progress_bar(){
        ProgressBar progress_bar = (ProgressBar) root_view.findViewById(R.id.diet_progress_bar);
        if (diet.get("startingweight") != null && diet.get("goalweight") != null && general.get("weight") != null) {
            progress_bar.setProgress((int)calculate_progress(Double.parseDouble(diet.get("startingweight")), Double.parseDouble(general.get("weight")), Double.parseDouble(diet.get("goalweight"))));
            System.out.println("aaaa" + (int)calculate_progress(Double.parseDouble(diet.get("startingweight")), Double.parseDouble(general.get("weight")), Double.parseDouble(diet.get("goalweight"))));
        } else {
            progress_bar.setProgress(0);
        }
    }

    public void change_highlighted_button() {
        Button traditional_button = (Button) root_view.findViewById(R.id.traditional_button);
        Button vegetarian_button = (Button) root_view.findViewById(R.id.vegetarian_button);
        Button atkins_button = (Button) root_view.findViewById(R.id.atkins_button);
        Button keto_button = (Button) root_view.findViewById(R.id.keto_button);
        Button custom_button = (Button) root_view.findViewById(R.id.custom_button);
        TextView diet_description = (TextView) root_view.findViewById(R.id.diet_description);

        switch(diet_plan){
            case "traditional":
                traditional_button.setBackgroundColor(Color.CYAN);
                vegetarian_button.setBackgroundResource(android.R.drawable.btn_default);
                atkins_button.setBackgroundResource(android.R.drawable.btn_default);
                keto_button.setBackgroundResource(android.R.drawable.btn_default);
                custom_button.setBackgroundResource(android.R.drawable.btn_default);
                diet_description.setText("Traditional - Standard diet");
                break;
            case "vegetarian":
                traditional_button.setBackgroundResource(android.R.drawable.btn_default);
                vegetarian_button.setBackgroundColor(Color.CYAN);
                atkins_button.setBackgroundResource(android.R.drawable.btn_default);
                keto_button.setBackgroundResource(android.R.drawable.btn_default);
                custom_button.setBackgroundResource(android.R.drawable.btn_default);
                diet_description.setText("Vegetarian - Focus on vegetables!");
                break;
            case "atkins":
                traditional_button.setBackgroundResource(android.R.drawable.btn_default);
                vegetarian_button.setBackgroundResource(android.R.drawable.btn_default);
                atkins_button.setBackgroundColor(Color.CYAN);
                keto_button.setBackgroundResource(android.R.drawable.btn_default);
                custom_button.setBackgroundResource(android.R.drawable.btn_default);
                diet_description.setText("Atkins - Eat as many protein and fats, just avoid carbs!");
                diet_description.setTextSize(13);
                break;
            case "keto":
                traditional_button.setBackgroundResource(android.R.drawable.btn_default);
                vegetarian_button.setBackgroundResource(android.R.drawable.btn_default);
                atkins_button.setBackgroundResource(android.R.drawable.btn_default);
                keto_button.setBackgroundColor(Color.CYAN);
                custom_button.setBackgroundResource(android.R.drawable.btn_default);
                diet_description.setText("Keto - Low carbs, high fat, avoid excessive protein!");
                break;
            case "custom":
                traditional_button.setBackgroundResource(android.R.drawable.btn_default);
                vegetarian_button.setBackgroundResource(android.R.drawable.btn_default);
                atkins_button.setBackgroundResource(android.R.drawable.btn_default);
                keto_button.setBackgroundResource(android.R.drawable.btn_default);
                custom_button.setBackgroundColor(Color.CYAN);
                diet_description.setText("Custom - whatever you like!");
                break;
        }
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.traditional_button:
                Button traditional_button = (Button) root_view.findViewById(R.id.traditional_button);
                traditional_button.setBackgroundColor(Color.CYAN);
                goal_carbs = 300;
                goal_fats = 50;
                goal_proteins = 150;
                goal_sugars = 20;
                diet_plan = "traditional";
                change_highlighted_button();
                setup_recommendations();
                update_macronutrients();
                add_goal_macros_to_file();
                break;

            case R.id.vegetarian_button:
                goal_carbs = 400;
                goal_fats = 20;
                goal_proteins = 100;
                goal_sugars = 20;
                diet_plan = "vegetarian";
                change_highlighted_button();
                setup_recommendations();
                update_macronutrients();
                add_goal_macros_to_file();
                break;

            case R.id.atkins_button:
                goal_carbs = 200;
                goal_fats = 50;
                goal_proteins = 300;
                goal_sugars = 200;
                diet_plan = "atkins";
                change_highlighted_button();
                setup_recommendations();
                update_macronutrients();
                add_goal_macros_to_file();
                break;

            case R.id.keto_button:
                goal_carbs = 300;
                goal_fats = 10;
                goal_proteins = 100;
                goal_sugars = 50;
                diet_plan = "keto";
                change_highlighted_button();
                setup_recommendations();
                update_macronutrients();
                add_goal_macros_to_file();
                break;

            case R.id.custom_button:
                diet_plan = "custom";
                change_highlighted_button();
                setup_recommendations();
                Intent custom_macros = new Intent(getActivity().getApplicationContext(), CustomMacrosActivity.class);
                startActivityForResult(custom_macros, REQUEST_CODE_CUSTOM_MACROS);
                break;


            case R.id.food_table_cancel_button:
                TableLayout food_table = (TableLayout) root_view.findViewById(R.id.food_table);
                for (int index = 0; index < food_table.getChildCount(); index++) {
                    View table_row = food_table.getChildAt(index);
                    if (table_row instanceof TableRow) {
                        TableRow food_table_row = (TableRow) table_row;
                        Button remove_button = (Button) food_table_row.getChildAt(0);
                        remove_button.setVisibility(View.GONE);
                        //System.out.println("hello?");
                    }
                }

                Button food_table_cancel_button = (Button) root_view.findViewById(R.id.food_table_cancel_button);
                food_table_cancel_button.setVisibility(View.GONE);
                break;
            case R.id.food_table_del_row_button:
                TableLayout del_food_table = (TableLayout) root_view.findViewById(R.id.food_table);
                for (int index = 0; index < del_food_table.getChildCount(); index++) {
                    View table_row = del_food_table.getChildAt(index);
                    if (table_row instanceof TableRow) {
                        TableRow food_table_row = (TableRow) table_row;
                        Button remove_button = (Button) food_table_row.getChildAt(0);
                        remove_button.setVisibility(View.VISIBLE);
                    }
                }
                Button cancel_button = (Button) root_view.findViewById(R.id.food_table_cancel_button);
                cancel_button.setVisibility(View.VISIBLE);
                break;
            case R.id.food_table_add_row_button:

                Intent add_food = new Intent(getActivity().getApplicationContext(), AddFoodFragment.class);
                startActivityForResult(add_food, REQUEST_CODE_ADD_FOOD);
                break;
        }

    }

    public void setup_recommendations(){
        TableLayout recommendations_table = (TableLayout) root_view.findViewById(R.id.recommendations);
        String[] recipes;

        if (recommendations_table.getChildCount() > 0) {
            recommendations_table.removeAllViews();
        }

        switch(diet_plan){
            case "traditional":
                recipes = this.getResources().getStringArray(R.array.food_traditional);
                break;
            case "vegetarian":
                recipes = this.getResources().getStringArray(R.array.food_vegetarian);
                break;
            case "atkins":
                recipes = this.getResources().getStringArray(R.array.food_atkins);
                break;
            case "keto":
                recipes = this.getResources().getStringArray(R.array.food_keto);
                break;
            case "custom":
                recipes = this.getResources().getStringArray(R.array.food_traditional);
                break;
            default:
                recipes = this.getResources().getStringArray(R.array.food_traditional);
                break;
        }



        for (int index = 0; index < recipes.length; index++) {
            final String curr_food = recipes[index];
            TableRow new_row = new TableRow(getActivity());
            TextView new_food_suggestion = new TextView(getActivity());
            Button add_food_suggestion = new Button(getActivity());
            add_food_suggestion.setBackgroundResource(android.R.drawable.btn_default);
            add_food_suggestion.setGravity(Gravity.RIGHT);
            add_food_suggestion.setText("Add Me!");
            //add_food_suggestion.setBackgroundResource(android.R.drawable.ic_menu_add);
            add_food_suggestion.setTextSize(11);
            add_food_suggestion.setLayoutParams(new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f));
            add_food_suggestion.setOnClickListener(new View.OnClickListener(){

                public void onClick(View v) {
                    add_food(curr_food, 200, 200, 200, 200, 200, "food.txt");
                    interpret_food_file(read_from_file(getActivity().getApplicationContext(), "food.txt"), false);
                    add_to_food_table(curr_food);
                    add_food(curr_food, 200, 200, 200, 200, 200, meta.get("name") + ".txt");
                }
            });

            Button view_recipe_suggestion = new Button(getActivity());
            view_recipe_suggestion.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    Intent recipe_intent = new Intent(getActivity().getApplicationContext(), RecipeActivity.class);
                    startActivity(recipe_intent);
                }
            });

            view_recipe_suggestion.setBackgroundResource(android.R.drawable.btn_default);
            view_recipe_suggestion.setGravity(Gravity.RIGHT);
            view_recipe_suggestion.setLayoutParams(new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT,1f));
            view_recipe_suggestion.setText("Recipe");
            //view_recipe_suggestion.setBackgroundResource(android.R.drawable.ic_dialog_info);
            view_recipe_suggestion.setTextSize(11);
            new_food_suggestion.setText(curr_food);
            new_food_suggestion.setLayoutParams(new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT,1f));
            new_row.addView(new_food_suggestion);
            new_row.addView(add_food_suggestion);
            new_row.addView(view_recipe_suggestion);
            recommendations_table.addView(new_row);
        }

    }



    public String get_goal_macros() {
        return goal_carbs + " " + goal_fats + " " + goal_proteins + " " + goal_sugars + " " +warning + " " + diet_plan;
    }

    public void startup() {
        //interpret_food_file(read_from_file(getActivity().getApplicationContext(), "food.txt"));
        //add_to_food_table(added_food_name);

        interpret_food_file(read_from_file(getActivity().getApplicationContext(), meta.get("name") + ".txt"), true);
        interpret_goal_macros_file(read_from_file(getActivity().getApplicationContext(), meta.get("name") + "2.txt"));
        update_macronutrients();
        setup_recommendations();
        change_highlighted_button();


    }

    private void interpret_goal_macros_file(String string) {
        String[] macros = string.split(" ");
        //System.out.println(string);
        if (macros.length > 1) {
            if (!macros[0].equals("")) {
                goal_carbs = Integer.parseInt(macros[0]);
            } else {
                goal_carbs = 0;
            }

            if (!macros[1].equals("")) {
                goal_fats = Integer.parseInt(macros[1]);
            } else {
                goal_fats = 0;
            }

            if (!macros[2].equals("")) {
                goal_proteins = Integer.parseInt(macros[2]);
            } else {
                goal_proteins = 0;
            }

            if (!macros[3].equals("")) {
                goal_sugars = Integer.parseInt(macros[3]);
            } else {
                goal_sugars = 0;
            }
        }


        //System.out.println("VALUE OF WARNING"+String.valueOf(warning));

        if (macros.length > 4) {
            warning = Boolean.valueOf(macros[4]);
        }

        if (macros.length > 5) {
            diet_plan = macros[5];
        }
    }

    public void update_macronutrients() {
        update_carbs_textview();
        update_fats_textview();
        update_proteins_textview();
        update_sugars_textview();
    }

    public void add_goal_macros_to_file(){
        empty_file(getActivity().getApplicationContext(), meta.get("name") + "2.txt");
        //System.out.println("ADDED GOAL MACROS" + get_goal_macros());
        write_to_file(get_goal_macros(), getActivity().getApplicationContext(), meta.get("name") + "2.txt");
    }

    public void sample_writes(Context context) {
        empty_file(context, meta.get("name") + ".txt");
        write_to_file("potato#90 calories#90 calories, 15g carbs, 10g fat, 5g protein, 3g sugar|", context, "food.txt");
        write_to_file("beans#100 calories#100 calories, 30g carbs, 15g fat, 5g protein, 1g sugar|", context, "food.txt");
        write_to_file("carrots#90 calories#80 calories, 20g carbs, 10g fat, 5g protein, 2g sugar|", context, "food.txt");
        write_to_file("tomatoes#90 calories#60 calories, 10g carbs, 5g fat, 3g protein, 3g sugar|", context, "food.txt");
    }


    public double calculate_progress(double starting_weight, double curr_weight, double goal_weight) {
        return (Math.abs(starting_weight - curr_weight)/Math.abs(starting_weight - goal_weight))*100;
    }

    //adds the food to the food table
    public void add_to_food_table(String food) {
        TableLayout food_table = (TableLayout) root_view.findViewById(R.id.food_table);
        food_table.addView(get_food_data(food));


        total_calories += strip_calories_string(get_calories(food));
        update_calories_table();

        total_carbs += strip_grams(get_carbs(food));
        total_fats += strip_grams(get_fats(food));
        total_proteins += strip_grams(get_proteins(food));
        total_sugars += strip_grams(get_sugars(food));

        update_macronutrients();

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

    public void update_proteins_textview(){
        TextView protein_textview = (TextView) root_view.findViewById(R.id.protein_textview);
        protein_textview.setText(String.valueOf((int)total_proteins) + "/" + String.valueOf(goal_proteins) + "g protein");

    }
    public void update_fats_textview(){
        TextView fats_textview = (TextView) root_view.findViewById(R.id.fats_textview);
        fats_textview.setText(String.valueOf((int)total_fats) + "/" + String.valueOf(goal_fats) + "g fats");

    }
    public void update_carbs_textview(){
        TextView carbs_textview = (TextView) root_view.findViewById(R.id.carbs_textview);
        carbs_textview.setText(String.valueOf((int)total_carbs) + "/" + String.valueOf(goal_carbs) + "g carbs");

    }
    public void update_sugars_textview(){
        TextView sugars_textview = (TextView) root_view.findViewById(R.id.sugars_textview);
        sugars_textview.setText(String.valueOf((int)total_sugars) + "/" + String.valueOf(goal_sugars) + "g sugars");

    }

    public void show_toasts(){

        //System.out.println("INSIDE TOAST" + warning + " " + !warning);
        //System.out.println((total_carbs > goal_carbs) && (!warning));

        if ((total_proteins > goal_proteins) && (!warning)) {
            Toast toast = Toast.makeText(getActivity(), "Be careful, one of your macronutrients is a bit high!" , Toast.LENGTH_LONG);
            toast.show();
            warning = true;
            add_goal_macros_to_file();
        } else if ((total_fats > goal_fats) && (!warning)) {
            Toast toast = Toast.makeText(getActivity(), "Be careful, one of your macronutrients is a bit high!" , Toast.LENGTH_LONG);
            toast.show();
            warning = true;
            add_goal_macros_to_file();
        } else if ((total_carbs > goal_carbs) && (!warning)) {
            Toast toast = Toast.makeText(getActivity(), "Be careful, one of your macronutrients is a bit high!" , Toast.LENGTH_LONG);
            toast.show();
            warning = true;
            add_goal_macros_to_file();
        } else if ((total_sugars > goal_sugars) && (!warning)) {
            Toast toast = Toast.makeText(getActivity(), "Be careful, one of your macronutrients is a bit high!" , Toast.LENGTH_LONG);
            toast.show();
            warning = true;
            add_goal_macros_to_file();
        } else if ((total_calories > goal_calories) && (!warning)) {
            Toast toast = Toast.makeText(getActivity(), "Be careful, your calories are a little high!" , Toast.LENGTH_LONG);
            toast.show();
            warning = true;
            add_goal_macros_to_file();
        }

    }

    //increases the total calories count
    public void update_calories_table(){
        TextView calories_textview = (TextView) root_view.findViewById(R.id.total_calories);
        calories_textview.setText(String.valueOf(total_calories) + "/" + String.valueOf(goal_calories) + " calories");
    }

    public String total_macronutrients_string() {
        return total_carbs + "g carbs" + ", " + total_fats + "g fats" + ", " + total_proteins + "g proteins" + ", " + total_sugars + "g sugars";
    }

    public void update_table_to_verbose(){
        TextView calories_textview = (TextView) root_view.findViewById(R.id.total_calories);
        //calories_textview.setText(total_macronutrients_string());
        calories_textview.setText(String.valueOf(total_calories) + " calories");
    }


    //creates the table row for the food table that will show the food's name and food's calories
    //potato        90 cals
    public TableRow get_food_data(String food) {

        final TableRow new_row = new TableRow(getActivity());

        Button remove_button = new Button(getActivity());
        remove_button.setVisibility(View.GONE);
        remove_button.setBackgroundResource(android.R.drawable.ic_menu_close_clear_cancel);
        remove_button.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        //remove button gone means it is really gone
                        //if (remove_button.getVisibility() != View.GONE) {
                            TableLayout food_table = (TableLayout) root_view.findViewById(R.id.food_table);
                            for (int index = 0; index < food_table.getChildCount(); index++) {
                                View table_row = food_table.getChildAt(index);
                                if (table_row == new_row) {
                                    TableRow food_table_row = (TableRow) table_row;
                                    TextView food_name = (TextView) food_table_row.getChildAt(1);
                                    remove_food(food_name.getText().toString());
                                }
                            }
                            //food_table.removeView(new_row);
                        //}
                    }
                });

        TextView new_food = new TextView(getActivity());
        TextView new_food_calories = new TextView(getActivity());
        new_food.setLayoutParams(new TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f));
        new_food_calories.setGravity(Gravity.RIGHT);
        //the new LayoutParams needs to be set to the PARENT. in this case, needs to be set to tablerow
        new_food_calories.setLayoutParams(new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.5f));
        new_food.setText(food);
        new_food.setTextSize(15);
        new_food_calories.setText(get_calories(food));
        new_food_calories.setTextSize(15);
        //System.out.println(get_calories(food));
        new_row.addView(remove_button);
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

    public double get_proteins_double(String food) {
        String macros = get_macronutrients(food);
        //System.out.println("--->" + macros);
        String[] macros_array = macros.split(",");
        return Double.parseDouble(macros_array[3].trim().split(" ")[0].replace("g", ""));
    }

    public double get_carbs_double(String food) {
        String macros = get_macronutrients(food);
        String[] macros_array = macros.split(",");
        return Double.parseDouble(macros_array[1].trim().split(" ")[0].replace("g", ""));
    }

    public double get_fats_double(String food) {
        String macros = get_macronutrients(food);
        String[] macros_array = macros.split(",");
        return Double.parseDouble(macros_array[2].trim().split(" ")[0].replace("g", ""));
    }

    public double get_sugars_double(String food) {
        String macros = get_macronutrients(food);
        String[] macros_array = macros.split(",");
        return Double.parseDouble(macros_array[4].trim().split(" ")[0].replace("g", ""));
    }


    public void add_food(String food, int calories, int fat, int protein, int carb, int sugar, String filename) {
        //write_to_file("potato#90 calories#90 calories, 15g carbs, 10g fat, 5g protein, 3g sugar|", context);
        write_to_file(food + "#" + calories + " calories#"  + calories + " calories," + carb +"g carbs, " + fat + "g fat, " + protein + "g protein, " + sugar + "g sugar|", getActivity().getApplicationContext(), filename);
        //System.out.println("what is the value of warning:" + warning);
        show_toasts();
    }

    public void remove_food(String food) {



        TableLayout food_table = (TableLayout) root_view.findViewById(R.id.food_table);
        for (int index = 0; index < food_table.getChildCount(); index++) {
            View table_row = food_table.getChildAt(index);
            if (table_row instanceof TableRow) {
                TableRow food_table_row = (TableRow) table_row;
                TextView food_item = (TextView) food_table_row.getChildAt(1);
                //TextView calories_row = (TextView) food_table.getChildAt(1);
                if (food_item.getText().equals(food)) {

                    mySwitch = (Switch) root_view.findViewById(R.id.verbose_switch);
                    //switch is on


                    total_proteins -= get_proteins_double(food);
                    total_carbs -= get_carbs_double(food);
                    total_sugars -= get_sugars_double(food);
                    total_fats -= get_fats_double(food);
                    total_calories -= strip_calories_string(get_calories(food));

                    if (total_proteins < goal_proteins && total_carbs < goal_carbs && total_sugars < goal_sugars && total_fats < goal_fats) {
                        warning = false;
                        add_goal_macros_to_file();
                    }

                    update_macronutrients();

                    if (mySwitch.isChecked()) {
                        update_table_to_verbose();
                    } else {
                        update_calories_table();
                    }

                    remove_from_file((food + "#" + get_calories(food) + "#"  + get_calories(food) + "," + get_carbs(food) +" carbs, " + get_fats(food) + " fat, " + get_proteins(food) + " protein, " + get_sugars(food) + " sugar|"), getActivity().getApplicationContext(), meta.get("name") + ".txt");

                    //System.out.println(food + "#" + get_calories(food) + "#"  + get_calories(food) + "," + get_carbs(food) +" carbs, " + get_fats(food) + " fat, " + get_proteins(food) + " protein, " + get_sugars(food) + " sugar|");
                    food_table.removeView(food_table_row);

                    //System.out.println(read_from_file(getActivity().getApplicationContext(), meta.get("name") + ".txt"));

                    // write_to_file(food + "#" + calories + " calories#"  + calories + " calories," + carb +"g carbs, " + fat + "g fat, " + protein + "g protein, " + sugar + "g sugar|",


                    break;
                }
            }
        }

    }

    private void interpret_food_file(String string, boolean add) {

        //this string will be a giant string, but "|" divides the foods from each other
        String[] parts = string.split("[|]");

        for (String a: parts) {
            //now that the string is split into food calorie pairs, need to split again
            String[] second_parts = a.split("[#]");
            //food = second_parts[0], calories = second_parts[1]
            if (second_parts.length > 1) {
                foods_calories.put(second_parts[0], second_parts[1]);

                //second_parts[2] is the macronutrients, so needs a null check to make sure
                if (second_parts[2] != null) {
                    verbose_foods_calories.put(second_parts[0], second_parts[2]);
                }

                if (add) {
                    add_to_food_table(second_parts[0]);
                }
            }
        }

    }

    private void write_to_file(String data,Context context, String filename) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_APPEND));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
        }
    }

    private void remove_from_file(String delete, Context context, String filename) {
        String charset = "UTF-8";
        String temp = "temp.txt";
        List<String> lines = new LinkedList<String>();

        try {
            InputStream inputStream = context.openFileInput(filename);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    receiveString = receiveString.replace(delete,"");
                    lines.add(receiveString);
                }

                inputStream.close();
            }


            //System.out.println("lololol-->"+ delete);


            empty_file(getActivity().getApplicationContext(), meta.get("name") + ".txt");

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_APPEND));


            for (String line: lines) {
                outputStreamWriter.write(line);
                //System.out.println(line);
            }
            outputStreamWriter.close();
            //writer.close();


        } catch (FileNotFoundException e){

            System.out.println("This file is not found ->" + filename);

        } catch (UnsupportedEncodingException e) {

            System.out.println("unsupport encoding");

        } catch (IOException e) {
            System.out.println("io exception");
        }

    }


    private String read_from_file(Context context, String filename) {

        //System.out.println(filename);

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(filename);

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
            System.out.println("This file missin ->" + filename);
        } catch (IOException e) {
            //Log.e("login activity", "Can not read file: " + e.toString());
        }
        //System.out.println("GOT THIS FROM FILE:" + ret);
        return ret;
    }

    private void empty_file(Context context, String filename) {
        try {
            OutputStreamWriter emptying = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            emptying.write("");
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_FOOD) {
            if (resultCode == Activity.RESULT_OK) {
                added_fats = Integer.parseInt(data.getCharSequenceExtra("FATS").toString());
                added_proteins = Integer.parseInt(data.getCharSequenceExtra("PROTEINS").toString());
                added_carbs = Integer.parseInt(data.getCharSequenceExtra("CARBS").toString());
                added_sugars = Integer.parseInt(data.getCharSequenceExtra("SUGARS").toString());
                added_calories = Integer.parseInt(data.getCharSequenceExtra("CALORIES").toString());
                added_food_name = data.getCharSequenceExtra("FOOD_NAME").toString();
                add_food(added_food_name, added_calories, added_fats, added_proteins, added_carbs, added_sugars, "food.txt");
                interpret_food_file(read_from_file(getActivity().getApplicationContext(), "food.txt"), false);
                add_to_food_table(added_food_name);
                add_food(added_food_name, added_calories, added_fats, added_proteins, added_carbs, added_sugars, meta.get("name") + ".txt");

                added_fats = 0;
                added_proteins = 0;
                added_carbs = 0;
                added_sugars = 0;
                added_calories = 0;
                added_food_name = "";
            }
        }
        if (requestCode == REQUEST_CODE_CUSTOM_MACROS) {
            if (resultCode == Activity.RESULT_OK) {
                goal_proteins = Integer.parseInt(data.getCharSequenceExtra("PROTEINS").toString());
                goal_fats = Integer.parseInt(data.getCharSequenceExtra("FATS").toString());
                goal_sugars = Integer.parseInt(data.getCharSequenceExtra("SUGARS").toString());
                goal_carbs = Integer.parseInt(data.getCharSequenceExtra("CARBS").toString());
                update_macronutrients();
                add_goal_macros_to_file();
            }
        }
    }

}
