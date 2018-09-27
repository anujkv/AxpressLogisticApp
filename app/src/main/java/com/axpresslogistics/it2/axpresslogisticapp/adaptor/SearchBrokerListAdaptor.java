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
import com.axpresslogistics.it2.axpresslogisticapp.model.SearchBrokerListModel;

import java.util.List;

public class SearchBrokerListAdaptor extends RecyclerView.Adapter<SearchBrokerListAdaptor.ListHolder> {
    Context context;
    List<SearchBrokerListModel> brokerListModelList;

    public SearchBrokerListAdaptor(Context context, List<SearchBrokerListModel> brokerListModelList) {
        this.context = context;
        this.brokerListModelList = brokerListModelList;
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.broker_view_item, parent, false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {
        final SearchBrokerListModel brokerListModel = brokerListModelList.get(position);
        holder.broker_name.setText(brokerListModel.getBroker_name());
        holder.broker_code.setText(brokerListModel.getBroker_code());
        holder.broker_branch.setText(brokerListModel.getBroker_branch());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, AddBrokerActivity.class);
                intent.putExtra("broker_code", brokerListModel.getBroker_code());
                intent.putExtra("method", "update_broker");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return brokerListModelList.size();
    }

    public class ListHolder extends RecyclerView.ViewHolder {
        TextView broker_name, broker_code, broker_branch;
        CardView cardView;

        public ListHolder(View itemView) {
            super(itemView);
            broker_name = itemView.findViewById(R.id.txt_broker_name);
            broker_code = itemView.findViewById(R.id.txtBrokerUniqueID);
            broker_branch = itemView.findViewById(R.id.txt_broker_branch);
            cardView = itemView.findViewById(R.id.brokerItemsCardView);
        }
    }
}
