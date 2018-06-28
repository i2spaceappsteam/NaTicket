package com.NaTicket.n.flights.pojo;

import java.io.Serializable;

/**
 * Created by Nagarjuna on 12/30/2017.
 */

public class Flight_City_DTO implements Serializable {

    private String AirportCode;

    private String City;

    private String Country;

    private String AirportDesc;

    private String Type;


    public String getAirportCode() {
        return AirportCode;
    }

    public void setAirportCode(String airportCode) {
        AirportCode = airportCode;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getAirportDesc() {
        return AirportDesc;
    }

    public void setAirportDesc(String airportDesc) {
        AirportDesc = airportDesc;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

}
