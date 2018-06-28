package com.NaTicket.n.buses;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.NaTicket.n.R;

import java.util.Objects;

/**
 * Created by Ankit on 7/16/2016.
 */
public class FilterActivity extends AppCompatActivity {

    TextView ACButton, Non_AC_Button, Sleeper_Button, Semi_Sleeper_Button, Travels, filter_boarding_point;
    TextView Applay, ClearFilter;
    String AC, NONAC, Sleeper, NonSleeper;
    String Clear;
    String JourneyDate, DayofJourney, OnResume;
    String Journey, Day_of_Journey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        ACButton = (TextView) findViewById(R.id.AC_Button);
        Non_AC_Button = (TextView) findViewById(R.id.Non_AcButton);
        Sleeper_Button = (TextView) findViewById(R.id.Sleeper_Button);
        Semi_Sleeper_Button = (TextView) findViewById(R.id.Non_Sleeper);
        filter_boarding_point = (TextView) findViewById(R.id.filter_boarding_point);
        Travels = (TextView) findViewById(R.id.filter_Travels);
        //    filter_droping_point=(Button)findViewById(R.id.filter_droping_point);
        Applay = (TextView) findViewById(R.id.Apply);
        ClearFilter = (TextView) findViewById(R.id.Clear_filter);
        Intent ip = getIntent();
        JourneyDate = ip.getStringExtra("JourneyDAte");
        DayofJourney = ip.getStringExtra("DayofJourney");
        OnResume = ip.getStringExtra("OnResume");


    }

    /**
     * Called when the activity is about to become visible.
     */
    @Override
    protected void onStart() {
        super.onStart();

        ACButton.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View view) {
                ACButton.setTextColor(getResources().getColor(R.color.colorAccent));
                Non_AC_Button.setTextColor(getResources().getColor(R.color.text_primary));
                Sleeper_Button.setTextColor(getResources().getColor(R.color.text_primary));
                Semi_Sleeper_Button.setTextColor(getResources().getColor(R.color.text_primary));
                AC = "true";
                NONAC = "false";
                Sleeper = "false";
                NonSleeper = "false";
            }
        });
        Non_AC_Button.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View view) {
                Non_AC_Button.setTextColor(getResources().getColor(R.color.colorAccent));
                ACButton.setTextColor(getResources().getColor(R.color.text_primary));
                Sleeper_Button.setTextColor(getResources().getColor(R.color.text_primary));
                Semi_Sleeper_Button.setTextColor(getResources().getColor(R.color.text_primary));
                NONAC = "true";
                Sleeper = "false";
                AC = "false";
                NonSleeper = "false";
            }
        });
        Sleeper_Button.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View view) {
                Sleeper_Button.setTextColor(getResources().getColor(R.color.colorAccent));
                ACButton.setTextColor(getResources().getColor(R.color.text_primary));
                Non_AC_Button.setTextColor(getResources().getColor(R.color.text_primary));
                Semi_Sleeper_Button.setTextColor(getResources().getColor(R.color.text_primary));
                Sleeper = "true";
                NONAC = "false";
                AC = "false";
                NonSleeper = "false";
            }
        });
        Semi_Sleeper_Button.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View view) {
                Semi_Sleeper_Button.setTextColor(getResources().getColor(R.color.colorAccent));
                Non_AC_Button.setTextColor(getResources().getColor(R.color.text_primary));
                Sleeper_Button.setTextColor(getResources().getColor(R.color.text_primary));
                ACButton.setTextColor(getResources().getColor(R.color.text_primary));
                NonSleeper = "true";
                AC = "false";
                NONAC = "false";
                Sleeper = "false";
            }
        });
        Travels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filter = "True";
                Intent i = new Intent(FilterActivity.this, FilterTravels.class);
                SharedPreferences preference = getApplicationContext().getSharedPreferences("Filter", MODE_PRIVATE);
                SharedPreferences.Editor editor = preference.edit();
                editor.putString("FILTER", filter);
                editor.putString("ACSELECTED", AC);
                editor.putString("NonACSELECTED", NONAC);
                editor.putString("SleeperSELECTED", Sleeper);
                editor.putString("NonSleeperSELECTED", NonSleeper);
                editor.putString("JourneyDate", JourneyDate);
                editor.putString("DayofJourney", DayofJourney);

                System.out.println("ACCLicked" + AC + NONAC + NONAC);
                editor.apply();
                startActivity(i);
            }
        });
        filter_boarding_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filter = "True";
                Intent i = new Intent(FilterActivity.this, FilterBoardingPoint.class);
                SharedPreferences preference = getApplicationContext().getSharedPreferences("Filter", MODE_PRIVATE);
                SharedPreferences.Editor editor = preference.edit();
                editor.putString("FILTER", filter);
                editor.putString("ACSELECTED", AC);
                editor.putString("NonACSELECTED", NONAC);
                editor.putString("SleeperSELECTED", Sleeper);
                editor.putString("NonSleeperSELECTED", NonSleeper);
                editor.putString("JourneyDate", JourneyDate);
                editor.putString("DayofJourney", DayofJourney);

                System.out.println("ACCLicked" + AC + NONAC + NONAC);
                editor.apply();
                startActivity(i);
            }
        });
      /*  filter_droping_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(FilterActivity.this, FilterDropingpoint.class);
                startActivity(i);
            }
        });*/

        ClearFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FilterActivity.this, SearchActivity.class);
                SharedPreferences prefer = getApplicationContext().getSharedPreferences("Filter", MODE_PRIVATE);
                prefer.edit().remove("ACSELECTED").apply();
                prefer.edit().remove("NonACSELECTED").apply();
                prefer.edit().remove("SleeperSELECTED").apply();
                prefer.edit().remove("SemiSleeperSELECTED").apply();
                prefer.edit().remove("Filter_OperaterId").apply();
                prefer.edit().remove("Filter_OperaterName").apply();
                prefer.edit().remove("FilterBoadingId").apply();
                prefer.edit().remove("FilterBoadingLocation").apply();
                prefer.edit().remove("FilterDropingId").apply();
                prefer.edit().remove("FilterDropinglocation").apply();
                prefer.edit().remove("FILTER").apply();
                startActivity(i);
            }
        });
        Applay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filter = "True";
                Intent i = new Intent(FilterActivity.this, SearchActivity.class);
                SharedPreferences preference = getApplicationContext().getSharedPreferences("Filter", MODE_PRIVATE);
                SharedPreferences.Editor editor = preference.edit();
                editor.putString("FILTER", filter);
                editor.putString("ACSELECTED", AC);
                editor.putString("NonACSELECTED", NONAC);
                editor.putString("SleeperSELECTED", Sleeper);
                editor.putString("SemiSleeperSELECTED", NonSleeper);
                editor.putString("JourneyDate", JourneyDate);
                editor.putString("DayofJourney", DayofJourney);

                System.out.println("ACCLicked" + AC + NONAC + NONAC);
                editor.apply();
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });
    }

    /**
     * Called when the activity has become visible.
     */
    @SuppressWarnings("deprecation")
    @SuppressLint({"NewApi", "SetTextI18n"})
    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences preference = getApplicationContext().getSharedPreferences("Filter", MODE_PRIVATE);

        String ac = preference.getString("ACSELECTED", null);
        String nonac = preference.getString("NonACSELECTED", null);
        String sleeper = preference.getString("SleeperSELECTED", null);
        String nonsleeper = preference.getString("SemiSleeperSELECTED", null);
        String FilterOpeName = preference.getString("Filter_OperaterName", null);
        String filterBlocation = preference.getString("FilterBoadingLocation", null);
        String filterDlocaation = preference.getString("FilterDropinglocation", null);
        if (OnResume.equals("True")) {
            Journey = JourneyDate;
            Day_of_Journey = DayofJourney;
        } else {
            Journey = preference.getString("JourneyDate", null);
            Day_of_Journey = preference.getString("DayofJourney", null);
        }
        if (Objects.equals(ac, "true")) {
            ACButton.setTextColor(getResources().getColor(R.color.colorAccent));
            AC = "true";
            NONAC = "false";
            Sleeper = "false";
            NonSleeper = "false";
        } else if (Objects.equals(nonac, "true")) {
            Non_AC_Button.setTextColor(getResources().getColor(R.color.colorAccent));
            NONAC = "true";
            Sleeper = "false";
            AC = "false";
            NonSleeper = "false";
        } else if (Objects.equals(sleeper, "true")) {
            Sleeper_Button.setTextColor(getResources().getColor(R.color.colorAccent));
            Sleeper = "true";
            NONAC = "false";
            AC = "false";
            NonSleeper = "false";
        } else if (Objects.equals(nonsleeper, "true")) {
            Semi_Sleeper_Button.setTextColor(getResources().getColor(R.color.colorAccent));
            NonSleeper = "true";
            AC = "false";
            NONAC = "false";
            Sleeper = "false";
        }
        if (FilterOpeName != null || filterBlocation != null) {
            if (Journey != null) {
                JourneyDate = Journey;
            }

            if (Day_of_Journey != null) {
                DayofJourney = Day_of_Journey;
            }
        }





     /*   if(FilterOpeName!=null || filterBlocation!=null || filterDlocaation!=null){
            if(FilterOpeName!=null){
                Travels.setText("   "+FilterOpeName);
            }if(filterBlocation!=null){
                filter_boarding_point.setText("   "+filterBlocation);
            }*//*if(filterDlocaation!=null){
                filter_droping_point.setText("   "+filterDlocaation);
            }*//*

        }*/
    }

    /**
     * Called when another activity is taking focus.
     */
    @Override
    protected void onPause() {
        super.onPause();

    }

    /**
     * Called when the activity is no longer visible.
     */
    @Override
    protected void onStop() {
        super.onStop();

    }

    /**
     * Called just before the activity is destroyed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();

    }


}
