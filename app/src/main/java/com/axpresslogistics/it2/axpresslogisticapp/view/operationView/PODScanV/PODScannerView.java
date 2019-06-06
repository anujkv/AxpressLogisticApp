package com.axpresslogistics.it2.axpresslogisticapp.view.operationView.PODScanV;

import com.axpresslogistics.it2.axpresslogisticapp.view.MainView;

public interface PODScannerView extends MainView {
    String getImage();

    String getDocketNo();

    String getEmplid();

    String getImageType();

    String getDBName();
}
