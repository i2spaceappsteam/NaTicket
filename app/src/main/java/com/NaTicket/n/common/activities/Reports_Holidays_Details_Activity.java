package com.NaTicket.n.common.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.NaTicket.n.R;
import com.NaTicket.n.common.cancel_pacakge.Cancel_Holiday_Ticket;
import com.NaTicket.n.common.cancel_pacakge.Cancel_Hotel_Ticket;
import com.NaTicket.n.holidays.ReviewHolidayActivity;
import com.NaTicket.n.holidays.pojo.Holiday_Ticket_Details_DTO;
import com.NaTicket.n.hotels.pojo.Hotel_Tickets_DTO;
import com.NaTicket.n.serviceclasses.Service_Cancellations;
import com.NaTicket.n.serviceclasses.Service_Reports;
import com.NaTicket.n.utils.BackActivity;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;
import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by Nagarjuna on 1/10/2018.
 */

public class Reports_Holidays_Details_Activity extends BackActivity{


    String Referneceno;
    ProgressDialog mProgressDialog;
    private TextView tvHolidayName;
    private TextView tvJourneydate;
    private TextView  tvPax;
    private TextView tvTotalFare;
    private TextView tvRoomType;
    private TextView tvRefNo;
    private TextView tvCategoryName;
    private TextView tvDuration;
    private TextView tvRooms;
    private TextView tvBookingDate;
    private TextView tvDestination;
    TextView Cancel_Ticket_Report;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.holiday_report_details);


        inittoolbar();
        TextView ToolBar_Title= (TextView) findViewById(R.id.toolbartitle);
        ToolBar_Title.setText(""+"Ticket Details"+"");


        Bundle extras = getIntent().getExtras();
        Referneceno=extras.getString("referencenumber");

        tvHolidayName= (TextView) findViewById(R.id.holPackName);
        tvJourneydate= (TextView) findViewById(R.id.holJourneyDate);
        tvPax= (TextView) findViewById(R.id.holPax);
        tvTotalFare= (TextView) findViewById(R.id.holfare);
        tvRoomType= (TextView) findViewById(R.id.holPacType);
        tvRefNo= (TextView) findViewById(R.id.tvRefNo);
        tvCategoryName= (TextView) findViewById(R.id.holCategory);
        tvDuration= (TextView) findViewById(R.id.holDuration);
        tvRooms= (TextView) findViewById(R.id.tvNoOfRoms);
        tvDestination= (TextView) findViewById(R.id.destination);
        tvBookingDate= (TextView) findViewById(R.id.holBookingDate);

        Cancel_Ticket_Report= (TextView) findViewById(R.id.Cancel_Ticket_Report);
        Cancel_Ticket_Report.setVisibility(View.VISIBLE);

        Cancel_Ticket_Report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog("Do you wish to cancel this Ticket?");
            }
        });


        callGetHolidayReports();
    }


    private void callGetHolidayReports(){
        if(Util.isNetworkAvailable(getApplicationContext())) {
            showProgressDialog("Please wait",this);
            Service_Reports.GetHolidayReportsDetails(Reports_Holidays_Details_Activity.this, Constants.GETHOLIDAYDETAILS+Referneceno+"&type=2");
        }else{
            Util.showMessage(Reports_Holidays_Details_Activity.this, Constants.NO_INT_MSG);
        }
    }

    public void getTicketDetailsResponse(String response){
        hideProgressDialog();
        if (response!=null){
            InputStream stream = new ByteArrayInputStream(response.getBytes());
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(stream);
            Holiday_Ticket_Details_DTO   holiday_ticket_details = gson.fromJson(reader,Holiday_Ticket_Details_DTO.class);
            if (holiday_ticket_details!=null){
                setHolidayData(holiday_ticket_details);
            }
        }
    }

    public  void setHolidayData(Holiday_Ticket_Details_DTO holiday_ticket_details){
        tvHolidayName.setText(holiday_ticket_details.getHolidayPackageName());
        String Currency= holiday_ticket_details.getCurrencyID();
        String Currency_Symbol = null;
        switch (Currency) {
            case "INR":
                Currency_Symbol = "₹ ";
                break;
            case "USD":
                Currency_Symbol = "$ ";
                break;
        }

        int Nights=holiday_ticket_details.getDuration();
        int days=Nights+1;


        double Fares= holiday_ticket_details.getTotalFare();
        double Taxes=holiday_ticket_details.getServiceCharge();
        double Convince=holiday_ticket_details.getConveniencefee();
        double PromoAmount=holiday_ticket_details.getPromoCodeAmount();

        //double Total=Fares+Convince+Taxes-PromoAmount;
        double Total=Fares;
        double TotalAmount=Util.getprice(Total*Double.valueOf(holiday_ticket_details.getCurrencyValue()));
        String FareDesc;
       /* if(PromoAmount==0) {
            FareDesc = "Fare : " + Currency_Symbol + " " + Fares + "\n" +
                    "GST : " + Currency_Symbol + " " + Taxes + "\n" +
                    "Conven. Fee : " + Currency_Symbol + " " + Convince + "\n" +
                    "Total : " + Currency_Symbol + " " + TotalAmount + "\n";
        }else{
            FareDesc = "Fare : " + Currency_Symbol + " " + Fares + "\n" +
                    "GST : " + Currency_Symbol + " " + Taxes + "\n" +
                    "Conven. Fee : " + Currency_Symbol + " " + Convince + "\n" +
                    "Discount : " + Currency_Symbol + " " + PromoAmount + "\n" +
                    "Total : " + Currency_Symbol + " " + TotalAmount + "\n";
        }*/




        tvJourneydate.setText(""+holiday_ticket_details.getJourneyDate()+"");
        tvTotalFare.setText(Currency_Symbol+String.valueOf(TotalAmount));
        tvRoomType.setText(""+holiday_ticket_details.getRoomType()+"");
        tvRefNo.setText(""+holiday_ticket_details.getBookingRefNo()+"");
        tvCategoryName.setText(""+holiday_ticket_details.getSubCategoryName()+"");
        tvDuration.setText(""+Nights+" Nights / "+days+" Days");
        tvRooms.setText(""+holiday_ticket_details.getRooms()+"");
        tvPax.setText(""+holiday_ticket_details.getAdultCount()+" Adults, "+holiday_ticket_details.getChildCount()+" Children");
        //tvBookingDate.setText(""+finaldate+"");
        tvDestination.setText(""+holiday_ticket_details.getDestinationName()+"");

         String Name=holiday_ticket_details.getName();
         String NameArray=Name.replace("|"," ");
         tvBookingDate.setText(""+NameArray+"");
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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Reports_Holidays_Details_Activity.this);
        ////alertDialog.setTitle("Alert!");
        alertDialog.setMessage(s);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                callGetHolidayTicketDetails();
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


    /////////////////Holiday Cancellation///////////////////////////////////////


    private void callGetHolidayTicketDetails(){
        if(Util.isNetworkAvailable(Reports_Holidays_Details_Activity.this)) {
            showProgressDialog("Please Wait, fetching ticket details...",this);
            Service_Cancellations.callGetHolidayTicketDetails(Reports_Holidays_Details_Activity.this, Constants.GETHOLIDAYDETAILS+Referneceno+"&type=2");
        }else{
            Util.showMessage(Reports_Holidays_Details_Activity.this, Constants.NO_INT_MSG);
        }
    }


    public void GetHolidayTicketDetailsResponse(String response, int StatusCode) {
        hideProgressDialog();
        if (response != null) {
            if (StatusCode == 200) {
                if (!Util.getBookingStatus(response).equals("3")) {
                    showAlertDialogforCancellation("Your ticket has already been cancelled.");
                } else {

                    try {

                        InputStream stream = new ByteArrayInputStream(response.getBytes());
                        Gson gson = new Gson();
                        Reader reader = new InputStreamReader(stream);
                        Holiday_Ticket_Details_DTO hotel_ticket_details = gson.fromJson(reader, Holiday_Ticket_Details_DTO.class);
                        Intent ip = new Intent(Reports_Holidays_Details_Activity.this, Cancel_Holiday_Ticket.class);
                        ip.putExtra("Holiday_Details", hotel_ticket_details);
                        startActivity(ip);
                    } catch (Exception e) {
                        System.out.println("Exception: " + e);

                    }


                }
            } else {
                Util.Vibrate(this);

            }
        }else {
            Util.Vibrate(this);

        }
    }

    private void showAlertDialogforCancellation(String s) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Reports_Holidays_Details_Activity.this);
        ////alertDialog.setTitle("Alert!");
        alertDialog.setMessage(s);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Util.startHomeActivity(Reports_Holidays_Details_Activity.this);
            }
        });
    }
}
