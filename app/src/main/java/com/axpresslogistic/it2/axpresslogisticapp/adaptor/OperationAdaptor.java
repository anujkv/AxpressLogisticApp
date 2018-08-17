package com.axpresslogistic.it2.axpresslogisticapp.adaptor;

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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.axpresslogistic.it2.axpresslogisticapp.R;
import com.axpresslogistic.it2.axpresslogisticapp.acitvities.DocketEnquiry;
import com.axpresslogistic.it2.axpresslogisticapp.model.OperationModel;

import java.util.List;

/**
 * Created by IT2 on 5/25/2018.
 */

public class OperationAdaptor extends RecyclerView.Adapter<OperationAdaptor.ViewHolder> {

    RequestOptions requestOptions;
    private Context context;
    private List<OperationModel> operationModels;

    public OperationAdaptor(Context context, List<OperationModel> operationModels) {
        this.context = context;
        this.operationModels = operationModels;
        requestOptions = new RequestOptions().centerCrop().placeholder(R.mipmap.icon_operation);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.operation_item_list,viewGroup,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Glide.with(context).load(operationModels.get(i).getOpt_icon()).apply(requestOptions).into(viewHolder.user_icon);
        viewHolder.tv_name.setText(operationModels.get(i).getOpt_name());
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DocketEnquiry.class);
                intent.putExtra("name",operationModels.get(i).getOpt_name().toString());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return operationModels.size();
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
