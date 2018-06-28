package com.NaTicket.n.buses;

/**
 * Created by Ankit on 8/17/2017.
 */

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.NaTicket.n.utils.DialogUtils;
import com.NaTicket.n.R;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ResponseActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ResponseActivity";

    ProgressDialog dialog;
    String data;
    int code;
    String newString, stringname, stringmessage, stringReturnReferencenumber;
    Button clickhere;
    String P1="",P2="",P3="",P4="",P5="",P6="";
    String Names;

    String refno,IsFromTrip="Yes";
    TextView tvBookingdate, Route, tvDisnationName, tvBoards, tvBoardingPoint, tvTRavel, tvSeat, tvPassengers,
            tvBoardingpointDetails, tvTicketNumber, Total, PNRNumber/*, Actual, Promo, GSTCharges, Convenience*/;
    TextView tvReturnBookingdtae,ReturnRoute, tvReturnFromName, tvReturnDisnationName, tvReturnBoards, tvReturnBoardingPoint, tvReturnTRavel,
            tvReturnSeat, tvReturnPassengers, tvReturnBoardingpointDetails, tvReturnTicketNumber, tvReturnTotal, tvReturnPNRNumber
            /*ActualReturn, PromoReturn, GSTChargesReturn, ConvenienceReturn*/;
    CardView ReturnticketDetails, ReturnticketPNR, Total_Fare_LYT;

//    TextView TotalOnward, TotalReturn, TotalDiscount, Total_Net_Fare;
    String TON, TR, TD, TNF;

    TextView NoTicket;
    ScrollView TicketScroll;


    LinearLayout discountlayout, discountlayoutReturn;


    int i;
    Integer BaseFares = 0, ServiceTaxes = 0, ServiceChargess = 0, ConvenienceFee = 0, PromoCodeAmountt = 0, fares = 0;


    protected void onCreate(Bundle savedInstanstate) {
        super.onCreate(savedInstanstate);
        setContentView(R.layout.bus_ticket_details);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanstate == null) {
            Bundle bundle = getIntent().getExtras();
            if (null != bundle) {
                newString = bundle.getString("status");
                refno = bundle.getString("Onwardrefno");
                stringname = bundle.getString("name");
                stringmessage = bundle.getString("msg");
                stringReturnReferencenumber = bundle.getString("Returnrefno");
                IsFromTrip=bundle.getString("IsFromTrips");
            } else {
                newString = null;
                refno = null;
                stringmessage = null;
                stringname = null;
            }
        } else {
            newString = (String) savedInstanstate.getSerializable("status");
            refno = (String) savedInstanstate.getSerializable("refno");
            stringname = (String) savedInstanstate.getSerializable("name");
            stringmessage = (String) savedInstanstate.getSerializable("msg");
            stringReturnReferencenumber = (String) savedInstanstate.getSerializable("Returnrefno");
            IsFromTrip=(String) savedInstanstate.getSerializable("IsFromTrips");
        }

        if(IsFromTrip==null){
            IsFromTrip="Yes";
        }


        SharedPreferences preference = getApplicationContext().getSharedPreferences("JourneyDetails", MODE_PRIVATE);

        //ConvenienceFeeType = preference.getString("ConvenienceFeeType", null);

        NoTicket= (TextView) findViewById(R.id.NoTicketText);
        TicketScroll= (ScrollView) findViewById(R.id.TicketScrollView);

        tvBookingdate = (TextView) findViewById(R.id.Bookingdate);
        Route = (TextView) findViewById(R.id.route);
        tvBoards = (TextView) findViewById(R.id.Boards);
        tvBoardingPoint = (TextView) findViewById(R.id.BoardingPoint);
        tvTRavel = (TextView) findViewById(R.id.TRavel);
        tvSeat = (TextView) findViewById(R.id.Seat);
        tvPassengers = (TextView) findViewById(R.id.Passengers);
        tvTicketNumber = (TextView) findViewById(R.id.TicketNumber);
        Total = (TextView) findViewById(R.id.Total);
        PNRNumber = (TextView) findViewById(R.id.PNRNumber);
        //Actual = (TextView) findViewById(R.id.Actual);
       // GSTCharges = (TextView) findViewById(R.id.GSTAndCharges);
        //Convenience = (TextView) findViewById(R.id.Convence);
        //Promo = (TextView) findViewById(R.id.promoAmt);
        //discountlayout = (LinearLayout) findViewById(R.id.discountlayout);
        //discountlayout.setVisibility(View.GONE);

        ReturnticketDetails = (CardView) findViewById(R.id.ReturnticketDetailsLYT);
        tvReturnBookingdtae = (TextView) findViewById(R.id.RetuenBookingdate);
        ReturnRoute= (TextView) findViewById(R.id.Return_route);
        tvReturnBoards = (TextView) findViewById(R.id.ReturnBoards);
        tvReturnBoardingPoint = (TextView) findViewById(R.id.ReturnBoardingPoint);
        tvReturnTRavel = (TextView) findViewById(R.id.ReturnTRavel);
        tvReturnSeat = (TextView) findViewById(R.id.ReturnSeat);
        tvReturnPassengers = (TextView) findViewById(R.id.ReturnPassengers);
        tvReturnTicketNumber = (TextView) findViewById(R.id.ReturnTicketNumber);
        tvReturnTotal = (TextView) findViewById(R.id.TotalReturn);
        tvReturnPNRNumber = (TextView) findViewById(R.id.PNRNumberReturn);
       /* ActualReturn = (TextView) findViewById(R.id.ActualReturn);
        GSTChargesReturn = (TextView) findViewById(R.id.GSTAndChargesReturn);
        ConvenienceReturn = (TextView) findViewById(R.id.ConvenceReturn);
        PromoReturn = (TextView) findViewById(R.id.promoAmtReturn);*/
       // discountlayoutReturn = (LinearLayout) findViewById(R.id.discountlayoutReturn);
        //discountlayoutReturn.setVisibility(View.GONE);

        //Total_Fare_LYT = (CardView) findViewById(R.id.Totoal_fare_layout);
//        TotalOnward = (TextView) findViewById(R.id.TotalOnward);
//        TotalReturn = (TextView) findViewById(R.id.TotalReturn);
//        TotalDiscount = (TextView) findViewById(R.id.TotalDiscount);
//        Total_Net_Fare = (TextView) findViewById(R.id.TotalFareTV);

        if (Util.isNetworkAvailable(this)) {
            new getOnwardTktDetails().execute();
        } else {
            DialogUtils dialogUtils = new DialogUtils(this);
            dialogUtils.showNetworkErrorDialog();
        }


    }

    @Override
    public void onClick(View v) {
        if (v == clickhere) {
            if (newString.equals("1") || newString.equals("4") || newString.equals("3")) {
                Intent i = new Intent(ResponseActivity.this, Buses_MainActivity.class);
                startActivity(i);
            }
        }
    }


    public class getOnwardTktDetails extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(ResponseActivity.this, "", "Confirmed");
        }


        @Override
        protected String doInBackground(String... params) {
            String strurl = Constants.BookingDetails + "referenceNo=" + refno + "&type=2";
            DefaultHttpClient httpClient = new DefaultHttpClient();


            HttpGet httpGet = new HttpGet(strurl);
            httpGet.setHeader("ConsumerKey", Constants.ConsumerKey);
            httpGet.setHeader("ConsumerSecret", Constants.ConsumerSecret);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();


            try {

                HttpResponse response = httpClient.execute(httpGet);
                code = response.getStatusLine().getStatusCode();
                data = httpClient.execute(httpGet, responseHandler);

            } catch (IOException e) {
                e.printStackTrace();
            }


            return data;


        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //  JSONObject jsonObject = null;
            try {


                System.out.println("BookingDetails" + code);

                if(data!=null) {

                    JSONObject jsonObject = new JSONObject(data);


                    String BookingDate = jsonObject.getString("BookingDate");
                    String APIRefNo = jsonObject.getString("APIRefNo");
                    String PromoCodeAmount = jsonObject.getString("PromoCodeAmount");
                    String BoardingTime = jsonObject.optString("DepartureTime");
                    String CollectedFare = jsonObject.getString("CollectedFare");
                    String ActualFare = jsonObject.getString("Fares");
                    String ServiceTax = jsonObject.optString("Servicetax");
                    String ServiceCharges = jsonObject.optString("ServiceCharge");
                    String Convenienvce = jsonObject.optString("ConvenienceFee");
                    String SourceName = jsonObject.optString("SourceName");
                    String DestinationName = jsonObject.optString("DestinationName");
                    String JourneyDate = jsonObject.optString("JourneyDate");
                    String OperatorName = jsonObject.optString("OperatorName");
                    String BusTypeName = jsonObject.optString("BusTypeName");
                    String PassengerName = jsonObject.optString("PassengerName");
                    String SeatNos = jsonObject.optString("SeatNos");
                    jsonObject.optString("CancellationPolicy");
                    JSONObject BoardingDroppingDetails = jsonObject.getJSONObject("BoardingDroppingDetails");
                    String Address = BoardingDroppingDetails.optString("Address");
                    String Landmark = BoardingDroppingDetails.optString("Landmark");
                    String Location = BoardingDroppingDetails.optString("Location");




                    Names = PassengerName.replace("~",", \n");

                    /*if(PassengerName.contains("~")){

                        StringTokenizer st = new StringTokenizer(PassengerName, "~");

                        while (st.hasMoreTokens()) {
                            try {
                                P1 = st.nextToken();
                                P2 = st.nextToken();
                                P3 = st.nextToken();
                                P4 = st.nextToken();
                                P5 = st.nextToken();
                                P6 = st.nextToken();
                            }catch (NoSuchElementException e){
                                ///Do Nothing, if next token is not found
                            }
                        }

                        if(P1.isEmpty()||P2.isEmpty()||P3.isEmpty()||P4.isEmpty()||P5.isEmpty()||P6.isEmpty()){
                            if(P3.isEmpty()){
                                Names= P1+" , \n"+P2+".";
                            }else if(P4.isEmpty()){
                                Names= P1+" , \n"+P2+" , \n"+P3+".";
                            }else if(P5.isEmpty()){
                                Names= P1+" , \n"+P2+" , \n"+P3+" , \n"+P4+".";
                            }else if(P6.isEmpty()){
                                Names= P1+" , \n"+P2+" , \n"+P3+" , \n"+P4+" , \n"+P5+".";
                            }
                        }else{
                            Names= P1+" , \n"+P2+" , \n"+P3+" , \n"+P4+" , \n"+P5+" , \n"+P6+"." ;
                        }




                    }else{
                        Names=PassengerName;
                    }*/

                    String SEATS = SeatNos.replace("~", " ,");

                    String[] parts = APIRefNo.split("-");
                    String part1 = parts[0]; // 004

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                    Date myDate = null;
                    try {
                        myDate = dateFormat.parse(JourneyDate);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    SimpleDateFormat timeFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
                    String finalDate = timeFormat.format(myDate);

                    System.out.println("DATE FORMAT :" + finalDate);
                    tvBoards.setText("" + finalDate + ", " + BoardingTime);
                    Route.setText("" + SourceName + " To "+ DestinationName + "");
                    tvBookingdate.setText("" + BookingDate + "");
                    tvBoardingPoint.setText("Boarding- " + Location + "");
                    tvTRavel.setText("" + OperatorName + "\n" + BusTypeName);
                    tvSeat.setText("" + SEATS + "");
                    tvPassengers.setText("" + Names + "");
                    //tvBoardingpointDetails.setText("" + Address + "\n" + "" + Landmark + "\n" + Location);
                    tvTicketNumber.setText("" + refno + "");


                    /*String[] FareArray = ActualFare.split("~");
                    String[] ServiceTaxArray = ServiceTax.split("~");
                    String[] ServiceChargesArray = ServiceCharges.split("~");

                    PromoCodeAmountt = RoundOff(Double.parseDouble(PromoCodeAmount));


                    BaseFares = TotalChargesFromArray(FareArray, ActualFare);
                    ServiceTaxes = TotalChargesFromArray(ServiceTaxArray, ServiceTax);
                    ServiceChargess = TotalChargesFromArray(ServiceChargesArray, ServiceCharges);



                    if (!Convenienvce.equals("0")) {
                        ConvenienceFee = RoundOff(Double.parseDouble(Convenienvce));

                    }


                    Integer TotalFare=0;

                   *//* if (stringReturnReferencenumber != null) {
                        if (PromoCodeAmountt == 0) {
                            discountlayout.setVisibility(View.GONE);

                        } else {
                            discountlayout.setVisibility(View.VISIBLE);
                            TotalFare = (BaseFares + ServiceTaxes + ServiceChargess + ConvenienceFee);
                        }

                    } else {*//*
                        if (PromoCodeAmountt == 0) {
                            discountlayout.setVisibility(View.GONE);
                            TotalFare = (BaseFares + ServiceTaxes + ServiceChargess + ConvenienceFee);
                        } else {
                            discountlayout.setVisibility(View.VISIBLE);
                            if (stringReturnReferencenumber != null) {
                                TotalFare = (BaseFares + ServiceTaxes + ServiceChargess + ConvenienceFee);
                            }else{
                                TotalFare = (BaseFares + ServiceTaxes + ServiceChargess + ConvenienceFee) - PromoCodeAmountt;
                            }
                        }

                    //}


                    Actual.setText("₹ " + BaseFares);
                    GSTCharges.setText("₹ " + (ServiceTaxes + ServiceChargess));
                    Convenience.setText("₹ " + ConvenienceFee);
                    Promo.setText("- ₹ " + PromoCodeAmountt);


                    TON = TotalFare.toString();
                    TD = PromoCodeAmountt.toString();*/

                    Total.setText("₹ " + CollectedFare + "");
                    PNRNumber.setText("" + part1 + "");

                    if (stringReturnReferencenumber != null) {
                        new getReturnTktDetails().execute();
                    }
                }else{
                    Toast.makeText(ResponseActivity.this, "No data received try again later", Toast.LENGTH_SHORT).show();
                    NoTicket.setVisibility(View.VISIBLE);
                    TicketScroll.setVisibility(View.GONE);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            //progressDialogUtil.dismissProgressDialg();
            dialog.dismiss();


        }

    }


    public class getReturnTktDetails extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        @Override
        protected String doInBackground(String... params) {
            String strurl =Constants.BookingDetails + "referenceNo=" + stringReturnReferencenumber + "&type=2";
            DefaultHttpClient httpClient = new DefaultHttpClient();


            HttpGet httpGet = new HttpGet(strurl);
            httpGet.setHeader("ConsumerKey", Constants.ConsumerKey);
            httpGet.setHeader("ConsumerSecret", Constants.ConsumerSecret);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();


            try {

                HttpResponse response = httpClient.execute(httpGet);
                code = response.getStatusLine().getStatusCode();
                data = httpClient.execute(httpGet, responseHandler);

            } catch (IOException e) {
                e.printStackTrace();
            }


            return data;


        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                dialog.dismiss();
                System.out.println("BookingDetails" + code);


                JSONObject jsonObject = new JSONObject(data);


                String BookingDate = jsonObject.getString("BookingDate");
                String APIRefNo = jsonObject.getString("APIRefNo");
                String PromoCodeAmount = jsonObject.getString("PromoCodeAmount");
                String BoardingTime = jsonObject.optString("DepartureTime");
                String CollectedFare = jsonObject.getString("CollectedFare");
               /* String ActualFare = jsonObject.getString("Fares");
                String ServiceTax = jsonObject.optString("Servicetax");
                String ServiceCharges = jsonObject.optString("ServiceCharge");
                String Convenienvce = jsonObject.optString("ConvenienceFee");*/
                String SourceName = jsonObject.optString("SourceName");
                String DestinationName = jsonObject.optString("DestinationName");
                String JourneyDate = jsonObject.optString("JourneyDate");
                String OperatorName = jsonObject.optString("OperatorName");
                String BusTypeName = jsonObject.optString("BusTypeName");
                String PassengerName = jsonObject.optString("PassengerName");
                String SeatNos = jsonObject.optString("SeatNos");
                jsonObject.optString("CancellationPolicy");
                JSONObject BoardingDroppingDetails = jsonObject.getJSONObject("BoardingDroppingDetails");
                String Address = BoardingDroppingDetails.optString("Address");
                String Landmark = BoardingDroppingDetails.optString("Landmark");
                String Location = BoardingDroppingDetails.optString("Location");


                Names = PassengerName.replace("~",", \n");
                /*if(PassengerName.contains("~")){

                    StringTokenizer st = new StringTokenizer(PassengerName, "~");

                    while (st.hasMoreTokens()) {
                        try {
                            P1 = st.nextToken();
                            P2 = st.nextToken();
                            P3 = st.nextToken();
                            P4 = st.nextToken();
                            P5 = st.nextToken();
                            P6 = st.nextToken();
                        }catch (NoSuchElementException e){
                            ///Do Nothing, if next token is not found
                        }
                    }

                    if(P1.isEmpty()||P2.isEmpty()||P3.isEmpty()||P4.isEmpty()||P5.isEmpty()||P6.isEmpty()){
                        if(P3.isEmpty()){
                            Names= P1+" , \n"+P2+".";
                        }else if(P4.isEmpty()){
                            Names= P1+" , \n"+P2+" , \n"+P3+".";
                        }else if(P5.isEmpty()){
                            Names= P1+" , \n"+P2+" , \n"+P3+" , \n"+P4+".";
                        }else if(P6.isEmpty()){
                            Names= P1+" , \n"+P2+" , \n"+P3+" , \n"+P4+" , \n"+P5+".";
                        }
                    }else{
                        Names= P1+" , \n"+P2+" , \n"+P3+" , \n"+P4+" , \n"+P5+" , \n"+P6+"." ;
                    }




                }else{
                    Names=PassengerName;
                }*/
                String SEATS = SeatNos.replace("~", " ,");

                String[] parts = APIRefNo.split("-");
                String part1 = parts[0]; // 004


                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                Date myDate = null;
                try {
                    myDate = dateFormat.parse(JourneyDate);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                SimpleDateFormat timeFormat = new SimpleDateFormat("dd MMM,yyyy", Locale.ENGLISH);
                String finalDate = timeFormat.format(myDate);

                System.out.println("DATE FORMAT :" + finalDate);

                tvReturnBoards.setText("" + finalDate + " ,"+ BoardingTime);
                ReturnRoute.setText("" + SourceName + " To " + DestinationName + "");
                tvReturnBookingdtae.setText("" + BookingDate + "");
                tvReturnBoardingPoint.setText("Boarding- " + Location + "");
                tvReturnTRavel.setText("" + OperatorName + "\n" + BusTypeName);
                tvReturnSeat.setText("" + SEATS + "");
                tvReturnPassengers.setText("" + Names + "");
                //tvReturnBoardingpointDetails.setText("" + Address + "\n" + "" + Landmark + "\n" + Location);
                tvReturnTicketNumber.setText("" + stringReturnReferencenumber + "");

                Integer TotalFare=0;

                /*String[] FareArray = ActualFare.split("~");
                String[] ServiceTaxArray = ServiceTax.split("~");
                String[] ServiceChargesArray = ServiceCharges.split("~");
                ConvenienceFee = RoundOff(Double.parseDouble(Convenienvce));
                PromoCodeAmountt = RoundOff(Double.parseDouble(PromoCodeAmount));
                BaseFares = TotalChargesFromArray(FareArray, ActualFare);
                ServiceTaxes = TotalChargesFromArray(ServiceTaxArray, ServiceTax);
                ServiceChargess = TotalChargesFromArray(ServiceChargesArray, ServiceCharges);
               *//* if (PromoCodeAmountt == 0) {
                    discountlayoutReturn.setVisibility(View.GONE);
                    TotalFare = (BaseFares + ServiceTaxes + ServiceChargess + ConvenienceFee);
                } else {
                    discountlayoutReturn.setVisibility(View.VISIBLE);
                    TotalFare = (BaseFares + ServiceTaxes + ServiceChargess + ConvenienceFee) - PromoCodeAmountt;
                }*//*

                TotalFare = (BaseFares + ServiceTaxes + ServiceChargess + ConvenienceFee);



                ActualReturn.setText("₹ " + BaseFares);
                GSTChargesReturn.setText("₹ " + (ServiceTaxes + ServiceChargess));
                ConvenienceReturn.setText("₹ " + ConvenienceFee);
                PromoReturn.setText("- ₹ " + PromoCodeAmountt);



                TR = TotalFare.toString();*/


                tvReturnTotal.setText("₹ " + CollectedFare + "");
                tvReturnPNRNumber.setText("" + part1 + "");


                ReturnticketDetails.setVisibility(View.VISIBLE);
               //ReturnticketPNR.setVisibility(View.VISIBLE);
                //Total_Fare_LYT.setVisibility(View.VISIBLE);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

    public Integer TotalChargesFromArray(String[] FareArray, String ReportListPosition) {

        int Fares = 0;

        if (FareArray.length > 1) {
            List<String> itemList = Arrays.asList(FareArray);

            for (i = 0; i < itemList.size(); i++) {
                fares += RoundOff(Double.parseDouble(itemList.get(i)));
            }
        } else {
            fares = RoundOff(Double.parseDouble(ReportListPosition));
        }

        Fares = fares;
        fares = 0;

        return Fares;


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
    public void onBackPressed() {
        super.onBackPressed();



        if(!IsFromTrip.equals("Yes")) {
            Intent ip = new Intent(ResponseActivity.this, Buses_MainActivity.class);
            ip.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(ip);
        }else{
            finish();

        }
    }
}

