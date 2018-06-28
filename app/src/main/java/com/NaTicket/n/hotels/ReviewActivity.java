package com.NaTicket.n.hotels;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.NaTicket.n.common.pojo.Currency_Utils;
import com.NaTicket.n.common.pojo.GetPromoCodesDTO;
import com.NaTicket.n.hotels.pojo.AvailableHotelsDTO;
import com.NaTicket.n.hotels.pojo.HotelImagesDTO;
import com.NaTicket.n.hotels.pojo.Room;
import com.NaTicket.n.hotels.pojo.RoomDetailsDTO;
import com.NaTicket.n.hotels.pojo.SelectionDetailsDTO;
import com.NaTicket.n.loginpackage.pojo.Login_utils;
import com.NaTicket.n.paymnet_gate_ways.Payment_Gateway_Main;
import com.NaTicket.n.utils.BackActivity;
import com.NaTicket.n.utils.Constants;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.NaTicket.n.R;
import com.NaTicket.n.serviceclasses.ServiceClasses;
import com.NaTicket.n.utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ReviewActivity extends BackActivity implements View.OnClickListener{
    ArrayList<Room> roomList;
    private AvailableHotelsDTO selHotel;
    private SelectionDetailsDTO selDetails;
    private HotelImagesDTO hotelImagesDTO;
    String totalNames=null,totalAges=null,totalGenders=null;
    EditText email,phone;
    CheckBox checkboxAgree;
    String UserId, User_agentid;
    Hotel_utils hotel_utils;
    LinearLayout Promo_layout,Promo_Card_layout,Promo_Amount_layout;
    String Referenceno,Message,AgentBalance,DeviceToken;
    TextView  ConvincenceFee,ExtraGuest;
    TextView HaveAPromo,Promo_Apply,Promo_Amount,roomTotalTv;
    String Adults,Children,Rooms,TotalADT,TotalCHD,ChildAges;
    EditText Promo_Edit;
    private ProgressDialog mProgressDialog;
    double  total,Servicetaxes,roomscharges,confee,extras;
    String TotalAmount;
    LinearLayout ConvinenceLayout;
    ArrayList<RoomDetailsDTO> roomDetailsDTOs=new ArrayList<>();
    GetPromoCodesDTO[] promoCodesDTOs;
    ArrayList<GetPromoCodesDTO> ValidPromoCode=new ArrayList<>();
    Currency_Utils currency_utils;
    String PromoCode,PromoAmount;
    String Currency_Symbol,CurrencyID;
    double Curr_Value;
    Login_utils login_utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        TextView toolbartitle=(TextView)findViewById(R.id.toolbartitle);
        inittoolbar();
        toolbartitle.setText("Booking Review");
        login_utils=new Login_utils(this);
        currency_utils=new Currency_Utils(this);
        CurrencyID=currency_utils.getCurrencyValue("Currency");
        Curr_Value= Double.parseDouble(currency_utils.getCurrencyValue("Currency_Value"));
        Currency_Symbol=currency_utils.getCurrencyValue("Currency_Symbol");
        selHotel = (AvailableHotelsDTO) getIntent().getSerializableExtra("SelHotel");
        roomList = (ArrayList<Room>) getIntent().getSerializableExtra("roomList");
        selDetails = (SelectionDetailsDTO) getIntent().getSerializableExtra("selDetails");

        hotelImagesDTO=new HotelImagesDTO();

        hotel_utils=new Hotel_utils(this);

         Adults= hotel_utils.getPaxDetails("Adults");
         Children=hotel_utils.getPaxDetails("Child");
         Rooms=hotel_utils.getPaxDetails("Rooms");
         TotalADT=hotel_utils.getPaxDetails("TotalADT");
         TotalCHD=hotel_utils.getPaxDetails("TotalCHD");
         ChildAges=hotel_utils.getPaxDetails("ChildAges");

        System.out.println("Adults:"+Adults+"  "+"Children:"+Children);
        DeviceToken= Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        //Util.showMessage(this,"sdfsd"+roomList.size());

        init();
        getLoginPrefernces();
        callPromoCodes();
    }

    public void getLoginPrefernces(){
        if (login_utils.getUserDetails(Constants.USERTYPE).equals("6")) {
            UserId = "6";
            User_agentid = login_utils.getUserDetails(Constants.USERID);
            email.setText(login_utils.getUserDetails(Constants.USEREMAIL));
            phone.setText(login_utils.getUserDetails(Constants.USERPHONE));
        } else if (login_utils.getUserDetails(Constants.USERTYPE).equals("4")) {
            UserId = "4";
            User_agentid = login_utils.getUserDetails(Constants.USERID);
            email.setText(login_utils.getUserDetails(Constants.USEREMAIL));
            phone.setText(login_utils.getUserDetails(Constants.USERPHONE));
            AgentBalance=login_utils.getUserDetails(Constants.BALANCE);
            ConvinenceLayout.setVisibility(View.GONE);
            Promo_Card_layout.setVisibility(View.GONE);
        } else {
            UserId = "5";
            User_agentid ="null";
        }

    }
    private void init() {
        TextView addDetails = (TextView)findViewById(R.id.addDetails);
        TextView hotelNameTv = (TextView)findViewById(R.id.hotelNameTv);
        TextView hotelDescTv = (TextView)findViewById(R.id.hotelDescTv);
        TextView roomcharges = (TextView)findViewById(R.id.roomcharges);
        TextView roomTaxes = (TextView)findViewById(R.id.roomTaxes);
        roomTotalTv = (TextView)findViewById(R.id.roomTotalTv);
        TextView checkinTv = (TextView)findViewById(R.id.checkinTv);
        TextView checkoutTv = (TextView)findViewById(R.id.checkoutTv);
        TextView totalRoomTv = (TextView)findViewById(R.id.totalRoomTv);
        TextView totalAdultsTv = (TextView)findViewById(R.id.totalAdultsTv);
        TextView totalChildTv = (TextView)findViewById(R.id.totalChildTv);
        TextView proceedTopay =(TextView)findViewById(R.id.proceedTopay);
        ConvincenceFee= (TextView) findViewById(R.id.conveniencefee);
        ConvinenceLayout= (LinearLayout) findViewById(R.id.conviinence_layout);
        checkboxAgree=(CheckBox)findViewById(R.id.checkboxAgree);
        ExtraGuest= (TextView) findViewById(R.id.extracharges);
        email =(EditText)findViewById(R.id.email);
        phone=(EditText)findViewById(R.id.phone);
        ImageView hotelImage = (ImageView)findViewById(R.id.hotelImage);
        Promo_Card_layout= (LinearLayout) findViewById(R.id.promo_layout);
        Promo_layout= (LinearLayout) findViewById(R.id.promo_code_layout);
        HaveAPromo= (TextView) findViewById(R.id.have_a_code);
        Promo_Edit= (EditText) findViewById(R.id.promocode_et);
        Promo_Apply= (TextView) findViewById(R.id.promo_apply);
        Promo_Amount_layout= (LinearLayout) findViewById(R.id.promo_amount_layout);
        Promo_Amount= (TextView) findViewById(R.id.discount_tv);
        RatingBar hotel_rating= (RatingBar) findViewById(R.id.hotelRating);
        hotelNameTv.setText(selHotel.getHotelName());
        hotelDescTv.setText(selHotel.getHotelAddress());
        checkinTv.setText(selDetails.getCheckIn());
        checkoutTv.setText(selDetails.getCheckOut());
        totalRoomTv.setText("Room : "+roomList.size());
        totalAdultsTv.setText("Adults : "+updateTotalNoofAdults());
        totalChildTv.setText("Child : "+updateTotalNoofChild());
        hotel_rating.setRating(selHotel.getStarRating());
        DecimalFormat df = new DecimalFormat("#.##");

        getRoomDetailsCharges();
        for (int p=0;p<roomDetailsDTOs.size();p++){
            roomscharges +=(Double.valueOf(roomDetailsDTOs.get(p).getRoomTotal()));
            Servicetaxes +=Double.valueOf(roomDetailsDTOs.get(p).getServicetaxTotal());
            extras +=Double.valueOf(roomDetailsDTOs.get(p).getExtGuestTotal());
        }

/*
        int charges =Util.getprice(roomscharges*Curr_Value*selDetails.getNoofDays());
        RoomAmount=String.valueOf(charges);

        int taxes =Util.getprice(Servicetaxes*Curr_Value);
        ServiceCharges=String.valueOf(taxes);

        int exch= Util.getprice(extras*Curr_Value);
        ExtraGuestCharges=String.valueOf(exch);
*/

        if(selHotel.getConvenienceFeeType()==1){

            double total=selDetails.getNoofDays()*roomList.size();

            confee=Double.valueOf(selHotel.getConvenienceFee())*total;
        }else {
            double Total_To_Calculate_Confee=roomscharges*selDetails.getNoofDays()+Servicetaxes+extras;
            double con_fee= Double.parseDouble(selHotel.getConvenienceFee());
            confee=(Total_To_Calculate_Confee/100.0f)*con_fee;
        }
        total=roomscharges*selDetails.getNoofDays()+Servicetaxes+extras+confee;



        //int Amount =Util.getprice(total);
        TotalAmount= String.valueOf(total);

        roomcharges.setText(Currency_Symbol+Util.getprice(roomscharges*Curr_Value*selDetails.getNoofDays())+"");
        roomTaxes.setText(Currency_Symbol+Util.getprice(Servicetaxes*Curr_Value)+"");
        roomTotalTv.setText(Currency_Symbol+Util.getprice(total*Curr_Value)+"");
        ConvincenceFee.setText(Currency_Symbol+Util.getprice(confee*Curr_Value)+"");
        ExtraGuest.setText(Currency_Symbol+Util.getprice(extras*Curr_Value)+"");

        if (selHotel.getHotelImages().size()!=0){
            Glide.with(this)
                    .load(selHotel.getHotelImages().get(0).getImagepath())
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_placeholder)
                    .into(hotelImage);
        }else {
            Glide.with(this)
                    .load(R.drawable.image_placeholder)
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_placeholder)
                    .into(hotelImage);
        }

        addDetails.setOnClickListener(this);
        proceedTopay.setOnClickListener(this);
        HaveAPromo.setOnClickListener(this);
        Promo_Apply.setOnClickListener(this);
    }

    private RoomDetailsDTO getRoomDetailsCharges() {
        RoomDetailsDTO selRoomDetailsDTO = null;

        for(int i=0;i<selHotel.getRoomDetails().size();i++){
            for (int p=0;p<selDetails.getSelectedRooms().size();p++){
                if(selDetails.getSelectedRooms().get(p).equals(selHotel.getRoomDetails().get(i).getRoomIndex()+"")){
                    roomDetailsDTOs.add(selHotel.getRoomDetails().get(i));
                    selRoomDetailsDTO=selHotel.getRoomDetails().get(i);
                }
            }

        }
        return selRoomDetailsDTO;

    }

    private void callPromoCodes() {
        if(Util.isNetworkAvailable(getApplicationContext())) {
            ServiceClasses.getPromocodes(ReviewActivity.this, Constants.GETPROMOCODES);
        }else{
            Util.showMessage(this,Constants.NO_INT_MSG);
        }
    }



    public void getPromoCodesResponse(String response) {
        System.out.println("Offers---"+response);
        //Util.showMessage(this,response);
        if(response!=null) {
            InputStream stream = new ByteArrayInputStream(response.getBytes());
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(stream);
            GetPromoCodesDTO[] getPromoCodesDTOs = gson.fromJson(reader, GetPromoCodesDTO[].class);
            if(getPromoCodesDTOs!=null){
                promoCodesDTOs=getPromoCodesDTOs;
            }
        }
    }


    public int updateTotalNoofAdults() {
        int adultCount=0;
        for(int i=0;i<roomList.size();i++){
            adultCount=adultCount+roomList.get(i).getAdults();
        }
       return adultCount;
    }

    public int updateTotalNoofChild() {
        int childCount=0;
        for(int i=0;i<roomList.size();i++){
            childCount=childCount+roomList.get(i).getChild();
        }
        return childCount;

    }

    private JSONArray getRoomDetails(){
        JSONArray array = new JSONArray();

        try {
            for (int p=0;p<roomDetailsDTOs.size();p++){
                JSONObject jsonBody = new JSONObject() ;
                jsonBody.put("RoomIndex", roomDetailsDTOs.get(p).getRoomIndex());
                jsonBody.put("RateType", roomDetailsDTOs.get(p).getRateType());
                jsonBody.put("RoomType", roomDetailsDTOs.get(p).getRoomType());
                jsonBody.put("RoomBasis", roomDetailsDTOs.get(p).getRoomBasis());
                jsonBody.put("RoomTypeCode",roomDetailsDTOs.get(p).getRoomTypeCode());
                jsonBody.put("RatePlanCode", roomDetailsDTOs.get(p).getRatePlanCode());
                jsonBody.put("WsKey", roomDetailsDTOs.get(p).getWsKey());
                jsonBody.put("RoomTotal",roomDetailsDTOs.get(p).getRoomTotal());
                jsonBody.put("RoomNetTotal",roomDetailsDTOs.get(p).getRoomNetTotal());
                jsonBody.put("ExtGuestTotal",roomDetailsDTOs.get(p).getExtGuestTotal());
                jsonBody.put("ServicetaxTotal",roomDetailsDTOs.get(p).getServicetaxTotal());
          /*      jsonBody.put("ExtGuestTotalZAR",roomDetailsDTOs.get(p).getExtGuestTotalZAR());
                jsonBody.put("RoomTotalZAR", roomDetailsDTOs.get(p).getRoomTotalZAR());
                jsonBody.put("RoomNetTotalZAR",roomDetailsDTOs.get(p).getRoomNetTotalZAR());
                jsonBody.put("ServicetaxTotalZAR",roomDetailsDTOs.get(p).getServicetaxTotalZAR());*/
                jsonBody.put("RoomCancellationPolicy", roomDetailsDTOs.get(p).getRoomCancellationPolicy());
                jsonBody.put("NoOfPax", roomDetailsDTOs.get(p).getNoOfPax());
                jsonBody.put("RefundRule", roomDetailsDTOs.get(p).getRefundRule());
                jsonBody.put("Inclusions", roomDetailsDTOs.get(p).getInclusions());
                jsonBody.put("IsInclusion",roomDetailsDTOs.get(p).isInclusion());
                jsonBody.put("PartnerFareDatails",getPartnerFareDatails(p));
                jsonBody.put("Exclusions",roomDetailsDTOs.get(p).getExclusions());
                jsonBody.put("Images",roomDetailsDTOs.get(p).getHotelImages());
                jsonBody.put("RoomView",roomDetailsDTOs.get(p).getRoomView());
                jsonBody.put("MaxChildOccupancy",roomDetailsDTOs.get(p).getMaxChildOccupancy());
                jsonBody.put("MaxAdultOccupancy",roomDetailsDTOs.get(p).getMaxAdultOccupancy());
                jsonBody.put("Validdays",roomDetailsDTOs.get(p).getValiddays());
                jsonBody.put("Commission",roomDetailsDTOs.get(p).getCommission());
                jsonBody.put("RoomCount",roomDetailsDTOs.get(p).getRoomCount());
                jsonBody.put("expediaPropertyId",roomDetailsDTOs.get(p).getExpediaPropertyId());
                jsonBody.put("HotelPackage",roomDetailsDTOs.get(p).getHotelPackage());
                jsonBody.put("BedType",roomDetailsDTOs.get(p).getBedType());
                jsonBody.put("RoomAmenities",roomDetailsDTOs.get(p).getRoomAmenities());
                jsonBody.put("IncExcCondition",roomDetailsDTOs.get(p).getIncExcCondition());
                jsonBody.put("Discount",roomDetailsDTOs.get(p).getDiscount());
                jsonBody.put("RoomDescription",roomDetailsDTOs.get(p).getRoomDescription());

                array.put(jsonBody);
            }




        } catch (JSONException e) {
            e.printStackTrace();
        }

        return array;
    }
    private String preparePayload() {
       /* ArrayList<RoomDetailsDTO> selRoomDetails = new ArrayList<>();
        selRoomDetails.add(getRoomDetailsCharges());*/
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("IsOfflineBooking", false);
            jsonBody.put("APIRefNo","");
            jsonBody.put("HotelId", selHotel.getHotelId());
            jsonBody.put("Names", totalNames);
            jsonBody.put("Ages", totalAges);
            jsonBody.put("Genders",totalGenders);
            jsonBody.put("MobileNo", phone.getText().toString());
            jsonBody.put("EmailId", email.getText().toString());
            jsonBody.put("Provider", selHotel.getProvider());
            jsonBody.put("SecondaryProvider", selHotel.getSecondaryProvider());
            jsonBody.put("EmergencyMobileNo","");
            jsonBody.put("IdCardIssuedBy","");
            jsonBody.put("nationality",selDetails.getNationalityid());
            jsonBody.put("Country",selDetails.getNationalityid());
            jsonBody.put("Allocavail","");
            jsonBody.put("Allocid","");
            jsonBody.put("DeviceOS",Constants.DeviceOs);
            jsonBody.put("DeviceOSVersion",Constants.DeviceOsversion);
            jsonBody.put("DeviceModel",Constants.DeviceModel);
            jsonBody.put("DeviceToken",DeviceToken);
            jsonBody.put("ApplicationType","2");
            jsonBody.put("DeviceType","2");
            jsonBody.put("EProductPrice","");
            jsonBody.put("CurrencyID",CurrencyID);
            jsonBody.put("CurrencyValue",Curr_Value);
            jsonBody.put("HotelDetail", getHotelDetail());
            jsonBody.put("HotelPolicy", "policy");
            jsonBody.put("DestinationId", selDetails.getCityId());
            jsonBody.put("CityName", selDetails.getCityName());
            jsonBody.put("ArrivalDate", selDetails.getCheckIn());
            jsonBody.put("DepartureDate", selDetails.getCheckOut());
            jsonBody.put("Rooms", String.valueOf(roomList.size()));
            jsonBody.put("Adults",Adults);
            jsonBody.put("Children",Children);
            jsonBody.put("ChildrenAges",ChildAges);
            jsonBody.put("UserType", UserId);
            jsonBody.put("User",User_agentid);
            jsonBody.put("NoOfdays", selDetails.getNoofDays());
            jsonBody.put("IsAgentPaymentGateway","false");
            jsonBody.put("Address","INDIA");
            jsonBody.put("AffiliateId","");
            jsonBody.put("Fare","");
            jsonBody.put("AdditionalInfo","");
            jsonBody.put("Titles","");
            jsonBody.put("IsBack","");
            jsonBody.put("HotelType", selDetails.getHoteltype());
            jsonBody.put("Status",1);
            jsonBody.put("State","3");
            jsonBody.put("BookingRefNo","");
            jsonBody.put("PromoCode",PromoCode);
            jsonBody.put("PromoCodeAmount",PromoAmount);
            jsonBody.put("WebsiteUrl","");
            jsonBody.put("PaymentId","");
            jsonBody.put("RoomDetails", getRoomDetails());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  jsonBody.toString();
    }

    private JSONObject getHotelDetail() {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("HotelName", selHotel.getHotelName());
            jsonBody.put("StarRating", selHotel.getStarRating());
            jsonBody.put("Rooms", selHotel.getRooms());
            jsonBody.put("SupplierType",selHotel.getSupplierType());
            jsonBody.put("Punchline",selHotel.getPunchline());
            jsonBody.put("LocationInfo",selHotel.getPunchline());
            jsonBody.put("RPH", selHotel.getRPH());
            jsonBody.put("Alias",selHotel.getAlias());
            jsonBody.put("City",selHotel.getCity());
            jsonBody.put("MinRate",selHotel.getMinRate());
            jsonBody.put("Events",selHotel.getEvents());
            jsonBody.put("Email",selHotel.getEmail());
            jsonBody.put("Description",selHotel.getDescription());
            jsonBody.put("Longitude",selHotel.getLongitude());
            jsonBody.put("Latitude",selHotel.getLatitude());
            jsonBody.put("Facebook",selHotel.getFacebook());
            jsonBody.put("PromoDetail",selHotel.getPromoDetail());
            jsonBody.put("Twitter",selHotel.getTwitter());
            jsonBody.put("HotelServices",selHotel.getHotelServices());
            jsonBody.put("Fax",selHotel.getFax());
            jsonBody.put("OtherFees",selHotel.getOtherFees());
            jsonBody.put("PostalCode",selHotel.getPostalCode());
            jsonBody.put("CreditCards",selHotel.getCreditCards());
            jsonBody.put("Website",selHotel.getWebsite());
            jsonBody.put("RoomServices",selHotel.getRoomServices());
            jsonBody.put("PropertyCategory",selHotel.getPropertyCategory());
            jsonBody.put("HotelPolicy",selHotel.getHotelPolicy());
            jsonBody.put("Checkintime",selHotel.getCheckintime());
            jsonBody.put("Floors",selHotel.getFloors());
            jsonBody.put("Linkedin",selHotel.getLinkedin());
            jsonBody.put("Awards",selHotel.getAwards());
            jsonBody.put("ConvenienceFee",confee);
            jsonBody.put("ConvenienceFeeType",selHotel.getConvenienceFeeType());
            jsonBody.put("WebService", selHotel.getWebService());
            jsonBody.put("Provider", selHotel.getProvider());
            jsonBody.put("SecondaryProvider", selHotel.getSecondaryProvider());
            jsonBody.put("RoomCombination", selHotel.getRoomCombination());
            jsonBody.put("RoomChain", selHotel.getRoomChain());
            jsonBody.put("HotelImages",getHotelImages());
            jsonBody.put("HotelAddress",selHotel.getHotelAddress());
            jsonBody.put("Facilities",selHotel.getFacilities());
            jsonBody.put("VideoURL",selHotel.getVideoURL());
            jsonBody.put("HotelChain",selHotel.getHotelChain());
            jsonBody.put("Checkouttime",selHotel.getCheckouttime());
            jsonBody.put("Distances",selHotel.getDistances());
            jsonBody.put("AdditionalInfo",selHotel.getAdditionalInfo());
            jsonBody.put("ConvenienceFeeTotal",0);
            jsonBody.put("AffiliateId",selHotel.getAffiliateId());
            jsonBody.put("CountryCode",selHotel.getCountryCode());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  jsonBody;
    }


    private JSONObject getPartnerFareDatails(int p){
        JSONObject jsonBody = new JSONObject();

        try {
            jsonBody.put("CommissionType",roomDetailsDTOs.get(p).getCommission());
            jsonBody.put("NetFares",roomDetailsDTOs.get(p).getRoomNetTotal());
            jsonBody.put("Commission",roomDetailsDTOs.get(p).getCommission());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonBody;
    }


    private JSONObject getHotelImages(){

        JSONObject jsonOBody=new JSONObject();

        try {
            jsonOBody.put("Imagedesc",hotelImagesDTO.getImagedesc());
            jsonOBody.put("Imagepath",hotelImagesDTO.getImagepath());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonOBody;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.addDetails:
                Intent intent = new Intent(ReviewActivity.this, AddDetailsActivity.class);
                intent.putExtra("roomList",roomList);
                intent.putExtra("SelHotel",selHotel);
                startActivityForResult(intent, 2);
                break;
            case R.id.proceedTopay:
                if (!validateForm()){

                }else if(totalNames==null && totalAges==null){
                    Util.showMessage(ReviewActivity.this, "Please Enter Guest Details");
                }else if(!checkboxAgree.isChecked()){
                    Util.showMessage(ReviewActivity.this, "Please Agree Terms & Conditions");
                }else{
                    callBlockHotel();
                }
                break;
            case R.id.have_a_code:
                Promo_layout.setVisibility(View.VISIBLE);
                break;
            case R.id.promo_apply:
                if (!validatePromoCode()){

                }else {
                    callValidatePromo();
                }
                break;

        }
    }

    private void callBlockHotel() {
        if(Util.isNetworkAvailable(getApplicationContext())) {
            showProgressDialog("Please wait",this);
            ServiceClasses.postBlockHotel(ReviewActivity.this, Constants.BLOCKHOTEL,preparePayload());
            System.out.println("Hotel_Request:: "+preparePayload());
            Log.v("Hotel Parameters : ",preparePayload());

        }else{
            Util.showMessage(ReviewActivity.this,Constants.NO_INT_MSG);
        }
    }

    private boolean validateForm() {
        if(!Util.checkField(email)){
            Util.showMessage(ReviewActivity.this,"Please fill Email");
            return false;
        }else if(!Util.checkField(phone)) {
            Util.showMessage(ReviewActivity.this, "Please fill Contact No.");
            return false;
        }else if(!Util.validateEmail(email.getText().toString())) {
            Util.showMessage(ReviewActivity.this, "Please Enter valid Email");
            return false;
        }else if(!Util.validateMobile(phone)){
            Util.showMessage(ReviewActivity.this, "Please Enter valid Contact No.");
            return false;
        }
        else{
            return true;
        }
    }


    private boolean validatePromoCode(){
        if (!Util.checkField(Promo_Edit)){
            Util.showMessage(ReviewActivity.this,"Enter Promo code");
            return false;
        }else {
            return true;
        }
    }
    public void callValidatePromo(){
        for (int i=0;i<promoCodesDTOs.length;i++){
            if (selDetails.getHoteltype().equals("1")){
                if(Promo_Edit.getText().toString().equals(promoCodesDTOs[i].getCode())&&promoCodesDTOs[i].getServiceType()==5) {
                    ValidPromoCode.add(promoCodesDTOs[i]);
                }
            }else {
                if(Promo_Edit.getText().toString().equals(promoCodesDTOs[i].getCode())&&promoCodesDTOs[i].getServiceType()==18) {
                    ValidPromoCode.add(promoCodesDTOs[i]);
                }
            }

        }

        if (ValidPromoCode.size()==0){
            showAlertDialog("Invalid PromoCode !");
        }else {
            String ValidFrom=ValidPromoCode.get(0).getValidFrom();
            String ValidTill=ValidPromoCode.get(0).getValidTill();
            double FromAmount=Double.parseDouble(String.valueOf(ValidPromoCode.get(0).getFromAmount()));
            double ToAmount=Double.parseDouble(String.valueOf(ValidPromoCode.get(0).getToAmount()));
            SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            String CurrentDate = output.format((c.getTime()));
            try {
                Date PromoCodeValidFromDate = output.parse(ValidFrom);
                Date PromoCodeValidTillDate = output.parse(ValidTill);
                Date todayDate = output.parse(CurrentDate);

                if (PromoCodeValidTillDate.after(todayDate) || todayDate.equals(PromoCodeValidTillDate) && PromoCodeValidFromDate.before(todayDate) || todayDate.equals(PromoCodeValidFromDate)) {
                    if (total>=FromAmount&&total<=ToAmount) {
                        int DiscountType=ValidPromoCode.get(0).getDiscountType();
                        double Discount=Double.parseDouble(String.valueOf(ValidPromoCode.get(0).getDiscount()));
                        PromoCode=ValidPromoCode.get(0).getCode();
                        if (DiscountType==0) {
                            double DiscountFare=(total/100.0f)*Discount;
                            double TotalFare=total-DiscountFare;
                            //int RoundDiscFare=Util.getprice(DiscountFare);
                            //int RoundTotalAmt=Util.getprice(TotalFare);
                            TotalAmount= String.valueOf(TotalFare);
                            Promo_Card_layout.setVisibility(View.GONE);
                            Promo_Amount_layout.setVisibility(View.VISIBLE);
                            Promo_Amount.setText(Currency_Symbol+Util.getprice(DiscountFare*Curr_Value)+"");
                            roomTotalTv.setText(Currency_Symbol+Util.getprice(TotalFare*Curr_Value)+"");
                        }else {
                            double TotalFare=total-Discount;
                            //int RoundDiscFare=Util.getprice(Discount);
                            //int RoundTotalAmt=Util.getprice(TotalFare);
                            TotalAmount= String.valueOf(TotalFare);
                            Promo_Card_layout.setVisibility(View.GONE);
                            Promo_Amount_layout.setVisibility(View.VISIBLE);
                            Promo_Amount.setText(Currency_Symbol+Util.getprice(Discount*Curr_Value)+"");
                            roomTotalTv.setText(Currency_Symbol+Util.getprice(TotalFare*Curr_Value)+"");
                        }
                    }else {
                        int From= (int) (FromAmount*Curr_Value);
                        int To= (int) (ToAmount*Curr_Value);
                        showAlertDialog("Promo Code is Valid only when \nTotal Amount is in between\n" +Currency_Symbol +" "+ From
                                + " and " +Currency_Symbol +" "+ To);
                    }

                }else {
                   showAlertDialog("Promocode Expired !");
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }


        }

    }


    private void showAlertDialog(String s) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        ////alertDialog.setTitle("Alert!");
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            if(data!=null) {
                totalNames = data.getStringExtra("totalNames");
                totalAges = data.getStringExtra("totalAges");
                totalGenders=data.getStringExtra("totalGenders");
            }
        }
    }

    public void postBlockhotelResponse(String response) throws JSONException {
        hideProgressDialog();
        System.out.println("HotelResponse : "+response);

        JSONObject object=new JSONObject(response);
        Message=object.getString("Message");
        String BookingStatus=object.getString("BookingStatus");

        Referenceno=object.getString("ReferenceNo");

        if (Message.equals("Hotel Room Blocked Successfully")&&BookingStatus.equals("1")){
                    if (UserId.equals("4")) {
                        final Dialog  agentpaymentDialog = new Dialog(ReviewActivity.this);
                        agentpaymentDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        agentpaymentDialog.setContentView(R.layout.agent_dialog);
                        TextView DialogPay= (TextView)agentpaymentDialog. findViewById(R.id.pay);
                        TextView  WalletBalance= (TextView)agentpaymentDialog. findViewById(R.id.wallet_bal);
                        TextView DialogAmount= (TextView)agentpaymentDialog. findViewById(R.id.AmountToPay);


                        WalletBalance.setText(Constants.AGENT_CURRENCY_SYMBOL+AgentBalance+"");
                        DialogAmount.setText(Currency_Symbol+Util.getprice(total*Curr_Value)+"");
                       ImageView wallet = (ImageView) agentpaymentDialog.findViewById(R.id.walletimg);
                        DialogPay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pay();

                            }
                        });

                        ImageView can_Dialog= (ImageView) agentpaymentDialog.findViewById(R.id.can_dialog);
                        can_Dialog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                agentpaymentDialog.dismiss();
                            }
                        });
                        agentpaymentDialog.show();
                        Window window = agentpaymentDialog.getWindow();
                        window.setLayout(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
                    }else {
                        final Context context = ReviewActivity.this;
                        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);

                        // set title
                        alertDialogBuilder.setTitle("Payment Confirmation ");

                        // set dialog message
                        alertDialogBuilder
                                .setMessage("Click Yes to proceed!")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //Util.showMessage(ReviewActivity.this,"You have to pay: "+TotalAmount);
                                        Intent i = new Intent(ReviewActivity.this, Payment_Gateway_Main.class);
                                        i.putExtra("name", "NaTicket");
                                        i.putExtra("email", email.getText().toString());
                                        i.putExtra("phone", phone.getText().toString());
                                        i.putExtra("amount", TotalAmount);
                                        i.putExtra("referencenumber", Referenceno);
                                        i.putExtra("PaymentType", "Hotel");
                                        System.out.println("referenceNo" + Referenceno);
                                        i.putExtra("isFromOrder", true);
                                        i.putExtra("BookingType","Hotel_Booking");
                                        startActivity(i);
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        Intent i = new Intent(ReviewActivity.this, HotelSearchActivity.class);
                                        startActivity(i);
                                    }
                                });

                        // create alert dialog
                        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();
                    }
        }else {
          Util.alertDialogShow(this,Message);
        }

    }

    public void errorreponse (String response){
        hideProgressDialog();
        Util.alertDialogShow(ReviewActivity.this,"Some thing went wrong \n Please try again");
    }

    public void pay() {
        Intent ip=new Intent(ReviewActivity.this,Hotel_Booking_Activity.class);

        ip.putExtra("PaymentGatewayId",Referenceno);
        ip.putExtra("Amount",TotalAmount);
        ip.putExtra("referencenumber",Referenceno);
        ip.putExtra("name","getResources().getString(R.string.app_name)");
        ip.putExtra("email",email.getText().toString());
        ip.putExtra("phone",phone.getText().toString());
        ip.putExtra("IsAgent","Yes");

        startActivity(ip);
    }


    private  void showProgressDialog(String msg,Context context) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage(msg);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
        }

        mProgressDialog.show();

       /* FragmentManager fm = getFragmentManager();
        dialogFragment = new LoadingDialog ();
        dialogFragment.show(fm, "Sample Fragment");*/
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
        //dialogFragment.dismiss();
    }
}
