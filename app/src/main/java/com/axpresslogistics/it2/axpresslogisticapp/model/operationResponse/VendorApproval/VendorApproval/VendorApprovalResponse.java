package com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.VendorApproval.VendorApproval;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VendorApprovalResponse {
    @SerializedName("vendor_name")
    @Expose
    private String vendorName;
    @SerializedName("vehicle_no")
    @Expose
    private String vehicle_no;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("method")
    @Expose
    private String method;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public void setVehicle_no(String vehicle_no) {
        this.vehicle_no = vehicle_no;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
