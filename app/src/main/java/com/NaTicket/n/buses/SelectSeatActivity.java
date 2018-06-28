package com.NaTicket.n.buses;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.NaTicket.n.loginpackage.pojo.Login_utils;
import com.NaTicket.n.serviceclasses.ServiceHandler;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.R;
import com.NaTicket.n.utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Ankit on 7/6/2016.
 */
public class SelectSeatActivity extends AppCompatActivity {

    TextView Seats, Net_fares;
    Button Done, Aminites, BusImages;
    RelativeLayout RelativeLayout;
    LinearLayout LinearLayout,/* LinearAminites_Layout,*/mSeat_icons_image,mSeat_icons_image_sleeper;
    LinearLayout scrollView;                                                /////////////////////
    Dialog dialog;
    ImageView SteeringImageView;
    String cancellationPolicy;
    String polcy;
            String Arrival_id, Destination_id, Journey_Date, ObiboAPIProvidrer, Provider, Operator, BusTypeID, PartialCancellationAllowed, ConvenienceFee,ConvenienceFeeType;
    String[] scripts;
    String cancalpolicy = "";

    Button lower, upper;
    Button myButton;
    JSONArray results;
    ProgressDialog mProgressDialog;
    int Screenheight, Screenwidth;

    Boolean selectseat = false;
    ArrayList<String> arList = new ArrayList<String>();
    ArrayList<String> netfare = new ArrayList<String>();
    ArrayList<String> ServiceTaxList = new ArrayList<String>();
    ArrayList<String> ServiceChargeList = new ArrayList<String>();
    ArrayList<String> PriceArray = new ArrayList<String>();
    ArrayList<String> Seatcodes = new ArrayList<String>();
    ArrayList<String> ConvinenceFee = new ArrayList<String>();
    String NetTotalAmount;

    String ZindexString;
    String ServiceTaxes, OperatorserviceCharge, Fares, SeatCodesString, ConvienceFeeString, TotalConvienceFeeString;

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String userLogin, agentLogin;
    String UserId, User_agentid;

    int x, y;
    ////*****TAb View****????
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    final Context context = this;
    String dialogboxseats = "";
    double dialogboxamount, dialogboxservicetax, dialogboxservicecharge;


    String ReturnNumberOfSeats, IsReturnYes, RETURNDate;

    int SeatWidth;
    int SeatHeaight;

    boolean UperLoarSelected = false;
    RelativeLayout RelativeSeatLayout;
    RelativeLayout RelativeSeatLayoutUpper;
    double NetFare=0;
    int noofSeats;

    RelativeLayout LoadingLyt,ViewLyt;

    Login_utils login_utils;
    String User_Role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_seat_layout);



        LoadingLyt= (RelativeLayout) findViewById(R.id.LoadingLyt);
        ViewLyt= (RelativeLayout) findViewById(R.id.ViewLyt);

        LoadingLyt.setVisibility(View.VISIBLE);
        ViewLyt.setVisibility(View.GONE);
        RelativeSeatLayoutUpper = (RelativeLayout) findViewById(R.id.Uppersetlayout);
        RelativeSeatLayout = (RelativeLayout) findViewById(R.id.setlayout);
        SteeringImageView = (ImageView) findViewById(R.id.steering);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        Screenheight = displaymetrics.heightPixels;
        Screenwidth = displaymetrics.widthPixels;
        System.out.println("Device width :" + Screenwidth + " Device Height :" + Screenheight);


        SharedPreferences prefer = getApplicationContext().getSharedPreferences("Filter", MODE_PRIVATE);
        prefer.edit().remove("ACSELECTED").apply();
        prefer.edit().remove("NonACSELECTED").apply();
        prefer.edit().remove("SleeperSELECTED").apply();
        prefer.edit().remove("NonSleeperSELECTED").apply();


       /* pref = getSharedPreferences("LoginPreference", MODE_PRIVATE);
        editor = pref.edit();
        userLogin = pref.getString("UserLOGIN", null);
        agentLogin = pref.getString("AgentLOGIN", null);
        if (userLogin != null && userLogin.equals("Yes")) {
            UserId = "6";
            User_agentid = pref.getString("User_agentid", null);
        } else if (agentLogin != null && agentLogin.equals("Yes")) {
            UserId = "4";
            User_agentid = pref.getString("User_agentid", null);
        } else {
            UserId = "5";
            User_agentid = pref.getString("SuperAdmin", null);
        }*/

        login_utils=new Login_utils(this);

        getLoginPreefernces();

        switch (User_Role) {
            case "5":
                UserId = "5";
                User_agentid = "";
                break;
            case "6":
                UserId = "6";
                User_agentid = login_utils.getUserDetails(Constants.USERID);
                break;
            case "4":
                UserId = "4";
                User_agentid = login_utils.getUserDetails(Constants.USERID);
                break;
        }

        mSeat_icons_image= (android.widget.LinearLayout) findViewById(R.id.info_image_view);
        mSeat_icons_image_sleeper= (android.widget.LinearLayout) findViewById(R.id.info_image_view_sleeper);

        /*LinearAminites_Layout = (LinearLayout) findViewById(R.id.Aminites_Layout);
        Aminites = (Button) findViewById(R.id.AminitesT);
        Aminites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectSeatActivity.this, AminitesActivity.class);
                i.putExtra("id", 0);
                startActivity(i);
            }
        });
        BusImages = (Button) findViewById(R.id.Bus_ImagesT);
        BusImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectSeatActivity.this, AminitesActivity.class);
                i.putExtra("id", 1);
                startActivity(i);
            }
        });*/
        scrollView = (LinearLayout) findViewById(R.id.SeatScrollView1);
        Seats = (TextView) findViewById(R.id.No_of_seats);
        Net_fares = (TextView) findViewById(R.id.Net_Fare);
        RelativeLayout = (RelativeLayout) findViewById(R.id.Done_layout);
        RelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // custom dialog
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.custemalertdialogbox);
                dialog.setTitle("Fare Details ");



                // set the custom dialog components - text, image and button
                TextView noofseats = (TextView) dialog.findViewById(R.id.Noofseats);
                TextView Amount = (TextView) dialog.findViewById(R.id.amount);
                TextView serviceTax = (TextView) dialog.findViewById(R.id.servicetax);
                TextView serviceCharge = (TextView) dialog.findViewById(R.id.servicecharge);
                TextView total = (TextView) dialog.findViewById(R.id.total);
                TextView ConvenienceFeeamount = (TextView) dialog.findViewById(R.id.ConvenienceFeeamount);
                LinearLayout ConfeeLinearlayout = (LinearLayout) dialog.findViewById(R.id.ConvenienceFee);

                noofseats.setText("" + dialogboxseats + "");
                Amount.setText("" + dialogboxamount + "");
                serviceTax.setText("" + dialogboxservicetax + "");
                serviceCharge.setText("" + dialogboxservicecharge + "");





                Double d = Double.parseDouble(String.valueOf(dialogboxamount));
                Double Servicecharge = Double.parseDouble(String.valueOf(dialogboxservicetax));
                Double Operatercharge = Double.parseDouble(String.valueOf(dialogboxservicecharge));


                int roundValue = RoundOff(d);
                int ServCharge = RoundOff(Servicecharge);
                int Opcharge = RoundOff(Operatercharge);

                double ConFee = 0;
                if (UserId.equals("4")) {
                    ConfeeLinearlayout.setVisibility(View.GONE);
                } else {
                    if (!ConvenienceFee.equals("0")) {

                        if(!ConvenienceFeeType.equals("0")){
                            ConfeeLinearlayout.setVisibility(View.VISIBLE);

                            Double ConveniFee = Double.parseDouble(ConvenienceFee);
                            int ConvFee = RoundOff(ConveniFee);
                            int noofSeats = arList.size();
                            ConFee = noofSeats * ConvFee;
                            ConvenienceFeeamount.setText("" + ConFee + "");
                        }else{
                            ConfeeLinearlayout.setVisibility(View.VISIBLE);
                            Double ConveniFee = Double.parseDouble(ConvenienceFee);
                            Double ConvFeePerVal=ConveniFee/100.0f;

                            double Net=roundValue + ServCharge + Opcharge;
                            ConFee=ConvFeePerVal*Net;
                            String ConV=String.format("%.2f", ConFee);
                            ConvenienceFeeamount.setText("" + ConV + "");


                        }
                    }

                }



                NetFare = roundValue + ServCharge + Opcharge + ConFee;
                total.setText("₹ " + NetFare + "");


                Button dialogButton = (Button) dialog.findViewById(R.id.cancel_dialog);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });


        SharedPreferences preference = getApplicationContext().getSharedPreferences("JourneyDetails", MODE_PRIVATE);
        cancellationPolicy = preference.getString("CancellationPolicy", null);
        BusTypeID = preference.getString("BusTypeID", null);
        Arrival_id = preference.getString("ARRIVAL_ID", null);
        Destination_id = preference.getString("DESTINATION_ID", null);
        Journey_Date = preference.getString("JourneyDate", null);
        PartialCancellationAllowed = preference.getString("PartialCancellationAllowed", null);
        ObiboAPIProvidrer = preference.getString("ObiboAPIProvider", null);
        Provider = preference.getString("Provider", null);
        Operator = preference.getString("Operator", null);
        ConvenienceFee = preference.getString("ConvenienceFee", null);
        ConvenienceFeeType=preference.getString("ConvenienceFeeType",null);
        IsReturnYes = preference.getString("IsReturnYes", null);
        RETURNDate = preference.getString("PassengerRETURN_DATE", null);
        ReturnNumberOfSeats = preference.getString("ReturnNumberOfSeats", null);
        System.out.println("Date :" + Journey_Date + "JourneyDate" + "JourneyDetails");

        LinearLayout = (LinearLayout) findViewById(R.id.UppearLayout);
        upper = (Button) findViewById(R.id.Upper);


        upper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upper.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                upper.setTextColor(getResources().getColor(R.color.colorWhite));
                lower.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                lower.setTextColor(getResources().getColor(R.color.colorPrimary));
                ZindexString = "1";
                UperLoarSelected = true;
                try {
                    RelativeSeatLayout.setVisibility(View.GONE);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                SteeringImageView.setVisibility(View.GONE);
                RelativeSeatLayoutUpper.setVisibility(View.VISIBLE);

                //     seatCreation();
            }
        });
        lower = (Button) findViewById(R.id.Lower);
        lower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lower.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                lower.setTextColor(getResources().getColor(R.color.colorWhite));
                upper.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                upper.setTextColor(getResources().getColor(R.color.colorPrimary));
                ZindexString = "0";
                UperLoarSelected = true;

                try {
                    RelativeSeatLayoutUpper.setVisibility(View.GONE);
                    SteeringImageView.setVisibility(View.VISIBLE);
                    RelativeSeatLayout.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //      seatCreation();
            }
        });




        Done = (Button) findViewById(R.id.Done);
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  new BlockTicket().execute();
                String listString;
               /* for (int i=0; i<arList.size(); i++)
                {
                    String s= arList.get(i);
                    if(i==arList.size()-1){

                        listString += s ;
                    }
                    else{
                        listString += s + "~";
                    }

                }*/


                if (IsReturnYes.equals("Yes")) {
                    String numberOfSeats = "" + arList.size();
                    if (numberOfSeats.equals(ReturnNumberOfSeats)) {
                        SeatCodesString = TextUtils.join("~", Seatcodes);

                        listString = TextUtils.join("~", arList);
                        ServiceTaxes = TextUtils.join("~", ServiceTaxList);
                        OperatorserviceCharge = TextUtils.join("~", ServiceChargeList);
                        Fares = TextUtils.join("~", PriceArray);
                        ConvienceFeeString = TextUtils.join("~", ConvinenceFee);
                        System.out.println("SeatCodesString :" + SeatCodesString);

                        Intent i = new Intent(SelectSeatActivity.this, BoadingPointActivity.class);
                        SharedPreferences preference = getApplicationContext().getSharedPreferences("JourneyDetails", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preference.edit();
                        editor.putString("NumberOfSeats", numberOfSeats);
                        editor.putString("SelectedSeats", listString);
                        editor.putString("NetFare", NetTotalAmount);
                        editor.putString("ServiceTaxes", ServiceTaxes);
                        editor.putString("OperatorserviceCharge", OperatorserviceCharge);
                        editor.putString("Fares", Fares);
                        editor.putString("SeatCodes", SeatCodesString);
                        editor.putString("ConvienceFee", ConvienceFeeString);
                        editor.putString("TotalConvienceFee", TotalConvienceFeeString);
                        editor.apply();
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "No of seats should be same for onward and return journeys", Toast.LENGTH_LONG).show();
                    }
                } else {
                    SeatCodesString = TextUtils.join("~", Seatcodes);

                    listString = TextUtils.join("~", arList);
                    ServiceTaxes = TextUtils.join("~", ServiceTaxList);
                    OperatorserviceCharge = TextUtils.join("~", ServiceChargeList);
                    Fares = TextUtils.join("~", PriceArray);
                    ConvienceFeeString = TextUtils.join("~", ConvinenceFee);
                    System.out.println("SeatCodesString :" + SeatCodesString);
                    String numberOfSeats = "" + arList.size();
                    Intent i = new Intent(SelectSeatActivity.this, BoadingPointActivity.class);
                    SharedPreferences preference = getApplicationContext().getSharedPreferences("JourneyDetails", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preference.edit();
                    editor.putString("NumberOfSeats", numberOfSeats);
                    editor.putString("SelectedSeats", listString);
                    editor.putString("NetFare", NetTotalAmount);
                    editor.putString("ServiceTaxes", ServiceTaxes);
                    editor.putString("OperatorserviceCharge", OperatorserviceCharge);
                    editor.putString("Fares", Fares);
                    editor.putString("SeatCodes", SeatCodesString);
                    editor.putString("ConvienceFee", ConvienceFeeString);
                    editor.putString("TotalConvienceFee", TotalConvienceFeeString);
                    editor.apply();
                    startActivity(i);
                }

            }
        });

        String cancelArray[] = cancellationPolicy.split(";");
        System.out.println("split" + cancelArray[0]);
        if (!cancelArray[0].equals("")) {
            for (String aCancelArray : cancelArray) {
                String fromCancelArray[] = aCancelArray.split(":");
                if (fromCancelArray.length > 1) {

                    String cancelTimeStr;
                    if (fromCancelArray[1].equals("-1")) {
                        cancelTimeStr = (fromCancelArray[0] + "   hours before journey time              ");
                    } else {
                        cancelTimeStr = (fromCancelArray[1] + "   hours before journey time              ");
                    }

                    String percentSybole = "%";
                    String percentStr = (fromCancelArray[2] + ".0 " + percentSybole);


                    polcy = cancelTimeStr + percentStr;


                    //   cancelDaytextArray addObject:cancelTimeStr;
                    //  percentageArray addObject:percentStr;
                    System.out.println("data" + cancelTimeStr + percentStr);
                }

                cancalpolicy = "" + cancalpolicy + "" + polcy + "\n";
            }
        } else {
            cancalpolicy = "No Cancellation policy found";
        }
        System.out.println("data*****" + scripts);

        if (PartialCancellationAllowed.equalsIgnoreCase("true")) {
            cancalpolicy = "" + cancalpolicy + "\n *Partial cancellation is allowed";
        } else {
            cancalpolicy = "" + cancalpolicy + "\n *Partial cancellation is not allowed";
        }

        isConnected();
    }


    public void getLoginPreefernces() {
        String role=login_utils.getUserDetails(Constants.USERTYPE);
        switch (role) {
            case "6":
                User_Role = "6";
                break;
            case "4":
                User_Role = "4";
                break;
            default:
                User_Role = "5";
                break;
        }
    }

    //////////Service Calling///////
    public class Availablebuses extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {


            mProgressDialog = ProgressDialog.show(SelectSeatActivity.this, "","Loading Bus Seat(s)...");
            mProgressDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {
            String url;

            if (IsReturnYes.equals("Yes")) {
                url = Constants.BASEURL + Constants.TripDetails + "?tripId=" + BusTypeID + "&sourceId=" + Arrival_id + "&destinationId=" + Destination_id + "&journeyDate=" + Journey_Date + "&tripType=2&provider=" + Provider + "&travelOperator=" + Operator + "&userType=" + UserId + "&user=" + User_agentid + "&returnDate=" + RETURNDate + "&obiboProvider=" + ObiboAPIProvidrer;
                SharedPreferences preference = getApplicationContext().getSharedPreferences("JourneyDetails", MODE_PRIVATE);
                SharedPreferences.Editor editor = preference.edit();
                editor.putString("PassengerIsReturnselected", "ReturnSelected");
                editor.apply();
            } else {
                url = Constants.BASEURL + Constants.TripDetails + "?tripId=" + BusTypeID + "&sourceId=" + Arrival_id + "&destinationId=" + Destination_id + "&journeyDate=" + Journey_Date + "&tripType=1&provider=" + Provider + "&travelOperator=" + Operator + "&userType=" + UserId + "&user=" + User_agentid + "&returnDate=&obiboProvider=" + ObiboAPIProvidrer;

            }

            System.out.println("url" + url);
            ServiceHandler sh = new ServiceHandler();
            String jsonstr = sh.makeServiceCall(url, ServiceHandler.GET);
            if (jsonstr != null) {

                //    postArrayList.clear();
                try {


                    JSONObject jsonObject = new JSONObject(jsonstr);

                    String tripid = jsonObject.getString("TripId");
                    results = jsonObject.getJSONArray("Seats");

                    System.out.println("Seats   " + results);


                } catch (JSONException e) {
                    e.printStackTrace();

                }


            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                ZindexString = "0";
                mProgressDialog.dismiss();
                if (results != null && results.length() != 0) {

                    LoadingLyt.setVisibility(View.GONE);
                    ViewLyt.setVisibility(View.VISIBLE);

                    seatCreation();
                } else {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SelectSeatActivity.this);

                    // set title
                    //     alertDialogBuilder.setTitle("No Bus Operators found!");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Error: Could not fetch trip details...")
                            .setCancelable(false)
                            .setPositiveButton("Search Again", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();

                                    finish();

                                }
                            });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    ///***** Seat Layout Dynamically creating *****////
    public void seatCreation() throws JSONException {
        boolean isavailableseat = true;
        boolean isStartingfromcoloumzero = false;
        try {


            ///*** Row Wise Sorting In Array list ***????
            List<String> jsonValues = new ArrayList<String>();
            for (int i = 0; i < results.length(); i++) {
                JSONObject seatObject = results.getJSONObject(i);
                jsonValues.add(seatObject.getString("Row"));

            }
            Collections.sort(jsonValues);
            JSONArray sortedJsonArray = new JSONArray(jsonValues);
            System.out.println("RowArray" + sortedJsonArray);

            ///*** Column Wise Sorting In Array list ***????
            List<String> jsonValueswidth = new ArrayList<String>();
            for (int i = 0; i < results.length(); i++) {
                JSONObject seatObject = results.getJSONObject(i);
                if (seatObject.getString("Column").equals("0")) {
                    isStartingfromcoloumzero = true;
                }
                jsonValues.add(seatObject.getString("Column"));

            }
            Collections.sort(jsonValues);
            JSONArray sortedJsonArraywidth = new JSONArray(jsonValues);
            System.out.println("ColoumnArray" + sortedJsonArraywidth);


            for (int i = 0; i < results.length(); i++) {
                JSONObject seatObject = results.getJSONObject(i);

                String Zindex = seatObject.getString("Zindex");

                if (Zindex.equals("1")) {

                    ZindexString = "1";
                } else {
                    ZindexString = "0";
                }

                if (seatObject.getString("Zindex").equals(ZindexString)) {
                    int xPosition, yPosition;

                    yPosition = Integer.parseInt(seatObject.getString("Column"));
                    xPosition = Integer.parseInt(seatObject.getString("Row"));

                    if (isStartingfromcoloumzero == true) {
                        if (xPosition == 0) {
                            yPosition = yPosition + 1;
                            xPosition = 1;
                        } else {
                            yPosition = yPosition + 1;
                            xPosition = xPosition + 1;
                        }

                    }

                    final String number = seatObject.getString("Number");
                    final String NetFare = seatObject.getString("Fare");
                    String fare = seatObject.getString("NetFare");
                    final Boolean IsAvailableSeat = Boolean.valueOf(seatObject.getString("IsAvailableSeat"));
                    final Boolean IsLadiesSeat = Boolean.valueOf(seatObject.getString("IsLadiesSeat"));
                    final int Length = Integer.parseInt(seatObject.getString("Length"));
                    final int Width = Integer.parseInt(seatObject.getString("Width"));
                    final String Servicetax = seatObject.getString("Servicetax");
                    final String OperatorServiceCharge = seatObject.getString("OperatorServiceCharge");
                    final String SeatCode = seatObject.getString("SeatCode");
                    System.out.println("Device width :" + Screenwidth + " Device Height :" + Screenheight);

                    /////*****Device Width wise Button Sizes *****
                    /*if ((String.valueOf(Screenwidth).equals("240"))) {
                        System.out.println("Device width 240:" + Screenwidth + " Device Height :" + Screenheight);
                        ///Sleeper 1+1
                        if (Width == 2) {
                            x = Screenwidth - xPosition * 100;
                            // x = x - 230;
                            y = yPosition * 170 - 10;
                            SeatWidth = 100 * Width;
                            SeatHeaight = 70 * Length;
                        } else {
                            x = Screenwidth - xPosition * 60;
                            //  x = x - 230;
                            y = yPosition * 100 - 60;
                            SeatWidth = 70 * Width;
                            SeatHeaight = 70 * Length;
                        }
                    } else if ((String.valueOf(Screenwidth).equals("320"))) {
                        System.out.println("Device width 320:" + Screenwidth + " Device Height :" + Screenheight);
                        if (Width == 2) {
                            x = Screenwidth - xPosition * 130;
                            // x = x - 230;
                            y = yPosition * 170 - 10;
                            SeatWidth = 140 * Width;
                            SeatHeaight = 70 * Length;
                        } else {
                            x = Screenwidth - xPosition * 70;
                            //  x = x - 230;
                            y = yPosition * 100 - 60;
                            SeatWidth = 70 * Width;
                            SeatHeaight = 70 * Length;
                        }
                    } else if ((String.valueOf(Screenwidth).equals("360"))) {
                        System.out.println("Device width 360 :" + Screenwidth + " Device Height :" + Screenheight);
                        if (Width == 2) {
                            x = Screenwidth - xPosition * 150;
                            // x = x - 230;
                            y = yPosition * 170 - 10;
                            SeatWidth = 140 * Width;
                            SeatHeaight = 70 * Length;
                        } else {
                            x = Screenwidth - xPosition * 70;
                            //  x = x - 230;
                            y = yPosition * 100 - 60;
                            SeatWidth = 70 * Width;
                            SeatHeaight = 70 * Length;
                        }
                    } else if ((String.valueOf(Screenwidth).equals("480"))) {
                        System.out.println("Device width 480 :" + Screenwidth + " Device Height :" + Screenheight);
                        if (Width == 2) {
                            x = Screenwidth - xPosition * 200;
                            // x = x - 230;
                            y = yPosition * 170 - 10;
                            SeatWidth = 140 * Width;
                            SeatHeaight = 70 * Length;
                        } else {
                            x = Screenwidth - xPosition * 80;
                            //  x = x - 230;
                            y = yPosition * 100 - 60;
                            SeatWidth = 70 * Width;
                            SeatHeaight = 70 * Length;
                        }
                    } else if ((String.valueOf(Screenwidth).equals("540"))) {
                        System.out.println("Device width 540 :" + Screenwidth + " Device Height :" + Screenheight);
                        if (Width == 2) {
                            x = Screenwidth - xPosition * 300;
                            // x = x - 230;
                            y = yPosition * 170 - 10;
                            SeatWidth = 140 * Width;
                            SeatHeaight = 70 * Length;
                        } else {
                            x = Screenwidth - xPosition * 90;
                            //  x = x - 230;
                            y = yPosition * 100 - 60;
                            SeatWidth = 70 * Width;
                            SeatHeaight = 70 * Length;
                        }
                    } else if ((String.valueOf(Screenwidth).equals("720"))) {
                        System.out.println("Device width 720 :" + Screenwidth + " Device Height :" + Screenheight);
                        if (Width == 2) {
                            x = Screenwidth - xPosition * 360;
                            // x = x - 230;
                            y = yPosition * 170 - 10;
                            SeatWidth = 140 * Width;
                            SeatHeaight = 80 * Length;
                        } else {
                            x = Screenwidth - xPosition * 140;
                            //  x = x - 230;
                            y = yPosition * 100 - 60;
                            SeatWidth = 70 * Width;
                            SeatHeaight = 80 * Length;
                        }
                    } else if ((String.valueOf(Screenwidth).equals("768"))) {
                        System.out.println("Device width 768 :" + Screenwidth + " Device Height :" + Screenheight);
                        if (Width == 2) {
                            x = Screenwidth - xPosition * 380;
                            // x = x - 230;
                            y = yPosition * 170 - 10;
                            SeatWidth = 140 * Width;
                            SeatHeaight = 70 * Length;
                        } else {
                            x = Screenwidth - xPosition * 120;
                            //  x = x - 230;
                            y = yPosition * 100 - 60;
                            SeatWidth = 70 * Width;
                            SeatHeaight = 70 * Length;
                        }
                    } else if ((String.valueOf(Screenwidth).equals("800"))) {
                        System.out.println("Device width 800 :" + Screenwidth + " Device Height :" + Screenheight);
                        if (Width == 2) {
                            x = Screenwidth - xPosition * 400;
                            // x = x - 230;
                            y = yPosition * 170 - 10;
                            SeatWidth = 140 * Width;
                            SeatHeaight = 70 * Length;
                        } else {
                            x = Screenwidth - xPosition * 130;
                            //  x = x - 230;
                            y = yPosition * 100 - 60;
                            SeatWidth = 70 * Width;
                            SeatHeaight = 70 * Length;
                        }
                    } else if ((String.valueOf(Screenwidth).equals("1080"))) {
                        System.out.println("Device width 1080 :" + Screenwidth + " Device Height :" + Screenheight);

                        if (Length == 1 && Width == 2) {
                            x = Screenwidth - xPosition * 415;
                            // x = x - 230;
                            y = yPosition * 170 - 10;
                            SeatWidth = 200 * Width;
                            SeatHeaight = 140 * Length;
                        } else if (Length == 2 && Width == 1) {
                            x = Screenwidth - xPosition * 215;
                            // x = x - 230;
                            y = yPosition * 170 - 10;
                            SeatWidth = 200 * Width;
                            SeatHeaight = 140 * Length;
                        } else if (Length == 1 && Width == 1) {
                            x = Screenwidth - xPosition * 215;
                            // x = x - 230;
                            y = yPosition * 170 - 10;
                            SeatWidth = 120 * Width;
                            SeatHeaight = 140 * Length;
                        }

                    } else if ((String.valueOf(Screenwidth).equals("1440"))) {
                        System.out.println("Device width 1440 :" + Screenwidth + " Device Height :" + Screenheight);
                        if (Width == 2) {
                            x = Screenwidth - xPosition * 500;
                            // x = x - 230;
                            y = yPosition * 170 - 10;
                            SeatWidth = 200 * Width;
                            SeatHeaight = 140 * Length;
                        } else {
                            x = Screenwidth - xPosition * 240;
                            //  x = x - 230;
                            y = yPosition * 160 - 10;

                            SeatWidth = 120 * Width;
                            SeatHeaight = 140 * Length;
                        }
                    } else if ((String.valueOf(Screenwidth).equals("2048"))) {
                        System.out.println("Device width 2048 :" + Screenwidth + " Device Height :" + Screenheight);
                        if (Width == 2) {
                            x = Screenwidth - xPosition * 800;
                            // x = x - 230;
                            y = yPosition * 170 - 10;
                            SeatWidth = 250 * Width;
                            SeatHeaight = 140 * Length;
                        } else {
                            x = Screenwidth - xPosition * 240;
                            //  x = x - 230;
                            y = yPosition * 160 - 10;

                            SeatWidth = 120 * Width;
                            SeatHeaight = 210 * Length;
                        }
                    } else if ((String.valueOf(Screenwidth).equals("2560"))) {
                        System.out.println("Device width 2560 :" + Screenwidth + " Device Height :" + Screenheight);
                        if (Width == 2) {
                            x = Screenwidth - xPosition * 1000;
                            // x = x - 230;
                            y = yPosition * 170 - 10;
                            SeatWidth = 200 * Width;
                            SeatHeaight = 140 * Length;
                        } else {
                            x = Screenwidth - xPosition * 240;
                            //  x = x - 230;
                            y = yPosition * 160 - 10;

                            SeatWidth = 120 * Width;
                            SeatHeaight = 140 * Length;
                        }
                    } else {
                        if (Width == 2) {
                            x = Screenwidth - xPosition * 400;
                            // x = x - 230;
                            y = yPosition * 170 - 10;
                            SeatWidth = 140 * Width;
                            SeatHeaight = 70 * Length;
                        } else {
                            x = Screenwidth - xPosition * 130;
                            //  x = x - 230;
                            y = yPosition * 100 - 60;
                            SeatWidth = 70 * Width;
                            SeatHeaight = 70 * Length;
                        }*/
                   /* x = Screenwidth - xPosition * 90;
                    //  x = x - 230;
                    y = yPosition * 100 - 60;
                    SeatWidth= 70 * Width;
                    SeatHeaight=70 * Length;*/




                    if ((String.valueOf(Screenwidth).equals("540"))||Screenwidth<540) {
                        System.out.println("Device width 540 :" + Screenwidth + " Device Height :" + Screenheight);
                        if (Width == 2) {
                            x = Screenwidth - xPosition * 360;
                            // x = x - 230;
                            y = yPosition * 170 - 10;
                            SeatWidth = 140 * Width;
                            SeatHeaight = 80 * Length;
                        } else {
                       /* x = Screenwidth - xPosition * 140;
                        //  x = x - 230;
                        y = yPosition * 100 - 60;
                        SeatWidth = 70 * Width;
                        SeatHeaight = 80 * Length;*/

                            if (xPosition == 1) {

                                x = Screenwidth - xPosition * 250;
                                x = 320;
                                y = yPosition * 80-75;

                                SeatWidth = 55 * Width;
                                SeatHeaight = 60 * Length;
                            } else if (xPosition == 2) {

                                // x = Screenwidth - 330;
                                x = 260;
                                //  x = x - 230;
                                y = yPosition * 80-75;
                                SeatWidth = 55 * Width;
                                SeatHeaight = 60 * Length;
                            } else if (xPosition == 3) {

                                //x = Screenwidth - 410;
                                x = 200;
                                //  x = x - 230;
                                y = yPosition * 80-75;
                                SeatWidth = 55 * Width;
                                SeatHeaight = 60 * Length;
                            } else if (xPosition == 4) {

                                // x = Screenwidth - 490;
                                x = 140;
                                //  x = x - 230;
                                y = yPosition * 80-75;
                                SeatWidth = 55 * Width;
                                SeatHeaight = 60 * Length;
                            } else if (xPosition == 5) {

                                // x = Screenwidth - 570;
                                //  x = x - 230;
                                x = 80;
                                y = yPosition * 80-75;
                                SeatWidth = 55 * Width;
                                SeatHeaight = 60 * Length;

                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                        android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
                                        android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
                                );
                                params.setMargins(-70, 0, 0, 0);
                                scrollView.setLayoutParams(params);

                                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(60,60);
                                params1.setMargins(100, 0, 0, 0);
                                params1.topMargin=40;
                                params1.bottomMargin=10;
                                SteeringImageView.setLayoutParams(params1);


                            } else if (xPosition == 6) {    ///////3x2 seat layout

                          /* x = Screenwidth - 780;*/
                                x = 20;
                                //  x = x - 230;
                                y = yPosition * 80-75;
                                SeatWidth = 55 * Width;
                                SeatHeaight = 60 * Length;

                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                        android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
                                        android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
                                );
                                params.setMargins(0, 0, 0, 0);
                                scrollView.setLayoutParams(params);

                                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(60,60);
                                params1.setMargins(40, 0, 0, 0);
                                params1.topMargin=40;
                                params1.bottomMargin=10;
                                SteeringImageView.setLayoutParams(params1);

                            }
                        }
                    } else if ((String.valueOf(Screenwidth).equals("720"))) {
                        System.out.println("Device width 720 :" + Screenwidth + " Device Height :" + Screenheight);
                        if (Width == 2) {
                            x = Screenwidth - xPosition * 360;
                            // x = x - 230;
                            y = yPosition * 170 - 10;
                            SeatWidth = 140 * Width;
                            SeatHeaight = 80 * Length;
                        } else {
                       /* x = Screenwidth - xPosition * 140;
                        //  x = x - 230;
                        y = yPosition * 100 - 60;
                        SeatWidth = 70 * Width;
                        SeatHeaight = 80 * Length;*/

                            if (xPosition == 1) {

                                x = Screenwidth - xPosition * 250;
                                x = 420;
                                y = yPosition * 100 - 80;

                                SeatWidth = 60 * Width;
                                SeatHeaight = 70 * Length;
                            } else if (xPosition == 2) {

                                x = Screenwidth - 330;
                                x = 340;
                                //  x = x - 230;
                                y = yPosition * 100 - 80;
                                SeatWidth = 60 * Width;
                                SeatHeaight = 70 * Length;
                            } else if (xPosition == 3) {

                                x = Screenwidth - 410;
                                x = 260;
                                //  x = x - 230;
                                y = yPosition * 100 - 80;
                                SeatWidth = 60 * Width;
                                SeatHeaight = 70 * Length;
                            } else if (xPosition == 4) {

                                x = Screenwidth - 490;
                                x = 180;
                                //  x = x - 230;
                                y = yPosition * 100 - 80;
                                SeatWidth = 60 * Width;
                                SeatHeaight = 70 * Length;
                            } else if (xPosition == 5) {

                                x = Screenwidth - 570;
                                //  x = x - 230;
                                x = 100;
                                y = yPosition * 100 - 80;
                                SeatWidth = 60 * Width;
                                SeatHeaight = 70 * Length;

                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                        android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
                                        android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
                                );
                                params.setMargins(-70, 0, 0, 0);
                                scrollView.setLayoutParams(params);


                            } else if (xPosition == 6) {    ///////3x2 seat layout

                          /* x = Screenwidth - 780;*/
                                x = 20;
                                //  x = x - 230;
                                y = yPosition * 100 - 80;
                                SeatWidth = 60 * Width;
                                SeatHeaight = 70 * Length;

                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                        android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
                                        android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
                                );
                                params.setMargins(0, 0, 0, 0);
                                scrollView.setLayoutParams(params);

                                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(60, 60);
                                params1.setMargins(20, 0, 0, 0);
                                params1.topMargin = 40;
                                params1.bottomMargin = 10;
                                SteeringImageView.setLayoutParams(params1);

                            }
                        }
                    }else if ((String.valueOf(Screenwidth).equals("1080"))) {
                        System.out.println("Device width 1080 :" + Screenwidth + " Device Height :" + Screenheight);

                    /*if (Length == 1 && Width == 2) {
                        x = Screenwidth - xPosition * 415;
                        // x = x - 230;
                        y = yPosition * 170 - 10;
                        SeatWidth = 200 * Width;
                        SeatHeaight = 140 * Length;
                    } else*/ if (Length == 2) {
                            // x = x - 230;
                            y = yPosition * 170 - 10;
                            SeatWidth = 120 * Width;
                            SeatHeaight = 140 * Length;


                            if (xPosition == 1) {

                                x = 620;
                                y = yPosition * 150 - 10;

                                SeatWidth = 100 * Width;
                                SeatHeaight =120 * Length;
                            } else if (xPosition == 2) {

                                //x = Screenwidth - 330;
                                x = 500;
                                //  x = x - 230;
                                y = yPosition * 150 - 10;
                                SeatWidth = 100 * Width;
                                SeatHeaight = 120 * Length;
                            } else if (xPosition == 3) {

                                //x = Screenwidth - 410;
                                x = 380;
                                //  x = x - 230;
                                y = yPosition * 150 - 10;
                                SeatWidth = 100 * Width;
                                SeatHeaight = 120 * Length;
                            } else if (xPosition == 4) {


                                x = 260;
                                //  x = x - 230;
                                y = yPosition * 150 - 10;
                                SeatWidth = 100 * Width;
                                SeatHeaight =120 * Length;
                            } else if (xPosition == 5) {


                                //  x = x - 230;
                                x = 140;
                                y = yPosition * 150 - 10;
                                SeatWidth = 100 * Width;
                                SeatHeaight = 120 * Length;

                                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(120, 120);
                                params1.setMargins(150, 0, 0, 0);
                                params1.topMargin = 40;
                                SteeringImageView.setLayoutParams(params1);


                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                        android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
                                        android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
                                );
                                params.setMargins(-110, 0, 0, 0);
                                scrollView.setLayoutParams(params);


                            } else if (xPosition == 6) {    ///////3x2 seat layout


                                x = 20;
                                //  x = x - 230;
                                y = yPosition * 150 - 10;
                                SeatWidth = 100 * Width;
                                SeatHeaight = 120 * Length;

                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                        android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
                                        android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
                                );
                                params.setMargins(0, 0, 0, 0);
                                scrollView.setLayoutParams(params);

                                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(120, 120);
                                params1.setMargins(20, 0, 0, 0);
                                params1.topMargin = 40;
                                SteeringImageView.setLayoutParams(params1);

                            }
                        } else
                        if (Length == 1 && Width == 1) {

                            // x = x - 230;
                            y = yPosition * 170 - 10;
                            SeatWidth = 150 * Width;
                            SeatHeaight = 160 * Length;


                            if (xPosition == 1) {

                                x = 620;
                                y = yPosition * 150 - 10;

                                SeatWidth = 100 * Width;
                                SeatHeaight =120 * Length;
                            } else if (xPosition == 2) {

                                //x = Screenwidth - 330;
                                x = 500;
                                //  x = x - 230;
                                y = yPosition * 150 - 10;
                                SeatWidth = 100 * Width;
                                SeatHeaight = 120 * Length;
                            } else if (xPosition == 3) {

                                //x = Screenwidth - 410;
                                x = 380;
                                //  x = x - 230;
                                y = yPosition * 150 - 10;
                                SeatWidth = 100 * Width;
                                SeatHeaight = 120 * Length;
                            } else if (xPosition == 4) {


                                x = 260;
                                //  x = x - 230;
                                y = yPosition * 150 - 10;
                                SeatWidth = 100 * Width;
                                SeatHeaight =120 * Length;
                            } else if (xPosition == 5) {


                                //  x = x - 230;
                                x = 140;
                                y = yPosition * 150 - 10;
                                SeatWidth = 100 * Width;
                                SeatHeaight = 120 * Length;

                                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(120, 120);
                                params1.setMargins(150, 0, 0, 0);
                                params1.topMargin = 40;
                                SteeringImageView.setLayoutParams(params1);


                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                        android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
                                        android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
                                );
                                params.setMargins(-110, 0, 0, 0);
                                scrollView.setLayoutParams(params);


                            } else if (xPosition == 6) {    ///////3x2 seat layout


                                x = 20;
                                //  x = x - 230;
                                y = yPosition * 150 - 10;
                                SeatWidth = 100 * Width;
                                SeatHeaight = 120 * Length;

                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                        android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
                                        android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
                                );
                                params.setMargins(0, 0, 0, 0);
                                scrollView.setLayoutParams(params);

                                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(120, 120);
                                params1.setMargins(20, 0, 0, 0);
                                params1.topMargin = 40;
                                SteeringImageView.setLayoutParams(params1);

                            }
                        }

                    }else{


                        ///for other screen sizes

                        if (Width == 2) {
                            x = Screenwidth - xPosition * 360;
                            // x = x - 230;
                            y = yPosition * 170 - 10;
                            SeatWidth = 140 * Width;
                            SeatHeaight = 80 * Length;
                        } else {
                       /* x = Screenwidth - xPosition * 140;
                        //  x = x - 230;
                        y = yPosition * 100 - 60;
                        SeatWidth = 70 * Width;
                        SeatHeaight = 80 * Length;*/

                            if (xPosition == 1) {

                                x = Screenwidth - xPosition * 250;
                                x = 420;
                                y = yPosition * 100 - 80;

                                SeatWidth = 60 * Width;
                                SeatHeaight = 70 * Length;
                            } else if (xPosition == 2) {

                                x = Screenwidth - 330;
                                x = 340;
                                //  x = x - 230;
                                y = yPosition * 100 - 80;
                                SeatWidth = 60 * Width;
                                SeatHeaight = 70 * Length;
                            } else if (xPosition == 3) {

                                x = Screenwidth - 410;
                                x = 260;
                                //  x = x - 230;
                                y = yPosition * 100 - 80;
                                SeatWidth = 60 * Width;
                                SeatHeaight = 70 * Length;
                            } else if (xPosition == 4) {

                                x = Screenwidth - 490;
                                x = 180;
                                //  x = x - 230;
                                y = yPosition * 100 - 80;
                                SeatWidth = 60 * Width;
                                SeatHeaight = 70 * Length;
                            } else if (xPosition == 5) {

                                x = Screenwidth - 570;
                                //  x = x - 230;
                                x = 100;
                                y = yPosition * 100 - 80;
                                SeatWidth = 60 * Width;
                                SeatHeaight = 70 * Length;

                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                        android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
                                        android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
                                );
                                params.setMargins(-70, 0, 0, 0);
                                scrollView.setLayoutParams(params);


                            } else if (xPosition == 6) {    ///////3x2 seat layout

                          /* x = Screenwidth - 780;*/
                                x = 20;
                                //  x = x - 230;
                                y = yPosition * 100 - 80;
                                SeatWidth = 60 * Width;
                                SeatHeaight = 70 * Length;

                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                        android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
                                        android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
                                );
                                params.setMargins(0, 0, 0, 0);
                                scrollView.setLayoutParams(params);

                                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(60, 60);
                                params1.setMargins(20, 0, 0, 0);
                                params1.topMargin = 40;
                                params1.bottomMargin = 10;
                                SteeringImageView.setLayoutParams(params1);

                            }
                        }

                    }




                    ///*** Dynamically Seat Button creation
                    if (ZindexString.equals("0")) {
                        RelativeSeatLayout = (RelativeLayout) findViewById(R.id.setlayout);
                        myButton = new Button(this);
                        //myButton.setTag(number);
                        System.out.println("*****" + number);
                    } else {
                        LinearLayout.setVisibility(View.VISIBLE);
                        RelativeSeatLayoutUpper = (RelativeLayout) findViewById(R.id.Uppersetlayout);
                        myButton = new Button(this);
                        //myButton.setTag(number);
                        System.out.println("*****" + number);
                        RelativeSeatLayoutUpper.setVisibility(View.GONE);
                    }

                    if (Length == 2) {
                        LinearLayout.setVisibility(View.VISIBLE);
                        RelativeSeatLayoutUpper.setVisibility(View.GONE);
                        mSeat_icons_image.setVisibility(View.GONE);
                        mSeat_icons_image_sleeper.setVisibility(View.VISIBLE);


                        if (!IsAvailableSeat) {

                            if (IsLadiesSeat) {
                                myButton.setBackgroundResource(R.drawable.notavailableleadiessleeperseat);
                                myButton.setClickable(false);
                            } else {
                                myButton.setBackgroundResource(R.drawable.seat_icon_sleeper_notavailable);
                                myButton.setClickable(false);
                            }

                        } else {
                            if (IsLadiesSeat)
                                myButton.setBackgroundResource(R.drawable.seat_icon_sleeper_ladies);
                            else
                                myButton.setBackgroundResource(R.drawable.seat_icon_available_sleeper);

                        }
                        if (arList.contains(String.valueOf(number))) {
                            myButton.setBackgroundResource(R.drawable.seat_icon_sleeper_selected);

                        }

                    } else if (Width == 2) {
                        LinearLayout.setVisibility(View.VISIBLE);
                        mSeat_icons_image.setVisibility(View.GONE);
                        mSeat_icons_image_sleeper.setVisibility(View.VISIBLE);
                        if (!IsAvailableSeat) {

                            if (IsLadiesSeat) {
                                myButton.setBackgroundResource(R.drawable.seat_icon_sleeper_ladies);
                                myButton.setClickable(false);
                            } else {
                                myButton.setBackgroundResource(R.drawable.notavailableleadiessleeperseat);
                                myButton.setClickable(false);
                            }

                        } else {
                            if (IsLadiesSeat)
                                myButton.setBackgroundResource(R.drawable.seat_icon_sleeper_ladies);
                            else
                                myButton.setBackgroundResource(R.drawable.seat_icon_available_sleeper);

                        }
                        if (arList.contains(String.valueOf(number))) {
                            myButton.setBackgroundResource(R.drawable.seat_icon_sleeper_selected);

                        }
                    } else if (!IsAvailableSeat) {

                        if (IsLadiesSeat) {
                            myButton.setBackgroundResource(R.drawable.notavailableladiesseat);
                            myButton.setClickable(false);
                        } else
                            myButton.setBackgroundResource(R.drawable.notavailable_seat_icon);
                        myButton.setClickable(false);


                    } else {
                        if (IsLadiesSeat)
                            myButton.setBackgroundResource(R.drawable.ladies_seat_available_icon);
                        else
                            myButton.setBackgroundResource(R.drawable.availableseat);

                    }
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(50, 50);
                    lp.addRule(RelativeLayout.ALIGN_RIGHT, 0);

                    lp.width = SeatWidth;
                    lp.height = SeatHeaight;
                    lp.leftMargin = x;
                    lp.topMargin = y;


                    myButton.setLayoutParams(lp);
                    if (ZindexString.equals("0")) {
                        RelativeSeatLayout.addView(myButton, lp);
                    } else {
                        RelativeSeatLayoutUpper.addView(myButton, lp);
                    }

                    myButton.setTextColor(getResources().getColor(R.color.colorWhite));
                    myButton.setTextSize(10);
                    myButton.setText(number);
                    System.out.println("seat" + "" + x + "" + y + "number" + number);

                    myButton.setOnClickListener(new View.OnClickListener() {

                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub


                            Double d = Double.parseDouble(NetFare);
                            int roundValue = RoundOff(d);
                            System.out.println("Math.round(" + d + ")=" + Math.round(d) + roundValue);


                            String seatno = String.valueOf(number);
                            String price = String.valueOf(roundValue);
                            String serviceTax = String.valueOf(Servicetax);
                            String serviceCharge = String.valueOf(OperatorServiceCharge);
                            int NetFareString = 0;
                            double NETFARESHOWING = 0;
                            String SeatcodeString = "";
                            String NumberOfSeats = "";
                            String STax = "";
                            System.out.println("seats***" + seatno);

                            if (arList.size() + 1 != 0) {
                                if (arList.contains(seatno)) {
                                   noofSeats = arList.size() - 1;

                                } else {
                                  noofSeats = arList.size() + 1;

                                }

                            }



                            if (!IsAvailableSeat) {
                                myButton.setClickable(false);
                            } else if (arList.contains(seatno)) {
                                Seatcodes.remove(SeatCode);
                                arList.remove(seatno);
                                netfare.remove(price);
                                PriceArray.remove(price);
                                ServiceTaxList.remove(serviceTax);
                                ServiceChargeList.remove(serviceCharge);
                                ConvinenceFee.remove(ConvenienceFee);


                                Net_fares.setText(" ₹ " + NetFareString + "");
                                Seats.setText("" + NumberOfSeats);
                                if (Length == 2) {
                                    if (IsLadiesSeat) {
                                        v.setBackgroundResource(R.drawable.seat_icon_sleeper_ladies);
                                    } else {
                                        v.setBackgroundResource(R.drawable.seat_icon_available_sleeper);
                                    }
                                } else if (Width == 2) {
                                    if (IsLadiesSeat) {
                                        v.setBackgroundResource(R.drawable.availableladiessleeperonepluesone);
                                    } else {
                                        v.setBackgroundResource(R.drawable.availablesleeperonepluesone);
                                    }
                                } else {
                                    if (IsLadiesSeat) {
                                        v.setBackgroundResource(R.drawable.ladies_seat_available_icon);
                                    } else {
                                        v.setBackgroundResource(R.drawable.availableseat);
                                    }

                                }
                                if (arList.size() == 0) {
                                    RelativeLayout.setVisibility(View.GONE);
                                    //LinearAminites_Layout.setVisibility(View.VISIBLE);
                                }
                                for (int i = 0; i < arList.size(); i++) {
                                    String s = arList.get(i);
                                    if (i == arList.size() - 1) {

                                        NumberOfSeats += s;
                                    } else {
                                        NumberOfSeats += s + ",";
                                    }

                                }
                                Seats.setText("" + NumberOfSeats);


                                for (int i = 0; i < netfare.size(); i++) {
                                    int s = Integer.parseInt(netfare.get(i));

                                    NetFareString += s;

                                }


                                ////******dialog box details *****//////
                                //  ServiceTaxList.addAll(Arrays.<String>asList(String.valueOf(Servicetax)));
                                double servicetax = 0;
                                for (int i = 0; i < ServiceTaxList.size(); i++) {
                                    int s = RoundOff(Double.parseDouble(ServiceTaxList.get(i)));

                                    servicetax += s;

                                }
                                //   ServiceChargeList.addAll(Arrays.<String>asList(String.valueOf(OperatorServiceCharge)));
                                double ServiceCharge = 0;
                                for (int i = 0; i < ServiceChargeList.size(); i++) {
                                    int s = RoundOff(Double.parseDouble(ServiceChargeList.get(i)));

                                    ServiceCharge += s;

                                }

                                ////**** Convience Fee adding to Convience fee Array ***//
                                //   ConvinenceFee.addAll(Arrays.<String>asList(String.valueOf(ConvenienceFee)));
                                double Convfee = 0;
                                for (int i = 0; i < ConvinenceFee.size(); i++) {

                                    float s = Float.parseFloat(((ConvinenceFee.get(i))));

                                    Convfee += s;

                                }

                                dialogboxseats = NumberOfSeats;
                                dialogboxamount = NetFareString;
                                dialogboxservicetax = servicetax;
                                dialogboxservicecharge = ServiceCharge;


                                double ConFee = 0;
                                if (!ConvenienceFee.equals("0")) {
                                    if(!ConvenienceFeeType.equals("0")) {

                                        Double ConveniFee = Double.parseDouble(ConvenienceFee);
                                        ConFee = RoundOff(ConveniFee);
                                        ConFee = noofSeats * ConFee;
//                                        if (arList.size() + 1 != 0) {
//                                            if (arList.contains(seatno)) {
//                                                int noofSeats = arList.size() - 1;
//                                                ConFee = noofSeats * ConFee;
//                                            } else {
//                                                int noofSeats = arList.size() + 1;
//                                                ConFee = noofSeats * ConFee;
//                                            }
//
//                                        }
                                    }else{
                                        Double ConveniFee = Double.parseDouble(ConvenienceFee);
                                        Double ConvFeePerVal=ConveniFee/100;

                                        double Net=dialogboxamount + dialogboxservicetax + dialogboxservicecharge;
                                        ConFee=ConvFeePerVal*Net;
                                    }

                                }


                                TotalConvienceFeeString = String.valueOf(ConFee);
                                NETFARESHOWING = dialogboxamount + ConFee + dialogboxservicetax + dialogboxservicecharge;
                                NetTotalAmount = String.valueOf(NETFARESHOWING);
                                int nFare=RoundOff(NETFARESHOWING);
                                Net_fares.setText("₹ " + nFare + " ");

                            } else {
                                if (arList.size() < 6) {
                                    ///***Return bus selected method
                                    if (ReturnNumberOfSeats != null) {
                                        int ReturnSeats = Integer.parseInt(ReturnNumberOfSeats);
                                        if (arList.size() < ReturnSeats) {
                                            RelativeLayout.setVisibility(View.VISIBLE);
                                            if (Length == 2) {
                                                if (IsLadiesSeat) {
                                                    v.setBackgroundResource(R.drawable.seat_icon_sleeper_selected);
                                                } else {
                                                    v.setBackgroundResource(R.drawable.seat_icon_sleeper_selected);
                                                }
                                            } else if (Width == 2) {
                                                if (IsLadiesSeat) {
                                                    v.setBackgroundResource(R.drawable.selectedleladiessleeperonepluesone);
                                                } else {
                                                    v.setBackgroundResource(R.drawable.selectedsleeperonepluesone);
                                                }
                                            } else {
                                                if (IsLadiesSeat) {
                                                    v.setBackgroundResource(R.drawable.selected_seat_icon);
                                                } else {
                                                    v.setBackgroundResource(R.drawable.selected_seat_icon);
                                                }
                                            }

                                            ////**** Return Bus Number of seats adding to arlist ***////
                                            arList.addAll(Arrays.<String>asList(String.valueOf(number)));
                                            for (int i = 0; i < arList.size(); i++) {
                                                String s = arList.get(i);
                                                if (i == arList.size() - 1) {

                                                    NumberOfSeats += s;
                                                } else {
                                                    NumberOfSeats += s + ",";
                                                }

                                            }
                                            Seats.setText("" + NumberOfSeats);

                                            /////***Return Bus Net Fare Round value adding to net fare aray ***/////
                                            netfare.addAll(Arrays.<String>asList(String.valueOf(roundValue)));
                                            for (int i = 0; i < netfare.size(); i++) {
                                                int s = Integer.parseInt(netfare.get(i));

                                                NetFareString += s;

                                            }

                                            ////**** Return bus Seat codes adding to Seatcode Array ***/////
                                            Seatcodes.addAll(Arrays.<String>asList(String.valueOf(SeatCode)));
                                            System.out.println("Seatcodes :" + Seatcodes);

                                            for (int i = 0; i < Seatcodes.size(); i++) {
                                                String s = (Seatcodes.get(i));

                                                SeatcodeString += s;

                                            }

                                            PriceArray.addAll(Arrays.<String>asList(String.valueOf(roundValue)));

                                            ////**** Return Bus Service Tax adding to serviceTaxList Array ***/////
                                            ServiceTaxList.addAll(Arrays.<String>asList(String.valueOf(Servicetax)));
                                            int servicetax = 0;
                                            for (int i = 0; i < ServiceTaxList.size(); i++) {
                                                float s = Float.parseFloat((ServiceTaxList.get(i)));

                                                servicetax += s;

                                            }

                                            ////****Return Bus Service Charge adding to serviceChargeList Array ***/////
                                            ServiceChargeList.addAll(Arrays.<String>asList(String.valueOf(OperatorServiceCharge)));
                                            double ServiceCharge = 0;
                                            for (int i = 0; i < ServiceChargeList.size(); i++) {

                                                float s = Float.parseFloat(((ServiceChargeList.get(i))));

                                                ServiceCharge += s;

                                            }

                                            ////**** Convience Fee adding to Convience fee Array ***//
                                            ConvinenceFee.addAll(Arrays.<String>asList(String.valueOf(ConvenienceFee)));
                                            double Convfee = 0;
                                            for (int i = 0; i < ConvinenceFee.size(); i++) {

                                                float s = Float.parseFloat(((ConvinenceFee.get(i))));

                                                Convfee += s;

                                            }

                                            dialogboxseats = NumberOfSeats;
                                            dialogboxamount = NetFareString;
                                            dialogboxservicetax = RoundOff(Double.parseDouble(String.valueOf(servicetax)));
                                            dialogboxservicecharge = RoundOff(Double.parseDouble(String.valueOf(ServiceCharge)));

                                            double ConFee = 0;
                                            if (!ConvenienceFee.equals("0")) {
                                                if(!ConvenienceFeeType.equals("0")) {

                                                    Double ConveniFee = Double.parseDouble(ConvenienceFee);
                                                    ConFee = RoundOff(ConveniFee);
                                                    ConFee = noofSeats * ConFee;
                                                   /* if (arList.size() + 1 != 0) {
                                                        if (arList.contains(seatno)) {
                                                            int noofSeats = arList.size() - 1;
                                                            ConFee = noofSeats * ConFee;
                                                        } else {
                                                            int noofSeats = arList.size() + 1;
                                                            ConFee = noofSeats * ConFee;
                                                        }

                                                    }*/
                                                }else{
                                                    Double ConveniFee = Double.parseDouble(ConvenienceFee);
                                                    Double ConvFeePerVal=ConveniFee/100;

                                                    double Net=dialogboxamount + dialogboxservicetax + dialogboxservicecharge;
                                                    ConFee=ConvFeePerVal*Net;
                                                }

                                            }
                                            TotalConvienceFeeString = String.valueOf(ConFee);


                                            NETFARESHOWING = dialogboxamount + ConFee + dialogboxservicetax + dialogboxservicecharge;
                                            NetTotalAmount = String.valueOf(NETFARESHOWING);
                                            int nFare=RoundOff(NETFARESHOWING);
                                            Net_fares.setText("₹ " + nFare + " ");
                                        } else {
                                            Toast.makeText(getApplicationContext(), "No of seats should be same for onward and return journey", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {

                                        RelativeLayout.setVisibility(View.VISIBLE);
                                        ///**** Seat images changing button on click ///
                                        if (Length == 2) {
                                            if (IsLadiesSeat) {
                                                v.setBackgroundResource(R.drawable.seat_icon_sleeper_selected);
                                            } else {
                                                v.setBackgroundResource(R.drawable.seat_icon_sleeper_selected);
                                            }
                                        } else if (Width == 2) {
                                            if (IsLadiesSeat) {
                                                v.setBackgroundResource(R.drawable.selectedsleeperonepluesone);
                                            } else {
                                                v.setBackgroundResource(R.drawable.selectedsleeperonepluesone);
                                            }
                                        } else {
                                            if (IsLadiesSeat) {
                                                v.setBackgroundResource(R.drawable.selected_seat_icon);
                                            } else {
                                                v.setBackgroundResource(R.drawable.selected_seat_icon);
                                            }
                                        }

                                        ////**** Number of seats adding to arlist ***////
                                        arList.addAll(Arrays.<String>asList(String.valueOf(number)));
                                        for (int i = 0; i < arList.size(); i++) {
                                            String s = arList.get(i);
                                            if (i == arList.size() - 1) {

                                                NumberOfSeats += s;
                                            } else {
                                                NumberOfSeats += s + ",";
                                            }

                                        }
                                        Seats.setText("" + NumberOfSeats);

                                        /////***Net Fare Round value adding to net fare aray ***/////
                                        netfare.addAll(Arrays.<String>asList(String.valueOf(roundValue)));
                                        for (int i = 0; i < netfare.size(); i++) {
                                            int s = Integer.parseInt(netfare.get(i));

                                            NetFareString += s;

                                        }

                                        PriceArray.addAll(Arrays.<String>asList(String.valueOf(roundValue)));

                                        ////**** Service Tax adding to serviceTaxList Array ***/////
                                        ServiceTaxList.addAll(Arrays.<String>asList(String.valueOf(Servicetax)));
                                        System.out.println("seats***" + ServiceTaxList);
                                        double servicetax = 0;
                                        for (int i = 0; i < ServiceTaxList.size(); i++) {
                                            float s = Float.parseFloat((ServiceTaxList.get(i)));

                                            servicetax += s;

                                        }

                                        ////**** Seat codes adding to Seatcode Array ***/////
                                        Seatcodes.addAll(Arrays.<String>asList(String.valueOf(SeatCode)));

                                        for (int i = 0; i < Seatcodes.size(); i++) {
                                            String s = (Seatcodes.get(i));

                                            SeatcodeString += s;

                                        }

                                        ////**** Service Charge adding to serviceChargeList Array ***/////
                                        ServiceChargeList.addAll(Arrays.<String>asList(String.valueOf(OperatorServiceCharge)));
                                        double ServiceCharge = 0;
                                        for (int i = 0; i < ServiceChargeList.size(); i++) {

                                            float s = Float.parseFloat(((ServiceChargeList.get(i))));

                                            ServiceCharge += s;

                                        }

                                        ////**** Convience Fee adding to Convience fee Array ***//
                                        ConvinenceFee.addAll(Arrays.<String>asList(String.valueOf(ConvenienceFee)));
                                        double Convfee = 0;
                                        for (int i = 0; i < ConvinenceFee.size(); i++) {

                                            float s = Float.parseFloat(((ConvinenceFee.get(i))));

                                            Convfee += s;

                                        }

                                        dialogboxseats = NumberOfSeats;
                                        dialogboxamount = NetFareString;
                                        dialogboxservicetax = RoundOff(Double.parseDouble(String.valueOf(servicetax)));
                                        dialogboxservicecharge = RoundOff(Double.parseDouble(String.valueOf(ServiceCharge)));

                                        double ConFee = 0;
                                        if (!ConvenienceFee.equals("0")) {
                                            if(!ConvenienceFeeType.equals("0")) {

                                                Double ConveniFee = Double.parseDouble(ConvenienceFee);
                                                ConFee = RoundOff(ConveniFee);
                                                ConFee = noofSeats * ConFee;
//                                                if (arList.size() + 1 != 0) {
//                                                    if (arList.contains(seatno)) {
//                                                        int noofSeats = arList.size() - 1;
//                                                        ConFee = noofSeats * ConFee;
//                                                    } else {
//                                                        int noofSeats = arList.size() + 1;
//                                                        ConFee = noofSeats * ConFee;
//                                                    }
//
//                                                }
                                            }else{
                                                Double ConveniFee = Double.parseDouble(ConvenienceFee);
                                                Double ConvFeePerVal=ConveniFee/100;

                                                double Net=dialogboxamount + dialogboxservicetax + dialogboxservicecharge;
                                                ConFee=ConvFeePerVal*Net;
                                            }

                                        }
                                        TotalConvienceFeeString = String.valueOf(ConFee);


                                        NETFARESHOWING = NetFareString + ConFee + dialogboxservicetax + dialogboxservicecharge;
                                        NetTotalAmount = String.valueOf(NETFARESHOWING);
                                        int nFare=RoundOff(NETFARESHOWING);
                                        Net_fares.setText("₹ " + nFare + " ");


                                    }
                                    //LinearAminites_Layout.setVisibility(View.INVISIBLE);

                                } else {
                                    Toast.makeText(getApplicationContext(), "Cant select more than 6 seats", Toast.LENGTH_SHORT).show();
                                }
                            }


                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public Integer RoundOff(double TotalRate) {
        double dAbs = Math.abs(TotalRate);
        int amt2 = (int) dAbs;
        double result = dAbs - (double) amt2;
        int finalrate;
        if (result < 0.5) {
            finalrate = (int) Math.floor(TotalRate);
        } else {
            finalrate = (int) Math.ceil(TotalRate);
        }

        return finalrate;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.cancel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.action_policy:

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

                // set title
                alertDialogBuilder.setTitle("Cancellation policy");

                // set dialog message
                alertDialogBuilder
                        .setMessage("" + cancalpolicy)
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity

                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Method to manually check connection status


    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener

    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */

    //  finish();
    public void isConnected(){
        if(Util.isNetworkAvailable(getApplicationContext())) {
            new Availablebuses().execute();
        }else{
            Util.showMessage(this,"Please Check Your Internet Connection");
        }
    }

}