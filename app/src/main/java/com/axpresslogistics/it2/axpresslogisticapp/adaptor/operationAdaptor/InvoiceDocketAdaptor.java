package com.axpresslogistics.it2.axpresslogisticapp.adaptor.operationAdaptor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.activities.Operations.DocketEnquiry.DocketTracking;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.InvoiceResponse.InvoiceDetail;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.InvoiceResponse.InvoiceResponse;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.Preferences;

import java.util.List;

public class InvoiceDocketAdaptor  extends RecyclerView.Adapter<InvoiceDocketAdaptor.InvoiceHolder>{
    Context context;
    List<InvoiceResponse> docketModelList;
    List<InvoiceDetail> invoiceDetails;

    public InvoiceDocketAdaptor(Context context, List<InvoiceDetail> invoiceDetails) {
        this.context = context;
        this.invoiceDetails = invoiceDetails;
    }

    @NonNull
    @Override
    public InvoiceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.docket_listview_item,
                viewGroup, false);
        return new InvoiceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceHolder holder, int i) {
        final InvoiceDetail docketModel = invoiceDetails.get(i);
        holder.txt_docketID.setText(docketModel.getDocketNo());
        holder.txt_consigneeID.setText(docketModel.getConsigneeName());
        holder.txt_docketDateID.setText(docketModel.getBookingDate());
        holder.txt_fromID.setText(docketModel.getFromDestination());
        holder.txt_toID.setText(docketModel.getToDestination());
        holder.txt_statusID.setText(docketModel.getStatus());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String docket = docketModel.getDocketNo();
                dataJsonFunction(docket);
            }
        });
    }

    private void dataJsonFunction(String docket) {
        Intent intent = new Intent(context, DocketTracking.class);
        Bundle b = new Bundle();
        b.putString("docketEnquiryResponse",docket);
        b.putString("db_name", Preferences.getPreference(context, CONSTANT.DB_NAME));
        intent.putExtras(b);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return invoiceDetails.size();
    }

    public class InvoiceHolder extends RecyclerView.ViewHolder {
        TextView txt_docketID, txt_docketDateID, txt_consigneeID, txt_fromID, txt_toID, txt_statusID;
        CardView cardView;
        public InvoiceHolder(@NonNull View itemView) {
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
