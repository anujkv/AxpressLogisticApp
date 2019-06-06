
package com.axpresslogistics.it2.axpresslogisticapp.presenter.todolist;




import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.tolistview.ToDoListView;

import java.util.HashMap;
import java.util.Map;

public class ToDoListPresenterImpl implements LoadListener, MainPresenter<ToDoListView> {
    private MainInteractor mainInteractor;
    private ToDoListView toDoListView;


    public ToDoListPresenterImpl(ToDoListView toDoListView)
    {
        this.toDoListView = toDoListView;
    }

    @Override
    public void onSuccess(Object o) {
        toDoListView.hideLoadingLayout();
        toDoListView.showSuccess(o);
    }

    @Override
    public void onFailure(String errorMessage) {
        toDoListView.hideLoadingLayout();
        toDoListView.showFailure(errorMessage);
    }

    @Override
    public void init()
    {
        toDoListView.showLoadingLayout();
        mainInteractor = new ToDoListPresenter(getHeader(),getBody());
        mainInteractor.loadItems(this);
    }

    @Override
    public void subscribe(ToDoListView toDoListView) {
    this.toDoListView = toDoListView;

    }

    @Override
    public void unSubscribe() {

        toDoListView = null;
    }

    public Map<String, String> getHeader(){
        Map<String, String> param = new HashMap<>();
        param.put("content-type","application/json");
        return param;
    }
    public Map<String, String> getBody(){
        Map<String, String> param = new HashMap<>();
        param.put("method",toDoListView.getMethod());
        param.put("key",toDoListView.getKey());
        param.put("emplid",toDoListView.getEmp_id());
        return param;
    }



}

