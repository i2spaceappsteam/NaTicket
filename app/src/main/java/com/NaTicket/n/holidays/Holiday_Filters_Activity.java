package com.NaTicket.n.holidays;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.NaTicket.n.R;
import com.NaTicket.n.common.pojo.Currency_Utils;
import com.NaTicket.n.custom.FontTypeface;
import com.NaTicket.n.holidays.pojo.AvailableHolidayPackagesDTO;
import com.NaTicket.n.holidays.pojo.Holiday_Filters_DTO;
import com.NaTicket.n.utils.BackActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by Nagarjuna on 12/19/2017.
 */

public class Holiday_Filters_Activity extends BackActivity implements View.OnClickListener{
    String Currency_Symbol,Currency;
    Currency_Utils currency_utils;
    CrystalRangeSeekbar rangeSeekbar;
    Boolean Filter;
    ArrayList<AvailableHolidayPackagesDTO> availableHolidaysDTOs;
    CheckBox zerostar,onestar,twostar,threestar,fourstar,fivestar;
    TextView Apply,ClearFilter,tvmin,tvmax;
    LinearLayout zero_layout,one_layout,two_layout,three_layout,four_layout,five_layout;
    private ArrayList<Double> Amount=new ArrayList<>();
    private ArrayList<Integer> Stars=new ArrayList<>();
    private ArrayList<String> Subcategory=new ArrayList<>();
    LinearLayout Check_box_layout;
    ArrayList<String> Selected_Sub = new ArrayList<String>();
    Holiday_Filters_DTO filterdeatils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.holiday_filters_activity);

        currency_utils=new Currency_Utils(this);
        Currency=currency_utils.getCurrencyValue("Currency");
        filterdeatils= (Holiday_Filters_DTO) getIntent().getSerializableExtra("Filteredlist");
        availableHolidaysDTOs=(ArrayList<AvailableHolidayPackagesDTO>) getIntent().getSerializableExtra("Holidayslist");

        init();
        Onres();
        Currency_Symbol=currency_utils.getCurrencyValue("Currency_Symbol");
        TextView toolbartitle=(TextView)findViewById(R.id.toolbartitle);
        inittoolbar();
        toolbartitle.setText("Filters");


        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tvmin.setText(Currency_Symbol+String.valueOf(minValue)+"");
                tvmax.setText(Currency_Symbol+String.valueOf(maxValue)+"");
            }
        });


        getdatatoset();

    }


    public void getdatatoset(){

        String CurrValue=currency_utils.getCurrencyValue("Currency_Value");

        for (int i = 0; i < availableHolidaysDTOs.size(); i++) {
            Subcategory.add(availableHolidaysDTOs.get(i).getSubCategory());
            Stars.add(availableHolidaysDTOs.get(i).getHotelRating());
            Amount.add((Double.valueOf(availableHolidaysDTOs.get(i).getFareDetails().get(0).getAdultFare()))*Double.parseDouble(CurrValue));
        }

        Amountmethod();
        Starsmethod();
        ///Delete Duplicates
        HashSet<String> hashSet = new HashSet<String>();
        hashSet.addAll(Subcategory);
        Subcategory.clear();
        Subcategory.addAll(hashSet);

        if (Subcategory.contains("")){
            Subcategory.remove("");
        }
        SetSubcategorylist(Subcategory,Check_box_layout);
    }

    private void init(){
        Apply= (TextView) findViewById(R.id.Apply);
        ClearFilter= (TextView) findViewById(R.id.Clear_filter);
        rangeSeekbar= (CrystalRangeSeekbar) findViewById(R.id.rangeSeekbar);
        tvmin= (TextView) findViewById(R.id.min_value);
        tvmax= (TextView) findViewById(R.id.max_value);

        zerostar= (CheckBox) findViewById(R.id.zero_star_check);
        onestar= (CheckBox) findViewById(R.id.one_star_check);
        twostar= (CheckBox) findViewById(R.id.two_star_check);
        threestar=(CheckBox) findViewById(R.id.three_star_check);
        fourstar= (CheckBox) findViewById(R.id.four_star_checked);
        fivestar= (CheckBox) findViewById(R.id.five_star_checked);

        zero_layout= (LinearLayout) findViewById(R.id.zero_layout);
        one_layout= (LinearLayout) findViewById(R.id.one_layout);
        two_layout= (LinearLayout) findViewById(R.id.two_layout);
        three_layout= (LinearLayout) findViewById(R.id.three_layout);
        four_layout= (LinearLayout) findViewById(R.id.four_layout);
        five_layout= (LinearLayout) findViewById(R.id.five_layout);

        Check_box_layout= (LinearLayout) findViewById(R.id.check_box_layout);
        Apply.setOnClickListener(this);
        ClearFilter.setOnClickListener(this);
    }

    private void Starsmethod(){
        if (Stars.contains(51)){
            zero_layout.setVisibility(View.VISIBLE);
        }else {
            zero_layout.setVisibility(View.GONE);
        }
        if (Stars.contains(52)){
            one_layout.setVisibility(View.VISIBLE);
        }else {
            one_layout.setVisibility(View.GONE);
        }
        if (Stars.contains(53)){
            two_layout.setVisibility(View.VISIBLE);
        }else {
            two_layout.setVisibility(View.GONE);
        }
        if (Stars.contains(54)){
            three_layout.setVisibility(View.VISIBLE);
        }else {
            three_layout.setVisibility(View.GONE);
        }
        if (Stars.contains(55)){
            four_layout.setVisibility(View.VISIBLE);
        }else {
            four_layout.setVisibility(View.GONE);
        }
        if (Stars.contains(56)){
            five_layout.setVisibility(View.VISIBLE);
        }else {
            five_layout.setVisibility(View.GONE);
        }

    }


    private void SetSubcategorylist(ArrayList<String> SubCategory,LinearLayout Check_box_layout){


        if(SubCategory.size()>0){
            CheckBox[] checkBoxes = new CheckBox[SubCategory.size()];
            for(int i=0;i<SubCategory.size();i++){
                final CheckBox checkBox = new CheckBox(Holiday_Filters_Activity.this);
                FontTypeface fontTypeface=new FontTypeface(Holiday_Filters_Activity.this);
                checkBox.setTypeface(fontTypeface.getTypefaceAndroid());
                checkBox.setText(""+SubCategory.get(i)+"");
                Check_box_layout.addView(checkBox);
                checkBoxes[i] = checkBox;

                if (filterdeatils!=null){
                    if (filterdeatils.getSelected_Sub().contains(checkBox.getText().toString())){
                        checkBox.setChecked(true);
                        Selected_Sub.add(checkBox.getText().toString());
                    }
                }

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (Selected_Sub.contains(checkBox.getText().toString())){
                            Selected_Sub.remove(checkBox.getText().toString());
                            System.out.println("Selected box  "+checkBox.getText().toString());
                        }else {
                            Selected_Sub.add(checkBox.getText().toString());
                            System.out.println("Selected box  "+checkBox.getText().toString());
                        }
                    }
                });

            }
        }
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

    private void ischecked(){
        filterdeatils=new Holiday_Filters_DTO();

        if (zerostar.isChecked()){
            filterdeatils.setzero_star(0);
        }else {
            filterdeatils.setzero_star(6);
        }

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
        filterdeatils.setSelected_Sub(Selected_Sub);
    }


    private  void Onres(){
        if (filterdeatils!=null){
            if (filterdeatils.getzero_star()==0){
                zerostar.setChecked(true);
            }
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
