package com.example.dan.integratedhealth;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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
    float number_weight;
    float number_rep;
    EditText wght_txt;
    EditText rep_txt;
    TableLayout tableLayout;

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
                wght_txt.setText("");
                rep_txt.setText("");
            }
        });

        save = (Button) view.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View v){
               String wght_temp = wght_txt.getText().toString().trim();
               String rep_temp = rep_txt.getText().toString().trim();

               if(wght_temp.isEmpty() && rep_temp.isEmpty()){
                   CharSequence text = "Please enter a weight or number of reps";
                   int duration = Toast.LENGTH_LONG;
                   Toast toast = Toast.makeText(getContext(), text, duration);
                   toast.show();
               } else {
                   CharSequence text = "Saved";
                   int duration = Toast.LENGTH_LONG;
                   Toast toast = Toast.makeText(getContext(), text, duration);
                   toast.show();
                   tableLayout = (TableLayout) view.findViewById(R.id.log_table);
                   TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
                   final TableRow tableRow = new TableRow(getContext());
                   tableRow.setLayoutParams(tableParams);
                   TextView weight_row = new TextView(getContext());
                   weight_row.setText(wght_txt.getText().toString() + " lbs");
                   tableRow.addView(weight_row);
                   TextView rep_row = new TextView(getContext());
                   rep_row.setText(rep_txt.getText().toString() + " reps");
                   tableRow.addView(rep_row);
                   tableRow.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           TextView txt_weight = (TextView) tableRow.getChildAt(0);
                           String set_weight = (txt_weight.getText().toString()).replaceAll("[ lbs]", "");
                           wght_txt.setText(set_weight);
                           TextView txt_rep = (TextView) tableRow.getChildAt(1);
                           String set_rep = (txt_rep.getText().toString()).replaceAll("[ reps]", "");
                           rep_txt.setText(set_rep);
                       }
                   });
                   tableLayout.addView(tableRow);


               }
           }

        });



        return view;
    }
}
