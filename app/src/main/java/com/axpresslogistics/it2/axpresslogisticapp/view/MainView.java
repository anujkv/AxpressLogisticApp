package com.axpresslogistics.it2.axpresslogisticapp.view;

public interface MainView {
        void showLoadingLayout();

        void hideLoadingLayout();

        void showSuccess(Object o);

        void showFailure(String error);
}
