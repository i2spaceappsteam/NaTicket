package com.NaTicket.n.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.NaTicket.n.R;

import java.util.ArrayList;

/**
 * Created by Ankit on 8/17/2017.
 */

public class FeatureGridAdapter extends BaseAdapter {
    private Context context;
    private final ArrayList<Integer> featuresList;
    public FeatureGridAdapter(Context context, ArrayList<Integer> featuresList) {
        this.context= context;
        this.featuresList=featuresList;
    }

    @Override
    public int getCount() {
        return featuresList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflator = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      ViewHolder holder;
        String featureName = null;
        int featureItem,featureImage= R.drawable.image_placeholder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflator.inflate(R.layout.feature_griditem, null);
            holder.featureName = (TextView) convertView.findViewById(R.id.featureName);
            holder.Card = (LinearLayout) convertView.findViewById(R.id.card);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        featureItem = featuresList.get(i);
        if(featureItem==1){
            featureName = "Flights";
            holder.Card.setBackgroundResource(R.drawable.two_side_round_blue);
        }else if(featureItem==2) {
            featureName = "Buses";
            holder.Card.setBackgroundResource(R.drawable.two_side_round_yellow);
        }else if(featureItem==3) {
            featureName = "Hotels";
            holder.Card.setBackgroundResource(R.drawable.two_side_round_green);
        }else if(featureItem==4) {
            featureName = "Holidays";
            holder.Card.setBackgroundResource(R.drawable.two_side_round_red);
        }else if(featureItem==5) {
            featureName = "Recharges";
            holder.Card.setBackgroundResource(R.drawable.two_side_round_aqua);
        }
        if(featureName!=null){
            holder.featureName.setText(featureName);
        }else {
            holder.featureName.setText("");
        }

       /* Glide.with(context)
                .load(featureImage)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .into(holder.featureImg);*/
        return convertView;
    }

    public static class ViewHolder {
        public TextView featureName;
        LinearLayout Card;
    }
}
