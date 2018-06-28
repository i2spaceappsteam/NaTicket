package com.NaTicket.n.buses.adapters;

/**
 * Created by Ankit on 7/6/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.NaTicket.n.R;
import com.NaTicket.n.buses.Buses_MainActivity;
import com.NaTicket.n.buses.SearchCityBus;
import com.NaTicket.n.buses.pojo.Top_city_DTO;

import java.util.ArrayList;
import java.util.List;

public class FromtopcitiesAdapter extends BaseAdapter {

    // Declare Variables
    SearchCityBus mContext;
    LayoutInflater inflater;
    private List<Top_city_DTO> TopCities = null;
    private ArrayList<Top_city_DTO> arraylist;


    public FromtopcitiesAdapter(SearchCityBus context, List<Top_city_DTO> topcity) {
        mContext = context;
        this.TopCities = topcity;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Top_city_DTO>();
        this.arraylist.addAll(topcity);
    }

    public class ViewHolder {
        TextView rank;
        TextView country;
        TextView population;
    }

    @Override
    public int getCount() {
        return TopCities.size();
    }

    @Override
    public Top_city_DTO getItem(int position) {
        return TopCities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.availablelist_item, null);
            // Locate the TextViews in listview_item.xml
            holder.rank = (TextView) view.findViewById(R.id.location);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.rank.setText(TopCities.get(position).getName());


        // Listen for ListView Item Click
        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Send single item click data to SingleItemView Class
                Intent intent = new Intent(mContext, Buses_MainActivity.class);
                // Pass all data rank
                intent.putExtra("rank", (TopCities.get(position).getId()));
                // Pass all data country
                SharedPreferences preference = mContext.getSharedPreferences("Sources", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preference.edit();
                editor.putString("FROMNAME",TopCities.get(position).getName());
                editor.putString("FROMID",TopCities.get(position).getId());
                editor.apply();

                // Start SingleItemView Class
                //mContext.startActivity(intent);

                mContext.getCityData(TopCities.get(position));

            }
        });

        return view;
    }
}
