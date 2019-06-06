package com.axpresslogistics.it2.axpresslogisticapp.adaptor.operationAdaptor.vendorApproval;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.VendorApproval.VendorFetch.VendorVehicleAttachRequestRateDetailse;

import java.util.List;

public class VendorAmountDetailsAdaptor extends RecyclerView.Adapter<VendorAmountDetailsAdaptor.ViewHolder> {
    Context context;
    List<VendorVehicleAttachRequestRateDetailse> rateDetailseList;
    List<VendorVehicleAttachRequestRateDetailse> updatedData;
    VendorVehicleAttachRequestRateDetailse rateDetailse;

    String afterTextChanged;
//    String[] mDataset;

    public VendorAmountDetailsAdaptor(Context context, List<VendorVehicleAttachRequestRateDetailse> rateDetailseList) {
        this.context = context;
        this.rateDetailseList = rateDetailseList;
    }

    @NonNull
    @Override
    public VendorAmountDetailsAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.vendor_amount_itemview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VendorAmountDetailsAdaptor.ViewHolder holder, final int position) {

//        updatedData.add(rateDetailse);
//        VendorVehicleAttachRequestRateDetailse feed = rateDetailseList.get(holder.getAdapterPosition());
        VendorVehicleAttachRequestRateDetailse requestRateDetailse = rateDetailseList.get(position);
        holder.edt_from_branch.setText(requestRateDetailse.getFromBranch());
        holder.edt_to_branch.setText(requestRateDetailse.getToBranch());
        holder.edt_valid_from.setText(requestRateDetailse.getValidFrom());
        holder.edt_valid_to.setText(requestRateDetailse.getValidUpto());
        holder.edt_amount.setText(requestRateDetailse.getRate());

//        holder.editTextListener.updatePosition(position);
//        holder.edt_amount.setText(mDataset[position]);
    }

    @Override
    public int getItemCount() {
        return rateDetailseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText edt_from_branch, edt_to_branch, edt_valid_from, edt_valid_to, edt_amount;

        public ViewHolder(View itemView) {
            super(itemView);
            edt_from_branch = itemView.findViewById(R.id.edt_from_branch);
            edt_to_branch = itemView.findViewById(R.id.edt_to_branch);
            edt_valid_from = itemView.findViewById(R.id.edt_valid_from);
            edt_valid_to = itemView.findViewById(R.id.edt_valid_to);
            edt_amount = itemView.findViewById(R.id.edt_amount);

        }

//        public String getData()
//        {
//            String s;
//            s=edt_amount.getText().toString();
//            return s;
//        }
    }
public void refreshData(List<VendorVehicleAttachRequestRateDetailse> updatedData){
        notifyDataSetChanged();

}


}