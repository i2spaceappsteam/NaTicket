package com.NaTicket.n.holidays;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.NaTicket.n.R;
import com.NaTicket.n.holidays.pojo.Holiday_Ticket_Details_DTO;
import com.NaTicket.n.serviceclasses.Service_Reports;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;
import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by Nagarjuna on 10/11/2017.
 */
public class Holiday_Booking_Details_Activity extends AppCompatActivity {

    ProgressDialog mProgressDialog;
    String Referneceno;

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



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.holiday_booking_details);


        TextView ToolBar_Title= (TextView) findViewById(R.id.toolbartitle);
        ToolBar_Title.setText(""+"Ticket Details"+"");
        ImageView ToolBar_Back= (ImageView) findViewById(R.id.backBtn);
        ToolBar_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ip=new Intent(Holiday_Booking_Details_Activity.this,HolidaySearchActivity.class);
                ip.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(ip);
            }
        });

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
        tvBookingDate= (TextView) findViewById(R.id.holBookingDate);
        tvDestination= (TextView) findViewById(R.id.destination);

        //new Holiday_Details().execute();
          callGetholidayDetails();
    }

    private void callGetholidayDetails(){
        if(Util.isNetworkAvailable(getApplicationContext())) {
            showProgressDialog("Please wait",this);
            Service_Reports.GetHolidayTicketDetails(Holiday_Booking_Details_Activity.this, Constants.GETHOLIDAYDETAILS+Referneceno+"&type=2");
        }else{
            Util.showMessage(Holiday_Booking_Details_Activity.this, Constants.NO_INT_MSG);
        }
    }

    public void getTicketDetailsResponse(String response){
        hideProgressDialog();
        if (response!=null){
            InputStream stream = new ByteArrayInputStream(response.getBytes());
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(stream);
            Holiday_Ticket_Details_DTO holiday_ticket_details = gson.fromJson(reader,Holiday_Ticket_Details_DTO.class);
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


        double Total=Fares+Convince+Taxes-PromoAmount;
        double TotalAmount=Util.getprice(Total*Double.valueOf(holiday_ticket_details.getCurrencyValue()));


        tvJourneydate.setText(""+holiday_ticket_details.getJourneyDate()+"");
        tvTotalFare.setText(Currency_Symbol+TotalAmount+"");
        tvRoomType.setText(""+holiday_ticket_details.getRoomType()+"");
        tvRefNo.setText(""+holiday_ticket_details.getBookingRefNo()+"");
        tvCategoryName.setText(""+holiday_ticket_details.getSubCategoryName()+"");
        tvDuration.setText(""+Nights+" Nights / "+days+" Days");
        tvRooms.setText(""+holiday_ticket_details.getRooms()+"");
        tvPax.setText(""+holiday_ticket_details.getAdultCount()+" Adults, "+holiday_ticket_details.getChildCount()+" Children");
        //tvBookingDate.setText(""+finaldate+"");

        String Name=holiday_ticket_details.getName();
        String NameArray=Name.replace("|"," ");
        tvBookingDate.setText(""+NameArray+"");
        tvDestination.setText(""+holiday_ticket_details.getDestinationName()+"");
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent ip=new Intent(Holiday_Booking_Details_Activity.this,HolidaySearchActivity.class);
        ip.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(ip);
    }

    public int getprice(double fares){
        double dAbs = Math.abs(fares);
        int amt2 = (int) dAbs;
        double result = dAbs - (double) amt2;
        int finalrate;
        if (result<0.5){
            finalrate= (int) Math.floor(fares);
        }else {
            finalrate= (int) Math.ceil(fares);
        }
        return finalrate;
    }
}
