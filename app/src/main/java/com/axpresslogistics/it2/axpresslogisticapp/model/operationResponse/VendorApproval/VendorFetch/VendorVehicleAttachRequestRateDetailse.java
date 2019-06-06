package com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.VendorApproval.VendorFetch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VendorVehicleAttachRequestRateDetailse {



    @SerializedName("from_branch")
    @Expose
    private String fromBranch;
    @SerializedName("to_branch")
    @Expose
    private String toBranch;
    @SerializedName("valid_from")
    @Expose
    private String validFrom;
    @SerializedName("valid_upto")
    @Expose
    private String validUpto;
    @SerializedName("rate")
    @Expose
    private String rate;

    public String getFromBranch() {
        return fromBranch;
    }

    public void setFromBranch(String fromBranch) {
        this.fromBranch = fromBranch;
    }

    public String getToBranch() {
        return toBranch;
    }

    public void setToBranch(String toBranch) {
        this.toBranch = toBranch;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public String getValidUpto() {
        return validUpto;
    }

    public void setValidUpto(String validUpto) {
        this.validUpto = validUpto;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

}