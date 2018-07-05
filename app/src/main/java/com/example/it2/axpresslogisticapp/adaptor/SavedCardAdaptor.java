package com.example.it2.axpresslogisticapp.adaptor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.it2.axpresslogisticapp.R;
import com.example.it2.axpresslogisticapp.model.SavedCardModel;

import java.util.List;

public class SavedCardAdaptor extends RecyclerView.Adapter<SavedCardAdaptor.CardViewHolder> {

    private Context context;
    private List<SavedCardModel> savedCardModelList;

    public SavedCardAdaptor(Context context,List<SavedCardModel> savedCardModelList){
        this.context = context;
        this.savedCardModelList = savedCardModelList;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.savedcard_listview, parent, false);
        return new CardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {

        SavedCardModel savedCardModel = savedCardModelList.get(position);
        holder.user_img.setImageDrawable(context.getResources().getDrawable(Integer.parseInt(
                savedCardModel.getUser_image())));
        holder.txt_username.setText(savedCardModel.getUsername());
        holder.txt_website.setText(savedCardModel.getWebsite());
        holder.txt_email.setText(savedCardModel.getEmail());
    }

    @Override
    public int getItemCount() {
        return savedCardModelList.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        TextView txt_username,txt_website,txt_email;
        ImageView user_img;
        public CardViewHolder(View itemView) {
            super(itemView);

            txt_username = itemView.findViewById(R.id.txt_SC_username);
            txt_website = itemView.findViewById(R.id.txt_SC_website);
            txt_email = itemView.findViewById(R.id.txt_SC_email);
            user_img = itemView.findViewById(R.id.profile_image);

        }
    }
}
