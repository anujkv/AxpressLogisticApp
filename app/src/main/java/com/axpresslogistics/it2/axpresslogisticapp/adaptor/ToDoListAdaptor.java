package com.axpresslogistics.it2.axpresslogisticapp.adaptor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.model.todolistmodel.ToDoListDeleteModel;
import com.axpresslogistics.it2.axpresslogisticapp.model.todolistmodel.ToDoListModelList;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.tododelete.ToDoListDeletePresenterImpl;
import com.axpresslogistics.it2.axpresslogisticapp.view.tolistview.ToDoLIstDeleteView;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.ApiKey;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.CONSTANT;
import com.axpresslogistics.it2.axpresslogisticapp.utilities.Preferences;

import java.util.List;


public class ToDoListAdaptor extends RecyclerView.Adapter<ToDoListAdaptor.ViewHolder> implements ToDoLIstDeleteView {
    Context context;
    List<ToDoListModelList> listModelList;
    ToDoListModelList toDoListModel;
    String apikey;
    MainPresenter presenter;
    String holder_Id= "";
    private ViewHolder holder;


    public ToDoListAdaptor(Context context, List<ToDoListModelList> listModelList) {
        this.context = context;
        this.listModelList = listModelList;
    }

    @NonNull
    @Override
    public ToDoListAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.saved_list_itemview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ToDoListAdaptor.ViewHolder holder, int position) {
        toDoListModel = listModelList.get(position);
        holder.id.setText(toDoListModel.getId());
        holder_Id = toDoListModel.getId();
        holder.note.setText(toDoListModel.getNote());
        holder.date_time.setText(toDoListModel.getDateTime());
        holder.reminder_switch.setText(toDoListModel.getReminderSwitch());
        if (toDoListModel.getReminderSwitch().equals("on")) {
            holder.reminder_on.setImageResource(R.drawable.ic_alarm_on_black_18dp);
        } else {
            holder.reminder_on.setImageResource(R.drawable.ic_note_black_18dp);
        }
        final String id = toDoListModel.getId();
        holder.created_by.setText(toDoListModel.getCreatedBy());
        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "ID" + toDoListModel.getId(), Toast.LENGTH_SHORT);
                Log.e("ID", toDoListModel.getId());
                deleteTask();
             //   holder.cardView.removeView(v);


            }
        });


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ID", toDoListModel.getId());
            }
        });
    }

    private void deleteTask() {
        presenter = new ToDoListDeletePresenterImpl(this);
        presenter.init();

    }


    @Override
    public int getItemCount() {

        return listModelList.size();
    }

    @Override
    public String getMethod() {

        return "remove_reminder";
    }

    @Override
    public String getKey() {
        final ApiKey obj = new ApiKey();
        apikey = obj.saltStr();
        return apikey;
    }

    @Override
    public String getEmpID() {

        return Preferences.getPreference(context, CONSTANT.EMPID);
    }

    @Override
    public String getId() {
        return holder_Id;
    }

    @Override
    public void showLoadingLayout() {

    }

    @Override
    public void hideLoadingLayout() {

    }

    @Override
    public void showSuccess(Object object) {
        if (object instanceof ToDoListDeleteModel) {
            ToDoListDeleteModel toDoListDeleteModel = (ToDoListDeleteModel) object;
            if (toDoListDeleteModel != null) {
                if (toDoListDeleteModel.getMethod().equals(CONSTANT.TRUE)) {

                  if(holder.reminder_switch.equals("on")){
                        Toast.makeText(context,"Reminder delete successfully!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context,"Note delete successfully!", Toast.LENGTH_SHORT).show();
                    }
                   
                 holder.cardView.removeAllViews();
                }
                else{
                   Toast.makeText(context,"This task will delete by only Task Creater!", Toast.LENGTH_SHORT).show();
                }
            }

        }}


    @Override
    public void showFailure(String error) {

    }

public class ViewHolder extends RecyclerView.ViewHolder {
    CardView cardView;
    TextView id, note, date_time, reminder_switch, created_by;
    ImageButton delete_btn;
    ImageView reminder_on, reminder_off, note_img;

    public ViewHolder(View itemView) {
        super(itemView);
        id = itemView.findViewById(R.id.todo_id);
        note = itemView.findViewById(R.id.todo_note);
        date_time = itemView.findViewById(R.id.todo_date_time);
        reminder_switch = itemView.findViewById(R.id.todo_reminder_status);
        created_by = itemView.findViewById(R.id.todo_created_by);
        delete_btn = itemView.findViewById(R.id.delete_item_id);
        reminder_on = itemView.findViewById(R.id.alarm_on);
        cardView = itemView.findViewById(R.id.todo_itemview_cardview);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //deleteTask();
            }
        });
    }
}
}
