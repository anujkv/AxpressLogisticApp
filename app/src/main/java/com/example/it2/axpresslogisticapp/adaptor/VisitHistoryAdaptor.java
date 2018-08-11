package com.example.it2.axpresslogisticapp.adaptor;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.it2.axpresslogisticapp.R;
import com.example.it2.axpresslogisticapp.model.VisitHistoryModel;

import java.util.List;

public class VisitHistoryAdaptor extends RecyclerView.Adapter<VisitHistoryAdaptor.HistoryHolder> {
    Context context;
    List<VisitHistoryModel> visitHistoryModels;
    Dialog dialog;

    public VisitHistoryAdaptor(Context context, List<VisitHistoryModel> visitHistoryModels) {
        this.context = context;
        this.visitHistoryModels = visitHistoryModels;
    }

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.visit_history_layout,parent,false);
        return new HistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HistoryHolder holder, int position) {
        final VisitHistoryModel visitHistoryModel = visitHistoryModels.get(position);
        holder.customer.setText(visitHistoryModel.getCustomer());
        holder.visit_date.setText(visitHistoryModel.getVisit_date());
        holder.visit_for.setText(visitHistoryModel.getVisit_for());
        holder.visit_type.setText(visitHistoryModel.getVisit_type());
        holder.ref_no.setText(visitHistoryModel.getRef_no());


        dialog = new Dialog(context);
        dialog.setContentView(R.layout.visit_item_details_listview);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            EditText edt_coustomer_company,edt_visit_date,edt_visit_for,edt_visit_type,edt_contact_person,
            edt_contact_no,edt_email_id,edt_address,edt_product,edt_status,edt_scope,edt_remark,
            edt_other_employee_name;
            TextView ref_no;
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Pressed ",Toast.LENGTH_SHORT).show();
                edt_coustomer_company = dialog.findViewById(R.id.edt_customer_name);
                edt_coustomer_company.setText(visitHistoryModel.getCustomer());
                edt_visit_date = dialog.findViewById(R.id.edtVisitDate);
                edt_visit_date.setText(visitHistoryModel.getVisit_date());
                edt_visit_for = dialog.findViewById(R.id.edt_visit_for);
                edt_visit_for.setText(visitHistoryModel.getVisit_for());
                edt_visit_type = dialog.findViewById(R.id.edt_visit_type);
                edt_visit_type.setText(visitHistoryModel.getVisit_type());
                edt_contact_person = dialog.findViewById(R.id.edtContactPerson);
                edt_contact_no = dialog.findViewById(R.id.edtContactNo);
                edt_email_id = dialog.findViewById(R.id.edtEmail);
                edt_address = dialog.findViewById(R.id.edtAddress);
                edt_product = dialog.findViewById(R.id.edt_product_name);
                edt_status = dialog.findViewById(R.id.edt_status);
                edt_scope = dialog.findViewById(R.id.edt_scope);
                edt_remark = dialog.findViewById(R.id.edtRemark);
                edt_other_employee_name = dialog.findViewById(R.id.edt_other_employee_name);
                ref_no = dialog.findViewById(R.id.ref_no_id);
//                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return visitHistoryModels.size();
    }

    public class HistoryHolder extends RecyclerView.ViewHolder {
        TextView  customer,visit_date,visit_for,visit_type,ref_no;
        CardView cardView;
        public HistoryHolder(View itemView) {
            super(itemView);
            customer = itemView.findViewById(R.id.edt_customer_name);
            visit_date = itemView.findViewById(R.id.edtVisitDate);
            visit_for = itemView.findViewById(R.id.edt_visit_for);
            visit_type = itemView.findViewById(R.id.edt_visit_type);
            ref_no = itemView.findViewById(R.id.ref_no_id);
            cardView = itemView.findViewById(R.id.visit_history_layout);
        }
    }
}
