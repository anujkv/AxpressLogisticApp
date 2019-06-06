package com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.VendorApproval.VendorApproval;

import com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.VendorApproval.VendorFetch.VendorVehicleAttachRequestRateDetailse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VendorApprovalRequest {
    @SerializedName("emplid")
    @Expose
    private String emplid;
    @SerializedName("db_name")
    @Expose
    private String dbName;
    @SerializedName("request_no")
    @Expose
    private String requestNo;
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("approval_status")
    @Expose
    private String approvalStatus;
    @SerializedName("VendorVehicleAttachRequestRateDetailses")
    @Expose
    private List<VendorVehicleAttachRequestRateDetailse> vendorVehicleAttachRequestRateDetailses = null;

    public String getEmplid() {
        return emplid;
    }

    public void setEmplid(String emplid) {
        this.emplid = emplid;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public List<VendorVehicleAttachRequestRateDetailse> getVendorVehicleAttachRequestRateDetailses() {
        return vendorVehicleAttachRequestRateDetailses;
    }

    public void setVendorVehicleAttachRequestRateDetailses(List<VendorVehicleAttachRequestRateDetailse> vendorVehicleAttachRequestRateDetailses) {
        this.vendorVehicleAttachRequestRateDetailses = vendorVehicleAttachRequestRateDetailses;
    }

}
