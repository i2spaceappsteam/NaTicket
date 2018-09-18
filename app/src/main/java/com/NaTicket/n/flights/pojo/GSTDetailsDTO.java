package com.NaTicket.n.flights.pojo;

import java.io.Serializable;

/**
 * Created by Nagarjuna on 1/5/2018.
 */

public class GSTDetailsDTO implements Serializable {


    private String GSTCompanyAddress;
    private String GSTCompanyContactNumber;
    private String GSTCompanyName;
    private String GSTNumber;
    private String GSTCompanyEmail;

    public String getGSTCompanyAddress() {
        return GSTCompanyAddress;
    }

    public void setGSTCompanyAddress(String GSTCompanyAddress) {
        this.GSTCompanyAddress = GSTCompanyAddress;
    }

    public String getGSTCompanyContactNumber() {
        return GSTCompanyContactNumber;
    }

    public void setGSTCompanyContactNumber(String GSTCompanyContactNumber) {
        this.GSTCompanyContactNumber = GSTCompanyContactNumber;
    }

    public String getGSTCompanyName() {
        return GSTCompanyName;
    }

    public void setGSTCompanyName(String GSTCompanyName) {
        this.GSTCompanyName = GSTCompanyName;
    }

    public String getGSTNumber() {
        return GSTNumber;
    }

    public void setGSTNumber(String GSTNumber) {
        this.GSTNumber = GSTNumber;
    }

    public String getGSTCompanyEmail() {
        return GSTCompanyEmail;
    }

    public void setGSTCompanyEmail(String GSTCompanyEmail) {
        this.GSTCompanyEmail = GSTCompanyEmail;
    }
}
