package com.NaTicket.n.hotels;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.NaTicket.n.common.activities.ResultIPC;
import com.NaTicket.n.common.pojo.Currency_Utils;
import com.NaTicket.n.hotels.pojo.AvailableHotelsDTO;
import com.NaTicket.n.hotels.pojo.Hotel_Filters_DTO;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.NaTicket.n.R;

import java.util.ArrayList;
import java.util.Collections;


/**
 * Created by Ankit on 9/22/2017.
 */

public class Hotel_Filters_Activity extends AppCompatActivity{

    TextView Apply,ClearFilter,tvmin,tvmax;
    //ArrayList<AvailableHotelsDTO> availableHotelsDTOs=new ArrayList<>();
    ArrayList<AvailableHotelsDTO> availableHotelsDTOs=new ArrayList<>();
    CrystalRangeSeekbar rangeSeekbar;
    CheckBox onestar,twostar,threestar,fourstar,fivestar;
    LinearLayout one_layout,two_layout,three_layout,four_layout,five_layout;
    private ArrayList<Double> Amount=new ArrayList<>();
    private ArrayList<Integer> Stars=new ArrayList<>();
    TextView toolbartitle;
    Intent i;
    Currency_Utils currency_utils;
    String Currency_Symbol,Currency;
    Boolean Filter;
    Hotel_Filters_DTO filterdeatils;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotel_filters);
        currency_utils=new Currency_Utils(this);
        Currency=currency_utils.getCurrencyValue("Currency");
        filterdeatils=new Hotel_Filters_DTO();
        filterdeatils=(Hotel_Filters_DTO) getIntent().getSerializableExtra("Filteredlist");
        //availableHotelsDTOs=(ArrayList<AvailableHotelsDTO>) getIntent().getSerializableExtra("Hotelslist");

        int sync = getIntent().getIntExtra("bigdata:synccode", -1);
        availableHotelsDTOs = ResultIPC.get().getLargeData(sync);
        init();
        Onres();
        switch (Currency) {
            case "INR":
                Currency_Symbol = "₹ ";
                break;
            case "USD":
                Currency_Symbol = "$ ";
                break;
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbartitle=(TextView)findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbartitle.setText("Filters");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        /*checkvalues();*/
        Apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ischecked();
                ischecked();
                Filter=true;
                Intent ip=new Intent();
                ip.putExtra("Filter",Filter);
                ip.putExtra("Filteredlist",filterdeatils);
                setResult(1,ip);
                finish();


            }
        });

        ClearFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Filter=false;
                Intent i = new Intent();
                i.putExtra("Filter",Filter);
                setResult(1,i);
                finish();
            }
        });

        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tvmin.setText(Currency_Symbol+String.valueOf(minValue)+"");
                tvmax.setText(Currency_Symbol+String.valueOf(maxValue)+"");
            }
        });

        getStarRating();
    }


    public void getStarRating(){
        String CurrValue=currency_utils.getCurrencyValue("Currency_Value");

        for (int p=0;p<availableHotelsDTOs.size();p++){
            Stars.add(availableHotelsDTOs.get(p).getStarRating());
            Amount.add((Double.valueOf(availableHotelsDTOs.get(p).getRoomDetails().get(0).getRoomTotal())+
                    Double.valueOf(availableHotelsDTOs.get(p).getRoomDetails().get(0).getServicetaxTotal()))*Double.valueOf(CurrValue));
        }
        Amountmethod();
        Starsmethod();
    }


    private void Starsmethod(){
        if (Stars.contains(1)){
            one_layout.setVisibility(View.VISIBLE);
        }else {
            one_layout.setVisibility(View.GONE);
        }
        if (Stars.contains(2)){
            two_layout.setVisibility(View.VISIBLE);
        }else {
            two_layout.setVisibility(View.GONE);
        }
        if (Stars.contains(3)){
            three_layout.setVisibility(View.VISIBLE);
        }else {
            three_layout.setVisibility(View.GONE);
        }
        if (Stars.contains(4)){
            four_layout.setVisibility(View.VISIBLE);
        }else {
            four_layout.setVisibility(View.GONE);
        }
        if (Stars.contains(5)){
            five_layout.setVisibility(View.VISIBLE);
        }else {
            five_layout.setVisibility(View.GONE);
        }

    }


    private  void Amountmethod(){


        double minimum= Collections.min(Amount);
        double maximum= Collections.max(Amount);

        rangeSeekbar.setMinValue(roundup(minimum));
        rangeSeekbar.setMaxValue(roundup(maximum));

        String min= String.valueOf(roundup(minimum));
        String max= String.valueOf(roundup(maximum));
        tvmin.setText(Currency_Symbol+min+"");
        tvmax.setText(Currency_Symbol+max+"");


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


    private void init(){
        Apply= (TextView) findViewById(R.id.Apply);
        ClearFilter= (TextView) findViewById(R.id.Clear_filter);

        tvmin= (TextView) findViewById(R.id.min_value);
        tvmax= (TextView) findViewById(R.id.max_value);


        onestar= (CheckBox) findViewById(R.id.one_star_check);
        twostar= (CheckBox) findViewById(R.id.two_star_check);
        threestar=(CheckBox) findViewById(R.id.three_star_check);
        fourstar= (CheckBox) findViewById(R.id.four_star_checked);
        fivestar= (CheckBox) findViewById(R.id.five_star_checked);
        rangeSeekbar= (CrystalRangeSeekbar) findViewById(R.id.rangeSeekbar);

        one_layout= (LinearLayout) findViewById(R.id.one_layout);
        two_layout= (LinearLayout) findViewById(R.id.two_layout);
        three_layout= (LinearLayout) findViewById(R.id.three_layout);
        four_layout= (LinearLayout) findViewById(R.id.four_layout);
        five_layout= (LinearLayout) findViewById(R.id.five_layout);
    }


    private void ischecked(){

        filterdeatils=new Hotel_Filters_DTO();
        if (onestar.isChecked()){
            filterdeatils.setone_star(1);
        }else {
            filterdeatils.setone_star(6);
        }

        if (twostar.isChecked()){
            filterdeatils.settwo_star(2);
        }else {
            filterdeatils.settwo_star(6);
        }

        if (threestar.isChecked()){
            filterdeatils.setthree_star(3);
        }else {
            filterdeatils.setthree_star(6);
        }

        if (fourstar.isChecked()){
            filterdeatils.setfour_star(4);
        }else {
            filterdeatils.setfour_star(6);
        }
        if (fivestar.isChecked()){
            filterdeatils.setfive_star(5);
        }else {
            filterdeatils.setfive_star(6);
        }

        filterdeatils.setMin_Rate(Integer.parseInt(String.valueOf(rangeSeekbar.getSelectedMinValue())));
        filterdeatils.setMax_Rate(Integer.parseInt(String.valueOf(rangeSeekbar.getSelectedMaxValue())));
    }

    private  void Onres(){
        if (filterdeatils!=null){
            if (filterdeatils.getone_star()==1){
                onestar.setChecked(true);
            }
            if (filterdeatils.gettwo_star()==2){
                twostar.setChecked(true);
            }
            if (filterdeatils.getthree_star()==3){
                threestar.setChecked(true);
            }
            if (filterdeatils.getfour_star()==4){
                fourstar.setChecked(true);
            }
            if (filterdeatils.getfive_star()==5){
                fivestar.setChecked(true);
            }
               /* rangeSeekbar.setMinStartValue(filterdeatils.getMin_Rate());
                rangeSeekbar.apply();
                rangeSeekbar.setMaxStartValue(filterdeatils.getMax_Rate());
                rangeSeekbar.apply();

                tvmin.setText(filterdeatils.getMin_Rate());
                tvmax.setText(filterdeatils.getMax_Rate());*/

        }
    }

}
