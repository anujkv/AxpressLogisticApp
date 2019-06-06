package com.axpresslogistics.it2.axpresslogisticapp.presenter.tododelete;



import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.tolistview.ToDoLIstDeleteView;

import java.util.HashMap;
import java.util.Map;

public class ToDoListDeletePresenterImpl implements LoadListener, MainPresenter<ToDoLIstDeleteView> {
   private MainInteractor interactor;
   private ToDoLIstDeleteView toDoLIstDeleteView;

   public ToDoListDeletePresenterImpl(ToDoLIstDeleteView toDoLIstDeleteView){

       this.toDoLIstDeleteView=toDoLIstDeleteView;
   }



    @Override
    public void onSuccess(Object o) {
       toDoLIstDeleteView.hideLoadingLayout();
       toDoLIstDeleteView.showSuccess(o);

    }

    @Override
    public void onFailure(String errorMessage) {
       toDoLIstDeleteView.hideLoadingLayout();
       toDoLIstDeleteView.showFailure(errorMessage);


    }

    @Override
    public void init() {
       toDoLIstDeleteView.showLoadingLayout();
       interactor = new ToDOLIstDeletePresenter(getHeader(),getBody());
       interactor.loadItems(this);

    }

    @Override
    public void subscribe(ToDoLIstDeleteView toDoLIstDeleteView) {
       this.toDoLIstDeleteView=toDoLIstDeleteView;

    }

    @Override
    public void unSubscribe() {
       toDoLIstDeleteView = null;

    }

   public Map<String, String> getHeader(){
       Map<String, String> param = new HashMap<>();
       param.put("content-type","application/json");
               return param;

    }

    public Map<String, String> getBody(){
       Map<String, String> param = new HashMap<>();
       param.put("method",toDoLIstDeleteView.getMethod());
       param.put("key",toDoLIstDeleteView.getKey());
       param.put("emplid",toDoLIstDeleteView.getEmpID());
       param.put("id",toDoLIstDeleteView.getId());
       return param;


    }
}
