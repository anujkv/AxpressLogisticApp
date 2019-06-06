package com.axpresslogistics.it2.axpresslogisticapp.adaptor.CRMAdaptor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.businesscard.BusinessCardListResponse.SearchBusinessCardM;

import java.util.List;


public class SearchBusinessCardAdaptor extends RecyclerView.Adapter<SearchBusinessCardAdaptor.ViewHolder> {
    Context context;
    List<SearchBusinessCardM> searchBusinessCardMList;

    public SearchBusinessCardAdaptor(Context context, List<SearchBusinessCardM> searchBusinessCardMList) {
        this.context = context;
        this.searchBusinessCardMList = searchBusinessCardMList;
    }

    @NonNull
    @Override
    public SearchBusinessCardAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item_listview,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchBusinessCardAdaptor.ViewHolder holder, int i) {
        final SearchBusinessCardM businessCardList = searchBusinessCardMList.get(i);
        if(businessCardList.getCardId()!=null || !businessCardList.getCardId().equals("")){
            holder.id.setText(businessCardList.getCardId());
        }
        if(businessCardList.getName()!=null || !businessCardList.getName().equals("")){
            holder.name.setText(businessCardList.getName());
            holder.name.setVisibility(View.VISIBLE);
        }else{
            holder.name.setVisibility(View.INVISIBLE);
        }
        if(businessCardList.getJobTitle()!=null || !businessCardList.getJobTitle().equals("")){
            holder.job_title.setText(businessCardList.getJobTitle());
            holder.job_title.setVisibility(View.VISIBLE);
        }else{
            holder.job_title.setVisibility(View.INVISIBLE);
        }
        if(businessCardList.getEmail()!=null || !businessCardList.getEmail().equals("")){
            holder.email.setText(businessCardList.getEmail());
            holder.email.setVisibility(View.VISIBLE);
        }else{
            holder.name.setVisibility(View.INVISIBLE);
        }


        if(businessCardList.getMobile1()!=null || !businessCardList.getMobile1().equals("")){
            holder.mobile1.setText(businessCardList.getMobile1());
            holder.mobile1.setVisibility(View.VISIBLE);
        }else{
            holder.mobile1.setVisibility(View.INVISIBLE);
        }
        if(businessCardList.getMobile2()!=null || !businessCardList.getMobile2().equals("") ||
                !businessCardList.getMobile2().equals("null")){
            holder.mobile2.setText(businessCardList.getMobile2());
            holder.mobile2.setVisibility(View.VISIBLE);
        }else{
            holder.mobile2.setVisibility(View.INVISIBLE);
        }
        if(businessCardList.getTelephone()!=null || !businessCardList.getTelephone().equals("")){
            holder.telephone.setText(businessCardList.getTelephone());
            holder.telephone.setVisibility(View.VISIBLE);
        }else{
            holder.telephone.setVisibility(View.INVISIBLE);
        }
        if(businessCardList.getWebsite()!=null || !businessCardList.getWebsite().equals("")){
            holder.website.setText(businessCardList.getWebsite());
            holder.website.setVisibility(View.VISIBLE);
        }else{
            holder.website.setVisibility(View.INVISIBLE);
        }
        if(businessCardList.getAddress()!=null || !businessCardList.getAddress().equals("")){
            holder.address.setText(businessCardList.getAddress());
            holder.address.setVisibility(View.VISIBLE);
        }else{
            holder.address.setVisibility(View.INVISIBLE);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Card ID",businessCardList.getCardId());
                Context context = v.getContext();
                //   Intent intent = new Intent(context, BusinessCardView.class);
                //  intent.putExtra("id",businessCardList.getCardId());
                // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchBusinessCardMList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id,name, job_title, email, mobile1, mobile2, telephone, website, address;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.card_id);
            name = itemView.findViewById(R.id.name);
            job_title = itemView.findViewById(R.id.job_title);
            email = itemView.findViewById(R.id.email);
            mobile1 = itemView.findViewById(R.id.mobile1);
            mobile2 = itemView.findViewById(R.id.mobile2);
            telephone = itemView.findViewById(R.id.telephone);
            website = itemView.findViewById(R.id.website);
            address = itemView.findViewById(R.id.address);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
