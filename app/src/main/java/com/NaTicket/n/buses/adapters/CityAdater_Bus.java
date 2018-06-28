package com.NaTicket.n.buses.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.NaTicket.n.R;
import com.NaTicket.n.buses.SearchCityBus;
import com.NaTicket.n.buses.pojo.Top_city_DTO;

import java.util.ArrayList;

/**
 * Created by Ankit on 23-02-2018.
 */

public class CityAdater_Bus extends RecyclerView.Adapter<CityAdater_Bus.ViewHolder> implements Filterable {

    private ArrayList<Top_city_DTO> mArrayList;
    private ArrayList<Top_city_DTO> mFilteredList;
    private SearchCityBus activity;


    public CityAdater_Bus(ArrayList<Top_city_DTO> citiesList, SearchCityBus citySearchActivity) {
        mArrayList = citiesList;
        mFilteredList = citiesList;
        activity=citySearchActivity;

    }

    @Override
    public CityAdater_Bus.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.availablelist_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CityAdater_Bus.ViewHolder viewHolder, final int i) {

        try {
            viewHolder.cityNameTv.setText(mFilteredList.get(i).getName());


        }catch(Exception e){

        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getCityData(mFilteredList.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {

            return mFilteredList.size();

    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().toLowerCase();
                if (charString.isEmpty()) {
                    mFilteredList = mArrayList;
                } else {
                    ArrayList<Top_city_DTO> filteredList = new ArrayList<>();
                    for (Top_city_DTO androidVersion : mArrayList) {
                        if (androidVersion.getName().toLowerCase().contains(charString)) {
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
                mFilteredList = (ArrayList<Top_city_DTO>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView cityNameTv;
        public ViewHolder(View view) {
            super(view);

            cityNameTv = (TextView)view.findViewById(R.id.location);


        }
    }
}