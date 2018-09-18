package com.NaTicket.n.holidays;


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
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.NaTicket.n.R;
import com.NaTicket.n.common.pojo.Currency_Utils;
import com.NaTicket.n.common.pojo.GetPromoCodesDTO;
import com.NaTicket.n.holidays.pojo.AvailableHolidayPackagesDTO;
import com.NaTicket.n.holidays.pojo.SelectedHolidayDetailsDTO;
import com.NaTicket.n.hotels.pojo.Room;
import com.NaTicket.n.loginpackage.pojo.Country_Codes_DTO;
import com.NaTicket.n.loginpackage.pojo.Login_utils;
import com.NaTicket.n.paymnet_gate_ways.Payment_Gateway_Main;
import com.NaTicket.n.serviceclasses.ServiceClasses;
import com.NaTicket.n.utils.BackActivity;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReviewHolidayActivity extends BackActivity implements View.OnClickListener {
    ArrayList<Room> roomList;

    EditText email, phone, firstName, lastName;
    CheckBox checkboxAgree;
    AvailableHolidayPackagesDTO SelHoliday;
    Spinner surname;
    private SelectedHolidayDetailsDTO selDetails;
    private ProgressDialog mProgressDialog;
    String UserId, User_agentid, UserType;
    String Referenceno, Message, AgentBalance;
    TextView ConvincenceFee;
    LinearLayout ConvinenceLayout;
    EditText Promo_Edit;
    LinearLayout Promo_layout, Promo_Card_layout, Promo_Amount_layout;
    GetPromoCodesDTO[] promoCodesDTOs;
    ArrayList<GetPromoCodesDTO> ValidPromoCode = new ArrayList<>();
    TextView HaveAPromo, Promo_Apply, Promo_Amount, roomTotalTv;
    String PromoCode, PromoAmount;
    double total, Servicetaxes, roomscharges, confee;
    String TotalAmount, DeviceToken;
    Currency_Utils currency_utils;
    String Currency_Symbol, CurrencyID;
    double currValue;
    Login_utils login_utils;

    Country_Codes_DTO[] country_dail_code;
    TextView dialing_code;

    private PopupWindow mPopupWindow;
    LinearLayout parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_holiday);
        TextView toolbartitle = (TextView) findViewById(R.id.toolbartitle);
        inittoolbar();
        toolbartitle.setText("Booking Review");
        SelHoliday = (AvailableHolidayPackagesDTO) getIntent().getSerializableExtra("SelHoliday");
        roomList = (ArrayList<Room>) getIntent().getSerializableExtra("roomList");
        selDetails = (SelectedHolidayDetailsDTO) getIntent().getSerializableExtra("selDetails");

        currency_utils = new Currency_Utils(this);
        CurrencyID = currency_utils.getCurrencyValue("Currency");
        currValue = Double.parseDouble(currency_utils.getCurrencyValue("Currency_Value"));
        Currency_Symbol = currency_utils.getCurrencyValue("Currency_Symbol");
        DeviceToken = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        //Util.showMessage(this,selDetails.getJourneyDate()+"");
        login_utils = new Login_utils(this);
        init();
        getLoginPreferences();
        callPromoCodes();

    }

    private void init() {

        TextView hotelNameTv = findViewById(R.id.hotelNameTv);
        TextView hotelDescTv = findViewById(R.id.hotelDescTv);
        TextView roomcharges = findViewById(R.id.roomcharges);
        TextView roomTaxes = findViewById(R.id.roomTaxes);
        roomTotalTv = findViewById(R.id.roomTotalTv);
        TextView checkinTv = findViewById(R.id.checkinTv);
        TextView totalRoomTv = findViewById(R.id.totalRoomTv);
        TextView totalAdultsTv = findViewById(R.id.totalAdultsTv);
        TextView totalChildTv = findViewById(R.id.totalChildTv);
        TextView proceedTopay = findViewById(R.id.proceedTopay);
        TextView Destination = findViewById(R.id.destination);
        ConvincenceFee = findViewById(R.id.conveniencefee);
        ConvinenceLayout = (LinearLayout) findViewById(R.id.conviinence_layout);
        checkboxAgree = (CheckBox) findViewById(R.id.checkboxAgree);
        RatingBar holiday_rating = (RatingBar) findViewById(R.id.holidayRating);
        surname = (Spinner) findViewById(R.id.surname);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);

        Promo_Card_layout = findViewById(R.id.promo_layout);
        Promo_layout = findViewById(R.id.promo_code_layout);
        HaveAPromo = findViewById(R.id.have_a_code);
        Promo_Edit = findViewById(R.id.promocode_et);
        Promo_Apply = findViewById(R.id.promo_apply);
        Promo_Amount_layout = findViewById(R.id.promo_amount_layout);
        Promo_Amount = findViewById(R.id.discount_tv);

        ImageView hotelImage = findViewById(R.id.hotelImage);
        hotelNameTv.setText(SelHoliday.getName());
        hotelDescTv.setText(SelHoliday.getDestination());
        int days = SelHoliday.getDuration() + 1;
        Destination.setText(SelHoliday.getDuration() + " Nights / " + days + " Days");
        checkinTv.setText(selDetails.getJourneyDate());
        totalRoomTv.setText("Room : " + roomList.size());
        totalAdultsTv.setText("Adults : " + updateTotalNoofAdults());
        totalChildTv.setText("Child : " + updateTotalNoofChild());

        int Rating = SelHoliday.getHotelRating();

        if (Rating == 51) {
            holiday_rating.setRating(0);
        } else if (Rating == 52) {
            holiday_rating.setRating(1);
        } else if (Rating == 53) {
            holiday_rating.setRating(2);
        } else if (Rating == 54) {
            holiday_rating.setRating(3);
        } else if (Rating == 55) {
            holiday_rating.setRating(4);
        } else if (Rating == 56) {
            holiday_rating.setRating(5);
        } else {
            holiday_rating.setRating(5);
        }

        roomscharges = Double.valueOf(SelHoliday.getTotalAmount());
        Servicetaxes = Double.valueOf(SelHoliday.getServiceCharge());

        if (SelHoliday.getConvenienceFeeType() == 1) {
            confee = SelHoliday.getConvenienceFee();
        } else {
            double Total_To_Calculate_Confee = roomscharges + Servicetaxes;
            double con_fee = SelHoliday.getConvenienceFee();
            confee = (Total_To_Calculate_Confee / 100.0f) * con_fee;
        }
        total = roomscharges + confee + Servicetaxes;
        TotalAmount = String.valueOf(total);

        roomcharges.setText(Currency_Symbol + Util.getprice(roomscharges * currValue) + "");
        roomTaxes.setText(Currency_Symbol + Util.getprice(Servicetaxes * currValue) + "");
        roomTotalTv.setText(Currency_Symbol + Util.getprice(total * currValue) + "");
        ConvincenceFee.setText(Currency_Symbol + Util.getprice(confee * currValue) + "");

        Glide.with(this)
                .load(SelHoliday.getHolidayPackageImages().get(0).getImagepath())
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .into(hotelImage);
        proceedTopay.setOnClickListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("Mr.");
        categories.add("Ms.");
        categories.add("Mrs.");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ReviewHolidayActivity.this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        surname.setAdapter(dataAdapter);


        HaveAPromo.setOnClickListener(this);
        Promo_Apply.setOnClickListener(this);

        dialing_code = findViewById(R.id.dail_code);
        dialing_code.setOnClickListener(this);
        parentLayout = findViewById(R.id.parentLayout);
        country_dail_code = Util.getdailingcodes_array(login_utils.getDailindcodesresponse(Constants.DAILING_CODES));

    }


    private void getLoginPreferences() {
        if (login_utils.getUserDetails(Constants.USERTYPE).equals("6")) {
            UserId = "6";
            UserType = "User";
            User_agentid = login_utils.getUserDetails(Constants.USERID);
            email.setText(login_utils.getUserDetails(Constants.USEREMAIL));
            phone.setText(login_utils.getUserDetails(Constants.USERPHONE));
        } else if (login_utils.getUserDetails(Constants.USERTYPE).equals("4")) {
            UserId = "4";
            UserType = "Agent";
            User_agentid = login_utils.getUserDetails(Constants.USERID);
            email.setText(login_utils.getUserDetails(Constants.USEREMAIL));
            phone.setText(login_utils.getUserDetails(Constants.USERPHONE));
            AgentBalance = login_utils.getUserDetails(Constants.BALANCE);
            Promo_Card_layout.setVisibility(View.GONE);
            ConvinenceLayout.setVisibility(View.GONE);
        } else {
            UserId = "5";
            UserType = "Guest";
            User_agentid = null;
        }
    }

    public int updateTotalNoofAdults() {
        int adultCount = 0;
        for (int i = 0; i < roomList.size(); i++) {
            adultCount = adultCount + roomList.get(i).getAdults();
        }
        return adultCount;
    }

    public int updateTotalNoofChild() {
        int childCount = 0;
        for (int i = 0; i < roomList.size(); i++) {
            childCount = childCount + roomList.get(i).getChild();
        }
        return childCount;

    }


    private void callPromoCodes() {
        if (Util.isNetworkAvailable(getApplicationContext())) {
            ServiceClasses.getPromocodes(ReviewHolidayActivity.this, Constants.GETPROMOCODES);
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


    private String preparePayload() {
        JSONObject PackageImage = new JSONObject();
        try {
            PackageImage.put("Imagedesc", SelHoliday.getHolidayPackageImages().get(0).getImagedesc());
            PackageImage.put("Imagepath", SelHoliday.getHolidayPackageImages().get(0).getImagepath());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("PromoCodeAmount", 0);
            jsonBody.put("IsOfflineBooking", false);
            jsonBody.put("AdultCount", updateTotalNoofAdults());
            jsonBody.put("ChildCount", updateTotalNoofChild());
            jsonBody.put("ServiceCharge", Servicetaxes);
            jsonBody.put("TotalFare", roomscharges);
            jsonBody.put("Name", getName());
            jsonBody.put("Address", "Hyderabad");
            jsonBody.put("City", "");
            jsonBody.put("State", "");
            jsonBody.put("PostalCode", "");
            jsonBody.put("RoomType", SelHoliday.getFareDetails().get(0).getRoomCategory());
            jsonBody.put("Rooms", roomList.size());
            jsonBody.put("PackageType", SelHoliday.getPackageType());
            jsonBody.put("MobileNo", phone.getText().toString());
            jsonBody.put("EmergencyMobileNo", null);
            jsonBody.put("EmailId", email.getText().toString());
            jsonBody.put("Provider", SelHoliday.getProvider());
            jsonBody.put("Operator", null);
            jsonBody.put("CancellationPolicy", html2text(SelHoliday.getCancellationPolicy()));
            jsonBody.put("Terms", html2text(SelHoliday.getTerms()));
            jsonBody.put("PromoCode", PromoCode);
            jsonBody.put("PromoCodeAmount", PromoAmount);
            jsonBody.put("PackageImage", PackageImage);
            jsonBody.put("BookingStatus", 0);
            jsonBody.put("ConvenienceFee", confee);
            jsonBody.put("ConvenienceFeeType", SelHoliday.getConvenienceFeeType());
            jsonBody.put("CurrencyID", CurrencyID);
            jsonBody.put("CurrencyValue", currValue);
            jsonBody.put("DestinationId", selDetails.getDestinationId());
            jsonBody.put("DestinationName", SelHoliday.getDestination());
            jsonBody.put("HolidayPackageId", SelHoliday.getHolidayPackageId());
            jsonBody.put("HolidayPackageName", SelHoliday.getName());
            jsonBody.put("CategoryId", selDetails.getCategoryId());
            jsonBody.put("CategoryName", SelHoliday.getSubCategory());
            jsonBody.put("JourneyDate", selDetails.getJourneyDate());
            jsonBody.put("User", User_agentid);
            jsonBody.put("UserType", UserId);
            jsonBody.put("Duration", SelHoliday.getDuration());
            jsonBody.put("HolidayType", 0);
            jsonBody.put("DeviceOS", Constants.DeviceOs);
            jsonBody.put("DeviceOSVersion", Constants.DeviceOsversion);
            jsonBody.put("DeviceModel", Constants.DeviceModel);
            jsonBody.put("DeviceToken", DeviceToken);
            jsonBody.put("ApplicationType", "2");
            jsonBody.put("DeviceType", "2");

 /*           jsonBody.put("Itinerary",SelHoliday.getItinerary());
            jsonBody.put("Exclusions",SelHoliday.getExclusions());
            jsonBody.put("Inclusions",SelHoliday.getInclusions());
            jsonBody.put("AdditionalInfo",SelHoliday.getAdditionalInfo());
            jsonBody.put("Description",SelHoliday.getDescription());*/


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonBody.toString();
    }

    private String getName() {
        return surname.getSelectedItem() + "|" + firstName.getText().toString() + "|" + lastName.getText().toString();
    }

    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.proceedTopay:
                if (!validateForm()) {

                } else if (!checkboxAgree.isChecked()) {
                    Util.showMessage(ReviewHolidayActivity.this, "Please Agree Terms & Conditions");
                } else {
                    Double TotalFares = (Double.valueOf(SelHoliday.getTotalAmount()) + Double.valueOf(SelHoliday.getServiceCharge()) + confee);
                    TotalAmount = String.valueOf(TotalFares);
                    callBlockHoliday();
                }
                break;

            case R.id.have_a_code:
                Promo_layout.setVisibility(View.VISIBLE);
                break;
            case R.id.promo_apply:
                if (!validatePromoCode()) {

                } else {
                    callValidatePromo();
                }
                break;
            case R.id.dail_code:
                showBottomSheet(Util.getdialingcodesresponse(login_utils.getDailindcodesresponse(Constants.DAILING_CODES)), "Dialing code", dialing_code);
                break;

        }
    }

    private boolean validatePromoCode() {
        if (!Util.checkField(Promo_Edit)) {
            Util.showMessage(ReviewHolidayActivity.this, "Enter Promo code");
            return false;
        } else {
            return true;
        }
    }


    public void callValidatePromo() {
        for (int i = 0; i < promoCodesDTOs.length; i++) {
            if (Promo_Edit.getText().toString().equals(promoCodesDTOs[i].getCode()) && promoCodesDTOs[i].getServiceType() == 6) {
                ValidPromoCode.add(promoCodesDTOs[i]);
            }
        }

        if (ValidPromoCode.size() == 0) {
            showAlertDialog("Invalid PromoCode !");
        } else {
            String ValidFrom = ValidPromoCode.get(0).getValidFrom();
            String ValidTill = ValidPromoCode.get(0).getValidTill();
            double FromAmount = Double.parseDouble(ValidPromoCode.get(0).getFromAmount());
            double ToAmount = Double.parseDouble(ValidPromoCode.get(0).getToAmount());

            SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            String CurrentDate = output.format((c.getTime()));
            try {
                Date PromoCodeValidFromDate = output.parse(ValidFrom);
                Date PromoCodeValidTillDate = output.parse(ValidTill);
                Date todayDate = output.parse(CurrentDate);

                if (PromoCodeValidTillDate.after(todayDate) || todayDate.equals(PromoCodeValidTillDate) && PromoCodeValidFromDate.before(todayDate) || todayDate.equals(PromoCodeValidFromDate)) {
                    if (total >= FromAmount && total <= ToAmount) {
                        int DiscountType = ValidPromoCode.get(0).getDiscountType();
                        double Discount = Double.parseDouble(ValidPromoCode.get(0).getDiscount());
                        PromoCode = ValidPromoCode.get(0).getCode();
                        if (DiscountType == 0) {
                            double DiscountFare = (total / 100.0f) * Discount;
                            double TotalFare = total - DiscountFare;
                            TotalAmount = String.valueOf(TotalFare);
                            Promo_Card_layout.setVisibility(View.GONE);
                            Promo_Amount_layout.setVisibility(View.VISIBLE);
                            Promo_Amount.setText(Currency_Symbol + Util.getprice(DiscountFare * currValue) + "");
                            roomTotalTv.setText(Currency_Symbol + Util.getprice(TotalFare * currValue) + "");
                        } else {
                            double TotalFare = total - Discount;
                            TotalAmount = String.valueOf(TotalFare);
                            Promo_Card_layout.setVisibility(View.GONE);
                            Promo_Amount_layout.setVisibility(View.VISIBLE);
                            Promo_Amount.setText(Currency_Symbol + Util.getprice(Discount * currValue) + "");
                            roomTotalTv.setText(Currency_Symbol + Util.getprice(TotalFare * currValue) + "");
                        }
                    } else {
                        int From = (int) (FromAmount * currValue);
                        int To = (int) (ToAmount * currValue);
                        showAlertDialog("Promo Code is Valid only when \nTotal Amount is in between\n" + Currency_Symbol + " " + From
                                + " and " + Currency_Symbol + " " + To);
                    }
                } else {
                    showAlertDialog("Promocode Expired !");
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }


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
        final ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(ReviewHolidayActivity.this, R.layout.spinner_item, content);
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

    private void callBlockHoliday() {
        if (Util.isNetworkAvailable(getApplicationContext())) {
            showProgressDialog("Please Wait...", this);
            ServiceClasses.postBlockHoliday(ReviewHolidayActivity.this, Constants.BLOCKHOLIDAY, preparePayload());
        } else {
            Util.showMessage(ReviewHolidayActivity.this, Constants.NO_INT_MSG);
        }
    }

    private boolean validateForm() {
        if (!Util.checkField(firstName)) {
            Util.showMessage(ReviewHolidayActivity.this, "Please fill First Name");
            return false;
        } else if (!Util.checkField(lastName)) {
            Util.showMessage(ReviewHolidayActivity.this, "Please fill Last Name");
            return false;
        } else if (!Util.checkField(email)) {
            Util.showMessage(ReviewHolidayActivity.this, "Please fill Email");
            return false;
        } else if (!Util.checkField(phone)) {
            Util.showMessage(ReviewHolidayActivity.this, "Please fill Mobile No.");
            return false;
        } else if (!Util.validateEmail(email.getText().toString())) {
            Util.showMessage(ReviewHolidayActivity.this, "Please Enter valid Email");
            return false;
        } else if (!Util.validateMobileNumberIndia(phone)) {
            Util.showMessage(ReviewHolidayActivity.this, "Please Enter valid Mobile No.");
            return false;
        } else {
            return true;
        }

    }


    public void postBlockhotelResponse(String response) throws JSONException {
        hideProgressDialog();
        try {
            JSONObject jObject = new JSONObject(response);
            System.out.println("Holiday Block response: " + response);
            Message = jObject.getString("Message");
            if (jObject.getInt("BookingStatus") == 1) {
                Referenceno = jObject.getString("ReferenceNo");
                if (UserId.equals("4")) {
                    final Dialog agentpaymentDialog = new Dialog(ReviewHolidayActivity.this);
                    agentpaymentDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    agentpaymentDialog.setContentView(R.layout.agent_dialog);
                    TextView DialogPay = agentpaymentDialog.findViewById(R.id.pay);
                    TextView WalletBalance = agentpaymentDialog.findViewById(R.id.wallet_bal);
                    TextView DialogAmount = agentpaymentDialog.findViewById(R.id.AmountToPay);

                    WalletBalance.setText(Constants.AGENT_CURRENCY_SYMBOL + AgentBalance + "");
                    DialogAmount.setText(Currency_Symbol + Util.getprice(total * currValue) + "");
                    ImageView can_Dialog = agentpaymentDialog.findViewById(R.id.can_dialog);
                    can_Dialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            agentpaymentDialog.dismiss();
                        }
                    });

                    DialogPay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pay();
                        }
                    });
                    agentpaymentDialog.show();
                    Window window = agentpaymentDialog.getWindow();
                    window.setLayout(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
                } else {
                    final Context context = ReviewHolidayActivity.this;
                    android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Payment Confirmation ");
                    alertDialogBuilder
                            .setMessage("Click Yes to proceed!")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //Util.showMessage(ReviewHolidayActivity.this,"You have to pay: "+TotalAmount);
                                    Intent i = new Intent(ReviewHolidayActivity.this, Payment_Gateway_Main.class);
                                    i.putExtra("name", "eTravos");
                                    i.putExtra("email", email.getText().toString());
                                    i.putExtra("phone", phone.getText().toString());
                                    i.putExtra("amount", TotalAmount);
                                    i.putExtra("referencenumber", Referenceno);
                                    i.putExtra("PaymentType", "Hotel");
                                    System.out.println("referenceNo" + Referenceno);
                                    i.putExtra("isFromOrder", true);
                                    i.putExtra("BookingType", "Holidays");
                                    startActivity(i);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    Intent i = new Intent(ReviewHolidayActivity.this, HolidaySearchActivity.class);
                                    startActivity(i);
                                }
                            });
                    android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }

            } else {
                Util.alertDialogShow(this, Message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void pay() {
        Intent ip = new Intent(ReviewHolidayActivity.this, Holiday_Booking_Activity.class);
        ip.putExtra("PaymentGatewayId", Referenceno);
        ip.putExtra("Amount", TotalAmount);
        ip.putExtra("referencenumber", Referenceno);
        ip.putExtra("name", "naticket");
        ip.putExtra("email", email.getText().toString());
        ip.putExtra("phone", phone.getText().toString());
        ip.putExtra("IsAgent", "Yes");
        startActivity(ip);
    }

    private void showAlertDialog(String s) {
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
