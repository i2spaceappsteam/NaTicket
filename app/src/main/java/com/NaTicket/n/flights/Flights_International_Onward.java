package com.NaTicket.n.flights;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.NaTicket.n.flights.pojo.FareDetailsDTO;
import com.NaTicket.n.flights.pojo.Flight_Filters_DTO;
import com.NaTicket.n.loginpackage.pojo.Login_utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.NaTicket.n.R;
import com.NaTicket.n.common.pojo.Currency_Utils;
import com.NaTicket.n.flights.adpaters.International_Onward_Adapter;
import com.NaTicket.n.flights.pojo.Flight_Utils;
import com.NaTicket.n.flights.pojo.Flights_Avalability_DTO;
import com.NaTicket.n.flights.pojo.InternationalFlightsDTO;
import com.NaTicket.n.flights.pojo.SelectedFlightDetailsDTO;
import com.NaTicket.n.serviceclasses.ServiceClasses;
import com.NaTicket.n.utils.BackActivity;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Nagarjuna on 31-12-2017.
 */

public class Flights_International_Onward extends BackActivity {
    RecyclerView flights_View;
    SelectedFlightDetailsDTO selDetails;
    TextView sort,filter;
    String UserId, User_agentid,UserType;
    ProgressDialog mProgressDialog;
    Flights_Avalability_DTO flights_main_dto;
    International_Onward_Adapter flights_adapter;
    InternationalFlightsDTO SelOnwardFlight;
    double GrandTotal;
    TextView hotels,price,star_rating;
    String Tags;
    boolean FilterTrue=false;
    Flight_Filters_DTO filter_details;
    Boolean Isvalid=false;
    Currency_Utils currency_utils;
    String Currency_Symbol;
    double Curr_Value;
    Login_utils login_utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flights_onward);
        login_utils=new Login_utils(this);
        selDetails= (SelectedFlightDetailsDTO) getIntent().getSerializableExtra("selDetails");
        initviews();
        inittoolbar();
        TextView toolbartitle=(TextView)findViewById(R.id.toolbartitle);
        toolbartitle.setText(selDetails.getFrom_City_ID()+" -> "+selDetails.getTo_City_ID());
        currency_utils=new Currency_Utils(this);
        Curr_Value= Double.parseDouble(currency_utils.getCurrencyValue("Currency_Value"));
        Currency_Symbol=currency_utils.getCurrencyValue("Currency_Symbol");

        sortfilter();
        getLoginPreferences();
    }

    private void initviews(){
        flights_View= (RecyclerView) findViewById(R.id.flightsview);
        sort= (TextView) findViewById(R.id.sort);
        filter= (TextView) findViewById(R.id.filter);
    }

    private void getLoginPreferences() {
        if (login_utils.getUserDetails(Constants.USERTYPE).equals("6")) {
            UserId = "6";
            UserType = "User";
            User_agentid =login_utils.getUserDetails(Constants.USERID);
            callFlightsData();

        } else if (login_utils.getUserDetails(Constants.USERTYPE).equals("4")) {
            UserId = "4";
            UserType = "Agent";
            User_agentid =login_utils.getUserDetails(Constants.USERID);
            callFlightsData();
        } else {
            UserId = "5";
            UserType = "Guest";
            User_agentid = null;
            callFlightsData();
        }
    }

    private void callFlightsData(){
        showProgressDialog("Fetching Flights",this);
        if(Util.isNetworkAvailable(getApplicationContext())) {
            String url;
            if (selDetails.getReturn_date()!= null) {
                url = Constants.AVAILABLEFLIGHTS + "source=" + selDetails.getFrom_City_ID() + "&destination=" + selDetails.getTo_City_ID() +
                        "&journeyDate=" + selDetails.getOnward_date() + "&tripType=" + selDetails.getTrip_Type() + "&flightType=" + selDetails.getFlight_Type() +
                        "&adults=" + selDetails.getadult_count() + "&children=" + selDetails.getchild_count() + "&infants=" + selDetails.getinfant_count() +
                        "&travelClass=" + selDetails.getClass_type_value() + "&userType=" + UserId + "&user=" + User_agentid + "&returnDate=" + selDetails.getReturn_date();//+RETURNDate
            } else {
                url =Constants.AVAILABLEFLIGHTS + "source=" + selDetails.getFrom_City_ID() + "&destination=" + selDetails.getTo_City_ID() +
                        "&journeyDate=" + selDetails.getOnward_date() + "&tripType=" + selDetails.getTrip_Type() + "&flightType=" + selDetails.getFlight_Type() +
                        "&adults=" + selDetails.getadult_count() + "&children=" + selDetails.getchild_count() + "&infants=" + selDetails.getinfant_count() +
                        "&travelClass=" + selDetails.getClass_type_value() + "&userType=" + UserId + "&user=" + User_agentid + "&returnDate="+selDetails.getOnward_date();//+RETURNDate
            }
            ServiceClasses.getAvaliableFlights(Flights_International_Onward.this,url);
            Log.v("FlightsAvailablity", url);
        }else{
            Util.showMessage(this, Constants.NO_INT_MSG);
        }
    }

    private void sortfilter(){
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BottomSheetDialog dialog=new BottomSheetDialog(Flights_International_Onward.this);
                dialog.setContentView(R.layout.hotel_search_sorting);

                hotels= (TextView) dialog.findViewById(R.id.hotels);
                star_rating= (TextView) dialog.findViewById(R.id.star_rating);
                price= (TextView) dialog.findViewById(R.id.price);

                hotels.setText("Airline");
                star_rating.setText("Time");
                price.setText("Price");

                hotels.setTag("Asc");
                star_rating.setTag("Asc");
                price.setTag("Asc");

                if (Tags!=null){
                    if (Tags.equals("TitleASC")){
                        sortAirlineColor();
                        hotels.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                    }else if (Tags.equals("TitleDSC")){
                        sortAirlineColor();
                        hotels.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up, 0);
                    }

                    if (Tags.equals("DURASC")){
                        sortTimeColor();
                        star_rating.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                    }else if (Tags.equals("DURDSC")){
                        sortTimeColor();
                        star_rating.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up, 0);
                    }

                    if (Tags.equals("FareASC")){
                        sortPriceColor();
                        price.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                    }else if (Tags.equals("FareDSC")){
                        sortPriceColor();
                        price.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up, 0);
                    }
                }
                hotels.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sortAirlineColor();
                        hotels.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        star_rating.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                        price.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                        if (hotels.getTag().toString().equals("Asc")){
                            hotels.setTag("Desc");
                            Tags="TitleASC";
                            ArrayList<InternationalFlightsDTO> H=flights_adapter.sortByNameAsc();
                            flights_adapter.refreshList(H);
                            hotels.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                        }else {
                            hotels.setTag("Asc");
                            Tags="TitleDSC";
                            ArrayList<InternationalFlightsDTO> h=flights_adapter.sortByNameDesc();
                            flights_adapter.refreshList(h);
                            hotels.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up, 0);
                        }
                    }
                });

                star_rating.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sortTimeColor();
                        hotels.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        star_rating.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                        price.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                        if (star_rating.getTag().equals("Asc")){
                            star_rating.setTag("Desc");
                            Tags="DURASC";
                            ArrayList<InternationalFlightsDTO> h=flights_adapter.sortByTimeAsc();
                            flights_adapter.refreshList(h);
                            star_rating.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.arrow_down,0);
                        }else{
                            star_rating.setTag("Asc");
                            Tags="DURDSC";
                            ArrayList<InternationalFlightsDTO> h=flights_adapter.sortByTimeDesc();
                            flights_adapter.refreshList(h);
                            star_rating.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.arrow_up,0);
                        }
                    }
                });


                price.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sortPriceColor();
                        hotels.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        star_rating.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                        price.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                        if (price.getTag().toString().equals("Asc")){
                            price.setTag("Desc");
                            Tags="FareASC";
                            ArrayList<InternationalFlightsDTO> h=flights_adapter.sortByPriceAsc();
                            flights_adapter.refreshList(h);
                            price.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.arrow_down,0);
                        }else {
                            price.setTag("Asc");
                            Tags="FareDSC";
                            ArrayList<InternationalFlightsDTO> h=flights_adapter.sortByPriceDesc();
                            flights_adapter.refreshList(h);
                            price.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.arrow_up,0);
                        }
                    }
                });
                dialog.show();
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextActivity = new Intent(Flights_International_Onward.this, Int_Onward_Flight_Filters_Activity.class);
                if (!FilterTrue) {
                    Tags=null;
                    nextActivity.putExtra("Int_Onward_list", flights_main_dto.getInternationalFlights());
                    nextActivity.putExtra("Filteredlist",filter_details);
                }
                startActivityForResult(nextActivity,1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    public void sortAirlineColor(){
        hotels.setTextColor(getResources().getColor(R.color.colorAccent));
        star_rating.setTextColor(getResources().getColor(R.color.white));
        price.setTextColor(getResources().getColor(R.color.white));
    }
    public void sortTimeColor(){
        hotels.setTextColor(getResources().getColor(R.color.white));
        star_rating.setTextColor(getResources().getColor(R.color.colorAccent));
        price.setTextColor(getResources().getColor(R.color.white));
    }
    public void sortPriceColor(){
        hotels.setTextColor(getResources().getColor(R.color.white));
        star_rating.setTextColor(getResources().getColor(R.color.white));
        price.setTextColor(getResources().getColor(R.color.colorAccent));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            if (data!=null){
                Boolean Filter=data.getBooleanExtra("Filter",false);
                if (Filter){
                    Tags=null;
                    filter_details= (Flight_Filters_DTO) data.getSerializableExtra("Filteredlist");
                    FilterMethod();
                }else {
                    callFlightsData();
                    filter_details=null;
                }

            }
        }
    }

    public void FilterMethod(){
        ArrayList<InternationalFlightsDTO> available_flights=new ArrayList<>();
        available_flights=flights_main_dto.getInternationalFlights();
        ArrayList<InternationalFlightsDTO> Filteredlist=new ArrayList<>();

        ArrayList<HashMap<String,String>> Selecteditems=new ArrayList<>();
        Selecteditems=filter_details.getFilter_Array();

        for (int m=0;m<available_flights.size();m++) {
            double d = Double.parseDouble(String.valueOf(available_flights.get(m).getFareDetails().getChargeableFares().getActualBaseFare()));
            double Servicecharge = Double.parseDouble(String.valueOf(available_flights.get(m).getFareDetails().getChargeableFares().getTax()));
            double Operatercharge = Double.parseDouble(String.valueOf(available_flights.get(m).getFareDetails().getNonchargeableFares().getTCharge()));
            double Markepcharge = Double.parseDouble(String.valueOf(available_flights.get(m).getFareDetails().getNonchargeableFares().getTMarkup()));

            final double TotalFare = d + Servicecharge + Operatercharge + Markepcharge;
            if (Util.getprice2(TotalFare) >= filter_details.getMin_Rate() && Util.getprice2(TotalFare) <= filter_details.getMax_Rate()) {
                Isvalid=true;
                for (int k = 0; k < Selecteditems.size(); k++) {
                    if (filter_details.getAirlines() != null && filter_details.getAirlines().size() != 0) {
                        if (filter_details.getAirlines().contains(available_flights.get(m).getIntOnward().getFlightSegments().get(0).getAirLineName())) {
                            Isvalid = true;
                        } else {
                            Isvalid = false;
                            break;
                        }

                    }
                    if (filter_details.getStops() != null && filter_details.getStops().size() != 0) {
                        if (filter_details.getStops().contains(0) && !filter_details.getStops().contains(1) && !filter_details.getStops().contains(2)) {
                            if (filter_details.getStops().contains(Flight_Utils.getLengthforfilters(available_flights.get(m).getIntOnward().getFlightSegments().size()))) {
                                Isvalid = true;
                            } else {
                                Isvalid = false;
                                break;
                            }
                        }
                        if (filter_details.getStops().contains(1) && !filter_details.getStops().contains(2) && !filter_details.getStops().contains(0)) {
                            if (filter_details.getStops().contains(Flight_Utils.getLengthforfilters(available_flights.get(m).getIntOnward().getFlightSegments().size()))) {
                                Isvalid = true;
                            } else {
                                Isvalid = false;
                                break;
                            }
                        }
                        if (filter_details.getStops().contains(2) && !filter_details.getStops().contains(1) && !filter_details.getStops().contains(0)) {
                            if (filter_details.getStops().contains(Flight_Utils.getLengthforfilters(available_flights.get(m).getIntOnward().getFlightSegments().size()))) {
                                Isvalid = true;
                            } else {
                                Isvalid = false;
                                break;
                            }
                        }


                        if (filter_details.getStops().contains(0) && filter_details.getStops().contains(1) && !filter_details.getStops().contains(2)) {
                            if (filter_details.getStops().contains(Flight_Utils.getLengthforfilters(available_flights.get(m).getIntOnward().getFlightSegments().size()))) {
                                Isvalid = true;
                            } else {
                                Isvalid = false;
                                break;
                            }
                        }
                        if (!filter_details.getStops().contains(0) && filter_details.getStops().contains(1) && filter_details.getStops().contains(2)) {
                            if (filter_details.getStops().contains(Flight_Utils.getLengthforfilters(available_flights.get(m).getIntOnward().getFlightSegments().size()))) {
                                Isvalid = true;
                            } else {
                                Isvalid = false;
                                break;
                            }
                        }
                        if (filter_details.getStops().contains(0) && !filter_details.getStops().contains(1) && filter_details.getStops().contains(2)) {
                            if (filter_details.getStops().contains(Flight_Utils.getLengthforfilters(available_flights.get(m).getIntOnward().getFlightSegments().size()))) {
                                Isvalid = true;
                            } else {
                                Isvalid = false;
                                break;
                            }
                        }

                        if (filter_details.getStops().contains(0) && filter_details.getStops().contains(1) && filter_details.getStops().contains(2)) {
                            if (filter_details.getStops().contains(Flight_Utils.getLengthforfilters(available_flights.get(m).getIntOnward().getFlightSegments().size()))) {
                                Isvalid = true;
                            } else {
                                Isvalid = false;
                                break;
                            }
                        }
                    }

                    if (filter_details.getRule() != null && filter_details.getRule().size() != 0) {
                        if (filter_details.getRule().contains("Refundable") && !filter_details.getRule().contains("Non Refundable")) {
                            if (filter_details.getRule().contains(available_flights.get(m).getIntOnward().getFlightSegments().get(0).getBookingClassFare().getRule())) {
                                Isvalid = true;
                            } else {
                                Isvalid = false;
                                break;
                            }
                        }

                        if (!filter_details.getRule().contains("Refundable") && filter_details.getRule().contains("Non Refundable")) {
                            if (filter_details.getRule().contains(available_flights.get(m).getIntOnward().getFlightSegments().get(0).getBookingClassFare().getRule())) {
                                Isvalid = true;
                            } else {
                                Isvalid = false;
                                break;
                            }
                        }
                        if (filter_details.getRule().contains("Refundable") && filter_details.getRule().contains("Non Refundable")) {
                            if (filter_details.getRule().contains(available_flights.get(m).getIntOnward().getFlightSegments().get(0).getBookingClassFare().getRule())) {
                                Isvalid = true;
                            } else {
                                Isvalid = false;
                                break;
                            }
                        }

                    }

                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
                    try {
                        Date date1 = sdf.parse("00:00");
                        Date date2 = sdf.parse("11:59");
                        Date date3 = sdf.parse("12:00");
                        Date date4 = sdf.parse("17:59");
                        Date date5 = sdf.parse("18:00");
                        Date date6 = sdf.parse("23:59");
                        String dept_time = Flight_Utils.getTime(available_flights.get(m).getIntOnward().getFlightSegments().get(0).getDepartureDateTime());
                        Date Departure = sdf.parse(dept_time);

                        if (Selecteditems.get(0).containsKey("Morning (12:00AM-11:59AM)") && !Selecteditems.get(0).containsKey("Afternoon (12:00PM-5:59PM)") &&
                                !Selecteditems.get(0).containsKey("Evening (6:00PM-11:59PM)")) {
                            if (Departure.after(date1) || Departure.equals(date1) && Departure.before(date2) || Departure.equals(date2)) {
                                Isvalid = true;
                            } else {
                                Isvalid = false;
                                break;
                            }
                        }

                        if (!Selecteditems.get(0).containsKey("Morning (12:00AM-11:59AM)") && Selecteditems.get(0).containsKey("Afternoon (12:00PM-5:59PM)") &&
                                !Selecteditems.get(0).containsKey("Evening (6:00PM-11:59PM)")) {
                            if (Departure.after(date3) || Departure.equals(date3) && Departure.before(date4) || Departure.equals(date4)) {
                                Isvalid = true;
                            } else {
                                Isvalid = false;
                                break;
                            }
                        }

                        if (!Selecteditems.get(0).containsKey("Morning (12:00AM-11:59AM)") && !Selecteditems.get(0).containsKey("Afternoon (12:00PM-5:59PM)") &&
                                Selecteditems.get(0).containsKey("Evening (6:00PM-11:59PM)")) {
                            if (Departure.after(date5) || Departure.equals(date5) && Departure.before(date6) || Departure.equals(date6)) {
                                Isvalid = true;
                            } else {
                                Isvalid = false;
                                break;
                            }
                        }

                        if (Selecteditems.get(0).containsKey("Morning (12:00AM-11:59AM)") && Selecteditems.get(0).containsKey("Afternoon (12:00PM-5:59PM)") &&
                                !Selecteditems.get(0).containsKey("Evening (6:00PM-11:59PM)")) {
                            if (Departure.after(date1) || Departure.equals(date1) && Departure.before(date2) || Departure.equals(date2) &&
                                    Departure.after(date3) || Departure.equals(date3) && Departure.before(date4) || Departure.equals(date4)) {
                                Isvalid = true;
                            } else {
                                Isvalid = false;
                                break;
                            }
                        }

                        if (!Selecteditems.get(0).containsKey("Morning (12:00AM-11:59AM)") && Selecteditems.get(0).containsKey("Afternoon (12:00PM-5:59PM)") &&
                                Selecteditems.get(0).containsKey("Evening (6:00PM-11:59PM)")) {
                            if (Departure.after(date3) || Departure.equals(date3) && Departure.before(date4) || Departure.equals(date4) &&
                                    Departure.after(date5) || Departure.equals(date5) && Departure.before(date6) || Departure.equals(date6)) {
                                Isvalid = true;
                            } else {
                                Isvalid = false;
                                break;
                            }
                        }

                        if (Selecteditems.get(0).containsKey("Morning (12:00AM-11:59AM)") && !Selecteditems.get(0).containsKey("Afternoon (12:00PM-5:59PM)") &&
                                Selecteditems.get(0).containsKey("Evening (6:00PM-11:59PM)")) {
                            if (Departure.after(date5) || Departure.equals(date5) && Departure.before(date6) || Departure.equals(date6) &&
                                    Departure.after(date1) || Departure.equals(date1) && Departure.before(date2) || Departure.equals(date2)) {
                                Isvalid = true;
                            } else {
                                Isvalid = false;
                                break;
                            }
                        }

                        if (Selecteditems.get(0).containsKey("Morning (12:00AM-11:59AM)") && Selecteditems.get(0).containsKey("Afternoon (12:00PM-5:59PM)") &&
                                Selecteditems.get(0).containsKey("Evening (6:00PM-11:59PM)")) {
                            if (Departure.after(date5) || Departure.equals(date5) && Departure.before(date6) || Departure.equals(date6) &&
                                    Departure.after(date1) || Departure.equals(date1) && Departure.before(date2) || Departure.equals(date2) &&
                                    Departure.after(date3) || Departure.equals(date3) && Departure.before(date4) || Departure.equals(date4)) {
                                Isvalid = true;
                            } else {
                                Isvalid = false;
                                break;
                            }
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }


                if (Isvalid) {
                    Filteredlist.add(available_flights.get(m));
                }
            }

        }

        setFlightsData(Filteredlist);
        Util.showMessage(this, String.valueOf(Filteredlist.size()+" Flights Found"));
        if (Filteredlist.size()==0){
            showAlertDialog2("No Flights Available for Selected Filter");
        }

    }


    private void showAlertDialog2(String s) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        ////alertDialog.setTitle("Alert!");
        alertDialog.setMessage(s);
        alertDialog.setCancelable(false);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent nextActivity = new Intent(Flights_International_Onward.this, Int_Onward_Flight_Filters_Activity.class);
                nextActivity.putExtra("Int_Onward_list",flights_main_dto.getInternationalFlights());
                nextActivity.putExtra("Filteredlist", filter_details);
                startActivityForResult(nextActivity,1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        alertDialog.show();
    }

    private  void showProgressDialog(String msg,Context context) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage(msg);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    public void getFlightsResponse(String response) {
        hideProgressDialog();
        if(response!=null) {
            InputStream stream = new ByteArrayInputStream(response.getBytes());
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(stream);
            flights_main_dto = gson.fromJson(reader, Flights_Avalability_DTO.class);
            //  Util.showMessage(this,hotelsMainDTO.getAvailableHotels().get(0).getHotelName());
            if(flights_main_dto!=null && flights_main_dto.getInternationalFlights()!=null){
                if(flights_main_dto.getInternationalFlights().size()>0){
                    setFlightsData(flights_main_dto.getInternationalFlights());
                }
                else {
                    showAlertDialog(getResources().getString(R.string.No_flights_found));
                }
            }
        }
    }


    public void getFlightData(InternationalFlightsDTO Flights_DTO,double total) {
        SelOnwardFlight=Flights_DTO;
        GrandTotal=total;
        callTaxDetails();
    }

    private void setFlightsData(ArrayList<InternationalFlightsDTO> availableFlights) {
        flights_adapter   = new International_Onward_Adapter(this,availableFlights);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        flights_View.setLayoutManager(mLayoutManager);
        flights_View.setItemAnimator(new DefaultItemAnimator());
        flights_View.setAdapter(flights_adapter);
    }

    private void showAlertDialog(String s) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        //alertDialog.setTitle("Alert!");
        alertDialog.setMessage(s);
        alertDialog.setCancelable(false);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertDialog.show();
    }

    private void callTaxDetails(){
        showProgressDialog("Fetching Tax Details", this);
        if (Util.isNetworkAvailable(getApplicationContext())) {
            ServiceClasses.getTaxDetails(Flights_International_Onward.this,Constants.GetTaxDetails,prepayload());
        } else {
            Util.showMessage(this, Constants.NO_INT_MSG);
        }

    }


    private String prepayload() {

        JSONArray array = new JSONArray();
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        try {
            for(int i=0;i<SelOnwardFlight.getIntOnward().getFlightSegments().size();i++) {
                array.put(new JSONObject(gson.toJson(SelOnwardFlight.getIntOnward().getFlightSegments().get(i))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject bcparam = new JSONObject();
        try {
            bcparam.put("BookingResponseXML", "");
            bcparam.put("IsOfflineBooking", "false");
            bcparam.put("BookingRefNo", "");
            bcparam.put("BookingStatus", "0");
            bcparam.put("APIRefNo", "");
            bcparam.put("Provider", SelOnwardFlight.getProvider());
            bcparam.put("PaymentId", "");
            bcparam.put("Names", "");
            bcparam.put("ages", "");
            bcparam.put("Genders", "");
            bcparam.put("telePhone", "");
            bcparam.put("MobileNo", "");
            bcparam.put("EmailId", "");
            bcparam.put("dob", "");
            bcparam.put("psgrtype", "");
            bcparam.put("Address", "");
            bcparam.put("State", "");
            bcparam.put("City", "");
            bcparam.put("PostalCode", "");
            bcparam.put("PassportDetails", "");
            bcparam.put("SMSUsageCount", "0");
            bcparam.put("ImagePath", SelOnwardFlight.getIntOnward().getFlightSegments().get(0).getImageFileName());
            bcparam.put("ImagePathRet", "");
            bcparam.put("Rule", SelOnwardFlight.getIntOnward().getFlightSegments().get(0).getBookingClassFare().getRule());
            bcparam.put("key", SelOnwardFlight.getOriginDestinationoptionId().getKey());
            bcparam.put("RuleRet", "");
            bcparam.put("keyRet", "");
            bcparam.put("FlightId", "");
            bcparam.put("FlightIdRet", "");
            bcparam.put("OnwardFlightSegments",array);
            bcparam.put("FareDetails", new JSONObject(gson.toJson(SelOnwardFlight.getFareDetails())));
            bcparam.put("BookingDate", "0001-01-01T00:00:00");
            bcparam.put("PromoCode", "");
            bcparam.put("PromoCodeAmount", "0");
            bcparam.put("PostMarkup", "");
            bcparam.put("OcTax", SelOnwardFlight.getIntOnward().getFlightSegments().get(0).getOcTax());
            bcparam.put("ActualBaseFare", SelOnwardFlight.getFareDetails().getChargeableFares().getActualBaseFare());
            bcparam.put("Tax", SelOnwardFlight.getFareDetails().getChargeableFares().getTax());
            bcparam.put("STax", SelOnwardFlight.getFareDetails().getChargeableFares().getSTax());
            bcparam.put("SCharge", SelOnwardFlight.getFareDetails().getChargeableFares().getSCharge());
            bcparam.put("TDiscount", SelOnwardFlight.getFareDetails().getChargeableFares().getTDiscount());
            bcparam.put("TPartnerCommission", SelOnwardFlight.getFareDetails().getChargeableFares().getTPartnerCommission());
            bcparam.put("TCharge", SelOnwardFlight.getFareDetails().getNonchargeableFares().getTCharge());
            bcparam.put("TMarkup", SelOnwardFlight.getFareDetails().getNonchargeableFares().getTMarkup());
            bcparam.put("TSdiscount", SelOnwardFlight.getFareDetails().getNonchargeableFares().getTSdiscount());
            bcparam.put("TransactionId", "");
            bcparam.put("ActualBaseFareRet", "0");
            bcparam.put("TaxRet", "0");
            bcparam.put("STaxRet", "0");
            bcparam.put("SChargeRet", "0");
            bcparam.put("TDiscountRet", "0");
            bcparam.put("TSDiscountRet", "0");
            bcparam.put("TPartnerCommissionRet", "0");
            bcparam.put("TChargeRet", "0");
            bcparam.put("TMarkupRet", "0");
            bcparam.put("Source", selDetails.getFrom_City_ID());
            bcparam.put("SourceName", selDetails.getFrom_City_Full_Name());
            bcparam.put("Destination", selDetails.getTo_City_ID());
            bcparam.put("DestinationName", selDetails.getTo_City_Full_Name());
            bcparam.put("JourneyDate", selDetails.getOnward_date());
            bcparam.put("ReturnDate", "");
            bcparam.put("TripType", "1");
            bcparam.put("FlightType", selDetails.getFlight_Type());
            bcparam.put("AdultPax", selDetails.getadult_count());
            bcparam.put("ChildPax", selDetails.getchild_count());
            bcparam.put("InfantPax", selDetails.getinfant_count());
            bcparam.put("TravelClass", selDetails.getClass_type_value());
            bcparam.put("IsNonStopFlight", "false");
            bcparam.put("FlightTimings", "");
            bcparam.put("AirlineName", "");
            bcparam.put("User", User_agentid);
            bcparam.put("UserType", UserId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("TaxDetails request: "+ bcparam.toString());
        return bcparam.toString();
    }


    public void postTaxDetailsResponse(String response) {
        hideProgressDialog();
        System.out.println("TaxDetails Response: "+response);
        if (response!=null) {
            InputStream stream = new ByteArrayInputStream(response.getBytes());
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(stream);
            FareDetailsDTO fareDetailsDTO = gson.fromJson(reader, FareDetailsDTO.class);
            if (Util.getTaxResponseCode(response) == 14) {
                double d = Double.parseDouble(String.valueOf(fareDetailsDTO.getChargeableFares().getActualBaseFare()));
                double Servicecharge = Double.parseDouble(String.valueOf(fareDetailsDTO.getChargeableFares().getTax()));
                double Operatercharge = Double.parseDouble(String.valueOf(fareDetailsDTO.getNonchargeableFares().getTCharge()));
                double Markepcharge = Double.parseDouble(String.valueOf(fareDetailsDTO.getNonchargeableFares().getTMarkup()));
                final double TotalFare = d + Servicecharge + Operatercharge + Markepcharge;
                SelOnwardFlight.setFareDetails(fareDetailsDTO);
                if (GrandTotal == TotalFare) {
                    send_to_nextactivity();
                } else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                    //alertDialog.setTitle("Alert!");
                    alertDialog.setMessage(getResources().getString(R.string.fare_changed_1) +" "+ Currency_Symbol+Util.getprice(TotalFare*Curr_Value) + getResources().getString(R.string.fare_changed_2));
                    alertDialog.setCancelable(false);
                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            send_to_nextactivity();
                        }
                    });
                    alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                    alertDialog.show();
                }
            }else {
                Util.alertDialogShow(this,getResources().getString(R.string.error_msg));
            }
        }
    }


    private void send_to_nextactivity(){
        Intent ip = new Intent(Flights_International_Onward.this, International_Flight_Review.class);
        ip.putExtra("selDetails",selDetails);
        ip.putExtra("SelFlight",SelOnwardFlight);
        ip.putExtra("Flights_Main_DTO",flights_main_dto);
        startActivity(ip);
    }

    public void errorresponse (String response){
        hideProgressDialog();
        showAlertDialogforError("Timed out! \nTry again");
    }


    private void showAlertDialogforError(String s) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        //alertDialog.setTitle("Alert!");
        alertDialog.setMessage(s);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
               callTaxDetails();
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.show();
    }


}
