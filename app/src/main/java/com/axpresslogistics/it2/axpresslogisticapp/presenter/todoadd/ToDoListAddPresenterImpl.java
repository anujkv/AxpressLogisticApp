package com.axpresslogistics.it2.axpresslogisticapp.presenter.todoadd;



import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.tolistview.ToDoListAddView;

import java.util.HashMap;
import java.util.Map;

public class ToDoListAddPresenterImpl implements LoadListener, MainPresenter<ToDoListAddView> {
 MainInteractor interactor;
  Map<String, String> header;
  Map<String, String> body;
  ToDoListAddView toDoListAddView;
  public ToDoListAddPresenterImpl(ToDoListAddView toDoListAddView){
    this.toDoListAddView =toDoListAddView;

      }


    @Override
    public void onSuccess(Object o) {
      toDoListAddView.hideLoadingLayout();
      toDoListAddView.showSuccess(o);

    }

    @Override
    public void onFailure(String errorMessage) {
      toDoListAddView.hideLoadingLayout();
      toDoListAddView.showFailure(errorMessage);

    }

    @Override
    public void init() {
      toDoListAddView.showLoadingLayout();
      interactor = new ToDoListAddPresenter(getHeader(),getBody());
      interactor.loadItems(this);

    }

    @Override
    public void subscribe(ToDoListAddView toDoListAddView) {
     this.toDoListAddView= toDoListAddView;

    }

    @Override
    public void unSubscribe() {
      toDoListAddView=null;

    }

    public Map<String, String> getHeader(){
      Map<String, String> param = new HashMap<>();
      param.put("content-type", "application/json");
      return param;
    }
    public Map<String, String> getBody(){
      Map<String, String> param = new HashMap<>();
      param.put("method", toDoListAddView.getMethod());
      param.put("key",toDoListAddView.getkey());
      return param;
    }
}
