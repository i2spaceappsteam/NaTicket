package com.NaTicket.n.hotels.pojo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by admin on 18-Jun-17.
 */

public class RoomDetailsDTO implements Serializable {

    private int RoomIndex;

    private String RateType;

    private String Hotelpackage;

    private String RoomType;

    private String RoomBasis;

    private String RoomTypeCode;

    private String RatePlanCode;

    private String Validdays;

    private String WsKey;

    private int ExtGuestTotal;

    private String RoomTotal;

    private String RoomNetTotal;

    private String RoomTotalZAR;

    private String RoomNetTotalZAR;

    private String ServicetaxTotal;

    private String ServicetaxTotalZAR;

    private int Discount;

    private int Commission;

    private String expediaPropertyId;

    private String RoomCancellationPolicy;

    private int NoOfPax;

    private String RefundRule;

    private String Inclusions;

    private String Exclusions;

    private int RoomCount;

    private int MaxAdultOccupancy;

    private int MaxChildOccupancy;

    private String BedType;

    private String RoomView;

    private String RoomDescription;

    private String RoomAmenities;

    private String IncExcCondition;

    private String ExtGuestTotalZAR;

    private boolean IsInclusion;

   /*private String Images;*/

    private ArrayList<ImagesDTO> Images;

    private boolean IsAvailable;

    public int getRoomIndex() {
        return RoomIndex;
    }

    public void setRoomIndex(int roomIndex) {
        RoomIndex = roomIndex;
    }

    public String getRateType() {
        return RateType;
    }

    public void setRateType(String rateType) {
        RateType = rateType;
    }

    public String getHotelpackage() {
        return Hotelpackage;
    }

    public void setHotelpackage(String hotelpackage) {
        Hotelpackage = hotelpackage;
    }

    public String getRoomType() {
        return RoomType;
    }

    public void setRoomType(String roomType) {
        RoomType = roomType;
    }

    public String getRoomBasis() {
        return RoomBasis;
    }

    public void setRoomBasis(String roomBasis) {
        RoomBasis = roomBasis;
    }

    public String getRoomTypeCode() {
        return RoomTypeCode;
    }

    public void setRoomTypeCode(String roomTypeCode) {
        RoomTypeCode = roomTypeCode;
    }

    public String getRatePlanCode() {
        return RatePlanCode;
    }

    public void setRatePlanCode(String ratePlanCode) {
        RatePlanCode = ratePlanCode;
    }

    public String getValiddays() {
        return Validdays;
    }

    public void setValiddays(String validdays) {
        Validdays = validdays;
    }

    public String getWsKey() {
        return WsKey;
    }

    public void setWsKey(String wsKey) {
        WsKey = wsKey;
    }

    public int getExtGuestTotal() {
        return ExtGuestTotal;
    }

    public void setExtGuestTotal(int extGuestTotal) {
        ExtGuestTotal = extGuestTotal;
    }

    public String getRoomTotal() {
        return RoomTotal;
    }

    public void setRoomTotal(String roomTotal) {
        RoomTotal = roomTotal;
    }



    public String getRoomNetTotal() {
        return RoomNetTotal;
    }

    public void setRoomNetTotal(String roomNetTotal) {
        RoomNetTotal = roomNetTotal;
    }

    public String getServicetaxTotal() {
        return ServicetaxTotal;
    }

    public void setServicetaxTotal(String servicetaxTotal) {
        ServicetaxTotal = servicetaxTotal;
    }

    public int getDiscount() {
        return Discount;
    }

    public void setDiscount(int discount) {
        Discount = discount;
    }

    public int getCommission() {
        return Commission;
    }

    public void setCommission(int commission) {
        Commission = commission;
    }

    public String getExpediaPropertyId() {
        return expediaPropertyId;
    }

    public void setExpediaPropertyId(String expediaPropertyId) {
        this.expediaPropertyId = expediaPropertyId;
    }

    public String getRoomCancellationPolicy() {
        return RoomCancellationPolicy;
    }

    public void setRoomCancellationPolicy(String roomCancellationPolicy) {
        RoomCancellationPolicy = roomCancellationPolicy;
    }

    private String HotelPackage;


    public String getHotelPackage() {
        return HotelPackage;
    }

    public void setHotelPackage(String hotelPackage) {
        HotelPackage = hotelPackage;
    }

    public int getNoOfPax() {
        return NoOfPax;
    }

    public void setNoOfPax(int noOfPax) {
        NoOfPax = noOfPax;
    }

    public String getRefundRule() {
        return RefundRule;
    }

    public void setRefundRule(String refundRule) {
        RefundRule = refundRule;
    }

    public String getInclusions() {
        return Inclusions;
    }

    public void setInclusions(String inclusions) {
        Inclusions = inclusions;
    }

    public String getExclusions() {
        return Exclusions;
    }

    public void setExclusions(String exclusions) {
        Exclusions = exclusions;
    }

    public int getRoomCount() {
        return RoomCount;
    }

    public void setRoomCount(int roomCount) {
        RoomCount = roomCount;
    }

    public int getMaxAdultOccupancy() {
        return MaxAdultOccupancy;
    }

    public void setMaxAdultOccupancy(int maxAdultOccupancy) {
        MaxAdultOccupancy = maxAdultOccupancy;
    }

    public int getMaxChildOccupancy() {
        return MaxChildOccupancy;
    }

    public void setMaxChildOccupancy(int maxChildOccupancy) {
        MaxChildOccupancy = maxChildOccupancy;
    }

    public String getBedType() {
        return BedType;
    }

    public void setBedType(String bedType) {
        BedType = bedType;
    }

    public String getRoomView() {
        return RoomView;
    }

    public void setRoomView(String roomView) {
        RoomView = roomView;
    }

    public String getRoomDescription() {
        return RoomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        RoomDescription = roomDescription;
    }

    public String getRoomAmenities() {
        return RoomAmenities;
    }

    public void setRoomAmenities(String roomAmenities) {
        RoomAmenities = roomAmenities;
    }

    public String getIncExcCondition() {
        return IncExcCondition;
    }

    public void setIncExcCondition(String incExcCondition) {
        IncExcCondition = incExcCondition;
    }

    public boolean isInclusion() {
        return IsInclusion;
    }

    public void setInclusion(boolean inclusion) {
        IsInclusion = inclusion;
    }

/*    public String getImages() {
        return Images;
    }

    public void setImages(String images) {
        Images = images;
    }*/

    public ArrayList<ImagesDTO> getHotelImages() {
        return Images;
    }

    public void setHotelImages(ArrayList<ImagesDTO> images) {
        Images = images;
    }



    public boolean isAvailable() {
        return IsAvailable;
    }

    public void setAvailable(boolean available) {
        IsAvailable = available;
    }
}
