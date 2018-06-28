package com.NaTicket.n.flights.adpaters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.NaTicket.n.flights.Flights_City_Search_Activity;
import com.NaTicket.n.R;
import com.NaTicket.n.flights.pojo.Flight_City_DTO;

import java.util.ArrayList;

/**
 * Created by Nagarjuna on 12/30/2017.
 */

public class Flights_Cities_Adpaters extends RecyclerView.Adapter<Flights_Cities_Adpaters.ViewHolder> implements Filterable {

        ArrayList<Flight_City_DTO> mArrayList=new ArrayList<>();
        ArrayList<Flight_City_DTO> mFilteredList=new ArrayList<>();
        Flights_City_Search_Activity activity;


    public Flights_Cities_Adpaters(ArrayList<Flight_City_DTO> citiesList, Flights_City_Search_Activity citySearchActivity) {
        mArrayList = citiesList;
        mFilteredList = citiesList;
        activity=citySearchActivity;
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Flights_Cities_Adpaters.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cities_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Flights_Cities_Adpaters.ViewHolder viewHolder, final int i) {

        viewHolder.cityNameTv.setText(mFilteredList.get(i).getAirportCode());
        viewHolder.countryNameTv.setText(mFilteredList.get(i).getCity()+ ", "+mFilteredList.get(i).getCountry()+"-"+mFilteredList.get(i).getAirportDesc()+"");

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getCityData(mFilteredList.get(i));
            }
        });
    }


    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFilteredList = mArrayList;
                } else {

                    ArrayList<Flight_City_DTO> filteredList = new ArrayList<>();
                    for (Flight_City_DTO androidVersion : mArrayList) {
                        if (androidVersion.getCity().toLowerCase().contains(charString)||
                                androidVersion.getAirportCode().toLowerCase().contains(charString)||
                                androidVersion.getCountry().toLowerCase().contains(charString)) {
                            filteredList.add(androidVersion);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<Flight_City_DTO>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView cityNameTv,countryNameTv;
        public ViewHolder(View view) {
            super(view);
            cityNameTv = (TextView)view.findViewById(R.id.cityNameTv);
            countryNameTv = (TextView)view.findViewById(R.id.countryNameTv);
        }
    }


}
