package com.NaTicket.n.holidays;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.NaTicket.n.R;
import com.NaTicket.n.common.pojo.Currency_Utils;
import com.NaTicket.n.holidays.adapters.HolidayRoomsAdapter;
import com.NaTicket.n.holidays.pojo.AvailableHolidayPackagesDTO;
import com.NaTicket.n.holidays.pojo.SelectedHolidayDetailsDTO;
import com.NaTicket.n.hotels.pojo.Room;
import com.NaTicket.n.utils.BackActivity;
import com.NaTicket.n.utils.Util;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CheckAvailabilityActivity extends BackActivity implements DatePickerDialog.OnDateSetListener,View.OnClickListener,RadioGroup.OnCheckedChangeListener {

    ImageView checkIncal;
    Calendar calendar ;
    DatePickerDialog datePickerDialog ;
    int Year, Month, Day ;
    RecyclerView roomsView;
    ArrayList<Room> roomList;
    HolidayRoomsAdapter mAdapter;
    TextView totalRoomTv,totalAdultsTv,totalChildTv,checkinTv,searchHotelsTv,ratesfromtoTv,packageTv,rateTv;
    AvailableHolidayPackagesDTO SelHoliday;
    private SelectedHolidayDetailsDTO selDetails;
    RadioGroup radioGroup;
    RadioGroup.LayoutParams rprms;
    Toolbar toolbar;
    int pos=0;
    String ValidFromdate,ValidTillDate;
    String Total;
    Currency_Utils currency_utils;
    String Currency_Symbol;
    double currValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_availability);
        SelHoliday = (AvailableHolidayPackagesDTO) getIntent().getSerializableExtra("SelHoliday");
        selDetails = (SelectedHolidayDetailsDTO) getIntent().getSerializableExtra("selDetails");
        TextView toolbartitle=(TextView)findViewById(R.id.toolbartitle);
        inittoolbar();
        toolbartitle.setText("Check Availability");
        currency_utils=new Currency_Utils(this);
         currValue= Double.parseDouble(currency_utils.getCurrencyValue("Currency_Value"));
         Currency_Symbol=currency_utils.getCurrencyValue("Currency_Symbol");

        checkIncal = (ImageView) findViewById(R.id.checkIncal);

        roomsView = (RecyclerView) findViewById(R.id.roomsView);
        TextView addRoom = (TextView)findViewById(R.id.addRoom);
        totalRoomTv = (TextView)findViewById(R.id.totalRoomTv);
        totalAdultsTv=(TextView)findViewById(R.id.totalAdultsTv);
        totalChildTv=(TextView)findViewById(R.id.totalChildTv);
        ratesfromtoTv=(TextView)findViewById(R.id.ratesfromtoTv);
        radioGroup= (RadioGroup) findViewById(R.id.radiogroup);
        rateTv= (TextView) findViewById(R.id.rateTv);


        String ValidFrom=SelHoliday.getValidFrom();
        String ValidTo=SelHoliday.getValidTo();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

        Date FromDate = null,Todate=null;
        try {
            FromDate=dateFormat.parse(ValidFrom);
            Todate=dateFormat.parse(ValidTo);
        } catch (ParseException e) {
            e.printStackTrace();
        }
         ValidFromdate=output.format(FromDate);
         ValidTillDate=output.format(Todate);


        //packageTv=(TextView)findViewById(R.id.packageTv);
        ratesfromtoTv.setText("From "+ValidFromdate+" To "+ValidTillDate);
        double TotalFare=Double.parseDouble(SelHoliday.getFareDetails().get(0).getAdultFare())*currValue;

        rateTv.setText(Currency_Symbol+getprice(TotalFare)+"");

        searchHotelsTv = (TextView)findViewById(R.id.searchHotelsTv);
        checkinTv =(TextView)findViewById(R.id.checkinTv);
        checkinTv.setText(selDetails.getJourneyDate());
        roomList = new ArrayList();

        mAdapter = new HolidayRoomsAdapter(this,roomList);
        addNewRoom();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        roomsView.setLayoutManager(mLayoutManager);
        roomsView.setItemAnimator(new DefaultItemAnimator());
        roomsView.setAdapter(mAdapter);
        addRoom.setOnClickListener(this);
        checkIncal.setOnClickListener(this);
        searchHotelsTv.setOnClickListener(this);

        calendar = Calendar.getInstance();

        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);


        for(int i = 0; i<SelHoliday.getFareDetails().size(); i++){
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(SelHoliday.getFareDetails().get(i).getRoomCategory()+" Package starting from "+SelHoliday.getFareDetails().get(i).getFromCity());
            radioButton.setId(i);
            rprms= new RadioGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            radioGroup.addView(radioButton, rprms);

            if (i==0){
                radioButton.setChecked(true);
                updateTotalRate(0);
            }
        }

        radioGroup.setOnCheckedChangeListener(this);

 /*     radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

          }
      });*/

    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        System.out.println("Checkedid ::: "+checkedId);
        pos=checkedId;
        updateTotalRate(pos);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int Year, int Month, int Day) {
            checkinTv.setText(String.format("%02d", Day) + "-" + String.format("%02d", Month+1) + "-" + Year);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.addRoom:
                  addNewRoom();
                break;

            case R.id.checkIncal:
                datePickerDialog = DatePickerDialog.newInstance(CheckAvailabilityActivity.this, Year, Month, Day);
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DAY_OF_YEAR, 0);
                Calendar cal2=Calendar.getInstance();
                cal2.add(Calendar.DAY_OF_YEAR,182);
                datePickerDialog.setMinDate(cal);
                datePickerDialog.setMaxDate(cal2);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(getResources().getColor(R.color.colorAccent));
                //datePickerDialog.setTitle("Select CheckIn Date");
                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");

                break;

            case R.id.searchHotelsTv:
                     if(validateValues()){
                        selDetails.setJourneyDate(checkinTv.getText().toString());
                       Intent i = new Intent(CheckAvailabilityActivity.this,ReviewHolidayActivity.class);
                        i.putExtra("roomList",roomList);
                        i.putExtra("SelHoliday",SelHoliday);
                        i.putExtra("selDetails",selDetails);
                        SelHoliday.setTotalAmount(Total);
                       startActivity(i);
                    }

               /* Intent i = new Intent(HotelSearchActivity.this,AddDetailsActivity.class);
                i.putExtra("roomsList",roomList);
                startActivity(i);*/


                break;

        }
    }
    private static long daysBetween(Date one, Date two) { long difference = (one.getTime()-two.getTime())/86400000; return Math.abs(difference); }

    private Date getDate(String checkIn) {

        DateFormat format = new SimpleDateFormat("dd-mm-yyyy", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(checkIn);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private boolean validateValues() {
        if(checkinTv.getText().toString().matches("Select Travel Date") || checkinTv.getText().toString().matches("") ){
            Util.showMessage(this,"Select Travel Date");
            return false;
        }
      return  true;

    }

    private long getDateinMilli(String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        Date date = null;
        try {
            date = sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }



    private void addNewRoom() {
        if (roomList.size()<4){
        Room newRoom = new Room();
        newRoom.setRoommNo(roomList.size());
        newRoom.setAdults(1);
        newRoom.setChild(0);
        roomList.add(newRoom);
        mAdapter.notifyDataSetChanged();
        updateViews();
        }else {
            Util.showMessage(this,"Only 4 Rooms can be Selected");
        }
    }

    private void updateViews() {
        totalRoomTv.setText("Room : "+roomList.size());
        updateTotalNoofAdults();
        updateTotalNoofChild();

    }


    public void removeRoom(int position) {

        for(int i=0;i<roomList.size();i++){
            if(i==position){
                roomList.remove(position);
            }
        }
        mAdapter.notifyDataSetChanged();
        updateViews();

    }

    public void updateTotalNoofAdults() {
        int adultCount=0;
        for(int i=0;i<roomList.size();i++){
           adultCount=adultCount+roomList.get(i).getAdults();
        }
        totalAdultsTv.setText("Adults : "+adultCount);
        updateTotalRate(pos);
    }

    public void updateTotalNoofChild() {
        int childCount=0;
        for(int i=0;i<roomList.size();i++){
            childCount=childCount+roomList.get(i).getChild();
        }
        totalChildTv.setText("Child : "+childCount);
        updateTotalRate(pos);
    }


    public void updateTotalRate(int pos){
        Double roomprice = null;
        roomprice=(Double.valueOf(SelHoliday.getFareDetails().get(pos).getAdultFare())*updateTotalNoofAdults2()+
                   Double.valueOf(SelHoliday.getFareDetails().get(pos).getChildFare())*updateTotalNoofChild2());
        Total= String.valueOf(roomprice);
        rateTv.setText(Currency_Symbol+getprice(roomprice*currValue)+"");
    }

    public int updateTotalNoofAdults2() {
        int adultCount=0;
        for(int i=0;i<roomList.size();i++){
            adultCount=adultCount+roomList.get(i).getAdults();
        }
        return adultCount;
    }
    public int updateTotalNoofChild2() {
        int childCount=0;
        for(int i=0;i<roomList.size();i++){
            childCount=childCount+roomList.get(i).getChild();
        }
        return childCount;
    }

    public int getprice(double fares){
        double dAbs = Math.abs(fares);
        int amt2 = (int) dAbs;
        double result = dAbs - (double) amt2;
        int finalrate;
        if (result<0.5){
            finalrate= (int) Math.floor(fares);
        }else {
            finalrate= (int) Math.ceil(fares);
        }
        return finalrate;
    }

}
