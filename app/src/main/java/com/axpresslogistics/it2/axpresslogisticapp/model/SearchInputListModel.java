package com.axpresslogistics.it2.axpresslogisticapp.model;

public class SearchInputListModel {
    String company_ref_no,company_name, contact_person_name, person_contact_no;

    public SearchInputListModel(String company_ref_no, String company_name, String contact_person_name, String person_contact_no) {
        this.company_ref_no = company_ref_no;
        this.company_name = company_name;
        this.contact_person_name = contact_person_name;
        this.person_contact_no = person_contact_no;
    }

    public SearchInputListModel() {
    }

    public String getCompany_ref_no() {
        return company_ref_no;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getContact_person_name() {
        return contact_person_name;
    }

    public String getPerson_contact_no() {
        return person_contact_no;
    }
}
