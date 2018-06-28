package com.NaTicket.n.flights.pojo;

import java.io.Serializable;

/**
 * Created by Nagarjuna on 12/30/2017.
 */

public class PartnerFareDatailsDTO implements Serializable {


    private String NetFares;

    public String getNetFares() { return this.NetFares; }

    public void setNetFares(String NetFares) { this.NetFares = NetFares; }

    private String Commission;

    public String getCommission() { return this.Commission; }

    public void setCommission(String Commission) { this.Commission = Commission; }

    private int CommissionType;

    public int getCommissionType() { return this.CommissionType; }

    public void setCommissionType(int CommissionType) { this.CommissionType = CommissionType; }
}
