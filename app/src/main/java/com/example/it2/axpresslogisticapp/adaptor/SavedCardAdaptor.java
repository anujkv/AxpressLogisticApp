package com.example.it2.axpresslogisticapp.adaptor;

import android.content.Context;
import android.content.Intent;
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
import com.example.it2.axpresslogisticapp.acitvities.CardActivity;
import com.example.it2.axpresslogisticapp.model.SaveCardModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SavedCardAdaptor extends RecyclerView.Adapter<SavedCardAdaptor.CardHolder> {

    private Context context;
    private List<SaveCardModel> saveCardModelList;

    public SavedCardAdaptor(Context context, List<SaveCardModel> saveCardModelList) {
        this.context = context;
        this.saveCardModelList = saveCardModelList;
    }

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.savedcard_listview,parent,false);
        return new CardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardHolder holder, final int position) {
        final SaveCardModel cardModel = saveCardModelList.get(position);
        holder.txt_username.setText(cardModel.getUsername());
        holder.txt_website.setText(cardModel.getWebsite());
        holder.txt_email.setText(cardModel.getEmail());
        Glide.with(context).asBitmap().load(cardModel.getImage()).into(holder.image);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,cardModel.getUsername() , Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(v.getContext(), CardActivity.class);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, CardActivity.class);
                        intent.putExtra("name",cardModel.getUsername());
                        intent.putExtra("website",cardModel.getWebsite());
                        intent.putExtra("email",cardModel.getEmail());
                        context.startActivity(intent);
                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return saveCardModelList.size();
    }

    public class CardHolder extends RecyclerView.ViewHolder  {
        TextView txt_username,txt_website,txt_email;
        ImageView image;
        CardView cardView;
        public CardHolder(View itemView) {
            super(itemView);
            txt_username = itemView.findViewById(R.id.txt_SC_username);
            txt_website = itemView.findViewById(R.id.txt_SC_website);
            txt_email = itemView.findViewById(R.id.txt_SC_email);
            image = itemView.findViewById(R.id.profile_image);
            cardView = itemView.findViewById(R.id.cardViewSavedCard);

        }
    }
}
