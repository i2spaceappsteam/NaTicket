package com.NaTicket.n.holidays.pojo;

import com.NaTicket.n.buses.pojo.Cancellation_Pojo;
import com.NaTicket.n.buses.pojo.Ecommerceproducts_Pojo;
import com.NaTicket.n.buses.pojo.PartnerAgentDetails_Pojo;
import com.NaTicket.n.buses.pojo.PaymentInfo_Pojo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Nagarjuna on 1/10/2018.
 */

public class Holiday_Ticket_Details_DTO implements Serializable{


        private String Name;

        public String getName() {
                return Name;
        }

        public void setName(String name) {
                Name = name;
        }

        private boolean IsAgentPaymentGateway;

        public boolean getIsAgentPaymentGateway() { return this.IsAgentPaymentGateway; }

        public void setIsAgentPaymentGateway(boolean IsAgentPaymentGateway) { this.IsAgentPaymentGateway = IsAgentPaymentGateway; }

        private int HolidayPackageId;

        public int getHolidayPackageId() { return this.HolidayPackageId; }

        public void setHolidayPackageId(int HolidayPackageId) { this.HolidayPackageId = HolidayPackageId; }

        private String HolidayPackageName;

        public String getHolidayPackageName() { return this.HolidayPackageName; }

        public void setHolidayPackageName(String HolidayPackageName) { this.HolidayPackageName = HolidayPackageName; }

        private int DestinationId;

        public int getDestinationId() { return this.DestinationId; }

        public void setDestinationId(int DestinationId) { this.DestinationId = DestinationId; }

        private String DestinationName;

        public String getDestinationName() { return this.DestinationName; }

        public void setDestinationName(String DestinationName) { this.DestinationName = DestinationName; }

        private int Duration;

        public int getDuration() { return this.Duration; }

        public void setDuration(int Duration) { this.Duration = Duration; }

        private int CategoryId;

        public int getCategoryId() { return this.CategoryId; }

        public void setCategoryId(int CategoryId) { this.CategoryId = CategoryId; }

        private int SubCategoryId;

        public int getSubCategoryId() { return this.SubCategoryId; }

        public void setSubCategoryId(int SubCategoryId) { this.SubCategoryId = SubCategoryId; }

        private String SubCategoryName;

        public String getSubCategoryName() { return this.SubCategoryName; }

        public void setSubCategoryName(String SubCategoryName) { this.SubCategoryName = SubCategoryName; }

        private String JourneyDate;

        public String getJourneyDate() { return this.JourneyDate; }

        public void setJourneyDate(String JourneyDate) { this.JourneyDate = JourneyDate; }

        private int Rooms;

        public int getRooms() { return this.Rooms; }

        public void setRooms(int Rooms) { this.Rooms = Rooms; }

        private String RoomType;

        public String getRoomType() { return this.RoomType; }

        public void setRoomType(String RoomType) { this.RoomType = RoomType; }

        private int AdultCount;

        public int getAdultCount() { return this.AdultCount; }

        public void setAdultCount(int AdultCount) { this.AdultCount = AdultCount; }

        private int ChildCount;

        public int getChildCount() { return this.ChildCount; }

        public void setChildCount(int ChildCount) { this.ChildCount = ChildCount; }

        private double ServiceCharge;

        public double getServiceCharge() { return this.ServiceCharge; }

        public void setServiceCharge(double ServiceCharge) { this.ServiceCharge = ServiceCharge; }

        private double TotalFare;

        public double getTotalFare() { return this.TotalFare; }

        public void setTotalFare(double TotalFare) { this.TotalFare = TotalFare; }

        private boolean IsAdminPackage;

        public boolean getIsAdminPackage() { return this.IsAdminPackage; }

        public void setIsAdminPackage(boolean IsAdminPackage) { this.IsAdminPackage = IsAdminPackage; }

        private String NoofPassengers;

        public String getNoofPassengers() { return this.NoofPassengers; }

        public void setNoofPassengers(String NoofPassengers) { this.NoofPassengers = NoofPassengers; }

        private String PassengerName;

        public String getPassengerName() { return this.PassengerName; }

        public void setPassengerName(String PassengerName) { this.PassengerName = PassengerName; }

        private String MobileNo;

        public String getMobileNo() { return this.MobileNo; }

        public void setMobileNo(String MobileNo) { this.MobileNo = MobileNo; }

        private String EmailId;

        public String getEmailId() { return this.EmailId; }

        public void setEmailId(String EmailId) { this.EmailId = EmailId; }

        private String City;

        public String getCity() { return this.City; }

        public void setCity(String City) { this.City = City; }

        private String Address;

        public String getAddress() { return this.Address; }

        public void setAddress(String Address) { this.Address = Address; }

        private String PostalCode;

        public String getPostalCode() { return this.PostalCode; }

        public void setPostalCode(String PostalCode) { this.PostalCode = PostalCode; }

        private String Operator;

        public String getOperator() { return this.Operator; }

        public void setOperator(String Operator) { this.Operator = Operator; }

        private String CancellationPolicy;

        public String getCancellationPolicy() { return this.CancellationPolicy; }

        public void setCancellationPolicy(String CancellationPolicy) { this.CancellationPolicy = CancellationPolicy; }

        private String Terms;

        public String getTerms() { return this.Terms; }

        public void setTerms(String Terms) { this.Terms = Terms; }

        private int BookingStatus;

        public int getBookingStatus() { return this.BookingStatus; }

        public void setBookingStatus(int BookingStatus) { this.BookingStatus = BookingStatus; }

        private String Itinerary;

        public String getItinerary() { return this.Itinerary; }

        public void setItinerary(String Itinerary) { this.Itinerary = Itinerary; }

        private String Exclusions;

        public String getExclusions() { return this.Exclusions; }

        public void setExclusions(String Exclusions) { this.Exclusions = Exclusions; }

        private String Inclusions;

        public String getInclusions() { return this.Inclusions; }

        public void setInclusions(String Inclusions) { this.Inclusions = Inclusions; }

        private String AdditionalInfo;

        public String getAdditionalInfo() { return this.AdditionalInfo; }

        public void setAdditionalInfo(String AdditionalInfo) { this.AdditionalInfo = AdditionalInfo; }

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

        private double ConvenienceFee;

        public double getConvenienceFee() { return this.ConvenienceFee; }

        public void setConvenienceFee(double ConvenienceFee) { this.ConvenienceFee = ConvenienceFee; }

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

        private PartnerAgentDetails_Pojo PartnerAgentDetails;

        public PartnerAgentDetails_Pojo getPartnerAgentDetails() { return this.PartnerAgentDetails; }

        public void setPartnerAgentDetails(PartnerAgentDetails_Pojo PartnerAgentDetails) { this.PartnerAgentDetails = PartnerAgentDetails; }

}
