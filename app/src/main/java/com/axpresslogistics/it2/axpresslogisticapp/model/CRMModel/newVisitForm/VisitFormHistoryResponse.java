package com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.newVisitForm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VisitFormHistoryResponse {

    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("ref_no")
    @Expose
    private String refNo;
    @SerializedName("History")
    @Expose
    private Object history;
    @SerializedName("History_View")
    @Expose
    private List<HistoryView> historyView = null;
    @SerializedName("status")
    @Expose
    private String status;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public Object getHistory() {
        return history;
    }

    public void setHistory(Object history) {
        this.history = history;
    }

    public List<HistoryView> getHistoryView() {
        return historyView;
    }

    public void setHistoryView(List<HistoryView> historyView) {
        this.historyView = historyView;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}