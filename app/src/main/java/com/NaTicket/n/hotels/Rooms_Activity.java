package com.NaTicket.n.hotels;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.NaTicket.n.R;
import com.NaTicket.n.hotels.adapters.RoomsAdapter;
import com.NaTicket.n.hotels.pojo.Room;
import com.NaTicket.n.utils.Util;

import java.util.ArrayList;

/**
 * Created by Ankit on 9/26/2017.
 */
public class Rooms_Activity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Room> roomList;
    RoomsAdapter mAdapter;
    RecyclerView roomsView;
    TextView totalRoomTv,totalAdultsTv,totalChildTv,addRoom,done,cancel;
    String Adults,Children;
    String TotalChildages="-1~-1~-1~-1~-1~-1~-1~-1";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rooms_activity);
        init();
        mAdapter = new RoomsAdapter(this, roomList);
        addNewRoom();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        roomsView.setLayoutManager(mLayoutManager);
        roomsView.setItemAnimator(new DefaultItemAnimator());
        roomsView.setAdapter(mAdapter);

    }

    private void init(){
        roomList = new ArrayList();
        roomsView = (RecyclerView) findViewById(R.id.roomsView);
        totalRoomTv = (TextView)findViewById(R.id.totalRoomTv);
        totalAdultsTv=(TextView)findViewById(R.id.totalAdultsTv);
        totalChildTv=(TextView)findViewById(R.id.totalChildTv);
        addRoom= (TextView) findViewById(R.id.addRoom);
        done= (TextView) findViewById(R.id.done);
        cancel= (TextView) findViewById(R.id.cancel);
        addRoom.setOnClickListener(this);
        done.setOnClickListener(this);
        cancel.setOnClickListener(this);


    }
    private void addNewRoom() {
        if (roomList.size()<4){
            Room newRoom = new Room();
            newRoom.setRoommNo(roomList.size());
            newRoom.setAdults(1);
            newRoom.setChild(0);
            roomList.add(newRoom);
            mAdapter.notifyDataSetChanged();
            updateViews();
        }else {
            Util.showMessage(this,"Only 4 Rooms can be Selected");
        }

    }

    private void updateViews() {
        totalRoomTv.setText("Room : "+roomList.size());
        updateTotalNoofAdults();
        updateTotalNoofChild();
    }


    public void removeRoom(int position) {
        for(int i=0;i<roomList.size();i++){
            if(i==position){
                roomList.remove(position);
            }
        }
        mAdapter.notifyDataSetChanged();
        updateViews();

    }

    public void updateTotalNoofAdults() {
        int adultCount=0;
        int room1adults = 0,room2adults = 0,room3adults = 0,room4adults = 0;
        for(int i=0;i<roomList.size();i++){
            adultCount=adultCount+roomList.get(i).getAdults();
            if (i==0){
                room1adults=roomList.get(i).getAdults();
                System.out.println("room1adults::"+room1adults);
            }else if (i==1){
                room2adults=roomList.get(i).getAdults();
                System.out.println("room2adults::"+room2adults);
            }else if (i==2){
                room3adults=roomList.get(i).getAdults();
                System.out.println("room3adults::"+room3adults);
            }else if (i==3){
                room4adults=roomList.get(i).getAdults();
                System.out.println("room4adults::"+room4adults);
            }
        }

        Adults=room1adults+"~"+room2adults+"~"+room3adults+"~"+room4adults;
        totalAdultsTv.setText("Adults : "+adultCount);
    }

    public void updateTotalNoofChild() {
        int childCount=0;
        int room1children = 0,room2children = 0,room3children= 0,room4children = 0;
        for(int i=0;i<roomList.size();i++){
            childCount=childCount+roomList.get(i).getChild();
            if (i==0){
                room1children=roomList.get(i).getChild();
                System.out.println("room1children::"+room1children);
            }else if (i==1){
                room2children=roomList.get(i).getChild();
                System.out.println("room1children::"+room2children);
            }else if (i==2){
                room3children=roomList.get(i).getChild();
                System.out.println("room1children::"+room3children);
            }else if (i==3){
                room4children=roomList.get(i).getChild();
                System.out.println("room1children::"+room4children);
            }
        }
        Children=room1children+"~"+room2children+"~"+room3children+"~"+room4children;
        totalChildTv.setText("Child : "+childCount);

    }

    public void updateChildAges(){

         String room1childrenages="-1~-1",room2childrenages="-1~-1",room3childrenages ="-1~-1",room4childrenages="-1~-1";

        for (int i=0;i<roomList.size();i++){
            if (i==0){
                room1childrenages=roomList.get(i).getChild1age()+"~"+roomList.get(i).getChild2age();
            }else if (i==1){
                room2childrenages=roomList.get(i).getChild1age()+"~"+roomList.get(i).getChild2age();
            }else if (i==2){
                room3childrenages=roomList.get(i).getChild1age()+"~"+roomList.get(i).getChild2age();
            }else if (i==3){
                room4childrenages=roomList.get(i).getChild1age()+"~"+roomList.get(i).getChild2age();
            }
        }
        TotalChildages=room1childrenages+"~"+room2childrenages+"~"+room3childrenages+"~"+room4childrenages;
        System.out.println("TotalChildagres::  "+TotalChildages);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addRoom:
                addNewRoom();
                break;
            case R.id.done:
                Hotel_utils hotel_utils;
                hotel_utils=new Hotel_utils(this);
                hotel_utils.addPaxDetails(Adults,Children, String.valueOf(roomList.size()),totalAdultsTv.getText().toString(),totalChildTv.getText().toString(),TotalChildages);
                Intent intent=new Intent();
                intent.putExtra("Rooms",totalRoomTv.getText().toString());
                intent.putExtra("Children",totalChildTv.getText().toString());
                intent.putExtra("Adults",totalAdultsTv.getText().toString());
                intent.putExtra("roomList",roomList);
                setResult(3,intent);
                finish();
                break;

            case R.id.cancel:
                finish();
                break;

        }
    }
}

