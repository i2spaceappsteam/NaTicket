package com.NaTicket.n.holidays.pojo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by admin on 10-Jul-17.
 */

public class AvailableHolidayPackagesDTO implements Serializable {


    private String Provider;

    private int HolidayPackageId;

    private String Name;

    private String Highlight;

    private String Destination;

    private String Category;

    private String SubCategory;

    private int Duration;

    private String Description;

    private String ValidFrom;

    private String ValidTo;

    private int HotelRating;

    private String Itinerary;

    private String Inclusions;

    private String Exclusions;

    private String Terms;

    private String CancellationPolicy;

    private String ChildPolicy;

    private int ServiceCharge;

    private String AdditionalInfo;

    private String MapUrl;

    private String VideoUrl;

    private String PackageType;

    private ArrayList<FareDetailsDTO> FareDetails;

    private ArrayList<HolidayPackageImagesDTO> HolidayPackageImages;

    private String TotalAmount;

    private int TotalNetAmount;

    private double ConvenienceFee;

    private int ConvenienceFeeType;

    public int getConvenienceFeeType() {
        return ConvenienceFeeType;
    }

    public void setConvenienceFeeType(int convenienceFeeType) {
        ConvenienceFeeType = convenienceFeeType;
    }

    private String AffiliateId;

    public String getProvider() {
        return Provider;
    }

    public void setProvider(String provider) {
        Provider = provider;
    }

    public int getHolidayPackageId() {
        return HolidayPackageId;
    }

    public void setHolidayPackageId(int holidayPackageId) {
        HolidayPackageId = holidayPackageId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getHighlight() {
        return Highlight;
    }

    public void setHighlight(String highlight) {
        Highlight = highlight;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getSubCategory() {
        return SubCategory;
    }

    public void setSubCategory(String subCategory) {
        SubCategory = subCategory;
    }

    public int getDuration() {
        return Duration;
    }

    public void setDuration(int duration) {
        Duration = duration;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getValidFrom() {
        return ValidFrom;
    }

    public void setValidFrom(String validFrom) {
        ValidFrom = validFrom;
    }

    public String getValidTo() {
        return ValidTo;
    }

    public void setValidTo(String validTo) {
        ValidTo = validTo;
    }

    public int getHotelRating() {
        return HotelRating;
    }

    public void setHotelRating(int hotelRating) {
        HotelRating = hotelRating;
    }

    public String getItinerary() {
        return Itinerary;
    }

    public void setItinerary(String itinerary) {
        Itinerary = itinerary;
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

    public String getTerms() {
        return Terms;
    }

    public void setTerms(String terms) {
        Terms = terms;
    }

    public String getCancellationPolicy() {
        return CancellationPolicy;
    }

    public void setCancellationPolicy(String cancellationPolicy) {
        CancellationPolicy = cancellationPolicy;
    }

    public String getChildPolicy() {
        return ChildPolicy;
    }

    public void setChildPolicy(String childPolicy) {
        ChildPolicy = childPolicy;
    }

    public int getServiceCharge() {
        return ServiceCharge;
    }

    public void setServiceCharge(int serviceCharge) {
        ServiceCharge = serviceCharge;
    }

    public String getAdditionalInfo() {
        return AdditionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        AdditionalInfo = additionalInfo;
    }

    public String getMapUrl() {
        return MapUrl;
    }

    public void setMapUrl(String mapUrl) {
        MapUrl = mapUrl;
    }

    public String getVideoUrl() {
        return VideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        VideoUrl = videoUrl;
    }

    public String getPackageType() {
        return PackageType;
    }

    public void setPackageType(String packageType) {
        PackageType = packageType;
    }

    public ArrayList<FareDetailsDTO> getFareDetails() {
        return FareDetails;
    }

    public void setFareDetails(ArrayList<FareDetailsDTO> fareDetails) {
        FareDetails = fareDetails;
    }

    public ArrayList<HolidayPackageImagesDTO> getHolidayPackageImages() {
        return HolidayPackageImages;
    }

    public void setHolidayPackageImages(ArrayList<HolidayPackageImagesDTO> holidayPackageImages) {
        HolidayPackageImages = holidayPackageImages;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public int getTotalNetAmount() {
        return TotalNetAmount;
    }

    public void setTotalNetAmount(int totalNetAmount) {
        TotalNetAmount = totalNetAmount;
    }

    public double getConvenienceFee() {
        return ConvenienceFee;
    }

    public void setConvenienceFee(double convenienceFee) {
        ConvenienceFee = convenienceFee;
    }

    public String getAffiliateId() {
        return AffiliateId;
    }

    public void setAffiliateId(String affiliateId) {
        AffiliateId = affiliateId;
    }
}
