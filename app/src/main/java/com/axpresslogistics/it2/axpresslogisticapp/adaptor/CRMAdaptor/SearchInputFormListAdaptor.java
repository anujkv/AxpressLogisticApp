package com.axpresslogistics.it2.axpresslogisticapp.adaptor.CRMAdaptor;

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
import com.axpresslogistics.it2.axpresslogisticapp.activities.CRM.VisitForm.NewVisitForm;
import com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.visitform.SearchVisitFormList;

import java.util.List;

public class SearchInputFormListAdaptor extends RecyclerView.Adapter<SearchInputFormListAdaptor.ListHolder> {
    Context context;
    List<SearchVisitFormList> inputListModelList;

    public SearchInputFormListAdaptor(Context context, List<SearchVisitFormList> inputListModelList) {
        this.context = context;
        this.inputListModelList = inputListModelList;
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.visit_listview,parent,false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {
        final SearchVisitFormList inputListModel = inputListModelList.get(position);
        holder.companyUniqueID.setText(inputListModel.getRefNo());
        holder.companyName.setText(inputListModel.getCompanyName());
        holder.contactPersonName.setText(inputListModel.getContactPerson());
        holder.contactPersonNo.setText(inputListModel.getMobile());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, NewVisitForm.class);
                intent.putExtra("ref_no", inputListModel.getRefNo());
                intent.putExtra("method", "customer_visit_search");
                intent.putExtra("input", inputListModel.getCompanyName());
//                intent.putExtra("followChecked",true);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return inputListModelList.size();
    }

    public static class ListHolder extends RecyclerView.ViewHolder {
        TextView companyUniqueID,companyName,contactPersonName,contactPersonNo;
        CardView cardView;
        public ListHolder(View itemView) {
            super(itemView);
            companyUniqueID = itemView.findViewById(R.id.txtCompanyUniqueID);
            companyName = itemView.findViewById(R.id.txt_visit_company);
            contactPersonName = itemView.findViewById(R.id.txt_visit_contactPerson);
            contactPersonNo = itemView.findViewById(R.id.txt_visit_contactPersonNo);
            cardView = itemView.findViewById(R.id.cardViewVisitCard);
        }
    }
}
