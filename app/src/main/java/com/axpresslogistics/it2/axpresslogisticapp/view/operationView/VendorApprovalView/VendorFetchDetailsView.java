package com.axpresslogistics.it2.axpresslogisticapp.view.operationView.VendorApprovalView;

import com.axpresslogistics.it2.axpresslogisticapp.view.MainView;

public interface VendorFetchDetailsView extends MainView {
    String getRequestNo();

    String getMethod();

    String getEmplid();

    String getKey();

    String getDBName();
}
