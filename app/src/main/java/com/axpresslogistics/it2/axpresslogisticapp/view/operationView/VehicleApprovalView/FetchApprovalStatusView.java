package com.axpresslogistics.it2.axpresslogisticapp.view.operationView.VehicleApprovalView;

import com.axpresslogistics.it2.axpresslogisticapp.view.MainView;

public interface FetchApprovalStatusView extends MainView {

    String getEmplid();

    String getVehical_request_code();

    String getMethod();

    String getKey();

    String getBranchCode();

    String getDBName();
}
