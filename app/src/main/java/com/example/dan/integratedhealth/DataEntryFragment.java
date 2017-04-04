package com.example.dan.integratedhealth;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.R.id.list;

/**
 * Created by twinniss on 4/1/2017.
 */

public class DataEntryFragment extends Fragment {

    private View view;
    ImageButton wght_plus;
    ImageButton wght_minus;
    ImageButton rep_plus;
    ImageButton rep_minus;
    Button save;
    Button clear;
    Button update;
    Button delete;
    float number_weight;
    float number_rep;
    EditText wght_txt;
    EditText rep_txt;
    TableLayout tableLayout;
    int selected;
    ArrayList<String> results = new ArrayList<String>();
    HashMap<Float, Float> weight_to_rep_map = new HashMap<Float, Float>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_data, container, false);
        wght_txt = (EditText) view.findViewById(R.id.Weight);
        rep_txt = (EditText) view.findViewById(R.id.Reps);

        wght_minus = (ImageButton)  view.findViewById(R.id.weight_minus);
        wght_minus.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String temp = wght_txt.getText().toString().trim();
                if(temp.isEmpty()){
                    number_weight = 0;
                } else {
                    number_weight = Float.parseFloat(wght_txt.getText().toString());
                }
                number_weight-=5;
                wght_txt.setText(String.valueOf(number_weight));
            }
        });

        wght_plus =  (ImageButton) view.findViewById(R.id.weight_plus);
        wght_plus.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String temp = wght_txt.getText().toString().trim();
                if(temp.isEmpty()){
                    number_weight = 0;
                } else {
                    number_weight = Float.parseFloat(wght_txt.getText().toString());
                }
                number_weight+=5;
                wght_txt.setText(String.valueOf(number_weight));
            }
        });

        rep_minus = (ImageButton)  view.findViewById(R.id.rep_minus);
        rep_minus.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String temp = rep_txt.getText().toString().trim();
                if(temp.isEmpty()){
                    number_rep = 0;
                } else {
                    number_rep = Float.parseFloat(rep_txt.getText().toString());
                }
                number_rep--;
                rep_txt.setText(String.valueOf(number_rep));
            }
        });

        rep_plus = (ImageButton)  view.findViewById(R.id.rep_plus);
        rep_plus.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String temp = rep_txt.getText().toString().trim();
                if(temp.isEmpty()){
                    number_rep = 0;
                } else {
                    number_rep = Float.parseFloat(rep_txt.getText().toString());
                }
                number_rep++;
                rep_txt.setText(String.valueOf(number_rep));
            }
        });

        clear = (Button) view.findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if(clear.getText().equals("Delete")){
                    //change to delete function
                    CharSequence text = "Entry Deleted";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(getContext(), text, duration);
                    toast.show();
                    final ArrayAdapter<String> adapter;
                    adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, results);
                    final ListView l = (ListView) view.findViewById(R.id.list_results);
                    results.remove(selected);
                    adapter.notifyDataSetChanged();

                    save.setText("Save");
                    clear.setText("Clear");
                    l.setAdapter(adapter);
                } else{
                    wght_txt.setText("");
                    rep_txt.setText("");
                }
            }
        });

        save = (Button) view.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v) {
               String wght_temp = wght_txt.getText().toString().trim();
               String rep_temp = rep_txt.getText().toString().trim();


               if (wght_temp.isEmpty() && rep_temp.isEmpty()) {
                   CharSequence text = "Please enter a weight or number of reps";
                   int duration = Toast.LENGTH_LONG;
                   Toast toast = Toast.makeText(getContext(), text, duration);
                   toast.show();
               } else {
                   if(save.getText().equals("Update")){
                        //change to update function
                       CharSequence text = "Updated";
                       int duration = Toast.LENGTH_LONG;
                       Toast toast = Toast.makeText(getContext(), text, duration);
                       toast.show();

                       float weight_key = Float.parseFloat(wght_temp);
                       float rep_value = Float.parseFloat(rep_temp);
                       String entry = wght_temp + " lbs                         ";
                       entry += rep_temp + " reps";

                       final ArrayAdapter<String> adapter;
                       adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, results);
                       final ListView l = (ListView) view.findViewById(R.id.list_results);
                       results.set(selected, entry);
                       TextView x = (TextView) l.getChildAt(selected);
                       x.setText(entry);
                       adapter.notifyDataSetChanged();

                       save.setText("Save");
                       clear.setText("Clear");
                       l.setAdapter(adapter);

                   } else {
                       CharSequence text = "Saved";
                       int duration = Toast.LENGTH_LONG;
                       Toast toast = Toast.makeText(getContext(), text, duration);
                       toast.show();

                       final ArrayAdapter<String> adapter;

                       float weight_key = Float.parseFloat(wght_temp);
                       float rep_value = Float.parseFloat(rep_temp);
                       String entry = wght_temp + " lbs                         ";
                       entry += rep_temp + " reps";
                       results.add(entry);
                       adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, results);
                       final ListView listView = (ListView) view.findViewById(R.id.list_results);

                       listView.setAdapter(adapter);
                       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                           @Override
                           public void onItemClick(AdapterView<?> parent, View v1, int position, long id) {
                               TextView w = (TextView) listView.getChildAt(position);
                               Matcher m = Pattern.compile("\\d+\\.\\d+").matcher(w.getText().toString());
                               int counter = 0;
                               while(m.find()){
                                   if(counter == 0){
                                       wght_txt.setText(m.group(0));
                                   } else {
                                       rep_txt.setText(m.group(0));
                                   }
                                   counter++;
                               }
                               update = (Button) view.findViewById(R.id.save);
                               update.setText("Update");
                               delete = (Button) view.findViewById(R.id.clear);
                               delete.setText("Delete");
                               selected = position;
                           }
                       });
                   }


               }
           }

        });
        return view;
    }
}
