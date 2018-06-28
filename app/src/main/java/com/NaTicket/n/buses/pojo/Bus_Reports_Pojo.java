package com.NaTicket.n.buses.pojo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Nagarjuna on 1/9/2018.
 */

public class Bus_Reports_Pojo implements Serializable {

    private boolean IsAgentBusPG;

    public boolean getIsAgentBusPG() { return this.IsAgentBusPG; }

    public void setIsAgentBusPG(boolean IsAgentBusPG) { this.IsAgentBusPG = IsAgentBusPG; }

    private String SourceId;

    public String getSourceId() { return this.SourceId; }

    public void setSourceId(String SourceId) { this.SourceId = SourceId; }

    private String SourceName;

    public String getSourceName() { return this.SourceName; }

    public void setSourceName(String SourceName) { this.SourceName = SourceName; }

    private String DestinationId;

    public String getDestinationId() { return this.DestinationId; }

    public void setDestinationId(String DestinationId) { this.DestinationId = DestinationId; }

    private String DestinationName;

    public String getDestinationName() { return this.DestinationName; }

    public void setDestinationName(String DestinationName) { this.DestinationName = DestinationName; }

    private String JourneyDate;

    public String getJourneyDate() { return this.JourneyDate; }

    public void setJourneyDate(String JourneyDate) { this.JourneyDate = JourneyDate; }

    private String ReturnDate;

    public String getReturnDate() { return this.ReturnDate; }

    public void setReturnDate(String ReturnDate) { this.ReturnDate = ReturnDate; }

    private int TripType;

    public int getTripType() { return this.TripType; }

    public void setTripType(int TripType) { this.TripType = TripType; }

    private String BusTypeName;

    public String getBusTypeName() { return this.BusTypeName; }

    public void setBusTypeName(String BusTypeName) { this.BusTypeName = BusTypeName; }

    private String Source;

    public String getSource() { return this.Source; }

    public void setSource(String Source) { this.Source = Source; }

    private String Destination;

    public String getDestination() { return this.Destination; }

    public void setDestination(String Destination) { this.Destination = Destination; }

    private String SeatNos;

    public String getSeatNos() { return this.SeatNos; }

    public void setSeatNos(String SeatNos) { this.SeatNos = SeatNos; }

    private String TripId;

    public String getTripId() { return this.TripId; }

    public void setTripId(String TripId) { this.TripId = TripId; }

    private String NoofSeats;

    public String getNoofSeats() { return this.NoofSeats; }

    public void setNoofSeats(String NoofSeats) { this.NoofSeats = NoofSeats; }

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

    private String PostalCode;

    public String getPostalCode() { return this.PostalCode; }

    public void setPostalCode(String PostalCode) { this.PostalCode = PostalCode; }

    private String MobileNo;

    public String getMobileNo() { return this.MobileNo; }

    public void setMobileNo(String MobileNo) { this.MobileNo = MobileNo; }

    private String EmailId;

    public String getEmailId() { return this.EmailId; }

    public void setEmailId(String EmailId) { this.EmailId = EmailId; }

    private String BlockId;

    public String getBlockId() { return this.BlockId; }

    public void setBlockId(String BlockId) { this.BlockId = BlockId; }

    private BoardingDetails_Pojo BoardingDetails;

    public BoardingDetails_Pojo getBoardingDetails() { return this.BoardingDetails; }

    public void setBoardingDetails(BoardingDetails_Pojo BoardingDetails) { this.BoardingDetails = BoardingDetails; }

    private DropingpointDetails_Pojo DropingpointDetails;

    public  DropingpointDetails_Pojo getDropingpointDetails() { return this.DropingpointDetails; }

    public void setDropingpointDetails(DropingpointDetails_Pojo DropingpointDetails) { this.DropingpointDetails = DropingpointDetails; }

    private ArrayList<Ecommerceproducts_Pojo> Ecommerceproducts;

    public ArrayList<Ecommerceproducts_Pojo> getEcommerceproducts() { return this.Ecommerceproducts; }

    public void setEcommerceproducts(ArrayList<Ecommerceproducts_Pojo> Ecommerceproducts) { this.Ecommerceproducts = Ecommerceproducts; }

    private String DepartureTime;

    public String getDepartureTime() { return this.DepartureTime; }

    public void setDepartureTime(String DepartureTime) { this.DepartureTime = DepartureTime; }

    private String CancellationPolicy;

    public String getCancellationPolicy() { return this.CancellationPolicy; }

    public void setCancellationPolicy(String CancellationPolicy) { this.CancellationPolicy = CancellationPolicy; }

    private String PassengerName;

    public String getPassengerName() { return this.PassengerName; }

    public void setPassengerName(String PassengerName) { this.PassengerName = PassengerName; }

    private String Fares;

    public String getFares() { return this.Fares; }

    public void setFares(String Fares) { this.Fares = Fares; }

    private String Servicetax;

    public String getServicetax() { return this.Servicetax; }

    public void setServicetax(String Servicetax) { this.Servicetax = Servicetax; }

    private String ServiceCharge;

    public String getServiceCharge() { return this.ServiceCharge; }

    public void setServiceCharge(String ServiceCharge) { this.ServiceCharge = ServiceCharge; }

    private String BookingDate;

    public String getBookingDate() { return this.BookingDate; }

    public void setBookingDate(String BookingDate) { this.BookingDate = BookingDate; }

    private int BookingStatus;

    public int getBookingStatus() { return this.BookingStatus; }

    public void setBookingStatus(int BookingStatus) { this.BookingStatus = BookingStatus; }

    private String CancellationRemarks;

    public String getCancellationRemarks() { return this.CancellationRemarks; }

    public void setCancellationRemarks(String CancellationRemarks) { this.CancellationRemarks = CancellationRemarks; }

    private boolean PartialCancellationAllowed;

    public boolean getPartialCancellationAllowed() { return this.PartialCancellationAllowed; }

    public void setPartialCancellationAllowed(boolean PartialCancellationAllowed) { this.PartialCancellationAllowed = PartialCancellationAllowed; }

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
