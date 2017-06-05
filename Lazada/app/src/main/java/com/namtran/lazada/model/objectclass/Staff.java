package com.namtran.lazada.model.objectclass;

/**
 * Created by namtr on 05/05/2017.
 */

public class Staff {
    private String username;
    private String password;
    private int staffCode;
    private int staffTypeCode;
    private int gender;
    private String staffName;
    private String address;
    private String dateOfBirth;
    private String phoneNumber;
    private String receiveNewsViaEmail;

    public int getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(int staffCode) {
        this.staffCode = staffCode;
    }

    public int getStaffTypeCode() {
        return staffTypeCode;
    }

    public void setStaffTypeCode(int staffTypeCode) {
        this.staffTypeCode = staffTypeCode;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getReceiveNewsViaEmail() {
        return receiveNewsViaEmail;
    }

    public void setReceiveNewsViaEmail(String receiveNewsViaEmail) {
        this.receiveNewsViaEmail = receiveNewsViaEmail;
    }
}
