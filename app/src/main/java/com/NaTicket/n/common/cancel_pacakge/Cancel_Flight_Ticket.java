package com.NaTicket.n.common.cancel_pacakge;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.NaTicket.n.R;
import com.NaTicket.n.custom.MyTextViewBold;
import com.NaTicket.n.flights.pojo.Flight_Ticket_Details_Pojo;
import com.NaTicket.n.serviceclasses.Service_Cancellations;
import com.NaTicket.n.utils.BackActivity;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by Ankit on 9/12/2016.
 */
public class Cancel_Flight_Ticket extends BackActivity {

    TextView activelfare, Reference_no, journeyname, cencellation, Ticket_Cancel;

    String NAME;
    String Fare = "";
    String listString, listStringR;
    ScrollView ScrollView;
    LinearLayout linearLayout, linearLayoutR, OnwardFlightDetails, ReturnFlightDetails, returnflgtDetails;
    String Refernce_No, EMAIL_ID, FlightDetail, Names, FlightNumberOnward = "", FlightNumberReturn = "";

    String data;
    int code;
    String firstname;
    private ArrayList<String> PassengerFullNameArray = new ArrayList<>();
    ArrayList<String> PassgArList = new ArrayList<String>();
    ArrayList<String> PassgArListR = new ArrayList<String>();
    ArrayList<String> FlightArList = new ArrayList<String>();
    ArrayList<String> FlightArListR = new ArrayList<String>();
    Flight_Ticket_Details_Pojo ticket_details;
    ProgressDialog mProgressDialog;


    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.flight_cancellation);
        inittoolbar();
        TextView ToolBar_Title = (TextView) findViewById(R.id.toolbartitle);
        ToolBar_Title.setText("" + "Cancel Ticket" + "");

        ticket_details = (Flight_Ticket_Details_Pojo) getIntent().getSerializableExtra("Flight_Details");
        initviews();
    }


    private void initviews() {
        Reference_no = (TextView) findViewById(R.id.Enter_reference_no);
        journeyname = (TextView) findViewById(R.id.Journey_Details);
        activelfare = (TextView) findViewById(R.id.Total_fare);
        OnwardFlightDetails = (LinearLayout) findViewById(R.id.OnwarFlightdetails);
        ReturnFlightDetails = (LinearLayout) findViewById(R.id.ReturnFlightdetails);
        cencellation = (TextView) findViewById(R.id.Cancellation_policy);
        Ticket_Cancel = (TextView) findViewById(R.id.Ticket_Cancel);
        //ScrollView = (ScrollView) findViewById(R.id.cancelflightscrollview);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout12);
        linearLayoutR = (LinearLayout) findViewById(R.id.linearLayout13);
        returnflgtDetails = (LinearLayout) findViewById(R.id.returnflgtDetails);
        returnflgtDetails.setVisibility(View.GONE);

        if (ticket_details != null) {
            setFlightData();
        }
    }


    public void setFlightData() {

        double TotalOnwardFare;
        final CheckBox ChboxFlight = new CheckBox(this);
        final CheckBox ChboxFlight2 = new CheckBox(this);
        final CheckBox ChboxFlight3 = new CheckBox(this);
        final CheckBox ChboxFlightR = new CheckBox(this);
        final CheckBox ChboxFlightR2 = new CheckBox(this);
        final CheckBox ChboxFlightR3 = new CheckBox(this);
        final CheckBox Chbox = new CheckBox(this);
        final CheckBox Chbox2 = new CheckBox(this);
        final CheckBox Chbox3 = new CheckBox(this);
        final CheckBox ChboxR = new CheckBox(this);
        final CheckBox ChboxR2 = new CheckBox(this);
        final CheckBox ChboxR3 = new CheckBox(this);


        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        String ActualFare = ticket_details.getActualBaseFare();
        String SourceName = ticket_details.getSourceName();
        String DestinationName = ticket_details.getDestinationName();
        String JourneyDate = ticket_details.getJourneyDate();


        double OnwardBaseFare = Double.parseDouble(ticket_details.getActualBaseFare());
        double OnwardTax = Double.parseDouble(ticket_details.getTax());
        double OnwardTcharge = Double.parseDouble(ticket_details.getTCharge());
        double OnwardTMarkup = Double.parseDouble(ticket_details.getTMarkup());
        double OnwardSTax = Double.parseDouble(ticket_details.getSTax());
        double OnwardConvFee = Double.parseDouble(ticket_details.getConveniencefee());
        double PromoAmount = Double.parseDouble(ticket_details.getPromoCodeAmount());


        //double Onward_Total = OnwardBaseFare + OnwardTax + OnwardTcharge + OnwardTMarkup + OnwardSTax + OnwardConvFee - PromoAmount;
        double Onward_Total = OnwardBaseFare + OnwardTax + OnwardTcharge + OnwardTMarkup + OnwardSTax - PromoAmount;
        double currvalue = Double.parseDouble(ticket_details.getCurrencyValue());


        String CancellationPolicy = ticket_details.getOnwardFlightSegments().get(0).getBookingClassFare().getRule() + " fare";
        Refernce_No = ticket_details.getBookingRefNo();
        EMAIL_ID = ticket_details.getEmailId();
        Names = ticket_details.getNames();
        List<String> items = Arrays.asList(Names.split("\\s*~\\s*"));
        System.out.println("seatCount" + items);


        cencellation.setText("" + CancellationPolicy + "");
        Reference_no.setText("Ref no. : " + Refernce_No + "");
        journeyname.setText("" + SourceName + "  To  \n" + DestinationName);//+"  On "+JourneyDate+" "+OperatorName+" - "+BusTypeName
        activelfare.setText(Constants.AGENT_CURRENCY_SYMBOL + " " + Util.getprice(Onward_Total * currvalue) + "");
        TotalOnwardFare = Onward_Total;


        for (int p = 0; p < ticket_details.getOnwardFlightSegments().size(); p++) {
            String ArrivalTime = null, DisptTime = null;

            String ArrivalAirportCode = ticket_details.getOnwardFlightSegments().get(p).getArrivalAirportCode();
            String DepartureAirportCode = ticket_details.getOnwardFlightSegments().get(p).getDepartureAirportCode();
            String AirlineName = ticket_details.getOnwardFlightSegments().get(p).getAirLineName();
            FlightNumberOnward = ticket_details.getOnwardFlightSegments().get(p).getFlightNumber();
            ArrivalTime = Util.getTime(ticket_details.getOnwardFlightSegments().get(p).getArrivalDateTime());
            DisptTime = Util.getTime(ticket_details.getOnwardFlightSegments().get(p).getDepartureDateTime());

            String depDate = "", ArrDate = "";
            try {
                depDate = Util.getReverseDate(ticket_details.getOnwardFlightSegments().get(p).getDepartureDateTime().split("T")[0]);
                ArrDate = Util.getReverseDate(ticket_details.getOnwardFlightSegments().get(p).getArrivalDateTime().split("T")[0]);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            MyTextViewBold tv = new MyTextViewBold(this);
            tv.setTextColor(getResources().getColor(R.color.text_primary));
            Typeface face = Typeface.createFromAsset(getAssets(), "fonts/OpenSans_Regular.ttf");
            tv.setTypeface(face);
            tv.setText("" + AirlineName + "(" + DepartureAirportCode + " to " + ArrivalAirportCode + ")\nDeparture On : " + depDate + " at " + DisptTime + ",\nArrival On : " + ArrDate + " at " + ArrivalTime);
            //tv.setText("Departure On : " + depDate + " at " + DisptTime + ",\nArrival On : " + ArrDate + " at " + ArrivalTime);


            if (p == 0) {
                ChboxFlight.setTag(FlightNumberOnward);
                ChboxFlight.setText(AirlineName + "(" + DepartureAirportCode + " to " + ArrivalAirportCode + ")");
            }
            if (p == 1) {
                ChboxFlight2.setTag(FlightNumberOnward);
                ChboxFlight2.setText(AirlineName + "(" + DepartureAirportCode + " to " + ArrivalAirportCode + ")");
            }
            if (p == 2) {
                ChboxFlight3.setTag(FlightNumberOnward);
                ChboxFlight3.setText(AirlineName + "(" + DepartureAirportCode + " to " + ArrivalAirportCode + ")");
            }
            //ChboxFlight.setLayoutParams(lparams);

            for (int i = 0; i < items.size(); i++) {
                String data = items.get(i);


                StringTokenizer st = new StringTokenizer(data, "|");
                String tit = st.nextToken();
                firstname = st.nextToken();
                String lastname = st.nextToken();
                String adult = st.nextToken();

                if (p == 0) {
                    Chbox.setTag(firstname);
                    Chbox.setText("" + tit + " " + firstname + " " + lastname + " (" + adult + ")");
                }
                if (p == 1) {
                    Chbox2.setTag(firstname);
                    Chbox2.setText("" + tit + " " + firstname + " " + lastname + " (" + adult + ")");
                }
                if (p == 2) {
                    Chbox3.setTag(firstname);
                    Chbox3.setText("" + tit + " " + firstname + " " + lastname + " (" + adult + ")");
                }
                //Chbox.setLayoutParams(lparams);
            }


            if (p == 0) {
                OnwardFlightDetails.addView(tv);
                OnwardFlightDetails.addView(ChboxFlight);
                OnwardFlightDetails.addView(Chbox);

            }

            if (p == 1) {
                OnwardFlightDetails.addView(tv);
                OnwardFlightDetails.addView(ChboxFlight2);
                OnwardFlightDetails.addView(Chbox2);
            }
            if (p == 2) {
                OnwardFlightDetails.addView(tv);
                OnwardFlightDetails.addView(ChboxFlight3);
                OnwardFlightDetails.addView(Chbox3);
            }


        }


        if (ticket_details.getReturnFlightSegments() != null && ticket_details.getReturnFlightSegments().size() != 0) {
            returnflgtDetails.setVisibility(View.VISIBLE);

            double ReturnBaseFare = Double.parseDouble(ticket_details.getActualBaseFareRet());
            double ReturnTax = Double.parseDouble(ticket_details.getTaxRet());
            double ReturnTcharge = Double.parseDouble(ticket_details.getTChargeRet());
            double ReturnTMarkup = Double.parseDouble(ticket_details.getTMarkupRet());
            double ReturnSTax = Double.parseDouble(ticket_details.getSTaxRet());
            double ReturnConvFee = Double.parseDouble(ticket_details.getConveniencefeeRet());


            //double Return_Total=ReturnBaseFare+ReturnTax+ReturnTcharge+ReturnTMarkup+ReturnSTax+ReturnConvFee;
            double Return_Total = ReturnBaseFare + ReturnTax + ReturnTcharge + ReturnTMarkup + ReturnSTax;
            activelfare.setText(Constants.AGENT_CURRENCY_SYMBOL + " " + Util.getprice((Return_Total + TotalOnwardFare) * currvalue) + "");


            for (int p = 0; p < ticket_details.getReturnFlightSegments().size(); p++) {

                String ArrivalTime = null, DisptTime = null;

                String ArrivalAirportCode = ticket_details.getReturnFlightSegments().get(p).getArrivalAirportCode();
                String DepartureAirportCode = ticket_details.getReturnFlightSegments().get(p).getDepartureAirportCode();
                String AirlineName = ticket_details.getReturnFlightSegments().get(p).getAirLineName();
                FlightNumberReturn = ticket_details.getReturnFlightSegments().get(p).getFlightNumber();
                ArrivalTime = Util.getTime(ticket_details.getReturnFlightSegments().get(p).getArrivalDateTime());
                DisptTime = Util.getTime(ticket_details.getReturnFlightSegments().get(p).getDepartureDateTime());


                String depDate = "", ArrDate = "";
                try {
                    depDate = Util.getReverseDate(ticket_details.getReturnFlightSegments().get(p).getDepartureDateTime().split("T")[0]);
                    ArrDate = Util.getReverseDate(ticket_details.getReturnFlightSegments().get(p).getArrivalDateTime().split("T")[0]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                MyTextViewBold tv = new MyTextViewBold(this);
                tv.setTextColor(getResources().getColor(R.color.text_primary));
                Typeface face = Typeface.createFromAsset(getAssets(), "fonts/OpenSans_Regular.ttf");
                tv.setTypeface(face);
                tv.setText("" + AirlineName + "(" + DepartureAirportCode + " to " + ArrivalAirportCode + ")\nDeparture On : " + depDate + " at " + DisptTime + ",\nArrival On : " + ArrDate + " at " + ArrivalTime);


                if (p == 0) {
                    ChboxFlightR.setTag(FlightNumberReturn);
                    ChboxFlightR.setText(AirlineName + "(" + DepartureAirportCode + " to " + ArrivalAirportCode + ")");
                }
                if (p == 1) {
                    ChboxFlightR2.setTag(FlightNumberReturn);
                    ChboxFlightR2.setText(AirlineName + "(" + DepartureAirportCode + " to " + ArrivalAirportCode + ")");
                }
                if (p == 2) {
                    ChboxFlightR3.setTag(FlightNumberReturn);
                    ChboxFlightR3.setText(AirlineName + "(" + DepartureAirportCode + " to " + ArrivalAirportCode + ")");
                }
                //ChboxFlightR.setLayoutParams(lparams);

                for (int i = 0; i < items.size(); i++) {
                    String data = items.get(i);


                    StringTokenizer st = new StringTokenizer(data, "|");
                    String tit = st.nextToken();
                    firstname = st.nextToken();
                    String lastname = st.nextToken();
                    String adult = st.nextToken();

                    if (p == 0) {
                        ChboxR.setTag(firstname);
                        ChboxR.setText("" + tit + " " + firstname + " " + lastname + " (" + adult + ")");
                    }
                    if (p == 1) {
                        ChboxR2.setTag(firstname);
                        ChboxR2.setText("" + tit + " " + firstname + " " + lastname + " (" + adult + ")");
                    }
                    if (p == 2) {
                        ChboxR3.setTag(firstname);
                        ChboxR3.setText("" + tit + " " + firstname + " " + lastname + " (" + adult + ")");
                    }
                    //Chbox.setLayoutParams(lparams);
                }
                if (p == 0) {
                    ReturnFlightDetails.addView(tv);
                    ReturnFlightDetails.addView(ChboxFlightR);
                    ReturnFlightDetails.addView(ChboxR);
                }
                if (p == 1) {
                    ReturnFlightDetails.addView(tv);
                    ReturnFlightDetails.addView(ChboxFlightR2);
                    ReturnFlightDetails.addView(ChboxR2);
                }
                if (p == 2) {
                    ReturnFlightDetails.addView(tv);
                    ReturnFlightDetails.addView(ChboxFlightR3);
                    ReturnFlightDetails.addView(ChboxR3);
                }


            }


        }


        try {

            for (int p = 0; p < ticket_details.getOnwardFlightSegments().size(); p++) {

                for (int i = 0; i < items.size(); i++) {
                    String data = items.get(i);


                    StringTokenizer st = new StringTokenizer(data, "|");
                    String tit = st.nextToken();
                    firstname = st.nextToken();
                    String lastname = st.nextToken();
                    String adult = st.nextToken();

                    Chbox.setTag(firstname);
                    Chbox.setText("" + tit + " " + firstname + " " + lastname + " (" + adult + ")");
                    Chbox.setLayoutParams(lparams);
                }
            }


        } catch (NoSuchElementException e) {

            System.out.println("Exception: " + items);
        }


        //linearLayout.addView(ChboxFlight);
        //linearLayout.addView(Chbox);
        //linearLayoutR.addView(ChboxFlightR);
        //linearLayoutR.addView(Chbox);


        Chbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Chbox.isChecked()) {
                    PassgArList.addAll(Arrays.<String>asList(String.valueOf(Chbox.getTag())));
                } else {
                    if (PassgArList.contains(Chbox.getTag())) {
                        PassgArList.remove(Chbox.getTag());
                    }
                }

            }
        });

        Chbox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Chbox2.isChecked()) {
                    PassgArList.addAll(Arrays.<String>asList(String.valueOf(Chbox2.getTag())));
                } else {
                    if (PassgArList.contains(Chbox2.getTag())) {
                        PassgArList.remove(Chbox2.getTag());
                    }
                }

            }
        });
        Chbox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Chbox3.isChecked()) {
                    PassgArList.addAll(Arrays.<String>asList(String.valueOf(Chbox3.getTag())));
                } else {
                    if (PassgArList.contains(Chbox3.getTag())) {
                        PassgArList.remove(Chbox3.getTag());
                    }
                }

            }
        });


        ChboxR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ChboxR.isChecked()) {
                    PassgArListR.addAll(Arrays.<String>asList(String.valueOf(ChboxR.getTag())));
                } else {
                    if (PassgArListR.contains(ChboxR.getTag())) {
                        PassgArListR.remove(ChboxR.getTag());
                    }
                }

            }
        });

        ChboxR2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ChboxR2.isChecked()) {
                    PassgArListR.addAll(Arrays.<String>asList(String.valueOf(ChboxR2.getTag())));
                } else {
                    if (PassgArListR.contains(ChboxR2.getTag())) {
                        PassgArListR.remove(ChboxR2.getTag());
                    }
                }

            }
        });

        ChboxR3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ChboxR3.isChecked()) {
                    PassgArListR.addAll(Arrays.<String>asList(String.valueOf(ChboxR3.getTag())));
                } else {
                    if (PassgArListR.contains(ChboxR3.getTag())) {
                        PassgArListR.remove(ChboxR3.getTag());
                    }
                }

            }
        });


        ChboxFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ChboxFlight.isChecked()) {
                    FlightArList.addAll(Arrays.<String>asList(String.valueOf(ChboxFlight.getTag())));
                } else {
                    if (FlightArList.contains(ChboxFlight.getTag())) {
                        FlightArList.remove(ChboxFlight.getTag());
                    }
                }

            }
        });
        ChboxFlight2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ChboxFlight2.isChecked()) {
                    FlightArList.addAll(Arrays.<String>asList(String.valueOf(ChboxFlight2.getTag())));
                } else {
                    if (FlightArList.contains(ChboxFlight2.getTag())) {
                        FlightArList.remove(ChboxFlight2.getTag());
                    }
                }

            }
        });
        ChboxFlight3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ChboxFlight3.isChecked()) {
                    FlightArList.addAll(Arrays.<String>asList(String.valueOf(ChboxFlight3.getTag())));
                } else {
                    if (FlightArList.contains(ChboxFlight3.getTag())) {
                        FlightArList.remove(ChboxFlight3.getTag());
                    }
                }

            }
        });
        ChboxFlightR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ChboxFlightR.isChecked()) {
                    FlightArListR.addAll(Arrays.<String>asList(String.valueOf(ChboxFlightR.getTag())));
                } else {
                    if (FlightArListR.contains(ChboxFlightR.getTag())) {
                        FlightArListR.remove(ChboxFlightR.getTag());
                    }
                }

            }
        });
        ChboxFlightR2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ChboxFlightR2.isChecked()) {
                    FlightArListR.addAll(Arrays.<String>asList(String.valueOf(ChboxFlightR2.getTag())));
                } else {
                    if (FlightArListR.contains(ChboxFlightR2.getTag())) {
                        FlightArListR.remove(ChboxFlightR2.getTag());
                    }
                }

            }
        });


        ChboxFlightR3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ChboxFlightR3.isChecked()) {
                    FlightArListR.addAll(Arrays.<String>asList(String.valueOf(ChboxFlightR3.getTag())));
                } else {
                    if (FlightArListR.contains(ChboxFlightR3.getTag())) {
                        FlightArListR.remove(ChboxFlightR3.getTag());
                    }
                }

            }
        });

        Ticket_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // add elements to hs, including duplicates
                Set<String> hs = new HashSet<>();
                hs.addAll(PassgArList);
                PassgArList.clear();
                PassgArList.addAll(hs);

                // add elements to hss, including duplicates
                Set<String> hss = new HashSet<>();
                hss.addAll(PassgArListR);
                PassgArListR.clear();
                PassgArListR.addAll(hss);

                listString = TextUtils.join("~", PassgArList);
                listStringR=TextUtils.join("~", PassgArListR);
                FlightNumberOnward = TextUtils.join("~", FlightArList);
                FlightNumberReturn = TextUtils.join("~", FlightArListR);

                if (!listString.equals("") && !FlightNumberOnward.equals("")) {
                    callGetTicketCancelDetails();
                } else {

                    if (ticket_details.getReturnFlightSegments() != null && ticket_details.getReturnFlightSegments().size() != 0) {
                        if (!listStringR.equals("") && !FlightNumberReturn.equals("")) {
                            callGetTicketCancelDetails();
                        }else{
                            Toast.makeText(Cancel_Flight_Ticket.this,
                                    "Select at least one passenger and one Flight", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(Cancel_Flight_Ticket.this,
                                "Select at least one passenger and one Flight", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }


    private void callGetTicketCancelDetails() {
        if (Util.isNetworkAvailable(getApplicationContext())) {
            showProgressDialog("Please wait", this);

            String strurl = null;
            if (ticket_details.getReturnFlightSegments() != null && ticket_details.getReturnFlightSegments().size() != 0&&!FlightNumberReturn.equals("")&&!listStringR.equals("")) {

                if(!FlightNumberOnward.equals("")&&!listString.equals("")) {
                    strurl = Constants.CancelFlightTicket + "referenceNo=" + Refernce_No + "&onwardFlightNumbers=" + FlightNumberOnward + "&onwardPax=" + listString + "&returnFlightNumbers=" + FlightNumberReturn + "&returnPax=" + listStringR;

                }else{
                    strurl = Constants.CancelFlightTicket + "referenceNo=" + Refernce_No + "&onwardFlightNumbers=" + FlightNumberReturn + "&onwardPax=" + listStringR + "&returnFlightNumbers=" + "&returnPax=";
                }
            } else {
                strurl = Constants.CancelFlightTicket + "referenceNo=" + Refernce_No + "&onwardFlightNumbers=" + FlightNumberOnward + "&onwardPax=" + listString + "&returnFlightNumbers=" + "&returnPax=";
            }

            Service_Cancellations.GetCancelTicketDetails(Cancel_Flight_Ticket.this, strurl);
        } else {
            Util.showMessage(Cancel_Flight_Ticket.this, Constants.NO_INT_MSG);
        }
    }

    public void getCancelDetailsResponse(String response, int StatusCode) {
        hideProgressDialog();
        if (response != null) {
            if (StatusCode == 500) {
                Toast.makeText(Cancel_Flight_Ticket.this,
                        "Error occurred", Toast.LENGTH_LONG).show();
                // Error_Invalid_Reference.setVisibility(View.VISIBLE);
            } else if (StatusCode == 200) {
                final String Message=Util.getResponseMessage(response);

                android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                        Cancel_Flight_Ticket.this).create();
                alertDialog.setMessage(Message);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(Message.contains("Failed")){
                            dialog.dismiss();
                        }else{
                            Util.startHomeActivity(Cancel_Flight_Ticket.this);
                        }


                    }
                });
                alertDialog.show();
            } else {
                Util.Vibrate(this);
                Util.showMessage(this, "Failed");
            }
        } else {
            Util.Vibrate(this);
            Util.showMessage(this, "Failed: No Data received");
        }
    }

    /*public class CancelFlightTicket extends AsyncTask<String, String, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(Cancel_Flight_Ticket.this, "", "Please Wait  .....");
        }


        @Override
        protected String doInBackground(String... params) {
            String strurl = null;

            try {

                if (ticket_details.getReturnFlightSegments() != null && ticket_details.getReturnFlightSegments().size() != 0&&!FlightNumberReturn.equals("")&&!listStringR.equals("")) {

                    if(!FlightNumberOnward.equals("")&&!listString.equals("")) {
                        strurl = Constants.CancelFlightTicket + "referenceNo=" + Refernce_No + "&onwardFlightNumbers=" + FlightNumberOnward + "&onwardPax=" + listString + "&returnFlightNumbers=" + FlightNumberReturn + "&returnPax=" + listStringR;

                    }else{
                        strurl = Constants.CancelFlightTicket + "referenceNo=" + Refernce_No + "&onwardFlightNumbers=" + FlightNumberReturn + "&onwardPax=" + listStringR + "&returnFlightNumbers=" + "&returnPax=";
                    }
                } else {
                    strurl = Constants.CancelFlightTicket + "referenceNo=" + Refernce_No + "&onwardFlightNumbers=" + FlightNumberOnward + "&onwardPax=" + listString + "&returnFlightNumbers=" + "&returnPax=";
                }

                System.out.println(strurl);


                DefaultHttpClient httpClient = new DefaultHttpClient();


                HttpGet httpGet = new HttpGet(strurl);
                httpGet.setHeader("ConsumerKey", Constants.ConsumerKey);
                httpGet.setHeader("ConsumerSecret", Constants.ConsumerSecret);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                try {
                    data = httpClient.execute(httpGet, responseHandler);
                    HttpResponse response = httpClient.execute(httpGet);
                    code = response.getStatusLine().getStatusCode();
                    System.out.println("BookingDetails " + strurl + "Data  " + data);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                //Util.showMessage(Cancel_Flight_Ticket.this, "Something went wrong");
            }


            return data;


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //  JSONObject jsonObject = null;
            try {


                if(data!=null) {
                    System.out.println("BookingDetails" + data);
                    dialog.dismiss();
                    if (code == 500) {
                        Toast.makeText(Cancel_Flight_Ticket.this,
                                "Error occurred", Toast.LENGTH_LONG).show();
                        // Error_Invalid_Reference.setVisibility(View.VISIBLE);
                    } else {

                        JSONObject jsonObject = null;
                        JSONArray results, PaymentGateWay;
                        JSONObject ResultObject = new JSONObject(data);
                        System.out.println("JSON Parseing " + ResultObject);

                        String Status = ResultObject.getString("ResponseStatus");


                        final String Message = ResultObject.optString("Message");
                   *//* String TotalTicketFare = ResultObject.optString("TotalTicketFare");
                    String TotalRefundAmount = ResultObject.optString("TotalRefundAmount");
                    String CancellationCharges = ResultObject.optString("CancellationCharges");
                    String isSeatCancellable = ResultObject.optString("isSeatCancellable");
                    String PartialCancellationAllowed = ResultObject.optString("PartialCancellationAllowed");*//*
                        if (Status != null) {
                            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                    Cancel_Flight_Ticket.this).create();
                            alertDialog.setMessage(Message);
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if(Message.contains("Failed")){
                                        dialog.dismiss();
                                    }else{
                                        Util.startHomeActivity(Cancel_Flight_Ticket.this);
                                    }


                                }
                            });
                            alertDialog.show();

                        }
                    }
                }else{
                    dialog.dismiss();
                    Util.showMessage(Cancel_Flight_Ticket.this,"Failed: No Data received");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }*/

    private void showProgressDialog(String msg, Context context) {
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
}
