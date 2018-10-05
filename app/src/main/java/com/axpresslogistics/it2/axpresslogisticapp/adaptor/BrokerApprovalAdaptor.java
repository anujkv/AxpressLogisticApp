package com.axpresslogistics.it2.axpresslogisticapp.adaptor;

import android.annotation.SuppressLint;
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

import java.util.ArrayList;
import java.util.List;

public class BrokerApprovalAdaptor extends RecyclerView.Adapter<BrokerApprovalAdaptor.AdaptorHolder> {
    Context context;
    List<BrokerApprovalModel> approvalModels;
    Boolean FLAG = false;
    int item_position, selection_code;
    String broker_code, broker_name, broker_rate, approved_status;
    int selected_position = -1;


    public BrokerApprovalAdaptor(Context context, List<BrokerApprovalModel> approvalModels) {
        this.context = context;
        this.approvalModels = approvalModels;
    }

    public BrokerApprovalAdaptor() {
    }

    @NonNull
    @Override
    public AdaptorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.broker_approval_listview, parent,
                false);

        return new AdaptorHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final AdaptorHolder holder, final int position) {
        int i = 1 + position;
        final BrokerApprovalModel model = approvalModels.get(position);
        holder.logged_in_status.setText(model.getLoggedin_member());

        holder.broker_code.setText(approvalModels.get(position).getBroker_code());
        holder.broker_name.setText(approvalModels.get(position).getBroker_name());
        holder.broker_rate.setText(approvalModels.get(position).getBroker_rate());
        holder.broker_advance.setText(approvalModels.get(position).getBroker_advance());
        holder.broker_remark.setText(approvalModels.get(position).getBroker_remark());
        holder.spinner_broker_selection.setText(approvalModels.get(position).getBroker_name());

        holder.broker_selection_no.setText("Broker " + i);


        if (model.getLoggedin_member().equals("L")) {
            holder.btn_broker_selectionReject.setVisibility(View.VISIBLE);
        }

        holder.approved_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item_position = holder.getAdapterPosition();
                broker_code = model.getBroker_code();
                broker_name = model.getBroker_name();
                broker_rate = model.getBroker_rate();

                if (model.getLoggedin_member().equals("L")) {
                    approved_status = "Approval";
                    approvedBtnClickedL(holder, model);
                } else {
                    approved_status = "BApproval";
                    selected_position = position;
//                    approvedBtnClickedB(holder,model,position);
                    holder.approved_btn.setTextColor(Color.GREEN);
                    holder.approved_btn.setText(CONSTANT.APPROVED);
                    holder.broker_code.setText(model.getBroker_code());
                    Log.e("position", String.valueOf(position));
                    notifyDataSetChanged();
                }

                Toast.makeText(context.getApplicationContext(),
                        model.getBroker_code() + "/ " + item_position,
                        Toast.LENGTH_SHORT).show();

                clicked_details();
            }
        });

        holder.btn_broker_selectionReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectBtnClickerL(holder, model, position);
                broker_code = model.getBroker_code();
                broker_name = model.getBroker_name();
                broker_rate = model.getBroker_rate();
                approved_status = "Reject";
                clicked_details();
            }
        });
    }

    public List<String> clicked_details() {
        List<String> list = new ArrayList<String>();
        list.add(broker_code);
        list.add(broker_name);
        list.add(broker_rate);
        list.add(approved_status);
        Log.e("LIST", String.valueOf(list));
        return list;
    }


    private void rejectBtnClickerL(AdaptorHolder holder, BrokerApprovalModel model, int position) {
        holder.approved_btn.setTextColor(Color.WHITE);
        holder.approved_btn.setText(CONSTANT.APPROVE);
        holder.btn_broker_selectionReject.setTextColor(Color.GREEN);
        holder.btn_broker_selectionReject.setText(CONSTANT.REJECT);
        holder.broker_code.setText(model.getBroker_code());
        selection_code = -1;
    }

    private void approvedBtnClickedB(AdaptorHolder holder, BrokerApprovalModel model, int position) {


    }

    private void approvedBtnClickedL(AdaptorHolder holder, BrokerApprovalModel model) {
        holder.approved_btn.setTextColor(Color.GREEN);
        holder.approved_btn.setText(CONSTANT.APPROVED);
        holder.btn_broker_selectionReject.setTextColor(Color.RED);
        holder.btn_broker_selectionReject.setText(CONSTANT.REJECT);
        holder.broker_code.setText(model.getBroker_code());
        selection_code = 0;
    }

    @Override
    public int getItemCount() {
        return approvalModels.size();
    }

    public class AdaptorHolder extends RecyclerView.ViewHolder {
        TextView broker_code, show_btn, approval_status, logged_in_status, broker_selection_no;
        EditText broker_name, broker_rate, broker_advance, broker_remark;
        CardView broker1_rate_details;
        Button approved_btn, btn_broker_selectionReject;
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
            broker_selection_no = itemView.findViewById(R.id.broker_selection_no);
        }
    }
}
