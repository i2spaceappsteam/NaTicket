package com.NaTicket.n.flights.pojo;

import java.io.Serializable;

/**
 * Created by Nagarjuna on 12/30/2017.
 */

public class RequestDetailsDTO implements Serializable {

    private String Mode;

    public String getMode() { return this.Mode; }

    public void setMode(String Mode) { this.Mode = Mode; }

    private String Source;

    public String getSource() { return this.Source; }

    public void setSource(String Source) { this.Source = Source; }

    private String Destination;

    public String getDestination() { return this.Destination; }

    public void setDestination(String Destination) { this.Destination = Destination; }

    private String Adults;

    public String getAdults() { return this.Adults; }

    public void setAdults(String Adults) { this.Adults = Adults; }

    private String Children;

    public String getChildren() { return this.Children; }

    public void setChildren(String Children) { this.Children = Children; }

    private String Infants;

    public String getInfants() { return this.Infants; }

    public void setInfants(String Infants) { this.Infants = Infants; }

    private String DepartDate;

    public String getDepartDate() { return this.DepartDate; }

    public void setDepartDate(String DepartDate) { this.DepartDate = DepartDate; }

    private String ReturnDate;

    public String getReturnDate() { return this.ReturnDate; }

    public void setReturnDate(String ReturnDate) { this.ReturnDate = ReturnDate; }

    private String MultiCityearch;

    public String getMultiCityearch() { return this.MultiCityearch; }

    public void setMultiCityearch(String MultiCityearch) { this.MultiCityearch = MultiCityearch; }
}
