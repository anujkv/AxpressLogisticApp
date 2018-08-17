package com.axpresslogistic.it2.axpresslogisticapp.model;

public class DocketTracking {

    String challan_no, challan_date, challan_from, challan_to, vehicle_no, challan_status;

    public DocketTracking() {
    }

    public DocketTracking(String challan_no, String challan_date, String challan_from, String challan_to, String vehicle_no, String challan_status) {
        this.challan_no = challan_no;
        this.challan_date = challan_date;
        this.challan_from = challan_from;
        this.challan_to = challan_to;
        this.vehicle_no = vehicle_no;
        this.challan_status = challan_status;
    }

    public String getChallan_no() {
        return challan_no;
    }

    public void setChallan_no(String challan_no) {
        this.challan_no = challan_no;
    }

    public String getChallan_date() {
        return challan_date;
    }

    public void setChallan_date(String challan_date) {
        this.challan_date = challan_date;
    }

    public String getChallan_from() {
        return challan_from;
    }

    public void setChallan_from(String challan_from) {
        this.challan_from = challan_from;
    }

    public String getChallan_to() {
        return challan_to;
    }

    public void setChallan_to(String challan_to) {
        this.challan_to = challan_to;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public void setVehicle_no(String vehicle_no) {
        this.vehicle_no = vehicle_no;
    }

    public String getChallan_status() {
        return challan_status;
    }

    public void setChallan_status(String challan_status) {
        this.challan_status = challan_status;
    }
}
