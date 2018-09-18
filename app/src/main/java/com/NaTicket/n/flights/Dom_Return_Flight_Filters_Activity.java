package com.NaTicket.n.flights;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.NaTicket.n.common.activities.ResultIPC;
import com.NaTicket.n.common.pojo.Currency_Utils;
import com.NaTicket.n.custom.FontTypeface;
import com.NaTicket.n.flights.pojo.DomesticReturnFlightDTO;
import com.NaTicket.n.flights.pojo.Flight_Filters_DTO;
import com.NaTicket.n.flights.pojo.Flight_Utils;
import com.NaTicket.n.utils.BackActivity;
import com.NaTicket.n.utils.Util;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.NaTicket.n.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Nagarjuna on 1/8/2018.
 */

public class Dom_Return_Flight_Filters_Activity  extends BackActivity implements View.OnClickListener{
    LinearLayout FareType_Layout,Airlines_Layout,Stops_Layout,Dept_layout,Arrival_layout;
    ArrayList<DomesticReturnFlightDTO> dom_onward_list;

    CrystalRangeSeekbar Price_Range_SeekBar;

    ArrayList<Integer> Stops=new ArrayList<>();
    ArrayList<String> Fare_Type=new ArrayList<>();
    ArrayList<String> Airlines=new ArrayList<>();
    ArrayList<String> Selected_Arravail_time=new ArrayList<>();
    ArrayList<String> Selected_Dept_time=new ArrayList<>();
    ArrayList<Integer> Selected_Stops=new ArrayList<>();
    ArrayList<String> Selected_Fare_Type=new ArrayList<>();
    ArrayList<String> Selected_Airlines=new ArrayList<>();
    HashMap<String,String> Selected=new HashMap<>();
    ArrayList<HashMap<String,String>> Items=new ArrayList<>();
    private ArrayList<Double> Amount=new ArrayList<>();
    TextView Apply,ResetAll;
    TextView Price_Min_value,Price_Max_value;
    Flight_Filters_DTO filterdeatils;
    Boolean Filter;
    Currency_Utils currency_utils;
    String Currency_Symbol,Currency;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_filters_activity);
        inittoolbar();
        TextView toolbartitle = (TextView) findViewById(R.id.toolbartitle);
        toolbartitle.setText("Filters");
        initviews();
        int sync = getIntent().getIntExtra("Domestic_Return_list",-1);
        dom_onward_list= ResultIPC.get().getDom_RetLargeData(sync);
        filterdeatils= (Flight_Filters_DTO) getIntent().getSerializableExtra("Filteredlist");

        Price_Range_SeekBar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                Price_Min_value.setText(Currency_Symbol+String.valueOf(minValue)+"");
                Price_Max_value.setText(Currency_Symbol+String.valueOf(maxValue)+"");
            }
        });
        getdatatoset();


    }

    public void initviews(){
        FareType_Layout= (LinearLayout) findViewById(R.id.check_box_faretype_layout);
        Airlines_Layout= (LinearLayout) findViewById(R.id.check_box_airline_layout);
        Stops_Layout= (LinearLayout) findViewById(R.id.check_box_stops_layout);
        Dept_layout= (LinearLayout) findViewById(R.id.dept_layout);
        Arrival_layout= (LinearLayout) findViewById(R.id.arrival_layout);
        Apply= (TextView) findViewById(R.id.Apply);
        ResetAll= (TextView) findViewById(R.id.Clear_filter);
        Price_Range_SeekBar= (CrystalRangeSeekbar) findViewById(R.id.price_rangeSeekbar);
        Price_Min_value= (TextView) findViewById(R.id.price_min_value);
        Price_Max_value= (TextView) findViewById(R.id.price_max_value);

        currency_utils=new Currency_Utils(this);
        Currency=currency_utils.getCurrencyValue("Currency");
        Currency_Symbol=currency_utils.getCurrencyValue("Currency_Symbol");

        Apply.setOnClickListener(this);
        ResetAll.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.Apply:
                Filter=true;
                ischecked();
                Intent ip=new Intent();
                ip.putExtra("Filter",Filter);
                ip.putExtra("Filteredlist",filterdeatils);
                setResult(1,ip);
                finish();
                break;
            case R.id.Clear_filter:
                Filter=false;
                Intent i = new Intent();
                i.putExtra("Filter",Filter);
                setResult(1,i);
                finish();
                break;
        }
    }

    public void getdatatoset(){
        for (int i=0;i<dom_onward_list.size();i++){
            double d = Double.parseDouble(String.valueOf(dom_onward_list.get(i).getFareDetails().getChargeableFares().getActualBaseFare()));
            double Servicecharge = Double.parseDouble(String.valueOf(dom_onward_list.get(i).getFareDetails().getChargeableFares().getTax()));
            double Operatercharge = Double.parseDouble(String.valueOf(dom_onward_list.get(i).getFareDetails().getNonchargeableFares().getTCharge()));
            double Markepcharge = Double.parseDouble(String.valueOf(dom_onward_list.get(i).getFareDetails().getNonchargeableFares().getTMarkup()));
            final double TotalFare=d+Servicecharge+Operatercharge+Markepcharge;
            Amount.add(Util.getprice(TotalFare));
            Airlines.add(dom_onward_list.get(i).getFlightSegments().get(0).getAirLineName());
            Fare_Type.add(dom_onward_list.get(i).getFlightSegments().get(0).getBookingClassFare().getRule());
            Stops.add(Flight_Utils.getLengthforfilters(dom_onward_list.get(i).getFlightSegments().size()));
        }

        HashSet<String> Airlines_hashSet = new HashSet<String>();
        Airlines_hashSet.addAll(Airlines);
        Airlines.clear();
        Airlines.addAll(Airlines_hashSet);

        HashSet<String> Fares_hashSet = new HashSet<String>();
        Fares_hashSet.addAll(Fare_Type);
        Fare_Type.clear();
        Fare_Type.addAll(Fares_hashSet);

        HashSet<Integer> Stops_hashSet = new HashSet<Integer>();
        Stops_hashSet.addAll(Stops);
        Stops.clear();
        Stops.addAll(Stops_hashSet);

        ArrayList<String> Timings=new ArrayList<>();
        Timings.add("Morning (12:00AM-11:59AM)");
        Timings.add("Afternoon (12:00PM-5:59PM)");
        Timings.add("Evening (6:00PM-11:59PM)");

        SetAirlines_Layout(Airlines,Airlines_Layout);
        SetFareType_Layout(Fare_Type,FareType_Layout);
        SetStops_Layout(Stops,Stops_Layout);
        SetDept_Layout(Timings,Dept_layout);
        SetArrival_Layout(Timings,Arrival_layout);




        Amountmethod();
    }

    public void ischecked(){
        filterdeatils=new Flight_Filters_DTO();
        filterdeatils.setAirlines(Selected_Airlines);
        filterdeatils.setRule(Selected_Fare_Type);
        filterdeatils.setStops(Selected_Stops);
        filterdeatils.setArrival_Times(Selected_Arravail_time);
        filterdeatils.setDept_Times(Selected_Dept_time);

        filterdeatils.setMin_Rate(Integer.parseInt(String.valueOf(Price_Range_SeekBar.getSelectedMinValue())));
        filterdeatils.setMax_Rate(Integer.parseInt(String.valueOf(Price_Range_SeekBar.getSelectedMaxValue())));

        Items.add(Selected);
        filterdeatils.setFilter_Array(Items);
    }


    private void SetAirlines_Layout(ArrayList<String> Airlines,LinearLayout Check_box_layout){
        if(Airlines.size()>0){
            CheckBox[] checkBoxes = new CheckBox[Airlines.size()];
            for(int i=0;i<Airlines.size();i++){
                final CheckBox checkBox = new CheckBox(Dom_Return_Flight_Filters_Activity.this);
                FontTypeface fontTypeface=new FontTypeface(Dom_Return_Flight_Filters_Activity.this);
                checkBox.setTypeface(fontTypeface.getTypefaceAndroid());
                checkBox.setText(""+Airlines.get(i)+"");
                Check_box_layout.addView(checkBox);
                checkBoxes[i] = checkBox;

                if (filterdeatils!=null){
                    if (filterdeatils.getAirlines().contains(checkBox.getText().toString())){
                        checkBox.setChecked(true);
                        Selected_Airlines.add(checkBox.getText().toString());
                    }
                }

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (Selected_Airlines.contains(checkBox.getText().toString())){
                            Selected_Airlines.remove(checkBox.getText().toString());
                            Selected.remove(checkBox.getText().toString());
                            System.out.println("Selected box  "+checkBox.getText().toString());
                        }else {
                            Selected_Airlines.add(checkBox.getText().toString());
                            Selected.put("Airline",checkBox.getText().toString());
                            System.out.println("Selected box  "+checkBox.getText().toString());
                        }
                    }
                });

            }
        }
    }


    private void SetFareType_Layout(ArrayList<String> Fare_Type,LinearLayout Check_box_layout){
        if(Fare_Type.size()>0){
            CheckBox[] checkBoxes = new CheckBox[Fare_Type.size()];
            for(int i=0;i<Fare_Type.size();i++){
                final CheckBox checkBox = new CheckBox(Dom_Return_Flight_Filters_Activity.this);
                FontTypeface fontTypeface=new FontTypeface(Dom_Return_Flight_Filters_Activity.this);
                checkBox.setTypeface(fontTypeface.getTypefaceAndroid());
                checkBox.setText(""+Fare_Type.get(i)+"");
                Check_box_layout.addView(checkBox);
                checkBoxes[i] = checkBox;

                if (filterdeatils!=null){
                    if (filterdeatils.getRule().contains(checkBox.getText().toString())){
                        checkBox.setChecked(true);
                        Selected_Fare_Type.add(checkBox.getText().toString());
                    }
                }

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (Selected_Fare_Type.contains(checkBox.getText().toString())){
                            Selected_Fare_Type.remove(checkBox.getText().toString());
                            Selected.remove(checkBox.getText().toString());
                            System.out.println("Selected box  "+checkBox.getText().toString());
                        }else {
                            Selected_Fare_Type.add(checkBox.getText().toString());
                            Selected.put("Rule",checkBox.getText().toString());
                            System.out.println("Selected box  "+checkBox.getText().toString());
                        }
                    }
                });

            }
        }
    }


    private void SetStops_Layout(final ArrayList<Integer> Stops, LinearLayout Check_box_layout){
        if(Stops.size()>0){
            CheckBox[] checkBoxes = new CheckBox[Stops.size()];
            for(int i=0;i<Stops.size();i++){
                final CheckBox checkBox = new CheckBox(Dom_Return_Flight_Filters_Activity.this);
                FontTypeface fontTypeface=new FontTypeface(Dom_Return_Flight_Filters_Activity.this);
                checkBox.setTypeface(fontTypeface.getTypefaceAndroid());
                checkBox.setText(""+Stops.get(i)+" Stops");
                Check_box_layout.addView(checkBox);
                checkBoxes[i] = checkBox;
                if (filterdeatils!=null){
                    if (filterdeatils.getStops().contains(Stops.get(i))){
                        checkBox.setChecked(true);
                        Selected_Stops.add(Stops.get(i));
                    }
                }

                final int finalI = i;
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (Selected_Stops.contains(Stops.get(finalI))){
                            Selected_Stops.remove(Stops.get(finalI));
                            Selected.remove(checkBox.getText().toString());
                            System.out.println("Selected box  "+checkBox.getText().toString());
                        }else {
                            Selected_Stops.add(Stops.get(finalI));
                            Selected.put("Stops", String.valueOf(checkBox.getText().toString()));
                            System.out.println("Selected box  "+checkBox.getText().toString());
                        }
                    }
                });

            }
        }
    }


    private void SetArrival_Layout(ArrayList<String> Depts,LinearLayout Check_box_layout){
        if(Depts.size()>0){
            CheckBox[] checkBoxes = new CheckBox[Depts.size()];
            for(int i=0;i<Depts.size();i++){
                final CheckBox checkBox = new CheckBox(Dom_Return_Flight_Filters_Activity.this);
                FontTypeface fontTypeface=new FontTypeface(Dom_Return_Flight_Filters_Activity.this);
                checkBox.setTypeface(fontTypeface.getTypefaceAndroid());
                checkBox.setText(""+Depts.get(i)+"");
                Check_box_layout.addView(checkBox);
                checkBoxes[i] = checkBox;

                if (filterdeatils!=null){
                    if (filterdeatils.getDept_Times().contains(checkBox.getText().toString())){
                        checkBox.setChecked(true);
                        Selected_Dept_time.add(checkBox.getText().toString());
                    }
                }

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (Selected_Dept_time.contains(checkBox.getText().toString())){
                            Selected_Dept_time.remove(checkBox.getText().toString());
                            Selected.remove(checkBox.getText().toString());
                            System.out.println("Selected box  "+checkBox.getText().toString());
                        }else {
                            Selected_Dept_time.add(checkBox.getText().toString());
                            Selected.put(checkBox.getText().toString(),checkBox.getText().toString());
                            System.out.println("Selected box  "+checkBox.getText().toString());
                        }
                    }
                });

            }
        }
    }

    private void SetDept_Layout(ArrayList<String> Arrivals,LinearLayout Check_box_layout){
        if(Arrivals.size()>0){
            CheckBox[] checkBoxes = new CheckBox[Arrivals.size()];
            for(int i=0;i<Arrivals.size();i++){
                final CheckBox checkBox = new CheckBox(Dom_Return_Flight_Filters_Activity.this);
                FontTypeface fontTypeface=new FontTypeface(Dom_Return_Flight_Filters_Activity.this);
                checkBox.setTypeface(fontTypeface.getTypefaceAndroid());
                checkBox.setText(""+Arrivals.get(i)+"");
                Check_box_layout.addView(checkBox);
                checkBoxes[i] = checkBox;

                if (filterdeatils!=null){
                    if (filterdeatils.getArrival_Times().contains(checkBox.getText().toString())){
                        checkBox.setChecked(true);
                        Selected_Arravail_time.add(checkBox.getText().toString());
                    }
                }

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (Selected_Arravail_time.contains(checkBox.getText().toString())){
                            Selected_Arravail_time.remove(checkBox.getText().toString());
                            Selected.remove(checkBox.getText().toString());
                            System.out.println("Selected box  "+checkBox.getText().toString());
                        }else {
                            Selected_Arravail_time.add(checkBox.getText().toString());
                            Selected.put(checkBox.getText().toString(),checkBox.getText().toString());
                            System.out.println("Selected box  "+checkBox.getText().toString());
                        }
                    }
                });

            }
        }
    }
    private  void Amountmethod(){
        double minimum= Collections.min(Amount);
        double maximum= Collections.max(Amount);



        Price_Range_SeekBar.setMinValue(roundup(minimum));
        Price_Range_SeekBar.setMaxValue(roundup(maximum));

        String min= String.valueOf(roundup(minimum));
        String max= String.valueOf(roundup(maximum));
        Price_Min_value.setText(Currency_Symbol+min+"");
        Price_Max_value.setText(Currency_Symbol+max+"");

        System.out.println("Min Rate:: "+minimum);
        System.out.println("Max RAte:: "+maximum);
    }

    private int roundup(double value){
        double dAbs = Math.abs(value);
        int amt2 = (int) dAbs;
        double result = dAbs - (double) amt2;
        int finalrate;
        if (result<0.5){
            finalrate= (int) Math.floor(value);
        }else {
            finalrate= (int) Math.ceil(value);
        }
        return finalrate;
    }

}

