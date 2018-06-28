package com.NaTicket.n.recharges;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.NaTicket.n.R;
import com.NaTicket.n.recharges.pojo.Operators_DTO;


/**
 * Created by Nagarjuna on 1/10/2018.
 */

public class Operators_adapter_REC extends RecyclerView.Adapter<Operators_adapter_REC.ViewHolder> {


    Operator_list_activity_rec activity;
    private Operators_DTO[] operators;
    private Operators_DTO[] mFilteredList;

    public Operators_adapter_REC(Operators_DTO[] operators_dtos, Operator_list_activity_rec SearchActivity) {
        operators=operators_dtos;
        activity=SearchActivity;
    }

    @Override
    public Operators_adapter_REC.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.operators_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Operators_adapter_REC.ViewHolder viewHolder, final int position) {

        final Operators_DTO operators_dtos=operators[position];

        viewHolder.operator_name.setText(operators_dtos.getOperatorName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getOperatorData(operators_dtos);
            }
        });
    }



    @Override
    public int getItemCount() {
        return operators.length;
    }

/*    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().toLowerCase();
                if (charString.isEmpty()) {
                    mFilteredList = operators;
                } else {

                     Operators_DTO[] filteredList=new Operators_DTO[];
                   // ArrayList<Operators_DTO> filteredList = new ArrayList<>();
                    for (Operators_DTO androidVersion : operators) {
                        if (androidVersion.getOperatorName().toLowerCase().contains(charString)) {
                            filteredList.;
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
                mFilteredList = (Operators_DTO[]) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }*/


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView operator_name;
        public ViewHolder(View view) {
            super(view);

            operator_name = (TextView)view.findViewById(R.id.operator_name);

        }
    }
}
