package com.axpresslogistics.it2.axpresslogisticapp.model.HrmsModel.ApprovalLeave;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApprovalLeaveResponse {

    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("approve_leave")
    @Expose
    private List<ApprovaLeave> approvaLeave = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("supervisior_emplid")
    @Expose
    private String supervisiorEmplid;

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

    public List<ApprovaLeave> getApprovaLeave() {
        return approvaLeave;
    }

    public void setApprovaLeave(List<ApprovaLeave> approvaLeave) {
        this.approvaLeave = approvaLeave;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSupervisiorEmplid() {
        return supervisiorEmplid;
    }

    public void setSupervisiorEmplid(String supervisiorEmplid) {
        this.supervisiorEmplid = supervisiorEmplid;
    }

}