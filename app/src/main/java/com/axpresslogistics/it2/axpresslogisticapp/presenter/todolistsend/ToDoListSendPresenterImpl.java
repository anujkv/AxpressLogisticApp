package com.axpresslogistics.it2.axpresslogisticapp.presenter.todolistsend;


import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.model.todolistmodel.ToDoListSendRequest;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.tolistview.ToDiListSendView;

import java.util.HashMap;
import java.util.Map;

public class ToDoListSendPresenterImpl implements LoadListener, MainPresenter<ToDiListSendView> {
   MainInteractor interactor;
    ToDoListSendRequest toDoListSendRequest;
    ToDiListSendView toDiListSendView;

    public ToDoListSendPresenterImpl(ToDiListSendView toDiListSendView) {
        this.toDiListSendView = toDiListSendView;
    }

    @Override
    public void onSuccess(Object o) {
        toDiListSendView.hideLoadingLayout();
        toDiListSendView.showSuccess(o);


    }

    @Override
    public void onFailure(String errorMessage) {
        toDiListSendView.hideLoadingLayout();
        toDiListSendView.showFailure(errorMessage);

    }

    @Override
    public void init() {
        if(isValid())
        toDiListSendView.showLoadingLayout();
        interactor = new ToDoListSendPresenter(getHeader(), toDoListSendRequest );
        interactor.loadItems(this);
    }

    @Override
    public void subscribe(ToDiListSendView toDiListSendView) {
        this.toDiListSendView = toDiListSendView;

    }

    @Override
    public void unSubscribe() {
        toDiListSendView = null;

    }

    private Map<String, String> getHeader(){
        Map<String, String> param = new HashMap<>();
        param.put("content-type","application/json");
        return param;
    }
   private boolean isValid(){
      toDoListSendRequest = new ToDoListSendRequest();
      toDoListSendRequest.setContact(toDiListSendView.getContact());
      toDoListSendRequest.setCreatedBy(toDiListSendView.getCreated_by());
      toDoListSendRequest.setDateTime(toDiListSendView.getDate_time());
      toDoListSendRequest.setKey(toDiListSendView.getKey());
      toDoListSendRequest.setMethod("reminder");
      toDoListSendRequest.setReminderSwitch(toDiListSendView.getReminder_Switch());
      toDoListSendRequest.setNote(toDiListSendView.getNote());
        return true;
   }


}
