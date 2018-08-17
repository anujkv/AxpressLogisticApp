package com.axpresslogistic.it2.axpresslogisticapp.adaptor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.axpresslogistic.it2.axpresslogisticapp.R;
import com.axpresslogistic.it2.axpresslogisticapp.model.DocketTracking;

import java.util.List;

public class DocketTrackingAdaptor extends RecyclerView.Adapter<DocketTrackingAdaptor.ViewHolder> {
    private Context context;
    private List<DocketTracking> list;

    private static int currentPosition = 0;

    public DocketTrackingAdaptor(Context context, List<DocketTracking> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.challan_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        DocketTracking docketTracking = list.get(position);

        holder.txtChallan_no.setText(docketTracking.getChallan_no());
        holder.txtChallan_date.setText(docketTracking.getChallan_date());
        holder.txtChallan_from.setText(docketTracking.getChallan_from());
        holder.txtChallan_to.setText(docketTracking.getChallan_to());
        holder.txtVehicle_no.setText(docketTracking.getVehicle_no());
        holder.txtChallan_status.setText(docketTracking.getChallan_status());

//        holder.linearLayout.setVisibility(View.GONE);
//
//        if (currentPosition == position) {
//            //creating an animation
//            Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.challan_anim);
//
//            //toggling visibility
//            holder.linearLayout.setVisibility(View.VISIBLE);
//
//            //adding sliding effect
//            holder.linearLayout.startAnimation(slideDown);
//        }
//
//        holder.txtChallan_details.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //getting the position of the item to expand it
//                currentPosition = position;
//
//                //reloding the list
//                notifyDataSetChanged();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtChallan_details,txtChallan_no,txtChallan_date,txtChallan_from, txtChallan_to, txtChallan_status, txtVehicle_no;
//        LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            txtChallan_details = itemView.findViewById(R.id.txt_challan_details_Id);
            txtChallan_no = itemView.findViewById(R.id.txt_challen_no_ID);
            txtChallan_date = itemView.findViewById(R.id.txt_challen_dateID);
            txtChallan_from = itemView.findViewById(R.id.txt_challan_date_fromID);
            txtChallan_to = itemView.findViewById(R.id.txt_challan_date_toID);
            txtVehicle_no = itemView.findViewById(R.id.txt_vehical_noID);
            txtChallan_status = itemView.findViewById(R.id.txt_vehical_statusID);

//            linearLayout = itemView.findViewById(R.id.challan_details_itemList);
        }
    }
}
