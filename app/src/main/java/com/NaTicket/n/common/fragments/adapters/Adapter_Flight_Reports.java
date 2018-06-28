package com.NaTicket.n.common.fragments.adapters;

/**
 * Created by Ankit on 14-01-2018.
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
import com.NaTicket.n.common.activities.Reports_Flight_Ticket_Details;
import com.NaTicket.n.flights.pojo.Flight_Ticket_Details_Pojo;
import com.NaTicket.n.utils.Util;

/**
 * Created by Nagarjuna on 8/24/2017.
 */

public class Adapter_Flight_Reports extends RecyclerView.Adapter<Adapter_Flight_Reports.MyViewHolder>  {

    private Context context;
    private Flight_Ticket_Details_Pojo[] Flights_Pojo;

    public Adapter_Flight_Reports(Context myTrips, Flight_Ticket_Details_Pojo[] flights_reports) {
        this.context=myTrips;
        this.Flights_Pojo=flights_reports;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.flights_list, parent, false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Flight_Ticket_Details_Pojo reports=Flights_Pojo[position];
        holder.from.setText(""+reports.getSource()+"");
        holder.to.setText(""+reports.getDestination()+"");
        holder.referenceno.setText(""+reports.getBookingRefNo()+"");

        holder.journeydate.setText(reports.getReportBookingDate());

        String status=reports.getBookingStatus();

        if(status.equals("3")){
            holder.bookingstatus.setTextColor(context.getResources().getColor(R.color.Colorgreen));
        }else{
            holder.bookingstatus.setTextColor(context.getResources().getColor(R.color.red));
        }

        switch (status) {
            case "1":
                holder.bookingstatus.setText("" + "Booking processing" + "");
                break;
            case "2":
                holder.bookingstatus.setText("" + "Cancelled" + "");
                break;
            case "3":
                holder.bookingstatus.setText("" + "Booked" + "");
                break;
            case "4":
                holder.bookingstatus.setText("" + "Cancelled" + "");
                break;
            case "5":
                holder.bookingstatus.setText("" + "Cancellation is in progress" + "");
                break;
            case "6":
                holder.bookingstatus.setText("" + "Cancelled" + "");
                break;
            case "7":
                holder.bookingstatus.setText("" + " Cancellation request is rejected " + "");
                break;
            case "8":
                holder.bookingstatus.setText("" + "Pending" + "");
                break;
            case "9":
                holder.bookingstatus.setText("" + "Payment Problem" + "");
                break;
            case "10":
                holder.bookingstatus.setText("" + "Booking Failed" + "");
                break;
            case "11":
                holder.bookingstatus.setText("" + "Ticket not Found " + "");
                break;
            case "12":
                holder.bookingstatus.setText("" + "Ticket Partially Cancelled " + "");
                break;
            case "13":
                holder.bookingstatus.setText("" + "Partial Cancellation Failed " + "");
                break;
            case "14":
                holder.bookingstatus.setText("" + "Processed " + "");
                break;
            case "15":
                holder.bookingstatus.setText("" + "Rejected " + "");
                break;
            case "16":
                holder.bookingstatus.setText("" + "Blocking Failed" + "");
                break;
        }
        


//        if(reports.getReportBookingStatus().equals("Booked")){
//            holder.bookingstatus.setTextColor(context.getResources().getColor(R.color.Colorgreen));
//        }



        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reports.getBookingStatus().equals("3")){
                    Intent i = new Intent(context, Reports_Flight_Ticket_Details.class);
                    i.putExtra("refno", reports.getBookingRefNo());
                    context.startActivity(i);
                }else {
                    Util.alertDialogShow(context,"Flight Details Not Found!");
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return Flights_Pojo.length;
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
            image= (ImageView) itemView.findViewById(R.id.flight_image);
            layout= (LinearLayout) itemView.findViewById(R.id.layout);

        }
    }
}
