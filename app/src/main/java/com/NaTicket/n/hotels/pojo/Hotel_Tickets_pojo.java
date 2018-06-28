package com.NaTicket.n.hotels.pojo;

import com.NaTicket.n.buses.pojo.Cancellation_Pojo;
import com.NaTicket.n.buses.pojo.Ecommerceproducts_Pojo;
import com.NaTicket.n.buses.pojo.PaymentInfo_Pojo;
import com.NaTicket.n.flights.pojo.GSTDetailsDTO;
import com.NaTicket.n.flights.pojo.PartnerFareDatailsDTO;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Nagarjuna on 1/9/2018.
 */

public class Hotel_Tickets_pojo implements Serializable {


        private GSTDetailsDTO GstDetails;

        public GSTDetailsDTO getGstDetails() { return this.GstDetails; }

        public void setGstDetails(GSTDetailsDTO GstDetails) { this.GstDetails = GstDetails; }

        private boolean IsAgentHotelPG;

        public boolean getIsAgentHotelPG() { return this.IsAgentHotelPG; }

        public void setIsAgentHotelPG(boolean IsAgentHotelPG) { this.IsAgentHotelPG = IsAgentHotelPG; }

        private int HotelType;

        public int getHotelType() { return this.HotelType; }

        public void setHotelType(int HotelType) { this.HotelType = HotelType; }

        private String HotelId;

        public String getHotelId() { return this.HotelId; }

        public void setHotelId(String HotelId) { this.HotelId = HotelId; }

        private String Adults;

        public String getAdults() { return this.Adults; }

        public void setAdults(String Adults) { this.Adults = Adults; }

        private String Children;

        public String getChildren() { return this.Children; }

        public void setChildren(String Children) { this.Children = Children; }

        private String Ages;

        public String getAges() { return this.Ages; }

        public void setAges(String Ages) { this.Ages = Ages; }

        private String ChildrenAges;

        public String getChildrenAges() { return this.ChildrenAges; }

        public void setChildrenAges(String ChildrenAges) { this.ChildrenAges = ChildrenAges; }

        private int Rooms;

        public int getRooms() { return this.Rooms; }

        public void setRooms(int Rooms) { this.Rooms = Rooms; }

        private String ArrivalDateTime;

        public String getArrivalDateTime() { return this.ArrivalDateTime; }

        public void setArrivalDateTime(String ArrivalDateTime) { this.ArrivalDateTime = ArrivalDateTime; }

        private String DepartDateTime;

        public String getDepartDateTime() { return this.DepartDateTime; }

        public void setDepartDateTime(String DepartDateTime) { this.DepartDateTime = DepartDateTime; }

        private String Titles;

        public String getTitles() { return this.Titles; }

        public void setTitles(String Titles) { this.Titles = Titles; }

        private String Names;

        public String getNames() { return this.Names; }

        public void setNames(String Names) { this.Names = Names; }

        private String Genders;

        public String getGenders() { return this.Genders; }

        public void setGenders(String Genders) { this.Genders = Genders; }

        private String Address;

        public String getAddress() { return this.Address; }

        public void setAddress(String Address) { this.Address = Address; }

        private String MobileNo;

        public String getMobileNo() { return this.MobileNo; }

        public void setMobileNo(String MobileNo) { this.MobileNo = MobileNo; }

        private String EmailId;

        public String getEmailId() { return this.EmailId; }

        public void setEmailId(String EmailId) { this.EmailId = EmailId; }

        private String State;

        public String getState() { return this.State; }

        public void setState(String State) { this.State = State; }

        private String PinCode;

        public String getPinCode() { return this.PinCode; }

        public void setPinCode(String PinCode) { this.PinCode = PinCode; }

        private String Allocavail;

        public String getAllocavail() { return this.Allocavail; }

        public void setAllocavail(String Allocavail) { this.Allocavail = Allocavail; }

        private String Allocid;

        public String getAllocid() { return this.Allocid; }

        public void setAllocid(String Allocid) { this.Allocid = Allocid; }

        private String HotelName;

        public String getHotelName() { return this.HotelName; }

        public void setHotelName(String HotelName) { this.HotelName = HotelName; }

        private String WebService;

        public String getWebService() { return this.WebService; }

        public void setWebService(String WebService) { this.WebService = WebService; }

        private String City;

        public String getCity() { return this.City; }

        public void setCity(String City) { this.City = City; }

        private String CityCode;

        public String getCityCode() { return this.CityCode; }

        public void setCityCode(String CityCode) { this.CityCode = CityCode; }

        private String HotelAddress;

        public String getHotelAddress() { return this.HotelAddress; }

        public void setHotelAddress(String HotelAddress) { this.HotelAddress = HotelAddress; }

        private String StarRating;

        public String getStarRating() { return this.StarRating; }

        public void setStarRating(String StarRating) { this.StarRating = StarRating; }

        private String Fare;

        public String getFare() { return this.Fare; }

        public void setFare(String Fare) { this.Fare = Fare; }

        private int NoOfdays;

        public int getNoOfdays() { return this.NoOfdays; }

        public void setNoOfdays(int NoOfdays) { this.NoOfdays = NoOfdays; }

        private String CancellationPolicy;

        public String getCancellationPolicy() { return this.CancellationPolicy; }

        public void setCancellationPolicy(String CancellationPolicy) { this.CancellationPolicy = CancellationPolicy; }

        private int BookingStatus;

        public int getBookingStatus() { return this.BookingStatus; }

        public void setBookingStatus(int BookingStatus) { this.BookingStatus = BookingStatus; }

        private String SupplierType;

        public String getSupplierType() { return this.SupplierType; }

        public void setSupplierType(String SupplierType) { this.SupplierType = SupplierType; }

        private ArrayList<RoomDetailsDTO> RoomDetails;

        public ArrayList<RoomDetailsDTO> getRoomDetails() { return this.RoomDetails; }

        public void setRoomDetails(ArrayList<RoomDetailsDTO> RoomDetails) { this.RoomDetails = RoomDetails; }

        private String BookingResponseXML;

        public String getBookingResponseXML() { return this.BookingResponseXML; }

        public void setBookingResponseXML(String BookingResponseXML) { this.BookingResponseXML = BookingResponseXML; }

        private String AdditionalInfo;

        public String getAdditionalInfo() { return this.AdditionalInfo; }

        public void setAdditionalInfo(String AdditionalInfo) { this.AdditionalInfo = AdditionalInfo; }

        private String Nationality;

        public String getNationality() { return this.Nationality; }

        public void setNationality(String Nationality) { this.Nationality = Nationality; }

        private double HProCurrency;

        public double getHProCurrency() { return this.HProCurrency; }

        public void setHProCurrency(double HProCurrency) { this.HProCurrency = HProCurrency; }

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

        private int ClientId;

        public int getClientId() { return this.ClientId; }

        public void setClientId(int ClientId) { this.ClientId = ClientId; }

        private int OperatorId;

        public int getOperatorId() { return this.OperatorId; }

        public void setOperatorId(int OperatorId) { this.OperatorId = OperatorId; }

        private String OperatorName;

        public String getOperatorName() { return this.OperatorName; }

        public void setOperatorName(String OperatorName) { this.OperatorName = OperatorName; }

        private int ProviderId;

        public int getProviderId() { return this.ProviderId; }

        public void setProviderId(int ProviderId) { this.ProviderId = ProviderId; }

        private String APIProviderName;

        public String getAPIProviderName() { return this.APIProviderName; }

        public void setAPIProviderName(String APIProviderName) { this.APIProviderName = APIProviderName; }

        private String BookingRefNo;

        public String getBookingRefNo() { return this.BookingRefNo; }

        public void setBookingRefNo(String BookingRefNo) { this.BookingRefNo = BookingRefNo; }

        private int PaxCount;

        public int getPaxCount() { return this.PaxCount; }

        public void setPaxCount(int PaxCount) { this.PaxCount = PaxCount; }

        private String APIRefNo;

        public String getAPIRefNo() { return this.APIRefNo; }

        public void setAPIRefNo(String APIRefNo) { this.APIRefNo = APIRefNo; }

        private int UserId;

        public int getUserId() { return this.UserId; }

        public void setUserId(int UserId) { this.UserId = UserId; }

        private int UserType;

        public int getUserType() { return this.UserType; }

        public void setUserType(int UserType) { this.UserType = UserType; }

        private String PSAName;

        public String getPSAName() { return this.PSAName; }

        public void setPSAName(String PSAName) { this.PSAName = PSAName; }

        private String PickUpLocation;

        public String getPickUpLocation() { return this.PickUpLocation; }

        public void setPickUpLocation(String PickUpLocation) { this.PickUpLocation = PickUpLocation; }

        private String DropLocation;

        public String getDropLocation() { return this.DropLocation; }

        public void setDropLocation(String DropLocation) { this.DropLocation = DropLocation; }

        private String PaymentId;

        public String getPaymentId() { return this.PaymentId; }

        public void setPaymentId(String PaymentId) { this.PaymentId = PaymentId; }

        private PaymentInfo_Pojo PaymentInfo;

        public PaymentInfo_Pojo getPaymentInfo() { return this.PaymentInfo; }

        public void setPaymentInfo(PaymentInfo_Pojo PaymentInfo) { this.PaymentInfo = PaymentInfo; }

        private int SMSUsageCount;

        public int getSMSUsageCount() { return this.SMSUsageCount; }

        public void setSMSUsageCount(int SMSUsageCount) { this.SMSUsageCount = SMSUsageCount; }

        private double ActualFare;

        public double getActualFare() { return this.ActualFare; }

        public void setActualFare(double ActualFare) { this.ActualFare = ActualFare; }

        private double CollectedFare;

        public double getCollectedFare() { return this.CollectedFare; }

        public void setCollectedFare(double CollectedFare) { this.CollectedFare = CollectedFare; }

        private double Commission;

        public double getCommission() { return this.Commission; }

        public void setCommission(double Commission) { this.Commission = Commission; }

        private double Markup;

        public double getMarkup() { return this.Markup; }

        public void setMarkup(double Markup) { this.Markup = Markup; }

        private double PostMarkup;

        public double getPostMarkup() { return this.PostMarkup; }

        public void setPostMarkup(double PostMarkup) { this.PostMarkup = PostMarkup; }

        private boolean IsOfflineBooking;

        public boolean getIsOfflineBooking() { return this.IsOfflineBooking; }

        public void setIsOfflineBooking(boolean IsOfflineBooking) { this.IsOfflineBooking = IsOfflineBooking; }

        private String PromoCode;

        public String getPromoCode() { return this.PromoCode; }

        public void setPromoCode(String PromoCode) { this.PromoCode = PromoCode; }

        private double PromoCodeAmount;

        public double getPromoCodeAmount() { return this.PromoCodeAmount; }

        public void setPromoCodeAmount(double PromoCodeAmount) { this.PromoCodeAmount = PromoCodeAmount; }

        private String CreatedBy;

        public String getCreatedBy() { return this.CreatedBy; }

        public void setCreatedBy(String CreatedBy) { this.CreatedBy = CreatedBy; }

        private String CreatedDate;

        public String getCreatedDate() { return this.CreatedDate; }

        public void setCreatedDate(String CreatedDate) { this.CreatedDate = CreatedDate; }

        private String ModifiedBy;

        public String getModifiedBy() { return this.ModifiedBy; }

        public void setModifiedBy(String ModifiedBy) { this.ModifiedBy = ModifiedBy; }

        private String ModifiedDate;

        public String getModifiedDate() { return this.ModifiedDate; }

        public void setModifiedDate(String ModifiedDate) { this.ModifiedDate = ModifiedDate; }

        private int CreatedById;

        public int getCreatedById() { return this.CreatedById; }

        public void setCreatedById(int CreatedById) { this.CreatedById = CreatedById; }

        private int ModifiedById;

        public int getModifiedById() { return this.ModifiedById; }

        public void setModifiedById(int ModifiedById) { this.ModifiedById = ModifiedById; }

        private ArrayList<Cancellation_Pojo> Cancellations;

        public ArrayList<Cancellation_Pojo> getCancellations() { return this.Cancellations; }

        public void setCancellations(ArrayList<Cancellation_Pojo> Cancellations) { this.Cancellations = Cancellations; }

        private ArrayList<Ecommerceproducts_Pojo> Ecommerceproducts;

        public ArrayList<Ecommerceproducts_Pojo> getEcommerceproducts() { return this.Ecommerceproducts; }

        public void setEcommerceproducts(ArrayList<Ecommerceproducts_Pojo> Ecommerceproducts) { this.Ecommerceproducts = Ecommerceproducts; }

        private double Conveniencefee;

        public double getConveniencefee() { return this.Conveniencefee; }

        public void setConveniencefee(double Conveniencefee) { this.Conveniencefee = Conveniencefee; }

        private double EProductPrice;

        public double getEProductPrice() { return this.EProductPrice; }

        public void setEProductPrice(double EProductPrice) { this.EProductPrice = EProductPrice; }

        private String ReportBookingStatus;

        public String getReportBookingStatus() { return this.ReportBookingStatus; }

        public void setReportBookingStatus(String ReportBookingStatus) { this.ReportBookingStatus = ReportBookingStatus; }

        private String ReportBookingDate;

        public String getReportBookingDate() { return this.ReportBookingDate; }

        public void setReportBookingDate(String ReportBookingDate) { this.ReportBookingDate = ReportBookingDate; }

        private boolean IsWallet;

        public boolean getIsWallet() { return this.IsWallet; }

        public void setIsWallet(boolean IsWallet) { this.IsWallet = IsWallet; }

        private String Remarks;

        public String getRemarks() { return this.Remarks; }

        public void setRemarks(String Remarks) { this.Remarks = Remarks; }

        private String CurrencyID;

        public String getCurrencyID() { return this.CurrencyID; }

        public void setCurrencyID(String CurrencyID) { this.CurrencyID = CurrencyID; }

        private String CurrencyValue;

        public String getCurrencyValue() { return this.CurrencyValue; }

        public void setCurrencyValue(String CurrencyValue) { this.CurrencyValue = CurrencyValue; }

        private String TotalPages;

        public String getTotalPages() { return this.TotalPages; }

        public void setTotalPages(String TotalPages) { this.TotalPages = TotalPages; }

        private PartnerFareDatailsDTO PartnerAgentDetails;

        public PartnerFareDatailsDTO getPartnerAgentDetails() { return this.PartnerAgentDetails; }

        public void setPartnerAgentDetails(PartnerFareDatailsDTO PartnerAgentDetails) { this.PartnerAgentDetails = PartnerAgentDetails; }


}
