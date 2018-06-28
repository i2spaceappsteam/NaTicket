package com.NaTicket.n.buses.busbeanclass;

/**
 * Created by Ankit on 7/11/2016.
 */
public class BoadingPoint {

    public  String BoardingAddress;
    public  String BoardingContactNumbers;
    public  String BoardingContactPersons;
    public  String BoardingPointId;
    public  String BoardingLandmark;
    public  String BoardingLocation;
    public  String BoardingName;
    public  String BoardingTime;

    private boolean selected;

    public String getBoardingAddress() {
        return BoardingAddress;
    }

    public void setBoardingAddress(String boardingAddress) {
        BoardingAddress = boardingAddress;
    }

    public String getBoardingContactNumbers() {
        return BoardingContactNumbers;
    }

    public void setBoardingContactNumbers(String boardingContactNumbers) {
        BoardingContactNumbers = boardingContactNumbers;
    }

    public String getBoardingContactPersons() {
        return BoardingContactPersons;
    }

    public void setBoardingContactPersons(String boardingContactPersons) {
        BoardingContactPersons = boardingContactPersons;
    }

    public String getBoardingPointId() {
        return BoardingPointId;
    }

    public void setBoardingPointId(String boardingPointId) {
        BoardingPointId = boardingPointId;
    }

    public String getBoardingLandmark() {
        return BoardingLandmark;
    }

    public void setBoardingLandmark(String boardingLandmark) {
        BoardingLandmark = boardingLandmark;
    }

    public String getBoardingLocation() {
        return BoardingLocation;
    }

    public void setBoardingLocation(String boardingLocation) {
        BoardingLocation = boardingLocation;
    }

    public String getBoardingName() {
        return BoardingName;
    }

    public void setBoardingName(String boardingName) {
        BoardingName = boardingName;
    }

    public String getBoardingTime() {
        return BoardingTime;
    }

    public void setBoardingTime(String boardingTime) {
        BoardingTime = boardingTime;
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
