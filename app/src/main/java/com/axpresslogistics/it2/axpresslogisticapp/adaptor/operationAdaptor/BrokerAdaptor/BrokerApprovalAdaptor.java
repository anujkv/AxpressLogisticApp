package com.axpresslogistics.it2.axpresslogisticapp.adaptor.operationAdaptor.BrokerAdaptor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
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
import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.ApprovalVehicle.FetchApprovalStatusModelListOfList;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;

import java.util.ArrayList;
import java.util.List;

public class BrokerApprovalAdaptor extends RecyclerView.Adapter<BrokerApprovalAdaptor.AdaptorHolder> {
    Context context;
    List<FetchApprovalStatusModelListOfList> approvalModels;
    int item_position, selection_code;
    String broker_code, broker_name, broker_rate, approved_status,broker_remark,broker_status ;
    int selected_position = -1;


    public BrokerApprovalAdaptor(Context context, List<FetchApprovalStatusModelListOfList> approvalModels, String broker_status) {
        this.context = context;
        this.approvalModels = approvalModels;
        this.broker_status = broker_status;
    }

    @NonNull
    @Override
    public AdaptorHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.broker_approval_listview, viewGroup,
                false);


        return new AdaptorHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdaptorHolder holder, final int position) {

        broker_status = "B";
        int i = 1 + position;
        final FetchApprovalStatusModelListOfList model = approvalModels.get(position);
        holder.logged_in_status.setText(model.getLoggedin_member());

        holder.broker_code.setText(approvalModels.get(position).getBrokerCode());
        holder.broker_name.setText(approvalModels.get(position).getBrokerName());
        holder.broker_rate.setText(approvalModels.get(position).getBrokerRate());
        holder.broker_advance.setText(approvalModels.get(position).getBrokerAdvance());
        holder.broker_remark.setText(approvalModels.get(position).getBrokerRemark());
        holder.spinner_broker_selection.setText(approvalModels.get(position).getBrokerName());

        holder.broker_selection_no.setText("Broker " + i);

        if (broker_status.equals("L")) {
            holder.btn_broker_selectionReject.setVisibility(View.VISIBLE);
        }

        holder.approved_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approvalModels.get(position).setSelected(true);

                selected_position = holder.getAdapterPosition();
                item_position = holder.getAdapterPosition();
                broker_code = model.getBrokerCode();
                broker_name = model.getBrokerName();
                broker_rate = model.getBrokerRate();
                broker_remark = model.getBrokerRemark();

                if (broker_status.equals("L")) {
                    approved_status = "Approval";
                    approvedBtnClickedL(holder, model);
                } else {
                    approved_status = "BApproval";
                }
                notifyDataSetChanged();
                clicked_details();
//                confirmDialog();
            }
        });

        holder.btn_broker_selectionReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectBtnClickerL(holder, model, position);
                broker_code = model.getBrokerCode();
                broker_name = model.getBrokerName();
                broker_rate = model.getBrokerRate();
                broker_remark = model.getBrokerRemark();
                approved_status = "Reject";
                clicked_details();
//                confirmDialog();
            }
        });
        if (approvalModels.get(position).isSelected()){
            holder.approved_btn.setTextColor(Color.BLACK);
            holder.approved_btn.setText(CONSTANT.APPROVED);
            holder.broker_code.setText(model.getBrokerCode());
            approvalModels.get(position).setSelected(false);
        }
        else
        {
            holder.approved_btn.setTextColor(Color.WHITE);
            holder.approved_btn.setText(CONSTANT.APPROVE);
            holder.broker_code.setText(model.getBrokerCode());
        }
    }

    private void confirmDialog() throws Resources.NotFoundException {
        new AlertDialog.Builder(context)
                .setTitle("Title")
                .setMessage("Do you really want to whatever?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(context, "Yaay", Toast.LENGTH_SHORT).show();
                    }}).show();
//                .setNegativeButton(android.R.string.no, null).show();
    }

    public List<String> clicked_details() {
        List<String> list = new ArrayList<String>();
        list.add(broker_code);
        list.add(broker_name);
        list.add(broker_rate);
        list.add(approved_status);
        list.add(broker_remark);
        Log.e("LIST", String.valueOf(list));
        return list;
    }

    private void rejectBtnClickerL(AdaptorHolder holder, FetchApprovalStatusModelListOfList model, int position) {
        holder.approved_btn.setTextColor(Color.WHITE);
        holder.approved_btn.setText(CONSTANT.APPROVE);
        holder.btn_broker_selectionReject.setTextColor(Color.GREEN);
        holder.btn_broker_selectionReject.setText(CONSTANT.REJECT);
        holder.broker_code.setText(model.getBrokerCode());
        selection_code = -1;
    }

    private void approvedBtnClickedL(AdaptorHolder holder, FetchApprovalStatusModelListOfList model) {
        holder.approved_btn.setTextColor(Color.GREEN);
        holder.approved_btn.setText(CONSTANT.APPROVED);
        holder.btn_broker_selectionReject.setTextColor(Color.WHITE);
        holder.btn_broker_selectionReject.setText(CONSTANT.REJECT);
        holder.broker_code.setText(model.getBrokerCode());
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

        public AdaptorHolder(@NonNull View itemView) {
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
