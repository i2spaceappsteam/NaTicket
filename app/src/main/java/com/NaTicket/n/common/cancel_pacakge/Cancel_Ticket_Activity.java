package com.NaTicket.n.common.cancel_pacakge;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.NaTicket.n.R;
import com.NaTicket.n.buses.pojo.Bus_Tickets_Pojo;
import com.NaTicket.n.flights.pojo.Flight_Ticket_Details_Pojo;
import com.NaTicket.n.holidays.pojo.Holiday_Ticket_Details_DTO;
import com.NaTicket.n.hotels.pojo.Hotel_Tickets_DTO;
import com.NaTicket.n.serviceclasses.Service_Cancellations;
import com.NaTicket.n.serviceclasses.Service_Reports;
import com.NaTicket.n.utils.BackActivity;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by Nagarjuna on 15-01-2018.
 */

public class Cancel_Ticket_Activity extends BackActivity implements View.OnClickListener {


    EditText referenceno, Email;
    TextView Error_Reference_Number, Error_Email, Error_message, Error_Invalid_Reference;
    TextView Cancel;
    ProgressDialog mProgressDialog;
    String Refernce_No, EMAIL;
    int code;
    String data;
    String ArrivalTime, DisptTime, Cancelticket;
    int TotalFare;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cancel_ticket);
        inittoolbar();
        TextView toolbar_title = (TextView) findViewById(R.id.toolbartitle);
        toolbar_title.setText("" + "Cancel Ticket" + "");


        referenceno = (EditText) findViewById(R.id.Reference_No);
        Email = (EditText) findViewById(R.id.email_cancel);
        Error_Reference_Number = (TextView) findViewById(R.id.error_Reference_no);
        Error_Email = (TextView) findViewById(R.id.error_Email_id);
        Error_message = (TextView) findViewById(R.id.error_cancel_ticket);
        Cancel = (TextView) findViewById(R.id.Cancel_Ticket);
        Error_Invalid_Reference = (TextView) findViewById(R.id.Invalid_Reference);

        Cancel.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Cancel_Ticket:
                if (validatevalues()) {
                    showAlertDialog("Do you wish to cancel this ticket ?");
                }
                break;
        }
    }


    /////////////////Bus Details///////////////////////////////////////

    public void callBusTicketDetails() {

        showProgressDialog("Please Wait, fetching ticket details...", this);
        if (Util.isNetworkAvailable(Cancel_Ticket_Activity.this)) {
            Service_Cancellations.GetTicketDetails(Cancel_Ticket_Activity.this, Constants.BookingDetails + "referenceNo=" + Refernce_No + "&type=2");
        } else {
            Util.showMessage(Cancel_Ticket_Activity.this, Constants.NO_INT_MSG);
        }
    }

    public void getTicketsResponse(String response, int StatusCode) {
        hideProgressDialog();
        if (response != null) {
            if (StatusCode == 200) {
                if (Util.getBookingStatus(response).equals("Cancelled")) {
                    //showAlertDialogforCancellation("Your ticket has already been cancelled.");
                    Util.showMessage(this, "This ticket has already been cancelled");
                    Util.Vibrate(this);
                } else {


                    String Emailid = Util.getEmailID(response);
                    if (EMAIL.equals(Emailid)) {
                        try {
                            InputStream stream = new ByteArrayInputStream(response.getBytes());
                            Gson gson = new GsonBuilder().serializeNulls().create();
                            Reader reader = new InputStreamReader(stream);
                            Bus_Tickets_Pojo bus_ticket = gson.fromJson(reader, Bus_Tickets_Pojo.class);
                            Intent ip = new Intent(Cancel_Ticket_Activity.this, Cancel_Bus_Ticket.class);
                            ip.putExtra("Bus_Ticket_Details", bus_ticket);
                            startActivity(ip);
                        } catch (Exception e) {
                            System.out.println("Exception: " + e);

                        }
                    } else {
                        Util.Vibrate(this);
                        Error_message.setVisibility(View.VISIBLE);
                        Error_Email.setVisibility(View.GONE);
                        Error_Reference_Number.setVisibility(View.GONE);
                    }

                }
            } else {
                Util.Vibrate(this);
                Error_Email.setVisibility(View.GONE);
                Error_Reference_Number.setVisibility(View.GONE);
                Error_Invalid_Reference.setVisibility(View.VISIBLE);
            }


        } else {
            Util.Vibrate(this);
            Error_Email.setVisibility(View.GONE);
            Error_Reference_Number.setVisibility(View.GONE);
            Error_Invalid_Reference.setVisibility(View.VISIBLE);
        }

    }


   /* public void errorresponse (String response){
        hideProgressDialog();
        showAlertDialogforError("Timed out! \nTry again");
    }*/


    /////////////////Flight Details///////////////////////////////////////


    private void callGetFlightTicketDetails() {
        if (Util.isNetworkAvailable(Cancel_Ticket_Activity.this)) {
            showProgressDialog("Please Wait, fetching ticket details...", this);
            Service_Cancellations.GetCancelFlightTicket_Details(Cancel_Ticket_Activity.this, Constants.FlightTicketDetails + Refernce_No + "&type=2");
        } else {
            Util.showMessage(Cancel_Ticket_Activity.this, Constants.NO_INT_MSG);
        }
    }


    public void getTicketDetailsResponse(String response, int StatusCode) {
        hideProgressDialog();
        if (response != null) {
            if (StatusCode == 200) {
                if (Util.getBookingStatus(response).equals("Cancelled")) {
                    //showAlertDialogforCancellation("Your ticket has already been cancelled.");
                    Util.showMessage(this, "This ticket has already been cancelled");
                    Util.Vibrate(this);
                } else {


                    String Emailid = Util.getEmailID(response);
                    if (EMAIL.equals(Emailid)) {
                        try {

                            InputStream stream = new ByteArrayInputStream(response.getBytes());
                            Gson gson = new GsonBuilder().serializeNulls().create();
                            Reader reader = new InputStreamReader(stream);
                            Flight_Ticket_Details_Pojo flight_ticket_details = gson.fromJson(reader, Flight_Ticket_Details_Pojo.class);
                            Intent ip = new Intent(Cancel_Ticket_Activity.this, Cancel_Flight_Ticket.class);
                            ip.putExtra("Flight_Details", flight_ticket_details);
                            startActivity(ip);
                        } catch (Exception e) {
                            System.out.println("Exception: " + e);

                        }
                    } else {
                        Util.Vibrate(this);
                        Error_message.setVisibility(View.VISIBLE);
                        Error_Email.setVisibility(View.GONE);
                        Error_Reference_Number.setVisibility(View.GONE);
                    }

                }
            } else {
                Util.Vibrate(this);
                Error_Email.setVisibility(View.GONE);
                Error_Reference_Number.setVisibility(View.GONE);
                Error_Invalid_Reference.setVisibility(View.VISIBLE);
            }
        } else {
            Util.Vibrate(this);
            Error_Email.setVisibility(View.GONE);
            Error_Reference_Number.setVisibility(View.GONE);
            Error_Invalid_Reference.setVisibility(View.VISIBLE);
        }
    }


    /////////////////Hotel Details///////////////////////////////////////


    private void callGetHotelTicketDetails() {
        if (Util.isNetworkAvailable(Cancel_Ticket_Activity.this)) {
            showProgressDialog("Please Wait, fetching ticket details...", this);
            Service_Cancellations.GetCancelHotelTicket_Details(Cancel_Ticket_Activity.this, Constants.GETHOTELDETAILS + Refernce_No + "&type=2");
        } else {
            Util.showMessage(Cancel_Ticket_Activity.this, Constants.NO_INT_MSG);
        }
    }


    public void getHotelTicketDetailsResponse(String response, int StatusCode) {
                hideProgressDialog();
                if (response != null) {
                    if (StatusCode == 200) {
                if (Util.getTaxResponseCode(response) == 5) {
                    //showAlertDialogforCancellation("Your ticket has already been cancelled.");
                    Util.showMessage(this, "This ticket has already been cancelled");
                    Util.Vibrate(this);
                } else if (Util.getTaxResponseCode(response) == 1) {
                    //showAlertDialogforCancellation("Your ticket has already been cancelled.");
                    Util.showMessage(this, "This ticket is in pending state");
                    Util.Vibrate(this);
                } else {


                    String Emailid = Util.getEmailID(response);
                    if (EMAIL.equals(Emailid)) {
                        try {

                            InputStream stream = new ByteArrayInputStream(response.getBytes());
                            Gson gson = new GsonBuilder().serializeNulls().create();
                            Reader reader = new InputStreamReader(stream);
                            Hotel_Tickets_DTO hotel_ticket_details = gson.fromJson(reader, Hotel_Tickets_DTO.class);
                            Intent ip = new Intent(Cancel_Ticket_Activity.this, Cancel_Hotel_Ticket.class);
                            ip.putExtra("Hotel_Details", hotel_ticket_details);
                            startActivity(ip);
                        } catch (Exception e) {
                            System.out.println("Exception: " + e);

                        }
                    } else {
                        Util.Vibrate(this);
                        Error_message.setVisibility(View.VISIBLE);
                        Error_Email.setVisibility(View.GONE);
                        Error_Reference_Number.setVisibility(View.GONE);
                    }

                }
            } else {
                Util.Vibrate(this);
                Error_Email.setVisibility(View.GONE);
                Error_Reference_Number.setVisibility(View.GONE);
                Error_Invalid_Reference.setVisibility(View.VISIBLE);
            }
        } else {
            Util.Vibrate(this);
            Error_Email.setVisibility(View.GONE);
            Error_Reference_Number.setVisibility(View.GONE);
            Error_Invalid_Reference.setVisibility(View.VISIBLE);
        }
    }


    /////////////////Holiday Details///////////////////////////////////////


    private void callGetholidayDetails() {
        if (Util.isNetworkAvailable(getApplicationContext())) {
            showProgressDialog("Please wait", this);
            Service_Cancellations.GetHolidayTicketDetails(Cancel_Ticket_Activity.this, Constants.GETHOLIDAYDETAILS + Refernce_No + "&type=2");
        } else {
            Util.showMessage(Cancel_Ticket_Activity.this, Constants.NO_INT_MSG);
        }
    }

    public void getHolTicketDetailsResponse(String response, int StatusCode) {
        hideProgressDialog();
        if (response != null) {

            if (StatusCode == 200) {
                if (Util.getBookingStatus(response).equals("Cancelled")) {
                    //showAlertDialogforCancellation("Your ticket has already been cancelled.");
                    Util.showMessage(this, "This ticket has already been cancelled");
                    Util.Vibrate(this);
                } else {


                    String Emailid = Util.getEmailID(response);
                    if (EMAIL.equals(Emailid)) {
                        try {
                            InputStream stream = new ByteArrayInputStream(response.getBytes());
                            Gson gson = new Gson();
                            Reader reader = new InputStreamReader(stream);
                            Holiday_Ticket_Details_DTO holiday_ticket_details = gson.fromJson(reader, Holiday_Ticket_Details_DTO.class);
                            if (holiday_ticket_details != null) {

                                Intent ip = new Intent(Cancel_Ticket_Activity.this, Cancel_Hotel_Ticket.class);
                                ip.putExtra("Holiday_Details", holiday_ticket_details);
                                startActivity(ip);
                            }

                        } catch (Exception e) {
                            System.out.println("Exception: " + e);

                        }
                    } else {
                        Util.Vibrate(this);
                        Error_message.setVisibility(View.VISIBLE);
                        Error_Email.setVisibility(View.GONE);
                        Error_Reference_Number.setVisibility(View.GONE);
                    }
                }

            } else {
                Util.Vibrate(this);
                Error_Email.setVisibility(View.GONE);
                Error_Reference_Number.setVisibility(View.GONE);
                Error_Invalid_Reference.setVisibility(View.VISIBLE);
            }
        } else {
            Util.Vibrate(this);
            Error_Email.setVisibility(View.GONE);
            Error_Reference_Number.setVisibility(View.GONE);
            Error_Invalid_Reference.setVisibility(View.VISIBLE);

        }
    }


    private boolean validatevalues() {
        if (!Util.checkField(referenceno)) {
            Util.showMessage(Cancel_Ticket_Activity.this, "Enter Reference number");
            return false;
        } else if (!Util.checkField(Email)) {
            Util.showMessage(Cancel_Ticket_Activity.this, "Enter Email");
            return false;
        } else if (!Util.validateEmail(Email.getText().toString())) {
            Util.showMessage(Cancel_Ticket_Activity.this, "Enter Valid Email");
            return false;
        }
        return true;
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

    private void showAlertDialog(String s) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cancel_Ticket_Activity.this);
        ////alertDialog.setTitle("Alert!");
        alertDialog.setMessage(s);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Refernce_No = referenceno.getText().toString();
                EMAIL = Email.getText().toString();

                if (Refernce_No.startsWith("100")) {
                    //Buses
                    callBusTicketDetails();
                } else if (Refernce_No.startsWith("300")) {
                    ///DomesticFlights
                    //new FlightTicketCancel().execute();
                    callGetFlightTicketDetails();
                } else if (Refernce_No.startsWith("400")) {
                    //InternationalFlights
                    callGetFlightTicketDetails();
                    //new FlightTicketCancel().execute();
                } else if (Refernce_No.startsWith("500")) {
                    //DomesticHotels
                    //new HotelTicketCancel().execute();
                    callGetHotelTicketDetails();

                } else if (Refernce_No.startsWith("180")) {
                    //InternationalHotels
                    //new HotelTicketCancel().execute();
                    callGetHotelTicketDetails();
                } else if (Refernce_No.startsWith("600")) {
                    //Holidays Packages

                } else {
                    Toast.makeText(Cancel_Ticket_Activity.this, "Invalid Reference Number", Toast.LENGTH_SHORT).show();
                }



               /*
                    if (Refernce_No.startsWith("200"))
                    return Service.Cabs;
                if (referenceNumber.StartsWith("700"))
                    return Service.Recharge;
                if (referenceNumber.StartsWith("900"))
                    return Service.Ecommerce;
                if (referenceNumber.StartsWith("110"))
                    return Service.BillPayments;
                if (referenceNumber.StartsWith("170"))
                    return Service.Transfers;

                if (referenceNumber.StartsWith("190"))
                    return Service.InternationalCabs;*/
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


    private void showAlertDialogforError(String s) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        ////alertDialog.setTitle("Alert!");
        alertDialog.setMessage(s);
        alertDialog.setCancelable(false);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (validatevalues()) {
                    showAlertDialog("Do you wish to cancel this ticket ?");
                }
            }
        });
        alertDialog.show();
    }

    private void showAlertDialogforCancellation(String s) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cancel_Ticket_Activity.this);
        ////alertDialog.setTitle("Alert!");
        alertDialog.setMessage(s);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Util.startHomeActivity(Cancel_Ticket_Activity.this);
            }
        });
    }


}

