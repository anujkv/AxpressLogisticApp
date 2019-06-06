package com.axpresslogistics.it2.axpresslogisticapp.presenter;

public interface MainPresenter<T> {
        void init();
        void subscribe(T t);
        void unSubscribe();
}
