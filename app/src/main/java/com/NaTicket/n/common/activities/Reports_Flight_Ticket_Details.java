package com.NaTicket.n.common.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.NaTicket.n.common.cancel_pacakge.Cancel_Flight_Ticket;
import com.NaTicket.n.flights.pojo.Flight_Ticket_Details_Pojo;
import com.NaTicket.n.serviceclasses.Service_Cancellations;
import com.NaTicket.n.serviceclasses.Service_Reports;
import com.NaTicket.n.utils.BackActivity;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.NaTicket.n.R;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Nagarjuna on 1/6/2018.
 */

public class Reports_Flight_Ticket_Details extends BackActivity {

    String newString,stringname,stringmessage;
    String data, refno;
    Flight_Ticket_Details_Pojo flight_ticket_details;
    ProgressDialog mProgressDialog;
    LinearLayout flight_ticket_scrool;
    TextView Cancel_Ticket_Report;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_ticket_new_details);

        inittoolbar();
        TextView ToolBar_Title= (TextView) findViewById(R.id.toolbartitle);
        ToolBar_Title.setText(""+"Ticket Details"+"");

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
                refno=null;
                stringmessage=null;
                stringname=null;
            } else {
                newString= extras.getString("status");
                refno=extras.getString("refno");
                stringname=extras.getString("name");
                stringmessage=extras.getString("msg");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("status");
            refno=(String)savedInstanceState.getSerializable("refno");
            stringname=(String)savedInstanceState.getSerializable("name");
            stringmessage=(String)savedInstanceState.getSerializable("msg");
        }
        initViews();
        callGetFlightTicketDetails();
    }


    private void callGetFlightTicketDetails(){
        if(Util.isNetworkAvailable(getApplicationContext())) {
            showProgressDialog("Please wait",this);
            Service_Reports.GetFlightTicket_Details(Reports_Flight_Ticket_Details.this, Constants.FlightTicketDetails+refno+"&type=2");
        }else{
            Util.showMessage(Reports_Flight_Ticket_Details.this, Constants.NO_INT_MSG);
        }
    }

    @SuppressWarnings("all")
    public void getTicketDetailsResponse(String response){
        hideProgressDialog();
        if (response!=null){
            InputStream stream = new ByteArrayInputStream(response.getBytes());
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(stream);
            try {
             flight_ticket_details = gson.fromJson(reader, Flight_Ticket_Details_Pojo.class);
            } catch (JsonParseException e) {
               e.printStackTrace();
            }
            if (flight_ticket_details!=null){
                setFlightData();
            }
        }
    }



        public void setFlightData(){
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            for (int p=0;p<flight_ticket_details.getOnwardFlightSegments().size();p++){
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewHolder = inflater.inflate(R.layout.flights_tickets_top, null);
                LinearLayout linearLayout = (LinearLayout) viewHolder.findViewById(R.id.flights_layout);
                TextView from= (TextView) viewHolder.findViewById(R.id.source);
                TextView to= (TextView) viewHolder.findViewById(R.id.destination);
                ImageView Airline_Image= (ImageView) viewHolder.findViewById(R.id.AirlineImage);
                TextView FlightNumber= (TextView) viewHolder.findViewById(R.id.flightNumber);
                TextView FromAirport= (TextView) viewHolder.findViewById(R.id.FromAirport);
                TextView ToAirport= (TextView) viewHolder.findViewById(R.id.ToAirport);
                TextView FromDate= (TextView) viewHolder.findViewById(R.id.Departure_Date);
                TextView ToDate= (TextView) viewHolder.findViewById(R.id.Arrival_Date);
                TextView Baggage= (TextView) viewHolder.findViewById(R.id.Baggage);
                TextView FromTime= (TextView) viewHolder.findViewById(R.id.departure_time);
                TextView ToTime= (TextView) viewHolder.findViewById(R.id.arrival_time);


                String CheckInBaggage=flight_ticket_details.getOnwardFlightSegments().get(0).getBaggageAllowed().getCheckInBaggage();
                String Handbag=flight_ticket_details.getOnwardFlightSegments().get(0).getBaggageAllowed().getHandBaggage();


                Baggage.setText("Hand : "+Handbag+"");

                if(!CheckInBaggage.equals("")){
                    Baggage.setText("Check-In : "+CheckInBaggage+"\nHand: "+Handbag+"");
                }

                String From_Place=flight_ticket_details.getOnwardFlightSegments().get(p).getDepartureAirportCode();
                String To_Place=flight_ticket_details.getOnwardFlightSegments().get(p).getArrivalAirportCode();

                String From_Time=Util.getTime(flight_ticket_details.getOnwardFlightSegments().get(p).getDepartureDateTime());
                String To_Time=Util.getTime(flight_ticket_details.getOnwardFlightSegments().get(p).getArrivalDateTime());



                String Duration;
                int FlightsegmentsSize=flight_ticket_details.getOnwardFlightSegments().size()-1;
                if (flight_ticket_details.getOnwardFlightSegments().size()==1){
                    Duration=flight_ticket_details.getOnwardFlightSegments().get(0).getDuration();
                }else {
                    Duration=flight_ticket_details.getOnwardFlightSegments().get(FlightsegmentsSize).getAccumulatedDuration();
                }


                String url= Constants.FLIGHTS_IMAGE_URL+flight_ticket_details.getOnwardFlightSegments().get(p).getImageFileName()+".png";

                Glide.with(this)
                        .load(url)
                        .placeholder(R.drawable.image_placeholder)
                        .error(R.drawable.image_placeholder)
                        .into(Airline_Image);


                //Bitmap bitmap= Util.getImage(this,flight_ticket_details.getOnwardFlightSegments().get(p).getImageFileName());
                //Airline_Image.setImageBitmap(bitmap);


                from.setText(""+From_Place+"");
                to.setText(""+To_Place+"");
                FromDate.setText(Util.getDate(flight_ticket_details.getOnwardFlightSegments().get(p).getDepartureDateTime()));
                ToDate.setText(Util.getDate(flight_ticket_details.getOnwardFlightSegments().get(p).getArrivalDateTime()));
                FromTime.setText(From_Time);
                ToTime.setText(To_Time);
                FlightNumber.setText(flight_ticket_details.getOnwardFlightSegments().get(p).getOperatingAirlineCode()+"-"+flight_ticket_details.getOnwardFlightSegments().get(p).getOperatingAirlineFlightNumber());
                FromAirport.setText(""+flight_ticket_details.getOnwardFlightSegments().get(p).getIntDepartureAirportName()+"");
                ToAirport.setText(""+flight_ticket_details.getOnwardFlightSegments().get(p).getIntArrivalAirportName()+"");
                ll.addView(linearLayout);
            }

            if (flight_ticket_details.getReturnFlightSegments()!=null&&flight_ticket_details.getReturnFlightSegments().size()!=0) {
                for (int p = 0; p < flight_ticket_details.getReturnFlightSegments().size(); p++) {
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View viewHolder = inflater.inflate(R.layout.flights_tickets_top, null);
                    LinearLayout linearLayout = (LinearLayout) viewHolder.findViewById(R.id.flights_layout);
                    TextView from = (TextView) viewHolder.findViewById(R.id.source);
                    TextView to = (TextView) viewHolder.findViewById(R.id.destination);
                    TextView FlightNumber= (TextView) viewHolder.findViewById(R.id.flightNumber);
                    ImageView Airline_Image = (ImageView) viewHolder.findViewById(R.id.AirlineImage);
                    TextView FromAirport = (TextView) viewHolder.findViewById(R.id.FromAirport);
                    TextView ToAirport = (TextView) viewHolder.findViewById(R.id.ToAirport);
                    TextView FromDate = (TextView) viewHolder.findViewById(R.id.Departure_Date);
                    TextView ToDate = (TextView) viewHolder.findViewById(R.id.Arrival_Date);
                    TextView Baggage= (TextView) viewHolder.findViewById(R.id.Baggage);
                    TextView FromTime = (TextView) viewHolder.findViewById(R.id.departure_time);
                    TextView ToTime = (TextView) viewHolder.findViewById(R.id.arrival_time);


                    String CheckInBaggage=flight_ticket_details.getReturnFlightSegments().get(0).getBaggageAllowed().getCheckInBaggage();
                    String Handbag=flight_ticket_details.getReturnFlightSegments().get(0).getBaggageAllowed().getHandBaggage();

                    if(!CheckInBaggage.equals("")&&!Handbag.equals("")){

                        Baggage.setText("Check-In : "+CheckInBaggage+"\nHand: "+Handbag+"");
                    }else{
                        if(CheckInBaggage.equals("")){
                            Baggage.setText("Hand : "+Handbag+"");
                        }else if(Handbag.equals("")){
                            Baggage.setText("Check-In : "+CheckInBaggage+"");
                        }
                    }

                    String From_Place = flight_ticket_details.getReturnFlightSegments().get(p).getDepartureAirportCode();
                    String To_Place = flight_ticket_details.getReturnFlightSegments().get(p).getArrivalAirportCode();

                    String From_Time = Util.getTime(flight_ticket_details.getReturnFlightSegments().get(p).getDepartureDateTime());
                    String To_Time = Util.getTime(flight_ticket_details.getReturnFlightSegments().get(p).getArrivalDateTime());


                    String Duration;
                    int FlightsegmentsSize = flight_ticket_details.getReturnFlightSegments().size() - 1;
                    if (flight_ticket_details.getOnwardFlightSegments().size() == 1) {
                        Duration = flight_ticket_details.getReturnFlightSegments().get(0).getDuration();
                    } else {
                        Duration = flight_ticket_details.getReturnFlightSegments().get(FlightsegmentsSize).getAccumulatedDuration();
                    }

                    //Bitmap bitmap = Util.getImage(this, flight_ticket_details.getReturnFlightSegments().get(p).getImageFileName());
                    //Airline_Image.setImageBitmap(bitmap);


                    String url= Constants.FLIGHTS_IMAGE_URL+flight_ticket_details.getReturnFlightSegments().get(p).getImageFileName()+".png";

                    Glide.with(this)
                            .load(url)
                            .placeholder(R.drawable.image_placeholder)
                            .error(R.drawable.image_placeholder)
                            .into(Airline_Image);

                    from.setText(""+From_Place+"");
                    to.setText(""+To_Place+"");
                    FromDate.setText(Util.getDate(flight_ticket_details.getReturnFlightSegments().get(p).getDepartureDateTime()));
                    ToDate.setText(Util.getDate(flight_ticket_details.getReturnFlightSegments().get(p).getArrivalDateTime()));
                    FlightNumber.setText(flight_ticket_details.getReturnFlightSegments().get(p).getOperatingAirlineCode()+"-"+flight_ticket_details.getReturnFlightSegments().get(p).getOperatingAirlineFlightNumber());
                    FromTime.setText(From_Time);
                    ToTime.setText(To_Time);
                    FromAirport.setText(""+flight_ticket_details.getReturnFlightSegments().get(p).getIntDepartureAirportName()+"");
                    ToAirport.setText(""+flight_ticket_details.getReturnFlightSegments().get(p).getIntArrivalAirportName()+"");
                    ll.addView(linearLayout);
                }
            }

            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewHolder = inflater.inflate(R.layout.flights_tickets_bottom, null);
            RelativeLayout linearLayout = (RelativeLayout) viewHolder.findViewById(R.id.flights_bottom);

            TextView Passengers=(TextView)viewHolder.findViewById(R.id.Passengers_Names);
            TextView TicketNumber=(TextView)viewHolder.findViewById(R.id.Reference_No);
            TextView PNRNumber=(TextView)viewHolder.findViewById(R.id.PNRNumber);
            TextView BookingDate= (TextView)viewHolder.findViewById(R.id.booking_date);

            LinearLayout Return_Fare_layout= (LinearLayout) viewHolder.findViewById(R.id.return_fare_layout);

            TextView OnwardFare_TV= (TextView) viewHolder.findViewById(R.id.onwardfare_tv);
            TextView ReturnFare_TV= (TextView) viewHolder.findViewById(R.id.return_Fare_tv);




            double OnwardBaseFare= Double.parseDouble(flight_ticket_details.getActualBaseFare());
            double OnwardTax= Double.parseDouble(flight_ticket_details.getTax());
            double OnwardTcharge= Double.parseDouble(flight_ticket_details.getTCharge());
            double OnwardTMarkup= Double.parseDouble(flight_ticket_details.getTMarkup());
            double OnwardSTax= Double.parseDouble(flight_ticket_details.getSTax());
            double OnwardConvFee= Double.parseDouble(flight_ticket_details.getConveniencefee());
            double PromoAmount=Double.parseDouble(flight_ticket_details.getPromoCodeAmount());

            //double Onward_Total=OnwardBaseFare+OnwardTax+OnwardTcharge+OnwardTMarkup+OnwardSTax+OnwardConvFee-PromoAmount;
            double Onward_Total=OnwardBaseFare+OnwardTax+OnwardTcharge+OnwardTMarkup+OnwardSTax-PromoAmount;


            double currvalue= Double.parseDouble(flight_ticket_details.getCurrencyValue());
            String Currency=flight_ticket_details.getCurrencyID();
            String Currency_Symbol = null;
            switch (Currency) {
                case "INR":
                    Currency_Symbol = "₹ ";
                    break;
                case "USD":
                    Currency_Symbol = "$ ";
                    break;
            }

            OnwardFare_TV.setText(Currency_Symbol+""+Util.getprice(Onward_Total*currvalue)+"");

            if (flight_ticket_details.getReturnFlightSegments().size()!=0&&flight_ticket_details.getReturnFlightSegments()!=null){
                Return_Fare_layout.setVisibility(View.VISIBLE);
                double ReturnBaseFare= Double.parseDouble(flight_ticket_details.getActualBaseFareRet());
                double ReturnTax= Double.parseDouble(flight_ticket_details.getTaxRet());
                double ReturnTcharge= Double.parseDouble(flight_ticket_details.getTChargeRet());
                double ReturnTMarkup= Double.parseDouble(flight_ticket_details.getTMarkupRet());
                double ReturnSTax= Double.parseDouble(flight_ticket_details.getSTaxRet());
                double ReturnConvFee= Double.parseDouble(flight_ticket_details.getConveniencefeeRet());


                //double Return_Total=ReturnBaseFare+ReturnTax+ReturnTcharge+ReturnTMarkup+ReturnSTax+ReturnConvFee;
                double Return_Total=ReturnBaseFare+ReturnTax+ReturnTcharge+ReturnTMarkup+ReturnSTax;
                ReturnFare_TV.setText(Currency_Symbol+""+Util.getprice(Return_Total*currvalue)+"");
            }





            String Names = flight_ticket_details.getNames().replace("|"," ");
            Names=Names.replace("ADT","[Adult]").replace("CHD","[Child]").replace("INF","[Infant]");
            //Names = Names.replace("~"," \n\n");

            StringTokenizer names = new StringTokenizer(Names, "~");
            StringTokenizer ages = new StringTokenizer(flight_ticket_details.getAges(), "~");
            //StringTokenizer genders = new StringTokenizer(flight_ticket_details.getGenders(), "~");

            ArrayList<String> resultList = new ArrayList<>();
            while(names.hasMoreTokens()) {
                StringBuilder single = new StringBuilder();
                single.append(names.nextToken()).append(" - ");
                //single.append(genders.nextToken()).append(" - ");
                single.append(ages.nextToken());
                resultList.add(single.toString());
            }
            String result = TextUtils.join(",\n\n", resultList);




            Passengers.setText(""+result+"");

            TicketNumber.setText(""+flight_ticket_details.getBookingRefNo()+"");
            PNRNumber.setText(""+flight_ticket_details.getAPIRefNo()+"");
            BookingDate.setText(""+Util.getDate(flight_ticket_details.getBookingDate())+"");



            ll.addView(linearLayout);
            flight_ticket_scrool.removeAllViews();
            flight_ticket_scrool.addView(ll);
        }

    private void initViews() {
        flight_ticket_scrool = (LinearLayout) findViewById(R.id.flightDetailsScrollview);

        Cancel_Ticket_Report= (TextView) findViewById(R.id.Cancel_Ticket_Report);
        Cancel_Ticket_Report.setVisibility(View.VISIBLE);

        Cancel_Ticket_Report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog("Do you wish to cancel this Ticket?");
            }
        });

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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Reports_Flight_Ticket_Details.this);
//        //alertDialog.setTitle("Alert!");
        alertDialog.setMessage(s);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                callGetFlightTicketDetailsRe();
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



    /////////////////Flight Cancellation///////////////////////////////////////


    private void callGetFlightTicketDetailsRe(){
        if(Util.isNetworkAvailable(Reports_Flight_Ticket_Details.this)) {
            showProgressDialog("Please Wait, fetching ticket details...",this);
            Service_Cancellations.GetCancelFlightTicket_DetailsCan(Reports_Flight_Ticket_Details.this, Constants.FlightTicketDetails+refno+"&type=2");
        }else{
            Util.showMessage(Reports_Flight_Ticket_Details.this, Constants.NO_INT_MSG);
        }
    }


    public void getTicketDetailsResponseCan(String response, int StatusCode) {
        hideProgressDialog();
        if (response != null) {
            if (StatusCode == 200) {
                if (Util.getBookingStatus(response).equals("Cancelled")) {
                    showAlertDialogforCancellation("Your ticket has already been cancelled.");
                } else {



                        try {

                            InputStream stream = new ByteArrayInputStream(response.getBytes());
                            Gson gson = new Gson();
                            Reader reader = new InputStreamReader(stream);
                            Flight_Ticket_Details_Pojo flight_ticket_details = gson.fromJson(reader, Flight_Ticket_Details_Pojo.class);
                            Intent ip = new Intent(Reports_Flight_Ticket_Details.this, Cancel_Flight_Ticket.class);
                            ip.putExtra("Flight_Details", flight_ticket_details);
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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Reports_Flight_Ticket_Details.this);
        //alertDialog.setTitle("Alert!");
        alertDialog.setMessage(s);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Util.startHomeActivity(Reports_Flight_Ticket_Details.this);
            }
        });
    }

}
