package com.NaTicket.n.common.fragments.adapters;

/**
 * Created by Nagarjuna on 14-01-2018.
 */

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
import com.NaTicket.n.buses.pojo.Bus_Tickets_Pojo;
import com.NaTicket.n.common.activities.Reports_Bus_Ticket_Details;
import com.NaTicket.n.utils.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public class Adapter_Bus_Reports extends RecyclerView.Adapter<Adapter_Bus_Reports.MyViewHolder>  {

    private ArrayList<HashMap<String,String>> Buses=new ArrayList<>();
    private Context context;
    private Bus_Tickets_Pojo[] bus_reports_pojo;
    public Adapter_Bus_Reports(Context myTrips, Bus_Tickets_Pojo[] Bus_Reports) {
        this.context=myTrips;
        this.bus_reports_pojo=Bus_Reports;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.buses_list, parent, false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Bus_Tickets_Pojo reports=bus_reports_pojo[position];
        //final HashMap<String,String> reports=Buses.get(position);
        String dayOfWeek = null;
        holder.referenceno.setText(""+reports.getBookingRefNo()+"");
        holder.from.setText(""+reports.getSourceName()+"");
        holder.to.setText(""+reports.getDestinationName()+"");


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

        holder.bookingstatus.setTextColor(context.getResources().getColor(R.color.colorRed));

        if (reports.getBookingStatus().equals("1")) {
            holder.bookingstatus.setText("Blocked");
        } else if (reports.getBookingStatus().equals("2")) {
            holder.bookingstatus.setText("BlockingFailed");
        } else if (reports.getBookingStatus().equals("3")) {
            holder.bookingstatus.setTextColor(context.getResources().getColor(R.color.Colorgreen));
            holder.bookingstatus.setText("Confirmed");
        } else if (reports.getBookingStatus().equals("4")) {
            holder.bookingstatus.setText("Failed");
        } else if (reports.getBookingStatus().equals("5")) {
            holder.bookingstatus.setText("Cancelled");
        } else if (reports.getBookingStatus().equals("6")) {
            holder.bookingstatus.setText("CancellationFailed");
        } else if (reports.getBookingStatus().equals("7")) {
            holder.bookingstatus.setText("PartaillyCancelled");
        } else if (reports.getBookingStatus().equals("8")) {
            holder.bookingstatus.setText( "PartialCancellationFailed");
            holder.bookingstatus.setTextColor(context.getResources().getColor(R.color.red));
        } else if (reports.getBookingStatus().equals("9")) {
            holder.bookingstatus.setText("Failed");
        } else if (reports.getBookingStatus().equals("10")) {
            holder.bookingstatus.setText("Visited");
        }



        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reports.getBookingStatus().equals("3")){
                    Intent i = new Intent(context, Reports_Bus_Ticket_Details.class);
                    i.putExtra("Onwardrefno", reports.getBookingRefNo());
                    context.startActivity(i);
                }else {
                    Util.alertDialogShow(context,"Ticket Details Not Found!");
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return bus_reports_pojo.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView from,to,bookingstatus, journeydate,referenceno;
        ImageView image;
        LinearLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);

            from = (TextView) itemView.findViewById(R.id.from_place);
            to= (TextView) itemView.findViewById(R.id.to_place);
            bookingstatus = (TextView) itemView.findViewById(R.id.status);
            journeydate = (TextView) itemView.findViewById(R.id.date);
            referenceno= (TextView) itemView.findViewById(R.id.Reference_No);
            image= (ImageView) itemView.findViewById(R.id.bus_image);
            layout= (LinearLayout) itemView.findViewById(R.id.layout);
        }
    }
}
