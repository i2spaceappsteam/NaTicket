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

import com.NaTicket.n.R;
import com.NaTicket.n.common.activities.Reports_Holidays_Details_Activity;
import com.NaTicket.n.holidays.pojo.Holiday_Ticket_Details_DTO;
import com.NaTicket.n.utils.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Nagarjuna on 1/10/2018.
 */

public class Adapter_Holiday_Reports extends RecyclerView.Adapter<Adapter_Holiday_Reports.MyViewHolder>{


    private Context context;
    private Holiday_Ticket_Details_DTO[] holiday_reports;

    public Adapter_Holiday_Reports(Context myTrips, Holiday_Ticket_Details_DTO[] available_hotels) {
        this.context=myTrips;
        this.holiday_reports=available_hotels;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.holidays_list, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Holiday_Ticket_Details_DTO reports=holiday_reports[position];
        String dayOfWeek = null;
       String Currency= reports.getCurrencyID();
        String Currency_Symbol = null;
        switch (Currency) {
            case "INR":
                Currency_Symbol = "₹ ";
                break;
            case "USD":
                Currency_Symbol = "$ ";
                break;
        }

        double Total=Util.getprice((reports.getCollectedFare()+reports.getConvenienceFee())*Double.parseDouble(reports.getCurrencyValue()));

        holder.fares.setText(""+Currency_Symbol+Total+"");
        holder.Holidayname.setText(""+reports.getHolidayPackageName()+"");
        holder.referenceno.setText(""+reports.getBookingRefNo()+"");
        holder.HolidayType.setText(""+reports.getSubCategoryName()+"");

        if(reports.getSubCategoryName().contains("&amp;")){
            holder.HolidayType.setText(""+reports.getSubCategoryName().replace("&amp;","&")+"");
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        String JourneyDate = reports.getJourneyDate();
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
        holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.holiday));

        holder.bookingstatus.setTextColor(context.getResources().getColor(R.color.red));
        if (reports.getBookingStatus()==1){
            holder.bookingstatus.setText(""+"Pending"+"");

        }else if (reports.getBookingStatus()==2){
            holder.bookingstatus.setText(""+"Failed"+"");
        }else if (reports.getBookingStatus()==3){
            holder.bookingstatus.setText(""+"Confirmed"+"");
            holder.bookingstatus.setTextColor(context.getResources().getColor(R.color.Colorgreen));
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
                    Intent i = new Intent(context, Reports_Holidays_Details_Activity.class);
                    i.putExtra("referencenumber", reports.getBookingRefNo());
                    context.startActivity(i);
                }else {
                    Util.alertDialogShow(context,"Holiday Details Not Found!");
                }

            }
        });


    }
    @Override
    public int getItemCount() {
        return holiday_reports.length;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Holidayname,fares,bookingstatus, journeydate,referenceno,HolidayType;
        ImageView image;
        LinearLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);

            Holidayname = (TextView) itemView.findViewById(R.id.holidayname);
            fares= (TextView) itemView.findViewById(R.id.fares);
            bookingstatus = (TextView) itemView.findViewById(R.id.status);
            journeydate = (TextView) itemView.findViewById(R.id.journey_date);
            referenceno= (TextView) itemView.findViewById(R.id.Reference_No);
            HolidayType= (TextView) itemView.findViewById(R.id.holidayType);
            image= (ImageView) itemView.findViewById(R.id.holiday_image);
            layout= (LinearLayout) itemView.findViewById(R.id.layout);

        }
    }

}




