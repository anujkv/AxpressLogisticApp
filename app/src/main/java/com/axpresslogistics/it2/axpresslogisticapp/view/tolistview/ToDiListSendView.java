package com.axpresslogistics.it2.axpresslogisticapp.view.tolistview;


import com.axpresslogistics.it2.axpresslogisticapp.model.todolistmodel.ToDoListAddModelList;
import com.axpresslogistics.it2.axpresslogisticapp.view.MainView;

import java.util.List;

public interface ToDiListSendView extends MainView {

    String getCreated_by();

    String getDate_time();

    String getKey();

    String getMethod();

    String getNote();

    String getReminder_Switch();

   List<ToDoListAddModelList> getContact();

}
