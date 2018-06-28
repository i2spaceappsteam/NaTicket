package com.NaTicket.n.flights.pojo;

import java.io.Serializable;

/**
 * Created by Nagarjuna on 12/30/2017.
 */

public class NonchargeableFaresDTO implements Serializable {

    private double TCharge;

    public double getTCharge() { return this.TCharge; }

    public void setTCharge(double TCharge) { this.TCharge = TCharge; }

    private double TMarkup;

    public double getTMarkup() { return this.TMarkup; }

    public void setTMarkup(double TMarkup) { this.TMarkup = TMarkup; }

    private double TSdiscount;

    public double getTSdiscount() { return this.TSdiscount; }

    public void setTSdiscount(double TSdiscount) { this.TSdiscount = TSdiscount; }
}
