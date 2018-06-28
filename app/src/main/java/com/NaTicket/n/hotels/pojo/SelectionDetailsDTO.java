package com.NaTicket.n.hotels.pojo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by admin on 02-Jul-17.
 */

public class SelectionDetailsDTO implements Serializable{

    private int cityId;

    private String checkIn;

    private String checkOut;

    private int noofDays;

    private String selectedRoom;

    private String cityName;

    private String nationality;

    private String hoteltype;

    private String nationalityid;

    private ArrayList<String> selectedRooms;


    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public int getNoofDays() {
        return noofDays;
    }

    public void setNoofDays(int noofDays) {
        this.noofDays = noofDays;
    }

    public String getSelectedRoom() {
        return selectedRoom;
    }

    public void setSelectedRoom(String selectedRoom) {
        this.selectedRoom = selectedRoom;
    }

    public ArrayList<String> getSelectedRooms(){
        return selectedRooms;
    }

    public void setSelectedRooms(ArrayList<String> selectedRooms){
        this.selectedRooms=selectedRooms;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNationalityid() {
        return nationalityid;
    }

    public void setNationalityid(String nationalityid) {
        this.nationalityid = nationalityid;
    }

    public String getHoteltype(){
        return hoteltype;
    }

    public void setHoteltype(String hoteltype){
        this.hoteltype=hoteltype;
    }
}
