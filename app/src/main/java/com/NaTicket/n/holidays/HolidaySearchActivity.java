package com.NaTicket.n.holidays;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.NaTicket.n.R;
import com.NaTicket.n.holidays.pojo.DetailsDTO;
import com.NaTicket.n.holidays.pojo.SelectedHolidayDetailsDTO;
import com.NaTicket.n.serviceclasses.ServiceClasses;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;
import com.google.gson.Gson;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Calendar;

public class HolidaySearchActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,View.OnClickListener{
    LinearLayout checkIncal;
    Calendar calendar ;
    DatePickerDialog datePickerDialog ;
    int Year, Month, Day ;
    TextView checkinTv,searchHotelsTv,International,Domestic;
    Spinner categoryspinner,destspinner;
    DetailsDTO[] detailsDTO,detailsDTOcat;
    Toolbar toolbar;
    TextView ToolbarTitle;
    int categorytype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holiday_search);

        toolbar= (Toolbar) findViewById(R.id.toolbar_holidays);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ToolbarTitle= (TextView) findViewById(R.id.toolbar_title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        init();
        callDestinationDetails(1);
        callCategoriesDeatils(1);
        categorytype=1;
        setCurrentDateOnView();


    }

    private void callCategoriesDeatils(int i) {
        if(Util.isNetworkAvailable(getApplicationContext())) {
            ServiceClasses.getCategoriesDeails(HolidaySearchActivity.this, Constants.GETCATEGORIES+"categoryId="+i);
        }else{
            Util.showMessage(this, Constants.NO_INT_MSG);
        }
    }

    private void callDestinationDetails(int i) {
        if(Util.isNetworkAvailable(getApplicationContext())) {
            ServiceClasses.getDestinationDetails(HolidaySearchActivity.this, Constants.GETDESTINATIONPLACES+i);
        }else{
            Util.showMessage(this, Constants.NO_INT_MSG);
        }
    }

    private void init() {
        checkIncal = (LinearLayout) findViewById(R.id.checkIncal);
        searchHotelsTv = (TextView)findViewById(R.id.searchHotelsTv);
        checkinTv =(TextView)findViewById(R.id.checkinTv);
        categoryspinner =(Spinner)findViewById(R.id.categoryspinner);
        destspinner = (Spinner)findViewById(R.id.destspinner);
        International= (TextView) findViewById(R.id.internationalButton);
        Domestic= (TextView) findViewById(R.id.domesticButtom);
        checkIncal.setOnClickListener(this);
        searchHotelsTv.setOnClickListener(this);
        Domestic.setOnClickListener(this);
        International.setOnClickListener(this);
        //radio_group.setOnCheckedChangeListener(this);
        calendar = Calendar.getInstance();

        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                    toolbar.setBackgroundResource(R.color.colorPrimary);

                } else if (isShow) {
                    isShow = false;
                    toolbar.setBackgroundResource(R.color.Transparent);

                }
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int Year, int Month, int Day) {
            checkinTv.setText(String.format("%02d", Day) + "-" + String.format("%02d", Month+1) + "-" + Year);
            //  Util.showMessage(this,Day + "-" + Month + "-" + Year);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.checkIncal:
                datePickerDialog = DatePickerDialog.newInstance(HolidaySearchActivity.this, Year, Month, Day);
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DAY_OF_YEAR, 0);
                Calendar cal2=Calendar.getInstance();
                cal2.add(Calendar.DAY_OF_YEAR,182);
                datePickerDialog.setMinDate(cal);
                datePickerDialog.setMaxDate(cal2);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));
                datePickerDialog.setTitle("Select Check In Date");
                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
                break;

            case R.id.searchHotelsTv:
                if(validateValues()){
                    SelectedHolidayDetailsDTO selHolidayDetails = new SelectedHolidayDetailsDTO();
                    selHolidayDetails.setJourneyDate(checkinTv.getText().toString());
                    selHolidayDetails.setCategory_type(categorytype);
                    selHolidayDetails.setDestinationId(getDestinationID(destspinner.getSelectedItem().toString()));
                    selHolidayDetails.setCategoryId(getCatergoryId(categoryspinner.getSelectedItem().toString()));
                    selHolidayDetails.setCategoryName(categoryspinner.getSelectedItem().toString());

                    Intent i = new Intent(HolidaySearchActivity.this,AvailableHolidaysActivity.class);
                    i.putExtra("selHolidayDetails",selHolidayDetails);
                    startActivity(i);
                }
                break;

            case R.id.domesticButtom:
                Domestic.setBackground(getResources().getDrawable(R.drawable.background_left));
                Domestic.setTextColor(getResources().getColor(R.color.white));
                International.setBackground(getResources().getDrawable(R.drawable.background_right));
                International.setTextColor(getResources().getColor(R.color.colorPrimary));
                International.setText(getResources().getString(R.string.international));
                categorytype=1;
                callCategoriesDeatils(1);
             break;

            case R.id.internationalButton:
                International.setBackground(getResources().getDrawable(R.drawable.background_reverse_right));
                International.setTextColor(getResources().getColor(R.color.white));
                Domestic.setBackground(getResources().getDrawable(R.drawable.background_reverse_left));
                Domestic.setText(getResources().getString(R.string.domestic));
                Domestic.setTextColor(getResources().getColor(R.color.colorPrimary));
                categorytype=2;
                callCategoriesDeatils(2);
                break;

        }

    }

    private int getCatergoryId(String s) {
        int destId=0;
        for (int i=0;i<detailsDTOcat.length;i++){
            if(s.matches(detailsDTOcat[i].getName()))
                destId=detailsDTOcat[i].getId();
        }
        return destId;
    }

    private int getDestinationID(String s) {
        int destId=0;
        for (int i=0;i<detailsDTO.length;i++){
            if(s.matches(detailsDTO[i].getName()))
            destId=detailsDTO[i].getId();
        }
        return destId;
    }

    private boolean validateValues() {
        try {
            if (checkinTv.getText().toString().matches("Travel Date") || checkinTv.getText().toString().matches("")) {
                Util.showMessage(this, "Select Travel Date");
                return false;
            } else if (destspinner.getSelectedItem().toString() == null && destspinner.getSelectedItem().toString().equals("")) {
                Util.showMessage(this, "Select Destination");
                return false;
            } else if (categoryspinner.getSelectedItem().toString() == null && categoryspinner.getSelectedItem().toString().equals("")) {
                Util.showMessage(this, "Select Category");
                return false;
            }

        } catch(Exception e){

        }
        return true;

    }
    public void getDestinationResponse(String response) {
        if(response!=null) {
            InputStream stream = new ByteArrayInputStream(response.getBytes());
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(stream);
            detailsDTO = gson.fromJson(reader, DetailsDTO[].class);
            if(detailsDTO!=null && detailsDTO.length>0) {
                ArrayList<String> destinations = new ArrayList<>();
                destinations.add(0,"ALL");
                for (int i=0;i<detailsDTO.length;i++){
                    destinations.add(detailsDTO[i].getName());
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item,destinations );
                dataAdapter.setDropDownViewResource(R.layout.spinner_item);
                destspinner.setAdapter(dataAdapter);
            }
        }
    }

    public void getCategoriesResponse(String response) {
        if(response!=null) {
            InputStream stream = new ByteArrayInputStream(response.getBytes());
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(stream);
            detailsDTOcat = gson.fromJson(reader, DetailsDTO[].class);
            if(detailsDTOcat!=null && detailsDTOcat.length>0) {
                ArrayList<String> destinations = new ArrayList<>();
                destinations.add(0,"ALL");
                for (int i=0;i<detailsDTOcat.length;i++){
                    destinations.add(detailsDTOcat[i].getName());
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item,destinations);
                dataAdapter.setDropDownViewResource(R.layout.spinner_item);
                categoryspinner.setAdapter(dataAdapter);
            }
        }



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Util.startHomeActivity(HolidaySearchActivity.this);
        finish();
    }


    public void setCurrentDateOnView() {


////CheckIndate
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH) + 1;
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        checkinTv.setText(String.format("%02d", Day) + "-" + String.format("%02d", Month) + "-" + Year);

        calendar = Calendar.getInstance();

////Setting calender instance back to current date
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);


    }
}
