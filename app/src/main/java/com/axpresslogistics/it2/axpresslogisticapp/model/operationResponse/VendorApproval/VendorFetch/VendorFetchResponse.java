package com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.VendorApproval.VendorFetch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VendorFetchResponse {
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("request_no")
    @Expose
    private String requestNo;
    @SerializedName("vendor_code")
    @Expose
    private String vendorCode;
    @SerializedName("pan_no")
    @Expose
    private String panNo;
    @SerializedName("vehicle_no")
    @Expose
    private String vehicleNo;
    @SerializedName("size_of_vehicle")
    @Expose
    private String sizeOfVehicle;
    @SerializedName("owner_name")
    @Expose
    private String ownerName;
    @SerializedName("vendor_name")
    @Expose
    private String vendorName;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("contact_no")
    @Expose
    private String contactNo;
    @SerializedName("vendor_bill_location")
    @Expose
    private String vendorBillLocation;
    @SerializedName("join_date")
    @Expose
    private String joinDate;
    @SerializedName("close_date")
    @Expose
    private String closeDate;
    @SerializedName("VendorVehicleAttachRequestRateDetailses")
    @Expose
    private List<VendorVehicleAttachRequestRateDetailse> vendorVehicleAttachRequestRateDetailses = null;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getSizeOfVehicle() {
        return sizeOfVehicle;
    }

    public void setSizeOfVehicle(String sizeOfVehicle) {
        this.sizeOfVehicle = sizeOfVehicle;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getVendorBillLocation() {
        return vendorBillLocation;
    }

    public void setVendorBillLocation(String vendorBillLocation) {
        this.vendorBillLocation = vendorBillLocation;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public List<VendorVehicleAttachRequestRateDetailse> getVendorVehicleAttachRequestRateDetailses() {
        return vendorVehicleAttachRequestRateDetailses;
    }

    public void setVendorVehicleAttachRequestRateDetailses(List<VendorVehicleAttachRequestRateDetailse> vendorVehicleAttachRequestRateDetailses) {
        this.vendorVehicleAttachRequestRateDetailses = vendorVehicleAttachRequestRateDetailses;
    }

}
