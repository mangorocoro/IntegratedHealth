package com.example.dan.integratedhealth;

import android.app.Activity;
import android.app.ListActivity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class AboutActivity extends Activity {


    ListView list;
    String[] itemname ={
            "General",
            "Fitness",
            "Diet",
            "Home",
            "Navbar General",
            "Navbar Fitness",
            "Navbar Diet"
    };

    Integer[] imgid={
            R.drawable.scale,
            R.drawable.runner,
            R.drawable.applebanana,
            R.drawable.home_icon,
            R.drawable.graph_icon,
            R.drawable.run_icon,
            R.drawable.diet_icon
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        String[] descriptions = {
                getString(R.string.general_button_description),
                getString(R.string.fitness_button_description),
                getString(R.string.diet_button_description),
                getString(R.string.home_bottom_nav_description),
                getString(R.string.general_bottom_nav_description),
                getString(R.string.fitness_bottom_nav_description),
                getString(R.string.diet_bottom_nav_description),
        };


        CustomListAdapter adapter=new CustomListAdapter(this, itemname, imgid, descriptions);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String Slecteditem= itemname[+position];

            }
        });
    }
}
