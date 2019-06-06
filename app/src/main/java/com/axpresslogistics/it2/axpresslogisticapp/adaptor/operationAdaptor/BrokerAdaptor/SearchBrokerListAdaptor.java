package com.axpresslogistics.it2.axpresslogisticapp.adaptor.operationAdaptor.BrokerAdaptor;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.activities.Operations.AddBroker.AddBroker;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.BrokerModel.BrokerList.Brokersearch;

import java.util.List;

public class SearchBrokerListAdaptor extends RecyclerView.Adapter<SearchBrokerListAdaptor.ViewHolder> {
    Context context;
    List<Brokersearch> searchResponses;

    public SearchBrokerListAdaptor(Context context, List<Brokersearch> searchResponses) {
        this.context = context;
        this.searchResponses = searchResponses;
    }

    @NonNull
    @Override
    public SearchBrokerListAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.broker_view_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchBrokerListAdaptor.ViewHolder holder, int i) {
        final Brokersearch brokerListModel = searchResponses.get(i);
        holder.broker_name.setText(brokerListModel.getBrokerName());
        holder.broker_code.setText(brokerListModel.getBrokerCode());
        holder.broker_branch.setText(brokerListModel.getBranch());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, AddBroker.class);
                intent.putExtra("broker_code", brokerListModel.getBrokerCode());
                intent.putExtra("method", "update_broker");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return searchResponses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView broker_name, broker_code, broker_branch;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            broker_name = itemView.findViewById(R.id.txt_broker_name);
            broker_code = itemView.findViewById(R.id.txtBrokerUniqueID);
            broker_branch = itemView.findViewById(R.id.txt_broker_branch);
            cardView = itemView.findViewById(R.id.brokerItemsCardView);
        }
    }
}
