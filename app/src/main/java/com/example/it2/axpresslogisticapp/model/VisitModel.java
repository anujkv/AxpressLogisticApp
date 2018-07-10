package com.example.it2.axpresslogisticapp.model;

public class VisitModel {

    String companyUniqueID,companyName, contactPersonName, personContactNo;

    public VisitModel(String companyUniqueID, String companyName, String contactPersonName, String personContactNo) {
        this.companyUniqueID = companyUniqueID;
        this.companyName = companyName;
        this.contactPersonName = contactPersonName;
        this.personContactNo = personContactNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyUniqueID() {
        return companyUniqueID;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public String getPersonContactNo() {
        return personContactNo;
    }
}
