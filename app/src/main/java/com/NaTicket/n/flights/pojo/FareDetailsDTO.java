package com.NaTicket.n.flights.pojo;

import java.io.Serializable;

/**
 * Created by Nagarjuna on 12/30/2017.
 */

public class FareDetailsDTO implements Serializable {

    private Chargablefare_DTO ChargeableFares;

    public Chargablefare_DTO getChargeableFares() { return this.ChargeableFares; }

    public void setChargeableFares(Chargablefare_DTO ChargeableFares) { this.ChargeableFares = ChargeableFares; }

    private NonchargeableFaresDTO NonchargeableFares;

    public NonchargeableFaresDTO getNonchargeableFares() { return this.NonchargeableFares; }

    public void setNonchargeableFares(NonchargeableFaresDTO NonchargeableFares) { this.NonchargeableFares = NonchargeableFares; }

    private FareBreakUpDTO FareBreakUp;

    public FareBreakUpDTO getFareBreakUp() { return this.FareBreakUp; }

    public void setFareBreakUp(FareBreakUpDTO FareBreakUp) { this.FareBreakUp = FareBreakUp; }

    private int OCTax;

    public int getOCTax() { return this.OCTax; }

    public void setOCTax(int OCTax) { this.OCTax = OCTax; }

    private double TotalFare;

    public double getTotalFare() { return this.TotalFare; }

    public void setTotalFare(double TotalFare) { this.TotalFare = TotalFare; }

    private int ResponseStatus;

    public int getResponseStatus() { return this.ResponseStatus; }

    public void setResponseStatus(int ResponseStatus) { this.ResponseStatus = ResponseStatus; }

    private int Status;

    public int getStatus() { return this.Status; }

    public void setStatus(int Status) { this.Status = Status; }

    private String Message;

    public String getMessage() { return this.Message; }

    public void setMessage(String Message) { this.Message = Message; }

    private boolean IsGSTMandatory;

    public boolean isGSTMandatory() {
        return IsGSTMandatory;
    }

    public void setGSTMandatory(boolean GSTMandatory) {
        IsGSTMandatory = GSTMandatory;
    }
}
