package com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.DocketEnquriy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DR {
    @SerializedName("drs_no")
    @Expose
    private String drsNo;
    @SerializedName("drs_date")
    @Expose
    private String drsDate;
    @SerializedName("drs_received_date")
    @Expose
    private String drsReceivedDate;
    @SerializedName("drs_received_by")
    @Expose
    private String drsReceivedBy;
    @SerializedName("drs_status")
    @Expose
    private String drsStatus;

    public String getDrsNo() {
        return drsNo;
    }

    public void setDrsNo(String drsNo) {
        this.drsNo = drsNo;
    }

    public String getDrsDate() {
        return drsDate;
    }

    public void setDrsDate(String drsDate) {
        this.drsDate = drsDate;
    }

    public String getDrsReceivedDate() {
        return drsReceivedDate;
    }

    public void setDrsReceivedDate(String drsReceivedDate) {
        this.drsReceivedDate = drsReceivedDate;
    }

    public String getDrsReceivedBy() {
        return drsReceivedBy;
    }

    public void setDrsReceivedBy(String drsReceivedBy) {
        this.drsReceivedBy = drsReceivedBy;
    }

    public String getDrsStatus() {
        return drsStatus;
    }

    public void setDrsStatus(String drsStatus) {
        this.drsStatus = drsStatus;
    }
}
