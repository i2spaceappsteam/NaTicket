package com.NaTicket.n.holidays.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.NaTicket.n.R;
import com.NaTicket.n.common.pojo.Currency_Utils;
import com.NaTicket.n.holidays.AvailableHolidaysActivity;
import com.NaTicket.n.holidays.pojo.AvailableHolidayPackagesDTO;
import com.NaTicket.n.utils.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by admin on 18-Jun-17.
 */

public class HolidaysAdapter extends RecyclerView.Adapter<HolidaysAdapter.ViewHolder> implements Filterable {

    private ArrayList<AvailableHolidayPackagesDTO> mArrayList;
    private ArrayList<AvailableHolidayPackagesDTO> mFilteredList;
    private AvailableHolidaysActivity activity;
    Currency_Utils currency_utils;
    private static final String TAG = "Holidaylist";


    public HolidaysAdapter(AvailableHolidaysActivity activity, ArrayList<AvailableHolidayPackagesDTO> hotelsList) {
         mArrayList = hotelsList;
         mFilteredList = hotelsList;
         this.activity=activity;
         currency_utils=new Currency_Utils(activity);

    }

    @Override
    public HolidaysAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hotels_row_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HolidaysAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.hotelNameTv.setText(mFilteredList.get(i).getName());
        viewHolder.hotelDescTv.setText(mFilteredList.get(i).getDuration()+ " Nights / "+getDays(mFilteredList.get(i).getDuration()) +" Days");
        int Rating=mFilteredList.get(i).getHotelRating();
        if (Rating==51){
            viewHolder.hotelRating.setRating(0);
        }else if (Rating==52){
            viewHolder.hotelRating.setRating(1);
        }else if (Rating==53){
            viewHolder.hotelRating.setRating(2);
        }else if (Rating==54){
            viewHolder.hotelRating.setRating(3);
        }else if (Rating==55){
            viewHolder.hotelRating.setRating(4);
        }else if (Rating==56){
            viewHolder.hotelRating.setRating(5);
        }else{
            viewHolder.hotelRating.setRating(5);
        }

        double currValue= Double.parseDouble(currency_utils.getCurrencyValue("Currency_Value"));
        String Currency_Symbol=currency_utils.getCurrencyValue("Currency_Symbol");

        double TotalRate;

        TotalRate=Double.valueOf(mFilteredList.get(i).getFareDetails().get(0).getAdultFare())*currValue;

        viewHolder.costTv.setText(Currency_Symbol+ Util.getprice(TotalRate)+" ");
        viewHolder.deals.setText(mFilteredList.get(i).getHighlight());
        Glide.with(activity)
                .load(mFilteredList.get(i).getHolidayPackageImages().get(0).getImagepath())
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .into(viewHolder.hotelIv);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getHotelData(mFilteredList.get(i));
            }
        });
    }

    private int getDays(int duration) {
        return duration+1;
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

                    ArrayList<AvailableHolidayPackagesDTO> filteredList = new ArrayList<>();

                    for (AvailableHolidayPackagesDTO androidVersion : mArrayList) {

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
                mFilteredList = (ArrayList<AvailableHolidayPackagesDTO>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView hotelNameTv,hotelDescTv,costTv,deals;
        private ImageView hotelIv;
        private RatingBar hotelRating;
        public ViewHolder(View view) {
            super(view);
            hotelIv = (ImageView)view.findViewById(R.id.hotelIv);
            hotelNameTv = (TextView)view.findViewById(R.id.hotelNameTv);
            hotelDescTv = (TextView)view.findViewById(R.id.hotelDescTv);
            hotelRating =(RatingBar)view.findViewById(R.id.hotelRating);
            deals= (TextView) view.findViewById(R.id.deals);
            costTv=(TextView)view.findViewById(R.id.costTv);
        }
    }

    public ArrayList<AvailableHolidayPackagesDTO> sortByNameAsc() {
        Comparator<AvailableHolidayPackagesDTO> comparator = new Comparator<AvailableHolidayPackagesDTO>() {
            @Override
            public int compare(AvailableHolidayPackagesDTO object1, AvailableHolidayPackagesDTO object2) {
                return object1.getName().compareToIgnoreCase(object2.getName());
            }
        };
        Collections.sort(mFilteredList, comparator);
        return mFilteredList;
    }


    public ArrayList<AvailableHolidayPackagesDTO> sortByNameDesc() {
        Comparator<AvailableHolidayPackagesDTO> comparator = new Comparator<AvailableHolidayPackagesDTO>() {
            @Override
            public int compare(AvailableHolidayPackagesDTO object1, AvailableHolidayPackagesDTO object2) {
                return object2.getName().compareToIgnoreCase(object1.getName());
            }
        };
        Collections.sort(mFilteredList, comparator);
        return mFilteredList;
    }

    public ArrayList<AvailableHolidayPackagesDTO> sortByStarAsc() {
        Comparator<AvailableHolidayPackagesDTO> comparator = new Comparator<AvailableHolidayPackagesDTO>() {
            @Override
            public int compare(AvailableHolidayPackagesDTO object1, AvailableHolidayPackagesDTO object2) {

                float f1,f2;
                f1 = (Float) Float.parseFloat(""+object1.getDuration());
                f2 = (Float) Float.parseFloat(""+object2.getDuration());
                return ((Float) f1).compareTo(f2);

               /* int value1 =  (int) Integer.parseInt(""+object1.getDuration());
                int value2 =  (int)   Integer.parseInt(""+object2.getDuration());
                return  ((int) value1).compareTo(value2);*/
            }
        };
        Collections.sort(mFilteredList, comparator);
        return mFilteredList;
    }


    public ArrayList<AvailableHolidayPackagesDTO> sortByStarDesc() {
        Comparator<AvailableHolidayPackagesDTO> comparator = new Comparator<AvailableHolidayPackagesDTO>() {
            @Override
            public int compare(AvailableHolidayPackagesDTO object1, AvailableHolidayPackagesDTO object2) {

                float f1,f2;
                f1 = (Float) Float.parseFloat(""+object1.getDuration());
                f2 = (Float) Float.parseFloat(""+object2.getDuration());
                return ((Float) f2).compareTo(f1);



             /*   String value1 = ""+object1.getDuration();
                String value2 = ""+object2.getDuration();
                return value2.compareTo(value1);*/
            }
        };
        Collections.sort(mFilteredList, comparator);
        return mFilteredList;
    }


    public ArrayList<AvailableHolidayPackagesDTO> sortByPriceAsc() {
        Comparator<AvailableHolidayPackagesDTO> comparator = new Comparator<AvailableHolidayPackagesDTO>() {
            @Override
            public int compare(AvailableHolidayPackagesDTO object1, AvailableHolidayPackagesDTO object2) {
                float f1,f2;
                f1 = (Float) Float.parseFloat(object1.getFareDetails().get(0).getAdultFare());
                f2 = (Float) Float.parseFloat(object2.getFareDetails().get(0).getAdultFare());
                return ((Float) f1).compareTo(f2);
            }
        };
        Collections.sort(mFilteredList, comparator);
        return mFilteredList;
    }


    public ArrayList<AvailableHolidayPackagesDTO> sortByPriceDesc() {
        Comparator<AvailableHolidayPackagesDTO> comparator = new Comparator<AvailableHolidayPackagesDTO>() {
            @Override
            public int compare(AvailableHolidayPackagesDTO object1, AvailableHolidayPackagesDTO object2) {
                float f1,f2;
                f1 = (Float) Float.parseFloat(object1.getFareDetails().get(0).getAdultFare());
                f2 = (Float) Float.parseFloat(object2.getFareDetails().get(0).getAdultFare());
                return ((Float) f2).compareTo(f1);
            }
        };

        Collections.sort(mFilteredList, comparator);
        return mFilteredList;
    }


    public void refreshList(ArrayList<AvailableHolidayPackagesDTO> list){
        if (list != null) {
            this.mFilteredList= list;
            for (AvailableHolidayPackagesDTO ho : mFilteredList) {
                Log.e(TAG, "refreshList:  " + ho.getName());
            }
            notifyDataSetChanged();
        }
    }

}
