package com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.DocketEnquriy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PodDocketResponse {
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("dwbno")
    @Expose
    private String dwbno;
    @SerializedName("image")
    @Expose
    private String image;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDwbno() {
        return dwbno;
    }

    public void setDwbno(String dwbno) {
        this.dwbno = dwbno;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}