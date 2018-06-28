package com.NaTicket.n.hotels.adapters;

/**
 * Created by Nagarjuna on 11/24/2017.
 */

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.NaTicket.n.R;

import java.util.List;

public class Hotel_Details_Adapter extends RecyclerView.Adapter<Hotel_Details_Adapter.ViewHolder> {

    private List<String> friends;
    private Activity activity;

    public Hotel_Details_Adapter(Activity activity, List<String> friends) {
        this.friends = friends;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        //inflate your layout and pass it to view holder
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.hotel_descrip_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Hotel_Details_Adapter.ViewHolder viewHolder, int position) {

        viewHolder.item.setText(friends.get(position));
    }

    @Override
    public int getItemCount() {
        return (null != friends ? friends.size() : 0);
    }

    /**
     * View holder to display each RecylerView item
     */
    protected class ViewHolder extends RecyclerView.ViewHolder {
        private TextView item;

        public ViewHolder(View view) {
            super(view);
            item = (TextView) view.findViewById(R.id.hotel_descpt);
        }
    }
}