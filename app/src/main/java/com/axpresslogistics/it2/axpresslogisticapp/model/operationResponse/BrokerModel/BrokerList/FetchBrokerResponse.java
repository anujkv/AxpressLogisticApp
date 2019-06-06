package com.axpresslogistics.it2.axpresslogisticapp.model.operationResponse.BrokerModel.BrokerList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FetchBrokerResponse {
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("branch")
    @Expose
    private String branch;
    @SerializedName("broker_code")
    @Expose
    private String brokerCode;
    @SerializedName("broker_name")
    @Expose
    private String brokerName;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("alternate_contact")
    @Expose
    private String alternateContact;
    @SerializedName("email_id")
    @Expose
    private String emailId;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("active_status")
    @Expose
    private String activeStatus;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("created_on")
    @Expose
    private String createdOn;
    @SerializedName("bank_account_no")
    @Expose
    private String bankAccountNo;
    @SerializedName("bank_name")
    @Expose
    private String bankName;
    @SerializedName("pan_no")
    @Expose
    private String panNo;
    @SerializedName("name_on_pancard")
    @Expose
    private String nameOnPancard;
    @SerializedName("bank_ifsc")
    @Expose
    private String bankIfsc;
    @SerializedName("status")
    @Expose
    private String status;

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

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBrokerCode() {
        return brokerCode;
    }

    public void setBrokerCode(String brokerCode) {
        this.brokerCode = brokerCode;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAlternateContact() {
        return alternateContact;
    }

    public void setAlternateContact(String alternateContact) {
        this.alternateContact = alternateContact;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getNameOnPancard() {
        return nameOnPancard;
    }

    public void setNameOnPancard(String nameOnPancard) {
        this.nameOnPancard = nameOnPancard;
    }

    public String getBankIfsc() {
        return bankIfsc;
    }

    public void setBankIfsc(String bankIfsc) {
        this.bankIfsc = bankIfsc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
