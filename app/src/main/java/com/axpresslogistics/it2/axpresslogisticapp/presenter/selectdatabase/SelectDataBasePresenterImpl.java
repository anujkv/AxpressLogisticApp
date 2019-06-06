package com.axpresslogistics.it2.axpresslogisticapp.presenter.selectdatabase;


import com.axpresslogistics.it2.axpresslogisticapp.interactor.MainInteractor;
import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;
import com.axpresslogistics.it2.axpresslogisticapp.presenter.MainPresenter;
import com.axpresslogistics.it2.axpresslogisticapp.view.BaseView.SelectDatabaseView;

public class SelectDataBasePresenterImpl implements LoadListener, MainPresenter<SelectDatabaseView> {
    SelectDatabaseView select_databaseview;
    MainInteractor interactor;

    public SelectDataBasePresenterImpl(SelectDatabaseView select_databaseview) {
        this.select_databaseview = select_databaseview;
    }

    @Override
    public void init() {
        select_databaseview.showLoadingLayout();
        interactor = new SelectDatabasePresenter(select_databaseview.getEmplid());
        interactor.loadItems(this);


    }

    @Override
    public void subscribe(SelectDatabaseView select_databaseview) {
        this.select_databaseview = select_databaseview;

    }


    @Override
    public void unSubscribe() {
        this.select_databaseview = null;
    }

    @Override
    public void onSuccess(Object o) {
        select_databaseview.hideLoadingLayout();
        select_databaseview.showSuccess(o);


    }

    @Override
    public void onFailure(String errorMessage) {
        select_databaseview.showLoadingLayout();
        select_databaseview.showFailure(errorMessage);

    }
}
