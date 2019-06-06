package com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.ChallanReceive;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChallanCloseResponse {
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("massage")
    @Expose
    private String massage;

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

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
}
