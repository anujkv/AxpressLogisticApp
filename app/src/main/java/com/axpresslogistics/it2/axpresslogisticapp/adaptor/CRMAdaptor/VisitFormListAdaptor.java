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
import com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.visitform.SavedList;

import java.util.List;

public class VisitFormListAdaptor extends RecyclerView.Adapter<VisitFormListAdaptor.ViewHolder> {
    Context context;
    List<SavedList> savedLists;

    public VisitFormListAdaptor(Context context, List<SavedList> savedLists) {
        this.context = context;
        this.savedLists = savedLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.visit_listview,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        final SavedList visitModel = savedLists.get(i);
        holder.companyUniqueID.setText(visitModel.getRefNo());
        holder.companyName.setText(visitModel.getCompanyName());
        holder.contactPersonName.setText(visitModel.getContactPerson());
        holder.contactPersonNo.setText(visitModel.getMobile());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, NewVisitForm.class);
                intent.putExtra("ref_no", visitModel.getRefNo());
                intent.putExtra("method", "customer_visit_search");
                intent.putExtra("input", visitModel.getCompanyName());
//                intent.putExtra("followChecked",true);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return savedLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView companyUniqueID,companyName,contactPersonName,contactPersonNo;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            companyUniqueID = itemView.findViewById(R.id.txtCompanyUniqueID);
            companyName = itemView.findViewById(R.id.txt_visit_company);
            contactPersonName = itemView.findViewById(R.id.txt_visit_contactPerson);
            contactPersonNo = itemView.findViewById(R.id.txt_visit_contactPersonNo);
            cardView = itemView.findViewById(R.id.cardViewVisitCard);
        }
    }
}
