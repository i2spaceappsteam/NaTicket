package com.NaTicket.n.hotels.adapters;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.NaTicket.n.R;
import com.NaTicket.n.hotels.Hotel_utils;
import com.NaTicket.n.hotels.pojo.AvailableHotelsDTO;

import java.util.ArrayList;

/**
 * Created by Nagarjuna on 11/22/2017.
 */

public class Hotel_Amenities extends Fragment {

    LinearLayout amenitiesLinear;
    private AvailableHotelsDTO selHotel;
    private ArrayList<String> stringArrayList;
    private RecyclerView recyclerView;
    private Hotel_Details_Adapter adapter;


    public Hotel_Amenities() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_hotel_aminities, container, false);
        //amenitiesLinear =(LinearLayout)view.findViewById(R.id.amenitiesLinear);
        //selHotel= (AvailableHotelsDTO) getActivity().getIntent().getSerializableExtra("SelHotel");
        //selHotel= (AvailableHotelsDTO) this.getArguments().getSerializable("SelHotel");

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler1);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        setData(); //adding data to array list
        adapter = new Hotel_Details_Adapter(getActivity(), stringArrayList);
        recyclerView.setAdapter(adapter);
        return view;

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
    }

    private void setData() {
        stringArrayList = new ArrayList<>();
        Hotel_utils hotel_utils=new Hotel_utils(getActivity());
        String amen=hotel_utils.getHotelDetails("Amenities");

        if (amen!=null&&!amen.equals("")){
            String[] amenitieslist = amen.split(",");
            for(int i=0;i<amenitieslist.length;i++){
                stringArrayList.add("-" + amenitieslist[i]);
            }
        }else {
            stringArrayList.add("-" + "No Amenities Available");
        }

    }

}