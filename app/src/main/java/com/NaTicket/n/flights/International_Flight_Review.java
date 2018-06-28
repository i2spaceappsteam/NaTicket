package com.NaTicket.n.flights;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.NaTicket.n.R;
import com.NaTicket.n.common.pojo.Currency_Utils;
import com.NaTicket.n.flights.pojo.Flight_Utils;
import com.NaTicket.n.flights.pojo.Flights_Avalability_DTO;
import com.NaTicket.n.flights.pojo.InternationalFlightsDTO;
import com.NaTicket.n.flights.pojo.SelectedFlightDetailsDTO;
import com.NaTicket.n.loginpackage.pojo.Login_utils;
import com.NaTicket.n.serviceclasses.ServiceClasses;
import com.NaTicket.n.utils.BackActivity;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;
import com.bumptech.glide.Glide;

import org.xml.sax.XMLReader;

import java.text.ParseException;

/**
 * Created by Nagarjuna on 31-12-2017.
 */

public class International_Flight_Review extends BackActivity implements View.OnClickListener {

    Flights_Avalability_DTO flights_main_dto;
    SelectedFlightDetailsDTO selDetails;
    InternationalFlightsDTO SelFlight;
    android.widget.ScrollView ScrollView;
    String UserId, User_agentid, UserType;
    TextView Onward_Fare_Rule_tv,Return_Fare_Rule_tv,layover,route;
    Currency_Utils currency_utils;
    double curr_value;
    String CurrencySymbol;
    TextView Total_Fare_tv,Continue_tv;
    ProgressDialog mProgressDialog;
    String TotalFares;
    Login_utils login_utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.international_flight_review);
        login_utils=new Login_utils(this);
        currency_utils=new Currency_Utils(this);
        curr_value= Double.parseDouble(currency_utils.getCurrencyValue("Currency_Value"));
        CurrencySymbol=currency_utils.getCurrencyValue("Currency_Symbol");
        initViews();
        getLoginPreferences();

        flights_main_dto= (Flights_Avalability_DTO) getIntent().getSerializableExtra("Flights_Main_DTO");
        SelFlight= (InternationalFlightsDTO) getIntent().getSerializableExtra("SelFlight");
        selDetails= (SelectedFlightDetailsDTO) getIntent().getSerializableExtra("selDetails");

        inittoolbar();
        TextView toolbartitle=(TextView)findViewById(R.id.toolbartitle);
        toolbartitle.setText("Flight Review");

        setFlightData();
    }


    private void getLoginPreferences() {
        if (login_utils.getUserDetails(Constants.USERTYPE).equals("6")) {
            UserId = "6";
            UserType = "User";
            User_agentid = login_utils.getUserDetails(Constants.USERID);
        } else if (login_utils.getUserDetails(Constants.USERTYPE).equals("4")) {
            UserId = "4";
            UserType = "Agent";
            User_agentid = login_utils.getUserDetails(Constants.USERID);
        } else {
            UserId = "5";
            UserType = "Guest";
            User_agentid = null;
        }
    }

    public void setFlightData(){
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));



            LayoutInflater inflaterRoute = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewHolderFare1 = inflaterRoute.inflate(R.layout.flight_route_layout, null);
            LinearLayout linearLayoutRoute = (LinearLayout) viewHolderFare1.findViewById(R.id.flights_route_layt);
            route = (TextView) viewHolderFare1.findViewById(R.id.Route);
        int size=SelFlight.getIntOnward().getFlightSegments().size();
        if(SelFlight.getIntOnward().getFlightSegments().size()==1) {
            route.setText(SelFlight.getIntOnward().getFlightSegments().get(0).getIntDepartureAirportName() + " To " + SelFlight.getIntOnward().getFlightSegments().get(0).getIntArrivalAirportName());
        }else{
            route.setText(SelFlight.getIntOnward().getFlightSegments().get(0).getIntDepartureAirportName() + " To " + SelFlight.getIntOnward().getFlightSegments().get(size-1).getIntArrivalAirportName());
        }
            ll.addView(linearLayoutRoute);


        for (int p=0;p<SelFlight.getIntOnward().getFlightSegments().size();p++){
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewHolder = inflater.inflate(R.layout.flight_review_onward, null);
            LinearLayout linearLayout = (LinearLayout) viewHolder.findViewById(R.id.flights_layout);

            ImageView Airline_Image= (ImageView) viewHolder.findViewById(R.id.AirlineImage);
            TextView Air_line_code= (TextView) viewHolder.findViewById(R.id.Airline_Name);
            TextView Source= (TextView) viewHolder.findViewById(R.id.source);
            TextView Flight_Deptimes= (TextView) viewHolder.findViewById(R.id.flight_DepTime);
            TextView Flight_Class= (TextView) viewHolder.findViewById(R.id.flight_Class);
            TextView Destination= (TextView) viewHolder.findViewById(R.id.destination);
            TextView Flight_Arrtimes= (TextView) viewHolder.findViewById(R.id.flight_ArrTime);
            TextView duration_stops= (TextView) viewHolder.findViewById(R.id.Duration);
            TextView baggage_info= (TextView) viewHolder.findViewById(R.id.baggage);

            String From_Place=SelFlight.getIntOnward().getFlightSegments().get(p).getDepartureAirportCode();
            String To_Place=SelFlight.getIntOnward().getFlightSegments().get(p).getArrivalAirportCode();
            String From_Time= Flight_Utils.getTime(SelFlight.getIntOnward().getFlightSegments().get(p).getDepartureDateTime());
            String To_Time=Flight_Utils.getTime(SelFlight.getIntOnward().getFlightSegments().get(p).getArrivalDateTime());

            String url= Constants.FLIGHTS_IMAGE_URL+SelFlight.getIntOnward().getFlightSegments().get(p).getImageFileName()+".png";

            Glide.with(this)
                    .load(url)
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_placeholder)
                    .into(Airline_Image);

            //Bitmap bitmap= Flight_Utils.getImage(this,SelFlight.getIntOnward().getFlightSegments().get(p).getImageFileName());
            //Airline_Image.setImageBitmap(bitmap);

            Source.setText(From_Place+" "+From_Time);
            Destination.setText(To_Place+" "+To_Time);

            Air_line_code.setText(SelFlight.getIntOnward().getFlightSegments().get(p).getAirLineName()+" "+SelFlight.getIntOnward().getFlightSegments().get(p).getOperatingAirlineCode()+" - "+
                    SelFlight.getIntOnward().getFlightSegments().get(p).getOperatingAirlineFlightNumber());


            int FlightsegmentsSize=SelFlight.getIntOnward().getFlightSegments().size()-1;

                try {
                    Flight_Deptimes.setText(Util.getReverseDate(SelFlight.getIntOnward().getFlightSegments().get(p).getDepartureDateTime().split("T")[0]));
                } catch (ParseException e) {
                    e.printStackTrace();
                }




                try {
                    Flight_Arrtimes.setText(Util.getReverseDate(SelFlight.getIntOnward().getFlightSegments().get(p).getArrivalDateTime().split("T")[0]));
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            String OnwardFareRule=SelFlight.getIntOnward().getFlightSegments().get(p).getBookingClassFare().getRule();
            Flight_Class.setText(OnwardFareRule+" | "+selDetails.getclass_type());

            String Duration=Flight_Utils.getDuration(SelFlight.getIntOnward().getFlightSegments().get(p).getDepartureDateTime().replace("T"," "),SelFlight.getIntOnward().getFlightSegments().get(p).getArrivalDateTime().replace("T"," "));
            duration_stops.setText(""+Duration+"");


            String CheckInBaggage=SelFlight.getIntOnward().getFlightSegments().get(p).getBaggageAllowed().getCheckInBaggage();
            String Handbag=SelFlight.getIntOnward().getFlightSegments().get(p).getBaggageAllowed().getHandBaggage();


            if(!CheckInBaggage.equals("")&&!Handbag.equals("")){

                baggage_info.setText("Check-In : "+CheckInBaggage+", Hand: "+Handbag+"");
            }else{
                if(CheckInBaggage.equals("")){
                    baggage_info.setText("Hand : "+Handbag+"");
                }else if(Handbag.equals("")){
                    baggage_info.setText("Check-In : "+CheckInBaggage+"");
                }
            }
            ll.addView(linearLayout);

            if(SelFlight.getIntOnward().getFlightSegments().size()!=1&&p!=FlightsegmentsSize){
                LayoutInflater inflaterFare1 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewHolderFare2 = inflaterFare1.inflate(R.layout.flight_layover_layout, null);
                LinearLayout linearLayoutLayover = (LinearLayout) viewHolderFare2.findViewById(R.id.layoverLyt);
                linearLayoutLayover.setVisibility(View.INVISIBLE);

                layover = (TextView) viewHolderFare2.findViewById(R.id.layoverTime);
                String LayDuration=null;
                if(p==0){
                    LayDuration=Flight_Utils.getDuration
                            (SelFlight.getIntOnward().getFlightSegments().get(0).getArrivalDateTime().replace("T"," "),
                            SelFlight.getIntOnward().getFlightSegments().get(1).getDepartureDateTime().replace("T"," "));
                }
                if(p==1){
                    LayDuration=Flight_Utils.getDuration
                            (SelFlight.getIntOnward().getFlightSegments().get(1).getArrivalDateTime().replace("T"," "),
                                    SelFlight.getIntOnward().getFlightSegments().get(2).getDepartureDateTime().replace("T"," "));
                }
                if(p==2){
                    LayDuration=Flight_Utils.getDuration
                            (SelFlight.getIntOnward().getFlightSegments().get(2).getArrivalDateTime().replace("T"," "),
                                    SelFlight.getIntOnward().getFlightSegments().get(3).getDepartureDateTime().replace("T"," "));
                }

                layover.setText(""+LayDuration+"");


                ll.addView(linearLayoutLayover);
            }


        }

        LayoutInflater inflaterFare = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewHolderFare = inflaterFare.inflate(R.layout.flights_fare_rules_layout, null);
        LinearLayout linearLayoutFare = (LinearLayout) viewHolderFare.findViewById(R.id.layoutFareRule);

        Onward_Fare_Rule_tv = (TextView) viewHolderFare.findViewById(R.id.FareRuleTextView);
        ll.addView(linearLayoutFare);


        LayoutInflater inflaterRouteRet = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewHolderFareRet = inflaterRouteRet.inflate(R.layout.flight_route_layout, null);
        LinearLayout linearLayoutRouteRet = (LinearLayout) viewHolderFareRet.findViewById(R.id.flights_route_layt);
        route = (TextView) viewHolderFareRet.findViewById(R.id.Route);


        if (selDetails.getReturn_date()!=null){


        int sizeRet=SelFlight.getIntReturn().getFlightSegments().size();
        if(SelFlight.getIntReturn().getFlightSegments().size()==1) {
            route.setText(SelFlight.getIntReturn().getFlightSegments().get(0).getIntDepartureAirportName() + " To " + SelFlight.getIntReturn().getFlightSegments().get(0).getIntArrivalAirportName());
        }else{
            route.setText(SelFlight.getIntReturn().getFlightSegments().get(0).getIntDepartureAirportName() + " To " + SelFlight.getIntReturn().getFlightSegments().get(sizeRet-1).getIntArrivalAirportName());
        }
        ll.addView(linearLayoutRouteRet);


            for (int p=0;p<SelFlight.getIntReturn().getFlightSegments().size();p++){
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewHolder = inflater.inflate(R.layout.flight_review_onward, null);
                LinearLayout linearLayout = (LinearLayout) viewHolder.findViewById(R.id.flights_layout);

                ImageView Airline_Image= (ImageView) viewHolder.findViewById(R.id.AirlineImage);
                TextView Air_line_code= (TextView) viewHolder.findViewById(R.id.Airline_Name);
                TextView Source= (TextView) viewHolder.findViewById(R.id.source);
                TextView Flight_Deptimes= (TextView) viewHolder.findViewById(R.id.flight_DepTime);
                TextView Flight_Class= (TextView) viewHolder.findViewById(R.id.flight_Class);
                TextView Destination= (TextView) viewHolder.findViewById(R.id.destination);
                TextView Flight_Arrtimes= (TextView) viewHolder.findViewById(R.id.flight_ArrTime);
                TextView duration_stops= (TextView) viewHolder.findViewById(R.id.Duration);
                TextView baggage_info= (TextView) viewHolder.findViewById(R.id.baggage);

                String From_Place=SelFlight.getIntReturn().getFlightSegments().get(p).getDepartureAirportCode();
                String To_Place=SelFlight.getIntReturn().getFlightSegments().get(p).getArrivalAirportCode();
                String From_Time= Flight_Utils.getTime(SelFlight.getIntReturn().getFlightSegments().get(p).getDepartureDateTime());
                String To_Time=Flight_Utils.getTime(SelFlight.getIntReturn().getFlightSegments().get(p).getArrivalDateTime());

                String url= Constants.FLIGHTS_IMAGE_URL+SelFlight.getIntReturn().getFlightSegments().get(p).getImageFileName()+".png";

                Glide.with(this)
                        .load(url)
                        .placeholder(R.drawable.image_placeholder)
                        .error(R.drawable.image_placeholder)
                        .into(Airline_Image);

                //Bitmap bitmap= Flight_Utils.getImage(this,SelFlight.getIntReturn().getFlightSegments().get(p).getImageFileName());
                //Airline_Image.setImageBitmap(bitmap);

                Source.setText(From_Place+" "+From_Time);
                Destination.setText(To_Place+" "+To_Time);

                Air_line_code.setText(SelFlight.getIntReturn().getFlightSegments().get(p).getAirLineName()+" "+SelFlight.getIntReturn().getFlightSegments().get(p).getOperatingAirlineCode()+" - "+
                        SelFlight.getIntReturn().getFlightSegments().get(p).getOperatingAirlineFlightNumber());


                int FlightsegmentsSize=SelFlight.getIntReturn().getFlightSegments().size()-1;

                    try {
                        Flight_Deptimes.setText(Util.getReverseDate(SelFlight.getIntReturn().getFlightSegments().get(p).getDepartureDateTime().split("T")[0]));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }




                    try {
                        Flight_Arrtimes.setText(Util.getReverseDate(SelFlight.getIntReturn().getFlightSegments().get(p).getArrivalDateTime().split("T")[0]));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                String OnwardFareRule=SelFlight.getIntReturn().getFlightSegments().get(p).getBookingClassFare().getRule();
                Flight_Class.setText(OnwardFareRule+" | "+selDetails.getclass_type());

                String Duration=Flight_Utils.getDuration(SelFlight.getIntReturn().getFlightSegments().get(p).getDepartureDateTime().replace("T"," "),SelFlight.getIntReturn().getFlightSegments().get(p).getArrivalDateTime().replace("T"," "));
                duration_stops.setText(""+Duration+"");


                String CheckInBaggage=SelFlight.getIntReturn().getFlightSegments().get(p).getBaggageAllowed().getCheckInBaggage();
                String Handbag=SelFlight.getIntReturn().getFlightSegments().get(p).getBaggageAllowed().getHandBaggage();

                if(!CheckInBaggage.equals("")&&!Handbag.equals("")){

                    baggage_info.setText("Check-In : "+CheckInBaggage+", Hand: "+Handbag+"");
                }else{
                    if(CheckInBaggage.equals("")){
                        baggage_info.setText("Hand : "+Handbag+"");
                    }else if(Handbag.equals("")){
                        baggage_info.setText("Check-In : "+CheckInBaggage+"");
                    }
                }

                ll.addView(linearLayout);

                if(SelFlight.getIntReturn().getFlightSegments().size()!=1&&p!=FlightsegmentsSize){
                    LayoutInflater inflaterFare1 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View viewHolderFare3 = inflaterFare1.inflate(R.layout.flight_layover_layout, null);
                    LinearLayout linearLayoutLayover = (LinearLayout) viewHolderFare3.findViewById(R.id.layoverLyt);
                    linearLayoutLayover.setVisibility(View.INVISIBLE);

                    layover = (TextView) viewHolderFare3.findViewById(R.id.layoverTime);

                    String LayDuration=null;
                    if(p==0){
                        LayDuration=Flight_Utils.getDuration
                                (SelFlight.getIntReturn().getFlightSegments().get(0).getArrivalDateTime().replace("T"," "),
                                        SelFlight.getIntReturn().getFlightSegments().get(1).getDepartureDateTime().replace("T"," "));
                    }
                    if(p==1){
                        LayDuration=Flight_Utils.getDuration
                                (SelFlight.getIntReturn().getFlightSegments().get(1).getArrivalDateTime().replace("T"," "),
                                        SelFlight.getIntReturn().getFlightSegments().get(2).getDepartureDateTime().replace("T"," "));
                    }
                    if(p==2){
                        LayDuration=Flight_Utils.getDuration
                                (SelFlight.getIntReturn().getFlightSegments().get(2).getArrivalDateTime().replace("T"," "),
                                        SelFlight.getIntReturn().getFlightSegments().get(3).getDepartureDateTime().replace("T"," "));
                    }

                    layover.setText(""+LayDuration+"");

                    ll.addView(linearLayoutLayover);
                }
            }

            LayoutInflater inflaterFare1 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewHolderFare4 = inflaterFare1.inflate(R.layout.flights_fare_rules_layout, null);
            LinearLayout linearLayoutFare1 = (LinearLayout) viewHolderFare4.findViewById(R.id.layoutFareRule);

            Return_Fare_Rule_tv = (TextView) viewHolderFare4.findViewById(R.id.FareRuleTextView);
            ll.addView(linearLayoutFare1);
        }


        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewHolder = inflater.inflate(R.layout.flights_fare_details_layout, null);
        LinearLayout linearLayout = (LinearLayout) viewHolder.findViewById(R.id.layoutFare);
        LinearLayout NumberOfChildLinearlayout = (LinearLayout) viewHolder.findViewById(R.id.NumberOfChildLinearlayout);
        LinearLayout NumberOfInfantLinearlayout = (LinearLayout) viewHolder.findViewById(R.id.NumberOfInfantLinearlayout);
        LinearLayout ServiceTaxLinerLayout = (LinearLayout) viewHolder.findViewById(R.id.ServiceTaxLinerLayout);
        LinearLayout ConvenienceFeeLinerLayout = (LinearLayout) viewHolder.findViewById(R.id.ConvenienceFeeLinerLayout);

        TextView NoOfAdultsTextview = (TextView) viewHolder.findViewById(R.id.NoOfAdults);
        TextView NoOfChildTextview = (TextView) viewHolder.findViewById(R.id.NoOfChild);
        TextView NoOfInfantTextview = (TextView) viewHolder.findViewById(R.id.NoOfInfant);

        TextView Service_TaxTextview = (TextView) viewHolder.findViewById(R.id.Service_Tax);
        TextView FLightsConvenienceFeeTextview = (TextView) viewHolder.findViewById(R.id.FLightsConvenienceFee);
        TextView BaseFare = (TextView) viewHolder.findViewById(R.id.BaseFare);
        TextView FeeandSurchargestv = (TextView) viewHolder.findViewById(R.id.feeandsercharges);
        TextView TotalAmount = (TextView) viewHolder.findViewById(R.id.TotalAmount);


        if (selDetails.getchild_count()!=0){
            NumberOfChildLinearlayout.setVisibility(View.VISIBLE);
            NoOfChildTextview.setText(""+selDetails.getchild_count()+"");
        }

        if (selDetails.getinfant_count()!=0){
            NumberOfInfantLinearlayout.setVisibility(View.VISIBLE);
            NoOfInfantTextview.setText(""+selDetails.getinfant_count()+"");
        }
        NoOfAdultsTextview.setText(""+selDetails.getadult_count()+"");


        double OnwardBaseFare=SelFlight.getFareDetails().getChargeableFares().getActualBaseFare();
        double OnwardTax=SelFlight.getFareDetails().getChargeableFares().getTax();
        double OnwardTcharge=SelFlight.getFareDetails().getNonchargeableFares().getTCharge();
        double OnwardTMarkup=SelFlight.getFareDetails().getNonchargeableFares().getTMarkup();
        double OnwardSTax=SelFlight.getFareDetails().getChargeableFares().getSTax();
        double OnwardConvFee;
        int Convi_Fee_Type=SelFlight.getFareDetails().getChargeableFares().getConveniencefeeType();

        if(Convi_Fee_Type==1){
            OnwardConvFee=SelFlight.getFareDetails().getChargeableFares().getConveniencefee();
        }else {
            double Total_To_Calculate_Confee=OnwardBaseFare+OnwardTax+OnwardTcharge+OnwardTMarkup+OnwardSTax;
            double con_fee=SelFlight.getFareDetails().getChargeableFares().getConveniencefee();
            OnwardConvFee=(Total_To_Calculate_Confee/100.0f)*con_fee;
            SelFlight.getFareDetails().getChargeableFares().setConveniencefee(OnwardConvFee);
        }

        if (OnwardConvFee!=0){
            ConvenienceFeeLinerLayout.setVisibility(View.VISIBLE);
            FLightsConvenienceFeeTextview.setText(CurrencySymbol+""+Util.getprice(curr_value*OnwardConvFee)+"");
        }
        if (OnwardSTax!=0){
            ServiceTaxLinerLayout.setVisibility(View.VISIBLE);
            Service_TaxTextview.setText(CurrencySymbol+""+Util.getprice(curr_value*OnwardSTax)+"");
        }

        double FeeandSurcharges=OnwardTax+OnwardTcharge+OnwardTMarkup;
        double Total_Fare=OnwardBaseFare+OnwardTax+OnwardTcharge+OnwardTMarkup+OnwardSTax+OnwardConvFee;

        FeeandSurchargestv.setText(CurrencySymbol+""+Util.getprice(curr_value*FeeandSurcharges)+"");
        BaseFare.setText(CurrencySymbol+""+Util.getprice(curr_value*OnwardBaseFare));
        TotalAmount.setText(""+CurrencySymbol+Util.getprice(curr_value*Total_Fare));
        Total_Fare_tv.setText(""+CurrencySymbol+Util.getprice(curr_value*Total_Fare));

        TotalFares= String.valueOf(Total_Fare);


        Onward_Fare_Rule_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callFareRules(SelFlight.getOriginDestinationoptionId().getKey(),
                        SelFlight.getIntOnward().getFlightSegments().get(0).getOperatingAirlineCode(),
                        SelFlight.getIntOnward().getFlightSegments().get(0).getOperatingAirlineFlightNumber(),
                        SelFlight.getIntOnward().getFlightSegments().get(0).getBookingClassFare().getClassType(),
                        SelFlight.getProvider());
            }
        });
        if (selDetails.getReturn_date()!=null){
            Return_Fare_Rule_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callFareRules(SelFlight.getOriginDestinationoptionId().getKey(),
                            SelFlight.getIntReturn().getFlightSegments().get(0).getOperatingAirlineCode(),
                            SelFlight.getIntReturn().getFlightSegments().get(0).getOperatingAirlineFlightNumber(),
                            SelFlight.getIntReturn().getFlightSegments().get(0).getBookingClassFare().getClassType(),
                            SelFlight.getProvider());
                }
            });
        }

        ll.addView(linearLayout);
        ScrollView.removeAllViews();
        ScrollView.addView(ll);

}

    private void initViews() {
        ScrollView = (ScrollView) findViewById(R.id.flightDetailsScrollview);
        Total_Fare_tv= (TextView) findViewById(R.id.flight_total);
        Continue_tv= (TextView) findViewById(R.id.continue_tv);
        Continue_tv.setOnClickListener(this);
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

    private void callFareRules(String Key,String Airlineid,String Flightid,String ClassCode,String Provider) {
        showProgressDialog("Getting Fare Rules", this);
        if (Util.isNetworkAvailable(getApplicationContext())) {
            String url;
            url= Constants.GetFareRule+"key="+Key+"&airlineId="+Airlineid+"&flightId="+Flightid+"&classCode="+ClassCode+
                    "&service=2&provider="+Provider+"&tripType="+selDetails.getTrip_Type()+"&couponFare=&userType="+UserId+
                    "&user="+User_agentid;
            Log.d("Fare Rules Url:",url);
            ServiceClasses.getFareRules(International_Flight_Review.this,url);
        }else {
            Util.showMessage(this, Constants.NO_INT_MSG);
        }

    }

    public void getFareRulesResponse(String response) {
        hideProgressDialog();
        if (response != null) {

            System.out.println("Response"+response);
            String rr = response.replaceAll("\\r|\\n", "\n").replace("\\r\\n","\n").replace("\\","'");


            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(International_Flight_Review.this);
            alertDialogBuilder.setTitle("Fare Rule :");
            alertDialogBuilder
                    .setMessage(Html.fromHtml(rr.replace("\"",""), null, new UlTagHandler()))
                    //.setView(webView)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

            TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
            assert textView != null;
            textView.setTextSize(12);
            textView.setGravity(Gravity.START);
            textView.setTextColor(getResources().getColor(R.color.colorAccent));
            Typeface face=Typeface.createFromAsset(getAssets(),"fonts/OpenSans_Regular.ttf");
            textView.setTypeface(face);
        }
    }

    public class UlTagHandler implements Html.TagHandler{
        @Override
        public void handleTag(boolean opening, String tag, Editable output,
                              XMLReader xmlReader) {
            if(tag.equals("ul") && !opening) output.append("\n");
            if(tag.equals("li") && opening) output.append("\n\t•");
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.continue_tv:
                Intent ip=new Intent(International_Flight_Review.this,Flight_Passenger_Information.class);
                ip.putExtra("SelFlight",SelFlight);
                ip.putExtra("selDetails",selDetails);
                ip.putExtra("TotalFare",TotalFares);
                startActivity(ip);
                break;

        }
    }
}
