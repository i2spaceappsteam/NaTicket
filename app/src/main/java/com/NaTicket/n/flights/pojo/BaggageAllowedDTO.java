package com.NaTicket.n.flights.pojo;

import java.io.Serializable;

/**
 * Created by Ankit on 30-12-2017.
 */

public class BaggageAllowedDTO implements Serializable {

    private String CheckInBaggage;

    public String getCheckInBaggage() { return this.CheckInBaggage; }

    public void setCheckInBaggage(String CheckInBaggage) { this.CheckInBaggage = CheckInBaggage; }

    private String HandBaggage;

    public String getHandBaggage() { return this.HandBaggage; }

    public void setHandBaggage(String HandBaggage) { this.HandBaggage = HandBaggage; }
}
