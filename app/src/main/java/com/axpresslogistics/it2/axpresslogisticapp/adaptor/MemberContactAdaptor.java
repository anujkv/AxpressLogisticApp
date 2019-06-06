package com.axpresslogistics.it2.axpresslogisticapp.adaptor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.model.todolistmodel.ToDoListAddModelList;

import java.util.List;

public class MemberContactAdaptor extends RecyclerView.Adapter<MemberContactAdaptor.ViewHolder>{

    Context context;
    List<ToDoListAddModelList> contactModels;

    public MemberContactAdaptor(Context context, List<ToDoListAddModelList> contactModels) {
        this.context = context;
        this.contactModels = contactModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_member,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        ToDoListAddModelList contactModel = contactModels.get(position);
        holder.emplid.setText(contactModel.getEmplid());
        holder.name.setText(contactModel.getName());
        holder.contact.setText(contactModel.getContact());
        holder.email.setText(contactModel.getEmail());
        holder.dept_code.setText(contactModel.getDeptCode());
        holder.branch_code.setText(contactModel.getBranchCode());
        holder.close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.cardView.removeAllViews();

            }
        });
//        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {

        return contactModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView emplid,name,contact,email,dept_code, branch_code;
        ImageButton close_btn;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            emplid = itemView.findViewById(R.id.emplid);
            name = itemView.findViewById(R.id.name);
            contact = itemView.findViewById(R.id.contact);
            email = itemView.findViewById(R.id.email);
            dept_code =itemView.findViewById(R.id.dept_code);
            branch_code = itemView.findViewById(R.id.branch_code);
            close_btn = itemView.findViewById(R.id.close_id);
            cardView = itemView.findViewById(R.id.member_contact_cardview);


        }
    }
}
