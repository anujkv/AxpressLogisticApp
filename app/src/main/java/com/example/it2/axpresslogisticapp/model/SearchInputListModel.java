package com.example.it2.axpresslogisticapp.model;

public class SearchInputListModel {
    String company_name,company_ref_no;

    public SearchInputListModel(String company_name, String company_ref_no) {
        this.company_name = company_name;
        this.company_ref_no = company_ref_no;
    }

    public SearchInputListModel() {
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_ref_no() {
        return company_ref_no;
    }

    public void setCompany_ref_no(String company_ref_no) {
        this.company_ref_no = company_ref_no;
    }
}
