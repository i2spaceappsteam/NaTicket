package com.NaTicket.n.buses;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.NaTicket.n.loginpackage.pojo.Login_utils;
import com.NaTicket.n.paymnet_gate_ways.Payment_Gateway_Main;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.TextDrawable;
import com.NaTicket.n.R;
import com.NaTicket.n.utils.Util;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;

import pl.droidsonroids.gif.GifTextView;


public class PassengerDetails extends AppCompatActivity implements View.OnClickListener {

    EditText contact_email, contact_mobile, passenger_fullname, passenger_age, address, Idnumber, Promocode;
    EditText Co_passenger1_Id_proof_number, Co_passenger2_Id_proof_number, Co_passenger3_Id_proof_number, Co_passenger4_Id_proof_number, Co_passenger5_Id_proof_number;
    EditText Co_passenger_fullname1, Co_passenger_fullname2, Co_passenger_fullname3, Co_passenger_fullname4, Co_passenger_fullname5;
    EditText Co_passenger_age1, Co_passenger_age2, Co_passenger_age3, Co_passenger_age4, Co_passenger_age5;
    Button  PromoCodeApplay;
    TextView ContinueBooking;
    RadioButton Male, Female,Co_Passenger_Female1, Co_Passenger_Female2, Co_Passenger_Female3, Co_Passenger_Female4, Co_Passenger_Female5;
    RadioButton Co_Passenger_Male1, Co_Passenger_Male2, Co_Passenger_Male3, Co_Passenger_Male4, Co_Passenger_Male5;
    CardView PromoCodeCardView, Co_Passenger1, Co_Passenger2, Co_Passenger3, Co_Passenger4, Co_Passenger5;
    Spinner Title, TravellerId, CopassengerTitle1, CopassengerTitle2, CopassengerTitle3, CopassengerTitle4, CopassengerTitle5;
    Spinner Co_passenger1_id_proof, Co_passenger2_id_proof, Co_passenger3_id_proof, Co_passenger4_id_proof, Co_passenger5_id_proof;
    String[] Titles = {"Mr", "Mrs", "Ms"};
    String[] primerPassengerIdProof = {"DRIVING_LICENCE", "PAN_CARD", "PASSPORT", "RATION_CARD", "VOTER_CARD", "AADHAR"};
    String IdCard = "PASSPORT", title="Mr", IDs = "PAN_CARD", Copassenger1Id = "PAN_CARD", Copassenger2Id = "RATION_CARD", Copassenger3Id = "RATION_CARD", Copassenger4Id = "RATION_CARD", Copassenger5Id = "RATION_CARD";
    String CTitle1="Mr", CTitle2="Mr", CTitle3="Mr", CTitle4="Mr", CTitle5="Mr";
    String EMAIL, MOBILE, FullName, AGE, ValidPromoCode, Address, IdProofNumber;
    String OnwardReferenceNumber;
    Dialog dialog;
    InputStream inputStream;
    String data, responce;
    String refno, str, status;

    String SourceName, DestinationName, SourceId, DestinationId, JourneyDate, SelectedSeats, NumberOfSeats, BusTypeID;
    String Operator, BusType, departureTime, BusTypeid, NetFare;
    String CancellationPolicy, PartialCancellationAllowed, Provider, ObiboAPIProvidrer;
    String gender = "M", gender1 = "M", gender2 = "M", gender3 = "M", gender4 = "M", gender5 = "M";

    String Co_passenger_fullname_1, Co_passenger_fullname_2, Co_passenger_fullname_3, Co_passenger_fullname_4, Co_passenger_fullname_5;
    String Co_passenger_age_1, Co_passenger_age_2, Co_passenger_age_3, Co_passenger_age_4, Co_passenger_age_5;
    String Co_passenger1_id_proof_number, Co_passenger2_id_proof_number, Co_passenger3_id_proof_number, Co_passenger4_id_proof_number, Co_passenger5_id_proof_number;

    String Passenger_Full_name, Passenger_Age, Passenger_gender, TotalTitles;
    String ServiceTaxes, OperatorserviceCharge, Fares, OnwardBoardingId, OnwardBoardingPointDetails;

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String UserId, User_agentid;
    String userLogin, agentLogin;
    String Phonenumber;
    String Returndate;
    String ReturnOperator, ReturnBusType, ReturnBusTypeid, ReturndepartureTime, ReturnCancellationPolicy, ReturnPartialCancellationAllowed;
    String ReturnProvider, ReturnObiboAPIProvidrer, ReturnJourneyDate, ReturnSelectedSeats, ReturnNumberOfSeats, ReturnBusTypeID, ReturnNetFare,
            ReturnServiceTaxes, ReturnOperatorserviceCharge, ReturnFares, ReturnBoardingId, ReturnBoardingPointDetails;

    String IsReturnSelected;
    String BoardingId, BoardingPointDetails, SeatCodes, ConvienceFee, ReturnSeatCodes, ReturnConvienceFee, TotalConvienceFee, ReturnTotalConvienceFee;

    String ReturnTicketrefno, ReturnTicketstr;
    String RoundTripFare;

    Dialog agentpaymentDialog;
    int Discountint;
    String DiscountAmount;
    ImageView wallet;
    String Correct_Promo = "false";
    String PROMO = "false";
    Boolean dialogwallet = false;
    ArrayList<HashMap<String, String>> Codes = new ArrayList<>();
    LinearLayout PrompromoCodeApplyLayoutocode, promocodeAmountDetails;
    TextView PromocodeTotalPay, PromocodeDiscountAmount, PromocodeYouPay,HavepromoTV;

    String DeviceID;
    String Response;

    String AgentBalance;
    TextView DialogPay,WalletBalance,DialogAmount;
    ImageView can_Dialog;
    Login_utils login_utils;
    String User_Role;
    Boolean HavePromo=false;
    private Handler h;
    int ActualTotalFare;

    String ActualNetFare,ActualReturnNetFare;

    pl.droidsonroids.gif.GifTextView promoAppliedGIF;



    RadioGroup group1,group2,group3,group4,group5,group6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_passenger_details);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        contact_email = (EditText) findViewById(R.id.Contact_Email);
        contact_mobile = (EditText) findViewById(R.id.Contact_Mobile);
        passenger_fullname = (EditText) findViewById(R.id.full_name);
        passenger_age = (EditText) findViewById(R.id.Age);
        ContinueBooking = (TextView) findViewById(R.id.Booking);
        address = (EditText) findViewById(R.id.Address);

        Idnumber = (EditText) findViewById(R.id.Id_proof_number);

        Male = (RadioButton) findViewById(R.id.Male);
        Female = (RadioButton) findViewById(R.id.Female);

        login_utils=new Login_utils(this);

        getLoginPreefernces();
        CountDownTimer timer;


        promoAppliedGIF= (GifTextView) findViewById(R.id.promoAppliedGIF);
        promoAppliedGIF.setVisibility(View.GONE);
        HavepromoTV= (TextView) findViewById(R.id.HavePromoTV);
        Promocode = (EditText) findViewById(R.id.Promocode);
        PromoCodeApplay = (Button) findViewById(R.id.PromoCodeApplay);
        PromoCodeCardView = (CardView) findViewById(R.id.PromoCodeCardView);
        PrompromoCodeApplyLayoutocode = (LinearLayout) findViewById(R.id.promoCodeApplyLayout);
        promocodeAmountDetails = (LinearLayout) findViewById(R.id.promocodeAmountDetails);
        PromocodeTotalPay = (TextView) findViewById(R.id.TotalPay);
        PromocodeDiscountAmount = (TextView) findViewById(R.id.DiscountAmount);
        PromocodeYouPay = (TextView) findViewById(R.id.YouPay);
        PromoCodeApplay.setOnClickListener(this);
        HavepromoTV.setOnClickListener(this);

        String code = "+91  ";
        contact_mobile.setCompoundDrawablesWithIntrinsicBounds(new TextDrawable(code), null, null, null);
        contact_mobile.setCompoundDrawablePadding(code.length() * 20);


        Co_passenger_fullname1 = (EditText) findViewById(R.id.full_name1);
        Co_passenger_fullname2 = (EditText) findViewById(R.id.full_name2);
        Co_passenger_fullname3 = (EditText) findViewById(R.id.full_name3);
        Co_passenger_fullname4 = (EditText) findViewById(R.id.full_name4);
        Co_passenger_fullname5 = (EditText) findViewById(R.id.full_name5);


        Co_passenger_age1 = (EditText) findViewById(R.id.Age1);
        Co_passenger_age2 = (EditText) findViewById(R.id.Age2);
        Co_passenger_age3 = (EditText) findViewById(R.id.Age3);
        Co_passenger_age4 = (EditText) findViewById(R.id.Age4);
        Co_passenger_age5 = (EditText) findViewById(R.id.Age5);


        Co_Passenger_Male1 = (RadioButton) findViewById(R.id.Male1);
        Co_Passenger_Female1 = (RadioButton) findViewById(R.id.Female1);
        Co_Passenger_Male2 = (RadioButton) findViewById(R.id.Male2);
        Co_Passenger_Female2 = (RadioButton) findViewById(R.id.Female2);
        Co_Passenger_Male3 = (RadioButton) findViewById(R.id.Male3);
        Co_Passenger_Female3 = (RadioButton) findViewById(R.id.Female3);
        Co_Passenger_Male4 = (RadioButton) findViewById(R.id.Male4);
        Co_Passenger_Female4 = (RadioButton) findViewById(R.id.Female4);
        Co_Passenger_Male5 = (RadioButton) findViewById(R.id.Male5);
        Co_Passenger_Female5 = (RadioButton) findViewById(R.id.Female5);


        group1= (RadioGroup) findViewById(R.id.group1);
        group2= (RadioGroup) findViewById(R.id.group2);
        group3= (RadioGroup) findViewById(R.id.group3);
        group4= (RadioGroup) findViewById(R.id.group4);
        group5= (RadioGroup) findViewById(R.id.group5);
        group6= (RadioGroup) findViewById(R.id.group6);




        Co_Passenger1 = (CardView) findViewById(R.id.Co_passenger1);
        Co_Passenger2 = (CardView) findViewById(R.id.Co_passenger2);
        Co_Passenger3 = (CardView) findViewById(R.id.Co_passenger3);
        Co_Passenger4 = (CardView) findViewById(R.id.Co_passenger4);
        Co_Passenger5 = (CardView) findViewById(R.id.Co_passenger5);

      /*  Co_passenger1_Id_proof_number = (EditText) findViewById(R.id.Co_passenger1_Id_proof_number);
        Co_passenger2_Id_proof_number = (EditText) findViewById(R.id.Co_passenger2_Id_proof_number);
        Co_passenger3_Id_proof_number = (EditText) findViewById(R.id.Co_passenger3_Id_proof_number);
        Co_passenger4_Id_proof_number = (EditText) findViewById(R.id.Co_passenger4_Id_proof_number);
        Co_passenger5_Id_proof_number = (EditText) findViewById(R.id.Co_passenger5_Id_proof_number);*/

        SharedPreferences preference3 = getApplicationContext().getSharedPreferences("Sources", MODE_PRIVATE);
        SourceName = preference3.getString("FROMNAME", null);
        DestinationName = preference3.getString("TONAME", null);
        SourceId = preference3.getString("FROMID", null);
        DestinationId = preference3.getString("TOID", null);


        SharedPreferences preference1 = getApplicationContext().getSharedPreferences("OnwardJourneyDetails", MODE_PRIVATE);
        Operator = preference1.getString("OnwardOperator", null);
        BusType = preference1.getString("OnwardBusType", null);
        BusTypeid = preference1.getString("OnwardBusTypeID", null);
        departureTime = preference1.getString("OnwarddepartureTime", null);
        CancellationPolicy = preference1.getString("OnwardCancellationPolicy", null);
        PartialCancellationAllowed = preference1.getString("OnwardPartialCancellationAllowed", null);
        Provider = preference1.getString("OnwardProvider", null);
        ObiboAPIProvidrer = preference1.getString("OnwardObiboAPIProvider", null);
        JourneyDate = preference1.getString("OnwardJourneyDate", null);
        SelectedSeats = preference1.getString("OnwardSelectedSeats", null);
        NumberOfSeats = preference1.getString("OnwardNumberOfSeats", null);
        BusTypeID = preference1.getString("OnwardBusTypeID", null);
        NetFare = preference1.getString("OnwardNetFare", null);
        ServiceTaxes = preference1.getString("OnwardServiceTaxes", null);
        OperatorserviceCharge = preference1.getString("OnwardOperatorserviceCharge", null);
        Fares = preference1.getString("OnwardFares", null);
        BoardingId = preference1.getString("OnwardBoadingId", null);
        BoardingPointDetails = preference1.getString("OnwardBoadingName", null);
        SeatCodes = preference1.getString("OnwardSeatCode", null);
        ConvienceFee = preference1.getString("OnwardConvienceFee", null);
        TotalConvienceFee = preference1.getString("OnwardTotalConvienceFee", null);
        ActualNetFare=NetFare;

        SharedPreferences preference = getApplicationContext().getSharedPreferences("JourneyDetails", MODE_PRIVATE);
        ReturnOperator = preference.getString("Operator", null);
        ReturnBusType = preference.getString("BusType", null);
        ReturnBusTypeid = preference.getString("BusTypeID", null);
        ReturndepartureTime = preference.getString("departureTime", null);
        ReturnCancellationPolicy = preference.getString("CancellationPolicy", null);
        ReturnPartialCancellationAllowed = preference.getString("PartialCancellationAllowed", null);
        ReturnProvider = preference.getString("Provider", null);
        ReturnObiboAPIProvidrer = preference.getString("ObiboAPIProvider", null);
        ReturnJourneyDate = preference.getString("JourneyDate", null);
        ReturnSelectedSeats = preference.getString("SelectedSeats", null);
        ReturnNumberOfSeats = preference.getString("NumberOfSeats", null);
        ReturnBusTypeID = preference.getString("BusTypeID", null);
        ReturnNetFare = preference.getString("NetFare", null);
        ReturnServiceTaxes = preference.getString("ServiceTaxes", null);
        ReturnOperatorserviceCharge = preference.getString("OperatorserviceCharge", null);
        ReturnFares = preference.getString("Fares", null);
        IsReturnSelected = preference.getString("IsReturnSelected", null);
        Returndate = preference.getString("PassengerRETURN_DATE", null);
        ReturnSeatCodes = preference.getString("SeatCodes", null);
        ReturnConvienceFee = preference.getString("ConvienceFee", null);
        ReturnTotalConvienceFee = preference.getString("TotalConvienceFee", null);
        ActualReturnNetFare=ReturnNetFare;

        SharedPreferences preference2 = getApplicationContext().getSharedPreferences("BoardingPoint", MODE_PRIVATE);
        ReturnBoardingId = preference2.getString("BoadingId", null);
        ReturnBoardingPointDetails = preference2.getString("BoadingName", null);



        if(IsReturnSelected.equals("Yes")){
            int TotalFareint =  RoundOff(Double.parseDouble(ActualNetFare));
            int ReturnTotalFareint = RoundOff(Double.parseDouble(ActualReturnNetFare));
           ActualTotalFare = (TotalFareint + ReturnTotalFareint);
        }else{
            ActualTotalFare =  RoundOff(Double.parseDouble(ActualNetFare));
        }
        ContinueBooking.setText("Proceed to pay ₹ "+ActualTotalFare+"");

       /* pref = getSharedPreferences("LoginPreference", MODE_PRIVATE);
        editor = pref.edit();
        userLogin = pref.getString("UserLOGIN", null);
        agentLogin = pref.getString("AgentLOGIN", null);*/

       /*userLogin=login_utils.getUserDetails(Constants.USERTYPE);


        if (userLogin != null && userLogin.equals("Yes")) {

        } else if (agentLogin != null && agentLogin.equals("Yes")) {
            UserId = "4";
            User_agentid = pref.getString("User_agentid", null);
            AgentBalance=pref.getString("Balance",null);
            PromoCodeCardView.setVisibility(View.GONE);
        } else {
            UserId = "5";
            User_agentid = "";
        }*/

       if(User_Role.equals("5")){
           UserId = "5";
           User_agentid = "";
       }else if(User_Role.equals("4")||User_Role.equals("6")){
           if(User_Role.equals("4")){
               UserId = "4";
               HavepromoTV.setVisibility(View.GONE);
               AgentBalance=login_utils.getUserDetails(Constants.BALANCE);
           }else {
               UserId = "6";
           }
           User_agentid = login_utils.getUserDetails(Constants.USERID);
           contact_email.setText(login_utils.getUserDetails(Constants.USEREMAIL));
           contact_mobile.setText(login_utils.getUserDetails(Constants.USERPHONE));
           passenger_fullname.setText(login_utils.getUserDetails(Constants.USERNAME));
           address.setText(login_utils.getUserDetails(Constants.USERADDRESS));

           if(login_utils.getUserDetails(Constants.USERGENDER).equals("Male")){
               Male.setChecked(true);
               Female.setChecked(false);
           }else{
               Male.setChecked(false);
               Female.setChecked(true);
           }

           String Dob=login_utils.getUserDetails(Constants.USERDOB).split(" ")[0];
           passenger_age.setText(getAge(Dob));
       }




        System.out.println("ObiboAPIProvidrer" + ObiboAPIProvidrer + "SelectedSeats" + SelectedSeats + "NumberOfSeats" + NumberOfSeats + "BusTypeID" + BusTypeID + "NetFare" + NetFare);
        switch (NumberOfSeats) {
            case "1":
                address.setImeOptions(EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                break;
            case "2":
                Co_Passenger1.setVisibility(View.VISIBLE);
                Co_passenger_age1.setImeOptions(EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                break;
            case "3":
                Co_Passenger1.setVisibility(View.VISIBLE);
                Co_Passenger2.setVisibility(View.VISIBLE);
                Co_passenger_age2.setImeOptions(EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                break;
            case "4":
                Co_Passenger1.setVisibility(View.VISIBLE);
                Co_Passenger2.setVisibility(View.VISIBLE);
                Co_Passenger3.setVisibility(View.VISIBLE);
                Co_passenger_age3.setImeOptions(EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                break;
            case "5":
                Co_Passenger1.setVisibility(View.VISIBLE);
                Co_Passenger2.setVisibility(View.VISIBLE);
                Co_Passenger3.setVisibility(View.VISIBLE);
                Co_Passenger4.setVisibility(View.VISIBLE);
                Co_passenger_age4.setImeOptions(EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                break;
            case "6":
                Co_Passenger1.setVisibility(View.VISIBLE);
                Co_Passenger2.setVisibility(View.VISIBLE);
                Co_Passenger3.setVisibility(View.VISIBLE);
                Co_Passenger4.setVisibility(View.VISIBLE);
                Co_Passenger5.setVisibility(View.VISIBLE);
                Co_passenger_age5.setImeOptions(EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                break;
        }




        group1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int ischecked) {
                if (Male.isChecked()){
                    gender="M";
                    title="Mr";
                }else {
                    gender="F";
                    title="Mrs";
                }
            }
        });

        group2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int ischecked) {
                if (Co_Passenger_Male1.isChecked()){
                    gender1="M";
                    CTitle1="Mr";
                }else {
                    gender1="F";
                    CTitle1="Mrs";
                }
            }
        });
        group3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int ischecked) {
                if (Co_Passenger_Male2.isChecked()){
                    gender2="M";
                    CTitle2="Mr";
                }else {
                    gender2="F";
                    CTitle2="Mrs";
                }
            }
        });
        group4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int ischecked) {
                if (Co_Passenger_Male3.isChecked()){
                    gender3="M";
                    CTitle3="Mr";
                }else {
                    gender3="F";
                    CTitle3="Mrs";
                }
            }
        });
        group5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int ischecked) {
                if (Co_Passenger_Male4.isChecked()){
                    gender4="M";
                    CTitle4="Mr";
                }else {
                    gender4="F";
                    CTitle4="Mrs";
                }
            }
        });
        group6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int ischecked) {
                if (Co_Passenger_Male5.isChecked()){
                    gender5="M";
                    CTitle5="Mr";
                }else {
                    CTitle5="Mrs";
                    gender5="F";
                }
            }
        });

       /* Title = (Spinner) findViewById(R.id.Titles);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, Titles);
        Title.setAdapter(adapter);
        Title.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long ard3) {
                int position = Title.getSelectedItemPosition();
                title = Titles[+position];


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        CopassengerTitle1 = (Spinner) findViewById(R.id.CoPassengerTitles1);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, Titles);
        CopassengerTitle1.setAdapter(adapter1);
        CopassengerTitle1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long ard3) {
                int position = CopassengerTitle1.getSelectedItemPosition();
                CTitle1 = Titles[+position];


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        CopassengerTitle2 = (Spinner) findViewById(R.id.CoPassengerTitles2);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, Titles);
        CopassengerTitle2.setAdapter(adapter2);
        CopassengerTitle2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long ard3) {
                int position = CopassengerTitle2.getSelectedItemPosition();
                CTitle2 = Titles[+position];


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        CopassengerTitle3 = (Spinner) findViewById(R.id.CoPassengerTitles3);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, Titles);
        CopassengerTitle3.setAdapter(adapter3);
        CopassengerTitle3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long ard3) {
                int position = CopassengerTitle3.getSelectedItemPosition();
                CTitle3 = Titles[+position];


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        CopassengerTitle4 = (Spinner) findViewById(R.id.CoPassengerTitles4);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, Titles);
        CopassengerTitle4.setAdapter(adapter4);
        CopassengerTitle4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long ard3) {
                int position = CopassengerTitle4.getSelectedItemPosition();
                CTitle4 = Titles[+position];


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        CopassengerTitle5 = (Spinner) findViewById(R.id.CoPassengerTitles5);
        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, Titles);
        CopassengerTitle5.setAdapter(adapter5);
        CopassengerTitle5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long ard3) {
                int position = CopassengerTitle5.getSelectedItemPosition();
                CTitle5 = Titles[+position];


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });*/
       /* TravellerId = (Spinner) findViewById(R.id.Traveller_id_proof);
        ArrayAdapter<String> Travelleradapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, primerPassengerIdProof);
        TravellerId.setAdapter(Travelleradapter);
        TravellerId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long ard3) {
                int position = TravellerId.getSelectedItemPosition();
                IDs = primerPassengerIdProof[+position];


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });*/

      /*  Co_passenger1_id_proof = (Spinner) findViewById(R.id.Co_passenger1_id_proof);
        ArrayAdapter<String> Co_passenger1idproof = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, IdProof);
        Co_passenger1_id_proof.setAdapter(Co_passenger1idproof);
        Co_passenger1_id_proof.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long ard3) {
                int position = Co_passenger1_id_proof.getSelectedItemPosition();
                Copassenger1Id = IdProofID[+position];


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        Co_passenger2_id_proof = (Spinner) findViewById(R.id.Co_passenger2_id_proof);
        ArrayAdapter<String> Co_passenger2idproof = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, IdProof);
        Co_passenger2_id_proof.setAdapter(Co_passenger2idproof);
        Co_passenger2_id_proof.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long ard3) {
                int position = Co_passenger2_id_proof.getSelectedItemPosition();
                Copassenger2Id = IdProofID[+position];


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        Co_passenger3_id_proof = (Spinner) findViewById(R.id.Co_passenger3_id_proof);
        ArrayAdapter<String> Co_passenger3idproof = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, IdProof);
        Co_passenger3_id_proof.setAdapter(Co_passenger3idproof);
        Co_passenger3_id_proof.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long ard3) {
                int position = Co_passenger3_id_proof.getSelectedItemPosition();
                Copassenger3Id = IdProofID[+position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        Co_passenger4_id_proof = (Spinner) findViewById(R.id.Co_passenger4_id_proof);
        ArrayAdapter<String> Co_passenger4idproof = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, IdProof);
        Co_passenger4_id_proof.setAdapter(Co_passenger4idproof);
        Co_passenger4_id_proof.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long ard3) {
                int position = Co_passenger4_id_proof.getSelectedItemPosition();
                Copassenger4Id = IdProofID[+position];


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        Co_passenger5_id_proof = (Spinner) findViewById(R.id.Co_passenger5_id_proof);
        ArrayAdapter<String> Co_passenger5idproof = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, IdProof);
        Co_passenger5_id_proof.setAdapter(Co_passenger5idproof);
        Co_passenger5_id_proof.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long ard3) {
                int position = Co_passenger5_id_proof.getSelectedItemPosition();
                Copassenger5Id = IdProofID[+position];


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });*/

        DeviceID= Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        System.out.println(DeviceID+"----deviceid");
        ContinueBooking.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v== HavepromoTV){
            if(HavePromo){
                HavepromoTV.setText(getResources().getText(R.string.havePromo));
                PromoCodeCardView.setVisibility(View.GONE);
                PrompromoCodeApplyLayoutocode.setVisibility(View.VISIBLE);
                promocodeAmountDetails.setVisibility(View.GONE);
                NetFare=ActualNetFare;
                ReturnNetFare=ActualReturnNetFare;
                if(IsReturnSelected.equals("Yes")){
                    int TotalFareint =  RoundOff(Double.parseDouble(ActualNetFare));
                    int ReturnTotalFareint = RoundOff(Double.parseDouble(ActualReturnNetFare));
                    ActualTotalFare = (TotalFareint + ReturnTotalFareint);
                }else{
                    ActualTotalFare =  RoundOff(Double.parseDouble(ActualNetFare));
                }
                ContinueBooking.setText("Proceed to pay ₹ "+ActualTotalFare+"");
                Promocode.setText("");
                Promocode.setHint("Enter promo code");
                HavePromo=false;
            }else{
                HavepromoTV.setText("X");
                PromoCodeCardView.setVisibility(View.VISIBLE);
                HavePromo=true;

            }
        }
        if (v == PromoCodeApplay) {
            ValidPromoCode = Promocode.getText().toString();
            if (!ValidPromoCode.equalsIgnoreCase("")) {
                new ValidPromoCodeAppaly().execute();

            } else {
                Promocode.setError("Enter Promo code");
            }
        }
        if (v == ContinueBooking) {
            int length;


            FullName = passenger_fullname.getText().toString();
            EMAIL = contact_email.getText().toString();
            MOBILE = contact_mobile.getText().toString();
            AGE = passenger_age.getText().toString();
            Address = address.getText().toString();
            IdProofNumber = "1234567890";


            Co_passenger_fullname_1 = Co_passenger_fullname1.getText().toString();
            Co_passenger_fullname_2 = Co_passenger_fullname2.getText().toString();
            Co_passenger_fullname_3 = Co_passenger_fullname3.getText().toString();
            Co_passenger_fullname_4 = Co_passenger_fullname4.getText().toString();
            Co_passenger_fullname_5 = Co_passenger_fullname5.getText().toString();

            Co_passenger_age_1 = Co_passenger_age1.getText().toString();
            Co_passenger_age_2 = Co_passenger_age2.getText().toString();
            Co_passenger_age_3 = Co_passenger_age3.getText().toString();
            Co_passenger_age_4 = Co_passenger_age4.getText().toString();
            Co_passenger_age_5 = Co_passenger_age5.getText().toString();

            Co_passenger1_id_proof_number="1234567890";
            Co_passenger2_id_proof_number="1234567890";
            Co_passenger3_id_proof_number="1234567890";
            Co_passenger4_id_proof_number="1234567890";
            Co_passenger5_id_proof_number="1234567890";


            length = MOBILE.length();
            if (!FullName.equals("") && !AGE.equals("") && !Address.equals("") && !IdProofNumber.equals("") && !MOBILE.equals("") && (length == 10) && !EMAIL.equals("") && EMAIL.matches(Constants.emailPattern)) {

                System.out.println("Mobile number :" + MOBILE);
                Phonenumber = MOBILE;
                if (NumberOfSeats.equals("1")) {
                    Passenger_Full_name = "" + FullName;
                    Passenger_Age = "" + AGE;
                    Passenger_gender = "" + gender;
                    TotalTitles = "" + title;
                    IdCard = "" + IDs;
                    isConnected();

                }
                switch (NumberOfSeats) {
                    case "2":

                        if (!Co_passenger_fullname_1.equals("") && !Co_passenger_age_1.equals("")) {
                            Passenger_Full_name = "" + FullName + "~" + Co_passenger_fullname_1;
                            Passenger_Age = "" + AGE + "~" + Co_passenger_age_1;
                            Passenger_gender = "" + gender + "~" + gender1;
                            TotalTitles = "" + title + "~" + CTitle1;
                            IdCard = "" + IDs + "~" + Copassenger1Id;
                            IdProofNumber = "" + IdProofNumber + "~" + Co_passenger1_id_proof_number;
                            System.out.println("Number of seats 2" + FullName + AGE);
                             isConnected();

                        } else {
                            if (Co_passenger_fullname_1.equals("") || Co_passenger_age_1.equals("")) {
                                if (Co_passenger_fullname_1.equals("")) {
                                    Co_passenger_fullname1.setError("Enter Name");
                                }
                                if (Co_passenger_age_1.equals("")) {
                                    Co_passenger_age1.setError("Enter Age");
                                }


                            }

                        }
                        break;
                    case "3":

                        if (!Co_passenger_fullname_1.equals("") && !Co_passenger_age_1.equals("") && !Co_passenger_fullname_2.equals("") && !Co_passenger_age_2.equals("")) {
                            Passenger_Full_name = "" + FullName + "~" + Co_passenger_fullname_1 + "~" + Co_passenger_fullname_2;
                            Passenger_Age = "" + AGE + "~" + Co_passenger_age_1 + "~" + Co_passenger_age_2;
                            Passenger_gender = "" + gender + "~" + gender1 + "~" + gender2;
                            TotalTitles = "" + title + "~" + CTitle1 + "~" + CTitle2;
                            IdCard = "" + IDs + "~" + Copassenger1Id + "~" + Copassenger2Id;
                            IdProofNumber = "" + IdProofNumber + "~" + Co_passenger1_id_proof_number + "~" + Co_passenger2_id_proof_number;
                            System.out.println("Number of seats 2" + FullName + AGE);
                            isConnected();

                        } else {
                            if (Co_passenger_fullname_1.equals("") || Co_passenger_age_1.equals("") || Co_passenger_fullname_2.equals("") || Co_passenger_age_2.equals("")) {
                                if (Co_passenger_fullname_1.equals("")) {
                                    Co_passenger_fullname1.setError("Enter Name");
                                }
                                if (Co_passenger_age_1.equals("")) {
                                    Co_passenger_age1.setError("Enter Age");
                                }
                                if (Co_passenger_fullname_2.equals("")) {
                                    Co_passenger_fullname2.setError("Enter Name");
                                }
                                if (Co_passenger_age_2.equals("")) {
                                    Co_passenger_age2.setError("Enter Age");
                                }


                            }

                        }
                        break;
                    case "4":

                        if (!Co_passenger_fullname_1.equals("") && !Co_passenger_age_1.equals("") && !Co_passenger_fullname_2.equals("") && !Co_passenger_age_2.equals("") && !Co_passenger_fullname_3.equals("") && !Co_passenger_age_3.equals("")) {
                            Passenger_Full_name = "" + FullName + "~" + Co_passenger_fullname_1 + "~" + Co_passenger_fullname_2 + "~" + Co_passenger_fullname_3;
                            Passenger_Age = "" + AGE + "~" + Co_passenger_age_1 + "~" + Co_passenger_age_2 + "~" + Co_passenger_age_3;
                            Passenger_gender = "" + gender + "~" + gender1 + "~" + gender2 + "~" + gender3;
                            TotalTitles = "" + title + "~" + CTitle1 + "~" + CTitle2 + "~" + CTitle3;
                            IdCard = "" + IDs + "~" + Copassenger1Id + "~" + Copassenger2Id + "~" + Copassenger3Id;
                            IdProofNumber = "" + IdProofNumber + "~" + Co_passenger1_id_proof_number + "~" + Co_passenger2_id_proof_number + "~" + Co_passenger3_id_proof_number;
                            isConnected();

                        } else {
                            if (Co_passenger_fullname_1.equals("") || Co_passenger_age_1.equals("") || Co_passenger_fullname_2.equals("") || Co_passenger_age_2.equals("") || Co_passenger_fullname_3.equals("") || Co_passenger_age_3.equals("")) {
                                if (Co_passenger_fullname_1.equals("")) {
                                    Co_passenger_fullname1.setError("Enter Name");
                                }
                                if (Co_passenger_age_1.equals("")) {
                                    Co_passenger_age1.setError("Enter Age");
                                }
                                if (Co_passenger_fullname_2.equals("")) {
                                    Co_passenger_fullname2.setError("Enter Name");
                                }
                                if (Co_passenger_age_2.equals("")) {
                                    Co_passenger_age2.setError("Enter Age");
                                }
                                if (Co_passenger_fullname_3.equals("")) {
                                    Co_passenger_fullname3.setError("Enter Name");
                                }
                                if (Co_passenger_age_3.equals("")) {
                                    Co_passenger_age3.setError("Enter Age");
                                }


                            }

                        }
                        break;
                    case "5":
                        if (!Co_passenger_fullname_1.equals("") && !Co_passenger_age_1.equals("") && !Co_passenger_fullname_2.equals("") && !Co_passenger_age_2.equals("") && !Co_passenger_fullname_3.equals("") && !Co_passenger_age_3.equals("") && !Co_passenger_fullname_4.equals("") && !Co_passenger_age_4.equals("")) {
                            Passenger_Full_name = "" + FullName + "~" + Co_passenger_fullname_1 + "~" + Co_passenger_fullname_2 + "~" + Co_passenger_fullname_3 + "~" + Co_passenger_fullname_4;
                            Passenger_Age = "" + AGE + "~" + Co_passenger_age_1 + "~" + Co_passenger_age_2 + "~" + Co_passenger_age_3 + "~" + Co_passenger_age_4;
                            Passenger_gender = "" + gender + "~" + gender1 + "~" + gender2 + "~" + gender3 + "~" + gender4;
                            TotalTitles = "" + title + "~" + CTitle1 + "~" + CTitle2 + "~" + CTitle3 + "~" + CTitle4;
                            IdCard = "" + IDs + "~" + Copassenger1Id + "~" + Copassenger2Id + "~" + Copassenger3Id + "~" + Copassenger4Id;
                            IdProofNumber = "" + IdProofNumber + "~" + Co_passenger1_id_proof_number + "~" + Co_passenger2_id_proof_number + "~" + Co_passenger3_id_proof_number + "~" + Co_passenger4_id_proof_number;
                            isConnected();

                        } else {
                            if (Co_passenger_fullname_1.equals("") || Co_passenger_age_1.equals("") || Co_passenger_fullname_2.equals("") || Co_passenger_age_2.equals("") || Co_passenger_fullname_3.equals("") || Co_passenger_age_3.equals("") || Co_passenger_fullname_4.equals("") || Co_passenger_age_4.equals("")) {
                                if (Co_passenger_fullname_1.equals("")) {
                                    Co_passenger_fullname1.setError("Enter Name");
                                }
                                if (Co_passenger_age_1.equals("")) {
                                    Co_passenger_age1.setError("Enter Age");
                                }
                                if (Co_passenger_fullname_2.equals("")) {
                                    Co_passenger_fullname2.setError("Enter Name");
                                }
                                if (Co_passenger_age_2.equals("")) {
                                    Co_passenger_age2.setError("Enter Age");
                                }
                                if (Co_passenger_fullname_3.equals("")) {
                                    Co_passenger_fullname3.setError("Enter Name");
                                }
                                if (Co_passenger_age_3.equals("")) {
                                    Co_passenger_age3.setError("Enter Age");
                                }
                                if (Co_passenger_fullname_4.equals("")) {
                                    Co_passenger_fullname4.setError("Enter Name");
                                }
                                if (Co_passenger_age_4.equals("")) {
                                    Co_passenger_age4.setError("Enter Age");
                                }


                            }

                        }
                        break;
                    case "6":
                        if (!Co_passenger_fullname_1.equals("") && !Co_passenger_age_1.equals("") && !Co_passenger_fullname_2.equals("") && !Co_passenger_age_2.equals("") && !Co_passenger_fullname_3.equals("") && !Co_passenger_age_3.equals("") && !Co_passenger_fullname_4.equals("") && !Co_passenger_age_4.equals("") && !Co_passenger_fullname_5.equals("") && !Co_passenger_age_5.equals("")) {
                            Passenger_Full_name = "" + FullName + "~" + Co_passenger_fullname_1 + "~" + Co_passenger_fullname_2 + "~" + Co_passenger_fullname_3 + "~" + Co_passenger_fullname_4 + "~" + Co_passenger_fullname_5;
                            Passenger_Age = "" + AGE + "~" + Co_passenger_age_1 + "~" + Co_passenger_age_2 + "~" + Co_passenger_age_3 + "~" + Co_passenger_age_4 + "~" + Co_passenger_age_5;
                            Passenger_gender = "" + gender + "~" + gender1 + "~" + gender2 + "~" + gender3 + "~" + gender4 + "~" + gender5;
                            TotalTitles = "" + title + "~" + CTitle1 + "~" + CTitle2 + "~" + CTitle3 + "~" + CTitle4 + "~" + CTitle5;
                            IdCard = "" + IDs + "~" + Copassenger1Id + "~" + Copassenger2Id + "~" + Copassenger3Id + "~" + Copassenger4Id + "~" + Copassenger5Id;
                            IdProofNumber = "" + IdProofNumber + "~" + Co_passenger1_id_proof_number + "~" + Co_passenger2_id_proof_number + "~" + Co_passenger3_id_proof_number + "~" + Co_passenger4_id_proof_number + "~" + Co_passenger5_id_proof_number;
                            isConnected();

                        } else {
                            if (Co_passenger_fullname_1.equals("") || Co_passenger_age_1.equals("") || Co_passenger_fullname_2.equals("") || Co_passenger_age_2.equals("") || Co_passenger_fullname_3.equals("") || Co_passenger_age_3.equals("") || Co_passenger_fullname_4.equals("") || Co_passenger_age_4.equals("") || Co_passenger_fullname_5.equals("") || Co_passenger_age_5.equals("")) {
                                if (Co_passenger_fullname_1.equals("")) {
                                    Co_passenger_fullname1.setError("Enter Name");
                                }
                                if (Co_passenger_age_1.equals("")) {
                                    Co_passenger_age1.setError("Enter Age");
                                }
                                if (Co_passenger_fullname_2.equals("")) {
                                    Co_passenger_fullname2.setError("Enter Name");
                                }
                                if (Co_passenger_age_2.equals("")) {
                                    Co_passenger_age2.setError("Enter Age");
                                }
                                if (Co_passenger_fullname_3.equals("")) {
                                    Co_passenger_fullname3.setError("Enter Name");
                                }
                                if (Co_passenger_age_3.equals("")) {
                                    Co_passenger_age3.setError("Enter Age");
                                }
                                if (Co_passenger_fullname_4.equals("")) {
                                    Co_passenger_fullname4.setError("Enter Name");
                                }
                                if (Co_passenger_age_4.equals("")) {
                                    Co_passenger_age4.setError("Enter Age");
                                }
                                if (Co_passenger_fullname_5.equals("")) {
                                    Co_passenger_fullname5.setError("Enter Name");
                                }
                                if (Co_passenger_age_5.equals("")) {
                                    Co_passenger_age5.setError("Enter Age");
                                }


                            }

                        }
                        break;
                }
            } else {
                if (FullName.equals("") || AGE.equals("") || Address.equals("") || IdProofNumber.equals("") || MOBILE.equals("") || length < 10 || EMAIL.equals("") || !EMAIL.matches(Constants.emailPattern)) {
                    if (FullName.equals("")) {
                        passenger_fullname.setError("Enter Name");
                    }
                    if (AGE.equals("")) {
                        passenger_age.setError("Enter Age");
                    }
                    if (Address.equals("")) {
                        address.setError("Enter Address");
                    }
                    if (IdProofNumber.equals("")) {
                        Idnumber.setError("Enter Id Proof Number");
                    }
                    if (MOBILE.length() < 10) {
                        contact_mobile.setError("Enter Valid Mobile No");
                    }
                    if (!EMAIL.matches(Constants.emailPattern)) {
                        contact_email.setError("Enter Valid Email");
                    }
                    if (EMAIL.equals("")) {
                        contact_email.setError("Enter Email");
                    }


                }

            }


        }
    }

    public void getLoginPreefernces() {

            String s=login_utils.getUserDetails(Constants.USERTYPE);

        if(s.equals("6")){
            User_Role="6";
        }else if(s.equals("4")){
            User_Role="4";
        }else{
            User_Role="5";
        }
    }

    public class PromoCode extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(PassengerDetails.this, "", "Please Wait...\n Validating Promo Code");
        }

        @Override
        protected String doInBackground(String... params) {
            String url= Constants.BASEURL+ Constants.ValidatePromo+ValidPromoCode+"&Devicetoken="+DeviceID;

            DefaultHttpClient httpClient = new DefaultHttpClient();


            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("ConsumerKey", Constants.ConsumerKey);
            httpGet.setHeader("ConsumerSecret", Constants.ConsumerSecret);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            try {
                Response = httpClient.execute(httpGet, responseHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }


            return Response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
             dialog.dismiss();
            if (Response!=null){
                try {
                    JSONObject object=new JSONObject(Response);
                    String Message=object.getString("Message");
                    String Code=object.getString("StatusCode");
                    if (Message.equals("Success")){
                      new ValidPromoCodeAppaly().execute();
                    }else {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PassengerDetails.this);

                        // set title
                        alertDialogBuilder.setTitle("");

                        // set dialog message
                        alertDialogBuilder
                                .setMessage("Promo Code Already used")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();


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
    }

    ///////****** TO CHECKING PROMO CODE VALID OR NOT ITS VALID PROMOCODE TO CHECK DISSCOUNT AMOUNT *******////////
    public class ValidPromoCodeAppaly extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(PassengerDetails.this, "", "Please Wait...\n Validating Promo Code");
        }

        @Override
        protected String doInBackground(String... params) {
            String strurl = Constants.GETPROMOCODES;
            DefaultHttpClient httpClient = new DefaultHttpClient();


            HttpGet httpGet = new HttpGet(strurl);
            httpGet.setHeader("ConsumerKey", Constants.ConsumerKey);
            httpGet.setHeader("ConsumerSecret", Constants.ConsumerSecret);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            try {
                data = httpClient.execute(httpGet, responseHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
             dialog.dismiss();
            try {

                if (!data.equals("") && !data.equals("null") && !data.contains("Message")) {
                    JSONArray PromocodeArray = new JSONArray(data);

                    int len = PromocodeArray.length();
                    for (int k = 0; k < len; k++) {
                        JSONObject object = PromocodeArray.getJSONObject(k);
                        HashMap<String, String> Ma = new HashMap<String, String>();
                        String Servicetype = object.getString("ServiceType");
                        if (Servicetype.equals("1")) {
                            Ma.put("Code", object.getString("Code"));
                            String Code=object.getString("Code");
                            if (Code.equals(ValidPromoCode)) {
                                Correct_Promo = "true";
                                String VaildFromDate = PromocodeArray.getJSONObject(k).getString("ValidFrom").split("T")[0];
                                String VaildTillDate = PromocodeArray.getJSONObject(k).getString("ValidTill").split("T")[0];
                                String From=PromocodeArray.getJSONObject(k).getString("FromAmount");
                                String To= PromocodeArray.getJSONObject(k).getString("ToAmount");
                                int FromAmount=(int) Math.round(Double.parseDouble(From));
                                int ToAmount=(int) Math.round(Double.parseDouble(To));
                                int Total = (int) Math.round(Double.parseDouble(NetFare));

                                SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    Date PromoCodeValidFromDate = output.parse(VaildFromDate);
                                    Date PromoCodeValidTillDate = output.parse(VaildTillDate);

                                    Calendar c = Calendar.getInstance();
                                    String CurrentDate = output.format((c.getTime()));
                                    Date todayDate = output.parse(CurrentDate);

                                    if (PromoCodeValidTillDate.after(todayDate) || todayDate.equals(PromoCodeValidTillDate) && PromoCodeValidFromDate.before(todayDate) || todayDate.equals(PromoCodeValidFromDate)) {
                                        if (Total>=FromAmount&&Total<=ToAmount) {
                                            // In between
                                            String DiscountType = PromocodeArray.getJSONObject(k).getString("DiscountType");
                                            String Discount = PromocodeArray.getJSONObject(k).getString("Discount");
                                            Double d = Double.parseDouble(String.valueOf(Discount));
                                            Discountint = (int) Math.round(d);
                                            PROMO = "true";
                                            if (IsReturnSelected.equals("Yes")) {
                                                if (DiscountType.equals("0")) {
                                                    int TotalFareint =  RoundOff(Double.parseDouble(NetFare));
                                                    int ReturnTotalFareint = RoundOff(Double.parseDouble(ReturnNetFare));
                                                    int Fare = (TotalFareint + ReturnTotalFareint);
                                                    double DiscountAmonut = (Fare / 100.0f) * Discountint;
                                                    int DisC = RoundOff(DiscountAmonut);
                                                    DiscountAmount = String.valueOf(DisC);
                                                    double TotalFAre = Fare - DisC;
                                                    int Amount = (int) TotalFAre;
                                                    ReturnNetFare = String.valueOf(Amount);
                                                    int TotalShowingFare = (TotalFareint + ReturnTotalFareint);
                                                    PrompromoCodeApplyLayoutocode.setVisibility(View.GONE);
                                                    promocodeAmountDetails.setVisibility(View.VISIBLE);
                                                    PromocodeTotalPay.setText("" + TotalShowingFare + "");
                                                    PromocodeDiscountAmount.setText("" + DisC + "");
                                                    PromocodeYouPay.setText("" + ReturnNetFare + "");
                                                    ContinueBooking.setText("Proceed to pay ₹ "+ReturnNetFare+"");

                                                } else {
                                                    int TotalFareint =  RoundOff(Double.parseDouble(NetFare));
                                                    int ReturnTotalFareint = RoundOff(Double.parseDouble(ReturnNetFare));
                                                    int Fare = (TotalFareint + ReturnTotalFareint) - Discountint;
                                                    DiscountAmount = String.valueOf(Discountint);
                                                    ReturnNetFare = String.valueOf(Fare);
                                                    int TotalShowingFare = (TotalFareint + ReturnTotalFareint);
                                                    PrompromoCodeApplyLayoutocode.setVisibility(View.GONE);
                                                    promocodeAmountDetails.setVisibility(View.VISIBLE);
                                                    PromocodeTotalPay.setText("" + TotalShowingFare + "");
                                                    PromocodeDiscountAmount.setText("" + Discountint + "");
                                                    PromocodeYouPay.setText("" + ReturnNetFare + "");
                                                    ContinueBooking.setText("Proceed to pay ₹ "+ReturnNetFare+"");
                                                }

                                            } else {
                                                if (DiscountType.equals("0")) {
                                                    int TotalFareint = RoundOff(Double.parseDouble(NetFare));
                                                    double Disconut = (TotalFareint / 100.0f) * Discountint;
                                                    int DisC = RoundOff(Disconut);
                                                    DiscountAmount = String.valueOf(DisC);
                                                    double DiscountAmont = TotalFareint - DisC;
                                                    int Amount = (int) DiscountAmont;
                                                    NetFare = String.valueOf(Amount);
                                                    PrompromoCodeApplyLayoutocode.setVisibility(View.GONE);
                                                    promocodeAmountDetails.setVisibility(View.VISIBLE);
                                                    PromocodeTotalPay.setText("" + TotalFareint + "");
                                                    PromocodeDiscountAmount.setText("" + DisC + "");
                                                    PromocodeYouPay.setText("" + NetFare + "");
                                                    ContinueBooking.setText("Proceed to pay ₹ "+NetFare+"");
                                                } else {
                                                    int TotalFareint =  RoundOff(Double.parseDouble(NetFare));
                                                    int Fare = (TotalFareint) - Discountint;
                                                    DiscountAmount = String.valueOf(Discountint);
                                                    NetFare = String.valueOf(Fare);
                                                    PrompromoCodeApplyLayoutocode.setVisibility(View.GONE);
                                                    promocodeAmountDetails.setVisibility(View.VISIBLE);
                                                    PromocodeTotalPay.setText("" + TotalFareint + "");
                                                    PromocodeDiscountAmount.setText("" + Discountint + "");
                                                    PromocodeYouPay.setText("" + NetFare + "");
                                                    ContinueBooking.setText("Proceed to pay ₹ "+NetFare+"");
                                                }

                                            }


                                            getWindow().setSoftInputMode(
                                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                                            );

                                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PassengerDetails.this);

                                            // set title
                                            alertDialogBuilder.setTitle("");

                                            // set dialog message
                                            alertDialogBuilder
                                                    .setMessage("Promo Code Applied Successfully")
                                                    .setCancelable(false)
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();


                                                        }
                                                    });

                                            // create alert dialog
                                            AlertDialog alertDialog = alertDialogBuilder.create();

                                            // show it
                                            alertDialog.show();


                                            //Toast.makeText(PassengerDetails.this, "Promo Code Applied Successfully", Toast.LENGTH_SHORT).show();





                                         new CountDownTimer(3000, 2000) {

                                                @Override
                                                public void onTick(long millisUntilFinished) {
                                                    promoAppliedGIF.setVisibility(View.VISIBLE);
                                                }

                                                @Override
                                                public void onFinish() {
                                                    promoAppliedGIF .setVisibility(View.GONE);
                                                }
                                            }.start();





                                            break;
                                        }else {
                                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PassengerDetails.this);

                                            // set title
                                            alertDialogBuilder.setTitle("");

                                            // set dialog message
                                            alertDialogBuilder
                                                    .setMessage("Promo Code is Valid only when \nTotal Amount is in between\n"+FromAmount +" and "+ToAmount)
                                                    .setCancelable(false)
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();


                                                        }
                                                    });

                                            // create alert dialog
                                            AlertDialog alertDialog = alertDialogBuilder.create();

                                            // show it
                                            alertDialog.show();
                                            break;
                                        }

                                    } else {
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PassengerDetails.this);

                                        // set title
                                        alertDialogBuilder.setTitle("");

                                        // set dialog message
                                        alertDialogBuilder
                                                .setMessage("PromoCode Expired!")
                                                .setCancelable(false)
                                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();


                                                    }
                                                });

                                        // create alert dialog
                                        AlertDialog alertDialog = alertDialogBuilder.create();

                                        // show it
                                        alertDialog.show();
                                        break;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    if (Correct_Promo.equals("false")) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PassengerDetails.this);

                        // set title
                        alertDialogBuilder.setTitle("");

                        // set dialog message
                        alertDialogBuilder
                                .setMessage("Invalid PromoCode !")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();


                                    }
                                });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }



    public void isConnected(){
        if(Util.isNetworkAvailable(getApplicationContext())) {
            new BlockTicket().execute();
        }else{
            Util.showMessage(this,"Please Check Your Internet Connection");
        }
    }

    ///*****BUS TICKET BLOCKING SOME PARAMETERS ARE SENDING TO API AND GETTING RESPONCE REFERENCE NUMBER FORM BOOK TICKET
    public class BlockTicket extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = ProgressDialog.show(PassengerDetails.this, "", "Please Wait...");
            dialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {


            JSONObject bcparam = new JSONObject();
            // List<NameValuePair> parm=new ArrayList<NameValuePair>();
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(Constants.BASEURL + Constants.BlockBusTicket);
            httpPost.setHeader("ConsumerKey", Constants.ConsumerKey);
            httpPost.setHeader("ConsumerSecret", Constants.ConsumerSecret);
            httpPost.setHeader("Content-type", "application/json");
            try {


                bcparam.put("Address", Address);
                bcparam.put("Ages", Passenger_Age);
                bcparam.put("ApplicationType", "Mobile");
                bcparam.put("BoardingId", BoardingId);
                bcparam.put("BoardingPointDetails", BoardingPointDetails);
                bcparam.put("BusType", BusTypeid);
                bcparam.put("BusTypeName", BusType);
                bcparam.put("CancellationPolicy", CancellationPolicy);
                bcparam.put("ConvenienceFee", TotalConvienceFee);
                bcparam.put("DepartureTime", departureTime);
                bcparam.put("DestinationId", DestinationId);
                bcparam.put("DestinationName", DestinationName);
                bcparam.put("DeviceModel", Util.getDeviceName());
                bcparam.put("DeviceOS", "Android");
                bcparam.put("DeviceOSVersion", Build.VERSION.RELEASE);
                bcparam.put("DeviceToken", DeviceID);
                bcparam.put("EmailId", EMAIL);
                bcparam.put("EmergencyMobileNo", Phonenumber);
                bcparam.put("Fares", Fares);
                bcparam.put("Genders", Passenger_gender);
                bcparam.put("IdCardIssuedBy", "AP");
                bcparam.put("IdCardNo", IdProofNumber);
                bcparam.put("IdCardType", IdCard);
                bcparam.put("IsAgentPaymentGateway", "0");
                bcparam.put("IsOfflineBooking", "false");
                bcparam.put("JourneyDate", JourneyDate);
                bcparam.put("MobileNo", Phonenumber);
                bcparam.put("Names", Passenger_Full_name);
                bcparam.put("NoofSeats", NumberOfSeats);
                bcparam.put("Operator", Operator);
                bcparam.put("PartialCancellationAllowed", PartialCancellationAllowed);
                bcparam.put("PostalCode", "500082");
                bcparam.put("Provider", Provider);
                bcparam.put("SeatNos", SelectedSeats);
                bcparam.put("ServiceCharge", OperatorserviceCharge);
                bcparam.put("Servicetax", ServiceTaxes);
                bcparam.put("SourceId", SourceId);
                bcparam.put("SourceName", SourceName);
                bcparam.put("Titles", TotalTitles);
                bcparam.put("TripId", BusTypeID);
                bcparam.put("TripType", "1");
                bcparam.put("User", User_agentid);
                bcparam.put("UserType", UserId);
                bcparam.put("obiboProvider", ObiboAPIProvidrer);
                bcparam.put("returnDate", "");
                bcparam.put("Seatcodes", SeatCodes);
                bcparam.put("PromoCode", ValidPromoCode);
                //bcparam.put("PromoCodeAmount", DiscountAmount);

                // bcparam.put("IsAgentPaymentGateway", false);


                // httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");
                // parm.add(new BasicNameValuePair("res",bcparam.toString()));
                try {
                    StringEntity seEntity;
                    // String data1 = null;
                /*
                 * try { data1 = URLEncoder.encode(jsonObjectrows, "UTF-8"); }
				 * catch (UnsupportedEncodingException e2) { // TODO
				 * Auto-generated catch block e2.printStackTrace(); }
				 */
                    seEntity = new StringEntity(bcparam.toString(), "UTF_8");
                    //seEntity.setContentType("application/json");
                    httpPost.setEntity(seEntity);
                    HttpResponse httpResponse;

                    httpResponse = httpClient.execute(httpPost);
                    System.out.println("jsonresponce: "+ bcparam);
                    inputStream = httpResponse.getEntity().getContent();


                } catch (Exception e) {
                    e.printStackTrace();
                }
                //json=bcparam.toString();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream));
            String line = "";
            data = "";
            try {
                while ((line = bufferedReader.readLine()) != null)
                    data += line;
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                inputStream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Log.d("TAG_LOCATIONS", bcparam.toString());

            //   Log.v("rsponse", "" + JsonWriter.formatJson(data.toString()).toString());
            System.out.println("BookingStatus" + data);
            responce = "" + bcparam;
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(data);
                jsonObject.getString("BlockingReferenceNo");
                refno = jsonObject.getString("BookingReferenceNo");
                str = jsonObject.getString("Message");
                jsonObject.getString("BookingStatus");
                String Bookingstatus=jsonObject.getString("BookingStatus");
                String Res = jsonObject.getString("ResponseStatus");


                if (Bookingstatus.equals("1") && Res.equals("200")) {

                    if (IsReturnSelected.equals("Yes")) {
                        new ReturnTicketBlockTicket().execute();
                    } else {


                        if (UserId.equals("4")) {

                            agentpaymentDialog = new Dialog(PassengerDetails.this);
                            agentpaymentDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            agentpaymentDialog.setContentView(R.layout.agent_dialog);
                            agentpaymentDialog.setCancelable(false);


                            wallet = (ImageView) agentpaymentDialog.findViewById(R.id.walletimg);
                            DialogPay = (TextView) agentpaymentDialog.findViewById(R.id.pay);
                            WalletBalance = (TextView) agentpaymentDialog.findViewById(R.id.wallet_bal);
                            can_Dialog = (ImageView) agentpaymentDialog.findViewById(R.id.can_dialog);
                            WalletBalance.setText("₹ " + AgentBalance + "");
                            DialogAmount = (TextView) agentpaymentDialog.findViewById(R.id.AmountToPay);

                            double d = Double.parseDouble(NetFare);
                            int roundValue = RoundOff(d);
                            DialogAmount.setText("₹ " + roundValue + "");
                            DialogPay.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialogwallet = false;


                                    pay();

                                }
                            });

                            can_Dialog.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    final Context context = PassengerDetails.this;
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                                    // set title
                                    alertDialogBuilder.setTitle("Alert");

                                    // set dialog message
                                    alertDialogBuilder
                                            .setMessage("Are you sure you want to cancel this transaction?")
                                            .setCancelable(false)
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    agentpaymentDialog.dismiss();
                                                    Intent i = new Intent(PassengerDetails.this, Buses_MainActivity.class);
                                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(i);
                                                }
                                                //  }
                                            })
                                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    // if this button is clicked, just close
                                                    // the dialog box and do nothing
                                                    dialog.cancel();

                                                }
                                            });


                                    // create alert dialog
                                    AlertDialog alertDialog = alertDialogBuilder.create();

                                    // show it
                                    alertDialog.show();

                                }
                            });
                            agentpaymentDialog.show();


                    }else {
                            final Context context = PassengerDetails.this;
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                            // set title
                            alertDialogBuilder.setTitle("Payment Confirmation ");

                            // set dialog message
                            alertDialogBuilder
                                    .setMessage("Click Yes to proceed!")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // if this button is clicked, close
                                            // current activity
                                            //Buses_MainActivity.this.finish();


                                            Intent i = new Intent(PassengerDetails.this, Payment_Gateway_Main.class);
                                            i.putExtra("name", FullName);
                                            i.putExtra("email", String.valueOf(EMAIL));
                                            i.putExtra("phone", MOBILE);
                                            i.putExtra("amount", NetFare);
                                            i.putExtra("referencenumber", refno);
                                            i.putExtra("PaymentType", "Bus");
                                            System.out.println("referenceNo" + refno);
                                            i.putExtra("isFromOrder", true);
                                            i.putExtra("BookingType","Bus_Booking");
                                            startActivity(i);
                                            //  }
                                        }
                                        //  }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // if this button is clicked, just close
                                            // the dialog box and do nothing
                                            dialog.cancel();
                                            Intent i = new Intent(PassengerDetails.this, Buses_MainActivity.class);
                                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(i);
                                        }
                                    });

                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it
                            alertDialog.show();
                        }


                    }


                } else {

                    final Context context = PassengerDetails.this;
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                    // set title
                    //alertDialogBuilder.setTitle("Message");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("" + str)
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent i = new Intent(PassengerDetails.this, SearchActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(i);
                                }
                                //  }
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


    ///// ******** REturn Bus Ticket Blockin ****??????

    public class ReturnTicketBlockTicket extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = ProgressDialog.show(PassengerDetails.this, "", "Please Wait....");
            dialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {


            JSONObject bcparam = new JSONObject();
            // List<NameValuePair> parm=new ArrayList<NameValuePair>();
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(Constants.BASEURL + Constants.BlockBusTicket);
            httpPost.setHeader("ConsumerKey", Constants.ConsumerKey);
            httpPost.setHeader("ConsumerSecret", Constants.ConsumerSecret);
            httpPost.setHeader("Content-type", "application/json");
            try {

                bcparam.put("Address", Address);
                bcparam.put("Ages", Passenger_Age);
                bcparam.put("ApplicationType", "Mobile");
                bcparam.put("BoardingId", ReturnBoardingId);
                bcparam.put("BoardingPointDetails", ReturnBoardingPointDetails);
                bcparam.put("BusType", ReturnBusTypeid);
                bcparam.put("BusTypeName", ReturnBusType);
                bcparam.put("CancellationPolicy", ReturnCancellationPolicy);
                bcparam.put("ConvenienceFee", ReturnTotalConvienceFee);
                bcparam.put("DepartureTime", ReturndepartureTime);
                bcparam.put("DestinationId", SourceId);
                bcparam.put("DestinationName", SourceName);
                bcparam.put("DeviceModel", "android");
                bcparam.put("DeviceOS", "android");
                bcparam.put("DeviceOSVersion", "4.4.3");
                bcparam.put("DeviceToken", DeviceID);
                bcparam.put("EmailId", EMAIL);
                bcparam.put("EmergencyMobileNo", Phonenumber);
                bcparam.put("Fares", ReturnFares);
                bcparam.put("Genders", Passenger_gender);
                bcparam.put("IdCardIssuedBy", "AP");
                bcparam.put("IdCardNo", IdProofNumber);
                bcparam.put("IdCardType", IdCard);
                bcparam.put("IsAgentPaymentGateway", "0");
                bcparam.put("IsOfflineBooking", "false");
                bcparam.put("JourneyDate", Returndate);
                bcparam.put("MobileNo", Phonenumber);
                bcparam.put("Names", Passenger_Full_name);
                bcparam.put("NoofSeats", ReturnNumberOfSeats);
                bcparam.put("Operator", ReturnOperator);
                bcparam.put("PartialCancellationAllowed", ReturnPartialCancellationAllowed);
                bcparam.put("PostalCode", "500082");
                bcparam.put("Provider", ReturnProvider);
                bcparam.put("SeatNos", ReturnSelectedSeats);
                bcparam.put("ServiceCharge", ReturnOperatorserviceCharge);
                bcparam.put("Servicetax", ReturnServiceTaxes);
                bcparam.put("SourceId", DestinationId);
                bcparam.put("SourceName", DestinationName);
                bcparam.put("Titles", TotalTitles);
                bcparam.put("TripId", ReturnBusTypeID);
                bcparam.put("TripType", "2");
                bcparam.put("User", User_agentid);
                bcparam.put("UserType", UserId);
                bcparam.put("obiboProvider", ReturnObiboAPIProvidrer);
                bcparam.put("returnDate", Returndate);
                bcparam.put("Seatcodes", ReturnSeatCodes);
                bcparam.put("PromoCode", ValidPromoCode);
                //bcparam.put("PromoCodeAmount", DiscountAmount);
                // bcparam.put("IsAgentPaymentGateway", false);


                // httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");
                // parm.add(new BasicNameValuePair("res",bcparam.toString()));
                try {
                    StringEntity seEntity;
                    // String data1 = null;
                /*
				 * try { data1 = URLEncoder.encode(jsonObjectrows, "UTF-8"); }
				 * catch (UnsupportedEncodingException e2) { // TODO
				 * Auto-generated catch block e2.printStackTrace(); }
				 */
                    seEntity = new StringEntity(bcparam.toString(), "UTF_8");
                    //seEntity.setContentType("application/json");
                    httpPost.setEntity(seEntity);
                    HttpResponse httpResponse;

                    httpResponse = httpClient.execute(httpPost);
                    Log.v("jsonresponce", "" + bcparam);
                    inputStream = httpResponse.getEntity().getContent();


                } catch (Exception e) {
                    e.printStackTrace();
                }
                //json=bcparam.toString();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream));
            String line = "";
            data = "";
            try {
                while ((line = bufferedReader.readLine()) != null)
                    data += line;
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                inputStream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Log.d("TAG_LOCATIONS", bcparam.toString());

            //   Log.v("rsponse", "" + JsonWriter.formatJson(data.toString()).toString());
            System.out.println("BookingStatus" + data);
            responce = "" + bcparam;
            return data;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(data);
                jsonObject.getString("BlockingReferenceNo");
                ReturnTicketrefno = jsonObject.getString("BookingReferenceNo");
                ReturnTicketstr = jsonObject.getString("Message");
                jsonObject.getString("BookingStatus");
                String Res = jsonObject.getString("ResponseStatus");


                if (ReturnTicketstr.equals("SUCCESS") && Res.equals("200")) {

                    if (UserId.equals("4")) {



                                        agentpaymentDialog = new Dialog(PassengerDetails.this);
                                        agentpaymentDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        agentpaymentDialog.setContentView(R.layout.agent_dialog);
                                        agentpaymentDialog.setCancelable(false);


                                        wallet = (ImageView) agentpaymentDialog.findViewById(R.id.walletimg);
                                        DialogPay= (TextView)agentpaymentDialog. findViewById(R.id.pay);
                                        WalletBalance= (TextView) agentpaymentDialog. findViewById(R.id.wallet_bal);
                                        DialogAmount= (TextView) agentpaymentDialog. findViewById(R.id.AmountToPay);
                                        can_Dialog= (ImageView) agentpaymentDialog.findViewById(R.id.can_dialog);
                                        WalletBalance.setText("₹ "+AgentBalance+"");

                                        if (PROMO.equals("true")) {
                                            RoundTripFare = ReturnNetFare;
                                        } else {
                                            double GrandFare = Double.parseDouble(NetFare) + Double.parseDouble(ReturnNetFare);
                                            int roundValue = (int) Math.round(GrandFare);
                                            RoundTripFare = String.valueOf(roundValue);
                                        }
                                        DialogAmount.setText("₹ "+RoundTripFare+"");
                                        DialogPay.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialogwallet = false;



                                                    pay();

                                            }
                                        });

                                        can_Dialog.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                final Context context = PassengerDetails.this;
                                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                                                // set title
                                                alertDialogBuilder.setTitle("Alert");

                                                // set dialog message
                                                alertDialogBuilder
                                                        .setMessage("Are you sure you want to cancel this transaction?")
                                                        .setCancelable(false)
                                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                agentpaymentDialog.dismiss();
                                                                Intent i = new Intent(PassengerDetails.this, Buses_MainActivity.class);
                                                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                startActivity(i);
                                                            }
                                                            //  }
                                                        })
                                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                // if this button is clicked, just close
                                                                // the dialog box and do nothing
                                                                dialog.cancel();

                                                            }
                                                        });


                                                // create alert dialog
                                                AlertDialog alertDialog = alertDialogBuilder.create();

                                                // show it
                                                alertDialog.show();

                                            }
                                        });
                                        agentpaymentDialog.show();




                    } else {
                        final Context context = PassengerDetails.this;
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                        // set title
                        alertDialogBuilder.setTitle("Payment Confirmation ");

                        // set dialog message
                        alertDialogBuilder
                                .setMessage("Click Yes to proceed")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // if this button is clicked, close
                                        if (PROMO.equals("true")) {
                                            RoundTripFare = ReturnNetFare;
                                        } else {
                                            double GrandFare = Double.parseDouble(NetFare) + Double.parseDouble(ReturnNetFare);
                                            int roundValue = (int) Math.round(GrandFare);
                                            RoundTripFare = String.valueOf(roundValue);
                                        }

                                        Intent i = new Intent(PassengerDetails.this, Payment_Gateway_Main.class);
                                        i.putExtra("name", FullName);
                                        i.putExtra("email", String.valueOf(EMAIL));
                                        i.putExtra("phone", MOBILE);
                                        i.putExtra("amount", RoundTripFare);
                                        i.putExtra("referencenumber", refno);
                                        i.putExtra("PaymentType", "Bus");
                                        i.putExtra("ReturnTicketrReferenceNumber", ReturnTicketrefno);
                                        i.putExtra("BookingType","Bus_Booking");
                                        System.out.println("referenceNo" + ReturnTicketrefno);
                                        i.putExtra("isFromOrder", true);
                                        startActivity(i);
                                        //  }
                                    }
                                    //  }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // if this button is clicked, just close
                                        // the dialog box and do nothing
                                        dialog.cancel();
                                        Intent i = new Intent(PassengerDetails.this, Buses_MainActivity.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);
                                    }
                                });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();
                    }


                } else {

                    final Context context = PassengerDetails.this;
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                    // set title
                    alertDialogBuilder.setTitle(" ");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("" + ReturnTicketstr)
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent i = new Intent(PassengerDetails.this, SearchActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                                    startActivity(i);
                                }
                                //  }
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


    public void pay() {
        Intent ip=new Intent(PassengerDetails.this,Bus_Booking_Activity.class);

        ip.putExtra("PaymentGatewayId",Response);
        ip.putExtra("Amount",NetFare);
        ip.putExtra("ReferenceNumber",refno);
        ip.putExtra("ReturnTicketrReferenceNumber",ReturnTicketrefno);
        ip.putExtra("name",FullName);
        ip.putExtra("email",String.valueOf(EMAIL));
        ip.putExtra("phone",MOBILE);
        ip.putExtra("IsAgent","Yes");
        startActivity(ip);
    }








    @Override
    protected void onResume() {
        super.onResume();
/*        SharedPreferences prefer = getApplicationContext().getSharedPreferences("PassengerDetailsPageLogin", MODE_PRIVATE);
        prefer.edit().remove("LoginClickedOnPassengerPage").apply();

        userLogin= pref.getString("UserLOGIN", null);
        agentLogin=pref.getString("AgentLOGIN",null);


        if( userLogin!=null && userLogin.equals("Yes")){
            UserId="6";
            User_agentid=pref.getString("User_agentid", null);
        }else if(agentLogin!=null && agentLogin.equals("Yes")){
            UserId="4";
            User_agentid=pref.getString("User_agentid", null);
            PromoCodeCardView.setVisibility(View.GONE);
        }else{
            UserId="5";
            User_agentid="";
        }*/
        // register connection status listener

    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.login, menu);
        EMAIL=pref.getString("Emailid",null);
        if((EMAIL != null )){
            MenuItem item = menu.findItem(R.id.action_login);
            item.setVisible(false);
        }else{
            MenuItem item = menu.findItem(R.id.action_login);
            item.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_login) {
            Intent nextActivity = new Intent(this, LoginActivity.class);
            SharedPreferences preference = getApplicationContext().getSharedPreferences("PassengerDetailsPageLogin", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preference.edit();
            editor.putString("LoginClickedOnPassengerPage", "Yes");
            editor.apply();
            startActivity(nextActivity);
            //slide from right to left
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/
    /**
     * Callback will be triggered when there is change in
     * network connection
     */

    private String getAge(String DOB){
        int year, month, day;
        String Date = null,Month = null,Year = null;
        StringTokenizer st = null;
        if(User_Role.equals("6")) {
             st= new StringTokenizer(DOB, "-");
            Date = st.nextToken();
            Month = st.nextToken();
            Year=st.nextToken();
        }else{
            st= new StringTokenizer(DOB, "/");
            Month = st.nextToken();
            Date = st.nextToken();
            Year=st.nextToken();
        }


        year=Integer.parseInt(Year);
        month=Integer.parseInt(Month);
        day=Integer.parseInt(Date);


        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = age;

        return ageInt.toString();
    }


    public Integer RoundOff(double TotalRate){
        double dAbs = Math.abs(TotalRate);
        int amt2 = (int) dAbs;
        double result = dAbs - (double) amt2;
        int finalrate;
        if (result<0.5){
            finalrate= (int) Math.floor(TotalRate);
        }else {
            finalrate= (int) Math.ceil(TotalRate);
        }

        return finalrate;
    }


}
