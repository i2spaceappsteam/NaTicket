package com.NaTicket.n.common.fragments.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.NaTicket.n.common.activities.Reports_Hotels_Details_Activity;
import com.NaTicket.n.hotels.pojo.Hotel_Tickets_pojo;
import com.NaTicket.n.R;
import com.NaTicket.n.utils.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Ankit on 14-01-2018.
 */

public class Adapter_Hotel_Reports extends RecyclerView.Adapter<Adapter_Hotel_Reports.MyViewHolder> {

    private Context context;
    private Hotel_Tickets_pojo[] hotels_reports;

    public Adapter_Hotel_Reports(Context myTrips, Hotel_Tickets_pojo[] available_hotels) {
        this.context=myTrips;
        this.hotels_reports=available_hotels;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.hotels_list, parent, false);
        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Hotel_Tickets_pojo reports=hotels_reports[position];
        String dayOfWeek = null;

        holder.Hotelname.setText(""+reports.getHotelName()+"");


        double Fares= Double.parseDouble(reports.getFare());
        double Convince=reports.getConveniencefee();
        double PromoAmount=Double.parseDouble(String.valueOf(reports.getPromoCodeAmount()));
        double Total=Fares+Convince-PromoAmount;
        double TotalAmount= Util.getprice(Total*Double.valueOf(reports.getCurrencyValue()));
        holder.fares.setText(""+TotalAmount+"");
        holder.referenceno.setText(""+reports.getBookingRefNo()+"");
        holder.address.setText(""+reports.getHotelAddress()+"");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        String JourneyDate = reports.getArrivalDateTime();
        String[] parts = JourneyDate.split("-");
        int day = Integer.parseInt(parts[0]); // 004
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);
        Date myDate = null;
        try {
            myDate = dateFormat.parse(JourneyDate);
            SimpleDateFormat sf = new SimpleDateFormat("EEE", Locale.ENGLISH);
            Date dat = new Date(year, month, day - 1);
            dayOfWeek = sf.format(dat);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat timeFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        String finalDate = timeFormat.format(myDate);

        System.out.println("DATE FORMAT :" + finalDate);
        String[] partss = finalDate.split("-");
        String part1 = partss[0];
        String part2 = partss[1];
        String part3 = partss[2];

        holder.journeydate.setText(dayOfWeek+", "+part1+" "+part2 );

        holder.bookingstatus.setTextColor(context.getResources().getColor(R.color.red));

        if (reports.getBookingStatus()==1){
            holder.bookingstatus.setText(""+"Pending"+"");
        }else if (reports.getBookingStatus()==2){
            holder.bookingstatus.setText(""+"Failed"+"");
        }else if (reports.getBookingStatus()==3){
            holder.bookingstatus.setTextColor(context.getResources().getColor(R.color.Colorgreen));
            holder.bookingstatus.setText(""+"Confirmed"+"");
        }else if (reports.getBookingStatus()==4){
            holder.bookingstatus.setText(""+"Rejected"+"");
        }else if (reports.getBookingStatus()==5){
            holder.bookingstatus.setText(""+"Cancelled"+"");
        }else if (reports.getBookingStatus()==6){
            holder.bookingstatus.setText(""+"Cancellation Failed"+"");
        }



        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reports.getBookingStatus()==3){
                    Intent i = new Intent(context, Reports_Hotels_Details_Activity.class);
                    i.putExtra("referencenumber", reports.getBookingRefNo());
                    context.startActivity(i);
                }else {
                    Util.alertDialogShow(context,"Hotel Details Not Found!");
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return hotels_reports.length;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Hotelname,fares,bookingstatus, journeydate,referenceno,address;
        ImageView image;
        LinearLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);

            Hotelname = (TextView) itemView.findViewById(R.id.hotelname);
            fares= (TextView) itemView.findViewById(R.id.fares);
            bookingstatus = (TextView) itemView.findViewById(R.id.status);
            journeydate = (TextView) itemView.findViewById(R.id.journey_date);
            referenceno= (TextView) itemView.findViewById(R.id.Reference_No);
            address= (TextView) itemView.findViewById(R.id.address);
            image= (ImageView) itemView.findViewById(R.id.hotel_image);
            layout= (LinearLayout) itemView.findViewById(R.id.layout);

        }
    }
}

