package com.axpresslogistics.it2.axpresslogisticapp.adaptor.operationAdaptor.VehicleApprovalAdaptor;

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
import com.axpresslogistics.it2.axpresslogisticapp.activities.Operations.VehicleApproval.VehicalApproval;
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.ApprovalVehicle.VehicleList.SavedvehiclList;

import java.util.List;

public class VehicleListAdaptor extends RecyclerView.Adapter<VehicleListAdaptor.ViewHolder> {
    Context context;
    private List<SavedvehiclList> savedvehiclListList;

    public VehicleListAdaptor(Context context, List<SavedvehiclList> savedvehiclListList) {
        this.context = context;
        this.savedvehiclListList = savedvehiclListList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.vehicle_req_list,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        final SavedvehiclList vehicleListModel = savedvehiclListList.get(i);
        holder.vehicle_req_code.setText(vehicleListModel.getRequestCode());
        holder.request_date.setText(vehicleListModel.getRequestDate());
        holder.from_branch.setText(vehicleListModel.getFromBranch());
        holder.to_branch.setText(vehicleListModel.getToBranch());
        holder.broker_name.setText(vehicleListModel.getBrokerName());
        holder.minimum_rate.setText(vehicleListModel.getMinimumRate());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, VehicalApproval.class);
                intent.putExtra("vehicle_request_code", vehicleListModel.getRequestCode());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return savedvehiclListList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView vehicle_req_code,request_date,from_branch,to_branch,broker_name,minimum_rate;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            vehicle_req_code = itemView.findViewById(R.id.txt_vehicle_req);
            request_date = itemView.findViewById(R.id.txt_req_date);
            from_branch = itemView.findViewById(R.id.txt_from_branchId);
            to_branch = itemView.findViewById(R.id.txt_to_branch);
            broker_name = itemView.findViewById(R.id.txt_broker_name);
            minimum_rate = itemView.findViewById(R.id.txt_minimum_rate);
            cardView = itemView.findViewById(R.id.vehicleReqItemsCardView);
        }
    }
}
