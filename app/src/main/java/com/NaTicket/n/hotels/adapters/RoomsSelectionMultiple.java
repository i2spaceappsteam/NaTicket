package com.NaTicket.n.hotels.adapters;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.NaTicket.n.hotels.pojo.AvailableHotelsDTO;
import com.NaTicket.n.R;
import com.NaTicket.n.hotels.SelectRoomActivityNew;
import com.NaTicket.n.hotels.pojo.SelectionDetailsDTO;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Nagarjuna on 11/24/2017.
 */

public class RoomsSelectionMultiple extends RecyclerView.Adapter<RoomsSelectionMultiple.MyViewHolder> {

    private SelectRoomActivityNew context;
    private final String[] chainArray;
    String chain;
    HashMap<String, JSONObject> roomslist=new HashMap<>();
    private String[] roomArray;
    AvailableHotelsDTO selhotel;
    int selectedPosition = -1;
    HashMap<Integer, String[]> integerList;
    SelectionDetailsDTO selectionDetails;



    public RoomsSelectionMultiple(SelectRoomActivityNew activity,
                                  AvailableHotelsDTO selHotel,
                                  HashMap<String, JSONObject> roomsDTO,SelectionDetailsDTO selectionDetails) {
        context=activity;
        chain=selHotel.getRoomChain();
        chainArray = chain.split("\\|");
        this.roomslist=roomsDTO;
        this.selhotel=selHotel;
        integerList = new HashMap<>();
        this.selectionDetails=selectionDetails;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.room_list_layout_chain, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        roomArray = chainArray[position].split(",");

        int pos=position+1;

       /* roomDetailsDTO=list.get(position);

        roomList=new ArrayList<>();
        ArrayList<Integer> list1 = new ArrayList<>();
        for (String a : roomArray) {
            int pos =Integer.parseInt(a)-1;
            list1.add(pos);
            roomList.add(list.get(pos));
        }*/

        integerList.put(position,roomArray);

        RoomListAdapter mAdapter=new RoomListAdapter(roomslist,context,selhotel,roomArray,selectionDetails);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        holder.recyclerView.setLayoutManager(mLayoutManager);
        holder.recyclerView.setItemAnimator(new DefaultItemAnimator());
        holder.recyclerView.setAdapter(mAdapter);
        mLayoutManager.setAutoMeasureEnabled(true);



        String Combination=selhotel.getRoomCombination().toLowerCase();
        if(Combination.equals("opencombination")){
            holder.radioButton.setVisibility(View.GONE);
            holder.Room_no_Tv.setText("Room - "+pos+"");
        }else {
            holder.radioButton.setVisibility(View.VISIBLE);
            holder.Room_no_Tv.setVisibility(View.GONE);
        }

        if (position == selectedPosition) {
            holder.radioButton.setChecked(true);
        } else {
            holder.radioButton.setChecked(false);
        }

        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition=position;
                String[] chain=integerList.get(position);
                context.setSeleectedID(position,chain,selectedPosition,"fixedcombination");
                notifyDataSetChanged();
            }
        });

 /*       holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition=position;
                notifyDataSetChanged();
            }
        });*/

    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        RadioButton radioButton;
        TextView Room_no_Tv;
        public MyViewHolder(View view) {
            super(view);
            recyclerView = ((RecyclerView) itemView.findViewById(R.id.listRecyclerView));
            radioButton = (RadioButton)itemView.findViewById(R.id.rb1);
            Room_no_Tv= (TextView) itemView.findViewById(R.id.room_no);
        }
    }

    @Override
    public int getItemCount() {
        int size = chainArray.length;
        //Log.e(TAG, "getItemCount:  " + size);
        return size;
    }
}
