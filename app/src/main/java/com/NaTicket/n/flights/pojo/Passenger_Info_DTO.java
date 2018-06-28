package com.NaTicket.n.flights.pojo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Nagarjuna on 1/3/2018.
 */

public class Passenger_Info_DTO implements Serializable {


    private String Passenger_Names;

    private String Gender;

    private String DOB;

    private String PassportNumber;

    private String PassPort_Place;

    private String Passport_Issue_date;

    private String Passport_Expiry_Date;

    private String Ages;

    private String PassPort_Details;

    private ArrayList<String> PassengersArrayList;

    public ArrayList<String> getPassengersArrayList() {
        return PassengersArrayList;
    }

    public void setPassengersArrayList(ArrayList<String> passengersArrayList) {
        PassengersArrayList = passengersArrayList;
    }

    public String getPassPort_Details() {
        return PassPort_Details;
    }

    public void setPassPort_Details(String passPort_Details) {
        PassPort_Details = passPort_Details;
    }

    public String getPassenger_Names() {
        return Passenger_Names;
    }

    public void setPassenger_Names(String passenger_Names) {
        Passenger_Names = passenger_Names;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getPassportNumber() {
        return PassportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        PassportNumber = passportNumber;
    }

    public String getPassPort_Place() {
        return PassPort_Place;
    }

    public void setPassPort_Place(String passPort_Place) {
        PassPort_Place = passPort_Place;
    }

    public String getPassport_Issue_date() {
        return Passport_Issue_date;
    }

    public void setPassport_Issue_date(String passport_Issue_date) {
        Passport_Issue_date = passport_Issue_date;
    }

    public String getPassport_Expiry_Date() {
        return Passport_Expiry_Date;
    }

    public void setPassport_Expiry_Date(String passport_Expiry_Date) {
        Passport_Expiry_Date = passport_Expiry_Date;
    }

    public String getAges() {
        return Ages;
    }

    public void setAges(String ages) {
        Ages = ages;
    }
}
