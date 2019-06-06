package com.axpresslogistics.it2.axpresslogisticapp.adaptor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.model.todolistmodel.ToDoListAddModelList;

import java.util.List;

public class ContactAdaptor extends RecyclerView.Adapter<ContactAdaptor.ViewHolder> {
    Context context;
    List<ToDoListAddModelList> contactModels;

    public ContactAdaptor(Context context, List<ToDoListAddModelList> contactModels) {
        this.context = context;
        this.contactModels = contactModels;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_list_listview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ToDoListAddModelList contactModel = contactModels.get(position);
        holder.emplid.setText(contactModel.getEmplid());
        holder.name.setText(contactModel.getName());
        holder.contact.setText(contactModel.getContact());
//        if(contactModel.getContact().trim().equals("")){
        holder.contact_layout.setVisibility(View.GONE);
//        }
        holder.email.setText(contactModel.getEmail());
        if (contactModel.getContact().equals("")) {
            holder.email_layout.setVisibility(View.GONE);
        }
        holder.dept_code.setText(contactModel.getDeptCode());
        holder.branch_code.setText(contactModel.getBranchCode());
        holder.checkBox.setTag(contactModels.get(position));

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                ToDoListAddModelList model = (ToDoListAddModelList) checkBox.getTag();

               model.setSelected(checkBox.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView emplid, name, contact, email, dept_code, branch_code;
        CheckBox checkBox;
        LinearLayout emplid_layout, name_layout, contact_layout, email_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            emplid = itemView.findViewById(R.id.emplid_tv);
            name = itemView.findViewById(R.id.name_tv);
            contact = itemView.findViewById(R.id.contact_tv);
            email = itemView.findViewById(R.id.email_tv);
            dept_code = itemView.findViewById(R.id.dept_code_tv);
            branch_code = itemView.findViewById(R.id.branch_code_tv);
            checkBox = itemView.findViewById(R.id.checkbox_id);
            emplid_layout = itemView.findViewById(R.id.emplid_layout);
            name_layout = itemView.findViewById(R.id.name_layout);
            contact_layout = itemView.findViewById(R.id.contact_layout);
            email_layout = itemView.findViewById(R.id.email_layout);
        }
    }
    public List<ToDoListAddModelList> getContactModels() {
        return contactModels;
    }
}
