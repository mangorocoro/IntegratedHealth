package com.example.dan.integratedhealth;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.HashMap;

public class GeneralFragment extends Fragment {

    View rootView;
    private static final int BMI_METRIC_CONVERSION_FACTOR = 703;
    private HashMap<String,HashMap> scenarioData;
    private HashMap<String, String> meta;
    private HashMap<String, String> general;
    EditText heightContent;
    EditText weightContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        scenarioData = new HashMap<>();
        if (getArguments()!=null) {
            scenarioData = (HashMap)getArguments().getSerializable("taskScenarioData");
            meta = scenarioData.get("metadata");
            general = scenarioData.get("general");
        }

        rootView = inflater.inflate(R.layout.fragment_general,container,false);

        /* fill in preloaded data */
        updateData(scenarioData);

        /* fill in suggestions if any */
        suggestions();


        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("General Health for " + meta.get("name"));


        if (!meta.get("name").equals("newuser")) {

            TextView heartRateContentView = (TextView) rootView.findViewById(R.id.heartrate_content);
            heartRateContentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.heartrate_sync_title)
                            .setMessage(R.string.heartrate_sync_message)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });


            TextView gutContentView = (TextView) rootView.findViewById(R.id.guthealth_content);
            gutContentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.gut_sync_title)
                            .setMessage(R.string.gut_sync_message)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });

            TextView hydrationContentView = (TextView) rootView.findViewById(R.id.hydration_content);
            hydrationContentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.hydration_sync_title)
                            .setMessage(R.string.hydration_sync_message)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });
        }

        ImageView poopImage = (ImageView) rootView.findViewById(R.id.poop_id);

        if (general.get("guthealth").equals("Diarrheal")) {
            poopImage.setImageResource(R.drawable.diarrheapoop);
        } else if (general.get("guthealth").equals("Constipated")) {
            poopImage.setImageResource(R.drawable.constipated);
        }


        heightContent = (EditText) rootView.findViewById(R.id.height_content);
        weightContent = (EditText) rootView.findViewById(R.id.weight_content);

        heightContent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // inflate alert dialog xml
                LayoutInflater li = LayoutInflater.from(getContext());
                View dialogView = li.inflate(R.layout.set_height_dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getContext());
                // set title
                alertDialogBuilder.setTitle("Enter New Height");
                // set custom dialog icon
                alertDialogBuilder.setIcon(R.drawable.height);
                // set custom_dialog.xml to alertdialog builder
                alertDialogBuilder.setView(dialogView);
                final EditText userInputFeet = (EditText) dialogView.findViewById(R.id.feet_input);

                final EditText userInputInches = (EditText) dialogView.findViewById(R.id.inches_input);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to etOutput
                                        // edit text
                                        String newHeight = userInputFeet.getText().toString() + " ft " + userInputInches.getText().toString() + " in";
                                        heightContent.setText(newHeight);
                                        general.put("height", newHeight);
                                        scenarioData.put("general", general);

                                        TextView bmiView = (TextView) rootView.findViewById(R.id.bmi_content);
                                        String weightValue = general.get("weight");
                                        String[] parsed_weight = weightValue.split(" ");
                                        if (parsed_weight.length> 1) {
                                            String bmiValue = bmiCalculation(userInputFeet.getText().toString() + " " + userInputInches.getText().toString(), parsed_weight[0]);
                                            bmiView.setText(bmiValue);
                                        } else {
                                            String bmiValue = bmiCalculation(userInputFeet.getText().toString() + " " + userInputInches.getText().toString(), weightValue);
                                            bmiView.setText(bmiValue);
                                        }
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }
        });


        weightContent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // inflate alert dialog xml
                LayoutInflater li = LayoutInflater.from(getContext());
                View dialogView = li.inflate(R.layout.set_weight_dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                // set title
                alertDialogBuilder.setTitle("Enter New Weight");
                // set custom dialog icon
                alertDialogBuilder.setIcon(R.drawable.height);
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
                                        weightContent.setText(newWeight + " lbs");
                                        general.put("weight", newWeight);
                                        scenarioData.put("general", general);

                                        TextView bmiView = (TextView) rootView.findViewById(R.id.bmi_content);
                                        String heightValue = general.get("height");
                                        String[] parsed_height = heightValue.split(" ");
                                        if (parsed_height.length > 2) {
                                            String bmiValue = bmiCalculation(parsed_height[0] + " " + parsed_height[2], newWeight);
                                            bmiView.setText(bmiValue);
                                        } else {
                                            String bmiValue = bmiCalculation(heightValue, newWeight);
                                            bmiView.setText(bmiValue);
                                        }
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



        return rootView;
    }

    /* a listener for text changes in edit text */
    private TextWatcher textWatcher = new TextWatcher() {
        public void afterTextChanged(Editable s) {
            general.put("height", s.toString());
            scenarioData.put("general", general);
            System.out.println("s is " + s.toString());
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
    };

    /* update the visualized data on screen with passed in hashmap */
    private void updateData(HashMap<String, HashMap> hash){

        HashMap<String, String> generalHash = hash.get("general");

        TextView heartrateView = (TextView) rootView.findViewById(R.id.heartrate_content);
        EditText heightView = (EditText) rootView.findViewById(R.id.height_content);
        EditText weightView = (EditText) rootView.findViewById(R.id.weight_content);
        TextView bmiView = (TextView) rootView.findViewById(R.id.bmi_content);
        TextView gutView = (TextView) rootView.findViewById(R.id.guthealth_content);
        TextView hydrationView = (TextView) rootView.findViewById(R.id.hydration_content);

        String heartrateValue = generalHash.get("heartrate");
        String heightValue = generalHash.get("height");
        String weightValue = generalHash.get("weight");

        /* height format check */
        String[] parsed_height = heightValue.split(" ");
        String[] parsed_weight = weightValue.split(" ");
        String bmiValue;

        if (parsed_height.length > 2) {
            if (parsed_weight.length> 1){
                bmiValue = bmiCalculation(parsed_height[0] + " " + parsed_height[2], parsed_weight[0]);
            } else {
                bmiValue = bmiCalculation(parsed_height[0] + " " + parsed_height[2], weightValue);
            }
        } else {
            if (parsed_weight.length > 1) { // make it check if it is less than 1 length
                bmiValue = bmiCalculation(heightValue, parsed_weight[0]);
            } else {
                bmiValue = bmiCalculation(heightValue, weightValue);
            }
        }


        String gutValue = generalHash.get("guthealth");
        String hydrationValue = generalHash.get("hydration");


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