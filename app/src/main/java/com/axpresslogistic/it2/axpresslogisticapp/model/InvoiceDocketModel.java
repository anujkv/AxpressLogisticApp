package com.axpresslogistic.it2.axpresslogisticapp.model;

import android.widget.TextView;

public class InvoiceDocketModel {
    String docket_noID,txt_docketDateID,txt_consigneeID, txt_fromID,txt_toID, txt_statusID;

    public InvoiceDocketModel(String docket_noID, String txt_docketDateID, String txt_consigneeID, String txt_fromID, String txt_toID, String txt_statusID) {
        this.docket_noID = docket_noID;
        this.txt_docketDateID = txt_docketDateID;
        this.txt_consigneeID = txt_consigneeID;
        this.txt_fromID = txt_fromID;
        this.txt_toID = txt_toID;
        this.txt_statusID = txt_statusID;
    }

    public InvoiceDocketModel() {
    }

    public String getDocket_noID() {
        return docket_noID;
    }

    public void setDocket_noID(String docket_noID) {
        this.docket_noID = docket_noID;
    }

    public String getTxt_docketDateID() {
        return txt_docketDateID;
    }

    public void setTxt_docketDateID(String txt_docketDateID) {
        this.txt_docketDateID = txt_docketDateID;
    }

    public String getTxt_consigneeID() {
        return txt_consigneeID;
    }

    public void setTxt_consigneeID(String txt_consigneeID) {
        this.txt_consigneeID = txt_consigneeID;
    }

    public String getTxt_fromID() {
        return txt_fromID;
    }

    public void setTxt_fromID(String txt_fromID) {
        this.txt_fromID = txt_fromID;
    }

    public String getTxt_toID() {
        return txt_toID;
    }

    public void setTxt_toID(String txt_toID) {
        this.txt_toID = txt_toID;
    }

    public String getTxt_statusID() {
        return txt_statusID;
    }

    public void setTxt_statusID(String txt_statusID) {
        this.txt_statusID = txt_statusID;
    }
}
