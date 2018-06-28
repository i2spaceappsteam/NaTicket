package com.NaTicket.n.buses;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.NaTicket.n.R;
import com.NaTicket.n.utils.Util;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Buses_MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,View.OnClickListener{

    TextView From, TO, Search,Oneway,Roundtrip;
    ImageView Flip;
    String SourceID, DestinationId;
    Boolean flip = true;
    String name;
    private int checkinyear;
    private int checkinmonth;
    private int checkinday;
    private String checkoutyear;
    private String checkoutmonth;
    private String checkoutday;
    String str;
    TextView inmonth, outmonth, inday, outday, inyear, outyear;
    TextView Nooffers;
    TextView currentdate, currentmonth, currentweek;
    TextView ReturnDate, ReturnMonth, ReturnWeek, Tx_ReturnJournet;
    TextView OriginError, DistinationError, SameSourceAndDestinationTextview;
    LinearLayout date, Returndate;
    static final int DATE_PICKER_ID1 = 111;
    static final int DATE_PICKER_ID2 = 222;
    String selectedDate, dayofjourney, OnwardSelectedDate;
    String Return_Date_jurney, Return_dayofjourney;
    String Names;
    int OnwardDate, OnwardMonth, OnwardYear;
    SharedPreferences.Editor editor;
    SharedPreferences pref;
    String AgentId, EMAIL, Name, ClientId;
    NavigationView navigationView;

    TextView clientname, clientbal;
    ImageView clientlogo;
    String data, clientid, parentid, DecriptId, AgentLOGIN = "No", UserLOGIN = "No";

    String IsReturnSelect = "Yes";
    String PassengerIsReturnselected = "No";
    String currentVersion;
    String CurrentDateString;

    FragmentManager fm;
    FragmentTransaction ft;
    String fromname, toname;
    String FROMNAME, TONAME;
    Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<String> Offerslist = new ArrayList<>();
    //Calendar cal,cal1,cal2,cal3,calendar,calendar1;

    int cur = 0;
    DatePickerDialog datePickerDialog;
    static final int DATE_DIALOG_ID = 1;
    static final int DATE_DIALOG_ID2 = 2;
    Calendar  calendar,cal1,cal2,cal4,cal5;
    TextView  onward_date_tv,return_date_tv;
    int d,m,y;
    int Year, Month,Day ;
    String arrival,distination;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_main_layout);
        isConnected();

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        initView();

        SharedPreferences prefer = getApplicationContext().getSharedPreferences("Filter", MODE_PRIVATE);
        prefer.edit().remove("ACSELECTED").apply();
        prefer.edit().remove("NonACSELECTED").apply();
        prefer.edit().remove("SleeperSELECTED").apply();
        prefer.edit().remove("NonSleeperSELECTED").apply();
        prefer.edit().remove("Filter_OperaterId").apply();
        prefer.edit().remove("Filter_OperaterName").apply();
        prefer.edit().remove("FilterBoadingId").apply();
        prefer.edit().remove("FilterBoadingLocation").apply();
        prefer.edit().remove("FilterDropingId").apply();
        prefer.edit().remove("FilterDropinglocation").apply();
        prefer.edit().remove("FILTER").apply();
        prefer.edit().remove("JourneyDate").apply();
        prefer.edit().remove("DayofJourney").apply();

        SharedPreferences preference1 = getApplicationContext().getSharedPreferences("JourneyDetails", MODE_PRIVATE);
        preference1.edit().remove("IsReturnSelected").apply();
        preference1.edit().remove("IsReturnYes").apply();
        preference1.edit().remove("ReturnTripDetails").apply();
        preference1.edit().remove("ReturnNumberOfSeats").apply();

        SharedPreferences preferences = getSharedPreferences("OnwardJourneyDetails", MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.clear();
        edit.apply();
        SharedPreferences preferencee = getSharedPreferences("JourneyDetails", MODE_PRIVATE);
        SharedPreferences.Editor editorr = preferencee.edit();
        editorr.clear();
        editorr.apply();

        try {
            currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        /*final SharedPreferences preference = getApplicationContext().getSharedPreferences("Sources", MODE_PRIVATE);
        fromname = preference.getString("FROMNAME", null);
        arrival = preference.getString("FROMID", null);
        toname = preference.getString("TONAME", null);
        distination = preference.getString("TOID", null);*/

        setCurrentDateOnView();





    }


    public void initView(){
        Oneway= (TextView) findViewById(R.id.oneway);
        Roundtrip= (TextView) findViewById(R.id.roundtrip);
        onward_date_tv = (TextView) findViewById(R.id.departonNameBus);
        return_date_tv = (TextView) findViewById(R.id.ArrivalNameBus);
        date = (LinearLayout) findViewById(R.id.departLayoutBus);
        Returndate = (LinearLayout) findViewById(R.id.toLinearLayoutBus);

        OriginError = (TextView) findViewById(R.id.OriginCityError);
        DistinationError = (TextView) findViewById(R.id.DestinationCityError);
        SameSourceAndDestinationTextview = (TextView) findViewById(R.id.SameSourceandDestination);
        From = (TextView) findViewById(R.id.FROM);
        TO = (TextView) findViewById(R.id.TO);
        Flip = (ImageView) findViewById(R.id.FLIP);
        Search = (TextView) findViewById(R.id.search);


        Oneway.setOnClickListener(this);
        Roundtrip.setOnClickListener(this);
        From.setOnClickListener(this);
        TO.setOnClickListener(this);
        date.setOnClickListener(this);
        Returndate.setOnClickListener(this);
        Flip.setOnClickListener(this);
        Search.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        SharedPreferences preference;
        SharedPreferences.Editor editor;
        Intent intent;

        switch (v.getId()) {


            case R.id.departLayoutBus:
                cur = DATE_DIALOG_ID;
                datePickerDialog = DatePickerDialog.newInstance(Buses_MainActivity.this, Year, Month, Day);
                cal1 = Calendar.getInstance();
                cal1.add(Calendar.DAY_OF_YEAR, 0);
                cal2=Calendar.getInstance();
                cal2.add(Calendar.DAY_OF_YEAR,45);
                datePickerDialog.setMinDate(cal1);
                datePickerDialog.setMaxDate(cal2);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));
                datePickerDialog.setTitle("Select Onward Date");
                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");

                break;
            case R.id.toLinearLayoutBus:
                cur = DATE_DIALOG_ID2;
                datePickerDialog = DatePickerDialog.newInstance(Buses_MainActivity.this, Year, Month, Day);
                cal4=Calendar.getInstance();
                cal4.add(Calendar.DAY_OF_YEAR,45);
                cal5=Calendar.getInstance();
                cal5.set(y,m,d);
                cal5.add(Calendar.DAY_OF_YEAR,0);
                datePickerDialog.setMinDate(cal5);
                datePickerDialog.setMaxDate(cal4);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));
                datePickerDialog.setTitle("Select Return Date");
                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
                break;

            case R.id.oneway:
                Returndate.setVisibility(View.GONE);
                Oneway.setBackground(getResources().getDrawable(R.drawable.background_left));
                Roundtrip.setBackground(getResources().getDrawable(R.drawable.background_right));
                Oneway.setTextColor(getResources().getColor(R.color.white));
                Roundtrip.setTextColor(getResources().getColor(R.color.colorPrimary));
                IsReturnSelect = "No";
                preference = getApplicationContext().getSharedPreferences("JourneyDetails", MODE_PRIVATE);
                editor = preference.edit();
                editor.putString("PassengerIsReturnselected", IsReturnSelect);
                editor.apply();
                break;

            case R.id.roundtrip:
                Returndate.setVisibility(View.VISIBLE);
                Oneway.setBackground(getResources().getDrawable(R.drawable.background_reverse_left));
                Roundtrip.setBackground(getResources().getDrawable(R.drawable.background_reverse_right));
                Oneway.setTextColor(getResources().getColor(R.color.colorPrimary));
                Roundtrip.setTextColor(getResources().getColor(R.color.white));
                //Returndate.setVisibility(View.VISIBLE);
                IsReturnSelect = "Yes";
                preference = getApplicationContext().getSharedPreferences("JourneyDetails", MODE_PRIVATE);
                editor = preference.edit();
                editor.putString("PassengerIsReturnselected", IsReturnSelect);
                editor.apply();
                break;

            case R.id.FROM:
                intent = new Intent(Buses_MainActivity.this, SearchCityBus.class);
                intent.putExtra("SourceCity",true);
                startActivityForResult(intent,2);
                break;
            case R.id.TO:
                intent = new Intent(Buses_MainActivity.this, SearchCityBus.class);
                intent.putExtra("SourceCity",false);
                startActivityForResult(intent,3);
                break;
            case R.id.FLIP:
                if (flip == true) {
                    From.setText(toname);
                    TO.setText(fromname);
                    Names = toname + " To " + fromname;
                    SourceID = distination;
                    DestinationId = arrival;
                    FROMNAME = toname;
                    TONAME = fromname;
                    System.out.println("flip_true" + SourceID + DestinationId);
                    flip = false;
                } else if (flip == false) {
                    From.setText(fromname);
                    TO.setText(toname);
                    Names = fromname + " To " + toname;
                    SourceID = arrival;
                    DestinationId = distination;
                    FROMNAME = fromname;
                    TONAME = toname;
                    System.out.println("flip_false" + SourceID + DestinationId);
                    flip = true;
                }

                break;
            case R.id.search:

                selectedDate=onward_date_tv.getText().toString();
                if(IsReturnSelect.equals("Yes")){
                    Return_Date_jurney=return_date_tv.getText().toString();
                }else{
                    Return_Date_jurney=null;
                }

                if (fromname == null || toname == null) {

                    if (fromname == null) {
                        OriginError.setVisibility(View.VISIBLE);
                        DistinationError.setVisibility(View.GONE);
                        SameSourceAndDestinationTextview.setVisibility(View.GONE);
                    } else if (toname == null) {
                        OriginError.setVisibility(View.GONE);
                        DistinationError.setVisibility(View.VISIBLE);
                        SameSourceAndDestinationTextview.setVisibility(View.GONE);
                    }

                } else if (fromname.equalsIgnoreCase(toname)) {
                    OriginError.setVisibility(View.GONE);
                    DistinationError.setVisibility(View.GONE);
                    SameSourceAndDestinationTextview.setVisibility(View.VISIBLE);
                }else if (Return_Date_jurney!=null && getDateinMilli(selectedDate).after(getDateinMilli(Return_Date_jurney))) {
                    Util.showMessage(Buses_MainActivity.this, "CheckOut Date should be greater than CheckIn Date");

                } else {
                    preference = getApplicationContext().getSharedPreferences("JourneyDetails", MODE_PRIVATE);
                    editor = preference.edit();
                    editor.putString("JourneyDate", selectedDate);
                    editor.putString("ARRIVAL_ID", SourceID);
                    editor.putString("DESTINATION_ID", DestinationId);
                    editor.putString("JOURNEY_DATE", selectedDate);
                    editor.putString("DAY_OF_JOURNEY", selectedDate);
                    editor.putString("TITLE_BAR", Names);
                    editor.putString("ReturnTripTITLE_BAR", Names);
                    editor.putString("PassengerRETURN_DATE", Return_Date_jurney);
                    editor.putString("FROMNAME", FROMNAME);
                    editor.putString("TONAME", TONAME);
                    editor.putString("PassengerIsReturnselected", IsReturnSelect);
                    editor.putString("IsReturnYes", "No");
                    editor.apply();




                    preference = getApplication().getSharedPreferences("Sources", Context.MODE_PRIVATE);
                    editor = preference.edit();
                    editor.putString("FROMID", SourceID);
                    editor.putString("FROMNAME", FROMNAME);
                    editor.putString("TOID", DestinationId);
                    editor.putString("TONAME", TONAME);
                    editor.apply();

                    intent = new Intent(Buses_MainActivity.this, SearchActivity.class);
                    /*intent.putExtra("ARRIVAL_ID", ArrivalID);
                    intent.putExtra("DESTINATION_ID", DestinationId);
                    intent.putExtra("JOURNEY_DATE", selectedDate);
                    intent.putExtra("DAY_OF_JOURNEY",dayofjourney);
                    intent.putExtra("TITLE_BAR",Names);*/


                    System.out.println("date***" + selectedDate);
                    startActivity(intent);
                }

                break;

        }
    }




    private Date getDateinMilli(String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /////On touch motion Event ////
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            return super.dispatchTouchEvent(ev);
        } catch (Exception e) {
            return false;
        }
    }




    @Override
    public void onDateSet(DatePickerDialog view, int Year, int Month, int Day) {
        if(cur == DATE_DIALOG_ID){

            onward_date_tv.setText(String.format("%02d", Day) + "-" + String.format("%02d", Month+1) + "-" + Year);

            d=Day;
            m=Month;
            y=Year;

            cal1=Calendar.getInstance();
            cal1.set(y,m,d);
            cal1.add(Calendar.DAY_OF_YEAR,1);
            Year = cal1.get(Calendar.YEAR);
            Month = cal1.get(Calendar.MONTH)+1;
            Day = cal1.get(Calendar.DAY_OF_MONTH);

            return_date_tv.setText(String.format("%02d", Day) + "-" + String.format("%02d", Month) + "-" + Year);

            if (IsReturnSelect.equals("Yes")){
                onClick(Returndate);
                Util.showMessage(this, "Select Return Date");
            }
        }
        else{
            return_date_tv.setText(String.format("%02d", Day) + "-" + String.format("%02d", Month+1) + "-" + Year);
        }
    }


    public void setCurrentDateOnView() {


////CheckIndate
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH) + 1;
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        onward_date_tv.setText(String.format("%02d", Day) + "-" + String.format("%02d", Month) + "-" + Year);

////CheckOutdate
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH) + 1;
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        return_date_tv.setText(String.format("%02d", Day) + "-" + String.format("%02d", Month) + "-" + Year);
        calendar = Calendar.getInstance();
        Return_Date_jurney=return_date_tv.getText().toString();
////Setting calender instance back to current date
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        d=Day;
        m=Month;
        y=Year;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {



        if(resultCode == 2){
            fromname = data.getStringExtra("CITY_NAME");
            arrival = data.getStringExtra("CITY_ID");
            SourceID = arrival;
            FROMNAME = fromname;
            From.setText(fromname);
        }
        if (resultCode == 3) {
            toname = data.getStringExtra("CITY_NAME");
            distination = data.getStringExtra("CITY_ID");
            DestinationId = distination;
            TONAME = toname;
            TO.setText(toname);
        }

        if (fromname!=null&&toname!=null){
            Names = fromname + " To " + toname;
            System.out.println("Route_Bus : "+Names);
        }
    }



    public void isConnected(){
        if(Util.isNetworkAvailable(getApplicationContext())) {

        }else{
            Util.alertDialogShow(this,"Please Check Your Internet Connection");
        }
    }





    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Util.startHomeActivity(Buses_MainActivity.this);
        finish();
    }



}
