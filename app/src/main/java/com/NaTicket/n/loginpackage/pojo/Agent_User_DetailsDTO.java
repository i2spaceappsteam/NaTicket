package com.NaTicket.n.loginpackage.pojo;

import java.io.Serializable;

/**
 * Created by Ankit on 8/21/2017.
 */

public class Agent_User_DetailsDTO implements Serializable {

    public String UserId;
    public String ClientId;
    public String ParentId;
    public String ClientName;
    public String FirstName;
    public String LastName;
    public String Password;
    public String Gender;
    public String DOB;
    public String Email;
    public String AlternateEmail;
    public String Mobile;
    public String AlternateMobile;
    public String Telephone;
    public String Address;
    public String City;
    public String State;
    public String Pincode;
    public String Status;
    public String ActivationCode;
    public String DeviceModel;
    public String DeviceOS;
    public String LoginType;
    public String DeviceOSVersion;
    public String DeviceToken;
    public String Deprcyt_User_Id;

    public String Balance;
    public String VirtualBalance;
    public String BalanceAlert;
    public String IsAgent;
    public String CompanyName;
    public String PAN;
    public String ClientType;
    public String IsSIteAdminAgent;
    public String[] Services;




    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getDeprcty_Userid(){return Deprcyt_User_Id;}

    public void setDeprcyt_User_Id(String deprcyt_user_id){
        Deprcyt_User_Id=deprcyt_user_id;
    }

    public String getClientId() {
        return ClientId;
    }

    public void setClientId(String clientId) {
        ClientId = clientId;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String parentId) {
        ParentId = parentId;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAlternateEmail() {
        return AlternateEmail;
    }

    public void setAlternateEmail(String alternateEmail) {
        AlternateEmail = alternateEmail;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getAlternateMobile() {
        return AlternateMobile;
    }

    public void setAlternateMobile(String alternateMobile) {
        AlternateMobile = alternateMobile;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
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

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getActivationCode() {
        return ActivationCode;
    }

    public void setActivationCode(String activationCode) {
        ActivationCode = activationCode;
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

    public String getLoginType() {
        return LoginType;
    }

    public void setLoginType(String loginType) {
        LoginType = loginType;
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




    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }
    public String getVirtualBalance() {
        return VirtualBalance;
    }

    public void setVirtualBalance(String virtualBalance) {
        VirtualBalance = virtualBalance;
    }

    public String getBalanceAlert() {
        return BalanceAlert;
    }

    public void setBalanceAlert(String balanceAlert) {
        BalanceAlert = balanceAlert;
    }

    public String getIsAgent() {
        return IsAgent;
    }

    public void setIsAgent(String isAgent) {
        IsAgent = isAgent;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getPAN() {
        return PAN;
    }

    public void setPAN(String PAN) {
        this.PAN = PAN;
    }

    public String getClientType() {
        return ClientType;
    }

    public void setClientType(String clientType) {
        ClientType = clientType;
    }

    public String getIsSIteAdminAgent() {
        return IsSIteAdminAgent;
    }

    public void setIsSIteAdminAgent(String isSIteAdminAgent) {
        IsSIteAdminAgent = isSIteAdminAgent;
    }

    public String[] getServices() {
        return Services;
    }

    public void setServices(String[] services) {
        Services = services;
    }
}
