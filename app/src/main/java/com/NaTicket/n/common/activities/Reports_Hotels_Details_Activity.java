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

import com.NaTicket.n.common.cancel_pacakge.Cancel_Hotel_Ticket;
import com.google.gson.Gson;
import com.NaTicket.n.R;
import com.NaTicket.n.hotels.pojo.Hotel_Tickets_DTO;
import com.NaTicket.n.serviceclasses.Service_Cancellations;
import com.NaTicket.n.serviceclasses.Service_Reports;
import com.NaTicket.n.utils.BackActivity;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by Nagarjuna on 9/19/2017.
 */

public class Reports_Hotels_Details_Activity extends BackActivity {

    private TextView tvHtlCheckIn;
    private TextView tvHtlCheckout;
    private TextView tvRefNo;
    private TextView tvNoOfDays;
    private TextView tvNoOfRooms;
    private TextView tvHtlName;
    private TextView tvHtlAddress;
    private TextView tvHtlrate;
    private TextView tvHtlBookingDate;
    private  TextView tvConfiramtionid;
    private  TextView tvRoomType;
    private TextView  tvPassengers;
    ProgressDialog mProgressDialog;
    String Referneceno;
    TextView Cancel_Ticket_Report;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reports_hotel_booking_details);

        inittoolbar();
        TextView ToolBar_Title= (TextView) findViewById(R.id.toolbartitle);
        ToolBar_Title.setText(""+"Ticket Details"+"");

        Bundle extras = getIntent().getExtras();
        Referneceno=extras.getString("referencenumber");

        tvHtlCheckIn = ((TextView) findViewById(R.id.tvCheckinDate));
        tvHtlCheckout = ((TextView) findViewById(R.id.tvCheckoutDate));
        tvRefNo = ((TextView) findViewById(R.id.tvRefNo));
        tvNoOfDays = ((TextView) findViewById(R.id.tvNoOfDays));
        tvNoOfRooms = ((TextView) findViewById(R.id.tvNoOfRoms));
        tvHtlName = ((TextView) findViewById(R.id.tvHtlName));
        tvHtlAddress = ((TextView) findViewById(R.id.tvHtlAddress));
        tvHtlrate= (TextView) findViewById(R.id.rate);
        tvHtlBookingDate= (TextView) findViewById(R.id.BookedDate);
        tvConfiramtionid= (TextView) findViewById(R.id.tv_allocation_id);
        tvRoomType= (TextView) findViewById(R.id.room_type);
        tvPassengers= (TextView) findViewById(R.id.passengers);

        Cancel_Ticket_Report= (TextView) findViewById(R.id.Cancel_Ticket_Report);
        Cancel_Ticket_Report.setVisibility(View.VISIBLE);

        Cancel_Ticket_Report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog("Do you wish to cancel this Ticket?");
            }
        });

        callHotelDetails();
    }


    private void callHotelDetails(){
        if(Util.isNetworkAvailable(getApplicationContext())) {
            showProgressDialog("Please wait",this);
            Service_Reports.GetHotelDetails(Reports_Hotels_Details_Activity.this, Constants.GETHOTELDETAILS+Referneceno+"&type=2");
        }else{
            Util.showMessage(Reports_Hotels_Details_Activity.this, Constants.NO_INT_MSG);
        }
    }

    public void getTicketsResponse(String response){
        hideProgressDialog();
        if (response!=null){
            InputStream stream = new ByteArrayInputStream(response.getBytes());
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(stream);
            Hotel_Tickets_DTO hotel_tickets_pojo = gson.fromJson(reader,Hotel_Tickets_DTO.class);
            if (hotel_tickets_pojo!=null){
                setHoteldata(hotel_tickets_pojo);
            }
        }
    }

    public void setHoteldata(Hotel_Tickets_DTO hotelticket){

        double Fares= Double.parseDouble(hotelticket.getFare());
        //double Convince=Double.parseDouble(String.valueOf(hotelticket.getHotelDetail().getConvenienceFee()));
        double PromoAmount=Double.parseDouble(String.valueOf(hotelticket.getPromoCodeAmount()));


        //double Total=Fares+Convince-PromoAmount;
        double Total=Fares-PromoAmount;

        double TotalAmount=Util.getprice(Total*Double.valueOf(hotelticket.getCurrencyValue()));
        String Currency=hotelticket.getCurrencyID();
        String Currency_Symbol = null;
        switch (Currency) {
            case "INR":
                Currency_Symbol = "₹ ";
                break;
            case "USD":
                Currency_Symbol = "$ ";
                break;
        }


        String ADT[]=hotelticket.getAdults().split("~");
        String CHD[]=hotelticket.getChildren().split("~");
        int m = 0,n=0;
        for (int p=0;p<ADT.length;p++){
            m += Integer.parseInt(ADT[p]);
            n +=Integer.parseInt(CHD[p]);
        }
        tvPassengers.setText("Adult(s) : "+m+" , "+"Children : "+n);

        tvHtlCheckIn.setText(""+hotelticket.getArrivalDate()+"");
        tvHtlCheckout.setText(""+hotelticket.getDepartureDate()+"");
        tvHtlName.setText(""+hotelticket.getHotelDetail().getHotelName()+"");
        tvHtlAddress.setText(""+hotelticket.getHotelDetail().getHotelAddress()+"");
        tvNoOfDays.setText(""+hotelticket.getNoOfdays()+"");
        tvNoOfRooms.setText(""+hotelticket.getRooms()+"");
        tvRefNo.setText(""+hotelticket.getBookingRefNo()+"");
        tvHtlrate.setText(Currency_Symbol+TotalAmount+"");
        tvConfiramtionid.setText(""+hotelticket.getAllocid()+"");

        for (int p=0;p<hotelticket.getRoomDetails().size();p++){
                String RoomType=hotelticket.getRoomDetails().get(p).getRoomType();
                tvRoomType.setText(""+RoomType+"");
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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Reports_Hotels_Details_Activity.this);
        ////alertDialog.setTitle("Alert!");
        alertDialog.setMessage(s);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                callGetHotelTicketDetails();
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


    /////////////////Hotel Cancellation///////////////////////////////////////


    private void callGetHotelTicketDetails(){
        if(Util.isNetworkAvailable(Reports_Hotels_Details_Activity.this)) {
            showProgressDialog("Please Wait, fetching ticket details...",this);
            Service_Cancellations.GetCancelHotelTicket_DetailsCan(Reports_Hotels_Details_Activity.this, Constants.GETHOTELDETAILS+Referneceno+"&type=2");
        }else{
            Util.showMessage(Reports_Hotels_Details_Activity.this, Constants.NO_INT_MSG);
        }
    }


    public void getHotelTicketDetailsResponse(String response, int StatusCode) {
        hideProgressDialog();
        if (response != null) {
            if (StatusCode == 200) {
                if (Util.getTaxResponseCode(response)!=3) {
                    showAlertDialogforCancellation("Your ticket has already been cancelled.");
                } else {

                        try {

                            InputStream stream = new ByteArrayInputStream(response.getBytes());
                            Gson gson = new Gson();
                            Reader reader = new InputStreamReader(stream);
                            Hotel_Tickets_DTO hotel_ticket_details = gson.fromJson(reader, Hotel_Tickets_DTO.class);
                            Intent ip = new Intent(Reports_Hotels_Details_Activity.this, Cancel_Hotel_Ticket.class);
                            ip.putExtra("Hotel_Details", hotel_ticket_details);
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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Reports_Hotels_Details_Activity.this);
        ////alertDialog.setTitle("Alert!");
        alertDialog.setMessage(s);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Util.startHomeActivity(Reports_Hotels_Details_Activity.this);
            }
        });
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}