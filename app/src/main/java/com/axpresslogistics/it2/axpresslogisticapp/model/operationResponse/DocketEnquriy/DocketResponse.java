package com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.DocketEnquriy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DocketResponse {
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("docket_no")
    @Expose
    private String docketNo;
    @SerializedName("consignor_name")
    @Expose
    private String consignorName;
    @SerializedName("consignee_name")
    @Expose
    private String consigneeName;
    @SerializedName("docket_date")
    @Expose
    private String docketDate;
    @SerializedName("docket_from")
    @Expose
    private String docketFrom;
    @SerializedName("docket_to")
    @Expose
    private String docketTo;
    @SerializedName("docket_status")
    @Expose
    private String docketStatus;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("db_name")
    @Expose
    private String dbName;
    @SerializedName("Challan")
    @Expose
    private List<Challan> challan = null;

    public String getConsignorName() {
        return consignorName;
    }

    public List<DR> getdRS() {
        return dRS;
    }

    public void setdRS(List<DR> dRS) {
        this.dRS = dRS;
    }

    public void setConsignorName(String consignorName) {
        this.consignorName = consignorName;
    }

    @SerializedName("DRS")
    @Expose


    private List<DR> dRS = null;

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

    public String getDocketNo() {
        return docketNo;
    }

    public void setDocketNo(String docketNo) {
        this.docketNo = docketNo;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getDocketDate() {
        return docketDate;
    }

    public void setDocketDate(String docketDate) {
        this.docketDate = docketDate;
    }

    public String getDocketFrom() {
        return docketFrom;
    }

    public void setDocketFrom(String docketFrom) {
        this.docketFrom = docketFrom;
    }

    public String getDocketTo() {
        return docketTo;
    }

    public void setDocketTo(String docketTo) {
        this.docketTo = docketTo;
    }

    public String getDocketStatus() {
        return docketStatus;
    }

    public void setDocketStatus(String docketStatus) {
        this.docketStatus = docketStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public List<Challan> getChallan() {
        return challan;
    }

    public void setChallan(List<Challan> challan) {
        this.challan = challan;
    }

    public List<DR> getDRS() {
        return dRS;
    }

    public void setDRS(List<DR> dRS) {
        this.dRS = dRS;
    }
}
