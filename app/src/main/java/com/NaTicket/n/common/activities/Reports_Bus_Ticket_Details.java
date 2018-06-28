package com.NaTicket.n.common.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.NaTicket.n.serviceclasses.Service_Cancellations;
import com.NaTicket.n.utils.BackActivity;
import com.NaTicket.n.utils.DialogUtils;
import com.google.gson.Gson;
import com.NaTicket.n.R;
import com.NaTicket.n.buses.pojo.Bus_Tickets_Pojo;
import com.NaTicket.n.common.cancel_pacakge.Cancel_Bus_Ticket;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Nagarjuna on 1/10/2018.
 */

public class Reports_Bus_Ticket_Details extends BackActivity {

    private static final String TAG = "ResponseActivity";

    ProgressDialog dialog;
    String data;
    int code;
    String newString, stringname, stringmessage, stringReturnReferencenumber;
    Button clickhere;
    String P1="",P2="",P3="",P4="",P5="",P6="";

    String refno,EmailID;
    TextView tvBookingdate, Route, tvDisnationName, tvBoards, tvBoardingPoint, tvTRavel, tvSeat, tvPassengers,
            tvBoardingpointDetails, tvTicketNumber, Total, PNRNumber/*, Actual, Promo, GSTCharges, Convenience*/;
    String TON, TR, TD, TNF;
    ProgressDialog mProgressDialog;

    String Names;

    LinearLayout discountlayout, discountlayoutReturn;
    TextView Cancel_Ticket_Report;


    int i;
    Integer BaseFares = 0, ServiceTaxes = 0, ServiceChargess = 0, ConvenienceFee = 0, PromoCodeAmountt = 0, fares = 0;


    protected void onCreate(Bundle savedInstanstate) {
        super.onCreate(savedInstanstate);
        setContentView(R.layout.bus_ticket_details);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanstate == null) {
            Bundle bundle = getIntent().getExtras();
            if (null != bundle) {

                refno = bundle.getString("Onwardrefno");




            } else {
                newString = null;
                refno = null;
                stringmessage = null;
                stringname = null;
            }
        } else {

            refno = (String) savedInstanstate.getSerializable("refno");

        }






        //ConvenienceFeeType = preference.getString("ConvenienceFeeType", null);

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
        /*Actual = (TextView) findViewById(R.id.Actual);
        GSTCharges = (TextView) findViewById(R.id.GSTAndCharges);
        Convenience = (TextView) findViewById(R.id.Convence);
        Promo = (TextView) findViewById(R.id.promoAmt);*/
        //discountlayout = (LinearLayout) findViewById(R.id.discountlayout);
        //discountlayout.setVisibility(View.GONE);




        if (Util.isNetworkAvailable(this)) {
            new Reports_Bus_Ticket_Details.getOnwardTktDetails().execute();
        } else {
            DialogUtils dialogUtils = new DialogUtils(this);
            dialogUtils.showNetworkErrorDialog();
        }



        Cancel_Ticket_Report= (TextView) findViewById(R.id.Cancel_Ticket_Report);
        Cancel_Ticket_Report.setVisibility(View.VISIBLE);

        Cancel_Ticket_Report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog("Do you wish to cancel this Ticket?");
            }
        });

    }

    /*@Override
    public void onClick(View v) {
        if (v == clickhere) {
            if (newString.equals("1") || newString.equals("4") || newString.equals("3")) {
                Intent i = new Intent(Reports_Bus_Ticket_Details.this, Buses_MainActivity.class);
                startActivity(i);
            }
        }
    }*/


    public class getOnwardTktDetails extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(Reports_Bus_Ticket_Details.this, "", "Loading Ticket...");
            dialog.setCancelable(false);
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
                    EmailID=jsonObject.optString("EmailId");
                    jsonObject.optString("CancellationPolicy");
                    JSONObject BoardingDroppingDetails = jsonObject.getJSONObject("BoardingDroppingDetails");
                    String Address = BoardingDroppingDetails.optString("Address");
                    String Landmark = BoardingDroppingDetails.optString("Landmark");
                    String Location = BoardingDroppingDetails.optString("Location");




                    Names = PassengerName.replace("~",", \n");


                    StringTokenizer names = new StringTokenizer(PassengerName, "~", true);
                    /*StringTokenizer ages = new StringTokenizer(Age, "~", true);
                    StringTokenizer genders = new StringTokenizer(Gender, "~", true);
                    List<String> resultList = new List<>();
                    while(names.hasMoreTokens()) {
                        StringBuilder single = new StringBuilder();
                        single.append(names.nextToken()+"-");
                        single.append(ages.nextToken()+"-");
                        single.append(genders.nextToken());
                        resultList.add(single.toString());
                    }
                    String result = String.join(",", resultList);*/



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

                    //String Names = PassengerName.replace("~", " ,");
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


                    Integer TotalFare;

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
                        TotalFare = (BaseFares + ServiceTaxes + ServiceChargess + ConvenienceFee) - PromoCodeAmountt;
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


                }else{
                    Toast.makeText(Reports_Bus_Ticket_Details.this, "No data received try again later", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            //progressDialogUtil.dismissProgressDialg();
            dialog.dismiss();


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

    }


    public void callBusTicketDetails(){
        showProgressDialog("Please Wait",Reports_Bus_Ticket_Details.this);
        if (Util.isNetworkAvailable(Reports_Bus_Ticket_Details.this)){
            Service_Cancellations.GetTicketDetailsCan(Reports_Bus_Ticket_Details.this,Constants.BookingDetails+ "referenceNo=" + refno + "&type=2");
        }else {
            Util.showMessage(Reports_Bus_Ticket_Details.this, Constants.NO_INT_MSG);
        }
    }
    public void getTicketsResponse(String response, int StatusCode) {
        hideProgressDialog();
        if (response!=null){
            if (StatusCode==200){
                if (Util.getBookingStatus(response).equals("Cancelled")){
                    showAlertDialogforCancellation("Your ticket has been already cancelled.");
                }else {
                    try {
                            InputStream stream = new ByteArrayInputStream(response.getBytes());
                            Gson gson = new Gson();
                            Reader reader = new InputStreamReader(stream);
                            Bus_Tickets_Pojo bus_ticket = gson.fromJson(reader, Bus_Tickets_Pojo.class);
                            Intent ip = new Intent(Reports_Bus_Ticket_Details.this, Cancel_Bus_Ticket.class);
                            ip.putExtra("Bus_Ticket_Details", bus_ticket);
                            startActivity(ip);
                        }catch(Exception e){
                            System.out.println("Exception: "+e);

                        }
                }
            }  else {
                Toast.makeText(this, "Try again", Toast.LENGTH_SHORT).show();
            }


        }

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

    private void showAlertDialog(String s) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Reports_Bus_Ticket_Details.this);
        ////alertDialog.setTitle("Alert!");
        alertDialog.setMessage(s);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                callBusTicketDetails();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.show();
    }

    private void showAlertDialogforCancellation(String s) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Reports_Bus_Ticket_Details.this);
        ////alertDialog.setTitle("Alert!");
        alertDialog.setMessage(s);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Util.startHomeActivity(Reports_Bus_Ticket_Details.this);
            }
        });
    }


}

