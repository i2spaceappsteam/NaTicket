package com.NaTicket.n.flights.pojo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Nagarjuna on 12/30/2017.
 */

public class FareBreakUpDTO implements Serializable {

    private ArrayList<FareAryDTO> FareAry;

    public ArrayList<FareAryDTO> getFareAry() { return this.FareAry; }

    public void setFareAry(ArrayList<FareAryDTO> FareAry) { this.FareAry = FareAry; }



}
