package com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.visitform;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchVisitFormResponse {
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("ref_no")
    @Expose
    private Object refNo;
    @SerializedName("search")
    @Expose
    private List<SearchVisitFormList> search = null;
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

    public Object getRefNo() {
        return refNo;
    }

    public void setRefNo(Object refNo) {
        this.refNo = refNo;
    }

    public List<SearchVisitFormList> getSearch() {
        return search;
    }

    public void setSearch(List<SearchVisitFormList> search) {
        this.search = search;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
