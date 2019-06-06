package com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.VendorApproval.VendorList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VendorViewListResponse {

    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("status")
    private String status;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("db_name")
    @Expose
    private String db_name;
    @SerializedName("savedrequestno")
    @Expose
    private List<VendorList> vendorList = null;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDb_name() {
        return db_name;
    }

    public void setDb_name(String db_name) {
        this.db_name = db_name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<VendorList> getVendorList() {
        return vendorList;
    }

    public void setVendorList(List<VendorList> vendorList) {
        this.vendorList = vendorList;
    }

}
