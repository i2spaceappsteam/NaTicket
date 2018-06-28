package com.NaTicket.n.hotels;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.NaTicket.n.hotels.adapters.RoomsSelectionMultiple;
import com.NaTicket.n.hotels.adapters.RoomsSelectionOpenSingle;
import com.NaTicket.n.hotels.pojo.AvailableHotelsDTO;
import com.NaTicket.n.hotels.pojo.Room;
import com.NaTicket.n.hotels.pojo.RoomDetailsDTO;
import com.NaTicket.n.utils.BackActivity;
import com.NaTicket.n.R;
import com.NaTicket.n.hotels.pojo.SelectionDetailsDTO;
import com.NaTicket.n.utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SelectRoomActivityNew extends BackActivity {


    private RecyclerView roomList_view;
    private AvailableHotelsDTO selHotel;
    private AvailableHotelsDTO hotelDetailselHotel;
    private RoomDetailsDTO roomDetailsDTO;
    private SelectionDetailsDTO selDetails;
    ArrayList<Room> roomList;
    private ProgressDialog mProgressDialog;
    private int selectedID=0;
    String[] RoomsArray;
    String Adults,Children,ChildAges;
    String Rooms;
    Hotel_utils hotel_utils;
    String RoomsDetails;
    HashMap<String, JSONObject> Roomslist=new HashMap<>();
    ArrayList<String> selectedList=new ArrayList<>();
    Hotel_utils utils;
    int NoofRooms;
    String Combination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_room_new);
        TextView toolbartitle=(TextView)findViewById(R.id.toolbartitle);
        inittoolbar();
        toolbartitle.setText("Select Room");
        utils=new Hotel_utils(this);
        roomList_view = (RecyclerView) findViewById(R.id.roomList_view);
        selHotel = (AvailableHotelsDTO) getIntent().getSerializableExtra("SelHotel");
        roomList = (ArrayList<Room>) getIntent().getSerializableExtra("roomList");
        selDetails = (SelectionDetailsDTO) getIntent().getSerializableExtra("selDetails");
        NoofRooms= Integer.parseInt(utils.getPaxDetails("Rooms"));
        TextView proceedToBackTv =(TextView)findViewById(R.id.proceedToBackTv);
        proceedToBackTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selDetails.setSelectedRooms(selectedList);
                String Comb=selHotel.getRoomCombination().toLowerCase();
                if (Comb.matches("fixedcombination")){
                    if(selDetails.getSelectedRooms().size()==NoofRooms) {
                     sendparams();
                    }else {
                        Util.showMessage(SelectRoomActivityNew.this,"Select A Combination of Rooms");
                    }
                }else if (Comb.matches("opencombination")){
                    if (selHotel.getRoomChain().contains("|")){
                        if(selDetails.getSelectedRooms().size()==NoofRooms) {
                          sendparams();
                        }else {
                            Util.showMessage(SelectRoomActivityNew.this,"Select A Room in each Combination");
                        }
                    }else {
                        if(selectedID!=0 && selectedID!=-1) {
                         sendparams();
                        }else{
                            Util.showMessage(SelectRoomActivityNew.this, "Select A Room");
                        }

                    }
                }
            }
        });

        hotel_utils=new Hotel_utils(this);

        Adults= hotel_utils.getPaxDetails("Adults");
        Children=hotel_utils.getPaxDetails("Child");
        Rooms=hotel_utils.getPaxDetails("Rooms");
        ChildAges=hotel_utils.getPaxDetails("ChildAges");
        createSelections(selHotel);

    }


    private void sendparams(){
        Intent i = new Intent(SelectRoomActivityNew.this, ReviewActivity.class);
        i.putExtra("roomList", roomList);
        i.putExtra("SelHotel", selHotel);
        i.putExtra("selDetails", selDetails);
        startActivity(i);
    }

    private void createSelections(AvailableHotelsDTO hotelsDetailDTO) {

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/OpenSans_Regular.ttf");
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 20, 0, 0);

        RoomsDetails=hotelsDetailDTO.getmRoomDetail();

        try {
            JSONObject object=new JSONObject(RoomsDetails);
            JSONArray array=object.getJSONArray("RoomDetails");
            for (int p = 0; p <array.length(); p++) {
                JSONObject roomObj = array.getJSONObject(p);
                String roomIndex=roomObj.getString("RoomIndex");
                Roomslist.put(roomIndex,roomObj);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

     /*   ArrayList<RoomDetailsDTO> rooms=new ArrayList<>();
        for (int p = 0; p < hotelsDetailDTO.getRoomDetails().size(); p++) {
            String roomindex = String.valueOf(hotelsDetailDTO.getRoomDetails().get(p).getRoomIndex());
            rooms.add(hotelsDetailDTO.getRoomDetails().get(p));
            System.out.println("Index=== "+roomindex);
            Roomslist.put(roomindex,rooms);
        }*/


        //String roomChain = "1,2|3,4|5,6";

        String roomChain = hotelsDetailDTO.getRoomChain();
        //Toast.makeText(getApplicationContext(),"Room Chain+== "+roomChain,Toast.LENGTH_LONG).show();
        String Combination=hotelsDetailDTO.getRoomCombination().toLowerCase();
        if (hotelsDetailDTO.getRoomCombination()!=null && Combination.matches("opencombination")) {
            if (roomChain.contains("|")) {
                String[] pipeSplits = roomChain.split("\\|");

                RoomsSelectionMultiple mAdapter=new RoomsSelectionMultiple(this,hotelsDetailDTO,Roomslist,selDetails);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                roomList_view.setLayoutManager(mLayoutManager);
                roomList_view.setItemAnimator(new DefaultItemAnimator());
                roomList_view.setAdapter(mAdapter);
            } else {

                ArrayList<String> roomIndices = new ArrayList<>(Arrays.asList(roomChain.split(",")));
                RoomsSelectionOpenSingle mAdapter = new RoomsSelectionOpenSingle(this,roomIndices,hotelsDetailDTO,selDetails);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                roomList_view.setLayoutManager(mLayoutManager);
                roomList_view.setItemAnimator(new DefaultItemAnimator());
                roomList_view.setAdapter(mAdapter);

            }
        } else if (hotelsDetailDTO.getRoomCombination()!=null && Combination.matches("fixedcombination")) {
                RoomsSelectionMultiple mAdapter=new RoomsSelectionMultiple(this,hotelsDetailDTO,Roomslist,selDetails);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                roomList_view.setLayoutManager(mLayoutManager);
                roomList_view.setItemAnimator(new DefaultItemAnimator());
                roomList_view.setAdapter(mAdapter);

        }
    }



    public void setSeleectedID(int selectedIndex) {
        selectedID=selectedIndex;
        //Util.showMessage(this,selectedID+"");
    }

    public void setSeleectedID(int selectedIndex, String[] roomArray,int Index,String combination) {
         selectedID=selectedIndex;
         RoomsArray=roomArray;
         Combination=combination;
        if (combination.matches("opencombination")){
            if (!isAddedtolists(selectedID)&&selectedList.size() <= NoofRooms) {
                selectedList.add(String.valueOf(selectedID));
            }
        }else if (combination.matches("fixedcombination")){
            selectedList.clear();
            if (!isAddedtolists(selectedID)&&selectedList.size() < NoofRooms){
                for (String a : RoomsArray) {
                    selectedList.add(a);
                }
            }
        }else if (combination.matches("single")){
            selectedList.clear();
            if (!isAddedtolists(selectedID)&&selectedList.size() < NoofRooms){
                for (String a : RoomsArray) {
                    selectedList.add(a);
                }
            }
        }

        System.out.println("Room Array=="+RoomsArray.length);
        System.out.println("Selected=="+selectedList);
        System.out.println("Index-- "+selectedID);

    }

    private boolean isAddedtolists(int position){
        boolean result = false;

        for (String a : RoomsArray) {
            //int pos =Integer.parseInt(a);
            if (selectedList.contains(a)){
                for (int j = 0; j < RoomsArray.length; j++) {
                    if (selectedList.contains(RoomsArray[j])){
                        selectedList.remove(RoomsArray[j]);
                    }
                }
                result = false;
            }
        }
        return result;
    }


}
