package com.NaTicket.n.hotels.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.NaTicket.n.hotels.CitySearchActivity;
import com.NaTicket.n.hotels.pojo.CityCTO;
import com.NaTicket.n.R;

import java.util.ArrayList;

/**
 * Created by Nagarjuna on 18-Jun-17.
 */

public class CitiesAdapter  extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> implements Filterable {

    private ArrayList<CityCTO> mArrayList;
    private ArrayList<CityCTO> mFilteredList;
    private CitySearchActivity activity;
    private String Hoteltype;


    public CitiesAdapter(ArrayList<CityCTO> citiesList, CitySearchActivity citySearchActivity,String HotelType) {
        mArrayList = citiesList;
        mFilteredList = citiesList;
        Hoteltype=HotelType;
        activity=citySearchActivity;

    }

    @Override
    public CitiesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cities_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CitiesAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.cityNameTv.setText(mFilteredList.get(i).getCityName());
        viewHolder.countryNameTv.setText(mFilteredList.get(i).getCountryName());

        if (Hoteltype.equals("1")){
            viewHolder.countryNameTv.setVisibility(View.GONE);
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
                    ArrayList<CityCTO> filteredList = new ArrayList<>();
                    for (CityCTO androidVersion : mArrayList) {
                        if (androidVersion.getCityName().toLowerCase().contains(charString)) {
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
                mFilteredList = (ArrayList<CityCTO>) filterResults.values;
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
