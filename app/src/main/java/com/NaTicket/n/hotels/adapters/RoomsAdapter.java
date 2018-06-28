package com.NaTicket.n.hotels.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.NaTicket.n.hotels.Rooms_Activity;
import com.NaTicket.n.hotels.pojo.Room;
import com.NaTicket.n.R;
import com.NaTicket.n.utils.Util;

import java.util.ArrayList;

/**
 * Created by Nagarjuna on 029-Jun-17.
 */

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.MyViewHolder> {


    private Rooms_Activity context;
    private ArrayList<Room> roomList;
    private int lastPosition = -1;
    int selectedIndex = -1;

    public RoomsAdapter(Rooms_Activity activity, ArrayList<Room> roomList) {
        context=activity;
        this.roomList=roomList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView  roomNoTv, noadultTv,nochildTv;
        ImageView removeRoom,adultAdd,adultRemove,childAdd,childRemove;
        LinearLayout Child1AgeLayout,Child2AgeLayout;
        NumberPicker Child1_Age_NP,Child2_Age_Np;

        public MyViewHolder(View view) {
            super(view);

            adultRemove = (ImageView) view.findViewById(R.id.adultRemove);
            roomNoTv = (TextView) view.findViewById(R.id.roomNoTv);
            removeRoom=(ImageView)view.findViewById(R.id.removeRoom);
            adultAdd=(ImageView)view.findViewById(R.id.adultAdd);
            noadultTv=(TextView) view.findViewById(R.id.noadultTv);
            childRemove = (ImageView) view.findViewById(R.id.childRemove);
            childAdd=(ImageView)view.findViewById(R.id.childAdd);
            nochildTv=(TextView) view.findViewById(R.id.nochildTv);

            Child1AgeLayout=(LinearLayout) view.findViewById(R.id.child1Age_layout);
            Child2AgeLayout=(LinearLayout) view.findViewById(R.id.child2Age_layout);
            Child1_Age_NP=(NumberPicker) view.findViewById(R.id.Child1_numberPicker);
            Child2_Age_Np=(NumberPicker) view.findViewById(R.id.Child2_numberPicker);
            assert Child1_Age_NP != null;
            Child1_Age_NP.setMaxValue(12);
            Child1_Age_NP.setMinValue(0);
            //Child1_Age_NP.setValue(infantsCountF);
            Child1_Age_NP.setWrapSelectorWheel(false);

            assert Child2_Age_Np != null;
            Child2_Age_Np.setMaxValue(12);
            Child2_Age_Np.setMinValue(0);
            //Child1_Age_NP.setValue(infantsCountF);
            Child2_Age_Np.setWrapSelectorWheel(false);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.room_list_row_hotel, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Room room = roomList.get(position);

        if(room.getChild()==0){
            holder.Child1AgeLayout.setVisibility(View.GONE);
            holder.Child2AgeLayout.setVisibility(View.GONE);
        }

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
                if (total<4){
                    room.setAdults(room.getAdults()+1);
                    holder.noadultTv.setText(room.getAdults()+"");
                    context.updateTotalNoofAdults();
                }else {
                    Util.showMessage(context,"Only 4 Persons Per Room Allowed");
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
                if (total<4){
                    if (room.getChild()<2){
                        room.setChild(room.getChild()+1);
                        holder.nochildTv.setText(room.getChild()+"");
                        context.updateTotalNoofChild();
                    }else {
                        Util.showMessage(context,"Only 2 Children Per Room Allowed");
                    }
                    if(room.getChild()==0){
                        holder.Child1AgeLayout.setVisibility(View.GONE);
                        holder.Child2AgeLayout.setVisibility(View.GONE);
                        room.setChild1age(-1);
                        room.setChild2age(-1);
                        context.updateChildAges();
                    }
                    if(room.getChild()==1){
                        holder.Child1AgeLayout.setVisibility(View.VISIBLE);
                        holder.Child2AgeLayout.setVisibility(View.GONE);
                        room.setChild1age(holder.Child1_Age_NP.getValue());
                        room.setChild2age(-1);
                        context.updateChildAges();
                    }
                    if(room.getChild()==2){
                        holder.Child1AgeLayout.setVisibility(View.VISIBLE);
                        holder.Child2AgeLayout.setVisibility(View.VISIBLE);
                        room.setChild1age(holder.Child1_Age_NP.getValue());
                        room.setChild2age(holder.Child2_Age_Np.getValue());
                        context.updateChildAges();
                    }
                }else{
                    Util.showMessage(context,"Only 4 Persons Per Room Allowed");
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
                if(room.getChild()==0){
                    holder.Child1AgeLayout.setVisibility(View.GONE);
                    holder.Child2AgeLayout.setVisibility(View.GONE);
                    room.setChild1age(-1);
                    room.setChild2age(-1);
                    context.updateChildAges();
                }
                if(room.getChild()==1){
                    holder.Child1AgeLayout.setVisibility(View.VISIBLE);
                    holder.Child2AgeLayout.setVisibility(View.GONE);
                    room.setChild1age(holder.Child1_Age_NP.getValue());
                    room.setChild2age(-1);
                    context.updateChildAges();
                }
                if(room.getChild()==2){
                    holder.Child1AgeLayout.setVisibility(View.VISIBLE);
                    holder.Child2AgeLayout.setVisibility(View.VISIBLE);
                    room.setChild1age(holder.Child1_Age_NP.getValue());
                    room.setChild2age(holder.Child2_Age_Np.getValue());
                    context.updateChildAges();
                }
            }
        });


        holder.Child1_Age_NP.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker numberPicker, int scrollState) {
                if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
                    if(room.getChild()==0){
                    room.setChild1age(-1);
                    room.setChild2age(-1);
                    context.updateChildAges();
                    }
                    if(room.getChild()==1){
                        room.setChild1age(holder.Child1_Age_NP.getValue());
                        room.setChild2age(-1);
                        context.updateChildAges();
                    }
                    if(room.getChild()==2){
                        room.setChild1age(holder.Child1_Age_NP.getValue());
                        room.setChild2age(holder.Child2_Age_Np.getValue());
                        context.updateChildAges();
                    }

                }
            }


        });

        holder.Child2_Age_Np.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker numberPicker, int scrollState) {
                if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
                    if(room.getChild()==0){
                        room.setChild1age(-1);
                        room.setChild2age(-1);
                        context.updateChildAges();
                    }
                    if(room.getChild()==1){
                        room.setChild1age(holder.Child1_Age_NP.getValue());
                        room.setChild2age(-1);
                        context.updateChildAges();
                    }
                    if(room.getChild()==2){
                        room.setChild1age(holder.Child1_Age_NP.getValue());
                        room.setChild2age(holder.Child2_Age_Np.getValue());
                        context.updateChildAges();
                    }

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
