package com.NaTicket.n.holidays.pojo;

import java.io.Serializable;

/**
 * Created by admin on 10-Jul-17.
 */

public class FareDetailsDTO implements Serializable{

    private String FromCity;

    private String AdultFare;

    private String ChildFare;

    private String RoomCategory;

    private String NetAmount;

    public String getFromCity() {
        return FromCity;
    }

    public void setFromCity(String fromCity) {
        FromCity = fromCity;
    }

    public String getAdultFare() {
        return AdultFare;
    }

    public void setAdultFare(String adultFare) {
        AdultFare = adultFare;
    }

    public String getChildFare() {
        return ChildFare;
    }

    public void setChildFare(String childFare) {
        ChildFare = childFare;
    }

    public String getRoomCategory() {
        return RoomCategory;
    }

    public void setRoomCategory(String roomCategory) {
        RoomCategory = roomCategory;
    }

    public String getNetAmount() {
        return NetAmount;
    }

    public void setNetAmount(String netAmount) {
        NetAmount = netAmount;
    }
}
