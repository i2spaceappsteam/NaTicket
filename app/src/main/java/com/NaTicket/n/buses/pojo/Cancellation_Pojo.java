package com.NaTicket.n.buses.pojo;

import java.io.Serializable;

/**
 * Created by Ankit on 14-01-2018.
 */

public class Cancellation_Pojo implements Serializable {

    private int CancellationID;

    public int getCancellationID() { return this.CancellationID; }

    public void setCancellationID(int CancellationID) { this.CancellationID = CancellationID; }

    private int ClientID;

    public int getClientID() { return this.ClientID; }

    public void setClientID(int ClientID) { this.ClientID = ClientID; }

    private String ReferenceNumber;

    public String getReferenceNumber() { return this.ReferenceNumber; }

    public void setReferenceNumber(String ReferenceNumber) { this.ReferenceNumber = ReferenceNumber; }

    private int ServiceType;

    public int getServiceType() { return this.ServiceType; }

    public void setServiceType(int ServiceType) { this.ServiceType = ServiceType; }

    private int UserId;

    public int getUserId() { return this.UserId; }

    public void setUserId(int UserId) { this.UserId = UserId; }

    private String CancelledSeats;

    public String getCancelledSeats() { return this.CancelledSeats; }

    public void setCancelledSeats(String CancelledSeats) { this.CancelledSeats = CancelledSeats; }

    private double RefundAmount;

    public double getRefundAmount() { return this.RefundAmount; }

    public void setRefundAmount(double RefundAmount) { this.RefundAmount = RefundAmount; }

    private boolean RefundStatus;

    public boolean getRefundStatus() { return this.RefundStatus; }

    public void setRefundStatus(boolean RefundStatus) { this.RefundStatus = RefundStatus; }

    private double CancellationCharges;

    public double getCancellationCharges() { return this.CancellationCharges; }

    public void setCancellationCharges(double CancellationCharges) { this.CancellationCharges = CancellationCharges; }

    private int RefundType;

    public int getRefundType() { return this.RefundType; }

    public void setRefundType(int RefundType) { this.RefundType = RefundType; }

    private int CancellationStatus;

    public int getCancellationStatus() { return this.CancellationStatus; }

    public void setCancellationStatus(int CancellationStatus) { this.CancellationStatus = CancellationStatus; }

    private String RefundRemarks;

    public String getRefundRemarks() { return this.RefundRemarks; }

    public void setRefundRemarks(String RefundRemarks) { this.RefundRemarks = RefundRemarks; }

    private String CancellationRemarks;

    public String getCancellationRemarks() { return this.CancellationRemarks; }

    public void setCancellationRemarks(String CancellationRemarks) { this.CancellationRemarks = CancellationRemarks; }

    private String CancellationResponse;

    public String getCancellationResponse() { return this.CancellationResponse; }

    public void setCancellationResponse(String CancellationResponse) { this.CancellationResponse = CancellationResponse; }

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
}