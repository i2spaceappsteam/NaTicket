package com.NaTicket.n.hotels.pojo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by admin on 18-Jun-17.
 */

public class AvailableHotelsDTO implements Serializable{

    private String HotelId;

    private String HotelName;

    private String Description;

    private String HotelChain;

    private int StarRating;

    private int Rooms;

    private int MinRate;

    private String RPH;

    private String WebService;

    private String HotelAddress;

    private String PostalCode;

    private String City;

    private String LocationInfo;

    private String PhoneNumber;

    private String Fax;

    private String Email;

    private String Website;

    private String Checkintime;

    private String Checkouttime;

    private String CreditCards;

    private String HotelServices;

    private String RoomServices;

    private String Facilities;

    private String CountryCode;

    private String AirportCode;

    private String SupplierType;

    private String PropertyCategory;

    private String Provider;

    private String SecondaryProvider;

    private ArrayList<RoomDetailsDTO> RoomDetails;

    private ArrayList<HotelImagesDTO> HotelImages;

    private String HotelPolicy;

    private String ConvenienceFee;

    private String AffiliateId;

    private String RoomCombination;

    private String RoomChain;

    private String Latitude;

    private String Longitude;

    private String CheckInDate;

    private String CheckOutDate;

    private int Floors;

    private String Alias;

    private String Punchline;

    private String MapURL;

    private String VideoURL;

    private String PromoTitle;

    private String PromoDetail;

    private String Distances;

    private String AdditionalInfo;

    private String Awards;

    private String Events;

    private String OtherFees;

    private String Facebook;

    private String Twitter;

    private String Linkedin;

    private int SearchCityId;

    private String mRoomDetail;

    private int ConvenienceFeeType;

    public int getConvenienceFeeType() {
        return ConvenienceFeeType;
    }

    public void setConvenienceFeeType(int convenienceFeeType) {
        ConvenienceFeeType = convenienceFeeType;
    }

    public String getHotelId() {
        return HotelId;
    }

    public void setHotelId(String hotelId) {
        HotelId = hotelId;
    }

    public String getHotelName() {
        return HotelName;
    }

    public void setHotelName(String hotelName) {
        HotelName = hotelName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getHotelChain() {
        return HotelChain;
    }

    public void setHotelChain(String hotelChain) {
        HotelChain = hotelChain;
    }

    public int getStarRating() {
        return StarRating;
    }

    public void setStarRating(int starRating) {
        StarRating = starRating;
    }

    public int getRooms() {
        return Rooms;
    }

    public void setRooms(int rooms) {
        Rooms = rooms;
    }

    public int getMinRate() {
        return MinRate;
    }

    public void setMinRate(int minRate) {
        MinRate = minRate;
    }

    public String getRPH() {
        return RPH;
    }

    public void setRPH(String RPH) {
        this.RPH = RPH;
    }

    public String getWebService() {
        return WebService;
    }

    public void setWebService(String webService) {
        WebService = webService;
    }

    public String getHotelAddress() {
        return HotelAddress;
    }

    public void setHotelAddress(String hotelAddress) {
        HotelAddress = hotelAddress;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getLocationInfo() {
        return LocationInfo;
    }

    public void setLocationInfo(String locationInfo) {
        LocationInfo = locationInfo;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getFax() {
        return Fax;
    }

    public void setFax(String fax) {
        Fax = fax;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }

    public String getCheckintime() {
        return Checkintime;
    }

    public void setCheckintime(String checkintime) {
        Checkintime = checkintime;
    }

    public String getCheckouttime() {
        return Checkouttime;
    }

    public void setCheckouttime(String checkouttime) {
        Checkouttime = checkouttime;
    }

    public String getCreditCards() {
        return CreditCards;
    }

    public void setCreditCards(String creditCards) {
        CreditCards = creditCards;
    }

    public String getHotelServices() {
        return HotelServices;
    }

    public void setHotelServices(String hotelServices) {
        HotelServices = hotelServices;
    }

    public String getRoomServices() {
        return RoomServices;
    }

    public void setRoomServices(String roomServices) {
        RoomServices = roomServices;
    }

    public String getFacilities() {
        return Facilities;
    }

    public void setFacilities(String facilities) {
        Facilities = facilities;
    }

    public String getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }

    public String getAirportCode() {
        return AirportCode;
    }

    public void setAirportCode(String airportCode) {
        AirportCode = airportCode;
    }

    public String getSupplierType() {
        return SupplierType;
    }

    public void setSupplierType(String supplierType) {
        SupplierType = supplierType;
    }

    public String getPropertyCategory() {
        return PropertyCategory;
    }

    public void setPropertyCategory(String propertyCategory) {
        PropertyCategory = propertyCategory;
    }

    public String getProvider() {
        return Provider;
    }

    public void setProvider(String provider) {
        Provider = provider;
    }

    public String getSecondaryProvider() {
        return SecondaryProvider;
    }

    public void setSecondaryProvider(String secondaryProvider) {
        SecondaryProvider = secondaryProvider;
    }

    public ArrayList<RoomDetailsDTO> getRoomDetails() {
        return RoomDetails;
    }

    public void setRoomDetails(ArrayList<RoomDetailsDTO> roomDetails) {
        RoomDetails = roomDetails;
    }

    public ArrayList<HotelImagesDTO> getHotelImages() {
        return HotelImages;
    }

    public void setHotelImages(ArrayList<HotelImagesDTO> hotelImages) {
        HotelImages = hotelImages;
    }

    public String getHotelPolicy() {
        return HotelPolicy;
    }

    public void setHotelPolicy(String hotelPolicy) {
        HotelPolicy = hotelPolicy;
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

    public String getRoomCombination() {
        return RoomCombination;
    }

    public void setRoomCombination(String roomCombination) {
        RoomCombination = roomCombination;
    }

    public String getRoomChain() {
        return RoomChain;
    }

    public void setRoomChain(String roomChain) {
        RoomChain = roomChain;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getCheckInDate() {
        return CheckInDate;
    }

    public void setCheckInDate(String checkInDate) {
        CheckInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return CheckOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        CheckOutDate = checkOutDate;
    }

    public int getFloors() {
        return Floors;
    }

    public void setFloors(int floors) {
        Floors = floors;
    }

    public String getAlias() {
        return Alias;
    }

    public void setAlias(String alias) {
        Alias = alias;
    }

    public String getPunchline() {
        return Punchline;
    }

    public void setPunchline(String punchline) {
        Punchline = punchline;
    }

    public String getMapURL() {
        return MapURL;
    }

    public void setMapURL(String mapURL) {
        MapURL = mapURL;
    }

    public String getVideoURL() {
        return VideoURL;
    }

    public void setVideoURL(String videoURL) {
        VideoURL = videoURL;
    }

    public String getPromoTitle() {
        return PromoTitle;
    }

    public void setPromoTitle(String promoTitle) {
        PromoTitle = promoTitle;
    }

    public String getPromoDetail() {
        return PromoDetail;
    }

    public void setPromoDetail(String promoDetail) {
        PromoDetail = promoDetail;
    }

    public String getDistances() {
        return Distances;
    }

    public void setDistances(String distances) {
        Distances = distances;
    }

    public String getAdditionalInfo() {
        return AdditionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        AdditionalInfo = additionalInfo;
    }

    public String getAwards() {
        return Awards;
    }

    public void setAwards(String awards) {
        Awards = awards;
    }

    public String getEvents() {
        return Events;
    }

    public void setEvents(String events) {
        Events = events;
    }

    public String getOtherFees() {
        return OtherFees;
    }

    public void setOtherFees(String otherFees) {
        OtherFees = otherFees;
    }

    public String getFacebook() {
        return Facebook;
    }

    public void setFacebook(String facebook) {
        Facebook = facebook;
    }

    public String getTwitter() {
        return Twitter;
    }

    public void setTwitter(String twitter) {
        Twitter = twitter;
    }

    public String getLinkedin() {
        return Linkedin;
    }

    public void setLinkedin(String linkedin) {
        Linkedin = linkedin;
    }

    public int getSearchCityId() {
        return SearchCityId;
    }

    public void setSearchCityId(int searchCityId) {
        SearchCityId = searchCityId;
    }

    public String getmRoomDetail(){return mRoomDetail;}

    public void setmRoomDetail(String roomDetail){this.mRoomDetail=roomDetail;}
}
