package com.axpresslogistics.it2.axpresslogisticapp.adaptor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.model.ListOfBrokersModel;

import java.util.List;

public class ListOfBrokerAdaptor extends RecyclerView.Adapter<ListOfBrokerAdaptor.ListHolder> {
    Context context;
    List<ListOfBrokersModel> brokersModelList;

    public ListOfBrokerAdaptor(Context context, List<ListOfBrokersModel> brokersModelList) {
        this.context = context;
        this.brokersModelList = brokersModelList;
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_of_broker_itemview,parent,false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {
        final ListOfBrokersModel listOfBrokersModel = brokersModelList.get(position);
        holder.broker_name.setText(listOfBrokersModel.getBroker_name());
        holder.broker_code.setText(listOfBrokersModel.getBroker_code());
        holder.contact.setText(listOfBrokersModel.getContact());
        holder.address.setText(listOfBrokersModel.getAddress());
        holder.account_no.setText(listOfBrokersModel.getAccount_no());
        holder.bank_name.setText(listOfBrokersModel.getBank_name());
        holder.pan_no.setText(listOfBrokersModel.getPan_no());
        holder.name_of_pancard.setText(listOfBrokersModel.getName_of_pancard());
        holder.ifsc_code.setText(listOfBrokersModel.getIfsc_code());
    }

    @Override
    public int getItemCount() {
        return brokersModelList.size();
    }

    public class ListHolder extends RecyclerView.ViewHolder {
        TextView broker_code,broker_name,contact,address,account_no,bank_name,pan_no,name_of_pancard,
                ifsc_code;
        public ListHolder(View itemView) {
            super(itemView);
            broker_code = itemView.findViewById(R.id.broker_code);
            broker_name = itemView.findViewById(R.id.broker_name);
            contact = itemView.findViewById(R.id.contact);
            address = itemView.findViewById(R.id.address);
            account_no = itemView.findViewById(R.id.account_no);
            bank_name = itemView.findViewById(R.id.bank_name);
            pan_no = itemView.findViewById(R.id.pan_no);
            name_of_pancard = itemView.findViewById(R.id.name_of_pancard);
            ifsc_code = itemView.findViewById(R.id.ifsc_code);
        }
    }
}
