package com.example.it2.axpresslogisticapp.adaptor;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.it2.axpresslogisticapp.R;
import com.example.it2.axpresslogisticapp.acitvities.CustomerVisitFormActivity;
import com.example.it2.axpresslogisticapp.model.SearchInputListModel;

import java.util.List;

public class SearchInputListAdaptor extends RecyclerView.Adapter<SearchInputListAdaptor.ListHolder> {
    Context context;
    List<SearchInputListModel> inputListModelList;

    public SearchInputListAdaptor(Context context, List<SearchInputListModel> inputListModelList) {
        this.context = context;
        this.inputListModelList = inputListModelList;
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.searchlistview,parent,false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {
        final SearchInputListModel inputListModel = inputListModelList.get(position);
        holder.company_name.setText(inputListModel.getCompany_name());
        holder.company_refid.setText(inputListModel.getCompany_ref_no());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, CustomerVisitFormActivity.class);
                intent.putExtra("ref_no", inputListModel.getCompany_ref_no());
                intent.putExtra("method", "customer_visit_search");
                intent.putExtra("input", inputListModel.getCompany_name());
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

    public class ListHolder extends RecyclerView.ViewHolder {
        TextView company_name,company_refid;
        CardView cardView;
        public ListHolder(View itemView) {
            super(itemView);
            company_name = itemView.findViewById(R.id.company_name_txt);
            company_refid = itemView.findViewById(R.id.company_refid_txt);
            cardView = itemView.findViewById(R.id.searchlistviewCardview);
        }
    }
}
