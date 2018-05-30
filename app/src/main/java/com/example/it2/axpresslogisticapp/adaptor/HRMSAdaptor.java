package com.example.it2.axpresslogisticapp.adaptor;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.it2.axpresslogisticapp.R;
import com.example.it2.axpresslogisticapp.acitvities.DocketEnquiry;
import com.example.it2.axpresslogisticapp.acitvities.MarkAttendanceActivity;
import com.example.it2.axpresslogisticapp.model.HRMSModel;
import com.example.it2.axpresslogisticapp.model.OperationModel;

import java.util.List;

public class HRMSAdaptor extends RecyclerView.Adapter<HRMSAdaptor.ViewHolder> {

    RequestOptions requestOptions;
    private Context context;
    private List<HRMSModel> hrmsModels;

    public HRMSAdaptor(Context context, List<HRMSModel> operationModels) {
        this.context = context;
        this.hrmsModels = operationModels;
        requestOptions = new RequestOptions().centerCrop().placeholder(R.mipmap.icon_operation);
    }

    @NonNull
    @Override
    public HRMSAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.operation_item_list,parent,false);
        final HRMSAdaptor.ViewHolder viewHolder = new HRMSAdaptor.ViewHolder(view);
        return new HRMSAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Glide.with(context).load(hrmsModels.get(position).getOpt_icon()).apply(requestOptions).into(holder.user_icon);
        holder.tv_name.setText(hrmsModels.get(position).getOpt_name());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MarkAttendanceActivity.class);
                intent.putExtra("name",hrmsModels.get(position).getOpt_name().toString());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return hrmsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name;
        ImageView user_icon;
        LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.opt_row_name_id);
            user_icon = itemView.findViewById(R.id.iconId);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
