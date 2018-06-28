package com.NaTicket.n.holidays.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.NaTicket.n.R;
import com.NaTicket.n.holidays.CheckAvailabilityActivity;
import com.NaTicket.n.hotels.pojo.Room;
import com.NaTicket.n.utils.Util;

import java.util.ArrayList;

/**
 * Created by SHIVA SAI on 07-Jun-17.
 */

public class HolidayRoomsAdapter extends RecyclerView.Adapter<HolidayRoomsAdapter.MyViewHolder> {


    private CheckAvailabilityActivity context;
    private ArrayList<Room> roomList;
    private int lastPosition = -1;
    public HolidayRoomsAdapter(CheckAvailabilityActivity activity, ArrayList<Room> roomList) {
        context=activity;
        this.roomList=roomList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView  roomNoTv, noadultTv,nochildTv;
        ImageView removeRoom,adultAdd,adultRemove,childAdd,childRemove;

        public MyViewHolder(View view) {
            super(view);
         // promoNameTv = (TextView) view.findViewById(R.id.promoNameTv);
           // discountTv = (TextView) view.findViewById(R.id.discountTv);
            adultRemove = (ImageView) view.findViewById(R.id.adultRemove);
            roomNoTv = (TextView) view.findViewById(R.id.roomNoTv);
            removeRoom=(ImageView)view.findViewById(R.id.removeRoom);
            adultAdd=(ImageView)view.findViewById(R.id.adultAdd);
            noadultTv=(TextView) view.findViewById(R.id.noadultTv);
            childRemove = (ImageView) view.findViewById(R.id.childRemove);
            childAdd=(ImageView)view.findViewById(R.id.childAdd);
            nochildTv=(TextView) view.findViewById(R.id.nochildTv);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.room_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Room room = roomList.get(position);

        if(roomList.size()>1){
            holder.removeRoom.setVisibility(View.VISIBLE);
        }else{
            holder.removeRoom.setVisibility(View.GONE);
        }
        holder.removeRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.removeRoom(position);
            }
        });
        holder.adultAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int total=room.getAdults()+room.getChild();
                if (total<3) {
                    room.setAdults(room.getAdults() + 1);
                    holder.noadultTv.setText(room.getAdults() + "");
                    context.updateTotalNoofAdults();
                }else {
                    Util.showMessage(context,"Only 3 Persons Per Room Allowed");
                }
            }
        });
        holder.adultRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(room.getAdults()>1) {
                    room.setAdults(room.getAdults() - 1);
                    holder.noadultTv.setText(room.getAdults()+"");
                    context.updateTotalNoofAdults();
                }else{
                    Util.showMessage(context,"Atleast One Adult Required");
                }
            }
        });
        holder.childAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int total=room.getAdults()+room.getChild();
                if (total<3){
                    if (room.getChild()<2){
                        room.setChild(room.getChild()+1);
                        holder.nochildTv.setText(room.getChild()+"");
                        context.updateTotalNoofChild();
                    }else {
                        Util.showMessage(context,"Only 2 Children Per Room Allowed");
                    }
                }else{
                    Util.showMessage(context,"Only 3 Persons Per Room Allowed");
                }

            }
        });
        holder.childRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(room.getChild()>0) {
                    room.setChild(room.getChild()-1);
                    holder.nochildTv.setText(room.getChild()+"");
                    context.updateTotalNoofChild();
                }else{
                    Util.showMessage(context,"No child to Remove");
                }
            }
        });
        holder.roomNoTv.setText("Room - "+(position+1));
        holder.noadultTv.setText(room.getAdults()+"");
        holder.nochildTv.setText(room.getChild()+"");
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
        return roomList.size();
    }
}
