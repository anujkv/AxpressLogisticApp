package com.axpresslogistics.it2.axpresslogisticapp.model.BranchList.Response;

import com.google.gson.annotations.SerializedName;

public class BranchCodeName {

    @SerializedName("branch_code")
    String branch_code;

    @SerializedName("branch_name")
    String branch_name;

    public String getBranch_code() {
        return branch_code;
    }

    public void setBranch_code(String branch_code) {
        this.branch_code = branch_code;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }
}
