package com.NaTicket.n.hotels.adapters;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.NaTicket.n.hotels.pojo.AvailableHotelsDTO;
import com.NaTicket.n.R;
import com.NaTicket.n.hotels.Hotel_utils;

import java.util.ArrayList;

/**
 * Created by Nagarjuna on 11/22/2017.
 */

public  class Hotel_Details extends Fragment {
    TextView hotelNameTv, hotelDescTv, roomTotalTv, serTaxTv;
    //LinearLayout amenitiesLinear;
    private AvailableHotelsDTO selHotel;
    private ArrayList<String> stringArrayList;
    private RecyclerView recyclerView;
    private Hotel_Details_Adapter adapter;


    public Hotel_Details() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_hotel_details, container, false);
        hotelNameTv =(TextView)view.findViewById(R.id.hotelNameTv);
       /*  hotelNameTv =(TextView)view.findViewById(R.id.hotelNameTv);
         hotelDescTv =(TextView)view.findViewById(R.id.hotelDescTv);
         roomTotalTv =(TextView)view.findViewById(R.id.roomTotalTv);
         serTaxTv =(TextView)view.findViewById(R.id.serTaxTv);*/
        //amenitiesLinear =(LinearLayout)view.findViewById(R.id.amenitiesLinear);
        //selHotel= (AvailableHotelsDTO) getActivity().getIntent().getSerializableExtra("SelHotel");

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        Hotel_utils hotel_utils=new Hotel_utils(getActivity());
        hotelNameTv.setText(hotel_utils.getHotelDetails("HotelName"));

        setData(); //adding data to array list
        adapter = new Hotel_Details_Adapter(getActivity(), stringArrayList);
        recyclerView.setAdapter(adapter);

        return view;
    }




    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        /*Hotel_utils hotel_utils=new Hotel_utils(getActivity());

        hotelNameTv.setText(hotel_utils.getHotelDetails("HotelName"));

        System.out.println("Hotel_Name== "+hotel_utils.getHotelDetails("HotelName"));
        String Descript=hotel_utils.getHotelDetails("Description");
        final String s = Descript.replaceAll("<br />","");
        hotelDescTv.setText(s);
        //setAmenities(selHotel.getFacilities(),amenitiesLinear);
   *//*     DecimalFormat df = new DecimalFormat("#.##");

        double roomtotal=Double.valueOf(selHotel.getRoomDetails().get(0).getRoomTotalZAR());
        int Total=getprice(roomtotal);

        double servicetax=Double.valueOf(selHotel.getRoomDetails().get(0).getServicetaxTotalZAR());
        int services=getprice(servicetax);
        roomTotalTv.setText("Room Total : R " +Total);
        serTaxTv.setText("Service Tax : R "+services);*//*
    }

    public int getprice(double fares){
        double dAbs = Math.abs(fares);
        int amt2 = (int) dAbs;
        double result = dAbs - (double) amt2;
        int finalrate;
        if (result<0.5){
            finalrate= (int) Math.floor(fares);
        }else {
            finalrate= (int) Math.ceil(fares);
        }
        return finalrate;
    }

    private void setAmenities(String facilities, LinearLayout amenitiesLinear) {
        if(facilities.length()>0){
            String[] amenitieslist = facilities.split(",");
            TextView[] myTextViews = new TextView[amenitieslist.length];
            for(int i=0;i<amenitieslist.length;i++){
                final TextView rowTextView = new TextView(getActivity());
                rowTextView.setText("-" + amenitieslist[i]);
                amenitiesLinear.addView(rowTextView);
                myTextViews[i] = rowTextView;
            }
        }
    }*/




    }

    private void setData() {
        stringArrayList = new ArrayList<>();

        Hotel_utils hotel_utils=new Hotel_utils(getActivity());
        String Descript=hotel_utils.getHotelDetails("Description");
        String s=Descript.replace("<br />","");
        String s1=s.replace("*","");
        stringArrayList.add(s1);
        /*for (int i = 0; i < 100; i++) {
            stringArrayList.add("Item " + (i + 1));
        }*/
    }
}