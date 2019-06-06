package com.axpresslogistics.it2.axpresslogisticapp.view.operationView.VendorApprovalView;

import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.VendorApproval.VendorFetch.VendorVehicleAttachRequestRateDetailse;
import com.axpresslogistics.it2.axpresslogisticapp.view.MainView;

import java.util.List;

public interface VendorApprovalView extends MainView {
    String getMethod();

    String getRequestNo();

    String getApprovalStatus();

    String getDBName();

    String getEmplid();

    List<VendorVehicleAttachRequestRateDetailse> getVendorVehicleAttachRequestRateDetailses();
}
