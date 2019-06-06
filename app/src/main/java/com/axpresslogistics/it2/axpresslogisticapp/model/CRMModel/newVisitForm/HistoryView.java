package com.axpresslogistics.it2.axpresslogisticapp.model.CRMModel.newVisitForm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HistoryView {

    @SerializedName("ref_no")
    @Expose
    private String ref_no;

    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("visit_date")
    @Expose
    private String visitDate;
    @SerializedName("visit_for")
    @Expose
    private String visitFor;
    @SerializedName("visit_type")
    @Expose
    private String visitType;

    public String getRef_no() {
        return ref_no;
    }

    public void setRef_no(String ref_no) {
        this.ref_no = ref_no;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

}