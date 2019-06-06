package com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.newVisitForm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VisitFormFetchResponse {
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("emplid")
    @Expose
    private String emplid;
    @SerializedName("ref_no")
    @Expose
    private String refNo;
    @SerializedName("customer")
    @Expose
    private String customer;
    @SerializedName("visit_date")
    @Expose
    private String visitDate;
    @SerializedName("visit_for")
    @Expose
    private String visitFor;
    @SerializedName("visit_type")
    @Expose
    private String visitType;
    @SerializedName("contact_person")
    @Expose
    private String contactPerson;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("email_id")
    @Expose
    private String emailId;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("product")
    @Expose
    private String product;
    @SerializedName("followup_status")
    @Expose
    private String followupStatus;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("scope")
    @Expose
    private String scope;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("other_employee_name")
    @Expose
    private String otherEmployeeName;

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

    public String getEmplid() {
        return emplid;
    }

    public void setEmplid(String emplid) {
        this.emplid = emplid;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getVisitFor() {
        return visitFor;
    }

    public void setVisitFor(String visitFor) {
        this.visitFor = visitFor;
    }

    public String getVisitType() {
        return visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getFollowupStatus() {
        return followupStatus;
    }

    public void setFollowupStatus(String followupStatus) {
        this.followupStatus = followupStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOtherEmployeeName() {
        return otherEmployeeName;
    }

    public void setOtherEmployeeName(String otherEmployeeName) {
        this.otherEmployeeName = otherEmployeeName;
    }
}
