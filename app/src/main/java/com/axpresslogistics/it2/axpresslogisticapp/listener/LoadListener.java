package com.axpresslogistics.it2.axpresslogisticapp.listener;

public interface LoadListener<T> {
    void onSuccess(T t);
    void onFailure(String errorMessage);
}
