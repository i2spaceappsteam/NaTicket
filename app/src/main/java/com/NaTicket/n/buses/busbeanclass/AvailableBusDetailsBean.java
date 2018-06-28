package com.NaTicket.n.buses.busbeanclass;

import org.json.JSONArray;

/**
 * Created by Administrator on 28-04-2016.
 */
public class AvailableBusDetailsBean implements Comparable<AvailableBusDetailsBean> {


    public  String AffiliateId;
    public  String NetFares;
    public  String Id;
    public  String PartialCancellationAllowed;
    public  String SeatType;
    public  String SourceId;
    public  String Travels;
    public  String Mticket;
    public  String InventoryType;
    public  String Journeydate;
    public  String ProvidersCount;
    public  String ResponseStatus;
    public  String Message;

    public  String Provider;
    public String ObiboAPIProvider;
    public JSONArray BoardingTimes;
    public JSONArray DropingTimes;
    public JSONArray Amenities;
    public JSONArray setBusImages;

    public  String ConvenienceFee;



    public  String ConvenienceFeeType;



    public  String ArrivalTime;
    public  String BoardingAddress;
    public  String BoardingContactNumbers;
    public  String BoardingContactPersons;
    public  String BoardingPointId;
    public  String BoardingLandmark;
    public  String BoardingLocation;
    public  String BoardingName;
    public  String BoardingTime;
    public  String DisplayName;
    public  String HotelName;
    public  String BusType;
    public  String CancellationPolicy;
    public  String DepartureTime;
    public  String DestinationId;
    public  String Duration;

    public  String AvailableSeats;

    public  String DroppingAddress;
    public  String DroppingContactNumbers;
    public  String DroppingContactPersons;
    public  String DroppingPointId;
    public  String DroppingLandmark;
    public  String DroppingLocation;
    public  String DroppingName;
    public  String DroppingTime;

    public  String Fares;
    public String ServiceTax;
    public String OperatorServiceCharge;
    public int CompareFare;

    private boolean selected;




    public String getAvailableSeats() {
        return AvailableSeats;
    }

    public void setAvailableSeats(String availableseats) {
        AvailableSeats = availableseats;
    }

    public String getArrivalTime() {
        return ArrivalTime;
    }

    public void setArrivalTime(String arrivaltime) {
        ArrivalTime = arrivaltime;
    }


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
    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public String getHotelName() {
        return HotelName;
    }

    public void setHotelName(String hotelName) {
        HotelName = hotelName;
    }

    public String getBusType() {
        return BusType;
    }

    public void setBusType(String busType) {
        BusType = busType;
    }

    public String getCancellationPolicy() {
        return CancellationPolicy;
    }

    public void setCancellationPolicy(String cancellationPolicy) {
        CancellationPolicy = cancellationPolicy;
    }

    public String getDepartureTime() {
        return DepartureTime;
    }

    public void setDepartureTime(String departureTime) {
        DepartureTime = departureTime;
    }

    public String getDestinationId() {
        return DestinationId;
    }

    public void setDestinationId(String destinationId) {
        DestinationId = destinationId;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }


    public String getDroppingAddress() {
        return DroppingAddress;
    }

    public void setDroppingAddress(String droppingAddress) {
        DroppingAddress = droppingAddress;
    }

    public String getDroppingContactNumbers() {
        return DroppingContactNumbers;
    }

    public void setDroppingContactNumbers(String droppingContactNumbers) {
        DroppingContactNumbers = droppingContactNumbers;
    }

    public String getDroppingContactPersons() {
        return DroppingContactPersons;
    }

    public void setDroppingContactPersons(String droppingContactPersons) {
        DroppingContactPersons = droppingContactPersons;
    }

    public String getDroppingPointId() {
        return DroppingPointId;
    }

    public void setDroppingPointId(String droppingPointId) {
        DroppingPointId = droppingPointId;
    }

    public String getDroppingLandmark() {
        return DroppingLandmark;
    }

    public void setDroppingLandmark(String droppingLandmark) {
        DroppingLandmark = droppingLandmark;
    }

    public String getDroppingLocation() {
        return DroppingLocation;
    }

    public void setDroppingLocation(String droppingLocation) {
        DroppingLocation = droppingLocation;
    }

    public String getDroppingName() {
        return DroppingName;
    }

    public void setDroppingName(String droppingName) {
        DroppingName = droppingName;
    }

    public String getDroppingTime() {
        return DroppingTime;
    }

    public void setDroppingTime(String droppingTime) {
        DroppingTime = droppingTime;
    }

    public String getFares() {
        return Fares;
    }

    public void setFares(String fares) {
        Fares = fares;
    }

    public String getServiceTax() {
        return ServiceTax;
    }

    public void setServiceTax(String serviceTax) {
        ServiceTax = serviceTax;
    }



    public String getOperatorServiceCharge()
    {
        return OperatorServiceCharge;
    }

    public void setOperatorServiceCharge(String operatorServiceCharge)
    {
        OperatorServiceCharge = operatorServiceCharge;
    }

    public String getConvenienceFeeType() {
        return ConvenienceFeeType;
    }

    public void setConvenienceFeeType(String convenienceFeeType) {
        ConvenienceFeeType = convenienceFeeType;
    }

    public String getConvenienceFee() {
        return ConvenienceFee;
    }

    public void setConvenienceFee(String convenienceFee) {
        ConvenienceFee = convenienceFee;
    }

    public String getAffiliateId() {
        return AffiliateId;
    }

    public void setAffiliateId(String affiliateId) {
        AffiliateId = affiliateId;
    }

    public String getNetFares() {
        return NetFares;
    }

    public void setNetFares(String netFares) {
        this.NetFares = netFares;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getProvider() {
        return Provider;
    }

    public void setProvider(String provider) {
        Provider = provider;
    }

    public String getObiboAPIProvider() {
        return ObiboAPIProvider;
    }

    public void setObiboAPIProvider(String obiboAPIProvider) {
        ObiboAPIProvider = obiboAPIProvider;
    }

    public String getPartialCancellationAllowed() {
        return PartialCancellationAllowed;
    }

    public void setPartialCancellationAllowed(String partialCancellationAllowed) {
        PartialCancellationAllowed = partialCancellationAllowed;
    }

    public String getSeatType() {
        return SeatType;
    }

    public void setSeatType(String seatType) {
        SeatType = seatType;
    }

    public String getSourceId() {
        return SourceId;
    }

    public void setSourceId(String sourceId) {
        SourceId = sourceId;
    }

    public String getTravels() {
        return Travels;
    }

    public void setTravels(String travels) {
        Travels = travels;
    }

    public String getMticket() {
        return Mticket;
    }

    public void setMticket(String mticket) {
        this.Mticket = mticket;
    }

    public String getInventoryType() {
        return InventoryType;
    }

    public void setInventoryType(String inventoryType) {
        InventoryType = inventoryType;
    }

    public String getJourneydate() {
        return Journeydate;
    }

    public void setJourneydate(String journeydate) {
        Journeydate = journeydate;
    }

    public String getProvidersCount() {
        return ProvidersCount;
    }

    public void setProvidersCount(String providersCount) {
        ProvidersCount = providersCount;
    }

    public String getResponseStatus() {
        return ResponseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        ResponseStatus = responseStatus;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public JSONArray getBoardingTimes() {
        return BoardingTimes;
    }

    public void setBoardingTimes(JSONArray boardingTimes) {
        BoardingTimes = boardingTimes;
    }

    public JSONArray getAmenities() {
        return Amenities;
    }

    public void setAmenities(JSONArray amenities) {
        Amenities = amenities;
    }

    public JSONArray getDropingTimes() {
        return DropingTimes;
    }

    public void setDropingTimes(JSONArray dropingTimes) {
        DropingTimes = dropingTimes;
    }

    @Override
    public int compareTo(AvailableBusDetailsBean another) {
        return this.getProvider().compareTo(another.getProvider());
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    public JSONArray getBusImages() {
        return setBusImages;
    }

    public void setBusImages(JSONArray busImages) {
        setBusImages = busImages;
    }
}
