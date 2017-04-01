package com.example.dan.integratedhealth;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        startActivity(intent);

    }

    public void diet(View v) {
        Toast.makeText(this, "you clicked the DIET button", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, FragmentViewer.class);
        startActivity(intent);

    }

    public void fitness(View v) {
        Toast.makeText(this, "you clicked the FITNESS button", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, FragmentViewer.class);
        startActivity(intent);


    }






}
