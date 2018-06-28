package com.NaTicket.n.flights.pojo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Nagarjuna on 12/30/2017.
 */

public class Flights_Avalability_DTO implements Serializable {


    private ArrayList<InternationalFlightsDTO> InternationalFlights;

    private ArrayList<DomesticOnwardFlightDTO> DomesticOnwardFlights;

    private ArrayList<DomesticReturnFlightDTO> DomesticReturnFlights;

    private int ProvidersCount;

    private int ResponseStatus;

    private String Message;

    public ArrayList<InternationalFlightsDTO> getInternationalFlights() { return this.InternationalFlights; }

    public void setInternationalFlights(ArrayList<InternationalFlightsDTO> InternationalFlights) { this.InternationalFlights = InternationalFlights; }

    public ArrayList<DomesticOnwardFlightDTO> getDomesticOnwardFlights() { return this.DomesticOnwardFlights; }

    public void setDomesticOnwardFlights(ArrayList<DomesticOnwardFlightDTO> DomesticOnwardFlights) { this.DomesticOnwardFlights = DomesticOnwardFlights; }

    public ArrayList<DomesticReturnFlightDTO> getDomesticReturnFlights() { return this.DomesticReturnFlights; }

    public void setDomesticReturnFlights(ArrayList<DomesticReturnFlightDTO> DomesticReturnFlights) { this.DomesticReturnFlights = DomesticReturnFlights; }

    public int getProvidersCount() { return this.ProvidersCount; }

    public void setProvidersCount(int ProvidersCount) { this.ProvidersCount = ProvidersCount; }

    public int getResponseStatus() { return this.ResponseStatus; }

    public void setResponseStatus(int ResponseStatus) { this.ResponseStatus = ResponseStatus; }

    public String getMessage() { return this.Message; }

    public void setMessage(String Message) { this.Message = Message; }

}
