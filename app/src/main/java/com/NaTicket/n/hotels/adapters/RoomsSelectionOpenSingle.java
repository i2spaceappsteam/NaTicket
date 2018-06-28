package com.NaTicket.n.hotels.adapters;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RadioButton;
import android.widget.TextView;

import com.NaTicket.n.hotels.pojo.AvailableHotelsDTO;
import com.NaTicket.n.R;
import com.NaTicket.n.common.pojo.Currency_Utils;
import com.NaTicket.n.hotels.SelectRoomActivityNew;
import com.NaTicket.n.hotels.pojo.SelectionDetailsDTO;
import com.NaTicket.n.utils.Util;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by SHIVA SAI on 07-Jun-17.
 */

public class RoomsSelectionOpenSingle extends RecyclerView.Adapter<RoomsSelectionOpenSingle.MyViewHolder> {


    private SelectRoomActivityNew context;

    AvailableHotelsDTO selHotel;
    ArrayList<String> roomIndices;
    private int lastPosition = -1;
    int selectedIndex = -1;
    DecimalFormat df;
    SelectionDetailsDTO selectionDetails;
    Currency_Utils currency_utils;

    public RoomsSelectionOpenSingle(SelectRoomActivityNew activity,
                                    ArrayList<String> roomIndices,
                                    AvailableHotelsDTO selHotel,
                                    SelectionDetailsDTO selectionDetailsDTO) {
        context=activity;
        this.roomIndices=roomIndices;
        this.selHotel=selHotel;
        this.selectionDetails=selectionDetailsDTO;
        df = new DecimalFormat("#.##");
        currency_utils=new Currency_Utils(activity);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView  roomType, roomTotal,refundRule,cancellationTv,Inclusions;
        RadioButton radioButton;


        public MyViewHolder(View view) {
            super(view);
            radioButton=(RadioButton)view.findViewById(R.id.radioButton);
            roomType = (TextView) view.findViewById(R.id.roomType);
            roomTotal=(TextView) view.findViewById(R.id.roomTotal);
            cancellationTv=(TextView) view.findViewById(R.id.cancellationTv);
            Inclusions= (TextView) view.findViewById(R.id.inclusions);
            refundRule=(TextView) view.findViewById(R.id.refundRule);
        }
    }

    public void setSelectedIndex(int index, int position) {
        selectedIndex = position;
        String roomarray= String.valueOf(index);
        String[] arr = new String[1];
        arr[0]=roomarray;
        context.setSeleectedID(index,arr,position,"single");
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.room_select_opensingle_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final String mIndex = roomIndices.get(position);
        double Curr_Value= Double.parseDouble(currency_utils.getCurrencyValue("Currency_Value"));
        String Currency_Symbol= currency_utils.getCurrencyValue("Currency_Symbol");
         for(int i = 0; i<selHotel.getRoomDetails().size(); i++){

             if(mIndex.matches(selHotel.getRoomDetails().get(i).getRoomIndex()+"")){
                 holder.roomType.setText(selHotel.getRoomDetails().get(i).getRoomType());


                 double Servicetax=Double.valueOf(selHotel.getRoomDetails().get(i).getServicetaxTotal());
                 double TotalServiceTax=Servicetax/selectionDetails.getNoofDays();

                 double Totalrate=(Double.valueOf(selHotel.getRoomDetails().get(i).getRoomTotal())+TotalServiceTax);

                 if (Currency_Symbol.equals("د.إ ")){
                     Util.getAllignment(holder.roomTotal);
                 }

                 holder.roomTotal.setText(Currency_Symbol+ Util.getprice(Totalrate*Curr_Value)+"");


                 holder.refundRule.setText(selHotel.getRoomDetails().get(i).getRefundRule());

                 String Inclusion=selHotel.getRoomDetails().get(i).getInclusions();
                 if(!Inclusion.isEmpty()||!Inclusion.equals("")) {
                     holder.Inclusions.setText(Inclusion);
                 }else{
                     holder.Inclusions.setText("Room Only");
                 }
                 final String msg = selHotel.getRoomDetails().get(i).getRoomCancellationPolicy();
                 String s = msg.replaceAll("<br />","").replaceAll("\\|\\|","").replaceAll("\\|","\n\n");
                 if(s.equals("")){
                     s="No policies found";
                 }
                 final int finalI = i;
                 final String finalS = s;
                 holder.cancellationTv.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         AlertDialog.Builder alert = new AlertDialog.Builder(context);
                         alert.setMessage("\u25CF "+ finalS);
                         alert.setCancelable(false);
                         alert.setPositiveButton("OK", null);
                         alert.show();
                     }
                 });
             }
         }

        if(selectedIndex == position){
            holder.radioButton.setChecked(true);
        }
        else{
            holder.radioButton.setChecked(false);
        }


        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedIndex(Integer.parseInt(mIndex),position);
                notifyDataSetChanged();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedIndex(Integer.parseInt(mIndex),position);
                notifyDataSetChanged();
            }
        });
        setAnimation(holder.itemView, position);
    }





    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return roomIndices.size();
    }
}
