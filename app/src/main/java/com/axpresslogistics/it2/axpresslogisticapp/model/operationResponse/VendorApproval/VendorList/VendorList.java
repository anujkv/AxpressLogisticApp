package com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.VendorApproval.VendorList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VendorList {
    @SerializedName("request_no")
    @Expose
    private String requestNo;
    @SerializedName("vendor_code")
    @Expose
    private String vendorCode;
    @SerializedName("vendor_name")
    @Expose
    private String vendorName;
    @SerializedName("vehicle_no")
    @Expose
    private String vehicleCode;

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVehicleCode() {
        return vehicleCode;
    }

    public void setVehicleCode(String vehicleCode) {
        this.vehicleCode = vehicleCode;
    }

}
