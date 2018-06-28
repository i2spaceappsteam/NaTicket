package com.NaTicket.n.common.pojo;

import java.io.Serializable;

/**
 * Created by Ankit on 8/16/2017.
 */

public class PaymentGatewaysDTO implements Serializable {

    private int PaymentGatewayId;

    private int Status;

    private String PaymentGatewayName;

    private String PaymentGatewaySandboxUrl;

    private String PaymentGatewayLiveUrl;

    private String MerchantId;

    private String SecretKey;

    private String AccessCode;

    private boolean IsAdminPaymentGateway;

    private String CurrencyCode;

    public int getPaymentGatewayId() {
        return PaymentGatewayId;
    }

    public void setPaymentGatewayId(int paymentGatewayId) {
        PaymentGatewayId = paymentGatewayId;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getPaymentGatewayName() {
        return PaymentGatewayName;
    }

    public void setPaymentGatewayName(String paymentGatewayName) {
        PaymentGatewayName = paymentGatewayName;
    }

    public String getPaymentGatewaySandboxUrl() {
        return PaymentGatewaySandboxUrl;
    }

    public void setPaymentGatewaySandboxUrl(String paymentGatewaySandboxUrl) {
        PaymentGatewaySandboxUrl = paymentGatewaySandboxUrl;
    }

    public String getPaymentGatewayLiveUrl() {
        return PaymentGatewayLiveUrl;
    }

    public void setPaymentGatewayLiveUrl(String paymentGatewayLiveUrl) {
        PaymentGatewayLiveUrl = paymentGatewayLiveUrl;
    }

    public String getMerchantId() {
        return MerchantId;
    }

    public void setMerchantId(String merchantId) {
        MerchantId = merchantId;
    }

    public String getSecretKey() {
        return SecretKey;
    }

    public void setSecretKey(String secretKey) {
        SecretKey = secretKey;
    }

    public String getAccessCode() {
        return AccessCode;
    }

    public void setAccessCode(String accessCode) {
        AccessCode = accessCode;
    }

    public boolean isAdminPaymentGateway() {
        return IsAdminPaymentGateway;
    }

    public void setAdminPaymentGateway(boolean adminPaymentGateway) {
        IsAdminPaymentGateway = adminPaymentGateway;
    }

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        CurrencyCode = currencyCode;
    }
}