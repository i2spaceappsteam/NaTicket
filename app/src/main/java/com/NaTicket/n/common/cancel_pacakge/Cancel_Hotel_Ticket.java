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
 * Created by Nagarjuna on 9/7/2017.
 */
public class Cancel_Hotel_Ticket extends BackActivity {


    ProgressDialog dialog;
    int code;
    String data;
    String Referneceno;

    Hotel_Tickets_DTO ticket_details;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotel_cancellation);
        inittoolbar();
        TextView toolbartitle = (TextView) findViewById(R.id.toolbartitle);
        toolbartitle.setText("Cancel Hotel Booking");


        ticket_details = (Hotel_Tickets_DTO) getIntent().getSerializableExtra("Hotel_Details");

        Referneceno=ticket_details.getBookingRefNo();


        TextView tvHtlCheckIn = ((TextView) findViewById(R.id.tvCheckinDate));
        TextView tvHtlCheckout = ((TextView) findViewById(R.id.tvCheckoutDate));
        TextView tvRefNo = ((TextView) findViewById(R.id.tvRefNo));
        TextView tvNoOfDays = ((TextView) findViewById(R.id.tvNoOfDays));
        TextView tvNoOfRooms = ((TextView) findViewById(R.id.tvNoOfRoms));
        TextView tvHtlName = ((TextView) findViewById(R.id.tvHtlName));
        TextView tvHtlAddress = ((TextView) findViewById(R.id.tvHtlAddress));
        TextView tvCancel= (TextView) findViewById(R.id.Cancel_Ticket);
        TextView tvCancellationPolicy= (TextView) findViewById(R.id.Cancellation_policy);
        TextView tvTotal_fare= (TextView) findViewById(R.id.Total_fare);
        TextView tvRefund= (TextView) findViewById(R.id.Cancellation_Mode);


        tvRefNo.setText("Ref no. : "+Referneceno+"");
        tvHtlAddress.setText(""+ticket_details.getHotelDetail().getHotelAddress()+"");
        tvHtlName.setText(""+ticket_details.getHotelDetail().getHotelName()+"");
        tvHtlCheckIn.setText(""+ticket_details.getArrivalDate()+"");
        tvHtlCheckout.setText(""+ticket_details.getDepartureDate()+"");
        tvNoOfDays.setText(""+ticket_details.getNoOfdays()+"");
        tvNoOfRooms.setText(""+ticket_details.getRooms()+"");
        tvCancellationPolicy.setText(""+ticket_details.getRoomDetails().get(0).getRoomCancellationPolicy()+"");
        tvRefund.setText(""+ticket_details.getRoomDetails().get(0).getRefundRule()+"");

        double Fares= Double.parseDouble(ticket_details.getFare());
        double Convince=Double.parseDouble(String.valueOf(ticket_details.getHotelDetail().getConvenienceFee()));
        double PromoAmount=Double.parseDouble(String.valueOf(ticket_details.getPromoCodeAmount()));
//        double Total=Fares+Convince-PromoAmount;
        double Total=Fares-PromoAmount;
        double TotalAmount= Util.getprice(Total*Double.valueOf(ticket_details.getCurrencyValue()));

        tvTotal_fare.setText(Constants.AGENT_CURRENCY_SYMBOL+" "+TotalAmount+"");

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Cancel_Hotel().execute();
            }
        });

    }

    public class Cancel_Hotel extends AsyncTask<String,String,String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(Cancel_Hotel_Ticket.this, "", "Please Wait  .....");
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
                    Toast.makeText(Cancel_Hotel_Ticket.this,
                            "Error occurred", Toast.LENGTH_LONG).show();
                    // Error_Invalid_Reference.setVisibility(View.VISIBLE);
                }else {
                    JSONObject jsonObject = null;
                    JSONObject ResultObject = new JSONObject(data);
                    String Booking_Status=ResultObject.getString("BookingStatus");
                    String Message=ResultObject.getString("Message");

                    if(Booking_Status!=null){

                            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                    Cancel_Hotel_Ticket.this).create();
                            //alertDialog.setTitle("! Confirmation");
                            alertDialog.setMessage(Message);
                            alertDialog.setIcon(R.mipmap.ic_launcher);
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent ip=new Intent(Cancel_Hotel_Ticket.this,Cancel_Ticket_Activity.class);
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
