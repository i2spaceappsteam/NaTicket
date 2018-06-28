package com.NaTicket.n.hotels.pojo;

import com.NaTicket.n.buses.pojo.Ecommerceproducts_Pojo;
import com.NaTicket.n.flights.pojo.GSTDetailsDTO;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ankit on 15-01-2018.
 */

public class Hotel_Tickets_DTO implements Serializable{

        private String DeviceModel;

        public String getDeviceModel() { return this.DeviceModel; }

        public void setDeviceModel(String DeviceModel) { this.DeviceModel = DeviceModel; }

        private String DeviceOS;

        public String getDeviceOS() { return this.DeviceOS; }

        public void setDeviceOS(String DeviceOS) { this.DeviceOS = DeviceOS; }

        private String DeviceOSVersion;

        public String getDeviceOSVersion() { return this.DeviceOSVersion; }

        public void setDeviceOSVersion(String DeviceOSVersion) { this.DeviceOSVersion = DeviceOSVersion; }

        private String DeviceToken;

        public String getDeviceToken() { return this.DeviceToken; }

        public void setDeviceToken(String DeviceToken) { this.DeviceToken = DeviceToken; }

        private int ApplicationType;

        public int getApplicationType() { return this.ApplicationType; }

        public void setApplicationType(int ApplicationType) { this.ApplicationType = ApplicationType; }

        private boolean IsAgentPaymentGateway;

        public boolean getIsAgentPaymentGateway() { return this.IsAgentPaymentGateway; }

        public void setIsAgentPaymentGateway(boolean IsAgentPaymentGateway) { this.IsAgentPaymentGateway = IsAgentPaymentGateway; }

        private boolean IsOfflineBooking;

        public boolean getIsOfflineBooking() { return this.IsOfflineBooking; }

        public void setIsOfflineBooking(boolean IsOfflineBooking) { this.IsOfflineBooking = IsOfflineBooking; }

        private String HotelId;

        public String getHotelId() { return this.HotelId; }

        public void setHotelId(String HotelId) { this.HotelId = HotelId; }

        private String Nationality;

        public String getNationality() { return this.Nationality; }

        public void setNationality(String Nationality) { this.Nationality = Nationality; }

        private String Fare;

        public String getFare() { return this.Fare; }

        public void setFare(String Fare) { this.Fare = Fare; }

        private String Titles;

        public String getTitles() { return this.Titles; }

        public void setTitles(String Titles) { this.Titles = Titles; }

        private String Names;

        public String getNames() { return this.Names; }

        public void setNames(String Names) { this.Names = Names; }

        private String Ages;

        public String getAges() { return this.Ages; }

        public void setAges(String Ages) { this.Ages = Ages; }

        private String Genders;

        public String getGenders() { return this.Genders; }

        public void setGenders(String Genders) { this.Genders = Genders; }

        private String Address;

        public String getAddress() { return this.Address; }

        public void setAddress(String Address) { this.Address = Address; }

        private String IdCardType;

        public String getIdCardType() { return this.IdCardType; }

        public void setIdCardType(String IdCardType) { this.IdCardType = IdCardType; }

        private String IdCardNo;

        public String getIdCardNo() { return this.IdCardNo; }

        public void setIdCardNo(String IdCardNo) { this.IdCardNo = IdCardNo; }

        private String IdCardIssuedBy;

        public String getIdCardIssuedBy() { return this.IdCardIssuedBy; }

        public void setIdCardIssuedBy(String IdCardIssuedBy) { this.IdCardIssuedBy = IdCardIssuedBy; }

        private String MobileNo;

        public String getMobileNo() { return this.MobileNo; }

        public void setMobileNo(String MobileNo) { this.MobileNo = MobileNo; }

        private String EmergencyMobileNo;

        public String getEmergencyMobileNo() { return this.EmergencyMobileNo; }

        public void setEmergencyMobileNo(String EmergencyMobileNo) { this.EmergencyMobileNo = EmergencyMobileNo; }

        private String EmailId;

        public String getEmailId() { return this.EmailId; }

        public void setEmailId(String EmailId) { this.EmailId = EmailId; }

        private String Provider;

        public String getProvider() { return this.Provider; }

        public void setProvider(String Provider) { this.Provider = Provider; }

        private String State;

        public String getState() { return this.State; }

        public void setState(String State) { this.State = State; }

        private String PinCode;

        public String getPinCode() { return this.PinCode; }

        public void setPinCode(String PinCode) { this.PinCode = PinCode; }

        private String PaymentId;

        public String getPaymentId() { return this.PaymentId; }

        public void setPaymentId(String PaymentId) { this.PaymentId = PaymentId; }

        private String BookingRefNo;

        public String getBookingRefNo() { return this.BookingRefNo; }

        public void setBookingRefNo(String BookingRefNo) { this.BookingRefNo = BookingRefNo; }

        private String Country;

        public String getCountry() { return this.Country; }

        public void setCountry(String Country) { this.Country = Country; }

        private String Allocavail;

        public String getAllocavail() { return this.Allocavail; }

        public void setAllocavail(String Allocavail) { this.Allocavail = Allocavail; }

        private String Allocid;

        public String getAllocid() { return this.Allocid; }

        public void setAllocid(String Allocid) { this.Allocid = Allocid; }

        private String City;

        public String getCity() { return this.City; }

        public void setCity(String City) { this.City = City; }

        private String APIRefNo;

        public String getAPIRefNo() { return this.APIRefNo; }

        public void setAPIRefNo(String APIRefNo) { this.APIRefNo = APIRefNo; }

        private String PromoCode;

        public String getPromoCode() { return this.PromoCode; }

        public void setPromoCode(String PromoCode) { this.PromoCode = PromoCode; }

        private double PromoCodeAmount;

        public double getPromoCodeAmount() { return this.PromoCodeAmount; }

        public void setPromoCodeAmount(double PromoCodeAmount) { this.PromoCodeAmount = PromoCodeAmount; }

        private int Status;

        public int getStatus() { return this.Status; }

        public void setStatus(int Status) { this.Status = Status; }

        private ArrayList<RoomDetailsDTO> RoomDetails;

        public ArrayList<RoomDetailsDTO> getRoomDetails() { return this.RoomDetails; }

        public void setRoomDetails(ArrayList<RoomDetailsDTO> RoomDetails) { this.RoomDetails = RoomDetails; }

        private Hotel_Details_DTO HotelDetail;

        public Hotel_Details_DTO getHotelDetail() { return this.HotelDetail; }

        public void setHotelDetail(Hotel_Details_DTO HotelDetail) { this.HotelDetail = HotelDetail; }

        private HotelImagesDTO HotelImages;

        public HotelImagesDTO getHotelImages() { return this.HotelImages; }

        public void setHotelImages(HotelImagesDTO HotelImages) { this.HotelImages = HotelImages; }

        private HotelPolicy_DTO HotelPolicy;

        public HotelPolicy_DTO getHotelPolicy() { return this.HotelPolicy; }

        public void setHotelPolicy(HotelPolicy_DTO HotelPolicy) { this.HotelPolicy = HotelPolicy; }

        private double PostMarkup;

        public double getPostMarkup() { return this.PostMarkup; }

        public void setPostMarkup(double PostMarkup) { this.PostMarkup = PostMarkup; }

        private double EProductPrice;

        public double getEProductPrice() { return this.EProductPrice; }

        public void setEProductPrice(double EProductPrice) { this.EProductPrice = EProductPrice; }

        private String Ecommerceproducts;

        public String getEcommerceproducts() { return this.Ecommerceproducts; }

        public void setEcommerceproducts(String Ecommerceproducts) { this.Ecommerceproducts = Ecommerceproducts; }

        private ArrayList<Ecommerceproducts_Pojo> EcommerceSegment;

        public ArrayList<Ecommerceproducts_Pojo> getEcommerceSegment() { return this.EcommerceSegment; }

        public void setEcommerceSegment(ArrayList<Ecommerceproducts_Pojo> EcommerceSegment) { this.EcommerceSegment = EcommerceSegment; }

        private String AdditionalInfo;

        public String getAdditionalInfo() { return this.AdditionalInfo; }

        public void setAdditionalInfo(String AdditionalInfo) { this.AdditionalInfo = AdditionalInfo; }

        private String CurrencyID;

        public String getCurrencyID() { return this.CurrencyID; }

        public void setCurrencyID(String CurrencyID) { this.CurrencyID = CurrencyID; }

        private String CurrencyValue;

        public String getCurrencyValue() { return this.CurrencyValue; }

        public void setCurrencyValue(String CurrencyValue) { this.CurrencyValue = CurrencyValue; }

        private boolean IsWallet;

        public boolean getIsWallet() { return this.IsWallet; }

        public void setIsWallet(boolean IsWallet) { this.IsWallet = IsWallet; }

        private int CreatedById;

        public int getCreatedById() { return this.CreatedById; }

        public void setCreatedById(int CreatedById) { this.CreatedById = CreatedById; }

        private PartnerAgentDetails_Pojo IsPartnerAgentDetails;

        public PartnerAgentDetails_Pojo getIsPartnerAgentDetails() { return this.IsPartnerAgentDetails; }

        public void setIsPartnerAgentDetails(PartnerAgentDetails_Pojo IsPartnerAgentDetails) { this.IsPartnerAgentDetails = IsPartnerAgentDetails; }

        private GSTDetailsDTO GSTDetails;

        public GSTDetailsDTO getGSTDetails() { return this.GSTDetails; }

        public void setGSTDetails(GSTDetailsDTO GSTDetails) { this.GSTDetails = GSTDetails; }

        private int DestinationId;

        public int getDestinationId() { return this.DestinationId; }

        public void setDestinationId(int DestinationId) { this.DestinationId = DestinationId; }

        private String CityName;

        public String getCityName() { return this.CityName; }

        public void setCityName(String CityName) { this.CityName = CityName; }

        private String ArrivalDate;

        public String getArrivalDate() { return this.ArrivalDate; }

        public void setArrivalDate(String ArrivalDate) { this.ArrivalDate = ArrivalDate; }

        private String DepartureDate;

        public String getDepartureDate() { return this.DepartureDate; }

        public void setDepartureDate(String DepartureDate) { this.DepartureDate = DepartureDate; }

        private int Rooms;

        public int getRooms() { return this.Rooms; }

        public void setRooms(int Rooms) { this.Rooms = Rooms; }

        private String Adults;

        public String getAdults() { return this.Adults; }

        public void setAdults(String Adults) { this.Adults = Adults; }

        private String Children;

        public String getChildren() { return this.Children; }

        public void setChildren(String Children) { this.Children = Children; }

        private String ChildrenAges;

        public String getChildrenAges() { return this.ChildrenAges; }

        public void setChildrenAges(String ChildrenAges) { this.ChildrenAges = ChildrenAges; }

        private String User;

        public String getUser() { return this.User; }

        public void setUser(String User) { this.User = User; }

        private int UserType;

        public int getUserType() { return this.UserType; }

        public void setUserType(int UserType) { this.UserType = UserType; }

        private int NoOfdays;

        public int getNoOfdays() { return this.NoOfdays; }

        public void setNoOfdays(int NoOfdays) { this.NoOfdays = NoOfdays; }

        private int HotelType;

        public int getHotelType() { return this.HotelType; }

        public void setHotelType(int HotelType) { this.HotelType = HotelType; }

        private String IsBack;

        public String getIsBack() { return this.IsBack; }

        public void setIsBack(String IsBack) { this.IsBack = IsBack; }

        private String AffiliateId;

        public String getAffiliateId() { return this.AffiliateId; }

        public void setAffiliateId(String AffiliateId) { this.AffiliateId = AffiliateId; }

        private String WebsiteUrl;

        public String getWebsiteUrl() { return this.WebsiteUrl; }

        public void setWebsiteUrl(String WebsiteUrl) { this.WebsiteUrl = WebsiteUrl; }

        private boolean IsHotelStairs;

        public boolean getIsHotelStairs() { return this.IsHotelStairs; }

        public void setIsHotelStairs(boolean IsHotelStairs) { this.IsHotelStairs = IsHotelStairs; }

        private double HProCurrency;

        public double getHProCurrency() { return this.HProCurrency; }

        public void setHProCurrency(double HProCurrency) { this.HProCurrency = HProCurrency; }

        private boolean IsCaching;

        public boolean getIsCaching() { return this.IsCaching; }

        public void setIsCaching(boolean IsCaching) { this.IsCaching = IsCaching; }
}
