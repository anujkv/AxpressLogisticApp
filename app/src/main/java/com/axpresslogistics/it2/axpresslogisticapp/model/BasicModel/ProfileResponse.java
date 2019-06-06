package com.axpresslogistics.it2.axpresslogisticapp.model.BasicModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileResponse {
    @SerializedName("employee_name")
    @Expose
    private String employeeName;
    @SerializedName("employee_contact")
    @Expose
    private String employeeContact;
    @SerializedName("employee_contact1")
    @Expose
    private String employeeContact1;
    @SerializedName("employee_email")
    @Expose
    private String employeeEmail;
    @SerializedName("employee_father_name")
    @Expose
    private String employeeFatherName;
    @SerializedName("employee_dob")
    @Expose
    private String employeeDob;
    @SerializedName("employee_birth_state")
    @Expose
    private String employeeBirthState;
    @SerializedName("employee_doj")
    @Expose
    private String employeeDoj;
    @SerializedName("employee_qualification")
    @Expose
    private String employeeQualification;
    @SerializedName("employee_panno")
    @Expose
    private String employeePanno;
    @SerializedName("employee_uanno")
    @Expose
    private String employeeUanno;
    @SerializedName("employee_adhaarno")
    @Expose
    private String employeeAdhaarno;
    @SerializedName("employee_esicno")
    @Expose
    private String employeeEsicno;
    @SerializedName("employee_department")
    @Expose
    private String employeeDepartment;
    @SerializedName("employee_designation")
    @Expose
    private String employeeDesignation;
    @SerializedName("employee_branch")
    @Expose
    private String employeeBranch;
    @SerializedName("employee_address")
    @Expose
    private String employeeAddress;
    @SerializedName("employee_bankname")
    @Expose
    private String employeeBankname;
    @SerializedName("employee_bankac")
    @Expose
    private String employeeBankac;
    @SerializedName("employee_ifsccode")
    @Expose
    private String employeeIfsccode;
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("emplid")
    @Expose
    private String emplid;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("address_status")
    @Expose
    private Object addressStatus;
    @SerializedName("address_type")
    @Expose
    private Object addressType;
    @SerializedName("bank_acc_status")
    @Expose
    private String bankAccStatus;
    @SerializedName("bank_name_status")
    @Expose
    private Object bankNameStatus;
    @SerializedName("ifsc_code_status")
    @Expose
    private Object ifscCodeStatus;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("imageURL")
    @Expose
    private Object imageURL;

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeContact() {
        return employeeContact;
    }

    public void setEmployeeContact(String employeeContact) {
        this.employeeContact = employeeContact;
    }

    public String getEmployeeContact1() {
        return employeeContact1;
    }

    public void setEmployeeContact1(String employeeContact1) {
        this.employeeContact1 = employeeContact1;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeeFatherName() {
        return employeeFatherName;
    }

    public void setEmployeeFatherName(String employeeFatherName) {
        this.employeeFatherName = employeeFatherName;
    }

    public String getEmployeeDob() {
        return employeeDob;
    }

    public void setEmployeeDob(String employeeDob) {
        this.employeeDob = employeeDob;
    }

    public String getEmployeeBirthState() {
        return employeeBirthState;
    }

    public void setEmployeeBirthState(String employeeBirthState) {
        this.employeeBirthState = employeeBirthState;
    }

    public String getEmployeeDoj() {
        return employeeDoj;
    }

    public void setEmployeeDoj(String employeeDoj) {
        this.employeeDoj = employeeDoj;
    }

    public String getEmployeeQualification() {
        return employeeQualification;
    }

    public void setEmployeeQualification(String employeeQualification) {
        this.employeeQualification = employeeQualification;
    }

    public String getEmployeePanno() {
        return employeePanno;
    }

    public void setEmployeePanno(String employeePanno) {
        this.employeePanno = employeePanno;
    }

    public String getEmployeeUanno() {
        return employeeUanno;
    }

    public void setEmployeeUanno(String employeeUanno) {
        this.employeeUanno = employeeUanno;
    }

    public String getEmployeeAdhaarno() {
        return employeeAdhaarno;
    }

    public void setEmployeeAdhaarno(String employeeAdhaarno) {
        this.employeeAdhaarno = employeeAdhaarno;
    }

    public String getEmployeeEsicno() {
        return employeeEsicno;
    }

    public void setEmployeeEsicno(String employeeEsicno) {
        this.employeeEsicno = employeeEsicno;
    }

    public String getEmployeeDepartment() {
        return employeeDepartment;
    }

    public void setEmployeeDepartment(String employeeDepartment) {
        this.employeeDepartment = employeeDepartment;
    }

    public String getEmployeeDesignation() {
        return employeeDesignation;
    }

    public void setEmployeeDesignation(String employeeDesignation) {
        this.employeeDesignation = employeeDesignation;
    }

    public String getEmployeeBranch() {
        return employeeBranch;
    }

    public void setEmployeeBranch(String employeeBranch) {
        this.employeeBranch = employeeBranch;
    }

    public String getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public String getEmployeeBankname() {
        return employeeBankname;
    }

    public void setEmployeeBankname(String employeeBankname) {
        this.employeeBankname = employeeBankname;
    }

    public String getEmployeeBankac() {
        return employeeBankac;
    }

    public void setEmployeeBankac(String employeeBankac) {
        this.employeeBankac = employeeBankac;
    }

    public String getEmployeeIfsccode() {
        return employeeIfsccode;
    }

    public void setEmployeeIfsccode(String employeeIfsccode) {
        this.employeeIfsccode = employeeIfsccode;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getAddressStatus() {
        return addressStatus;
    }

    public void setAddressStatus(Object addressStatus) {
        this.addressStatus = addressStatus;
    }

    public Object getAddressType() {
        return addressType;
    }

    public void setAddressType(Object addressType) {
        this.addressType = addressType;
    }

    public String getBankAccStatus() {
        return bankAccStatus;
    }

    public void setBankAccStatus(String bankAccStatus) {
        this.bankAccStatus = bankAccStatus;
    }

    public Object getBankNameStatus() {
        return bankNameStatus;
    }

    public void setBankNameStatus(Object bankNameStatus) {
        this.bankNameStatus = bankNameStatus;
    }

    public Object getIfscCodeStatus() {
        return ifscCodeStatus;
    }

    public void setIfscCodeStatus(Object ifscCodeStatus) {
        this.ifscCodeStatus = ifscCodeStatus;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Object getImageURL() {
        return imageURL;
    }

    public void setImageURL(Object imageURL) {
        this.imageURL = imageURL;
    }

}
