package com.NaTicket.n.common.cancel_pacakge;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.NaTicket.n.buses.pojo.Bus_Tickets_Pojo;
import com.NaTicket.n.common.MainActivity;
import com.NaTicket.n.serviceclasses.ServiceHandler;
import com.NaTicket.n.utils.BackActivity;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.R;
import com.NaTicket.n.utils.Util;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Nagarjuna on 16-01-2018.
 */

public class Cancel_Bus_Ticket extends BackActivity {

    TextView activelfare, Reference_no, Route, cencellation, partailCancellation, Ticket_Cancel, Operator,Journey;
    ToggleButton seat1, seat2, seat3, seat4, seat5, seat6;
    TextView num1, num2, num3, num4, num5, num6;
    String cancalpolicy = "";
    String polcy;
    String Refernce_No, EMAIL_ID;
    String NoofSeats, SeatNos, Partial="false";
    String Message;
    ArrayList<String> selectedStrings = new ArrayList<String>();
    String SelectedSeats;
    String seatCount[];
    Bus_Tickets_Pojo bus_tickets;

    String data;
    int code;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_cancel_activity);
        inittoolbar();
        TextView ToolBar_Title= (TextView) findViewById(R.id.toolbartitle);
        ToolBar_Title.setText(""+"Cancel Ticket"+"");
        bus_tickets= (Bus_Tickets_Pojo) getIntent().getSerializableExtra("Bus_Ticket_Details");
        initviews();
    }

    private void initviews() {
        Reference_no = (TextView) findViewById(R.id.Enter_reference_no);
        Route = (TextView) findViewById(R.id.route);
        activelfare = (TextView) findViewById(R.id.Total_fare);
        cencellation = (TextView) findViewById(R.id.Cancellation_policy);
        partailCancellation = (TextView) findViewById(R.id.Partial);
        Ticket_Cancel = (TextView) findViewById(R.id.Ticket_Cancel);
        Operator = (TextView) findViewById(R.id.operator_name);
        Journey = (TextView) findViewById(R.id.journey_date);


        seat1 = (ToggleButton) findViewById(R.id.seat1);
        seat2 = (ToggleButton) findViewById(R.id.seat2);
        seat3 = (ToggleButton) findViewById(R.id.seat3);
        seat4 = (ToggleButton) findViewById(R.id.seat4);
        seat5 = (ToggleButton) findViewById(R.id.seat5);
        seat6 = (ToggleButton) findViewById(R.id.seat6);


        num1 = (TextView) findViewById(R.id.seat_num1);
        num2 = (TextView) findViewById(R.id.seat_num2);
        num3 = (TextView) findViewById(R.id.seat_num3);
        num4 = (TextView) findViewById(R.id.seat_num4);
        num5 = (TextView) findViewById(R.id.seat_num5);
        num6 = (TextView) findViewById(R.id.seat_num6);

        try {
            double Fare = Double.parseDouble(String.valueOf(bus_tickets.getActualFare()));
            double Convinec = Double.parseDouble(String.valueOf(bus_tickets.getConvenienceFee()));
            double promo = Double.parseDouble(String.valueOf(bus_tickets.getPromoCodeAmount()));
           // double Total = (Fare - promo) + Convinec;
            double Total = (Fare - promo);


            Reference_no.setText("Ref No. : " + bus_tickets.getBookingRefNo() + "");
            Route.setText("" + bus_tickets.getSourceName() + "  To  " + bus_tickets.getDestinationName());
            activelfare.setText(Constants.AGENT_CURRENCY_SYMBOL + " " + Util.getprice(Total) + "");
            Operator.setText(bus_tickets.getOperatorName());
            Journey.setText(bus_tickets.getJourneyDate());

            NoofSeats = bus_tickets.getNoofSeats();
            SeatNos = bus_tickets.getSeatNos();
            String seatCount[] = SeatNos.split("~");
            System.out.println("seatCount" + seatCount[0]);
            num1.setText(seatCount[0]);
            setvisibility();

            String cancelArray[] = bus_tickets.getCancellationPolicy().split(";");
            System.out.println("split" + cancelArray[0]);

            for (int i = 0; i < cancelArray.length; i++) {
                String fromCancelArray[] = cancelArray[i].split(":");
                if (fromCancelArray.length > 1) {
                    String cancelTimeStr;
                    if (fromCancelArray[1].equals("-1")) {
                        cancelTimeStr = (fromCancelArray[0] + "   hours before journey time          ");
                    } else {
                        cancelTimeStr = (fromCancelArray[1] + "   hours before journey time           " + fromCancelArray[0]);
                    }

                    String percentSybole = "%";
                    String percentStr = (fromCancelArray[2] + ".0 " + percentSybole);


                    polcy = cancelTimeStr + percentStr;


                    System.out.println("data" + cancelTimeStr + percentStr);
                }

                cancalpolicy = "" + cancalpolicy + "" + polcy + "\n";
            }
            if (bus_tickets.getCancellationPolicy().equals("")) {
                cencellation.setText("" + "No Cancellation policy found" + "");
            } else {
                cencellation.setText("" + cancalpolicy + "");
            }
            if (bus_tickets.getPartialCancellationAllowed()) {
                Partial = "true";
                partailCancellation.setText("" + "Partial Cancellation is Allowed" + "");
            } else {
                partailCancellation.setText("" + "Partial Cancellation is not Allowed" + "");
            }


        }catch (NullPointerException e){

        }
    }


    public void setvisibility(){
        String seatCount[] = SeatNos.split("~");
        if (NoofSeats.equals("2")) {
            num2.setVisibility(View.VISIBLE);
            seat2.setVisibility(View.VISIBLE);
            num2.setText(seatCount[1]);
        } else if (NoofSeats.equals("3")) {
            num2.setVisibility(View.VISIBLE);
            seat2.setVisibility(View.VISIBLE);
            num3.setVisibility(View.VISIBLE);
            seat3.setVisibility(View.VISIBLE);
            num2.setText(seatCount[1]);
            num3.setText(seatCount[2]);
        } else if (NoofSeats.equals("4")) {
            num2.setVisibility(View.VISIBLE);
            seat2.setVisibility(View.VISIBLE);
            num3.setVisibility(View.VISIBLE);
            seat3.setVisibility(View.VISIBLE);
            num4.setVisibility(View.VISIBLE);
            seat4.setVisibility(View.VISIBLE);
            num2.setText(seatCount[1]);
            num3.setText(seatCount[2]);
            num4.setText(seatCount[3]);
        } else if (NoofSeats.equals("5")) {
            num2.setVisibility(View.VISIBLE);
            seat2.setVisibility(View.VISIBLE);
            num3.setVisibility(View.VISIBLE);
            seat3.setVisibility(View.VISIBLE);
            num4.setVisibility(View.VISIBLE);
            seat4.setVisibility(View.VISIBLE);
            num5.setVisibility(View.VISIBLE);
            seat5.setVisibility(View.VISIBLE);
            num2.setText(seatCount[1]);
            num3.setText(seatCount[2]);
            num4.setText(seatCount[3]);
            num5.setText(seatCount[4]);
        } else if (NoofSeats.equals("6")) {
            num2.setVisibility(View.VISIBLE);
            seat2.setVisibility(View.VISIBLE);
            num3.setVisibility(View.VISIBLE);
            seat3.setVisibility(View.VISIBLE);
            num4.setVisibility(View.VISIBLE);
            seat4.setVisibility(View.VISIBLE);
            num5.setVisibility(View.VISIBLE);
            seat5.setVisibility(View.VISIBLE);
            num6.setVisibility(View.VISIBLE);
            seat6.setVisibility(View.VISIBLE);
            num2.setText(seatCount[1]);
            num3.setText(seatCount[2]);
            num4.setText(seatCount[3]);
            num5.setText(seatCount[4]);
            num6.setText(seatCount[5]);
        }
        seatcheck(seat1,num1);
        seatcheck(seat2,num2);
        seatcheck(seat3,num3);
        seatcheck(seat4,num4);
        seatcheck(seat5,num5);
        seatcheck(seat6,num6);

        Ticket_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (seat1.isChecked() || seat2.isChecked() || seat3.isChecked() || seat4.isChecked() || seat5.isChecked() || seat6.isChecked()) {
                    System.out.println("Checked" + selectedStrings);
                    if (Partial.equals("true")){
                        SelectedSeats = TextUtils.join(",", selectedStrings);
                        new CancelTicket().execute();
                    }else {
                        int noofseats= Integer.parseInt(NoofSeats);
                        if (selectedStrings.size()==noofseats){
                            SelectedSeats = TextUtils.join(",", selectedStrings);
                            new CancelTicket().execute();
                        }else {
                            Util.showMessage(Cancel_Bus_Ticket.this,"Partial cancellation is not allowed");
                        }
                    }
                } else {
                    Toast.makeText(Cancel_Bus_Ticket.this,
                            "Please Select at least one seat", Toast.LENGTH_LONG).show();
                }
            }
        });


    }


    public class CancelTicket extends AsyncTask<String, String, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(Cancel_Bus_Ticket.this, "", "Please Wait  under Process.....");
        }


        @Override
        protected String doInBackground(String... params) {
            String strurl = Constants.BASEURL + Constants.CancelBusTicket + "referenceNo=" + bus_tickets.getBookingRefNo() + "&seatNos=" + SelectedSeats + "&emailId=" + bus_tickets.getEmailId();
            DefaultHttpClient httpClient = new DefaultHttpClient();


            HttpGet httpGet = new HttpGet(strurl);
            httpGet.setHeader("ConsumerKey", Constants.ConsumerKey);
            httpGet.setHeader("ConsumerSecret", Constants.ConsumerSecret);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();


            ServiceHandler sh = new ServiceHandler();
            data = sh.makeServiceCall(strurl, ServiceHandler.GET);

            System.out.println("JSON LENGTH" + strurl);
            /*try {
                HttpResponse response = httpClient.execute(httpGet);
                code = response.getStatusLine().getStatusCode();
                data = httpClient.execute(httpGet, responseHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }*/


            return data;


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //  JSONObject jsonObject = null;
            try {


                System.out.println("BookingDetails" + data);

                dialog.dismiss();

                if (code == 403) {
                    Toast.makeText(Cancel_Bus_Ticket.this,
                            "Error occurred", Toast.LENGTH_LONG).show();
                    // Error_Invalid_Reference.setVisibility(View.VISIBLE);
                } else {
                    JSONObject jsonObject = new JSONObject(data);

                    String Status = jsonObject.getString("BookingStatus");
                    String TicketNumber = jsonObject.getString("TicketNumber");
                    String TotalTicketFare = jsonObject.optString("TotalTicketFare");
                    String TotalRefundAmount = jsonObject.optString("TotalRefundAmount");
                    Message = jsonObject.optString("Message");
                    String CancellationCharges = jsonObject.optString("CancellationCharges");
                    String RefundType = jsonObject.optString("RefundType");
                    String isSeatCancellable = jsonObject.optString("isSeatCancellable");
                    String PartialCancellationAllowed = jsonObject.optString("PartialCancellationAllowed");

                    if (Status.equals("5")) {
                        showDialog();
                    } else if (Status.equals("6")) {
                        showDialog();
                    } else {
                        showDialog();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


    public void showDialog() {
        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                Cancel_Bus_Ticket.this).create();
        alertDialog.setMessage(Message);
        alertDialog.setCancelable(false);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                Intent ip = new Intent(Cancel_Bus_Ticket.this, MainActivity.class);
                ip.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(ip);
                //finish();
            }
        });
        alertDialog.show();
    }

    private void seatcheck(final ToggleButton toggleButton,final TextView number){

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                if (ischecked){
                    toggleButton.setBackgroundResource(R.drawable.redseat);
                    selectedStrings.add(number.getText().toString());

                }else {
                    toggleButton.setBackgroundResource(R.drawable.green_seat);
                    selectedStrings.remove(number.getText().toString());

                }
            }
        });

    }
}
