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
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.BrokerModel.BrokerList.Broker;

import java.util.List;

public class BrokerAdaptor  extends RecyclerView.Adapter<BrokerAdaptor.BrokerHolder>{
    Context context;
    List<Broker> brokerList;

    public BrokerAdaptor(Context context, List<Broker> brokerList) {
        this.context = context;
        this.brokerList = brokerList;
    }

    @NonNull
    @Override
    public BrokerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.broker_view_item, viewGroup,false);
        return new BrokerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrokerHolder holder, int i) {
        final Broker brokerModel = brokerList.get(i);
        holder.broker_name.setText(brokerModel.getBrokerName());
        holder.broker_code.setText(brokerModel.getBrokerCode());
        holder.broker_branch.setText(brokerModel.getBranch());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, AddBroker.class);
                intent.putExtra("broker_code", brokerModel.getBrokerCode());
                intent.putExtra("method","update_broker");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return brokerList.size();
    }

    public class BrokerHolder extends RecyclerView.ViewHolder {
        TextView broker_name, broker_code, broker_branch;
        CardView cardView;
        public BrokerHolder(View itemView) {
            super(itemView);
            broker_name = itemView.findViewById(R.id.txt_broker_name);
            broker_code = itemView.findViewById(R.id.txtBrokerUniqueID);
            broker_branch = itemView.findViewById(R.id.txt_broker_branch);
            cardView = itemView.findViewById(R.id.brokerItemsCardView);
        }
    }
}
