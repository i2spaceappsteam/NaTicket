package com.NaTicket.n.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.NaTicket.n.common.Offers_Activity;
import com.NaTicket.n.common.pojo.GetPromoCodesDTO;
import com.bumptech.glide.Glide;
import com.NaTicket.n.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Nagarjuna on 14-01-2018.
 */

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.MyViewHolder> {

    private GetPromoCodesDTO[] promoCodesList;
    private Context context;
    public OffersAdapter(GetPromoCodesDTO[] promoCodesList, Offers_Activity offersActivity) {
        this.promoCodesList=promoCodesList;
        context=offersActivity;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView promoNameTv, discountTv, validTv,promoTv;
        ImageView serviceIv;

        public MyViewHolder(View view) {
            super(view);
            promoNameTv = (TextView) view.findViewById(R.id.promoNameTv);
            discountTv = (TextView) view.findViewById(R.id.discountTv);
            validTv = (TextView) view.findViewById(R.id.validTv);
            promoTv = (TextView) view.findViewById(R.id.promoTv);
            serviceIv=(ImageView)view.findViewById(R.id.serviceIv);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offers_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        GetPromoCodesDTO offer = promoCodesList[position];
        int featureItem = offer.getServiceType();

            if (offer.getDiscountType() == 0) {
                if (featureItem == 3) {
                    holder.discountTv.setText("Get " + offer.getDiscount() + "% " + "cash back on Flight booking with order value between INR " +
                            offer.getFromAmount() + " to INR " + offer.getToAmount());
                } else if (featureItem == 5) {
                    holder.discountTv.setText("Get " + offer.getDiscount() + "% " + "cash back on Hotel booking with order value between INR " +
                            offer.getFromAmount() + " to INR " + offer.getToAmount());
                } else if (featureItem == 7) {
                    holder.discountTv.setText("Get " + offer.getDiscount() + "% " + "cash back on Recharges with order value between INR " +
                            offer.getFromAmount() + " to INR " + offer.getToAmount());
                } else if (featureItem == 8) {
                    holder.discountTv.setText("Get " + offer.getDiscount() + "% " + "cash back on Bill Pay with order value between INR " +
                            offer.getFromAmount() + " to INR " + offer.getToAmount());
                }else if (featureItem==1){
                    holder.discountTv.setText("Get " + offer.getDiscount() + "% " + "cash back on Bus booking with order value between INR " +
                            offer.getFromAmount() + " to INR " + offer.getToAmount());
                }
            }else {
                if (featureItem == 3) {
                    holder.discountTv.setText("Get Flat INR " + offer.getDiscount() + " cash back on Flight booking with order value between INR " +
                            offer.getFromAmount() + " to INR " + offer.getToAmount());
                } else if (featureItem == 5) {
                    holder.discountTv.setText("Get Flat INR " + offer.getDiscount()  + " cash back on Hotel booking with order value between INR " +
                            offer.getFromAmount() + " to INR " + offer.getToAmount());
                } else if (featureItem == 7) {
                    holder.discountTv.setText("Get Flat INR " + offer.getDiscount()  + " cash back on Recharges with order value between INR " +
                            offer.getFromAmount() + " to INR " + offer.getToAmount());
                } else if (featureItem == 8) {
                    holder.discountTv.setText("Get Flat INR " + offer.getDiscount() + " cash back on Bill Pay with order value between INR" +
                            offer.getFromAmount() + " to INR " + offer.getToAmount());
                }else if (featureItem==1){
                    holder.discountTv.setText("Get Flat INR " + offer.getDiscount() + " cash back on Bus booking with order value between INR " +
                            offer.getFromAmount() + " to INR " + offer.getToAmount());
                }
            }

            String ValidFrom = offer.getValidFrom();
            String ValidTo = offer.getValidTill();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
            SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

            Date FromDate = null, Todate = null;
            try {
                FromDate = dateFormat.parse(ValidFrom);
                Todate = dateFormat.parse(ValidTo);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String ValidFromdate = output.format(FromDate);
            String ValidTillDate = output.format(Todate);
            holder.validTv.setText("From " + ValidFromdate + " To " + ValidTillDate);
            holder.promoTv.setText(offer.getCode());
            int featureImage = R.mipmap.na_ticket_round;

            if (featureItem == 3) {
                holder.promoNameTv.setText("Flights");
                featureImage = R.drawable.nav_flights;
            }else if (featureItem == 1) {
                holder.promoNameTv.setText("Bus");
                featureImage = R.drawable.nav_bus;
            } else if (featureItem == 5) {
                holder.promoNameTv.setText("Hotels");
                featureImage = R.drawable.nav_hotel;
            }else if (featureItem==7){
                holder.promoNameTv.setText("Recharge");
                featureImage = R.drawable.nav_recharge;
            }else if (featureItem==8){
                holder.promoNameTv.setText("Bill Pay");
                featureImage = R.drawable.nav_reports;
            }

            Glide.with(context)
                    .load(featureImage)
                    .placeholder(R.mipmap.na_ticket_round)
                    .error(R.mipmap.na_ticket_round)
                    .into(holder.serviceIv);


    }

    @Override
    public int getItemCount() {
        return promoCodesList.length;
    }
}
