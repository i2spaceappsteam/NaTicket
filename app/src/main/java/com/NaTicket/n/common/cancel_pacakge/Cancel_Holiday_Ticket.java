package com.NaTicket.n.common.cancel_pacakge;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.NaTicket.n.R;
import com.NaTicket.n.holidays.pojo.Holiday_Ticket_Details_DTO;
import com.NaTicket.n.hotels.pojo.Hotel_Tickets_DTO;
import com.NaTicket.n.utils.BackActivity;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Ankit on 13-02-2018.
 */

public class Cancel_Holiday_Ticket extends BackActivity {


    ProgressDialog dialog;
    int code;
    String data;
    String Referneceno;

    Holiday_Ticket_Details_DTO ticket_details;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.holiday_cancellation);
        inittoolbar();
        TextView toolbartitle = (TextView) findViewById(R.id.toolbartitle);
        toolbartitle.setText("Cancel Holiday Booking");


        ticket_details = (Holiday_Ticket_Details_DTO) getIntent().getSerializableExtra("Holiday_Details");




        TextView tvHolPack = ((TextView) findViewById(R.id.Package_Details));
        TextView tvHolJour_Details = ((TextView) findViewById(R.id.Journey_Details));
        TextView tvRefNo = ((TextView) findViewById(R.id.Enter_reference_no));
        TextView tvPax = ((TextView) findViewById(R.id.PaxHoliday));
        TextView tvCancel= (TextView) findViewById(R.id.Ticket_Cancel);
        TextView tvCancellationPolicy= (TextView) findViewById(R.id.can_policy);
        TextView tvTotal_fare= (TextView) findViewById(R.id.Total_fare);

        int Nights=ticket_details.getDuration();
        int days=Nights+1;

        Referneceno=ticket_details.getBookingRefNo();
        tvRefNo.setText("Ref no.-"+Referneceno+"");
        tvHolPack.setText(""+ticket_details.getHolidayPackageName()+"\n"+ticket_details.getRooms()+" Room(s)"+"\nRoom Type: "+ticket_details.getRoomType()+"\nDestination : "+ticket_details.getDestinationName());
        tvHolJour_Details.setText(""+ticket_details.getJourneyDate()+"\nDuration: "+Nights+" Nights / "+days+" Days");

        String Name=ticket_details.getName();
        String NameArray=Name.replace("|"," ");

        tvPax.setText(NameArray+"\n"+ticket_details.getAdultCount()+" Adults, "+ticket_details.getChildCount()+" Children");
        tvCancellationPolicy.setText(""+ticket_details.getCancellationPolicy()+"");


        double Fares= ticket_details.getTotalFare();
        tvTotal_fare.setText(Constants.AGENT_CURRENCY_SYMBOL+" "+Fares+"");

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Cancel_Holiday_Ticket.this, "Cancel from web", Toast.LENGTH_SHORT).show();
                //new Cancel_Hotel().execute();
            }
        });

    }

    public class Cancel_Hotel extends AsyncTask<String,String,String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(Cancel_Holiday_Ticket.this, "", "Please Wait  .....");
        }

        @Override
        protected String doInBackground(String... strings) {
            String strurl = Constants.CANCELHOTEL + Referneceno;
            DefaultHttpClient httpClient = new DefaultHttpClient();


            HttpGet httpGet = new HttpGet(strurl);
            httpGet.setHeader("ConsumerKey", Constants.ConsumerKey);
            httpGet.setHeader("ConsumerSecret", Constants.ConsumerSecret);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            try {
                data = httpClient.execute(httpGet, responseHandler);
                HttpResponse response = httpClient.execute(httpGet);
                code = response.getStatusLine().getStatusCode();
                System.out.println("BookingDetails " + strurl + "Data  " + data);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                System.out.println("BookingDetails" + data);
                dialog.dismiss();
                if (code == 500) {
                    Toast.makeText(Cancel_Holiday_Ticket.this,
                            "Error occurred", Toast.LENGTH_LONG).show();
                    // Error_Invalid_Reference.setVisibility(View.VISIBLE);
                }else {
                    JSONObject jsonObject = null;
                    JSONObject ResultObject = new JSONObject(data);
                    String Booking_Status=ResultObject.getString("BookingStatus");
                    String Message=ResultObject.getString("Message");

                    if(Booking_Status!=null){

                        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                Cancel_Holiday_Ticket.this).create();
                        //alertDialog.setTitle("! Confirmation");
                        alertDialog.setMessage(Message);
                        alertDialog.setIcon(R.mipmap.ic_launcher);
                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent ip=new Intent(Cancel_Holiday_Ticket.this,Cancel_Ticket_Activity.class);
                                ip.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(ip);
                                finish();
                            }
                        });
                        alertDialog.show();


                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
