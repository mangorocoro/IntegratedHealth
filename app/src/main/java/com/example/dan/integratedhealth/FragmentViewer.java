package com.example.dan.integratedhealth;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

public class FragmentViewer extends AppCompatActivity implements AHBottomNavigation.OnTabSelectedListener {

    AHBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_viewer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnTabSelectedListener(this);
        this.createNavItems();

    }

    private void createNavItems() {

        //CREATE ITEMS
        AHBottomNavigationItem homeItem = new AHBottomNavigationItem(R.string.home, R.drawable.home_icon, R.color.colorAccent);
        AHBottomNavigationItem generalItem=new AHBottomNavigationItem(R.string.general, R.drawable.graph_icon, R.color.colorAccent);
        AHBottomNavigationItem fitnessItem=new AHBottomNavigationItem(R.string.fitness,R.drawable.run_icon, R.color.colorAccent);
        AHBottomNavigationItem dietItem=new AHBottomNavigationItem(R.string.diet,R.drawable.diet_icon, R.color.colorAccent);

        //ADD THEM to bar
        bottomNavigation.addItem(homeItem);
        bottomNavigation.addItem(generalItem);
        bottomNavigation.addItem(fitnessItem);
        bottomNavigation.addItem(dietItem);

        //set properties
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));

        //set current item
        bottomNavigation.setCurrentItem(2);
    }


    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        //show fragment
        switch(position) {
            case 0:
                System.out.println("case 0");
                Intent intent = new Intent(FragmentViewer.this, HomeScreen.class);
                startActivity(intent);
                break;

            case 1:
                System.out.println("case 1");
                GeneralFragment generalFragment = new GeneralFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_id, generalFragment)
                        .commit();
                break;

            case 2:
                System.out.println("case 2");
                FitnessFragment fitnessFragment = new FitnessFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_id, fitnessFragment)
                        .commit();
                break;

            case 3:
                System.out.println("case 3");
                DietFragment dietFragment = new DietFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_id,dietFragment)
                        .commit();
                break;
        }
        return true;
    }

}