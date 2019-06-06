package com.axpresslogistics.it2.axpresslogisticapp.view.operationView.ChallanRec;


import com.axpresslogistics.it2.axpresslogisticapp.view.MainView;

public interface ChallanReceiveView extends MainView {
   String getBarcode();

   String getMethod();

   String getKey();

   String getDBName();
}
