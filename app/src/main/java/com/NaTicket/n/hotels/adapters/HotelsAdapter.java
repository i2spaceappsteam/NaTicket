package com.NaTicket.n.hotels.adapters;

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

import com.NaTicket.n.hotels.pojo.AvailableHotelsDTO;
import com.bumptech.glide.Glide;
import com.NaTicket.n.R;
import com.NaTicket.n.common.pojo.Currency_Utils;
import com.NaTicket.n.hotels.AvailableHotelsActivity;
import com.NaTicket.n.hotels.pojo.SelectionDetailsDTO;
import com.NaTicket.n.utils.Util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by admin on 18-Jun-17.
 */

public class HotelsAdapter extends RecyclerView.Adapter<HotelsAdapter.ViewHolder> implements Filterable {

    private ArrayList<AvailableHotelsDTO> mArrayList;
    private ArrayList<AvailableHotelsDTO> mFilteredList;
    private AvailableHotelsActivity activity;
    private static final String TAG = "HotelsListAdapter";
    Currency_Utils currency_utils;
    SelectionDetailsDTO selDetails;

    public HotelsAdapter(AvailableHotelsActivity activity, ArrayList<AvailableHotelsDTO> hotelsList,SelectionDetailsDTO selectionDetailsDTO) {
        mArrayList = hotelsList;
        mFilteredList = hotelsList;
        this.activity=activity;
        currency_utils=new Currency_Utils(activity);
        this.selDetails=selectionDetailsDTO;

    }

    @Override
    public HotelsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hotels_row_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HotelsAdapter.ViewHolder viewHolder, final int i) {


        double TotalRate;
        viewHolder.hotelNameTv.setText(mFilteredList.get(i).getHotelName());
        viewHolder.hotelDescTv.setText(mFilteredList.get(i).getHotelAddress());
        viewHolder.hotelRating.setRating(mFilteredList.get(i).getStarRating());

          double currValue= Double.parseDouble(currency_utils.getCurrencyValue("Currency_Value"));

            TotalRate= (Double.valueOf(mFilteredList.get(i).getRoomDetails().get(0).getRoomTotal())+
                    Double.valueOf(mFilteredList.get(i).getRoomDetails().get(0).getServicetaxTotal())/selDetails.getNoofDays())*currValue ;
            Glide.with(activity)
                    .load(mFilteredList.get(i).getHotelImages().get(0).getImagepath())
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_placeholder)
                    .into(viewHolder.hotelIv);

        //int Amount = Util.getprice(TotalRate);

        String Currency_Symbol=currency_utils.getCurrencyValue("Currency_Symbol");

        DecimalFormat df = new DecimalFormat("#.##");
        //System.out.println("Rounded Values== "+df.format(Amount));

        viewHolder.costTv.setText(Currency_Symbol+Util.getprice(TotalRate)+"");
        viewHolder.hotelDeals.setText("Deals from "+Currency_Symbol+Util.getprice(TotalRate)+" ");
        float rates=mFilteredList.get(i).getStarRating();
        String Rating= String.valueOf(rates);
        viewHolder.RatingText.setText(Rating);



        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getHotelData(mFilteredList.get(i));
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

                    ArrayList<AvailableHotelsDTO> filteredList = new ArrayList<>();

                    for (AvailableHotelsDTO androidVersion : mArrayList) {

                        if (androidVersion.getHotelName().toLowerCase().contains(charString)||
                                androidVersion.getHotelAddress().toLowerCase().contains(charString)) {
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
                mFilteredList = (ArrayList<AvailableHotelsDTO>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView hotelNameTv,hotelDescTv,costTv,hotelDeals,RatingText;
        private ImageView hotelIv;
        private RatingBar hotelRating;
        public ViewHolder(View view) {
            super(view);
            hotelIv = (ImageView)view.findViewById(R.id.hotelIv);
            hotelNameTv = (TextView)view.findViewById(R.id.hotelNameTv);
            hotelDescTv = (TextView)view.findViewById(R.id.hotelDescTv);
            hotelRating =(RatingBar)view.findViewById(R.id.hotelRating);
            RatingText= (TextView) view.findViewById(R.id.rating_text);
            hotelDeals= (TextView) view.findViewById(R.id.deals);
            costTv=(TextView)view.findViewById(R.id.costTv);
        }
    }


    public ArrayList<AvailableHotelsDTO> sortByNameAsc() {
        Comparator<AvailableHotelsDTO> comparator = new Comparator<AvailableHotelsDTO>() {
            @Override
            public int compare(AvailableHotelsDTO object1, AvailableHotelsDTO object2) {
                return object1.getHotelName().compareToIgnoreCase(object2.getHotelName());
            }
        };
        Collections.sort(mFilteredList, comparator);
        return mFilteredList;
    }


    public ArrayList<AvailableHotelsDTO> sortByNameDesc() {
        Comparator<AvailableHotelsDTO> comparator = new Comparator<AvailableHotelsDTO>() {
            @Override
            public int compare(AvailableHotelsDTO object1, AvailableHotelsDTO object2) {
                return object2.getHotelName().compareToIgnoreCase(object1.getHotelName());
            }
        };
        Collections.sort(mFilteredList, comparator);
        return mFilteredList;
    }

    public ArrayList<AvailableHotelsDTO> sortByStarAsc() {
        Comparator<AvailableHotelsDTO> comparator = new Comparator<AvailableHotelsDTO>() {
            @Override
            public int compare(AvailableHotelsDTO object1, AvailableHotelsDTO object2) {

                String value1 = ""+object1.getStarRating();
                String value2 = ""+object2.getStarRating();
                return value1.compareTo(value2);
            }
        };
        Collections.sort(mFilteredList, comparator);
        return mFilteredList;
    }


    public ArrayList<AvailableHotelsDTO> sortByStarDesc() {
        Comparator<AvailableHotelsDTO> comparator = new Comparator<AvailableHotelsDTO>() {
            @Override
            public int compare(AvailableHotelsDTO object1, AvailableHotelsDTO object2) {
                String value1 = ""+object1.getStarRating();
                String value2 = ""+object2.getStarRating();
                return value2.compareTo(value1);
            }
        };
        Collections.sort(mFilteredList, comparator);
        return mFilteredList;
    }


    public ArrayList<AvailableHotelsDTO> sortByPriceAsc() {
        Comparator<AvailableHotelsDTO> comparator = new Comparator<AvailableHotelsDTO>() {
            @Override
            public int compare(AvailableHotelsDTO object1, AvailableHotelsDTO object2) {
                float f1,f2;
                     f1 = (Float) Float.parseFloat(object1.getRoomDetails().get(0).getRoomTotal()) + (Float) Float.parseFloat(object1.getRoomDetails().get(0).getServicetaxTotal());
                     f2 = (Float) Float.parseFloat(object2.getRoomDetails().get(0).getRoomTotal()) + (Float) Float.parseFloat(object2.getRoomDetails().get(0).getServicetaxTotal());
                return ((Float) f1).compareTo(f2);
            }
        };
        Collections.sort(mFilteredList, comparator);
        return mFilteredList;
    }


    public ArrayList<AvailableHotelsDTO> sortByPriceDesc() {
        Comparator<AvailableHotelsDTO> comparator = new Comparator<AvailableHotelsDTO>() {
            @Override
            public int compare(AvailableHotelsDTO object1, AvailableHotelsDTO object2) {
                float f1,f2;
                    f1 = (Float) Float.parseFloat(object1.getRoomDetails().get(0).getRoomTotal()) + (Float) Float.parseFloat(object1.getRoomDetails().get(0).getServicetaxTotal());
                    f2 = (Float) Float.parseFloat(object2.getRoomDetails().get(0).getRoomTotal()) + (Float) Float.parseFloat(object2.getRoomDetails().get(0).getServicetaxTotal());
                return ((Float) f2).compareTo(f1);
            }
        };

        Collections.sort(mFilteredList, comparator);
        return mFilteredList;
    }


    public void refreshList(ArrayList<AvailableHotelsDTO> list){
        if (list != null) {
            this.mFilteredList= list;
            for (AvailableHotelsDTO ho : mFilteredList) {
                Log.e(TAG, "refreshList:  " + ho.getHotelName());
            }
            notifyDataSetChanged();
        }
    }

}
