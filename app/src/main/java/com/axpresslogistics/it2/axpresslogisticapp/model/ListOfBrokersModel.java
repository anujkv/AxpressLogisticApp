package com.axpresslogistics.it2.axpresslogisticapp.model;

public class ListOfBrokersModel {
    String broker_code,broker_name,contact,address, account_no, bank_name, pan_no, name_of_pancard,
            ifsc_code;

    public ListOfBrokersModel() {
    }

    public ListOfBrokersModel(String broker_code, String broker_name, String contact, String address,
                              String account_no, String bank_name, String pan_no, String name_of_pancard,
                              String ifsc_code) {
        this.broker_code = broker_code;
        this.broker_name = broker_name;
        this.contact = contact;
        this.address = address;
        this.account_no = account_no;
        this.bank_name = bank_name;
        this.pan_no = pan_no;
        this.name_of_pancard = name_of_pancard;
        this.ifsc_code = ifsc_code;
    }

    public String getBroker_code() {
        return broker_code;
    }

    public String getBroker_name() {
        return broker_name;
    }

    public String getContact() {
        return contact;
    }

    public String getAddress() {
        return address;
    }

    public String getAccount_no() {
        return account_no;
    }

    public String getBank_name() {
        return bank_name;
    }

    public String getPan_no() {
        return pan_no;
    }

    public String getName_of_pancard() {
        return name_of_pancard;
    }

    public String getIfsc_code() {
        return ifsc_code;
    }
}
