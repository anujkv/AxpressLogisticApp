package com.example.it2.axpresslogisticapp.adaptor;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.it2.axpresslogisticapp.R;
import com.example.it2.axpresslogisticapp.acitvities.CustomerVisitFormActivity;
import com.example.it2.axpresslogisticapp.model.VisitModel;

import java.util.List;

public class VisitAdaptor extends RecyclerView.Adapter<VisitAdaptor.VisitHolder> {

    private Context context;
    private List<VisitModel> visitModelList;

    public VisitAdaptor(Context context, List<VisitModel> visitModelList) {
        this.context = context;
        this.visitModelList = visitModelList;
    }

    @NonNull
    @Override
    public VisitHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.visit_listview,parent,false);
        return new VisitHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VisitHolder holder, final int position) {
        final VisitModel visitModel = visitModelList.get(position);
        holder.companyUniqueID.setText(visitModel.getCompanyUniqueID());
        holder.companyName.setText(visitModel.getCompanyName());
        holder.contactPersonName.setText(visitModel.getContactPersonName());
        holder.contactPersonNo.setText(visitModel.getPersonContactNo());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, CustomerVisitFormActivity.class);
                intent.putExtra("companyUniqueID", holder.companyUniqueID.toString());
                intent.putExtra("method", "saved_visit_list_Item_delete");

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return visitModelList.size();
    }

    public class VisitHolder extends RecyclerView.ViewHolder {
        TextView companyUniqueID,companyName,contactPersonName,contactPersonNo;
        CardView cardView;
        public VisitHolder(View itemView) {
            super(itemView);
            companyUniqueID = itemView.findViewById(R.id.txtCompanyUniqueID);
            companyName = itemView.findViewById(R.id.txt_visit_company);
            contactPersonName = itemView.findViewById(R.id.txt_visit_contactPerson);
            contactPersonNo = itemView.findViewById(R.id.txt_visit_contactPersonNo);
            cardView = itemView.findViewById(R.id.cardViewVisitCard);
        }
    }
}
