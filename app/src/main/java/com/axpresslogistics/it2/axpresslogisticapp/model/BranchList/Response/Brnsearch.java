package com.axpresslogistics.it2.axpresslogisticapp.model.BranchList.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Brnsearch {

    @SerializedName("branch_code")
    @Expose
    private String branchCode;
    @SerializedName("branch_name")
    @Expose
    private String branchName;

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

}