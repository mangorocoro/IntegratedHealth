package com.example.dan.integratedhealth;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.HashMap;

public class GeneralFragment extends Fragment {

    View rootView;
    private static final int BMI_METRIC_CONVERSION_FACTOR = 703;
    private HashMap<String,HashMap> scenarioData;
    private HashMap<String, String> meta;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        scenarioData = new HashMap<>();
        if (getArguments()!=null) {
            scenarioData = (HashMap)getArguments().getSerializable("taskScenarioData");
        }

        rootView=inflater.inflate(R.layout.fragment_general,container,false);

        /* fill in preloaded data */
        updateData(scenarioData);

        /* fill in suggestions if any */
        suggestions();



        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("General Health for " + meta.get("name"));


        return rootView;
    }

    /* update the visualized data on screen with passed in hashmap */
    private void updateData(HashMap<String, HashMap> hash){

        HashMap<String, String> generalHash = hash.get("general");

        TextView heartrateView = (TextView) rootView.findViewById(R.id.heartrate_content);
        TextView heightView = (TextView) rootView.findViewById(R.id.height_content);
        TextView weightView = (TextView) rootView.findViewById(R.id.weight_content);
        TextView bmiView = (TextView) rootView.findViewById(R.id.bmi_content);
        TextView gutView = (TextView) rootView.findViewById(R.id.guthealth_content);
        TextView hydrationView = (TextView) rootView.findViewById(R.id.hydration_content);

        String heartrateValue = generalHash.get("heartrate");
        String heightValue = generalHash.get("height");
        String weightValue = generalHash.get("weight");
        String bmiValue = bmiCalculation(heightValue, weightValue);
        String gutValue = generalHash.get("guthealth");
        String hydrationValue = generalHash.get("hydration");

        String[] parsed_height = heightValue.split(" ");

        String formattedHeight= parsed_height[0] + " ft  " + parsed_height[1] + " in";

        heartrateView.setText(heartrateValue + " bpm");
        heightView.setText(formattedHeight);
        weightView.setText(weightValue + " lbs");
        bmiView.setText(bmiValue);
        gutView.setText(gutValue);
        hydrationView.setText(hydrationValue);

    }

    /* calculate bmi */
    private String bmiCalculation(String height, String weight) {

        meta = scenarioData.get("metadata");

        if (!meta.get("name").equals("newuser")) {
            String [] parsed_height = height.split(" ");
            int heightInt = Integer.parseInt(parsed_height[0])*12 + Integer.parseInt(parsed_height[1]);
            int weightInt = Integer.parseInt(weight);

            DecimalFormat df = new DecimalFormat("#.##");
            Double d = new Double(BMI_METRIC_CONVERSION_FACTOR*weightInt/(Math.pow(heightInt,2)));
            return df.format(d);
        } else {
            return "no data";
        }

    }


    /* suggestions box filler */
    private void suggestions() {

        meta = scenarioData.get("metadata");
        if (!meta.get("name").equals("newuser")) {
            /* hardcoded for stressed out Jimmy */
            String[] parsedHeartrate = (((TextView) rootView.findViewById(R.id.heartrate_content)).getText().toString()).split(" ");
            int heartrateValue = Integer.parseInt(parsedHeartrate[0]);

            if (heartrateValue > 175) {

                TableLayout suggestionsBox = (TableLayout) rootView.findViewById(R.id.table_suggestions_content);

                TextView chilloutMessage = new TextView(getContext());
                chilloutMessage.setText("Hey Jimmy, it looks like you're feeling a bit stressed out there. Give this song a listen.");

                TextView chilloutMusic = new TextView(getContext());
                chilloutMusic.setClickable(true);
                chilloutMusic.setMovementMethod(LinkMovementMethod.getInstance());
                String musicLink = "<a href='https://youtu.be/yIYLbf-qHfY'> Chilled Cow Calm Down Music </a>";
                chilloutMusic.setText(Html.fromHtml(musicLink));

                suggestionsBox.addView(chilloutMessage);
                suggestionsBox.addView(chilloutMusic);


            /* also show a toast message with a link to click on */
                Intent notificationIntent = new Intent(Intent.ACTION_VIEW);
                notificationIntent.setData(Uri.parse("https://youtu.be/yIYLbf-qHfY"));
                PendingIntent pi = PendingIntent.getActivity(getContext(), 0, notificationIntent, 0);
                // Resources r = getResources();
                Notification notification = new NotificationCompat.Builder(getContext())
                        .setTicker("chill out music")
                        .setSmallIcon(android.R.drawable.ic_menu_report_image)
                        .setContentTitle("chill out music")
                        .setContentText("Click here to open it up or else swipe me away!")
                        .setContentIntent(pi)
                        .setAutoCancel(true)
                        .build();
                NotificationManager notificationManager2 =  (NotificationManager) getContext().getSystemService(Service.NOTIFICATION_SERVICE);
                notificationManager2.notify(0, notification);
            }

        }



    }


}