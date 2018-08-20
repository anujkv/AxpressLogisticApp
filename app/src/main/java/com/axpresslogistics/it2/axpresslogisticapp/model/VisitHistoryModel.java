package com.axpresslogistics.it2.axpresslogisticapp.model;

public class VisitHistoryModel {
    String customer,visit_date,visit_for,visit_type,ref_no;

    public VisitHistoryModel() {
    }

    public VisitHistoryModel(String customer, String visit_date, String visit_for, String visit_type,
                             String ref_no) {
        this.customer = customer;
        this.visit_date = visit_date;
        this.visit_for = visit_for;
        this.visit_type = visit_type;
        this.ref_no = ref_no;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getVisit_date() {
        return visit_date;
    }

    public void setVisit_date(String visit_date) {
        this.visit_date = visit_date;
    }

    public String getVisit_for() {
        return visit_for;
    }

    public void setVisit_for(String visit_for) {
        this.visit_for = visit_for;
    }

    public String getVisit_type() {
        return visit_type;
    }

    public void setVisit_type(String visit_type) {
        this.visit_type = visit_type;
    }

    public String getRef_no() {
        return ref_no;
    }

    public void setRef_no(String ref_no) {
        this.ref_no = ref_no;
    }
}
