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
import com.axpresslogistics.it2.axpresslogisticapp.acitvities.AddVehicleReq;
import com.axpresslogistics.it2.axpresslogisticapp.acitvities.VehicalApproval;
import com.axpresslogistics.it2.axpresslogisticapp.model.VehicleListModel;

import java.util.List;

public class VehicleListAdaptor extends RecyclerView.Adapter<VehicleListAdaptor.ListHolder> {
    Context context;
    List<VehicleListModel> listModelList;

    public VehicleListAdaptor(Context context, List<VehicleListModel> listModelList) {
        this.context = context;
        this.listModelList = listModelList;
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.vehicle_req_list,parent,false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {
        final VehicleListModel vehicleListModel = listModelList.get(position);
        holder.vehicle_req_code.setText(vehicleListModel.getRequest_code());
        holder.request_date.setText(vehicleListModel.getRequest_date());
        holder.from_branch.setText(vehicleListModel.getFrom_branch());
        holder.to_branch.setText(vehicleListModel.getTo_branch());
        holder.broker_name.setText(vehicleListModel.getBroker_name());
        holder.minimum_rate.setText(vehicleListModel.getMinimum_rate());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, VehicalApproval.class);
                intent.putExtra("vehicle_request_code", vehicleListModel.getRequest_code());
                intent.putExtra("method","fetch_approval_details");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listModelList.size();
    }

    public class ListHolder extends RecyclerView.ViewHolder {
        TextView vehicle_req_code,request_date,from_branch,to_branch,broker_name,minimum_rate;
        CardView cardView;
        public ListHolder(View itemView) {
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
