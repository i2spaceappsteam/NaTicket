package com.NaTicket.n.recharges.pojo;

import com.NaTicket.n.buses.pojo.PaymentInfo_Pojo;

import java.io.Serializable;

/**
 * Created by Nagarjuna on 1/18/2018.
 */

public class Recharge_reports_pojo implements Serializable {

    public String MerchantRefNo;
    public String RechargeNumber;
    public int RechargeAmount;
    public String RechargeType;
    public String FailureDetails;
    public String PopularRechargeId;
    public String IPAddress;
    public String Name;
    public String City;
    public String State;
    public String Address;
    public String Pincode;
    public String Email;
    public String RechargeStatus;
    public String RechargeDate;
    public String OpertorRef;
    public String IsP2P;
    public String DeviceModel;
    public String DeviceOS;
    public String DeviceOSVersion;
    public String DeviceToken;
    public String ApplicationType;
    public String ClientId;
    public String OperatorId;
    public String OperatorName;
    public String ProviderId;
    public String APIProviderName;
    public String BookingRefNo;
    public String PaxCount;
    public String APIRefNo;
    public String UserId;
    public String UserType;
    public String PSAName;
    public String PickUpLocation;
    public String DropLocation;
    public String PaymentId;
    public String SMSUsageCount;
    public String ActualFare;
    public String CollectedFare;
    public String Commission;
    public String Markup;
    public String PostMarkup;
    public String IsOfflineBooking;
    public String PromoCode;
    public String PromoCodeAmount;
    public String CreatedBy;
    public String CreatedDate;
    public String ModifiedBy;
    public String ModifiedDate;
    public String CreatedById;
    public String ModifiedById;
    public String Cancellations;
    public String Ecommerceproducts;
    public String Conveniencefee;
    public String EProductPrice;
    public String ReportBookingStatus;
    public String ReportBookingDate;
    public String IsWallet;
    public String Remarks;
    public String CurrencyID;
    public String CurrencyValue;
    public String TotalPages;
    public String PartnerAgentDetails;



    private PaymentInfo_Pojo PaymentInfo;

    public PaymentInfo_Pojo getPaymentInfo() { return this.PaymentInfo; }

    public void setPaymentInfo(PaymentInfo_Pojo PaymentInfo) { this.PaymentInfo = PaymentInfo; }

    public int getRechargeId() {
        return RechargeId;
    }

    public void setRechargeId(int rechargeId) {
        RechargeId = rechargeId;
    }

    public int RechargeId;


    public String getMerchantRefNo() {
        return MerchantRefNo;
    }

    public void setMerchantRefNo(String merchantRefNo) {
        MerchantRefNo = merchantRefNo;
    }

    public String getRechargeNumber() {
        return RechargeNumber;
    }

    public void setRechargeNumber(String rechargeNumber) {
        RechargeNumber = rechargeNumber;
    }

    public Integer getRechargeAmount() {
        return RechargeAmount;
    }

    public void setRechargeAmount(Integer rechargeAmount) {
        RechargeAmount = rechargeAmount;
    }

    public String getRechargeType() {
        return RechargeType;
    }

    public void setRechargeType(String rechargeType) {
        RechargeType = rechargeType;
    }

    public String getFailureDetails() {
        return FailureDetails;
    }

    public void setFailureDetails(String failureDetails) {
        FailureDetails = failureDetails;
    }

    public String getPopularRechargeId() {
        return PopularRechargeId;
    }

    public void setPopularRechargeId(String popularRechargeId) {
        PopularRechargeId = popularRechargeId;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getRechargeStatus() {
        return RechargeStatus;
    }

    public void setRechargeStatus(String rechargeStatus) {
        RechargeStatus = rechargeStatus;
    }

    public String getRechargeDate() {
        return RechargeDate;
    }

    public void setRechargeDate(String rechargeDate) {
        RechargeDate = rechargeDate;
    }

    public String getOpertorRef() {
        return OpertorRef;
    }

    public void setOpertorRef(String opertorRef) {
        OpertorRef = opertorRef;
    }

    public String getIsP2P() {
        return IsP2P;
    }

    public void setIsP2P(String isP2P) {
        IsP2P = isP2P;
    }

    public String getDeviceModel() {
        return DeviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        DeviceModel = deviceModel;
    }

    public String getDeviceOS() {
        return DeviceOS;
    }

    public void setDeviceOS(String deviceOS) {
        DeviceOS = deviceOS;
    }

    public String getDeviceOSVersion() {
        return DeviceOSVersion;
    }

    public void setDeviceOSVersion(String deviceOSVersion) {
        DeviceOSVersion = deviceOSVersion;
    }

    public String getDeviceToken() {
        return DeviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        DeviceToken = deviceToken;
    }

    public String getApplicationType() {
        return ApplicationType;
    }

    public void setApplicationType(String applicationType) {
        ApplicationType = applicationType;
    }

    public String getClientId() {
        return ClientId;
    }

    public void setClientId(String clientId) {
        ClientId = clientId;
    }

    public String getOperatorId() {
        return OperatorId;
    }

    public void setOperatorId(String operatorId) {
        OperatorId = operatorId;
    }

    public String getOperatorName() {
        return OperatorName;
    }

    public void setOperatorName(String operatorName) {
        OperatorName = operatorName;
    }

    public String getProviderId() {
        return ProviderId;
    }

    public void setProviderId(String providerId) {
        ProviderId = providerId;
    }

    public String getAPIProviderName() {
        return APIProviderName;
    }

    public void setAPIProviderName(String APIProviderName) {
        this.APIProviderName = APIProviderName;
    }

    public String getBookingRefNo() {
        return BookingRefNo;
    }

    public void setBookingRefNo(String bookingRefNo) {
        BookingRefNo = bookingRefNo;
    }

    public String getPaxCount() {
        return PaxCount;
    }

    public void setPaxCount(String paxCount) {
        PaxCount = paxCount;
    }

    public String getAPIRefNo() {
        return APIRefNo;
    }

    public void setAPIRefNo(String APIRefNo) {
        this.APIRefNo = APIRefNo;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getPSAName() {
        return PSAName;
    }

    public void setPSAName(String PSAName) {
        this.PSAName = PSAName;
    }

    public String getPickUpLocation() {
        return PickUpLocation;
    }

    public void setPickUpLocation(String pickUpLocation) {
        PickUpLocation = pickUpLocation;
    }

    public String getDropLocation() {
        return DropLocation;
    }

    public void setDropLocation(String dropLocation) {
        DropLocation = dropLocation;
    }

    public String getPaymentId() {
        return PaymentId;
    }

    public void setPaymentId(String paymentId) {
        PaymentId = paymentId;
    }

    public String getSMSUsageCount() {
        return SMSUsageCount;
    }

    public void setSMSUsageCount(String SMSUsageCount) {
        this.SMSUsageCount = SMSUsageCount;
    }

    public String getActualFare() {
        return ActualFare;
    }

    public void setActualFare(String actualFare) {
        ActualFare = actualFare;
    }

    public String getCollectedFare() {
        return CollectedFare;
    }

    public void setCollectedFare(String collectedFare) {
        CollectedFare = collectedFare;
    }

    public String getCommission() {
        return Commission;
    }

    public void setCommission(String commission) {
        Commission = commission;
    }

    public String getMarkup() {
        return Markup;
    }

    public void setMarkup(String markup) {
        Markup = markup;
    }

    public String getPostMarkup() {
        return PostMarkup;
    }

    public void setPostMarkup(String postMarkup) {
        PostMarkup = postMarkup;
    }

    public String getIsOfflineBooking() {
        return IsOfflineBooking;
    }

    public void setIsOfflineBooking(String isOfflineBooking) {
        IsOfflineBooking = isOfflineBooking;
    }

    public String getPromoCode() {
        return PromoCode;
    }

    public void setPromoCode(String promoCode) {
        PromoCode = promoCode;
    }

    public String getPromoCodeAmount() {
        return PromoCodeAmount;
    }

    public void setPromoCodeAmount(String promoCodeAmount) {
        PromoCodeAmount = promoCodeAmount;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getModifiedBy() {
        return ModifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        ModifiedBy = modifiedBy;
    }

    public String getModifiedDate() {
        return ModifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        ModifiedDate = modifiedDate;
    }

    public String getCreatedById() {
        return CreatedById;
    }

    public void setCreatedById(String createdById) {
        CreatedById = createdById;
    }

    public String getModifiedById() {
        return ModifiedById;
    }

    public void setModifiedById(String modifiedById) {
        ModifiedById = modifiedById;
    }

    public String getCancellations() {
        return Cancellations;
    }

    public void setCancellations(String cancellations) {
        Cancellations = cancellations;
    }

    public String getEcommerceproducts() {
        return Ecommerceproducts;
    }

    public void setEcommerceproducts(String ecommerceproducts) {
        Ecommerceproducts = ecommerceproducts;
    }

    public String getConveniencefee() {
        return Conveniencefee;
    }

    public void setConveniencefee(String conveniencefee) {
        Conveniencefee = conveniencefee;
    }

    public String getEProductPrice() {
        return EProductPrice;
    }

    public void setEProductPrice(String EProductPrice) {
        this.EProductPrice = EProductPrice;
    }

    public String getReportBookingStatus() {
        return ReportBookingStatus;
    }

    public void setReportBookingStatus(String reportBookingStatus) {
        ReportBookingStatus = reportBookingStatus;
    }

    public String getReportBookingDate() {
        return ReportBookingDate;
    }

    public void setReportBookingDate(String reportBookingDate) {
        ReportBookingDate = reportBookingDate;
    }

    public String getIsWallet() {
        return IsWallet;
    }

    public void setIsWallet(String isWallet) {
        IsWallet = isWallet;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getCurrencyID() {
        return CurrencyID;
    }

    public void setCurrencyID(String currencyID) {
        CurrencyID = currencyID;
    }

    public String getCurrencyValue() {
        return CurrencyValue;
    }

    public void setCurrencyValue(String currencyValue) {
        CurrencyValue = currencyValue;
    }

    public String getTotalPages() {
        return TotalPages;
    }

    public void setTotalPages(String totalPages) {
        TotalPages = totalPages;
    }

    public String getPartnerAgentDetails() {
        return PartnerAgentDetails;
    }

    public void setPartnerAgentDetails(String partnerAgentDetails) {
        PartnerAgentDetails = partnerAgentDetails;
    }









}
