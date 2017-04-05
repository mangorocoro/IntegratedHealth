package com.example.dan.integratedhealth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Clayton on 4/5/2017.
 */

public class CustomMacrosActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText proteins;
    private EditText carbs;
    private EditText fats;
    private EditText sugars;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_custom_macros);

        proteins = (EditText) findViewById(R.id.custom_protein_edittext);
        carbs = (EditText) findViewById(R.id.custom_carbs_edittext);
        fats = (EditText) findViewById(R.id.custom_fats_edittext);
        sugars = (EditText) findViewById(R.id.custom_sugars_edittext);

        Button custom_save_button = (Button) findViewById(R.id.custom_save_button);
        custom_save_button.setOnClickListener(this);

        Button custom_cancel_button = (Button) findViewById(R.id.custom_cancel_button);
        custom_cancel_button.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.custom_save_button:
                Intent custom_save_intent = new Intent();
                if (fats.getText() != null && fats.getText().length() > 0) {
                    custom_save_intent.putExtra("FATS", fats.getText());
                } else {
                    custom_save_intent.putExtra("FATS", String.valueOf(0));
                }

                if (sugars.getText() != null && sugars.getText().length() > 0) {
                    custom_save_intent.putExtra("SUGARS", sugars.getText());
                } else {
                    custom_save_intent.putExtra("SUGARS", String.valueOf(0));
                }

                if (proteins.getText() != null && proteins.getText().length() > 0) {
                    custom_save_intent.putExtra("PROTEINS", proteins.getText());
                } else {
                    custom_save_intent.putExtra("PROTEINS", String.valueOf(0));
                }

                if (carbs.getText() != null && carbs.getText().length() > 0) {
                    custom_save_intent.putExtra("CARBS", carbs.getText());
                } else {
                    custom_save_intent.putExtra("CARBS", String.valueOf(0));
                }

                setResult(Activity.RESULT_OK, custom_save_intent);
                finish();

                break;
            case R.id.custom_cancel_button:

                Intent custom_cancel_intent = new Intent();
                setResult(Activity.RESULT_CANCELED, custom_cancel_intent);
                finish();
                break;
        }
    }


}
