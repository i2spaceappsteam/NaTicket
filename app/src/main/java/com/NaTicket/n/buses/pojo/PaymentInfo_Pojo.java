package com.NaTicket.n.buses.pojo;

import java.io.Serializable;

/**
 * Created by Nagarjuna on 14-01-2018.
 */

public class PaymentInfo_Pojo implements Serializable {

    private String PaymentName;

    public String getPaymentName() { return this.PaymentName; }

    public void setPaymentName(String PaymentName) { this.PaymentName = PaymentName; }

    private String PaymentId;

    public String getPaymentId() { return this.PaymentId; }

    public void setPaymentId(String PaymentId) { this.PaymentId = PaymentId; }

    private String ResponseCode;

    public String getResponseCode() { return this.ResponseCode; }

    public void setResponseCode(String ResponseCode) { this.ResponseCode = ResponseCode; }

    private String ResponseMessage;

    public String getResponseMessage() { return this.ResponseMessage; }

    public void setResponseMessage(String ResponseMessage) { this.ResponseMessage = ResponseMessage; }

    private String MerchtRefNumber;

    public String getMerchtRefNumber() { return this.MerchtRefNumber; }

    public void setMerchtRefNumber(String MerchtRefNumber) { this.MerchtRefNumber = MerchtRefNumber; }

    private String Amount;

    public String getAmount() { return this.Amount; }

    public void setAmount(String Amount) { this.Amount = Amount; }

    private String Mode;

    public String getMode() { return this.Mode; }

    public void setMode(String Mode) { this.Mode = Mode; }

    private String TransactionId;

    public String getTransactionId() { return this.TransactionId; }

    public void setTransactionId(String TransactionId) { this.TransactionId = TransactionId; }

    private String PaymentMethod;

    public String getPaymentMethod() { return this.PaymentMethod; }

    public void setPaymentMethod(String PaymentMethod) { this.PaymentMethod = PaymentMethod; }

    private String PaymentStatus;

    public String getPaymentStatus() { return this.PaymentStatus; }

    public void setPaymentStatus(String PaymentStatus) { this.PaymentStatus = PaymentStatus; }
}

