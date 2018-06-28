package com.NaTicket.n.flights.pojo;

import java.io.Serializable;

/**
 * Created by Nagarjuna on 12/30/2017.
 */

public class SelectedFlightDetailsDTO implements Serializable {

    private String From_City_Name;

    private String To_City_Name;

    private String Flight_Type;

    private String Trip_Type;

    private String Onward_date;

    private String Return_date;

    private int adult_count;

    private int child_count;

    private int infant_count;

    private String class_type;

    private String class_type_value;

    private String From_City_ID;

    private String To_City_ID;

    private String From_City_Full_Name;

    private String To_City_Full_Name;

    public String getFrom_City_Full_Name() {
        return From_City_Full_Name;
    }

    public void setFrom_City_Full_Name(String from_City_Full_Name) {
        From_City_Full_Name = from_City_Full_Name;
    }

    public String getTo_City_Full_Name() {
        return To_City_Full_Name;
    }

    public void setTo_City_Full_Name(String to_City_Full_Name) {
        To_City_Full_Name = to_City_Full_Name;
    }

    public String getFrom_City_ID() {
        return From_City_ID;
    }

    public void setFrom_City_ID(String From_City_ID) {
        this.From_City_ID = From_City_ID;
    }


    public String getTo_City_ID() {
        return To_City_ID;
    }

    public void setTo_City_ID(String To_City_ID) {
        this.To_City_ID = To_City_ID;
    }

    public String getFrom_City_Name() {
        return From_City_Name;
    }

    public void setFrom_City_Name(String from_City_Name) {
        this.From_City_Name = from_City_Name;
    }


    public String getTo_City_Name() {
        return To_City_Name;
    }

    public void setTo_City_Name(String to_City_Name) {
        this.To_City_Name = to_City_Name;
    }

    public String getFlight_Type() {
        return Flight_Type;
    }

    public void setFlight_Type(String flight_Type) {
        this.Flight_Type = flight_Type;
    }

    public String getTrip_Type() {
        return Trip_Type;
    }

    public void setTrip_Type(String trip_type) {
        this.Trip_Type = trip_type;
    }


    public String getOnward_date() {
        return Onward_date;
    }

    public void setOnward_date(String onward_date) {
        this.Onward_date = onward_date;
    }

    public String getReturn_date() {
        return Return_date;
    }

    public void setReturn_date(String return_date) {
        this.Return_date = return_date;
    }

    public int getadult_count() {
        return adult_count;
    }

    public void setadult_count(int Adult_count) {
        this.adult_count= Adult_count;
    }

    public int getchild_count() {
        return child_count;
    }

    public void setchild_count(int Child_count) {
        this.child_count= Child_count;
    }

    public int getinfant_count() {
        return infant_count;
    }

    public void setinfant_count(int Infant_count) {
        this.infant_count= Infant_count;
    }

    public String getclass_type() {
        return class_type;
    }

    public void setclass_type(String class_type) {
        this.class_type = class_type;
    }

    public String getClass_type_value() {
        return class_type_value;
    }

    public void setClass_type_value(String class_type_value) {
        this.class_type_value = class_type_value;
    }



}
