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

import com.NaTicket.n.flights.pojo.DomesticOnwardFlightDTO;
import com.NaTicket.n.flights.pojo.DomesticReturnFlightDTO;
import com.NaTicket.n.R;
import com.NaTicket.n.common.pojo.Currency_Utils;
import com.NaTicket.n.flights.pojo.Flight_Utils;
import com.NaTicket.n.flights.pojo.Flights_Avalability_DTO;
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

public class Domestic_Flight_Review extends BackActivity implements View.OnClickListener{

    Flights_Avalability_DTO flights_main_dto;
    DomesticOnwardFlightDTO SelOnwardFlight;
    DomesticReturnFlightDTO SelReturnFlight;
    SelectedFlightDetailsDTO selDetails;
    ScrollView ScrollView;
    String UserId, User_agentid, UserType;
    TextView Onward_Fare_Rule_tv,Return_Fare_Rule_tv,layover,route;
    Currency_Utils currency_utils;
    double curr_value;
    String CurrencySymbol;
    TextView Total_Fare_tv,Continue_tv;
    ProgressDialog mProgressDialog;
     Login_utils login_utils;
    String TotalFares;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.domestic_flight_review);
         currency_utils=new Currency_Utils(this);
         curr_value= Double.parseDouble(currency_utils.getCurrencyValue("Currency_Value"));
         CurrencySymbol=currency_utils.getCurrencyValue("Currency_Symbol");

        login_utils=new Login_utils(this);
        initViews();
        getLoginPreferences();

        inittoolbar();
        TextView toolbartitle=(TextView)findViewById(R.id.toolbartitle);
        toolbartitle.setText("Flight Review");

        flights_main_dto= (Flights_Avalability_DTO) getIntent().getSerializableExtra("Flights_Main_DTO");
        SelOnwardFlight= (DomesticOnwardFlightDTO) getIntent().getSerializableExtra("SelOnwardFlight");
        selDetails= (SelectedFlightDetailsDTO) getIntent().getSerializableExtra("selDetails");
        if (selDetails.getReturn_date()!=null) {
            SelReturnFlight= (DomesticReturnFlightDTO) getIntent().getSerializableExtra("SelReturnFlight");
        }


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
        int size=SelOnwardFlight.getFlightSegments().size();
        if(SelOnwardFlight.getFlightSegments().size()==1) {
            route.setText(SelOnwardFlight.getFlightSegments().get(0).getIntDepartureAirportName() + " To " + SelOnwardFlight.getFlightSegments().get(0).getIntArrivalAirportName());
        }else{
            route.setText(SelOnwardFlight.getFlightSegments().get(0).getIntDepartureAirportName() + " To " + SelOnwardFlight.getFlightSegments().get(size-1).getIntArrivalAirportName());
        }
        ll.addView(linearLayoutRoute);


        for (int p=0;p<SelOnwardFlight.getFlightSegments().size();p++){
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

            String From_Place=SelOnwardFlight.getFlightSegments().get(p).getDepartureAirportCode();
            String To_Place=SelOnwardFlight.getFlightSegments().get(p).getArrivalAirportCode();
            String From_Time= Flight_Utils.getTime(SelOnwardFlight.getFlightSegments().get(p).getDepartureDateTime());
            String To_Time=Flight_Utils.getTime(SelOnwardFlight.getFlightSegments().get(p).getArrivalDateTime());


            String url= Constants.FLIGHTS_IMAGE_URL+SelOnwardFlight.getFlightSegments().get(p).getImageFileName()+".png";

            Glide.with(this)
                    .load(url)
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_placeholder)
                    .into(Airline_Image);

           // Bitmap bitmap= Flight_Utils.getImage(this,SelOnwardFlight.getFlightSegments().get(p).getImageFileName());
           // Airline_Image.setImageBitmap(bitmap);

            Source.setText(From_Place+" "+From_Time);
            Destination.setText(To_Place+" "+To_Time);

            Air_line_code.setText(SelOnwardFlight.getFlightSegments().get(p).getAirLineName()+" "+SelOnwardFlight.getFlightSegments().get(p).getOperatingAirlineCode()+" - "+
                    SelOnwardFlight.getFlightSegments().get(p).getOperatingAirlineFlightNumber());


            int FlightsegmentsSize=SelOnwardFlight.getFlightSegments().size()-1;

                try {
                    Flight_Deptimes.setText(Util.getReverseDate(SelOnwardFlight.getFlightSegments().get(p).getDepartureDateTime().split("T")[0]));
                } catch (ParseException e) {
                    e.printStackTrace();
                }




                try {
                    Flight_Arrtimes.setText(Util.getReverseDate(SelOnwardFlight.getFlightSegments().get(p).getArrivalDateTime().split("T")[0]));
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            String OnwardFareRule=SelOnwardFlight.getFlightSegments().get(p).getBookingClassFare().getRule();
            Flight_Class.setText(OnwardFareRule+" | "+selDetails.getclass_type());

            String Duration=Flight_Utils.getDuration(SelOnwardFlight.getFlightSegments().get(p).getDepartureDateTime().replace("T"," "),SelOnwardFlight.getFlightSegments().get(p).getArrivalDateTime().replace("T"," "));
            duration_stops.setText(""+Duration+"");


            String CheckInBaggage=SelOnwardFlight.getFlightSegments().get(p).getBaggageAllowed().getCheckInBaggage();
            String Handbag=SelOnwardFlight.getFlightSegments().get(p).getBaggageAllowed().getHandBaggage();


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


            if(SelOnwardFlight.getFlightSegments().size()!=1&&p!=FlightsegmentsSize){
                LayoutInflater inflaterFare1 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewHolderFare3 = inflaterFare1.inflate(R.layout.flight_layover_layout, null);
                LinearLayout linearLayoutLayover = (LinearLayout) viewHolderFare3.findViewById(R.id.layoverLyt);

                layover= (TextView) viewHolderFare3.findViewById(R.id.layoverTime);

                String LayDuration=null;
                if(p==0){
                    LayDuration=Flight_Utils.getDuration
                            (SelOnwardFlight.getFlightSegments().get(0).getArrivalDateTime().replace("T"," "),
                                    SelOnwardFlight.getFlightSegments().get(1).getDepartureDateTime().replace("T"," "));
                }
                if(p==1){
                    LayDuration=Flight_Utils.getDuration
                            (SelOnwardFlight.getFlightSegments().get(1).getArrivalDateTime().replace("T"," "),
                                    SelOnwardFlight.getFlightSegments().get(2).getDepartureDateTime().replace("T"," "));
                }
                if(p==2){
                    LayDuration=Flight_Utils.getDuration
                            (SelOnwardFlight.getFlightSegments().get(2).getArrivalDateTime().replace("T"," "),
                                    SelOnwardFlight.getFlightSegments().get(3).getDepartureDateTime().replace("T"," "));
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
        int sizeRet=SelReturnFlight.getFlightSegments().size();
        if(SelReturnFlight.getFlightSegments().size()==1) {
            route.setText(SelReturnFlight.getFlightSegments().get(0).getIntDepartureAirportName() + " To " + SelReturnFlight.getFlightSegments().get(0).getIntArrivalAirportName());
        }else{
            route.setText(SelReturnFlight.getFlightSegments().get(0).getIntDepartureAirportName() + " To " + SelReturnFlight.getFlightSegments().get(sizeRet-1).getIntArrivalAirportName());
        }
        ll.addView(linearLayoutRouteRet);



            for (int p=0;p<SelReturnFlight.getFlightSegments().size();p++){
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

                String From_Place=SelReturnFlight.getFlightSegments().get(p).getDepartureAirportCode();
                String To_Place=SelReturnFlight.getFlightSegments().get(p).getArrivalAirportCode();
                String From_Time= Flight_Utils.getTime(SelReturnFlight.getFlightSegments().get(p).getDepartureDateTime());
                String To_Time=Flight_Utils.getTime(SelReturnFlight.getFlightSegments().get(p).getArrivalDateTime());

                String url= Constants.FLIGHTS_IMAGE_URL+SelReturnFlight.getFlightSegments().get(p).getImageFileName()+".png";

                Glide.with(this)
                        .load(url)
                        .placeholder(R.drawable.image_placeholder)
                        .error(R.drawable.image_placeholder)
                        .into(Airline_Image);


                //Bitmap bitmap= Flight_Utils.getImage(this,SelReturnFlight.getFlightSegments().get(p).getImageFileName());
                //Airline_Image.setImageBitmap(bitmap);

                Source.setText(From_Place+" "+From_Time);
                Destination.setText(To_Place+" "+To_Time);

                Air_line_code.setText(SelReturnFlight.getFlightSegments().get(p).getAirLineName()+" "+SelReturnFlight.getFlightSegments().get(p).getOperatingAirlineCode()+" - "+
                        SelReturnFlight.getFlightSegments().get(p).getOperatingAirlineFlightNumber());


                int FlightsegmentsSize=SelReturnFlight.getFlightSegments().size()-1;

                    try {
                        Flight_Deptimes.setText(Util.getReverseDate(SelReturnFlight.getFlightSegments().get(p).getDepartureDateTime().split("T")[0]));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }




                    try {
                        Flight_Arrtimes.setText(Util.getReverseDate(SelReturnFlight.getFlightSegments().get(p).getArrivalDateTime().split("T")[0]));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                String OnwardFareRule=SelReturnFlight.getFlightSegments().get(p).getBookingClassFare().getRule();
                Flight_Class.setText(OnwardFareRule+" | "+selDetails.getclass_type());

                String Duration=Flight_Utils.getDuration(SelReturnFlight.getFlightSegments().get(p).getDepartureDateTime().replace("T"," "),SelReturnFlight.getFlightSegments().get(p).getArrivalDateTime().replace("T"," "));
                duration_stops.setText(""+Duration+"");


                String CheckInBaggage=SelReturnFlight.getFlightSegments().get(p).getBaggageAllowed().getCheckInBaggage();
                String Handbag=SelReturnFlight.getFlightSegments().get(p).getBaggageAllowed().getHandBaggage();


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



                if(SelReturnFlight.getFlightSegments().size()!=1&&p!=FlightsegmentsSize){
                    LayoutInflater inflaterFare1 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View viewHolderFare3 = inflaterFare1.inflate(R.layout.flight_layover_layout, null);
                    LinearLayout linearLayoutLayover = (LinearLayout) viewHolderFare3.findViewById(R.id.layoverLyt);

                     layover= (TextView) viewHolderFare3.findViewById(R.id.layoverTime);

                    String LayDuration=null;
                    if(p==0){
                        LayDuration=Flight_Utils.getDuration
                                (SelReturnFlight.getFlightSegments().get(0).getArrivalDateTime().replace("T"," "),
                                        SelReturnFlight.getFlightSegments().get(1).getDepartureDateTime().replace("T"," "));
                    }
                    if(p==1){
                        LayDuration=Flight_Utils.getDuration
                                (SelReturnFlight.getFlightSegments().get(1).getArrivalDateTime().replace("T"," "),
                                        SelReturnFlight.getFlightSegments().get(2).getDepartureDateTime().replace("T"," "));
                    }
                    if(p==2){
                        LayDuration=Flight_Utils.getDuration
                                (SelReturnFlight.getFlightSegments().get(2).getArrivalDateTime().replace("T"," "),
                                        SelReturnFlight.getFlightSegments().get(3).getDepartureDateTime().replace("T"," "));
                    }

                    layover.setText(""+LayDuration+"");

                    ll.addView(linearLayoutLayover);
                }


            }

            LayoutInflater inflaterFare1 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewHolderFare6 = inflaterFare1.inflate(R.layout.flights_fare_rules_layout, null);
            LinearLayout linearLayoutFare1 = (LinearLayout) viewHolderFare6.findViewById(R.id.layoutFareRule);

            Return_Fare_Rule_tv = (TextView) viewHolderFare6.findViewById(R.id.FareRuleTextView);
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

        if (selDetails.getReturn_date()!=null){
            double OnwardBaseFare=SelOnwardFlight.getFareDetails().getChargeableFares().getActualBaseFare();
            double OnwardTax=SelOnwardFlight.getFareDetails().getChargeableFares().getTax();
            double OnwardTcharge=SelOnwardFlight.getFareDetails().getNonchargeableFares().getTCharge();
            double OnwardTMarkup=SelOnwardFlight.getFareDetails().getNonchargeableFares().getTMarkup();
            double OnwardSTax=SelOnwardFlight.getFareDetails().getChargeableFares().getSTax();
            double OnwardConvFee=SelOnwardFlight.getFareDetails().getChargeableFares().getConveniencefee();


            double FeeandSurchargesOnw=OnwardTax+OnwardTcharge+OnwardTMarkup;

            double ConviFee1;
            int Convi_Fee_TypeOnw=SelOnwardFlight.getFareDetails().getChargeableFares().getConveniencefeeType();
            if(Convi_Fee_TypeOnw==1){
                ConviFee1=OnwardConvFee;
            }else {
                double Total_To_Calculate_Confee= OnwardSTax +FeeandSurchargesOnw+ OnwardBaseFare;
                ConviFee1=(Total_To_Calculate_Confee/100.0f)*OnwardConvFee;
                SelOnwardFlight.getFareDetails().getChargeableFares().setConveniencefee(ConviFee1);
            }




            double ReturnBaseFare=SelReturnFlight.getFareDetails().getChargeableFares().getActualBaseFare();
            double ReturnTax=SelReturnFlight.getFareDetails().getChargeableFares().getTax();
            double ReturnTcharge=SelReturnFlight.getFareDetails().getNonchargeableFares().getTCharge();
            double ReturnTMarkup=SelReturnFlight.getFareDetails().getNonchargeableFares().getTMarkup();
            double ReturnSTax=SelReturnFlight.getFareDetails().getChargeableFares().getSTax();
            double ReturnConvFee=SelReturnFlight.getFareDetails().getChargeableFares().getConveniencefee();


            double FeeandSurchargesRet=ReturnTax+ReturnTcharge+ReturnTMarkup;
            double ConviFeeRet;
            int Convi_Fee_TypeRet=SelOnwardFlight.getFareDetails().getChargeableFares().getConveniencefeeType();
            if(Convi_Fee_TypeRet==1){
                ConviFeeRet=ReturnConvFee;
            }else {
                double Total_To_Calculate_Confee= ReturnSTax +FeeandSurchargesRet+ ReturnBaseFare;
                ConviFeeRet=(Total_To_Calculate_Confee/100.0f)*OnwardConvFee;
                SelReturnFlight.getFareDetails().getChargeableFares().setConveniencefee(ConviFeeRet);
            }


            double GST=OnwardSTax+ReturnSTax;
            double FeeandSurcharges=OnwardTax+OnwardTcharge+OnwardTMarkup+ReturnTax+ReturnTcharge+ReturnTMarkup;
            double Total_Base_fare=OnwardBaseFare+ReturnBaseFare;

            double ConviFee;
            int Convi_Fee_Type=SelOnwardFlight.getFareDetails().getChargeableFares().getConveniencefeeType();
            if(Convi_Fee_Type==1){
                ConviFee=ReturnConvFee;
            }else {
                double Total_To_Calculate_Confee=GST+FeeandSurcharges+Total_Base_fare;
                ConviFee=(Total_To_Calculate_Confee/100.0f)*OnwardConvFee;
            }

            if (ConviFee!=0){
                ConvenienceFeeLinerLayout.setVisibility(View.VISIBLE);
                FLightsConvenienceFeeTextview.setText(CurrencySymbol+""+Util.getprice(curr_value*ConviFee)+"");
            }
            if (GST!=0){
                ServiceTaxLinerLayout.setVisibility(View.VISIBLE);
                Service_TaxTextview.setText(CurrencySymbol+""+Util.getprice(curr_value*GST)+"");
            }

            FeeandSurchargestv.setText(CurrencySymbol+""+Util.getprice(curr_value*FeeandSurcharges)+"");
            BaseFare.setText(CurrencySymbol+""+Util.getprice(curr_value*Total_Base_fare));
            double Total_Fare=GST+ConviFee+FeeandSurcharges+Total_Base_fare;
            TotalAmount.setText(""+CurrencySymbol+Util.getprice(curr_value*Total_Fare));
            Total_Fare_tv.setText(""+CurrencySymbol+Util.getprice(curr_value*Total_Fare));
            TotalFares= String.valueOf(Total_Fare);

        }else {
            double OnwardBaseFare=SelOnwardFlight.getFareDetails().getChargeableFares().getActualBaseFare();
            double OnwardTax=SelOnwardFlight.getFareDetails().getChargeableFares().getTax();
            double OnwardTcharge=SelOnwardFlight.getFareDetails().getNonchargeableFares().getTCharge();
            double OnwardTMarkup=SelOnwardFlight.getFareDetails().getNonchargeableFares().getTMarkup();
            double OnwardSTax=SelOnwardFlight.getFareDetails().getChargeableFares().getSTax();
            double OnwardConvFee=SelOnwardFlight.getFareDetails().getChargeableFares().getConveniencefee();

            double FeeandSurcharges=OnwardTax+OnwardTcharge+OnwardTMarkup;
            double ConviFee;
            int Convi_Fee_Type=SelOnwardFlight.getFareDetails().getChargeableFares().getConveniencefeeType();
            if(Convi_Fee_Type==1){
                ConviFee=OnwardConvFee;
            }else {
                double Total_To_Calculate_Confee=FeeandSurcharges+OnwardBaseFare;
                double con_fee=SelOnwardFlight.getFareDetails().getChargeableFares().getConveniencefee();
                ConviFee=(Total_To_Calculate_Confee/100.0f)*con_fee;
            }

            if (OnwardConvFee!=0){
                ConvenienceFeeLinerLayout.setVisibility(View.VISIBLE);
                FLightsConvenienceFeeTextview.setText(CurrencySymbol+""+Util.getprice(curr_value*ConviFee)+"");
            }
            if (OnwardSTax!=0){
                ServiceTaxLinerLayout.setVisibility(View.VISIBLE);
                Service_TaxTextview.setText(CurrencySymbol+""+Util.getprice(curr_value*OnwardSTax)+"");
            }

            double Total_Fare=OnwardBaseFare+OnwardTax+OnwardTcharge+OnwardTMarkup+OnwardSTax+ConviFee;
            FeeandSurchargestv.setText(CurrencySymbol+""+Util.getprice(curr_value*FeeandSurcharges)+"");
            BaseFare.setText(CurrencySymbol+""+Util.getprice(curr_value*OnwardBaseFare));
            TotalAmount.setText(""+CurrencySymbol+Util.getprice(curr_value*Total_Fare));
            Total_Fare_tv.setText(""+CurrencySymbol+Util.getprice(curr_value*Total_Fare));
            TotalFares= String.valueOf(Total_Fare);
        }


        Onward_Fare_Rule_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callFareRules(SelOnwardFlight.getOriginDestinationoptionId().getKey(),
                              SelOnwardFlight.getFlightSegments().get(0).getOperatingAirlineCode(),
                              SelOnwardFlight.getFlightSegments().get(0).getOperatingAirlineFlightNumber(),
                              SelOnwardFlight.getFlightSegments().get(0).getBookingClassFare().getClassType(),
                              SelOnwardFlight.getProvider());
            }
        });
        if (selDetails.getReturn_date()!=null){
            Return_Fare_Rule_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callFareRules(SelReturnFlight.getOriginDestinationoptionId().getKey(),
                            SelReturnFlight.getFlightSegments().get(0).getOperatingAirlineCode(),
                            SelReturnFlight.getFlightSegments().get(0).getOperatingAirlineFlightNumber(),
                            SelReturnFlight.getFlightSegments().get(0).getBookingClassFare().getClassType(),
                            SelReturnFlight.getProvider());
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
                "&service=1&provider="+Provider+"&tripType="+selDetails.getTrip_Type()+"&couponFare=&userType="+UserId+
                    "&user="+User_agentid;
            Log.d("Fare Rules Url:",url);
            ServiceClasses.getFareRules(Domestic_Flight_Review.this,url);
        }else {
            Util.showMessage(this, Constants.NO_INT_MSG);
        }

    }

    public void getFareRulesResponse(String response) {
        hideProgressDialog();
        if (response != null) {
            System.out.println("Response"+response);
            String rr = response.replaceAll("\\r|\\n", "\n").replace("\\r\\n","\n").replace("\\","'");

            

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Domestic_Flight_Review.this);
            alertDialogBuilder.setTitle("Fare Rule :");
            alertDialogBuilder
                    .setMessage(Html.fromHtml(rr.replaceAll("\"",""), null, new UlTagHandler()))
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
            Typeface face=Typeface.createFromAsset(getAssets(),"fonts/"+getResources().getString(R.string.custom_font));
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
                Intent ip=new Intent(Domestic_Flight_Review.this,Flight_Passenger_Information.class);
                ip.putExtra("SelDOM_Onward",SelOnwardFlight);
                ip.putExtra("SelDOM_Return",SelReturnFlight);
                ip.putExtra("selDetails",selDetails);
                ip.putExtra("TotalFare",TotalFares);
                startActivity(ip);
                break;

        }
    }
}
