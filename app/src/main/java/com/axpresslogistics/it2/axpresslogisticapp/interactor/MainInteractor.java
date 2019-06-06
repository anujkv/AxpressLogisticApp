package com.axpresslogistics.it2.axpresslogisticapp.interactor;

import com.axpresslogistics.it2.axpresslogisticapp.listener.LoadListener;

public interface MainInteractor {
    void loadItems(final LoadListener<Object> loadListener);
}
