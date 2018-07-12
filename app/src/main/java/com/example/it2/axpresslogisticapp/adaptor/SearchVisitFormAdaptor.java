package com.example.it2.axpresslogisticapp.adaptor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.it2.axpresslogisticapp.R;
import com.example.it2.axpresslogisticapp.acitvities.CustomerVisitFormActivity;
import com.example.it2.axpresslogisticapp.model.SearchVisitForm;

import java.util.List;

public class SearchVisitFormAdaptor extends RecyclerView.Adapter<SearchVisitFormAdaptor.SearchHolder>{

    private Context  context;
    private List<SearchVisitForm> searchVisitFormList;

    CustomerVisitFormActivity customerVisitFormActivity;

    public SearchVisitFormAdaptor(Context context, List<SearchVisitForm> searchVisitFormList) {
        this.context = context;
        this.searchVisitFormList = searchVisitFormList;
    }

    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.search_listview,parent,false);
        return new SearchHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHolder holder, int position) {

        final SearchVisitForm searchVisitForm = searchVisitFormList.get(position);
        holder.company_name.setText(searchVisitForm.getInput());
        holder.unique_comp_id.setText(searchVisitForm.getId());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerVisitFormActivity.selectedSearchValue(searchVisitForm.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchVisitFormList.size();
    }

    public class SearchHolder extends RecyclerView.ViewHolder {
        TextView company_name,unique_comp_id;
        LinearLayout linearLayout;

        public SearchHolder(View itemView) {
            super(itemView);
            company_name = itemView.findViewById(R.id.txt_compId);
            unique_comp_id = itemView.findViewById(R.id.txt_unique_compId);
            linearLayout = itemView.findViewById(R.id.search_listviewLayout);
        }
    }
}
