package com.NaTicket.n.hotels;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.NaTicket.n.holidays.HolidaySearchActivity;
import com.NaTicket.n.hotels.pojo.Hotel_Tickets_DTO;
import com.NaTicket.n.serviceclasses.Service_Reports;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;
import com.google.gson.Gson;
import com.NaTicket.n.R;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by Nagarjuna on 9/5/2017.
 */

public class Hotel_Booking_Details_Activity extends AppCompatActivity {

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
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotel_booking_details);


        TextView ToolBar_Title= (TextView) findViewById(R.id.toolbartitle);
        ToolBar_Title.setText(""+"Ticket Details"+"");
        ImageView ToolBar_Back= (ImageView) findViewById(R.id.backBtn);
        ToolBar_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

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
        //new Hotel_details().execute();
        callHotelDetails();
    }


    private void callHotelDetails(){
        if(Util.isNetworkAvailable(getApplicationContext())) {
            showProgressDialog("Please wait",this);
            Service_Reports.GetHotelDetails(Hotel_Booking_Details_Activity.this, Constants.GETHOTELDETAILS+Referneceno+"&type=2");
        }else{
            Util.showMessage(Hotel_Booking_Details_Activity.this, Constants.NO_INT_MSG);
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
        double Convince=Double.parseDouble(String.valueOf(hotelticket.getHotelDetail().getConvenienceFee()));
        double PromoAmount=Double.parseDouble(String.valueOf(hotelticket.getPromoCodeAmount()));


        double Total=Fares+Convince-PromoAmount;

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


   /* public class Hotel_details extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(Hotel_Booking_Details_Activity.this, "", "Please Wait.....");
        }

        @Override
        protected String doInBackground(String... strings) {
            String strurl = Constants.GETHOTELDETAILS+Referneceno+"&type=2";
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

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
             dialog.dismiss();
            System.out.println("BookingDetails" + code);
            try {
                JSONObject jsonObject = new JSONObject(data);

                String NoOfdays=jsonObject.getString("NoOfdays");
                String Rooms=jsonObject.getString("Rooms");
                String ArrivalDate=jsonObject.getString("ArrivalDate");
                String DepartureDate=jsonObject.getString("DepartureDate");
                String BookingRefNo=jsonObject.getString("BookingRefNo");
                String Fare=jsonObject.getString("Fare");
                String Allocationid=jsonObject.getString("Allocid");
                String Adults=jsonObject.getString("Adults");
                String Children=jsonObject.getString("Children");
                String PromoCodeAmount=jsonObject.getString("PromoCodeAmount");
                JSONObject HotelDetails=jsonObject.getJSONObject("HotelDetail");
                String Hotel_Name=HotelDetails.getString("HotelName");
                String Hotel_Address=HotelDetails.getString("HotelAddress");
                String ConvenienceFee=HotelDetails.getString("ConvenienceFee");
                String CurrValue=jsonObject.getString("CurrencyValue");
                String Currency=jsonObject.getString("CurrencyID");


                double Fares= Double.parseDouble(Fare);
                double Convince=Double.parseDouble(ConvenienceFee);
                double PromoAmount=Double.parseDouble(PromoCodeAmount);


                double Total=Fares+Convince-PromoAmount;

                int TotalAmount=getprice(Total*Double.valueOf(CurrValue));

                String Currency_Symbol = null;
                switch (Currency) {
                    case "INR":
                        Currency_Symbol = "₹ ";
                        break;
                    case "USD":
                        Currency_Symbol = "$ ";
                        break;
                }

                String ADT[]=Adults.split("~");
                String CHD[]=Children.split("~");
                int m = 0,n=0;
                for (int p=0;p<ADT.length;p++){
                    m += Integer.parseInt(ADT[p]);
                    n +=Integer.parseInt(CHD[p]);
                }

                tvPassengers.setText("Adult(s) : "+m+" , "+"Children : "+n);


                JSONArray array=jsonObject.getJSONArray("RoomDetails");

                for (int i=0;i<array.length();i++){
                    JSONObject object=array.getJSONObject(i);
                    String RoomType=object.getString("RoomType");
                    tvRoomType.setText(""+RoomType+"");
                }

       *//*         String CreatedDate=jsonObject.getString("CreatedDate");


                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
                SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                SimpleDateFormat time=new SimpleDateFormat("hh:mm a",Locale.ENGLISH);

                Date myDate = null,Time=null;
                try {
                    myDate=dateFormat.parse(CreatedDate);
                    Time=dateFormat.parse(CreatedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String finaldate=output.format(myDate);
                String FinalTime= time.format(Time);*//*



                tvHtlCheckIn.setText(""+ArrivalDate+"");
                tvHtlCheckout.setText(""+DepartureDate+"");
                tvHtlName.setText(""+Hotel_Name+"");
                tvHtlAddress.setText(""+Hotel_Address+"");
                tvNoOfDays.setText(""+NoOfdays+"");
                tvNoOfRooms.setText(""+Rooms+"");
                tvRefNo.setText(""+BookingRefNo+"");
                tvHtlrate.setText(Currency_Symbol+TotalAmount+"");
                tvConfiramtionid.setText(""+Allocationid+"");
                //tvHtlBookingDate.setText(""+finaldate+" "+FinalTime);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }*/



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent ip=new Intent(Hotel_Booking_Details_Activity.this,HotelSearchActivity.class);
        ip.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(ip);
    }

}

