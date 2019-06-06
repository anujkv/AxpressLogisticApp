package com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.visitform;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchVisitFormList {
    @SerializedName("ref_no")
    @Expose
    private String refNo;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("contact_person")
    @Expose
    private String contactPerson;
    @SerializedName("mobile")
    @Expose
    private String mobile;

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
