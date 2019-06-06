package com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.businesscard.FetchBusinessCardResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FetchBusinessCardResponse {
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("business_card")
    @Expose
    private FetchBusinessCard businessCard;

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

    public FetchBusinessCard getBusinessCard() {
        return businessCard;
    }

    public void setBusinessCard(FetchBusinessCard businessCard) {
        this.businessCard = businessCard;
    }
}
