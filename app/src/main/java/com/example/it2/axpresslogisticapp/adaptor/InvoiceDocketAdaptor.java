package com.example.it2.axpresslogisticapp.adaptor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.it2.axpresslogisticapp.R;
import com.example.it2.axpresslogisticapp.model.AppliedLeaveModel;
import com.example.it2.axpresslogisticapp.model.InvoiceDocketModel;

import java.util.List;

public class InvoiceDocketAdaptor extends RecyclerView.Adapter<InvoiceDocketAdaptor.DocketHolder> {

    Context context;
    List<InvoiceDocketModel> docketModelList;

    @NonNull
    @Override
    public DocketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_invoice_docket_list,
                parent,false);
        return new DocketHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocketHolder holder, int position) {
        final InvoiceDocketModel docketModel = docketModelList.get(position);
        holder.txt_docketID.setText(docketModel.getDocket_noID());
        holder.txt_consigneeID.setText(docketModel.getTxt_consigneeID());
        holder.txt_docketDateID.setText(docketModel.getTxt_docketDateID());
        holder.txt_fromID.setText(docketModel.getTxt_fromID());
        holder.txt_toID.setText(docketModel.getTxt_toID());
        holder.txt_statusID.setText(docketModel.getTxt_statusID());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return docketModelList.size();
    }

    public class DocketHolder extends RecyclerView.ViewHolder {
        TextView txt_docketID,txt_docketDateID,txt_consigneeID, txt_fromID,txt_toID, txt_statusID;
        CardView cardView;
        public DocketHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.docket_listview_cardview);
            txt_docketID = itemView.findViewById(R.id.txt_docketID);
            txt_docketDateID = itemView.findViewById(R.id.txt_docketDateID);
            txt_consigneeID = itemView.findViewById(R.id.txt_consigneeID);
            txt_fromID = itemView.findViewById(R.id.txt_fromID);
            txt_toID = itemView.findViewById(R.id.txt_toID);
            txt_statusID = itemView.findViewById(R.id.txt_statusID);
        }
    }
}
