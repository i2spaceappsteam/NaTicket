package com.NaTicket.n.flights;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.NaTicket.n.R;
import com.NaTicket.n.common.pojo.Currency_Utils;
import com.NaTicket.n.common.pojo.GetPromoCodesDTO;
import com.NaTicket.n.flights.adpaters.Passenger_Adapter;
import com.NaTicket.n.flights.pojo.DomesticOnwardFlightDTO;
import com.NaTicket.n.flights.pojo.DomesticReturnFlightDTO;
import com.NaTicket.n.flights.pojo.Flight_Utils;
import com.NaTicket.n.flights.pojo.GSTDetailsDTO;
import com.NaTicket.n.flights.pojo.InternationalFlightsDTO;
import com.NaTicket.n.flights.pojo.Passenger_Info_DTO;
import com.NaTicket.n.flights.pojo.SelectedFlightDetailsDTO;
import com.NaTicket.n.loginpackage.pojo.Country_Codes_DTO;
import com.NaTicket.n.loginpackage.pojo.Login_utils;
import com.NaTicket.n.paymnet_gate_ways.Payment_Gateway_Main;
import com.NaTicket.n.serviceclasses.ServiceClasses;
import com.NaTicket.n.utils.BackActivity;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Nagarjuna on 1/2/2018.
 */

public class Flight_Passenger_Information extends BackActivity implements View.OnClickListener {

    SelectedFlightDetailsDTO selDetails;
    DomesticOnwardFlightDTO Domestic_Onward;
    DomesticReturnFlightDTO Domestic_Return;
    InternationalFlightsDTO International;
    String UserId, User_agentid, UserType;
    Currency_Utils currency_utils;
    Login_utils login_utils;
    double curr_value;
    String CurrencySymbol, CurrencyID;
    RecyclerView Pax_Info_view;
    Passenger_Adapter madapter;
    private ArrayList<String> PassengersArrayList = new ArrayList<>();
    TextView Continue_to_Pay;
    ArrayList<Passenger_Info_DTO> passenegrs = new ArrayList<>();
    Passenger_Info_DTO passenger_info;
    ListView list;
    private MyAppAdapter myAppAdapter;
    int cur = 0;
    DatePickerDialog datePickerDialog;
    static final int DATE_DIALOG_ID = 1;
    static final int DATE_DIALOG_ID2 = 2;
    static final int DATE_DIALOG_ID3 = 3;
    Calendar calendar;
    int Year, Month, Day;
    private ArrayList<String> PassengerFullNameArray = new ArrayList<>();
    private ArrayList<String> genderType = new ArrayList<>();
    private ArrayList<String> DOB = new ArrayList<>();
    private ArrayList<String> Ages = new ArrayList<>();
    private ArrayList<String> PassportDetailsArrayList = new ArrayList<>();
    EditText Address_et, City_et, Mobile_et, Email_et, Pincode_et, State_et;
    ProgressDialog mProgressDialog;
    String GENDER, DATEOFBIRTH, TotalAges, PassengerFullDetails, PassportFinalDetails;
    String DeviceToken;
    String AgentBalance, Refernce_Num;
    double Total_Fares;
    GetPromoCodesDTO[] promoCodesDTOs;
    LinearLayout Promo_layout, Promo_Card_layout, Promo_Amount_layout;
    String PromoCode, PromoAmount, TotalAmount;
    TextView HaveAPromo, Promo_Apply, Promo_Amount, TotalTv, FaresTv;
    EditText Promo_Edit;
    ArrayList<GetPromoCodesDTO> ValidPromoCode = new ArrayList<>();
    CheckBox checkboxAgree;

    TextView ADD_GST_DETAILS;
    private PopupWindow mPopupWindow;
    RelativeLayout parentLayout;
    EditText GST_Company_name, GST_Number, GST_Contact_Num, GST_Company_Email, GST_Company_Address;
    TextView GST_Submit;

    Country_Codes_DTO[] country_dail_code;
    TextView dialing_code;
    GSTDetailsDTO gstDetailsDTO;
    boolean isGSTOpened=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_passenger_info);
        inittoolbar();
        TextView toolbartitle = findViewById(R.id.toolbartitle);
        toolbartitle.setText("Passenger Information");

        login_utils = new Login_utils(this);
        currency_utils = new Currency_Utils(this);
        curr_value = Double.parseDouble(currency_utils.getCurrencyValue("Currency_Value"));
        CurrencySymbol = currency_utils.getCurrencyValue("Currency_Symbol");
        CurrencyID = currency_utils.getCurrencyValue("Currency");
        DeviceToken = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        gstDetailsDTO = new GSTDetailsDTO();
        initviews();

        Total_Fares = Double.parseDouble(getIntent().getStringExtra("TotalFare"));
        selDetails = (SelectedFlightDetailsDTO) getIntent().getSerializableExtra("selDetails");
        if (selDetails.getFlight_Type().equals("1")) {
            Domestic_Onward = (DomesticOnwardFlightDTO) getIntent().getSerializableExtra("SelDOM_Onward");
            if (selDetails.getTrip_Type().equals("2")) {
                Domestic_Return = (DomesticReturnFlightDTO) getIntent().getSerializableExtra("SelDOM_Return");
            }
        } else {
            International = (InternationalFlightsDTO) getIntent().getSerializableExtra("SelFlight");
        }

        setPaxdata();
        callPromoCodes();
        getLoginPreferences();
    }

    public void initviews() {
        list = findViewById(R.id.listView2);
        //Pax_Info_view= (RecyclerView) findViewById(R.id.pass_info_recyclerview);
        Continue_to_Pay = findViewById(R.id.proceedTopay);
        Continue_to_Pay.setOnClickListener(this);
    }


    public void setPaxdata() {

        for (int i = 0; i < selDetails.getadult_count(); i++) {
            int lenth = i;
            lenth = lenth + 1;
            PassengersArrayList.add("Adult " + lenth);
        }
        for (int j = 0; j < selDetails.getchild_count(); j++) {
            int lenth = j;
            lenth = lenth + 1;
            PassengersArrayList.add("Child " + lenth);
        }
        for (int k = 0; k < selDetails.getinfant_count(); k++) {
            int lenth = k;
            lenth = lenth + 1;
            PassengersArrayList.add("Infant " + lenth);
        }

       /* madapter   = new Passenger_Adapter(this,PassengersArrayList,selDetails,passenegrs);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        Pax_Info_view.setLayoutManager(mLayoutManager);
        Pax_Info_view.setItemAnimator(new DefaultItemAnimator());
        Pax_Info_view.setAdapter(madapter);*/


        myAppAdapter = new MyAppAdapter(PassengersArrayList, Flight_Passenger_Information.this);
        list.setAdapter(myAppAdapter);


        View footerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.flights_passenger_footer, null, false);
        list.addFooterView(footerView);

        Address_et = findViewById(R.id.address);
        Mobile_et = findViewById(R.id.phone);
        Email_et = findViewById(R.id.email);
        City_et = findViewById(R.id.city);
        State_et = findViewById(R.id.state);
        Pincode_et = findViewById(R.id.postalcode);
        checkboxAgree = findViewById(R.id.checkboxAgree);
        Promo_Card_layout = findViewById(R.id.promo_layout);
        Promo_layout = findViewById(R.id.promo_code_layout);
        HaveAPromo = findViewById(R.id.have_a_code);
        Promo_Edit = findViewById(R.id.promocode_et);
        Promo_Apply = findViewById(R.id.promo_apply);
        Promo_Amount_layout = findViewById(R.id.promo_amount_layout);
        Promo_Amount = findViewById(R.id.discount_tv);
        FaresTv = findViewById(R.id.fare_tv);
        TotalTv = findViewById(R.id.TotalTv);
        ADD_GST_DETAILS = findViewById(R.id.add_gst_Details);
        parentLayout = findViewById(R.id.parentLayout);
        ADD_GST_DETAILS.setOnClickListener(this);
        TotalAmount = String.valueOf(Total_Fares);
        dialing_code = findViewById(R.id.dail_code);
        dialing_code.setOnClickListener(this);
        Promo_Card_layout.setOnClickListener(this);
        Promo_Apply.setOnClickListener(this);
        country_dail_code = Util.getdailingcodes_array(login_utils.getDailindcodesresponse(Constants.DAILING_CODES));

    }

    private void getLoginPreferences() {
        if (login_utils.getUserDetails(Constants.USERTYPE).equals("6")) {
            UserId = "6";
            UserType = "User";
            User_agentid = login_utils.getUserDetails(Constants.USERID);
            Email_et.setText(login_utils.getUserDetails(Constants.USEREMAIL));
            Mobile_et.setText(login_utils.getUserDetails(Constants.USERPHONE));
        } else if (login_utils.getUserDetails(Constants.USERTYPE).equals("4")) {
            UserId = "4";
            UserType = "Agent";
            User_agentid = login_utils.getUserDetails(Constants.USERID);
            Email_et.setText(login_utils.getUserDetails(Constants.USEREMAIL));
            Mobile_et.setText(login_utils.getUserDetails(Constants.USERPHONE));
            AgentBalance = login_utils.getUserDetails(Constants.BALANCE);
            Promo_Card_layout.setVisibility(View.GONE);
        } else {
            UserId = "5";
            UserType = "Guest";
            User_agentid = null;
        }
    }

    private void callPromoCodes() {
        if (Util.isNetworkAvailable(getApplicationContext())) {
            ServiceClasses.getPromocodes(Flight_Passenger_Information.this, Constants.GETPROMOCODES);
        } else {
            Util.showMessage(this, Constants.NO_INT_MSG);
        }
    }

    public void getPromoCodesResponse(String response) {
        System.out.println("Offers---" + response);
        //Util.showMessage(this,response);
        if (response != null) {
            InputStream stream = new ByteArrayInputStream(response.getBytes());
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(stream);
            GetPromoCodesDTO[] getPromoCodesDTOs = gson.fromJson(reader, GetPromoCodesDTO[].class);
            if (getPromoCodesDTOs != null) {
                promoCodesDTOs = getPromoCodesDTOs;
            }
        }
    }

    private boolean validatePromoCode() {
        if (!Util.checkField(Promo_Edit)) {
            Util.showMessage(Flight_Passenger_Information.this, "Enter Promo code");
            return false;
        } else {
            return true;
        }
    }

    public void callValidatePromo() {
        for (int i = 0; i < promoCodesDTOs.length; i++) {
            if (selDetails.getFlight_Type().equals("1")) {
                if (Promo_Edit.getText().toString().equals(promoCodesDTOs[i].getCode()) && promoCodesDTOs[i].getServiceType() == 3) {
                    ValidPromoCode.add(promoCodesDTOs[i]);
                }
            } else {
                if (Promo_Edit.getText().toString().equals(promoCodesDTOs[i].getCode()) && promoCodesDTOs[i].getServiceType() == 4) {
                    ValidPromoCode.add(promoCodesDTOs[i]);
                }
            }
        }

        if (ValidPromoCode.size() == 0) {
            showAlertDialog("Invalid PromoCode !");
        } else {
            String ValidFrom = ValidPromoCode.get(0).getValidFrom();
            String ValidTill = ValidPromoCode.get(0).getValidTill();
            double FromAmount = Double.parseDouble(String.valueOf(ValidPromoCode.get(0).getFromAmount()));
            double ToAmount = Double.parseDouble(String.valueOf(ValidPromoCode.get(0).getToAmount()));
            SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            String CurrentDate = output.format((c.getTime()));
            try {
                Date PromoCodeValidFromDate = output.parse(ValidFrom);
                Date PromoCodeValidTillDate = output.parse(ValidTill);
                Date todayDate = output.parse(CurrentDate);

                if (PromoCodeValidTillDate.after(todayDate) || todayDate.equals(PromoCodeValidTillDate) && PromoCodeValidFromDate.before(todayDate) || todayDate.equals(PromoCodeValidFromDate)) {
                    if (Total_Fares >= FromAmount && Total_Fares <= ToAmount) {
                        int DiscountType = ValidPromoCode.get(0).getDiscountType();
                        double Discount = Double.parseDouble(String.valueOf(ValidPromoCode.get(0).getDiscount()));
                        PromoCode = ValidPromoCode.get(0).getCode();
                        if (DiscountType == 0) {
                            double DiscountFare = (Total_Fares / 100.0f) * Discount;
                            double TotalFare = Total_Fares - DiscountFare;
                            TotalAmount = String.valueOf(TotalFare);
                            Promo_Card_layout.setVisibility(View.GONE);
                            Promo_Amount_layout.setVisibility(View.VISIBLE);
                            Promo_Amount.setText(CurrencySymbol + Util.getprice(DiscountFare * curr_value) + "");
                            FaresTv.setText(CurrencySymbol + Util.getprice(Total_Fares * curr_value) + "");
                            TotalTv.setText(CurrencySymbol + Util.getprice(TotalFare * curr_value) + "");
                        } else {
                            double TotalFare = Total_Fares - Discount;
                            TotalAmount = String.valueOf(TotalFare);
                            Promo_Card_layout.setVisibility(View.GONE);
                            Promo_Amount_layout.setVisibility(View.VISIBLE);
                            Promo_Amount.setText(CurrencySymbol + Util.getprice(Discount * curr_value) + "");
                            FaresTv.setText(CurrencySymbol + Util.getprice(Total_Fares * curr_value) + "");
                            TotalTv.setText(CurrencySymbol + Util.getprice(TotalFare * curr_value) + "");
                        }
                    } else {
                        int From = (int) (FromAmount * curr_value);
                        int To = (int) (ToAmount * curr_value);
                        showAlertDialog("Promo Code is Valid only when \nTotal Amount is in between\n" + CurrencySymbol + " " + From
                                + " and " + CurrencySymbol + " " + To);
                    }
                } else {
                    showAlertDialog("Promocode Expired !");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void showAlertDialog(String s) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage(s);
        alertDialog.setCancelable(false);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.proceedTopay:
                if (!checkboxAgree.isChecked()) {
                    Util.showMessage(Flight_Passenger_Information.this, "Please Agree Terms & Conditions");
                } else if (validateValues()) {
                    PassengerFullDetails = TextUtils.join("~", PassengersArrayList);
                    PassportFinalDetails = TextUtils.join("~", PassportDetailsArrayList);
                    GENDER = TextUtils.join("~", genderType);
                    DATEOFBIRTH = TextUtils.join("~", DOB);
                    TotalAges = TextUtils.join("~", Ages);
                    callBlockFlight();
                }
                break;
            case R.id.promo_layout:
                Promo_layout.setVisibility(View.VISIBLE);
                break;
            case R.id.promo_apply:
                if (!validatePromoCode()) {

                } else {
                    callValidatePromo();
                }
                break;
            case R.id.add_gst_Details:
                isGSTOpened=true;
                showGSTDETAILS("GST Details", gstDetailsDTO);
                break;
            case R.id.dail_code:
                showBottomSheet(Util.getdialingcodesresponse(login_utils.getDailindcodesresponse(Constants.DAILING_CODES)), "Dialing code", dialing_code);
                break;
        }
    }


    private void callBlockFlight() {
        if (Util.isNetworkAvailable(getApplicationContext())) {
            showProgressDialog("Please Wait...", this);
            ServiceClasses.postBlockFlight(Flight_Passenger_Information.this, Constants.BlockFlightTicket, preparePayload());
        } else {
            Util.showMessage(Flight_Passenger_Information.this, Constants.NO_INT_MSG);
        }

    }


    private String preparePayload() {
        JSONObject jsonBody = new JSONObject();

        try {
            jsonBody.put("BookingResponseXML", "");
            jsonBody.put("IsOfflineBooking", "false");
            jsonBody.put("BookingRefNo", "");
            jsonBody.put("BookingStatus", 0);
            jsonBody.put("APIRefNo", "");
            jsonBody.put("AffiliateId", "");
            jsonBody.put("PaymentId", "");
            jsonBody.put("Names", PassengerFullDetails);
            jsonBody.put("ages", TotalAges);
            jsonBody.put("Genders", GENDER);
            jsonBody.put("telePhone", Mobile_et.getText().toString());
            jsonBody.put("MobileNo", Mobile_et.getText().toString());
            jsonBody.put("EmailId", Email_et.getText().toString());
            jsonBody.put("dob", DATEOFBIRTH);
            jsonBody.put("psgrtype", "");
            jsonBody.put("Address", Address_et.getText().toString());
            jsonBody.put("State", State_et.getText().toString());
            jsonBody.put("City", City_et.getText().toString());
            jsonBody.put("PostalCode", Pincode_et.getText().toString());
            jsonBody.put("PassportDetails", PassportFinalDetails);
            jsonBody.put("SMSUsageCount", "0");
            jsonBody.put("PromoCode", PromoCode);
            jsonBody.put("PromoCodeAmount", PromoAmount);
            jsonBody.put("DeviceOS", Constants.DeviceOs);
            jsonBody.put("DeviceOSVersion", Constants.DeviceOsversion);
            jsonBody.put("DeviceModel", Constants.DeviceModel);
            jsonBody.put("DeviceToken", DeviceToken);
            jsonBody.put("ApplicationType", "2");
            jsonBody.put("DeviceType", "2");
            jsonBody.put("CurrencyID", CurrencyID);
            jsonBody.put("CurrencyValue", curr_value);
            jsonBody.put("User", User_agentid);
            jsonBody.put("UserType", UserId);
            jsonBody.put("AdultPax", selDetails.getadult_count());
            jsonBody.put("ChildPax", selDetails.getchild_count());
            jsonBody.put("InfantPax", selDetails.getinfant_count());
            jsonBody.put("TravelClass", selDetails.getClass_type_value());
            jsonBody.put("IsGDS", "");
            jsonBody.put("AirlineName", "");
            jsonBody.put("GSTDetails", prepareGSTDetails());

            if (selDetails.getFlight_Type().equals("1")) {
                if (selDetails.getTrip_Type().equals("1")) {

                    JSONArray array = new JSONArray();
                    Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                    try {
                        for (int i = 0; i < Domestic_Onward.getFlightSegments().size(); i++) {
                            array.put(new JSONObject(gson.toJson(Domestic_Onward.getFlightSegments().get(i))));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    jsonBody.put("Provider", Domestic_Onward.getProvider());
                    jsonBody.put("JourneyDate", selDetails.getOnward_date());
                    jsonBody.put("ReturnDate", "");
                    jsonBody.put("TripType", 1);
                    jsonBody.put("FlightType", selDetails.getFlight_Type());
                    jsonBody.put("ImagePath", Domestic_Onward.getFlightSegments().get(0).getImageFileName());
                    jsonBody.put("ImagePathRet", "");
                    jsonBody.put("Rule", Domestic_Onward.getFlightSegments().get(0).getBookingClassFare().getRule());
                    jsonBody.put("key", Domestic_Onward.getOriginDestinationoptionId().getKey());
                    jsonBody.put("RuleRet", "");
                    jsonBody.put("keyRet", "");
                    jsonBody.put("FlightId", "");
                    jsonBody.put("FlightIdRet", "");
                    jsonBody.put("OnwardFlightSegments", array);
                    jsonBody.put("FareDetails", new JSONObject(gson.toJson(Domestic_Onward.getFareDetails())));
                    jsonBody.put("BookingDate", "0001-01-01T00:00:00");
                    jsonBody.put("PostMarkup", "");
                    jsonBody.put("OcTax", Domestic_Onward.getFlightSegments().get(0).getOcTax());
                    jsonBody.put("ActualBaseFare", Domestic_Onward.getFareDetails().getChargeableFares().getActualBaseFare());
                    jsonBody.put("Tax", Domestic_Onward.getFareDetails().getChargeableFares().getTax());
                    jsonBody.put("STax", Domestic_Onward.getFareDetails().getChargeableFares().getSTax());
                    jsonBody.put("SCharge", Domestic_Onward.getFareDetails().getChargeableFares().getSCharge());
                    jsonBody.put("TDiscount", Domestic_Onward.getFareDetails().getChargeableFares().getTDiscount());
                    jsonBody.put("TPartnerCommission", Domestic_Onward.getFareDetails().getChargeableFares().getTPartnerCommission());
                    jsonBody.put("TCharge", Domestic_Onward.getFareDetails().getNonchargeableFares().getTCharge());
                    jsonBody.put("TMarkup", Domestic_Onward.getFareDetails().getNonchargeableFares().getTMarkup());
                    jsonBody.put("TSdiscount", Domestic_Onward.getFareDetails().getNonchargeableFares().getTSdiscount());
                    jsonBody.put("Conveniencefee", Domestic_Onward.getFareDetails().getChargeableFares().getConveniencefee());
                    jsonBody.put("ConveniencefeeType", Domestic_Onward.getFareDetails().getChargeableFares().getConveniencefeeType());
                    jsonBody.put("ConveniencefeeRet", 0);
                    jsonBody.put("TransactionId", "0");
                    jsonBody.put("ActualBaseFareRet", 0);
                    jsonBody.put("TaxRet", 0);
                    jsonBody.put("STaxRet", 0);
                    jsonBody.put("SChargeRet", 0);
                    jsonBody.put("TDiscountRet", 0);
                    jsonBody.put("TSDiscountRet", 0);
                    jsonBody.put("TPartnerCommissionRet", 0);
                    jsonBody.put("TChargeRet", 0);
                    jsonBody.put("TMarkupRet", 0);
                    jsonBody.put("Source", selDetails.getFrom_City_ID());
                    jsonBody.put("SourceName", selDetails.getFrom_City_Full_Name());
                    jsonBody.put("Destination", selDetails.getTo_City_ID());
                    jsonBody.put("DestinationName", selDetails.getTo_City_Full_Name());
                    jsonBody.put("IsNonStopFlight", "false");
                    jsonBody.put("FlightTimings", "");
                    jsonBody.put("IsAgentPaymentGateway", "false");
                    jsonBody.put("IsBlockTicket", "false");
                    jsonBody.put("OcTaxRet", 0);
                    jsonBody.put("IsGDS", "");
                    jsonBody.put("AirlineName", "");
                    jsonBody.put("IsLCC", Domestic_Onward.getIsLCC());

                } else {

                    JSONArray onward_array = new JSONArray();
                    JSONArray ret_array = new JSONArray();
                    Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                    try {
                        for (int i = 0; i < Domestic_Onward.getFlightSegments().size(); i++) {
                            onward_array.put(new JSONObject(gson.toJson(Domestic_Onward.getFlightSegments().get(i))));
                        }

                        for (int i = 0; i < Domestic_Return.getFlightSegments().size(); i++) {
                            ret_array.put(new JSONObject(gson.toJson(Domestic_Return.getFlightSegments().get(i))));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    jsonBody.put("JourneyDate", selDetails.getOnward_date());
                    jsonBody.put("ReturnDate", selDetails.getReturn_date());
                    jsonBody.put("TripType", 2);
                    jsonBody.put("FlightType", selDetails.getFlight_Type());
                    jsonBody.put("ImagePath", Domestic_Onward.getFlightSegments().get(0).getImageFileName());
                    jsonBody.put("ImagePathRet", "");
                    jsonBody.put("Rule", Domestic_Onward.getFlightSegments().get(0).getBookingClassFare().getRule());
                    jsonBody.put("key", Domestic_Onward.getOriginDestinationoptionId().getKey());
                    jsonBody.put("RuleRet", Domestic_Return.getFlightSegments().get(0).getBookingClassFare().getRule());
                    jsonBody.put("keyRet", Domestic_Return.getOriginDestinationoptionId().getKey());
                    jsonBody.put("FlightId", "");
                    jsonBody.put("FlightIdRet", "");
                    jsonBody.put("OnwardFlightSegments", onward_array);
                    jsonBody.put("ReturnFlightSegments", ret_array);
                    jsonBody.put("FareDetails", new JSONObject(gson.toJson(Domestic_Onward.getFareDetails())));
                    jsonBody.put("BookingDate", "0001-01-01T00:00:00");
                    jsonBody.put("PostMarkup", "");
                    jsonBody.put("OcTax", Domestic_Onward.getFlightSegments().get(0).getOcTax());
                    jsonBody.put("ActualBaseFare", Domestic_Onward.getFareDetails().getChargeableFares().getActualBaseFare());
                    jsonBody.put("Tax", Domestic_Onward.getFareDetails().getChargeableFares().getTax());
                    jsonBody.put("STax", Domestic_Onward.getFareDetails().getChargeableFares().getSTax());
                    jsonBody.put("SCharge", Domestic_Onward.getFareDetails().getChargeableFares().getSCharge());
                    jsonBody.put("TDiscount", Domestic_Onward.getFareDetails().getChargeableFares().getTDiscount());
                    jsonBody.put("TPartnerCommission", Domestic_Onward.getFareDetails().getChargeableFares().getTPartnerCommission());
                    jsonBody.put("TCharge", Domestic_Onward.getFareDetails().getNonchargeableFares().getTCharge());
                    jsonBody.put("TMarkup", Domestic_Onward.getFareDetails().getNonchargeableFares().getTCharge());
                    jsonBody.put("TSdiscount", Domestic_Onward.getFareDetails().getNonchargeableFares().getTSdiscount());
                    jsonBody.put("Conveniencefee", Domestic_Onward.getFareDetails().getChargeableFares().getConveniencefee());
                    jsonBody.put("ConveniencefeeType", Domestic_Onward.getFareDetails().getChargeableFares().getConveniencefeeType());
                    jsonBody.put("ConveniencefeeRet", Domestic_Return.getFareDetails().getChargeableFares().getConveniencefee());
                    jsonBody.put("ConveniencefeeType", Domestic_Return.getFareDetails().getChargeableFares().getConveniencefeeType());
                    jsonBody.put("TransactionId", "0");
                    jsonBody.put("ActualBaseFareRet", Domestic_Return.getFareDetails().getChargeableFares().getActualBaseFare());
                    jsonBody.put("TaxRet", Domestic_Return.getFareDetails().getChargeableFares().getTax());
                    jsonBody.put("STaxRet", Domestic_Return.getFareDetails().getChargeableFares().getSTax());
                    jsonBody.put("SChargeRet", Domestic_Return.getFareDetails().getChargeableFares().getSCharge());
                    jsonBody.put("TDiscountRet", Domestic_Return.getFareDetails().getChargeableFares().getTDiscount());
                    jsonBody.put("TSDiscountRet", Domestic_Return.getFareDetails().getNonchargeableFares().getTSdiscount());
                    jsonBody.put("TPartnerCommissionRet", Domestic_Return.getFareDetails().getChargeableFares().getTPartnerCommission());
                    jsonBody.put("TChargeRet", Domestic_Return.getFareDetails().getNonchargeableFares().getTCharge());
                    jsonBody.put("TMarkupRet", Domestic_Return.getFareDetails().getNonchargeableFares().getTCharge());
                    jsonBody.put("Source", selDetails.getFrom_City_ID());
                    jsonBody.put("SourceName", selDetails.getFrom_City_Full_Name());
                    jsonBody.put("Destination", selDetails.getTo_City_ID());
                    jsonBody.put("DestinationName", selDetails.getTo_City_Full_Name());
                    jsonBody.put("OcTaxRet", 0);
                    jsonBody.put("IsLCC", Domestic_Return.getIsLCC());
                    jsonBody.put("Provider", Domestic_Return.getProvider());
                }
            } else {
                if (selDetails.getTrip_Type().equals("1")) {
                    JSONArray array = new JSONArray();
                    Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                    try {
                        for (int i = 0; i < International.getIntOnward().getFlightSegments().size(); i++) {
                            array.put(new JSONObject(gson.toJson(International.getIntOnward().getFlightSegments().get(i))));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    jsonBody.put("JourneyDate", selDetails.getOnward_date());
                    jsonBody.put("ReturnDate", "");
                    jsonBody.put("TripType", 1);
                    jsonBody.put("FlightType", selDetails.getFlight_Type());
                    jsonBody.put("ImagePath", International.getIntOnward().getFlightSegments().get(0).getImageFileName());
                    jsonBody.put("ImagePathRet", "");
                    jsonBody.put("Rule", International.getIntOnward().getFlightSegments().get(0).getBookingClassFare().getRule());
                    jsonBody.put("key", International.getOriginDestinationoptionId().getKey());
                    jsonBody.put("RuleRet", "");
                    jsonBody.put("keyRet", "");
                    jsonBody.put("FlightId", "");
                    jsonBody.put("FlightIdRet", "");
                    jsonBody.put("OnwardFlightSegments", array);
                    jsonBody.put("FareDetails", new JSONObject(gson.toJson(International.getFareDetails())));
                    jsonBody.put("BookingDate", "0001-01-01T00:00:00");
                    jsonBody.put("PostMarkup", "");
                    jsonBody.put("OcTax", International.getIntOnward().getFlightSegments().get(0).getOcTax());
                    jsonBody.put("ActualBaseFare", International.getFareDetails().getChargeableFares().getActualBaseFare());
                    jsonBody.put("Tax", International.getFareDetails().getChargeableFares().getTax());
                    jsonBody.put("STax", International.getFareDetails().getChargeableFares().getSTax());
                    jsonBody.put("SCharge", International.getFareDetails().getChargeableFares().getSCharge());
                    jsonBody.put("TDiscount", International.getFareDetails().getChargeableFares().getTDiscount());
                    jsonBody.put("TPartnerCommission", International.getFareDetails().getChargeableFares().getTPartnerCommission());
                    jsonBody.put("TCharge", International.getFareDetails().getNonchargeableFares().getTCharge());
                    jsonBody.put("TMarkup", International.getFareDetails().getNonchargeableFares().getTMarkup());
                    jsonBody.put("TSdiscount", International.getFareDetails().getNonchargeableFares().getTSdiscount());
                    jsonBody.put("Conveniencefee", International.getFareDetails().getChargeableFares().getConveniencefee());
                    jsonBody.put("ConveniencefeeType", International.getFareDetails().getChargeableFares().getConveniencefeeType());
                    jsonBody.put("ConveniencefeeRet", 0);
                    jsonBody.put("TransactionId", "0");
                    jsonBody.put("ActualBaseFareRet", 0);
                    jsonBody.put("TaxRet", 0);
                    jsonBody.put("STaxRet", 0);
                    jsonBody.put("SChargeRet", 0);
                    jsonBody.put("TDiscountRet", 0);
                    jsonBody.put("TSDiscountRet", 0);
                    jsonBody.put("TPartnerCommissionRet", 0);
                    jsonBody.put("TChargeRet", 0);
                    jsonBody.put("TMarkupRet", 0);
                    jsonBody.put("Source", selDetails.getFrom_City_ID());
                    jsonBody.put("SourceName", selDetails.getFrom_City_Full_Name());
                    jsonBody.put("Destination", selDetails.getTo_City_ID());
                    jsonBody.put("DestinationName", selDetails.getTo_City_Full_Name());
                    jsonBody.put("IsNonStopFlight", "false");
                    jsonBody.put("FlightTimings", "");
                    jsonBody.put("IsAgentPaymentGateway", "false");
                    jsonBody.put("IsBlockTicket", "false");
                    jsonBody.put("OcTaxRet", 0);
                    jsonBody.put("IsGDS", "");
                    jsonBody.put("AirlineName", "");
                    jsonBody.put("IsLCC", International.getIsLCC());
                    jsonBody.put("Provider", International.getProvider());
                } else {
                    JSONArray onward_array = new JSONArray();
                    JSONArray ret_array = new JSONArray();
                    Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                    try {
                        for (int i = 0; i < International.getIntOnward().getFlightSegments().size(); i++) {
                            onward_array.put(new JSONObject(gson.toJson(International.getIntOnward().getFlightSegments().get(i))));
                        }

                        for (int i = 0; i < International.getIntReturn().getFlightSegments().size(); i++) {
                            ret_array.put(new JSONObject(gson.toJson(International.getIntReturn().getFlightSegments().get(i))));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    jsonBody.put("JourneyDate", selDetails.getOnward_date());
                    jsonBody.put("ReturnDate", selDetails.getReturn_date());
                    jsonBody.put("TripType", 2);
                    jsonBody.put("FlightType", selDetails.getFlight_Type());
                    jsonBody.put("ImagePath", International.getIntOnward().getFlightSegments().get(0).getImageFileName());
                    jsonBody.put("ImagePathRet", "");
                    jsonBody.put("Rule", International.getIntOnward().getFlightSegments().get(0).getBookingClassFare().getRule());
                    jsonBody.put("key", International.getOriginDestinationoptionId().getKey());
                    jsonBody.put("RuleRet", International.getIntReturn().getFlightSegments().get(0).getBookingClassFare().getRule());
                    jsonBody.put("keyRet", International.getOriginDestinationoptionId().getKey());
                    jsonBody.put("FlightId", "");
                    jsonBody.put("FlightIdRet", "");
                    jsonBody.put("OnwardFlightSegments", onward_array);
                    jsonBody.put("ReturnFlightSegments", ret_array);
                    jsonBody.put("FareDetails", new JSONObject(gson.toJson(International.getFareDetails())));
                    jsonBody.put("BookingDate", "0001-01-01T00:00:00");
                    jsonBody.put("PostMarkup", "");
                    jsonBody.put("OcTax", International.getIntOnward().getFlightSegments().get(0).getOcTax());
                    jsonBody.put("ActualBaseFare", International.getFareDetails().getChargeableFares().getActualBaseFare());
                    jsonBody.put("Tax", International.getFareDetails().getChargeableFares().getTax());
                    jsonBody.put("STax", International.getFareDetails().getChargeableFares().getSTax());
                    jsonBody.put("SCharge", International.getFareDetails().getChargeableFares().getSCharge());
                    jsonBody.put("TDiscount", International.getFareDetails().getChargeableFares().getTDiscount());
                    jsonBody.put("TPartnerCommission", International.getFareDetails().getChargeableFares().getTPartnerCommission());
                    jsonBody.put("TCharge", International.getFareDetails().getNonchargeableFares().getTCharge());
                    jsonBody.put("TMarkup", International.getFareDetails().getNonchargeableFares().getTCharge());
                    jsonBody.put("TSdiscount", International.getFareDetails().getNonchargeableFares().getTSdiscount());
                    jsonBody.put("Conveniencefee", International.getFareDetails().getChargeableFares().getConveniencefee());
                    jsonBody.put("ConveniencefeeType", International.getFareDetails().getChargeableFares().getConveniencefeeType());
                    jsonBody.put("ConveniencefeeRet", 0);
                    jsonBody.put("TransactionId", "0");
                    jsonBody.put("ActualBaseFareRet", 0);
                    jsonBody.put("TaxRet", 0);
                    jsonBody.put("STaxRet", 0);
                    jsonBody.put("SChargeRet", 0);
                    jsonBody.put("TDiscountRet", 0);
                    jsonBody.put("TSDiscountRet", 0);
                    jsonBody.put("TPartnerCommissionRet", 0);
                    jsonBody.put("TChargeRet", 0);
                    jsonBody.put("TMarkupRet", 0);
                    jsonBody.put("Source", selDetails.getFrom_City_ID());
                    jsonBody.put("SourceName", selDetails.getFrom_City_Full_Name());
                    jsonBody.put("Destination", selDetails.getTo_City_ID());
                    jsonBody.put("DestinationName", selDetails.getTo_City_Full_Name());
                    jsonBody.put("IsNonStopFlight", "false");
                    jsonBody.put("FlightTimings", "");
                    jsonBody.put("IsAgentPaymentGateway", "false");
                    jsonBody.put("IsBlockTicket", "false");
                    jsonBody.put("OcTaxRet", 0);
                    jsonBody.put("IsGDS", "");
                    jsonBody.put("AirlineName", "");
                    jsonBody.put("IsLCC", International.getIsLCC());
                    jsonBody.put("Provider", International.getProvider());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonBody.toString();
    }

    private JSONObject prepareGSTDetails() {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("GSTCompanyAddress", GST_Company_Address.getText().toString());
            jsonBody.put("GSTCompanyContactNumber", GST_Contact_Num.getText().toString());
            jsonBody.put("GSTCompanyName", GST_Company_name.getText().toString());
            jsonBody.put("GSTNumber", GST_Number.getText().toString());
            jsonBody.put("GSTCompanyEmail", GST_Company_Email.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonBody;
    }


    public void postBlockFlightResponse(String Response) {
        hideProgressDialog();
        if (Util.getBlockResponseCode(Response) == 200) {
            if (Util.getResponseMessage(Response).matches("Blocked successully")) {
                try {
                    JSONObject jsonObject = new JSONObject(Response);
                    Refernce_Num = jsonObject.getString("ReferenceNo");
                    System.out.println("Reference no Flight: " + Refernce_Num);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (UserId.equals("4")) {
                    final Dialog agentpaymentDialog = new Dialog(Flight_Passenger_Information.this);
                    agentpaymentDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    agentpaymentDialog.setContentView(R.layout.agent_dialog);
                    TextView DialogPay = agentpaymentDialog.findViewById(R.id.pay);
                    TextView WalletBalance = agentpaymentDialog.findViewById(R.id.wallet_bal);
                    TextView DialogAmount = agentpaymentDialog.findViewById(R.id.AmountToPay);
                    WalletBalance.setText(Constants.AGENT_CURRENCY_SYMBOL + AgentBalance + "");
                    DialogAmount.setText(CurrencySymbol + Util.getprice(Total_Fares * curr_value) + "");
                    ImageView can_Dialog = agentpaymentDialog.findViewById(R.id.can_dialog);

                    DialogPay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pay();
                        }
                    });

                    can_Dialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            agentpaymentDialog.dismiss();
                        }
                    });
                    agentpaymentDialog.show();
                    Window window = agentpaymentDialog.getWindow();
                    window.setLayout(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
                } else {
                    final Context context = Flight_Passenger_Information.this;
                    android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Payment Confirmation ");
                    alertDialogBuilder
                            .setMessage("Click Yes to proceed!")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //Util.showMessage(ReviewActivity.this,"You have to pay: "+TotalAmount);
                                    Intent i = new Intent(Flight_Passenger_Information.this, Payment_Gateway_Main.class);
                                    i.putExtra("name", "naticket");
                                    i.putExtra("email", Email_et.getText().toString());
                                    i.putExtra("phone", Mobile_et.getText().toString());
                                    i.putExtra("amount", TotalAmount);
                                    i.putExtra("referencenumber", Refernce_Num);
                                    i.putExtra("PaymentType", "Flight_Booking");
                                    System.out.println("referenceNo" + Refernce_Num);
                                    i.putExtra("isFromOrder", true);
                                    i.putExtra("BookingType", "Flight_Booking");
                                    startActivity(i);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    startActivity(new Intent(Flight_Passenger_Information.this, Flights_Search_Activity.class));
                                }
                            });
                    android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            } else {
                showAlertdialogforothers(Util.getResponseMessage(Response));
            }
        } else {
            Util.showMessage(this, Util.getResponseMessage(Response));
        }
    }


    public void errorResponse(String message) {
        hideProgressDialog();
        showAlertdialogforerror("Timed out");
    }


    public void pay() {
        Intent ip = new Intent(Flight_Passenger_Information.this, Flight_Booking_Activity.class);

        ip.putExtra("PaymentGatewayId", Refernce_Num);
        ip.putExtra("Amount", TotalAmount);
        ip.putExtra("referencenumber", Refernce_Num);
        ip.putExtra("name", getResources().getString(R.string.app_name));
        ip.putExtra("email", Email_et.getText().toString());
        ip.putExtra("phone", Mobile_et.getText().toString());
        ip.putExtra("IsAgent", "Yes");

        startActivity(ip);
    }


    private void showAlertdialogforerror(String s) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        //alertDialog.setTitle("Alert!");
        alertDialog.setMessage(s);
        alertDialog.setCancelable(false);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }


    private void showAlertdialogforothers(String s) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        ////alertDialog.setTitle("Alert!");
        alertDialog.setMessage(s);
        alertDialog.setCancelable(false);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent ik = new Intent(Flight_Passenger_Information.this, Flights_Search_Activity.class);
                ik.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(ik);
            }
        });
        alertDialog.show();
    }


    private boolean validateValues() {
        String psngrs = "";
        for (int i = 0; i < PassengersArrayList.size(); i++) {
            psngrs = "" + psngrs + "" + PassengersArrayList.get(i);
        }
        if (psngrs.contains("Adult")) {
            Util.showMessage(this, "Enter Adult information");
            return false;
        } else if (psngrs.contains("Child")) {
            Util.showMessage(this, "Enter Child information");
            return false;
        } else if (psngrs.contains("Infant")) {
            Util.showMessage(this, "Enter Infant information");
            return false;
        }

        if (!Util.checkField(Email_et)) {
            Util.showMessage(this, "Enter Email");
            return false;
        } else if (!Util.checkField(Mobile_et)) {
            Util.showMessage(this, "Enter Mobile Number");
            return false;
        } else if (!Util.checkField(State_et)) {
            Util.showMessage(this, "Enter State");
            return false;
        } else if (!Util.checkField(City_et)) {
            Util.showMessage(this, "Enter City");
            return false;
        } else if (!Util.checkField(Pincode_et)) {
            Util.showMessage(this, "Enter Pin Code");
            return false;
        } else if (!Util.checkField(Address_et)) {
            Util.showMessage(this, "Enter Address");
            return false;
        }

        if (selDetails.isGSTMandatory()) {
            if (isGSTOpened){
                validate_GST_Params();
            }else {
                Util.showMessage(this, "Enter GST Details");
                return false;
            }
        }

        return true;
    }

    private boolean validate_GST_Params() {
        if (!Util.checkField(GST_Company_name)) {
            Util.showMessage(this, "Enter GST Company Name");
            return false;
        } else if (!Util.checkField(GST_Number)) {
            Util.showMessage(this, "Enter GST Number");
            return false;
        } else if (!Util.checkField(GST_Company_Email)) {
            Util.showMessage(this, "Enter GST Company Email");
            return false;
        } else if (!Util.checkField(GST_Contact_Num)) {
            Util.showMessage(this, "Enter GST Contact Number");
            return false;
        } else if (!Util.checkField(GST_Company_Address)) {
            Util.showMessage(this, "Enter GST Company Address");
            return false;
        } else if (!Util.validateEmail(GST_Company_Email.getText().toString())) {
            Util.showMessage(this, "Enter valid GST Company Email");
            return false;
        } else if (!Util.validateMobileNumberIndia(GST_Contact_Num)) {
            Util.showMessage(this, "Enter valid GST Contact Number");
            return false;
        } else if (!Util.validateGSTNumber(GST_Number)) {
            Util.showMessage(this, "Enter valid GST Number");
            return false;
        }
        return true;
    }

    public void errorresponse(String message) {
    }


    public class MyAppAdapter extends BaseAdapter implements DatePickerDialog.OnDateSetListener {
        public class ViewHolder {
            TextView txtTitle;
        }

        public List<String> paxdata;
        public Context context;
        ArrayList<String> arraylist;
        TextView Dob, Issue_Date, Expiry_Date;

        private MyAppAdapter(List<String> apps, Flight_Passenger_Information airportsList) {
            this.paxdata = apps;
            arraylist = new ArrayList<>();
        }

        @Override
        public int getCount() {
            return paxdata.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View rowView = convertView;
            ViewHolder viewHolder;
            if (rowView == null) {
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.flight_pax, null);
                viewHolder = new ViewHolder();
                viewHolder.txtTitle = rowView.findViewById(R.id.AdultsCount);
                rowView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            String Data = PassengersArrayList.get(position);
            if (Data.contains("|")) {
                StringTokenizer st = new StringTokenizer(Data, "|");
                String tit = st.nextToken();
                String firstname = st.nextToken();
                String lastname = st.nextToken();
                viewHolder.txtTitle.setText("" + tit + " " + firstname + " " + lastname);
            } else {
                viewHolder.txtTitle.setText("" + Data + "");
            }
            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(Flight_Passenger_Information.this);
                    dialog.setContentView(R.layout.flights_passenger_dilog);
                    dialog.setTitle("TRAVELLER DETAILS");
                    try {
                        dialog.getWindow().setBackgroundDrawableResource(R.color.Transparent);
                    } catch (Exception e) {
                    }

                    final Spinner Surname_Spinner = dialog.findViewById(R.id.surname);
                    final Spinner Gender_Spinner = dialog.findViewById(R.id.gender);
                    final EditText FirstName = dialog.findViewById(R.id.firstName);
                    final EditText LastName = dialog.findViewById(R.id.lastName);
                    ImageView close = dialog.findViewById(R.id.closePopup);

                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    Dob = dialog.findViewById(R.id.dob_tv);

                    TextView Save_tv = dialog.findViewById(R.id.save);
                    final LinearLayout Passport_layout = dialog.findViewById(R.id.passport_details_layout);

                    final EditText Passport_Number = dialog.findViewById(R.id.pass_port_number);
                    final EditText Passport_Place = dialog.findViewById(R.id.pass_port_place);
                    Issue_Date = dialog.findViewById(R.id.passport_issue_date);
                    Expiry_Date = dialog.findViewById(R.id.passport_expiry_date);

                    LinearLayout DOB_Layout = dialog.findViewById(R.id.dob_layout);
                    LinearLayout Issue_Layout = dialog.findViewById(R.id.passport_issue_layout);
                    LinearLayout Expiry_Layout = dialog.findViewById(R.id.passport_expiry_layout);

                    if (selDetails.getFlight_Type().equals("2")) {
                        Passport_layout.setVisibility(View.VISIBLE);
                    }


                    DOB_Layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getcurrent();
                            cur = DATE_DIALOG_ID;
                            datePickerDialog = DatePickerDialog.newInstance(MyAppAdapter.this, Year, Month, Day);
                            if (paxdata.get(position).contains("Adult") || PassengersArrayList.get(position).contains("ADT")) {
                                Calendar cal1 = Calendar.getInstance();
                                cal1.add(Calendar.YEAR, -12);
                                Calendar cal2 = Calendar.getInstance();
                                cal2.add(Calendar.YEAR, -100);
                                datePickerDialog.setMinDate(cal2);
                                datePickerDialog.setMaxDate(cal1);
                            } else if (paxdata.get(position).contains("Child") || PassengersArrayList.get(position).contains("CHD")) {
                                Calendar cal1 = Calendar.getInstance();
                                cal1.add(Calendar.YEAR, -2);
                                Calendar cal2 = Calendar.getInstance();
                                cal2.add(Calendar.YEAR, -12);
                                datePickerDialog.setMinDate(cal2);
                                datePickerDialog.setMaxDate(cal1);
                            } else {
                                Calendar cal1 = Calendar.getInstance();
                                cal1.add(Calendar.YEAR, 0);
                                Calendar cal2 = Calendar.getInstance();
                                cal2.add(Calendar.YEAR, -2);
                                datePickerDialog.setMinDate(cal2);
                                datePickerDialog.setMaxDate(cal1);
                            }
                            setdatepicker(datePickerDialog, "Select Date of Birth");
                        }
                    });


                    Issue_Layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getcurrent();
                            cur = DATE_DIALOG_ID2;
                            datePickerDialog = DatePickerDialog.newInstance(MyAppAdapter.this, Year, Month, Day);
                            Calendar cal = Calendar.getInstance();
                            cal.add(Calendar.YEAR, 0);
                            datePickerDialog.setMaxDate(cal);
                            setdatepicker(datePickerDialog, "Select Issue Date");

                        }
                    });

                    Expiry_Layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getcurrent();
                            cur = DATE_DIALOG_ID3;
                            datePickerDialog = DatePickerDialog.newInstance(MyAppAdapter.this, Year, Month, Day);
                            Calendar cal = Calendar.getInstance();
                            cal.add(Calendar.YEAR, 0);
                            datePickerDialog.setMinDate(cal);
                            setdatepicker(datePickerDialog, "Select Expiry Date");

                        }
                    });


                    List<String> categories = new ArrayList<String>();
                    categories.add("Mr.");
                    categories.add("Ms.");
                    categories.add("Mrs.");

                    List<String> chdcategories = new ArrayList<String>();
                    chdcategories.add("Mr.");
                    chdcategories.add("Ms.");

                    List<String> gender_cat = new ArrayList<String>();
                    gender_cat.add("Male");
                    gender_cat.add("Female");


                    ArrayAdapter<String> childadapter = new ArrayAdapter<String>(Flight_Passenger_Information.this, android.R.layout.simple_spinner_item, chdcategories);
                    childadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Flight_Passenger_Information.this, android.R.layout.simple_spinner_item, categories);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                    ArrayAdapter<String> genderadapter = new ArrayAdapter<String>(Flight_Passenger_Information.this, android.R.layout.simple_spinner_item, gender_cat);
                    genderadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Gender_Spinner.setAdapter(genderadapter);


                    if (paxdata.get(position).contains("Adult") || PassengersArrayList.get(position).contains("ADT")) {
                        Surname_Spinner.setAdapter(dataAdapter);
                    } else {
                        Surname_Spinner.setAdapter(childadapter);
                    }

                    Save_tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!Util.checkField(FirstName) || !Util.checkField(LastName) || Dob.getText().toString().matches("Date of Birth") || Dob.getText().toString().matches("")) {
                                if (!Util.checkField(FirstName)) {
                                    Util.showMessage(Flight_Passenger_Information.this, "Fill First Name");
                                } else if (!Util.checkField(LastName)) {
                                    Util.showMessage(Flight_Passenger_Information.this, "Fill Last Name");
                                } else if (Dob.getText().toString().matches("Date of Birth") || Dob.getText().toString().matches("")) {
                                    Util.showMessage(Flight_Passenger_Information.this, "Fill Date of Birth");
                                }
                            } else if (selDetails.getFlight_Type().equals("2")) {
                                if (!Util.checkField(Passport_Number) || !Util.checkField(Passport_Place) ||
                                        Expiry_Date.getText().toString().matches("Expiry Date") || Expiry_Date.getText().toString().matches("") ||
                                        Issue_Date.getText().toString().matches("Issue Date") || Issue_Date.getText().toString().matches("")) {
                                    if (!Util.checkField(Passport_Number)) {
                                        Util.showMessage(Flight_Passenger_Information.this, "Fill PassPort Number");
                                    } else if (!Util.checkField(Passport_Place)) {
                                        Util.showMessage(Flight_Passenger_Information.this, "Fill PassPort Issued Place");
                                    } else if (Expiry_Date.getText().toString().matches("Expiry Date") || Expiry_Date.getText().toString().matches("")) {
                                        Util.showMessage(Flight_Passenger_Information.this, "Fill Expiry Date");
                                    } else if (Issue_Date.getText().toString().matches("Issue Date") || Issue_Date.getText().toString().matches("")) {
                                        Util.showMessage(Flight_Passenger_Information.this, "Fill Issued Date");
                                    }
                                } else {
                                    String passengerType;
                                    if (paxdata.get(position).contains("Adult") || PassengersArrayList.get(position).contains("ADT")) {
                                        passengerType = "ADT";
                                    } else if (paxdata.get(position).contains("Child") || PassengersArrayList.get(position).contains("CHD")) {
                                        passengerType = "CHD";
                                    } else {
                                        passengerType = "INF";
                                    }


                                    String Names = "" + Surname_Spinner.getSelectedItem() + "|" + FirstName.getText().toString() + "|" + LastName.getText().toString() + "|" + passengerType;
                                    PassengersArrayList.set(position, Names);
                                    PassengerFullNameArray.addAll(Arrays.<String>asList(Names));
                                    if (DOB.size() > position) {
                                        DOB.set(position, Dob.getText().toString());
                                    } else {
                                        DOB.addAll(Arrays.<String>asList(Dob.getText().toString()));
                                    }
                                    if (Ages.size() > position) {
                                        Ages.set(position, Flight_Utils.getAge(Dob.getText().toString()));
                                    } else {
                                        Ages.addAll(Arrays.<String>asList(Flight_Utils.getAge(Dob.getText().toString())));
                                    }
                                    String Gender;
                                    if (Gender_Spinner.getSelectedItem().toString().equals("Male")) {
                                        Gender = "M";
                                    } else {
                                        Gender = "F";
                                    }
                                    if (genderType.size() > position) {
                                        genderType.set(position, Gender);
                                    } else {
                                        genderType.addAll(Arrays.<String>asList(Gender));
                                    }
                                    if (selDetails.getFlight_Type().equals("2")) {
                                        String PassportFullDetails = "|" + Passport_Number.getText().toString() + "|" + Passport_Place.getText().toString() + "|" + Issue_Date.getText().toString() + "|" + Expiry_Date.getText().toString();
                                        if (PassportDetailsArrayList.size() > position) {
                                            PassportDetailsArrayList.set(position, PassportFullDetails);
                                        } else {
                                            PassportDetailsArrayList.addAll(Arrays.<String>asList(PassportFullDetails));
                                        }
                                    }
                                    myAppAdapter.notifyDataSetChanged();
                                    dialog.dismiss();
                                }
                            } else {
                                String passengerType;
                                if (paxdata.get(position).contains("Adult") || PassengersArrayList.get(position).contains("ADT")) {
                                    passengerType = "ADT";
                                } else if (paxdata.get(position).contains("Child") || PassengersArrayList.get(position).contains("CHD")) {
                                    passengerType = "CHD";
                                } else {
                                    passengerType = "INF";
                                }

                                String Names = "" + Surname_Spinner.getSelectedItem() + "|" + FirstName.getText().toString() + "|" + LastName.getText().toString() + "|" + passengerType;
                                PassengersArrayList.set(position, Names);
                                PassengerFullNameArray.addAll(Arrays.<String>asList(Names));
                                if (DOB.size() > position) {
                                    DOB.set(position, Dob.getText().toString());
                                } else {
                                    DOB.addAll(Arrays.<String>asList(Dob.getText().toString()));
                                }
                                if (Ages.size() > position) {
                                    Ages.set(position, Flight_Utils.getAge(Dob.getText().toString()));
                                } else {
                                    Ages.addAll(Arrays.<String>asList(Flight_Utils.getAge(Dob.getText().toString())));
                                }
                                String Gender;
                                if (Gender_Spinner.getSelectedItem().toString().equals("Male")) {
                                    Gender = "M";
                                } else {
                                    Gender = "F";
                                }
                                if (genderType.size() > position) {
                                    genderType.set(position, Gender);
                                } else {
                                    genderType.addAll(Arrays.<String>asList(Gender));
                                }
                                if (selDetails.getFlight_Type().equals("2")) {
                                    String PassportFullDetails = "|" + Passport_Number.getText().toString() + "|" + Passport_Place.getText().toString() + "|" + Issue_Date.getText().toString() + "|" + Expiry_Date.getText().toString();
                                    if (PassportDetailsArrayList.size() > position) {
                                        PassportDetailsArrayList.set(position, PassportFullDetails);
                                    } else {
                                        PassportDetailsArrayList.addAll(Arrays.<String>asList(PassportFullDetails));
                                    }
                                }
                                myAppAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        }
                    });


                    if (DOB.size() > position) {
                        Dob.setText(DOB.get(position));
                    }

                    if (PassengerFullNameArray.size() > position) {
                        StringTokenizer st = new StringTokenizer(PassengerFullNameArray.get(position), "|");
                        String tit = st.nextToken();
                        String firstname = st.nextToken();
                        String lastname = st.nextToken();

                        FirstName.setText(firstname);
                        LastName.setText(lastname);
                    }

                    if (PassportDetailsArrayList.size() > position) {
                        StringTokenizer st = new StringTokenizer(PassportDetailsArrayList.get(position), "|");
                        String Number = st.nextToken();
                        String Place = st.nextToken();
                        String Issue = st.nextToken();
                        String Expiry = st.nextToken();

                        Passport_Number.setText(Number);
                        Passport_Place.setText(Place);
                        Issue_Date.setText(Issue);
                        Expiry_Date.setText(Expiry);

                    }

                    dialog.show();
                    Window window = dialog.getWindow();
                    window.setLayout(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
                }
            });
            return rowView;
        }

        @Override
        public void onDateSet(DatePickerDialog view, int Year, int Month, int Day) {
            if (cur == DATE_DIALOG_ID) {
                Dob.setText(String.format("%02d", Day) + "-" + String.format("%02d", Month + 1) + "-" + Year);
            }
            if (cur == DATE_DIALOG_ID2) {
                Issue_Date.setText(String.format("%02d", Day) + "-" + String.format("%02d", Month + 1) + "-" + Year);
            }
            if (cur == DATE_DIALOG_ID3) {
                Expiry_Date.setText(String.format("%02d", Day) + "-" + String.format("%02d", Month + 1) + "-" + Year);
            }
        }


        private void setdatepicker(DatePickerDialog datePickerDialog, String title) {
            datePickerDialog.setThemeDark(false);
            datePickerDialog.showYearPickerFirst(false);
            datePickerDialog.setAccentColor(getResources().getColor(R.color.colorAccent));
            datePickerDialog.setTitle(title);
            datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
        }

        private void getcurrent() {
            calendar = Calendar.getInstance();
            Year = calendar.get(Calendar.YEAR);
            Month = calendar.get(Calendar.MONTH);
            Day = calendar.get(Calendar.DAY_OF_MONTH);
        }
    }


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

    @SuppressLint("SetTextI18n")
    public void showGSTDETAILS(String Title, GSTDetailsDTO gstDetailsDTO_set) {
        View parentView = getLayoutInflater().inflate(R.layout.gst_pop_up, null);
        TextView BSDTittle = parentView.findViewById(R.id.BSDTittle);
        final ImageView close_window = parentView.findViewById(R.id.closePopup);
        BSDTittle.setText(Title);
        GST_Company_name = parentView.findViewById(R.id.gst_company_name);
        GST_Number = parentView.findViewById(R.id.gst_number);
        GST_Contact_Num = parentView.findViewById(R.id.company_contact_num);
        GST_Company_Email = parentView.findViewById(R.id.gst_company_email);
        GST_Company_Address = parentView.findViewById(R.id.gst_company_address);
        GST_Submit = parentView.findViewById(R.id.submit_gst);

        if (gstDetailsDTO_set != null) {
            GST_Contact_Num.setText(gstDetailsDTO_set.getGSTCompanyContactNumber());
            GST_Number.setText(gstDetailsDTO_set.getGSTNumber());
            GST_Company_name.setText(gstDetailsDTO_set.getGSTCompanyName());
            GST_Company_Email.setText(gstDetailsDTO_set.getGSTCompanyEmail());
            GST_Company_Address.setText(gstDetailsDTO_set.getGSTCompanyAddress());
        }

        GST_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate_GST_Params()) {
                    gstDetailsDTO.setGSTCompanyName(GST_Company_name.getText().toString());
                    gstDetailsDTO.setGSTNumber(GST_Number.getText().toString());
                    gstDetailsDTO.setGSTCompanyContactNumber(GST_Contact_Num.getText().toString());
                    gstDetailsDTO.setGSTCompanyEmail(GST_Company_Email.getText().toString());
                    gstDetailsDTO.setGSTCompanyAddress(GST_Company_Address.getText().toString());
                    mPopupWindow.dismiss();
                }
            }
        });

        close_window.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow = new PopupWindow(
                parentView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        if (Build.VERSION.SDK_INT >= 21) {
            mPopupWindow.setElevation(5.0f);
        }
        mPopupWindow.setFocusable(true);
        mPopupWindow.update();
        if (!isFinishing()) {
            mPopupWindow.showAtLocation(parentLayout, Gravity.CENTER, 0, 0);
        }
    }


    @SuppressLint("SetTextI18n")
    public void showBottomSheet(ArrayList<String> content, String Title, final TextView value_toset) {
        View parentView = getLayoutInflater().inflate(R.layout.countries_list, null);
        TextView BSDTittle = parentView.findViewById(R.id.BSDTittle);
        final SearchView searchView = parentView.findViewById(R.id.searchcountryView);
        ImageView close_window = parentView.findViewById(R.id.closePopup);
        BSDTittle.setText("Select " + Title);
        ListView select_city_list = (ListView) parentView.findViewById(R.id.select_city_pilg_bs);
        final ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(Flight_Passenger_Information.this, R.layout.spinner_item, content);
        if (select_city_list != null) {
            select_city_list.setAdapter(cityAdapter);
        }
        assert select_city_list != null;

        select_city_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String visatype = (String) ((TextView) view).getText();
                value_toset.setText(Util.getcountryIndex(country_dail_code, visatype));
                mPopupWindow.dismiss();
            }
        });
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.onActionViewExpanded();
            }
        });
        searchView.setQueryHint("Search " + Title);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String txt) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean onQueryTextChange(String txt) {
                // TODO Auto-generated method stub
                cityAdapter.getFilter().filter(txt);
                return false;
            }
        });

        close_window.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow = new PopupWindow(
                parentView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        if (Build.VERSION.SDK_INT >= 21) {
            mPopupWindow.setElevation(5.0f);
        }
        mPopupWindow.setFocusable(true);
        mPopupWindow.update();
        if (!isFinishing()) {
            mPopupWindow.showAtLocation(parentLayout, Gravity.CENTER, 0, 0);
        }
    }

}
