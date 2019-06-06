package com.axpresslogistics.it2.axpresslogisticapp.adaptor.operationAdaptor.vendorApproval;

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
import com.axpresslogistics.it2.axpresslogisticapp.activities.Operations.VendorApprovalActivity;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.VendorApproval.VendorList.VendorList;

import java.util.List;

public class VendorListAdaptor extends RecyclerView.Adapter<VendorListAdaptor.ViewHolder> {
    Context context;
    List<VendorList> viewListResponses;

    public VendorListAdaptor(Context context, List<VendorList> viewListResponses) {
        this.context = context;
        this.viewListResponses = viewListResponses;
    }

    @NonNull
    @Override
    public VendorListAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.vendor_view_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VendorListAdaptor.ViewHolder holder, int position) {
        final VendorList vendorViewListResponse = viewListResponses.get(position);
        holder.request_no.setText(vendorViewListResponse.getRequestNo());
        holder.vendor_code.setText(vendorViewListResponse.getVendorCode());
        holder.vendor_name.setText(vendorViewListResponse.getVendorName());
        holder.vehicle_no.setText(vendorViewListResponse.getVehicleCode());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, VendorApprovalActivity.class);
                intent.putExtra("request_code", vendorViewListResponse.getRequestNo());
                intent.putExtra("method","vendor_vehicle_detail");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return viewListResponses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView request_no,vendor_code,vendor_name,vehicle_no;
        CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            request_no = itemView.findViewById(R.id.txt_request_no);
            vendor_code = itemView.findViewById(R.id.txt_vendor_code);
            vendor_name = itemView.findViewById(R.id.txt_vendor_name);
            vehicle_no = itemView.findViewById(R.id.txt_vehicle_no);
            cardView = itemView.findViewById(R.id.vendorItemsCardView);
        }
    }
}
