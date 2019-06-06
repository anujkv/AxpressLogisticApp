package com.axpresslogistics.it2.axpresslogisticapp.view.CRMView.BusinessCardView;


import com.axpresslogistics.it2.axpresslogisticapp.view.MainView;

public interface UpdateBusinessCardDetailsView extends MainView {
    String getKey();
    String getMethod();
    String getCardId();
    String getFrontImage();
    String getBackImage();
    String getJobTitle();
    String getName();
    String getCompany();
    String getMobile1();
    String getMobile2();
    String getTel();
    String getEmail();
    String getFax();
    String getWeb();
    String getQRCode();
    String getAddress();
    String getCreatedBy();
}
