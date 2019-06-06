package com.axpresslogistics.it2.axpresslogisticapp.adaptor.CRMAdaptor;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.newVisitForm.HistoryView;
import com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.newVisitForm.VisitFormHistoryResponse;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.Preferences;
import com.axpresslogistics.it2.axpresslogisticapp.view.CRMView.VisitFormView.VisitFormHistoryView;

import java.util.List;

public class VisitHistoryAdaptor extends RecyclerView.Adapter<VisitHistoryAdaptor.ViewHoldder> implements VisitFormHistoryView {
    Context context;
    List<HistoryView> historyViews;
    Dialog dialog;
    HistoryView visitHistoryModel;

    String ref_No = "";
    MainPresenter presenter;

    private EditText edt_coustomer_company,edt_visit_date,edt_visit_for,edt_visit_type,edt_contact_person,
            edt_contact_no,edt_email_id,edt_address,edt_product,edt_status,edt_scope,edt_remark,
            edt_other_employee_name;

    public VisitHistoryAdaptor(Context context, List<HistoryView> historyViews) {
        this.context = context;
        this.historyViews = historyViews;
    }


    public VisitHistoryAdaptor(Context context, List<HistoryView> historyViews, String ref_No) {
        this.context = context;
        this.historyViews = historyViews;
        this.ref_No = ref_No;
    }

    @NonNull
    @Override
    public ViewHoldder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.visit_history_layout,viewGroup,false);
        return new ViewHoldder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoldder holder, int position) {
        visitHistoryModel = historyViews.get(position);
        holder.customer.setText(visitHistoryModel.getCustomerName());
        holder.visit_date.setText(visitHistoryModel.getVisitDate());
        holder.visit_for.setText(visitHistoryModel.getVisitFor());
        holder.visit_type.setText(visitHistoryModel.getVisitType());
        holder.ref_no.setText(visitHistoryModel.getRef_no());

        holder.cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                MyDialog();
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyViews.size();
    }

    @Override
    public String getKey() {
        return null;
    }

    @Override
    public String getMethod() {
        return null;
    }

    @Override
    public String getRefNo() {
        return null;
    }

    @Override
    public String getDBName() {
        return Preferences.getPreference(context, CONSTANT.DB_NAME);
    }

    @Override
    public void showLoadingLayout() {

    }

    @Override
    public void hideLoadingLayout() {

    }

    @Override
    public void showSuccess(Object object) {
        if(object instanceof VisitFormHistoryResponse){
//            CustomerVisitSearchModel response = (CustomerVisitSearchModel)object;
//            if(response != null && response.getStatus().equals(Constant.TRUE)){
//                dialog = new Dialog(context);
//                dialog.setContentView(R.layout.visit_item_details_listview);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                Window window = dialog.getWindow();
//                window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                edt_coustomer_company = dialog.findViewById(R.id.edt_customer_name);
//                edt_coustomer_company.setText(response.getCustomer());
//                edt_visit_date = dialog.findViewById(R.id.edtVisitDate);
//                edt_visit_date.setText(response.getVisitDate());
//                edt_visit_for = dialog.findViewById(R.id.edt_visit_for);
//                edt_visit_for.setText(response.getVisitFor());
//                edt_visit_type = dialog.findViewById(R.id.edt_visit_type);
//                edt_visit_type.setText(response.getVisitType());
//                edt_contact_person = dialog.findViewById(R.id.edtContactPerson);
//                edt_contact_person.setText(response.getContactPerson());
//                edt_contact_no = dialog.findViewById(R.id.edtContactNo);
//                edt_contact_no.setText(response.getContact());
//                edt_email_id = dialog.findViewById(R.id.edtEmail);
//                edt_email_id.setText(response.getEmailId());
//                edt_address = dialog.findViewById(R.id.edtAddress);
//                edt_address.setText(response.getAddress());
//                edt_product = dialog.findViewById(R.id.edt_product_name);
//                edt_product.setText(response.getProduct());
//                edt_status = dialog.findViewById(R.id.edt_status);
//                edt_status.setText(response.getFollowupStatus());
//                edt_scope = dialog.findViewById(R.id.edt_scope);
//                edt_scope.setText(response.getScope());
//                edt_remark = dialog.findViewById(R.id.edtRemark);
//                edt_remark.setText(response.getRemark());
//                edt_other_employee_name = dialog.findViewById(R.id.edt_other_employee_name);
//                edt_other_employee_name.setText(response.getOtherEmployeeName());
//                dialog.show();
//            }
//        } else {
//            Toast.makeText(mContext, Constant.DATA_NOT_FOUND, Toast.LENGTH_SHORT).show();
 }

    }

    @Override
    public void showFailure(String error) {

    }

    public class ViewHoldder extends RecyclerView.ViewHolder {
        TextView customer,visit_date,visit_for,visit_type,ref_no;
        CardView cardView;
        public ViewHoldder(@NonNull View itemView) {
            super(itemView);
            customer = itemView.findViewById(R.id.edt_customer_name);
            visit_date = itemView.findViewById(R.id.edtVisitDate);
            visit_for = itemView.findViewById(R.id.edt_visit_for);
            visit_type = itemView.findViewById(R.id.edt_visit_type);
            ref_no = itemView.findViewById(R.id.ref_no_id);
            cardView = itemView.findViewById(R.id.visit_history_layout);
        }
    }


    private void MyDialog() {
//        presenter = new VisitFormHistoryPresenterImpl(this);
//        presenter.init();
    }

}
