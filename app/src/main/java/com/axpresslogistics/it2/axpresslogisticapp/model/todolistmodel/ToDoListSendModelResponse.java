package com.axpresslogistics.it2.axpresslogisticapp.model.todolistmodel;

import com.google.gson.annotations.SerializedName;

public class ToDoListSendModelResponse {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmplid() {
        return emplid;
    }

    public void setEmplid(String emplid) {
        this.emplid = emplid;
    }

    @SerializedName("method")
    private String method;

    @SerializedName("key")
    private String key;
    @SerializedName("status")
    private String status;

    @SerializedName("id")
    private String id;

    @SerializedName("emplid")
    private String emplid;


}
