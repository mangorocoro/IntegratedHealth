package com.example.dan.integratedhealth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Clayton on 4/5/2017.
 */

public class RecipeActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Button recipe_return_button = (Button) findViewById(R.id.recipe_return_button);
        recipe_return_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.recipe_return_button:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }

    }

}
