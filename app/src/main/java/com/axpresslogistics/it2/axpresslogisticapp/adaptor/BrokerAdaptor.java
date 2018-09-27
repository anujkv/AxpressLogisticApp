package com.axpresslogistics.it2.axpresslogisticapp.adaptor;

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
import com.axpresslogistics.it2.axpresslogisticapp.acitvities.AddBrokerActivity;
import com.axpresslogistics.it2.axpresslogisticapp.model.BrokerModel;

import java.util.List;

public class BrokerAdaptor extends RecyclerView.Adapter<BrokerAdaptor.BrokerHolder> {
    Context context;
    List<BrokerModel> brokerModelList;

    public BrokerAdaptor(Context context, List<BrokerModel> brokerModelList) {
        this.context = context;
        this.brokerModelList = brokerModelList;
    }

    @NonNull
    @Override
    public BrokerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.broker_view_item,parent,false);
        return new BrokerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrokerHolder holder, int position) {
    final BrokerModel brokerModel = brokerModelList.get(position);
        holder.broker_name.setText(brokerModel.getBroker_name());
        holder.broker_code.setText(brokerModel.getBroker_code());
        holder.broker_branch.setText(brokerModel.getBroker_branch());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, AddBrokerActivity.class);
                intent.putExtra("broker_code", brokerModel.getBroker_code());
                intent.putExtra("method","update_broker");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return brokerModelList.size();
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
