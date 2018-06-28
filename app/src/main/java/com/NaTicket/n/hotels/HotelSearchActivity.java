package com.NaTicket.n.hotels;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.NaTicket.n.hotels.pojo.Room;
import com.NaTicket.n.hotels.pojo.SelectionDetailsDTO;
import com.NaTicket.n.hotels.pojo.countriesListDTO;
import com.NaTicket.n.utils.BackActivity;
import com.NaTicket.n.utils.Util;
import com.google.gson.Gson;
import com.NaTicket.n.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class HotelSearchActivity extends BackActivity implements DatePickerDialog.OnDateSetListener,View.OnClickListener {
    LinearLayout checkIncal,checkOutcal;
    DatePickerDialog datePickerDialog ;
    int Year, Month, Day ;
    RecyclerView roomsView;
    ArrayList<Room> roomList;
    TextView totalRoomTv,totalAdultsTv,totalChildTv,cityTv,checkinTv,checkOutTv,searchHotelsTv;
    TextView domestic,international;
    LinearLayout whichCityTv;
    private int cityId;
    static final int DATE_DIALOG_ID = 1;
    static final int DATE_DIALOG_ID2 = 2;
    Spinner nationalityspinner;
    String HotelType="1";
    int cur = 0;
    Toolbar toolbar;
    Calendar calendar,c,cal1,cal2,cal3,cal4,cal5;
    com.NaTicket.n.hotels.pojo.countriesListDTO[] countriesListDTO;
    int d,m,y;
    Boolean Inside=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_search);

        checkIncal = (LinearLayout) findViewById(R.id.checkIncal);
        checkOutcal =(LinearLayout) findViewById(R.id.checkOutcal);
        roomsView = (RecyclerView) findViewById(R.id.roomsView);
        TextView addRoom = (TextView)findViewById(R.id.addRoom);
        totalRoomTv = (TextView)findViewById(R.id.totalRoomTv);
        totalAdultsTv=(TextView)findViewById(R.id.totalAdultsTv);
        totalChildTv=(TextView)findViewById(R.id.totalChildTv);
        whichCityTv=(LinearLayout)findViewById(R.id.whichCityTv);
        searchHotelsTv = (TextView)findViewById(R.id.searchHotelsTv);
        checkinTv =(TextView)findViewById(R.id.checkinTv);
        checkOutTv=(TextView)findViewById(R.id.checkOutTv);
        cityTv=(TextView)findViewById(R.id.cityTv);
        domestic= (TextView) findViewById(R.id.domestic);
        international= (TextView) findViewById(R.id.interantional);
        nationalityspinner = (Spinner)findViewById(R.id.nationalityspinner);


        TextView toolbartitle = (TextView) findViewById(R.id.toolbartitle);
        toolbartitle.setText("Hotels");
        ImageView ToolBar_Back= (ImageView) findViewById(R.id.backBtn);
        ToolBar_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Util.startHomeActivity(HotelSearchActivity.this);
            }
        });

        addRoom.setOnClickListener(this);
        whichCityTv.setOnClickListener(this);
        checkIncal.setOnClickListener(this);
        checkOutcal.setOnClickListener(this);
        searchHotelsTv.setOnClickListener(this);
        domestic.setOnClickListener(this);
        international.setOnClickListener(this);

        calendar = Calendar.getInstance();

        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);


        setCurrentDateOnView();

        String response = loadJSONFromAsset();

        if(response!=null) {
            InputStream stream = new ByteArrayInputStream(response.getBytes());
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(stream);
            countriesListDTO = gson.fromJson(reader, countriesListDTO[].class);
            ArrayList<String> countries = new ArrayList<>();
            ArrayList<String> mcountries = new ArrayList<>();

            for(int i=0;i<countriesListDTO.length;i++){
                countries.add(countriesListDTO[i].getNationality());
            }

            Collections.sort(mcountries);
            Set<String> hs = new HashSet<>();
            hs.addAll(countries);
            countries.clear();
            countries.addAll(hs);
            Collections.sort(countries);
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item,countries);
            dataAdapter.setDropDownViewResource(R.layout.spinner_item);
            nationalityspinner.setAdapter(dataAdapter);
            nationalityspinner.setSelection(getIndex(nationalityspinner,"Indian"));

        }

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getAssets().open("countries.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    @Override
    public void onDateSet(DatePickerDialog view, int Year, int Month, int Day) {
      if(cur == DATE_DIALOG_ID){

          checkinTv.setText(String.format("%02d", Day) + "-" + String.format("%02d", Month+1) + "-" + Year);

          d=Day;
          m=Month;
          y=Year;


          cal1=Calendar.getInstance();
          cal1.set(y,m,d);
          cal1.add(Calendar.DAY_OF_YEAR,1);
          Year = cal1.get(Calendar.YEAR);
          Month = cal1.get(Calendar.MONTH)+1;
          Day = cal1.get(Calendar.DAY_OF_MONTH);

          checkOutTv.setText(String.format("%02d", Day) + "-" + String.format("%02d", Month) + "-" + Year);

          onClick(checkOutcal);
          Toast.makeText(HotelSearchActivity.this, "Select Check Out Date", Toast.LENGTH_SHORT).show();

          //  Util.showMessage(this,Day + "-" + Month + "-" + Year);
        }
        else{
            checkOutTv.setText(String.format("%02d", Day) + "-" + String.format("%02d", Month+1) + "-" + Year);
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.addRoom:
                 Intent ip=new Intent(HotelSearchActivity.this,Rooms_Activity.class);
                 startActivityForResult(ip,3);
                break;
            case R.id.whichCityTv:
                Intent intent=new Intent(HotelSearchActivity.this,CitySearchActivity.class);
                intent.putExtra("hoteltype",HotelType);
                startActivityForResult(intent, 2);
                break;
            case R.id.checkIncal:
                cur = DATE_DIALOG_ID;
                datePickerDialog = DatePickerDialog.newInstance(HotelSearchActivity.this, Year, Month, Day);
                cal1 = Calendar.getInstance();
                cal1.add(Calendar.DAY_OF_YEAR, 0);
                cal2=Calendar.getInstance();
                cal2.add(Calendar.DAY_OF_YEAR,182);
                datePickerDialog.setMinDate(cal1);
                datePickerDialog.setMaxDate(cal2);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));
                datePickerDialog.setTitle("Select Check In Date");
                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");

                break;
            case R.id.checkOutcal:
                cur = DATE_DIALOG_ID2;
                datePickerDialog = DatePickerDialog.newInstance(HotelSearchActivity.this, Year, Month, Day);
                cal3 = Calendar.getInstance();
                cal3.add(Calendar.DAY_OF_YEAR, 1);
                cal4=Calendar.getInstance();
                cal4.add(Calendar.DAY_OF_YEAR,183);
                cal5=Calendar.getInstance();
                cal5.set(y,m,d);
                cal5.add(Calendar.DAY_OF_YEAR,1);
                datePickerDialog.setMinDate(cal5);
                datePickerDialog.setMaxDate(cal4);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));
                datePickerDialog.setTitle("Select Check Out Date");
                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");

                break;
            case R.id.searchHotelsTv:
                     if(validateValues()){
                       SelectionDetailsDTO selDetails = new SelectionDetailsDTO();
                       selDetails.setCheckIn(checkinTv.getText().toString());
                       selDetails.setCheckOut(checkOutTv.getText().toString());
                       selDetails.setNationality(nationalityspinner.getSelectedItem()+"");
                       selDetails.setNationalityid(getNationalityId(nationalityspinner.getSelectedItem()+""));
                       int days = (int) daysBetween(getDate(selDetails.getCheckIn()), getDate(selDetails.getCheckOut()));
                         selDetails.setNoofDays(days);
                         selDetails.setCityId(cityId);
                         selDetails.setCityName(cityTv.getText().toString());
                         selDetails.setHoteltype(HotelType);
                           if (!Inside){
                               Hotel_utils hotel_utils=new Hotel_utils(this);
                               hotel_utils.addPaxDetails("1~0~0~0","0~0~0~0","1","1","0","-1~-1~-1~-1~-1~-1~-1~-1");
                               Room newRoom = new Room();
                               newRoom.setRoommNo(1);
                               newRoom.setAdults(1);
                               newRoom.setChild(0);
                               roomList=new ArrayList<>();
                               roomList.add(newRoom);
                           }

                       Intent i = new Intent(HotelSearchActivity.this,AvailableHotelsActivity.class);
                       i.putExtra("roomsList",roomList);
                       i.putExtra("selDetails",selDetails);
                       startActivity(i);
                    }

               /* Intent i = new Intent(HotelSearchActivity.this,AddDetailsActivity.class);
                i.putExtra("roomsList",roomList);
                startActivity(i);*/


                break;
             case R.id.domestic:
                 domestic.setBackground(getResources().getDrawable(R.drawable.background_left));
                 domestic.setTextColor(getResources().getColor(R.color.white));
                 international.setBackground(getResources().getDrawable(R.drawable.background_right));
                 international.setTextColor(getResources().getColor(R.color.colorPrimary));
                 HotelType= "1";
                 cityTv.setText("Select City");
                 break;
            case R.id.interantional:

                international.setBackground(getResources().getDrawable(R.drawable.background_reverse_right));
                international.setTextColor(getResources().getColor(R.color.white));
                domestic.setBackground(getResources().getDrawable(R.drawable.background_reverse_left));
                domestic.setTextColor(getResources().getColor(R.color.colorPrimary));
                HotelType= "2";
                cityTv.setText("Select City");
                break;

        }
    }

    private static long daysBetween(Date one, Date two) { long difference = (one.getTime()-two.getTime())/86400000; return Math.abs(difference); }


    private Date getDate(String checkIn) {

        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(checkIn);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private boolean validateValues() {
        if(cityTv.getText().toString().matches("Select City") || cityTv.getText().toString().matches("")){
            Util.showMessage(this,"Select City");
            return false;
        }else if(checkinTv.getText().toString().matches("Check In") || checkinTv.getText().toString().matches("") ){
            Util.showMessage(this,"Select Check In Date");
            return false;
        }else if(checkOutTv.getText().toString().matches("Check Out") || checkOutTv.getText().toString().matches("") ) {
            Util.showMessage(this, "Select Check Out Date");
            return false;
        }  else if(getDateinMilli(checkOutTv.getText().toString()) <= getDateinMilli(checkinTv.getText().toString()) ) {
            Util.showMessage(this, "Check Out Date should be greater than Check In Date");
            return false;
        }
      return  true;

    }

    private long getDateinMilli(String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }


    private  long getdateinmonth(String format){
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
        Date date=null;

        try {
            date=sdf.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            if(data!=null) {
                cityTv.setText(data.getStringExtra("CITY_NAME"));
                cityId = data.getIntExtra("CITY_ID", 0);
                //Util.showMessage(this,cityId+"");
            }
        }else if (requestCode==3){
            if (data!=null){
                Inside=true;
                totalRoomTv.setText(data.getStringExtra("Rooms"));
                totalAdultsTv.setText(data.getStringExtra("Adults"));
                totalChildTv.setText(data.getStringExtra("Children"));
                roomList=(ArrayList<Room>) data.getSerializableExtra("roomList");
                //roomList=data.getStringExtra((ArrayList<Room>) getIntent().getSerializableExtra("roomList"));
            }
        }
    }


    private int getIndex(Spinner spinner, String myString){

        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        return index;
    }


    private String getNationalityId(String s) {
        String destId=null;
        for (int i=0;i<countriesListDTO.length;i++){
            if(s.matches(countriesListDTO[i].getNationality()))
                destId=countriesListDTO[i].getAlpha_2_code();
        }
        return destId;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Util.startHomeActivity(HotelSearchActivity.this);
        finish();
    }

    public void setCurrentDateOnView() {


////CheckIndate
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH) + 1;
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        checkinTv.setText(String.format("%02d", Day) + "-" + String.format("%02d", Month) + "-" + Year);
////CheckOutdate
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH) + 1;
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        checkOutTv.setText(String.format("%02d", Day) + "-" + String.format("%02d", Month) + "-" + Year);


        calendar = Calendar.getInstance();

////Setting calender instance back to current date
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        d=Day;
        m=Month;
        y=Year;

    }
}
