package com.axpresslogistics.it2.axpresslogisticapp.adaptor;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.Utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.model.BrokerApprovalModel;

import java.util.List;

public class BrokerApprovalAdaptor extends RecyclerView.Adapter<BrokerApprovalAdaptor.AdaptorHolder> {
    Context context;
    List<BrokerApprovalModel> approvalModels;
    Boolean FLAG = false;
    int item_position,selection_code;


    public BrokerApprovalAdaptor(Context context, List<BrokerApprovalModel> approvalModels) {
        this.context = context;
        this.approvalModels = approvalModels;
    }

    @NonNull
    @Override
    public AdaptorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.broker_approval_listview, parent,
                false);
        return new AdaptorHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdaptorHolder holder, final int position) {
        final BrokerApprovalModel model = approvalModels.get(position);
        holder.logged_in_status.setText(model.getLoggedin_member());
//        holder.broker_code.setText(model.getBroker_code());
//        holder.broker_name.setText(model.getBroker_name());
//        holder.broker_rate.setText(model.getBroker_rate());
//        holder.broker_advance.setText(model.getBroker_advance());
//        holder.broker_remark.setText(model.getBroker_remark());
//        holder.spinner_broker_selection.setText(model.getBroker_name());

        holder.broker_code.setText(approvalModels.get(position).getBroker_code());
        holder.broker_name.setText(approvalModels.get(position).getBroker_name());
        holder.broker_rate.setText(approvalModels.get(position).getBroker_rate());
        holder.broker_advance.setText(approvalModels.get(position).getBroker_advance());
        holder.broker_remark.setText(approvalModels.get(position).getBroker_remark());
        holder.spinner_broker_selection.setText(approvalModels.get(position).getBroker_name());

        holder.show_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.getLoggedin_member().equals("L")){
                    Log.e("LoggedL:",model.getLoggedin_member());
                    if(FLAG.equals(false)){
                        holder.btn_broker_selectionReject.setVisibility(View.VISIBLE);
                       visibleFalseState(holder);

                    }else if(FLAG.equals(true)){
                        holder.btn_broker_selectionReject.setVisibility(View.GONE);
                        visibleTrueState(holder);
                    }
                } else {
                    Log.e("LoggedB:",model.getLoggedin_member());
                    if(FLAG.equals(false)){
                        visibleFalseState(holder);

                    }else if(FLAG.equals(true)){
                        visibleTrueState(holder);
                    }
                }
            }
        });

        holder.approved_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item_position = holder.getAdapterPosition();
                if(model.getLoggedin_member().equals("L")){
                    approvedBtnClickedL(holder,model);
                }else{
                    approvedBtnClickedB(holder,model);
                }
                Toast.makeText(context.getApplicationContext(),
                        model.getBroker_code() + "/ " + item_position,
                        Toast.LENGTH_SHORT).show();
            }
        });
        holder.btn_broker_selectionReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectBtnClickerL(holder,model,position);
            }
        });
    }

    private void rejectBtnClickerL(AdaptorHolder holder, BrokerApprovalModel model, int position) {
        holder.approved_btn.setTextColor(Color.WHITE);
        holder.approved_btn.setText(CONSTANT.APPROVE);
        holder.btn_broker_selectionReject.setTextColor(Color.GREEN);
        holder.btn_broker_selectionReject.setText(CONSTANT.REJECT);
        holder.broker_code.setText(model.getBroker_code());
        selection_code = -1;
    }

    private void approvedBtnClickedB(AdaptorHolder holder, BrokerApprovalModel model) {
        holder.approved_btn.setTextColor(Color.GREEN);
        holder.approved_btn.setText(CONSTANT.APPROVED);
        holder.broker_code.setText(model.getBroker_code());
        for (int i = 0; i < approvalModels.size(); i++) {
            Log.e("I:", String.valueOf(i));
            Log.e("approvalModels.get(i):", String.valueOf(approvalModels.get(i)));
            Log.e("model.getBroker_code():", model.getBroker_code());
            Log.e("getAdapterPosition()):", String.valueOf(holder.getAdapterPosition()));

            if (i == holder.getAdapterPosition()) continue;
//                Button b =approvalModels.get(i);
//                Font f = approvalModels.get(i);
            if (i != holder.getAdapterPosition()) {
                notifyItemChanged(i); // Tell the adapter this item is updated
            }
        }approvalModels.get(holder.getAdapterPosition());
        notifyItemChanged(holder.getAdapterPosition());
    }

    private void approvedBtnClickedL(AdaptorHolder holder, BrokerApprovalModel model) {
        holder.approved_btn.setTextColor(Color.GREEN);
        holder.approved_btn.setText(CONSTANT.APPROVED);
        holder.btn_broker_selectionReject.setTextColor(Color.RED);
        holder.btn_broker_selectionReject.setText(CONSTANT.REJECT);
        holder.broker_code.setText(model.getBroker_code());
        selection_code = 0;
    }

    private void visibleTrueState(AdaptorHolder holder) {
        holder.broker1_rate_details.setVisibility(View.GONE);
        holder.approved_btn.setVisibility(View.GONE);
        holder.spinner_broker_selection.setVisibility(View.VISIBLE);
        FLAG = false;
        holder.show_btn.setText(CONSTANT.SHOW);
    }

    private void visibleFalseState(AdaptorHolder holder) {
        holder.broker1_rate_details.setVisibility(View.VISIBLE);
        holder.approved_btn.setVisibility(View.VISIBLE);
        holder.spinner_broker_selection.setVisibility(View.GONE);
        FLAG = true;
        holder.show_btn.setText(CONSTANT.HIDE);
    }

    @Override
    public int getItemCount() {
        return approvalModels.size();
    }

    public class AdaptorHolder extends RecyclerView.ViewHolder {
        TextView broker_code,show_btn,approval_status,logged_in_status;
        EditText broker_name, broker_rate, broker_advance, broker_remark;
        CardView broker1_rate_details;
        Button approved_btn,btn_broker_selectionReject;
        EditText spinner_broker_selection;


        public AdaptorHolder(View itemView) {
            super(itemView);
            logged_in_status = itemView.findViewById(R.id.logged_in_status);
            broker_name = itemView.findViewById(R.id.edtbrokerName);
            broker_code = itemView.findViewById(R.id.branch_codeId);
            broker_rate = itemView.findViewById(R.id.edt_broker_rate);
            broker_advance = itemView.findViewById(R.id.edt_advance);
            broker_remark = itemView.findViewById(R.id.edt_remark);
            show_btn = itemView.findViewById(R.id.show_more_click_link);
            approved_btn = itemView.findViewById(R.id.btn_broker_selection);
            broker1_rate_details = itemView.findViewById(R.id.broker1_rate_details);
            spinner_broker_selection = itemView.findViewById(R.id.spinner_broker_selection);
            btn_broker_selectionReject = itemView.findViewById(R.id.btn_broker_selectionReject);
        }
    }
}
