package com.NaTicket.n.hotels.adapters;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.NaTicket.n.hotels.pojo.AvailableHotelsDTO;
import com.NaTicket.n.R;
import com.NaTicket.n.common.pojo.Currency_Utils;
import com.NaTicket.n.hotels.SelectRoomActivityNew;
import com.NaTicket.n.hotels.pojo.SelectionDetailsDTO;
import com.NaTicket.n.utils.Util;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Nagarjuna on 11/24/2017.
 */

public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.MyViewHolder> {

    String[] roomArray;
    HashMap<String, JSONObject> roomslist=new HashMap<>();
    SelectRoomActivityNew context;
    AvailableHotelsDTO selHotel;
    int selectedIndex = -1;
    SelectionDetailsDTO selectionDetails;
    Currency_Utils currency_utils;

    public RoomListAdapter(HashMap<String, JSONObject> roomdetails,
                           SelectRoomActivityNew activity, AvailableHotelsDTO selHotel,String[] roomArray,
                           SelectionDetailsDTO selDetails){
        roomslist=roomdetails;
        this.roomArray=roomArray;
        this.context = activity;
        this.selHotel=selHotel;
        this.selectionDetails=selDetails;
        currency_utils=new Currency_Utils(activity);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.room_select_opensingle_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

    }

    @Override
    public int getItemCount() {
        return roomArray.length;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int i) {

      final JSONObject roomDetails=roomslist.get(roomArray[i]);
        double Curr_Value= Double.parseDouble(currency_utils.getCurrencyValue("Currency_Value"));
        String Currency_Symbol=currency_utils.getCurrencyValue("Currency_Symbol");
        try {

            holder.roomType.setText(roomDetails.getString("RoomType"));
            double Servicetax=Double.valueOf(roomDetails.getString("ServicetaxTotal"));
            double TotalServiceTax=Servicetax/selectionDetails.getNoofDays();
            double Totalrate=(Double.valueOf(roomDetails.getString("RoomTotal"))+TotalServiceTax);


            holder.roomTotal.setText(Currency_Symbol+ Util.getprice(Totalrate*Curr_Value)+"");

            holder.refundRule.setText(roomDetails.getString("RefundRule"));

            String Inclusion=roomDetails.getString("Inclusions");
            if(!Inclusion.isEmpty()||!Inclusion.equals("")) {
                holder.Inclusions.setText(Inclusion);
            }else{
                holder.Inclusions.setText("Room Only");
            }
            //holder.Inclusions.setText(roomDetails.getString("Inclusions"));
            final String msg =roomDetails.getString("RoomCancellationPolicy");


            final String s = msg.replaceAll("<br />","").replaceAll("\\|\\|","").replaceAll("\\|","\n");
            final int finalI = i;

            holder.cancellationTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("Cancellation Policy");
                    alert.setMessage(s);
                    alert.setPositiveButton("OK", null);
                    alert.show();
                }
            });

            String Combination=selHotel.getRoomCombination().toLowerCase();
            if(Combination.equals("opencombination")){
                holder.radioButton.setVisibility(View.VISIBLE);
            }else {
                holder.radioButton.setVisibility(View.GONE);
            }


            if(selectedIndex == i){  // check the position to update correct radio button.
                holder.radioButton.setChecked(true);
            }
            else{
                holder.radioButton.setChecked(false);
            }

            holder.radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelectedIndex(Integer.parseInt(roomArray[i]),i);
                    notifyDataSetChanged();
                }
            });


      /*      holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelectedIndex(Integer.parseInt(roomArray[i]),i);
                    notifyDataSetChanged();
                }
            });*/
        } catch (JSONException e) {
            e.printStackTrace();
        }




    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView roomType, roomTotal,refundRule,cancellationTv,Inclusions;
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
        context.setSeleectedID(index,roomArray,selectedIndex,"opencombination");
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


   /* public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }*/
}
