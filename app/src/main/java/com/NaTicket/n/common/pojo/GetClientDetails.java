package com.NaTicket.n.common.pojo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ankit on 8/16/2017.
 */

public class GetClientDetails implements Serializable {

    private boolean IsCorporate;

    private String ClientId;

    private String Name;

    private String Domain;

    private String IP;

    private String ConsumerKey;

    private String ConsumerSecret;

    private String Email;

    private int BusinessType;

    private int ClientType;

    private int Status;

    private String Template;

    private ArrayList<Integer> Services;

    private String ContactNo;

    private String Telephone;

    private String Address;

    private int Country;

    private String Languages;

    private String Currencies;

    private ArrayList<PaymentGatewaysDTO> PaymentGateways;

    public boolean isCorporate() {
        return IsCorporate;
    }

    public void setCorporate(boolean corporate) {
        IsCorporate = corporate;
    }

    public String getClientId() {
        return ClientId;
    }

    public void setClientId(String clientId) {
        ClientId = clientId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDomain() {
        return Domain;
    }

    public void setDomain(String domain) {
        Domain = domain;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getConsumerKey() {
        return ConsumerKey;
    }

    public void setConsumerKey(String consumerKey) {
        ConsumerKey = consumerKey;
    }

    public String getConsumerSecret() {
        return ConsumerSecret;
    }

    public void setConsumerSecret(String consumerSecret) {
        ConsumerSecret = consumerSecret;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getBusinessType() {
        return BusinessType;
    }

    public void setBusinessType(int businessType) {
        BusinessType = businessType;
    }

    public int getClientType() {
        return ClientType;
    }

    public void setClientType(int clientType) {
        ClientType = clientType;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getTemplate() {
        return Template;
    }

    public void setTemplate(String template) {
        Template = template;
    }

    public ArrayList<Integer> getServices() {
        return Services;
    }

    public void setServices(ArrayList<Integer> services) {
        Services = services;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
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

    public int getCountry() {
        return Country;
    }

    public void setCountry(int country) {
        Country = country;
    }

    public String getLanguages() {
        return Languages;
    }

    public void setLanguages(String languages) {
        Languages = languages;
    }

    public String getCurrencies() {
        return Currencies;
    }

    public void setCurrencies(String currencies) {
        Currencies = currencies;
    }

    public ArrayList<PaymentGatewaysDTO> getPaymentGateways() {
        return PaymentGateways;
    }

    public void setPaymentGateways(ArrayList<PaymentGatewaysDTO> paymentGateways) {
        PaymentGateways = paymentGateways;
    }
}
