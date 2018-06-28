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

import com.NaTicket.n.common.activities.Reports_Recharge_Details;
import com.NaTicket.n.recharges.pojo.Recharge_reports_pojo;
import com.NaTicket.n.R;
import com.NaTicket.n.utils.Util;

/**
 * Created by Ankit on 14-01-2018.
 */

public class Adapter_Recharge_Reports extends RecyclerView.Adapter<Adapter_Recharge_Reports.MyViewHolder> {

    private Context context;
    private Recharge_reports_pojo[] recharge_reports;

    public Adapter_Recharge_Reports(Context myTrips, Recharge_reports_pojo[] available_hotels) {
        this.context=myTrips;
        this.recharge_reports=available_hotels;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.recharge_list, parent, false);
        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Recharge_reports_pojo reports=recharge_reports[position];
        String dayOfWeek = null;

        holder.OperatorName.setText(""+reports.getOperatorName()+"");
        holder.RechargeAmount.setText("₹ "+reports.getRechargeAmount()+"");
        holder.referenceno.setText(""+reports.getBookingRefNo()+"");
        holder.RechargeNumber.setText(""+reports.getRechargeNumber()+"");
        holder.ReportBookingDate.setText(""+reports.getReportBookingDate()+"" );


        String Status=reports.getRechargeStatus();


        /*SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
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
        String part3 = partss[2];*/



        holder.bookingstatus.setTextColor(context.getResources().getColor(R.color.red));



        if (reports.getRechargeStatus().equals("1")){
            holder.bookingstatus.setText(""+"Successful"+"");
            holder.bookingstatus.setTextColor(context.getResources().getColor(R.color.Colorgreen));
        }else if (reports.getRechargeStatus().equals("2")){
            holder.bookingstatus.setText(""+"Failed"+"");
        }else if (reports.getRechargeStatus().equals("3")){
            holder.bookingstatus.setText(""+"Pending"+"");
        }else if (reports.getRechargeStatus().equals("4")){
            holder.bookingstatus.setText(""+"Initiated"+"");
        }



        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reports.getRechargeStatus().equals("1")){
                    Intent i = new Intent(context, Reports_Recharge_Details.class);
                    i.putExtra("referencenumber", reports.getBookingRefNo());
                    i.putExtra("Amount", reports.getRechargeAmount());
                    i.putExtra("phone", reports.getRechargeNumber());
                    i.putExtra("operatorName", reports.getOperatorName());

                    context.startActivity(i);
                }else {
                    Util.alertDialogShow(context,"Recharge Unsuccessful");
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return recharge_reports.length;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView OperatorName,RechargeAmount,bookingstatus, ReportBookingDate,referenceno,RechargeNumber;
        ImageView image;
        LinearLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);

            OperatorName = (TextView) itemView.findViewById(R.id.OperatorName);
            RechargeAmount= (TextView) itemView.findViewById(R.id.RechargeAmount);
            bookingstatus = (TextView) itemView.findViewById(R.id.status);
            ReportBookingDate = (TextView) itemView.findViewById(R.id.ReportBookingDate);
            referenceno= (TextView) itemView.findViewById(R.id.Reference_No);
            RechargeNumber= (TextView) itemView.findViewById(R.id.RechargeNumber);
            image= (ImageView) itemView.findViewById(R.id.hotel_image);
            layout= (LinearLayout) itemView.findViewById(R.id.layout);

        }
    }
}


